package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;

import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import trafico.utiles.enumerados.TipoProcedencia;
import trafico.utiles.enumerados.TipoVehiculoCam;
import utilidades.estructuras.Fecha;

public class VehiculoBean implements Serializable {

	private static final long serialVersionUID = 8858178337715926269L;

	private BigDecimal idVehiculo;
	private String bastidor;
	private String numColegiado;
	private String matricula;
	private MarcaBean marcaBean;
	private MarcaBean marcaBaseBean;
	private TipoVehiculoCam tipVehi;
	private TipoProcedencia tipProc;
	private String cdMarca;
	private String cdModVeh;
	private Fecha fecDesde;
	private Fecha fechaMatriculacion;
	private String modelo;
	private Direccion direccionBean;
	private Provincia provinciaPrimeraMatricula;
	private ServicioTraficoBean servicioTraficoBean;
	private ServicioTraficoBean servicioTraficoAnteriorBean;
	private Color colorBean;
	private LugarAdquisicionBean lugarAdquisicionBean;
	private ClasificacionCriterioConstruccionBean criterioConstruccionBean;
	private ClasificacionCriterioUtilizacionBean criterioUtilizacionBean;
	private DirectivaCEEBean directivaCeeBean;
	private Boolean vehiculoAgricola;
	private Boolean vehiculoTransporte;
	private EpigrafeBean epigrafeBean;
	private TipoTarjetaItvBean tipoTarjetaItvBean;
	private MotivoBean motivoBean;
	private LugarPrimeraMatriculacionBean lugarPrimeraMatriculacionBean;
	private VehiculoPreverBean vehiculoPreverBean;
	private TipoVehiculoBean tipoVehiculoBean;
	private String diplomatico;
	private BigDecimal plazas;
	private String codItv;
	private BigDecimal potenciaFiscal;
	private BigDecimal potenciaNeta;
	private BigDecimal potenciaPeso;
	private String variante;
	private String varianteBase;
	private String cilindrada;
	private String co2;
	private String tara;
	private String pesoMma;
	private String excesoPeso;
	private String tipoIndustria;
	private Fecha fechaItv;
	private String clasificacionItv; //Corresponde con DatosITV.tipo
	private String nive;
	private String mtmaItv;
	private String version;
	private String versionBase;
	private String conceptoItv;
	private String estacionItv;
	private Fecha fechaInspeccion;
	private BigDecimal numPlazasPie;
	private String numHomologacion; // Corresponde con DatosITV.contraseniaHomologacion
	private String numHomologacionBase;
	private BigDecimal numRuedas;
	private String numSerie;
	private Persona integradorBean;
	private String vehiUsado;
	private String matriAyuntamiento;
	private Fecha limiteMatrTuris;
	private Fecha fechaPrimMatri;
	private String idLugarMatriculacion;
	private BigDecimal kmUso;
	private BigDecimal horasUso;
	private BigDecimal numCilindros;
	private String caracteristicas;
	private String anioFabrica;
	private String consentimiento;
	// 25-01-2023. DVV. Nuevos campos
	private String indTransmitente;
	private TipoMotorBean tipoMotor;
	private String tipoProcedencia;

	private String tipoItv;
	private String tipoItvBase;

	// MATE 2.5
	private Boolean importado;
	private Boolean subasta;
	private BigDecimal consumo;
	private BigDecimal distanciaEntreEjes;
	private BigDecimal viaAnterior;
	private BigDecimal viaPosterior;
	private BigDecimal reduccionEco;
	private BigDecimal mom;
	private BigDecimal momBase;
	private String nivelEmisiones;
	private String ecoInnovacion;
	private String fabricante;
	private String fabricanteBase;
	private String codigoEco;
	private CarroceriaBean carroceriaBean;
	private HomologacionBean homologacionBean; //Se corresponde con DatosITV.catHomologacion
	private CarburanteBean carburanteBean;
	private FabricacionBean fabricacionBean; //Se corresponde con DatosITV.procedencia
	private PaisFabricacionBean paisFabricacionBean;
	private PaisImportacionBean paisImportacionBean;
	private AlimentacionBean alimentacionBean; //Se corresponde con DatosITV.tipoAlimentacion
	private CategoriaElectricaBean categoriaElectricaBean;
	private BigDecimal autonomiaElectrica;
	private Boolean revisado;
	private String checkFechaCaducidadITV;

