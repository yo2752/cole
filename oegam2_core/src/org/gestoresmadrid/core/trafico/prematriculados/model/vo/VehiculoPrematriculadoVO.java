package org.gestoresmadrid.core.trafico.prematriculados.model.vo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the ENTIDADES_FINANCIERAS_EITV database table.
 * 
 */
@Entity
@Table(name="VEHICULO_PREMATRICULADO")
public class VehiculoPrematriculadoVO implements Serializable {

	private static final long serialVersionUID = 269527723185135780L;

	@Id
	@SequenceGenerator(name="VEHICULO_PREMATRICULADO_GENERATOR", sequenceName="ID_VEHICULO_PREMATRICULADO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="VEHICULO_PREMATRICULADO_GENERATOR")
	@Column(name="ID_VEHICULO_PREMATRICULADO")
	private Long id;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	private String nive;
	
	private String bastidor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Column(name="ESTADO_CARACTERISTICAS")
	private Integer estadoCaracteristicas;

	@Column(name="RESPUESTA_CARACTERISTICAS")
	private String respuestaCaracteristicas;

	@Column(name="ESTADO_FICHA_TECNICA")
	private Integer estadoFichaTecnica;

	@Column(name="RESPUESTA_FICHA_TECNICA")
	private String respuestaFichaTecnica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Integer getEstadoCaracteristicas() {
		return estadoCaracteristicas;
	}

	public void setEstadoCaracteristicas(Integer estadoCaracteristicas) {
		this.estadoCaracteristicas = estadoCaracteristicas;
	}

	public String getRespuestaCaracteristicas() {
		return respuestaCaracteristicas;
	}

	public void setRespuestaCaracteristicas(String respuestaCaracteristicas) {
		this.respuestaCaracteristicas = respuestaCaracteristicas;
	}

	public Integer getEstadoFichaTecnica() {
		return estadoFichaTecnica;
	}

	public void setEstadoFichaTecnica(Integer estadoFichaTecnica) {
		this.estadoFichaTecnica = estadoFichaTecnica;
	}

	public String getRespuestaFichaTecnica() {
		return respuestaFichaTecnica;
	}

	public void setRespuestaFichaTecnica(String respuestaFichaTecnica) {
		this.respuestaFichaTecnica = respuestaFichaTecnica;
	}
	

}