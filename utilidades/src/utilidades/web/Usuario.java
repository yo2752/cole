package utilidades.web;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Usuario {	
	private String _nif=null;
	private BigDecimal _id_usuario=null;
	private String _num_colegiado=null;
	private String _num_colegiado_nacional=null;
	private BigDecimal _estado_usuario=null;
	private String _apellidos_nombre=null;
	private String _anagrama=null;
	private String _correo_electronico=null;
	private Timestamp _ultima_conexion=null;
	private Timestamp _fecha_renovacion=null;
	
	public String getNif() {
		return _nif;
	}
	public void setNif(String nif) {
		_nif = nif;
	}
	public BigDecimal getId_usuario() {
		return _id_usuario;
	}
	public void setId_usuario(BigDecimal idUsuario) {
		_id_usuario = idUsuario;
	}
	public String getNum_colegiado() {
		return _num_colegiado;
	}
	public void setNum_colegiado(String numColegiado) {
		_num_colegiado = numColegiado;
	}
	public String getNum_colegiado_nacional() {
		return _num_colegiado_nacional;
	}
	public void setNum_colegiado_nacional(String numColegiadoNacional) {
		_num_colegiado_nacional = numColegiadoNacional;
	}
	public BigDecimal getEstado_usuario() {
		return _estado_usuario;
	}
	public void setEstado_usuario(BigDecimal estadoUsuario) {
		_estado_usuario = estadoUsuario;
	}
	public String getApellidos_nombre() {
		return _apellidos_nombre;
	}
	public void setApellidos_nombre(String apellidosNombre) {
		_apellidos_nombre = apellidosNombre;
	}
	public String getAnagrama() {
		return _anagrama;
	}
	public void setAnagrama(String anagrama) {
		_anagrama = anagrama;
	}
	public String getCorreo_electronico() {
		return _correo_electronico;
	}
	public void setCorreo_electronico(String correoElectronico) {
		_correo_electronico = correoElectronico;
	}
	public Timestamp getUltima_conexion() {
		return _ultima_conexion;
	}
	public void setUltima_conexion(Timestamp ultimaConexion) {
		_ultima_conexion = ultimaConexion;
	}
	public Timestamp getFecha_renovacion() {
		return _fecha_renovacion;
	}
	public void setFecha_renovacion(Timestamp fechaRenovacion) {
		_fecha_renovacion = fechaRenovacion;
	}                              
}
