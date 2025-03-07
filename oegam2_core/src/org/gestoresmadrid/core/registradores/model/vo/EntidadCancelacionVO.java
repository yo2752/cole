package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ENTIDAD_CANCELACION database table.
 * 
 */
@Entity
@Table(name="ENTIDAD_CANCELACION")
public class EntidadCancelacionVO implements Serializable {

	private static final long serialVersionUID = 7519305804881646620L;

	@Id
	@SequenceGenerator(name = "entidad_cancelacion_secuencia", sequenceName = "ENTIDAD_CANCELACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "entidad_cancelacion_secuencia")
	@Column(name="ID_ENTIDAD")
	private long idEntidad;

	@Column(name="CODIGO_IDENTIFICACION_FISCAL")
	private String codigoIdentificacionFiscal;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="NUMERO_IMPRESO")
	private String numeroImpreso;

	@Column(name="RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name = "ID_CONTRATO")
	private BigDecimal idContrato;

	//uni-directional one-to-one association to DatRegMercantil
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DAT_REG_MERCANTIL")
	private DatRegMercantilVO datRegMercantil;

	public EntidadCancelacionVO() {
	}

	public long getIdEntidad() {
		return this.idEntidad;
	}

	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getCodigoIdentificacionFiscal() {
		return this.codigoIdentificacionFiscal;
	}

	public void setCodigoIdentificacionFiscal(String codigoIdentificacionFiscal) {
		this.codigoIdentificacionFiscal = codigoIdentificacionFiscal;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getNumeroImpreso() {
		return this.numeroImpreso;
	}

	public void setNumeroImpreso(String numeroImpreso) {
		this.numeroImpreso = numeroImpreso;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public DatRegMercantilVO getDatRegMercantil() {
		return this.datRegMercantil;
	}

	public void setDatRegMercantil(DatRegMercantilVO datRegMercantil) {
		this.datRegMercantil = datRegMercantil;
	}

	/**
	 * @return the idContrato
	 */
	public BigDecimal getIdContrato() {
		return idContrato;
	}

	/**
	 * @param idContrato the idContrato to set
	 */
	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

}