package org.gestoresmadrid.core.contrato.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;

/**
 * The persistent class for the CORREO_CONTRATO_TRAMITE database table.
 */
@Entity
@Table(name = "CORREO_CONTRATO_TRAMITE")
public class CorreoContratoTramiteVO implements Serializable {

	private static final long serialVersionUID = 1565389889644551306L;

	@Id
	@SequenceGenerator(name = "correo_contrato_tramite_secuencia", sequenceName = "CORREO_CONTRATO_TRAMITE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "correo_contrato_tramite_secuencia")
	@Column(name = "ID_CORREO")
	private Long idCorreo;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_TRAMITE")
	private TipoTramiteVO tipoTramite;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "ENVIAR_CORREO_IMPRESION")
	private String enviarCorreoImpresion;

	@Column(name = "TIPO_IMPRESION")
	private String tipoImpresion;

	public CorreoContratoTramiteVO() {}

	public Long getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(Long idCorreo) {
		this.idCorreo = idCorreo;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public TipoTramiteVO getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramiteVO tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getEnviarCorreoImpresion() {
		return enviarCorreoImpresion;
	}

	public void setEnviarCorreoImpresion(String enviarCorreoImpresion) {
		this.enviarCorreoImpresion = enviarCorreoImpresion;
	}

	public String getTipoImpresion() {
		return tipoImpresion;
	}

	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

}