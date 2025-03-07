package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos del contrato
 *
 */
/*Select C.id_Usuario,C.Estado_Usuario From Usuario C where C.Id_Usuario*/

public class EstadoUsuarioDao {

	
	private BigDecimal id_Usuario;
	
	private BigDecimal  estado_Usuario;

	public BigDecimal getId_Usuario() {
		return id_Usuario;
	}

	public void setId_Usuario(BigDecimal idUsuario) {
		id_Usuario = idUsuario;
	}

	public BigDecimal getEstado_Usuario() {
		return estado_Usuario;
	}

	public void setEstado_Usuario(BigDecimal estadoUsuario) {
		estado_Usuario = estadoUsuario;
	}

	

	



	
}