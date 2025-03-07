package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utilidades.estructuras.Fecha;

public class TramiteRegRbmDto extends TramiteRegistroDto {

	private static final long serialVersionUID = 1542429791480922747L;

	private String aprobadoDgrn;

	private String estadoFacturacion;

	private Timestamp fecModificacion;

	private BigDecimal idOficinaRegistro;

	private String modeloContrato;

	private String nombreAsociacion;

	private String nombreDocumento;

	private BigDecimal numeroEjemplar;

	private String numeroEmision;

	private String numeroImpreso;

	private String numeroOperacion;

	private String personaNoDeterminada;

	private String tipoCesionTercero;

	private String tipoContrato;

	private String tipoContratoLr;

	private String tipoOperacion;

	private String email;

	private String email2;

	private String telefono;

	private String telefono2;

	private List<ClausulaDto> clausulas;

	private List<DatoFirmaDto> datoFirmas;

	private DatosFinancierosDto datosFinanciero;

	private List<MensajeDto> mensajes;

	private List<PropiedadDto> propiedades;

	private List<IntervinienteRegistroDto> cesionarios;

	private List<IntervinienteRegistroDto> representantesCesionario;

	private List<IntervinienteRegistroDto> arrendatarios;

	private List<IntervinienteRegistroDto> representantesArrendatario;

	private List<IntervinienteRegistroDto> arrendadores;

	private List<IntervinienteRegistroDto> representantesArrendador;

	private List<IntervinienteRegistroDto> avalistas;

	private List<IntervinienteRegistroDto> representantesAvalista;

	private List<IntervinienteRegistroDto> financiadores;

	private List<IntervinienteRegistroDto> compradores;

	private List<IntervinienteRegistroDto> representantesComprador;

	private List<IntervinienteRegistroDto> vendedores;

	private List<IntervinienteRegistroDto> proveedores;

	private List<IntervinienteRegistroDto> compradoresArrendatarios;

	private List<PactoDto> pactos;

	//Cancelaciones
	private String causaCancelacion;

	private DatosInscripcionDto datosInscripcion;

	private BigDecimal importeComisionCancelacion;

	private String situacionJuridicaCancelacion;

	private EntidadCancelacionDto entidadSucesora;

	private List<IntervinienteRegistroDto> representantesSolicitante;

	//Desistimientos
	private Date fecEntradaOrigen;
	private Fecha fecEntradaOrigenDesistimim;
	private BigDecimal numEntradaOrigen;
	private Date fecPresentacionOrigen;
	private Fecha fecPresentacionOrigenDesistimim;
	private BigDecimal numPresentacionOrigen;
	

	//Objetos de pantalla
	private DatoFirmaDto datoFirmaDto;
	private ComisionDto comisionDto;
	private OtroImporteDto otroImporteDto;
	private PactoDto pactoDto;
	private AcuerdoDto acuerdoDto;
	private ClausulaDto clausulaDto;
	private ReconocimientoDeudaDto reconocimientoDeudaDto;
	private CuadroAmortizacionDto cuadroAmortizacionDto;
	private PropiedadDto propiedadDto;
	private IntervinienteRegistroDto cesionario;
	private IntervinienteRegistroDto representanteCesionario;
	private IntervinienteRegistroDto arrendador;
	private IntervinienteRegistroDto representanteArrendador;
	private IntervinienteRegistroDto arrendatario;
	private IntervinienteRegistroDto representanteArrendatario;
	private IntervinienteRegistroDto comprador;
	private IntervinienteRegistroDto representanteComprador;
	private IntervinienteRegistroDto avalista;
	private IntervinienteRegistroDto representanteAvalista;
	private IntervinienteRegistroDto vendedor;
	private IntervinienteRegistroDto proveedor;
	private IntervinienteRegistroDto solicitante;
	private IntervinienteRegistroDto representanteSolicitante;
	private IntervinienteRegistroDto compradorArrendatario;
	private List<String> situacionesJuridicas;
	private String situacionJuridica;




	/*
	 * *****************************************************************
	 * Getters & setters
	 * ***************************************************************** 
	 */

