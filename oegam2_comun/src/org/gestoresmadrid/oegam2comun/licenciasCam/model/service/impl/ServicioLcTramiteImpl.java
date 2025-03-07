package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.licencias.model.dao.LcEvolucionTramiteDao;
import org.gestoresmadrid.core.licencias.model.dao.LcTramiteDao;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.licencias.model.enumerados.TipoEdificacionEnum;
import org.gestoresmadrid.core.licencias.model.enumerados.TipoResumenEdificacionEnum;
import org.gestoresmadrid.core.licencias.model.vo.LcIntervinienteVO;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcActuacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPortalAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDocumentoLicencia;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEpigrafe;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEvolucionTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoLocal;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcObra;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcParteAutonoma;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcResumenEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSuministros;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSupNoComputablePlanta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcActuacionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEdificacionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEvolucionTramiteDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioBajaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoLocalDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcIntervinienteDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcObraDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcPersonaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcResumenEdificacionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcTramiteImpl implements ServicioLcTramite {

	private static final long serialVersionUID = 5741610125491044100L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcTramiteImpl.class);

	@Autowired
	LcTramiteDao lcTramiteDao;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioLcInterviniente servicioLcInterviniente;

	@Autowired
	ServicioLcEvolucionTramite servicioLcEvolucionTramite;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioLcActuacion servicioLcActuacion;

	@Autowired
	ServicioLcObra servicioLcObra;

	@Autowired
	ServicioLcDireccion servicioLcDireccion;

	@Autowired
	ServicioLcInfoLocal servicioLcInfoLocal;

	@Autowired
	ServicioLcEpigrafe servicioLcEpigrafe;

	@Autowired
	ServicioLcParteAutonoma servicioLcParteAutonoma;

	@Autowired
	ServicioLcEdificacion servicioLcEdificacion;

	@Autowired
	ServicioLcInfoEdificioAlta servicioLcInfoEdificioAlta;

	@Autowired
	ServicioLcDatosPortalAlta servicioLcDatosPortalAlta;

	@Autowired
	ServicioLcDatosPlantaAlta servicioLcDatosPlantaAlta;

	@Autowired
	ServicioLcSupNoComputablePlanta servicioLcSupNoComputablePlanta;

	@Autowired
	ServicioLcInfoEdificioBaja servicioLcInfoEdificioBaja;

	@Autowired
	ServicioLcDatosPlantaBaja servicioLcDatosPlantaBaja;

	@Autowired
	ServicioLcDocumentoLicencia servicioLcDocumentoLicencia;

	@Autowired
	ServicioLcResumenEdificacion servicioLcResumenEdificacion;

	@Autowired
	ServicioLcSuministros servicioLcSuministros;

	@Autowired
	LcEvolucionTramiteDao lcEvTramiteDao;

	@Autowired
	Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultadoLicenciasBean guardarTramite(LcTramiteDto tramiteDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {

			if (tramiteDto.getLcIdDirEmplazamiento() != null) {
				resultado = servicioLcDireccion.guardarOActualizarDireccion(tramiteDto.getLcIdDirEmplazamiento());
				if (resultado != null && resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.setIdLcDirEmplazamiento((Long) resultado.getObj());
				}
			}

			if (tramiteDto.getLcActuacion() != null) {
				resultado = servicioLcActuacion.guardarOActualizarActuacion(tramiteDto.getLcActuacion());
				if (resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.setIdLcActuacion((Long) resultado.getObj());
				}
			}

			if (tramiteDto.getLcObra() != null && (StringUtils.isNotBlank(tramiteDto.getLcObra().getTipoObraPantalla()) || StringUtils.isNotBlank(tramiteDto.getLcObra().getTipoObra()))) {
				resultado = servicioLcObra.guardarOActualizarObra(tramiteDto.getLcObra());
				if (resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.setIdLcObra((Long) resultado.getObj());
				}
				if (tramiteDto.getIdLcObra() != null && tramiteDto.getLcObra().getParteAutonoma() != null && tramiteDto.getLcObra().getParteAutonoma().getNumero() != null) {
					tramiteDto.getLcObra().getParteAutonoma().setIdDatosObra(tramiteDto.getIdLcObra());
					resultado = servicioLcParteAutonoma.guardarOActualizarParteAutonoma(tramiteDto.getLcObra().getParteAutonoma());
					if (resultado.getError()) {
						return resultado;
					}
				}
			}

			if (tramiteDto.getLcInfoLocal() != null && StringUtils.isNotBlank(tramiteDto.getLcInfoLocal().getLocalizacion())) {
				resultado = servicioLcInfoLocal.guardarOActualizarInfoLocal(tramiteDto.getLcInfoLocal());
				if (resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.setIdLcInfoLocal((Long) resultado.getObj());
				}
				if (tramiteDto.getIdLcInfoLocal() != null && tramiteDto.getLcInfoLocal().getLcEpigrafeDto() != null && (StringUtils.isNotBlank(tramiteDto.getLcInfoLocal()
						.getLcEpigrafeDto().getSeccion()) || StringUtils.isNotBlank(tramiteDto.getLcInfoLocal().getLcEpigrafeDto().getEpigrafe()))) {
					tramiteDto.getLcInfoLocal().getLcEpigrafeDto().setIdInfoLocal(tramiteDto.getIdLcInfoLocal());
					resultado = servicioLcEpigrafe.guardarOActualizarEpigrafe(tramiteDto.getLcInfoLocal().getLcEpigrafeDto());
					if (resultado.getError()) {
						return resultado;
					}
				}
			}

			if (tramiteDto.getLcDatosSuministros() != null) {
				resultado = servicioLcSuministros.guardarOActualizarSuministro(tramiteDto.getLcDatosSuministros());
				if (resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.setIdLcDatosSuministros((Long) resultado.getObj());
				}
			}

			LcTramiteVO tramiteVO = conversor.transform(tramiteDto, LcTramiteVO.class);
			lcTramiteDao.guardarOActualizar(tramiteVO);

			// Edificación Alta
			if (tramiteDto.getLcEdificacionAlta() != null && tramiteDto.getLcEdificacionAlta().getNumEdificios() != null && StringUtils.isNotBlank(tramiteDto.getLcEdificacionAlta().getDescripcion())
					&& StringUtils.isNotBlank(tramiteDto.getLcEdificacionAlta().getTipologia())) {
				tramiteDto.getLcEdificacionAlta().setNumExpediente(tramiteDto.getNumExpediente());
				resultado = servicioLcEdificacion.guardarOActualizarEdificacion(tramiteDto.getLcEdificacionAlta(), true);
				if (resultado.getError()) {
					return resultado;
				} else {
					tramiteDto.getLcEdificacionAlta().setIdEdificacion((Long) resultado.getObj());
					resultado = guardarResumenEdificaciones(tramiteDto.getLcEdificacionAlta());
					if (resultado.getError()) {
						return resultado;
					}
					// Se guarda/actualiza Info Edificio Alta si se ha rellenado
					if (tramiteDto.getLcEdificacionAlta().getIdEdificacion() != null && tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta() != null && tramiteDto.getLcEdificacionAlta()
							.getLcInfoEdificioAlta().getLcDirEdificacionAlta() != null) {
						tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setIdEdificacion(tramiteDto.getLcEdificacionAlta().getIdEdificacion());
						resultado = servicioLcInfoEdificioAlta.guardarOActualizarInfoEdificioAlta(tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta());
						if (resultado.getError()) {
							return resultado;
						} else {
							// Se guarda/actualiza Portal Alta si se ha rellenado
							if (tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta() != null) {
								tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setIdInfoEdificioAlta((Long) resultado.getObj());
								resultado = servicioLcDatosPortalAlta.guardarOActualizarDatosPortalAlta(tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta());
								if (resultado.getError()) {
									return resultado;
								} else {
									// Se guarda/actualiza Planta Alta si se ha rellenado
									if (tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta() != null) {
										tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setIdDatosPortalAlta((Long) resultado.getObj());
										resultado = servicioLcDatosPlantaAlta.guardarOActualizarDatosPlantaAlta(tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta()
												.getLcDatosPlantaAlta());
										if (resultado.getError()) {
											return resultado;
										} else {
											// Se guarda/actualiza Superficies No Computables Alta si se ha rellenado
											if (tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getLcSupNoComputablePlanta() != null) {
												tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getLcSupNoComputablePlanta()
														.setIdDatosPlantaAlta((Long) resultado.getObj());
												resultado = servicioLcSupNoComputablePlanta.guardarOActualizarSupNoComputablesPlanta(tramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta()
														.getLcDatosPortalAlta().getLcDatosPlantaAlta().getLcSupNoComputablePlanta());
												if (resultado.getError()) {
													return resultado;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			// Edificación Baja
			if (tramiteDto.getLcEdificacionBaja() != null && tramiteDto.getLcEdificacionBaja().getNumEdificios() != null && StringUtils.isNotBlank(tramiteDto.getLcEdificacionBaja().getIndustrial())
					&& StringUtils.isNotBlank(tramiteDto.getLcEdificacionBaja().getTipoDemolicion())) {
				tramiteDto.getLcEdificacionBaja().setNumExpediente(tramiteDto.getNumExpediente());
				resultado = servicioLcEdificacion.guardarOActualizarEdificacion(tramiteDto.getLcEdificacionBaja(), false);
				if (resultado.getError()) {
					return resultado;
				} else {
					// Se guarda/actualiza Info Edificio Baja si se ha rellenado
					if (tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja() != null && tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDirEdificacion() != null) {
						tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setIdEdificacion((Long) resultado.getObj());
						resultado = servicioLcInfoEdificioBaja.guardarOActualizarInfoEdificioBaja(tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja());
						if (resultado.getError()) {
							return resultado;
						} else {
							// Se guarda/actualiza Planta Baja si se ha rellenado
							if (tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja() != null) {
								tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja().setIdInfoEdificioBaja((Long) resultado.getObj());
								resultado = servicioLcDatosPlantaBaja.guardarOActualizarDatosPlantaBaja(tramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja());
								if (resultado.getError()) {
									return resultado;
								}
							}
						}
					}
				}
			}

			// Interviniente Interesado Principal
			if (tramiteDto.getIntervinienteInteresado() != null && tramiteDto.getIntervinienteInteresado().getLcPersona() != null && StringUtils.isNotBlank(tramiteDto.getIntervinienteInteresado()
					.getLcPersona().getNif())) {
				tramiteDto.getIntervinienteInteresado().setTipoInterviniente(TipoInterviniente.InteresadoPrincipal.getValorEnum());
				tramiteDto.getIntervinienteInteresado().setNumColegiado(tramiteDto.getNumColegiado());
				tramiteDto.getIntervinienteInteresado().setNumExpediente(tramiteDto.getNumExpediente());

				resultado = servicioLcInterviniente.guardarInterviniente(tramiteDto.getIntervinienteInteresado());
				if (resultado.getError()) {
					return resultado;
				}
			}

			// Interviniente Representante Interesado Principal
			if (tramiteDto.getIntervinienteRepresentante() != null && tramiteDto.getIntervinienteRepresentante().getLcPersona() != null && StringUtils.isNotBlank(tramiteDto
					.getIntervinienteRepresentante().getLcPersona().getNif())) {
				tramiteDto.getIntervinienteRepresentante().setTipoInterviniente(TipoInterviniente.RepresentanteInteresado.getValorEnum());
				tramiteDto.getIntervinienteRepresentante().setNumColegiado(tramiteDto.getNumColegiado());
				tramiteDto.getIntervinienteRepresentante().setNumExpediente(tramiteDto.getNumExpediente());

				resultado = servicioLcInterviniente.guardarInterviniente(tramiteDto.getIntervinienteRepresentante());
				if (resultado.getError()) {
					return resultado;
				}
			}

			// Interviniente Otro Interesado
			if (tramiteDto.getIntervinienteOtrosInteresados() != null && tramiteDto.getIntervinienteOtrosInteresados().getLcPersona() != null && StringUtils.isNotBlank(tramiteDto
					.getIntervinienteOtrosInteresados().getLcPersona().getNif())) {
				tramiteDto.getIntervinienteOtrosInteresados().setTipoInterviniente(TipoInterviniente.OtroInteresado.getValorEnum());
				tramiteDto.getIntervinienteOtrosInteresados().setNumColegiado(tramiteDto.getNumColegiado());
				tramiteDto.getIntervinienteOtrosInteresados().setNumExpediente(tramiteDto.getNumExpediente());

				resultado = servicioLcInterviniente.guardarInterviniente(tramiteDto.getIntervinienteOtrosInteresados());
				if (resultado.getError()) {
					return resultado;
				}
			}

			// Interviniente Notificación
			if (tramiteDto.getIntervinienteNotificacion() != null && tramiteDto.getIntervinienteNotificacion().getLcPersona() != null && StringUtils.isNotBlank(tramiteDto
					.getIntervinienteNotificacion().getLcPersona().getNif())) {
				tramiteDto.getIntervinienteNotificacion().setTipoInterviniente(TipoInterviniente.Notificacion.getValorEnum());
				tramiteDto.getIntervinienteNotificacion().setNumColegiado(tramiteDto.getNumColegiado());
				tramiteDto.getIntervinienteNotificacion().setNumExpediente(tramiteDto.getNumExpediente());

				resultado = servicioLcInterviniente.guardarInterviniente(tramiteDto.getIntervinienteNotificacion());
				if (resultado.getError()) {
					return resultado;
				}
			}

		} catch (Exception e) {
			log.error("Error al guardar el trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar el trámite");
		} finally {
			if (resultado.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultadoLicenciasBean crearTramite(LcTramiteDto tramiteDto, BigDecimal idUsuario) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			ContratoVO contrato = servicioContrato.getContrato(tramiteDto.getIdContrato());
			if (contrato != null && contrato.getColegio() != null && contrato.getColegio().getCif() != null && !contrato.getColegio().getCif().isEmpty()) {
				tramiteDto.setNumExpediente(obtenerNExpediente(contrato.getColegiado().getNumColegiado()));
				tramiteDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
				tramiteDto.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
				tramiteDto.setEstado(new BigDecimal(EstadoLicenciasCam.Iniciado.getValorEnum()));

				LcTramiteVO tramiteVO = conversor.transform(tramiteDto, LcTramiteVO.class);
				lcTramiteDao.guardarOActualizar(tramiteVO);

				guardarEvolucionTramite(tramiteDto.getNumExpediente(), BigDecimal.ZERO, tramiteDto.getEstado(), idUsuario);

			}
		} catch (Exception e) {
			log.error("Error al guardar el contrato.");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar el contrato");
			return resultado;
		}
		return resultado;
	}

	private ResultadoLicenciasBean guardarResumenEdificaciones(LcEdificacionDto edificacion) {
		ResultadoLicenciasBean resultLicencia = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			if (edificacion.getLcResumenEdificacionVivienda() != null && edificacion.getLcResumenEdificacionVivienda().getNumUnidadesBajoRasante() != null && edificacion
					.getLcResumenEdificacionVivienda().getNumUnidadesSobreRasante() != null) {
				edificacion.getLcResumenEdificacionVivienda().setIdEdificacion(edificacion.getIdEdificacion());
				edificacion.getLcResumenEdificacionVivienda().setTipoResumenEdificacion(TipoResumenEdificacionEnum.Vivienda.getValorEnum());
				servicioLcResumenEdificacion.guardarOActualizarResumenEdificacion(edificacion.getLcResumenEdificacionVivienda());
			}
			if (edificacion.getLcResumenEdificacionLocal() != null && edificacion.getLcResumenEdificacionLocal().getNumUnidadesBajoRasante() != null && edificacion.getLcResumenEdificacionLocal()
					.getNumUnidadesSobreRasante() != null) {
				edificacion.getLcResumenEdificacionLocal().setIdEdificacion(edificacion.getIdEdificacion());
				edificacion.getLcResumenEdificacionLocal().setTipoResumenEdificacion(TipoResumenEdificacionEnum.Local.getValorEnum());
				servicioLcResumenEdificacion.guardarOActualizarResumenEdificacion(edificacion.getLcResumenEdificacionLocal());
			}
			if (edificacion.getLcResumenEdificacionGaraje() != null && edificacion.getLcResumenEdificacionGaraje().getNumUnidadesBajoRasante() != null && edificacion.getLcResumenEdificacionGaraje()
					.getNumUnidadesSobreRasante() != null) {
				edificacion.getLcResumenEdificacionGaraje().setIdEdificacion(edificacion.getIdEdificacion());
				edificacion.getLcResumenEdificacionGaraje().setTipoResumenEdificacion(TipoResumenEdificacionEnum.Garaje.getValorEnum());
				servicioLcResumenEdificacion.guardarOActualizarResumenEdificacion(edificacion.getLcResumenEdificacionGaraje());
			}
			if (edificacion.getLcResumenEdificacionTrastero() != null && edificacion.getLcResumenEdificacionTrastero().getNumUnidadesBajoRasante() != null && edificacion
					.getLcResumenEdificacionTrastero().getNumUnidadesSobreRasante() != null) {
				edificacion.getLcResumenEdificacionTrastero().setIdEdificacion(edificacion.getIdEdificacion());
				edificacion.getLcResumenEdificacionTrastero().setTipoResumenEdificacion(TipoResumenEdificacionEnum.Trastero.getValorEnum());
				servicioLcResumenEdificacion.guardarOActualizarResumenEdificacion(edificacion.getLcResumenEdificacionTrastero());
			}
		} catch (Exception e) {
			log.error("Error al guardar el resumen de edificación", e);
			resultLicencia.setError(Boolean.TRUE);
			resultLicencia.addMensaje("Ha sucedido un error a la hora de guardar el Resumen de Edificación");
		}
		return resultLicencia;
	}

	@Override
	@Transactional
	public void guardarOActualizar(LcTramiteVO tramite) throws Exception {
		try {
			lcTramiteDao.guardarOActualizar(tramite);
		} catch (Exception e) {
			throw new Exception("Error al guardar el trámite de licencias");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoLicenciasBean getTramiteLc(BigDecimal numExpediente) {
		ResultadoLicenciasBean resultLicencia = new ResultadoLicenciasBean(Boolean.FALSE);

		LcTramiteVO lcTramiteVO = lcTramiteDao.getTramiteLc(numExpediente, true);
		if (lcTramiteVO != null) {
			LcTramiteDto lcTramiteDto = conversor.transform(lcTramiteVO, LcTramiteDto.class);

			// Recuperamos los intervinientes
			lcTramiteDto.setOtrosInteresados(new ArrayList<LcIntervinienteDto>());
			if (lcTramiteVO.getLcIntervinientesAsList() != null && !lcTramiteVO.getLcIntervinientesAsList().isEmpty()) {
				for (LcIntervinienteVO elemento : lcTramiteVO.getLcIntervinientesAsList()) {
					if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equalsIgnoreCase(elemento.getTipoInterviniente())) {
						lcTramiteDto.setIntervinienteInteresado(conversor.transform(elemento, LcIntervinienteDto.class));
					} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equalsIgnoreCase(elemento.getTipoInterviniente())) {
						lcTramiteDto.setIntervinienteRepresentante(conversor.transform(elemento, LcIntervinienteDto.class));
					} else if (TipoInterviniente.Notificacion.getValorEnum().equalsIgnoreCase(elemento.getTipoInterviniente())) {
						lcTramiteDto.setIntervinienteNotificacion(conversor.transform(elemento, LcIntervinienteDto.class));
					} else if (TipoInterviniente.OtroInteresado.getValorEnum().equalsIgnoreCase(elemento.getTipoInterviniente())) {
						lcTramiteDto.getOtrosInteresados().add(conversor.transform(elemento, LcIntervinienteDto.class));
					}
				}
			}

			if (lcTramiteDto.getOtrosInteresados() != null && !lcTramiteDto.getOtrosInteresados().isEmpty()) {
				Collections.sort(lcTramiteDto.getOtrosInteresados(), new ComparadorInterviniente());
			}

			if (lcTramiteDto.getLcEdificacionBaja() != null && lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificiosBaja() != null && !lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificiosBaja()
					.isEmpty()) {
				Collections.sort(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificiosBaja(), new ComparadorInfoEdificiosBaja());
			}

			// Se conforma la lista de Tipos Obra
			if (lcTramiteDto.getLcObra() != null && StringUtils.isNotBlank(lcTramiteDto.getLcObra().getTipoObra())) {
				lcTramiteDto.getLcObra().setTiposObra(new ArrayList<String>());
				String[] tipos = lcTramiteDto.getLcObra().getTipoObra().split(";");

				for (String tipo : tipos) {
					lcTramiteDto.getLcObra().getTiposObra().add(tipo);
				}
			}

			if (lcTramiteDto.getLcEdificaciones() != null && !lcTramiteDto.getLcEdificaciones().isEmpty()) {
				for (LcEdificacionDto edif : lcTramiteDto.getLcEdificaciones()) {
					if (TipoEdificacionEnum.Edificacion_Alta.getValorEnum().equals(edif.getTipoEdificacion())) {
						lcTramiteDto.setLcEdificacionAlta(edif);
						if (edif.getLcResumenEdificacion() != null && !edif.getLcResumenEdificacion().isEmpty()) {
							for (LcResumenEdificacionDto resuEdif : edif.getLcResumenEdificacion()) {
								if (TipoResumenEdificacionEnum.Vivienda.getValorEnum().equals(resuEdif.getTipoResumenEdificacion())) {
									lcTramiteDto.getLcEdificacionAlta().setLcResumenEdificacionVivienda(resuEdif);
								} else if (TipoResumenEdificacionEnum.Local.getValorEnum().equals(resuEdif.getTipoResumenEdificacion())) {
									lcTramiteDto.getLcEdificacionAlta().setLcResumenEdificacionLocal(resuEdif);
								} else if (TipoResumenEdificacionEnum.Garaje.getValorEnum().equals(resuEdif.getTipoResumenEdificacion())) {
									lcTramiteDto.getLcEdificacionAlta().setLcResumenEdificacionGaraje(resuEdif);
								} else if (TipoResumenEdificacionEnum.Trastero.getValorEnum().equals(resuEdif.getTipoResumenEdificacion())) {
									lcTramiteDto.getLcEdificacionAlta().setLcResumenEdificacionTrastero(resuEdif);
								}
							}
						}
					} else if (TipoEdificacionEnum.Edificacion_Baja.getValorEnum().equals(edif.getTipoEdificacion())) {
						lcTramiteDto.setLcEdificacionBaja(edif);
					}
				}
			}

			lcTramiteDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(lcTramiteDto.getNumColegiado(), lcTramiteDto.getIdContrato()));

			ArrayList<FicheroInfo> ficherosSubidos = servicioLcDocumentoLicencia.recuperarDocumentos(lcTramiteDto.getNumExpediente());
			if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
				lcTramiteDto.setFicherosSubidos(ficherosSubidos);
			}

			resultLicencia.setObj(lcTramiteDto);
		} else {
			resultLicencia.setError(Boolean.TRUE);
			resultLicencia.setMensaje("No se ha recuperado el trámite de Licencias CAM para el número de expdiente " + numExpediente);
		}

		return resultLicencia;
	}

	@Override
	@Transactional(readOnly = true)
	public LcTramiteVO getTramiteLcVO(BigDecimal numExpediente, boolean completo) {
		try {
			return lcTramiteDao.getTramiteLc(numExpediente, completo);
		} catch (Exception e) {
			log.error("Error a la hora de recuperar el trámite de licencias CAM", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obtenerNExpediente(String numColegiado) throws Exception {
		BigDecimal nTramite = BigDecimal.ZERO;
		if (!numColegiado.isEmpty() && numColegiado != null) {
			nTramite = lcTramiteDao.generarNumExpediente(numColegiado);
		}
		return nTramite;
	}

	@Override
	public boolean eliminarLicencia(LcTramiteDto tramite) {
		if (tramite != null) {
			LcTramiteVO tramiteVO = conversor.transform(tramite, LcTramiteVO.class);
			lcTramiteDao.borrar(tramiteVO);
			return true;
		}
		return false;
	}

	@Override
	public List<LcTramiteDto> listarTodos() {
		List<LcTramiteDto> lista = null;
		List<LcTramiteVO> vo = lcTramiteDao.buscar(null);
		if (vo != null) {
			conversor.transform(vo, lista);
			return lista;
		}
		return null;
	}

	private void validarIntervinientes(LcTramiteDto tramite, ResultadoLicenciasBean resultado) {
		if (tramite.getIntervinienteInteresado() != null && tramite.getIntervinienteInteresado().getLcPersona() != null && StringUtils.isNotBlank(tramite.getIntervinienteInteresado().getLcPersona()
				.getNif())) {
			validarInterviniente(tramite.getIntervinienteInteresado(), TipoInterviniente.InteresadoPrincipal.getNombreEnum(), resultado);
			if (tramite.getIntervinienteRepresentante() != null && tramite.getIntervinienteRepresentante().getLcPersona() != null && StringUtils.isNotBlank(tramite.getIntervinienteRepresentante()
					.getLcPersona().getNif())) {
				validarInterviniente(tramite.getIntervinienteRepresentante(), TipoInterviniente.RepresentanteInteresado.getNombreEnum(), resultado);
			}
			if (tramite.getOtrosInteresados() != null && !tramite.getOtrosInteresados().isEmpty()) {
				for (LcIntervinienteDto interviniente : tramite.getOtrosInteresados()) {
					validarInterviniente(interviniente, TipoInterviniente.OtroInteresado.getNombreEnum(), resultado);
				}
			}
			if (tramite.getIntervinienteNotificacion() != null && tramite.getIntervinienteNotificacion().getLcPersona() != null && StringUtils.isNotBlank(tramite.getIntervinienteNotificacion()
					.getLcPersona().getNif())) {
				validarInterviniente(tramite.getIntervinienteNotificacion(), TipoInterviniente.Notificacion.getNombreEnum(), resultado);
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El trámite debe contener un Interesado Principal.");
		}
	}

	private void validarInterviniente(LcIntervinienteDto interviniente, String textoValidacion, ResultadoLicenciasBean resultado) {
		validarPersona(interviniente.getLcPersona(), textoValidacion, resultado);

		if (interviniente.getLcDireccion() != null && interviniente.getLcDireccion().getIdDireccion() != null) {
			servicioLcDireccion.validarDireccion(interviniente.getLcDireccion(), resultado, textoValidacion, true, false);
		}
	}

	private void validarPersona(LcPersonaDto persona, String textoValidacion, ResultadoLicenciasBean resultado) {
		boolean errorValidacion = false;
		if (StringUtils.isBlank(persona.getNif())) {
			errorValidacion = true;
		}
		if (StringUtils.isBlank(persona.getNombre())) {
			errorValidacion = true;
		}
		if (StringUtils.isBlank(persona.getApellido1RazonSocial())) {
			errorValidacion = true;
		}
		if (StringUtils.isBlank(persona.getApellido2())) {
			errorValidacion = true;
		}
		if (StringUtils.isBlank(persona.getNumTelefono1())) {
			errorValidacion = true;
		}
		if (errorValidacion) {
			resultado.addValidacion("El " + textoValidacion + " le faltan datos obligatorios.");
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	@Transactional
	public ResultadoLicenciasBean cambiarEstado(boolean evolucion, BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		if (nuevoEstado == null || "-1".equals(nuevoEstado.toString())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("No se ha indicado el estado para el cambio de estado. ");
		} else if (antiguoEstado != null && !antiguoEstado.equals(nuevoEstado)) {
			LcTramiteVO tramite = lcTramiteDao.cambiarEstado(numExpediente, nuevoEstado);

			if (tramite == null) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Error durante el cambio de estado del trámite. ");
			}

			if (evolucion) {
				guardarEvolucionTramite(tramite.getNumExpediente(), antiguoEstado, nuevoEstado, idUsuario);
			}

			result.setObj(tramite);
			result.setError(Boolean.FALSE);
		}
		return result;
	}

	@Override
	@Transactional
	public String generarIdentificadorCam() {
		String identificadorCam = null;

		String tipoInformacion = gestorPropiedades.valorPropertie("licencias.cam.tipo.informacion");
		String codigoAgente = gestorPropiedades.valorPropertie("licencias.cam.codigo.agente");

		Fecha fecha = utilesFecha.getFechaActual();
		String anio = fecha.getAnio().substring(2);

		Integer maximoExistente = lcTramiteDao.getCountTramitesConIdSolicitud();

		if (maximoExistente == null || maximoExistente == 0) {
			identificadorCam = codigoAgente + tipoInformacion + anio + "00001";
		} else if (maximoExistente.intValue() < 99999) {
			maximoExistente = maximoExistente + 1;
			String secuencia = utiles.rellenarCeros(maximoExistente.toString(), 5);
			identificadorCam = codigoAgente + tipoInformacion + anio + secuencia;
		} else {
			identificadorCam = codigoAgente + "11" + anio + "00001";
		}

		String digitoControl = obtenerDigitoControl(identificadorCam);

		return new BigDecimal(identificadorCam + digitoControl).toString();
	}

	private String obtenerDigitoControl(String identificadorCam) {
		String codigoControl = gestorPropiedades.valorPropertie("licencias.cam.codigo.control");

		int total = 0;

		if (codigoControl != null) {
			if (codigoControl.length() == identificadorCam.length()) {
				for (int i = 0; i < codigoControl.length(); i++) {
					int numIdentificador = Integer.parseInt(identificadorCam.substring(i, i + 1));
					int numCodigoControl = Integer.parseInt(codigoControl.substring(i, i + 1));
					total = total + (numIdentificador * numCodigoControl);
				}
				total = total % 10;
			}
		}
		return String.valueOf(total);
	}

	@Override
	@Transactional
	public void guardarEvolucionTramite(BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario) {
		LcEvolucionTramiteDto evolucion = new LcEvolucionTramiteDto();
		if (antiguoEstado != null) {
			evolucion.setEstadoAnterior(antiguoEstado);
		} else {
			evolucion.setEstadoAnterior(BigDecimal.ZERO);
		}
		evolucion.setEstadoNuevo(nuevoEstado);
		evolucion.setFechaCambio(new Date());
		evolucion.setNumExpediente(numExpediente);
		evolucion.setIdUsuario(idUsuario.longValue());
		servicioLcEvolucionTramite.guardar(evolucion);
	}

	private class ComparadorInterviniente implements Comparator<LcIntervinienteDto> {
		@Override
		public int compare(LcIntervinienteDto o1, LcIntervinienteDto o2) {
			return ((Long) o1.getIdInterviniente()).compareTo(o2.getIdInterviniente());
		}

	}

	private class ComparadorInfoEdificiosBaja implements Comparator<LcInfoEdificioBajaDto> {
		@Override
		public int compare(LcInfoEdificioBajaDto o1, LcInfoEdificioBajaDto o2) {
			return ((Long) o1.getIdInfoEdificioBaja()).compareTo(o2.getIdInfoEdificioBaja());
		}

	}

	@Override
	public ResultadoLicenciasBean validarTramite(LcTramiteDto tramite) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		validarGeneral(tramite, resultado);

		validarDireccionEmplazamiento(tramite.getLcIdDirEmplazamiento(), resultado);

		validarDatosActuacion(tramite.getLcActuacion(), resultado);

		if (tramite.getLcInfoLocal() != null && StringUtils.isNotBlank(tramite.getLcInfoLocal().getLocalizacion())) {
			validarInfoLocal(tramite.getLcInfoLocal(), resultado);
		}

		validarDatosObra(tramite.getLcObra(), resultado);

		if (tramite.getLcEdificacionAlta() != null && tramite.getLcEdificacionAlta().getNumEdificios() != null) {
			validarEdificacionAlta(tramite.getLcEdificacionAlta(), resultado);
		}

		if (tramite.getLcEdificacionBaja() != null && tramite.getLcEdificacionBaja().getNumEdificios() != null) {
			validarEdificacionBaja(tramite.getLcEdificacionBaja(), resultado);
		}

		validarIntervinientes(tramite, resultado);
		return resultado;
	}

	private void validarDireccionEmplazamiento(LcDireccionDto direccion, ResultadoLicenciasBean resultado) {
		servicioLcDireccion.validarDireccion(direccion, resultado, "Emplazamiento Licencia", false, false);
	}

	private void validarDatosActuacion(LcActuacionDto actuacion, ResultadoLicenciasBean resultado) {
		servicioLcActuacion.validarDatosActuacion(actuacion, resultado);
	}

	private void validarEdificacionAlta(LcEdificacionDto edifAlta, ResultadoLicenciasBean resultado) {
		servicioLcEdificacion.validarEdificacionAlta(edifAlta, resultado);
	}

	private void validarEdificacionBaja(LcEdificacionDto edifBaja, ResultadoLicenciasBean resultado) {
		servicioLcEdificacion.validarEdificacionBaja(edifBaja, resultado);
	}

	private void validarDatosObra(LcObraDto obra, ResultadoLicenciasBean resultado) {
		servicioLcObra.validarDatosObra(obra, resultado);
	}

	private void validarInfoLocal(LcInfoLocalDto infoLocal, ResultadoLicenciasBean resultado) {
		servicioLcInfoLocal.validarInfoLocal(infoLocal, resultado);
	}

	private void validarGeneral(LcTramiteDto tramite, ResultadoLicenciasBean resultado) {
		if (StringUtils.isBlank(tramite.getCorreoElectronico())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Correo Electrónico del trámite es obligatorio.");
		}
		if (StringUtils.isBlank(tramite.getTelefono())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Teléfono del trámite es obligatorio.");
		}
		if (StringUtils.isBlank(tramite.getTipoActuacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Tipo Actuación del trámite es obligatorio.");
		}
	}
}
