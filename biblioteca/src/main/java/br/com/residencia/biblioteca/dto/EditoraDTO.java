package br.com.residencia.biblioteca.dto;

import java.util.List;

public class EditoraDTO {
	private Integer codigoEditora;
	private String nome;
	private List<LivroDTO> listaLivrosDTO;
	private String imagemNome;
	private String imagemFileName;
	private String imagemUrl;
	
	public EditoraDTO() {
	}

	public EditoraDTO(Integer codigoEditora, String nome) {
		this.codigoEditora = codigoEditora;
		this.nome = nome;
	}

//	Getters and Setters 
	public Integer getCodigoEditora() {
		return codigoEditora;
	}

	public List<LivroDTO> getListaLivrosDTO() {
		return listaLivrosDTO;
	}

	public void setListaLivrosDTO(List<LivroDTO> listaLivrosDTO) {
		this.listaLivrosDTO = listaLivrosDTO;
	}

	public void setCodigoEditora(Integer codigoEditora) {
		this.codigoEditora = codigoEditora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public String getImagemFileName() {
		return imagemFileName;
	}

	public void setImagemFileName(String imagemFileName) {
		this.imagemFileName = imagemFileName;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}
}
