package br.com.residencia.biblioteca.entity;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigolivro")
	private int codigoLivro;
	
	@Column(name = "nomelivro")
	private String nomeLivro;
	
	@Column(name = "nomeautor")
	private String nomeAutor;
	
	@Column(name = "datalancamento")
	private Instant dataLancamento;
	
	@Column(name = "codigoisbn")
	private int codigoIsbn;
	
	@ManyToOne
	@JoinColumn(name = "codigoeditora", referencedColumnName = "codigoeditora")
	private Editora editora;
	
	@OneToOne(mappedBy = "livro")
	private Emprestimo emprestimo;

//	Getters and Setters
	public Editora getEditora() {
		return editora;
	}
	
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	public Emprestimo getEmprestimo() {
		return emprestimo;
	}
	
	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	public int getCodigoLivro() {
		return codigoLivro;
	}
	
	public void setCodigoLivro(int codigoLivro) {
		this.codigoLivro = codigoLivro;
	}
	
	public String getNomeLivro() {
		return nomeLivro;
	}
	
	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}
	
	public String getNomeAutor() {
		return nomeAutor;
	}
	
	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	
	public Instant getDataLancamento() {
		return dataLancamento;
	}
	
	public void setDataLancamento(Instant dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	public int getCodigoIsbn() {
		return codigoIsbn;
	}
	
	public void setCodigoIsbn(int codigoIsbn) {
		this.codigoIsbn = codigoIsbn;
	}
	
	public Editora getCodigoEditora() {
		return editora;
	}
	
	public void setCodigoEditora(Editora codigoEditora) {
		this.editora = codigoEditora;
	}
}
