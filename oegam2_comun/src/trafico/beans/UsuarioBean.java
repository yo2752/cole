package trafico.beans;

import java.util.Date;

public class UsuarioBean {

	
	
	 private Long idUsuario;
	 private String numColegiado;
	 private Integer estadoUsuario;
	 private String nif; 
	 private String apellidosNombre;
	 private String anagrama;
	 private String correoElectronico;
	 private Date ultimaConexion; 
	 private Date fechaRenovacion; 
	 private String usuario; 
	 private String password; 
	 private Date fechaNotificacion;
	 
	 
	 //DIFERENCIAS CON USUARIO DEL PAQUETE ESCRITURAS
//     private Long idContrato; 
//     private Long idTipoEstadoUsuario; 
//     private Boolean usuarioPrincipal; 
//     private Boolean gestionUsuarios; 
//     private String tipoEstadoUsuario;
	 
	 
	public UsuarioBean(boolean inicializar) {
		
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public Integer getEstadoUsuario() {
		return estadoUsuario;
	}
	public void setEstadoUsuario(Integer estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getAnagrama() {
		return anagrama;
	}
	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Date getUltimaConexion() {
		return ultimaConexion;
	}
	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}
	public Date getFechaRenovacion() {
		return fechaRenovacion;
	}
	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	} 
	 
	
      
      
     
     
    
     
	
	
	
	
	
	
	
}
