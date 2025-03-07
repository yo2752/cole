package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the MOTOR_BUQUE database table.
 * 
 */
@Entity
@Table(name="MOTOR_BUQUE")
public class MotorBuqueVO implements Serializable {

	private static final long serialVersionUID = -3962879712274378151L;

	@Id
	@SequenceGenerator(name = "motbu_registro_secuencia", sequenceName = "MOTBU_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "motbu_registro_secuencia")
	@Column(name="ID_MOTOR")
	private long idMotor;

	@Column(name="ANIO_CONSTRUCCION")
	private String anioConstruccion;

	private String marca;

	private String modelo;

	@Column(name="NUM_SERIE")
	private String numSerie;

	@Column(name="POTENCIA_CV")
	private String potenciaCv;

	@Column(name="POTENCIA_KW")
	private String potenciaKw;

	private String tipo;

	@Column(name="ID_BUQUE")
	private long idBuque;

	@ManyToOne
	@JoinColumn(name="ID_BUQUE", insertable=false, updatable=false)
	private BuqueRegistroVO buqueRegistro;

	public MotorBuqueVO() {
	}

	public long getIdMotor() {
		return this.idMotor;
	}

	public void setIdMotor(long idMotor) {
		this.idMotor = idMotor;
	}

	public String getAnioConstruccion() {
		return this.anioConstruccion;
	}

	public void setAnioConstruccion(String anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getPotenciaCv() {
		return this.potenciaCv;
	}

	public void setPotenciaCv(String potenciaCv) {
		this.potenciaCv = potenciaCv;
	}

	public String getPotenciaKw() {
		return this.potenciaKw;
	}

	public void setPotenciaKw(String potenciaKw) {
		this.potenciaKw = potenciaKw;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BuqueRegistroVO getBuqueRegistro() {
		return this.buqueRegistro;
	}

	public void setBuqueRegistro(BuqueRegistroVO buqueRegistro) {
		this.buqueRegistro = buqueRegistro;
	}

	public long getIdBuque() {
		return idBuque;
	}

	public void setIdBuque(long idBuque) {
		this.idBuque = idBuque;
	}

}