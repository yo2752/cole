package escrituras.beans.contratos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Cursor para intercambiar los datos de usuarios que saquemos de la consulta de usuarios.
 * @author juan.gomez
 *
 *     Open  C_Usuarios For
      Select U.Id_Usuario, U.Estado_Usuario, U.Nif, U.Apellidos_Nombre,
            U.Anagrama, U.Correo_Electronico, U.Ultima_Conexion, U.Fecha_Renovacion
 */

public class CursorUsuario {
	
	private BigDecimal Id_Usuario;
	private BigDecimal Estado_Usuario;
	private String Nif;
	private String Apellidos_Nombre;
	private String Anagrama;
	private String Correo_Electronico;
	private Timestamp Ultima_Conexion;
	private Timestamp Fecha_Renovacion;

	public CursorUsuario() {
		

	}

	public BigDecimal getId_Usuario() {
		return Id_Usuario;
	}

	public void setId_Usuario(BigDecimal idUsuario) {
		Id_Usuario = idUsuario;
	}

	public String getNif() {
		return Nif;
	}

	public void setNif(String nif) {
		Nif = nif;
	}

	public String getApellidos_Nombre() {
		return Apellidos_Nombre;
	}

	public void setApellidos_Nombre(String apellidosNombre) {
		Apellidos_Nombre = apellidosNombre;
	}

	public String getAnagrama() {
		return Anagrama;
	}

	public void setAnagrama(String anagrama) {
		Anagrama = anagrama;
	}

	public String getCorreo_Electronico() {
		return Correo_Electronico;
	}

	public void setCorreo_Electronico(String correoElectronico) {
		Correo_Electronico = correoElectronico;
	}

	public BigDecimal getEstado_Usuario() {
		return Estado_Usuario;
	}

	public void setEstado_Usuario(BigDecimal estadoUsuario) {
		Estado_Usuario = estadoUsuario;
	}

	public Timestamp getUltima_Conexion() {
		return Ultima_Conexion;
	}

	public void setUltima_Conexion(Timestamp ultimaConexion) {
		Ultima_Conexion = ultimaConexion;
	}

	public Timestamp getFecha_Renovacion() {
		return Fecha_Renovacion;
	}

	public void setFecha_Renovacion(Timestamp fechaRenovacion) {
		Fecha_Renovacion = fechaRenovacion;
	}


	
	
}
