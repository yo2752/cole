package trafico.modelo;

import java.math.BigDecimal;
import java.util.HashMap;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesCreditos;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.BeanPQDescontarCreditos;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQValidarCreditos;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

/**
 * Esta será una clase que contendrá todos los métodos necesarios para la gestión de los créditos
 * en el subsistema de tráfico.
 * @author juan.gomez
 *
 */
public class ModeloCreditosTrafico extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloCreditosTrafico.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	public ModeloCreditosTrafico() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método que validará si un colegiado tiene créditos disponibles para un tipo de trámites,
	 * y en el caso de tenerlos nos devolverá el número.
	 * @param numColegiado
	 * @return
	 */
	public HashMap<String, Object> validarCreditosPorNumColegiado(String idContratoTramite, BigDecimal numeroCreditos, String tipoTramite) {

		HashMap<String, Object> resultadoMetodo = new HashMap<>();
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";
		BeanPQValidarCreditos beanCreditos= new BeanPQValidarCreditos();
		BigDecimal numCreditosTotales = null;
		BigDecimal numCreditosDisponibles = null;
		BigDecimal numCreditosBloqueados = null;

		//Relleno el bean para la consulta en la base de datos
		beanCreditos.setNUMERO(numeroCreditos);
		beanCreditos.setP_ID_CONTRATO(new BigDecimal(idContratoTramite));
		beanCreditos.setP_TIPO_TRAMITE(tipoTramite);

		//Realizo la llamada y las validaciones de resultados así como la conversión de los datos.
		//Hacemos la búsqueda de los datos generales del trámite.
		RespuestaGenerica resultadoBD = 
			ejecutarProc(beanCreditos,valoresSchemas.getSchema(),ValoresCatalog.PQ_CREDITOS,"VALIDAR_CREDITOS",BeanPQGeneral.class);	

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		if(!"0".equals(pCodeTramite.toString())){
			mensaje += "Se ha producido un error al validar los créditos: " + resultadoBD.getParametro(ConstantesPQ.P_CODE) + resultadoBD.getParametro(ConstantesPQ.P_SQLERRM);
			error = true;
		}
		log.info(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.info(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.info(ConstantesPQ.LOG_P_CUENTA + resultadoBD.getParametro("CUENTA"));

		//Obtenemos el número de créditos totales.
		numCreditosTotales = (BigDecimal)resultadoBD.getParametro("P_CREDITOS");

		//Obtenemos el número de créditos disponibles.
		numCreditosDisponibles = (BigDecimal)resultadoBD.getParametro("P_CREDITOS_DISPONIBLES");

		//Obtenemos el número de créditos bloqueados.
		//Gilber - Mantis 2773
		if(numCreditosTotales!=null && numCreditosDisponibles!=null){
			numCreditosBloqueados = numCreditosTotales.subtract(numCreditosDisponibles);
		}

		numCreditosDisponibles = null != numCreditosDisponibles?numCreditosDisponibles:new BigDecimal(0);
		//Rellenamos los datos de resultados y lo devolvemos.
		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesCreditos.CREDITOS_TOTALES, numCreditosTotales);
		resultadoMetodo.put(ConstantesCreditos.CREDITOS_DISPONIBLES, numCreditosDisponibles);
		resultadoMetodo.put(ConstantesCreditos.CREDITOS_BLOQUEADOS, numCreditosBloqueados);

		return resultadoMetodo;
	}

	/**
	 * Método que descontará el número de créditos que le indiquemos para el colegiado que le indiquemos.
	 * @param string 
	 * @param numColegiado
	 * @param string2 
	 * @return
	 */
	public HashMap<String, Object> descontarCreditos(String idContratoTramite, BigDecimal numeroCreditos, String tipoTramite, BigDecimal idUsuario){

		HashMap<String, Object> resultadoMetodo = new HashMap<>();
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";
		BeanPQDescontarCreditos beanCreditos= new BeanPQDescontarCreditos();
		BigDecimal numCreditosDisponibles = null;

		//Relleno el bean para la consulta en la base de datos
		beanCreditos.setNUMERO(numeroCreditos);
		beanCreditos.setP_ID_USUARIO(idUsuario);
		beanCreditos.setP_ID_CONTRATO(new BigDecimal(idContratoTramite));
		beanCreditos.setP_TIPO_TRAMITE(tipoTramite);

		log.info("Se va a descontar " + numeroCreditos + " Tipotramite: " + tipoTramite + " idUsuario: " + idUsuario + " idContrato: " + idContratoTramite);

		//Realizo la llamada y las validaciones de resultados así como la conversión de los datos.
		//Hacemos la búsqueda de los datos generales del trámite.
		RespuestaGenerica resultadoBD = 
			ejecutarProc(beanCreditos,valoresSchemas.getSchema(),ValoresCatalog.PQ_CREDITOS,"DECONTAR_CREDITOS",BeanPQGeneral.class);	

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		if(!"0".equals(pCodeTramite.toString())){
			mensaje += "Se ha producido un error al validar los créditos: " + resultadoBD.getParametro(ConstantesPQ.P_CODE) + resultadoBD.getParametro(ConstantesPQ.P_SQLERRM);
			error = true;
		}
		log.info(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.info(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.info(ConstantesPQ.LOG_P_CUENTA + resultadoBD.getParametro("CUENTA"));

		//Obtenemos el número de créditos disponibles.
		numCreditosDisponibles = (BigDecimal)resultadoBD.getParametro("P_CREDITOS");

		//Rellenamos los datos de resultados y lo devolvemos.
		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesPQ.BEANPANTALLA, numCreditosDisponibles);

		return resultadoMetodo;
	}

	/**
	 * llama al metodo ya existente de descontar.
	 * @param idContratoTramite
	 * @param numeroCreditos
	 * @param tipoTramite
	 * @param idUsuario
	 * @return
	 * @throws DescontarCreditosExcepcion
	 */
	public BigDecimal descontarCreditosExcep(BigDecimal idContratoTramite, BigDecimal numeroCreditos, String tipoTramite,BigDecimal idUsuario) throws DescontarCreditosExcepcion{
		HashMap<String, Object> resultadoMetodo = descontarCreditos(idContratoTramite.toString(), numeroCreditos, tipoTramite, idUsuario);
		ResultBean resultBean = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
		if (resultBean.getError())
			throw new DescontarCreditosExcepcion(resultBean.getMensaje());
		else return (BigDecimal) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
	}

	public boolean comprobarCreditos(int numCreditosSolicitados, int numCreditosDisponibles) {
		if(numCreditosSolicitados != 0 && numCreditosDisponibles >= numCreditosSolicitados) {
			return false;
		}
		return true;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

}