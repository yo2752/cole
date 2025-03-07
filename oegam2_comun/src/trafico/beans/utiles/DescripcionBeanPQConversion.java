package trafico.beans.utiles;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;

import trafico.beans.DescripcionesTraficoBean;


public class DescripcionBeanPQConversion {

	public static DescripcionesTraficoBean convertirPQToBean(RespuestaGenerica resultadoPQ) {

		DescripcionesTraficoBean descripciones = new DescripcionesTraficoBean();

		descripciones.setNumExpediente((BigDecimal)resultadoPQ.getParametro("P_NUM_EXPEDIENTE"));
		descripciones.setCodigoTasa((String)resultadoPQ.getParametro("P_CODIGO_TASA"));
		descripciones.setTipoTasa((String)resultadoPQ.getParametro("P_TIPO_TASA"));
		descripciones.setJefaturaProvincial((String)resultadoPQ.getParametro("P_JEFATURA_PROVINCIAL"));
		descripciones.setJefaturaProvincia((String)resultadoPQ.getParametro("P_JEFATURA_PROV"));
		descripciones.setDescripcionJefaturaProvincial((String)resultadoPQ.getParametro("P_DESC_JEFATURA"));
		descripciones.setDescripcionJefaturaSucursal((String)resultadoPQ.getParametro("P_DESC_SUCURSAL"));

		descripciones.setCif((String)resultadoPQ.getParametro("P_CIF"));
		descripciones.setRazonSocial((String)resultadoPQ.getParametro("P_RAZON_SOCIAL"));

		if (resultadoPQ.getParametro("P_ID_VEHICULO") != null) {
			BigDecimal idVehiculo = (BigDecimal)resultadoPQ.getParametro("P_ID_VEHICULO");
			descripciones.setIdVehiculo(idVehiculo);
		}

		if (resultadoPQ.getParametro("P_CODIGO_MARCA") != null) {
			BigDecimal codigoMarca = (BigDecimal)resultadoPQ.getParametro("P_CODIGO_MARCA");
			descripciones.setCodigoMarca(codigoMarca);
		}

		if (resultadoPQ.getParametro("P_CODIGO_MARCA_BASE") != null) {
			BigDecimal codigoMarcaBase = (BigDecimal)resultadoPQ.getParametro("P_CODIGO_MARCA_BASE");
			descripciones.setCodigoMarcaBase(codigoMarcaBase);
		}

		descripciones.setMarca((String)resultadoPQ.getParametro("P_MARCA"));
		descripciones.setMarcaBase((String)resultadoPQ.getParametro("P_MARCA_BASE"));
		descripciones.setTipoVehiculo((String)resultadoPQ.getParametro("P_TIPO_VEHICULO"));
		descripciones.setDescripcionTipoVehiculo((String)resultadoPQ.getParametro("P_DESC_TIPO_VEHI"));
		descripciones.setIdServicio((String)resultadoPQ.getParametro("P_ID_SERVICIO"));
		descripciones.setDescripcionServicio((String)resultadoPQ.getParametro("P_DESC_SERVICIO"));
		descripciones.setIdCriterioConstruccion((String)resultadoPQ.getParametro("P_ID_CRITERIO_CONSTRUCCION"));
		descripciones.setDescripcionCriterioConstruccion((String)resultadoPQ.getParametro("P_DESC_CRITER_CONSTRUCCION"));
		descripciones.setIdCriterioUtilizacion((String)resultadoPQ.getParametro("P_ID_CRITERIO_UTILIZACION"));
		descripciones.setDescripcionCriterioUtilizacion((String)resultadoPQ.getParametro("P_DESC_CRITER_UTILIZACION"));
		descripciones.setIdDirectivaCEE((String)resultadoPQ.getParametro("P_ID_DIRECTIVA_CEE"));
		descripciones.setDescripcionDirectivaCEE((String)resultadoPQ.getParametro("P_DESC_DIRECTIVA_CEE"));
		descripciones.setIdMotivoITV((String)resultadoPQ.getParametro("P_ID_MOTIVO_ITV"));
		descripciones.setDescripcionMotivoITV((String)resultadoPQ.getParametro("P_DESC_MOTIVO_ITV"));
		descripciones.setEstacionITV((String)resultadoPQ.getParametro("P_ESTACION_ITV"));
		descripciones.setProvinciaEstacionITV((String)resultadoPQ.getParametro("P_PROV_ESTACION_ITV"));
		descripciones.setMunicipioEstacionITV((String)resultadoPQ.getParametro("P_MUNI_ESTACION_ITV"));
		descripciones.setOtrosSinCodig((String)resultadoPQ.getParametro("P_OTROS_SINCODIG"));
		descripciones.setNive((String)resultadoPQ.getParametro("P_NIVE"));

		descripciones.setCodigoTasaInf((String)resultadoPQ.getParametro("P_CODIGO_TASA_INF"));
		descripciones.setTipoTasaInf((String)resultadoPQ.getParametro("P_TIPO_TASA_INF"));
		descripciones.setCodigoTasaCamser((String)resultadoPQ.getParametro("P_CODIGO_TASA_CAMSER"));
		descripciones.setTipoTasaCamser((String)resultadoPQ.getParametro("P_TIPO_TASA_CAMSER"));

		return descripciones;
	}
}