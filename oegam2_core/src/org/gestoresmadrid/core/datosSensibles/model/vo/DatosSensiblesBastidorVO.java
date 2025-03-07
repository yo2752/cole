package org.gestoresmadrid.core.datosSensibles.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.GrupoVO;

/**
 * The persistent class for the DATOS_SENSIBLES_BASTIDOR database table.
 */
@Entity
@Table(name = "DATOS_SENSIBLES_BASTIDOR")
public class DatosSensiblesBastidorVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatosSensiblesBastidorPK id;

	@Column(name = "APELLIDOS_NOMBRE")
	private String apellidosNombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	// bi-directional many-to-one association to GrupoVO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO", insertable = false, updatable = false)
	private GrupoVO grupo;

	// bi-directional many-to-one association to UsuarioVO
	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name = "ESTADO")
	private BigDecimal estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;
	
	@Column(name = "TIEMPO_RESTAURACION")
	private Long tiempoRestauracion;
	
	@Column(name = "FECHA_OPERACION")
	private Date fechaOperacion;
	
	@Column(name = "USUARIO_OPERACION")
	private Long usuarioOperacion;
	
	@Column(name = "TIPO_BASTIDOR")
	private BigDecimal tipoBastidor;
	
	@Column(name = "TIPO_BASTIDOR_SANTANDER")
	private BigDecimal tipoBastidorSantander;
	
	@Column(name = "TIPO_ALERTA")
	private BigDecimal tipoAlerta;
	
	public DatosSensiblesBastidorVO() {}

	public DatosSensiblesBastidorPK getId() {
		return this.id;
	}

	public void setId(DatosSensiblesBastidorPK id) {
		this.id = id;
	}

	public String getApellidosNombre() {
		return this.apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public GrupoVO getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getTiempoRestauracion() {
		return tiempoRestauracion;
	}

	public void setTiempoRestauracion(Long tiempoRestauracion) {
		this.tiempoRestauracion = tiempoRestauracion;
	}

	public Long getUsuarioOperacion() {
		return usuarioOperacion;
	}

	public void setUsuarioOperacion(Long usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public BigDecimal getTipoBastidor() {
		return tipoBastidor;
	}

	public void setTipoBastidor(BigDecimal tipoBastidor) {
		this.tipoBastidor = tipoBastidor;
	}

	public BigDecimal getTipoBastidorSantander() {
		return tipoBastidorSantander;
	}

	public void setTipoBastidorSantander(BigDecimal tipoBastidorSantander) {
		this.tipoBastidorSantander = tipoBastidorSantander;
	}

	public BigDecimal getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(BigDecimal tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}
}