package br.com.residencia.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.biblioteca.dto.EditoraDTO;
import br.com.residencia.biblioteca.entity.Editora;
import br.com.residencia.biblioteca.repository.EditoraRepository;

@Service
public class EditoraService {
	@Autowired
	EditoraRepository editoraRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<Editora> getAllEditora() {
		return editoraRepository.findAll();	
	}
	
	private EditoraDTO converteEntitytoDTO(Editora editora) {
		EditoraDTO editoraDTO = new EditoraDTO();
		editoraDTO = (modelMapper.map(editora, EditoraDTO.class));
		return editoraDTO;	
	}
	
	public List<EditoraDTO> getAllEditoraDTO() {
		List<Editora> editora = new ArrayList<Editora>();
		List<EditoraDTO> editoraDTO = new ArrayList<EditoraDTO>();
		editora = getAllEditora();
		editora.forEach(edt -> {
			editoraDTO.add(converteEntitytoDTO(edt));
		});
		return editoraDTO;
	}
	
	public EditoraDTO getEditoraDtoById(Integer id) {
		Editora editora = editoraRepository.findById(id).orElse(null);
		if(editora != null) {
			return converteEntitytoDTO(editora);
		}
		return null;
	}
	
	public Editora getEditoraById(Integer id) {
		return editoraRepository.findById(id).get();
//		return EditoraRepository.findById(id).orElse(null);
	}
	
	public Editora saveEditora(Editora editora) {
		return editoraRepository.save(editora);
	}
	
	public EditoraDTO saveEditoraDTO(EditoraDTO editoraDTO) {
		Editora editora = new Editora();
		editora.setNome(editoraDTO.getNome());
		
		Editora novaEditora = editoraRepository.save(editora);
		EditoraDTO novaEditoraDTO = new EditoraDTO();
		novaEditoraDTO.setCodigoEditora(novaEditora.getCodigoEditora());
		novaEditoraDTO.setNome(novaEditora.getNome());
		
		return novaEditoraDTO;
	}
	
	public Editora updateEditora(Editora Editora, Integer id) {
		Editora editoraExistenteNoBanco = getEditoraById(id);
		
		editoraExistenteNoBanco.setNome(Editora.getNome());
		return editoraRepository.save(editoraExistenteNoBanco);
	}
	
	public Editora deleteEditora(Integer id) {
		if(getEditoraById(id) != null) {
			editoraRepository.deleteById(id);
		}
		return getEditoraById(id);
	}
		
	public Boolean deleteEditoraBool(Integer id) {
		if(getEditoraById(id) != null) {	
			editoraRepository.deleteById(id);
			return true;
		} 
		return true;
	}
}
	
