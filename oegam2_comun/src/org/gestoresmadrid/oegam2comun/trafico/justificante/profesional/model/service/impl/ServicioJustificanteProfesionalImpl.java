package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTraficoJustificante;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesPK;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioEvolucionJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildJustificantesProfesionales;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildJustificantesProfesionalesSega;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.beans.schemas.generated.justificantesProf.generated.SolicitudJustificanteProfesional;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.utiles.XMLPruebaCertificado;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioJustificanteProfesionalImpl implements ServicioJustificanteProfesional {

	private static final long serialVersionUID = -5690047312374579879L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioJustificanteProfesionalImpl.class);

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public final String NOMBRE_HOST_SOLICITUD_NODO_3 = "nombreHostSolicitudProcesos3";

	private static final String RECIPIENT = "justificantes.no.finalizados.recipient";
	private static final String SUBJECT = "justificantes.no.finalizados.subject";

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	private ServicioEvolucionJustificanteProfesional servicioEvolucionJustificanteProfesional;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

	@Autowired
	private JustificanteProfDao justificanteProfDao;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	private Conversor conversor;

	@Autowired
	BuildJustificantesProfesionales buildJustificantesProfesionales;

	@Autowired
	BuildJustificantesProfesionalesSega buildJustificantesProfesionalesSega;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	@Transactional
	public ResultBean generarJustificanteTransmision(TramiteTrafTranDto tramiteTransmision, JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, boolean admin) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		int numPeticiones = 0;
		BigDecimal numExpediente = null;
		try {
			if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTransmision.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(
					tramiteTransmision.getEstado())) {
				result.setError(true);
				result.addMensajeALista("El trámite de transmisión no debe estar Finalizado Telemáticamente");
			} else {
				if (hayJustificante(null, tramiteTransmision.getNumExpediente(), EstadoJustificante.Iniciado)) {
					result.setError(true);
					result.addMensajeALista("Existe un Justificante Profesional en proceso para el tramite: " + tramiteTransmision.getNumExpediente() + " por favor espere a la respuesta.");
				} else if (hayJustificante(null, tramiteTransmision.getNumExpediente(), EstadoJustificante.Pendiente_autorizacion_colegio)) {
					result.setError(true);
					result.addMensajeALista("Ya existe un Justificante Profesional generado para el trámite: " + tramiteTransmision.getNumExpediente()
							+ " que se encuentra en estado pendiente autorización del colegio. Por favor contacte con el colegio para que sea autorizado.");
				} else {
					result = validarDatosObligatorios(tramiteTransmision.getVehiculoDto(), tramiteTransmision.getAdquiriente(), tramiteTransmision.getJefaturaTraficoDto());
					if (!result.getError()) {
						result = validarNumeroPeticiones(tramiteTransmision.getNumColegiado(), tramiteTransmision.getVehiculoDto().getMatricula(), tramiteTransmision.getAdquiriente()
								.getNifInterviniente(), justificanteProfDto.getObservaciones());
						if (!result.getError()) {
							numPeticiones = (Integer) result.getAttachment(NUM_PETICIONES);
							if (tramiteTransmision.getNumExpediente() == null) {
								result = servicioTramiteTraficoTransmision.guardarTramite(tramiteTransmision, idUsuario, false, true, admin);
								numExpediente = (BigDecimal) result.getAttachment(ServicioTramiteTraficoTransmision.NUMEXPEDIENTE);
							} else {
								numExpediente = tramiteTransmision.getNumExpediente();
							}
							if (!result.getError()) {
								BigDecimal estado = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
								if (numPeticiones == 1 || numPeticiones == 2) {
									estado = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
									justificanteProfDto.setEstado(estado);
								}
								result = guardarJustificanteProfesional(justificanteProfDto, numExpediente, idUsuario, new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()), estado, null);
								if (!result.getError()) {
									Long idJustificanteInterno = (Long) result.getAttachment(ID_JUSTIFICANTE_INTERNO);
									result = descontarCreditos(numExpediente, idUsuario, tramiteTransmision.getIdContrato());
									if (!result.getError()) {
										if (numPeticiones == 1 || numPeticiones == 2) {
											result.addMensajeALista(
													"Ya ha solicitado este Justificante, si quiere volver a emitirlo póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
										} else {
											result = crearSolicitud(ProcesosEnum.EMISIONJPROF.toString(), idJustificanteInterno, idUsuario, tramiteTransmision.getIdContrato(), null);
											if (!result.getError()) {
												result.addMensajeALista("Solicitud de justificante enviada.");
											} else {
												servicioCredito.devolverCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), tramiteTransmision.getIdContrato(), 1, idUsuario,
														ConceptoCreditoFacturado.DEBB, numExpediente.toString());
												result.addMensajeALista("No se ha enviado la solicitud de justificante.");
											}
										}
										result.addAttachment(NUM_EXPEDIENTE, numExpediente);
										result.addAttachment(NUM_PETICIONES, numPeticiones);
										result.addAttachment(JUSTIFICANTE, justificanteProfDto);
									}
								} else {
									result.setError(true);
									result.addMensajeALista("Error al guardar el justificante profesional");
								}
							} else {
								result.setError(true);
								result.addMensajeALista("Error al guardar el trámite");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar el justificante profesional", e, tramiteTransmision.getNumExpediente().toString());
			result.setError(true);
			result.addMensajeALista("Error al guardar el justificante profesional");
		}
		if (result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean generarJustificanteDuplicado(TramiteTrafDuplicadoDto tramiteDuplicado, JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, boolean admin) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		int numPeticiones = 0;
		BigDecimal numExpediente = null;
		try {
			if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteDuplicado.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(
					tramiteDuplicado.getEstado())) {
				result.setError(true);
				result.addMensajeALista("El trámite de duplicado no debe estar Finalizado Telemáticamente");
			} else {
				if (hayJustificante(null, tramiteDuplicado.getNumExpediente(), EstadoJustificante.Iniciado)) {
					result.setError(true);
					result.addMensajeALista("Existe un Justificante Profesional en proceso para el tramite: " + tramiteDuplicado.getNumExpediente() + " por favor espere a la respuesta.");
				} else if (hayJustificante(null, tramiteDuplicado.getNumExpediente(), EstadoJustificante.Pendiente_autorizacion_colegio)) {
					result.setError(true);
					result.addMensajeALista("Ya existe un Justificante Profesional generado para el trámite: " + tramiteDuplicado.getNumExpediente()
							+ " que se encuentra en estado pendiente autorización del colegio. Por favor contacte con el colegio para que sea autorizado.");
				} else {
					result = validarDatosObligatorios(tramiteDuplicado.getVehiculoDto(), tramiteDuplicado.getTitular(), tramiteDuplicado.getJefaturaTraficoDto());
					if (!result.getError()) {
						result = validarNumeroPeticiones(tramiteDuplicado.getNumColegiado(), tramiteDuplicado.getVehiculoDto().getMatricula(), tramiteDuplicado.getTitular().getNifInterviniente(),
								justificanteProfDto.getObservaciones());
						if (!result.getError()) {
							numPeticiones = (Integer) result.getAttachment(NUM_PETICIONES);
							if (tramiteDuplicado.getNumExpediente() == null) {
								result = servicioTramiteTraficoDuplicado.guardarTramite(tramiteDuplicado, idUsuario, false, true, admin);
								numExpediente = (BigDecimal) result.getAttachment(ServicioTramiteTraficoTransmision.NUMEXPEDIENTE);
							} else {
								numExpediente = tramiteDuplicado.getNumExpediente();
							}
							if (!result.getError()) {
								BigDecimal estado = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
								if (numPeticiones == 2) {
									estado = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
									justificanteProfDto.setEstado(estado);
								}
								result = guardarJustificanteProfesional(justificanteProfDto, numExpediente, idUsuario, new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()), estado, null);
								if (!result.getError()) {
									result = descontarCreditos(numExpediente, idUsuario, tramiteDuplicado.getIdContrato());
									if (!result.getError()) {
										if (numPeticiones == 2) {
											result.addMensajeALista(
													"Tercera vez que solicita este Justificante, si quiere emitir este Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
										} else {
											result = crearSolicitud(ProcesosEnum.EMISIONJPROF.toString(), justificanteProfDto.getIdJustificanteInterno(), idUsuario, tramiteDuplicado.getIdContrato(),
													null);
											if (!result.getError()) {
												result.addMensajeALista("Solicitud de justificante enviada.");
											} else {
												servicioCredito.devolverCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), tramiteDuplicado.getIdContrato(), 1, idUsuario,
														ConceptoCreditoFacturado.DEBB, numExpediente.toString());
												result.addMensajeALista("No se ha enviado la solicitud de justificante.");
											}
										}
										result.addAttachment(NUM_EXPEDIENTE, numExpediente);
										result.addAttachment(NUM_PETICIONES, numPeticiones);
										result.addAttachment(JUSTIFICANTE, justificanteProfDto);
									}
								} else {
									result.setError(true);
									result.addMensajeALista("Error al guardar el justificante profesional");
								}
							} else {
								result.setError(true);
								result.addMensajeALista("Error al guardar el trámite");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar el justificante profesional", e, tramiteDuplicado.getNumExpediente().toString());
			result.setError(true);
			result.addMensajeALista("Error al guardar el justificante profesional");
		}
		if (result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean crearSolicitud(String nombreProceso, Long idJustificanteInterno, BigDecimal idUsuario, BigDecimal idContrato, String nombreXml) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		try {
			String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
			if ("SI".equals(generarJustifSega)) {
				// if(nombreProceso == null || ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum().equals(nombreProceso)){
				result = servicioCola.crearSolicitud(ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum(), nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
						TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idJustificanteInterno.toString(), idUsuario, null, idContrato);
				// } else {
				// result = servicioCola.crearSolicitud(nombreProceso, nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idJustificanteInterno
				// .toString(), idUsuario, null, idContrato);
				// }
			} else {
				result = servicioCola.crearSolicitud(nombreProceso, nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(),
						idJustificanteInterno.toString(), idUsuario, null, idContrato);
			}
			if (result.getError()) {
				result.setMensaje("Error al enviar la solicitud.");
			}
		} catch (Exception e) {
			log.error("Error al crear la solicitud del justificante profesional", e);
			result.setError(true);
			result.setMensaje("Error al crear la solicitud del justificante profesional");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean verificar(JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		try {
			result = guardarJustificanteProfesional(justificanteProfDto, null, idUsuario, new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()), new BigDecimal(EstadoJustificante.Pendiente_DGT
					.getValorEnum()), null);
			if (!result.getError()) {
				String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
				if ("SI".equals(generarJustifSega)) {
					Long idJustificanteInterno = (Long) result.getAttachment(ID_JUSTIFICANTE_INTERNO);
					result = servicioCola.crearSolicitud(ProcesosEnum.VERIFICACIONJPROFSEGA.toString(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
							TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idJustificanteInterno.toString(), idUsuario, null, idContrato);
					if (result.getError()) {
						result.setMensaje("Error al enviar la solicitud.");
					}
				} else {
					Long idJustificanteInterno = (Long) result.getAttachment(ID_JUSTIFICANTE_INTERNO);
					result = servicioCola.crearSolicitud(ProcesosEnum.VERIFICACIONJPROF.toString(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
							TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idJustificanteInterno.toString(), idUsuario, null, idContrato);
					if (result.getError()) {
						result.setMensaje("Error al enviar la solicitud.");
					}
				}
			} else {
				result.setMensaje("Error al guardar el jsutificante.");
			}
		} catch (Exception e) {
			log.error("Error al forzar el justificante profesional", e);
			result.setError(true);
			result.setMensaje("Error al forzar el justificante profesional");
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hayJustificante(Long idJustificanteInterno, BigDecimal numExpediente, EstadoJustificante estadoJustificante) {
		List<JustificanteProfVO> lista = justificanteProfDao.getJustificante(idJustificanteInterno, numExpediente, new BigDecimal(estadoJustificante.getValorEnum()));
		if (lista != null && lista.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> obtenerExpedientesSinJustificantesEnEstado(Long[] idsJustificantesInternos, EstadoJustificante estadoJustificantes) {
		List<String> listaIdSinJustificantes = new ArrayList<String>();
		if (idsJustificantesInternos != null) {
			for (Long idJustificanteInterno : idsJustificantesInternos) {
				if (!hayJustificante(idJustificanteInterno, null, estadoJustificantes)) {
					listaIdSinJustificantes.add(idJustificanteInterno.toString());
				}
			}
		}
		return listaIdSinJustificantes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> obtenerExpedientesSinJustificantesEnEstadoPorNumExpediente(String[] numExpedientes, EstadoJustificante estadoJustificantes) {
		List<String> listaIdSinJustificantes = new ArrayList<String>();
		if (numExpedientes != null) {
			for (String numExpediente : numExpedientes) {
				if (!hayJustificante(null, new BigDecimal(numExpediente), estadoJustificantes)) {
					listaIdSinJustificantes.add(numExpediente);
				}
			}
		}
		return listaIdSinJustificantes;
	}

	@Override
	@Transactional
	public ResultBean guardarJustificanteProfesional(JustificanteProfDto justificanteProfDto, BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoAnterior, BigDecimal estado,
			String comentariosEvolucion) {
		ResultBean resultado = new ResultBean();
		try {
			justificanteProfDto.setNumExpediente(numExpediente);
			justificanteProfDto.setEstado(estado);
			JustificanteProfVO justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
			if (justificanteProfDto.getIdJustificanteInterno() != null) {
				justificanteProfDao.actualizar(justificanteProfVO);
			} else {
				Long idJustificanteInterno = (Long) justificanteProfDao.guardar(justificanteProfVO);
				justificanteProfDto.setIdJustificanteInterno(idJustificanteInterno);
			}
			guardarEvolucionJustificanteProfesional(justificanteProfDto.getIdJustificanteInterno(), numExpediente, estadoAnterior, estado, idUsuario, comentariosEvolucion);
			resultado.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfDto.getIdJustificanteInterno());
		} catch (Exception e) {
			log.error("Error al guardar el justificante profesional: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardar el justificante profesional");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarJustificanteProfesional(JustificanteProfVO justificanteProfVO, BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoAnterior, BigDecimal estado,
			String comentariosEvolucion) {
		ResultBean resultado = new ResultBean();
		try {
			justificanteProfVO.setNumExpediente(numExpediente);
			justificanteProfVO.setEstado(estado);
			if (justificanteProfVO.getIdJustificanteInterno() != null) {
				justificanteProfDao.actualizar(justificanteProfVO);
			} else {
				Long idJustificanteInterno = (Long) justificanteProfDao.guardar(justificanteProfVO);
				justificanteProfVO.setIdJustificanteInterno(idJustificanteInterno);
			}
			guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), numExpediente, estadoAnterior, estado, idUsuario, comentariosEvolucion);
			resultado.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfVO.getIdJustificanteInterno());
		} catch (Exception e) {
			log.error("Error al guardar el justificante profesional: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el justificante profesional");
		}
		return resultado;
	}

	private ResultBean descontarCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultBean result = new ResultBean();
		try {
			result = servicioCredito.validarCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idContrato, BigDecimal.ONE);
			if (!result.getError()) {
				result = servicioCredito.descontarCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), idContrato, BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.DSJP,
						numExpediente.toString());
			}
		} catch (Exception e) {
			log.error("Error al descontar los créditos.", e, numExpediente.toString());
			result.setError(true);
			result.addMensajeALista("Error al descontar los créditos.");
		}
		return result;

	}

	private void guardarEvolucionJustificanteProfesional(Long idJustificanteInterno, BigDecimal numExpediente, BigDecimal estadoAnterior, BigDecimal estado, BigDecimal idUsuario, String comentarios) {
		if (numExpediente != null) {
			EvolucionJustifProfesionalesVO evolucionJustificanteProfVO = new EvolucionJustifProfesionalesVO();

			EvolucionJustifProfesionalesPK id = new EvolucionJustifProfesionalesPK();
			id.setIdJustificanteInterno(idJustificanteInterno);
			id.setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			id.setNumExpediente(numExpediente);
			id.setEstadoAnterior(estadoAnterior);
			id.setEstado(estado);

			evolucionJustificanteProfVO.setId(id);

			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionJustificanteProfVO.setUsuario(usuario);
			evolucionJustificanteProfVO.setComentarios(comentarios);

			servicioEvolucionJustificanteProfesional.guardar(evolucionJustificanteProfVO);
		}
	}

	private ResultBean validarNumeroPeticiones(String numColegiado, String matricula, String nif, String observaciones) {
		ResultBean result = new ResultBean();
		int numVecesPeticiones = 0;
		List<JustificanteProfVO> listaJustificante = getJustificantes(numColegiado, matricula, nif);

		if (listaJustificante != null) {
			for (JustificanteProfVO justificante : listaJustificante) {
				if (justificante.getEstado() != null) {
					if (EstadoJustificante.Pendiente_DGT.getValorEnum().equals(justificante.getEstado().toString()) || EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum().equals(
							justificante.getEstado().toString()) || EstadoJustificante.Autorizado_icogam.getValorEnum().equals(justificante.getEstado().toString())) {
						result.setError(true);
						result.addMensajeALista("Existe alguna petición encolada de este justificante profesional");
						return result;
					} else if (EstadoJustificante.Ok.getValorEnum().equals(justificante.getEstado().toString())) {
						numVecesPeticiones++;
					}
				}
			}
		}
		String esJustProfNumPeticiones = gestorPropiedades.valorPropertie("justificantes.nuevos.varias.peticiones");
		if ("SI".equalsIgnoreCase(esJustProfNumPeticiones)) {
			if (numVecesPeticiones == 1) {
				if (observaciones == null || observaciones.isEmpty()) {
					result.setError(true);
					result.addMensajeALista("Para solicitar por segunda vez un justificante deberá rellenar el campo observaciones.");
				}
			} else if (numVecesPeticiones == 2) {
				if (observaciones == null || observaciones.isEmpty()) {
					result.setError(true);
					result.addMensajeALista("Para solicitar por tercera vez un justificante deberá rellenar el campo observaciones.");
				}
			} else if (numVecesPeticiones > 2) {
				result.setError(true);
				result.addMensajeALista("El Colegio le recuerda que sólo se puede solicitar tres justificantes para un mismo vehículo y titular.");
			}
		}

		result.addAttachment(NUM_PETICIONES, numVecesPeticiones);

		return result;
	}

	@Override
	public ResultBean validarDatosObligatorios(VehiculoDto vehiculo, IntervinienteTraficoDto titular, JefaturaTraficoDto jefatura) {
		ResultBean result = new ResultBean();

		if (vehiculo != null) {
			if (vehiculo.getCodigoMarca() == null || vehiculo.getCodigoMarca().isEmpty() || "-1".equals(vehiculo.getCodigoMarca())) {
				result.setError(true);
				result.addMensajeALista("- Marca obligatoria.");
			}

			if (vehiculo.getMatricula() == null || vehiculo.getMatricula().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("- Matricula obligatoria.");
			}

			if (vehiculo.getBastidor() == null || vehiculo.getBastidor().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("- Bastidor obligatorio.");
			}

			if (vehiculo.getModelo() == null || vehiculo.getModelo().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("- Modelo obligatorio.");
			} else if (vehiculo.getModelo().length() > 22) {
				result.setError(true);
				result.addMensajeALista("- El modelo del vehículo no puede exceder de 22 carácteres.");
			}

			if (vehiculo.getTipoVehiculo() == null || "-1".equals(vehiculo.getTipoVehiculo()) || vehiculo.getTipoVehiculo().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("- Tipo de vehículo obligatorio.");
			}
		} else {
			result.setError(true);
			result.addMensajeALista("- El vehículo es obligatorio.");
		}

		if (jefatura == null || jefatura.getJefaturaProvincial() == null || jefatura.getJefaturaProvincial().isEmpty()) {
			result.setError(true);
			result.addMensajeALista("- La jefatura es obligatoria.");
		}

		if (titular != null) {
			if (titular.getPersona() != null) {
				if (titular.getPersona().getNif() == null || titular.getPersona().getNif().isEmpty()) {
					result.setError(true);
					result.addMensajeALista("- NIF obligatorio.");
				}
				if (titular.getPersona().getApellido1RazonSocial() == null || titular.getPersona().getApellido1RazonSocial().isEmpty()) {
					result.setError(true);
					result.addMensajeALista("- Nombre/Apellido o Razón Social obligatorio.");
				} else if (TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona()) && titular.getPersona().getApellido1RazonSocial().length() > 26) {
					result.setError(true);
					result.addMensajeALista("- El primer apellido de la persona no puede exceder de 26 carácteres.");
				} else if (TipoPersona.Juridica.getValorEnum().equals(titular.getPersona().getTipoPersona()) && titular.getPersona().getApellido1RazonSocial().length() > 70) {
					result.setError(true);
					result.addMensajeALista("- La razón social no puede exceder de 70 carácteres.");
				}
				if (titular.getPersona().getNombre() != null && titular.getPersona().getNombre().length() > 18) {
					result.setError(true);
					result.addMensajeALista("- El nombre de la persona no puede exceder de 18 carácteres.");
				}
				if (titular.getPersona().getApellido2() != null && titular.getPersona().getApellido2().length() > 26) {
					result.setError(true);
					result.addMensajeALista("- El segundo apellido de la persona no puede exceder de 26 carácteres.");
				}
			} else {
				result.setError(true);
				result.addMensajeALista("- NIF y Nombre/Apellido o Razón Socia obligatorios.");
			}

			if (titular.getDireccion() != null) {
				if (titular.getDireccion().getNombreVia() == null || titular.getDireccion().getNombreVia().isEmpty()) {
					result.setError(true);
					result.addMensajeALista("- Nombre de vía obligatoria.");
				}
				if (titular.getDireccion().getNumero() == null || titular.getDireccion().getNumero().isEmpty()) {
					result.setError(true);
					result.addMensajeALista("- Número obligatorio.");
				}
			} else {
				result.setError(true);
				result.addMensajeALista("- Nombre de vía y Número obligatorios.");
			}
		} else {
			result.setError(true);
			result.addMensajeALista("- El titular es obligatorio.");
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JustificanteProfVO> getJustificantes(String numColegiado, String matricula, String nif) {
		try {
			return justificanteProfDao.getJustificantes(numColegiado, matricula, nif);
		} catch (Exception e) {
			log.error("Error al recuperar la lista de jsutificantes.", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public JustificanteProfDto getJustificanteProfesional(Long idJustificanteInterno) {
		if (idJustificanteInterno != null) {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			if (justificanteProfVO != null) {
				return conversor.transform(justificanteProfVO, JustificanteProfDto.class);
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public JustificanteProfVO getJustificanteProfesionalPorIDInternoVO(Long idJustificanteInterno) {
		try {
			if (idJustificanteInterno != null) {
				return justificanteProfDao.getJustificanteProfPorIdInterno(idJustificanteInterno);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el justificanteProfVO por su idInterno, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public JustificanteProfVO getJustificanteProfesionalPorCodigoVerificacion(String codigoVerificacion) {

		return justificanteProfDao.getJustificanteCodigoVerificacion(codigoVerificacion);

	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getJustificanteProfesionalPantalla(Long idJustificanteInterno) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idJustificanteInterno != null) {
				JustificanteProfDto justificanteProfDto = getJustificanteProfesional(idJustificanteInterno);
				if (justificanteProfDto != null) {
					if (justificanteProfDto.getTramiteTrafico() != null) {
						String tipoInterviniete = null;
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
							tipoInterviniete = TipoInterviniente.Adquiriente.getValorEnum();
						} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
							tipoInterviniete = TipoInterviniente.Titular.getValorEnum();
						}
						IntervinienteTraficoDto titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(justificanteProfDto.getTramiteTrafico().getNumExpediente(), tipoInterviniete, null,
								justificanteProfDto.getTramiteTrafico().getNumColegiado());
						if (titular != null) {
							resultado.addAttachment(TITULAR_JUST_PROF, titular);
							resultado.addAttachment(DTO_JUSTIFICANTE, justificanteProfDto);
						} else {
							resultado.setError(true);
							resultado.setMensaje("No se han encontrado datos del titular del justificante.");
						}
					} else {
						resultado.setError(true);
						resultado.setMensaje("No se han encontrado datos del tramite asociado.");
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("No existen datos para ese justificante.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe de indicar un justificante para poder obtener su detalle.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el justificante de pantalla, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el justificante de pantalla.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public JustificanteProfDto getJustificanteProfesionalPorNumExpediente(BigDecimal numExpediente, BigDecimal estadoJustificante) {
		JustificanteProfDto dto = null;
		List<JustificanteProfVO> lista = justificanteProfDao.getJustificante(null, numExpediente, estadoJustificante);
		if (lista != null && !lista.isEmpty()) {
			dto = conversor.transform(lista.get(0), JustificanteProfDto.class);
		}
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JustificanteProfVO> obtenerJustificantesNoFinalizados(Date fechaHoy, Date fechaMenosDiezDias) {
		List<JustificanteProfVO> lista = justificanteProfDao.obtenerJustificantesNoFinalizados(fechaHoy, fechaMenosDiezDias);
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ResultBean executeEmailJustificantesNoFinalizados() {
		ResultBean result = new ResultBean(false);
		try {
			Date fechaHoy = Calendar.getInstance().getTime();
			Date fechaMenosDiezDias = utilesFecha.restarDias(fechaHoy, -10, new Integer(0), new Integer(0), new Integer(0));
			List<JustificanteProfVO> lista = obtenerJustificantesNoFinalizados(fechaHoy, fechaMenosDiezDias);
			if (lista != null && !lista.isEmpty()) {
				result = enviarCorreoJustificantesNoFinalizados(lista, fechaHoy, fechaMenosDiezDias);
			}
		} catch (Exception e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			result.setError(true);
		}
		return result;
	}

	private ResultBean enviarCorreoJustificantesNoFinalizados(List<JustificanteProfVO> lista, Date fechaHoy, Date fechaMenosDiezDias) {
		String subject = null;
		String recipient = null;
		ResultBean resultado = null;
		StringBuffer texto = null;
		FicheroBean ficheroBean = new FicheroBean();

		try {
			subject = gestorPropiedades.valorPropertie(SUBJECT);
			recipient = gestorPropiedades.valorPropertie(RECIPIENT);

			texto = crearTablaJustificanteNoFinalizados(lista, fechaHoy, fechaMenosDiezDias);

			resultado = generarExcel(lista, fechaHoy, fechaMenosDiezDias);
			if (resultado != null && !resultado.getError()) {
				File fichero = (File) resultado.getAttachment("fichero");
				String nombreFichero = (String) resultado.getAttachment("nombreFichero");
				ficheroBean.setFichero(fichero);
				ficheroBean.setNombreDocumento(nombreFichero);
			}

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, null, null, ficheroBean);
			if (resultado.getError()) {
				log.error("No se enviaron correos de justificantes no finalizados");
				for (String textoError : resultado.getListaMensajes()) {
					log.error(textoError);
				}
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de justificantes no finalizados", e);
		}
		return resultado;
	}

	private ResultBean generarExcel(List<JustificanteProfVO> lista, Date fechaHoy, Date fechaMenosDiezDias) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();
		// --------------------------------------------------------------------------------

		nombreFichero = TipoImpreso.JustificantesNoFinalizadosExcel.getNombreEnum() + "_ICOGAM_" + utilesFecha.formatoFecha("ddMMyyyy", fechaHoy);

		fichero.setExtension(".xls");
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
		fichero.setSubTipo(ConstantesGestorFicheros.EXCEL_JUSTIFICANTE_NO_FIN);
		fichero.setFecha(utilesFecha.getFechaFracionada(fechaHoy));
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);

			sheet = copyWorkbook.createSheet(JUSTIF_NO_FINALIZADOS, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i <= COL_DESCRIPCION; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;
			try {
				label = new Label(COL_NEXPEDIENTE, 0, NEXPEDIENTE, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NIDENJUSTIFICANTE, 0, NIDENJUSTIFICANTE, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NNUMJUSTIFICANTES, 0, NNUMJUSTIFICANTES, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NMATRICULA, 0, NMATRICULA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NGESTOR, 0, NGESTOR, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NDNITITULAR, 0, NDNITITULAR, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NFECHACREACION, 0, NFECHACREACION, formatoCabecera);
				sheet.addCell(label);

				Integer i = 1;
				for (JustificanteProfVO justificante : lista) {
					try {
						String dniAdquiriente = obtenerDniAdquiriente(justificante);
						String matricula = "";
						String numColegiado = "";
						if (justificante.getTramiteTrafico() != null) {
							numColegiado = justificante.getTramiteTrafico().getNumColegiado();
							if (justificante.getTramiteTrafico().getVehiculo() != null) {
								matricula = justificante.getTramiteTrafico().getVehiculo().getMatricula();
							}
						}

						label = new Label(COL_NEXPEDIENTE, i, justificante.getNumExpediente().toString(), formatoDatos);
						sheet.addCell(label);

						label = new Label(COL_NIDENJUSTIFICANTE, i, justificante.getIdJustificanteInterno().toString(), formatoDatos);
						sheet.addCell(label);

						// TODO: cantidad: hacer consulta por numColegiado, titular, matricula, tipotramite, fecha justificante
						Integer numTramites = justificanteProfDao.numeroTramitesJustificantes(fechaHoy, fechaMenosDiezDias, matricula, numColegiado, dniAdquiriente);
						if (numTramites != null) {
							label = new Label(COL_NNUMJUSTIFICANTES, i, numTramites.toString(), formatoDatos);
						} else {
							label = new Label(COL_NNUMJUSTIFICANTES, i, "", formatoDatos);
						}
						sheet.addCell(label);

						label = new Label(COL_NMATRICULA, i, matricula, formatoDatos);
						sheet.addCell(label);

						label = new Label(COL_NGESTOR, i, numColegiado, formatoDatos);
						sheet.addCell(label);

						label = new Label(COL_NDNITITULAR, i, dniAdquiriente, formatoDatos);
						sheet.addCell(label);

						if (justificante.getFechaInicio() != null) {
							label = new Label(COL_NFECHACREACION, i, utilesFecha.formatoFecha(justificante.getFechaInicio()), formatoDatos);
						} else {
							label = new Label(COL_NFECHACREACION, i, "", formatoDatos);
						}
						sheet.addCell(label);

						i++;
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de adjuntar la excel de los justificantes no finalizados", e.getMessage());
						resultado.setError(true);
						resultado.setMensaje("Ha sucedido un error a la hora de adjuntar la excel de los justificantes no finalizados");
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
				resultado.setError(true);
				resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
			} catch (WriteException e) {
				log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
				resultado.setError(true);
				resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
			}
			copyWorkbook.write();
		} catch (IOException e) {
			log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
		} catch (Exception e) {
			log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
				} catch (IOException e) {
					log.error("Error al adjuntar la excel de los justificantes no finalizados", e.getMessage());
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al adjuntar la excel de los justificantes no finalizados");
				}
			}
		}

		if (!resultado.getError()) {
			resultado.addAttachment("fichero", archivo);
			resultado.addAttachment("nombreFichero", nombreFichero + ".xls");
		}

		return resultado;
	}

	private StringBuffer crearTablaJustificanteNoFinalizados(List<JustificanteProfVO> lista, Date fechaHoy, Date fechaMenosDiezDias) {
		StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
		texto.append("Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos ");
		texto.append("que los siguientes trámites y vehículos tienen un justificante ");
		texto.append("creado con 10 días de antigüedad. ").append("<BR>");

		/*
		 * texto.append("<BR>"); texto.append("<table border=\"1\">"); texto.append("<thead>"); texto.append("<tr bgcolor=\"#a52642\" >"); texto.append("<th><font color=\"#ffffff\" >Número Expediente</font></th>"); texto.append("<th><font color=\"#ffffff\" >Identificador Justificante</font></th>");
		 * texto.append("<th><font color=\"#ffffff\" >Número de justificantes</font></th>"); texto.append("<th><font color=\"#ffffff\" >Matrícula</font></th>"); texto.append("<th><font color=\"#ffffff\" >Gestor</font></th>"); texto.append("<th><font color=\"#ffffff\" >DNI Titular</font></th>");
		 * texto.append("<th><font color=\"#ffffff\" >Fecha Creación Justificante</font></th>"); texto.append("</tr>"); texto.append("</thead>"); for (JustificanteProfVO justificante : lista) { String dniAdquiriente = obtenerDniAdquiriente(justificante); String matricula = ""; String numColegiado =
		 * ""; if (justificante.getTramiteTrafico() != null) { numColegiado = justificante.getTramiteTrafico().getNumColegiado(); if (justificante.getTramiteTrafico().getVehiculo() != null) { matricula = justificante.getTramiteTrafico().getVehiculo().getMatricula(); } } texto.append("<tr>");
		 * texto.append("<font color=\"#000000\" >"); texto.append("<td align=\"center\">" + justificante.getNumExpediente() + "</td>"); texto.append("<td align=\"center\">" + justificante.getIdJustificanteInterno() + "</td>"); // TODO: cantidad: hacer consulta por numColegiado, titular, matricula,
		 * tipotramite, fecha justificante Integer numTramites = justificanteProfDao.numeroTramitesJustificantes(fechaHoy, fechaMenosDiezDias, matricula, numColegiado, dniAdquiriente); if (numTramites != null) { texto.append("<td align=\"center\">" + numTramites.toString() + "</td>"); } else {
		 * texto.append("<td align=\"center\"></td>"); } texto.append("<td align=\"center\">" + matricula + "</td>"); texto.append("<td align=\"center\">" + numColegiado + "</td>"); texto.append("<td align=\"center\">" + dniAdquiriente + "</td>"); if (justificante.getFechaInicio() != null) {
		 * texto.append("<td align=\"center\">" + utilesFecha.formatoFecha(justificante.getFechaInicio()) + "</td>"); } else { texto.append("<td align=\"center\"></td>"); } texto.append("</font>"); texto.append("</tr>"); } texto.append("</table>");
		 */
		texto.append("<BR>");

		return texto;
	}

	private String obtenerDniAdquiriente(JustificanteProfVO justificante) {
		String dniAdquiriente = "";
		if (justificante.getTramiteTrafico() != null && justificante.getTramiteTrafico().getIntervinienteTraficos() != null) {
			for (IntervinienteTraficoVO interviniente : justificante.getTramiteTrafico().getIntervinienteTraficosAsList()) {
				if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())) {
					dniAdquiriente = interviniente.getId().getNif();
				}
			}
		}
		return dniAdquiriente;
	}

	@Override
	@Transactional
	public IntervinienteTraficoDto getTitularAdquiriente(TramiteTrafDto tramiteTrafico) {
		IntervinienteTraficoDto titular = null;
		for (IntervinienteTraficoDto interviniente : tramiteTrafico.getIntervinienteTraficos()) {
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().toString().equals(tramiteTrafico.getTipoTramite()) && TipoInterviniente.Adquiriente.toString().equals(interviniente
					.getTipoInterviniente())) {
				titular = interviniente;
				break;
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().toString().equals(tramiteTrafico.getTipoTramite()) && TipoInterviniente.Titular.toString().equals(interviniente
					.getTipoInterviniente())) {
				titular = interviniente;
				break;
			} else if (TipoTramiteTrafico.Transmision.getValorEnum().toString().equals(tramiteTrafico.getTipoTramite()) && TipoInterviniente.Adquiriente.toString().equals(interviniente
					.getTipoInterviniente())) {
				titular = interviniente;
				break;
			}
		}
		return titular;
	}

	@Override
	@Transactional
	// Mantis 20494. David Sierra: Se modifica el justificante a estado anulado
	public ResultBean cambiarEstadoAnularJustificante(JustificanteProfDto justificanteProfDto, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean();
		try {
			if (numExpediente != null) {
				justificanteProfDto.setNumExpediente(numExpediente);
			}
			justificanteProfDto.setEstado(new BigDecimal(EstadoJustificante.Anulado.getValorEnum()));
			JustificanteProfVO justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
			if (justificanteProfDto.getIdJustificanteInterno() != null) {
				justificanteProfDao.actualizar(justificanteProfVO);
			} else {
				Long idJustificanteInterno = (Long) justificanteProfDao.guardar(justificanteProfVO);
				justificanteProfDto.setIdJustificanteInterno(idJustificanteInterno);
			}
			resultado.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfDto.getIdJustificanteInterno());
		} catch (Exception e) {
			log.error("Error al guardar el justificante profesional: " + e.getMessage(), e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardar el justificante profesional");
		}
		return resultado;
	}

	@Override
	public ByteArrayInputStreamBean imprimirJustificantes(Long[] listaIdJustificantesInternos) {
		return servicioJustificanteProfesionalImprimir.imprimirJustificantes(listaIdJustificantesInternos);
	}

	@Override
	public ByteArrayInputStreamBean imprimirJustificantesPorNumExpediente(String[] listaNumExpediente) {
		return servicioJustificanteProfesionalImprimir.imprimirJustificantesNumExpedientes(listaNumExpediente);
	}

	@Override
	@Transactional
	public ResultBean pendienteAutorizacionColegioJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esPteConsulta) {
		ResultBean resultBean = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			if (justificanteProfVO != null) {
				if (!EstadoJustificante.Finalizado_Con_Error.getValorEnum().equals(justificanteProfVO.getEstado().toString()) && !EstadoJustificante.Iniciado.getValorEnum().equals(justificanteProfVO
						.getEstado().toString())) {
					resultBean.setError(true);
					resultBean.setMensaje("No se pudo cambiar el estado del justificante del expediente " + justificanteProfVO.getNumExpediente() + " a 'Pendiente autorización colegio' "
							+ " asegúrese de que está 'Iniciado' o 'Finalizado con Error'.");
				} else {
					BigDecimal estadoAnt = justificanteProfVO.getEstado();
					BigDecimal estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
					justificanteProfVO.setEstado(estadoNuevo);
					justificanteProfDao.actualizar(justificanteProfVO);
					guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, estadoNuevo, idUsuario, null);
					resultBean.setMensaje("El justificante: " + justificanteProfVO.getNumExpediente() + " se encuentra en estado pendiente autorización del colegio.");
					resultBean.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfVO.getIdJustificanteInterno());
				}
			} else {
				if (esPteConsulta) {
					resultBean.setMensaje("Unos de los justificantes seleccionados no se encuentra dado de alta en la base de datos");
				} else {
					resultBean.setMensaje("No se encuentran datos del justificante profesional.");
				}
				resultBean.setError(true);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error no controlado a la hora de modificar el justificante profesional, error: ", e);
			resultBean.setError(true);
			resultBean.setMensaje("Ha sucedido un error no controlado a la hora de modificar el justificante profesional.");
		}
		if (resultBean != null && resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean anularJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esAnularConsulta) {
		ResultBean resultBean = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			if (justificanteProfVO != null) {
				if (!EstadoJustificante.Iniciado.getValorEnum().equals(justificanteProfVO.getEstado().toString()) && !EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum().equals(
						justificanteProfVO.getEstado().toString())) {
					resultBean.setError(true);
					resultBean.setMensaje("No se pudo anular el justificante del expediente " + justificanteProfVO.getNumExpediente()
							+ " porque no se encuentra en 'Iniciado' o 'Pendiente autorizacion colegio'");
				} else {
					BigDecimal estadoAnt = justificanteProfVO.getEstado();
					BigDecimal estadoNuevo = new BigDecimal(EstadoJustificante.Anulado.getValorEnum());
					justificanteProfVO.setEstado(estadoNuevo);
					justificanteProfDao.actualizar(justificanteProfVO);
					guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, estadoNuevo, idUsuario, null);
					resultBean.setMensaje("El justificante: " + justificanteProfVO.getNumExpediente() + " se ha anulado correctamente.");
					resultBean.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfVO.getIdJustificanteInterno());
				}
			} else {
				if (esAnularConsulta) {
					resultBean.setMensaje("Unos de los justificantes seleccionados no se encuentra dado de alta en la base de datos");
				} else {
					resultBean.setMensaje("No se encuentran datos del justificante profesional.");
				}
				resultBean.setError(true);
			}
		} catch (Exception e) {
			log.error("Error al recuperar un justificante", e);
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean forzarJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esForzadoConsulta) {
		ResultBean resultBean = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			if (justificanteProfVO != null) {
				resultBean.addAttachment(TIPO_TRAMITE_JUSTIFICANTE, justificanteProfVO.getTramiteTrafico().getTipoTramite());
				if (!EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum().equals(justificanteProfVO.getEstado().toString())) {
					resultBean.setError(true);
					resultBean.setMensaje("El justificante del expediente " + justificanteProfVO.getNumExpediente() + " debe estar en Pendiente de Autorización del Colegio");
				} else {
					String tipoInterviniete = null;
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(justificanteProfVO.getTramiteTrafico().getTipoTramite())) {
						tipoInterviniete = TipoInterviniente.Adquiriente.getValorEnum();
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(justificanteProfVO.getTramiteTrafico().getTipoTramite())) {
						tipoInterviniete = TipoInterviniente.Titular.getValorEnum();
					}
					IntervinienteTraficoDto titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(justificanteProfVO.getTramiteTrafico().getNumExpediente(), tipoInterviniete, null,
							justificanteProfVO.getTramiteTrafico().getNumColegiado());
					if (titular != null) {
						BigDecimal estadoAnt = justificanteProfVO.getEstado();
						BigDecimal estadoNuevo = new BigDecimal(EstadoJustificante.Autorizado_icogam.getValorEnum());
						justificanteProfVO.setEstado(estadoNuevo);
						justificanteProfDao.actualizar(justificanteProfVO);
						guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, estadoNuevo, idUsuario, null);
						ResultBean resultSolicitud = crearSolicitudJP(justificanteProfVO, titular, idUsuario);
						if (resultSolicitud.getError()) {
							resultBean.setError(true);
							resultBean.setMensaje("El justificante " + justificanteProfVO.getNumExpediente() + " ha fallado por el siguiente motivo: " + obtenerMensajeResultado(resultSolicitud));
						} else {
							resultBean.setMensaje("Solicitud de justificante enviada: " + justificanteProfVO.getNumExpediente());
							resultBean.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfVO.getIdJustificanteInterno());
						}
					} else {
						resultBean.setError(true);
						resultBean.addMensajeALista("El justificante " + justificanteProfVO.getNumExpediente()
								+ " ha fallado por el siguiente motivo: no se encuentran datos del titular del tramite.");
					}
				}
			} else {
				if (esForzadoConsulta) {
					resultBean.setMensaje("Unos de los justificantes seleccionados no se encuentra dado de alta en la base de datos");
				} else {
					resultBean.setMensaje("No se encuentran datos del justificante profesional.");
				}
				resultBean.setError(true);
			}
		} catch (Exception e) {
			resultBean.setError(true);
			resultBean.setMensaje("Error al forzar el envío del justificantes");
			log.error("Error al forzar el envío de los justificantes, error: ", e);
		}
		if (resultBean != null && resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultBean;
	}

	private String obtenerMensajeResultado(ResultBean resultBean) {
		String mensaje = "";
		if (resultBean != null && !resultBean.getListaMensajes().isEmpty()) {
			for (String mensajeResult : resultBean.getListaMensajes()) {
				mensaje += " - " + mensajeResult;
			}
		}
		return mensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean imprimirJP(Long idJustificanteInterno) {
		ResultBean resultBean = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			ResultBean resultVal = validarJustificanteImpr(justificanteProfVO);
			if (!resultVal.getError()) {
				String nombre = generarNombre(justificanteProfVO.getNumExpediente(), justificanteProfVO.getIdJustificante());
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA, Utilidades
						.transformExpedienteFecha(justificanteProfVO.getNumExpediente()), nombre, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResultBean != null && fileResultBean.getFile() != null) {
					resultBean.addAttachment(NOMBRE_FICHERO_JP, nombre + ConstantesGestorFicheros.EXTENSION_PDF);
					resultBean.addAttachment(FICHERO_JP, fileResultBean.getFile());
					resultBean.setError(false);
					resultBean.setMensaje("Justificante para imprimir con el numero de expediente : " + justificanteProfVO.getNumExpediente());
				} else {
					resultBean.setError(true);
					resultBean.setMensaje("No existe ningún justificante para imprimir con el numero de expediente : " + justificanteProfVO.getNumExpediente());
				}
			} else {
				return resultVal;
			}
		} catch (Throwable e) {
			resultBean.setError(true);
			resultBean.setMensaje("Error al imprimir el jusficante profesiona.");
			log.error("Error al imprimir el jusficante profesiona, error: ", e);
		}
		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean imprimirConsultaJP(Long idJustificanteInterno) {
		ResultBean resultBean = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			ResultBean resultVal = validarJustificanteImpr(justificanteProfVO);
			if (!resultVal.getError()) {
				String nombre = generarNombre(justificanteProfVO.getNumExpediente(), justificanteProfVO.getIdJustificante());
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA, Utilidades
						.transformExpedienteFecha(justificanteProfVO.getNumExpediente()), nombre, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResultBean != null && fileResultBean.getFile() != null) {
					resultBean.addAttachment(NOMBRE_FICHERO_JP, nombre);
					resultBean.setError(false);
					resultBean.setMensaje("Justificante para imprimir con el numero de expediente : " + justificanteProfVO.getNumExpediente());
				} else {
					resultBean.setError(true);
					resultBean.setMensaje("No existe ningún justificante para imprimir con el numero de expediente : " + justificanteProfVO.getNumExpediente());
				}
			} else {
				return resultVal;
			}
		} catch (Throwable e) {
			resultBean.setError(true);
			resultBean.setMensaje("Error al imprimir el jusficante profesiona.");
			log.error("Error al imprimir el jusficante profesiona, error: ", e);
		}
		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoConsultaJustProfBean descargaJP(String nombreJustificantes) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		try {
			String[] datosJustificante = nombreJustificantes.split("_");
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIdJustificante(new BigDecimal(datosJustificante[1]));
			ResultBean resultVal = validarJustificanteImpr(justificanteProfVO);
			if (!resultVal.getError()) {
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA, Utilidades
						.transformExpedienteFecha(justificanteProfVO.getNumExpediente()), nombreJustificantes, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResultBean != null && fileResultBean.getFile() != null) {
					resultado.setFichero(fileResultBean.getFile());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningún justificante para imprimir con el numero de expediente : " + justificanteProfVO.getNumExpediente());
				}
			}
		} catch (Throwable e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al imprimir el jusficante profesional.");
			log.error("Error al imprimir el jusficante profesional, error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public JustificanteProfVO getJustificanteProfesionalPorIdJustificante(BigDecimal idJustificante) {
		try {
			if (idJustificante != null) {
				return justificanteProfDao.getJustificanteProfPorIdJustificante(idJustificante);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el justificanteProfVO por su id, error: ", e);
		}
		return null;
	}

	public String generarNombre(BigDecimal numExpediente, BigDecimal numJustificante) {
		String nombre = "";
		if (null != numJustificante) {
			String anadido = "0000000000";
			String id = numJustificante.toString();
			nombre = anadido.substring(0, anadido.length() - id.length()) + id;
		} else {
			nombre = numExpediente.toString();
		}

		return numExpediente.toString() + "_" + nombre;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean validarGenracionJPDesdeTransmisionODuplicado(JustificanteProfDto justificanteProf, IntervinienteTraficoDto titular, Boolean checkFuerzasArmadas) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = validarGuardarJP(justificanteProf, titular, checkFuerzasArmadas);
			if (!resultado.getError()) {
				resultado = validarGenerarJP(justificanteProf, titular, justificanteProf.getFechaInicio());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el justificante profesional, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el justificante profesional.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean crearJustificanteDesdeTransmisionODuplicado(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, Boolean esForzar, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
			if (justificanteProfVO.getTramiteTrafico().getContrato() == null) {
				justificanteProfVO.getTramiteTrafico().setContrato(new ContratoVO());
				justificanteProfVO.getTramiteTrafico().getContrato().setIdContrato(justificanteProfDto.getTramiteTrafico().getIdContrato().longValue());
			}
			justificanteProfDao.guardarOActualizar(justificanteProfVO);
			guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), BigDecimal.ZERO, new BigDecimal(EstadoJustificante.Iniciado
					.getValorEnum()), idUsuario, null);
			BigDecimal estadoNuevo = null;
			if (esForzar) {
				estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
				justificanteProfVO.setEstado(estadoNuevo);
				justificanteProfDao.guardarOActualizar(justificanteProfVO);
				resultado.setMensaje("Justificante con expediente: " + justificanteProfVO.getNumExpediente() + " Pendiente Autorizacion del Colegio, " + JUSTIFICANTE_REPETIDO);
			} else {
				estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
				justificanteProfDao.guardarOActualizar(justificanteProfVO);
				ResultBean resultCrearSolicitud = crearSolicitudJP(justificanteProfVO, titular, idUsuario);
				if (resultCrearSolicitud.getError()) {
					resultado = resultCrearSolicitud;
				} else {
					resultado.setMensaje("Solicitud de justificante generada.");
				}
			}
			guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()),
					estadoNuevo, idUsuario, null);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el justificante profesional, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar el justificante profesional.");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean generarJP(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, boolean checkIdFuerzasArmadas, BigDecimal idUsuario, Boolean esAdmin) {
		ResultBean resultado = new ResultBean(false);
		try {
			Date fechaInicio = new Date();
			ResultBean resultValidarGJP = validarGuardarJP(justificanteProfDto, titular, checkIdFuerzasArmadas);
			if (!resultValidarGJP.getError()) {
				ResultBean resulValGenerarJP = validarGenerarJP(justificanteProfDto, titular, fechaInicio);
				Boolean esOk = (Boolean) resulValGenerarJP.getAttachment(ES_JP_OK);
				Boolean esForzar = (Boolean) resulValGenerarJP.getAttachment(ES_JP_FORZAR);
				Boolean esErrorFecha = (Boolean) resulValGenerarJP.getAttachment(ES_JP_ERROR_FECHA);
				BigDecimal estadoNuevo = null;
				BigDecimal estadoAnterior = null;
				JustificanteProfVO justificanteProfVO = null;
				if (!resulValGenerarJP.getError()) {
					ResultBean resultTramite = new ResultBean(false);
					if (justificanteProfDto.getNumExpediente() == null) {
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
							TramiteTrafTranDto tramiteTrafTranDto = getTramiteTrafTranDto(justificanteProfDto.getTramiteTrafico(), titular);
							resultTramite = servicioTramiteTraficoTransmision.guardarTramite(tramiteTrafTranDto, idUsuario, false, true, esAdmin);
						} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
							TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto = getTramiteTrafDupDto(justificanteProfDto.getTramiteTrafico(), titular);
							resultTramite = servicioTramiteTraficoDuplicado.guardarTramite(tramiteTrafDuplicadoDto, idUsuario, false, true, esAdmin);
						}
						if (resultTramite.getError()) {
							resultado = resultTramite;
						} else {
							justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
							if (justificanteProfVO.getTramiteTrafico().getContrato() == null) {
								justificanteProfVO.getTramiteTrafico().setContrato(new ContratoVO());
								justificanteProfVO.getTramiteTrafico().getContrato().setIdContrato(justificanteProfDto.getTramiteTrafico().getIdContrato().longValue());
							}
							if (justificanteProfVO.getIdJustificanteInterno() != null) {
								estadoAnterior = justificanteProfVO.getEstado();
							} else {
								estadoAnterior = BigDecimal.ZERO;
							}
							justificanteProfVO.setNumExpediente((BigDecimal) resultTramite.getAttachment(ServicioTramiteTraficoTransmision.NUMEXPEDIENTE));
							justificanteProfVO.setEstado(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
							justificanteProfVO.setFechaAlta(fechaInicio);
							justificanteProfDao.guardarOActualizar(justificanteProfVO);
							guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnterior, new BigDecimal(
									EstadoJustificante.Iniciado.getValorEnum()), idUsuario, null);
							estadoAnterior = justificanteProfVO.getEstado();
							if (esOk) {
								estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
								ResultBean resultCrearSolicitud = crearSolicitudJP(justificanteProfVO, titular, idUsuario);
								if (resultCrearSolicitud.getError()) {
									resultado = resultCrearSolicitud;
								} else {
									resultado.setMensaje("Solicitud de justificante generada.");
								}
							} else if (esForzar) {
								estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
								if (esErrorFecha) {
									resultado.setMensaje("Tramite " + justificanteProfVO.getNumExpediente() + "; El Justificante Profesional fue emitido hace menos de 30 días para este vehículo. "
											+ "Si quiere volver a emitir un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
								} else {
									resultado.setMensaje("Justificante con expediente: " + justificanteProfVO.getNumExpediente() + " , " + JUSTIFICANTE_REPETIDO);
								}
							}
						}
					} else {
						estadoAnterior = justificanteProfDto.getEstado();
						justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
						justificanteProfVO.setTramiteTrafico(servicioTramiteTrafico.getTramite(justificanteProfDto.getNumExpediente(), true));
						String tipoInterviniete = null;
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(justificanteProfVO.getTramiteTrafico().getTipoTramite())) {
							tipoInterviniete = TipoInterviniente.Adquiriente.getValorEnum();
						} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(justificanteProfVO.getTramiteTrafico().getTipoTramite())) {
							tipoInterviniete = TipoInterviniente.Titular.getValorEnum();
						}
						IntervinienteTraficoVO titularBBDD = servicioIntervinienteTrafico.getIntervinienteTrafico(justificanteProfVO.getTramiteTrafico().getNumExpediente(), tipoInterviniete, null,
								justificanteProfVO.getTramiteTrafico().getNumColegiado());
						if (esOk) {
							estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
							ResultBean resultCrearSolicitud = crearSolicitudJP(justificanteProfVO, conversor.transform(titularBBDD, IntervinienteTraficoDto.class), idUsuario);
							if (resultCrearSolicitud.getError()) {
								resultado = resultCrearSolicitud;
							} else {
								resultado.setMensaje("Solicitud de justificante generada.");
							}
						} else if (esForzar) {
							estadoNuevo = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
							if (esErrorFecha) {
								resultado.setMensaje("Tramite " + justificanteProfVO.getNumExpediente() + "; El Justificante Profesional fue emitido hace menos de 30 días para este vehículo. "
										+ "Si quiere volver a emitir un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
							} else {
								resultado.setMensaje("Justificante con expediente: " + justificanteProfVO.getNumExpediente() + " , " + JUSTIFICANTE_REPETIDO);
							}
						}
						justificanteProfDao.evict(justificanteProfVO);
					}
					if (!resultado.getError()) {
						justificanteProfVO.setEstado(estadoNuevo);
						justificanteProfDao.actualizar(justificanteProfVO);
						guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnterior, estadoNuevo, idUsuario, null);
						resultado.addAttachment(ID_JUSTIFICANTE_INTERNO, justificanteProfVO.getIdJustificanteInterno());
					}
				} else {
					resultado = resulValGenerarJP;
				}
			} else {
				resultado = resultValidarGJP;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el justificante profesional, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar el justificante profesional.");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean crearSolicitudJP(JustificanteProfVO justificanteProfVO, IntervinienteTraficoDto titular, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultCreditos = descontarCreditos(justificanteProfVO.getNumExpediente(), idUsuario, new BigDecimal(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato()));
			if (!resultCreditos.getError()) {
				ResultBean resultXml = generarXmlJP(justificanteProfVO, titular);
				if (!resultXml.getError()) {
					ResultBean resultCola;
					resultCola = crearSolicitud(ProcesosEnum.JUSTIFICANTE_PROFESIONAL.toString(), justificanteProfVO.getIdJustificanteInterno(), idUsuario, new BigDecimal(justificanteProfVO
							.getTramiteTrafico().getContrato().getIdContrato()), (String) resultXml.getAttachment(NOMBRE_XML));
					if (resultCola.getError()) {
						return resultCola;
					}
				} else {
					return resultXml;
				}
			} else {
				return resultCreditos;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar la solicitud e justificantes, error: ", e, justificanteProfVO.getNumExpediente().toString());
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar la solicitud de justificantes.");
		}
		return resultado;
	}

	public ResultBean generarXmlJP(JustificanteProfVO justificanteProfVO, IntervinienteTraficoDto titular) throws UnsupportedEncodingException, OegamExcepcion {
		ResultBean result = new ResultBean(false);
		ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato()));
		boolean esCertColegiadoCorrecto = comprobarCaducidadCertificado(contratoDto.getColegiadoDto().getAlias());
		if (esCertColegiadoCorrecto) {
			ResultBean resultFirma = null;
			String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
			if ("SI".equals(generarJustifSega)) {
				org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.SolicitudJustificanteProfesional solicitudJustificanteProfesionalSega = buildJustificantesProfesionalesSega
						.obtenerSolicitudJustificanteSega(justificanteProfVO, contratoDto, titular);
				resultFirma = buildJustificantesProfesionalesSega.realizarFirmaSega(solicitudJustificanteProfesionalSega, contratoDto.getColegiadoDto().getAlias());
				if (!resultFirma.getError()) {
					return buildJustificantesProfesionalesSega.validarYGuardarXml(solicitudJustificanteProfesionalSega, justificanteProfVO.getNumExpediente());
				}
			} else {
				SolicitudJustificanteProfesional solicitudJustificanteProfesional = buildJustificantesProfesionales.obtenerSolicitudJustificante(justificanteProfVO, contratoDto, titular);
				resultFirma = buildJustificantesProfesionales.realizarFirma(solicitudJustificanteProfesional, contratoDto.getColegiadoDto().getAlias());
				if (!resultFirma.getError()) {
					return buildJustificantesProfesionales.validarYGuardarXml(solicitudJustificanteProfesional, justificanteProfVO.getNumExpediente());
				}
			}
			if (resultFirma.getError()) {
				result = resultFirma;
			}
		} else {
			result.setError(true);
			result.addMensajeALista("El certificado del colegiado se encuentra caducado.");
		}
		return result;
	}

	private boolean comprobarCaducidadCertificado(String aliasColegiado) {
		boolean esOk = false;
		SolicitudPruebaCertificado solicitudPruebaCertificado = buildJustificantesProfesionales.obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
		if (idFirma != null) {
			esOk = true;
		}
		return esOk;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean validarGenerarJP(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, Date fechaInicio) {
		ResultBean resultBean = new ResultBean(false);
		Boolean esJPOk = Boolean.TRUE;
		Boolean esJPForzar = Boolean.FALSE;
		Boolean esErrorFecha = Boolean.FALSE;
		try {
			if (justificanteProfDto.getTramiteTrafico().getNumExpediente() != null) {
				if (hayJustificante(null, justificanteProfDto.getTramiteTrafico().getNumExpediente(), EstadoJustificante.Pendiente_DGT)) {
					resultBean.setError(true);
					resultBean.setMensaje("Ya existe un justificante en estado Pendiente DGT para el expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente());
				} else if (hayJustificante(null, justificanteProfDto.getTramiteTrafico().getNumExpediente(), EstadoJustificante.Pendiente_autorizacion_colegio)) {
					resultBean.setError(true);
					resultBean.setMensaje("Ya existe un justificante en estado Pendiente autorizacion colegio para el expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente());
				}
			}
			if (!resultBean.getError()) {
				Date fecha = utilesFecha.restarDias(fechaInicio, -30, new Integer(0), new Integer(0), new Integer(0));
				if (justificanteProfDao.comprobarJustificantesPorMatriculaNifYFecha(justificanteProfDto.getIdJustificanteInterno(), justificanteProfDto.getTramiteTrafico().getVehiculoDto()
						.getMatricula(), titular.getPersona().getNif(), null) > 0) {
					esJPOk = Boolean.FALSE;
					if (Constantes.PERMITE_SER_FORZADO.equals("SI")) {
						esJPForzar = Boolean.TRUE;
					} else {
						resultBean.setError(true);
						resultBean.setMensaje(JUSTIFICANTE_REPETIDO);
					}
				} else if (justificanteProfDao.comprobarJustificantesPorMatriculaNifYFecha(justificanteProfDto.getIdJustificanteInterno(), justificanteProfDto.getTramiteTrafico().getVehiculoDto()
						.getMatricula(), null, fecha) > 0) {
					esErrorFecha = Boolean.TRUE;
					esJPForzar = Boolean.TRUE;
					esJPOk = Boolean.FALSE;
				} else if (justificanteProfDao.existenJustificantesPorMatriculaEnEstadoPendiente(justificanteProfDto.getIdJustificanteInterno(), justificanteProfDto.getTramiteTrafico()
						.getVehiculoDto().getMatricula()) > 0) {
					esJPOk = Boolean.FALSE;
					if (Constantes.PERMITE_SER_FORZADO.equals("SI")) {
						esJPForzar = Boolean.TRUE;
					} else {
						resultBean.setError(true);
						resultBean.setMensaje(JUSTIFICANTE_REPETIDO_MATRICULA);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si existen justificantes para la matricula: " + justificanteProfDto.getTramiteTrafico().getVehiculoDto().getMatricula()
					+ ", error: ", e);
			resultBean.setError(true);
			resultBean.addMensajeALista("Ha sucedido un error a la hora de comprobar si existen justificantes para la matricula: " + justificanteProfDto.getTramiteTrafico().getVehiculoDto()
					.getMatricula());
		}
		if (!resultBean.getError()) {
			resultBean.addAttachment(ES_JP_OK, esJPOk);
			resultBean.addAttachment(ES_JP_FORZAR, esJPForzar);
			resultBean.addAttachment(ES_JP_ERROR_FECHA, esErrorFecha);
		}
		return resultBean;
	}

	private TramiteTrafDuplicadoDto getTramiteTrafDupDto(TramiteTrafDto tramiteTrafDto, IntervinienteTraficoDto titular) {
		TramiteTrafDuplicadoDto tramite = new TramiteTrafDuplicadoDto(tramiteTrafDto);
		titular.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		tramite.setTitular(titular);
		tramite.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
		tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
		if (tramite.getIdContrato() == null) {
			tramite.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}
		return tramite;
	}

	private TramiteTrafTranDto getTramiteTrafTranDto(TramiteTrafDto tramiteTrafDto, IntervinienteTraficoDto titular) {
		TramiteTrafTranDto tramite = new TramiteTrafTranDto(tramiteTrafDto);
		tramite.setElectronica("S");
		tramite.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		if (titular != null) {
			titular.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());
			tramite.setAdquiriente(titular);
		}
		tramite.setImprPermisoCircu(TipoSINO.SI.value());

		if (tramite.getIdContrato() == null) {
			tramite.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}

		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteTrafDto.getNumColegiado(), tramite.getIdContrato());
		IntervinienteTraficoDto presentador = new IntervinienteTraficoDto();
		presentador.setPersona(colegiado);
		presentador.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
		tramite.setPresentador(presentador);

		tramite.setAdquiriente(titular);
		return tramite;
	}

	private ResultBean validarGuardarJP(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, boolean checkIdFuerzasArmadas) {
		ResultBean resultBean = new ResultBean(false);
		if (justificanteProfDto.getTramiteTrafico() == null || justificanteProfDto.getTramiteTrafico() == null) {
			resultBean.setError(true);
			resultBean.setMensaje("- Debe rellenar los datos mínimos del justificante para poder guardarlo.");
		}
		if (justificanteProfDto.getTramiteTrafico().getTipoTramite() == null || justificanteProfDto.getTramiteTrafico().getTipoTramite().isEmpty()) {
			resultBean.setError(true);
			resultBean.setMensaje("- Debe de seleccionar el tipo de tramite relacionado con el justificante.");
		}
		if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
				.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getEstado())) {
			resultBean.setError(true);
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
				resultBean.setMensaje("- El trámite de transmisión no debe estar Finalizado Telemáticamente");
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
				resultBean.setMensaje("- El trámite de duplicado no debe estar Finalizado Telemáticamente");
			}
		}
		// validar motivo
		if (justificanteProfDto.getMotivo() == null || justificanteProfDto.getMotivo().isEmpty()) {
			resultBean.setError(true);
			resultBean.setMensaje("- Debe de indicar el motivo del justificante.");
		}
		if (justificanteProfDto.getDocumentos() == null || justificanteProfDto.getDocumentos().isEmpty()) {
			resultBean.setError(true);
			resultBean.setMensaje("- Debe seleccionar el tipo de documento.");
		}
		if (justificanteProfDto.getTramiteTrafico().getJefaturaTraficoDto() == null || justificanteProfDto.getTramiteTrafico().getJefaturaTraficoDto().getJefaturaProvincial() == null
				|| justificanteProfDto.getTramiteTrafico().getJefaturaTraficoDto().getJefaturaProvincial().isEmpty()) {
			resultBean.setError(true);
			resultBean.setMensaje("- La jefatura es obligatoria.");
		}

		if (titular != null) {
			if (titular.getPersona() != null) {
				if (titular.getPersona().getNif() == null || titular.getPersona().getNif().isEmpty()) {
					resultBean.setError(true);
					resultBean.setMensaje("- NIF obligatorio.");
				}
				if (titular.getPersona().getApellido1RazonSocial() == null || titular.getPersona().getApellido1RazonSocial().isEmpty()) {
					resultBean.setError(true);
					resultBean.setMensaje("- Nombre/Apellido o Razón Social obligatorio.");
				} else if (TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona()) && titular.getPersona().getApellido1RazonSocial().length() > 26) {
					resultBean.setError(true);
					resultBean.setMensaje("- El primer apellido de la persona no puede exceder de 26 carácteres.");
				} else if (TipoPersona.Juridica.getValorEnum().equals(titular.getPersona().getTipoPersona()) && titular.getPersona().getApellido1RazonSocial().length() > 70) {
					resultBean.setError(true);
					resultBean.setMensaje("- La razón social no puede exceder de 70 carácteres.");
				}
				if (titular.getPersona().getNombre() != null && titular.getPersona().getNombre().length() > 18) {
					resultBean.setError(true);
					resultBean.setMensaje("- El nombre de la persona no puede exceder de 18 carácteres.");
				}
				if (titular.getPersona().getApellido2() != null && titular.getPersona().getApellido2().length() > 26) {
					resultBean.setError(true);
					resultBean.setMensaje("- El segundo apellido de la persona no puede exceder de 26 carácteres.");
				}
				if (checkIdFuerzasArmadas) {
					if (titular.getPersona().getFa() == null || titular.getPersona().getFa().isEmpty()) {
						resultBean.setError(true);
						resultBean.setMensaje("- Debe de indicar el identificador de las fuerza armadas.");
					} else if (titular.getPersona().getFa().length() < 6) {
						resultBean.setError(true);
						resultBean.setMensaje("- El identificador de las fuerzas armadas se compone de 6 digitos.");
					}
				}
			} else {
				resultBean.setError(true);
				resultBean.setMensaje("- NIF y Nombre/Apellido o Razón Socia obligatorios.");
			}
			if (titular.getDireccion() != null) {
				if (titular.getDireccion().getNombreVia() == null || titular.getDireccion().getNombreVia().isEmpty()) {
					resultBean.setError(true);
					resultBean.setMensaje("- Nombre de vía obligatoria.");
				}
				if (titular.getDireccion().getNumero() == null || titular.getDireccion().getNumero().isEmpty()) {
					resultBean.setError(true);
					resultBean.setMensaje("- Número obligatorio.");
				}
			} else {
				resultBean.setError(true);
				resultBean.setMensaje("- Nombre de vía y Número obligatorios.");
			}
		} else {
			resultBean.setError(true);
			resultBean.setMensaje("- El titular es obligatorio.");
		}
		if (justificanteProfDto.getTramiteTrafico().getVehiculoDto() != null) {
			if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getCodigoMarca() == null || justificanteProfDto.getTramiteTrafico().getVehiculoDto().getCodigoMarca().isEmpty() || "-1".equals(
					justificanteProfDto.getTramiteTrafico().getVehiculoDto().getCodigoMarca())) {
				resultBean.setError(true);
				resultBean.setMensaje("- Marca obligatoria.");
			}

			if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getMatricula() == null || justificanteProfDto.getTramiteTrafico().getVehiculoDto().getMatricula().isEmpty()) {
				resultBean.setError(true);
				resultBean.setMensaje("- Matricula obligatoria.");
			}

			if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getBastidor() == null || justificanteProfDto.getTramiteTrafico().getVehiculoDto().getBastidor().isEmpty()) {
				resultBean.setError(true);
				resultBean.setMensaje("- Bastidor obligatorio.");
			}

			if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getModelo() == null || justificanteProfDto.getTramiteTrafico().getVehiculoDto().getModelo().isEmpty()) {
				resultBean.setError(true);
				resultBean.setMensaje("- Modelo obligatorio.");
			} else if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getModelo().length() > 22) {
				resultBean.setError(true);
				resultBean.setMensaje("- El modelo del vehículo no puede exceder de 22 carácteres.");
			}

			if (justificanteProfDto.getTramiteTrafico().getVehiculoDto().getTipoVehiculo() == null || "-1".equals(justificanteProfDto.getTramiteTrafico().getVehiculoDto().getTipoVehiculo())
					|| justificanteProfDto.getTramiteTrafico().getVehiculoDto().getTipoVehiculo().isEmpty()) {
				resultBean.setError(true);
				resultBean.setMensaje("- Tipo de vehículo obligatorio.");
			}
		} else {
			resultBean.setError(true);
			resultBean.setMensaje("- El vehículo es obligatorio.");
		}
		if (!resultBean.getError()) {
			if (justificanteProfDto.getDiasValidez() == null || justificanteProfDto.getDiasValidez().intValue() > 30) {
				justificanteProfDto.setDiasValidez(new BigDecimal(ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO));
			}
		}
		return resultBean;
	}

	private ResultBean validarJustificanteImpr(JustificanteProfVO justificanteProfVO) {
		ResultBean resultValidar = new ResultBean(false);
		if (justificanteProfVO == null) {
			resultValidar.setError(true);
			resultValidar.setMensaje("El justificante que esta intentando imprimir no existe.");
		} else if (!EstadoJustificante.Ok.getValorEnum().equals(justificanteProfVO.getEstado().toString())) {
			resultValidar.setError(true);
			resultValidar.setMensaje("El justificante: " + justificanteProfVO.getNumExpediente() + " no se encuentra en un estado valido para imprimir.");
		}
		return resultValidar;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getTitularNif(String nif, String numColegiado) {
		ResultBean resultado = new ResultBean(false);
		try {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nif.toUpperCase(), numColegiado);
			if (interviniente == null) {
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nif);
				interviniente.setNumColegiado(numColegiado);
				interviniente.setPersona(persona);
			}
			resultado.addAttachment(TITULAR_JUST_PROF, interviniente);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del titular por nif y numColegiado, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener los datos del titular.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getVehiculoColegiado(String matricula, String numColegiado) {
		ResultBean resultado = new ResultBean(false);
		try {
			VehiculoDto vehiculoDto = servicioVehiculo.getVehiculoDto(null, numColegiado, matricula.toUpperCase(), null, null, EstadoVehiculo.Activo);
			if (vehiculoDto == null) {
				vehiculoDto = new VehiculoDto();
				vehiculoDto.setMatricula(matricula);
				vehiculoDto.setNumColegiado(numColegiado);
			}
			resultado.addAttachment(VEHICULO_JUST_PROF, vehiculoDto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del vehiculo por matricula y numColegiado, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener los datos del vehiculo.");
		}
		return resultado;
	};

	@Override
	@Transactional(readOnly = true)
	public ColegioDto getColegioContrato(Long idContrato) {
		ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		if (contratoDto != null && contratoDto.getColegioDto() != null) {
			return contratoDto.getColegioDto();
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarJustifProfWS(Long idJustificanteInterno, BigDecimal numExpediente, BigDecimal idJustificante, byte[] justificantePdf) {
		ResultBean resultBean = new ResultBean(false);
		String nombrePdf = null;
		try {
			Date fechaInicio = Calendar.getInstance().getTime();
			JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
			justificanteProfVO.setFechaInicio(fechaInicio);
			justificanteProfVO.setFechaFin(utilesFecha.sumarDias(fechaInicio, 30, new Integer(0), new Integer(0), new Integer(0)));
			justificanteProfVO.setIdJustificante(idJustificante);
			justificanteProfDao.actualizar(justificanteProfVO);

			nombrePdf = numExpediente + "_" + utiles.rellenarCeros(justificanteProfVO.getIdJustificante().toString(), BigDecimal.TEN.intValue());
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.RESPUESTA, Utilidades.transformExpedienteFecha(numExpediente), nombrePdf,
					ConstantesGestorFicheros.EXTENSION_PDF, utiles.doBase64Decode(new String(justificantePdf), "UTF-8"));
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de guardar la respuesta del WS de justificante profesional, error: ", e, numExpediente.toString());
			resultBean.setError(true);
			resultBean.setMensaje("Ha sucedido un error a la hora de guardar la respuesta del WS de justificante profesional.");
		}
		if (resultBean != null && resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (nombrePdf != null && !nombrePdf.isEmpty()) {
				try {
					gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.RESPUESTA, Utilidades.transformExpedienteFecha(numExpediente), nombrePdf);
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de borrar el justificante, error :", e, numExpediente.toString());
				}
			}
		}
		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean crearJustificanteProfDtoToConsultaTramite(String numExp, String motivo, String documento, String diasValidez) {
		ResultBean resultado = new ResultBean(false);
		String tipoInterviniente = null;
		IntervinienteTraficoDto titular = null;
		try {
			if (numExp != null && !numExp.isEmpty()) {
				TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numExp), true);
				// validamos los datos minimos validos del tramite
				if (tramiteTrafDto == null) {
					resultado.setError(true);
					resultado.setMensaje("No existen datos del tramite para el expediente : " + numExp);
				} else if (!TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite()) && !TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafDto
						.getTipoTramite())) {
					resultado.setError(true);
					resultado.setMensaje("Del expediente: " + tramiteTrafDto.getNumExpediente()
							+ " no se puede generar un justificante profesional porque no es un tramite de Transmisión Electronica o Duplicado.");
				} else if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafDto.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()
						.equals(tramiteTrafDto.getEstado())) {
					resultado.setError(true);
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
						resultado.setMensaje("El trámite de transmisión no debe estar Finalizado Telemáticamente");
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
						resultado.setMensaje("El trámite de duplicado no debe estar Finalizado Telemáticamente");
					}
				} else if (tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getMatricula() != null && tramiteTrafDto.getVehiculoDto().getMatricula().isEmpty()) {
					resultado.setError(true);
					resultado.setMensaje("No existen datos del vehiculo del tramite para el expediente : " + numExp);
				} else {
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
						tipoInterviniente = TipoInterviniente.Adquiriente.getValorEnum();
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
						tipoInterviniente = TipoInterviniente.Titular.getValorEnum();
					}
					titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(tramiteTrafDto.getNumExpediente(), tipoInterviniente, null, tramiteTrafDto.getNumColegiado());
					if (titular == null) {
						resultado.setError(true);
						resultado.setMensaje("No existen datos del titular del tramite para el expediente : " + numExp);
					}
				}
				if (!resultado.getError()) {
					JustificanteProfDto justificanteProfDto = new JustificanteProfDto();
					justificanteProfDto.setTramiteTrafico(tramiteTrafDto);
					justificanteProfDto.setNumExpediente(tramiteTrafDto.getNumExpediente());
					justificanteProfDto.setEstado(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
					justificanteProfDto.setDocumentos(documento);
					justificanteProfDto.setMotivo(motivo);
					justificanteProfDto.setDiasValidez(new BigDecimal(diasValidez));
					ResultBean resultValidar = validarGenerarJP(justificanteProfDto, titular, new Date());
					if (!resultValidar.getError()) {
						resultado.addAttachment(DTO_JUSTIFICANTE, justificanteProfDto);
					} else {
						resultado = resultValidar;
					}
				}
				if (tramiteTrafDto != null) {
					resultado.addAttachment(TIPO_TRAMITE_JUSTIFICANTE, tramiteTrafDto.getTipoTramite());
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Error en el formato del numero de expediente del tramite que desea generar su justificante profesional.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un errro a la hora de generar el justificanteProfDto para el expediente : " + numExp + ", error: ", e, numExp);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un errro a la hora de generar el justificante para el expediente: " + numExp);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarJustificanteConsultaTramite(JustificanteProfDto justificanteProfDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			JustificanteProfVO justificanteProfVO = conversor.transform(justificanteProfDto, JustificanteProfVO.class);
			justificanteProfVO.setFechaAlta(new Date());
			justificanteProfDao.guardar(justificanteProfVO);
			guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), BigDecimal.ZERO, justificanteProfVO.getEstado(), idUsuario,
					null);
			resultado.setMensaje("Justificante Profesional guardado para el expediente: " + justificanteProfDto.getNumExpediente()
					+ ", debe completar los datos en la pantalla de justificantes para poder solicitarlo.");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el justificante desde la consulta de tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el justificante para el expediente: " + justificanteProfDto.getNumExpediente());
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getJustificanteProfPorNumExpediente(BigDecimal numExpediente, Boolean justificanteCompleto, BigDecimal estadoJustificante) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (numExpediente != null) {
				JustificanteProfVO justificanteProfVO = justificanteProfDao.getJustificanteProfPorNumExpediente(numExpediente, justificanteCompleto, estadoJustificante);
				if (justificanteProfVO != null) {
					resultado.addAttachment(DTO_JUSTIFICANTE, conversor.transform(justificanteProfVO, JustificanteProfDto.class));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para el numero de expediente: " + numExpediente + ", no existe ningún justificante profesional.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar el numero de expediente para obtener el detalle del justificante.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el justificante con el numero de expediente: " + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el justificante con el numero de expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	@Transactional
	public void cambiarEstadoConEvolucion(Long idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		try {
			if (idJustificanteInterno != null) {
				JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
				if (justificanteProfVO != null) {
					BigDecimal estadoAnt = justificanteProfVO.getEstado();
					justificanteProfVO.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
					justificanteProfDao.actualizar(justificanteProfVO);
					guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, new BigDecimal(estadoNuevo.getValorEnum()),
							idUsuario, null);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del justificante, error: ", e);
		}
	}

	@Override
	@Transactional
	public boolean cambiarEstadoConEvolucionPorNumExpediente(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		try {
			if (numExpediente != null) {
				JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorNumExpediente(numExpediente, Boolean.TRUE, null);
				if (justificanteProfVO != null) {
					BigDecimal estadoAnt = justificanteProfVO.getEstado();

					if (estadoNuevo.getValorEnum().equals(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum())) {
						// Para cambiar a este estado el justificante debe estar iniciado...
						if (!justificanteProfVO.getEstado().toString().equals(EstadoJustificante.Iniciado.getValorEnum())) {
							return false;
						}
						// y no estar encolado...
						ColaVO cola = servicioCola.getColaIdTramite(numExpediente, null);
						if (cola != null) {
							return false;
						}
					}

					justificanteProfVO.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
					justificanteProfDao.actualizar(justificanteProfVO);
					guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, new BigDecimal(estadoNuevo.getValorEnum()),
							idUsuario, null);
					return true;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del justificante, error: ", e);
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public void cambiarEstadoConEvolucionSega(BigDecimal numExpediente, EstadoJustificante estadoAnterior, EstadoJustificante estadoJustificanteNuevo, BigDecimal idUsuario) {
		try {
			if (numExpediente != null) {
				JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorNumExpediente(numExpediente, Boolean.TRUE, new BigDecimal(estadoAnterior.getValorEnum()));
				if (justificanteProfVO != null) {
					BigDecimal estadoAnt = justificanteProfVO.getEstado();
					justificanteProfVO.setEstado(new BigDecimal(estadoJustificanteNuevo.getValorEnum()));
					justificanteProfDao.actualizar(justificanteProfVO);
					guardarEvolucionJustificanteProfesional(justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente(), estadoAnt, new BigDecimal(estadoJustificanteNuevo
							.getValorEnum()), idUsuario, null);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del justificante, error: ", e, numExpediente.toString());
		}
	}

	@Override
	@Transactional
	public void devolverCreditos(Long idJustificanteInterno, BigDecimal idUsuario) {
		try {
			if (idJustificanteInterno != null) {
				JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorIDInternoVO(idJustificanteInterno);
				if (justificanteProfVO != null) {
					servicioCredito.devolverCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), new BigDecimal(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato()),
							BigDecimal.ONE.intValue(), idUsuario, ConceptoCreditoFacturado.DSJP, justificanteProfVO.getNumExpediente().toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos del justificante, error: ", e);
		}
	}

	@Transactional
	public void devolverCreditosSega(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoJustificante) {
		try {
			if (numExpediente != null) {
				JustificanteProfVO justificanteProfVO = getJustificanteProfesionalPorNumExpediente(numExpediente, Boolean.TRUE, estadoJustificante);
				if (justificanteProfVO != null) {
					servicioCredito.devolverCreditos(TipoTramiteTrafico.JustificantesProfesionales.getValorEnum(), new BigDecimal(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato()),
							BigDecimal.ONE.intValue(), idUsuario, ConceptoCreditoFacturado.DSJP, justificanteProfVO.getNumExpediente().toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos del justificante, error: ", e, numExpediente.toString());
		}
	}

	public JustificanteProfDao getJustificanteProfDao() {
		return justificanteProfDao;
	}

	public void setJustificanteProfDao(JustificanteProfDao justificanteProfDao) {
		this.justificanteProfDao = justificanteProfDao;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioTramiteTraficoTransmision getServicioTramiteTraficoTransmision() {
		return servicioTramiteTraficoTransmision;
	}

	public void setServicioTramiteTraficoTransmision(ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision) {
		this.servicioTramiteTraficoTransmision = servicioTramiteTraficoTransmision;
	}

	public ServicioTramiteTraficoDuplicado getServicioTramiteTraficoDuplicado() {
		return servicioTramiteTraficoDuplicado;
	}

	public void setServicioTramiteTraficoDuplicado(ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado) {
		this.servicioTramiteTraficoDuplicado = servicioTramiteTraficoDuplicado;
	}

	public ServicioEvolucionJustificanteProfesional getServicioEvolucionJustificanteProfesional() {
		return servicioEvolucionJustificanteProfesional;
	}

	public void setServicioEvolucionJustificanteProfesional(ServicioEvolucionJustificanteProfesional servicioEvolucionJustificanteProfesional) {
		this.servicioEvolucionJustificanteProfesional = servicioEvolucionJustificanteProfesional;
	}

	public ServicioCredito getServicioCredito() {
		return servicioCredito;
	}

	public void setServicioCredito(ServicioCredito servicioCredito) {
		this.servicioCredito = servicioCredito;
	}

	public ServicioCola getServicioCola() {
		return servicioCola;
	}

	public void setServicioCola(ServicioCola servicioCola) {
		this.servicioCola = servicioCola;
	}

	public ServicioJustificanteProfesionalImprimir getServicioJustificanteProfesionalImprimir() {
		return servicioJustificanteProfesionalImprimir;
	}

	public void setServicioJustificanteProfesionalImprimir(ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir) {
		this.servicioJustificanteProfesionalImprimir = servicioJustificanteProfesionalImprimir;
	}

	@Override
	@Transactional
	public JustificanteProfVO getJustificanteProfesionalPorNumExpediente(BigDecimal numExpediente, Boolean justificanteCompleto, BigDecimal estadoJustificante) {
		try {
			if (numExpediente != null) {
				return justificanteProfDao.getJustificanteProfPorNumExpediente(numExpediente, justificanteCompleto, estadoJustificante);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el justificanteProfVO por su id, error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public void cambiarEstadoConEvolucionSega(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambiarEstadoConEvolucionSega2(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public int numeroElementosListadoJustificantesNoUltimados(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		List<JustificanteProfVO> lista = justificanteProfDao.listadoJustificantesNoUltimadosEstadisticas(filter, fetchModeJoinList, entitiesJoin, listaProyecciones);
		if (lista != null && !lista.isEmpty()) {
			return (Integer) lista.size();
		}
		return 0;
	}


	@Override
	@Transactional(readOnly = true)
	public ResultBean generarExcelListadoJustificantesNoUltimados(Object filter) {
		File archivo = null;
		ResultBean resultado = new ResultBean();

		String nombreFichero = "ESTADISTICAS__JUSTIFICANTES__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.ESTADISTICAS);
		fichero.setSubTipo(ConstantesGestorFicheros.EXCELS);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
		fichero.setNombreDocumento(nombreFichero);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		try {
			archivo = gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero excel con las estadísticas de justificantes profesionales no ultimados: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al guardar el fichero excel con las estadísticas de justificantes profesionales no ultimados.");
		}

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;

		try {

			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("EstadisticasJustificantes", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 8; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 8; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;

			try {

				label = new Label(0, 0, "Número de Colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Matrícula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Número de Justificante", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Número de Expediente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Tipo de Trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Estado del Trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Fecha Inicio", formatoCabecera);
				sheet.addCell(label);
				label = new Label(7, 0, "Fecha Fin", formatoCabecera);
				sheet.addCell(label);
				label = new Label(8, 0, "Días Validez", formatoCabecera);
				sheet.addCell(label);

				Integer contador = 1;
				
				List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
				listaAlias.add(new AliasQueryBean(TramiteTraficoVO.class, "tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN));
				listaAlias.add(new AliasQueryBean(VehiculoVO.class, "tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN));

				List<JustificanteProfVO> lista = justificanteProfDao.listadoJustificantesNoUltimadosEstadisticas(filter, null, listaAlias, null);

				for (JustificanteProfVO element : lista) {

					// Columna Núm. Colegiado
					if (element.getTramiteTrafico() != null && StringUtils.isNotBlank(element.getTramiteTrafico().getNumColegiado())) {
						label = new Label(0, contador, utiles.rellenarCeros(element.getTramiteTrafico().getNumColegiado(), 4), formatoDatos);
					} else {
						label = new Label(0, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Matrícula
					if (element.getTramiteTrafico() != null && element.getTramiteTrafico().getVehiculo() != null && StringUtils.isNotBlank(element.getTramiteTrafico().getVehiculo().getMatricula())) {
						label = new Label(1, contador, element.getTramiteTrafico().getVehiculo().getMatricula(), formatoDatos);
					} else {
						label = new Label(1, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Número de Justificante
					if (element.getIdJustificante() != null) {
						label = new Label(2, contador, element.getIdJustificante().toString(), formatoDatos);
					} else {
						label = new Label(2, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Número de Expediente
					if (element.getTramiteTrafico() != null && element.getTramiteTrafico().getNumExpediente() != null) {
						label = new Label(3, contador, element.getTramiteTrafico().getNumExpediente().toString(), formatoDatos);
					} else {
						label = new Label(3, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo de Trámite
					if (element.getTramiteTrafico() != null && StringUtils.isNotBlank(element.getTramiteTrafico().getTipoTramite())) {
						label = new Label(4, contador, TipoTramiteTraficoJustificante.convertirTexto(element.getTramiteTrafico().getTipoTramite()), formatoDatos);
					} else {
						label = new Label(4, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Estado
					if (element.getTramiteTrafico() != null && element.getTramiteTrafico().getEstado() != null) {
						label = new Label(5, contador, EstadoTramiteTrafico.convertirTexto(element.getTramiteTrafico().getEstado().toString()), formatoDatos);
					} else {
						label = new Label(5, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Fecha Inicio
					if (element.getFechaInicio() != null) {
						label = new Label(6, contador, utilesFecha.formatoFecha(element.getFechaInicio()), formatoDatos);
					} else {
						label = new Label(6, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Fecha Fin
					if (element.getFechaFin() != null) {
						label = new Label(7, contador, utilesFecha.formatoFecha(element.getFechaFin()), formatoDatos);
					} else {
						label = new Label(7, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Días de Validez
					if (element.getDiasValidez() != null) {
						label = new Label(8, contador, element.getDiasValidez().toString(), formatoDatos);
					} else {
						label = new Label(8, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					contador++;
				}

			} catch (RowsExceededException e) {
				log.error("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados: ,error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados.");
			}
			copyWorkbook.write();
		} catch (IOException | WriteException e) {
			log.error("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (IOException | WriteException e) {
					log.error("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados: ,error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de justificantes profesionales no ultimados.");
				}
			}
		}

		resultado.addAttachment(ResultBean.TIPO_FILE, archivo);
		resultado.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero);

		return resultado;

	}

}
