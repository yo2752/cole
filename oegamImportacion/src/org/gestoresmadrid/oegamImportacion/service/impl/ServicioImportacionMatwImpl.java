package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGAMatw;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionMatw;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIntervinienteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIvtmMatriculacionImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioLiberacionImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionMatwImpl implements ServicioImportacionMatw {

	private static final long serialVersionUID = 8801593928953570645L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionMatwImpl.class);

	@Autowired
	ConversionFormatoImpGAMatw conversionFormatoGAMatw;

	@Autowired
	ServicioValidacionesImportacion servicioValidaciones;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioIntervinienteTraficoImportacion servicioIntervinienteTrafico;

	@Autowired
	ServicioIvtmMatriculacionImportacion servicioIvtmMatriculacion;

	@Autowired
	ServicioLiberacionImportacion servicioLiberacion;

	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Boolean tienePermisoLiberarEEFF, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			log.error("INICIO CONVERTIR GA MATW del colegiado " + contrato.getColegiado().getNumColegiado());
			resultado = conversionFormatoGAMatw.convertirFicheroFormatoGA(fichero, contrato, tienePermisoAdmin);
			log.error("FIN CONVERTIR GA MATW del colegiado " + contrato.getColegiado().getNumColegiado());
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGAMatw.convertirFormatoGAToVO(resultado.getFormatoOegam2Matw(), contrato, tienePermisoAdmin, tipoCreacion, tienePermisoLiberarEEFF, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de matw, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de matw.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafMatrVO tramiteMatrVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicado(tramiteMatrVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteMatw(tramiteMatrVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteMatrVO.getVehiculo() != null) {
						ResultadoValidacionImporBean resultValVehiculo = servicioValidaciones.validarVehiculo(tramiteMatrVO.getVehiculo(), TipoTramiteTrafico.Matriculacion.getValorEnum());
						if (resultValVehiculo.getErrorVehiculo() != null && resultValVehiculo.getErrorVehiculo()) {
							tramiteMatrVO.setVehiculo(null);
						} else if (resultValVehiculo.getErrorDireccion() != null && resultValVehiculo.getErrorDireccion()) {
							tramiteMatrVO.getVehiculo().setDireccion(null);
						}
						gestionarResultado(resultado, resultValVehiculo);
					}
					if (tramiteMatrVO.getIntervinienteTraficos() != null && !tramiteMatrVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteMatrVO.getIntervinienteTraficos()) {
							if (intervinienteTraficoVO.getId().getTipoInterviniente() != null) {
								ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.Matriculacion.getValorEnum());
								if (resultValIntrv.getDireccion() != null) {
									intervinienteTraficoVO.setDireccion(resultValIntrv.getDireccion());
								}
								gestionarResultado(resultado, resultValIntrv);
							}
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

	private ResultadoValidacionImporBean validarPosibleDuplicado(TramiteTrafMatrVO tramiteMatrVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String bastidor = "";
		String nif = "";
		if (tramiteMatrVO.getVehiculo() != null) {
			bastidor = tramiteMatrVO.getVehiculo().getBastidor();
		}

		if (tramiteMatrVO.getIntervinienteTraficosAsList() != null && !tramiteMatrVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteMatrVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
					nif = interviente.getId().getNif();
					break;
				}
			}
		}

		if (StringUtils.isNotBlank(bastidor) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicado(bastidor, nif, tramiteMatrVO.getTipoTramite(), tramiteMatrVO.getNumColegiado());
		}
		return resultado;
	}

	@Override
	public ResultadoImportacionBean guardarImportacion(TramiteTrafMatrVO tramiteMatrVO, Long idUsuario, Boolean tienePermisoLiberarEEFF) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			String mensajeErrorVeh = "";
			String mensajeErrorInterv = "";
			String mensajeErrorTram = "";
			String mensajeErrorLib = "";
			String mensajeErrorIvtm = "";
			String mensajeErrorTasa = "";
			ResultadoBean resultCrearTram = new ResultadoBean(Boolean.FALSE);

			// Por si falla al crear el numero de expediente
			for (int i = 1; i <= 10; i++) {
				try {
					resultCrearTram = servicioTramiteTrafico.crearTramite(tramiteMatrVO.getNumColegiado(), tramiteMatrVO.getTipoTramite(), tramiteMatrVO.getContrato().getIdContrato(), idUsuario,
							tramiteMatrVO.getFechaAlta(), tramiteMatrVO.getJefaturaTrafico(), tramiteMatrVO.getIdTipoCreacion());
				} catch (Exception e) {
					resultCrearTram.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de crear el tramite.");
					if (i == 10) {
						log.error("Ha sucedido un error a la hora de crear el tramite, error: ", e);
					}
				}
				if (resultCrearTram != null && !resultCrearTram.getError()) {
					break;
				}
			}

			if (!resultado.getError()) {
				tramiteMatrVO.setNumExpediente(resultCrearTram.getNumExpediente());
				resultado.setNumExpediente(resultCrearTram.getNumExpediente());
				if (tramiteMatrVO.getVehiculo() != null) {
					ResultadoBean resultGVeh = servicioVehiculo.guardarVehiculoMatriculacion(tramiteMatrVO.getVehiculo(), tramiteMatrVO.getNumExpediente(), tramiteMatrVO.getContrato(), idUsuario,
							tramiteMatrVO.getFechaAlta());
					mensajeErrorVeh = gestionarResultado(resultado, resultGVeh, "El vehiculo se ha guardado con las siguientes incidencias: ");
					if (!resultGVeh.getError()) {
						tramiteMatrVO.getVehiculo().setIdVehiculo(resultGVeh.getIdVehiculo());
					}
					if (tramiteMatrVO.getIntervinienteTraficos() != null && !tramiteMatrVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervTrafVO : tramiteMatrVO.getIntervinienteTraficos()) {
							try {
								ResultadoBean resultGInterv = servicioIntervinienteTrafico.guardarInterviniente(intervTrafVO, tramiteMatrVO.getNumExpediente(), tramiteMatrVO.getContrato(), idUsuario,
										tramiteMatrVO.getTipoTramite());
								if (resultGInterv.getError()) {
									mensajeErrorInterv += resultGInterv.getMensaje();
								}
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + " del trámite: "
										+ tramiteMatrVO.getNumExpediente() + ", error: ", e);
								mensajeErrorInterv += "Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + ". ";
							}
						}
					}
					if (tramiteMatrVO.getTasa() != null) {
						mensajeErrorTasa = servicioTasa.asignarTasa(tramiteMatrVO.getTasa(), tramiteMatrVO.getNumExpediente(), tramiteMatrVO.getUsuario().getIdUsuario());
					}
					ResultadoBean resultGTram = servicioTramiteTrafico.guardarTramiteMatw(tramiteMatrVO, idUsuario);
					if (resultGTram.getError()) {
						mensajeErrorTram = resultGTram.getMensaje();
					} else {
						if (tienePermisoLiberarEEFF && tramiteMatrVO.getLiberacionEEFF() != null) {
							mensajeErrorLib = servicioLiberacion.guardarDatosLiberacion(tramiteMatrVO);
						}
						if (tramiteMatrVO.getIvtmMatriculacionVO() != null) {
							tramiteMatrVO.getIvtmMatriculacionVO().setNumExpediente(tramiteMatrVO.getNumExpediente());
							if (tramiteMatrVO.getVehiculo() != null) {
								tramiteMatrVO.getIvtmMatriculacionVO().setBastidor(tramiteMatrVO.getVehiculo().getBastidor());
							}
							mensajeErrorIvtm = servicioIvtmMatriculacion.guardarIvtm(tramiteMatrVO.getIvtmMatriculacionVO());
						}
					}
				}
				resultado.setMensaje(tratarMensajes(mensajeErrorTram, mensajeErrorInterv, mensajeErrorVeh, mensajeErrorLib, mensajeErrorIvtm, mensajeErrorTasa));
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

	private String tratarMensajes(String mensajeErrorTram, String mensajeErrorInterv, String mensajeErrorVeh, String mensajeErrorLib, String mensajeErrorIvtm, String mensajeErrorTasa) {
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
		if (mensajeErrorLib != null && !mensajeErrorLib.isEmpty()) {
			mensaje += mensajeErrorLib;
		}
		if (mensajeErrorIvtm != null && !mensajeErrorIvtm.isEmpty()) {
			mensaje += mensajeErrorIvtm;
		}
		if (mensajeErrorTasa != null && !mensajeErrorTasa.isEmpty()) {
			mensaje += mensajeErrorTasa;
		}
		return mensaje;
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
}
