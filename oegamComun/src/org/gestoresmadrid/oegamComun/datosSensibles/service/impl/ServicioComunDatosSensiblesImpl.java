package org.gestoresmadrid.oegamComun.datosSensibles.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesBastidorDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesNifDao;
import org.gestoresmadrid.core.datosSensibles.model.enumerados.TiposDatosSensibles;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesBastidorDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesNifDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesBastidorVO;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesNifVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoBastidorSantander;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.datosSensibles.service.ServicioComunDatosSensibles;
import org.gestoresmadrid.oegamComun.utiles.UtilesDatosSensibles;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunDatosSensiblesImpl implements ServicioComunDatosSensibles {

	private static final long serialVersionUID = 3314435208447454979L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunDatosSensiblesImpl.class);

	@Autowired
	private DatosSensiblesBastidorDao datosSensiblesBastidorDao;

	@Autowired
	private DatosSensiblesMatriculaDao datosSensiblesMatriculaDao;

	@Autowired
	private DatosSensiblesNifDao datosSensiblesNifDao;

	@Autowired
	private EvolucionDatosSensiblesMatriculaDao evolucionDatosSensiblesMatriculaDao;

	@Autowired
	private EvolucionDatosSensiblesBastidorDao evolucionDatosSensiblesBastidorDao;

	@Autowired
	private EvolucionDatosSensiblesNifDao evolucionDatosSensiblesNifDao;

	@Autowired
	private ServicioComunCorreo servicioCorreo;

	@Autowired
	private ServicioComunCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private UtilidadesNIFValidator utilidadesNIFValidator;

	@Autowired
	private UtilesDatosSensibles utilesDatosSensibles;

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	private static final String asuntoCorreo_Baja = "asunto.correo.datosSensiblesBaja";
	private static final String destinatarioCorreoBastidoresConCopia = "datos.sensibles.mail.cc";
	private static final String destinatarioSantanderOperDatoSensibleFR = "destinatario.correo.sensibles.fr";
	private static final String destinatarioCorreoBastidores = "destinatario.correo.bastidores";
	private static final String destinatarioCorreoBastidoresOculto = "destinatario.correo.bastidores.oculto";
	private static final String dirOcultasOperDatoSensible = "datos.sensibles.mail.bcc";
	private static final String dirOcultasOperDatoSensibleVN = "datos.sensibles.mail.bvncc";
	private static final String dirOcultasOperDatoSensibleVO = "datos.sensibles.mail.bvocc";
	private static final String dirOcultasOperDatoSensibleDM = "datos.sensibles.mail.bdmcc";
	private static final String dirOcultasOperDatoSensibleFI = "datos.sensibles.mail.bficc";
	private static final String dirOcultasOperDatoSensibleLE = "datos.sensibles.mail.blecc";
	private static final String dirOcultasOperDatoSensibleRE = "datos.sensibles.mail.brecc";
	private static final String dirOcultasOperDatoSensibleFR = "datos.sensibles.mail.bfrcc";

	private static final String activadoEnvioCorreoBastidorVO = "envio.correo.notificacion.bastidorvo";
	private static final String activadoEnvioCorreoBastidorDM = "envio.correo.notificacion.bastidordm";
	private static final String activadoEnvioCorreoBastidorFI = "envio.correo.notificacion.bastidorfi";
	private static final String activadoEnvioCorreoBastidorLE = "envio.correo.notificacion.bastidorle";
	private static final String activadoEnvioCorreoBastidorRE = "envio.correo.notificacion.bastidorre";
	private static final String activadoEnvioCorreoBastidorFR = "envio.correo.notificacion.bastidorfr";
	private static final String subjectOperDatoSensible = "subject.datos.sensibles";
	private static final String subjectOperDatoSensibleFR = "subject.datos.sensiblesFR";

	private static final String COMPROBAR_DATOS_SENSIBLES = "comprobar.datos.sensibles.";
	private static final String COMPROBAR_DATOS_SENSIBLES_PATRON = "comprobar.datos.sensibles.patron.";
	private static final String ASUNTO_LIBERACION_DATO_SENSIBLE = "asunto.liberacion.dato.sensible";
	private static final String EMAIL_DESTINATARIO_LIBERACION_DATO_SENSIBLE = "correo.liberacion.dato.sensible.destinatario";

	private static final String encolarTipoBastidor = "datosSensibles.encolar.Bastidor";

	private static final String APLICACION = "APLICACION";

	@Override
	@Transactional
	public boolean existeDatosSensible(TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario) {
		boolean resultado = false;

		Map<String, String> dirGrupos = utilesDatosSensibles.direccionesGrupos();
		String tipoPersonaDT = extraerTipoPersonaTramite(tramiteTraficoVO);

		try {
			if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getMatricula() != null) {
				if (contained(utilesDatosSensibles.getMatriculasSensibles(), tramiteTraficoVO.getVehiculo().getMatricula())) {
					if (continuaMatriculaBloqueada(tramiteTraficoVO.getVehiculo().getMatricula(), idUsuario)) {
						log.info("Se ha detectado la siguiente matricula sensible: " + tramiteTraficoVO.getVehiculo().getMatricula());
						List<DatosSensiblesMatriculaVO> listaGruposSiguiendoMatricula = datosSensiblesMatriculaDao.getListaGruposPorMatricula(tramiteTraficoVO.getVehiculo().getMatricula(),
								new BigDecimal(1));
						resultado = true;
						if (listaGruposSiguiendoMatricula != null && !listaGruposSiguiendoMatricula.isEmpty()) {
							for (DatosSensiblesMatriculaVO datosSMatriculaVO : listaGruposSiguiendoMatricula) {
								if (!isFranjaHorariaAutoliberacionActiva(datosSMatriculaVO.getGrupo().getIdGrupo(), datosSMatriculaVO, null, null, idUsuario)) {
									StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matrícula a seguir: - ");
									sb.append("  Matrícula ").append(tramiteTraficoVO.getVehiculo().getMatricula()).append(" - ");
									if (dirGrupos.containsKey(datosSMatriculaVO.getGrupo().getIdGrupo())) {
										envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), datosSMatriculaVO.getGrupo().getCorreoElectronico(), sb,
												tramiteTraficoVO.getVehiculo().getMatricula(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
									}
								}
							}
						}

					}
				}
			}
		} catch (Throwable e) {
			log.error("Fallo al analizar los datos sensibles de matricula", e);
		}

		try {
			if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getMatriculaOrigen() != null) {
				if (contained(utilesDatosSensibles.getMatriculasSensibles(), tramiteTraficoVO.getVehiculo().getMatriculaOrigen())) {
					if (continuaMatriculaBloqueada(tramiteTraficoVO.getVehiculo().getMatriculaOrigen(), idUsuario)) {
						log.info("Se ha detectado la siguiente matricula origen sensible: " + tramiteTraficoVO.getVehiculo().getMatriculaOrigen());
						List<DatosSensiblesMatriculaVO> listaGruposSiguiendoMatricula = datosSensiblesMatriculaDao.getListaGruposPorMatricula(tramiteTraficoVO.getVehiculo().getMatriculaOrigen(),
								new BigDecimal(1));
						resultado = true;
						if (listaGruposSiguiendoMatricula != null && !listaGruposSiguiendoMatricula.isEmpty()) {
							for (DatosSensiblesMatriculaVO datosSMatriculaVO : listaGruposSiguiendoMatricula) {
								if (!isFranjaHorariaAutoliberacionActiva(datosSMatriculaVO.getGrupo().getIdGrupo(), datosSMatriculaVO, null, null, idUsuario)) {
									StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matrícula Origen a seguir: - ");
									sb.append("  Matrícula Origen ").append(tramiteTraficoVO.getVehiculo().getMatriculaOrigen()).append(" - ");
									sb.append("  Matrícula ").append(tramiteTraficoVO.getVehiculo().getMatricula()).append(" - ");
									if (dirGrupos.containsKey(datosSMatriculaVO.getGrupo().getIdGrupo())) {
										envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), datosSMatriculaVO.getGrupo().getCorreoElectronico(), sb,
												tramiteTraficoVO.getVehiculo().getMatricula(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
									}
								}
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Fallo al analizar los datos sensibles de matrícula origen", e);
		}

		try {
			if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getBastidor() != null) {
				if (contained(utilesDatosSensibles.getBastidoresSensibles(), tramiteTraficoVO.getVehiculo().getBastidor())) {
					if (continuaBastidorBloqueado(tramiteTraficoVO.getVehiculo().getBastidor(), idUsuario)) {
						log.info("Se ha detectado el siguiente bastidor sensible: " + tramiteTraficoVO.getVehiculo().getBastidor());
						List<DatosSensiblesBastidorVO> listaGruposSiguendoBastidor = datosSensiblesBastidorDao.getlistaGruposPorBastidor(tramiteTraficoVO.getVehiculo().getBastidor(), new BigDecimal(
								1));
						if (listaGruposSiguendoBastidor != null && !listaGruposSiguendoBastidor.isEmpty()) {
							for (DatosSensiblesBastidorVO bastidorVO : listaGruposSiguendoBastidor) {
								if (bastidorVO.getTipoBastidor() != null) {
									if (!isFranjaHorariaAutoliberacionActiva(bastidorVO.getGrupo().getIdGrupo(), null, bastidorVO, null, idUsuario)) {
										StringBuffer sb = null;
										String nuevoFormatosFicheros = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp");
										if ("SI".equalsIgnoreCase(nuevoFormatosFicheros) && (bastidorVO.getTipoBastidorSantander() != null && TipoBastidorSantander.validarTipoBastidorPorValor(
												bastidorVO.getTipoBastidorSantander().toString()))) {
											sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
													+ " correspondiente a un veh&iacute;culo de Financiaci&oacute;n, Leasing o Renting. - ");
											envioCorreoDatosSensiblesCrtRt(tramiteTraficoVO, bastidorVO, sb, tipoPersonaDT);
										} else {
											if (TipoBastidor.VN.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
												if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoVO.getTipoTramite())) {
													resultado = true;
													sb = new StringBuffer("Se intent&oacute; realizar un tr&aacute;mite sobre un veh&iacute;culo con un Bastidor a seguir: - ");
													sb.append("  Bastidor ").append(bastidorVO.getId().getBastidor()).append(" - ");
												} else {
													sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
															+ " correspondiente a un veh&iacute;culo nuevo. - ");
												}
											} else if (TipoBastidor.DM.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) || TipoBastidor.VO.getValorEnum().equals(bastidorVO
													.getTipoBastidor().toString()) || TipoBastidor.FI.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) || TipoBastidor.LE.getValorEnum()
															.equals(bastidorVO.getTipoBastidor().toString()) || TipoBastidor.RE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
												sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
														+ " correspondiente a un veh&iacute;culo de Ocasi&oacute;n,Demo,Financiaci&oacute;n,Leasing o Renting. - ");
											} else if (TipoBastidor.FR.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
												sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
														+ " correspondiente a un veh&iacute;culo de Financiaci&oacute;n Retail. - ");
											}
											if (dirGrupos.containsKey(bastidorVO.getGrupo().getIdGrupo())) {
												if (TipoBastidor.VN.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
													envioCorreoDatosSensiblesBastidorVN(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.VO.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorVO)))) {
													envioCorreoDatosSensiblesBastidorVO(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.DM.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorDM)))) {
													envioCorreoDatosSensiblesBastidorDM(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.FI.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorFI)))) {
													envioCorreoDatosSensiblesBastidorFI(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.LE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorLE)))) {
													envioCorreoDatosSensiblesBastidorLE(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.RE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorRE)))) {
													envioCorreoDatosSensiblesBastidorRE(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
												} else if (TipoBastidor.FR.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
														activadoEnvioCorreoBastidorFR)))) {
													envioCorreoDatosSensiblesBastidorFR(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
															.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(),
															tipoPersonaDT);
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
		} catch (Throwable e) {
			log.error("Fallo al analizar los datos sensibles de bastidor", e);
		}

		try {
			if (tramiteTraficoVO.getIntervinienteTraficos() != null && !tramiteTraficoVO.getIntervinienteTraficos().isEmpty()) {
				for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTraficoVO.getIntervinienteTraficos()) {
					if (intervinienteTraficoVO.getId().getNif() != null && contained(utilesDatosSensibles.getNifsSensibles(), intervinienteTraficoVO.getId().getNif())
							&& continuaNifBloqueado(intervinienteTraficoVO.getId().getNif(), idUsuario)) {
						log.info("Se ha detectado el siguiente NIF sensible: " + intervinienteTraficoVO.getId().getNif());
						List<DatosSensiblesNifVO> listaGruposSiguiendoNif = datosSensiblesNifDao.getListaGruposPorNif(intervinienteTraficoVO.getId().getNif(), new BigDecimal(1));
						resultado = true;
						if (listaGruposSiguiendoNif != null && !listaGruposSiguiendoNif.isEmpty()) {
							for (DatosSensiblesNifVO nifVO : listaGruposSiguiendoNif) {
								if (!isFranjaHorariaAutoliberacionActiva(nifVO.getId().getNif(), null, null, nifVO, idUsuario)) {
									StringBuffer sb = new StringBuffer("Se intentó realizar un trámite con un NIF a seguir: - ");
									sb.append("  Nif ").append(nifVO.getId().getNif()).append(" - ");
									if (dirGrupos.containsKey(nifVO.getGrupo().getIdGrupo())) {
										envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), nifVO.getGrupo().getCorreoElectronico(), sb, nifVO
												.getId().getNif(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
									}
								}
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Fallo al analizar los datos sensibles de NIF", e);
		}
		return resultado;
	}

	private String extraerTipoPersonaTramite(TramiteTraficoVO tramiteTrafico) {
		String tipoPersona = "";
		if (tramiteTrafico.getIntervinienteTraficos() != null && !tramiteTrafico.getIntervinienteTraficos().isEmpty()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite()) || TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())
					|| TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
				IntervinienteTraficoVO intervinienteTraficoVO = buscarInterviniente(tramiteTrafico, TipoInterviniente.Titular.getValorEnum());
				if (intervinienteTraficoVO != null) {
					int tipo = utilidadesNIFValidator.isValidDniNieCif(intervinienteTraficoVO.getId().getNif().toUpperCase());
					if (TipoPersona.Fisica.getValorPersona() == tipo) {
						tipoPersona = TipoPersona.Fisica.getNombreEnum();
					} else {
						tipoPersona = TipoPersona.Juridica.getNombreEnum();
					}
				}
			} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite()) || TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico
					.getTipoTramite())) {
				IntervinienteTraficoVO intervinienteTraficoVO = buscarInterviniente(tramiteTrafico, TipoInterviniente.Adquiriente.getValorEnum());
				if (intervinienteTraficoVO != null) {
					int tipo = utilidadesNIFValidator.isValidDniNieCif(intervinienteTraficoVO.getId().getNif().toUpperCase());
					if (TipoPersona.Fisica.getValorPersona() == tipo) {
						tipoPersona = TipoPersona.Fisica.getNombreEnum();
					} else {
						tipoPersona = TipoPersona.Juridica.getNombreEnum();
					}
				}
			}
		}
		return tipoPersona;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTraficoVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}

	private boolean contained(List<String> list, String s) {
		if (list != null && s != null) {
			for (String sl : list) {
				if (s.equalsIgnoreCase(sl)) {
					return true;
				}
			}
		}
		return false;
	}

	private Boolean continuaMatriculaBloqueada(String matricula, BigDecimal idUsuario) {
		boolean matriculaBlock = false;
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = getDatosSensiblesMatriculaVOPorMatricula(matricula);
		if (datosSensiblesMatriculaVO != null) {
			if (datosSensiblesMatriculaVO.getTiempoRestauracion() != null && datosSensiblesMatriculaVO.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
				if (datosSensiblesMatriculaVO.getFechaOperacion() == null) {
					datosSensiblesMatriculaVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesMatriculaVO.setUsuarioOperacion(idUsuario);
					datosSensiblesMatriculaDao.actualizar(datosSensiblesMatriculaVO);
					matriculaBlock = true;
				} else if (fechaOperacionSuperada(datosSensiblesMatriculaVO.getTiempoRestauracion(), datosSensiblesMatriculaVO.getFechaOperacion())) {
					datosSensiblesMatriculaVO.setEstado(new BigDecimal(0));
					datosSensiblesMatriculaVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesMatriculaDao.actualizar(datosSensiblesMatriculaVO);
					utilesDatosSensibles.recargarDatosSensibles();
					matriculaBlock = true;
				}
			} else {
				matriculaBlock = true;
			}
		}
		return matriculaBlock;
	}

	public boolean fechaOperacionSuperada(BigDecimal tiempoRestauracion, Date fechaOperacion) {
		long diferenciaDias = 0;
		boolean resultado = false;
		try {
			diferenciaDias = utilesFecha.diferenciaFechaEnDias(fechaOperacion, new Date());
		} catch (Exception e) {
			log.error("Se ha producido un error en datos sensibles al comparar la fecha de operacion con la actual", e);
		}

		if (tiempoRestauracion.compareTo(new BigDecimal(1)) == 0 && diferenciaDias > 0) {
			resultado = true;
		}

		if (tiempoRestauracion.compareTo(new BigDecimal(2)) == 0 && diferenciaDias > 1) {
			resultado = true;
		}

		return resultado;
	}

	private DatosSensiblesMatriculaVO getDatosSensiblesMatriculaVOPorMatricula(String matricula) {
		List<DatosSensiblesMatriculaVO> listaDatosSensiblesMatriculaVO = datosSensiblesMatriculaDao.buscarPorMatricula(matricula);
		if (listaDatosSensiblesMatriculaVO != null && !listaDatosSensiblesMatriculaVO.isEmpty()) {
			return listaDatosSensiblesMatriculaVO.get(0);
		}
		return null;
	}

	private DatosSensiblesBastidorVO getDatosSensiblesBastidorVOPorBastidor(String bastidor) {
		List<DatosSensiblesBastidorVO> listaDatosSensiblesBastidorVO = datosSensiblesBastidorDao.buscarPorBastidor(bastidor);
		if (listaDatosSensiblesBastidorVO != null && !listaDatosSensiblesBastidorVO.isEmpty()) {
			return listaDatosSensiblesBastidorVO.get(0);
		}
		return null;
	}

	private DatosSensiblesNifVO getDatosSensiblesNifPorNif(String nif) {
		List<DatosSensiblesNifVO> listaDatosSensiblesNif = datosSensiblesNifDao.buscarporNif(nif);
		if (listaDatosSensiblesNif != null && !listaDatosSensiblesNif.isEmpty()) {
			return listaDatosSensiblesNif.get(0);
		}
		return null;
	}

	private boolean isFranjaHorariaAutoliberacionActiva(String idGrupo, DatosSensiblesMatriculaVO datosMatriculaVO, DatosSensiblesBastidorVO datosBastidorVO, DatosSensiblesNifVO datosNifVO,
			BigDecimal idUsuario) {
		boolean activa = false;
		String grupo = idGrupo;
		if ("SI".equals(gestorPropiedades.valorPropertie(COMPROBAR_DATOS_SENSIBLES + grupo.toLowerCase()))) {
			String patron = gestorPropiedades.valorPropertie(COMPROBAR_DATOS_SENSIBLES_PATRON + grupo.toLowerCase());
			if (patron != null) {
				Fecha fechaActual = utilesFecha.getFechaHoraActualLEG();
				Calendar calendario = Calendar.getInstance();
				int diaSemanaNum;
				String diaSemana = null;
				try {
					calendario.setTime(fechaActual.getDate());
					diaSemanaNum = calendario.get(Calendar.DAY_OF_WEEK);
					String[] dias = { "D", "L", "M", "X", "J", "V", "S" };
					diaSemana = dias[diaSemanaNum - 1];
				} catch (ParseException e) {
					log.error(e);
				}
				for (String franja : patron.split(",")) {
					if (franja != null && diaSemana != null && franja.startsWith(diaSemana)) {
						int hora = Integer.parseInt(fechaActual.getHora());
						int horaini = Integer.parseInt(franja.substring(1, 3));
						int horafin = Integer.parseInt(franja.substring(4, 6));
						if (horaini <= hora && horafin >= hora) {
							activa = true;
							if (datosMatriculaVO != null) {
								String indices = datosMatriculaVO.getId().getMatricula();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								ResultadoBean resultado = eliminarMatricula(indices, idUsuario);
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosMatriculaVO.getId().getMatricula(), "matricula");
								}
							} else if (datosBastidorVO != null) {
								String indices = datosBastidorVO.getId().getBastidor();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								ResultadoBean resultado = eliminarBastidores(indices, new BigDecimal(gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)), new BigDecimal(
										gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)), APLICACION);
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosBastidorVO.getId().getBastidor(), "bastidor");
								}
							} else if (datosNifVO != null) {
								String indices = datosNifVO.getId().getNif();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								ResultadoBean resultado = eliminarNif(indices, idUsuario, APLICACION);
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosNifVO.getId().getNif(), "nif");
								}
							}
						}
					}
				}
			}
		}
		return activa;
	}

	private ResultadoBean eliminarNif(String indices, BigDecimal idUsuario, String origenCambio) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		String[] codSeleccionados = indices.split("-");
		try {
			for (int i = 0; i < codSeleccionados.length; i++) {
				String[] valores = codSeleccionados[i].split(",");

				DatosSensiblesNifVO datosSensiblesNifVO = setDatosSensiblesNifVo(valores[0], valores[1]);

				List<DatosSensiblesNifVO> lista = datosSensiblesNifDao.getNifPorId(datosSensiblesNifVO);

				if (lista != null && !lista.isEmpty()) {
					datosSensiblesNifVO = lista.get(0);
					datosSensiblesNifVO.setEstado(new BigDecimal(0));
					datosSensiblesNifVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));

					datosSensiblesNifDao.borradoLogico(datosSensiblesNifVO);
					evolucionDatosSensiblesNifDao.guardar(dameEvolucionDatosSensiblesNifVO(datosSensiblesNifVO, "BORRADO LOGICO", idUsuario, origenCambio));
				} else {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje("No existen datos para borra del siguiente Nif: " + datosSensiblesNifVO.getId().getNif());
					break;
				}
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de dar de baja los nif, error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha surgido un error a la hora de dar de baja los nif");
		}
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensibles(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, StringBuffer sb, String datoSensible, String numColegiado,
			String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensible);
		String copia = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresConCopia);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible) + " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: "
				+ tipoPersona;
		if (datoSensible != null && !datoSensible.isEmpty() && datoSensible.length() < 17) {
			try {
				resultBean = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
				if (resultBean == null || resultBean.getError()) {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje("Se ha producido un error al enviar el mail");
				}
			} catch (Throwable e) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} else {
			try {
				resultBean = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direccionGrupo, copia, dirOcultas, null, null);
				if (resultBean == null || resultBean.getError()) {
				}
			} catch (Throwable e) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private boolean continuaBastidorBloqueado(String bastidor, BigDecimal idUsuario) {
		boolean resultado = false;
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = getDatosSensiblesBastidorVOPorBastidor(bastidor);
		if (datosSensiblesBastidorVO != null) {
			if (datosSensiblesBastidorVO.getTiempoRestauracion() != null && datosSensiblesBastidorVO.getTiempoRestauracion().compareTo(new Long(0)) != 0) {
				if (datosSensiblesBastidorVO.getFechaOperacion() == null) {
					datosSensiblesBastidorVO.setUsuarioOperacion(idUsuario.longValue());
					datosSensiblesBastidorVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesBastidorDao.actualizar(datosSensiblesBastidorVO);
					resultado = true;
				} else {
					if (fechaOperacionSuperada(new BigDecimal(datosSensiblesBastidorVO.getTiempoRestauracion()), datosSensiblesBastidorVO.getFechaOperacion())) {
						datosSensiblesBastidorVO.setEstado(new BigDecimal(0));
						datosSensiblesBastidorVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
						datosSensiblesBastidorDao.actualizar(datosSensiblesBastidorVO);
						utilesDatosSensibles.recargarDatosSensibles();
					} else
						resultado = true;
				}
			} else {
				resultado = true;
			}
		}
		return resultado;
	}

	private ResultadoBean envioCorreoDatosSensiblesCrtRt(TramiteTraficoVO tramiteTraficoVO, DatosSensiblesBastidorVO bastidorVO, StringBuffer sb, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		String tipoTramite = TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite());
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(tramiteTraficoVO.getNumExpediente()).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String destinatario = null;
		String destinatarioOculto = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresOculto);

		if (TipoBastidorSantander.RT.getValorEnum().equals(bastidorVO.getTipoBastidorSantander().toString())) {
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioRetailAlerta");
		} else {
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioCartAlerta");
		}

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(bastidorVO.getTipoBastidor().toString());
		subject += " (" + bastidorVO.getId().getBastidor() + ") - Colegiado: " + tramiteTraficoVO.getNumColegiado() + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, destinatario, null, destinatarioOculto, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + destinatario + " y con copia oculta a: "
				+ destinatarioOculto);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorVN(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleVN);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorVO(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleVO);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorDM(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleDM);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorFI(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleFI);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorLE(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleLE);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorRE(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleRE);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, direccionGrupo, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private ResultadoBean envioCorreoDatosSensiblesBastidorFR(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleFR);
		String dirSantander = gestorPropiedades.valorPropertie(destinatarioSantanderOperDatoSensibleFR);
		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensibleFR);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		try {
			resultBean = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes("UTF-8"), "UTF-8"), null, null, subject, dirSantander, null, dirOcultas, null, null);
			if (resultBean == null || resultBean.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Se ha producido un error al enviar el mail");
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + dirSantander + ";; y con copia oculta a: "
				+ dirOcultas);
		return resultBean;
	}

	private boolean continuaNifBloqueado(String nif, BigDecimal idUsuario) {
		boolean resultado = false;
		DatosSensiblesNifVO datosSensiblesNifVO = getDatosSensiblesNifPorNif(nif);

		if (datosSensiblesNifVO != null) {
			if (datosSensiblesNifVO.getTiempoRestauracion() != null && datosSensiblesNifVO.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
				if (datosSensiblesNifVO.getFechaOperacion() == null) {
					datosSensiblesNifVO.setUsuarioOperacion(idUsuario);
					datosSensiblesNifVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesNifDao.actualizar(datosSensiblesNifVO);
					resultado = true;
				} else if (fechaOperacionSuperada(datosSensiblesNifVO.getTiempoRestauracion(), datosSensiblesNifVO.getFechaOperacion())) {
					datosSensiblesNifVO.setEstado(new BigDecimal(0));
					datosSensiblesNifVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesNifDao.actualizar(datosSensiblesNifVO);
					resultado = true;
				}
			} else {
				resultado = true;
			}
		}
		return resultado;
	}

	private void enviarCorreoAvisoLiberacionSantander(String datoSensible, String elemento) {
		String subject = null;
		String recipent = null;

		try {
			subject = gestorPropiedades.valorPropertie(ASUNTO_LIBERACION_DATO_SENSIBLE);
			String articulo = "";

			articulo = "matricula".equals(elemento) ? "La" : "El";

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append(articulo + " " + elemento + " " + datoSensible + " ha sido liberado fuera del horario previsto" + "<br>");

			recipent = gestorPropiedades.valorPropertie(EMAIL_DESTINATARIO_LIBERACION_DATO_SENSIBLE);
			ResultadoBean resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo de aviso de liberacion de bastidores " + resultado.getMensaje());
			}
		} catch (Throwable e) {
			log.error("No se ha enviado el correo de aviso de liberacion de bastidores ", e);
		}
	}

	protected ResultadoBean eliminarMatricula(String indices, BigDecimal idUsuario) {
		String[] codSeleccionados = indices.split("-");
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");

			DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = setDatosSensiblesMatriculaVo(valores[0], valores[1]);

			List<DatosSensiblesMatriculaVO> lista = datosSensiblesMatriculaDao.getMatriculaPorId(datosSensiblesMatriculaVO);

			if (lista != null && !lista.isEmpty()) {
				datosSensiblesMatriculaVO = lista.get(0);
				datosSensiblesMatriculaVO.setEstado(new BigDecimal(0));
				datosSensiblesMatriculaVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));

				datosSensiblesMatriculaDao.borradoLogico(datosSensiblesMatriculaVO);
				evolucionDatosSensiblesMatriculaDao.guardar(dameEvolucionDatosSensiblesMatriculaVO(datosSensiblesMatriculaVO, "BORRADO LOGICO", idUsuario, APLICACION));
			} else {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("No existen datos para borra de la siguiente Matricula: " + datosSensiblesMatriculaVO.getId().getMatricula());
			}
		}
		return resultBean;
	}

	private DatosSensiblesMatriculaVO setDatosSensiblesMatriculaVo(String matricula, String grupo) {
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = new DatosSensiblesMatriculaVO();
		DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
		id.setMatricula(matricula);
		id.setIdGrupo(grupo);
		datosSensiblesMatriculaVO.setId(id);
		return datosSensiblesMatriculaVO;
	}

	private DatosSensiblesBastidorVO setDatosSensiblesBastidorVO(String bastidor, String grupo) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(bastidor);
		id.setIdGrupo(grupo);
		datosSensiblesBastidorVO.setId(id);
		return datosSensiblesBastidorVO;
	}

	private DatosSensiblesNifVO setDatosSensiblesNifVo(String nif, String grupo) {
		DatosSensiblesNifVO datosSensiblesNifVO = new DatosSensiblesNifVO();
		DatosSensiblesNifPK id = new DatosSensiblesNifPK();
		id.setNif(nif);
		id.setIdGrupo(grupo);
		datosSensiblesNifVO.setId(id);
		return datosSensiblesNifVO;
	}

	private EvolucionDatosSensiblesBastidorVO dameEvolucionDatosSensiblesBastidorVO(DatosSensiblesBastidorVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesBastidorVO resultado = new EvolucionDatosSensiblesBastidorVO();
		resultado.setBastidor(origen.getId().getBastidor());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getBastidor(), origen.getId().getIdGrupo(), TiposDatosSensibles.Bastidor));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private EvolucionDatosSensiblesMatriculaVO dameEvolucionDatosSensiblesMatriculaVO(DatosSensiblesMatriculaVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesMatriculaVO resultado = new EvolucionDatosSensiblesMatriculaVO();
		resultado.setMatricula(origen.getId().getMatricula());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getMatricula(), origen.getId().getIdGrupo(), TiposDatosSensibles.Matricula));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private EvolucionDatosSensiblesNifVO dameEvolucionDatosSensiblesNifVO(DatosSensiblesNifVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesNifVO resultado = new EvolucionDatosSensiblesNifVO();
		resultado.setNif(origen.getId().getNif());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getNif(), origen.getId().getIdGrupo(), TiposDatosSensibles.Nif));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private ResultadoBean crearSolicitudesProcesos(String xmlEnviar, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		ArrayList<String> listaErrorres = new ArrayList<String>();

		try {
			ResultadoBean resultadoProcesoA9 = servicioCola.crearSolicitud(null, ProcesosEnum.SEA_ENVIO_DS.toString(), gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), "N/A", idUsuario,
					idContrato, xmlEnviar);
			if (resultadoProcesoA9 != null && resultadoProcesoA9.getError() && resultadoProcesoA9.getListaMensajes() != null && !resultadoProcesoA9.getListaMensajes().isEmpty()) {
				listaErrorres.add(resultadoProcesoA9.getListaMensajes().get(0));
			}

			if (listaErrorres != null && !listaErrorres.isEmpty()) {
				resultBean.setError(true);
				resultBean.setListaMensajes(listaErrorres);
			}
		} catch (Throwable e) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error al crear la solicitud de datos sensibles.");
			log.error("Error al crear la solicitud de datos sensibles.", e);
		}
		return resultBean;
	}

	private ResultadoBean eliminarBastidores(String indices, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		try {
			result = eliminarBastidor(indices, idContrato, idUsuario, origenCambio);
			if (result != null && !result.getError()) {
				result = envioMail("Se ha dado de baja el siguiente bastidor: " + indices.substring(0, 17) + "<br>", asuntoCorreo_Baja, null);
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error a la hora de eliminar el bastidor");
			log.error("Error a la hora de eliminar el bastidor", e);
		}
		return result;
	}

	private ResultadoBean envioMail(String texto, String asunto, String tipo) {
		ResultadoBean resultEnvio = new ResultadoBean(Boolean.FALSE);
		String subject = gestorPropiedades.valorPropertie(asunto);
		if (tipo != null) {
			subject += " del Tipo " + tipo;
		}
		String destinatario = gestorPropiedades.valorPropertie(destinatarioCorreoBastidores);
		String destinatarioOculto = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresOculto);

		try {
			resultEnvio = servicioCorreo.enviarCorreo(texto, null, null, subject, destinatario, null, destinatarioOculto, null, null);
			if (resultEnvio == null || resultEnvio.getError()) {
				resultEnvio.setError(Boolean.TRUE);
				resultEnvio.setMensaje("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .");
			}
		} catch (Throwable e) {
			resultEnvio.setError(Boolean.TRUE);
			resultEnvio.setMensaje("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .");
			log.error("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .");
		}
		return resultEnvio;

	}

	private ResultadoBean eliminarBastidor(String indices, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		}
		String[] codSeleccionados = indices.split("-");
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");
			DatosSensiblesBastidorVO datosSensiblesBastidorVO = setDatosSensiblesBastidorVO(valores[0], valores[1]);
			List<DatosSensiblesBastidorVO> lista = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);
			if (lista != null && !lista.isEmpty()) {
				datosSensiblesBastidorVO = lista.get(0);
				datosSensiblesBastidorVO.setEstado(new BigDecimal(0));
				datosSensiblesBastidorVO.setFechaBaja(Calendar.getInstance().getTime());
				String xmlEnviar = valores[0] + "_" + datosSensiblesBastidorVO.getGrupo().getIdGrupo();
				log.info("Dentro de eliminar Bastidores");
				datosSensiblesBastidorDao.borradoLogico(datosSensiblesBastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "BORRADO LOGICO", idUsuario, origenCambio));
				if (esPosibleEncolar) {
					result = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No existen datos para borra del siguiente Bastidor: " + datosSensiblesBastidorVO.getId().getBastidor());
				break;
			}
		}
		return result;
	}

	private BigDecimal dameEstadoAnterior(String numero, String idGrupo, TiposDatosSensibles tipo) {
		if (TiposDatosSensibles.Bastidor.equals(tipo)) {
			List<EvolucionDatosSensiblesBastidorVO> lista = evolucionDatosSensiblesBastidorDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		} else if (TiposDatosSensibles.Matricula.equals(tipo)) {
			List<EvolucionDatosSensiblesMatriculaVO> lista = evolucionDatosSensiblesMatriculaDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		} else if (TiposDatosSensibles.Nif.equals(tipo)) {
			List<EvolucionDatosSensiblesNifVO> lista = evolucionDatosSensiblesNifDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		}
		return null;
	}

}