package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */


public class EstadoUsuarioSearchDao {

	private String apellidos_nombre;
	private BigDecimal id_Usuario;
	
	private BigDecimal  estado_Usuario;

	public String getApellidos_nombre() {
		return apellidos_nombre;
	}

	public void setApellidos_nombre(String apellidosNombre) {
		apellidos_nombre = apellidosNombre;
	}

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