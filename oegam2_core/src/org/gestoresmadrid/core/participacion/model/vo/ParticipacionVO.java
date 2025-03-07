package org.gestoresmadrid.core.participacion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

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

import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;

@Entity
@Table(name="PARTICIPACION")
public class ParticipacionVO implements Serializable{

	private static final long serialVersionUID = -1168494714017474039L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_PARTICIPACION")
	@SequenceGenerator(name="SEC_ID_PARTICIPACION", sequenceName="ID_PARTICIPACION_SEQ")
	@Column(name = "ID_PARTICIPACION")
	private Long idParticipacion;
	
	@Column(name="ID_REMESA")
	private Long idRemesa;
	
	@Column(name="ID_BIEN")
	private Long idBien;
	
	@Column(name="ID_INTERVINIENTE")
	private Long idInterviniente;
	
	@Column(name="PORCENTAJE")
	private BigDecimal porcentaje;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_REMESA",referencedColumnName = "ID_REMESA",insertable=false,updatable=false)
	private RemesaVO remesa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_BIEN",referencedColumnName="ID_BIEN",insertable=false,updatable=false)
	private BienVO bien;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_INTERVINIENTE",referencedColumnName="ID_INTERVINIENTE",insertable=false,updatable=false)
	private IntervinienteModelosVO intervinienteModelos;

	public Long getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Long idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}

	public Long getIdInterviniente() {
		return idInterviniente;
	}

	public void setIdInterviniente(Long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public RemesaVO getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaVO remesa) {
		this.remesa = remesa;
	}

	public IntervinienteModelosVO getIntervinienteModelos() {
		return intervinienteModelos;
	}

	public void setIntervinienteModelos(IntervinienteModelosVO intervinienteModelos) {
		this.intervinienteModelos = intervinienteModelos;
	}

	public BienVO getBien() {
		return bien;
	}

	public void setBien(BienVO bien) {
		this.bien = bien;
	}

}
