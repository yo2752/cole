package hibernate.entities.registradores;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the TRAMITE_REGISTRO database table.
 */
@Entity
@Table(name = "TRAMITE_REGISTRO")
public class TramiteRegistro implements Serializable {

	private static final long serialVersionUID = 4818348481145220821L;

	@Id
	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Column(name = "ANIO_PROTOCOLO")
	private BigDecimal anioProtocolo;

	@Column(name = "ANIO_REG")
	private BigDecimal anioReg;

	@Column(name = "ANIO_REG_SUB")
	private BigDecimal anioRegSub;

	private String cif;

	@Column(name = "CIF_TITULAR_CUENTA")
	private String cifTitularCuenta;

	@Column(name = "CODIGO_NOTARIA")
	private String codigoNotaria;

	@Column(name = "CODIGO_NOTARIO")
	private String codigoNotario;

	private BigDecimal estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CERTIF")
	private Date fechaCertif;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ULT_ESTADO")
	private Date fechaUltEstado;

	@Column(name = "FICHERO_SUBIDO")
	private String ficheroSubido;

	@Column(name = "FORMA_PAGO")
	private BigDecimal formaPago;

	@Column(name = "HORA_ENTRADA_REG")
	private String horaEntradaReg;

	@Column(name = "ID_CONTRATO")
	private BigDecimal idContrato;

	@Column(name = "ID_FIRMA_DOC")
	private String idFirmaDoc;

	@Column(name = "ID_REGISTRO")
	private String idRegistro;

	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	private String inmatriculada;

	@Column(name = "LIBRO_REG")
	private BigDecimal libroReg;

	@Column(name = "LIBRO_REG_SUB")
	private BigDecimal libroRegSub;

	private String lugar;

	@Column(name = "N_ENVIOS")
	private BigDecimal nEnvios;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NUM_REG")
	private BigDecimal numReg;

	@Column(name = "NUM_REG_SUB")
	private BigDecimal numRegSub;

	private String operacion;

	private String presentante;

	private String protocolo;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	private String respuesta;

	private String subsanacion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	public TramiteRegistro() {}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public BigDecimal getnEnvios() {
		return nEnvios;
	}

	public void setnEnvios(BigDecimal nEnvios) {
		this.nEnvios = nEnvios;
	}

	public BigDecimal getAnioProtocolo() {
		return this.anioProtocolo;
	}

	public void setAnioProtocolo(BigDecimal anioProtocolo) {
		this.anioProtocolo = anioProtocolo;
	}

	public BigDecimal getAnioReg() {
		return this.anioReg;
	}

	public void setAnioReg(BigDecimal anioReg) {
		this.anioReg = anioReg;
	}

	public BigDecimal getAnioRegSub() {
		return this.anioRegSub;
	}

	public void setAnioRegSub(BigDecimal anioRegSub) {
		this.anioRegSub = anioRegSub;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCifTitularCuenta() {
		return this.cifTitularCuenta;
	}

	public void setCifTitularCuenta(String cifTitularCuenta) {
		this.cifTitularCuenta = cifTitularCuenta;
	}

	public String getCodigoNotaria() {
		return this.codigoNotaria;
	}

	public void setCodigoNotaria(String codigoNotaria) {
		this.codigoNotaria = codigoNotaria;
	}

	public String getCodigoNotario() {
		return this.codigoNotario;
	}

	public void setCodigoNotario(String codigoNotario) {
		this.codigoNotario = codigoNotario;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaCertif() {
		return this.fechaCertif;
	}

	public void setFechaCertif(Date fechaCertif) {
		this.fechaCertif = fechaCertif;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaUltEstado() {
		return this.fechaUltEstado;
	}

	public void setFechaUltEstado(Date fechaUltEstado) {
		this.fechaUltEstado = fechaUltEstado;
	}

	public String getFicheroSubido() {
		return this.ficheroSubido;
	}

	public void setFicheroSubido(String ficheroSubido) {
		this.ficheroSubido = ficheroSubido;
	}

	public BigDecimal getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(BigDecimal formaPago) {
		this.formaPago = formaPago;
	}

	public String getHoraEntradaReg() {
		return this.horaEntradaReg;
	}

	public void setHoraEntradaReg(String horaEntradaReg) {
		this.horaEntradaReg = horaEntradaReg;
	}

	public BigDecimal getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getIdFirmaDoc() {
		return this.idFirmaDoc;
	}

	public void setIdFirmaDoc(String idFirmaDoc) {
		this.idFirmaDoc = idFirmaDoc;
	}

	public String getIdRegistro() {
		return this.idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getInmatriculada() {
		return this.inmatriculada;
	}

	public void setInmatriculada(String inmatriculada) {
		this.inmatriculada = inmatriculada;
	}

	public BigDecimal getLibroReg() {
		return this.libroReg;
	}

	public void setLibroReg(BigDecimal libroReg) {
		this.libroReg = libroReg;
	}

	public BigDecimal getLibroRegSub() {
		return this.libroRegSub;
	}

	public void setLibroRegSub(BigDecimal libroRegSub) {
		this.libroRegSub = libroRegSub;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public BigDecimal getNEnvios() {
		return this.nEnvios;
	}

	public void setNEnvios(BigDecimal nEnvios) {
		this.nEnvios = nEnvios;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumReg() {
		return this.numReg;
	}

	public void setNumReg(BigDecimal numReg) {
		this.numReg = numReg;
	}

	public BigDecimal getNumRegSub() {
		return this.numRegSub;
	}

	public void setNumRegSub(BigDecimal numRegSub) {
		this.numRegSub = numRegSub;
	}

	public String getOperacion() {
		return this.operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getPresentante() {
		return this.presentante;
	}

	public void setPresentante(String presentante) {
		this.presentante = presentante;
	}

	public String getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getRefPropia() {
		return this.refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getSubsanacion() {
		return this.subsanacion;
	}

	public void setSubsanacion(String subsanacion) {
		this.subsanacion = subsanacion;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}