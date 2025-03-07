package escrituras.beans;

import java.io.Serializable;
import java.util.Date;



/**
 * Data Object.
 * Clase que mapea los atributos de una entidad de base de datos.  
 * Tabla gestionada: USUARIO
 * @template: DO
 * @author: TB-Solutions Bean Builder Engine
 * @version: 1.0.0 (Tue Sep 11 15:32:46 CEST 2007)
 */

  
    @SuppressWarnings("serial")
	public class UsuarioBean
	 implements Serializable{

      private Long idUsuario; 
      private Long idContrato; 
      private Long idTipoEstadoUsuario; 
      private Boolean usuarioPrincipal; 
      private String nif; 
      private String apellidosNombre; 
      private String correoElectronico; 
      private Date ultimaConexion; 
      private String anagrama; 
      private Boolean gestionUsuarios; 
      private String tipoEstadoUsuario;
      private String numColegiado;

    /**
     * Constructor vacio del Bean
     */
    public UsuarioBean () {
        init();
    } 
  
    /** 
     * Inicializacion de variables (Si las hay)
     */
    protected void init() {
      // Valores iniciales
    }
  

  
    /**
     * Método Setter para el campo ID_USUARIO
     * @param idUsuario
     */
    public void  setIdUsuario ( Long idUsuario ) {
        this.idUsuario = idUsuario;
    }
  
    /**
     * Método Getter para el campo ID_USUARIO
     * @return el valor
     */ 
    public Long getIdUsuario() {
        return idUsuario;
    }
  
    /**
     * Método Setter para el campo ID_CONTRATO
     * @param idContrato
     */
    public void  setIdContrato ( Long idContrato ) {
        this.idContrato = idContrato;
    }
  
    /**
     * Método Getter para el campo ID_CONTRATO
     * @return el valor
     */ 
    public Long getIdContrato() {
        return idContrato;
    }
  
    /**
     * Método Setter para el campo ID_TIPO_ESTADO_USUARIO
     * @param idTipoEstadoUsuario
     */
    public void  setIdTipoEstadoUsuario ( Long idTipoEstadoUsuario ) {
        this.idTipoEstadoUsuario = idTipoEstadoUsuario;
    }
  
    /**
     * Método Getter para el campo ID_TIPO_ESTADO_USUARIO
     * @return el valor
     */ 
    public Long getIdTipoEstadoUsuario() {
        return idTipoEstadoUsuario;
    }
  
    /**
     * Método Setter para el campo USUARIO_PRINICPAL
     * @param usuarioPrincipal
     */
    public void  setUsuarioPrincipal ( Boolean usuarioPrincipal ) {
        this.usuarioPrincipal = usuarioPrincipal;
    }
  
    /**
     * Método Getter para el campo USUARIO_PRINICPAL
     * @return el valor
     */ 
    public Boolean getUsuarioPrincipal() {
        return usuarioPrincipal;
    }
  
    /**
     * Método Setter para el campo NIF
     * @param nif
     */
    public void  setNif ( String nif ) {
        this.nif = nif;
    }
  
    /**
     * Método Getter para el campo NIF
     * @return el valor
     */ 
    public String getNif() {
        return nif;
    }
  
    /**
     * Método Setter para el campo APELLIDOS_NOMBRE
     * @param apellidosNombre
     */
    public void  setApellidosNombre ( String apellidosNombre ) {
        this.apellidosNombre = apellidosNombre;
    }
  
    /**
     * Método Getter para el campo APELLIDOS_NOMBRE
     * @return el valor
     */ 
    public String getApellidosNombre() {
        return apellidosNombre;
    }
  
    /**
     * Método Setter para el campo CORREO_ELECTRONICO
     * @param correoElectronico
     */
    public void  setCorreoElectronico ( String correoElectronico ) {
        this.correoElectronico = correoElectronico;
    }
  
    /**
     * Método Getter para el campo CORREO_ELECTRONICO
     * @return el valor
     */ 
    public String getCorreoElectronico() {
        return correoElectronico;
    }
  
    /**
     * Método Setter para el campo ULTIMA_CONEXION
     * @param ultimaConexion
     */
    public void  setUltimaConexion ( Date ultimaConexion ) {
        this.ultimaConexion = ultimaConexion;
    }
  
    /**
     * Método Getter para el campo ULTIMA_CONEXION
     * @return el valor
     */ 
    public Date getUltimaConexion() {
        return ultimaConexion;
    }
  
    /**
     * Método Setter para el campo ANAGRAMA
     * @param anagrama
     */
    public void  setAnagrama ( String anagrama ) {
        this.anagrama = anagrama;
    }
  
    /**
     * Método Getter para el campo ANAGRAMA
     * @return el valor
     */ 
    public String getAnagrama() {
        return anagrama;
    }
  
    /**
     * Método Setter para el campo GESTION_USUARIOS
     * @param gestionUsuarios
     */
    public void  setGestionUsuarios ( Boolean gestionUsuarios ) {
        this.gestionUsuarios = gestionUsuarios;
    }
  
    /**
     * Método Getter para el campo GESTION_USUARIOS
     * @return el valor
     */ 
    public Boolean getGestionUsuarios() {
        return gestionUsuarios;
    }

	public String getTipoEstadoUsuario() {
		return tipoEstadoUsuario;
	}

	public void setTipoEstadoUsuario(String tipoEstadoUsuario) {
		this.tipoEstadoUsuario = tipoEstadoUsuario;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
    
    

}

