package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VehiculoAtex5Dto implements Serializable{

	private static final long serialVersionUID = 2651636231074159644L;
	
	private String bastidor;
	private String marca;
	private String modelo;
	private String nive;
	private String paisProcedencia;
	private String servicio;
	private String servicioComplementario;
	private String tipoIndustria;
	private String tipoVehiculo;
	private DatosDomicilioAtex5Dto domicilio;
	private DatosDomicilioAtex5Dto domicilioIne;
	private Date fechaCaducidadTuristica;
	private Date fechaImportacion;
	private Date fechaItv;
	private Date fechaMatriculacion;
	private Date fechaPrimeraMatriculacion;
	private Boolean bajaDefinitiva;
	private Boolean bajaTemporal;
	private Boolean cargaEEFF;
	private Boolean embargo;
	private Boolean excesoPesoDimension;
	private Boolean importacion;
	private Boolean incidencias;
	private Boolean limitacionDisposicion;
	private Boolean posesion;
	private Boolean precinto;
	private Boolean puertoFranco;
	private Boolean reformas;
	private Boolean renting;
	private Boolean subasta;
	private Boolean sustraccion;
	private Boolean tutela;
	private List<DatosCotitularAtex5Dto> listaCotitularesDto;
	private String claseMatriculacion;
	private String jefatura;
	private String sucursal;
	private String codigoIAE;
	private String nifTitularAutonomo;
	private String tipoRequisitoria;
	private String descripcionRequisitoria;
	private String fechaVigorRequisitoria;
	private String accionesRequisitoria;
	private DatosPersonaAtex5Dto personaFisicaDto;
	private DatosPersonaAtex5Dto personaJuridicaDto;
	private String carroceria;
	private String catHomologacion;
	private String color;
	private String combustible;
	private Integer distanciaEjes;
	private Integer viaAnterior;
	private Integer viaPosterior;
	private byte[] certificadoConformidad;
	private byte[] hojaRosa;
	private byte[] hojaRescate;
	private String periodoValidezTarjeta;
	private String periodoValidezTarjeta15;
	private String periodoValidezTarjetaNoValida;
	private String periodoValidezTarjetaValida;
	private byte[] tarjetaItvDefinitiva;
	private byte[] tarjetaItvNoValida;
	private byte[] tarjetaItvTemporal;
    private String fabricante;
    private Integer masaMaxTecnica;
    private Integer masaMaxima;
    private Integer masaServicio;
    private Integer pesoMaximo;
    private Integer tara;
    private String numHomologacion;
    private String mixtas;
    private Integer normales;
    private Integer numPlazasPie;
    private BigDecimal cilindrada;
    private BigDecimal potenciaFiscal;
    private BigDecimal potenciaNetaMax;
    private BigDecimal relPotenciaPeso;
    private String procedencia;
    private String tipoItv;
    private String variante;
    private String version;
    private String tipoTarjetaItv;
    private String fabricanteBase;
    private String masaServicioBase;
    private String marcaBase;
    private String chomologacionBase;
    private String tipoBase;
    private String varianteBase;
    private String versionBase;
    private String momBase;
    private String autonomiaElectrica;
    private String categoriaElectrica;
    private String codigoEco;
    private String codos;
    private BigDecimal consumo;
    private String ecoInnovacion;
    private String nivelEmisiones;
    private String propulsion;
    private String reduccionEco;
    private String tipoAlimentacion;
    private List<DatosResponsableAtex5Dto> listaArrendatariosDto;
    private List<DatosResponsableAtex5Dto> listaConductoresHabitualesDto;
    private List<DatosResponsableAtex5Dto> listaPoseedoresDto;
    private List<DatosResponsableAtex5Dto> listaTutoresDto;
    private List<DatosTramitesAtex5Dto> listaBajasDto;
    private List<DatosTramitesAtex5Dto> listaDuplicadosDto;
    private List<DatosTramitesAtex5Dto> listaProrrogasDto;
    private List<DatosTramitesAtex5Dto> listaRematriculacionesDto;
    private List<DatosTramitesAtex5Dto> listaTransferenciasDto;
    private List<DatosTramitesAtex5Dto> listaMatriculacionTemporalDto;
    private List<DatosAdministrativosAtex5Dto> listaAvisosDto;
    private List<DatosAdministrativosAtex5Dto> listaDenegatoriasDto;
    private List<DatosAdministrativosAtex5Dto> listaEmbargosDto;
    private List<DatosAdministrativosAtex5Dto> listaImpagosDto;
    private DatosAdministrativosAtex5Dto limitacionesDisposicionDto;
    private List<DatosAdministrativosAtex5Dto> listaPrecintosDto;
    private List<DatosItvAtex5Dto> listaItvsDto;
    private DatosReformaAtex5Dto reformaDto;
    private List<DatosSeguroAtex5Dto> listaSegurosDto;
	private DatosSeguridadAtex5Dto datosSeguridadDto;
	private DatosLibroTallerAtex5Dto datosLibroTallerDto;

	private Boolean existenDatosAdministrativos = Boolean.FALSE;
	private Boolean existenDatosItvs = Boolean.FALSE;
	private Boolean existenDatosTramites = Boolean.FALSE;
	private Boolean existenDatosResponsables = Boolean.FALSE;
	private Boolean existenDatosSeguridad = Boolean.FALSE;
	private Boolean existenDatosSeguros = Boolean.FALSE;
	private Boolean existenDatosLibroTalles = Boolean.FALSE;
	
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
	public String getPaisProcedencia() {
		return paisProcedencia;
	}
	public void setPaisProcedencia(String paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getServicioComplementario() {
		return servicioComplementario;
	}
	public void setServicioComplementario(String servicioComplementario) {
		this.servicioComplementario = servicioComplementario;
	}
	public String getTipoIndustria() {
		return tipoIndustria;
	}
	public void setTipoIndustria(String tipoIndustria) {
		this.tipoIndustria = tipoIndustria;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public DatosDomicilioAtex5Dto getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(DatosDomicilioAtex5Dto domicilio) {
		this.domicilio = domicilio;
	}
	public DatosDomicilioAtex5Dto getDomicilioIne() {
		return domicilioIne;
	}
	public void setDomicilioIne(DatosDomicilioAtex5Dto domicilioIne) {
		this.domicilioIne = domicilioIne;
	}
	public Date getFechaCaducidadTuristica() {
		return fechaCaducidadTuristica;
	}
	public void setFechaCaducidadTuristica(Date fechaCaducidadTuristica) {
		this.fechaCaducidadTuristica = fechaCaducidadTuristica;
	}
	public Date getFechaImportacion() {
		return fechaImportacion;
	}
	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}
	public Date getFechaItv() {
		return fechaItv;
	}
	public void setFechaItv(Date fechaItv) {
		this.fechaItv = fechaItv;
	}
	public Date getFechaMatriculacion() {
		return fechaMatriculacion;
	}
	public void setFechaMatriculacion(Date fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}
	public Date getFechaPrimeraMatriculacion() {
		return fechaPrimeraMatriculacion;
	}
	public void setFechaPrimeraMatriculacion(Date fechaPrimeraMatriculacion) {
		this.fechaPrimeraMatriculacion = fechaPrimeraMatriculacion;
	}
	public Boolean getBajaDefinitiva() {
		return bajaDefinitiva;
	}
	public void setBajaDefinitiva(Boolean bajaDefinitiva) {
		this.bajaDefinitiva = bajaDefinitiva;
	}
	public Boolean getBajaTemporal() {
		return bajaTemporal;
	}
	public void setBajaTemporal(Boolean bajaTemporal) {
		this.bajaTemporal = bajaTemporal;
	}
	public Boolean getCargaEEFF() {
		return cargaEEFF;
	}
	public void setCargaEEFF(Boolean cargaEEFF) {
		this.cargaEEFF = cargaEEFF;
	}
	public Boolean getEmbargo() {
		return embargo;
	}
	public void setEmbargo(Boolean embargo) {
		this.embargo = embargo;
	}
	public Boolean getExcesoPesoDimension() {
		return excesoPesoDimension;
	}
	public void setExcesoPesoDimension(Boolean excesoPesoDimension) {
		this.excesoPesoDimension = excesoPesoDimension;
	}
	public Boolean getImportacion() {
		return importacion;
	}
	public void setImportacion(Boolean importacion) {
		this.importacion = importacion;
	}
	public Boolean getIncidencias() {
		return incidencias;
	}
	public void setIncidencias(Boolean incidencias) {
		this.incidencias = incidencias;
	}
	public Boolean getLimitacionDisposicion() {
		return limitacionDisposicion;
	}
	public void setLimitacionDisposicion(Boolean limitacionDisposicion) {
		this.limitacionDisposicion = limitacionDisposicion;
	}
	public Boolean getPosesion() {
		return posesion;
	}
	public void setPosesion(Boolean posesion) {
		this.posesion = posesion;
	}
	public Boolean getPrecinto() {
		return precinto;
	}
	public void setPrecinto(Boolean precinto) {
		this.precinto = precinto;
	}
	public Boolean getPuertoFranco() {
		return puertoFranco;
	}
	public void setPuertoFranco(Boolean puertoFranco) {
		this.puertoFranco = puertoFranco;
	}
	public Boolean getReformas() {
		return reformas;
	}
	public void setReformas(Boolean reformas) {
		this.reformas = reformas;
	}
	public Boolean getRenting() {
		return renting;
	}
	public void setRenting(Boolean renting) {
		this.renting = renting;
	}
	public Boolean getSubasta() {
		return subasta;
	}
	public void setSubasta(Boolean subasta) {
		this.subasta = subasta;
	}
	public Boolean getSustraccion() {
		return sustraccion;
	}
	public void setSustraccion(Boolean sustraccion) {
		this.sustraccion = sustraccion;
	}
	public Boolean getTutela() {
		return tutela;
	}
	public void setTutela(Boolean tutela) {
		this.tutela = tutela;
	}
	public List<DatosCotitularAtex5Dto> getListaCotitularesDto() {
		return listaCotitularesDto;
	}
	public void setListaCotitularesDto(List<DatosCotitularAtex5Dto> listaCotitularesDto) {
		this.listaCotitularesDto = listaCotitularesDto;
	}
	public String getClaseMatriculacion() {
		return claseMatriculacion;
	}
	public void setClaseMatriculacion(String claseMatriculacion) {
		this.claseMatriculacion = claseMatriculacion;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getCodigoIAE() {
		return codigoIAE;
	}
	public void setCodigoIAE(String codigoIAE) {
		this.codigoIAE = codigoIAE;
	}
	public String getNifTitularAutonomo() {
		return nifTitularAutonomo;
	}
	public void setNifTitularAutonomo(String nifTitularAutonomo) {
		this.nifTitularAutonomo = nifTitularAutonomo;
	}
	public String getTipoRequisitoria() {
		return tipoRequisitoria;
	}
	public void setTipoRequisitoria(String tipoRequisitoria) {
		this.tipoRequisitoria = tipoRequisitoria;
	}
	public String getDescripcionRequisitoria() {
		return descripcionRequisitoria;
	}
	public void setDescripcionRequisitoria(String descripcionRequisitoria) {
		this.descripcionRequisitoria = descripcionRequisitoria;
	}
	public String getFechaVigorRequisitoria() {
		return fechaVigorRequisitoria;
	}
	public void setFechaVigorRequisitoria(String fechaVigorRequisitoria) {
		this.fechaVigorRequisitoria = fechaVigorRequisitoria;
	}
	public String getAccionesRequisitoria() {
		return accionesRequisitoria;
	}
	public void setAccionesRequisitoria(String accionesRequisitoria) {
		this.accionesRequisitoria = accionesRequisitoria;
	}
	public DatosPersonaAtex5Dto getPersonaFisicaDto() {
		return personaFisicaDto;
	}
	public void setPersonaFisicaDto(DatosPersonaAtex5Dto personaFisicaDto) {
		this.personaFisicaDto = personaFisicaDto;
	}
	public DatosPersonaAtex5Dto getPersonaJuridicaDto() {
		return personaJuridicaDto;
	}
	public void setPersonaJuridicaDto(DatosPersonaAtex5Dto personaJuridicaDto) {
		this.personaJuridicaDto = personaJuridicaDto;
	}
	public String getCarroceria() {
		return carroceria;
	}
	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}
	public String getCatHomologacion() {
		return catHomologacion;
	}
	public void setCatHomologacion(String catHomologacion) {
		this.catHomologacion = catHomologacion;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCombustible() {
		return combustible;
	}
	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}
	public Integer getDistanciaEjes() {
		return distanciaEjes;
	}
	public void setDistanciaEjes(Integer distanciaEjes) {
		this.distanciaEjes = distanciaEjes;
	}
	public Integer getViaAnterior() {
		return viaAnterior;
	}
	public void setViaAnterior(Integer viaAnterior) {
		this.viaAnterior = viaAnterior;
	}
	public Integer getViaPosterior() {
		return viaPosterior;
	}
	public void setViaPosterior(Integer viaPosterior) {
		this.viaPosterior = viaPosterior;
	}
	public byte[] getCertificadoConformidad() {
		return certificadoConformidad;
	}
	public void setCertificadoConformidad(byte[] certificadoConformidad) {
		this.certificadoConformidad = certificadoConformidad;
	}
	public byte[] getHojaRosa() {
		return hojaRosa;
	}
	public void setHojaRosa(byte[] hojaRosa) {
		this.hojaRosa = hojaRosa;
	}
	public byte[] getHojaRescate() {
		return hojaRescate;
	}
	public void setHojaRescate(byte[] hojaRescate) {
		this.hojaRescate = hojaRescate;
	}
	public String getPeriodoValidezTarjeta() {
		return periodoValidezTarjeta;
	}
	public void setPeriodoValidezTarjeta(String periodoValidezTarjeta) {
		this.periodoValidezTarjeta = periodoValidezTarjeta;
	}
	public String getPeriodoValidezTarjeta15() {
		return periodoValidezTarjeta15;
	}
	public void setPeriodoValidezTarjeta15(String periodoValidezTarjeta15) {
		this.periodoValidezTarjeta15 = periodoValidezTarjeta15;
	}
	public String getPeriodoValidezTarjetaNoValida() {
		return periodoValidezTarjetaNoValida;
	}
	public void setPeriodoValidezTarjetaNoValida(String periodoValidezTarjetaNoValida) {
		this.periodoValidezTarjetaNoValida = periodoValidezTarjetaNoValida;
	}
	public String getPeriodoValidezTarjetaValida() {
		return periodoValidezTarjetaValida;
	}
	public void setPeriodoValidezTarjetaValida(String periodoValidezTarjetaValida) {
		this.periodoValidezTarjetaValida = periodoValidezTarjetaValida;
	}
	public byte[] getTarjetaItvDefinitiva() {
		return tarjetaItvDefinitiva;
	}
	public void setTarjetaItvDefinitiva(byte[] tarjetaItvDefinitiva) {
		this.tarjetaItvDefinitiva = tarjetaItvDefinitiva;
	}
	public byte[] getTarjetaItvNoValida() {
		return tarjetaItvNoValida;
	}
	public void setTarjetaItvNoValida(byte[] tarjetaItvNoValida) {
		this.tarjetaItvNoValida = tarjetaItvNoValida;
	}
	public byte[] getTarjetaItvTemporal() {
		return tarjetaItvTemporal;
	}
	public void setTarjetaItvTemporal(byte[] tarjetaItvTemporal) {
		this.tarjetaItvTemporal = tarjetaItvTemporal;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public Integer getMasaMaxTecnica() {
		return masaMaxTecnica;
	}
	public void setMasaMaxTecnica(Integer masaMaxTecnica) {
		this.masaMaxTecnica = masaMaxTecnica;
	}
	public Integer getMasaMaxima() {
		return masaMaxima;
	}
	public void setMasaMaxima(Integer masaMaxima) {
		this.masaMaxima = masaMaxima;
	}
	public Integer getMasaServicio() {
		return masaServicio;
	}
	public void setMasaServicio(Integer masaServicio) {
		this.masaServicio = masaServicio;
	}
	public Integer getPesoMaximo() {
		return pesoMaximo;
	}
	public void setPesoMaximo(Integer pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}
	public Integer getTara() {
		return tara;
	}
	public void setTara(Integer tara) {
		this.tara = tara;
	}
	public String getNumHomologacion() {
		return numHomologacion;
	}
	public void setNumHomologacion(String numHomologacion) {
		this.numHomologacion = numHomologacion;
	}
	public String getMixtas() {
		return mixtas;
	}
	public void setMixtas(String mixtas) {
		this.mixtas = mixtas;
	}
	public Integer getNormales() {
		return normales;
	}
	public void setNormales(Integer normales) {
		this.normales = normales;
	}
	public Integer getNumPlazasPie() {
		return numPlazasPie;
	}
	public void setNumPlazasPie(Integer numPlazasPie) {
		this.numPlazasPie = numPlazasPie;
	}
	public BigDecimal getCilindrada() {
		return cilindrada;
	}
	public void setCilindrada(BigDecimal cilindrada) {
		this.cilindrada = cilindrada;
	}
	public BigDecimal getPotenciaFiscal() {
		return potenciaFiscal;
	}
	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}
	public BigDecimal getPotenciaNetaMax() {
		return potenciaNetaMax;
	}
	public void setPotenciaNetaMax(BigDecimal potenciaNetaMax) {
		this.potenciaNetaMax = potenciaNetaMax;
	}
	public BigDecimal getRelPotenciaPeso() {
		return relPotenciaPeso;
	}
	public void setRelPotenciaPeso(BigDecimal relPotenciaPeso) {
		this.relPotenciaPeso = relPotenciaPeso;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	public String getTipoItv() {
		return tipoItv;
	}
	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
	}
	public String getVariante() {
		return variante;
	}
	public void setVariante(String variante) {
		this.variante = variante;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTipoTarjetaItv() {
		return tipoTarjetaItv;
	}
	public void setTipoTarjetaItv(String tipoTarjetaItv) {
		this.tipoTarjetaItv = tipoTarjetaItv;
	}
	public String getFabricanteBase() {
		return fabricanteBase;
	}
	public void setFabricanteBase(String fabricanteBase) {
		this.fabricanteBase = fabricanteBase;
	}
	public String getMasaServicioBase() {
		return masaServicioBase;
	}
	public void setMasaServicioBase(String masaServicioBase) {
		this.masaServicioBase = masaServicioBase;
	}
	public String getMarcaBase() {
		return marcaBase;
	}
	public void setMarcaBase(String marcaBase) {
		this.marcaBase = marcaBase;
	}
	public String getChomologacionBase() {
		return chomologacionBase;
	}
	public void setChomologacionBase(String chomologacionBase) {
		this.chomologacionBase = chomologacionBase;
	}
	public String getTipoBase() {
		return tipoBase;
	}
	public void setTipoBase(String tipoBase) {
		this.tipoBase = tipoBase;
	}
	public String getVarianteBase() {
		return varianteBase;
	}
	public void setVarianteBase(String varianteBase) {
		this.varianteBase = varianteBase;
	}
	public String getVersionBase() {
		return versionBase;
	}
	public void setVersionBase(String versionBase) {
		this.versionBase = versionBase;
	}
	public String getMomBase() {
		return momBase;
	}
	public void setMomBase(String momBase) {
		this.momBase = momBase;
	}
	public String getAutonomiaElectrica() {
		return autonomiaElectrica;
	}
	public void setAutonomiaElectrica(String autonomiaElectrica) {
		this.autonomiaElectrica = autonomiaElectrica;
	}
	public String getCategoriaElectrica() {
		return categoriaElectrica;
	}
	public void setCategoriaElectrica(String categoriaElectrica) {
		this.categoriaElectrica = categoriaElectrica;
	}
	public String getCodigoEco() {
		return codigoEco;
	}
	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}
	public String getCodos() {
		return codos;
	}
	public void setCodos(String codos) {
		this.codos = codos;
	}
	public BigDecimal getConsumo() {
		return consumo;
	}
	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}
	public String getEcoInnovacion() {
		return ecoInnovacion;
	}
	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}
	public String getNivelEmisiones() {
		return nivelEmisiones;
	}
	public void setNivelEmisiones(String nivelEmisiones) {
		this.nivelEmisiones = nivelEmisiones;
	}
	public String getPropulsion() {
		return propulsion;
	}
	public void setPropulsion(String propulsion) {
		this.propulsion = propulsion;
	}
	public String getReduccionEco() {
		return reduccionEco;
	}
	public void setReduccionEco(String reduccionEco) {
		this.reduccionEco = reduccionEco;
	}
	public String getTipoAlimentacion() {
		return tipoAlimentacion;
	}
	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}
	public List<DatosResponsableAtex5Dto> getListaArrendatariosDto() {
		return listaArrendatariosDto;
	}
	public void setListaArrendatariosDto(List<DatosResponsableAtex5Dto> listaArrendatariosDto) {
		this.listaArrendatariosDto = listaArrendatariosDto;
	}
	public List<DatosResponsableAtex5Dto> getListaConductoresHabitualesDto() {
		return listaConductoresHabitualesDto;
	}
	public void setListaConductoresHabitualesDto(List<DatosResponsableAtex5Dto> listaConductoresHabitualesDto) {
		this.listaConductoresHabitualesDto = listaConductoresHabitualesDto;
	}
	public List<DatosResponsableAtex5Dto> getListaPoseedoresDto() {
		return listaPoseedoresDto;
	}
	public void setListaPoseedoresDto(List<DatosResponsableAtex5Dto> listaPoseedoresDto) {
		this.listaPoseedoresDto = listaPoseedoresDto;
	}
	public List<DatosResponsableAtex5Dto> getListaTutoresDto() {
		return listaTutoresDto;
	}
	public void setListaTutoresDto(List<DatosResponsableAtex5Dto> listaTutoresDto) {
		this.listaTutoresDto = listaTutoresDto;
	}
	public List<DatosTramitesAtex5Dto> getListaBajasDto() {
		return listaBajasDto;
	}
	public void setListaBajasDto(List<DatosTramitesAtex5Dto> listaBajasDto) {
		this.listaBajasDto = listaBajasDto;
	}
	public List<DatosTramitesAtex5Dto> getListaDuplicadosDto() {
		return listaDuplicadosDto;
	}
	public void setListaDuplicadosDto(List<DatosTramitesAtex5Dto> listaDuplicadosDto) {
		this.listaDuplicadosDto = listaDuplicadosDto;
	}
	public List<DatosTramitesAtex5Dto> getListaProrrogasDto() {
		return listaProrrogasDto;
	}
	public void setListaProrrogasDto(List<DatosTramitesAtex5Dto> listaProrrogasDto) {
		this.listaProrrogasDto = listaProrrogasDto;
	}
	public List<DatosTramitesAtex5Dto> getListaRematriculacionesDto() {
		return listaRematriculacionesDto;
	}
	public void setListaRematriculacionesDto(List<DatosTramitesAtex5Dto> listaRematriculacionesDto) {
		this.listaRematriculacionesDto = listaRematriculacionesDto;
	}
	public List<DatosTramitesAtex5Dto> getListaTransferenciasDto() {
		return listaTransferenciasDto;
	}
	public void setListaTransferenciasDto(List<DatosTramitesAtex5Dto> listaTransferenciasDto) {
		this.listaTransferenciasDto = listaTransferenciasDto;
	}
	public List<DatosTramitesAtex5Dto> getListaMatriculacionTemporalDto() {
		return listaMatriculacionTemporalDto;
	}
	public void setListaMatriculacionTemporalDto(List<DatosTramitesAtex5Dto> listaMatriculacionTemporalDto) {
		this.listaMatriculacionTemporalDto = listaMatriculacionTemporalDto;
	}
	public List<DatosAdministrativosAtex5Dto> getListaAvisosDto() {
		return listaAvisosDto;
	}
	public void setListaAvisosDto(List<DatosAdministrativosAtex5Dto> listaAvisosDto) {
		this.listaAvisosDto = listaAvisosDto;
	}
	public List<DatosAdministrativosAtex5Dto> getListaDenegatoriasDto() {
		return listaDenegatoriasDto;
	}
	public void setListaDenegatoriasDto(List<DatosAdministrativosAtex5Dto> listaDenegatoriasDto) {
		this.listaDenegatoriasDto = listaDenegatoriasDto;
	}
	public List<DatosAdministrativosAtex5Dto> getListaEmbargosDto() {
		return listaEmbargosDto;
	}
	public void setListaEmbargosDto(List<DatosAdministrativosAtex5Dto> listaEmbargosDto) {
		this.listaEmbargosDto = listaEmbargosDto;
	}
	public List<DatosAdministrativosAtex5Dto> getListaImpagosDto() {
		return listaImpagosDto;
	}
	public void setListaImpagosDto(List<DatosAdministrativosAtex5Dto> listaImpagosDto) {
		this.listaImpagosDto = listaImpagosDto;
	}
	public DatosAdministrativosAtex5Dto getLimitacionesDisposicionDto() {
		return limitacionesDisposicionDto;
	}
	public void setLimitacionesDisposicionDto(DatosAdministrativosAtex5Dto limitacionesDisposicionDto) {
		this.limitacionesDisposicionDto = limitacionesDisposicionDto;
	}
	public List<DatosAdministrativosAtex5Dto> getListaPrecintosDto() {
		return listaPrecintosDto;
	}
	public void setListaPrecintosDto(List<DatosAdministrativosAtex5Dto> listaPrecintosDto) {
		this.listaPrecintosDto = listaPrecintosDto;
	}
	public List<DatosItvAtex5Dto> getListaItvsDto() {
		return listaItvsDto;
	}
	public void setListaItvsDto(List<DatosItvAtex5Dto> listaItvsDto) {
		this.listaItvsDto = listaItvsDto;
	}
	public DatosReformaAtex5Dto getReformaDto() {
		return reformaDto;
	}
	public void setReformaDto(DatosReformaAtex5Dto reformaDto) {
		this.reformaDto = reformaDto;
	}
	public DatosSeguridadAtex5Dto getDatosSeguridadDto() {
		return datosSeguridadDto;
	}
	public void setDatosSeguridadDto(DatosSeguridadAtex5Dto datosSeguridadDto) {
		this.datosSeguridadDto = datosSeguridadDto;
	}
	public DatosLibroTallerAtex5Dto getDatosLibroTallerDto() {
		return datosLibroTallerDto;
	}
	public void setDatosLibroTallerDto(DatosLibroTallerAtex5Dto datosLibroTallerDto) {
		this.datosLibroTallerDto = datosLibroTallerDto;
	}
	public List<DatosSeguroAtex5Dto> getListaSegurosDto() {
		return listaSegurosDto;
	}
	public void setListaSegurosDto(List<DatosSeguroAtex5Dto> listaSegurosDto) {
		this.listaSegurosDto = listaSegurosDto;
	}
	public Boolean getExistenDatosAdministrativos() {
		return existenDatosAdministrativos;
	}
	public void setExistenDatosAdministrativos(Boolean existenDatosAdministrativos) {
		this.existenDatosAdministrativos = existenDatosAdministrativos;
	}
	public Boolean getExistenDatosItvs() {
		return existenDatosItvs;
	}
	public void setExistenDatosItvs(Boolean existenDatosItvs) {
		this.existenDatosItvs = existenDatosItvs;
	}
	public Boolean getExistenDatosTramites() {
		return existenDatosTramites;
	}
	public void setExistenDatosTramites(Boolean existenDatosTramites) {
		this.existenDatosTramites = existenDatosTramites;
	}
	public Boolean getExistenDatosResponsables() {
		return existenDatosResponsables;
	}
	public void setExistenDatosResponsables(Boolean existenDatosResponsables) {
		this.existenDatosResponsables = existenDatosResponsables;
	}
	public Boolean getExistenDatosSeguridad() {
		return existenDatosSeguridad;
	}
	public void setExistenDatosSeguridad(Boolean existenDatosSeguridad) {
		this.existenDatosSeguridad = existenDatosSeguridad;
	}
	public Boolean getExistenDatosSeguros() {
		return existenDatosSeguros;
	}
	public void setExistenDatosSeguros(Boolean existenDatosSeguros) {
		this.existenDatosSeguros = existenDatosSeguros;
	}
	public Boolean getExistenDatosLibroTalles() {
		return existenDatosLibroTalles;
	}
	public void setExistenDatosLibroTalles(Boolean existenDatosLibroTalles) {
		this.existenDatosLibroTalles = existenDatosLibroTalles;
	}
}
