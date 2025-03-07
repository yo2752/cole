package org.gestoresmadrid.oegamSanciones.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.sancion.constantes.ConstantesSancion;
import org.gestoresmadrid.core.sancion.model.enumerados.Motivo;
import org.gestoresmadrid.core.sancion.model.vo.SancionVO;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamSanciones.service.ServicioPersistenciaSanciones;
import org.gestoresmadrid.oegamSanciones.service.ServicioSanciones;
import org.gestoresmadrid.oegamSanciones.service.ServicioValidacionSanciones;
import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionListadosMotivosDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.thoughtworks.xstream.XStream;

import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

@Service
public class ServicioSancionesImpl implements ServicioSanciones {

	private static final long serialVersionUID = -4456484815869976627L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSancionesImpl.class);

	private static final int ESTADO = 1;
	private static final int ESTADO_INICIADO = 1;
	private static final int ESTADO_SOLICITADO = 2;
	private static final int ESTADO_FINALIZADO = 3;
	private static final int ESTADO_BAJA = 0;
	private static final String COBRAR_CREDITOS = "si";

	@Autowired
	private ServicioPersistenciaSanciones servicioPersistencia;

	@Autowired
	private ServicioValidacionSanciones servicioValidacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ServicioSancionesImpl() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public SancionDto getSancion(SancionDto sancionInDto) {
		SancionVO sancion = servicioPersistencia.getSancionPorId(sancionInDto.getIdSancion());

		return conversor.transform(sancion, SancionDto.class);

	}

	@Override
	public SancionDto getSancionPorId(Integer idSancion) {
		SancionVO sancion = servicioPersistencia.getSancionPorId(idSancion);
		return conversor.transform(sancion, SancionDto.class);
	}

	@Override
	public SancionListadosMotivosDto getListado(String numColegiado, Fecha fechaListado) throws Throwable {
		SancionVO sancion = new SancionVO();

		SancionListadosMotivosDto sancionListadosMotivosDto = new SancionListadosMotivosDto();

		if (StringUtils.isNotBlank(numColegiado)) {
			sancion.setNumColegiado(numColegiado);
			sancion.setFechaPresentacion(fechaListado.getFecha());

			List<SancionDto> listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.ALEGACION.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoAle(listSancionDto);
			listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.RECURSO.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoRec(listSancionDto);

		} else {
			sancion.setFechaPresentacion(fechaListado.getFecha());

			List<SancionDto> listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.ALEGACION.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoAle(listSancionDto);
			listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.RECURSO.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoRec(listSancionDto);
		}

		return sancionListadosMotivosDto;
	}

	private List<SancionDto> obtenerListadoPorMotivo(SancionVO sancion, Integer motivo) throws Throwable {
		List<SancionDto> listSancionDto;

		sancion.setMotivo(motivo);
		List<SancionVO> listSan = servicioPersistencia.obtenerListadoPorMotivo(sancion);

		listSancionDto = conversor.transform(listSan, SancionDto.class);

		return listSancionDto;
	}

	private String transformToXML(SancionListadosMotivosDto sancionListadosMotivosDto) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

		XStream xStream = new XStream();
		xStream.processAnnotations(SancionDto.class);
		xml += xStream.toXML(sancionListadosMotivosDto);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");

		return xml;
	}

	@Override
	public ResultadoSancionesBean generarInforme(String[] codSeleccionados) throws Exception {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);

		SancionListadosMotivosDto sancionListadosMotivosDto;

		// Validación de las sanciones seleccionadas
		ResultadoSancionesBean resultadoValidacion = servicioValidacion.validacionImprimirListado(codSeleccionados);
		if (resultadoValidacion.getError()) {
			resultado.setError(true);
			resultado.setMensaje(resultadoValidacion.getMensaje());
			return resultado;
		}

		// rellenamos el mapa de los parametros del informe
		Map<String, Object> params = new HashMap<String, Object>();

		sancionListadosMotivosDto = resultadoValidacion.getSancionListadosMotivosDto();
		try {
			params.put("FECHA_PRESENTACION", resultadoValidacion.getFechaPresentacion().getFecha());
		} catch (ParseException pe) {
			resultado.setError(true);
			resultado.setMensaje("Ha habido un problema con la fecha de presentación");
			log.error(pe);
			return resultado;
		}

		// parametros de listados
		params.put("LISTADOALE", sancionListadosMotivosDto.getListaSancionMotivoAle());
		params.put("LISTADOREC", sancionListadosMotivosDto.getListaSancionMotivoRec());
		params.put("LISTADORES", sancionListadosMotivosDto.getListaSancionMotivoRes());

		// ruta donde esta el informe.
		String ruta = gestorPropiedades.valorPropertie(ConstantesSancion.DIR_PLANTILLAS);
		String nombreInforme = gestorPropiedades.valorPropertie(ConstantesSancion.PLANTILLA_COLEGIO);
		params.put("IMG_DIR", ruta);
		params.put("SUBREPORT_DIR", ruta);

		// Generación del informe
		String xml = transformToXML(sancionListadosMotivosDto);

		try {
			ReportExporter re = new ReportExporter();
			byte[] byteFinal = re.generarInforme(ruta, nombreInforme, "pdf", xml, "Sancion", params, null);
			resultado.setByteFinal(byteFinal);
			resultado.setFechaPresentacion(resultadoValidacion.getFechaPresentacion());
		} catch (JRException jre) {
			resultado.setError(true);
			resultado.setMensaje("Error al generar el informe");
			throw new Exception(jre);
		} catch (ParserConfigurationException pce) {
			resultado.setError(true);
			resultado.setMensaje("Error al generar el informe");
			throw new Exception(pce);
		}

		return resultado;
	}

	public static ILoggerOegam getLog() {
		return log;
	}

	@Override
	public ResultadoSancionesBean guardarSancion(SancionDto sancionDto, BigDecimal idUsuario, boolean esAdmin) throws Exception {

		ResultadoSancionesBean validacionGuardar = servicioValidacion.validacionGuardarSancion(sancionDto);
		if (validacionGuardar.getError()) {
			return validacionGuardar;
		}

		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		sancionDto.setEstado(ESTADO);
		if (StringUtils.isNotBlank(sancionDto.getNombre())) {
			sancionDto.setNombre(sancionDto.getNombre().toUpperCase());
		}
		sancionDto.setApellidos(sancionDto.getApellidos().toUpperCase());
		sancionDto.setDni(sancionDto.getDni().toUpperCase());

		// Si está en estado finalizado entonces no hay que cambiar el estado
		if (sancionDto.getEstadoSancion() == null || ESTADO_FINALIZADO != sancionDto.getEstadoSancion()) {
			if (sancionDto.getFechaPresentacion() != null) {
				sancionDto.setEstadoSancion(ESTADO_SOLICITADO);
			} else {
				sancionDto.setEstadoSancion(ESTADO_INICIADO);
			}
		}

		SancionVO sancion = conversor.transform(sancionDto, SancionVO.class);
		sancion = servicioPersistencia.guardarOActualizar(sancion);
		if (sancion != null) {
			sancionDto.setIdSancion(sancion.getIdSancion());
			if (!esAdmin && gestorPropiedades.valorPropertie("cobrar.sanciones").equals(COBRAR_CREDITOS)) {
				try {
					descontarCreditos(sancionDto.getNumColegiado(), idUsuario);
				} catch (DescontarCreditosExcepcion e) {
					log.error("Se ha producido un error al descontar creditos: ", e);
					throw new Exception("No dispone de suficientes créditos para poder dar de alta la sanción.");
				}
			}
		} else {
			resultado.setError(true);
			resultado.setMensaje("Se ha producido un error al intentar dar de alta la sanción.");
			log.error("Se ha producido un error al intentar dar de alta la sanción.");
		}

		if (!resultado.getError()) {
			resultado.setSancionDto(conversor.transform(sancion, SancionDto.class));
			resultado.setMensaje("Sanción guardada");
		}

		return resultado;
	}

