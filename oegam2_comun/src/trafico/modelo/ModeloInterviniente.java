package trafico.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.daos.BeanPQIntervinientes;
import trafico.beans.daos.IntervinientesCursor;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloInterviniente  extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloInterviniente.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	public ModeloInterviniente(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	public List<IntervinienteTrafico> obtenerDetalleIntervinientes(BigDecimal numEXPEDIENTE,String numColegiado,BigDecimal idContrato) {

		BeanPQIntervinientes intervinientesPQ = new BeanPQIntervinientes();
		intervinientesPQ.setP_NUM_EXPEDIENTE(numEXPEDIENTE);
		intervinientesPQ.setP_NUM_COLEGIADO(numColegiado);
		intervinientesPQ.setP_ID_CONTRATO_SESSION(idContrato);

		RespuestaGenerica resultado = ejecutarProc(intervinientesPQ,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"INTERVINIENTES", IntervinientesCursor.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultado.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultado.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultado.getParametro("CUENTA"));

		List<Object> listaCursor = resultado.getListaCursor();
		List<IntervinienteTrafico> listaIntervinientes = new ArrayList<>();

		for (Object intervinienteCursor : listaCursor) {
			IntervinientesCursor intervinienteAux = (IntervinientesCursor)intervinienteCursor;
			IntervinienteTrafico intervinienteBean = new IntervinienteTrafico(true);
			intervinienteBean = intervinienteTraficoBeanPQConversion.convertirCursorToBean(intervinienteAux);
			listaIntervinientes.add(intervinienteBean);
		}

		return listaIntervinientes;
	}
}