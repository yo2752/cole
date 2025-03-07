package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos del contrato
 *
 */
/*  Select id_Contrato,C.Estado_Contrato From Contrato*/

public class EstadoDetalleDao {

	
	private BigDecimal id_Contrato;
	
	private BigDecimal  estado_Contrato;

	

	



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