package br.com.residencia.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.residencia.biblioteca.entity.Editora;
import br.com.residencia.biblioteca.service.EditoraService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/editoras")
public class EditoraController {
//	C = Create
//	R = Read
//	U = Update
//	D = Delete
	
	@Autowired
	EditoraService editoraService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Editora>> getAllEditoras() {
		return new ResponseEntity<>(editoraService.getAllEditora(), HttpStatus.OK);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Editora> getEditoraById(@PathVariable Integer id) {
		return new ResponseEntity<>(editoraService.getEditoraById(id), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Editora> saveEditora(@RequestBody Editora editora) {
		return new ResponseEntity<>(editoraService.saveEditora(editora), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Editora> deleteEditora(@PathVariable Integer id) {
		return new ResponseEntity<>(editoraService.deleteEditora(id), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Editora> updateEditora(@RequestBody Editora editora, @PathVariable Integer id) {
		return new ResponseEntity<>(editoraService.updateEditora(editora, id), HttpStatus.OK);
	}
}