	// Nuevo campo MATW
	private String matriculaOrigen;
	private String procedencia;
	private Boolean fichaTecnicaRD750;
	private String matriculaOrigExtr;

	private String doiResponsableKm;
	private Fecha fechaLecturaKm;

	private BigDecimal bastidorMatriculado;
	private Boolean esSiniestro;
//	private Boolean tieneCargaFinanciera;

	/* @author Carlos García
	 * Incidencia: 0002003: Asociación entre el vehículo y el trámite tráfico
	 */
	private VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean;

	public VehiculoBean(boolean inicializar) {
		marcaBean = new MarcaBean();
		marcaBaseBean = new MarcaBean();
		direccionBean = new Direccion (true);
		servicioTraficoBean = new ServicioTraficoBean (true);
		servicioTraficoAnteriorBean = new ServicioTraficoBean(true);
		lugarPrimeraMatriculacionBean = new LugarPrimeraMatriculacionBean();
		lugarAdquisicionBean = new LugarAdquisicionBean ();
		criterioConstruccionBean = new ClasificacionCriterioConstruccionBean();
		criterioUtilizacionBean = new ClasificacionCriterioUtilizacionBean();
		directivaCeeBean = new DirectivaCEEBean();
		epigrafeBean = new EpigrafeBean();
		tipoTarjetaItvBean = new TipoTarjetaItvBean();
		motivoBean = new MotivoBean ();
		vehiculoPreverBean = new VehiculoPreverBean();
		tipoVehiculoBean = new TipoVehiculoBean();
		integradorBean=new Persona(true);
		fecDesde = new Fecha();
		fechaMatriculacion = new Fecha();
		fechaInspeccion = new Fecha();
		fechaItv = new Fecha();
		fechaPrimMatri = new Fecha();
		limiteMatrTuris = new Fecha();
		carburanteBean = new CarburanteBean();
		fabricacionBean = new FabricacionBean();
		paisFabricacionBean = new PaisFabricacionBean();
		carroceriaBean = new CarroceriaBean();
		alimentacionBean = new AlimentacionBean();
		homologacionBean = new HomologacionBean();
		vehiculoTramiteTraficoBean = new VehiculoTramiteTraficoBean();
		paisImportacionBean = new PaisImportacionBean();
		categoriaElectricaBean = new CategoriaElectricaBean();
		fechaLecturaKm = new Fecha();
	}

