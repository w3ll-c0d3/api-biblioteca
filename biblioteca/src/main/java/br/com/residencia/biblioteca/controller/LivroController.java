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

import br.com.residencia.biblioteca.entity.Livro;
import br.com.residencia.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/livros")
public class LivroController {
//	C = Create
//	R = Read
//	U = Update
//	D = Delete
	
	@Autowired
	LivroService livroService;

	@GetMapping("/search")
	public ResponseEntity<List<Livro>> getAllLivros() {
		return new ResponseEntity<>(livroService.getAllLivros(), HttpStatus.OK);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Livro> getLivroById(@PathVariable Integer id) {
		return new ResponseEntity<>(livroService.getLivroById(id), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Livro> saveLivro(@RequestBody Livro livro) {
		return new ResponseEntity<>(livroService.saveLivro(livro), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Livro> deleteLivro(@PathVariable Integer id) {
		return new ResponseEntity<>(livroService.deleteLivro(id), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Livro> updateLivro(@RequestBody Livro livro, @PathVariable Integer id) {
		return new ResponseEntity<>(livroService.updateLivro(livro, id), HttpStatus.OK);
	}
}
