package org.gestoresmadrid.oegam2comun.proceso.model.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.general.model.dao.EnvioDataDao;
import org.gestoresmadrid.core.general.model.vo.EnvioDataPK;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.proceso.model.dao.ProcesoDao;
import org.gestoresmadrid.core.proceso.model.enumerados.PatronProcesos;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoPK;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioGestProcesosVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.cola.model.service.impl.GestorColas;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioConexionGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.bean.ResultadoProcesosBean;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;

@Service(value = "servicioProcesos")
public class ServicioProcesosImpl implements ServicioProcesos {
	private static final String NOMBRE_HOST_PROCESO = "nombreHostProceso";

	private static final String SEPARATOR_TOKEN = ", ";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioProcesosImpl.class);

	@Autowired
	ProcesoDao procesoDao;

	@Autowired
	EnvioDataDao envioDataDao;

	@Autowired
	ModeloSolicitud modeloSolicitud;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioMensajeErrorServicio mensajeErrorServicio;

	@Autowired
	ServicioUsuarioConexionGestProcesos servicioUsuarioConexionGestProcesos;

	@Autowired
	ServicioUsuarioGestProcesos servicioUsuarioGestProcesos;

	@Autowired
	Conversor conversor;

	@Autowired
	GestorColas gestorColas;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoProcesosBean pararProceso(String ordenProceso, String patron, Long idUsuario, String username) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (ordenProceso != null && !ordenProceso.isEmpty()) {
				gestorColas.pararProceso(Integer.parseInt(ordenProceso));
				registrarOperacionProceso(Integer.parseInt(ordenProceso), ServicioUsuarioConexionGestProcesosImpl.PARAR, idUsuario, username);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("Debe de indicar que proceso quiere parar.");
			}
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de para el proceso con orden: " + ordenProceso + " , error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de para el proceso.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	private void rellenarResultadoListaProceso(ResultadoProcesosBean resultadoProcesosBean, String patron) {
		if (patron != null && !patron.isEmpty()) {
			resultadoProcesosBean.setListaProcesos(listaProcesosPorPatron(patron));
			String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patron);
			if (procesosPatron != null && !procesosPatron.isEmpty()) {
				resultadoProcesosBean.setListaProcesosPatron(procesosPatron);
			}
		} else {
			resultadoProcesosBean.setListaProcesos(gestorColas.listarProcesosOrdenados());
		}
	}

	@Override
	public ResultadoProcesosBean activarProceso(String ordenProceso, String patron, Long idUsuario, String username) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (ordenProceso != null && !ordenProceso.isEmpty()) {
				gestorColas.arrancarProceso(Integer.parseInt(ordenProceso));
				rellenarResultadoListaProceso(resultadoProcesosBean, patron);
				registrarOperacionProceso(Integer.parseInt(ordenProceso), ServicioUsuarioConexionGestProcesosImpl.ARRANCAR, idUsuario, username);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("Debe de indicar que proceso quiere activar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de activar el proceso con orden: " + ordenProceso + " , error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de activar el proceso.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	private void registrarOperacionProceso(int ordenProceso, String operacion, Long idUsuario, String username) {
		UsuarioGestProcesosVO usuario = servicioUsuarioGestProcesos.getUserProcesosByIdUsuarioAndUserVO(idUsuario, username);
		if (usuario != null && !"admin".equals(usuario.getUserName())) {
			ProcesoDto procesoDto = gestorColas.getProcesoPorOrden(ordenProceso);
			servicioUsuarioConexionGestProcesos.registrarOperacion(usuario.getIdGestProcesos(), operacion, procesoDto.getProceso(), idUsuario);
		}
	}

	private void registrarOperacionPatron(String patron, String operacion, Long idUsuario) {
		UsuarioGestProcesosVO usuario = servicioUsuarioGestProcesos.getUsuarioGestProcesosByIdUsuarioVO(idUsuario);
		if (usuario != null && !"admin".equals(usuario.getUserName())) {
			servicioUsuarioConexionGestProcesos.registrarOperacion(usuario.getIdGestProcesos(), operacion, patron, idUsuario);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResultadoProcesosBean actualizarGestorColas(String patron) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			List<ProcesoDto> listaProcesosBBDD = getListadoProcesos();
			if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
				gestorColas.iniciarGestorColas();
				for (ProcesoDto proceso : listaProcesosBBDD) {
					try {
						proceso.setClasse((Class<T>) Class.forName(proceso.getClase()));
						gestorColas.nuevoProceso(proceso);
					} catch (SchedulerException e) {
						log.error("Error: " + e.getMessage());
					} catch (Exception e) {
						log.error("Error a la hora de convertir ProcesoVO to ProcesoDto para el proceso: " + proceso.getProceso() + ", error: ", e);
					}
				}
				rellenarResultadoListaProceso(resultadoProcesosBean, patron);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("No existen procesos para actualizar la lista.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la lista de los procesos, error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de actualizar la lista de los procesos.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	@Override
	@Transactional
	public ResultadoProcesosBean modificar(String ordenProceso, String intentosMax, String hilosColas, String horaInicio, String horaFin, String tiempoCorrecto, String tiempoRecuperable,
			String tiempoNoRecuperable, String tiempoSinDatos, String patron) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (ordenProceso != null && !ordenProceso.isEmpty()) {
				ProcesoDto procesoDto = gestorColas.getProcesoPorOrden(Integer.parseInt(ordenProceso));
				if (procesoDto != null) {
					if (hilosColas != null && !Long.valueOf(hilosColas).equals(procesoDto.getHilosColas())) {
						cambiarNumeroColas(procesoDto, hilosColas);
					}
					actualizarCamposProcesoDto(procesoDto, intentosMax, hilosColas, horaInicio, horaFin, tiempoCorrecto, tiempoRecuperable, tiempoNoRecuperable, tiempoSinDatos);
					gestorColas.setProcesoToLista(Integer.parseInt(ordenProceso), procesoDto);
					actualizarProceso(procesoDto);
					resultadoProcesosBean.setNombreProceso(procesoDto.getDescripcion());
				} else {
					resultadoProcesosBean.setError(Boolean.TRUE);
					resultadoProcesosBean.addListaMensaje("No existen datos que modificar para ese proceso.");
					log.error("No existen datos que modificar para ese proceso: " + ordenProceso);
				}
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("Debe de indicar que proceso desea modificar.");
			}
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de modificar el proceso con orden: " + ordenProceso + ", error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de modificar el proceso.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	private void cambiarNumeroColas(ProcesoDto proceso, String hilosColas) {
		try {
			if (proceso.getHilosColas().intValue() > Integer.valueOf(hilosColas)) {
				int colasParar = proceso.getHilosColas().intValue() - Integer.valueOf(hilosColas);
				int desde = colasParar;
				int hasta = proceso.getHilosColas().intValue();
				while (hasta >= desde) {
					// Buscamos si hay solicitudes para esa cola
					List<ColaVO> solicitudes = servicioCola.getSolicitudesActivasProceso(proceso.getProceso(), proceso.getNodo());
					if (solicitudes != null && !solicitudes.isEmpty()) {
						int cola = 1;
						// Va asignando en las colas que quedarán activas las distintas solicitudes
						for (ColaVO solicitud : solicitudes) {
							solicitud.setCola(String.valueOf(cola));
							servicioCola.actualizar(solicitud);
							if (cola == desde) {
								cola = 1;
							} else {
								cola++;
							}
						}
					}
					ColaVO colaPrincipal = servicioCola.getColaPrincipal(proceso.getProceso(), String.valueOf(hasta), proceso.getNodo());
					servicioCola.borrar(colaPrincipal);
					hasta--;
				}
			} else {
				int colasArrancar = Integer.valueOf(hilosColas) - proceso.getHilosColas().intValue();
				for (int i = 1; i <= colasArrancar; i++) {
					ColaVO colaVO = crearCola(proceso.getProceso(), proceso.getNodo(), proceso.getHilosColas().intValue() + i);
					servicioCola.generarCola(colaVO);
				}
			}
		} catch (Throwable e) {

		}
	}

	private ColaVO crearCola(String proceso, String nodo, int hilo) {
		ColaVO colaVO = new ColaVO();
		colaVO.setProceso(proceso);
		colaVO.setNodo(nodo);
		colaVO.setEstado(BigDecimal.ZERO);
		colaVO.setnIntento(BigDecimal.ZERO);
		colaVO.setCola(String.valueOf(hilo));
		return colaVO;
	}

	private void actualizarCamposProcesoDto(ProcesoDto procesoDto, String intentosMax, String hilosColas, String horaInicio, String horaFin, String tiempoCorrecto, String tiempoRecuperable,
			String tiempoNoRecuperable, String tiempoSinDatos) {
		if (intentosMax != null && !intentosMax.isEmpty()) {
			procesoDto.setnIntentosMax(new BigDecimal(intentosMax));
		}
		if (hilosColas != null && !hilosColas.isEmpty()) {
			procesoDto.setHilosColas(new Long(hilosColas));
		}
		if (horaInicio != null && !horaInicio.isEmpty()) {
			procesoDto.setHoraInicio(horaInicio);
		}
		if (horaFin != null && !horaFin.isEmpty()) {
			procesoDto.setHoraFin(horaFin);
		}
		if (tiempoCorrecto != null && !tiempoCorrecto.isEmpty()) {
			procesoDto.setTiempoCorrecto(new BigDecimal(tiempoCorrecto));
		}
		if (tiempoRecuperable != null && !tiempoRecuperable.isEmpty()) {
			procesoDto.setTiempoErroneoRecuperable(new BigDecimal(tiempoRecuperable));
		}
		if (tiempoNoRecuperable != null && !tiempoNoRecuperable.isEmpty()) {
			procesoDto.setTiempoErroneoNoRecuperable(new BigDecimal(tiempoNoRecuperable));
		}
		if (tiempoSinDatos != null && !tiempoSinDatos.isEmpty()) {
			procesoDto.setTiempoSinDatos(new BigDecimal(tiempoSinDatos));
		}
	}

	@Override
	public ResultadoProcesosBean activarProcesoPorPatron(String patron, Long idUsuario) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (patron != null && !patron.isEmpty()) {
				List<ProcesoDto> listaProcesosBBDD = null;
				if (!PatronProcesos.TODOS.getValorEnum().equals(patron)) {
					listaProcesosBBDD = listaProcesosPorPatron(patron);
				} else {
					listaProcesosBBDD = gestorColas.listarProcesosOrdenados();
				}
				if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
					for (ProcesoDto procesoDto : listaProcesosBBDD) {
						gestorColas.arrancarProceso(procesoDto.getOrden().intValue());
					}
					registrarOperacionPatron(patron, ServicioUsuarioConexionGestProcesosImpl.ARRANCAR_PATRON, idUsuario);
				} else {
					resultadoProcesosBean.setError(Boolean.TRUE);
					resultadoProcesosBean.addListaMensaje("No se han podido recuperar los procesos por el patron indicado.");
				}

				rellenarResultadoListaProceso(resultadoProcesosBean, patron);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("Debe de indicar que el patron para activar los procesos en bloque.");
			}
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de activar en bloque los procesos por el patron: " + patron + " , error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de activar los procesos por patron.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	@Override
	public ResultadoProcesosBean pararProcesoPorPatron(String patron, Long idUsuario) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (patron != null && !patron.isEmpty()) {
				List<ProcesoDto> listaProcesosBBDD = null;
				if (!PatronProcesos.TODOS.getValorEnum().equals(patron)) {
					listaProcesosBBDD = listaProcesosPorPatron(patron);
				} else {
					listaProcesosBBDD = gestorColas.listarProcesosOrdenados();
				}
				if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
					for (ProcesoDto procesoDto : listaProcesosBBDD) {
						gestorColas.pararProceso(procesoDto.getOrden().intValue());
					}
					registrarOperacionPatron(patron, ServicioUsuarioConexionGestProcesosImpl.PARAR_PATRON, idUsuario);
				} else {
					resultadoProcesosBean.setError(Boolean.TRUE);
					resultadoProcesosBean.addListaMensaje("No se han podido recuperar los procesos por el patron indicado.");
				}

				rellenarResultadoListaProceso(resultadoProcesosBean, patron);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("Debe de indicar que el patron para parar los procesos en bloque.");
			}
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de parar en bloque los procesos por el patron: " + patron + " , error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de parar los procesos por patron.");
			rellenarResultadoListaProceso(resultadoProcesosBean, patron);
		}
		return resultadoProcesosBean;
	}

	@Override
	public ResultadoProcesosBean getListadoProcesosPorPatron(String patron) {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			if (patron != null && !patron.isEmpty() && !PatronProcesos.TODOS.getValorEnum().equals(patron)) {
				resultadoProcesosBean.setListaProcesos(listaProcesosPorPatron(patron));
				String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patron);
				if (procesosPatron != null && !procesosPatron.isEmpty()) {
					resultadoProcesosBean.setListaProcesosPatron(procesosPatron);
				}
			} else {
				resultadoProcesosBean.setListaProcesos(gestorColas.listarProcesosOrdenados());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de los procesos, error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de obtener la lista de los procesos.");
		}
		return resultadoProcesosBean;
	}

	private List<ProcesoDto> listaProcesosPorPatron(String patron) {
		List<ProcesoDto> listaProcesoDtos = null;
		List<ProcesoDto> listaProcesosBBDD = gestorColas.listarProcesosOrdenados();
		if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
			if (!PatronProcesos.TODOS.getValorEnum().equals(patron)) {
				listaProcesoDtos = new ArrayList<ProcesoDto>();
				String procesosPatron = gestorPropiedades.valorPropertie("procesos.patron." + patron);
				if (procesosPatron != null && !procesosPatron.isEmpty()) {
					for (ProcesoDto procesoDto : listaProcesosBBDD) {
						if (procesosPatron.contains(procesoDto.getProceso())) {
							listaProcesoDtos.add(procesoDto);
						}
					}
				}
			} else {
				return listaProcesosBBDD;
			}
		}
		return listaProcesoDtos;
	}

	@Override
	public ResultadoProcesosBean getListadoProcesosPantalla() {
		ResultadoProcesosBean resultadoProcesosBean = new ResultadoProcesosBean(Boolean.FALSE);
		try {
			List<ProcesoDto> listaProcesosBBDD = gestorColas.listarProcesosOrdenados();
			if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
				resultadoProcesosBean.setListaProcesos(listaProcesosBBDD);
			} else {
				resultadoProcesosBean.setError(Boolean.TRUE);
				resultadoProcesosBean.addListaMensaje("No se ha podido obtener la lista de procesos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de los procesos, error: ", e);
			resultadoProcesosBean.setError(Boolean.TRUE);
			resultadoProcesosBean.addListaMensaje("Ha sucedido un error a la hora de obtener la lista de los procesos.");
		}
		return resultadoProcesosBean;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProcesoDto> getListadoProcesos() {
		try {
			String nodo = gestorPropiedades.valorPropertie(NOMBRE_HOST_PROCESO);
			List<ProcesoVO> listaProcesosBBDD = procesoDao.getListaProcesosOrdenados(nodo);
			if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
				List<ProcesoDto> listaProcesosDto = new ArrayList<ProcesoDto>();
				for (ProcesoVO procesoVO : listaProcesosBBDD) {
					ProcesoDto procesoDto = convertirVOtoDto(procesoVO);
					if (procesoDto != null) {
						listaProcesosDto.add(procesoDto);
					}
				}
				return listaProcesosDto;
			} else {
				log.error("No existen procesos en la base de datos para el nodo: " + nodo);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de los procesos, error: ", e);
		}
		return Collections.emptyList();
	}

	private ProcesoDto convertirVOtoDto(ProcesoVO procesoVO) {
		ProcesoDto procesoDto = conversor.transform(procesoVO, ProcesoDto.class);
		if (BigDecimal.ONE.compareTo(procesoDto.getEstado()) == 0) {
			procesoDto.setActivo(Boolean.TRUE);
		} else {
			procesoDto.setActivo(Boolean.FALSE);
		}
		return procesoDto;
	}

	@Override
	@Transactional
	public void actualizarProceso(ProcesoDto procesoDto) {
		try {
			if (procesoDto != null) {
				ProcesoVO procesoVo = conversor.transform(procesoDto, ProcesoVO.class);
				if (procesoVo != null) {
					if (procesoDto.getHilosColas() != null) {
						procesoVo.setHilosColas(new BigDecimal(procesoDto.getHilosColas()));
					}
					procesoDao.actualizar(procesoVo);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el proceso, error: ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getNombreProceso(BigDecimal tipo) {
		// Se recupera de properties el nombre del host
		String nodo = gestorPropiedades.valorPropertie(NOMBRE_HOST_PROCESO);
		// Se crea el filtro de busqueda con nodo y tipo
		ProcesoVO filter = new ProcesoVO();
		filter.setId(new ProcesoPK());
		filter.getId().setNodo(nodo);
		filter.setTipo(tipo);
		// Se busca y debería devolver uno solo.
		List<ProcesoVO> list = procesoDao.list(filter);
		if (list != null && list.size() == 1) {
			return list.get(0).getId().getProceso();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getNombreProcesoOrdenado(Long tipo) {
		String nodo = gestorPropiedades.valorPropertie(NOMBRE_HOST_PROCESO);

		List<ProcesoVO> salida = procesoDao.getProceso(nodo, tipo);
		if (salida != null && salida.size() == 1) {
			return salida.get(0).getId().getProceso();
		}
		return null;
	}

	/**
	 * @see ProcesoDao
	 */
	@Override
	@Transactional(readOnly = true)
	public String getNombreProceso(Integer tipo) {
		return getNombreProceso(BigDecimal.valueOf(tipo.longValue()));
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getIntentosMaximos(String nombreProceso, String nodo) {
		return procesoDao.getIntentosMaximos(nombreProceso, nodo);
	}

	/**
	 * @see EnvioDataDao#actualizarUltimaEjecucion(ColaBean, String, String)
	 */
	@Override
	@Transactional
	public void actualizarUltimaEjecucion(ColaBean colaBean, String resultadoEjecucion, String excepcion) {
		envioDataDao.actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
	}

	/**
	 * @see EnvioDataDao#actualizarUltimaEjecucion(ColaBean, String, String)
	 */
	@Override
	@Transactional
	public void actualizarUltimaEjecucionNuevo(ColaDto cola, String resultadoEjecucion, String excepcion) {
		envioDataDao.actualizarUltimaEjecucionNuevo(cola.getProceso(), cola.getCola(), cola.getIdTramite(), cola.getRespuesta(), resultadoEjecucion, excepcion);
	}

	/**
	 * @see EnvioDataDao#actualizarUltimaEjecucion(String, String, String)
	 */
	@Override
	@Transactional
	public void actualizarEjecucion(String proceso, String respuesta, String resultadoEjecucion, String numCola) {
		envioDataDao.actualizarEjecucion(proceso, respuesta, resultadoEjecucion, numCola);
	}

	@Override
	@Transactional
	public void guardarEnvioData(String proceso, String respuesta, String resultadoEjecucion, String numCola) {
		EnvioDataVO envioDataVO = new EnvioDataVO();
		EnvioDataPK id = new EnvioDataPK();
		id.setCola(numCola);
		id.setCorrecta(resultadoEjecucion);
		id.setProceso(proceso);
		envioDataVO.setId(id);
		envioDataVO.setFechaEnvio(new Date());
		envioDataVO.setRespuesta(respuesta);

		envioDataDao.guardarOActualizar(envioDataVO);
	}

	@Override
	public void marcarSolicitudConErrorServicio(ColaBean cola, String respuestaError) {
		modeloSolicitud.errorServicio(cola.getIdEnvio(), respuestaError);
		notificarErrorServicio(cola, respuestaError);
	}

	@Override
	@Transactional
	public Date getUltimaFechaEnvioData(String proceso, String resultadoEjecucion) {
		List<EnvioDataVO> lista = envioDataDao.getUltimoEnvioData(proceso, resultadoEjecucion);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0).getFechaEnvio();
		}
		return null;
	}

	@Override
	public ColaBean tomarSolicitud(String nombreProceso, Integer hiloActivo) {
		ColaBean colaBean = null;
		if (nombreProceso != null && !nombreProceso.isEmpty() && hiloActivo != null) {
			try {
				colaBean = modeloSolicitud.tomarSolicitud(nombreProceso, hiloActivo.toString());
			} catch (SinSolicitudesExcepcion e) {
				log.info("Error al recuperar la solicitud del proceso " + nombreProceso + " en el hilo " + hiloActivo, e.getMensajeError1());
			}
		}
		return colaBean;
	}

	@Override
	public ColaDto tomarSolicitudNuevo(String nombreProceso, Integer hiloActivo) {
		ColaDto colaDto = null;
		if (nombreProceso != null && !nombreProceso.isEmpty() && hiloActivo != null) {
			String nodo = gestorPropiedades.valorPropertie(NOMBRE_HOST_PROCESO);
			ColaVO colaVO = servicioCola.tomarSolicitud(nombreProceso, hiloActivo.toString(), nodo);
			if (colaVO != null) {
				boolean existenEstados = servicioCola.comprobarEstados(colaVO, nombreProceso);
				if (!existenEstados) {
					colaDto = conversor.transform(colaVO, ColaDto.class);
				}
			}
		}
		return colaDto;
	}

	@Override
	public void borrarSolicitud(Long idEnvio, String resultado, String proceso, String nodo) {
		servicioCola.solicitudCorrecta(idEnvio, resultado, proceso, nodo);
	}

	@Override
	public void errorSolicitud(Long idEnvio) {
		servicioCola.solicitudErronea(idEnvio);
	}

	@Override
	public void errorServicio(Long idEnvio, String respuesta) {
		servicioCola.errorServicio(idEnvio, respuesta);
	}

	@Override
	@Transactional
	public boolean tratarRecuperable(ColaBean solicitud, String respuestaError) {
		boolean recuperable = false;
		if (solicitud != null) {
			if (solicitud.getProceso() != null && solicitud.getNumeroIntento() != null && solicitud.getIdEnvio() != null) {
				if (log.isInfoEnabled()) {
					log.info("tratar Recuperable:" + "proceso=" + solicitud.getProceso() + " cola=" + solicitud.getCola());
				}
				String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
				BigDecimal numIntentos = getIntentosMaximos(solicitud.getProceso(), nodo);
				if (solicitud.getNumeroIntento().intValue() < numIntentos.intValue()) {
					if (log.isInfoEnabled()) {
						log.info("numero de intentos de la solicitud es " + solicitud.getNumeroIntento() + " y el numero maximo de intentos es " + numIntentos);
					}
					if (ProcesosEnum.IMPRIMIRTRAMITES.getNombreEnum().equals(solicitud.getProceso())) {
						servicioCola.solicitudErroneaImprimirTramites(solicitud.getIdEnvio());
					} else {
						modeloSolicitud.errorSolicitud(solicitud.getIdEnvio(), respuestaError);
					}
					recuperable = true;
				} else {
					log.info("superado el numero de intentos maximos permitidos para la cola");
					marcarSolicitudConErrorServicio(solicitud, respuestaError);
				}
			} else {
				log.info("Ocurrio un error al recuperar la solicitud, se debe enviar la notificacion");
				marcarSolicitudConErrorServicio(solicitud, respuestaError);
			}
		}
		return recuperable;
	}

	@Override
	public void notificarErrorServicio(ColaBean cola, String error) {
		StringBuffer texto = new StringBuffer(
				"<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM): <br>La petición de envío con identificador: <b>")
						.append(cola.getIdEnvio()).append("</b> y número de expediente: <b> ").append(cola.getIdTramite()).append(
								"</b> ha pasado a estado 9 (ERROR DEL SERVICIO).<br>- Información de la petición:<br>   * Tipo de trámite : <b>").append(cola.getTipoTramite()).append(
										"</b><br>   * Proceso         : <b>").append(cola.getProceso()).append("</b><br>   * Respuesta       : <b>").append(error).append("</b><br></span><br><br>");

		cola.setRespuesta(error);
		notificarErrorServicio(texto.toString(), cola);
	}

	@Override
	public void notificarErrorServicioNuevo(ColaDto cola, String error) {
		StringBuffer texto = new StringBuffer(
				"<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM): <br>La petición de envío con identificador: <b>")
						.append(cola.getIdEnvio()).append("</b> y número de expediente: <b> ").append(cola.getIdTramite()).append(
								"</b> ha pasado a estado 9 (ERROR DEL SERVICIO).<br>- Información de la petición:<br>   * Tipo de trámite : <b>").append(cola.getTipoTramite()).append(
										"</b><br>   * Proceso         : <b>").append(cola.getProceso()).append("</b><br>   * Respuesta       : <b>").append(error).append("</b><br></span><br><br>");

		cola.setRespuesta(error);
		notificarErrorServicioNuevo(texto.toString(), cola);
	}

	@Override
	public void notificarErrorServicio(String proceso, String error) {
		StringBuffer texto = new StringBuffer(
				"<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM): <br>ERROR DEL SERVICIO: <br>")
						.append("* Proceso         : <b>").append(proceso).append("</b><br>   * Respuesta       : <b>").append(error).append("</b><br></span><br><br>");

		ColaBean cola = new ColaBean();
		cola.setProceso(proceso);
		cola.setCola("0");
		cola.setRespuesta(error);
		notificarErrorServicio(texto.toString(), cola);

		// Mantis 18664. David Sierra. Correo especial de aviso para error servicio del proceso DOCUMENTOBASE
		if ("DOCUMENTOBASE".equals(proceso)) {
			notificarErrorServicioDocumentoBase(texto.toString());
		}
	}

	/**
	 * Encapsula la lógica de envío de un correo de notificación de que cierta petición ha superado su número máximo de envios con ERROR_RECUPERABLE pasando a estado 9 : ERROR_SERVICIO
	 * @param idEnvio
	 * @param error
	 */
	private void notificarErrorServicio(String texto, ColaBean cola) {
		log.info("Inicio del metodo notificarErrorServicio.");
		try {
			String destinatariosNotificacion = gestorPropiedades.valorPropertie("numero.destinatarios.notificacion.error.servicio");

			StringBuffer direcciones = new StringBuffer();

			if (destinatariosNotificacion != null) {
				try {
					// Convierte la propiedad en número para establecer la
					// longitud del array de direcciones:
					int numDesNot = Integer.parseInt(destinatariosNotificacion);
					boolean flagFirst = true;
					for (int i = 0; i < numDesNot; i++) {
						// Recorre el fichero de propiedades recuperando las
						// direcciones:
						String direccion = gestorPropiedades.valorPropertie("direccion.notificacion.error.servicio." + (i + 1));
						if (direccion != null) {
							if (flagFirst) {
								flagFirst = false;
							} else {
								direcciones.append(SEPARATOR_TOKEN);
							}
							direcciones.append(direccion);
						}
					}
				} catch (NumberFormatException e) {
					log.error("La propiedad 'numero.destinatarios.notificacion.error.servicio' ha de ser un numero", e);
				}
			}

			StringBuffer subject = new StringBuffer("ERROR DEL SERVICIO EN: ").append(gestorPropiedades.valorPropertie("Entorno"));

			ResultBean resultEnvio;
			MensajeErrorServicioDto mensaje = new MensajeErrorServicioDto();
			mensaje.setCola(cola.getCola());
			mensaje.setDescripcion(cola.getRespuesta());
			mensaje.setFecha(new Date());
			mensaje.setIdEnvio(cola.getIdEnvio().longValue());
			mensaje.setProceso(cola.getProceso());
			ResultadoMensajeErrorServicio respuesta = mensajeErrorServicio.guardar(mensaje);
			if (respuesta.isError()) {
				resultEnvio = servicioCorreo.enviarCorreo(texto, null, null, subject.toString(), null, null, direcciones.toString(), null, null);
				if (resultEnvio == null || resultEnvio.getError())
					throw new OegamExcepcion("Error en la notificacion de error servicio");
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("Error en la notificacion de error servicio", e);
		}
		log.info("Fin del metodo notificarErrorServicio");

	}

	private void notificarErrorServicioNuevo(String texto, ColaDto cola) {
		log.info("Inicio del metodo notificarErrorServicio.");
		try {
			String destinatariosNotificacion = gestorPropiedades.valorPropertie("numero.destinatarios.notificacion.error.servicio");

			StringBuffer direcciones = new StringBuffer();

			if (destinatariosNotificacion != null) {
				try {
					// Convierte la propiedad en número para establecer la
					// longitud del array de direcciones:
					int numDesNot = Integer.parseInt(destinatariosNotificacion);
					boolean flagFirst = true;
					for (int i = 0; i < numDesNot; i++) {
						// Recorre el fichero de propiedades recuperando las
						// direcciones:
						String direccion = gestorPropiedades.valorPropertie("direccion.notificacion.error.servicio." + (i + 1));
						if (direccion != null) {
							if (flagFirst) {
								flagFirst = false;
							} else {
								direcciones.append(SEPARATOR_TOKEN);
							}
							direcciones.append(direccion);
						}
					}
				} catch (NumberFormatException e) {
					log.error("La propiedad 'numero.destinatarios.notificacion.error.servicio' ha de ser un numero", e);
				}
			}

			StringBuffer subject = new StringBuffer("ERROR DEL SERVICIO EN: ").append(gestorPropiedades.valorPropertie("Entorno"));

			ResultBean resultEnvio;
			MensajeErrorServicioDto mensaje = new MensajeErrorServicioDto();
			mensaje.setCola(cola.getCola());
			mensaje.setDescripcion(cola.getRespuesta());
			mensaje.setFecha(new Date());
			mensaje.setIdEnvio(cola.getIdEnvio().longValue());
			mensaje.setProceso(cola.getProceso());
			ResultadoMensajeErrorServicio respuesta = mensajeErrorServicio.guardar(mensaje);
			if (respuesta.isError()) {
				resultEnvio = servicioCorreo.enviarCorreo(texto, null, null, subject.toString(), null, null, direcciones.toString(), null, null);
				if (resultEnvio == null || resultEnvio.getError())
					throw new OegamExcepcion("Error en la notificacion de error servicio");
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("Error en la notificacion de error servicio", e);
		}
		log.info("Fin del metodo notificarErrorServicio");

	}

	/**
	 * Encapsula la logica de envio de un correo de notificacion de que cierta peticion del proceso DOC_BASE_GENERATOR ha superado su número máximo de envios con ERROR_RECUPERABLE pasando a estado 9 : ERROR_SERVICIO
	 * @param idEnvio
	 * @param error
	 */
	private void notificarErrorServicioDocumentoBase(String texto) {
		log.info("Inicio del metodo notificarErrorServicioDocumentoBase.");

		try {
			String direccionErrorYerbabuena = gestorPropiedades.valorPropertie("direccion.error.servicio.yerbanuena.noche");
			String direccionOcultaErrorYerbabuena = gestorPropiedades.valorPropertie("direccion.error.servicio.yerbanuena.noche.cco");

			StringBuffer subject = new StringBuffer("ERROR EN LA CREACION DE DOCUMENTOS BASE NOCHE EN: ").append(gestorPropiedades.valorPropertie("Entorno"));
			ResultBean resultEnvio;
			resultEnvio = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject.toString(), direccionErrorYerbabuena, null, direccionOcultaErrorYerbabuena, null, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Error en la notificacion de error servicio");

		} catch (OegamExcepcion | IOException e) {
			log.error("Error en la notificacion de error servicio", e);
		}
		log.info("Fin del metodo notificarErrorServicio");
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hayEnvio(String proceso) {
		boolean resultado = false;
		try {
			List<EnvioDataVO> lista = envioDataDao.getExisteEnvioData(proceso, ConstantesProcesos.EJECUCION_CORRECTA, utilesFecha.setHorasMinutosSegundosEnDate(Calendar.getInstance().getTime(),
					"00:00:00"), Calendar.getInstance().getTime());
			if (lista != null && !lista.isEmpty()) {
				resultado = true;
			}
		} catch (Exception e) {
			log.error("Error al consultar la ejecucion en envio data, error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<String> getListaEnvio(String proceso) {
		ArrayList<String> envioStock = new ArrayList<String>();
		try {
			List<EnvioDataVO> lista = envioDataDao.getExisteEnvioDataEnvioStock(proceso, ConstantesProcesos.EJECUCION_ENVIO_STOCK, utilesFecha.setHorasMinutosSegundosEnDate(Calendar.getInstance()
					.getTime(), "00:00:00"), Calendar.getInstance().getTime());
			if (lista != null && !lista.isEmpty()) {
				for (EnvioDataVO envioData : lista) {
					envioStock.add(envioData.getRespuesta());
				}
			}
		} catch (Exception e) {
			log.error("Error al consultar la ejecuicion en envio data del stock de envio, error: ", e);
		}
		return envioStock;
	}

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo) {
		ProcesoVO procesoVO = new ProcesoVO();
		ProcesoPK id = new ProcesoPK();
		id.setProceso(proceso);
		if (nodo != null && !nodo.isEmpty()) {
			id.setNodo(nodo);
		}

		procesoVO.setId(id);
		Log.info("Dentro de ServicioProcesosImpl.java:getProcesoPorProcesoYNodo " + " procesos " + proceso + " nodo " + nodo);
		List<ProcesoVO> listaProceso = procesoDao.buscar(procesoVO);
		if (listaProceso != null && !listaProceso.isEmpty()) {
			return listaProceso.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProceso(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				return procesoDao.getProcesoVO(proceso);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el proceso, error: ", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<EnvioDataVO> recuperarUltimasEjecuciones(String proceso, String resultadoEjecucion) {
		List<EnvioDataVO> lista = envioDataDao.getUltimoEnvioData(proceso, resultadoEjecucion);
		return lista;
	}

	@Override
	@Transactional
	public List<EnvioDataVO> recuperarUltimasOk(String proceso, String cola) {
		List<EnvioDataVO> lista = envioDataDao.recuperarUltimasOk(proceso, cola);
		return lista;
	}
	
	@Override
	@Transactional
	public List<EnvioDataVO> ejecucionesUltimasProceso(String proceso, String correcta) {
		List<EnvioDataVO> lista = envioDataDao.getListaEnvioDataPorProcesoYTipoEjecucionPorCola(proceso, correcta);
		return lista;
	}
	
	@Override
	@Transactional
	public List<ProcesoVO> getProcesosPatron(String nodo, ArrayList<String> procesosPatron) {
		List<ProcesoVO> lista = procesoDao.getProcesosPatron(nodo, procesosPatron);
		return lista;
	}
	
}
