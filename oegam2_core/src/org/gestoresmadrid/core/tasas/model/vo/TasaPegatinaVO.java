package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "TASA_PEGATINA")
public class TasaPegatinaVO implements Serializable {

	private static final long serialVersionUID = 1976314631765980770L;

	@Id
	@Column(name = "ID_TASA_PEGATINA")
	@SequenceGenerator(name = "tasas_pegatinas_secuencia", sequenceName = "TASA_PEGATINA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tasas_pegatinas_secuencia")
	private Long idTasaPegatina;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Column(name = "TIPO_TASA")
	private String tipoTasa;

	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO")
	private ContratoVO contrato;

	@Column(name = "PRECIO")
	private BigDecimal precio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN_VIGENCIA")
	private Date fechaFinVigencia;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FACTURACION")
	private Date fechaFacturacion;
	
	@Column(name="IMPORTADO_ICOGAM")
	private BigDecimal importadoIcogam;

	public Long getIdTasaPegatina() {
		return idTasaPegatina;
	}

	public void setIdTasaPegatina(Long idTasaPegatina) {
		this.idTasaPegatina = idTasaPegatina;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}
	
}