package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la aplicacion
 *
 */
/*  Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
                decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada*/

public class AplicacionFuncionesDao {

	
	private String codigo_Aplicacion;
	private String codigo_Funcion;
	private String desc_Funcion;
	private String codigo_Funcion_Padre;
	private String url;
	private BigDecimal nivel;
	private BigDecimal orden;
	private String tipo;
	private BigDecimal asignada;
	private Boolean esPadre;
	
	
	public Boolean getEsPadre() {
		return esPadre;
	}
	public void setEsPadre(Boolean esPadre) {
		this.esPadre = esPadre;
	}
	public String getCodigo_Aplicacion() {
		return codigo_Aplicacion;
	}
	public void setCodigo_Aplicacion(String codigoAplicacion) {
		codigo_Aplicacion = codigoAplicacion;
	}
	public String getCodigo_Funcion() {
		return codigo_Funcion;
	}
	public void setCodigo_Funcion(String codigoFuncion) {
		codigo_Funcion = codigoFuncion;
	}
	public String getDesc_Funcion() {
		return desc_Funcion;
	}
	public void setDesc_Funcion(String descFuncion) {
		desc_Funcion = descFuncion;
	}
	public String getCodigo_Funcion_Padre() {
		return codigo_Funcion_Padre;
	}
	public void setCodigo_Funcion_Padre(String codigoFuncionPadre) {
		codigo_Funcion_Padre = codigoFuncionPadre;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getNivel() {
		return nivel;
	}
	public void setNivel(BigDecimal nivel) {
		this.nivel = nivel;
	}
	public BigDecimal getOrden() {
		return orden;
	}
	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getAsignada() {
		return asignada;
	}
	public void setAsignada(BigDecimal asignada) {
		this.asignada = asignada;
	}
	
	
}