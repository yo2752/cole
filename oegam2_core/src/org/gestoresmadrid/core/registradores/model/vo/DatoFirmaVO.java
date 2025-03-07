package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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


/**
 * The persistent class for the DATO_FIRMA database table.
 * 
 */
@Entity
@Table(name="DATO_FIRMA")
public class DatoFirmaVO implements Serializable {

	private static final long serialVersionUID = 5757660135303279098L;

	@Id
	@SequenceGenerator(name = "dato_firma_secuencia", sequenceName = "DATO_FIRMA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dato_firma_secuencia")
	@Column(name="ID_DATO_FIRMA")
	private long idDatoFirma;

	private String consumidor;

	@Column(name="DERECHO_DES")
	private String derechoDes;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIRMA")
	private Date fecFirma;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String informado;

	private String lugar;

	@Column(name="TIPO_FIRMA")
	private String tipoFirma;

	@Column(name="TIPO_INTERVENCION")
	private String tipoIntervencion;
	
	@Column(name="ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	//bi-directional many-to-one association to tramiteRegRbm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TRAMITE_REGISTRO",referencedColumnName="ID_TRAMITE_REGISTRO", insertable=false, updatable=false)
	private TramiteRegRbmVO tramiteRegRbm;

	public DatoFirmaVO() {
	}

	public long getIdDatoFirma() {
		return this.idDatoFirma;
	}

	public void setIdDatoFirma(long idDatoFirma) {
		this.idDatoFirma = idDatoFirma;
	}

	public String getConsumidor() {
		return this.consumidor;
	}

	public void setConsumidor(String consumidor) {
		this.consumidor = consumidor;
	}

	public String getDerechoDes() {
		return this.derechoDes;
	}

	public void setDerechoDes(String derechoDes) {
		this.derechoDes = derechoDes;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecFirma() {
		return this.fecFirma;
	}

	public void setFecFirma(Date fecFirma) {
		this.fecFirma = fecFirma;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getInformado() {
		return this.informado;
	}

	public void setInformado(String informado) {
		this.informado = informado;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTipoFirma() {
		return this.tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getTipoIntervencion() {
		return this.tipoIntervencion;
	}

	public void setTipoIntervencion(String tipoIntervencion) {
		this.tipoIntervencion = tipoIntervencion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public TramiteRegRbmVO getTramiteRegRbm() {
		return tramiteRegRbm;
	}

	public void setTramiteRegRbm(TramiteRegRbmVO tramiteRegRbm) {
		this.tramiteRegRbm = tramiteRegRbm;
	}

}