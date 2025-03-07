package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the MENSAJE database table.
 * 
 */
@Entity
@Table(name="MENSAJE")
public class MensajeVO implements Serializable {

	private static final long serialVersionUID = -1269453798247212638L;

	@Id
	@SequenceGenerator(name = "mensaje_secuencia", sequenceName = "MENSAJE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mensaje_secuencia")
	@Column(name="ID_MENSAJE")
	private long idMensaje;

	@Column(name="ACUSE_RECIBIDO")
	private String acuseRecibido;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ACUSE")
	private Date fecAcuse;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NOTIFICACION")
	private Date fecNotificacion;

	@Column(name="TIPO_MENSAJE")
	private String tipoMensaje;

	//bi-directional many-to-many association to Fichero
	@ManyToMany(mappedBy="mensajes")
	private List<FicheroVO> ficheros;

	//bi-directional many-to-one association to Cancelacion
	@ManyToOne
	@JoinColumn(name="ID_CANCELACION")
	private CancelacionVO cancelacion;

	@Column(name="ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	//bi-directional many-to-one association to tramiteRegRbm
	@ManyToOne
	@JoinColumn(name="ID_TRAMITE_REGISTRO", insertable=false, updatable=false)
	private TramiteRegRbmVO tramiteRegRbm;

	public MensajeVO() {
	}

	public long getIdMensaje() {
		return this.idMensaje;
	}

	public void setIdMensaje(long idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getAcuseRecibido() {
		return this.acuseRecibido;
	}

	public void setAcuseRecibido(String acuseRecibido) {
		this.acuseRecibido = acuseRecibido;
	}

	public Date getFecAcuse() {
		return this.fecAcuse;
	}

	public void setFecAcuse(Date fecAcuse) {
		this.fecAcuse = fecAcuse;
	}

	public Date getFecNotificacion() {
		return this.fecNotificacion;
	}

	public void setFecNotificacion(Date fecNotificacion) {
		this.fecNotificacion = fecNotificacion;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public List<FicheroVO> getFicheros() {
		return this.ficheros;
	}

	public void setFicheros(List<FicheroVO> ficheros) {
		this.ficheros = ficheros;
	}

	public CancelacionVO getCancelacion() {
		return this.cancelacion;
	}

	public void setCancelacion(CancelacionVO cancelacion) {
		this.cancelacion = cancelacion;
	}

	public TramiteRegRbmVO getTramiteRegRbm() {
		return tramiteRegRbm;
	}

	public void setTramiteRegRbm(TramiteRegRbmVO tramiteRegRbm) {
		this.tramiteRegRbm = tramiteRegRbm;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}


}