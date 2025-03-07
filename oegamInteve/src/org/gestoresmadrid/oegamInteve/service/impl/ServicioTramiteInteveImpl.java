package org.gestoresmadrid.oegamInteve.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.enumerados.TipoInformeInteve;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.interviniente.service.ServicioComunIntervinienteTrafico;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamInteve.conversor.service.ConversorInteve;
import org.gestoresmadrid.oegamInteve.service.ServicioPersistenciaTramiteInteve;
import org.gestoresmadrid.oegamInteve.service.ServicioTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoWSInteveBean;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoSolInteveDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.InInformeCompleto;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ObtenerInformeCompleto;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ObtenerInformeCompletoResponse;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ServicioInformesCompletoIntv;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ServicioWebInformesCompletoIntv;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ws.SecurityClientHandlerInteveWsCompleto;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto.ws.SoapInteveWSCompletoWSHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioTramiteInteveImpl implements ServicioTramiteInteve {

	private static final long serialVersionUID = 4345583648357131361L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteInteveImpl.class);

	private static final String NO_EXISTEN_DATOS_PARA_CONTRATO_INDICADO = "No existen datos para el contrato indicado.";
	private static final String NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE = "No existen datos en BBDD para el expediente: ";

	@Autowired
	ServicioPersistenciaTramiteInteve servicioPersistencia;

	@Autowired
	ConversorInteve conversor;

	@Autowired
	ServicioComunContrato servicioComunContrato;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunIntervinienteTrafico servicioComunIntervinienteTrafico;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Autowired
	ServicioComunPersona servicioComunPersona;

	@Autowired
	ServicioComunTasa servicioComunTasa;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoTramiteInteveBean obtenerListadoTasas(Long idContrato, String codigoTasa) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			ContratoVO contrato = servicioComunContrato.getContrato(idContrato);
			if (contrato != null) {
				List<TasaDto> listaCodigosTasaDto = null;
				String gestionTasaAlmacen = gestorPropiedades.valorPropertie("gestion.almacen.tasa");
				if ("SI".equals(gestionTasaAlmacen)) {
					List<TasaInteveVO> listaCodigosTasa = servicioComunTasa.obtenerTasasInteveContrato(idContrato, TipoTasa.CuatroUno.getValorEnum());
					if (listaCodigosTasa == null || listaCodigosTasa.isEmpty()) {
						listaCodigosTasaDto = new ArrayList<>();
					} else {
						listaCodigosTasaDto = conversor.transform(listaCodigosTasa, TasaDto.class);
					}
				} else {
					List<TasaVO> listaCodigosTasa = servicioComunTasa.obtenerTasasContrato(idContrato, TipoTasa.CuatroUno.getValorEnum());
					if (listaCodigosTasa == null || listaCodigosTasa.isEmpty()) {
						listaCodigosTasaDto = new ArrayList<>();
					} else {
						listaCodigosTasaDto = conversor.transform(listaCodigosTasa, TasaDto.class);
					}
				}
				if (codigoTasa != null && !codigoTasa.isEmpty()) {
					TasaDto tasa = new TasaDto();
					tasa.setCodigoTasa(codigoTasa);
					List<TasaDto> respuestaConTasaSeleccionada = listaCodigosTasaDto;
					if (!listaCodigosTasaDto.contains(tasa) && !listaCodigosTasaDto.isEmpty()) {
						respuestaConTasaSeleccionada.add(0, tasa);
						resultado.setListaTasas(respuestaConTasaSeleccionada);
					} else {
						respuestaConTasaSeleccionada = new ArrayList<>();
						respuestaConTasaSeleccionada.add(0, tasa);
						resultado.setListaTasas(respuestaConTasaSeleccionada);
					}
				} else {
					resultado.setListaTasas(listaCodigosTasaDto);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(NO_EXISTEN_DATOS_PARA_CONTRATO_INDICADO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cargar las tasas, error: ", e);
			resultado.setError(Boolean.FALSE);
			resultado.setMensaje("Ha sucedido un error a la hora de cargar las tasas.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean getSolicitudInteve(Long idSolInteve) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = servicioPersistencia.getTramiteSolIntevePorId(idSolInteve);
			if (tramiteTraficoSolInteveVO != null) {
				resultado.setTramiteSolicitudInteve(conversor.transform(tramiteTraficoSolInteveVO, TramiteTraficoSolInteveDto.class));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos en BBDD para obtener su solicitud.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la solicitud con id: " + idSolInteve + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la solicitud.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean getSolicitante(String nif, Long idContrato) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			ContratoVO contrato = servicioComunContrato.getContrato(idContrato);
			if (contrato != null) {
				PersonaVO persona = servicioComunPersona.getPersona(nif.toUpperCase(), contrato.getColegiado().getNumColegiado());
				IntervinienteTraficoDto intervinienteTraficoDto = new IntervinienteTraficoDto();
				intervinienteTraficoDto.setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
				intervinienteTraficoDto.setNifInterviniente(nif);
				PersonaDto personaDto = new PersonaDto();
				personaDto.setNif(nif);
				personaDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
				if (persona != null) {
					personaDto.setNombre(persona.getNombre());
					personaDto.setApellido1RazonSocial(persona.getApellido1RazonSocial());
					personaDto.setApellido2(persona.getApellido2());
				}
				intervinienteTraficoDto.setPersona(personaDto);
				resultado.setSolicitante(intervinienteTraficoDto);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(NO_EXISTEN_DATOS_PARA_CONTRATO_INDICADO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el solictante con nif: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el solictante con NIF: " + nif);
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean getRepresentante(String nif, Long idContrato) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			ContratoVO contrato = servicioComunContrato.getContrato(idContrato);
			if (contrato != null) {
				PersonaVO persona = servicioComunPersona.getPersona(nif.toUpperCase(), contrato.getColegiado().getNumColegiado());
				IntervinienteTraficoDto intervinienteTraficoDto = new IntervinienteTraficoDto();
				intervinienteTraficoDto.setTipoInterviniente(TipoInterviniente.RepresentanteSolicitante.getValorEnum());
				intervinienteTraficoDto.setNifInterviniente(nif);
				PersonaDto personaDto = new PersonaDto();
				personaDto.setNif(nif);
				personaDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
				if (persona != null) {
					personaDto.setNombre(persona.getNombre());
					personaDto.setApellido1RazonSocial(persona.getApellido1RazonSocial());
					personaDto.setApellido2(persona.getApellido2());
				}
				intervinienteTraficoDto.setPersona(personaDto);
				resultado.setRepresentante(intervinienteTraficoDto);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(NO_EXISTEN_DATOS_PARA_CONTRATO_INDICADO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el solictante con nif: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el solictante con NIF: " + nif);
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean obtenerTramitePantalla(TramiteTraficoInteveDto tramiteInteve) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveVO = servicioPersistencia.getTramiteInteve(tramiteInteve.getNumExpediente(), Boolean.TRUE);
			if (tramiteTraficoInteveVO != null) {
				tramiteInteve = conversor.transform(tramiteTraficoInteveVO, TramiteTraficoInteveDto.class);
				if (tramiteTraficoInteveVO.getIntervinienteTraficosAsList() != null && !tramiteTraficoInteveVO.getIntervinienteTraficosAsList().isEmpty()) {
					for (IntervinienteTraficoVO interviniente : tramiteTraficoInteveVO.getIntervinienteTraficosAsList()) {
						if (TipoInterviniente.Solicitante.getValorEnum().equals(interviniente.getId().getTipoInterviniente())) {
							tramiteInteve.setSolicitante(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(interviniente.getId().getTipoInterviniente())) {
							tramiteInteve.setRepresentante(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}
				resultado.setTramiteInteve(tramiteInteve);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + tramiteInteve.getNumExpediente());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite con numExpediente: " + tramiteInteve.getNumExpediente() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el trámite con numExpediente: " + tramiteInteve.getNumExpediente());
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean cambioEstado(BigDecimal numExpediente, String estadoNuevo, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveVO = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			if (tramiteTraficoInteveVO != null) {
				BigDecimal estadoAnterior = tramiteTraficoInteveVO.getEstado();
				servicioPersistencia.actualizarEstadoTramiteInteve(tramiteTraficoInteveVO.getNumExpediente(), estadoNuevo);
				guardarEvolucionTramite(tramiteTraficoInteveVO.getNumExpediente(), estadoAnterior, new BigDecimal(estadoNuevo), idUsuario, new Date());
				for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO : tramiteTraficoInteveVO.getTramitesInteves()) {
					servicioPersistencia.actualizarCambioEstadoTramiteSolInteve(tramiteTraficoSolInteveVO, estadoAnterior.toString(), estadoNuevo);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del trámite con numExpediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado del trámite con numExpediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean guardarTramiteInteve(TramiteTraficoInteveDto tramiteInteve, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			resultado = validarDatosMinimosGuardado(tramiteInteve);
			if (!resultado.getError()) {
				Boolean guardarEvolucion = Boolean.FALSE;
				TramiteTraficoInteveVO tramiteInteveVO = null;
				Date fecha = new Date();
				BigDecimal estadoAnterior = null;
				ContratoVO contrato = servicioComunContrato.getContrato(tramiteInteve.getContrato().getIdContrato().longValue());
				if (tramiteInteve.getNumExpediente() != null) {
					tramiteInteveVO = servicioPersistencia.getTramiteInteve(tramiteInteve.getNumExpediente(), Boolean.TRUE);
					if (tramiteInteveVO != null) {
						tramiteInteveVO.setRefPropia(tramiteInteve.getRefPropia());
						estadoAnterior = tramiteInteveVO.getEstado();
						if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(estadoAnterior.toString())) {
							guardarEvolucion = Boolean.TRUE;
						}
						tramiteInteveVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteInteveVO.setFechaUltModif(fecha);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + tramiteInteve.getNumExpediente());
						return resultado;
					}
					servicioPersistencia.guardarOactualizarTramiteInteve(tramiteInteveVO);
				} else {
					guardarEvolucion = Boolean.TRUE;
					tramiteInteveVO = conversor.transform(tramiteInteve, TramiteTraficoInteveVO.class);
					if (tramiteInteveVO.getNumColegiado() == null || tramiteInteveVO.getNumColegiado().isEmpty()) {
						tramiteInteveVO.setNumColegiado(contrato.getColegiado().getNumColegiado());
					}
					tramiteInteveVO.setFechaAlta(fecha);
					tramiteInteveVO.setFechaUltModif(fecha);
					tramiteInteveVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					UsuarioVO usuario = new UsuarioVO();
					usuario.setIdUsuario(idUsuario);
					tramiteInteveVO.setUsuario(usuario);
					tramiteInteveVO.setTipoTramite(TipoTramiteTrafico.Inteve.getValorEnum());
					ResultadoBean resultGenNumExp = servicioTramiteTrafico.crearTramite(tramiteInteveVO.getContrato().getIdContrato(),
							tramiteInteveVO.getFechaAlta(), tramiteInteveVO.getTipoTramite(), idUsuario, tramiteInteveVO.getNumColegiado(),
							new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
					if (resultGenNumExp.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultGenNumExp.getMensaje());
					} else {
						tramiteInteveVO.setNumExpediente(resultGenNumExp.getNumExpediente());
					}
					estadoAnterior = BigDecimal.ZERO;
				}
				if (!resultado.getError()) {
					if (guardarEvolucion) {
						guardarEvolucionTramite(tramiteInteveVO.getNumExpediente(), estadoAnterior, new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()), idUsuario, fecha);
					}
					resultado.setNumExpediente(tramiteInteveVO.getNumExpediente());
					guardarSolicitante(tramiteInteve, tramiteInteveVO.getNumExpediente(), contrato.getColegiado().getNumColegiado(), resultado);
					guardarRepresentante(tramiteInteve, tramiteInteveVO.getNumExpediente(), contrato.getColegiado().getNumColegiado(), resultado);
					guardarSolicitudInteve(tramiteInteve, tramiteInteveVO.getNumExpediente(), resultado, contrato.getIdContrato(), idUsuario);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el trámite con numExpediente: " + tramiteInteve.getNumExpediente() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el trámite con numExpediente: " + tramiteInteve.getNumExpediente());
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean eliminarSolicitudes(String codSeleccionados, Long idContrato, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)) {
				String[] ids = codSeleccionados.split(",");
				for (String id : ids) {
					try {
						TramiteTraficoSolInteveVO traficoSolInteveBBDD = servicioPersistencia.getTramiteSolIntevePorId(new Long(id));
						ResultadoTramiteInteveBean resultSol = validarEliminarSolicitud(traficoSolInteveBBDD, id);
						if (!resultSol.getError()) {
							servicioPersistencia.eliminarSolicitud(Long.valueOf(id), idContrato, idUsuario);
						} else {
							resultado.addListaMensaje(resultSol.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reiniciar la solicitud con id: " + id + ", error: ", e);
						resultado.addListaMensaje("Ha sucedido un error a la hora de reiniciar la solicitud con id: " + id);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna solicitud para eliminar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar solicitudes: , error: ", e);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarEliminarSolicitud(TramiteTraficoSolInteveVO traficoSolInteveBBDD, String id) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (traficoSolInteveBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han enciontrado datos para la solicitud con id: " + id);
		} else if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(traficoSolInteveBBDD.getEstado()) || EstadoTramiteTrafico.Pendiente_Envio.getValorEnum().equals(
				traficoSolInteveBBDD.getEstado())) {
			resultado.setError(Boolean.TRUE);
			if (StringUtils.isNotBlank(traficoSolInteveBBDD.getMatricula())) {
				resultado.setMensaje("La solicitud de la matrícula: " + traficoSolInteveBBDD.getMatricula() + " no se puede eliminar porque se encuentra en estado: " + EstadoTramiteTrafico
						.convertirTexto(traficoSolInteveBBDD.getEstado()));
			} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getBastidor())) {
				resultado.setMensaje("La solicitud del bastidor: " + traficoSolInteveBBDD.getBastidor() + " no se puede eliminar porque se encuentra en estado: " + EstadoTramiteTrafico.convertirTexto(
						traficoSolInteveBBDD.getEstado()));
			} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getNive())) {
				resultado.setMensaje("La solicitud del nive: " + traficoSolInteveBBDD.getNive() + " no se puede eliminar porque se encuentra en estado: " + EstadoTramiteTrafico.convertirTexto(
						traficoSolInteveBBDD.getEstado()));
			}
		}
		return resultado;
	}

	private void guardarSolicitudInteve(TramiteTraficoInteveDto tramiteInteve, BigDecimal numExpediente, ResultadoTramiteInteveBean resultado, Long idContrato, Long idUsuario) {
		try {
			if (esGuardadoSolicitudTramInteve(tramiteInteve)) {
				TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = null;
				if (tramiteInteve.getSolicitudInteve().getIdTramiteInteve() != null) {
					tramiteTraficoSolInteveVO = servicioPersistencia.getTramiteSolIntevePorId(tramiteInteve.getSolicitudInteve().getIdTramiteInteve());
				}
				if (tramiteTraficoSolInteveVO != null && EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTraficoSolInteveVO.getEstado())) {
					resultado.addListaMensaje("La solicitud de inteve para el vehículo no se puede modificar al estar en un estado finalizado telematciamente.");
				} else {
					if (tramiteTraficoSolInteveVO == null) {
						tramiteTraficoSolInteveVO = new TramiteTraficoSolInteveVO();
					}
					ResultadoTramiteInteveBean resultTasa = gestionarTasaSolictudInteve(tramiteInteve.getSolicitudInteve().getCodigoTasa(), tramiteTraficoSolInteveVO.getCodigoTasa(),
							numExpediente, idContrato, idUsuario);
					if (!resultTasa.getError()) {
						if (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getBastidor())) {
							tramiteTraficoSolInteveVO.setBastidor(tramiteInteve.getSolicitudInteve().getBastidor().toUpperCase());
						}
						if (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getMatricula())) {
							tramiteTraficoSolInteveVO.setMatricula(tramiteInteve.getSolicitudInteve().getMatricula().toUpperCase());
						}
						if (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getNive())) {
							tramiteTraficoSolInteveVO.setNive(tramiteInteve.getSolicitudInteve().getNive().toUpperCase());
						}
						if (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getTipoInforme())) {
							tramiteTraficoSolInteveVO.setTipoInforme(tramiteInteve.getSolicitudInteve().getTipoInforme());
						}
						tramiteTraficoSolInteveVO.setCodigoTasa(tramiteInteve.getSolicitudInteve().getCodigoTasa());
						tramiteTraficoSolInteveVO.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
						tramiteTraficoSolInteveVO.setNumExpediente(numExpediente);
						tramiteTraficoSolInteveVO.setAceptacion(tramiteInteve.getSolicitudInteve().isAceptacion() ? 1 : 0);
						servicioPersistencia.guardarOactualizarTramiteSolInteve(tramiteTraficoSolInteveVO);
					} else {
						resultado.addListaMensaje(resultTasa.getMensaje());
					}
				}
			} else {
				servicioPersistencia.actualizarSolicitudesNoFinalizadas(numExpediente, EstadoTramiteTrafico.Iniciado.getValorEnum());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la solicitud del trámite, error: ", e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de guardar la solicitud del trámite.");
		}
	}

	private Boolean esGuardadoSolicitudTramInteve(TramiteTraficoInteveDto tramiteInteve) {
		if (tramiteInteve.getSolicitudInteve() == null
				|| StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getTipoInforme())
				|| StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getCodigoTasa())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private ResultadoTramiteInteveBean gestionarTasaSolictudInteve(String codigoTasaNueva, String codigoTasaAnt, BigDecimal numExpediente, Long idContrato, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (codigoTasaAnt != null && !codigoTasaAnt.isEmpty()) {
				if (!codigoTasaAnt.equals(codigoTasaNueva)) {
					ResultadoBean resultTasa = servicioComunTasa.desasignarTasaExpediente(codigoTasaAnt, numExpediente, idContrato, TipoTasa.CuatroUno.getValorEnum(), idUsuario);
					if (!resultado.getError()) {
						resultTasa = servicioComunTasa.asignarTasaExpediente(codigoTasaNueva, numExpediente, idContrato, TipoTasa.CuatroUno.getValorEnum(), idUsuario);
						if (resultTasa.getError()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultTasa.getMensaje());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultTasa.getMensaje());
					}
				}
			} else {
				ResultadoBean resultTasa = servicioComunTasa.asignarTasaExpediente(codigoTasaNueva, numExpediente, idContrato, TipoTasa.CuatroUno.getValorEnum(), idUsuario);
				if (resultTasa.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultTasa.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la tasa de la solicitud de inteve para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar la tasa de la solicitud de inteve para el expediente: " + numExpediente);
		}
		return resultado;
	}

	private void guardarSolicitante(TramiteTraficoInteveDto tramiteInteve, BigDecimal numExpediente, String numColegiado, ResultadoTramiteInteveBean resultado) {
		try {
			IntervinienteTraficoVO intervinienteTraficoVO = servicioComunIntervinienteTrafico.getIntervinienteTramite(numExpediente, TipoInterviniente.Solicitante.getValorEnum());
			if (intervinienteTraficoVO != null) {
				if (tramiteInteve.getSolicitante() != null && tramiteInteve.getSolicitante().getNifInterviniente() != null && !tramiteInteve.getSolicitante().getNifInterviniente().isEmpty()) {
					if (intervinienteTraficoVO.getId().getNif().equals(tramiteInteve.getSolicitante().getNifInterviniente())) {
						intervinienteTraficoVO.getPersona().setNombre(tramiteInteve.getSolicitante().getPersona().getNombre());
						intervinienteTraficoVO.getPersona().setApellido1RazonSocial(tramiteInteve.getSolicitante().getPersona().getApellido1RazonSocial());
						intervinienteTraficoVO.getPersona().setApellido2(tramiteInteve.getSolicitante().getPersona().getApellido2());
						servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(intervinienteTraficoVO);
					} else {
						servicioComunIntervinienteTrafico.eliminarInterviniente(numExpediente, TipoInterviniente.Solicitante.getValorEnum());
						servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(convertirSolicitanteToVO(tramiteInteve, numColegiado, numExpediente));
					}
				} else {
					servicioComunIntervinienteTrafico.eliminarInterviniente(numExpediente, TipoInterviniente.Solicitante.getValorEnum());
				}
			} else if (tramiteInteve.getSolicitante() != null && tramiteInteve.getSolicitante().getNifInterviniente() != null && !tramiteInteve.getSolicitante().getNifInterviniente().isEmpty()) {
				servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(convertirSolicitanteToVO(tramiteInteve, numColegiado, numExpediente));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el solicitante del trámite, error: ", e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de guardar el solicitante del trámite.");
		}
	}

	private IntervinienteTraficoVO convertirSolicitanteToVO(TramiteTraficoInteveDto tramiteInteve, String numColegiado, BigDecimal numExpediente) {
		IntervinienteTraficoVO intervinienteTraficoNuevo = new IntervinienteTraficoVO();
		IntervinienteTraficoPK id = new IntervinienteTraficoPK();
		id.setNif(tramiteInteve.getSolicitante().getNifInterviniente().toUpperCase());
		id.setNumColegiado(numColegiado);
		id.setNumExpediente(numExpediente);
		id.setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
		intervinienteTraficoNuevo.setId(id);
		PersonaVO personaVO = new PersonaVO();
		PersonaPK idPersona = new PersonaPK();
		idPersona.setNif(tramiteInteve.getSolicitante().getNifInterviniente().toUpperCase());
		idPersona.setNumColegiado(numColegiado);
		personaVO.setId(idPersona);
		personaVO.setApellido1RazonSocial(tramiteInteve.getSolicitante().getPersona().getApellido1RazonSocial().toUpperCase());
		personaVO.setApellido2(tramiteInteve.getSolicitante().getPersona().getApellido2() != null && !tramiteInteve.getSolicitante().getPersona().getApellido2().isEmpty() ? tramiteInteve
				.getSolicitante().getPersona().getApellido2().toUpperCase() : null);
		personaVO.setNombre(tramiteInteve.getSolicitante().getPersona().getNombre() != null && !tramiteInteve.getSolicitante().getPersona().getNombre().isEmpty() ? tramiteInteve.getSolicitante()
				.getPersona().getNombre().toUpperCase() : null);
		intervinienteTraficoNuevo.setPersona(personaVO);
		return intervinienteTraficoNuevo;
	}

	private void guardarRepresentante(TramiteTraficoInteveDto tramiteInteve, BigDecimal numExpediente, String numColegiado, ResultadoTramiteInteveBean resultado) {
		try {
			IntervinienteTraficoVO intervinienteTraficoVO = servicioComunIntervinienteTrafico.getIntervinienteTramite(numExpediente, TipoInterviniente.RepresentanteSolicitante.getValorEnum());
			if (intervinienteTraficoVO != null) {
				if (tramiteInteve.getRepresentante() != null && tramiteInteve.getRepresentante().getNifInterviniente() != null && !tramiteInteve.getRepresentante().getNifInterviniente().isEmpty()) {
					if (intervinienteTraficoVO.getId().getNif().equals(tramiteInteve.getRepresentante().getNifInterviniente())) {
						intervinienteTraficoVO.getPersona().setNombre(tramiteInteve.getRepresentante().getPersona().getNombre());
						intervinienteTraficoVO.getPersona().setApellido1RazonSocial(tramiteInteve.getRepresentante().getPersona().getApellido1RazonSocial());
						intervinienteTraficoVO.getPersona().setApellido2(tramiteInteve.getRepresentante().getPersona().getApellido2());
						servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(intervinienteTraficoVO);
					} else {
						servicioComunIntervinienteTrafico.eliminarInterviniente(numExpediente, TipoInterviniente.RepresentanteSolicitante.getValorEnum());
						servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(convertirRepresentanteToVO(tramiteInteve, numColegiado, numExpediente));
					}
				} else {
					servicioComunIntervinienteTrafico.eliminarInterviniente(numExpediente, TipoInterviniente.RepresentanteSolicitante.getValorEnum());
				}
			} else if (tramiteInteve.getRepresentante() != null && tramiteInteve.getRepresentante().getNifInterviniente() != null && !tramiteInteve.getRepresentante().getNifInterviniente().isEmpty()) {
				servicioComunIntervinienteTrafico.actualizarIntervinienteInteve(convertirRepresentanteToVO(tramiteInteve, numColegiado, numExpediente));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el representante del trámite, error: ", e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de guardar el representante del trámite.");
		}
	}

	private IntervinienteTraficoVO convertirRepresentanteToVO(TramiteTraficoInteveDto tramiteInteve, String numColegiado, BigDecimal numExpediente) {
		IntervinienteTraficoVO intervinienteTraficoNuevo = new IntervinienteTraficoVO();
		IntervinienteTraficoPK id = new IntervinienteTraficoPK();
		id.setNif(tramiteInteve.getRepresentante().getNifInterviniente().toUpperCase());
		id.setNumColegiado(numColegiado);
		id.setNumExpediente(numExpediente);
		id.setTipoInterviniente(TipoInterviniente.RepresentanteSolicitante.getValorEnum());
		intervinienteTraficoNuevo.setId(id);
		PersonaVO personaVO = new PersonaVO();
		PersonaPK idPersona = new PersonaPK();
		idPersona.setNif(tramiteInteve.getRepresentante().getNifInterviniente().toUpperCase());
		idPersona.setNumColegiado(numColegiado);
		personaVO.setId(idPersona);
		personaVO.setApellido1RazonSocial(tramiteInteve.getRepresentante().getPersona().getApellido1RazonSocial().toUpperCase());
		personaVO.setApellido2(tramiteInteve.getRepresentante().getPersona().getApellido2() != null && !tramiteInteve.getRepresentante().getPersona().getApellido2().isEmpty() ? tramiteInteve
				.getRepresentante().getPersona().getApellido2().toUpperCase() : null);
		personaVO.setNombre(tramiteInteve.getRepresentante().getPersona().getNombre() != null && !tramiteInteve.getRepresentante().getPersona().getNombre().isEmpty() ? tramiteInteve.getRepresentante()
				.getPersona().getNombre().toUpperCase() : null);
		intervinienteTraficoNuevo.setPersona(personaVO);
		return intervinienteTraficoNuevo;
	}

	private void guardarEvolucionTramite(BigDecimal numExpediente, BigDecimal estadoAnterior, BigDecimal estadoNuevo, Long idUsuario, Date fecha) {
		try {
			EvolucionTramiteTraficoVO evolucion = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(estadoAnterior);
			id.setEstadoNuevo(estadoNuevo);
			id.setFechaCambio(fecha);
			id.setNumExpediente(numExpediente);
			evolucion.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
			servicioPersistencia.guardarEvolucionTramite(evolucion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del trámite de inteve, error: ", e);
		}
	}

	private ResultadoTramiteInteveBean validarDatosMinimosGuardado(TramiteTraficoInteveDto tramiteInteve) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteInteve == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar los datos obligatorios para poder dar de alta un trámite.");
		} else if (tramiteInteve.getContrato() == null || tramiteInteve.getContrato().getIdContrato() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar un contrato para poder guardar el trámite.");
		} else if (!intervicientesValidos(tramiteInteve)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar los datos obligatorios de los intervinientes.");
		} else if (tramiteInteve.getSolicitudInteve() != null) {
			if (StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getTipoInforme()) && (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getCodigoTasa()))) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Para poder solicitar un Inteve debe de indicar el tipo de Informe.");
			} else if (StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getCodigoTasa()) && StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getTipoInforme())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Para poder solicitar un Inteve debe de indicar el codigo de tasa.");
			} else if (StringUtils.isNotBlank(tramiteInteve.getSolicitudInteve().getTipoInforme())
					&& StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getMatricula()) && StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getBastidor()) 
					&& StringUtils.isBlank(tramiteInteve.getSolicitudInteve().getNive())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Para poder solicitar un Inteve debe de indicar la matricula, bastidor o nive.");
			}
		}
		return resultado;
	}

	private boolean intervicientesValidos(TramiteTraficoInteveDto tramiteInteve) {
		boolean resultado = false;
		if (tramiteInteve.getSolicitante() != null) {
			if ((StringUtils.isNotBlank(tramiteInteve.getSolicitante().getNifInterviniente()) && StringUtils.isNotBlank(tramiteInteve.getSolicitante().getPersona().getApellido1RazonSocial()))
					|| esVacio(tramiteInteve.getSolicitante())) {
				resultado = true;
			}
		}
		// Comprobamos solo si el solicitante es valido
		if (resultado) {
			resultado = false;
			if (tramiteInteve.getRepresentante() != null
					&& ((StringUtils.isNotBlank(tramiteInteve.getRepresentante().getNifInterviniente()) && StringUtils.isNotBlank(tramiteInteve.getRepresentante().getPersona().getApellido1RazonSocial()))
						|| esVacio(tramiteInteve.getRepresentante()))) {
				resultado = true;
			}
		}
		return resultado;
	}

	private boolean esVacio(IntervinienteTraficoDto interviniente) {
		return (StringUtils.isBlank(interviniente.getNifInterviniente()) && StringUtils.isBlank(interviniente.getPersona().getApellido1RazonSocial()) && StringUtils.isBlank(interviniente.getPersona()
				.getApellido2()) && StringUtils.isBlank(interviniente.getPersona().getNombre()));
	}

	@Override
	public ResultadoTramiteInteveBean validarTramite(BigDecimal numExpediente, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.TRUE);
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveBBDD = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			resultado = validarTramiteInteve(tramiteTraficoInteveBBDD, numExpediente);
			if (!resultado.getError()) {
				resultado = validarSolicitudesInteve(tramiteTraficoInteveBBDD);
				if (!resultado.getError()) {
					servicioPersistencia.actualizarEstadoTramiteInteve(tramiteTraficoInteveBBDD.getNumExpediente(), EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					guardarEvolucionTramite(tramiteTraficoInteveBBDD.getNumExpediente(), new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()), new BigDecimal(
							EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()), idUsuario, new Date());
					for (Long idTramiteSolInteve : resultado.getListaIdsSolicitudes()) {
						servicioPersistencia.actualizarEstadoTramiteSolInteve(idTramiteSolInteve, EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					}
					resultado.setMensaje("Trámite: " + numExpediente + " validado telematicamente.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de inteve con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de inteve con numero de expediente: " + numExpediente);
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarSolicitudesInteve(TramiteTraficoInteveVO tramiteTraficoInteveBBDD) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD.getTramitesInteves() == null || tramiteTraficoInteveBBDD.getTramitesInteves().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen solicitudes de inteve para poder validar el trámite");
		} else {
			Boolean existeIni = Boolean.FALSE;
			for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveBBDD : tramiteTraficoInteveBBDD.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTraficoSolInteveBBDD.getEstado())) {
					if ((StringUtils.isBlank(tramiteTraficoSolInteveBBDD.getMatricula()) && !tramiteTraficoSolInteveBBDD.getTipoInforme().equalsIgnoreCase(TipoInformeInteve.VEHICULOS_SIN_MATRICULA
							.getValorEnum())) && StringUtils.isBlank(tramiteTraficoSolInteveBBDD.getBastidor())
							&& StringUtils.isBlank(tramiteTraficoSolInteveBBDD.getNive())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Una de las solicitudes no tiene indicada la matrícula, el bastidor o el nive para poder solicitar su inteve.");
						break;
					} else if (tramiteTraficoSolInteveBBDD.getTipoInforme() == null || tramiteTraficoSolInteveBBDD.getTipoInforme().isEmpty()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Una de las solicitudes no tiene tipo de informe seleccionado.");
						break;
					} else if (StringUtils.isBlank(tramiteTraficoSolInteveBBDD.getCodigoTasa())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Una de las solicitudes no tiene tasa seleccionada.");
						break;
					} else if (solicitudDuplicada(tramiteTraficoSolInteveBBDD, tramiteTraficoInteveBBDD)) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Una de las solicitudes esta duplicada para el día de hoy.");
					} else {
						resultado.addListaIdsSolicitudes(tramiteTraficoSolInteveBBDD.getIdTramiteInteve());
					}
					existeIni = Boolean.TRUE;
				}
			}
			if (!resultado.getError() && !existeIni) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene ninguna solicitud de inteve en estado iniciado para poder validarlas.");
			}
		}
		return resultado;
	}

	// Valida si existe una solicitud para el día de hoy en estado validada, finalizada o pendiente de finalizar
	private boolean solicitudDuplicada(TramiteTraficoSolInteveVO tramiteTraficoSolInteveBBDD, TramiteTraficoInteveVO tramiteTraficoInteveBBDD) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<TramiteTraficoSolInteveVO> listaDuplicados = servicioPersistencia.getDuplicados(tramiteTraficoInteveBBDD.getContrato().getIdContrato(), tramiteTraficoSolInteveBBDD.getMatricula(),
				tramiteTraficoSolInteveBBDD.getBastidor(), tramiteTraficoSolInteveBBDD.getNive());
		if (listaDuplicados == null || listaDuplicados.isEmpty()) {
			return false;
		} else {
			for (TramiteTraficoSolInteveVO elemento : listaDuplicados) {
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(elemento.getEstado())) {
					if (sdf.format(elemento.getFechaPresentacion()).equals(sdf.format(new Date()))) {
						return true;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private ResultadoTramiteInteveBean validarTramiteInteve(TramiteTraficoInteveVO tramiteTraficoInteveBBDD, BigDecimal numExpediente) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + numExpediente);
		} else if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTraficoInteveBBDD.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite no se encuentra en estado Iniciado por lo que no se podra validar.");
		} else if (tramiteTraficoInteveBBDD.getIntervinienteTraficosAsList() == null || tramiteTraficoInteveBBDD.getIntervinienteTraficosAsList().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar el Solicitante del trámite.");
		} else {
			Boolean existeSolicitante = Boolean.FALSE;
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTraficoInteveBBDD.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Solicitante.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
					if (intervinienteTraficoVO.getPersona() == null || intervinienteTraficoVO.getPersona().getApellido1RazonSocial() == null || intervinienteTraficoVO.getPersona()
							.getApellido1RazonSocial().isEmpty()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Debe de rellenar el primer apellido o razon social del solicitante.");
					}
					existeSolicitante = Boolean.TRUE;
					break;
				}
			}
			if (!existeSolicitante) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de rellenar el Solicitante del trámite.");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean solicitarTramite(BigDecimal numExpediente, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		List<Long> listaIdsTramSolInteve = null;
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveBBDD = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			resultado = validarTramiteSolicitar(tramiteTraficoInteveBBDD, numExpediente);
			if (!resultado.getError()) {
				resultado = validarTramiteSolInteveSolicitar(tramiteTraficoInteveBBDD);
				listaIdsTramSolInteve = resultado.getListaIdsSolicitudes();
				if (!resultado.getError()) {
					servicioPersistencia.actualizarEstadoTramiteInteve(tramiteTraficoInteveBBDD.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
					for (Long idTramiteSolInteve : listaIdsTramSolInteve) {
						servicioPersistencia.actualizarEstadoTramiteSolInteve(idTramiteSolInteve, EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
					}
					ResultadoBean resultCola = servicioComunCola.crearSolicitud(numExpediente.longValue(), ProcesosEnum.INTEVE_COMPLETO_WS.getNombreEnum(), gestorPropiedades.valorPropertie(
							"nombreHostProceso"), TipoTramiteTrafico.Inteve.getValorEnum(), new BigDecimal(idUsuario), new BigDecimal(tramiteTraficoInteveBBDD.getContrato().getIdContrato()), null);
					if (!resultCola.getError()) {
						guardarEvolucionTramite(numExpediente, new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio
								.getValorEnum()), idUsuario, new Date());
						resultado.setMensaje("Tramite: " + numExpediente + " tramitado correctamente.");
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solicitar el trámite de inteve con número de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar el trámite de inteve con número de expediente: " + numExpediente);
		} finally {
			if (resultado.getError() && listaIdsTramSolInteve != null && !listaIdsTramSolInteve.isEmpty()) {
				try {
					servicioPersistencia.actualizarEstadoTramiteInteve(numExpediente, EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					for (Long idTramiteSolInteve : listaIdsTramSolInteve) {
						servicioPersistencia.actualizarEstadoTramiteSolInteve(idTramiteSolInteve, EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					}
				} catch (Exception e2) {
					log.error("Ha sucedido un error a la hora de actualizar el estado del expediente: " + numExpediente + ", error: ", e2);
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean reiniciarEstadosSolicitudes(String codSeleccionados) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)) {
				String[] ids = codSeleccionados.split(",");
				for (String id : ids) {
					try {
						TramiteTraficoSolInteveVO traficoSolInteveBBDD = servicioPersistencia.getTramiteSolIntevePorId(new Long(id));
						ResultadoTramiteInteveBean resultSol = validarReinicioSolicitud(traficoSolInteveBBDD, id);
						if (!resultSol.getError()) {
							servicioPersistencia.actualizarEstadoTramiteSolInteve(traficoSolInteveBBDD.getIdTramiteInteve(), EstadoTramiteTrafico.Iniciado.getValorEnum());
							servicioPersistencia.actualizarEstadoTramiteInteve(traficoSolInteveBBDD.getNumExpediente(), EstadoTramiteTrafico.Iniciado.getValorEnum());
						} else {
							resultado.addListaMensaje(resultSol.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reiniciar la solicitud con id: " + id + ", error: ", e);
						resultado.addListaMensaje("Ha sucedido un error a la hora de reiniciar la solicitud con id: " + id);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna solicitud para reiniciar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reiniciar el estado de las solicitudes seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reiniciar el estado de las solicitudes seleccionadas.");
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarReinicioSolicitud(TramiteTraficoSolInteveVO traficoSolInteveBBDD, String id) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (traficoSolInteveBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han enciontrado datos para la solicitud con id: " + id);
		} else if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(traficoSolInteveBBDD.getEstado())) {
			resultado.setError(Boolean.TRUE);
			if (StringUtils.isNotBlank(traficoSolInteveBBDD.getMatricula())) {
				resultado.setMensaje("La solicitud de la matrícula: " + traficoSolInteveBBDD.getMatricula()
						+ " no se puede reiniciar porque en estado Finalizado Telematicamente no se puede modificar.");
			} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getBastidor())) {
				resultado.setMensaje("La solicitud del bastidor: " + traficoSolInteveBBDD.getBastidor() + " no se puede reiniciar porque en estado Finalizado Telemáticamente no se puede modificar.");
			} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getNive())) {
				resultado.setMensaje("La solicitud del nive: " + traficoSolInteveBBDD.getNive() + " no se puede reiniciar porque en estado Finalizado Telemáticamente no se puede modificar.");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean reiniciarEstados(BigDecimal numExpediente, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveVO = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			resultado = validarTramiteReiniciarEstado(tramiteTraficoInteveVO, numExpediente);
			if (!resultado.getError()) {
				servicioPersistencia.actualizarEstadoTramiteInteve(numExpediente, EstadoTramiteTrafico.Iniciado.getValorEnum());
				for (Long idTramiteSolInteve : resultado.getListaIdsSolicitudes()) {
					servicioPersistencia.actualizarEstadoTramiteSolInteve(idTramiteSolInteve, EstadoTramiteTrafico.Iniciado.getValorEnum());
				}
				guardarEvolucionTramite(numExpediente, tramiteTraficoInteveVO.getEstado(), new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()), idUsuario, new Date());
				resultado.setMensaje("Trámite: " + numExpediente + " reiniciado sus estados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reiniciar los estados para el trámite: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reiniciar los estados para el trámite: " + numExpediente);
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteReiniciarEstado(TramiteTraficoInteveVO tramiteTraficoInteveVO, BigDecimal numExpediente) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveVO.getTramitesInteves() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen solicitudes de inteve para poder reiniciar los estados del trámite");
		} else if (!EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTraficoInteveVO.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum()
				.equals(tramiteTraficoInteveVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite: " + numExpediente + " no se puede reiniciar sus estado porque no se encuentra finalizado con error o parcialmente.");
		} else {
			Boolean existeSolFinalizadoError = Boolean.FALSE;
			for (TramiteTraficoSolInteveVO solInteve : tramiteTraficoInteveVO.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(solInteve.getEstado())) {
					existeSolFinalizadoError = Boolean.TRUE;
					resultado.addListaIdsSolicitudes(solInteve.getIdTramiteInteve());
				}
			}
			if (!existeSolFinalizadoError) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El trámite: " + numExpediente
						+ " no se puede reiniciar sus estado porque no se encuentra ninguna solicitud de informe en estado finalizado con error o parcialmente.");
			}
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteSolInteveSolicitar(TramiteTraficoInteveVO tramiteTraficoInteveBBDD) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD.getTramitesInteves() == null || tramiteTraficoInteveBBDD.getTramitesInteves().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen solicitudes de inteve para poder solicitar el trámite");
		} else {
			Boolean existeVal = Boolean.FALSE;
			for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveBBDD : tramiteTraficoInteveBBDD.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTraficoSolInteveBBDD.getEstado())) {
					existeVal = Boolean.TRUE;
					resultado.addListaIdsSolicitudes(tramiteTraficoSolInteveBBDD.getIdTramiteInteve());
				}
			}
			if (!existeVal) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene ninguna solicitud de inteve en estado validado telematicamente para poder solicitarlas.");
			}
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteSolicitar(TramiteTraficoInteveVO tramiteTraficoInteveBBDD, BigDecimal numExpediente) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + numExpediente);
		} else if (!EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTraficoInteveBBDD.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite no se encuentra en estado Validado Telematicamente por lo que no se podra solicitar.");
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteInteveWS(TramiteTraficoInteveVO tramiteTraficoInteveBBDD, BigDecimal numExpediente) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + numExpediente);
		} else if (!EstadoTramiteTrafico.Pendiente_Envio.getValorEnum().equals(tramiteTraficoInteveBBDD.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite no se encuentra en estado Pendiente de Envio por lo que no se podra solicitar.");
		} else {
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTraficoInteveBBDD.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Solicitante.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
					resultado.setSolicitanteVO(intervinienteTraficoVO);
					break;
				}
			}
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteSolInteveWS(TramiteTraficoInteveVO tramiteTraficoInteveBBDD) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteTraficoInteveBBDD.getTramitesInteves() == null || tramiteTraficoInteveBBDD.getTramitesInteves().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen solicitudes de inteve para poder solicitar el trámite");
		} else {
			Boolean existeVal = Boolean.FALSE;
			for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveBBDD : tramiteTraficoInteveBBDD.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Pendiente_Envio.getValorEnum().equals(tramiteTraficoSolInteveBBDD.getEstado())) {
					existeVal = Boolean.TRUE;
					resultado.addListaSolicitudesInteve(tramiteTraficoSolInteveBBDD);
				}
			}
			if (!existeVal) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene ninguna solicitud de inteve en estado Pendiente de Envío para poder solicitarlas.");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean solicitarInteveCompletoWS(BigDecimal numExpediente, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoInteveVO tramiteTraficoInteveBBDD = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			resultado = validarTramiteInteveWS(tramiteTraficoInteveBBDD, numExpediente);
			if (!resultado.getError()) {
				IntervinienteTraficoVO solicitanteInteve = resultado.getSolicitanteVO();
				resultado = validarTramiteSolInteveWS(tramiteTraficoInteveBBDD);
				if (!resultado.getError()) {
					for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO : resultado.getListaSolicitudesInteve()) {
						try {
							llamadaWSInteveCompleto(tramiteTraficoSolInteveVO, solicitanteInteve, resultado);
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de solicitar el inteve con id: " + tramiteTraficoSolInteveVO.getIdTramiteInteve() + ", error: ", e);
							resultado.addListaResultadoWS(tramiteTraficoSolInteveVO.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(),
									"Ha sucedido un error a la hora de solicitar el inteve.");
							resultado.addNumErrorWS();
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solicitar el trámite de inteve con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar el trámite de inteve con número de expediente: " + numExpediente);
		}
		return resultado;
	}

	/*
	 * private void llamadaWSInteveCompletoAxis(TramiteTraficoSolInteveVO tramiteTraficoSolInteve, IntervinienteTraficoVO solicitanteInteve, ResultadoTramiteInteveBean resultado) throws RemoteException, MalformedURLException, ServiceException{ InInformeCompleto solictudInforme =
	 * rellenarSolictudInteveAxis(tramiteTraficoSolInteve, solicitanteInteve); OutInformeCompleto resultInformeCompleto = getStubInteveCompleto(tramiteTraficoSolInteve.getIdTramiteInteve().toString()).obtenerInformeCompleto(solictudInforme); if (resultInformeCompleto != null) { } } private
	 * IntvServicioInformesCompletoSoapBindingStub getStubInteveCompleto(String idTramiteSolInteve) throws MalformedURLException, ServiceException { IntvServicioInformesCompletoSoapBindingStub stub = null; String timeOut = gestorPropiedades.valorPropertie("webServicer.inteve.timeOut"); URL miUrl =
	 * new URL(gestorPropiedades.valorPropertie("webServicer.inteve.completo.url")); ServicioWebInformesCompletoIntv informesCompletoService = null; if (miUrl != null) { ServicioInformesCompletoIntvLocator informeComplLocator = new ServicioInformesCompletoIntvLocator(); javax.xml.namespace.QName
	 * portName = new javax.xml.namespace.QName(miUrl.toString(), informeComplLocator.getIntvServicioInformesCompletoSoapWSDDServiceName());
	 * @SuppressWarnings("unchecked") List<HandlerInfo> list = informeComplLocator.getHandlerRegistry().getHandlerChain(portName); list.add(getSignerHandlerInformeCompleto(gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"))); list.add(getFileHandlerInformeCompleto(idTramiteSolInteve));
	 * informesCompletoService = informeComplLocator.getIntvServicioInformesCompletoSoap(miUrl); stub = (IntvServicioInformesCompletoSoapBindingStub) informesCompletoService; stub.setTimeout(Integer.parseInt(timeOut)); } return stub; } private HandlerInfo getSignerHandlerInformeCompleto(String
	 * alias) { // Configuración del almacén de claves y certificado a usar Map<String, String> securityConfig = new HashMap<String, String>(); securityConfig.put(SoapSecurityInteveHandler.ALIAS_KEY, alias); // Handler de firmado HandlerInfo signerHandlerInfo = new HandlerInfo();
	 * signerHandlerInfo.setHandlerClass(SoapSecurityInteveHandler.class); signerHandlerInfo.setHandlerConfig(securityConfig); return signerHandlerInfo; } private HandlerInfo getFileHandlerInformeCompleto(String idTramiteSolInteve) { HandlerInfo logHandlerInfo = new HandlerInfo();
	 * logHandlerInfo.setHandlerClass(SoapInteveWSCompletoWSHandler.class); Map<String, Object> filesConfig = new HashMap<String, Object>(); filesConfig.put(SoapInteveWSCompletoWSHandler.PROPERTY_KEY_ID, idTramiteSolInteve.toString()); logHandlerInfo.setHandlerConfig(filesConfig); return
	 * logHandlerInfo; } private InInformeCompleto rellenarSolictudInteveAxis(TramiteTraficoSolInteveVO tramiteTraficoSolInteve, IntervinienteTraficoVO solicitanteInteve) { InInformeCompleto solicitudInforme = new InInformeCompleto(); if (tramiteTraficoSolInteve.getBastidor() != null &&
	 * !tramiteTraficoSolInteve.getBastidor().isEmpty()) { solicitudInforme.setBastidor(new String[] { tramiteTraficoSolInteve.getBastidor().trim().toUpperCase() }); } else if (tramiteTraficoSolInteve.getMatricula() != null && !tramiteTraficoSolInteve.getMatricula().isEmpty()) {
	 * solicitudInforme.setMatricula(new String[] { tramiteTraficoSolInteve.getMatricula().trim().toUpperCase() }); ; } else if (tramiteTraficoSolInteve.getNive() != null && !tramiteTraficoSolInteve.getNive().isEmpty()) { solicitudInforme.setNive(new String[] {
	 * tramiteTraficoSolInteve.getNive().trim().toUpperCase() }); } solicitudInforme.setDoi(solicitanteInteve.getId().getNif().toUpperCase()); solicitudInforme.setMotivoSolicitud(new Integer(tramiteTraficoSolInteve.getTipoInforme()));
	 * solicitudInforme.setNumTasa(tramiteTraficoSolInteve.getCodigoTasa()); return solicitudInforme; }
	 */

	private void llamadaWSInteveCompleto(TramiteTraficoSolInteveVO tramiteTraficoSolInteve, IntervinienteTraficoVO solicitanteInteve, ResultadoTramiteInteveBean resultado)
			throws MalformedURLException, ServiceException {
		ObtenerInformeCompleto solictudInforme = rellenarSolictudInteve(tramiteTraficoSolInteve, solicitanteInteve);
		ObtenerInformeCompletoResponse resultInformeCompleto = getStubInteveCompleto(tramiteTraficoSolInteve.getIdTramiteInteve().toString()).obtenerInformeCompleto(solictudInforme);
		if (resultInformeCompleto != null && resultInformeCompleto.getResultadoInformeCompleto() != null) {
			if (resultInformeCompleto.getResultadoInformeCompleto().getInformePdf() != null) {
				try {
					byte[] informe = utiles.doBase64Decode(resultInformeCompleto.getResultadoInformeCompleto().getInformePdf(), "UTF-8");
					FicheroBean fichero = new FicheroBean();
					fichero.setTipoDocumento(ConstantesGestorFicheros.INTEVE_WS_COMPLETO);
					fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
					fichero.setFecha(Utilidades.transformExpedienteFecha(tramiteTraficoSolInteve.getNumExpediente()));
					fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
					fichero.setFicheroByte(informe);
					fichero.setNombreDocumento(ConstantesGestorFicheros.INFORME_COMPLETO + tramiteTraficoSolInteve.getIdTramiteInteve());
					gestorDocumentos.guardarFichero(fichero);
					resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum(), "Informe Recibido Correctamente.");
					resultado.addNumOkWS();
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de guardar el informe del inteve con id: " + tramiteTraficoSolInteve.getIdTramiteInteve() + ", error: ", e);
					resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(),
							"Ha sucedido un error a la hora de guardar el PDF con el informe.");
					resultado.addNumErrorWS();
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de guardar el informe del inteve con id: " + tramiteTraficoSolInteve.getIdTramiteInteve() + ", error: ", e);
					resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(),
							"Ha sucedido un error a la hora de guardar el PDF con el informe.");
					resultado.addNumErrorWS();
				}
			} else if (resultInformeCompleto.getResultadoInformeCompleto().getResultadoComunicacion() != null && resultInformeCompleto.getResultadoInformeCompleto().getResultadoComunicacion()
					.getCodigoRetorno() != null) {
				resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(), "Error: " + resultInformeCompleto
						.getResultadoInformeCompleto().getResultadoComunicacion().getDescripcionRetorno());
				resultado.addNumErrorWS();
			} else {
				resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(), "Error: " + resultInformeCompleto
						.getResultadoInformeCompleto().getResultadoComunicacion().getDescripcionRetorno());
				resultado.addNumErrorWS();
			}
		} else {
			resultado.addListaResultadoWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(),
					"La llamada al inteveWSCompleto no devolvio ningun resultado.");
			resultado.addNumErrorWS();
		}
	}

	private ObtenerInformeCompleto rellenarSolictudInteve(TramiteTraficoSolInteveVO tramiteTraficoSolInteve, IntervinienteTraficoVO solicitanteInteve) {
		ObtenerInformeCompleto informeCompleto = new ObtenerInformeCompleto();
		InInformeCompleto solicitudInforme = new InInformeCompleto();
		if (tramiteTraficoSolInteve.getBastidor() != null && !tramiteTraficoSolInteve.getBastidor().isEmpty()) {
			solicitudInforme.getBastidor().add(tramiteTraficoSolInteve.getBastidor().trim().toUpperCase());
		} else if (tramiteTraficoSolInteve.getMatricula() != null && !tramiteTraficoSolInteve.getMatricula().isEmpty()) {
			solicitudInforme.getMatricula().add(tramiteTraficoSolInteve.getMatricula().trim().toUpperCase());
		} else if (tramiteTraficoSolInteve.getNive() != null && !tramiteTraficoSolInteve.getNive().isEmpty()) {
			solicitudInforme.getNive().add(tramiteTraficoSolInteve.getNive().trim().toUpperCase());
		}
		solicitudInforme.setDoi(solicitanteInteve.getId().getNif().toUpperCase());
		solicitudInforme.setMotivoSolicitud(new Integer(tramiteTraficoSolInteve.getTipoInforme()));
		solicitudInforme.setNumTasa(tramiteTraficoSolInteve.getCodigoTasa());
		informeCompleto.setSolicitudInforme(solicitudInforme);
		return informeCompleto;
	}

	private ServicioWebInformesCompletoIntv getStubInteveCompleto(String idTramiteSolInteve) throws MalformedURLException, ServiceException {
		String timeOut = gestorPropiedades.valorPropertie("webServicer.inteve.timeOut");
		URL miUrl = new URL(gestorPropiedades.valorPropertie("webServicer.inteve.completo.url"));
		ServicioInformesCompletoIntv informeCompletoLocator = new ServicioInformesCompletoIntv(miUrl);
		ServicioWebInformesCompletoIntv servicioInfCompleto = informeCompletoLocator.getIntvServicioInformesCompletoSoap();

		Binding binding = ((BindingProvider) servicioInfCompleto).getBinding();
		List<Handler> handlerList = binding.getHandlerChain();

		((BindingProvider) servicioInfCompleto).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", Integer.parseInt(timeOut));
		((BindingProvider) servicioInfCompleto).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.parseInt(timeOut));

		handlerList.add(new SecurityClientHandlerInteveWsCompleto());

		((BindingProvider) servicioInfCompleto).getRequestContext().put(SecurityClientHandlerInteveWsCompleto.ALIAS_KEY, gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"));

		handlerList.add(new SoapInteveWSCompletoWSHandler());

		((BindingProvider) servicioInfCompleto).getRequestContext().put(SoapInteveWSCompletoWSHandler.PROPERTY_KEY_ID, new BigDecimal(idTramiteSolInteve));

		binding.setHandlerChain(handlerList);

		return servicioInfCompleto;
	}

	private HandlerInfo getSignerHandlerInformeCompleto(String alias) {
		// Configuración del almacén de claves y certificado a usar
		Map<String, String> securityConfig = new HashMap<>();
		securityConfig.put(SecurityClientHandlerInteveWsCompleto.ALIAS_KEY, alias);
		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(SecurityClientHandlerInteveWsCompleto.class);
		signerHandlerInfo.setHandlerConfig(securityConfig);
		return signerHandlerInfo;
	}

	private HandlerInfo getFileHandlerInformeCompleto(String idTramiteSolInteve) {
		HandlerInfo logHandlerInfo = new HandlerInfo();
		logHandlerInfo.setHandlerClass(SoapInteveWSCompletoWSHandler.class);
		Map<String, Object> filesConfig = new HashMap<>();
		filesConfig.put(SoapInteveWSCompletoWSHandler.PROPERTY_KEY_ID, idTramiteSolInteve);
		logHandlerInfo.setHandlerConfig(filesConfig);
		return logHandlerInfo;
	}

	@Override
	public ResultadoBean tratarCreditosInteve(BigDecimal numExpediente, Long idContrato, Long idUsuario, Long numOkWS) {
		return servicioComunCredito.descontarCreditosInteve(TipoTramiteTrafico.Inteve.getValorEnum(), idContrato, idUsuario, numExpediente, numOkWS);
	}

	@Override
	public void finalizarSolicitudInteveCompleto(List<ResultadoWSInteveBean> listaResultadoWS, Long numErrorWS, Long numOkWS, BigDecimal numExpediente, Long idUsuario) {
		for (ResultadoWSInteveBean resultadoInteveWS : listaResultadoWS) {
			try {
				servicioPersistencia.finalizarTramiteSolInteveWS(resultadoInteveWS.getIdTramiteInteve(), resultadoInteveWS.getEstado(), resultadoInteveWS.getResultado());
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de finalizar el inteve con id: " + resultadoInteveWS.getIdTramiteInteve() + ",error: ", e);
			}
		}
		String estadoNuevo = null;
		if (numErrorWS != null && numOkWS != null) {
			estadoNuevo = EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum();
		} else if (numErrorWS != null) {
			estadoNuevo = EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum();
		} else {
			estadoNuevo = EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum();
		}
		try {
			servicioPersistencia.actualizarEstadoTramiteInteve(numExpediente, estadoNuevo);
			guardarEvolucionTramite(numExpediente, new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()), new BigDecimal(estadoNuevo), idUsuario, new Date());

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar el trámite de inteve con número de expediente: " + numExpediente + ",error: ", e);
		}
	}

	@Override
	public void finalizarSolicitudNoRecuperable(BigDecimal numExpediente, String mensaje, Long idUsuario) {
		try {
			TramiteTraficoInteveVO tramite = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			if (tramite != null) {
				if (tramite.getTramitesInteves() != null && !tramite.getTramitesInteves().isEmpty()) {
					for (TramiteTraficoSolInteveVO tramiteTraficoSolInteve : tramite.getTramitesInteves()) {
						if (EstadoTramiteTrafico.Pendiente_Envio.getValorEnum().equals(tramiteTraficoSolInteve.getEstado())) {
							servicioPersistencia.finalizarTramiteSolInteveWS(tramiteTraficoSolInteve.getIdTramiteInteve(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(), mensaje);
						}
					}
				}
				servicioPersistencia.actualizarEstadoTramiteInteve(numExpediente, EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum());
				guardarEvolucionTramite(numExpediente, new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()),
						idUsuario, new Date());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar el trámite de inteve con número de expediente: " + numExpediente + ",error: ", e);
		}
	}

	@Override
	public List<TramiteTraficoSolInteveVO> getListaTramitesIntevePorExpediente(BigDecimal numExpediente) {
		try {
			return servicioPersistencia.getListaTramitesSolInteve(numExpediente);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de trámites de inteve para el expediente: " + numExpediente + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoTramiteInteveBean obtenerInformesTramites(List<TramiteTraficoSolInteveVO> listaTramitesDescargar) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			Boolean esZip = listaTramitesDescargar.size() > 1 ? Boolean.TRUE : Boolean.FALSE;
			if (esZip) {
				url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				File ficheroDestino = new File(url);
				out = new FileOutputStream(ficheroDestino);
				zip = new ZipOutputStream(out);
			}
			for (TramiteTraficoSolInteveVO traficoSolInteveBBDD : listaTramitesDescargar) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.INTEVE_WS_COMPLETO, ConstantesGestorFicheros.INFORMES, Utilidades
						.transformExpedienteFecha(traficoSolInteveBBDD.getNumExpediente()), ConstantesGestorFicheros.INFORME_COMPLETO + traficoSolInteveBBDD.getIdTramiteInteve().toString(),
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (fichero != null && fichero.getFile() != null) {
					if (StringUtils.isNotBlank(traficoSolInteveBBDD.getMatricula())) {
						resultado.setNombreFichero(traficoSolInteveBBDD.getMatricula() + ConstantesGestorFicheros.EXTENSION_PDF);
					} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getBastidor())) {
						resultado.setNombreFichero(traficoSolInteveBBDD.getBastidor() + ConstantesGestorFicheros.EXTENSION_PDF);
					} else if (StringUtils.isNotBlank(traficoSolInteveBBDD.getNive())) {
						resultado.setNombreFichero(traficoSolInteveBBDD.getNive() + ConstantesGestorFicheros.EXTENSION_PDF);
					}
					if (esZip) {
						addZipEntryFromFile(zip, fichero.getFile(), resultado.getNombreFichero());
					} else {
						resultado.setFichero(fichero.getFile());
					}
					resultado.addNumOkWS();
				}
			}
			if (esZip) {
				zip.close();
				File fichero = new File(url);
				resultado.setNombreFichero("Informes_" + System.currentTimeMillis() + ConstantesGestorFicheros.EXTENSION_ZIP);
				resultado.setFichero(fichero);
				resultado.setEsZip(Boolean.TRUE);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar la documentación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de recuperar la documentación.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean recuperarDocumento(String listaIdsTramites) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			if (StringUtils.isNotBlank(listaIdsTramites)) {
				String[] idsTramitesInteve = listaIdsTramites.split(",");
				Boolean esZip = idsTramitesInteve.length > 1 ? Boolean.TRUE : Boolean.FALSE;
				if (esZip) {
					url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
					File ficheroDestino = new File(url);
					out = new FileOutputStream(ficheroDestino);
					zip = new ZipOutputStream(out);
				}
				for (String idTramiteInteve : idsTramitesInteve) {
					TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = servicioPersistencia.getTramiteSolIntevePorId(new Long(idTramiteInteve));
					validarDatosDescarga(tramiteTraficoSolInteveVO, resultado, idTramiteInteve);
					if (!resultado.getError()) {
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.INTEVE_WS_COMPLETO, ConstantesGestorFicheros.INFORMES, Utilidades
								.transformExpedienteFecha(tramiteTraficoSolInteveVO.getNumExpediente()), ConstantesGestorFicheros.INFORME_COMPLETO + tramiteTraficoSolInteveVO.getIdTramiteInteve()
										.toString(), ConstantesGestorFicheros.EXTENSION_PDF);
						if (fichero != null && fichero.getFile() != null) {
							if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getMatricula())) {
								resultado.setNombreFichero(tramiteTraficoSolInteveVO.getMatricula() + ConstantesGestorFicheros.EXTENSION_PDF);
							} else if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getBastidor())) {
								resultado.setNombreFichero(tramiteTraficoSolInteveVO.getBastidor() + ConstantesGestorFicheros.EXTENSION_PDF);

							} else if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getNive())) {
								resultado.setNombreFichero(tramiteTraficoSolInteveVO.getNive() + ConstantesGestorFicheros.EXTENSION_PDF);
							}
							if (esZip) {
								addZipEntryFromFile(zip, fichero.getFile(), resultado.getNombreFichero());
							} else {
								resultado.setFichero(fichero.getFile());
							}
							resultado.addNumOkWS();
						}
					} else {
						break;
					}
				}
				if (esZip) {
					zip.close();
					if (!resultado.getError()) {
						File fichero = new File(url);
						resultado.setNombreFichero("Informes_" + System.currentTimeMillis() + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.setFichero(fichero);
						resultado.setEsZip(Boolean.TRUE);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún informe para su descarga.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar la documentación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de recuperar la documentación.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	public void addZipEntryFromFile(ZipOutputStream zip, File file, String nombreFichero) {
		if (file != null && file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(nombreFichero);
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			} catch (IOException e) {
				log.error("Error al añadir una entrada al zip del fichero: " + file.getName(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("Error cerrando FileInputStream", e);
					}
				}
			}
		}
	}

	@Override
	public void borrarZip(File ficheroZip) {
		try {
			if (ficheroZip != null) {
				ficheroZip.delete();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar el zip temporal, error: ", e);
		}
	}

	private void validarDatosDescarga(TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO, ResultadoTramiteInteveBean resultado, String idTramitesInteve) {
		if (tramiteTraficoSolInteveVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos del informe para desacargar con id: " + idTramitesInteve);
		} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTraficoSolInteveVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getMatricula())) {
				resultado.setMensaje("El informe de la matrícula: " + tramiteTraficoSolInteveVO.getMatricula() + " debe estar en estado Finalizado Telematicamente para poder recuperar el informe.");
			} else if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getBastidor())) {
				resultado.setMensaje("El informe del bastidor: " + tramiteTraficoSolInteveVO.getBastidor() + " debe estar en estado Finalizado Telematicamente para poder recuperar el informe.");
			} else if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getNive())) {
				resultado.setMensaje("El informe del nive: " + tramiteTraficoSolInteveVO.getNive() + " debe estar en estado Finalizado Telematicamente para poder recuperar el informe.");
			}
		}
	}

	@Override
	public ResultadoTramiteInteveBean eliminar(BigDecimal numExpediente, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			TramiteTraficoInteveVO tramiteInteve = servicioPersistencia.getTramiteInteve(numExpediente, Boolean.TRUE);
			resultado = validarTramiteEliminar(tramiteInteve, numExpediente);
			if (!resultado.getError()) {
				ResultadoTramiteInteveBean resultEliminar = cambioEstado(numExpediente, EstadoTramiteTrafico.Anulado.getValorEnum(), idUsuario);
				if (resultEliminar.getError()) {
					resultado.addResumenError(resultEliminar.getMensaje());
				} else {
					resultado.addResumenOk(resultEliminar.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de borrar los tramites seleccionados.");
		}
		return resultado;
	}

	private ResultadoTramiteInteveBean validarTramiteEliminar(TramiteTraficoInteveVO tramiteInteve, BigDecimal numExpediente) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		if (tramiteInteve == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(NO_EXISTEN_DATOS_EN_BBDD_PARA_EXPEDIENTE + numExpediente);
		} else if (!new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()).equals(tramiteInteve.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteInteve.getNumExpediente() + " no se puede eliminar porque no se encuentra en estado Iniciado.");
		} else if (tramiteInteve.getTramitesInteveAsList() != null && !tramiteInteve.getTramitesInteveAsList().isEmpty()) {
			for (TramiteTraficoSolInteveVO solicitudes : tramiteInteve.getTramitesInteveAsList()) {
				if (solicitudes.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El tramite: " + tramiteInteve.getNumExpediente() + " tiene alguna solicitud en estado 'Finalizado Telemáticamente'.");
					break;
				} else if (StringUtils.isNotBlank(solicitudes.getCodigoTasa())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El tramite: " + tramiteInteve.getNumExpediente() + " tiene alguna solicitud con tasa asignada.");
					break;
				}
			}
		}
		return resultado;
	}
}