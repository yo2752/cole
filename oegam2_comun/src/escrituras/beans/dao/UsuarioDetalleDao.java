package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos de los usuarios asociados a numuro del colegiado
 *
 */
/* Select U.Id_Usuario, U.Estado_Usuario, U.Nif, U.Apellidos_Nombre,
            U.Anagrama, U.Correo_Electronico, U.Ultima_Conexion, U.Fecha_Renovacion
        From Usuario u, Colegiado Co
        Where Co.Num_Colegiado = P_Num_Colegiado
          And U.Num_Colegiado = Co.Num_Colegiado
          And U.Id_Usuario <> co.id_usuario*/

public class UsuarioDetalleDao {

	private BigDecimal id_Usuario;
	private BigDecimal estado_Usuario;
	private String nif;
	private String apellidos_Nombre;
	private String Anagrama;
	private String correo_Electronico;
	private Timestamp ultima_Conexion;
	private Timestamp fecha_Renovacion;
	
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
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getApellidos_Nombre() {
		return apellidos_Nombre;
	}
	public void setApellidos_Nombre(String apellidosNombre) {
		apellidos_Nombre = apellidosNombre;
	}
	public String getAnagrama() {
		return Anagrama;
	}
	public void setAnagrama(String anagrama) {
		Anagrama = anagrama;
	}
	public String getCorreo_Electronico() {
		return correo_Electronico;
	}
	public void setCorreo_Electronico(String correoElectronico) {
		correo_Electronico = correoElectronico;
	}
	public Timestamp getUltima_Conexion() {
		return ultima_Conexion;
	}
	public void setUltima_Conexion(Timestamp ultimaConexion) {
		ultima_Conexion = ultimaConexion;
	}
	public Timestamp getFecha_Renovacion() {
		return fecha_Renovacion;
	}
	public void setFecha_Renovacion(Timestamp fechaRenovacion) {
		fecha_Renovacion = fechaRenovacion;
	}
	
	
	
	
}