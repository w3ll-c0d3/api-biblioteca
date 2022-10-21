package br.com.residencia.biblioteca.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.residencia.biblioteca.dto.ConsultaCnpjDTO;
import br.com.residencia.biblioteca.dto.EditoraDTO;
import br.com.residencia.biblioteca.dto.LivroDTO;
import br.com.residencia.biblioteca.dto.imgbb.ImgBBDTO;
import br.com.residencia.biblioteca.entity.Editora;
import br.com.residencia.biblioteca.entity.Livro;
import br.com.residencia.biblioteca.repository.EditoraRepository;
import br.com.residencia.biblioteca.repository.LivroRepository;

@Service
public class EditoraService {
	@Autowired
	EditoraRepository editoraRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	LivroService livroService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	EmailService emailService;
	
	@Value("${imgbb.host.url}")
	private String imgBBHostUrl;
	
	@Value("${imgbb.host.key}")
    private String imgBBHostKey;
	
//	Get
	public List<Editora> getAllEditora() {
		return editoraRepository.findAll();	
	}
	
	public List<EditoraDTO> getAllEditoraDTO() {
		List<Editora> editora = getAllEditora();
		List<EditoraDTO> editoraDTO = new ArrayList<EditoraDTO>();
		editora.forEach(edt -> {
			editoraDTO.add(converteEntitytoDTO(edt));
		});
		return editoraDTO;
	}
	
	public List<EditoraDTO> getAllEditorasLivrosDTO() {
		List<Editora> editora = getAllEditora();
		List<EditoraDTO> listaEditoraDTO = new ArrayList<EditoraDTO>();
		
		editora.forEach(edt -> {
			EditoraDTO editoraDTO = converteEntitytoDTO(edt);
			List<Livro> listaLivros = new ArrayList<>();
			List<LivroDTO> listaLivrosDTO = new ArrayList<>();
			listaLivros = livroRepository.findByEditora(edt);
			
			listaLivros.forEach(lvr -> {
				listaLivrosDTO.add(livroService.converteEntitytoDTO(lvr));
			});
			
			editoraDTO.setListaLivrosDTO(listaLivrosDTO);
			listaEditoraDTO.add(editoraDTO);
		});
		return listaEditoraDTO;
	}
	
	public EditoraDTO getEditoraDtoById(Integer id) {
		Editora editora = editoraRepository.findById(id).orElse(null);
		if(editora != null) {
			return converteEntitytoDTO(editora);
		}
		return null;
	}
	
	public Editora getEditoraById(Integer id) {
		return editoraRepository.findById(id).orElse(null);
	}
	
	public ConsultaCnpjDTO consultaCnpjApiExterna(String cnpj) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://receitaws.com.br/v1/cnpj/{cnpj}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cnpj", cnpj);
		
		ConsultaCnpjDTO consultaCnpjDTO = restTemplate.getForObject(uri, ConsultaCnpjDTO.class, params);
		return consultaCnpjDTO;
	}

//	Save
	public Editora saveEditora(Editora editora) {
		editora = formatToUpper(editora);
		return editoraRepository.save(editora);
	}
	
	public Editora saveFromExternalSource(String cnpj) {
		ConsultaCnpjDTO cnpjResponse = consultaCnpjApiExterna(cnpj);
		Editora editora = new Editora();
		editora.setNome(cnpjResponse.getNome());
		editora = formatToUpper(editora);
		return editoraRepository.save(editora);
	}
	
	public List<Editora> saveAllEditoras(List<Editora> editora) {
		editora.forEach(edt -> {
			formatToUpper(edt);
		});
		return editoraRepository.saveAll(editora);
	}
	
	public EditoraDTO saveEditoraDTO(EditoraDTO editoraDTO) {
		editoraDTO = formatToUpperDTO(editoraDTO);
		Editora editora = toEntity(editoraDTO);
		Editora novaEditora = editoraRepository.save(editora);
		
		EditoraDTO editoraAtualizadaDTO = converteEntitytoDTO(novaEditora);
		return editoraAtualizadaDTO;
	}
	
	public List<EditoraDTO> saveAllEditorasDTO(List<EditoraDTO> editoraDTO) {
		editoraDTO.forEach(edt -> {
			formatToUpperDTO(edt);
			editoraRepository.save(toEntity(edt));
		});
		return editoraDTO;
	}
