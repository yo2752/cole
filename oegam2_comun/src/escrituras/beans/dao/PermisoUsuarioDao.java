package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la aplicacion
 *
 */
/*  Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
                decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada*/

public class PermisoUsuarioDao {

	
	
	private BigDecimal id_Contrato;
	private BigDecimal id_Usuario;
	private String codigo_Aplicacion;
	private String codigo_Funcion;
	private String asignada;
	
	
	
	public BigDecimal getId_Usuario() {
		return id_Usuario;
	}
	public void setId_Usuario(BigDecimal idUsuario) {
		id_Usuario = idUsuario;
	}
	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
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
	public String getAsignada() {
		return asignada;
	}
	public void setAsignada(String asignada) {
		this.asignada = asignada;
	}
	
}