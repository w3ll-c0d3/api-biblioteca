package br.com.residencia.biblioteca.dto.imgbb;

public class ImgBBDTO {
	private Data data;

//	Getters and Setters
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "imgBBDTO [data=" + data + "]";
	}
}
