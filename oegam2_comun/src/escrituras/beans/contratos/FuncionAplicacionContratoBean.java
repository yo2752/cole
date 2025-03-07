package escrituras.beans.contratos;

import java.math.BigDecimal;

/**
 * Clase para almacenar las funciones con los permisos que tienen los usuarios sobre las mismas
 *          
 *          Select F.Codigo_Aplicacion, F.Codigo_Funcion, F.Desc_Funcion,
                F.Codigo_Funcion_Padre, F.Url, F.Nivel, F.Orden, F.Tipo,
                decode(nvl(U.Id_Contrato,0),0,1,0) Asignada
 * 
 * @author juan.gomez
 *
 */
public class FuncionAplicacionContratoBean {
	
	private String Codigo_Aplicacion;
	private String Codigo_Funcion;
	private String Desc_Funcion;
	private String Codigo_Funcion_Padre;
	private String Url;
	private BigDecimal Nivel;
	private BigDecimal Orden;
	private String Tipo;
	private Integer asignada;
	private Boolean esPadre;
	
	public FuncionAplicacionContratoBean() {
	}

	public String getCodigo_Aplicacion() {
		return Codigo_Aplicacion;
	}

	public void setCodigo_Aplicacion(String codigoAplicacion) {
		Codigo_Aplicacion = codigoAplicacion;
	}

	public String getCodigo_Funcion() {
		return Codigo_Funcion;
	}

	public void setCodigo_Funcion(String codigoFuncion) {
		Codigo_Funcion = codigoFuncion;
	}

	public String getDesc_Funcion() {
		return Desc_Funcion;
	}

	public void setDesc_Funcion(String descFuncion) {
		Desc_Funcion = descFuncion;
	}

	public String getCodigo_Funcion_Padre() {
		return Codigo_Funcion_Padre;
	}

	public void setCodigo_Funcion_Padre(String codigoFuncionPadre) {
		Codigo_Funcion_Padre = codigoFuncionPadre;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public BigDecimal getNivel() {
		return Nivel;
	}

	public void setNivel(BigDecimal nivel) {
		Nivel = nivel;
	}

	public BigDecimal getOrden() {
		return Orden;
	}

	public void setOrden(BigDecimal orden) {
		Orden = orden;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public Integer getAsignada() {
		return asignada;
	}

	public void setAsignada(Integer asignada) {
		this.asignada = asignada;
	}

	public Boolean getEsPadre() {
		return esPadre;
	}

	public void setEsPadre(Boolean esPadre) {
		this.esPadre = esPadre;
	}	
	
}
