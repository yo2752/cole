package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.bien.model.vo.BienVO;

/**
 * The persistent class for the INMUEBLE database table.
 */
@Entity
@Table(name = "INMUEBLE")
public class InmuebleVO implements Serializable {

	private static final long serialVersionUID = 7988082910253535380L;

	@Id
	@SequenceGenerator(name = "inmueble_secuencia", sequenceName = "ID_INMUEBLE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "inmueble_secuencia")
	@Column(name = "ID_INMUEBLE")
	private Long idInmueble;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Column(name = "ID_BIEN")
	private Long idBien;

	@ManyToOne
	@JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN", insertable = false, updatable = false)
	private BienVO bien;

	public Long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(Long idInmueble) {
		this.idInmueble = idInmueble;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}

	public BienVO getBien() {
		return bien;
	}

	public void setBien(BienVO bien) {
		this.bien = bien;
	}

}