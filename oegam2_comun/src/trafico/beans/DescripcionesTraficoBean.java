package trafico.beans;

import java.math.BigDecimal;

/**
 * Clase para el bean de salida de datos de descripciones de tráfico.
 * @author juan.gomez
 *	ROWID_TRAMITE IN OUT UROWID,
    -- VALORES PROPIOS DEL TRAMITE GENERAL
    P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE,
    P_CODIGO_TASA OUT TRAMITE_TRAFICO.CODIGO_TASA%TYPE,
    P_TIPO_TASA OUT TASA.TIPO_TASA%TYPE,
    P_JEFATURA_PROVINCIAL OUT TRAMITE_TRAFICO.JEFATURA_PROVINCIAL%TYPE,
    P_JEFATURA_PROV OUT TRAMITE_TRAFICO.JEFATURA_PROVINCIAL%TYPE,
    P_DESC_JEFATURA OUT JEFATURA_TRAFICO.DESCRIPCION%TYPE,
    P_CIF OUT CONTRATO.CIF%TYPE,
    P_RAZON_SOCIAL OUT CONTRATO.RAZON_SOCIAL%TYPE,
    -- VALORES PROPIOS DEL VEHICULO 
    P_ID_VEHICULO OUT TRAMITE_TRAFICO.ID_VEHICULO%TYPE,
    P_CODIGO_MARCA OUT VEHICULO.CODIGO_MARCA%TYPE,
    P_MARCA OUT MARCA_DGT.MARCA%TYPE,
    P_MARCA_SIN_EDITAR OUT MARCA_DGT.MARCA_SIN_EDITAR%TYPE,
    P_TIPO_VEHICULO OUT VEHICULO.TIPO_VEHICULO%TYPE,
    P_DESC_TIPO_VEHI OUT TIPO_VEHICULO.DESCRIPCION%TYPE,
    P_ID_SERVICIO OUT VEHICULO.ID_SERVICIO%TYPE,
    P_DESC_SERVICIO OUT SERVICIO_TRAFICO.DESCRIPCION%TYPE,
    P_ID_CRITERIO_CONSTRUCCION OUT VEHICULO.ID_CRITERIO_CONSTRUCCION%TYPE,
    P_DESC_CRITER_CONSTRUCCION OUT CRITERIO_CONSTRUCCION.DESCRIPCION%TYPE,
    P_ID_CRITERIO_UTILIZACION OUT VEHICULO.ID_CRITERIO_UTILIZACION%TYPE,                      
    P_DESC_CRITER_UTILIZACION OUT CRITERIO_UTILIZACION.DESCRIPCION%TYPE,
    P_ID_DIRECTIVA_CEE OUT VEHICULO.ID_DIRECTIVA_CEE%TYPE,
    P_DESC_DIRECTIVA_CEE OUT DIRECTIVA_CEE.DESCRIPCION%TYPE,
    P_ID_MOTIVO_ITV OUT VEHICULO.ID_MOTIVO_ITV%TYPE,
    P_DESC_MOTIVO_ITV OUT MOTIVO_ITV.DESCRIPCION%TYPE,
    P_ESTACION_ITV OUT VEHICULO.ESTACION_ITV%TYPE,
    P_PROV_ESTACION_ITV OUT ESTACION_ITV.PROVINCIA%TYPE,
    P_MUNI_ESTACION_ITV OUT ESTACION_ITV.MUNICIPIO%TYPE,
    -- VALORES PROPIOS DE LA TRANSMISION
    P_CODIGO_TASA_INF OUT TRAMITE_TRAF_TRAN.CODIGO_TASA_INF%TYPE,
    P_TIPO_TASA_INF OUT TASA.TIPO_TASA%TYPE,
    P_CODIGO_TASA_CAMSER OUT TRAMITE_TRAF_TRAN.CODIGO_TASA_CAMSER%TYPE,
    P_TIPO_TASA_CAMSER OUT TASA.TIPO_TASA%TYPE,
 */

public class DescripcionesTraficoBean {

	// tabla Tramite_trafico

	private BigDecimal numExpediente;

	private String codigoTasa;
	private String tipoTasa;
	private String jefaturaProvincial;
	private String jefaturaProvincia;
	private String descripcionJefaturaProvincial;
	private String descripcionJefaturaSucursal;
	private String cif;
	private String razonSocial;
	private BigDecimal idVehiculo;
	private BigDecimal codigoMarca;
	private String marca;
	private String marcaSinEditar;
	private String tipoVehiculo;
	private String descripcionTipoVehiculo;
	private String idServicio;
	private String descripcionServicio;
	private String idCriterioConstruccion;
	private String descripcionCriterioConstruccion;
	private String idCriterioUtilizacion;
	private String descripcionCriterioUtilizacion;
	private String idDirectivaCEE;
	private String descripcionDirectivaCEE;
	private String idMotivoITV;
	private String descripcionMotivoITV;
	private String estacionITV;
	private String provinciaEstacionITV;
	private String municipioEstacionITV;
	private String otrosSinCodig;
	private String nive;

	private String codigoTasaInf;
	private String tipoTasaInf;
	private String codigoTasaCamser;
	private String tipoTasaCamser;

	private BigDecimal codigoMarcaBase;
	private String marcaBase;
	private String marcaSinEditarBase;

	public DescripcionesTraficoBean() {
		super();
	}

