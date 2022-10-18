package br.com.residencia.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.biblioteca.dto.EditoraDTO;
import br.com.residencia.biblioteca.dto.LivroDTO;
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

//	Save
	public Editora saveEditora(Editora editora) {
		return editoraRepository.save(editora);
	}
	
	public EditoraDTO saveEditoraDTO(EditoraDTO editoraDTO) {
		Editora editora = toEntity(editoraDTO);
		Editora novaEditora = editoraRepository.save(editora);
		
		EditoraDTO editoraAtualizadaDTO = converteEntitytoDTO(novaEditora);
		return editoraAtualizadaDTO;
	}
	
//	public EditoraDTO saveEditoraDTOotimizado(EditoraDTO editoraDTO) {
//		Editora novaEditora = editoraRepository.save(toEntity(editoraDTO));	
//		return converteEntitytoDTO(novaEditora);
//	}

//	Update
	public Editora updateEditora(Editora editora, Integer id) {
		Editora editoraExistenteNoBanco = getEditoraById(id);
		editoraExistenteNoBanco.setNome(editora.getNome());
		return editoraRepository.save(editoraExistenteNoBanco);
	}
	
	public EditoraDTO updateEditoraDTO(EditoraDTO editoraDTO, Integer id) {
		Editora editoraExistenteNoBanco = getEditoraById(id);
		EditoraDTO editoraAtualizadaDTO = new EditoraDTO();
		if(editoraExistenteNoBanco != null) {
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
}
	

