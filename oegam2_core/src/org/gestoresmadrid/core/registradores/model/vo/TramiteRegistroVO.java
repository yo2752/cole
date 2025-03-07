package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

/**
 * The persistent class for the TRAMITE_REGISTRO database table.
 */
@Entity
@Table(name = "TRAMITE_REGISTRO")
@Inheritance(strategy = InheritanceType.JOINED)
public class TramiteRegistroVO implements Serializable {

	private static final long serialVersionUID = -4603540239767474930L;

	@Id
	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Column(name = "ID_TRAMITE_CORPME")
	private String idTramiteCorpme;

	@Column(name = "ANIO_PROTOCOLO")
	private BigDecimal anioProtocolo;

	@Column(name = "ANIO_REG")
	private BigDecimal anioReg;

	@Column(name = "ANIO_REG_SUB")
	private BigDecimal anioRegSub;

	@Column(name = "CIF")
	private String cif;

	@Column(name = "CIF_TITULAR_CUENTA")
	private String cifTitularCuenta;

	@Column(name = "CODIGO_NOTARIA")
	private String codigoNotaria;

	@Column(name = "CODIGO_NOTARIO")
	private String codigoNotario;

	@Column(name = "ID_DIRECCION_DESTINATARIO")
	private Long idDireccionDestinatario;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CERTIF")
	private Date fechaCertif;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULT_ESTADO")
	private Date fechaUltEstado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_DOCUMENTO")
	private Date fechaDocumento;

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

	@Column(name = "LOCALIZADOR_REG")
	private String localizadorReg;

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

	@Column(name = "EJERCICIO_CUENTA")
	private String ejercicioCuenta;

	@Column(name = "CLASE_CUENTA")
	private String claseCuenta;

	@Column(name = "NOMBRE_TITULAR_CUENTA")
	private String nombreTitular;

	@Column(name = "ID_MUNICIPIO_CUENTA")
	private String idMunicipioCuenta;

	@Column(name = "ID_PROVINCIA_CUENTA")
	private String idProvinciaCuenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_BANCARIOS", insertable = false, updatable = false)
	private DatosBancariosFavoritosVO datosBancarios;

	@Column(name = "ID_DATOS_BANCARIOS")
	private BigDecimal idDatosBancarios;

	@Column(name = "DATOS_BANCARIOS")
	private String numCuentaPago;

	@Column(name = "APLICAR_IRPF")
	private String aplicarIrpf;

	@Column(name = "PORCENTAJE_IRPF")
	private String porcentajeIrpf;

	@Column(name = "ID_TRAMITE_ORIGEN")
	private BigDecimal idTramiteOrigen;

	@Column(name = "TIPO_DESTINATARIO")
	private String tipoDestinatario;

	// bi-directional One-to-many association to FacturaRegistro
	@OneToMany(mappedBy = "tramiteRegistro")
	private Set<FacturaRegistroVO> facturasRegistro;

	// bi-directional One-to-many association to MinutaRegistro
	@OneToMany(mappedBy = "tramiteRegistro")
	private Set<MinutaRegistroVO> minutasRegistro;

	// bi-directional One-to-many association to LibroRegistro
	@OneToMany(mappedBy = "tramiteRegistro")
	private Set<LibroRegistroVO> librosRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "CIF", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private PersonaVO sociedad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_REGISTRO", referencedColumnName = "ID", insertable = false, updatable = false)
	private RegistroVO registro;

	public RegistroVO getRegistro() {
		return registro;
	}

	public void setRegistro(RegistroVO registro) {
		this.registro = registro;
	}

	public TramiteRegistroVO() {}

	public BigDecimal getIdTramiteRegistro() {
		return this.idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public String getIdTramiteCorpme() {
		return idTramiteCorpme;
	}

	public void setIdTramiteCorpme(String idTramiteCorpme) {
		this.idTramiteCorpme = idTramiteCorpme;
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

	public BigDecimal getnEnvios() {
		return nEnvios;
	}

	public void setnEnvios(BigDecimal nEnvios) {
		this.nEnvios = nEnvios;
	}

	public PersonaVO getSociedad() {
		return sociedad;
	}

	public void setSociedad(PersonaVO sociedad) {
		this.sociedad = sociedad;
	}

	public Long getIdDireccionDestinatario() {
		return idDireccionDestinatario;
	}

	public void setIdDireccionDestinatario(Long idDireccionDestinatario) {
		this.idDireccionDestinatario = idDireccionDestinatario;
	}

	public Set<LibroRegistroVO> getLibrosRegistro() {
		return librosRegistro;
	}

	public void setLibrosRegistro(Set<LibroRegistroVO> librosRegistro) {
		this.librosRegistro = librosRegistro;
	}

	public String getEjercicioCuenta() {
		return ejercicioCuenta;
	}

	public void setEjercicioCuenta(String ejercicioCuenta) {
		this.ejercicioCuenta = ejercicioCuenta;
	}

	public String getClaseCuenta() {
		return claseCuenta;
	}

	public void setClaseCuenta(String claseCuenta) {
		this.claseCuenta = claseCuenta;
	}

	public Set<FacturaRegistroVO> getFacturasRegistro() {
		return facturasRegistro;
	}

	public void setFacturasRegistro(Set<FacturaRegistroVO> facturasRegistro) {
		this.facturasRegistro = facturasRegistro;
	}

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public String getIdMunicipioCuenta() {
		return idMunicipioCuenta;
	}

	public void setIdMunicipioCuenta(String idMunicipioCuenta) {
		this.idMunicipioCuenta = idMunicipioCuenta;
	}

	public String getIdProvinciaCuenta() {
		return idProvinciaCuenta;
	}

	public void setIdProvinciaCuenta(String idProvinciaCuenta) {
		this.idProvinciaCuenta = idProvinciaCuenta;
	}

	public DatosBancariosFavoritosVO getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosVO datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public BigDecimal getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(BigDecimal idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public String getNumCuentaPago() {
		return numCuentaPago;
	}

	public void setNumCuentaPago(String numCuentaPago) {
		this.numCuentaPago = numCuentaPago;
	}

	public Set<MinutaRegistroVO> getMinutasRegistro() {
		return minutasRegistro;
	}

	public void setMinutasRegistro(Set<MinutaRegistroVO> minutasRegistro) {
		this.minutasRegistro = minutasRegistro;
	}

	public String getLocalizadorReg() {
		return localizadorReg;
	}

	public void setLocalizadorReg(String localizadorReg) {
		this.localizadorReg = localizadorReg;
	}

	public String getAplicarIrpf() {
		return aplicarIrpf;
	}

	public void setAplicarIrpf(String aplicarIrpf) {
		this.aplicarIrpf = aplicarIrpf;
	}

	public String getPorcentajeIrpf() {
		return porcentajeIrpf;
	}

	public void setPorcentajeIrpf(String porcentajeIrpf) {
		this.porcentajeIrpf = porcentajeIrpf;
	}

	public BigDecimal getIdTramiteOrigen() {
		return idTramiteOrigen;
	}

	public void setIdTramiteOrigen(BigDecimal idTramiteOrigen) {
		this.idTramiteOrigen = idTramiteOrigen;
	}

	public Date getFechaDocumento() {
		return this.fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
}