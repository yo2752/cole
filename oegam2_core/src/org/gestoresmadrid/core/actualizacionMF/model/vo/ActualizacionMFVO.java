package org.gestoresmadrid.core.actualizacionMF.model.vo;

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

@Entity
@Table(name = "ACTUALIZACION_MF")
public class ActualizacionMFVO implements Serializable{

	private static final long serialVersionUID = -7523512209420803696L;
	

	@Id
	@SequenceGenerator(name = "actualizacionMF_secuencia", sequenceName = "ACTUALIZACION_MF_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "actualizacionMF_secuencia")
	@Column(name="ID_ACTUALIZACION")
	private Long idActualizacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;


	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "RESULTADO")
	private String resultado;
	
	@Column(name = "FICHERO_SUBIDO")
	private String ficheroSubido;
	
	@Column(name = "FICHERO_RESULTADO")
	private String ficheroResultado;
	
	
	@Column(name = "TIPO")
	private String tipo;

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFicheroSubido() {
		return ficheroSubido;
	}

	public void setFicheroSubido(String ficheroSubido) {
		this.ficheroSubido = ficheroSubido;
	}

	public String getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(String ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

	public Long getIdActualizacion() {
		return idActualizacion;
	}

	public void setIdActualizacion(Long idActualizacion) {
		this.idActualizacion = idActualizacion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	
}
