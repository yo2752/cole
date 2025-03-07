package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPLEO database table.
 * 
 */
@Entity
@Table(name="EMPLEO")
public class EmpleoVO implements Serializable {

	private static final long serialVersionUID = 6793505132098805422L;

	@Id
	@SequenceGenerator(name = "empleo_secuencia", sequenceName = "EMPLEO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "empleo_secuencia")
	@Column(name="ID_EMPLEO")
	private long idEmpleo;

	@Column(name="ESTADO_USUARIO")
	private BigDecimal estadoUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

	@Column(name="ID_CONTRATO")
	private BigDecimal idContrato;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private UsuarioVO usuario;

	public EmpleoVO() {
	}

	public long getIdEmpleo() {
		return this.idEmpleo;
	}

	public void setIdEmpleo(long idEmpleo) {
		this.idEmpleo = idEmpleo;
	}

	public BigDecimal getEstadoUsuario() {
		return this.estadoUsuario;
	}

	public void setEstadoUsuario(BigDecimal estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

}