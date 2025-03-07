package trafico.beans.utiles;

import trafico.beans.VehiculoTramiteTraficoBean;
import trafico.beans.daos.pq_vehiculos.BeanPQDETALLE_VEHICULO_TRAMITE_TRAF;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class VehiculoTramiteTraficoBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(VehiculoTramiteTraficoBeanPQConversion.class);

	public static BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF ConvertirVehiculoTramiteGuardarBeanPQ(VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean) {
		BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF beanPQGUARDARVEHICULOTRAMITETRAF = new BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF();

		beanPQGUARDARVEHICULOTRAMITETRAF.setP_ID_VEHICULO(vehiculoTramiteTraficoBean.getIdVehiculo());
		beanPQGUARDARVEHICULOTRAMITETRAF.setP_KILOMETROS(vehiculoTramiteTraficoBean.getKilometros());
		beanPQGUARDARVEHICULOTRAMITETRAF.setP_NUM_COLEGIADO(vehiculoTramiteTraficoBean.getNumColegiado());
		beanPQGUARDARVEHICULOTRAMITETRAF.setP_NUM_EXPEDIENTE(vehiculoTramiteTraficoBean.getNumExpediente());
		return beanPQGUARDARVEHICULOTRAMITETRAF;
	}

	public static BeanPQDETALLE_VEHICULO_TRAMITE_TRAF ConvertirVehiculoTramiteDetalleBeanPQ(VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean) {
		BeanPQDETALLE_VEHICULO_TRAMITE_TRAF beanPQDETALLEVEHICULOTRAMITETRAF = new BeanPQDETALLE_VEHICULO_TRAMITE_TRAF();

		beanPQDETALLEVEHICULOTRAMITETRAF.setP_ID_VEHICULO(vehiculoTramiteTraficoBean.getIdVehiculo());
		beanPQDETALLEVEHICULOTRAMITETRAF.setP_NUM_COLEGIADO(vehiculoTramiteTraficoBean.getNumColegiado());
		beanPQDETALLEVEHICULOTRAMITETRAF.setP_NUM_EXPEDIENTE(vehiculoTramiteTraficoBean.getNumExpediente());
		return beanPQDETALLEVEHICULOTRAMITETRAF;
	}

	public static VehiculoTramiteTraficoBean ConvertirPQBean(BeanPQDETALLE_VEHICULO_TRAMITE_TRAF detalleVehiculoTramiteTrafico) {
		VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean = new VehiculoTramiteTraficoBean();
		vehiculoTramiteTraficoBean.setKilometros(detalleVehiculoTramiteTrafico.getP_KILOMETROS());
		return vehiculoTramiteTraficoBean;
	}
}