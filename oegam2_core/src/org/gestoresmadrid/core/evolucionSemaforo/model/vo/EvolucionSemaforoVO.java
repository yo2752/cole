package org.gestoresmadrid.core.evolucionSemaforo.model.vo;

import java.io.Serializable;
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
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.semaforo.model.vo.SemaforoVO;

/**
 * The persistent class for the EVOLUCION_SEMAFORO database table.
 * 
 */
@Entity
@Table(name="EVOLUCION_SEMAFORO")
public class EvolucionSemaforoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="evolucion_semaforo_seq_gen")
	@SequenceGenerator(name="evolucion_semaforo_seq_gen", sequenceName="EVOLUCION_SEMAFORO_SEQ")
	@Column(name="ID")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name="OPERACION")
	private String operacion;

	//bi-directional many-to-one association to Semaforo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SEMAFORO",insertable=false,updatable=false)
	private SemaforoVO semaforo;
	
	@Column(name="ID_SEMAFORO")
	private Long idSemaforo;

	public EvolucionSemaforoVO() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getOperacion() {
		return this.operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public SemaforoVO getSemaforo() {
		return this.semaforo;
	}

	public void setSemaforo(SemaforoVO semaforo) {
		this.semaforo = semaforo;
	}

	public Long getIdSemaforo() {
		return idSemaforo;
	}

	public void setIdSemaforo(Long idSemaforo) {
		this.idSemaforo = idSemaforo;
	}

}