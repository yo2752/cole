package escrituras.beans.contratos;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.Estado;

import utilidades.estructuras.Fecha;
import escrituras.beans.UsuarioBean;

/**
 * Bean de pantalla para los datos de un colegiado para un contrato.
 * @author juan.gomez Select C.Num_Colegiado, co.Id_Usuario, U.Estado_Usuario, U.Nif, P.APELLIDO1_RAZON_SOCIAL, P.APELLIDO2, p.nombre, p.ncorpme, U.Anagrama, U.Correo_Electronico, U.Ultima_Conexion, U.Fecha_Renovacion
 */
public class DatosColegiadoBean {

	private String numColegiado;
	private String alias;
	private UsuarioBean usuario;
	private Estado estadoUsuario;
	private String nif;
	private String apellido1;
	private String apellido2;
	private String nombre;
	private String ncorpme;
	private String anagrama;
	private String correoElectronico;
	private Fecha ultimaConexion;
	private Fecha fechaRenovacion;
	private BigDecimal id_Contrato;
	private String provincia;
	private String idContratoProvincia;
	private String idGrupoUsuario;
	private String numColegiadoNacional;

	public DatosColegiadoBean() {}

	public DatosColegiadoBean(boolean inicio) {
		this();
		usuario = new UsuarioBean();
		ultimaConexion = new Fecha();
		fechaRenovacion = new Fecha();
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
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

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
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

	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}

	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getIdContratoProvincia() {
		return idContratoProvincia;
	}

	public void setIdContratoProvincia(String idContratoProvincia) {
		this.idContratoProvincia = idContratoProvincia;
	}

	public String getIdGrupoUsuario() {
		return idGrupoUsuario;
	}

	public void setIdGrupoUsuario(String idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}
}
