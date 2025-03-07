package trafico.modelo;

import escrituras.beans.ResultBean;

import java.math.BigDecimal;

import oegam.constantes.ConstantesPQ;

import trafico.beans.VehiculoTramiteTraficoBean;
import trafico.beans.daos.pq_vehiculos.BeanPQDETALLE_VEHICULO_TRAMITE_TRAF;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF;
import trafico.beans.utiles.VehiculoTramiteTraficoBeanPQConversion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloVehiculoTramiteTrafico {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloVehiculoTramiteTrafico.class);

	/**
	 * @author Carlos García
	 * Incidencia: 0002003 
	 * Método que permitirá guardar los datos de un vehiculo tramite trafico.
	 * @param vehiculoTramiteTraficoBean
	 * @return ResultBean
	 * @throws 
	 */
	public static ResultBean guardarVehiculoTramiteTrafico(VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean) {
		ResultBean resultadoModelo = new ResultBean();

		BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF beanPQGuardar = VehiculoTramiteTraficoBeanPQConversion.ConvertirVehiculoTramiteGuardarBeanPQ(vehiculoTramiteTraficoBean);

		// Para evitar las restricciones le indicamos que es de tipo Mantenimiento.
		beanPQGuardar.execute();

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQGuardar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQGuardar.getP_SQLERRM());

		if (!pCodeTramite.toString().equals("0")) {
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQGuardar.getP_SQLERRM());
		} else {
			resultadoModelo.setError(false);
		}
		return resultadoModelo;
	}

	/**
	 * @author Carlos García
	 * Incidencia: 0002003 
	 * Método que permitirá guardar los datos de un vehículo trámite tráfico.
	 * @param vehiculoTramiteTraficoBean
	 * @return ResultBean
	 * @throws 
	 */
	public static VehiculoTramiteTraficoBean detalleVehiculoTramiteTrafico(VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean) {
		BeanPQDETALLE_VEHICULO_TRAMITE_TRAF beanPQDetalle = VehiculoTramiteTraficoBeanPQConversion.ConvertirVehiculoTramiteDetalleBeanPQ(vehiculoTramiteTraficoBean);

		// Para evitar las restricciones le indicamos que es de tipo Mantenimiento.
		beanPQDetalle.execute();

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQDetalle.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + ": " + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

		VehiculoTramiteTraficoBean vehiculoTramiteTraficoBeanResult = null;

		if (!pCodeTramite.toString().equals("0")) {
			// Hay que ver qué se hace en este caso
			return vehiculoTramiteTraficoBeanResult;
		} else {
			vehiculoTramiteTraficoBeanResult = VehiculoTramiteTraficoBeanPQConversion.ConvertirPQBean(beanPQDetalle);
			// Agrego sus valores originales
			vehiculoTramiteTraficoBeanResult.setIdVehiculo(vehiculoTramiteTraficoBean.getIdVehiculo());
			vehiculoTramiteTraficoBeanResult.setNumColegiado(vehiculoTramiteTraficoBean.getNumColegiado());
			vehiculoTramiteTraficoBeanResult.setNumExpediente(vehiculoTramiteTraficoBean.getNumExpediente());
			return vehiculoTramiteTraficoBeanResult;
		}
	}
}