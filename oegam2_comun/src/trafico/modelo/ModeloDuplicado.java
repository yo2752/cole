package trafico.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.DescripcionesDireccionBean;
import trafico.beans.DescripcionesTraficoBean;
import trafico.beans.ExcelDuplicadosCursor;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQTramiteDuplicado;
import trafico.beans.daos.pq_tramite_trafico.BeanPQDETALLE_DUPLICADO;
import trafico.beans.utiles.DuplicadoTramiteTraficoBeanPQConversion;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.utiles.constantes.ConstantesDGT;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloDuplicado  extends ModeloBasePQ{

	private final ILoggerOegam log = LoggerOegam.getLogger(ModeloDuplicado.class);

	private ModeloInterviniente modeloInterviniente;
	private ModeloTrafico modeloTrafico;
	private ModeloVehiculo modeloVehiculo;

	@Autowired
	DuplicadoTramiteTraficoBeanPQConversion duplicadoTramiteTraficoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloDuplicado() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @author javier.folgueras
	 * @param beanBaja
	 * @return HashMap<String,Object> resultadoBaja
	 */
	/*
	 * Método para guardar un alta de trámite de duplicado
	 */
	public HashMap<String, Object> guardarAltaTramiteDuplicado(BeanPQTramiteDuplicado beanDuplicado){

		if (beanDuplicado==null){
			HashMap<String, Object> hashMap = new HashMap<String, Object> ();
			ResultBean resultBean = new ResultBean();
			resultBean.setError(true);
			resultBean.setMensaje("No hay tramite de Duplicado.");
			hashMap.put(ConstantesPQ.RESULTBEAN, resultBean);
			return hashMap;
		}

		HashMap<String,Object> resultadoDuplicado = new HashMap<String, Object>();
		ResultBean resultado = new ResultBean();
		TramiteTraficoDuplicadoBean tramite = new TramiteTraficoDuplicadoBean(true);
		resultado.setError(false);
		String mensaje = "";
		VehiculoBean beanVehiculo = new VehiculoBean(true);

		if (beanDuplicado.getBeanGuardarVehiculo().getP_MATRICULA()!=null && !"".equals(beanDuplicado.getBeanGuardarVehiculo().getP_MATRICULA())){
			//Se setea el campo necesario al VEHICULO
			if (null==beanDuplicado.getBeanGuardarVehiculo().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarVehiculo().getP_NUM_COLEGIADO()))
				beanDuplicado.getBeanGuardarVehiculo().setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
			beanDuplicado.getBeanGuardarVehiculo().setP_TIPO_TRAMITE(TipoTramiteTrafico.Duplicado.getValorEnum());
			//Vamos a guardar el vehiculo primero.
			RespuestaGenerica resultadoVehiculo = ejecutarProc(beanDuplicado.getBeanGuardarVehiculo(),
																valoresSchemas.getSchema(),
																ValoresCatalog.PQ_VEHICULOS,"GUARDAR",
																BeanPQGeneral.class);
			//Recuperamos el vehiculo del PQ, lo convertirmos y añadimos al Bean de Pantalla
			// Se queda en una variable VehiculoBean para luego añadirlo después del tramite para no pisar los datos del primero.
			beanVehiculo = vehiculoBeanPQConversion.convertirPQToBean(resultadoVehiculo);
			//Recuperamos información de respuesta
			BigDecimal pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
			log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el vehículo: " + pCodeVehiculo);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));
			//Si ha ido bien la operación de guardar vehículo (P_CODE es 0 si todo fue bien).
			if (ConstantesPQ.pCodeOk.equals(pCodeVehiculo)) { //ConstantesPQ.pCodeOk
				log.debug("          EL VEHICULO (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
			}else{ //Si la operación de guardar vehiculo fue erronea, se guarda el error en el resultado para devolverlo después.
				mensaje += ("  - Error al guardar el vehículo. " + 
						(String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
				if (resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION)!=null){
					mensaje += ((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));
				}
			}
			//Seteamos los campos necesarios al TRAMITE
			BigDecimal idVehiculo = (BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO");
			beanDuplicado.getBeanGuardarDuplicado().setP_ID_VEHICULO(idVehiculo);
		}

		if (null==beanDuplicado.getBeanGuardarDuplicado().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarDuplicado().getP_NUM_COLEGIADO())) 
			beanDuplicado.getBeanGuardarDuplicado().setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
		if (null==beanDuplicado.getBeanGuardarDuplicado().getP_ID_CONTRATO()) 
			beanDuplicado.getBeanGuardarDuplicado().setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());
		beanDuplicado.getBeanGuardarDuplicado().setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanDuplicado.getBeanGuardarDuplicado().setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());

		//Guardamos el tramite
		RespuestaGenerica resultadoTramite = ejecutarProc(beanDuplicado.getBeanGuardarDuplicado(),
															valoresSchemas.getSchema(),
															ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_DUPLICADO",
															BeanPQGeneral.class);
		//Recuperamos el tramite del PQ y lo convertirmos y añadimos al Bean de Pantalla
		tramite = (duplicadoTramiteTraficoBeanPQConversion.convertirPQToBean(resultadoTramite));
		tramite.getTramiteTrafico().setVehiculo(beanVehiculo);
		//Recuperamos información de respuesta
		BigDecimal pCodeBaja = (BigDecimal) resultadoTramite.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el tramite de baja: " + pCodeBaja);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoTramite.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + (String) resultadoTramite.getParametro(ConstantesPQ.P_INFORMACION));
		//Si ha ido bien la operación de guardar el tramite de baja.
		if (ConstantesPQ.pCodeOk.equals(pCodeBaja)){
			log.debug("          EL TRAMITE (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
			//Solo si el tramite se ha guardado correctamente se procede a guardar los intervinientes.
			//Seteamos lo necesario a al TITULAR
			BigDecimal numExpediente = (BigDecimal)resultadoTramite.getParametro("P_NUM_EXPEDIENTE");
			//Si hay un NIF en titular, se procede a guardarlo.  si el NIF es nulo pero hay rowid se procede a borrarlo de la bbdd
			if ((beanDuplicado.getBeanGuardarTitular().getP_NIF()!=null && !"".equals(beanDuplicado.getBeanGuardarTitular().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarTitular().getP_TIPO_INTERVINIENTE()!=null && !"".equals(beanDuplicado.getBeanGuardarTitular().getP_TIPO_INTERVINIENTE()))){

				beanDuplicado.getBeanGuardarTitular().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarTitular().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_TITULAR);
				if (null==beanDuplicado.getBeanGuardarTitular().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarTitular().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarTitular().setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
				//Guardarmos el Titular
				RespuestaGenerica resultadoTitular = ejecutarProc(beanDuplicado.getBeanGuardarTitular(),
																  valoresSchemas.getSchema(), 
																  ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",
																  BeanPQGeneral.class);
				//Recuperamos el tramite del PQ y lo convertirmos y añadimos al Bean de Pantalla
				BigDecimal pCodeTitular = (BigDecimal) resultadoTitular.getParametro(ConstantesPQ.P_CODE);
				if (!ConstantesPQ.pCodeOkAlt.equals(pCodeTitular)) {
					tramite.setTitular(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTitular));
					//Recuperamos la información de la respuesta
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el titular: " + pCodeTitular);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoTitular.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String) resultadoTitular.getParametro(ConstantesPQ.P_INFORMACION));
					//Comprobamos si se ha habído un error al guardar el titular. Aunque haya habido un error seguimos con el siguiente interviniente.
					if (ConstantesPQ.pCodeOk.equals(pCodeTitular)) {
						log.debug("          EL TITULAR (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
						//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
						tramite.getTramiteTrafico().setEstado(tramite.getTitular().getEstadoTramite()!= null ?tramite.getTitular().getEstadoTramite().getValorEnum():null);
					}else{  //Si la operación de guardar titular fue erronea, se guarda el error en el resultado para devolverlo después.
						mensaje += ("  - Error al guardar el titular. " + 
								(String) resultadoTitular.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
						if (resultadoTitular.getParametro(ConstantesPQ.P_INFORMACION)!=null){
							mensaje += (String) resultadoTitular.getParametro(ConstantesPQ.P_INFORMACION);
						}
					}
				}
			}
			// Guardamos el cotitular
			if ((beanDuplicado.getBeanGuardarCotitular().getP_NIF()!=null && !"".equals(beanDuplicado.getBeanGuardarCotitular().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarCotitular().getP_TIPO_INTERVINIENTE()!=null && !"".equals(beanDuplicado.getBeanGuardarCotitular().getP_TIPO_INTERVINIENTE()))){

				beanDuplicado.getBeanGuardarCotitular().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarCotitular().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_COTITULAR_TRANSMISION);
				if (null==beanDuplicado.getBeanGuardarCotitular().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarCotitular().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarCotitular().setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
				//Guardarmos el Titular
				RespuestaGenerica resultadoCoTitular = ejecutarProc(beanDuplicado.getBeanGuardarCotitular(),
																  valoresSchemas.getSchema(), 
																  ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",
																  BeanPQGeneral.class);
				//Recuperamos el tramite del PQ y lo convertirmos y añadimos al Bean de Pantalla
				BigDecimal pCodeTitular = (BigDecimal) resultadoCoTitular.getParametro(ConstantesPQ.P_CODE);
				if (!ConstantesPQ.pCodeOkAlt.equals(pCodeTitular)) {
				tramite.setCotitular(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoCoTitular));
					//Recuperamos la información de la respuesta
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el titular: " + pCodeTitular);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoCoTitular.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String) resultadoCoTitular.getParametro(ConstantesPQ.P_INFORMACION));
					//Comprobamos si se ha habído un error al guardar el titular. Aunque haya habido un error seguimos con el siguiente interviniente.
					if (ConstantesPQ.pCodeOk.equals(pCodeTitular)) {
						log.debug("          EL COTITULAR (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
						//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
						tramite.getTramiteTrafico().setEstado(tramite.getTitular().getEstadoTramite()!= null ?tramite.getTitular().getEstadoTramite().getValorEnum():null);
					}else{  //Si la operación de guardar titular fue erronea, se guarda el error en el resultado para devolverlo después.
						mensaje += ("  - Error al guardar el cotitular. " + 
								(String) resultadoCoTitular.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
						if (resultadoCoTitular.getParametro(ConstantesPQ.P_INFORMACION)!=null){
							mensaje += (String) resultadoCoTitular.getParametro(ConstantesPQ.P_INFORMACION);
						}
					}
				}
			}

			//Si hay un NIF en representante, se procede a guardarlo, si el NIF es nulo pero hay rowid se procede a borrarlo de la bbdd
			if ((beanDuplicado.getBeanGuardarRepresentante().getP_NIF()!=null && !"".equals(beanDuplicado.getBeanGuardarRepresentante().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarRepresentante().getP_TIPO_INTERVINIENTE()!= null && !beanDuplicado.getBeanGuardarRepresentante().getP_TIPO_INTERVINIENTE().equals(""))){
				//Se setea lo necesario en el REPRESENTANTE
				beanDuplicado.getBeanGuardarRepresentante().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarRepresentante().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR);
				if (null==beanDuplicado.getBeanGuardarRepresentante().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarRepresentante().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarRepresentante().setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
				//Guardarmos el Representante
				RespuestaGenerica resultadoRepresentante = ejecutarProc(beanDuplicado.getBeanGuardarRepresentante(),
																		valoresSchemas.getSchema(), 
																		ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",
																		BeanPQGeneral.class);
				//Recuperamos el tramite del PQ y lo convertirmos y añadimos al Bean de Pantalla
				BigDecimal pCodeRepresentante = (BigDecimal) resultadoRepresentante.getParametro(ConstantesPQ.P_CODE);
				if (!ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentante)) {
				tramite.setRepresentante(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoRepresentante));
				//Recuperamos la información de la respuesta
				log.debug(ConstantesPQ.LOG_P_CODE +  " al guardar el representante: " + pCodeRepresentante);
				log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoRepresentante.getParametro(ConstantesPQ.P_SQLERRM));
				log.debug(ConstantesPQ.LOG_P_INFORMACION + (String) resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION));
				//Comprobamos si se ha habído un error al guardar el representante. Aunque haya habido un error seguimos con el siguiente interviniente.
				if (ConstantesPQ.pCodeOk.equals(pCodeRepresentante)) {
					log.debug("          EL REPRESENTANTE (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					tramite.getTramiteTrafico().setEstado(tramite.getRepresentante().getEstadoTramite()!= null ?tramite.getRepresentante().getEstadoTramite().getValorEnum():null);
				}else{
					mensaje += ("  - Error al guardar el representante. " +
							(String) resultadoRepresentante.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
					if (resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION)!=null){
						mensaje += (String) resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION);
					}
				}
				}
			}

		}else{ //Si la operación de guardar tramite de baja fue erronea, se guarda el error para devolverlo después.
			resultado.setError(true);
			mensaje += ("  - Error al guardar el trámite de duplicado." +
					//transformarMensajesDeError(pCodeBaja) +  
					(String) resultadoTramite.getParametro(ConstantesPQ.P_SQLERRM)); //+
					//" No se guardará ningún dato de los intervinientes. ");
			if (resultadoTramite.getParametro(ConstantesPQ.P_INFORMACION)!=null){
				mensaje += (String) resultadoTramite.getParametro(ConstantesPQ.P_INFORMACION);
			}
		}

		resultado.setMensaje(mensaje);
		resultadoDuplicado.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoDuplicado.put(ConstantesPQ.BEANPANTALLA, tramite);
		return resultadoDuplicado;
	}

	/**
	 * Método que nos devuelve el detalle completo del trámite con todos sus campos cargados.
	 * Vehículo, intervinientes, etc.
	 * @param numEXPEDIENTE
	 * @param numColegiado 
	 * @return
	 */
	public HashMap<String, Object> obtenerDetalle(BigDecimal numEXPEDIENTE,String numColegiado,BigDecimal idContrato) {

		HashMap<String, Object> resultadoMetodo = new HashMap<String, Object>();
		TramiteTraficoDuplicadoBean beanDetalleDuplicado = new TramiteTraficoDuplicadoBean(true);
		BeanPQDETALLE_DUPLICADO beanConsultaDuplicado = new BeanPQDETALLE_DUPLICADO();
		VehiculoBean vehiculo = new VehiculoBean(true);
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";

		beanConsultaDuplicado.setP_NUM_EXPEDIENTE(numEXPEDIENTE);

		//Hacemos la búsqueda de los datos generales del trámite.
		RespuestaGenerica resultadoBD = 
			ejecutarProc(beanConsultaDuplicado,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"DETALLE_DUPLICADO",BeanPQGeneral.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		//Se controla el error y se actualiza para su devolucion
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)){
			error = true;
			mensaje += ("  - Se ha producido un error al obtener el mensaje. " + 
					(String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
		}
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoBD.getParametro("CUENTA"));

		//Convertimos y rellenamos los datos complejos del bean.
		beanDetalleDuplicado = duplicadoTramiteTraficoBeanPQConversion.convertirPQToBean(resultadoBD);

		//Vehículo
		vehiculo = getModeloVehiculo().cargaVehiculoPorId(beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getIdVehiculo());
		beanDetalleDuplicado.getTramiteTrafico().setVehiculo(vehiculo);

		//Intervinientes
		List<IntervinienteTrafico> listaIntervinienteTrafico = getModeloInterviniente().obtenerDetalleIntervinientes(numEXPEDIENTE,numColegiado,idContrato);

		for (IntervinienteTrafico intervinienteTrafico : listaIntervinienteTrafico) {
			if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.Titular.getValorEnum())){
				beanDetalleDuplicado.setTitular(intervinienteTrafico);
			}else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.CotitularTransmision.getValorEnum())){
				beanDetalleDuplicado.setCotitular(intervinienteTrafico);
			}else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteTitular.getValorEnum())){
				beanDetalleDuplicado.setRepresentante(intervinienteTrafico);
			}
		}

		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesPQ.BEANPANTALLA, beanDetalleDuplicado);

		return resultadoMetodo;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	public HashMap<String, Object> obtenerDetalleConDescripciones(BigDecimal numEXPEDIENTE, String numColegiado, BigDecimal idContrato) {

		HashMap<String, Object> resultado = new HashMap<String, Object>();
		HashMap<String, Object> resultadoMetodo = new HashMap<String, Object>();
		ResultBean resultBean = new ResultBean();
		ResultBean resultBeanMetodo = new ResultBean();
		TramiteTraficoDuplicadoBean beanDetalleDuplicado = new TramiteTraficoDuplicadoBean(true);

		resultadoMetodo = obtenerDetalle(numEXPEDIENTE,numColegiado,idContrato);

		resultBeanMetodo = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN);

		if(resultBeanMetodo.getError()){
			resultBean.setError(true);
			resultBean.setMensaje(resultBeanMetodo.getMensaje());
		}else{
			beanDetalleDuplicado = 
				(TramiteTraficoDuplicadoBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
			//Llamada al método de obtener descripciones
			HashMap<String, Object> resultadoDescripciones = 
					getModeloTrafico().obtenerDescripcionesPorNumExpediente(numEXPEDIENTE);
			ResultBean resultBeanDescripciones = (ResultBean)resultadoDescripciones.get(ConstantesPQ.RESULTBEAN);

			if(resultBeanDescripciones.getError()){
				resultBean.setError(true);
				resultBean.setMensaje(resultBeanDescripciones.getMensaje());
			}else{
				DescripcionesTraficoBean descripciones = 
					(DescripcionesTraficoBean) resultadoDescripciones.get(ConstantesPQ.BEANPANTALLA);

				//Rellenamos todos los datos de descripción que necesitamos.
				//Primero la dirección de los intervinientes.Titular
				if(null != beanDetalleDuplicado.getTitular()&&
						null != beanDetalleDuplicado.getTitular().getPersona()&&
						null != beanDetalleDuplicado.getTitular().getPersona().getDireccion()&&
						null != beanDetalleDuplicado.getTitular().getPersona().getDireccion().getIdDireccion()){

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion( new BigDecimal(
							 beanDetalleDuplicado.getTitular().getPersona().getDireccion().getIdDireccion()));

					direccion = (DescripcionesDireccionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					beanDetalleDuplicado.getTitular().getPersona().getDireccion().getTipoVia()
						.setNombre(direccion.getTipViaNombre());
					beanDetalleDuplicado.getTitular().getPersona().getDireccion().getMunicipio().getProvincia()
						.setNombre(direccion.getProvNombre());
					beanDetalleDuplicado.getTitular().getPersona().getDireccion().getMunicipio()
						.setNombre(direccion.getMuniNombre());
				}

				//Representante
				if(null != beanDetalleDuplicado.getRepresentante()&&
						null != beanDetalleDuplicado.getRepresentante().getPersona()&&
						null != beanDetalleDuplicado.getRepresentante().getPersona().getDireccion()&&
						null != beanDetalleDuplicado.getRepresentante().getPersona().getDireccion().getIdDireccion()){

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion( new BigDecimal(
								 beanDetalleDuplicado.getRepresentante().getPersona().getDireccion().getIdDireccion()));

						direccion = (DescripcionesDireccionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						beanDetalleDuplicado.getRepresentante().getPersona().getDireccion().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
						beanDetalleDuplicado.getRepresentante().getPersona().getDireccion().getMunicipio().getProvincia()
							.setNombre(direccion.getProvNombre());
						beanDetalleDuplicado.getRepresentante().getPersona().getDireccion().getMunicipio()
							.setNombre(direccion.getMuniNombre());
					}

				//Ahora cargamos todos los datos del TramiteTráficoBean
				if(null != beanDetalleDuplicado.getTramiteTrafico()){

					//Gestoria CIF y RAZON SOCIAL
					beanDetalleDuplicado.getTramiteTrafico().setCif(descripciones.getCif());
					beanDetalleDuplicado.getTramiteTrafico().setRazonSocial(descripciones.getRazonSocial());

					//Jefatura
					if(null != beanDetalleDuplicado.getTramiteTrafico().getJefaturaTrafico()){

						beanDetalleDuplicado.getTramiteTrafico().getJefaturaTrafico()
							.setDescripcion(descripciones.getDescripcionJefaturaSucursal());
						beanDetalleDuplicado.getTramiteTrafico().getJefaturaTrafico().getProvincia().
							setNombre(descripciones.getDescripcionJefaturaProvincial());
					}
					//Vehículo
					if(null != beanDetalleDuplicado.getTramiteTrafico().getVehiculo()){

						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getMarcaBean().setMarca(descripciones.getMarca());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getMarcaBean().setMarcaSinEditar(descripciones.getMarcaSinEditar());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getMarcaBaseBean().setMarca(descripciones.getMarcaBase());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getMarcaBaseBean().setMarcaSinEditar(descripciones.getMarcaSinEditarBase());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getTipoVehiculoBean().setDescripcion(descripciones.getDescripcionTipoVehiculo());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo()
							.getServicioTraficoBean().setDescripcion(descripciones.getDescripcionServicio());

						//Dirección del Vehículo
						if(	null != beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean()&&
							null != beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean().getIdDireccion()){

						DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
						resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion( new BigDecimal(
								 beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean().getIdDireccion()));

						direccion = (DescripcionesDireccionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean().getMunicipio().getProvincia()
							.setNombre(direccion.getProvNombre());
						beanDetalleDuplicado.getTramiteTrafico().getVehiculo().getDireccionBean().getMunicipio()
							.setNombre(direccion.getMuniNombre());
						}
					}
				}
			}

		}

		resultado.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultado.put(ConstantesPQ.BEANPANTALLA, beanDetalleDuplicado);
		return resultado;
	}

	/**
	 *  Cambia el estado de la lista de trámites de baja que se le pasa al estado
	 *  que se recibe en el segundo parámetro
	 */
	public void cambiarEstadoListaDuplicados(ArrayList<ExcelDuplicadosCursor> listaTramitesB,
			EstadoTramiteTrafico estado) throws Exception {
		for (ExcelDuplicadosCursor tramiteLista : listaTramitesB) {
			// Cambia el estado del trámite
			BeanPQCambiarEstadoTramite beanCambioEstado = new BeanPQCambiarEstadoTramite();
			beanCambioEstado.setP_NUM_EXPEDIENTE(tramiteLista.getNUM_EXPEDIENTE());
			beanCambioEstado.setP_ESTADO(new BigDecimal(estado.getValorEnum()));
			HashMap<String, Object> resultadoModelo = getModeloTrafico().cambiarEstadoTramite(beanCambioEstado);
			@SuppressWarnings("unused")
			ResultBean resultadoCambio = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
		}
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

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
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