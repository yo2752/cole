package org.gestoresmadrid.core.importacionFichero.model.vo;

import java.io.Serializable;
import java.util.Date;

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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "ESTADISTICA_IMP_FICHEROS")
public class EstadisticaImportacionFicherosVO implements Serializable {

	private static final long serialVersionUID = 6479532776133377988L;

	@Id
	@SequenceGenerator(name = "secuencia_est_imp_fichero", sequenceName = "ID_EST_IMP_FICHERO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_est_imp_fichero")
	@Column(name = "ID")
	private Long idImportacionFich;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "ORIGEN")
	private String origen;

	@Column(name = "ESTADO")
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Column(name = "NUM_OK")
	private Long numOk;

	@Column(name = "NUM_ERROR")
	private Long numError;

	@Column(name = "TIPO_ERROR")
	private String tipoError;

	@Column(name = "NOMBRE_FICHERO")
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	public Long getIdImportacionFich() {
		return idImportacionFich;
	}

	public void setIdImportacionFich(Long idImportacionFich) {
		this.idImportacionFich = idImportacionFich;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getNumOk() {
		return numOk;
	}

	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}

	public Long getNumError() {
		return numError;
	}

	public void setNumError(Long numError) {
		this.numError = numError;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
}
