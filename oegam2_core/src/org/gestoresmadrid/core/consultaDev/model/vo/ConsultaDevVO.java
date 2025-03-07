package org.gestoresmadrid.core.consultaDev.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

@Entity
@Table(name = "CONSULTAS_DEV")
public class ConsultaDevVO implements Serializable{

	private static final long serialVersionUID = -8440936745978222562L;
	
	@Id
	@Column(name = "ID_CONSULTA_DEV")
	@SequenceGenerator(name = "consultas_dev_secuencia", sequenceName = "ID_CONSULTAS_DEV_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "consultas_dev_secuencia")
	private Long idConsultaDev;
	
	@Column(name="CIF")
	private String cif;
	
	@Column(name="ESTADO")
	private BigDecimal estado;
	
	@Column(name="ESTADO_CIF")
	private BigDecimal estadoCif;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;
	
	@Column(name="COD_RESPUESTA")
	private String codRespuesta;
	
	@Column(name="RESPUESTA")
	private String respuesta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@OneToMany(mappedBy="consultaDev")
	private List<SuscripcionDevVO> suscripciones;
	
	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCodRespuesta() {
		return codRespuesta;
	}

	public void setCodRespuesta(String codRespuesta) {
		this.codRespuesta = codRespuesta;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<SuscripcionDevVO> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(List<SuscripcionDevVO> suscripciones) {
		this.suscripciones = suscripciones;
	}

	public BigDecimal getEstadoCif() {
		return estadoCif;
	}

	public void setEstadoCif(BigDecimal estadoCif) {
		this.estadoCif = estadoCif;
	}
	
}
