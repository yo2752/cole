package escrituras.beans.contratos;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.Estado;

import utilidades.estructuras.Fecha;

/**
 * Bean de pantalla para los datos de un usuario que está vinculado a un contrato, con sus permisos.
 * @author juan.gomez PROCEDURE DETALLE(P_ID_CONTRATO IN CONTRATO.ID_CONTRATO%TYPE, P_ID_USUARIO OUT USUARIO.ID_USUARIO%TYPE, P_NUM_COLEGIADO OUT USUARIO.NUM_COLEGIADO%TYPE, P_ESTADO_USUARIO OUT USUARIO.ESTADO_USUARIO%TYPE, P_NIF OUT USUARIO.NIF%TYPE, P_APELLIDOS_NOMBRE OUT
 *         USUARIO.APELLIDOS_NOMBRE%TYPE, P_ANAGRAMA OUT USUARIO.ANAGRAMA%TYPE, P_CORREO_ELECTRONICO OUT USUARIO.CORREO_ELECTRONICO%TYPE, P_ULTIMA_CONEXION OUT USUARIO.ULTIMA_CONEXION%TYPE, P_FECHA_RENOVACION OUT USUARIO.FECHA_RENOVACION%TYPE, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2, C_PERMISOS
 *         OUT SYS_REFCURSOR P_FECHA_ALTA OUT usuario.fecha_alta%TYPE, P_FECHA_FIN OUT usuario.fecha_fin%TYPE)
 */
public class UsuarioContratoBean {

	private BigDecimal idContrato;
	private BigDecimal idUsuario;
	private String numColegiado;
	private Estado estadoUsuario;
	private String nif;
	private String apellidosNombre;
	private String anagrama;
	private String correoElectronico;
	private Fecha ultimaConexion;
	private Fecha fechaRenovacion;
	private Boolean usuarioPrincipal;
	private Boolean gestionUsuarios;
	private String idGrupoUsuario;
	// Mantis 11562
	private Fecha fechaAlta;
	private Fecha fechaFin;

	private List<PermisoUsuarioContratoBean> listaPermisos;

	private String numColegiadoNacional;

	public UsuarioContratoBean() {
		super();

	}

	public UsuarioContratoBean(Boolean inicio) {
		ultimaConexion = new Fecha();
		fechaRenovacion = new Fecha();
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Estado getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = Estado.convertir(estadoUsuario);
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

	public Fecha getUltimaConexion() {
		return ultimaConexion;
	}

	public void setUltimaConexion(Fecha ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public Fecha getFechaRenovacion() {
		return fechaRenovacion;
	}

	public void setFechaRenovacion(Fecha fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	public Boolean getUsuarioPrincipal() {
		return usuarioPrincipal;
	}

	public void setUsuarioPrincipal(Boolean usuarioPrincipal) {
		this.usuarioPrincipal = usuarioPrincipal;
	}

	public Boolean getGestionUsuarios() {
		return gestionUsuarios;
	}

	public void setGestionUsuarios(Boolean gestionUsuarios) {
		this.gestionUsuarios = gestionUsuarios;
	}

	public List<PermisoUsuarioContratoBean> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(List<PermisoUsuarioContratoBean> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	public String getIdGrupoUsuario() {
		return idGrupoUsuario;
	}

	public void setIdGrupoUsuario(String idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}
}
