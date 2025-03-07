package escrituras.beans.dao;

import java.math.BigDecimal;

/**
 * Bean que almacena datos de la tabla TIPO_IMPUESTO.
 *
 */
public class TipoImpuestoDao {

	 private String tipo_Impuesto;
	 private String modelo;
	 private String concepto;
	 private BigDecimal monto;
	 private String descripcion;
	 private String fundamento_exencion;
	 private String sujeto_exento;//S,E, null
	
	 
	 
	 public TipoImpuestoDao() {
		
	}


	public BigDecimal getMonto() {
		return monto;
	}


	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getTipo_Impuesto() {
		return tipo_Impuesto;
	}
	

	public void setTipo_Impuesto(String tipoImpuesto) {
		tipo_Impuesto = tipoImpuesto;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getConcepto() {
		return concepto;
	}


	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	public String getFundamento_exencion() {
		return fundamento_exencion;
	}


	public void setFundamento_exencion(String fundamentoExencion) {
		fundamento_exencion = fundamentoExencion;
	}


	public String getSujeto_exento() {
		return sujeto_exento;
	}


	public void setSujeto_exento(String sujetoExento) {
		sujeto_exento = sujetoExento;
	}	 
	 
}
