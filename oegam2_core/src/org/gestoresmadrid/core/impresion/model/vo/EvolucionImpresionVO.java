package org.gestoresmadrid.core.impresion.model.vo;

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
 * The persistent class for the EVOLUCION_IMPRESION database table.
 */
@Entity
@Table(name = "EVOLUCION_IMPRESION")
public class EvolucionImpresionVO implements Serializable {

	private static final long serialVersionUID = -3268429028805374261L;

	@Id
	@SequenceGenerator(name = "evolucion_impresion_secuencia", sequenceName = "EVOLUCION_IMPRESION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_impresion_secuencia")
	@Column(name="ID")
	private Long idEvolucion;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ID_IMPRESION")
	private Long idImpresion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "NOMBRE_DOCUMENTO")
	private String nombreDocumento;

	@Column(name = "TRAMITES_ERRONEOS")
	private String tramiterErroneos;

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTramiterErroneos() {
		return tramiterErroneos;
	}

	public void setTramiterErroneos(String tramiterErroneos) {
		this.tramiterErroneos = tramiterErroneos;
	}

	public Long getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Long idEvolucion) {
		this.idEvolucion = idEvolucion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(Long idImpresion) {
		this.idImpresion = idImpresion;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
}