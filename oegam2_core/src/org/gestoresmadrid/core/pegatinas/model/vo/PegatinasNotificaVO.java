package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS_NOTIFICA")
public class PegatinasNotificaVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasNotificaVO() {
	}

	@Id
	@Column(name = "ID_NOTIFICA")
	@SequenceGenerator(name = "secuencia_pegatinas_notifica", sequenceName = "PEGATINAS_NOTIFICA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinas_notifica")
	private Integer idNotifica;
	
	@Column(name = "ID_PEGATINA")
	private Integer idPegatina;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "ACCION")
	private String accion;
	
	@Column(name = "MOTIVO")
	private String motivo;
	
	@Column(name = "IDENTIFICADOR")
	private String identificador;

	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "JEFATURA")
	private String jefatura;
	
	public Integer getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(Integer idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Integer getIdPegatina() {
		return idPegatina;
	}

	public void setIdPegatina(Integer idPegatina) {
		this.idPegatina = idPegatina;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}