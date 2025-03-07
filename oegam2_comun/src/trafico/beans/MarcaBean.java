package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;


public class MarcaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1100257305996087013L;

	private BigDecimal codigoMarca;	
	private String marca; 
	private String marcaSinEditar;
	
	public MarcaBean() {
	}
	
	public MarcaBean(boolean inicializar) {
	}
	



	public String getMarcaSinEditar() {
		return marcaSinEditar;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public BigDecimal getCodigoMarca() {
		return codigoMarca;
	}



	public void setCodigoMarca(BigDecimal codigoMarca) {
		this.codigoMarca = codigoMarca;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}



	



	

}
