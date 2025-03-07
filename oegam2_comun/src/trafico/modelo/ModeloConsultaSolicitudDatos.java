package trafico.modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.pq_tramite_trafico.BeanPQDETALLE_SOLICITUD;
import trafico.beans.utiles.SolicitudDatosBeanPQConversion;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloConsultaSolicitudDatos  extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloConsultaSolicitudDatos.class);

	private ModeloInterviniente modeloInterviniente;
	private ModeloVehiculo modeloVehiculo;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	SolicitudDatosBeanPQConversion solicitudDatosBeanPQConversion;

	public ModeloConsultaSolicitudDatos() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public HashMap<String,Object> consultarSolicitudDatos(SolicitudDatosBean solicitudDatosBean) {
		return null;
	}

	public HashMap<String,Object> historicoSolicitudDatos(SolicitudDatosBean solicitudDatosBean) {
		return null;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	public HashMap<String, Object> obtenerDetalle(BigDecimal numEXPEDIENTE, String numColegiado, BigDecimal idContrato) {

		HashMap<String, Object> detalle = new HashMap<>();

		SolicitudDatosBean solicitudBeanPantalla = new SolicitudDatosBean(true);
		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		VehiculoBean vehiculo = new VehiculoBean(true);
		BeanPQDETALLE_SOLICITUD beanConsultaSolicitud = new BeanPQDETALLE_SOLICITUD();
		beanConsultaSolicitud.setP_NUM_EXPEDIENTE(numEXPEDIENTE);

		//Ejecución genérica del acceso a la base de datos
		RespuestaGenerica resultado = ejecutarProc(beanConsultaSolicitud,
													valoresSchemas.getSchema(),
													ValoresCatalog.PQ_TRAMITE_TRAFICO, "DETALLE_SOLICITUD",
													BeanPQGeneral.class);
		//Convertimos al bean de pantalla el resultado.
		solicitudBeanPantalla = solicitudDatosBeanPQConversion.convertirPQToBean(resultado);
		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultado.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultado.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultado.getParametro("CUENTA"));
		//Se controla el error y se actualiza para su devolucion
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)){
			resultBean.setError(true);
		}

		//Vehículo
		vehiculo = getModeloVehiculo().cargaVehiculoPorId(solicitudBeanPantalla.getTramiteTrafico().getVehiculo().getIdVehiculo());
		solicitudBeanPantalla.getTramiteTrafico().setVehiculo(vehiculo);

		//Interviniente
		List<IntervinienteTrafico> listaInterviniente = getModeloInterviniente().obtenerDetalleIntervinientes(numEXPEDIENTE, numColegiado, idContrato);
		IntervinienteTrafico solicitante = listaInterviniente.get(0);
		solicitudBeanPantalla.setSolicitante(solicitante);

		//solicitudDetalle = SolicitudTramiteTraficoBeanPQConversion.convertirPQToBean(resultado);
		detalle.put(ConstantesPQ.BEANPANTALLA, solicitudBeanPantalla);
		detalle.put(ConstantesPQ.RESULTBEAN, resultBean);
		return detalle;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloInterviniente getModeloInterviniente() {
		if (modeloInterviniente == null) {
			modeloInterviniente = new ModeloInterviniente();
		}
		return modeloInterviniente;
	}

	public void setModeloInterviniente(ModeloInterviniente modeloInterviniente) {
		this.modeloInterviniente = modeloInterviniente;
	}

	public ModeloVehiculo getModeloVehiculo() {
		if (modeloVehiculo == null) {
			modeloVehiculo = new ModeloVehiculo();
		}
		return modeloVehiculo;
	}

	public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}

}