package trafico.beans;

import java.math.BigDecimal;



public class ModeloBean{
	private String marca;
	private String modelo;
	private String descripcion;
	private BigDecimal cilindrada;
	private BigDecimal potFiscal;
	
	public ModeloBean() {
		super();
		
	}
	
	public ModeloBean(boolean inicializar) {
		super();
		
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(BigDecimal cilindrada) {
		this.cilindrada = cilindrada;
	}

	public BigDecimal getPotFiscal() {
		return potFiscal;
	}

	public void setPotFiscal(BigDecimal potFiscal) {
		this.potFiscal = potFiscal;
	}
	
}
