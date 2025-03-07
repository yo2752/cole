package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class TramiteRegistroDto implements Serializable {

	private static final long serialVersionUID = -8838695156994463331L;

	private BigDecimal idTramiteRegistro;

	private String idTramiteCorpme;

	private BigDecimal anioProtocolo;

	private BigDecimal anioReg;

	private BigDecimal anioRegSub;

	private String cif;

	private String cifTitularCuenta;

	private String codigoNotaria;

	private String codigoNotario;

	private BigDecimal estado;

	private Fecha fechaCertif;

	private Fecha fechaCreacion;

	private Fecha fechaEnvio;

	private Fecha fechaUltEstado;

	private Fecha fechaDocumento;

	private String ficheroSubido;

	private BigDecimal formaPago;

	private String horaEntradaReg;

	private BigDecimal idContrato;

	private Long idDireccionDestinatario;

	private String idFirmaDoc;

	private String idRegistro;

	private String registroSeleccionadoOculto;

	private BigDecimal idUsuario;

	private String inmatriculada;

	private BigDecimal libroReg;

	private BigDecimal libroRegSub;

	private String localizadorReg;

	private String lugar;

	private BigDecimal nEnvios;

	private String numColegiado;

	private BigDecimal numReg;

	private BigDecimal numRegSub;

	private String operacion;

	private String operacionDes;

	private String presentante;

	private String protocolo;

	private String refPropia;

	private String respuesta;

	private String subsanacion;

	private String tipoTramite;

	private PersonaDto sociedad;

	private boolean sociedadEstablecida;

	private boolean reunionEstablecida;

	private boolean escrituraEstablecida;

	private List<CertifCargoDto> certificantes;

	private CertifCargoDto certificante;

	private List<AsistenteDto> asistentes;

	private AsistenteDto asistente;

	private List<AcuerdoDto> ceses;

	private List<AcuerdoDto> nombramientos;

	private AcuerdoDto acuerdoCese;

	private AcuerdoDto acuerdoNombramiento;

	private ReunionDto reunion;

	private List<InmuebleDto> inmuebles;

	private List<RegistroFueraSecuenciaDto> acusesPendientes;

	private ArrayList<FicheroInfo> ficherosSubidos;
	private ArrayList<FicheroInfo> listaDocuRecibida;
	private String extensionFicheroEnviado;
	private File ficheroAEnviar;

	private String usuarioCorpme;
	private String passwordCorpme;
	private String ncorpme;
	private String identificacionCorpme;

	private String version;

	private PersonaDto presentador;
	private PersonaDto destinatario;

	private String tipoEntrada;

	private BigDecimal numSociedades;

	private BigDecimal numInmuebles;

	private String nombreTitular;

	private String idMunicipioCuenta;

	private String idProvinciaCuenta;

	private DatosBancariosFavoritosDto datosBancarios;

	private BigDecimal idDatosBancarios;

	private String numCuentaPago;

	// Checkbox escrituras
	private boolean gestor;
	private boolean gestoria;
	private boolean checkPrimera;
	private boolean checkSubsanacion;
	private boolean identificacionNotarial;
	private boolean identificacionNumeroEntrada;
	private boolean suspension;

	private List<LibroRegistroDto> librosRegistro;

	private List<FacturaRegistroDto> facturasRegistro;

	private FacturaRegistroDto facturaRegistro;

	private List<MinutaRegistroDto> minutasRegistro;

	private MinutaRegistroDto minutaRegistro;

	//Objeto de pantalla
	private InmuebleDto inmueble;

	private LibroRegistroDto libroRegistro;

	private String ejercicioCuenta;
	private String claseCuenta;

	private RegistroDto registro;

	private String aplicarIrpf;

	private String porcentajeIrpf;

	private BigDecimal idTramiteOrigen;
	
	private String tipoDestinatario;

	public RegistroDto getRegistro() {
		return registro;
	}

	public void setRegistro(RegistroDto registro) {
		this.registro = registro;
	}

	public TramiteRegistroDto() {}

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

	public Fecha getFechaCertif() {
		return fechaCertif;
	}

	public void setFechaCertif(Fecha fechaCertif) {
		this.fechaCertif = fechaCertif;
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Fecha getFechaUltEstado() {
		return fechaUltEstado;
	}

	public void setFechaUltEstado(Fecha fechaUltEstado) {
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

	public Long getIdDireccionDestinatario() {
		return idDireccionDestinatario;
	}

	public void setIdDireccionDestinatario(Long idDireccionDestinatario) {
		this.idDireccionDestinatario = idDireccionDestinatario;
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
		return inmatriculada;
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

	public boolean isSociedadEstablecida() {
		return sociedadEstablecida;
	}

	public void setSociedadEstablecida(boolean sociedadEstablecida) {
		this.sociedadEstablecida = sociedadEstablecida;
	}

	public boolean isReunionEstablecida() {
		return reunionEstablecida;
	}

	public void setReunionEstablecida(boolean reunionEstablecida) {
		this.reunionEstablecida = reunionEstablecida;
	}

	public PersonaDto getSociedad() {
		return sociedad;
	}

	public void setSociedad(PersonaDto sociedad) {
		this.sociedad = sociedad;
	}

	public List<CertifCargoDto> getCertificantes() {
		return certificantes;
	}

	public void setCertificantes(List<CertifCargoDto> certificantes) {
		this.certificantes = certificantes;
	}

	public List<AsistenteDto> getAsistentes() {
		return asistentes;
	}

	public void setAsistentes(List<AsistenteDto> asistentes) {
		this.asistentes = asistentes;
	}

	public String getRegistroSeleccionadoOculto() {
		return registroSeleccionadoOculto;
	}

	public void setRegistroSeleccionadoOculto(String registroSeleccionadoOculto) {
		this.registroSeleccionadoOculto = registroSeleccionadoOculto;
	}

	public ReunionDto getReunion() {
		return reunion;
	}

	public void setReunion(ReunionDto reunion) {
		this.reunion = reunion;
	}

	public List<AcuerdoDto> getCeses() {
		return ceses;
	}

	public void setCeses(List<AcuerdoDto> ceses) {
		this.ceses = ceses;
	}

	public List<AcuerdoDto> getNombramientos() {
		return nombramientos;
	}

	public void setNombramientos(List<AcuerdoDto> nombramientos) {
		this.nombramientos = nombramientos;
	}

	public ArrayList<FicheroInfo> getFicherosSubidos() {
		return ficherosSubidos;
	}

	public void setFicherosSubidos(ArrayList<FicheroInfo> ficherosSubidos) {
		this.ficherosSubidos = ficherosSubidos;
	}

	public String getExtensionFicheroEnviado() {
		return extensionFicheroEnviado;
	}

	public void setExtensionFicheroEnviado(String extensionFicheroEnviado) {
		this.extensionFicheroEnviado = extensionFicheroEnviado;
	}

	public File getFicheroAEnviar() {
		return ficheroAEnviar;
	}

	public void setFicheroAEnviar(File ficheroAEnviar) {
		this.ficheroAEnviar = ficheroAEnviar;
	}

	public String getUsuarioCorpme() {
		return usuarioCorpme;
	}

	public void setUsuarioCorpme(String usuarioCorpme) {
		this.usuarioCorpme = usuarioCorpme;
	}

	public String getPasswordCorpme() {
		return passwordCorpme;
	}

	public void setPasswordCorpme(String passwordCorpme) {
		this.passwordCorpme = passwordCorpme;
	}

	public String getNcorpme() {
		return ncorpme;
	}

	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
	}

	public String getIdentificacionCorpme() {
		return identificacionCorpme;
	}

	public void setIdentificacionCorpme(String identificacionCorpme) {
		this.identificacionCorpme = identificacionCorpme;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public PersonaDto getPresentador() {
		if(null != presentador && null == presentador.getDireccionDto()){
			presentador.setDireccionDto(new DireccionDto());
		}
		return presentador;
	}

	public void setPresentador(PersonaDto presentador) {
		this.presentador = presentador;
	}

	public BigDecimal getNumSociedades() {
		return numSociedades;
	}

	public void setNumSociedades(BigDecimal numSociedades) {
		this.numSociedades = numSociedades;
	}

	public List<RegistroFueraSecuenciaDto> getAcusesPendientes() {
		return acusesPendientes;
	}

	public void setAcusesPendientes(List<RegistroFueraSecuenciaDto> acusesPendientes) {
		this.acusesPendientes = acusesPendientes;
	}

	public boolean isEscrituraEstablecida() {
		return escrituraEstablecida;
	}

	public void setEscrituraEstablecida(boolean escrituraEstablecida) {
		this.escrituraEstablecida = escrituraEstablecida;
	}

	public boolean isGestor() {
		return gestor;
	}

	public void setGestor(boolean gestor) {
		this.gestor = gestor;
	}

	public boolean isGestoria() {
		return gestoria;
	}

	public void setGestoria(boolean gestoria) {
		this.gestoria = gestoria;
	}

	public List<InmuebleDto> getInmuebles() {
		return inmuebles;
	}

	public void setInmuebles(List<InmuebleDto> inmuebles) {
		this.inmuebles = inmuebles;
	}

	public String getOperacionDes() {
		return operacionDes;
	}

	public void setOperacionDes(String operacionDes) {
		this.operacionDes = operacionDes;
	}

	public boolean isIdentificacionNotarial() {
		return identificacionNotarial;
	}

	public void setIdentificacionNotarial(boolean identificacionNotarial) {
		this.identificacionNotarial = identificacionNotarial;
	}

	public boolean isIdentificacionNumeroEntrada() {
		return identificacionNumeroEntrada;
	}

	public void setIdentificacionNumeroEntrada(boolean identificacionNumeroEntrada) {
		this.identificacionNumeroEntrada = identificacionNumeroEntrada;
	}

	public boolean isCheckPrimera() {
		return checkPrimera;
	}

	public void setCheckPrimera(boolean checkPrimera) {
		this.checkPrimera = checkPrimera;
	}

	public boolean isCheckSubsanacion() {
		return checkSubsanacion;
	}

	public void setCheckSubsanacion(boolean checkSubsanacion) {
		this.checkSubsanacion = checkSubsanacion;
	}

	public boolean isSuspension() {
		return suspension;
	}

	public void setSuspension(boolean suspension) {
		this.suspension = suspension;
	}

	public String getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(String tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public PersonaDto getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(PersonaDto destinatario) {
		this.destinatario = destinatario;
	}

	public BigDecimal getNumInmuebles() {
		return numInmuebles;
	}

	public void setNumInmuebles(BigDecimal numInmuebles) {
		this.numInmuebles = numInmuebles;
	}

	public BigDecimal getnEnvios() {
		return nEnvios;
	}

	public void setnEnvios(BigDecimal nEnvios) {
		this.nEnvios = nEnvios;
	}

	public List<LibroRegistroDto> getLibrosRegistro() {
		return librosRegistro;
	}

	public void setLibrosRegistro(List<LibroRegistroDto> librosRegistro) {
		this.librosRegistro = librosRegistro;
	}

	public LibroRegistroDto getLibroRegistro() {
		return libroRegistro;
	}

	public void setLibroRegistro(LibroRegistroDto libroRegistro) {
		this.libroRegistro = libroRegistro;
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

	public InmuebleDto getInmueble() {
		return inmueble;
	}

	public void setInmueble(InmuebleDto inmueble) {
		this.inmueble = inmueble;
	}

	public List<FacturaRegistroDto> getFacturasRegistro() {
		if(null==facturasRegistro){
			facturasRegistro = new ArrayList<FacturaRegistroDto>();
		}
		return facturasRegistro;
	}

	public void setFacturasRegistro(List<FacturaRegistroDto> facturasRegistro) {
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

	public DatosBancariosFavoritosDto getDatosBancarios() {
		if(null == datosBancarios)
			datosBancarios = new DatosBancariosFavoritosDto();
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public FacturaRegistroDto getFacturaRegistro() {
		return facturaRegistro;
	}

	public void setFacturaRegistro(FacturaRegistroDto facturaRegistro) {
		this.facturaRegistro = facturaRegistro;
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

	public List<MinutaRegistroDto> getMinutasRegistro() {
		return minutasRegistro;
	}

	public void setMinutasRegistro(List<MinutaRegistroDto> minutasRegistro) {
		this.minutasRegistro = minutasRegistro;
	}

	public MinutaRegistroDto getMinutaRegistro() {
		return minutaRegistro;
	}

	public void setMinutaRegistro(MinutaRegistroDto minutaRegistro) {
		this.minutaRegistro = minutaRegistro;
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

	/**
	 * @return the listaDocuRecibida
	 */
	public ArrayList<FicheroInfo> getListaDocuRecibida() {
		return listaDocuRecibida;
	}

	/**
	 * @param listaDocuRecibida the listaDocuRecibida to set
	 */
	public void setListaDocuRecibida(ArrayList<FicheroInfo> listaDocuRecibida) {
		this.listaDocuRecibida = listaDocuRecibida;
	}

	public AcuerdoDto getAcuerdoCese() {
		return acuerdoCese;
	}

	public void setAcuerdoCese(AcuerdoDto acuerdoCese) {
		this.acuerdoCese = acuerdoCese;
	}

	public AcuerdoDto getAcuerdoNombramiento() {
		return acuerdoNombramiento;
	}

	public void setAcuerdoNombramiento(AcuerdoDto acuerdoNombramiento) {
		this.acuerdoNombramiento = acuerdoNombramiento;
	}

	public CertifCargoDto getCertificante() {
		return certificante;
	}

	public void setCertificante(CertifCargoDto certificante) {
		this.certificante = certificante;
	}

	public AsistenteDto getAsistente() {
		return asistente;
	}

	public void setAsistente(AsistenteDto asistente) {
		this.asistente = asistente;
	}

	public Fecha getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Fecha fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

}