	public String getAprobadoDgrn() {
		return aprobadoDgrn;
	}

	public void setAprobadoDgrn(String aprobadoDgrn) {
		this.aprobadoDgrn = aprobadoDgrn;
	}

	public String getEstadoFacturacion() {
		return estadoFacturacion;
	}

	public void setEstadoFacturacion(String estadoFacturacion) {
		this.estadoFacturacion = estadoFacturacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getIdOficinaRegistro() {
		return idOficinaRegistro;
	}

	public void setIdOficinaRegistro(BigDecimal idOficinaRegistro) {
		this.idOficinaRegistro = idOficinaRegistro;
	}

	public String getModeloContrato() {
		return modeloContrato;
	}

	public void setModeloContrato(String modeloContrato) {
		this.modeloContrato = modeloContrato;
	}

	public String getNombreAsociacion() {
		return nombreAsociacion;
	}

	public void setNombreAsociacion(String nombreAsociacion) {
		this.nombreAsociacion = nombreAsociacion;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public BigDecimal getNumeroEjemplar() {
		return numeroEjemplar;
	}

	public void setNumeroEjemplar(BigDecimal numeroEjemplar) {
		this.numeroEjemplar = numeroEjemplar;
	}

	public String getNumeroEmision() {
		return numeroEmision;
	}

	public void setNumeroEmision(String numeroEmision) {
		this.numeroEmision = numeroEmision;
	}

	public String getNumeroImpreso() {
		return numeroImpreso;
	}

	public void setNumeroImpreso(String numeroImpreso) {
		this.numeroImpreso = numeroImpreso;
	}

	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public String getPersonaNoDeterminada() {
		return personaNoDeterminada;
	}

	public void setPersonaNoDeterminada(String personaNoDeterminada) {
		this.personaNoDeterminada = personaNoDeterminada;
	}

	public String getTipoCesionTercero() {
		return tipoCesionTercero;
	}

	public void setTipoCesionTercero(String tipoCesionTercero) {
		this.tipoCesionTercero = tipoCesionTercero;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getTipoContratoLr() {
		return tipoContratoLr;
	}

	public void setTipoContratoLr(String tipoContratoLr) {
		this.tipoContratoLr = tipoContratoLr;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public List<ClausulaDto> getClausulas() {
		return clausulas;
	}

	public void setClausulas(List<ClausulaDto> clausulas) {
		this.clausulas = clausulas;
	}

	public List<DatoFirmaDto> getDatoFirmas() {
		if(null==datoFirmas){
			datoFirmas = new ArrayList<DatoFirmaDto>();
		}
		return datoFirmas;
	}

	public void setDatoFirmas(List<DatoFirmaDto> datoFirmas) {
		this.datoFirmas = datoFirmas;
	}

	public DatosFinancierosDto getDatosFinanciero() {
		if(null == datosFinanciero)
			datosFinanciero = new DatosFinancierosDto();
		return datosFinanciero;
	}

	public void setDatosFinanciero(DatosFinancierosDto datosFinanciero) {
		this.datosFinanciero = datosFinanciero;
	}

	public List<MensajeDto> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeDto> mensajes) {
		this.mensajes = mensajes;
	}

	public List<PactoDto> getPactos() {
		return pactos;
	}

	public void setPactos(List<PactoDto> pactos) {
		this.pactos = pactos;
	}

	public List<IntervinienteRegistroDto> getArrendatarios() {
		return arrendatarios;
	}

	public void setArrendatarios(List<IntervinienteRegistroDto> arrendatarios) {
		this.arrendatarios = arrendatarios;
	}

	public List<IntervinienteRegistroDto> getRepresentantesArrendatario() {
		return representantesArrendatario;
	}

	public void setRepresentantesArrendatario(List<IntervinienteRegistroDto> representantesArrendatario) {
		this.representantesArrendatario = representantesArrendatario;
	}

	public List<IntervinienteRegistroDto> getArrendadores() {
		return arrendadores;
	}

	public void setArrendadores(List<IntervinienteRegistroDto> arrendadores) {
		this.arrendadores = arrendadores;
	}

	public List<IntervinienteRegistroDto> getRepresentantesArrendador() {
		return representantesArrendador;
	}

	public void setRepresentantesArrendador(List<IntervinienteRegistroDto> representantesArrendador) {
		this.representantesArrendador = representantesArrendador;
	}

	public List<IntervinienteRegistroDto> getCesionarios() {
		return cesionarios;
	}

	public void setCesionarios(List<IntervinienteRegistroDto> cesionarios) {
		this.cesionarios = cesionarios;
	}

	public List<IntervinienteRegistroDto> getRepresentantesCesionario() {
		return representantesCesionario;
	}

	public void setRepresentantesCesionario(List<IntervinienteRegistroDto> representantesCesionario) {
		this.representantesCesionario = representantesCesionario;
	}

	public List<IntervinienteRegistroDto> getAvalistas() {
		return avalistas;
	}

	public void setAvalistas(List<IntervinienteRegistroDto> avalistas) {
		this.avalistas = avalistas;
	}

	public List<IntervinienteRegistroDto> getRepresentantesAvalista() {
		return representantesAvalista;
	}

	public void setRepresentantesAvalista(List<IntervinienteRegistroDto> representantesAvalista) {
		this.representantesAvalista = representantesAvalista;
	}

	public List<IntervinienteRegistroDto> getFinanciadores() {
		return financiadores;
	}

	public void setFinanciadores(List<IntervinienteRegistroDto> financiadores) {
		this.financiadores = financiadores;
	}

	public List<IntervinienteRegistroDto> getCompradores() {
		return compradores;
	}

	public void setCompradores(List<IntervinienteRegistroDto> compradores) {
		this.compradores = compradores;
	}

	public List<IntervinienteRegistroDto> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<IntervinienteRegistroDto> vendedores) {
		this.vendedores = vendedores;
	}

	public List<IntervinienteRegistroDto> getRepresentantesComprador() {
		return representantesComprador;
	}

	public void setRepresentantesComprador(List<IntervinienteRegistroDto> representantesComprador) {
		this.representantesComprador = representantesComprador;
	}

	public AcuerdoDto getAcuerdoDto() {
		return acuerdoDto;
	}

	public void setAcuerdoDto(AcuerdoDto acuerdoDto) {
		this.acuerdoDto = acuerdoDto;
	}

	public ClausulaDto getClausulaDto() {
		return clausulaDto;
	}

	public void setClausulaDto(ClausulaDto clausulaDto) {
		this.clausulaDto = clausulaDto;
	}

	public ReconocimientoDeudaDto getReconocimientoDeudaDto() {
		return reconocimientoDeudaDto;
	}

	public void setReconocimientoDeudaDto(ReconocimientoDeudaDto reconocimientoDeudaDto) {
		this.reconocimientoDeudaDto = reconocimientoDeudaDto;
	}

	public CuadroAmortizacionDto getCuadroAmortizacionDto() {
		return cuadroAmortizacionDto;
	}

	public void setCuadroAmortizacionDto(CuadroAmortizacionDto cuadroAmortizacionDto) {
		this.cuadroAmortizacionDto = cuadroAmortizacionDto;
	}

	public PropiedadDto getPropiedadDto() {
		if(null == propiedadDto){
			propiedadDto = new PropiedadDto();
		}
		return propiedadDto;
	}

	public void setPropiedadDto(PropiedadDto propiedadDto) {
		this.propiedadDto = propiedadDto;
	}

	public IntervinienteRegistroDto getCesionario() {
		return cesionario;
	}

	public void setCesionario(IntervinienteRegistroDto cesionario) {
		this.cesionario = cesionario;
	}

	public IntervinienteRegistroDto getRepresentanteCesionario() {
		if(null == representanteCesionario)
			representanteCesionario = new IntervinienteRegistroDto();
		return representanteCesionario;
	}

	public void setRepresentanteCesionario(IntervinienteRegistroDto representanteCesionario) {
		this.representanteCesionario = representanteCesionario;
	}

	public IntervinienteRegistroDto getComprador() {
		return comprador;
	}

	public void setComprador(IntervinienteRegistroDto comprador) {
		this.comprador = comprador;
	}

	public IntervinienteRegistroDto getRepresentanteComprador() {
		if(null == representanteComprador)
			representanteComprador = new IntervinienteRegistroDto();
		return representanteComprador;
	}

	public void setRepresentanteComprador(IntervinienteRegistroDto representanteComprador) {
		this.representanteComprador = representanteComprador;
	}

	public IntervinienteRegistroDto getAvalista() {
		return avalista;
	}

	public void setAvalista(IntervinienteRegistroDto avalista) {
		this.avalista = avalista;
	}

	public IntervinienteRegistroDto getRepresentanteAvalista() {
		if(null == representanteAvalista)
			representanteAvalista = new IntervinienteRegistroDto();
		return representanteAvalista;
	}

	public void setRepresentanteAvalista(IntervinienteRegistroDto representanteAvalista) {
		this.representanteAvalista = representanteAvalista;
	}

	public IntervinienteRegistroDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(IntervinienteRegistroDto vendedor) {
		this.vendedor = vendedor;
	}

	public IntervinienteRegistroDto getProveedor() {
		return proveedor;
	}

	public void setProveedor(IntervinienteRegistroDto proveedor) {
		this.proveedor = proveedor;
	}

	public DatoFirmaDto getDatoFirmaDto() {
		if(null == datoFirmaDto)
			datoFirmaDto = new DatoFirmaDto();
		return datoFirmaDto;
	}

	public void setDatoFirmaDto(DatoFirmaDto datoFirmaDto) {
		this.datoFirmaDto = datoFirmaDto;
	}

	public ComisionDto getComisionDto() {
		return comisionDto;
	}

	public void setComisionDto(ComisionDto comisionDto) {
		this.comisionDto = comisionDto;
	}

	public OtroImporteDto getOtroImporteDto() {
		return otroImporteDto;
	}

	public void setOtroImporteDto(OtroImporteDto otroImporteDto) {
		this.otroImporteDto = otroImporteDto;
	}

	public PactoDto getPactoDto() {
		return pactoDto;
	}

	public void setPactoDto(PactoDto pactoDto) {
		this.pactoDto = pactoDto;
	}

	public List<IntervinienteRegistroDto> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<IntervinienteRegistroDto> proveedores) {
		this.proveedores = proveedores;
	}

	public IntervinienteRegistroDto getArrendador() {
		return arrendador;
	}

	public void setArrendador(IntervinienteRegistroDto arrendador) {
		this.arrendador = arrendador;
	}

	public IntervinienteRegistroDto getRepresentanteArrendador() {
		if(null == representanteArrendador)
			representanteArrendador = new IntervinienteRegistroDto();
		return representanteArrendador;
	}

	public void setRepresentanteArrendador(IntervinienteRegistroDto representanteArrendador) {
		this.representanteArrendador = representanteArrendador;
	}

	public IntervinienteRegistroDto getArrendatario() {
		return arrendatario;
	}

	public void setArrendatario(IntervinienteRegistroDto arrendatario) {
		this.arrendatario = arrendatario;
	}

	public IntervinienteRegistroDto getRepresentanteArrendatario() {
		if(null == representanteArrendatario)
			representanteArrendatario = new IntervinienteRegistroDto();
		return representanteArrendatario;
	}

	public void setRepresentanteArrendatario(IntervinienteRegistroDto representanteArrendatario) {
		this.representanteArrendatario = representanteArrendatario;
	}

	public List<PropiedadDto> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<PropiedadDto> propiedades) {
		this.propiedades = propiedades;
	}

