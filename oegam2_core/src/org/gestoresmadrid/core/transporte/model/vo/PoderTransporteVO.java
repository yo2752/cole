package org.gestoresmadrid.core.transporte.model.vo;

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

@Entity
@Table(name="DATOS_PODER_TRANSPORTE")
public class PoderTransporteVO implements Serializable{

	private static final long serialVersionUID = 8386882156284223350L;

	@Id
	@Column(name="ID_PODER_TRANSPORTE")
	@SequenceGenerator(name = "poder_transporte_secuencia", sequenceName = "ID_DATOS_PODER_TRANS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "poder_transporte_secuencia")
	private Long idPoderTransporte;

	@Column(name="ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable=false,updatable=false)
	private ContratoVO contrato;

	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name="ID_USUARIO_ALTA")
	private Long idUsuarioAlta;

	@Column(name="NIF_PODERDANTE")
	private String nifPoderdante;

	@Column(name="APE1_PODERDANTE")
	private String apellido1Poderdante;

	@Column(name="APE2_PODERDANTE")
	private String apellido2Poderdante;

	@Column(name="NOMBRE_PODERDANTE")
	private String nombrePoderdante;

	@Column(name="NIF_EMPRESA")
	private String nifEmpresa;

	@Column(name="NOMBRE_EMPRESA")
	private String nombreEmpresa;

	@Column(name="ID_PROVINCIA")
	private String idProvincia;

	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;

	@Column(name="PUEBLO")
	private String pueblo;

	@Column(name="ID_TIPO_VIA")
	private String idTipoVia;

	@Column(name="NOMBRE_VIA")
	private String nombreVia; 

	@Column(name="COD_POSTAL")
	private String codPostal;

	@Column(name="NUMERO_VIA")
	private String numeroVia;

	public Long getIdPoderTransporte() {
		return idPoderTransporte;
	}

	public void setIdPoderTransporte(Long idPoderTransporte) {
		this.idPoderTransporte = idPoderTransporte;
	}

	public String getNifPoderdante() {
		return nifPoderdante;
	}

	public void setNifPoderdante(String nifPoderdante) {
		this.nifPoderdante = nifPoderdante;
	}

	public String getApellido1Poderdante() {
		return apellido1Poderdante;
	}

	public void setApellido1Poderdante(String apellido1Poderdante) {
		this.apellido1Poderdante = apellido1Poderdante;
	}

	public String getApellido2Poderdante() {
		return apellido2Poderdante;
	}

	public void setApellido2Poderdante(String apellido2Poderdante) {
		this.apellido2Poderdante = apellido2Poderdante;
	}

	public String getNombrePoderdante() {
		return nombrePoderdante;
	}

	public void setNombrePoderdante(String nombrePoderdante) {
		this.nombrePoderdante = nombrePoderdante;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getNumeroVia() {
		return numeroVia;
	}

	public void setNumeroVia(String numeroVia) {
		this.numeroVia = numeroVia;
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

	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

}