//	private ResultadoSancionesBean guardar(SancionDto sancionDto, BigDecimal idUsuario, boolean esAdmin) throws Exception {
//		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
//		SancionVO sancion = conversor.transform(sancionDto, SancionVO.class);
//
//		sancion = servicioPersistencia.guardarOActualizar(sancion);
//		if (sancion != null) {
//			sancionDto.setIdSancion(sancion.getIdSancion());
//			if (!esAdmin && gestorPropiedades.valorPropertie("cobrar.sanciones").equals(COBRAR_CREDITOS)) {
//				try {
//					descontarCreditos(sancionDto.getNumColegiado(), idUsuario);
//				} catch (DescontarCreditosExcepcion e) {
//					log.error("Se ha producido un error al descontar creditos: ", e);
//					throw new Exception("No dispone de suficientes créditos para poder dar de alta la sanción.");
//				}
//			}
//		} else {
//			resultado.setError(true);
//			resultado.setMensaje("Se ha producido un error al intentar dar de alta la sanción");
//			log.error("Se ha producido un error al intentar dar de alta la sanción");
//		}
//
//		if (!resultado.getError()) {
//			resultado.setSancionDto(conversor.transform(sancion, SancionDto.class));
//			resultado.setMensaje("Sanción guardada");
//		}
//
//		return resultado;
//	}

	private void descontarCreditos(String numColegiado, BigDecimal idUsuario) throws DescontarCreditosExcepcion {
		BigDecimal idContrato = utilesColegiado.getIdUsuarioByNumColegiado(numColegiado);
		ResultBean resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Sancion.getValorEnum(), idContrato, BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.SAN);

		if (resultado.getError()) {
			log.error("Error al descontar créditos de la operación");
			throw new DescontarCreditosExcepcion(resultado.getMensaje());
		}
	}

	@Override
	public ResultadoSancionesBean borrarSancion(String[] codSeleccionados) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		for (String idSancion : codSeleccionados) {
			ResultadoSancionesBean resultadoUnitarioBorrar = borrar(idSancion);
			if (resultadoUnitarioBorrar.getError() != null && resultadoUnitarioBorrar.getError()) {
				if (resultado.getListaMensajesError() == null) {
					resultado.setListaMensajesError(new ArrayList<String>(1));
					resultado.setError(true);
				}
				resultado.getListaMensajesError().add(resultadoUnitarioBorrar.getMensaje());
			} else {
				if (resultado.getListaMensajesAvisos() == null) {
					resultado.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultado.getListaMensajesAvisos().add("La sanción " + idSancion + " fue borrada correctamente.");
			}
		}

		return resultado;
	}

	private ResultadoSancionesBean borrar(String idSancion) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		if (StringUtils.isNotBlank(idSancion)) {
			try {
				SancionVO sancion = servicioPersistencia.getSancionPorId(Integer.parseInt(idSancion));
				sancion.setEstado(ESTADO_BAJA);
				sancion = servicioPersistencia.guardarOActualizar(sancion);
				if (sancion == null) {
					resultado.setError(true);
					resultado.setMensaje("Se ha producido un error no esperado al intentar borrar la sanción.");
					log.error("Se ha producido un error no esperado al intentar borrar la sanción.");
				}
			} catch (Exception e) {
				log.error("Se ha producido un error no esperado al intentar borrar la sanción: " + idSancion, e);
				resultado.setError(true);
				resultado.setMensaje("Se ha producido un error no esperado al intentar borrar la sanción: " + idSancion);
			}
		} else {
			resultado.setError(true);
			resultado.setMensaje("No existe la sanción");
		}
		return resultado;
	}

	@Override
	public ResultadoSancionesBean cambiarEstados(String[] idsSancion, String cambioEstado) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		for (String idSancion : idsSancion) {
			ResultadoSancionesBean resultadoUnitarioCambiarEstado = cambiarEstado(idSancion, cambioEstado);
			if (resultadoUnitarioCambiarEstado.getError()) {
				if (resultado.getListaMensajesAvisos() == null) {
					resultado.setListaMensajesAvisos(new ArrayList<String>());
					resultado.setError(true);
				}
				resultado.addListaMensajeError(resultadoUnitarioCambiarEstado.getMensaje());
			} else {
				if (resultado.getListaMensajesAvisos() == null) {
					resultado.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultado.addListaMensajeAvisos("La sanción: " + idSancion + " ha sido cambiada de estado correctamente");
			}
		}

		return resultado;
	}

	private ResultadoSancionesBean cambiarEstado(String idSancion, String cambioEstado) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		if (StringUtils.isNotBlank(idSancion)) {
			try {
				SancionVO sancion = servicioPersistencia.getSancionPorId(Integer.parseInt(idSancion));
				if (StringUtils.isNotBlank(cambioEstado)) {
					sancion.setEstadoSancion(Integer.parseInt(cambioEstado));

					sancion = servicioPersistencia.guardarOActualizar(sancion);

					if (sancion == null) {
						resultado.setError(true);
						resultado.setMensaje("Seleccione un estado para poder realizar el cambio.");
						log.error("Se ha producido un error no esperado al intentar actualizar la sanción");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Seleccione un estado para poder realizar el cambio.");
				}
			} catch (Exception e) {
				log.error("Se ha producido un error no esperado al intentar cambiar el estado de la sanción: " + idSancion, e);
				resultado.setError(true);
				resultado.setMensaje("Se ha producido un error no esperado al intentar cambiar el estado de la sanción: " + idSancion);
			}
		} else {
			resultado.setError(true);
			resultado.setMensaje("No existe la sanción");
		}

		return resultado;
	}

}
