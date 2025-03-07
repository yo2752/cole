package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the BIEN_CANCELADO database table.
 * 
 */
@Entity
@Table(name="BIEN_CANCELADO")
public class BienCanceladoVO implements Serializable {

	private static final long serialVersionUID = 2320053834176741687L;

	@Id
	@SequenceGenerator(name = "bien_cancelado_secuencia", sequenceName = "BIEN_CANCELADO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bien_cancelado_secuencia")
	@Column(name="ID_BIEN_CANCELADO")
	private long idBienCancelado;

	private String bastidor;

	private String clase;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String marca;

	private String matricula;

	private String modelo;

	@Column(name="NUMERO_FABRICACION")
	private String numeroFabricacion;

	//bi-directional many-to-one association to Cancelacion
	@OneToMany(mappedBy="bienCancelado")
	private List<CancelacionVO> cancelacions;

	public BienCanceladoVO() {
	}

	public long getIdBienCancelado() {
		return this.idBienCancelado;
	}

	public void setIdBienCancelado(long idBienCancelado) {
		this.idBienCancelado = idBienCancelado;
	}

	public String getBastidor() {
		return this.bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
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

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumeroFabricacion() {
		return this.numeroFabricacion;
	}

	public void setNumeroFabricacion(String numeroFabricacion) {
		this.numeroFabricacion = numeroFabricacion;
	}

	public List<CancelacionVO> getCancelacions() {
		return this.cancelacions;
	}

	public void setCancelacions(List<CancelacionVO> cancelacions) {
		this.cancelacions = cancelacions;
	}

	public CancelacionVO addCancelacion(CancelacionVO cancelacion) {
		getCancelacions().add(cancelacion);
		cancelacion.setBienCancelado(this);

		return cancelacion;
	}

	public CancelacionVO removeCancelacion(CancelacionVO cancelacion) {
		getCancelacions().remove(cancelacion);
		cancelacion.setBienCancelado(null);

		return cancelacion;
	}

}