package trafico.modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.CamposRespuestaPLSQL;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;
import general.utiles.OraUtiles;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQAltaMatriculacionImport;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQTramiteDuplicado;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarIntervinienteImport;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.daos.BeanPQVehiculosGuardarImport;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCARGAR_CET;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCONTROL_IMPORTACION;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF;
import trafico.beans.jaxb.cet_import.OptiCET620.Autoliquidacion;
import trafico.beans.utiles.DuplicadoTramiteTraficoBeanPQConversion;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import trafico.beans.utiles.MatriculacionTramiteTraficoBeanMatwPQConversion;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.utiles.constantes.ConstantesDGT;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.constantes.ValoresCatalog;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloImportacionDGT extends ModeloGenerico {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloImportacionDGT.class);

	private ModeloColegiado modeloColegiado;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;
	
	@Autowired
	private ServicioEstacionITV servicioEstacionITV;

	@Autowired
	MatriculacionTramiteTraficoBeanMatwPQConversion matriculacionTramiteTraficoBeanMatwPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	DuplicadoTramiteTraficoBeanPQConversion duplicadoTramiteTraficoBeanPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	public ModeloImportacionDGT() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método que irá llamando a los procedimientos almacenados necesarios para realizar una MATRICULACION mediante importación
	 * de fichero DGT
	 * @param beanPQAltaMatriculacionImport
	 * @return
	 */
	public ResultBean guardarAltaMatriculacionMatwImport(
			BeanPQAltaMatriculacionImport beanAlta, boolean xmlmate, BigDecimal idContratoTramite, BigDecimal idUsuario, BigDecimal idContrato,
			TipoCreacion tipoCreacion) {
		ResultBean resultado = new ResultBean();

		//Si no se ha seleccionado uno, se mete el de sesión
		if (idContratoTramite==null) idContratoTramite = idContrato;

		BeanPQVehiculosGuardarImport vehiculo = beanAlta.getBeanGuardarVehiculo();

		//Seteamos los parámetros de control del vehículo
		vehiculo.setP_ID_CONTRATO_SESSION(idContrato);
		vehiculo.setP_ID_USUARIO(idUsuario);
		//vehiculo.setP_NUM_EXPEDIENTE(beanAlta.getBeanGuardarMatriculacion().getP_NUM_EXPEDIENTE());
		vehiculo.setP_NUM_EXPEDIENTE(null);
		vehiculo.setP_TIPO_TRAMITE(TipoTramiteTrafico.Matriculacion.getValorEnum());
		vehiculo.setP_ID_CONTRATO(idContratoTramite);
		vehiculo.setP_ID_COLOR(null != vehiculo.getP_ID_COLOR() ? vehiculo.getP_ID_COLOR(): "-");

		Boolean representanteTitularNombreSlipt = false;
		String mensajeWarnings = "";
		// Para DGT y Mate
		RespuestaGenerica resultadoVehiculo = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarMatriculacion = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteTitular = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentante = null;

		// Añadidos solo para Mate
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteArrendatario = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteReprArrendatario= null;

		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual = null;

		BigDecimal numExpediente = null;

		//JRG: No se puede meter la LN de CEM y ExentoCEM por PQ. Por eso tiene la validacion aqui.
		if ((beanAlta.getBeanGuardarMatriculacion().getP_CEM()!=null
				&& !beanAlta.getBeanGuardarMatriculacion().getP_CEM().equals("")
				&& beanAlta.getBeanGuardarMatriculacion().getP_EXENTO_CEM()!=null
				&& beanAlta.getBeanGuardarMatriculacion().getP_EXENTO_CEM().equals("SI"))) {
			resultado.setError(true);
			resultado.setMensaje("El trámite no se ha guardado. No se puede indicar CEM y Exento_CEM a la vez. Por favor modifique uno.");
		}else{
			// Mantis 14937. David Sierra: Se muestra un mensaje informativo cuando un colegiado introduce un bastidor duplicado
			if ((vehiculo.getP_BASTIDOR() != null && !vehiculo.getP_BASTIDOR().isEmpty()) 
				&& (vehiculo.getP_NUM_COLEGIADO() != null && !vehiculo.getP_NUM_COLEGIADO().isEmpty())) {
				String bastidor = vehiculo.getP_BASTIDOR();
				String colegiado = vehiculo.getP_NUM_COLEGIADO();
				// Llamada al servicio
				if(servicioVehiculo.isBastidorDuplicado(bastidor, colegiado)) {
					mensajeWarnings += " -Vehiculo: El bastidor " + bastidor + " está duplicado para su número de colegiado.";
					log.error("El " + colegiado + " ha dado de alta el siguiente bastidor duplicado " + bastidor);
				}
			}
			//Mantis 15845.David Sierra: Validacion Tipo Inspección ITV si es exento se vacía fecha ITV
			if (((vehiculo.getP_ID_MOTIVO_ITV()) != null && !vehiculo.getP_ID_MOTIVO_ITV().isEmpty())
					&& (vehiculo.getP_FECHA_ITV() != null && !vehiculo.getP_FECHA_ITV().toString().isEmpty())
					&& "E".equals(vehiculo.getP_ID_MOTIVO_ITV())) {
				vehiculo.setP_FECHA_ITV(null);
				String mensajeTipoInspeccionItvAvisoFecha = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_AVISOFECHA;
				mensajeWarnings += " -Vehiculo: " + mensajeTipoInspeccionItvAvisoFecha;
			}
			
			if (StringUtils.isNotBlank(vehiculo.getP_ESTACION_ITV())) {
				List<EstacionITVVO> listadoPosibleEstacion = servicioEstacionITV
						.getEstacion(vehiculo.getP_ESTACION_ITV());
				if (listadoPosibleEstacion == null || listadoPosibleEstacion.isEmpty()) {
					vehiculo.setP_ESTACION_ITV(null);
					mensajeWarnings += " -La estación ITV indicada no es válida. ";
				}
			}

			if (vehiculo.getP_MODELO() != null && !vehiculo.getP_MODELO().isEmpty() && vehiculo.getP_MODELO().length() > 22) {
				vehiculo.setP_MODELO(vehiculo.getP_MODELO().substring(0, 21));
				mensajeWarnings += " -Vehiculo: El modelo se ha acortado a 22 caracteres, si desea modificarlo puede hacerlo desde el propio tramite";
			}

			//beanAlta.getBeanGuardarMatriculacion().setP_ID_TIPO_CREACION(new BigDecimal(tipoCreacion.getValorEnum()));
			/*
			 *  Vamos a guardar el vehiculo primero (si por lo menos tiene cod_itv o bastidor)
			 *  Mantis 0009136. Eliminamos la restricción de que el bastidor deba tener 17 caracteres,
			 *  sólo lo dejamos a distinto de nulo o vacío.
			 */
			if ( (!"".equals(vehiculo.getP_CODITV()) && null != vehiculo.getP_CODITV())
					|| (!"".equals(vehiculo.getP_BASTIDOR()) && null!=vehiculo.getP_BASTIDOR())) {
				vehiculo.setP_MATRICULA(StringUtils.isNotBlank(vehiculo.getP_MATRICULA()) ? vehiculo.getP_MATRICULA().trim() : vehiculo.getP_MATRICULA());
				resultadoVehiculo = ejecutarProc(vehiculo, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS,"GUARDAR_IMPORT_MATW", BeanPQGeneral.class);
			} else {
				//Si no tiene matrícula, no llamamos al procedimiento pero devolvemos un resultado como si lo hubiera hecho
				resultadoVehiculo = resultadoNoBastidorOCoditv();
			}
			//Recuperamos información de respuesta
			BigDecimal pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
			log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el vehículo: " + pCodeVehiculo);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));

			//Si ha ido bien la operación de guardar vehículo, continuamos guardando el trámite
			if ((ConstantesPQ.pCodeOk.equals(pCodeVehiculo))) {
				//Si ha habido un mensaje de información, lo guardamos:
				if (null != resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION))
					mensajeWarnings += !"".equals((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION))?(" -Vehiculo: " + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";

				if (null != ((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Vehiculo: " +(String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));

				//Seteamos el P_ID_VEHICULO
				//BigDecimal idVehiculo = (BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO");
				//	beanAlta.getBeanGuardarMatriculacion().setP_ID_VEHICULO(idVehiculo);
				//beanAlta.getBeanGuardarMatriculacion().setP_NUM_EXPEDIENTE(null);
				//beanAlta.getBeanGuardarMatriculacion().setP_ID_CONTRATO(idContratoTramite);

				if (resultadoVehiculo!=null && resultadoVehiculo.getParametro("P_ID_VEHICULO") != null) {
					beanAlta.getBeanGuardarMatriculacion().setP_ID_VEHICULO((BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO"));
				} else {
					beanAlta.getBeanGuardarMatriculacion().setP_ID_VEHICULO(vehiculo.getP_ID_VEHICULO());
				}

				beanAlta.getBeanGuardarMatriculacion().setP_NUM_EXPEDIENTE(null);
				beanAlta.getBeanGuardarMatriculacion().setP_ID_CONTRATO(idContratoTramite);

				// A partir de ahora se podran realizar matriculaciones de todas las jefaturas provinciales.
				beanAlta.getBeanGuardarMatriculacion().setP_JEFATURA_PROVINCIAL(beanAlta.getBeanGuardarMatriculacion().getP_JEFATURA_PROVINCIAL());

				//DRC@14-02-2013 Incidencia Mantis: 2708
				beanAlta.getBeanGuardarMatriculacion().setP_ID_TIPO_CREACION(new BigDecimal(tipoCreacion.getValorEnum()));

				//Ejecutamos el procedimiento de guardar el trámite
				resultadoTramiteTraficoGuardarMatriculacion = ejecutarProc(beanAlta.getBeanGuardarMatriculacion(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_MATRICULACION",BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeTramite = (BigDecimal)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_CODE);
				log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el tramite de matriculación: " + pCodeTramite);
				log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_SQLERRM));
				log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION));

				//Si el trámite se ha guardado bien, continuamos guardando los intervinientes
				if ((ConstantesPQ.pCodeOk.equals(pCodeTramite))) {
					//Si ha habido un mensaje de información, lo guardamos:
					if (null!=resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION))
						mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION))?(" -Tramite: " + (String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
					if (null!=((String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Tramite: "+(String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION));

					//Seteamos el P_NUM_EXPEDIENTE a los intervinientes
					numExpediente = (BigDecimal)resultadoTramiteTraficoGuardarMatriculacion.getParametro("P_NUM_EXPEDIENTE");
					log.info("Tramite de matriculación importado correctamente: " + numExpediente);
					beanAlta.getBeanGuardarTitular().setP_NUM_EXPEDIENTE(numExpediente);
					beanAlta.getBeanGuardarRepresentante().setP_NUM_EXPEDIENTE(numExpediente);
					beanAlta.getBeanGuardarArrendatario().setP_NUM_EXPEDIENTE(numExpediente);
					beanAlta.getBeanGuardarReprArrendatario().setP_NUM_EXPEDIENTE(numExpediente);
					beanAlta.getBeanGuardarConductorHabitual().setP_NUM_EXPEDIENTE(numExpediente);
					beanAlta.getBeanGuardarReprConductorHabitual().setP_NUM_EXPEDIENTE(numExpediente);
					
					//11/01/2024 Se añade al vehículo el campo Velocidad_maxima en la importación Siga
					BigDecimal velocidadMaxima = vehiculo.getP_VELOCIDAD_MAXIMA();
					if (velocidadMaxima != null && !velocidadMaxima.equals(0)) {
						servicioVehiculo.actualizarVelocidadMaxima(numExpediente, velocidadMaxima);
					}

					//ID_contrato
					beanAlta.getBeanGuardarTitular().setP_ID_CONTRATO(idContratoTramite);
					beanAlta.getBeanGuardarRepresentante().setP_ID_CONTRATO(idContratoTramite);
					beanAlta.getBeanGuardarArrendatario().setP_ID_CONTRATO(idContratoTramite);
					beanAlta.getBeanGuardarReprArrendatario().setP_ID_CONTRATO(idContratoTramite);
					beanAlta.getBeanGuardarConductorHabitual().setP_ID_CONTRATO(idContratoTramite);
					beanAlta.getBeanGuardarReprConductorHabitual().setP_ID_CONTRATO(idContratoTramite);

					String errorInterviniente = "";
					Boolean errorIntervinientes = false;

					//Ejecutamos el procedimiento de guardar el titular
					if (null != beanAlta.getBeanGuardarTitular().getP_NIF() && !"".equals(beanAlta.getBeanGuardarTitular().getP_NIF())) {
						resultadoTramiteTraficoGuardarIntervinienteTitular = ejecutarProc(beanAlta.getBeanGuardarTitular(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);
						//Recuperamos información de respuesta
						BigDecimal pCodeTitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente titular: " + pCodeTitular);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION));
						if (!ConstantesPQ.pCodeOk.equals(pCodeTitular)) {
							errorInterviniente += " -El titular no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION))
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION))?(" -Titular: " + (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Titular: "+(String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION));
						}
					} else if (null!=beanAlta.getBeanGuardarTitular().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanAlta.getBeanGuardarTitular().getP_APELLIDO1_RAZON_SOCIAL())) {
						errorInterviniente += " -El titular no se ha guardado: No tiene NIF";
						errorIntervinientes = true;
					}

					//Ejecutamos el procedimiento de guardar el representante en caso de que haya (que tenga NIF)
					if (null!=beanAlta.getBeanGuardarRepresentante().getP_NIF() && !"".equals(beanAlta.getBeanGuardarRepresentante().getP_NIF())) {

						BigDecimal pCodeRepresentante;
	//					BigDecimal pCodeRepresentante = ConstantesPQ.pCodeFaltaNombre;
	//					if (!utilConv.isNifNie(beanAlta.getBeanGuardarRepresentante().getP_NIF())) {
						resultadoTramiteTraficoGuardarIntervinienteRepresentante = ejecutarProc(beanAlta.getBeanGuardarRepresentante(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);
						//Recuperamos información de respuesta
						pCodeRepresentante = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente representante: " + pCodeRepresentante);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION));
	//					}

						if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentante)) {
							//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
							if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeRepresentante) && null!=beanAlta.getBeanGuardarRepresentante().getP_APELLIDO1_RAZON_SOCIAL() && beanAlta.getBeanGuardarRepresentante().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
								String todo = beanAlta.getBeanGuardarRepresentante().getP_APELLIDO1_RAZON_SOCIAL();
								HashMap<String,String> separado = separarApellidosNombre(todo);
								beanAlta.getBeanGuardarRepresentante().setP_NOMBRE(separado.get("nombre"));
								beanAlta.getBeanGuardarRepresentante().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

								//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
								resultadoTramiteTraficoGuardarIntervinienteRepresentante = ejecutarProc(beanAlta.getBeanGuardarRepresentante(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

								//Recuperamos información de respuesta
								pCodeRepresentante = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_CODE);
								log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente representante, otra vez: " + pCodeRepresentante);
								log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM));
								log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION));

								//Si ha vuelto a dar error el guardar representante
								if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentante)) {
									errorInterviniente += " -El representante no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
									errorIntervinientes = true;
								} else {
									representanteTitularNombreSlipt = true;
									//Si ha habido un mensaje de información, lo guardamos:
									if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentante && null!=resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION)) 
										mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
									if (representanteTitularNombreSlipt) mensajeWarnings += " -Representante:  No olvide revisar el nombre y apellidos del representante.";
									if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION));
								}
							} else {
								errorInterviniente += " -El representante no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							}
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentante && null!=resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION)) 
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";						
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION))) 
								resultado.setMensaje(resultado.getMensaje()+ " Representante: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION));
						}
					} else if (null!=beanAlta.getBeanGuardarRepresentante().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanAlta.getBeanGuardarRepresentante().getP_APELLIDO1_RAZON_SOCIAL())) {
						errorInterviniente += " -El representante no se ha guardado: No tiene NIF";
						errorIntervinientes = true;
					}

					//Ejecutamos el procedimiento de guardar el Arrendatario en caso de que haya (que tenga NIF)
					if (beanAlta.getBeanGuardarMatriculacion().getP_RENTING()!=null && !"NO".equalsIgnoreCase(beanAlta.getBeanGuardarMatriculacion().getP_RENTING())
							&& null!=beanAlta.getBeanGuardarArrendatario().getP_NIF() && !"".equals(beanAlta.getBeanGuardarArrendatario().getP_NIF())) {

						resultadoTramiteTraficoGuardarIntervinienteArrendatario = ejecutarProc(beanAlta.getBeanGuardarArrendatario(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

						//Recuperamos información de respuesta
						BigDecimal pCodeArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente arrendatario: " + pCodeArrendatario);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION));

						if (!ConstantesPQ.pCodeOk.equals(pCodeArrendatario)) {
							//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
							if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeArrendatario) && null!=beanAlta.getBeanGuardarArrendatario().getP_APELLIDO1_RAZON_SOCIAL() && beanAlta.getBeanGuardarArrendatario().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
								String todo = beanAlta.getBeanGuardarArrendatario().getP_APELLIDO1_RAZON_SOCIAL();
								HashMap<String,String> separado = separarNombreApellidos(todo);
								beanAlta.getBeanGuardarArrendatario().setP_NOMBRE(separado.get("nombre"));
								beanAlta.getBeanGuardarArrendatario().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

								//Lo volvemos a ejecutar, ahora con nombre porque el arrendatario es una persona física
								resultadoTramiteTraficoGuardarIntervinienteArrendatario = ejecutarProc(beanAlta.getBeanGuardarArrendatario(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

								//Recuperamos información de respuesta
								pCodeArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_CODE);
								log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente arrendatario, otra vez: " + pCodeArrendatario);
								log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM));
								log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION));

								//Si ha vuelto a dar error el guardar arrendatario
								if (!ConstantesPQ.pCodeOk.equals(pCodeArrendatario)) {
									errorInterviniente += " -El arrendatario no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
									errorIntervinientes = true;
								} else {
									//Si ha habido un mensaje de información, lo guardamos:
									if (null!=resultadoTramiteTraficoGuardarIntervinienteArrendatario && null!=resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION)) 
										mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION))?(" -Arrendatario: " + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
									if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Arrendatario: "+(String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION));
								}

							} else {
								errorInterviniente += " -El arrendatario no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							}
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null != resultadoTramiteTraficoGuardarIntervinienteArrendatario && null!=resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION)) 
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION)) ? (" -Arrendatario: " + (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION) + ".") : "";
							if (null != ((String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Arrendatario: "+(String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION));
						}
					} else if (beanAlta.getBeanGuardarMatriculacion().getP_RENTING()!=null && !"NO".equalsIgnoreCase(beanAlta.getBeanGuardarMatriculacion().getP_RENTING())
							&& null!=beanAlta.getBeanGuardarArrendatario().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanAlta.getBeanGuardarArrendatario().getP_APELLIDO1_RAZON_SOCIAL())) {
						errorInterviniente += " -El arrendatario no se ha guardado: No tiene NIF";
						errorIntervinientes = true;
					}

					//Ejecutamos el procedimiento de guardar el Representante del Arrendatario en caso de que haya (que tenga NIF)
					if (beanAlta.getBeanGuardarMatriculacion().getP_RENTING()!=null && !"NO".equalsIgnoreCase(beanAlta.getBeanGuardarMatriculacion().getP_RENTING()) 
							&& null!=beanAlta.getBeanGuardarReprArrendatario().getP_NIF() && !"".equals(beanAlta.getBeanGuardarReprArrendatario().getP_NIF())) {

						resultadoTramiteTraficoGuardarIntervinienteReprArrendatario = ejecutarProc(beanAlta.getBeanGuardarReprArrendatario(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW", BeanPQGeneral.class);

						//Recuperamos información de respuesta
						BigDecimal pCodeReprArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente representante del arrendatario: " + pCodeReprArrendatario);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION));

						if (!ConstantesPQ.pCodeOk.equals(pCodeReprArrendatario)) {
							//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
							if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeReprArrendatario) && null!=beanAlta.getBeanGuardarReprArrendatario().getP_APELLIDO1_RAZON_SOCIAL() && beanAlta.getBeanGuardarReprArrendatario().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length >= 2) {
								String todo = beanAlta.getBeanGuardarReprArrendatario().getP_APELLIDO1_RAZON_SOCIAL();
								HashMap<String,String> separado = separarNombreApellidos(todo);
								beanAlta.getBeanGuardarReprArrendatario().setP_NOMBRE(separado.get("nombre"));
								beanAlta.getBeanGuardarReprArrendatario().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

								//Lo volvemos a ejecutar, ahora con nombre porque el arrendatario es una persona física
								resultadoTramiteTraficoGuardarIntervinienteReprArrendatario = ejecutarProc(beanAlta.getBeanGuardarReprArrendatario(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

								//Recuperamos información de respuesta
								pCodeReprArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_CODE);
								log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente representante del arrendatario, otra vez: " + pCodeReprArrendatario);
								log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_SQLERRM));
								log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION));

								//Si ha vuelto a dar error el guardar arrendatario
								if (!ConstantesPQ.pCodeOk.equals(pCodeReprArrendatario)) {
									errorInterviniente += " -El representante del arrendatario no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
									errorIntervinientes = true;
								} else {
									//Si ha habido un mensaje de información, lo guardamos:
									if (null!=resultadoTramiteTraficoGuardarIntervinienteReprArrendatario && null!=resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION)) 
										mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION))?(" -Arrendatario: " + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
									if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Arrendatario: "+(String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION));
								}
							} else {
								errorInterviniente += " -El representante del arrendatario no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							}
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=resultadoTramiteTraficoGuardarIntervinienteReprArrendatario && null!=resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION)) 
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION))?(" -Arrendatario: " + (String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Arrendatario: "+(String)resultadoTramiteTraficoGuardarIntervinienteReprArrendatario.getParametro(ConstantesPQ.P_INFORMACION));
						}
					} else if (beanAlta.getBeanGuardarMatriculacion().getP_RENTING()!=null && !"NO".equalsIgnoreCase(beanAlta.getBeanGuardarMatriculacion().getP_RENTING())
							&& null!=beanAlta.getBeanGuardarReprArrendatario().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanAlta.getBeanGuardarReprArrendatario().getP_APELLIDO1_RAZON_SOCIAL())) {
						errorInterviniente += " -El representante del arrendatario no se ha guardado: No tiene NIF";
						errorIntervinientes = true;
					}

					//Ejecutamos el procedimiento de guardar el Conductor Habitual en caso de que haya (que tenga NIF)
					if (null!=beanAlta.getBeanGuardarConductorHabitual().getP_NIF() && !"".equals(beanAlta.getBeanGuardarConductorHabitual().getP_NIF())) {

						resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = ejecutarProc(beanAlta.getBeanGuardarConductorHabitual(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

						//Recuperamos información de respuesta
						BigDecimal pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente conductor habitual: " + pCodeConductorHabitual);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));

						if (!ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual)) {
							errorInterviniente += " -El conductor habitual no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=resultadoTramiteTraficoGuardarIntervinienteConductorHabitual && null!=resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION)) 
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))?(" -Conductor Habitual: " + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " ConductorHabitual: "+(String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));
						}
					}
	//				else if (null!=beanAlta.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanAlta.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL())) {
	//					errorInterviniente += " -El conductor habitual no se ha guardado: No tiene NIF";
	//					errorIntervinientes = true;
	//				}

					//Ejecutamos el procedimiento de guardar el Representante del Conductor Habitual en caso de que haya (que tenga NIF)
					if (null != beanAlta.getBeanGuardarReprConductorHabitual().getP_NIF() && !"".equals(beanAlta.getBeanGuardarReprConductorHabitual().getP_NIF())) {

						resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual = ejecutarProc(beanAlta.getBeanGuardarReprConductorHabitual(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERV_IMPOR_MATW",BeanPQGeneral.class);

						//Recuperamos información de respuesta
						BigDecimal pCodeReprConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el interviniente conductor habitual: " + pCodeReprConductorHabitual);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));

						if (!ConstantesPQ.pCodeOk.equals(pCodeReprConductorHabitual)) {
							errorInterviniente += " -El representante del conductor habitual no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						} else {
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual && null!=resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION)) 
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))?(" -Conductor Habitual: " + (String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " ConductorHabitual: "+(String)resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));
						}
					}

					//Si alguno de los intervinientes no se ha guardado o ha dado información, devolvemos el error, pero el trámite diremos que ha sido correcto (pues el trámite se ha guardado)
					if (errorIntervinientes || !"".equals(mensajeWarnings)) {
						resultado.setError(false);
						resultado.setMensaje("Trámite (" + numExpediente + ") guardado. Información:" + errorInterviniente + mensajeWarnings);
					} else {
						resultado.setError(false);
						resultado.setMensaje("Trámite (" + numExpediente + ") guardado.");
					}
				} else { //El tramite no se ha guardado bien
					// Eliminar vehiculo
					if (resultadoVehiculo != null && resultadoVehiculo.getParametro("P_CODE") != null && resultadoVehiculo.getParametro("P_ID_VEHICULO") != null
							&& ((BigDecimal) resultadoVehiculo.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
						BigDecimal idVehiculo = (BigDecimal) resultadoVehiculo.getParametro("P_ID_VEHICULO");
						ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);
						servicioVehiculo.eliminar(idVehiculo.longValue());
					}
					resultado.setError(true);
					resultado.setMensaje(OraUtiles.traducirError((String) resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_SQLERRM)));
				}
			} else { //El vehículo no se ha guardado bien
				resultado.setError(true);
				resultado.setMensaje("El trámite no se ha guardado. -Hay un error en el vehículo: " + OraUtiles.traducirError((String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM)));
			}

		}

		//Generamos el bean de pantalla, para el caso de que se haya guardado un trámite de XML único (mate)
		TramiteTraficoMatriculacionBean tramiteRespuesta = null;
		if (numExpediente != null && xmlmate) {
			tramiteRespuesta = new TramiteTraficoMatriculacionBean();
			tramiteRespuesta=matriculacionTramiteTraficoBeanMatwPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarMatriculacion);

			if (resultadoVehiculo != null) {
				tramiteRespuesta.getTramiteTraficoBean().setVehiculo(vehiculoBeanPQConversion.convertirPQToBean(resultadoVehiculo));
				if (tramiteRespuesta.getTramiteTraficoBean().getVehiculo() != null) {
					tramiteRespuesta.getTramiteTraficoBean().getVehiculo().setHomologacionBean(servicioDirectivaCee.getHomologacionBean((String)resultadoVehiculo.getParametro("P_ID_DIRECTIVA_CEE")));
				}
				tramiteRespuesta.setVehiculoPreverBean(vehiculoBeanPQConversion.convertirPQToBeanPrever(resultadoVehiculo));
			}

			if (resultadoTramiteTraficoGuardarIntervinienteTitular != null)
				tramiteRespuesta.setTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteTitular));

			if ( resultadoTramiteTraficoGuardarIntervinienteArrendatario != null)
				tramiteRespuesta.setArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteArrendatario));

			if (resultadoTramiteTraficoGuardarIntervinienteReprArrendatario != null)
				tramiteRespuesta.setRepresentanteArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteReprArrendatario));

			if (resultadoTramiteTraficoGuardarIntervinienteRepresentante != null)
				tramiteRespuesta.setRepresentanteTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentante));

			if (resultadoTramiteTraficoGuardarIntervinienteConductorHabitual != null)
				tramiteRespuesta.setConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteConductorHabitual));

			if (resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual != null)
				tramiteRespuesta.setRepresentanteConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteReprConductorHabitual));
		}

		log.debug(ConstantesPQ.MENSAJE + resultado.getMensaje());
		resultado.addAttachment(ConstantesPQ.BEANPANTALLA, tramiteRespuesta); // Utilizado solo en caso de que sea importación XML-MATE
		//TODO MPC. IVTM
		//Se ha hecho para el caso de IVTM y de EEFF para recuperar el numExpediente
		if(numExpediente!=null){
			resultado.addAttachment("numExpediente", numExpediente);
		}

		return resultado;
	}

	private HashMap<String, String> separarNombreApellidos(String todo) {
		HashMap<String,String> respuesta = new HashMap<String,String>();
		String[] palabras = todo.trim().split(" ");
		if (palabras.length < 2) {
			if (palabras.length == 1) {
				respuesta.put("nombre",".");
				respuesta.put("apellidos", palabras[0]);
				return respuesta;
			} else {
				respuesta.put("nombre",".");
				respuesta.put("apellidos", ".");
				return respuesta;
			}
		}
		String nombre = palabras[0];
		String apellidos = todo.trim().substring(nombre.length());

		respuesta.put("nombre",nombre.trim());
		respuesta.put("apellidos", apellidos.trim());

		return respuesta;
	}

	private HashMap<String, String> separarApellidosNombre(String todo) {
		HashMap<String,String> respuesta = new HashMap<String,String>();
		String[] palabras = todo.trim().split(" ");
		if (palabras.length<2) {
			if (palabras.length==1) {
				respuesta.put("nombre",".");
				respuesta.put("apellidos", palabras[0]);
				return respuesta;
			} else {
				respuesta.put("nombre",".");
				respuesta.put("apellidos", ".");
				return respuesta;
			}
		}
		String nombre = palabras[palabras.length-1];
		String apellidos = todo.trim().substring(0,todo.length()-nombre.length());
		respuesta.put("nombre",nombre.trim());
		respuesta.put("apellidos", apellidos.trim());
		return respuesta;
	}

	/**
	 * Simula la respuesta de una llamada al procedimiento de guardar vehículo sin matrícula
	 * @return
	 */
	private RespuestaGenerica resultadoNoBastidorOCoditv() {
		RespuestaGenerica resultadoVeh = new RespuestaGenerica();
		HashMap<String,Object> paramManual = new HashMap<String,Object>();
		paramManual.put(ConstantesPQ.P_CODE, new BigDecimal(1));
		paramManual.put(ConstantesPQ.P_SQLERRM, "El vehículo no tiene codigo de itv ni número de bastidor válido, no se puede guardar");
		resultadoVeh.setMapaParametros(paramManual);
		return resultadoVeh;
	}

	/**
	 * Simula la respuesta de una llamada al procedimiento de guardar vehículo sin matrícula
	 * @return
	 */
	private RespuestaGenerica resultadoNoMatriculaOCoditv() {
		RespuestaGenerica resultadoVeh = new RespuestaGenerica();
		HashMap<String,Object> paramManual = new HashMap<String,Object>();
		paramManual.put(ConstantesPQ.P_CODE, new BigDecimal(1));
		paramManual.put(ConstantesPQ.P_SQLERRM, "El vehículo no tiene matrícula ni número de bastidor válido, no se puede guardar");
		resultadoVeh.setMapaParametros(paramManual);
		return resultadoVeh;
	}

	/**
	 * Método que irá llamando a los procedimientos almacenados necesarios para realizar una TRANSMISION mediante importación
	 * de fichero DGT
	 * @param BeanPQTramiteTransmisionImport
	 * @return
	 * @throws OegamExcepcion 
	 */
	public HashMap<String, Object> guardarTransmisionImport (
			BeanPQTramiteTransmisionImport beanTransmision, BigDecimal idContratoTramite,BigDecimal idUsuario , BigDecimal idContrato,
			TipoCreacion tipoCreacion) throws OegamExcepcion {
		HashMap<String,Object> resultadoTransmision = new HashMap<String,Object>();
		ResultBean resultado = new ResultBean();
		BigDecimal numExpediente = null;
		Boolean PoseedorNombreSlipt = false;
		Boolean representanteAdquirienteNombreSlipt = false;
		Boolean representanteTransmitenteNombreSlipt = false;
		Boolean representantePoseedorNombreSlipt = false;
		Boolean representantePresentadorNombreSlipt = false;
		RespuestaGenerica resultadoVehiculo = null;
		String mensajeWarnings = "";

		if (idContratoTramite == null) 
			idContratoTramite = idContrato;

		BeanPQVehiculosGuardarImport vehiculo = beanTransmision.getBeanGuardarVehiculo();
		BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF vehiculoTramite = new BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF();
		//Seteamos los parámetros de control del vehículo
		vehiculo.setP_ID_CONTRATO_SESSION(idContrato);
		vehiculo.setP_ID_USUARIO(idUsuario);
		vehiculo.setP_NUM_EXPEDIENTE(null);
		vehiculo.setP_TIPO_TRAMITE(TipoTramiteTrafico.Transmision.getValorEnum());
		vehiculo.setP_ID_CONTRATO(idContratoTramite);

		//Vamos a guardar el vehiculo primero (si por lo menos tiene matrícula o bastidor)
		if ((!"".equals(vehiculo.getP_MATRICULA()) && null!=vehiculo.getP_MATRICULA()) || (!"".equals(vehiculo.getP_BASTIDOR()) && null != vehiculo.getP_BASTIDOR() && vehiculo.getP_BASTIDOR().length() == 17)) {
			vehiculo.setP_MATRICULA(StringUtils.isNotBlank(vehiculo.getP_MATRICULA()) ? vehiculo.getP_MATRICULA().trim() : vehiculo.getP_MATRICULA());
			resultadoVehiculo = ejecutarProc(vehiculo, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS, "GUARDAR_IMPORT", BeanPQGeneral.class);
		} else {
			//Si no tiene matrícula, no llamamos al procedimiento pero devolvemos un resultado como si lo hubiera hecho
			resultadoVehiculo = resultadoNoMatriculaOCoditv();
		}

		//Recuperamos información de respuesta
		BigDecimal pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el vehículo: " + pCodeVehiculo);
		log.debug(ConstantesPQ.LOG_P_SQLERRM+ (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));

		//Si ha ido bien la operación de guardar vehículo, continuamos guardando el trámite
		if ((ConstantesPQ.pCodeOk.equals(pCodeVehiculo))) {
			//Si ha habido un mensaje de información, lo guardamos:
			if (null != resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION))
				mensajeWarnings += !"".equals((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION)) ? (" -Vehiculo: " + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION) + ".") : "";
			if (null != ((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Vehículo: "+(String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));

			//Seteamos el P_ID_VEHICULO
			BigDecimal idVehiculo = (BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO");
			beanTransmision.getBeanGuardarTransmision().setP_ID_VEHICULO(idVehiculo);

			//Id_contrato
			beanTransmision.getBeanGuardarTransmision().setP_ID_CONTRATO(idContratoTramite);
			beanTransmision.getBeanGuardarTransmision().setP_NUM_EXPEDIENTE(null);

			if ("328".equals(idContrato.toString())) {
				if (TipoTransferencia.tipo5.getValorEnum().equals(beanTransmision.getBeanGuardarTransmision().getP_TIPO_TRANSFERENCIA()) || TipoTransferencia.tipo4.getValorEnum().equals(beanTransmision
						.getBeanGuardarTransmision().getP_TIPO_TRANSFERENCIA())) {
					beanTransmision.getBeanGuardarTransmision().setP_IMPR_PERMISO_CIRCU("NO");
					mensajeWarnings += " No se imprimira el permiso de circulación definitivo en una transmision de Entrega Compraventa o Notificación Cambio Titularidad";
				} else {
					if (beanTransmision.getBeanGuardarTransmision().getP_IMPR_PERMISO_CIRCU() != null && !beanTransmision.getBeanGuardarTransmision().getP_IMPR_PERMISO_CIRCU().isEmpty()) {
						beanTransmision.getBeanGuardarTransmision().setP_IMPR_PERMISO_CIRCU(beanTransmision.getBeanGuardarTransmision().getP_IMPR_PERMISO_CIRCU().trim().toUpperCase());
					} else {
						beanTransmision.getBeanGuardarTransmision().setP_IMPR_PERMISO_CIRCU("SI");
					}
				}
			} else {
				if (TipoTransferencia.tipo5.getValorEnum().equals(beanTransmision.getBeanGuardarTransmision().getP_TIPO_TRANSFERENCIA()) || TipoTransferencia.tipo4.getValorEnum().equals(beanTransmision
						.getBeanGuardarTransmision().getP_TIPO_TRANSFERENCIA())) {
					beanTransmision.getBeanGuardarTransmision().setP_IMPR_PERMISO_CIRCU("NO");
					mensajeWarnings += " No se imprimira el permiso de circulación definitivo en una transmision de Entrega Compraventa o Notificación Cambio Titularidad";
				} else {
					beanTransmision.getBeanGuardarTransmision().setP_IMPR_PERMISO_CIRCU("SI");
				}
			}

			//Quitamos la jefatura a mano, pues en BD se seleccionará la jefatura del contrato
			//beanTransmision.getBeanGuardarTransmision().setP_JEFATURA_PROVINCIAL(null);

			// A partir de ahora se podran realizar transmisiones de todas las jefaturas provinciales.
			beanTransmision.getBeanGuardarTransmision().setP_JEFATURA_PROVINCIAL(beanTransmision.getBeanGuardarTransmision().getP_JEFATURA_PROVINCIAL());

			//DRC@14-02-2013 Incidencia Mantis: 2708
			beanTransmision.getBeanGuardarTransmision().setP_ID_TIPO_CREACION(new BigDecimal(tipoCreacion.getValorEnum()));

			//Quitamos la jefatura a mano, pues en BD se seleccionará la jefatura del contrato
			beanTransmision.getBeanGuardarTransmision().setP_NUM_TITULARES(beanTransmision.getBeanGuardarTransmitente() != null ?
					beanTransmision.getBeanGuardarTransmitente().getP_NUM_TITULARES() : null);

			//JRG 0003728: Mejora para indicar cuando es una transferencia simultanea
			//Esto es particular a OEGAM y no se enviara a DGT por lo que al improtar no existirán estos campos.
			beanTransmision.getBeanGuardarTransmision().setP_SIMULTANEA(beanTransmision.getBeanGuardarTransmision().getP_SIMULTANEA()!=null?
					beanTransmision.getBeanGuardarTransmision().getP_SIMULTANEA():new BigDecimal(-1));

			// Ejecutamos el procedimiento de guardar el trámite de transmision
			RespuestaGenerica resultadoTramiteTraficoGuardarTransmision = ejecutarProc(beanTransmision.getBeanGuardarTransmision(), valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_TRANSMISION", BeanPQGeneral.class);

			//Mantis 25415 Aquí actualizamos el valor del campo valor real
			if (beanTransmision.getBeanGuardarTransmision().getP_VALOR_REAL() != null) {
				BigDecimal valor = beanTransmision.getBeanGuardarTransmision().getP_VALOR_REAL();
				if (valor != null) {
					int longitudCorte = 0;
					if (valor.toString().indexOf(".") != -1) {
						longitudCorte = valor.toString().indexOf(".");
					} else if (valor.toString().indexOf(",") != -1) {
						longitudCorte = valor.toString().indexOf(",");
					}
					if (longitudCorte != 0 && valor.toString().substring(longitudCorte).length() > 3) {
						mensajeWarnings += " -Tramite: No se puede introducir m\u00E1s de dos decimales en el valor real,"
								+ " se ha añadido s\u00F3lo con dos decimales.";
						valor = new BigDecimal(valor.toString().substring(0, longitudCorte+3));
					}
	
					servicioTramiteTraficoTransmision.actualizarValorRealTransmision((BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE"),
						valor);
				}
			}

			servicioTramiteTraficoTransmision.actualizarDatos620((BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE"),beanTransmision.getBeanGuardarTransmision() );

			// Recuperamos información de respuesta
			BigDecimal pCodeTramite = (BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_CODE);
			log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el tramite de transmision: " + pCodeTramite);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));

			//Si el trámite se ha guardado bien, continuamos guardando los intervinientes
			if ((ConstantesPQ.pCodeOk.equals(pCodeTramite))) {
				// DRC@25-09-2012 Incidencia: 2358 y 2667		
				vehiculoTramite.setP_CODE(vehiculo.getP_CODE());
				if (resultadoTramiteTraficoGuardarTransmision.getParametro("P_ID_VEHICULO") != null)
					vehiculoTramite.setP_ID_VEHICULO(new BigDecimal (resultadoTramiteTraficoGuardarTransmision.getParametro("P_ID_VEHICULO").toString()));
				vehiculoTramite.setP_INFORMACION(vehiculo.getP_INFORMACION());
				if (vehiculo.getP_KILOMETRAJE() != null && !vehiculo.getP_KILOMETRAJE().equalsIgnoreCase(""))
					vehiculoTramite.setP_KILOMETROS(new BigDecimal (vehiculo.getP_KILOMETRAJE()));
				else
					vehiculoTramite.setP_KILOMETROS(null);
				vehiculoTramite.setP_NUM_COLEGIADO(vehiculo.getP_NUM_COLEGIADO());

				if (resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE") != null)
					vehiculoTramite.setP_NUM_EXPEDIENTE(new BigDecimal(resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE").toString()));
				ejecutarProc(vehiculoTramite, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS, "GUARDAR_VEHICULO_TRAMITE_TRAF", BeanPQGeneral.class);

				//Si ha habido un mensaje de información, lo guardamos:
				if (null != resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION))
					mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION))?(" -Tramite: " + (String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
				if (null != ((String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Tramite: "+(String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION));					

				// Seteamos el P_NUM_EXPEDIENTE a los intervinientes
				numExpediente = (BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE");
				log.info("Tramite de transmisión importado correctamente: " + numExpediente);
				beanTransmision.getBeanGuardarAdquiriente().setP_NUM_EXPEDIENTE(numExpediente);
				// DRC@02-10-2012 Incidencia: 1801
				beanTransmision.getBeanGuardarPoseedor().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarArrendador().setP_NUM_EXPEDIENTE(numExpediente);
				//beanTransmision.getBeanGuardarCompraventa().setP_NUM_EXPEDIENTE(numExpediente);

				beanTransmision.getBeanGuardarTransmitente().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarRepresentanteAdquiriente().setP_NUM_EXPEDIENTE(numExpediente);
				// DRC@02-10-2012 Incidencia: 1801
				beanTransmision.getBeanGuardarRepresentantePoseedor().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarRepresentanteArrendador().setP_NUM_EXPEDIENTE(numExpediente);
				//beanTransmision.getBeanGuardarRepresentanteCompraVenta().setP_NUM_EXPEDIENTE(numExpediente);

				beanTransmision.getBeanGuardarRepresentanteTransmitente().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarPresentador().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarConductorHabitual().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarPrimerCotitular().setP_NUM_EXPEDIENTE(numExpediente);
				beanTransmision.getBeanGuardarSegundoCotitular().setP_NUM_EXPEDIENTE(numExpediente);

				// ID_CONTRATO
				beanTransmision.getBeanGuardarAdquiriente().setP_ID_CONTRATO(idContratoTramite);
				// DRC@02-10-2012 Incidencia: 1801
				beanTransmision.getBeanGuardarPoseedor().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarArrendador().setP_ID_CONTRATO(idContratoTramite);
				//beanTransmision.getBeanGuardarCompraventa().setP_ID_CONTRATO(idContratoTramite);

				beanTransmision.getBeanGuardarTransmitente().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarRepresentanteAdquiriente().setP_ID_CONTRATO(idContratoTramite);
				// DRC@02-10-2012 Incidencia: 1801
				beanTransmision.getBeanGuardarRepresentantePoseedor().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarRepresentanteArrendador().setP_ID_CONTRATO(idContratoTramite);
				//beanTransmision.getBeanGuardarRepresentanteCompraVenta().setP_ID_CONTRATO(idContratoTramite);

				beanTransmision.getBeanGuardarRepresentanteTransmitente().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarPresentador().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarSegundoCotitular().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarPrimerCotitular().setP_ID_CONTRATO(idContratoTramite);
				beanTransmision.getBeanGuardarConductorHabitual().setP_ID_CONTRATO(idContratoTramite);

				String errorInterviniente = "";
				Boolean errorIntervinientes = false;

				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteAdquiriente = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteTransmitente = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente = new RespuestaGenerica();
				// DRC@02-10-2012 Incidencia: 1801
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePoseedor = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteArrendador = new RespuestaGenerica();
				//RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteCompraVenta = new RespuestaGenerica();

				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador = new RespuestaGenerica();
				//RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteCompraVenta = new RespuestaGenerica();

				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePresentador = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePrimerCotitular = new RespuestaGenerica();
				RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = new RespuestaGenerica();

				//Ejecutamos el procedimiento de guardar el ADQUIRIENTE en caso de que haya (que tenga NIF)
				if (null != beanTransmision.getBeanGuardarAdquiriente().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarAdquiriente().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteAdquiriente = ejecutarProc(beanTransmision.getBeanGuardarAdquiriente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					BigDecimal pCodeAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Adquiriente: " + pCodeAdquiriente);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));

					if (!ConstantesPQ.pCodeOk.equals(pCodeAdquiriente)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeAdquiriente) && null!=beanTransmision.getBeanGuardarAdquiriente().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarAdquiriente().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarAdquiriente().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarAdquiriente().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarAdquiriente().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteAdquiriente = ejecutarProc(beanTransmision.getBeanGuardarAdquiriente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el adquiriente, otra vez: " + pCodeAdquiriente);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeAdquiriente)) {
								errorInterviniente += " -El adquiriente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								//Si ha habido un mensaje de información, lo guardamos:
								if (null != resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Adquiriente: " + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (null != ((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Adquiriente: "+(String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El adquiriente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null != resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Adquiriente: " + (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null != ((String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Adquiriente: "+(String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarAdquiriente().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarAdquiriente().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El adquiriente no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Ejecutamos el procedimiento de guardar el REPRESENTANTE DEL ADQUIRIENTE en caso de que haya (que tenga NIF)
				if (null != beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_NIF())) {

					BigDecimal pCodeRepresentanteAdquiriente;
//					BigDecimal pCodeRepresentanteAdquiriente = ConstantesPQ.pCodeFaltaNombre;
//					if (!utilConv.isNifNie(beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteAdquiriente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodeRepresentanteAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteAdquiriente: " + pCodeRepresentanteAdquiriente);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));
//					}

					if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteAdquiriente)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeRepresentanteAdquiriente) && null!=beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarRepresentanteAdquiriente().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarRepresentanteAdquiriente().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteAdquiriente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeRepresentanteAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteAdquiriente, otra vez: " + pCodeRepresentanteAdquiriente);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteAdquiriente)) {
								errorInterviniente += " -El representante del adquiriente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								representanteAdquirienteNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del adquiriente: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (representanteAdquirienteNombreSlipt) mensajeWarnings += " -Representante del adquiriente: No olvide revisar el nombre y apellidos del representante del adquiriente.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null != ((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del Adquiriente: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));
							}
						} else {
							errorInterviniente += " -El representante del adquiriente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null != resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del adquiriente: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null != ((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del Adquiriente: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION));
					}
				} else if (null!=beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarRepresentanteAdquiriente().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El representante del adquiriente no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Ejecutamos el procedimiento de guardar el Transmitente en caso de que haya (que tenga NIF)
				if (null != beanTransmision.getBeanGuardarTransmitente().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarTransmitente().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteTransmitente = ejecutarProc(beanTransmision.getBeanGuardarTransmitente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					BigDecimal pCodeTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Transmitente: " + pCodeTransmitente);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));

					if (!ConstantesPQ.pCodeOk.equals(pCodeTransmitente)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeTransmitente) && null != beanTransmision.getBeanGuardarTransmitente().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarTransmitente().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length >= 2) {
							String todo = beanTransmision.getBeanGuardarTransmitente().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarTransmitente().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarTransmitente().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteTransmitente = ejecutarProc(beanTransmision.getBeanGuardarTransmitente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Transmitente, otra vez: " + pCodeTransmitente);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeTransmitente)) {
								errorInterviniente += " -El transmitente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Transmitente: " + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Transmitente: "+(String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El Transmitente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Transmitente: " + (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Transmitente: "+(String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarTransmitente().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarTransmitente().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El transmitente no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Ejecutamos el procedimiento de guardar el RepresentanteTransmitente en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_NIF())) {
					BigDecimal pCodeRepresentanteTransmitente;
//					BigDecimal pCodeRepresentanteTransmitente = ConstantesPQ.pCodeFaltaNombre;
//					if (!utilConv.isNifNie(beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteTransmitente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodeRepresentanteTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteTransmitente: " + pCodeRepresentanteTransmitente);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));
//					}
					if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteTransmitente)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeRepresentanteTransmitente) && null!=beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarRepresentanteTransmitente().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarRepresentanteTransmitente().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteTransmitente(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeRepresentanteTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteTransmitente, otra vez: " + pCodeRepresentanteTransmitente);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteTransmitente)) {
								errorInterviniente += " -El representante del transmitente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								representanteTransmitenteNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del transmitente: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (representanteTransmitenteNombreSlipt) mensajeWarnings += " -Representante del transmitente: No olvide revisar el nombre y apellidos del representante del transmitente.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del transmitente: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El RepresentanteTransmitente no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del transmitente: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del transmitente: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarRepresentanteTransmitente().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El representante del transmitente no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				// DRC@02-10-2012 Incidencia: 1801
				//Ejecutamos el procedimiento de guardar el Poseedor en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarArrendador().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarArrendador().getP_NIF())) {
					BigDecimal pCodeArrendador;
//					BigDecimal pCodePoseedor = ConstantesPQ.pCodeFaltaNombre;
//					if (!utilConv.isNifNie(beanTransmision.getBeanGuardarPoseedor().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteArrendador = ejecutarProc(beanTransmision.getBeanGuardarArrendador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodeArrendador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Arrendador: " + pCodeArrendador);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION));
//					}
					if (!ConstantesPQ.pCodeOk.equals(pCodeArrendador)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeArrendador) && null!=beanTransmision.getBeanGuardarArrendador().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarArrendador().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarArrendador().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarArrendador().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarArrendador().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteArrendador = ejecutarProc(beanTransmision.getBeanGuardarArrendador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeArrendador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Arrendador, otra vez: " + pCodeArrendador);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeArrendador)) {
								errorInterviniente += " -El Arrendador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								PoseedorNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (PoseedorNombreSlipt) mensajeWarnings += " -Poseedor: No olvide revisar el nombre y apellidos del Poseedor.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Poseedor: "+(String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El Arrendador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Poseedor: "+(String)resultadoTramiteTraficoGuardarIntervinienteArrendador.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarArrendador().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarArrendador().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El Arrendador no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				// DRC@02-10-2012 Incidencia: 1801
				//Ejecutamos el procedimiento de guardar el RepresentantePoseedor en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarRepresentanteArrendador().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarRepresentanteArrendador().getP_NIF())) {
					BigDecimal pCodeRepresentanteArrendador;
					resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteArrendador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodeRepresentanteArrendador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteArrendador: " + pCodeRepresentanteArrendador);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION));
//					}

					if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteArrendador)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeRepresentanteArrendador) && null!=beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarRepresentanteArrendador().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarRepresentanteArrendador().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarRepresentanteArrendador().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarRepresentanteArrendador().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador = ejecutarProc(beanTransmision.getBeanGuardarRepresentanteArrendador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeRepresentanteArrendador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteArrendador, otra vez: " + pCodeRepresentanteArrendador);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION));
							
							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteArrendador)) {
								errorInterviniente += " -El representante del Arrendador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								representantePoseedorNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (representantePoseedorNombreSlipt) mensajeWarnings += " -Representante del CompraVenta: No olvide revisar el nombre y apellidos del representante del Arrendador.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del Arrendador: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El representante del Arrendador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del CompraVenta: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del CompraVenta: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendador.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarRepresentanteArrendador().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarRepresentanteArrendador().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El representante del Arrendador no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				// DRC@02-10-2012 Incidencia: 1801
				//Ejecutamos el procedimiento de guardar el Poseedor en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarPoseedor().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarPoseedor().getP_NIF())) {
					BigDecimal pCodePoseedor;
//					BigDecimal pCodePoseedor = ConstantesPQ.pCodeFaltaNombre;
//					if (!utilConv.isNifNie(beanTransmision.getBeanGuardarPoseedor().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinientePoseedor = ejecutarProc(beanTransmision.getBeanGuardarPoseedor(), valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "GUARDAR_INTERVINIENTE_IMPOR", BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el CompraVenta: " + pCodePoseedor);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION));
//					}
					if (!ConstantesPQ.pCodeOk.equals(pCodePoseedor)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodePoseedor) && null!=beanTransmision.getBeanGuardarPoseedor().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarPoseedor().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarPoseedor().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarPoseedor().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarPoseedor().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinientePoseedor = ejecutarProc(beanTransmision.getBeanGuardarPoseedor(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el CompraVenta, otra vez: " + pCodePoseedor);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodePoseedor)) {
								errorInterviniente += " -El CompraVenta no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								PoseedorNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))?(" -Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (PoseedorNombreSlipt) mensajeWarnings += " -Poseedor: No olvide revisar el nombre y apellidos del Poseedor.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Poseedor: "+(String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El CompraVenta no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))?(" -Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Poseedor: "+(String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarPoseedor().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarPoseedor().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El CompraVenta no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Ejecutamos el procedimiento de guardar el RepresentantePoseedor en caso de que haya (que tenga NIF)
				if (null != beanTransmision.getBeanGuardarRepresentantePoseedor().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarRepresentantePoseedor().getP_NIF())) {
					BigDecimal pCodeRepresentantePoseedor;
//					BigDecimal pCodeRepresentantePoseedor = ConstantesPQ.pCodeFaltaNombre;
//					if (!utilConv.isNifNie(beanTransmision.getBeanGuardarRepresentantePoseedor().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor = ejecutarProc(beanTransmision.getBeanGuardarRepresentantePoseedor(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					pCodeRepresentantePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteCompraVenta: " + pCodeRepresentantePoseedor);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION));
//					}

					if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentantePoseedor)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeRepresentantePoseedor) && null!=beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL()
								&& beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length >= 2) {
							String todo = beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarRepresentantePoseedor().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarRepresentantePoseedor().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor = ejecutarProc(beanTransmision.getBeanGuardarRepresentantePoseedor(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeRepresentantePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el RepresentanteCompraVenta, otra vez: " + pCodeRepresentantePoseedor);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentantePoseedor)) {
								errorInterviniente += " -El representante del CompraVenta no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								representantePoseedorNombreSlipt = true;
								if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del Poseedor: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (representantePoseedorNombreSlipt) mensajeWarnings += " -Representante del CompraVenta: No olvide revisar el nombre y apellidos del representante del Poseedor.";
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del Poseedor: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El representante del CompraVenta no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))?(" -Representante del CompraVenta: " + (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Representante del CompraVenta: "+(String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarRepresentantePoseedor().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El representante del CompraVenta no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//NUEVO: si no tiene presentador, lo guardaremos con los datos del usuario de sesión
				//Como el usuario de sesión siempre va a estar dado de alta, con meter el NIF valdrá.
				//creamos uno nuevo, por si traía otros datos pero no el nif, no los machaque
				BeanPQTramiteTraficoGuardarIntervinienteImport nuevo = new BeanPQTramiteTraficoGuardarIntervinienteImport();
				nuevo.setP_ID_CONTRATO(idContratoTramite);
				nuevo.setP_ID_CONTRATO_SESSION(idUsuario);
				nuevo.setP_ID_USUARIO(idContrato);
				nuevo.setP_NUM_EXPEDIENTE(numExpediente);

				Persona gestoria = getModeloColegiado().obtenerColegiadoCompleto(idContratoTramite);
				nuevo.setP_NIF(gestoria.getNif());
				nuevo.setP_NUM_COLEGIADO(beanTransmision.getBeanGuardarTransmision().getP_NUM_COLEGIADO());

				nuevo.setP_TIPO_INTERVINIENTE(TipoInterviniente.PresentadorTrafico.getValorEnum());
				beanTransmision.setBeanGuardarPresentador(nuevo);

				//Ejecutamos el procedimiento de guardar el Presentador en caso de que haya (que tenga NIF)
				resultadoTramiteTraficoGuardarIntervinientePresentador = ejecutarProc(beanTransmision.getBeanGuardarPresentador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodePresentador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_CODE);
				log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Presentador: " + pCodePresentador);
				log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_SQLERRM));
				log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION));

				if (!ConstantesPQ.pCodeOk.equals(pCodePresentador)) {
					//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
					if (ConstantesPQ.pCodeFaltaNombre.equals(pCodePresentador) && null!=beanTransmision.getBeanGuardarPresentador().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarPresentador().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
						String todo = beanTransmision.getBeanGuardarPresentador().getP_APELLIDO1_RAZON_SOCIAL();
						HashMap<String,String> separado = separarNombreApellidos(todo);
						beanTransmision.getBeanGuardarPresentador().setP_NOMBRE(separado.get("nombre"));
						beanTransmision.getBeanGuardarPresentador().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

						//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
						resultadoTramiteTraficoGuardarIntervinientePresentador = ejecutarProc(beanTransmision.getBeanGuardarPresentador(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

						//Recuperamos información de respuesta
						pCodePresentador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_CODE);
						log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Presentador, otra vez: " + pCodePresentador);
						log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_SQLERRM));
						log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION));

						//Si ha vuelto a dar error el guardar representante
						if (!ConstantesPQ.pCodeOk.equals(pCodePresentador)) {
							errorInterviniente += " -El presentador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						} else {
							representantePresentadorNombreSlipt = true;
							if (null!=resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))
								mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Presentador: " + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
							if (representantePresentadorNombreSlipt) mensajeWarnings += " -Presentador: No olvide revisar el nombre y apellidos del presentador.";
							//Si ha habido un mensaje de información, lo guardamos:
							if (null!=((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Presentador: "+(String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION));								
						}
					} else {
						errorInterviniente += " -El presentador no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
						errorIntervinientes = true;
					}
				} else {
					if (null != resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))
						mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))?(" -Presentador: " + (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
					//Si ha habido un mensaje de información, lo guardamos:
					if (null != ((String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Presentador: "+(String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION));						
				}

				// Nuevo XSD, nuevos intervinientes Conductor Habitual, Primer Cotitular, Segundo Cotitular
				// Conductor Habitual				
				// Ejecutamos el procedimiento de guardar el conductor habitual en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarConductorHabitual().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarConductorHabitual().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = ejecutarProc(beanTransmision.getBeanGuardarConductorHabitual(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					BigDecimal pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Conductor Habitual: " + pCodeConductorHabitual);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));

					if (!ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeConductorHabitual) && null!=beanTransmision.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarConductorHabitual().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarConductorHabitual().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = ejecutarProc(beanTransmision.getBeanGuardarConductorHabitual(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Conductor Habitual, otra vez: " + pCodeConductorHabitual);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual)) {
								errorInterviniente += " -El Conductor Habitual no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))?(" -Conductor habitual: " + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Conductor habitual: "+(String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));								
							}
						} else {
							errorInterviniente += " -El Conductor Habitual no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))?(" -Conductor habitual: " + (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION))) resultado.setMensaje(resultado.getMensaje()+ " Conductor habitual: "+(String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION));						
					}
				} else if (null!=beanTransmision.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarConductorHabitual().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El Conductor habitual no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Primer Cotitular
				//Ejecutamos el procedimiento de guardar el PrimerCotitular en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarPrimerCotitular().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarPrimerCotitular().getP_NIF())) {
					resultadoTramiteTraficoGuardarIntervinientePrimerCotitular = ejecutarProc(beanTransmision.getBeanGuardarPrimerCotitular(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					BigDecimal pCodePrimerCotitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Primer Cotitular: " + pCodePrimerCotitular);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION));

					if (!ConstantesPQ.pCodeOk.equals(pCodePrimerCotitular)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodePrimerCotitular) && null!=beanTransmision.getBeanGuardarPrimerCotitular().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarPrimerCotitular().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarPrimerCotitular().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarPrimerCotitular().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarPrimerCotitular().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinientePrimerCotitular = ejecutarProc(beanTransmision.getBeanGuardarPrimerCotitular(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodePrimerCotitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Primer Cotitular, otra vez: " + pCodePrimerCotitular);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodePrimerCotitular)) {
								errorInterviniente += " -El primer cotitular no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))?(" -Primer cotitular: " + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))) 
									resultado.setMensaje(resultado.getMensaje()+ " Primer Cotitular: "+(String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION));
							}
						} else {
							errorInterviniente += " -El primer cotitular no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))?(" -Primer Cotitular: " + (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION))) 
							resultado.setMensaje(resultado.getMensaje()+ " Primer Cotitular: "+(String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitular.getParametro(ConstantesPQ.P_INFORMACION));
					}
				} else if (null!=beanTransmision.getBeanGuardarPrimerCotitular().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarPrimerCotitular().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El primer cotitular no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}

				//Segundo Cotitular
				//Ejecutamos el procedimiento de guardar el Segundo Cotitular en caso de que haya (que tenga NIF)
				if (null!=beanTransmision.getBeanGuardarSegundoCotitular().getP_NIF() && !"".equals(beanTransmision.getBeanGuardarSegundoCotitular().getP_NIF())) {
					beanTransmision.getBeanGuardarSegundoCotitular().setP_NUM_INTERVINIENTE(new BigDecimal(2));
					resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular = ejecutarProc(beanTransmision.getBeanGuardarSegundoCotitular(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

					//Recuperamos información de respuesta
					BigDecimal pCodeSegundoCotitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_CODE);
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Segundo Cotitular: " + pCodeSegundoCotitular);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION));

					if (!ConstantesPQ.pCodeOk.equals(pCodeSegundoCotitular)) {
						//Si es porque no tiene nombre, separo el primer apellido y le meto un nombre
						if (ConstantesPQ.pCodeFaltaNombre.equals(pCodeSegundoCotitular) && null!=beanTransmision.getBeanGuardarSegundoCotitular().getP_APELLIDO1_RAZON_SOCIAL() && beanTransmision.getBeanGuardarSegundoCotitular().getP_APELLIDO1_RAZON_SOCIAL().trim().split(" ").length>=2) {
							String todo = beanTransmision.getBeanGuardarSegundoCotitular().getP_APELLIDO1_RAZON_SOCIAL();
							HashMap<String,String> separado = separarNombreApellidos(todo);
							beanTransmision.getBeanGuardarSegundoCotitular().setP_NOMBRE(separado.get("nombre"));
							beanTransmision.getBeanGuardarSegundoCotitular().setP_APELLIDO1_RAZON_SOCIAL(separado.get("apellidos"));

							//Lo volvemos a ejecutar, ahora con nombre porque el representante es una persona física
							resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular = ejecutarProc(beanTransmision.getBeanGuardarSegundoCotitular(),valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE_IMPOR",BeanPQGeneral.class);

							//Recuperamos información de respuesta
							pCodeSegundoCotitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_CODE);
							log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el Segundo Cotitular, otra vez: " + pCodeSegundoCotitular);
							log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_SQLERRM));
							log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION));

							//Si ha vuelto a dar error el guardar representante
							if (!ConstantesPQ.pCodeOk.equals(pCodeSegundoCotitular)) {
								errorInterviniente += " -El Segundo Cotitular no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
								errorIntervinientes = true;
							} else {
								//Si ha habido un mensaje de información, lo guardamos:
								if (null!=resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION))
									mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION))?(" -Segundo Cotitular: " + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
								if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION)))
									resultado.setMensaje(resultado.getMensaje()+ " Segundo Cotitular: "+(String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION));
							}
						} else {
							errorInterviniente += " -El Segundo Cotitular no se ha guardado: " + OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_SQLERRM)) + " ";
							errorIntervinientes = true;
						}
					} else {
						//Si ha habido un mensaje de información, lo guardamos:
						if (null!=resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION))
							mensajeWarnings += !"".equals((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION))?(" -Segundo Cotitular: " + (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION) + "."):"";
						if (null!=((String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION)))
							resultado.setMensaje(resultado.getMensaje()+ " Segundo Cotitular: "+(String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitular.getParametro(ConstantesPQ.P_INFORMACION));
					}
				} else if (null!=beanTransmision.getBeanGuardarSegundoCotitular().getP_APELLIDO1_RAZON_SOCIAL() && !"".equals(beanTransmision.getBeanGuardarSegundoCotitular().getP_APELLIDO1_RAZON_SOCIAL())) {
					errorInterviniente += " -El segundo cotitular no se ha guardado: No tiene NIF";
					errorIntervinientes = true;
				}
				//servicioTramiteTraficoTransmision.actualizarDatos620(numExpediente,beanTransmision.getBeanGuardarTransmision() );
				//Si alguno de los intervinientes no se ha guardado o ha dado información, devolvemos el error, pero el trámite diremos que ha sido correcto (pues el trámite se ha guardado)
				if (errorIntervinientes || !"".equals(mensajeWarnings)) {
					resultado.setError(false);
					resultado.setMensaje("Trámite (" + numExpediente + ") guardado. Información:" + errorInterviniente + mensajeWarnings);
					resultadoTransmision.put(ConstantesPQ.NUM_EXPEDIENTE, numExpediente);
				} else {
					resultado.setError(false);
					resultado.setMensaje("Trámite (" + numExpediente + ") guardado.");
					resultadoTransmision.put(ConstantesPQ.NUM_EXPEDIENTE, numExpediente);
				}
			} else { //El trámite no se ha guardado bien
				resultado.setError(true);
				resultado.setMensaje(OraUtiles.traducirError((String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_SQLERRM)));
			}
		} else { //El vehiculo no se ha guardado bien
			resultado.setError(true);
			resultado.setMensaje("El trámite no se ha guardado. Hay un error en el vehículo: " + OraUtiles.traducirError((String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM)));
		}

		resultadoTransmision.put(ConstantesPQ.RESULTBEAN, resultado);
		return resultadoTransmision;
	}

	public CamposRespuestaPLSQL importarCET(Autoliquidacion auto,BigDecimal idUsuario,
			BigDecimal idContrato,BigDecimal idContratoSesion){

		CamposRespuestaPLSQL respuestaPlsql = new CamposRespuestaPLSQL();
		BeanPQCARGAR_CET beanCargar = new BeanPQCARGAR_CET();

		beanCargar.setP_ID_USUARIO(idUsuario);
		beanCargar.setP_ID_CONTRATO_SESSION(idContratoSesion);
		beanCargar.setP_ID_CONTRATO(idContrato);
		beanCargar.setP_CET(auto.getCET());
		beanCargar.setP_CODE(new BigDecimal(auto.getCodigo()));
		beanCargar.setP_MATRICULA(auto.getMatricula());
		beanCargar.execute();

		BigDecimal pCodeTramite = beanCargar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanCargar.getP_SQLERRM());

		respuestaPlsql.setPSqlErrm(beanCargar.getP_SQLERRM());
		respuestaPlsql.setPCode(new Integer(beanCargar.getP_CODE().intValue()));

		return respuestaPlsql;
	}

	/**
	 * Método para insertar la información de seguridad para un trámite que se va a importar
	 * @param numExpediente
	 * @param token
	 * @return
	 */
	public CamposRespuestaPLSQL controlImportacion(BigDecimal numExpediente, String token) {
		CamposRespuestaPLSQL respuestaPlsql = new CamposRespuestaPLSQL();
		BeanPQCONTROL_IMPORTACION beanPQCONTROLIMPORTACION = new BeanPQCONTROL_IMPORTACION();

		//Vamos a insertar la información que nos viene del xml
		beanPQCONTROLIMPORTACION.setP_NUM_EXPEDIENTE(numExpediente);
		beanPQCONTROLIMPORTACION.setP_TOKEN_SEGURIDAD(token);
		beanPQCONTROLIMPORTACION.execute();

		BigDecimal pCodeTramite = beanPQCONTROLIMPORTACION.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCONTROLIMPORTACION.getP_SQLERRM());

		respuestaPlsql.setPSqlErrm(beanPQCONTROLIMPORTACION.getP_SQLERRM());
		respuestaPlsql.setPCode(new Integer(beanPQCONTROLIMPORTACION.getP_CODE().intValue()));

		return respuestaPlsql;
	}

	/*
	 * Método para guardar un alta de trámite de duplicado
	 */
	public HashMap<String, Object> guardarAltaTramiteDuplicado(BeanPQTramiteDuplicado beanDuplicado, BigDecimal idContrato, BigDecimal idUsuario) {

		if (beanDuplicado == null) {
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
		String numColegiado = beanDuplicado.getBeanGuardarDuplicado().getP_NUM_COLEGIADO();

		if (beanDuplicado.getBeanGuardarVehiculo().getP_MATRICULA()!=null && !"".equals(beanDuplicado.getBeanGuardarVehiculo().getP_MATRICULA())){
			beanDuplicado.getBeanGuardarVehiculo().setP_MATRICULA(beanDuplicado.getBeanGuardarVehiculo().getP_MATRICULA().trim());
			//Se setea el campo necesario al VEHICULO
			if (null==beanDuplicado.getBeanGuardarVehiculo().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarVehiculo().getP_NUM_COLEGIADO()))
				beanDuplicado.getBeanGuardarVehiculo().setP_NUM_COLEGIADO(numColegiado);
			beanDuplicado.getBeanGuardarVehiculo().setP_TIPO_TRAMITE(TipoTramiteTrafico.Duplicado.getValorEnum());
			//Vamos a guardar el vehiculo primero.
			RespuestaGenerica resultadoVehiculo = ejecutarProc(beanDuplicado.getBeanGuardarVehiculo(),
																valoresSchemas.getSchema(),
																ValoresCatalog.PQ_VEHICULOS, "GUARDAR",
																BeanPQGeneral.class);
			//Recuperamos el vehículo del PQ, lo convertirmos y añadimos al Bean de Pantalla
			// Se queda en una variable VehiculoBean para luego añadirlo después del tramite para no pisar los datos del primero.
			beanVehiculo = vehiculoBeanPQConversion.convertirPQToBean(resultadoVehiculo);
			beanVehiculo.setHomologacionBean(servicioDirectivaCee.getHomologacionBean((String)resultadoVehiculo.getParametro("P_ID_DIRECTIVA_CEE")));

			//Recuperamos información de respuesta
			BigDecimal pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
			log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el vehículo: " + pCodeVehiculo);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));
			//Si ha ido bien la operación de guardar vehículo (P_CODE es 0 si todo fue bien).
			if (ConstantesPQ.pCodeOk.equals(pCodeVehiculo)) { //ConstantesPQ.pCodeOk
				log.debug("          EL VEHICULO (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
			}else{ //Si la operación de guardar vehiculo fue erronea, se guarda el error en el resultado para devolverlo después.
				mensaje += ("   - Error al guardar el vehículo. " + 
						(String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
				if (resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION)!=null){
					mensaje += ((String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION));
				}
			}
			//Seteamos los campos necesarios al TRAMITE
			BigDecimal idVehiculo = (BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO");
			beanDuplicado.getBeanGuardarDuplicado().setP_ID_VEHICULO(idVehiculo);
		}

		if (null == beanDuplicado.getBeanGuardarDuplicado().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarDuplicado().getP_NUM_COLEGIADO()))
			beanDuplicado.getBeanGuardarDuplicado().setP_NUM_COLEGIADO(numColegiado);
		if (null == beanDuplicado.getBeanGuardarDuplicado().getP_ID_CONTRATO()) 
			beanDuplicado.getBeanGuardarDuplicado().setP_ID_CONTRATO(idContrato);
		beanDuplicado.getBeanGuardarDuplicado().setP_ID_CONTRATO_SESSION(idContrato);
		beanDuplicado.getBeanGuardarDuplicado().setP_ID_USUARIO(idUsuario);

		//Guardamos el trámite
		RespuestaGenerica resultadoTramite = ejecutarProc(beanDuplicado.getBeanGuardarDuplicado(),
															valoresSchemas.getSchema(),
															ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_DUPLICADO",
															BeanPQGeneral.class);
		//Recuperamos el trámite del PQ y lo convertirmos y añadimos al Bean de Pantalla
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
			//Si hay un NIF en titular, se procede a guardarlo. Si el NIF es nulo pero hay rowid se procede a borrarlo de la bbdd
			if ((beanDuplicado.getBeanGuardarTitular().getP_NIF()!=null && !"".equals(beanDuplicado.getBeanGuardarTitular().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarTitular().getP_TIPO_INTERVINIENTE()!=null && !"".equals(beanDuplicado.getBeanGuardarTitular().getP_TIPO_INTERVINIENTE()))){

				beanDuplicado.getBeanGuardarTitular().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarTitular().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_TITULAR);
				if (null==beanDuplicado.getBeanGuardarTitular().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarTitular().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarTitular().setP_NUM_COLEGIADO(numColegiado);
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
						tramite.getTramiteTrafico().setEstado(tramite.getTitular().getEstadoTramite()!= null ? tramite.getTitular().getEstadoTramite().getValorEnum() : null);
					}else{ // Si la operación de guardar titular fue erronea, se guarda el error en el resultado para devolverlo después.
						mensaje += ("   - Error al guardar el titular. " + 
								(String) resultadoTitular.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
						if (resultadoTitular.getParametro(ConstantesPQ.P_INFORMACION) != null) {
							mensaje += (String) resultadoTitular.getParametro(ConstantesPQ.P_INFORMACION);
						}
					}
				}
			}
			// Guardamos el cotitular
			if ((beanDuplicado.getBeanGuardarCotitular().getP_NIF() != null && !"".equals(beanDuplicado.getBeanGuardarCotitular().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarCotitular().getP_TIPO_INTERVINIENTE() != null && !"".equals(beanDuplicado.getBeanGuardarCotitular().getP_TIPO_INTERVINIENTE()))) {

				beanDuplicado.getBeanGuardarCotitular().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarCotitular().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_COTITULAR_TRANSMISION);
				if (null == beanDuplicado.getBeanGuardarCotitular().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarCotitular().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarCotitular().setP_NUM_COLEGIADO(numColegiado);
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
					} else { // Si la operación de guardar titular fue erronea, se guarda el error en el resultado para devolverlo después.
						mensaje += ("  - Error al guardar el cotitular. " + 
								(String) resultadoCoTitular.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
						if (resultadoCoTitular.getParametro(ConstantesPQ.P_INFORMACION)!=null){
							mensaje += (String) resultadoCoTitular.getParametro(ConstantesPQ.P_INFORMACION);
						}
					}
				}
			}

			//Si hay un NIF en representante, se procede a guardarlo, si el NIF es nulo pero hay rowid se procede a borrarlo de la BBDD
			if ((beanDuplicado.getBeanGuardarRepresentante().getP_NIF()!=null && !"".equals(beanDuplicado.getBeanGuardarRepresentante().getP_NIF()))
					|| (beanDuplicado.getBeanGuardarRepresentante().getP_TIPO_INTERVINIENTE()!= null && !beanDuplicado.getBeanGuardarRepresentante().getP_TIPO_INTERVINIENTE().equals(""))) {
				//Se setea lo necesario en el REPRESENTANTE
				beanDuplicado.getBeanGuardarRepresentante().setP_NUM_EXPEDIENTE(numExpediente);
				beanDuplicado.getBeanGuardarRepresentante().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR);
				if (null==beanDuplicado.getBeanGuardarRepresentante().getP_NUM_COLEGIADO() || "".equals(beanDuplicado.getBeanGuardarRepresentante().getP_NUM_COLEGIADO()))
					beanDuplicado.getBeanGuardarRepresentante().setP_NUM_COLEGIADO(numColegiado);
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
					log.debug(ConstantesPQ.LOG_P_CODE + " al guardar el representante: " + pCodeRepresentante);
					log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoRepresentante.getParametro(ConstantesPQ.P_SQLERRM));
					log.debug(ConstantesPQ.LOG_P_INFORMACION + (String) resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION));
					//Comprobamos si se ha habído un error al guardar el representante. Aunque haya habido un error seguimos con el siguiente interviniente.
					if (ConstantesPQ.pCodeOk.equals(pCodeRepresentante)) {
						log.debug("          EL REPRESENTANTE (TRAMITE TRAFICO DUPLICADO) SE HA GUARDADO CORRECTAMENTE");
						//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
						tramite.getTramiteTrafico().setEstado(tramite.getRepresentante().getEstadoTramite()!= null ? tramite.getRepresentante().getEstadoTramite().getValorEnum():null);
					} else {
						mensaje += ("  - Error al guardar el representante. " +
								(String) resultadoRepresentante.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
						if (resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION)!=null){
							mensaje += (String) resultadoRepresentante.getParametro(ConstantesPQ.P_INFORMACION);
						}
					}
				}
			}
		} else { //Si la operación de guardar tramite de baja fue erronea, se guarda el error para devolverlo después.
			resultado.setError(true);
			mensaje += ("  - Error al guardar el trámite de duplicado." + 
					//transformarMensajesDeError(pCodeBaja) +
					(String) resultadoTramite.getParametro(ConstantesPQ.P_SQLERRM));//+
					//" No se guardará ningún dato de los intervinientes. ");
			if (resultadoTramite.getParametro(ConstantesPQ.P_INFORMACION) != null) {
				mensaje += (String) resultadoTramite.getParametro(ConstantesPQ.P_INFORMACION);
			}
		}

		if ("".equals(mensaje)){
			mensaje ="Trámite (" + tramite.getTramiteTrafico().getNumExpediente().toString() + ") guardado.";
		}

		resultado.setMensaje(mensaje);
		resultadoDuplicado.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoDuplicado.put(ConstantesPQ.BEANPANTALLA, tramite);
		return resultadoDuplicado;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyección por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloColegiado getModeloColegiado() {
		if (modeloColegiado == null) {
			modeloColegiado = new ModeloColegiado();
		}
		return modeloColegiado;
	}

	public void setModeloColegiado(ModeloColegiado modeloColegiado) {
		this.modeloColegiado = modeloColegiado;
	}

}