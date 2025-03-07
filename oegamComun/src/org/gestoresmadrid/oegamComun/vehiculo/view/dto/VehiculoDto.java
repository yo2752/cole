package org.gestoresmadrid.oegamComun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class VehiculoDto implements Serializable {

	private static final long serialVersionUID = 8485669351909284735L;

	private Short activo;

	private String anioFabrica;

	private BigDecimal autonomiaElectrica;

	private String bastidor;

	private Integer bastidorMatriculado;

	private String caracteristicas;

	private String carroceria;

	private String categoriaElectrica;

	private String cdMarca;

	private String cdModVeh;

	private Boolean checkFechaCaducidadITV;

	private String cilindrada;

	private String clasificacionItv;

	private String codigoEco;

	private String codigoMarca;

	private String codigoMarcaBase;

	private String codItv;

	private String codItvOriginal;

	private String conceptoItv;

	private BigDecimal consumo;

	private String co2;

	private Boolean diplomatico;

	private BigDecimal distanciaEjes;

	private String ecoInnovacion;

	private String estacionItv;

	private Boolean excesoPeso;

	private String fabricacion;

	private String fabricante;

	private String fabricanteBase;

	private Fecha fecDesde;

	private Fecha fechaItv;

	private Fecha fechaInspeccion;

	private Fecha fechaMatriculacion;

	private Fecha fechaPrimMatri;

	private Fecha fechaUltmModif;

	private Boolean fichaTecnicaRD750;

	private BigDecimal horasUso;

	private String carburante;

	private String color;

	private String criterioConstruccion;

	private String criterioUtilizacion;

	private DireccionDto direccion;

	private String idDirectivaCee;

	private String epigrafe;

	private String lugarAdquisicion;

	private String lugarPrimeraMatriculacion;

	private String motivoItv;

	private String idProcedencia;

	private ServicioTraficoDto servicioTrafico;

	private ServicioTraficoDto servicioTraficoAnterior;

	private String tipoTarjetaITV;

	private BigDecimal idVehiculo;

	private BigDecimal idVehiculoPrever;

	private Boolean importado;

	private BigDecimal kmUso;

	private Fecha limiteMatrTuris;

	private String matriAyuntamiento;

	private String matricula;

	private String matriculaOrigen;

	private String modelo;

	private BigDecimal mom;

	private BigDecimal momBase;

	private String mtmaItv;

	private BigDecimal nCilindros;

	private String numHomologacion;

	private String numHomologacionBase;

	private PersonaDto nifIntegrador;

	private String nive;

	private String nivelEmisiones;

	private BigDecimal numPlazasPie;

	private BigDecimal numRuedas;

	private String numSerie;

	private String numColegiado;

	private String paisFabricacion;

	private String paisImportacion;

	private String matriculaOrigExtr;

	private String pesoMma;

	private BigDecimal plazas;

	private BigDecimal potenciaFiscal;

	private BigDecimal potenciaNeta;

	private BigDecimal potenciaPeso;

	private String procedencia;

	private BigDecimal provinciaPrimeraMatricula;

	private BigDecimal reduccionEco;

	private Boolean subastado;

	private String tara;

	private String tipoAlimentacion;

	private String tipoBase;

	private String tipoIndustria;

	private String tipoItv;

	private String tipoVehiculo;

	private String tipoVehiculoDes;

	private String tipVehi;

	private String variante;

	private String varianteBase;

	private Boolean vehiculoAgricola;

	private Boolean vehiculoTransporte;

	private Boolean vehiUsado;

	private String version;

	private String versionBase;

	private BigDecimal viaAnterior;

	private BigDecimal viaPosterior;

	private Fecha fechaLecturaKm;

	private String doiResponsableKm;

	private Boolean revisado;

	// Importación

	private String marcaSinEditar;

	private VehiculoDto vehiculoPrever;

	private PersonaDto integrador;

	private Boolean esSiniestro;

	private BigDecimal velocidadMaxima;
	
	private BigDecimal longitud;
	
	private BigDecimal anchura;
	
	private BigDecimal altura;
	
	private BigDecimal numEjes;

//	private Boolean tieneCargaFinanciera;

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getAnioFabrica() {
		return anioFabrica;
	}

	public void setAnioFabrica(String anioFabrica) {
		this.anioFabrica = anioFabrica;
	}

	public BigDecimal getAutonomiaElectrica() {
		return autonomiaElectrica;
	}

	public void setAutonomiaElectrica(BigDecimal autonomiaElectrica) {
		this.autonomiaElectrica = autonomiaElectrica;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Integer getBastidorMatriculado() {
		return bastidorMatriculado;
	}

	public void setBastidorMatriculado(Integer bastidorMatriculado) {
		this.bastidorMatriculado = bastidorMatriculado;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCdMarca() {
		return cdMarca;
	}

	public void setCdMarca(String cdMarca) {
		this.cdMarca = cdMarca;
	}

	public String getCdModVeh() {
		return cdModVeh;
	}

	public void setCdModVeh(String cdModVeh) {
		this.cdModVeh = cdModVeh;
	}

	public Boolean getCheckFechaCaducidadITV() {
		return checkFechaCaducidadITV;
	}

	public void setCheckFechaCaducidadITV(Boolean checkFechaCaducidadITV) {
		this.checkFechaCaducidadITV = checkFechaCaducidadITV;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getClasificacionItv() {
		return clasificacionItv;
	}

	public void setClasificacionItv(String clasificacionItv) {
		this.clasificacionItv = clasificacionItv;
	}

	public String getCodigoEco() {
		return codigoEco;
	}

	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getCodigoMarcaBase() {
		return codigoMarcaBase;
	}

	public void setCodigoMarcaBase(String codigoMarcaBase) {
		this.codigoMarcaBase = codigoMarcaBase;
	}

	public String getCodItv() {
		return codItv;
	}

	public void setCodItv(String codItv) {
		this.codItv = codItv;
	}

	public String getCodItvOriginal() {
		return codItvOriginal;
	}

	public void setCodItvOriginal(String codItvOriginal) {
		this.codItvOriginal = codItvOriginal;
	}

	public String getConceptoItv() {
		return conceptoItv;
	}

	public void setConceptoItv(String conceptoItv) {
		this.conceptoItv = conceptoItv;
	}

	public BigDecimal getConsumo() {
		return consumo;
	}

	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public Boolean getDiplomatico() {
		return diplomatico;
	}

	public void setDiplomatico(Boolean diplomatico) {
		this.diplomatico = diplomatico;
	}

	public Boolean getExcesoPeso() {
		return excesoPeso;
	}

	public void setExcesoPeso(Boolean excesoPeso) {
		this.excesoPeso = excesoPeso;
	}

	public Boolean getVehiUsado() {
		return vehiUsado;
	}

	public void setVehiUsado(Boolean vehiUsado) {
		this.vehiUsado = vehiUsado;
	}

	public BigDecimal getDistanciaEjes() {
		return distanciaEjes;
	}

	public void setDistanciaEjes(BigDecimal distanciaEjes) {
		this.distanciaEjes = distanciaEjes;
	}

	public String getEcoInnovacion() {
		return ecoInnovacion;
	}

	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}

	public String getEstacionItv() {
		return estacionItv;
	}

	public void setEstacionItv(String estacionItv) {
		this.estacionItv = estacionItv;
	}

	public String getFabricacion() {
		return fabricacion;
	}

	public void setFabricacion(String fabricacion) {
		this.fabricacion = fabricacion;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getFabricanteBase() {
		return fabricanteBase;
	}

	public void setFabricanteBase(String fabricanteBase) {
		this.fabricanteBase = fabricanteBase;
	}

	public Fecha getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Fecha fecDesde) {
		this.fecDesde = fecDesde;
	}

	public Fecha getFechaItv() {
		return fechaItv;
	}

	public void setFechaItv(Fecha fechaItv) {
		this.fechaItv = fechaItv;
	}

	public Fecha getFechaInspeccion() {
		return fechaInspeccion;
	}

	public void setFechaInspeccion(Fecha fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	public Fecha getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(Fecha fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public Fecha getFechaPrimMatri() {
		return fechaPrimMatri;
	}

	public void setFechaPrimMatri(Fecha fechaPrimMatri) {
		this.fechaPrimMatri = fechaPrimMatri;
	}

	public Fecha getFechaUltmModif() {
		return fechaUltmModif;
	}

	public void setFechaUltmModif(Fecha fechaUltmModif) {
		this.fechaUltmModif = fechaUltmModif;
	}

	public Boolean getFichaTecnicaRD750() {
		return fichaTecnicaRD750;
	}

	public void setFichaTecnicaRD750(Boolean fichaTecnicaRD750) {
		this.fichaTecnicaRD750 = fichaTecnicaRD750;
	}

	public BigDecimal getHorasUso() {
		return horasUso;
	}

	public void setHorasUso(BigDecimal horasUso) {
		this.horasUso = horasUso;
	}

	public String getCarburante() {
		return carburante;
	}

	public void setCarburante(String carburante) {
		this.carburante = carburante;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCriterioConstruccion() {
		return criterioConstruccion;
	}

	public void setCriterioConstruccion(String criterioConstruccion) {
		this.criterioConstruccion = criterioConstruccion;
	}

	public String getCriterioUtilizacion() {
		return criterioUtilizacion;
	}

	public void setCriterioUtilizacion(String criterioUtilizacion) {
		this.criterioUtilizacion = criterioUtilizacion;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public String getIdDirectivaCee() {
		return idDirectivaCee;
	}

	public void setIdDirectivaCee(String idDirectivaCee) {
		this.idDirectivaCee = idDirectivaCee;
	}

	public String getEpigrafe() {
		return epigrafe;
	}

	public void setEpigrafe(String epigrafe) {
		this.epigrafe = epigrafe;
	}

	public String getLugarAdquisicion() {
		return lugarAdquisicion;
	}

	public void setLugarAdquisicion(String lugarAdquisicion) {
		this.lugarAdquisicion = lugarAdquisicion;
	}

	public String getLugarPrimeraMatriculacion() {
		return lugarPrimeraMatriculacion;
	}

	public void setLugarPrimeraMatriculacion(String lugarPrimeraMatriculacion) {
		this.lugarPrimeraMatriculacion = lugarPrimeraMatriculacion;
	}

	public String getMotivoItv() {
		return motivoItv;
	}

	public void setMotivoItv(String motivoItv) {
		this.motivoItv = motivoItv;
	}

	public String getIdProcedencia() {
		return idProcedencia;
	}

	public void setIdProcedencia(String idProcedencia) {
		this.idProcedencia = idProcedencia;
	}

	public String getTipoTarjetaITV() {
		return tipoTarjetaITV;
	}

	public void setTipoTarjetaITV(String tipoTarjetaITV) {
		this.tipoTarjetaITV = tipoTarjetaITV;
	}

	public BigDecimal getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(BigDecimal idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public BigDecimal getIdVehiculoPrever() {
		return idVehiculoPrever;
	}

	public void setIdVehiculoPrever(BigDecimal idVehiculoPrever) {
		this.idVehiculoPrever = idVehiculoPrever;
	}

	public Boolean getImportado() {
		return importado;
	}

	public void setImportado(Boolean importado) {
		this.importado = importado;
	}

	public BigDecimal getKmUso() {
		return kmUso;
	}

	public void setKmUso(BigDecimal kmUso) {
		this.kmUso = kmUso;
	}

	public Fecha getLimiteMatrTuris() {
		return limiteMatrTuris;
	}

	public void setLimiteMatrTuris(Fecha limiteMatrTuris) {
		this.limiteMatrTuris = limiteMatrTuris;
	}

	public String getMatriAyuntamiento() {
		return matriAyuntamiento;
	}

	public void setMatriAyuntamiento(String matriAyuntamiento) {
		this.matriAyuntamiento = matriAyuntamiento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMatriculaOrigen() {
		return matriculaOrigen;
	}

	public void setMatriculaOrigen(String matriculaOrigen) {
		this.matriculaOrigen = matriculaOrigen;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getMom() {
		return mom;
	}

	public void setMom(BigDecimal mom) {
		this.mom = mom;
	}

	public BigDecimal getMomBase() {
		return momBase;
	}

	public void setMomBase(BigDecimal momBase) {
		this.momBase = momBase;
	}

	public String getMtmaItv() {
		return mtmaItv;
	}

	public void setMtmaItv(String mtmaItv) {
		this.mtmaItv = mtmaItv;
	}

	public BigDecimal getnCilindros() {
		return nCilindros;
	}

	public void setnCilindros(BigDecimal nCilindros) {
		this.nCilindros = nCilindros;
	}

	public PersonaDto getNifIntegrador() {
		return nifIntegrador;
	}

	public void setNifIntegrador(PersonaDto nifIntegrador) {
		this.nifIntegrador = nifIntegrador;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getNivelEmisiones() {
		return nivelEmisiones;
	}

	public void setNivelEmisiones(String nivelEmisiones) {
		this.nivelEmisiones = nivelEmisiones;
	}

	public String getNumHomologacion() {
		return numHomologacion;
	}

	public void setNumHomologacion(String numHomologacion) {
		this.numHomologacion = numHomologacion;
	}

	public String getNumHomologacionBase() {
		return numHomologacionBase;
	}

	public void setNumHomologacionBase(String numHomologacionBase) {
		this.numHomologacionBase = numHomologacionBase;
	}

	public BigDecimal getNumPlazasPie() {
		return numPlazasPie;
	}

	public void setNumPlazasPie(BigDecimal numPlazasPie) {
		this.numPlazasPie = numPlazasPie;
	}

	public BigDecimal getNumRuedas() {
		return numRuedas;
	}

	public void setNumRuedas(BigDecimal numRuedas) {
		this.numRuedas = numRuedas;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getPaisFabricacion() {
		return paisFabricacion;
	}

	public void setPaisFabricacion(String paisFabricacion) {
		this.paisFabricacion = paisFabricacion;
	}

	public String getPaisImportacion() {
		return paisImportacion;
	}

	public void setPaisImportacion(String paisImportacion) {
		this.paisImportacion = paisImportacion;
	}

	public String getMatriculaOrigExtr() {
		return matriculaOrigExtr;
	}

	public void setMatriculaOrigExtr(String matriculaOrigExtr) {
		this.matriculaOrigExtr = matriculaOrigExtr;
	}

	public String getPesoMma() {
		return pesoMma;
	}

	public void setPesoMma(String pesoMma) {
		this.pesoMma = pesoMma;
	}

	public BigDecimal getPlazas() {
		return plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getPotenciaFiscal() {
		return potenciaFiscal;
	}

	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}

	public BigDecimal getPotenciaNeta() {
		return potenciaNeta;
	}

	public void setPotenciaNeta(BigDecimal potenciaNeta) {
		this.potenciaNeta = potenciaNeta;
	}

	public BigDecimal getPotenciaPeso() {
		return potenciaPeso;
	}

	public void setPotenciaPeso(BigDecimal potenciaPeso) {
		this.potenciaPeso = potenciaPeso;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public BigDecimal getProvinciaPrimeraMatricula() {
		return provinciaPrimeraMatricula;
	}

	public void setProvinciaPrimeraMatricula(BigDecimal provinciaPrimeraMatricula) {
		this.provinciaPrimeraMatricula = provinciaPrimeraMatricula;
	}

	public BigDecimal getReduccionEco() {
		return reduccionEco;
	}

	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}

	public Boolean getSubastado() {
		return subastado;
	}

	public void setSubastado(Boolean subastado) {
		this.subastado = subastado;
	}

	public String getTara() {
		return tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getTipoAlimentacion() {
		return tipoAlimentacion;
	}

	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}

	public String getTipoBase() {
		return tipoBase;
	}

	public void setTipoBase(String tipoBase) {
		this.tipoBase = tipoBase;
	}

	public String getTipoIndustria() {
		return tipoIndustria;
	}

	public void setTipoIndustria(String tipoIndustria) {
		this.tipoIndustria = tipoIndustria;
	}

	public String getTipoItv() {
		return tipoItv;
	}

	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getTipoVehiculoDes() {
		return tipoVehiculoDes;
	}

	public void setTipoVehiculoDes(String tipoVehiculoDes) {
		this.tipoVehiculoDes = tipoVehiculoDes;
	}

	public String getTipVehi() {
		return tipVehi;
	}

	public void setTipVehi(String tipVehi) {
		this.tipVehi = tipVehi;
	}

	public String getVariante() {
		return variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	public String getVarianteBase() {
		return varianteBase;
	}

	public void setVarianteBase(String varianteBase) {
		this.varianteBase = varianteBase;
	}

	public Boolean getVehiculoAgricola() {
		return vehiculoAgricola;
	}

	public void setVehiculoAgricola(Boolean vehiculoAgricola) {
		this.vehiculoAgricola = vehiculoAgricola;
	}

	public Boolean getVehiculoTransporte() {
		return vehiculoTransporte;
	}

	public void setVehiculoTransporte(Boolean vehiculoTransporte) {
		this.vehiculoTransporte = vehiculoTransporte;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionBase() {
		return versionBase;
	}

	public void setVersionBase(String versionBase) {
		this.versionBase = versionBase;
	}

	public BigDecimal getViaAnterior() {
		return viaAnterior;
	}

	public void setViaAnterior(BigDecimal viaAnterior) {
		this.viaAnterior = viaAnterior;
	}

	public BigDecimal getViaPosterior() {
		return viaPosterior;
	}

	public void setViaPosterior(BigDecimal viaPosterior) {
		this.viaPosterior = viaPosterior;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getCategoriaElectrica() {
		return categoriaElectrica;
	}

	public void setCategoriaElectrica(String categoriaElectrica) {
		this.categoriaElectrica = categoriaElectrica;
	}

	public ServicioTraficoDto getServicioTrafico() {
		return servicioTrafico;
	}

	public void setServicioTrafico(ServicioTraficoDto servicioTrafico) {
		this.servicioTrafico = servicioTrafico;
	}

	public ServicioTraficoDto getServicioTraficoAnterior() {
		return servicioTraficoAnterior;
	}

	public void setServicioTraficoAnterior(ServicioTraficoDto servicioTraficoAnterior) {
		this.servicioTraficoAnterior = servicioTraficoAnterior;
	}

	public Boolean getRevisado() {
		return revisado;
	}

	public void setRevisado(Boolean revisado) {
		this.revisado = revisado;
	}

	public String getMarcaSinEditar() {
		return marcaSinEditar;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public VehiculoDto getVehiculoPrever() {
		return vehiculoPrever;
	}

	public void setVehiculoPrever(VehiculoDto vehiculoPrever) {
		this.vehiculoPrever = vehiculoPrever;
	}

	public PersonaDto getIntegrador() {
		return integrador;
	}

	public void setIntegrador(PersonaDto integrador) {
		this.integrador = integrador;
	}

	public Fecha getFechaLecturaKm() {
		return fechaLecturaKm;
	}

	public void setFechaLecturaKm(Fecha fechaLecturaKm) {
		this.fechaLecturaKm = fechaLecturaKm;
	}

	public String getDoiResponsableKm() {
		return doiResponsableKm;
	}

	public void setDoiResponsableKm(String doiResponsableKm) {
		this.doiResponsableKm = doiResponsableKm;
	}

	public Boolean getEsSiniestro() {
		return esSiniestro;
	}

	public void setEsSiniestro(Boolean esSiniestro) {
		this.esSiniestro = esSiniestro;
	}

	public BigDecimal getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(BigDecimal velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public BigDecimal getAnchura() {
		return anchura;
	}

	public void setAnchura(BigDecimal anchura) {
		this.anchura = anchura;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getNumEjes() {
		return numEjes;
	}

	public void setNumEjes(BigDecimal numEjes) {
		this.numEjes = numEjes;
	}

//	public Boolean getTieneCargaFinanciera() {
//		return tieneCargaFinanciera;
//	}

//	public void setTieneCargaFinanciera(Boolean tieneCargaFinanciera) {
//		this.tieneCargaFinanciera = tieneCargaFinanciera;
//	}

}