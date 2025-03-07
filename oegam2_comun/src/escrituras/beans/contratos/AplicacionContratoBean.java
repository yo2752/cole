package escrituras.beans.contratos;

import java.math.BigDecimal;

/**
 * Bean de pantalla para almacenar los datos de las aplicaciones asignadas a un contrato
 * @author juan.gomez
 *
 *	CODIGO_APLICACION	VARCHAR2(12 BYTE)
	DESC_APLICACION	NVARCHAR2(75 CHAR)
	ALIAS	NVARCHAR2(10 CHAR)
 */

public class AplicacionContratoBean {
	
	private String codigo_Aplicacion;
	private BigDecimal id_Contrato;
	private String alias;
	private Boolean asignada;
	
	public AplicacionContratoBean() {
		super();
	}

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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getAsignada() {
		return asignada;
	}

	public void setAsignada(Boolean asignada) {
		this.asignada = asignada;
	}

	
}