	public String getMarcaSinEditar() {
		return marcaSinEditar;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
	public String getJefaturaProvincia() {
		return jefaturaProvincia;
	}
	public void setJefaturaProvincia(String jefaturaProvincia) {
		this.jefaturaProvincia = jefaturaProvincia;
	}
	
	public String getDescripcionJefaturaProvincial() {
		return descripcionJefaturaProvincial;
	}

	public void setDescripcionJefaturaProvincial(
			String descripcionJefaturaProvincial) {
		this.descripcionJefaturaProvincial = descripcionJefaturaProvincial;
	}

	public String getDescripcionJefaturaSucursal() {
		return descripcionJefaturaSucursal;
	}

	public void setDescripcionJefaturaSucursal(String descripcionJefaturaSucursal) {
		this.descripcionJefaturaSucursal = descripcionJefaturaSucursal;
	}

	public BigDecimal getIdVehiculo() {
		return idVehiculo;
	}
	public void setIdVehiculo(BigDecimal idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	public BigDecimal getCodigoMarca() {
		return codigoMarca;
	}
	public void setCodigoMarca(BigDecimal codigoMarca) {
		this.codigoMarca = codigoMarca;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public String getDescripcionTipoVehiculo() {
		return descripcionTipoVehiculo;
	}
	public void setDescripcionTipoVehiculo(String descripcionTipoVehiculo) {
		this.descripcionTipoVehiculo = descripcionTipoVehiculo;
	}
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getDescripcionServicio() {
		return descripcionServicio;
	}
	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}
	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}
	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}
	public String getDescripcionCriterioConstruccion() {
		return descripcionCriterioConstruccion;
	}
	public void setDescripcionCriterioConstruccion(
			String descripcionCriterioConstruccion) {
		this.descripcionCriterioConstruccion = descripcionCriterioConstruccion;
	}
	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}
	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}
	public String getDescripcionCriterioUtilizacion() {
		return descripcionCriterioUtilizacion;
	}
	public void setDescripcionCriterioUtilizacion(
			String descripcionCriterioUtilizacion) {
		this.descripcionCriterioUtilizacion = descripcionCriterioUtilizacion;
	}
	public String getIdDirectivaCEE() {
		return idDirectivaCEE;
	}
	public void setIdDirectivaCEE(String idDirectivaCEE) {
		this.idDirectivaCEE = idDirectivaCEE;
	}
	public String getDescripcionDirectivaCEE() {
		return descripcionDirectivaCEE;
	}
	public void setDescripcionDirectivaCEE(String descripcionDirectivaCEE) {
		this.descripcionDirectivaCEE = descripcionDirectivaCEE;
	}
	public String getIdMotivoITV() {
		return idMotivoITV;
	}
	public void setIdMotivoITV(String idMotivoITV) {
		this.idMotivoITV = idMotivoITV;
	}
	public String getDescripcionMotivoITV() {
		return descripcionMotivoITV;
	}
	public void setDescripcionMotivoITV(String descripcionMotivoITV) {
		this.descripcionMotivoITV = descripcionMotivoITV;
	}
	public String getEstacionITV() {
		return estacionITV;
	}
	public void setEstacionITV(String estacionITV) {
		this.estacionITV = estacionITV;
	}
	public String getProvinciaEstacionITV() {
		return provinciaEstacionITV;
	}
	public void setProvinciaEstacionITV(String provinciaEstacionITV) {
		this.provinciaEstacionITV = provinciaEstacionITV;
	}
	public String getMunicipioEstacionITV() {
		return municipioEstacionITV;
	}
	public void setMunicipioEstacionITV(String municipioEstacionITV) {
		this.municipioEstacionITV = municipioEstacionITV;
	}
	public String getCodigoTasaInf() {
		return codigoTasaInf;
	}
	public void setCodigoTasaInf(String codigoTasaInf) {
		this.codigoTasaInf = codigoTasaInf;
	}
	public String getTipoTasaInf() {
		return tipoTasaInf;
	}
	public void setTipoTasaInf(String tipoTasaInf) {
		this.tipoTasaInf = tipoTasaInf;
	}
	public String getCodigoTasaCamser() {
		return codigoTasaCamser;
	}
	public void setCodigoTasaCamser(String codigoTasaCamser) {
		this.codigoTasaCamser = codigoTasaCamser;
	}
	public String getTipoTasaCamser() {
		return tipoTasaCamser;
	}
	public void setTipoTasaCamser(String tipoTasaCamser) {
		this.tipoTasaCamser = tipoTasaCamser;
	}

	public String getOtrosSinCodig() {
		return otrosSinCodig;
	}

	public void setOtrosSinCodig(String otrosSinCodig) {
		this.otrosSinCodig = otrosSinCodig;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public BigDecimal getCodigoMarcaBase() {
		return codigoMarcaBase;
	}

	public void setCodigoMarcaBase(BigDecimal codigoMarcaBase) {
		this.codigoMarcaBase = codigoMarcaBase;
	}

	public String getMarcaBase() {
		return marcaBase;
	}

	public void setMarcaBase(String marcaBase) {
		this.marcaBase = marcaBase;
	}

	public String getMarcaSinEditarBase() {
		return marcaSinEditarBase;
	}

	public void setMarcaSinEditarBase(String marcaSinEditarBase) {
		this.marcaSinEditarBase = marcaSinEditarBase;
	}

}