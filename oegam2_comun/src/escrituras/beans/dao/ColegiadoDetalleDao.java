package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos del colegiado
 *
 */


/*Select C.Num_Colegiado, co.Id_Usuario,
U.Estado_Usuario, U.Nif, P.APELLIDO1_RAZON_SOCIAL, P.APELLIDO2, p.nombre, p.ncorpme,
U.Anagrama, U.Correo_Electronico, U.Ultima_Conexion, U.Fecha_Renovacion*/

public class ColegiadoDetalleDao {
	

		
	private String num_Colegiado;
	private BigDecimal id_Usuario;
	private BigDecimal estado_Usuario;
	private String nif;
	private String apellido1_Razon_Social;
	private String apellido2;
	private String nombre;
	private String ncorpme;
	private String anagrama;
	private String correo_Electronico;
	private Timestamp ultima_Conexion;
	private  Timestamp fecha_Renovacion;
	
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
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
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getApellido1_Razon_Social() {
		return apellido1_Razon_Social;
	}
	public void setApellido1_Razon_Social(String apellido1RazonSocial) {
		apellido1_Razon_Social = apellido1RazonSocial;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNcorpme() {
		return ncorpme;
	}
	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
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