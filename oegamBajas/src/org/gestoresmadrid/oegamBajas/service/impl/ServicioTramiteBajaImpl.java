package org.gestoresmadrid.oegamBajas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamBajas.service.ServicioPersistenciaBaja;
import org.gestoresmadrid.oegamBajas.service.ServicioTramiteBaja;
import org.gestoresmadrid.oegamBajas.service.ServicioValidacionBajas;
import org.gestoresmadrid.oegamBajas.service.build.BuildBtv;
import org.gestoresmadrid.oegamBajas.service.build.BuildCheckBtv;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.am.service.ServicioWebServiceAm;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunPersonaDireccion;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.service.ServicioComunIntervinienteTrafico;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.service.build.BuildCheckCaducidadCert;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.view.bean.ResultadoVehiculoBean;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteBajaImpl implements ServicioTramiteBaja {

	private static final long serialVersionUID = -4027306496528956660L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteBajaImpl.class);

	private static final String COLEGIADO_AM = "2631";
	private static final String SI = "SI";

	@Autowired
	ServicioValidacionBajas servicioValidacionBajas;

	@Autowired
	ServicioPersistenciaBaja servicioPersistenciaBaja;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	ServicioComunVehiculo servicioComunVehiculo;

	@Autowired
	ServicioComunTasa servicioComunTasa;

	@Autowired
	ServicioComunIntervinienteTrafico servicioComunIntervinienteTrafico;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioComunPersona servicioComunPersona;

	@Autowired
	ServicioComunPersonaDireccion servicioComunPersonaDireccion;

	@Autowired
	BuildCheckBtv buildCheckBtv;

	@Autowired
	BuildBtv buildBtv;

	@Autowired
	BuildCheckCaducidadCert buildCheckCaducidadCert;

	@Autowired
	GestorPropiedades gestorProperties;

	@Autowired
	ServicioWebServiceAm servicioWebServiceAm;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoBajasBean pendienteEnvioExcel(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(numExpediente, Boolean.TRUE);
			resultado = servicioValidacionBajas.validarPendienteEnvioExcel(tramiteTrafBajaVO, numExpediente);
			if (!resultado.getError()) {
				resultado = servicioPersistenciaBaja.realizarPdteEnvioExcel(tramiteTrafBajaVO.getNumExpediente(),
						idUsuario, tramiteTrafBajaVO.getContrato().getIdContrato());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de poner el tramite de baja con numero de expediente: " + numExpediente + " a pendiente envio excel, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de poner el trámite de baja con número de expediente: " + numExpediente + " a pendiente envío excel.");
		}
		return resultado;
	}

	@Override
	public ResultadoBajasBean tramitarBtv(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		String nombreXml = null;
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(numExpediente, Boolean.TRUE);
			String gestionarConAM = gestorProperties.valorPropertie("gestionar.conAm");
			if (COLEGIADO_AM.equals(tramiteTrafBajaVO.getNumColegiado()) && SI.equals(gestionarConAM)) {
				resultado = servicioPersistenciaBaja.realizarTramitacionBtv(tramiteTrafBajaVO.getNumExpediente(),
						ProcesosAmEnum.BTV.toString(), nombreXml, idUsuario,
						gestorProperties.valorPropertie("nombreHostSolicitud"),
						tramiteTrafBajaVO.getContrato().getIdContrato());
			} else {
				resultado = servicioValidacionBajas.validarTramitabilidadTramite(tramiteTrafBajaVO, numExpediente);
				if (!resultado.getError()) {
					resultado = servicioPersistenciaBaja.realizarTramitacionBtv(tramiteTrafBajaVO.getNumExpediente(),
							ProcesosEnum.BTV.toString(), nombreXml, idUsuario,
							gestorProperties.valorPropertie("nombreHostSolicitud"),
							tramiteTrafBajaVO.getContrato().getIdContrato());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tramitarBtv el tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tramitarBtv el trámite de baja con número de expediente: " + numExpediente);
		}
		if (resultado.getError() && StringUtils.isNotBlank(nombreXml)) {
			try {
				buildBtv.borraFicheroSiExisteConExtension(numExpediente, nombreXml);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de borrar el fichero del tramitarBtv si existe para el expediente: " + numExpediente + ", error: ", e);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoBajasBean comprobarBtv(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		String nombreXml = null;
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(numExpediente, Boolean.TRUE);
			resultado = servicioValidacionBajas.comprobarLocalmenteTramitabilidad(tramiteTrafBajaVO, numExpediente);
			if (!resultado.getError()) {
				String gestionarConAM = gestorProperties.valorPropertie("gestionar.conAm");
				if (COLEGIADO_AM.equals(tramiteTrafBajaVO.getNumColegiado()) && SI.equals(gestionarConAM)) {
					resultado = servicioPersistenciaBaja.realizarCheckBtv(tramiteTrafBajaVO.getNumExpediente(),
							ProcesosAmEnum.CHECK_BTV.toString(), null, idUsuario,
							gestorProperties.valorPropertie("nombreHostSolicitud"),
							tramiteTrafBajaVO.getContrato().getIdContrato());
				} else {
					resultado = servicioPersistenciaBaja.realizarCheckBtv(tramiteTrafBajaVO.getNumExpediente(),
							ProcesosEnum.CONSULTABTV.toString(), null, idUsuario,
							gestorProperties.valorPropertie("nombreHostSolicitud"),
							tramiteTrafBajaVO.getContrato().getIdContrato());
					}
				}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de comprobarBTV del tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobarBTV del trámite de baja con número de expediente: " + numExpediente);
		}
		if (resultado.getError() && StringUtils.isNotBlank(nombreXml)) {
			try {
				buildCheckBtv.borraFicheroSiExisteConExtension(numExpediente, nombreXml);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de borrar el fichero del checkBtv si existe para el expediente: " + numExpediente + ", error: ", e);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoBajasBean validarTramite(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(numExpediente, Boolean.TRUE);
			resultado = validacionInterna(tramiteTrafBajaVO, numExpediente);
			if (!resultado.getError()) {
				String gestionarConAM = gestorProperties.valorPropertie("gestionar.conAm");
				if (COLEGIADO_AM.equals(tramiteTrafBajaVO.getNumColegiado()) && SI.equals(gestionarConAM)) {
					ResultadoBean resultVal = servicioWebServiceAm.validarBaja(tramiteTrafBajaVO.getNumExpediente(), idUsuario.longValue());
					if (!resultVal.getError()) {
						if (StringUtils.isNotBlank(resultVal.getEstado())) {
							resultado.setEstadoNuevo(Long.parseLong(resultVal.getEstado()));
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se ha podido recuperar el estado del trámite: " + tramiteTrafBajaVO.getNumExpediente());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultVal.getMensaje());
					}
				} else {
					if (MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())
							|| MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())) {
						resultado = servicioValidacionBajas.validarTramiteBtvPQ(tramiteTrafBajaVO,idUsuario);
					}else if(utiles.esUsuarioBajaSive(tramiteTrafBajaVO.getNumColegiado()) 
							&& (MotivoBaja.TempV.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) || MotivoBaja.TempR.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()))
								&& !"B06".equalsIgnoreCase(tramiteTrafBajaVO.getVehiculo().getIdServicio())) {
						resultado = servicioValidacionBajas.validarTramiteBtvPQ(tramiteTrafBajaVO,idUsuario);
					} else {
						resultado = servicioValidacionBajas.validarTramitePQ(tramiteTrafBajaVO);
					}
				}
				if (!resultado.getError() && resultado.getEstadoNuevo() != null && EstadoTramiteTrafico.Validado_PDF
						.getValorEnum().equals(resultado.getEstadoNuevo().toString())) {
					try {
						servicioComunTramiteTrafico.custodiar(tramiteTrafBajaVO, tramiteTrafBajaVO.getContrato().getColegiado().getAlias());
					} catch (Throwable e) {
						log.error("Error al guardar el archivo de custodia para el expediente: " + tramiteTrafBajaVO.getNumExpediente() + ", error: ", e);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de baja con número de expediente: " + numExpediente);
		}
		return resultado;
	}

	private ResultadoBajasBean validacionInterna(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		if (tramiteTrafBajaVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos para el numero de expediente: " + numExpediente);
		} else {
			resultado = servicioValidacionBajas.validarFechaMatriculacion(tramiteTrafBajaVO);
		}
		return resultado;
	}

	@Override
	public ResultadoBajasBean obtenerTramiteBajaDto(BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			log.info("Recuperar el trámite baja: " + numExpediente);
			TramiteTrafBajaVO tramiteTrafBajaBBDD = servicioPersistenciaBaja.getTramiteBaja(numExpediente, Boolean.TRUE);
			if (tramiteTrafBajaBBDD != null) {
				log.info("trámite baja: " + numExpediente + " recuperado");
				TramiteTrafBajaDto tramiteTrafBajaDto = conversor.transform(tramiteTrafBajaBBDD, TramiteTrafBajaDto.class);
				if (tramiteTrafBajaBBDD.getIntervinienteTraficosAsList() != null
						&& !tramiteTrafBajaBBDD.getIntervinienteTraficosAsList().isEmpty()) {
					convertirIntervinientesVoToDto(tramiteTrafBajaBBDD, tramiteTrafBajaDto);
				}
				if (tramiteTrafBajaBBDD.getTramiteFacturacion() != null
						&& !tramiteTrafBajaBBDD.getTramiteFacturacion().isEmpty()
						&& null != tramiteTrafBajaBBDD.getFirstElementTramiteFacturacion()) {
					log.info("Inicio convertir facturacion expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
					tramiteTrafBajaDto.setTramiteFacturacion(conversor.transform(tramiteTrafBajaBBDD.getFirstElementTramiteFacturacion(), TramiteTrafFacturacionDto.class));
					log.info("Fin convertir facturacion expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
					// Si existe facturación se debe de recuperar la persona y la dirección
					log.info("Inicio convertir persona facturacion expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
					PersonaVO persona = servicioComunPersona.getPersona(tramiteTrafBajaDto.getTramiteFacturacion().getNif(), tramiteTrafBajaDto.getNumColegiado());
					if (persona != null) {
						tramiteTrafBajaDto.getTramiteFacturacion().setPersona(conversor.transform(persona, PersonaDto.class));
						ResultadoPersonaBean resultDir = servicioComunPersonaDireccion.buscarPersonaDireccionVO(
								tramiteTrafBajaDto.getTramiteFacturacion().getNif(),
								tramiteTrafBajaDto.getNumColegiado());
						if (!resultDir.getError() && resultDir.getPersonaDireccion() != null
								&& resultDir.getPersonaDireccion().getDireccion() != null) {
							tramiteTrafBajaDto.getTramiteFacturacion().setDireccion(conversor.transform(resultDir.getPersonaDireccion().getDireccion(), DireccionDto.class));
						}
					}
					log.info("Fin convertir persona facturacion expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
				}
				resultado.setTramiteBajaDto(tramiteTrafBajaDto);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos en la base de datos para el expediente: " + numExpediente);
			}
			log.info("trámite baja: " + numExpediente + " convertido a dto");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el trámite de baja con número de expediente: " + numExpediente);
		}
		return resultado;
	}

	private void convertirIntervinientesVoToDto(TramiteTrafBajaVO tramiteTrafBajaBBDD, TramiteTrafBajaDto tramiteTrafBajaDto) {
		log.info("Inicio convertir intervinientes expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
		for (IntervinienteTraficoVO interviniente : tramiteTrafBajaBBDD.getIntervinienteTraficosAsList()) {
			if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
				tramiteTrafBajaDto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
			} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())) {
				tramiteTrafBajaDto.setAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
			} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
				tramiteTrafBajaDto.setRepresentanteTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
			} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
				tramiteTrafBajaDto.setRepresentanteAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
			}
		}
		log.info("Fin convertir intervinientes expediente: " + tramiteTrafBajaBBDD.getNumExpediente());
	}

	@Override
	public ResultadoBajasBean guardarTramite(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			Boolean guardarEvo = Boolean.FALSE;
			BigDecimal estadoAnt = null;
			Date fecha = new Date();
			log.info("Inicio tramite baja.");
			log.info("Inicio validaciones bloqueantes guardado");
			resultado = servicioValidacionBajas.validacionesBloqueantesGuardado(tramiteTraficoBaja);
			log.info("Fin validaciones bloqueantes guardado");
			if (!resultado.getError()) {
				TramiteTrafBajaVO tramiteTrafBajaVO = null;
				if (tramiteTraficoBaja.getNumExpediente() != null) {
					log.info("Recuperamos datos BBDD tramite: " + tramiteTraficoBaja.getNumExpediente());
					tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(tramiteTraficoBaja.getNumExpediente(), Boolean.TRUE);
					if (tramiteTrafBajaVO == null) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existen datos del expediente: " + tramiteTraficoBaja.getNumExpediente() + " en BBDD para actualizar.");
					} else {
						log.info("Inicio conversion expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
						ContratoDto contratoDto = new ContratoDto();
						contratoDto.setIdContrato(tramiteTraficoBaja.getIdContrato());
						tramiteTraficoBaja.setContrato(contratoDto);
						conversor.transform(tramiteTraficoBaja, tramiteTrafBajaVO);
						if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString())) {
							estadoAnt = tramiteTrafBajaVO.getEstado();
							guardarEvo = Boolean.TRUE;
							tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						}
						tramiteTrafBajaVO.setFechaUltModif(fecha);
						log.info("Fin conversion expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
					}
				} else {
					log.info("Inicio creacion expediente tramite baja");
					guardarEvo = Boolean.TRUE;
					tramiteTrafBajaVO = new TramiteTrafBajaVO();
					log.info("Inicio conversion expediente tramite baja");
					conversor.transform(tramiteTraficoBaja, tramiteTrafBajaVO);
					log.info("Fin conversion expediente tramite baja");
					tramiteTrafBajaVO.setFechaAlta(fecha);
					tramiteTrafBajaVO.setFechaUltModif(fecha);
					tramiteTrafBajaVO.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
					ContratoVO contratoVO = new ContratoVO();
					contratoVO.setIdContrato(tramiteTraficoBaja.getIdContrato().longValue());
					tramiteTrafBajaVO.setContrato(contratoVO);
					UsuarioVO usuario = new UsuarioVO();
					usuario.setIdUsuario(idUsuario.longValue());
					tramiteTrafBajaVO.setUsuario(usuario);
					tramiteTrafBajaVO.setNumColegiado(tramiteTraficoBaja.getNumColegiado());
					tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					tramiteTrafBajaVO.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
					ResultadoBean resultGenNumExp = servicioComunTramiteTrafico.crearTramite(
							tramiteTraficoBaja.getIdContrato().longValue(), fecha,
							TipoTramiteTrafico.Baja.getValorEnum(), idUsuario.longValue(),
							tramiteTraficoBaja.getNumColegiado(), new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
					if (resultGenNumExp.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultGenNumExp.getMensaje());
					} else {
						tramiteTrafBajaVO.setNumExpediente(resultGenNumExp.getNumExpediente());
					}
				}
				if (!resultado.getError()) {
					log.info("Inicio guardados objetos expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
					tramiteTrafBajaVO.getVehiculo().setCheckFechaCaducidadItv(SI);
					guardarVehiculo(tramiteTrafBajaVO, resultado, idUsuario.longValue());
					guardarIntervinientes(tramiteTraficoBaja, tramiteTrafBajaVO.getNumExpediente(), idUsuario.longValue(), resultado);
					gestionarTasa(tramiteTrafBajaVO, idUsuario, resultado);
					guardarTramiteBaja(tramiteTrafBajaVO, resultado, estadoAnt, guardarEvo, idUsuario.longValue());
					log.info("Fin guardados objetos expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
					resultado.setNumExpediente(tramiteTrafBajaVO.getNumExpediente());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramite de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el trámite de baja.");
		}
		return resultado;
	}

	@Override
	public ResultadoBean crearTramiteBajaJustificante(Long idContrato, Long idUsuario, String numColegiado,
			IntervinienteTraficoDto titular, VehiculoDto vehiculo, String refPropia, String jefaturaProvincial) {
		ResultadoBean resultGenNumExp = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramite = new TramiteTrafBajaVO();
			Date fecha = new Date();
			resultGenNumExp = servicioComunTramiteTrafico.crearTramite(idContrato, fecha,
					TipoTramiteTrafico.Baja.getValorEnum(), idUsuario, numColegiado,
					new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

			IntervinienteTraficoVO titularVO = convertirIntervinienteTrafDtoToVO(titular, numColegiado,
					resultGenNumExp.getNumExpediente(), TipoInterviniente.Titular.getValorEnum());
			servicioComunIntervinienteTrafico.guardarIntervinienteTrafico(titularVO, idUsuario,
					TipoTramiteTrafico.Baja.getValorEnum(), ServicioPersona.CONVERSION_PERSONA_BAJA,
					ServicioComunDireccion.CONVERSION_DIRECCION_INE);

			VehiculoVO vehiculoVO = conversor.transform(vehiculo, VehiculoVO.class);
			ResultadoVehiculoBean resultVehiculo = servicioComunVehiculo.guardarVehiculo(vehiculoVO,
					resultGenNumExp.getNumExpediente(), TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(),
					idUsuario, null, ServicioComunVehiculo.CONVERSION_VEHICULO_BAJAS, Boolean.FALSE, Boolean.FALSE,
					null);
			if (resultVehiculo != null && !resultVehiculo.getError()) {
				tramite.setVehiculo(resultVehiculo.getVehiculo());
			}

			tramite.setNumExpediente(resultGenNumExp.getNumExpediente());
			tramite.setNumColegiado(numColegiado);
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(idContrato);
			tramite.setContrato(contrato);
			tramite.setFechaAlta(fecha);
			tramite.setFechaUltModif(fecha);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			tramite.setUsuario(usuario);
			tramite.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
			tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
			tramite.setRefPropia(refPropia);
			tramite.setJefaturaTrafico(servicioJefaturaTrafico.getJefaturaTrafico(jefaturaProvincial));

			EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = rellenarEvolucionTramite(tramite, null, idUsuario);
			servicioPersistenciaBaja.guardarActualizarTramiteConEvo(tramite, evolucionTramiteTraficoVO);
		} catch (Exception e) {
			log.error("No se ha guardado el expediente de baja para el justificante profesiones", e);
		}
		return resultGenNumExp;
	}

	private void guardarTramiteBaja(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado,
			BigDecimal estadoAnt, Boolean guardarEvo, Long idUsuario) {
		log.info("Inicio actualizacion expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		try {
			if (tramiteTrafBajaVO.getVehiculo() != null) {
				tramiteTrafBajaVO.getVehiculo().setCheckFechaCaducidadItv(SI);
			}
			if (guardarEvo) {
				EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = rellenarEvolucionTramite(tramiteTrafBajaVO, estadoAnt, idUsuario);
				servicioPersistenciaBaja.guardarActualizarTramiteConEvo(tramiteTrafBajaVO, evolucionTramiteTraficoVO);
			} else {
				servicioPersistenciaBaja.guardarActualizarTramite(tramiteTrafBajaVO);
			}
			log.info("Fin actualizacion expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramite: " + tramiteTrafBajaVO.getNumExpediente() + " en la tabla de baja, error: ", e);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de guardar el trámite.");
		}
	}

	private EvolucionTramiteTraficoVO rellenarEvolucionTramite(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal estadoAnt, Long idUsuario) {
		EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();

		id.setEstadoAnterior(estadoAnt == null ? BigDecimal.ZERO : estadoAnt);

		id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
		id.setNumExpediente(tramiteTrafBajaVO.getNumExpediente());
		evolucionTramiteTraficoVO.setId(id);
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(idUsuario);
		evolucionTramiteTraficoVO.setUsuario(usuarioVO);
		return evolucionTramiteTraficoVO;
	}

	private void gestionarTasa(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal idUsuario, ResultadoBajasBean resultado) {
		log.info("Inicio gestion Tasas expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		String tipoTasa = TipoTasa.CuatroUno.getValorEnum();
		if (tramiteTrafBajaVO.getTasa() != null && (StringUtils.isBlank(tramiteTrafBajaVO.getTasa().getCodigoTasa())
				|| "-1".equals(tramiteTrafBajaVO.getTasa().getCodigoTasa()))) {
			tramiteTrafBajaVO.setTasa(null);
		}
		ResultadoTasaBean resultadoTasa = servicioComunTasa.gestionarTasaTramite(tramiteTrafBajaVO.getTasa(),
				tramiteTrafBajaVO.getNumExpediente(), idUsuario.longValue(), tipoTasa);
		if (resultadoTasa.getError()) {
			resultado.addListaMensajeError(resultadoTasa.getMensaje());
			tramiteTrafBajaVO.setTasa(null);
		}
		log.info("Fin gestion Tasas expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		log.info("Inicio gestion Tasa Duplicado expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		TasaVO tasaDuplicado = null;
		tipoTasa = TipoTasa.CuatroCuatro.getValorEnum();
		if (StringUtils.isNotBlank(tramiteTrafBajaVO.getTasaDuplicado())
				&& !"-1".equals(tramiteTrafBajaVO.getTasaDuplicado())) {
			tasaDuplicado = new TasaVO();
			tasaDuplicado.setCodigoTasa(tramiteTrafBajaVO.getTasaDuplicado());
			tasaDuplicado.setTipoTasa(TipoTasa.CuatroCuatro.getValorEnum());
		} else {
			tasaDuplicado = new TasaVO();
			tasaDuplicado.setTipoTasa(TipoTasa.CuatroCuatro.getValorEnum());
			tramiteTrafBajaVO.setTasaDuplicado(null);
		}
		resultadoTasa = servicioComunTasa.gestionarTasaTramite(tasaDuplicado, tramiteTrafBajaVO.getNumExpediente(), idUsuario.longValue(), tipoTasa);
		if (resultadoTasa.getError()) {
			resultado.addListaMensajeError(resultadoTasa.getMensaje());
			tramiteTrafBajaVO.setTasa(null);
		}
		log.info("Fin gestion Tasa Duplicado expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
	}

	private void guardarIntervinientes(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal numExpediente, Long idUsuario,
			ResultadoBajasBean resultado) {
		List<IntervinienteTraficoVO> listaIntervinientes = convertirIntervinientesDtoToVO(tramiteTraficoBaja, numExpediente);
		if (listaIntervinientes != null && !listaIntervinientes.isEmpty()) {
			log.info("Inicio guardado intervinientes expediente tramite baja: " + numExpediente);
			for (IntervinienteTraficoVO intervinienteTraficoVO : listaIntervinientes) {
				try {
					String conversion = null;
					if (TipoInterviniente.RepresentanteTitular.getValorEnum()
							.equals(intervinienteTraficoVO.getId().getTipoInterviniente())
							|| TipoInterviniente.RepresentanteAdquiriente.getValorEnum()
									.equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
						conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE;
					} else {
						conversion = ServicioPersona.CONVERSION_PERSONA_BAJA;
					}
					ResultadoBean resultInterv = servicioComunIntervinienteTrafico.guardarIntervinienteTrafico(
							intervinienteTraficoVO, idUsuario, TipoTramiteTrafico.Baja.getValorEnum(), conversion,
							ServicioComunDireccion.CONVERSION_DIRECCION_INE);
					if (resultInterv.getError()) {
						resultado.addListaMensajeError(resultInterv.getMensaje());
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de guardar el interviniente del tipo: " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
							+ " y nif: " + intervinienteTraficoVO.getId().getNif() + " para el expediente: " + intervinienteTraficoVO.getId().getNumExpediente() + ", error: ", e);
					resultado.addListaMensajeError("Ha sucedido un error a la hora de guardar el interviniente del tipo: " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId()
							.getTipoInterviniente()) + " y nif: " + intervinienteTraficoVO.getId().getNif() + " para el expediente: " + intervinienteTraficoVO.getId().getNumExpediente());
				}
			}
			log.info("Fin guardado interviniente expediente tramite baja: " + numExpediente);
		} else {
			gestionarBorrarIntervinientes(servicioComunIntervinienteTrafico.getListaIntervinientesExpediente(numExpediente), resultado, numExpediente);
		}
	}

	private void gestionarBorrarIntervinientes(List<IntervinienteTraficoVO> listaIntervinientesExpediente, ResultadoBajasBean resultado, BigDecimal numExpediente) {
		if (listaIntervinientesExpediente != null && !listaIntervinientesExpediente.isEmpty()) {
			log.info("Inicio borrado intervinientes expediente tramite baja: " + numExpediente);
			for (IntervinienteTraficoVO intervinienteTraficoBBDD : listaIntervinientesExpediente) {
				try {
					servicioComunIntervinienteTrafico.eliminarIntervinienteVO(intervinienteTraficoBBDD);
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de eliminar el interviniente del tipo: " + TipoInterviniente.convertirTexto(intervinienteTraficoBBDD.getId().getTipoInterviniente())
							+ " y nif: " + intervinienteTraficoBBDD.getId().getNif() + " para el expediente: " + intervinienteTraficoBBDD.getId().getNumExpediente() + ", error: ", e);
					resultado.addListaMensajeError("Ha sucedido un error a la hora de eliminar el interviniente del tipo: " + TipoInterviniente.convertirTexto(intervinienteTraficoBBDD.getId()
							.getTipoInterviniente()) + " y nif: " + intervinienteTraficoBBDD.getId().getNif() + " para el expediente: " + intervinienteTraficoBBDD.getId().getNumExpediente());
				}
			}
			log.info("Fin borrado intervinientes expediente tramite baja: " + numExpediente);
		}
	}

	private List<IntervinienteTraficoVO> convertirIntervinientesDtoToVO(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal numExpediente) {
		List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<>();
		if (tramiteTraficoBaja.getTitular() != null && tramiteTraficoBaja.getTitular().getPersona() != null
				&& StringUtils.isNotBlank(tramiteTraficoBaja.getTitular().getPersona().getNif())) {
			listaIntervinientes.add(convertirIntervinienteTrafDtoToVO(tramiteTraficoBaja.getTitular(),
					tramiteTraficoBaja.getNumColegiado(), numExpediente, TipoInterviniente.Titular.getValorEnum()));
		}
		if (tramiteTraficoBaja.getRepresentanteTitular() != null
				&& tramiteTraficoBaja.getRepresentanteTitular().getPersona() != null
				&& StringUtils.isNotBlank(tramiteTraficoBaja.getRepresentanteTitular().getPersona().getNif())) {
			listaIntervinientes.add(convertirIntervinienteTrafDtoToVO(tramiteTraficoBaja.getRepresentanteTitular(),
					tramiteTraficoBaja.getNumColegiado(), numExpediente,
					TipoInterviniente.RepresentanteTitular.getValorEnum()));
		}
		if (tramiteTraficoBaja.getAdquiriente() != null && tramiteTraficoBaja.getAdquiriente().getPersona() != null
				&& StringUtils.isNotBlank(tramiteTraficoBaja.getAdquiriente().getPersona().getNif())) {
			listaIntervinientes.add(convertirIntervinienteTrafDtoToVO(tramiteTraficoBaja.getAdquiriente(),
					tramiteTraficoBaja.getNumColegiado(), numExpediente, TipoInterviniente.Adquiriente.getValorEnum()));
		}
		if (tramiteTraficoBaja.getRepresentanteAdquiriente() != null
				&& tramiteTraficoBaja.getRepresentanteAdquiriente().getPersona() != null
				&& StringUtils.isNotBlank(tramiteTraficoBaja.getRepresentanteAdquiriente().getPersona().getNif())) {
			listaIntervinientes.add(convertirIntervinienteTrafDtoToVO(tramiteTraficoBaja.getRepresentanteAdquiriente(),
					tramiteTraficoBaja.getNumColegiado(), numExpediente,
					TipoInterviniente.RepresentanteAdquiriente.getValorEnum()));
		}
		return listaIntervinientes;
	}

	private IntervinienteTraficoVO convertirIntervinienteTrafDtoToVO(IntervinienteTraficoDto interviniente,
			String numColegiado, BigDecimal numExpediente, String tipoInterviniente) {
		if (interviniente != null && interviniente.getPersona() != null
				&& StringUtils.isNotBlank(interviniente.getPersona().getNif())) {
			interviniente.setNifInterviniente(interviniente.getPersona().getNif());
			interviniente.setNumColegiado(numColegiado);
			interviniente.setNumExpediente(numExpediente);
			interviniente.setTipoInterviniente(tipoInterviniente);
			interviniente.getPersona().setNumColegiado(numColegiado);
			return conversor.transform(interviniente, IntervinienteTraficoVO.class);
		}
		return null;
	}

	private void guardarVehiculo(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado, Long idUsuario) {
		if (tramiteTrafBajaVO.getVehiculo() != null
				&& StringUtils.isNotBlank(tramiteTrafBajaVO.getVehiculo().getMatricula())) {
			log.info("Inicio guardado vehiculo expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
			Date fechaPresentacion = null;
			if (tramiteTrafBajaVO.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteTrafBajaVO.getFechaPresentacion();
			}
			tramiteTrafBajaVO.getVehiculo().setNumColegiado(tramiteTrafBajaVO.getNumColegiado());
			ResultadoVehiculoBean resultVehiculo = servicioComunVehiculo.guardarVehiculo(
					tramiteTrafBajaVO.getVehiculo(), tramiteTrafBajaVO.getNumExpediente(),
					tramiteTrafBajaVO.getTipoTramite(), idUsuario, fechaPresentacion,
					ServicioComunVehiculo.CONVERSION_VEHICULO_BAJAS, Boolean.FALSE, Boolean.FALSE, null);
			if (resultVehiculo.getError()) {
				resultado.addListaMensajeError(resultVehiculo.getMensaje());
				tramiteTrafBajaVO.setVehiculo(null);
			} else if (resultVehiculo.getListaMensajes() != null && !resultVehiculo.getListaMensajes().isEmpty()) {
				String mensajeAviso = "El vehículo se ha guardado con las siguientes incidencias: ";
				for (String mensaje : resultVehiculo.getListaMensajes()) {
					mensajeAviso += " " + mensaje;
				}
				resultado.addListaMensajeAvisos(mensajeAviso);
				tramiteTrafBajaVO.setVehiculo(resultVehiculo.getVehiculo());
			} else {
				tramiteTrafBajaVO.setVehiculo(resultVehiculo.getVehiculo());
			}
			log.info("Fin guardado vehiculo expediente tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		} else if (tramiteTrafBajaVO.getVehiculo() != null
				&& (tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion() != null
						|| StringUtils.isNotBlank(tramiteTrafBajaVO.getVehiculo().getBastidor()))) {
			resultado.addListaMensajeError("Los datos del Vehículo no se guardarán si no tiene Matrícula.");
			tramiteTrafBajaVO.setVehiculo(null);
		} else {
			tramiteTrafBajaVO.setVehiculo(null);
		}
	}
}