package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the USUARIO database table.
 */
@Entity
@Table(name = "USUARIO")
public class UsuarioVO implements Serializable {

	private static final long serialVersionUID = 3641949741872162998L;

	@Id
	@SequenceGenerator(name = "usuarios_secuencia", sequenceName = "USUARIO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuarios_secuencia")
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ANAGRAMA")
	private String anagrama;

	@Column(name = "APELLIDOS_NOMBRE")
	private String apellidosNombre;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "ESTADO_USUARIO")
	private BigDecimal estadoUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_NOTIFICACION")
	private Date fechaNotificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_RENOVACION")
	private Date fechaRenovacion;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NUM_COLEGIADO_NACIONAL")
	private String numColegiadoNacional;

	@Column(name = "PASSWORD")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ULTIMA_CONEXION")
	private Date ultimaConexion;

	@Column(name = "USUARIO")
	private String usuario;

	@Column(name = "ID_GRUPO")
	private String idGrupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO", insertable = false, updatable = false)
	private GrupoVO grupo;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<EvolucionTramiteTraficoVO> evolucionTramiteTrafico;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<AccionTramiteVO> accionTramite;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<NotificacionVO> notificacions;

	// bi-directional many-to-one association to Tasa
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<TasaVO> tasas;

	@Column(name = "JEFATURA_JPT")
	private String jefaturaJPT;

	@Column(name = "FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name = "FECHA_FIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	public UsuarioVO() {}
	
	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAnagrama() {
		return this.anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getApellidosNombre() {
		return this.apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public BigDecimal getEstadoUsuario() {
		return this.estadoUsuario;
	}

	public void setEstadoUsuario(BigDecimal estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public Date getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Date getFechaRenovacion() {
		return this.fechaRenovacion;
	}

	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getUltimaConexion() {
		return this.ultimaConexion;
	}

	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Set<NotificacionVO> getNotificacions() {
		return notificacions;
	}

	public List<NotificacionVO> getNotificacionsAsList() {
		// Map from Set to List
		List<NotificacionVO> lista = new ArrayList<NotificacionVO>();
		if (notificacions != null) {
			lista.addAll(notificacions);
		}
		return lista;
	}

	public NotificacionVO getFirstElementotificacion() {
		ListsOperator<NotificacionVO> listOperator = new ListsOperator<NotificacionVO>();
		return listOperator.getFirstElement(getNotificacions());
	}

	public void setNotificacions(Set<NotificacionVO> notificacions) {
		this.notificacions = notificacions;
	}

	public void setNotificacions(List<NotificacionVO> tasas) {
		if (tasas == null) {
			this.notificacions = null;
		} else {
			// Map from List to Set
			Set<NotificacionVO> set = new HashSet<NotificacionVO>();
			set.addAll(notificacions);
			this.notificacions = set;
		}
	}

	public Set<TasaVO> getTasas() {
		return this.tasas;
	}

	public List<TasaVO> getTasasAsList() {
		// Map from Set to List
		List<TasaVO> lista = new ArrayList<TasaVO>();
		if (tasas != null) {
			lista.addAll(tasas);
		}
		return lista;
	}

	public TasaVO getFirstElementTasa() {
		ListsOperator<TasaVO> listOperator = new ListsOperator<TasaVO>();
		return listOperator.getFirstElement(getTasas());
	}

	public void setTasas(Set<TasaVO> tasas) {
		this.tasas = tasas;
	}

	public void setTasas(List<TasaVO> tasas) {
		if (tasas == null) {
			this.tasas = null;
		} else {
			// Map from List to Set
			Set<TasaVO> set = new HashSet<TasaVO>();
			set.addAll(tasas);
			this.tasas = set;
		}
	}

	public Set<EvolucionTramiteTraficoVO> getEvolucionTramiteTrafico() {
		return evolucionTramiteTrafico;
	}

	public List<EvolucionTramiteTraficoVO> getEvolucionTramiteTraficoAsList() {
		// Map from Set to List
		List<EvolucionTramiteTraficoVO> lista = new ArrayList<EvolucionTramiteTraficoVO>();
		if (evolucionTramiteTrafico != null) {
			lista.addAll(evolucionTramiteTrafico);
		}
		return lista;
	}

	public EvolucionTramiteTraficoVO getFirstElementEvolucionTramiteTrafico() {
		ListsOperator<EvolucionTramiteTraficoVO> listOperator = new ListsOperator<EvolucionTramiteTraficoVO>();
		return listOperator.getFirstElement(getEvolucionTramiteTrafico());
	}

	public void setEvolucionTramiteTrafico(Set<EvolucionTramiteTraficoVO> evolucionTramiteTrafico) {
		this.evolucionTramiteTrafico = evolucionTramiteTrafico;
	}

	public void setEvolucionTramiteTrafico(List<EvolucionTramiteTraficoVO> evolucionTramiteTrafico) {
		if (evolucionTramiteTrafico == null) {
			this.evolucionTramiteTrafico = null;
		} else {
			// Map from List to Set
			Set<EvolucionTramiteTraficoVO> set = new HashSet<EvolucionTramiteTraficoVO>();
			set.addAll(evolucionTramiteTrafico);
			this.evolucionTramiteTrafico = set;
		}
	}

	public Set<AccionTramiteVO> getAccionTramite() {
		return accionTramite;
	}

	public List<AccionTramiteVO> getAccionTramiteAsList() {
		// Map from Set to List
		List<AccionTramiteVO> lista = new ArrayList<AccionTramiteVO>();
		if (accionTramite != null) {
			lista.addAll(accionTramite);
		}
		return lista;
	}

	public AccionTramiteVO getFirstElementAccionTramite() {
		ListsOperator<AccionTramiteVO> listOperator = new ListsOperator<AccionTramiteVO>();
		return listOperator.getFirstElement(getAccionTramite());
	}

	public void setAccionTramite(Set<AccionTramiteVO> accionTramite) {
		this.accionTramite = accionTramite;
	}

	public void setAccionTramite(List<AccionTramiteVO> accionTramite) {
		if (accionTramite == null) {
			this.accionTramite = null;
		} else {
			// Map from List to Set
			Set<AccionTramiteVO> set = new HashSet<AccionTramiteVO>();
			set.addAll(accionTramite);
			this.accionTramite = set;
		}
	}

	public String getJefaturaJPT() {
		return jefaturaJPT;
	}

	public void setJefaturaJPT(String jefaturaJPT) {
		this.jefaturaJPT = jefaturaJPT;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public GrupoVO getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	
}