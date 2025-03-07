package trafico.modelo;

import java.math.BigDecimal;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.EvolucionTasaCursor;
import trafico.beans.daos.pq_tasas.BeanPQTasaEvolucion;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloEvolucionTasa extends ModeloBasePQ{

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloEvolucionTasa.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**********************************************************************************************************
		PROCEDURE: EVOLUCION
		PARAMETROS:
		DESCRIPCION:

	*********************************************************************************************************************************

		PROCEDURE EVOLUCION (P_ID_USUARIO IN USUARIO.ID_USUARIO%TYPE,
				P_ID_CONTRATO_SESSION IN CONTRATO.ID_CONTRATO%TYPE,
				P_CODIGO_TASA IN TASA.CODIGO_TASA%TYPE,
				PAGINA IN NUMBER,
				NUM_REG IN NUMBER,
				COLUMNA_ORDEN IN VARCHAR2,
				ORDEN IN VARCHAR2,
				CUENTA OUT NUMBER,
				P_CODE OUT NUMBER,
				P_SQLERRM OUT VARCHAR2,
				C_EVOLUCION OUT SYS_REFCURSOR);

	**********************************************************************************************************/
	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();

		//Datos de la búsqueda.
		//Datos que sacamos de la sesión para la búsqueda
		BeanPQTasaEvolucion beanEvolucionTasa = new BeanPQTasaEvolucion();
		beanEvolucionTasa.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanEvolucionTasa.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanEvolucionTasa.setP_CODIGO_TASA((String)getParametrosBusqueda().get(ConstantesSession.COD_TASA));
		//Datos de paginación, que pasamos por defecto
		beanEvolucionTasa.setPAGINA(new BigDecimal(pagina));
		beanEvolucionTasa.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanEvolucionTasa.setCOLUMNA_ORDEN(columnaOrden);
		beanEvolucionTasa.setORDEN(orden.toString().toUpperCase());

		//Realiza la llamada genérica para la consulta
		RespuestaGenerica resultadoEvolucionVehiculo = 
			ejecutarProc(beanEvolucionTasa,valoresSchemas.getSchema(),ValoresCatalog.PQ_TASAS,"EVOLUCION",EvolucionTasaCursor.class);

		//////////////

		//Recuperamos información de respuesta

		BigDecimal pCodeVehiculo = (BigDecimal)resultadoEvolucionVehiculo.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeVehiculo);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoEvolucionVehiculo.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoEvolucionVehiculo.getParametro("CUENTA"));

		List<Object> listaCursor = resultadoEvolucionVehiculo.getListaCursor();

		BigDecimal tamRegs = (BigDecimal) resultadoEvolucionVehiculo.getParametro("CUENTA");//listaCursor.size();
		listaRegistros.setTamano(utiles.convertirBigDecimalAInteger(tamRegs));
		for (int i=0;i<listaCursor.size();i++) {
			((EvolucionTasaCursor)listaCursor.get(i)).setINDICE(i);
		}

		listaRegistros.setLista(listaCursor);

		//////////////

		return listaRegistros;
	}

}