package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGACtit;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionCtit;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIntervinienteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionCtitImpl implements ServicioImportacionCtit {

	private static final long serialVersionUID = 2805913894206057543L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionCtitImpl.class);

	@Autowired
	ConversionFormatoImpGACtit conversionFormatoGACtit;

	@Autowired
	ServicioValidacionesImportacion servicioValidaciones;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Autowired
	ServicioIntervinienteTraficoImportacion servicioIntervinienteTrafico;

	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			log.error("INICIO CONVERTIR GA CTIT del colegiado " + contrato.getColegiado().getNumColegiado());
			resultado = conversionFormatoGACtit.convertirFicheroFormatoGA(fichero, contrato, tienePermisoAdmin);
			log.error("FIN CONVERTIR GA CTIT del colegiado " + contrato.getColegiado().getNumColegiado());
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGACtit.convertirFormatoGAToVO(resultado.getFormatoOegam2Ctit(), contrato, tienePermisoAdmin, tipoCreacion, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de ctit.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafTranVO tramiteCtitVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicado(tramiteCtitVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteCtit(tramiteCtitVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteCtitVO.getVehiculo() != null) {
						ResultadoValidacionImporBean resultValVehiculo = servicioValidaciones.validarVehiculo(tramiteCtitVO.getVehiculo(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
						if (resultValVehiculo.getErrorVehiculo() != null && resultValVehiculo.getErrorVehiculo()) {
							tramiteCtitVO.setVehiculo(null);
						} else if (resultValVehiculo.getErrorDireccion() != null && resultValVehiculo.getErrorDireccion()) {
							tramiteCtitVO.getVehiculo().setDireccion(null);
						}
						gestionarResultado(resultado, resultValVehiculo);
					}
					if (tramiteCtitVO.getIntervinienteTraficos() != null && !tramiteCtitVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteCtitVO.getIntervinienteTraficos()) {
							ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
							if (resultValIntrv.getDireccion() != null) {
								intervinienteTraficoVO.setDireccion(resultValIntrv.getDireccion());
							}
							gestionarResultado(resultado, resultValIntrv);
						}
					}
				} else {
					return resultValTram;
				}
			} else {
				return resultValTram;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite.");
		}

		return resultado;
	}

	@Override
	public ResultadoImportacionBean guardarImportacion(TramiteTrafTranVO tramiteTranVO, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			String mensajeErrorVeh = "";
			String mensajeErrorInterv = "";
			String mensajeErrorTram = "";
			String mensajeErrorTasa = "";
			ResultadoBean resultCrearTram = new ResultadoBean(Boolean.FALSE);

			// Por si falla al crear el numero de expediente
			for (int i = 1; i <= 10; i++) {
				try {
					log.error("INICIO CREAR tramite con matricula: " + tramiteTranVO.getVehiculo().getMatricula() + " en iteración " + i);
					resultCrearTram = servicioTramiteTrafico.crearTramite(tramiteTranVO.getNumColegiado(), tramiteTranVO.getTipoTramite(), tramiteTranVO.getContrato().getIdContrato(), idUsuario,
							tramiteTranVO.getFechaAlta(), tramiteTranVO.getJefaturaTrafico(), tramiteTranVO.getIdTipoCreacion());
				} catch (Exception e) {
					resultCrearTram.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de crear el tramite.");
					log.error("FALLO CREAR tramite con matricula: " + tramiteTranVO.getVehiculo().getMatricula() + " en iteración " + i);
					if (i == 10) {
						log.error("Ha sucedido un error a la hora de crear el tramite, error: ", e);
					}
				}
				if (resultCrearTram != null && !resultCrearTram.getError()) {
					log.error("OK CREAR tramite con matricula: " + tramiteTranVO.getVehiculo().getMatricula() + " en iteración " + i);
					break;
				}
			}

			if (!resultado.getError()) {
				log.error("INICIO GUARDAR EN METODO tramite con matricula: " + tramiteTranVO.getVehiculo().getMatricula());
				tramiteTranVO.setNumExpediente(resultCrearTram.getNumExpediente());
				resultado.setNumExpediente(resultCrearTram.getNumExpediente());
				if (tramiteTranVO.getVehiculo() != null) {
					ResultadoBean resultGVeh = servicioVehiculo.guardarVehiculo(tramiteTranVO.getVehiculo(), tramiteTranVO.getNumExpediente(), tramiteTranVO.getContrato(), idUsuario, tramiteTranVO
							.getTipoTramite(), false);
					mensajeErrorVeh = gestionarResultado(resultado, resultGVeh, "El vehiculo se ha guardado con las siguientes incidencias: ");
					if (!resultGVeh.getError()) {
						tramiteTranVO.getVehiculo().setIdVehiculo(resultGVeh.getIdVehiculo());
					}
					if (tramiteTranVO.getIntervinienteTraficos() != null && !tramiteTranVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervTrafVO : tramiteTranVO.getIntervinienteTraficos()) {
							try {
								ResultadoBean resultGInterv = servicioIntervinienteTrafico.guardarInterviniente(intervTrafVO, tramiteTranVO.getNumExpediente(), tramiteTranVO.getContrato(), idUsuario,
										tramiteTranVO.getTipoTramite());
								if (resultGInterv.getError()) {
									mensajeErrorInterv += resultGInterv.getMensaje();
								}
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + " del trámite: "
										+ tramiteTranVO.getNumExpediente() + ", error: ", e);
								mensajeErrorInterv += "Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + ". ";
							}
						}
					}
					if (tramiteTranVO.getTasa() != null) {
						mensajeErrorTasa += servicioTasa.asignarTasa(tramiteTranVO.getTasa(), tramiteTranVO.getNumExpediente(), tramiteTranVO.getUsuario().getIdUsuario());
					}
					if (tramiteTranVO.getTasa1() != null) {
						mensajeErrorTasa += servicioTasa.asignarTasa(tramiteTranVO.getTasa1(), tramiteTranVO.getNumExpediente(), tramiteTranVO.getUsuario().getIdUsuario());
					}
					if (tramiteTranVO.getTasa2() != null) {
						mensajeErrorTasa += servicioTasa.asignarTasa(tramiteTranVO.getTasa2(), tramiteTranVO.getNumExpediente(), tramiteTranVO.getUsuario().getIdUsuario());
					}
					ResultadoBean resultGTram = servicioTramiteTrafico.guardarTramiteCtit(tramiteTranVO, idUsuario);
					if (resultGTram.getError()) {
						mensajeErrorTram = resultGTram.getMensaje();
					}
				}
				log.error("FIN GUARDAR EN METODO tramite con matricula: " + tramiteTranVO.getVehiculo().getMatricula());
				resultado.setMensaje(tratarMensajes(mensajeErrorTram, mensajeErrorInterv, mensajeErrorVeh, mensajeErrorTasa));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCrearTram.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el tramite.");
		}
		return resultado;
	}

	private void gestionarResultado(ResultadoValidacionImporBean resultado, ResultadoValidacionImporBean resultValidaciones) {
		if (resultValidaciones.getListaMensajesAvisos() != null && !resultValidaciones.getListaMensajesAvisos().isEmpty()) {
			for (String mensajeVal : resultValidaciones.getListaMensajesAvisos()) {
				resultado.addListaMensajeAvisos(mensajeVal);
			}
		}
		if (resultValidaciones.getListaMensajeError() != null && !resultValidaciones.getListaMensajeError().isEmpty()) {
			for (String mensajeError : resultValidaciones.getListaMensajeError()) {
				resultado.addListaMensajeError(mensajeError);
			}
			resultado.setError(Boolean.TRUE);
		}
	}

	private String tratarMensajes(String mensajeErrorTram, String mensajeErrorInterv, String mensajeErrorVeh, String mensajeErrorTasa) {
		String mensaje = "";
		if (mensajeErrorTram != null && !mensajeErrorTram.isEmpty()) {
			mensaje = mensajeErrorTram;
		}
		if (mensajeErrorInterv != null && !mensajeErrorInterv.isEmpty()) {
			mensaje += "Existen errores a la hora de guardar los intervienientes: " + mensajeErrorInterv;
		}
		if (mensajeErrorVeh != null && !mensajeErrorVeh.isEmpty()) {
			mensaje += mensajeErrorVeh;
		}
		if (mensajeErrorTasa != null && !mensajeErrorTasa.isEmpty()) {
			mensaje += mensajeErrorTasa;
		}
		return mensaje;
	}

	private String gestionarResultado(ResultadoImportacionBean resultado, ResultadoBean resultTratar, String mensajeInicial) {
		if (resultTratar.getError()) {
			return resultTratar.getMensaje();
		} else if (resultTratar.getListaMensaje() != null && !resultTratar.getListaMensaje().isEmpty()) {
			for (String mensaje : resultTratar.getListaMensaje()) {
				mensajeInicial += mensaje;
			}
			return mensajeInicial;
		}
		return "";
	}

	private ResultadoValidacionImporBean validarPosibleDuplicado(TramiteTrafTranVO tramiteCtitVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String bastidor = "";
		String matricula = "";
		String nif = "";
		if (tramiteCtitVO.getVehiculo() != null) {
			bastidor = tramiteCtitVO.getVehiculo().getBastidor();
			matricula = tramiteCtitVO.getVehiculo().getBastidor();
		}

		if (tramiteCtitVO.getIntervinienteTraficosAsList() != null && !tramiteCtitVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteCtitVO.getIntervinienteTraficosAsList()) {
				if (TipoTransferencia.tipo5.getValorEnum().equals(tramiteCtitVO.getTipoTransferencia())) {
					if (TipoInterviniente.TransmitenteTrafico.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
						nif = interviente.getId().getNif();
						break;
					}
				} else {
					if (TipoInterviniente.Adquiriente.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
						nif = interviente.getId().getNif();
						break;
					}
				}
			}
		}

		if (StringUtils.isNotBlank(bastidor) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicadoCtit(bastidor, matricula, nif, tramiteCtitVO.getTipoTramite(), tramiteCtitVO.getNumColegiado(), tramiteCtitVO
					.getTipoTransferencia());
		}
		return resultado;
	}
}
