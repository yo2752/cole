package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;


/**
 * The persistent class for the PACTO database table.
 * 
 */
@Entity
@Table(name = "PACTO")
public class PactoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2215931825909988826L;

	@Id
	@SequenceGenerator(name = "pacto_secuencia", sequenceName = "PACTO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pacto_secuencia")
	@Column(name="ID_PACTO")
	private long idPacto;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String pactado;

	@Column(name="TIPO_PACTO")
	private String tipoPacto;

	@Column(name="ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@ManyToOne
	@JoinColumn(name="ID_TRAMITE_REGISTRO", insertable=false, updatable=false)
	private TramiteRegRbmVO tramiteRegRbm;

	public PactoVO() {
	}

	public long getIdPacto() {
		return this.idPacto;
	}

	public void setIdPacto(long idPacto) {
		this.idPacto = idPacto;
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

	public String getPactado() {
		return this.pactado;
	}

	public void setPactado(String pactado) {
		this.pactado = pactado;
	}

	public String getTipoPacto() {
		return this.tipoPacto;
	}

	public void setTipoPacto(String tipoPacto) {
		this.tipoPacto = tipoPacto;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public TramiteRegRbmVO getTramiteRegRbm() {
		return tramiteRegRbm;
	}

	public void setTramiteRegRbm(TramiteRegRbmVO tramiteRegRbm) {
		this.tramiteRegRbm = tramiteRegRbm;
	}

}