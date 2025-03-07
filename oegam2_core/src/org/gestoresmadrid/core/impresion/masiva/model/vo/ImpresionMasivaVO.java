package org.gestoresmadrid.core.impresion.masiva.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the IMPRESION_MASIVA database table.
 */
@Entity
@Table(name = "IMPRESION_MASIVA")
public class ImpresionMasivaVO implements Serializable {

	private static final long serialVersionUID = 8766908212983961228L;

	@Id
	@Column(name = "ID_IMPRESION_MASIVA")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_IMPRESION_MASIVA")
	@SequenceGenerator(name = "SEC_IMPRESION_MASIVA", sequenceName = "IMPRESION_MASIVA_SEQ")
	private Long idImpresionMasiva;

	@Column(name = "RUTA_FICHERO")
	private String rutaFichero;

	@Column(name = "NOMBRE_FICHERO")
	private String nombreFichero;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA_BBDD")
	private Date fechaAltaBBDD;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ENVIADO_PROCESO")
	private Date fechaEnviadoProceso;

	@Column(name = "ESTADO_IMPRESION")
	private Integer estadoImpresion;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	public ImpresionMasivaVO() {}

	public Long getIdImpresionMasiva() {
		return idImpresionMasiva;
	}

	public void setIdImpresionMasiva(Long idImpresionMasiva) {
		this.idImpresionMasiva = idImpresionMasiva;
	}

	public String getRutaFichero() {
		return rutaFichero;
	}

	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getFechaAltaBBDD() {
		return fechaAltaBBDD;
	}

	public void setFechaAltaBBDD(Date fechaAltaBBDD) {
		this.fechaAltaBBDD = fechaAltaBBDD;
	}

	public Date getFechaEnviadoProceso() {
		return fechaEnviadoProceso;
	}

	public void setFechaEnviadoProceso(Date fechaEnviadoProceso) {
		this.fechaEnviadoProceso = fechaEnviadoProceso;
	}

	public Integer getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(Integer estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}