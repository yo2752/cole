package org.gestoresmadrid.core.mandato.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

@Entity
@Table(name = "MANDATO")
public class MandatoVO implements Serializable {

	private static final long serialVersionUID = 2640112707235030250L;

	@Id
	@Column(name = "ID_MANDATO")
	@SequenceGenerator(name = "mandato_secuencia", sequenceName = "MANDATO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mandato_secuencia")
	private Long idMandato;

	@Column(name = "CODIGO_MANDATO")
	private String codigoMandato;

	@Column(name = "FECHA_MANDATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMandato;

	@Column(name = "EMPRESA")
	private String empresa;

	@Column(name = "CIF")
	private String cif;

	@Column(name = "NOMBRE_ADMINISTRADOR")
	private String nombreAdministrador;

	@Column(name = "DNI_ADMINISTRADOR")
	private String dniAdministrador;

	@Column(name = "FECHA_CAD_DNI_ADMIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCadDniAdmin;

	@Column(name = "FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name = "FECHA_CADUCIDAD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCaducidad;

	@Column(name = "NOMBRE_ADMINISTRADOR_2")
	private String nombreAdministrador2;

	@Column(name = "DNI_ADMINISTRADOR_2")
	private String dniAdministrador2;

	@Column(name = "FECHA_CAD_DNI_ADMIN_2")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCadDniAdmin2;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "VISIBLE")
	private Boolean visible;

	public Long getIdMandato() {
		return idMandato;
	}

	public void setIdMandato(Long idMandato) {
		this.idMandato = idMandato;
	}

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public Date getFechaMandato() {
		return fechaMandato;
	}

	public void setFechaMandato(Date fechaMandato) {
		this.fechaMandato = fechaMandato;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public String getDniAdministrador() {
		return dniAdministrador;
	}

	public void setDniAdministrador(String dniAdministrador) {
		this.dniAdministrador = dniAdministrador;
	}

	public Date getFechaCadDniAdmin() {
		return fechaCadDniAdmin;
	}

	public void setFechaCadDniAdmin(Date fechaCadDniAdmin) {
		this.fechaCadDniAdmin = fechaCadDniAdmin;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getNombreAdministrador2() {
		return nombreAdministrador2;
	}

	public void setNombreAdministrador2(String nombreAdministrador2) {
		this.nombreAdministrador2 = nombreAdministrador2;
	}

	public String getDniAdministrador2() {
		return dniAdministrador2;
	}

	public void setDniAdministrador2(String dniAdministrador2) {
		this.dniAdministrador2 = dniAdministrador2;
	}

	public Date getFechaCadDniAdmin2() {
		return fechaCadDniAdmin2;
	}

	public void setFechaCadDniAdmin2(Date fechaCadDniAdmin2) {
		this.fechaCadDniAdmin2 = fechaCadDniAdmin2;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