	public IntervinienteRegistroDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteRegistroDto solicitante) {
		this.solicitante = solicitante;
	}

	public String getCausaCancelacion() {
		return causaCancelacion;
	}

	public void setCausaCancelacion(String causaCancelacion) {
		this.causaCancelacion = causaCancelacion;
	}

	public DatosInscripcionDto getDatosInscripcion() {
		return datosInscripcion;
	}

	public void setDatosInscripcion(DatosInscripcionDto datosInscripcion) {
		this.datosInscripcion = datosInscripcion;
	}

	public BigDecimal getImporteComisionCancelacion() {
		return importeComisionCancelacion;
	}

	public void setImporteComisionCancelacion(BigDecimal importeComisionCancelacion) {
		this.importeComisionCancelacion = importeComisionCancelacion;
	}

	public String getSituacionJuridicaCancelacion() {
		return situacionJuridicaCancelacion;
	}

	public void setSituacionJuridicaCancelacion(String situacionJuridicaCancelacion) {
		this.situacionJuridicaCancelacion = situacionJuridicaCancelacion;
	}

	public EntidadCancelacionDto getEntidadSucesora() {
		return entidadSucesora;
	}

	public void setEntidadSucesora(EntidadCancelacionDto entidadSucesora) {
		this.entidadSucesora = entidadSucesora;
	}

	public IntervinienteRegistroDto getCompradorArrendatario() {
		return compradorArrendatario;
	}

	public void setCompradorArrendatario(IntervinienteRegistroDto compradorArrendatario) {
		this.compradorArrendatario = compradorArrendatario;
	}

	public List<IntervinienteRegistroDto> getRepresentantesSolicitante() {
		return representantesSolicitante;
	}

	public void setRepresentantesSolicitante(List<IntervinienteRegistroDto> representantesSolicitante) {
		this.representantesSolicitante = representantesSolicitante;
	}

	public IntervinienteRegistroDto getRepresentanteSolicitante() {
		if(null == representanteSolicitante)
			representanteSolicitante = new IntervinienteRegistroDto();
		return representanteSolicitante;
	}

	public void setRepresentanteSolicitante(IntervinienteRegistroDto representanteSolicitante) {
		this.representanteSolicitante = representanteSolicitante;
	}

	public List<IntervinienteRegistroDto> getCompradoresArrendatarios() {
		return compradoresArrendatarios;
	}

	public void setCompradoresArrendatarios(List<IntervinienteRegistroDto> compradoresArrendatarios) {
		this.compradoresArrendatarios = compradoresArrendatarios;
	}

	public List<String> getSituacionesJuridicas() {
		return situacionesJuridicas;
	}

	public void setSituacionesJuridicas(List<String> situacionesJuridicas) {
		this.situacionesJuridicas = situacionesJuridicas;
	}

	public String getSituacionJuridica() {
		return situacionJuridica;
	}

	public void setSituacionJuridica(String situacionJuridica) {
		this.situacionJuridica = situacionJuridica;
	}

	public Date getFecEntradaOrigen() {
		return fecEntradaOrigen;
	}

	public void setFecEntradaOrigen(Date fecEntradaOrigen) {
		this.fecEntradaOrigen = fecEntradaOrigen;
	}

	public Fecha getFecEntradaOrigenDesistimim() {
		return fecEntradaOrigenDesistimim;
	}

	public void setFecEntradaOrigenDesistimim(Fecha fecEntradaOrigenDesistimim) {
		this.fecEntradaOrigenDesistimim = fecEntradaOrigenDesistimim;
	}

	public BigDecimal getNumEntradaOrigen() {
		return numEntradaOrigen;
	}

	public void setNumEntradaOrigen(BigDecimal numEntradaOrigen) {
		this.numEntradaOrigen = numEntradaOrigen;
	}

	public Date getFecPresentacionOrigen() {
		return fecPresentacionOrigen;
	}

	public void setFecPresentacionOrigen(Date fecPresentacionOrigen) {
		this.fecPresentacionOrigen = fecPresentacionOrigen;
	}

	public Fecha getFecPresentacionOrigenDesistimim() {
		return fecPresentacionOrigenDesistimim;
	}

	public void setFecPresentacionOrigenDesistimim(Fecha fecPresentacionOrigenDesistimim) {
		this.fecPresentacionOrigenDesistimim = fecPresentacionOrigenDesistimim;
	}

	public BigDecimal getNumPresentacionOrigen() {
		return numPresentacionOrigen;
	}

	public void setNumPresentacionOrigen(BigDecimal numPresentacionOrigen) {
		this.numPresentacionOrigen = numPresentacionOrigen;
	}
}
