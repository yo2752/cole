package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */


public class EstadoContratoDao {

	private String razon_social;
	private BigDecimal id_Contrato;
	
	private BigDecimal  estado_Contrato;
    
	
	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razonSocial) {
		razon_social = razonSocial;
	}

	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}

	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}

	public BigDecimal getEstado_Contrato() {
		return estado_Contrato;
	}

	public void setEstado_Contrato(BigDecimal estadoContrato) {
		estado_Contrato = estadoContrato;
	}
	
	
}