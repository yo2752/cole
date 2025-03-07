package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos del contrato
 *
 */
/*  Select 
    C.C.Id_Contrato
    From Contrato C
    where C.CIF='31696535M'*/

public class ConsultarDetalleDao {

		
	private BigDecimal  id_Contrato;


	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}

	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}

	
	
}