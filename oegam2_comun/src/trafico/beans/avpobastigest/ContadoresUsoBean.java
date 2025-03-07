package trafico.beans.avpobastigest;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ContadoresUsoBean{

      private BigDecimal idContadoresUso; 
      private BigDecimal idAplicacion; 
      private Timestamp fecha; 
      private BigDecimal idUsuario; 
      private BigDecimal idContrato; 
      private String valorTexto; 
      private BigDecimal valorEntero; 
      private BigDecimal valorEnteroAux; 
      private BigDecimal valorDecimal; 

      //Añadidos para el listado de búsqueda
      private String razonSocial;
      private String numColegiado;
      private String nombreAplicacion;
      private BigDecimal contador;
      
      
    /**
     * Constructor vacio del Bean
     */
    public ContadoresUsoBean () {
    } 
  
  
    public BigDecimal getIdContadoresUso() {
		return idContadoresUso;
	}






	public void setIdContadoresUso(BigDecimal idContadoresUso) {
		this.idContadoresUso = idContadoresUso;
	}






	public BigDecimal getIdAplicacion() {
		return idAplicacion;
	}






	public void setIdAplicacion(BigDecimal idAplicacion) {
		this.idAplicacion = idAplicacion;
	}






	public BigDecimal getIdUsuario() {
		return idUsuario;
	}






	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}






	public BigDecimal getIdContrato() {
		return idContrato;
	}






	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}






	public String getValorTexto() {
		return valorTexto;
	}






	public void setValorTexto(String valorTexto) {
		this.valorTexto = valorTexto;
	}






	public BigDecimal getValorEntero() {
		return valorEntero;
	}






	public void setValorEntero(BigDecimal valorEntero) {
		this.valorEntero = valorEntero;
	}






	public BigDecimal getValorEnteroAux() {
		return valorEnteroAux;
	}






	public void setValorEnteroAux(BigDecimal valorEnteroAux) {
		this.valorEnteroAux = valorEnteroAux;
	}






	public BigDecimal getValorDecimal() {
		return valorDecimal;
	}






	public void setValorDecimal(BigDecimal valorDecimal) {
		this.valorDecimal = valorDecimal;
	}






	public String getRazonSocial() {
		return razonSocial;
	}






	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}






	public String getNumColegiado() {
		return numColegiado;
	}






	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}






	public String getNombreAplicacion() {
		return nombreAplicacion;
	}






	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}






	public BigDecimal getContador() {
		return contador;
	}






	public void setContador(BigDecimal contador) {
		this.contador = contador;
	}






	public Timestamp getFecha() {
		return fecha;
	}


	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}


}

