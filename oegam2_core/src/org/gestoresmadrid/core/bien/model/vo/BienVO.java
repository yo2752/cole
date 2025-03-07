package org.gestoresmadrid.core.bien.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.tipoInmueble.model.vo.TipoInmuebleVO;

@Entity
@Table(name = "BIEN")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_BIEN", discriminatorType = DiscriminatorType.STRING)
public class BienVO implements Serializable {

	private static final long serialVersionUID = 7311727699941418004L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_ID_BIEN")
	@SequenceGenerator(name = "SEC_ID_BIEN", sequenceName = "ID_BIEN_SEQ")
	@Column(name = "ID_BIEN")
	private Long idBien;

	@Column(name = "ESTADO")
	private Long estado;

	@Column(name = "REF_CATASTRAL")
	private String refCatrastal;

	@Column(name = "FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "TIPO_BIEN", referencedColumnName = "TIPO_BIEN"), @JoinColumn(name = "TIPO_INMUEBLE", referencedColumnName = "TIPO_INMUEBLE") })
	private TipoInmuebleVO tipoInmueble;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "IDUFIR")
	private Long idufir;

	@Column(name = "NUMERO_FINCA")
	private Long numeroFinca;

	@Column(name = "NUM_FINCA_DUPL")
	private Long numFincaDupl;

	@Column(name = "SECCION")
	private Long seccion;

	@Column(name = "SUBNUM_FINCA")
	private String subnumFinca;

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getRefCatrastal() {
		return refCatrastal;
	}

	public void setRefCatrastal(String refCatrastal) {
		this.refCatrastal = refCatrastal;
	}

	public TipoInmuebleVO getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(TipoInmuebleVO tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}

	public Long getNumeroFinca() {
		return numeroFinca;
	}

	public void setNumeroFinca(Long numeroFinca) {
		this.numeroFinca = numeroFinca;
	}

	public Long getNumFincaDupl() {
		return numFincaDupl;
	}

	public void setNumFincaDupl(Long numFincaDupl) {
		this.numFincaDupl = numFincaDupl;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public String getSubnumFinca() {
		return subnumFinca;
	}

	public void setSubnumFinca(String subnumFinca) {
		this.subnumFinca = subnumFinca;
	}

}