//	public EditoraDTO saveEditoraDTOotimizado(EditoraDTO editoraDTO) {
//		Editora novaEditora = editoraRepository.save(toEntity(editoraDTO));	
//		return converteEntitytoDTO(novaEditora);
//	}
	public EditoraDTO saveEditoraFotoDTO(String editoraTxt, MultipartFile file) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		String serverUrl = imgBBHostUrl + imgBBHostKey;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
		
		ContentDisposition contentDisposition = ContentDisposition
				.builder("form-data")
				.name("image")
				.filename(file.getOriginalFilename())
				.build();
		
		fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
		
		HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("image", fileEntity);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity =
				new HttpEntity<>(body, headers);
		
		ResponseEntity<ImgBBDTO> response = null;
		ImgBBDTO imgDTO = new ImgBBDTO();
		Editora novaEditora = new Editora(); 
		try {
			response = restTemplate.exchange(
					serverUrl,
					HttpMethod.POST,
					requestEntity,
					ImgBBDTO.class);
			
			imgDTO = response.getBody();
			System.out.println("ImgBBDTO: " + imgDTO.getData().toString());
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		
		//Converte os dados da editora recebidos no formato String em Entidade
		//  Coleta os dados da imagem, após upload via API, e armazena na Entidade Editora
		if(null != imgDTO) {
			Editora editoraFromJson = convertEditoraFromStringJson(editoraTxt);
			editoraFromJson.setImagemFileName(imgDTO.getData().getImage().getFilename());
			editoraFromJson.setImagemNome(imgDTO.getData().getTitle());
			editoraFromJson.setImagemUrl(imgDTO.getData().getUrl());
			novaEditora = editoraRepository.save(editoraFromJson);
		}
		
		return converteEntitytoDTO(novaEditora);
	}
	
	private Editora convertEditoraFromStringJson(String editoraJson) {
		Editora editora = new Editora();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			editora = objectMapper.readValue(editoraJson, Editora.class);
		} catch (IOException err) {
			System.out.printf("Ocorreu um erro ao tentar converter a string json para um instância da entidade Editora", err.toString());
		}
		
		return editora;
	}
	
//	Update
	public Editora updateEditora(Editora editora, Integer id) {
		editora = formatToUpper(editora);
		Editora editoraExistenteNoBanco = getEditoraById(id);
		editoraExistenteNoBanco.setNome(editora.getNome());
		emailService.sendMail("wellington.lima6@aluno.senai.br", "Teste", "Body");
		return editoraRepository.save(editoraExistenteNoBanco);
	}
	
	public EditoraDTO updateEditoraDTO(EditoraDTO editoraDTO, Integer id) {
		editoraDTO = formatToUpperDTO(editoraDTO);
		Editora editoraExistenteNoBanco = getEditoraById(id);
		EditoraDTO editoraAtualizadaDTO = new EditoraDTO();
		if(editoraExistenteNoBanco != null) {
			editoraDTO.setCodigoEditora(editoraExistenteNoBanco.getCodigoEditora());
			editoraExistenteNoBanco = toEntity(editoraDTO);
			Editora editoraAtualizada = editoraRepository.save(editoraExistenteNoBanco);
			editoraAtualizadaDTO = converteEntitytoDTO(editoraAtualizada);
		}
		return editoraAtualizadaDTO;
	}
	
//	Delete
	public Editora deleteEditora(Integer id) {
		if(getEditoraById(id) != null) {
			editoraRepository.deleteById(id);
		}
		return getEditoraById(id);
	}
		
	public EditoraDTO deleteEditoraDTO(Integer id) {
		editoraRepository.deleteById(id);
		return getEditoraDtoById(id);
	}
//	public Boolean deleteEditoraBool(Integer id) {
//		if(getEditoraById(id) != null) {	
//			editoraRepository.deleteById(id);
//			return true;
//		} 
//		return true;
//	}
	
//	Convert
	private EditoraDTO converteEntitytoDTO(Editora editora) {
		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO = (modelMapper.map(editora, EditoraDTO.class));
		return editoraDTO;	
	}
	
	private Editora toEntity(EditoraDTO editoraDTO) {
		Editora editora = new Editora();
		editora.setNome(editoraDTO.getNome());
		return editora;	
	}
	
//	Format
	private Editora formatToUpper(Editora editora) {
		editora.setNome(editora.getNome().toUpperCase());
		return editora;
	}
	
	private EditoraDTO formatToUpperDTO(EditoraDTO editoraDTO) {
		editoraDTO.setNome(editoraDTO.getNome().toUpperCase());
		return editoraDTO;
	}
}
	

