package org.gestoresmadrid.core.semaforo.model.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.evolucionSemaforo.model.vo.EvolucionSemaforoVO;

/**
 * The persistent class for the SEMAFORO database table.
 * 
 */
@Entity
@Table(name="SEMAFORO")
public class SemaforoVO implements Serializable {

	private static final long serialVersionUID = -5305321893196133894L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="semaforo_seq_gen")
	@SequenceGenerator(name="semaforo_seq_gen", sequenceName="SEMAFORO_SEQ")
	@Column(name="ID_SEMAFORO")
	private Long idSemaforo;

	@Column(name="ESTADO")
	private Integer estado;

	@Column(name="NODO")
	private String nodo;

	@Column(name="PROCESO")
	private String proceso;

	/*@OneToOne
	@JoinColumns({
		@JoinColumn(name="PROCESO", referencedColumnName="PROCESO", insertable=false, updatable=false),
		@JoinColumn(name="NODO", referencedColumnName="NODO", insertable=false, updatable=false)
	})
	private ProcesoVO procesoVO;*/

	//bi-directional many-to-one association to EvolucionSemaforo
	@OneToMany(mappedBy="semaforo")
	private Set<EvolucionSemaforoVO> evolucionSemaforos;

	public SemaforoVO() {
	}

	public Long getIdSemaforo() {
		return this.idSemaforo;
	}

	public void setIdSemaforo(Long idSemaforo) {
		this.idSemaforo = idSemaforo;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNodo() {
		return this.nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Set<EvolucionSemaforoVO> getEvolucionSemaforos() {
		return this.evolucionSemaforos;
	}

	public void setEvolucionSemaforos(Set<EvolucionSemaforoVO> evolucionSemaforos) {
		this.evolucionSemaforos = evolucionSemaforos;
	}

	public EvolucionSemaforoVO addEvolucionSemaforo(EvolucionSemaforoVO evolucionSemaforo) {
		getEvolucionSemaforos().add(evolucionSemaforo);
		evolucionSemaforo.setSemaforo(this);
		return evolucionSemaforo;
	}

	public EvolucionSemaforoVO removeEvolucionSemaforo(EvolucionSemaforoVO evolucionSemaforo) {
		getEvolucionSemaforos().remove(evolucionSemaforo);
		evolucionSemaforo.setSemaforo(null);
		return evolucionSemaforo;
	}

}