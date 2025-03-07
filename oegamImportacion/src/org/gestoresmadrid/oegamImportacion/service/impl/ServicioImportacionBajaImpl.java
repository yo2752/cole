package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGABaja;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionBaja;
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
public class ServicioImportacionBajaImpl implements ServicioImportacionBaja {

	private static final long serialVersionUID = 2595524976554102986L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionBajaImpl.class);

	@Autowired
	ConversionFormatoImpGABaja conversionFormatoGABaja;

	@Autowired
	ServicioValidacionesImportacion servicioValidaciones;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioIntervinienteTraficoImportacion servicioIntervinienteTrafico;

	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			log.error("INICIO CONVERTIR GA Baja del colegiado " + contrato.getColegiado().getNumColegiado());
			resultado = conversionFormatoGABaja.convertirFicheroFormatoGA(fichero, contrato, tienePermisoAdmin);
			log.error("FIN CONVERTIR GA Baja del colegiado " + contrato.getColegiado().getNumColegiado());
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGABaja.convertirFormatoGAToVO(resultado.getFormatoOegam2Baja(), contrato, tienePermisoAdmin, tipoCreacion, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de baja.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafBajaVO tramiteBajaVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicado(tramiteBajaVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteBaja(tramiteBajaVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteBajaVO.getVehiculo() != null) {
						ResultadoValidacionImporBean resultValVehiculo = servicioValidaciones.validarVehiculo(tramiteBajaVO.getVehiculo(), TipoTramiteTrafico.Baja.getValorEnum());
						if (resultValVehiculo.getErrorVehiculo() != null && resultValVehiculo.getErrorVehiculo()) {
							tramiteBajaVO.setVehiculo(null);
						} else if (resultValVehiculo.getErrorDireccion() != null && resultValVehiculo.getErrorDireccion()) {
							tramiteBajaVO.getVehiculo().setDireccion(null);
						}
						gestionarResultado(resultado, resultValVehiculo);
					}
					if (tramiteBajaVO.getIntervinienteTraficos() != null && !tramiteBajaVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteBajaVO.getIntervinienteTraficos()) {
							ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.Baja.getValorEnum());
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

	@Override
	public ResultadoImportacionBean guardarImportacion(TramiteTrafBajaVO tramiteBajaVO, Long idUsuario) {
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
					resultCrearTram = servicioTramiteTrafico.crearTramite(tramiteBajaVO.getNumColegiado(), tramiteBajaVO.getTipoTramite(), tramiteBajaVO.getContrato().getIdContrato(), idUsuario,
							tramiteBajaVO.getFechaAlta(), tramiteBajaVO.getJefaturaTrafico(), tramiteBajaVO.getIdTipoCreacion());
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
				BigDecimal numExpediente = resultCrearTram.getNumExpediente();
				tramiteBajaVO.setNumExpediente(numExpediente);
				resultado.setNumExpediente(numExpediente);
				if (tramiteBajaVO.getVehiculo() != null) {
					ResultadoBean resultGVeh = servicioVehiculo.guardarVehiculo(tramiteBajaVO.getVehiculo(), tramiteBajaVO.getNumExpediente(), tramiteBajaVO.getContrato(), idUsuario, tramiteBajaVO
							.getTipoTramite(), false);
					mensajeErrorVeh = gestionarResultado(resultado, resultGVeh, "El vehiculo se ha guardado con las siguientes incidencias: ");
					if (!resultGVeh.getError()) {
						tramiteBajaVO.getVehiculo().setIdVehiculo(resultGVeh.getIdVehiculo());
					} else {
						tramiteBajaVO.setVehiculo(null);
					}
					if (tramiteBajaVO.getIntervinienteTraficos() != null && !tramiteBajaVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervTrafVO : tramiteBajaVO.getIntervinienteTraficos()) {
							try {
								ResultadoBean resultGInterv = servicioIntervinienteTrafico.guardarInterviniente(intervTrafVO, tramiteBajaVO.getNumExpediente(), tramiteBajaVO.getContrato(), idUsuario,
										tramiteBajaVO.getTipoTramite());
								if (resultGInterv.getError()) {
									mensajeErrorInterv += resultGInterv.getMensaje();
								}
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + " del trámite: "
										+ tramiteBajaVO.getNumExpediente() + ", error: ", e);
								mensajeErrorInterv += "Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + ". ";
							}
						}
					}
					if (tramiteBajaVO.getTasa() != null) {
						mensajeErrorTasa += servicioTasa.asignarTasa(tramiteBajaVO.getTasa(), tramiteBajaVO.getNumExpediente(), tramiteBajaVO.getUsuario().getIdUsuario());
					}
					if (tramiteBajaVO.getTasaDuplicado() != null) {
						TasaVO tasaDuplicado = servicioTasa.getTasa(tramiteBajaVO.getTasaDuplicado(), TipoTasa.CuatroUno.getValorEnum());
						mensajeErrorTasa += servicioTasa.asignarTasa(tasaDuplicado, tramiteBajaVO.getNumExpediente(), tramiteBajaVO.getUsuario().getIdUsuario());
					}
					ResultadoBean resultGTram = servicioTramiteTrafico.guardarTramiteBaja(tramiteBajaVO, idUsuario);
					if (resultGTram.getError()) {
						mensajeErrorTram = resultGTram.getMensaje();
					}
				}
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

	private ResultadoValidacionImporBean validarPosibleDuplicado(TramiteTrafBajaVO tramiteBajaVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String bastidor = "";
		String nif = "";
		if (tramiteBajaVO.getVehiculo() != null) {
			bastidor = tramiteBajaVO.getVehiculo().getBastidor();
		}

		if (tramiteBajaVO.getIntervinienteTraficosAsList() != null && !tramiteBajaVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteBajaVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
					nif = interviente.getId().getNif();
					break;
				}
			}
		}

		if (StringUtils.isNotBlank(bastidor) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicado(bastidor, nif, tramiteBajaVO.getTipoTramite(), tramiteBajaVO.getNumColegiado());
		}
		return resultado;
	}
}
