package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos del usuario
 *
 */
/*Select Id_Usuario, Num_Colegiado, Estado_Usuario,
          Nif, Apellidos_Nombre, Anagrama,
          Correo_Electronico, Ultima_Conexion, Fecha_Renovacion
     
      From Usuario
      Where id_Usuario='1'*/

public class UsuarioModificacionDao {
    
	private BigDecimal id_Usuario;
	private String num_Colegiado;	
	private BigDecimal  estado_Usuario;
	private String nif;
	private String apellidos_Nombre;
	private String  anagrama;
	private String correo_Electronico;
	private Timestamp ultima_Conexion;
	private Timestamp fecha_Renovacion;
	
	
	public BigDecimal getId_Usuario() {
		return id_Usuario;
	}
	public void setId_Usuario(BigDecimal idUsuario) {
		id_Usuario = idUsuario;
	}
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
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
		return anagrama;
	}
	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
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