package escrituras.beans.contratos;

import java.math.BigDecimal;
/**
 * Bean de pantalla para mostrar los permisos asignados a los usuarios en las aplicaciones.
 * @author juan.gomez
 *
 */
public class PermisoUsuarioContratoBean {
	
	private BigDecimal id_Contrato;
	private String id_Provincia;
	private String codigo_Aplicacion;
	private String codigo_Funcion;
	private String desc_Funcion;
	private String codigo_Funcion_Padre;
	private String url;
	private BigDecimal nivel;
	private BigDecimal orden;
	private String tipo;
	private Integer asignada;
	private Boolean esPadre;
	
	public PermisoUsuarioContratoBean() {
		super();

	}

	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}

	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}

	public String getId_Provincia() {
		return id_Provincia;
	}

	public void setId_Provincia(String idProvincia) {
		id_Provincia = idProvincia;
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
