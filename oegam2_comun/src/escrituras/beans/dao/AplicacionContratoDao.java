package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la aplicacion
 *
 */
/*  Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
                decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada*/

public class AplicacionContratoDao {

	
	private String codigo_Aplicacion;
	private BigDecimal id_Contrato;
	private Integer asignada;
	public String getCodigo_Aplicacion() {
		return codigo_Aplicacion;
	}
	public void setCodigo_Aplicacion(String codigoAplicacion) {
		codigo_Aplicacion = codigoAplicacion;
	}

	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}
	public Integer getAsignada() {
		return asignada;
	}
	public void setAsignada(Integer asignada) {
		this.asignada = asignada;
	}
	
	
}