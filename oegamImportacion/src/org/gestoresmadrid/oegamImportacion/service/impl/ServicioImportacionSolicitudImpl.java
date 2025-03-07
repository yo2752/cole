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
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoSolInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGAInteve;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionSolicitud;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIntervinienteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionSolicitudImpl implements ServicioImportacionSolicitud {

	private static final long serialVersionUID = -4153158937298875982L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionSolicitudImpl.class);

	@Autowired
	ConversionFormatoImpGAInteve conversionFormatoGAInteve;

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

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	TramiteTraficoSolInteveDao tramiteTraficoSolInteveDao;
	
	@Autowired
	TramiteTraficoDao tramiteTraficoDao;
	
	@Autowired
	ServicioContratoImportacion servicioContrato;
	
	
	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato,
			Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			log.error("INICIO CONVERTIR GA Solicitud del colegiado " + contrato.getColegiado().getNumColegiado());
			resultado = conversionFormatoGAInteve.convertirFicheroFormatoGA(fichero, contrato, tienePermisoAdmin);
			log.error("FIN CONVERTIR GA Solicitud del colegiado " + contrato.getColegiado().getNumColegiado());
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGAInteve.convertirFormatoGAToVO(resultado.getFormatoOegam2Solicitud(), contrato, tienePermisoAdmin, tipoCreacion, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de baja.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteSolicitud(TramiteTraficoInteveVO tramiteInteveVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicado(tramiteInteveVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteSolicitud(tramiteInteveVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteInteveVO.getIntervinienteTraficos() != null && !tramiteInteveVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteInteveVO.getIntervinienteTraficos()) {
							ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.Inteve.getValorEnum());
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
	public ResultadoImportacionBean guardarImportacion(TramiteTraficoInteveVO tramiteInteveVO, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			String mensajeErrorVeh = "";
			String mensajeErrorInterv = "";
			String mensajeErrorTram = "";
			String mensaje = "";
			
			ResultadoBean resultCrearTram = new ResultadoBean(Boolean.FALSE);
			// Por si falla al crear el numero de expediente
			for (int i = 1; i <= 10; i++) {
				try {
					resultCrearTram = servicioTramiteTrafico.crearTramite(tramiteInteveVO.getNumColegiado(), tramiteInteveVO.getTipoTramite(), tramiteInteveVO.getContrato().getIdContrato(), idUsuario,
							tramiteInteveVO.getFechaAlta(), tramiteInteveVO.getJefaturaTrafico(), tramiteInteveVO.getIdTipoCreacion());
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
				tramiteInteveVO.setNumExpediente(numExpediente);
				resultado.setNumExpediente(numExpediente);
				if (tramiteInteveVO.getIntervinienteTraficos() != null && !tramiteInteveVO.getIntervinienteTraficos().isEmpty()) {
					for (IntervinienteTraficoVO intervTrafVO : tramiteInteveVO.getIntervinienteTraficos()) {
						try {
							ResultadoBean resultGInterv = servicioIntervinienteTrafico.guardarInterviniente(intervTrafVO, tramiteInteveVO.getNumExpediente(), tramiteInteveVO.getContrato(), idUsuario,
									tramiteInteveVO.getTipoTramite());
							if (resultGInterv.getError()) {
								mensajeErrorInterv += resultGInterv.getMensaje();
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + " del trámite: "
									+ tramiteInteveVO.getNumExpediente() + ", error: ", e);
							mensajeErrorInterv += "Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + ". ";
						}
					}
				}
				if(tramiteInteveVO.getTramitesInteves() != null && !tramiteInteveVO.getTramitesInteves().isEmpty()) {
					for(TramiteTraficoSolInteveVO solInteveVo : tramiteInteveVO.getTramitesInteves()) {
						try {
							solInteveVo.setNumExpediente(numExpediente);
							if(StringUtils.isNotBlank(solInteveVo.getCodigoTasa())) {
								mensaje += gestionarTasa(solInteveVo, tramiteInteveVO.getContrato().getIdContrato(), idUsuario);
							} else {
								if(StringUtils.isNotBlank(solInteveVo.getMatricula())){
									mensaje += "- La solicitud para la Matricula " +solInteveVo.getMatricula()+  " se va a importar sin tasa, ya que no existe. ";
								}else if(StringUtils.isNotBlank(solInteveVo.getBastidor())){
									mensaje += "- La solicitud para el Bastidor " +solInteveVo.getBastidor()+  " se va a importar sin tasa, ya que no pexiste. ";
								}else if(StringUtils.isNotBlank(solInteveVo.getNive())){
									mensaje += "- La solicitud para el Nive " +solInteveVo.getNive()+  " se va a importar sin tasa, ya que no existe. ";
								}
							}
							servicioTramiteTrafico.guardarOactualizarTramiteSolInteve(solInteveVo);
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de guardar la solicitud para :" + obtenerDato(solInteveVo)+ ", error: ", e);
							mensajeErrorInterv += "Ha sucedido un error a la hora de guardar la solicitud para :" + obtenerDato(solInteveVo);
						}
					}
				}
				servicioTramiteTrafico.guardarTramiteTraficoConEvolucion(tramiteInteveVO,idUsuario);
				resultado.setMensaje(tratarMensajes(mensajeErrorTram, mensajeErrorInterv, mensajeErrorVeh, mensaje));
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
	

	private String obtenerDato(TramiteTraficoSolInteveVO solInteveVo) {
		if(StringUtils.isNotBlank(solInteveVo.getBastidor())) {
			return " el bastidor: " + solInteveVo.getBastidor();
		} else if(StringUtils.isNotBlank(solInteveVo.getMatricula())) {
			return " la matricula: " + solInteveVo.getMatricula();
		} else if(StringUtils.isNotBlank(solInteveVo.getNive())) {
			return " el nive: " + solInteveVo.getNive();
		}
		return null;
	}

	public String gestionarTasa(TramiteTraficoSolInteveVO solInteveVo, Long idContrato, Long idUsuario) {
		String mensajeErrorTasa = "";
		TasaVO codTasa = servicioTasa.getTasa(solInteveVo.getCodigoTasa(), TipoTasa.CuatroUno.getValorEnum());
		if(codTasa != null) {
			if(codTasa.getNumExpediente() != null && codTasa.getFechaAsignacion() != null) {
				solInteveVo.setCodigoTasa(null);
				if(StringUtils.isNotBlank(solInteveVo.getMatricula())){
					mensajeErrorTasa = "- La solicitud para la Matricula " +solInteveVo.getMatricula()+  " se va a importar sin tasa, ya que está aplicada a otra solicitud. ";
				}else if(StringUtils.isNotBlank(solInteveVo.getBastidor())){
					mensajeErrorTasa = "- La solicitud para el Bastidor " +solInteveVo.getBastidor()+  " se va a importar sin tasa, ya que está aplicada a otra solicitud. ";
				}else if(StringUtils.isNotBlank(solInteveVo.getNive())){
					mensajeErrorTasa = "- La solicitud para el Nive " +solInteveVo.getNive()+  " se va a importar sin tasa, ya que está aplicada a otra solicitud. ";
				}
			} else if(codTasa.getContrato().getIdContrato().compareTo(idContrato) != 0) {
				solInteveVo.setCodigoTasa(null);
				if(StringUtils.isNotBlank(solInteveVo.getMatricula())){
					mensajeErrorTasa = "- La solicitud para la Matricula " +solInteveVo.getMatricula()+  " se va a importar sin tasa, ya que no pertenece al contrato indicado. ";
				}else if(StringUtils.isNotBlank(solInteveVo.getBastidor())){
					mensajeErrorTasa = "- La solicitud para el Bastidor " +solInteveVo.getBastidor()+  " se va a importar sin tasa, ya que no pertenece al contrato indicado. ";
				}else if(StringUtils.isNotBlank(solInteveVo.getNive())){
					mensajeErrorTasa = "- La solicitud para el Nive " +solInteveVo.getNive()+  " se va a importar sin tasa, ya que no pertenece al contrato indicado. ";
				}
			}else {
				servicioTasa.asignarTasa(codTasa,solInteveVo.getNumExpediente(), idUsuario);
			}
		} else {
			solInteveVo.setCodigoTasa(null);
			if(StringUtils.isNotBlank(solInteveVo.getMatricula())){
				mensajeErrorTasa = "- La solicitud para la Matricula " +solInteveVo.getMatricula()+  " se va a importar sin tasa, ya que no existe. ";
			}else if(StringUtils.isNotBlank(solInteveVo.getBastidor())){
				mensajeErrorTasa = "- La solicitud para el Bastidor " +solInteveVo.getBastidor()+  " se va a importar sin tasa, ya que no pexiste. ";
			}else if(StringUtils.isNotBlank(solInteveVo.getNive())){
				mensajeErrorTasa = "- La solicitud para el Nive " +solInteveVo.getNive()+  " se va a importar sin tasa, ya que no existe. ";
			}
		}
		return mensajeErrorTasa;
	}

	private String tratarMensajes(String mensajeErrorTram, String mensajeErrorInterv, String mensajeErrorVeh, String mensajeTasa) {
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
		if (mensajeTasa != null && !mensajeTasa.isEmpty()) {
			mensaje += mensajeTasa;
		}
		return mensaje;
	}
	

	private ResultadoValidacionImporBean validarPosibleDuplicado(TramiteTraficoInteveVO tramiteInteveVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String bastidor = "";
		String nif = "";
		if (tramiteInteveVO.getVehiculo() != null) {
			bastidor = tramiteInteveVO.getTramitesInteveAsList().get(0).getBastidor();
		}

		if (tramiteInteveVO.getIntervinienteTraficosAsList() != null && !tramiteInteveVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteInteveVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Solicitante.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
					nif = interviente.getId().getNif();
					break;
				}
			}
		}

		if (StringUtils.isNotBlank(bastidor) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicado(bastidor, nif, tramiteInteveVO.getTipoTramite(), tramiteInteveVO.getNumColegiado());
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
	
}