	public VehiculoBean() {
		marcaBean = new MarcaBean();
		marcaBaseBean = new MarcaBean();
		direccionBean = new Direccion(true);
		servicioTraficoBean = new ServicioTraficoBean(true);
		servicioTraficoAnteriorBean = new ServicioTraficoBean(true);
		lugarPrimeraMatriculacionBean = new LugarPrimeraMatriculacionBean();
		lugarAdquisicionBean = new LugarAdquisicionBean();
		criterioConstruccionBean = new ClasificacionCriterioConstruccionBean();
		criterioUtilizacionBean = new ClasificacionCriterioUtilizacionBean();
		directivaCeeBean = new DirectivaCEEBean();
		epigrafeBean = new EpigrafeBean ();
		tipoTarjetaItvBean = new TipoTarjetaItvBean();
		motivoBean = new MotivoBean ();
		vehiculoPreverBean = new VehiculoPreverBean();
		tipoVehiculoBean = new TipoVehiculoBean();
		integradorBean = new Persona(true);
		fecDesde = new Fecha();
		fechaMatriculacion = new Fecha();
		fechaInspeccion = new Fecha();
		fechaItv = new Fecha();
		fechaPrimMatri = new Fecha();
		limiteMatrTuris = new Fecha();
		carburanteBean = new CarburanteBean();
		fabricacionBean = new FabricacionBean();
		paisFabricacionBean = new PaisFabricacionBean();
		carroceriaBean = new CarroceriaBean();
		alimentacionBean = new AlimentacionBean();
		homologacionBean = new HomologacionBean();
		vehiculoTramiteTraficoBean = new VehiculoTramiteTraficoBean();
		paisImportacionBean = new PaisImportacionBean();
	}

	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
	public BigDecimal getPotenciaPeso() {
		return potenciaPeso;
	}
	public void setPotenciaPeso(BigDecimal potenciaPeso) {
		this.potenciaPeso = potenciaPeso;
	}
	public String getMtmaItv() {
		return mtmaItv;
	}
	public void setMtmaItv(String mtmaItv) {
		this.mtmaItv = mtmaItv;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public BigDecimal getNumPlazasPie() {
		return numPlazasPie;
	}
	public void setNumPlazasPie(BigDecimal numPlazasPie) {
		this.numPlazasPie = numPlazasPie;
	}
	public String getNumHomologacion() {
		return numHomologacion;
	}
	public void setNumHomologacion(String numHomologacion) {
		this.numHomologacion = numHomologacion;
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
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Boolean isVehiculoAgricola() {
		return vehiculoAgricola;
	}
	public void setVehiculoAgricola(Boolean vehiculoAgricola) {
		this.vehiculoAgricola = vehiculoAgricola;
	}
	public Boolean isVehiculoTransporte() {
		return vehiculoTransporte;
	}
	public void setVehiculoTransporte(Boolean vehiculoTransporte) {
		this.vehiculoTransporte = vehiculoTransporte;
	}
	public ServicioTraficoBean getServicioTraficoAnteriorBean() {
		return servicioTraficoAnteriorBean;
	}
	public void setServicioTraficoAnteriorBean(
			ServicioTraficoBean servicioTraficoAnteriorBean) {
		this.servicioTraficoAnteriorBean = servicioTraficoAnteriorBean;
	}
	public TipoVehiculoBean getTipoVehiculoBean() {
		return tipoVehiculoBean;
	}
	public void setTipoVehiculoBean(TipoVehiculoBean tipoVehiculoBean) {
		this.tipoVehiculoBean = tipoVehiculoBean;
	}
	public MotivoBean getMotivoBean() {
		return motivoBean;
	}
	public void setMotivoBean(MotivoBean motivoBean) {
		this.motivoBean = motivoBean;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public MarcaBean getMarcaBean() {
		return marcaBean;
	}
	public void setMarcaBean(MarcaBean marcaBean) {
		this.marcaBean = marcaBean;
	}
	public Boolean getVehiculoAgricola() {
		return vehiculoAgricola;
	}
	public String getVariante() {
		return variante;
	}
	public void setVariante(String variante) {
		this.variante = variante;
	}
	public Boolean getVehiculoTransporte() {
		return vehiculoTransporte;
	}
	public TipoVehiculoCam getTipVehi() {
		return tipVehi;
	}
	public void setTipVehi(String tipVehi) {
		this.tipVehi = TipoVehiculoCam.convertir(tipVehi);
	}

	public TipoProcedencia getTipProc() {
		return tipProc;
	}
	public void setTipProc(String tipProc) {
		this.tipProc = TipoProcedencia.convertir(tipProc);
	}

	public String getTipoProcedencia() {
		return tipoProcedencia;
	}
	public void setTipoProcedencia(String tipoProcedencia) {
		this.tipoProcedencia = tipoProcedencia;
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
	public String getDiplomatico() {
		return diplomatico;
	}
	public void setDiplomatico(String diplomatico) {
		this.diplomatico = diplomatico;
	}
	public String getCodItv() {
		return codItv;
	}
	public void setCodItv(String codItv) {
		this.codItv = codItv;
	}
	public String getCilindrada() {
		return cilindrada;
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
	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}
	public String getCo2() {
		return co2;
	}
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	public String getTara() {
		return tara;
	}
	public void setTara(String tara) {
		this.tara = tara;
	}
	public String getPesoMma() {
		return pesoMma;
	}
	public void setPesoMma(String pesoMma) {
		this.pesoMma = pesoMma;
	}
	public String getExcesoPeso() {
		return excesoPeso;
	}
	public void setExcesoPeso(String excesoPeso) {
		this.excesoPeso = excesoPeso;
	}
	public String getTipoIndustria() {
		return tipoIndustria;
	}
	public void setTipoIndustria(String tipoIndustria) {
		this.tipoIndustria = tipoIndustria;
	}
	public String getClasificacionItv() {
		return clasificacionItv;
	}
	public void setClasificacionItv(String clasificacionItv) {
		this.clasificacionItv = clasificacionItv;
	}
	public String getConceptoItv() {
		return conceptoItv;
	}
	public void setConceptoItv(String conceptoItv) {
		this.conceptoItv = conceptoItv;
	}
	public String getEstacionItv() {
		return estacionItv;
	}
	public void setEstacionItv(String estacionItv) {
		this.estacionItv = estacionItv;
	}
	public String getVehiUsado() {
		return vehiUsado;
	}
	public void setVehiUsado(String vehiUsado) {
		this.vehiUsado = vehiUsado;
	}
	public String getMatriAyuntamiento() {
		return matriAyuntamiento;
	}
	public void setMatriAyuntamiento(String matriAyuntamiento) {
		this.matriAyuntamiento = matriAyuntamiento;
	}
	public String getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public String getAnioFabrica() {
		return anioFabrica;
	}
	public void setAnioFabrica(String anioFabrica) {
		this.anioFabrica = anioFabrica;
	}
	public Direccion getDireccionBean() {
		return direccionBean;
	}
	public void setDireccionBean(Direccion direccionBean) {
		this.direccionBean = direccionBean;
	}
	public ServicioTraficoBean getServicioTraficoBean() {
		return servicioTraficoBean;
	}
	public void setServicioTraficoBean(ServicioTraficoBean servicioTraficoBean) {
		this.servicioTraficoBean = servicioTraficoBean;
	}
	public Color getColorBean() {
		return colorBean;
	}
	public void setColorBean(String colorBean) {
		this.colorBean= Color.convertir(colorBean);
	}
	public LugarPrimeraMatriculacionBean getLugarPrimeraMatriculacionBean() {
		return lugarPrimeraMatriculacionBean;
	}
	public void setLugarPrimeraMatriculacionBean(
			LugarPrimeraMatriculacionBean lugarPrimeraMatriculacionBean) {
		this.lugarPrimeraMatriculacionBean = lugarPrimeraMatriculacionBean;
	}
	public FabricacionBean getFabricacionBean() {
		return fabricacionBean;
	}
	public void setFabricacionBean(FabricacionBean fabricacionBean) {
		this.fabricacionBean = fabricacionBean;
	}
	public LugarAdquisicionBean getLugarAdquisicionBean() {
		return lugarAdquisicionBean;
	}
	public void setLugarAdquisicionBean(LugarAdquisicionBean lugarAdquisicionBean) {
		this.lugarAdquisicionBean = lugarAdquisicionBean;
	}
	public DirectivaCEEBean getDirectivaCeeBean() {
		return directivaCeeBean;
	}
	public void setDirectivaCeeBean(DirectivaCEEBean directivaCeeBean) {
		this.directivaCeeBean = directivaCeeBean;
	}
	public EpigrafeBean getEpigrafeBean() {
		return epigrafeBean;
	}
	public void setEpigrafeBean(EpigrafeBean epigrafeBean) {
		this.epigrafeBean = epigrafeBean;
	}
	public TipoTarjetaItvBean getTipoTarjetaItvBean() {
		return tipoTarjetaItvBean;
	}
	public void setTipoTarjetaItvBean(TipoTarjetaItvBean tipoTarjetaItvBean) {
		this.tipoTarjetaItvBean = tipoTarjetaItvBean;
	}
	public MotivoBean getMotivoItv() {
		return motivoBean;
	}
	public void setMotivoItv(MotivoBean motivoItv) {
		this.motivoBean = motivoItv;
	}
	public VehiculoPreverBean getVehiculoPreverBean() {
		return vehiculoPreverBean;
	}
	public void setVehiculoPreverBean(VehiculoPreverBean vehiculoPreverBean) {
		this.vehiculoPreverBean = vehiculoPreverBean;
	}
	public Fecha getFecDesde() {
		return fecDesde;
	}
	public void setFecDesde(Fecha fecDesde) {
		this.fecDesde = fecDesde;
	}
	public Fecha getFechaMatriculacion() {
		return fechaMatriculacion;
	}
	public void setFechaMatriculacion(Fecha fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
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
	public Fecha getLimiteMatrTuris() {
		return limiteMatrTuris;
	}
	public void setLimiteMatrTuris(Fecha limiteMatrTuris) {
		this.limiteMatrTuris = limiteMatrTuris;
	}
	public Fecha getFechaPrimMatri() {
		return fechaPrimMatri;
	}
	public void setFechaPrimMatri(Fecha fechaPrimMatri) {
		this.fechaPrimMatri = fechaPrimMatri;
	}
	public ClasificacionCriterioConstruccionBean getCriterioConstruccionBean() {
		return criterioConstruccionBean;
	}
	public void setCriterioConstruccionBean(
			ClasificacionCriterioConstruccionBean criterioConstruccionBean) {
		this.criterioConstruccionBean = criterioConstruccionBean;
	}
	public ClasificacionCriterioUtilizacionBean getCriterioUtilizacionBean() {
		return criterioUtilizacionBean;
	}
	public void setCriterioUtilizacionBean(
			ClasificacionCriterioUtilizacionBean criterioUtilizacionBean) {
		this.criterioUtilizacionBean = criterioUtilizacionBean;
	}

	public String getConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(String consentimiento) {
		this.consentimiento = consentimiento;
	}

	public String getIndTransmitente() {
		return indTransmitente;
	}
	public void setIndTransmitente(String indTransmitente) {
		this.indTransmitente = indTransmitente;
	}

	public TipoMotorBean getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(TipoMotorBean tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public BigDecimal getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(BigDecimal idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	public BigDecimal getKmUso() {
		return kmUso;
	}
	public void setKmUso(BigDecimal kmUso) {
		this.kmUso = kmUso;
	}
	public BigDecimal getHorasUso() {
		return horasUso;
	}
	public void setHorasUso(BigDecimal horasUso) {
		this.horasUso = horasUso;
	}
	public BigDecimal getNumCilindros() {
		return numCilindros;
	}
	public void setNumCilindros(BigDecimal numCilindros) {
		this.numCilindros = numCilindros;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public BigDecimal getPlazas() {
		return plazas;
	}
	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}
	public Persona getIntegradorBean() {
		return integradorBean;
	}
	public void setIntegradorBean(Persona integradorBean) {
		this.integradorBean = integradorBean;
	}
	public String getTipoItv() {
		return tipoItv;
	}
	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
	}
	public Boolean getImportado() {
		return importado;
	}
	public void setImportado(Boolean importado) {
		this.importado = importado;
	}
	public Boolean getSubasta() {
		return subasta;
	}
	public void setSubasta(Boolean subasta) {
		this.subasta = subasta;
	}

	public PaisFabricacionBean getPaisFabricacionBean() {
		return paisFabricacionBean;
	}

	public void setPaisFabricacionBean(PaisFabricacionBean paisFabricacionBean) {
		this.paisFabricacionBean = paisFabricacionBean;
	}

	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public CarroceriaBean getCarroceriaBean(){
		return carroceriaBean;
	}
	public void setCarroceriaBean(CarroceriaBean carroceriaBean){
		this.carroceriaBean = carroceriaBean;
	}
	public BigDecimal getConsumo() {
		return consumo;
	}
	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}
	public BigDecimal getDistanciaEntreEjes() {
		return distanciaEntreEjes;
	}
	public void setDistanciaEntreEjes(BigDecimal distanciaEntreEjes) {
		this.distanciaEntreEjes = distanciaEntreEjes;
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
	public AlimentacionBean getAlimentacionBean() {
		return alimentacionBean;
	}
	public void setAlimentacionBean(AlimentacionBean alimentacionBean) {
		this.alimentacionBean = alimentacionBean;
	}
	public String getNivelEmisiones() {
		return nivelEmisiones;
	}
	public void setNivelEmisiones(String nivelEmisiones) {
		this.nivelEmisiones = nivelEmisiones;
	}
	public String getEcoInnovacion() {
		return ecoInnovacion;
	}
	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}
	public BigDecimal getReduccionEco() {
		return reduccionEco;
	}
	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}
	public String getCodigoEco() {
		return codigoEco;
	}
	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}
	public HomologacionBean getHomologacionBean() {
		return homologacionBean;
	}
	public void setHomologacionBean(HomologacionBean homologacionBean) {
		this.homologacionBean = homologacionBean;
	}
	public String getIdLugarMatriculacion() {
		return idLugarMatriculacion;
	}
	public void setIdLugarMatriculacion(String idLugarMatriculacion) {
		this.idLugarMatriculacion = idLugarMatriculacion;
	}
	public void setCarburanteBean(CarburanteBean carburanteBean) {
		this.carburanteBean = carburanteBean;
	}
	public CarburanteBean getCarburanteBean() {
		return carburanteBean;
	}
	public BigDecimal getMom() {
		return mom;
	}
	public void setMom(BigDecimal mom) {
		this.mom = mom;
	}
	public Boolean getRevisado() {
		return revisado;
	}
	public void setRevisado(Boolean revisado) {
		this.revisado = revisado;
	}
	public VehiculoTramiteTraficoBean getVehiculoTramiteTraficoBean() {
		return vehiculoTramiteTraficoBean;
	}
	public void setVehiculoTramiteTraficoBean(
			VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean) {
		this.vehiculoTramiteTraficoBean = vehiculoTramiteTraficoBean;
	}
	public String getCheckFechaCaducidadITV() {
		return checkFechaCaducidadITV;
	}
	public void setCheckFechaCaducidadITV(String checkFechaCaducidadITV) {
		this.checkFechaCaducidadITV = checkFechaCaducidadITV;
	}

	public void setProvinciaPrimeraMatricula(Provincia provinciaPrimeraMatricula) {
		this.provinciaPrimeraMatricula = provinciaPrimeraMatricula;
	}

	public Provincia getProvinciaPrimeraMatricula() {
		return provinciaPrimeraMatricula;
	}

	public String getMatriculaOrigen() {
		return matriculaOrigen;
	}

	public void setMatriculaOrigen(String matriculaOrigen) {
		this.matriculaOrigen = matriculaOrigen;
	}

	public PaisImportacionBean getPaisImportacionBean() {
		return paisImportacionBean;
	}

	public void setPaisImportacionBean(PaisImportacionBean paisImportacionBean) {
		this.paisImportacionBean = paisImportacionBean;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public Boolean getFichaTecnicaRD750() {
		return fichaTecnicaRD750;
	}

	public void setFichaTecnicaRD750(Boolean fichaTecnicaRD750) {
		this.fichaTecnicaRD750 = fichaTecnicaRD750;
	}

	public BigDecimal getBastidorMatriculado() {
		return bastidorMatriculado;
	}

	public void setBastidorMatriculado(BigDecimal bastidorMatriculado) {
		this.bastidorMatriculado = bastidorMatriculado;
	}

	public MarcaBean getMarcaBaseBean() {
		return marcaBaseBean;
	}

	public void setMarcaBaseBean(MarcaBean marcaBaseBean) {
		this.marcaBaseBean = marcaBaseBean;
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

	public String getTipoItvBase() {
		return tipoItvBase;
	}

	public void setTipoItvBase(String tipoItvBase) {
		this.tipoItvBase = tipoItvBase;
	}

	public BigDecimal getMomBase() {
		return momBase;
	}

	public void setMomBase(BigDecimal momBase) {
		this.momBase = momBase;
	}

	public String getFabricanteBase() {
		return fabricanteBase;
	}

	public void setFabricanteBase(String fabricanteBase) {
		this.fabricanteBase = fabricanteBase;
	}

	public String getNumHomologacionBase() {
		return numHomologacionBase;
	}

	public void setNumHomologacionBase(String numHomologacionBase) {
		this.numHomologacionBase = numHomologacionBase;
	}

	public CategoriaElectricaBean getCategoriaElectricaBean() {
		return categoriaElectricaBean;
	}

	public void setCategoriaElectricaBean(
			CategoriaElectricaBean categoriaElectricaBean) {
		this.categoriaElectricaBean = categoriaElectricaBean;
	}

	public BigDecimal getAutonomiaElectrica() {
		return autonomiaElectrica;
	}

	public void setAutonomiaElectrica(BigDecimal autonomiaElectrica) {
		this.autonomiaElectrica = autonomiaElectrica;
	}

	public String getMatriculaOrigExtr() {
		return matriculaOrigExtr;
	}

	public void setMatriculaOrigExtr(String matriculaOrigExtr) {
		this.matriculaOrigExtr = matriculaOrigExtr;
	}

	public String getDoiResponsableKm() {
		return doiResponsableKm;
	}

	public void setDoiResponsableKm(String doiResponsableKm) {
		this.doiResponsableKm = doiResponsableKm;
	}

	public Fecha getFechaLecturaKm() {
		return fechaLecturaKm;
	}

	public void setFechaLecturaKm(Fecha fechaLecturaKm) {
		this.fechaLecturaKm = fechaLecturaKm;
	}

	public Boolean getEsSiniestro() {
		return esSiniestro;
	}

	public void setEsSiniestro(Boolean esSiniestro) {
		this.esSiniestro = esSiniestro;
	}

//	public Boolean getTieneCargaFinanciera() {
//		return tieneCargaFinanciera;
//	}

//	public void setTieneCargaFinanciera(Boolean tieneCargaFinanciera) {
//		this.tieneCargaFinanciera = tieneCargaFinanciera;
//	}

}