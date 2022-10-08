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

import br.com.residencia.biblioteca.entity.Emprestimo;
import br.com.residencia.biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
//	C = Create
//	R = Read
//	U = Update
//	D = Delete
	
	@Autowired
	EmprestimoService emprestimoService;

	@GetMapping("/search")
	public ResponseEntity<List<Emprestimo>> getAllEmprestimos() {
		return new ResponseEntity<>(emprestimoService.getAllEmprestimos(), HttpStatus.OK);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Emprestimo> getEmprestimoById(@PathVariable Integer id) {
		return new ResponseEntity<>(emprestimoService.getEmprestimoById(id), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Emprestimo> saveEmprestimo(@RequestBody Emprestimo emprestimo) {
		return new ResponseEntity<>(emprestimoService.saveEmprestimo(emprestimo), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Emprestimo> deleteEmprestimo(@PathVariable Integer id) {
		return new ResponseEntity<>(emprestimoService.deleteEmprestimo(id), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Emprestimo> updateEmprestimo(@RequestBody Emprestimo emprestimo, @PathVariable Integer id) {
		return new ResponseEntity<>(emprestimoService.updateEmprestimo(emprestimo, id), HttpStatus.OK);
	}
}
