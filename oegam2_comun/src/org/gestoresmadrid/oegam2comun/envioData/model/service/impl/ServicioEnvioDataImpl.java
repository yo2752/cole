package org.gestoresmadrid.oegam2comun.envioData.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.EnvioDataDao;
import org.gestoresmadrid.core.general.model.vo.EnvioDataPK;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.envioData.model.service.ServicioEnvioData;
import org.gestoresmadrid.oegam2comun.envioData.view.bean.ResultadoEnvioDataBean;
import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEnvioDataImpl implements ServicioEnvioData {

	private static final long serialVersionUID = 3558017026915281955L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEnvioDataImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	EnvioDataDao envioDataDao;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioProcesos servicioProceso;

	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataDto> getListaEnviosDataProceso(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				List<EnvioDataDto> listaEnviosData = new ArrayList<EnvioDataDto>();
				EnvioDataVO envioDataCorrecto = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucion(proceso, ConstantesProcesos.EJECUCION_CORRECTA);
				if (envioDataCorrecto != null) {
					listaEnviosData.add(conversor.transform(envioDataCorrecto, EnvioDataDto.class));
				}
				EnvioDataVO envioDataError = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucion(proceso, ConstantesProcesos.EJECUCION_NO_CORRECTA);
				if (envioDataError != null) {
					listaEnviosData.add(conversor.transform(envioDataError, EnvioDataDto.class));
				}
				EnvioDataVO envioDataExcepcion = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucion(proceso, ConstantesProcesos.EJECUCION_EXCEPCION);
				if (envioDataExcepcion != null) {
					listaEnviosData.add(conversor.transform(envioDataExcepcion, EnvioDataDto.class));
				}
				return listaEnviosData;
			} else {
				log.error("Debe de indicar un proceso para obtener su lista de envioData.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con los envioData para el proceso: " + proceso + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataDto> getListaEnviosDataProcesoPorCola(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				List<EnvioDataDto> listaEnviosData = new ArrayList<EnvioDataDto>();
				List<EnvioDataVO> listaEnvioDataCorrecto = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucionPorCola(proceso, ConstantesProcesos.EJECUCION_CORRECTA);
				if (listaEnvioDataCorrecto != null && !listaEnvioDataCorrecto.isEmpty()) {
					añadirEnvioDataToLista(listaEnviosData, listaEnvioDataCorrecto);
				}
				List<EnvioDataVO> listaEnvioDataError = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucionPorCola(proceso, ConstantesProcesos.EJECUCION_NO_CORRECTA);
				if (listaEnvioDataError != null && !listaEnvioDataError.isEmpty()) {
					añadirEnvioDataToLista(listaEnviosData, listaEnvioDataError);
				}
				List<EnvioDataVO> listaEnvioDataExcepcion = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucionPorCola(proceso, ConstantesProcesos.EJECUCION_EXCEPCION);
				if (listaEnvioDataExcepcion != null && !listaEnvioDataExcepcion.isEmpty()) {
					añadirEnvioDataToLista(listaEnviosData, listaEnvioDataExcepcion);
				}
				return listaEnviosData;
			} else {
				log.error("Debe de indicar un proceso para obtener su lista de envioData.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con los envioData para el proceso: " + proceso + ", error: ", e);
		}
		return null;
	}

	public void añadirEnvioDataToLista(List<EnvioDataDto> listaEnviosDataDestino, List<EnvioDataVO> listaEnvioDataBBDD) {
		for (EnvioDataVO envioDataVO : listaEnvioDataBBDD) {
			listaEnviosDataDestino.add(conversor.transform(envioDataVO, EnvioDataDto.class));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataDto> getListaUltimosEnvioDataCorrectos(ConsultaGestionColaBean consultaGestionCola, String orden, String dir) {
		try {
			List<EnvioDataVO> listaEnvioDataBBDD = envioDataDao.getListaUltimoEnvioPorEjecucion(consultaGestionCola.getProceso(), consultaGestionCola.getCola(), ConstantesProcesos.EJECUCION_CORRECTA,
					orden, dir);
			if (listaEnvioDataBBDD != null && !listaEnvioDataBBDD.isEmpty()) {
				return conversor.transform(listaEnvioDataBBDD, EnvioDataDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con el ultimo envio data correcto, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataDto> recuperarUltimasEjecucionesProceso(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				List<EnvioDataVO> listaBBDD = envioDataDao.recuperarUltimasEjecucionesProceso(proceso);
				if (listaBBDD != null && !listaBBDD.isEmpty()) {
					return conversor.transform(listaBBDD, EnvioDataDto.class);
				} else {
					log.error("No se pueden recuperar las ultimas ejecuciones pendientes porque el proceso viene vacio");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar las ultimas ejecuciones para el proceso: " + proceso + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoEnvioDataBean actualizarFechaEnvioData(String proceso, String tipoActualizacion, String cola) {
		ResultadoEnvioDataBean resultado = new ResultadoEnvioDataBean(Boolean.FALSE);
		try {
			resultado = comprobarDatosMinimosEnvioData(proceso, tipoActualizacion, cola);
			if (!resultado.getError()) {
				EnvioDataVO envioDataBBDD = envioDataDao.getEnvioDataPorProcesoYColaYTipoEjecucion(proceso, cola, ConstantesProcesos.EJECUCION_CORRECTA);
				if (envioDataBBDD != null) {
					Date fechaActualizar = null;
					if (ACTUALIZAR_FECHA_HOY.equals(tipoActualizacion)) {
						fechaActualizar = new Date();
					} else if (ACTUALIZAR_FECHA_ULTIMA_LABORAL.equals(tipoActualizacion)) {
						fechaActualizar = utilesFecha.getPrimerLaborableAnterior(new Date());
					}
					if (fechaActualizar != null) {
						envioDataBBDD.setFechaEnvio(fechaActualizar);
						envioDataDao.actualizar(envioDataBBDD);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.addListaMensaje("Ha sucedido un error a la hora de actualizar el envio data con la fecha.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensaje("No existe ningún envio data que se pueda actualizar.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la fecha de envio data, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensaje("Ha sucedido un error a la hora de actualizar la fecha de envio data.");
		}
		listadoEnvioDataPantalla(resultado);
		return resultado;
	}

	private void listadoEnvioDataPantalla(ResultadoEnvioDataBean resultado) {
		try {
			ResultadoEnvioDataBean resultadoListaEnvioData = listarEnvioData(ConstantesProcesos.EJECUCION_CORRECTA);
			if (!resultadoListaEnvioData.getError()) {
				resultado.setListaEnvioData(resultadoListaEnvioData.getListaEnvioData());
			} else {
				for (String mensajeError : resultadoListaEnvioData.getListaMensajes()) {
					resultado.addListaMensaje(mensajeError);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de envio data de pantalla, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensaje("Ha sucedido un error a la hora de obtener la lista de envio data.");
		}
	}

	private ResultadoEnvioDataBean comprobarDatosMinimosEnvioData(String proceso, String tipoActualizacion, String cola) {
		ResultadoEnvioDataBean resultadoEnvio = new ResultadoEnvioDataBean(Boolean.FALSE);
		if (proceso == null || proceso.isEmpty()) {
			resultadoEnvio.setError(Boolean.TRUE);
			resultadoEnvio.addListaMensaje("Debe de indicar el proceso que desea actualizar su fecha.");
		}
		if (cola == null || cola.isEmpty()) {
			resultadoEnvio.setError(Boolean.TRUE);
			resultadoEnvio.addListaMensaje("Debe de indicar la cola del proceso que desea actualizar su fecha.");
		}
		if (tipoActualizacion == null || tipoActualizacion.isEmpty()) {
			resultadoEnvio.setError(Boolean.TRUE);
			resultadoEnvio.addListaMensaje("Debe de indicar el tipo de actualizacion de la fecha para el envio data que desea actualizar su fecha.");
		} else {
			if (!ACTUALIZAR_FECHA_ULTIMA_LABORAL.equals(tipoActualizacion) && !ACTUALIZAR_FECHA_HOY.equals(tipoActualizacion)) {
				resultadoEnvio.setError(Boolean.TRUE);
				resultadoEnvio.addListaMensaje("El tipo de actualizacion de la fecha del envio data no es correcto.");
			}
		}
		return resultadoEnvio;
	}

	@Override
	@Transactional
	public void actualizarEjecucion(String proceso, BigDecimal idTramite, String respuesta, String resultadoEjecucion, String numCola) {
		try {
			if (proceso != null && !proceso.isEmpty() && resultadoEjecucion != null && !resultadoEjecucion.isEmpty()) {
				EnvioDataVO envioDataBBDD = envioDataDao.getEnvioDataProcesoResultadoEjecucion(proceso, resultadoEjecucion, numCola);
				if (envioDataBBDD != null) {
					envioDataBBDD.setFechaEnvio(new Date());
					envioDataBBDD.setNumExpediente(idTramite);
					envioDataBBDD.setRespuesta(respuesta);
				} else {
					envioDataBBDD = new EnvioDataVO();
					EnvioDataPK id = new EnvioDataPK();
					id.setCola(numCola);
					id.setCorrecta(resultadoEjecucion);
					id.setProceso(proceso);
					envioDataBBDD.setId(id);
					envioDataBBDD.setFechaEnvio(new Date());
					envioDataBBDD.setNumExpediente(idTramite);
					envioDataBBDD.setRespuesta(respuesta);
				}
				envioDataDao.guardarOActualizar(envioDataBBDD);
			} else {
				log.error("No se puede actualizar el envioData porque el proceso o el resultado de la ejecucion es nulo o esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el envioData, error: ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Date getUltimaFechaEnvioData(String proceso, String resultadoEjecucion, String hiloCola) {
		try {
			if (proceso != null && !proceso.isEmpty() && resultadoEjecucion != null && !resultadoEjecucion.isEmpty()) {
				if (hiloCola == null || hiloCola.isEmpty()) {
					hiloCola = "0";
				}
				return envioDataDao.getUltimoEnvioDataProcesoEjecucion(proceso, resultadoEjecucion, hiloCola);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la fecha mayor del envio data del proceso: " + proceso + ", error: ", e);
		}
		return null;
	}

	@Override
	public Boolean hayEnvioDataProcesoCola(String proceso, String hiloCola) {
		try {
			EnvioDataVO envioDataBBDD = envioDataDao.getEnvioDataPorHoras(proceso, ConstantesProcesos.EJECUCION_CORRECTA, hiloCola, utilesFecha.setHorasMinutosSegundosEnDate(Calendar.getInstance()
					.getTime(), "00:00:00"), Calendar.getInstance().getTime());
			if (envioDataBBDD != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Error al consultar la ejecucion en envio data, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoEnvioDataBean listarEnvioData(String tipoEjecucion) {
		ResultadoEnvioDataBean resultado = new ResultadoEnvioDataBean(Boolean.FALSE);
		try {
			List<EnvioDataVO> listaBBDD = envioDataDao.listar(tipoEjecucion);
			if (listaBBDD != null && !listaBBDD.isEmpty()) {
				resultado.setListaEnvioData(conversor.transform(listaBBDD, EnvioDataDto.class));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No existe ninguna lista de envio data que mostrar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de listar el envio data, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensaje("Ha sucedido un error a la hora de listar el envio data.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataVO> getlistaEnvioData(String tipoEjecucion) {
		return envioDataDao.listar(tipoEjecucion);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EnvioDataVO> getListaUltimoEnvioPorEjecucion(String proceso, String cola, String correcta, String orden, String dir) {
		return envioDataDao.getListaUltimoEnvioPorEjecucion(proceso, cola, correcta, orden, dir);
	}
	
	@Override
	@Transactional
	public EnvioDataVO actualizarEnvioData(EnvioDataVO envioDataVO) {
		return envioDataDao.actualizar(envioDataVO);
	}
}
