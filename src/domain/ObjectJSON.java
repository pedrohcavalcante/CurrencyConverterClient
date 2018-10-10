package domain;

public class ObjectJSON {
	
	String moedaDestino;
	double valor;
	String moedaOrigem;
	
	public ObjectJSON(String moedaDestino, double valor, String moedaOrigem) {
		super();
		this.moedaDestino = moedaDestino;
		this.valor = valor;
		this.moedaOrigem = moedaOrigem;
	}
	
	public ObjectJSON() {
		
	}

	public String getMoedaDestino() {
		return moedaDestino;
	}

	public void setMoedaDestino(String moedaDestino) {
		this.moedaDestino = moedaDestino;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getMoedaOrigem() {
		return moedaOrigem;
	}

	public void setMoedaOrigem(String moedaOrigem) {
		this.moedaOrigem = moedaOrigem;
	}
	
	
	
}
