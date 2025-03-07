package org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.ServicioConsultaTramiteTrafico;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFicheroSolicitud05;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioConsultaTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.CertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultCambioEstadoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamDocBase.service.ServicioDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.ResumenErroresFicheroMOVE;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioConsultaTramiteTraficoImpl implements ServicioConsultaTramiteTrafico {

	private static final long serialVersionUID = -3165826192194522364L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaTramiteTraficoImpl.class);

	@Autowired
	private ServicioConsultaEitv servicioConsultaEitv;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioFicheroSolicitud05 servicioFicheroSolicitud05;

	@Autowired
	private ServicioConsultaTasa servicioConsultaTasa;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;

	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;

	@Autowired
	ServicioDocBase servicioDocBase;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioImpresionDocumentos servicioImpresionTrafico;

	@Autowired
	ServicioComunTasa servicioComunTasa;
	
	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Override
	public ResultadoDocBaseBean generarDocBase(String numsExpedientes, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			String[] listaExpedientes = numsExpedientes.split("_");
			List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesGenDocBase(utiles.convertirStringArrayToBigDecimal(listaExpedientes));
			if (listaTramites != null && !listaTramites.isEmpty()) {
				resultado = servicioDocBase.generarDocBase(listaTramites, idUsuario, tienePermisoAdmin);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos de los expedientes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el docBase, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el docBase.");
		}
		return resultado;
	}

	@Override
	public ResultCambioEstadoBean eliminarTramites(String codSeleccionados, BigDecimal idUsuario) {
		ResultCambioEstadoBean resultado = new ResultCambioEstadoBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.split("_");
				for (String numExpediente : numsExp) {
					resultado = eliminar(new BigDecimal(numExpediente), idUsuario);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar alguna consulta para anular el tramite.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultCambioEstadoBean eliminar(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultCambioEstadoBean resultado = new ResultCambioEstadoBean(Boolean.FALSE);
		ResultBean resultEstado = new ResultBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
			resultEstado = validarTramiteEliminar(tramite);
			if (!resultEstado.getError()) {
				try {
					resultEstado = servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramite.getEstado()), EstadoTramiteTrafico.Anulado, false, null,
							idUsuario);
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de realizar el cambiarEstadoConGestionCreditos " + numExpediente + ", error: ", e, numExpediente.toString());
					resultEstado.setError(true);
					resultEstado.setMensaje("Ha sucedido un error a la hora de realizar el cambio de estado para: " + numExpediente);
				}
			}
			if (resultEstado.getError()) {
				resultado.addError();
				String mensaje = "";
				if (resultEstado.getMensaje() != null && !resultEstado.getMensaje().isEmpty()) {
					mensaje = resultEstado.getMensaje();
				} else if (resultEstado.getListaMensajes() != null && resultEstado.getListaMensajes().size() > 0) {
					mensaje = resultEstado.getListaMensajes().get(0);
				}
				resultado.addListaError("El expediente: " + numExpediente + " no se ha podido eliminar. " + mensaje);
			} else {
				resultado.addOk();
				resultado.addListaOk("El expediente: " + numExpediente + " se ha eliminado correctamente.");
				resultado.addNumExpedientesOk(numExpediente);
			}
			return resultado;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de anular el tramite: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de anular el tramite: " + numExpediente);
		}
		return resultado;
	}

	private ResultBean validarTramiteEliminar(TramiteTraficoVO tramite) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (tramite == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existe el trámite");
		} else if (!new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.No_Tramitable.getValorEnum()).equals(tramite
				.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Tramitable
						.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
								EstadoTramiteTrafico.Validado_PDF.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()).equals(
										tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
												EstadoTramiteTrafico.Pendiente_Envio_Excel.getValorEnum()).equals(tramite.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite debe ser modificable para poderse anular.");
		} else if (tramite.getTasa() != null && StringUtils.isNotBlank(tramite.getTasa().getCodigoTasa())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para anular el trámite debe desasignar antes la tasa.");
		}
		return resultado;
	}

	@Override
	public ResultBean consultaEitv(String[] numsExpedientes, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultBean result = null;
		List<String> listaMensajes = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		for (String numExp : numsExpedientes) {
			ResultBean resultado = servicioConsultaEitv.consultaEitv(new BigDecimal(numExp), idUsuario, idContrato);
			if (!resultado.getError()) {
				lResultadosOk++;
				listaMensajesOK.add(resultado.getListaMensajes().get(0));
			} else {
				listaMensajes.add(resultado.getListaMensajes().get(0));
				lResultadosError++;
			}
		}
		result = new ResultBean();
		result.setListaMensajes(listaMensajes);
		result.addAttachment("lResultadosOk", lResultadosOk);
		result.addAttachment("lResultadosError", lResultadosError);
		result.addAttachment("listaMensajesOK", listaMensajesOK);
		return result;
	}

	@Override
	public ResultBean autorizarImpresionBTV(String[] numsExpedientes, Boolean tienePermisoAdmin) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			List<String> listaErrores = new ArrayList<String>();
			List<String> listaOKs = new ArrayList<String>();
			for (String numExpediente : numsExpedientes) {
				try {
					ResultBean resultAuto = servicioTramiteTraficoBaja.autorizarImpresionInformeBtv(new BigDecimal(numExpediente), tienePermisoAdmin);
					if (resultAuto.getError()) {
						listaErrores.add(resultAuto.getMensaje());
					} else {
						listaOKs.add("Expediente: " + numExpediente + " autorizado correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de autorizar el expediente : " + numExpediente + ", error: ", e, numExpediente);
					listaErrores.add("Ha sucedido un error a la hora de autorizar el expediente : " + numExpediente);
				}
			}
			resultado.addAttachment("lResultadosOk", listaOKs);
			resultado.addAttachment("lResultadosError", listaErrores);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autorizar los expedientes seleccionados, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de autorizar los expedientes seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoCertificadoTasasBean generarCertificadoDesdeConsultaTramite(String[] numExpedientes, BigDecimal idContrato) {
		ResultadoCertificadoTasasBean resultado = new ResultadoCertificadoTasasBean(false);
		try {
			if (numExpedientes != null) {
				List<CertificadoTasasBean> listaCertificados = new ArrayList<CertificadoTasasBean>();
				for (String numExp : numExpedientes) {
					TramiteTraficoDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteTraficoDto(new BigDecimal(numExp), true);
					if (tramiteTraficoDto != null) {
						if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())) {
							servicioConsultaTasa.generarCertificadoSolInfoDesdeConsultaTramite(tramiteTraficoDto, listaCertificados, resultado);
						} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTraficoDto.getTipoTramite()) || TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(
								tramiteTraficoDto.getTipoTramite())) {
							TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoDto.getNumExpediente(), true);
							if (tramiteTrafTranDto.getCodigoTasaCamser() != null) {
								servicioConsultaTasa.generarCertificadoTasaCambSer(tramiteTraficoDto, tramiteTrafTranDto, listaCertificados, resultado);
							} else {
								servicioConsultaTasa.generarCertificadoTasaConsultaTramite(tramiteTraficoDto, listaCertificados, resultado);
							}
						} else {
							servicioConsultaTasa.generarCertificadoTasaConsultaTramite(tramiteTraficoDto, listaCertificados, resultado);
						}
					} else {
						resultado.addError();
						resultado.aniadirMensajeListaError("No se ha encontrado el expediente de número: " + numExp);
					}
				}
				if (listaCertificados.size() > 0) {
					servicioConsultaTasa.generarPdfCertificadosTasas(listaCertificados, resultado, idContrato);
				}
			} else {
				resultado.setError(true);
				resultado.setMensajeError("Debe seleccionar algún expediente para generar el certificado de sus tasas.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el certificado de tasas de los expedientes seleccionados, error: ", e);
			resultado.setError(true);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el certificado de tasas de los expedientes seleccionados.");
		}
		return resultado;
	}

	@Override
	public File getFicheroCertificadosTasa(String ficheroDescarga) {
		return servicioConsultaTasa.getFicheroCertificadosTasa(ficheroDescarga);
	}

	@Override
	public ResultadoConsultaJustProfBean generarJustificanteProfDesdeConsultaTramite(String[] numsExpedientes, BigDecimal idUsuario, String motivo, String documento, String diasValidez) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		try {
			if (numsExpedientes != null && numsExpedientes.length > 0) {
				String tipoTramite = null;
				for (String numExp : numsExpedientes) {
					ResultBean resultCrearJustif = new ResultBean(Boolean.FALSE);
					try {
						TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numExp), Boolean.TRUE);
						IntervinienteTraficoDto titular = null;
						String tipoInterviniente = null;
						if (tramiteTrafDto != null) {
							tipoTramite = tramiteTrafDto.getTipoTramite();
							if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
								tipoInterviniente = TipoInterviniente.Adquiriente.getValorEnum();
							} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
								tipoInterviniente = TipoInterviniente.Titular.getValorEnum();
							}
							titular = servicioIntervinienteTrafico.getIntervinienteTraficoDto(tramiteTrafDto.getNumExpediente(), tipoInterviniente, null, tramiteTrafDto.getNumColegiado());
							if (titular != null) {
								Date fecha = new Date();
								JustificanteProfDto justificanteProfDto = new JustificanteProfDto();
								justificanteProfDto.setTramiteTrafico(tramiteTrafDto);
								justificanteProfDto.setNumExpediente(tramiteTrafDto.getNumExpediente());
								justificanteProfDto.setEstado(new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
								justificanteProfDto.setDocumentos(documento);
								justificanteProfDto.setMotivo(motivo);
								justificanteProfDto.setDiasValidez(new BigDecimal(diasValidez));
								justificanteProfDto.setFechaInicio(fecha);
								justificanteProfDto.setFechaAlta(fecha);
								resultCrearJustif = servicioJustificanteProfesional.validarGenracionJPDesdeTransmisionODuplicado(justificanteProfDto, titular, Boolean.FALSE);
								if (!resultCrearJustif.getError()) {
									Boolean esForzar = (Boolean) resultCrearJustif.getAttachment(ServicioJustificanteProfesional.ES_JP_FORZAR);
									Boolean esErrorFecha = (Boolean) resultCrearJustif.getAttachment(ServicioJustificanteProfesional.ES_JP_ERROR_FECHA);
									resultCrearJustif = servicioJustificanteProfesional.crearJustificanteDesdeTransmisionODuplicado(justificanteProfDto, titular, esForzar, idUsuario);
									if (!resultCrearJustif.getError()) {
										if (esForzar) {
											if (esErrorFecha) {
												resultCrearJustif.setMensaje("Tramite " + justificanteProfDto.getNumExpediente()
														+ "; El Justificante Profesional fue emitido hace menos de 30 días para este vehículo. "
														+ "Si quiere volver a emitir un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
											}
										} else {
											resultCrearJustif.setMensaje("Tramite " + justificanteProfDto.getNumExpediente() + ", solicitud generada correctamente.");
										}
									}
								}
							} else {
								resultCrearJustif.setError(Boolean.TRUE);
								resultCrearJustif.setMensaje("No existen datos del " + TipoInterviniente.convertirTexto(tipoInterviniente) + " del expediente: " + numExp);
							}
						} else {
							resultCrearJustif.setError(Boolean.TRUE);
							resultCrearJustif.setMensaje("No existen datos en base de datos para el expediente: " + numExp);
						}
					} catch (Exception e) {
						log.error("Ha sucedidio un error a la hora de crear el justificante para el trámite: " + numExp + ", error: ", e, numExp);
						resultCrearJustif.setError(Boolean.TRUE);
						resultCrearJustif.setMensaje("Ha sucedidio un error a la hora de crear el justificante para el trámite: " + numExp);
					}
					gestionarResultadoJustificante(resultado, resultCrearJustif, tipoTramite);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para poder generar su justificante profesional");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los justificantes en bloque desde la consulta de tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los justificantes");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean forzarJustificantesProf(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		try {
			if (numsExpedientes != null && numsExpedientes.length > 0) {
				for (String numExp : numsExpedientes) {
					ResultBean resultJustifProf = servicioJustificanteProfesional.getJustificanteProfPorNumExpediente(new BigDecimal(numExp), Boolean.FALSE, new BigDecimal(
							EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum()));
					if (!resultJustifProf.getError()) {
						JustificanteProfDto justificanteProfDto = (JustificanteProfDto) resultJustifProf.getAttachment(ServicioJustificanteProfesional.DTO_JUSTIFICANTE);
						ResultBean resultForzar = servicioJustificanteProfesional.forzarJP(justificanteProfDto.getIdJustificanteInterno(), idUsuario, Boolean.FALSE);
						gestionarResultadoJustificante(resultado, resultForzar, (String) resultForzar.getAttachment(ServicioJustificanteProfesional.TIPO_TRAMITE_JUSTIFICANTE));
					} else {
						gestionarResultadoJustificante(resultado, resultJustifProf, null);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de forzar los justificantes en bloque desde la consulta de tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de forzar los justificantes");
		}
		return resultado;
	}

	@Override
	public ResultBean cambiarEstadoFinalizadoErrorBloque(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<String> listaMensajesError = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		for (String numExpediente : numsExpedientes) {
			try {
				ResultBean resultCambioEstado = servicioTramiteTrafico.cambiarEstadoFinalizadoError(new BigDecimal(numExpediente), idUsuario);
				if (resultCambioEstado.getError()) {
					lResultadosError++;
					listaMensajesError.add(resultCambioEstado.getMensaje());
				} else {
					lResultadosOk++;
					listaMensajesOK.add("El estado del expediente " + numExpediente + " se ha cambiado correctamente a 'Iniciado' ");
				}
			} catch (Exception e) {
				lResultadosError++;
				listaMensajesError.add("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente);
				log.error("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente + ", error: ", e, numExpediente);
			}
		}
		resultado.addAttachment("listaMensajesOK", listaMensajesOK);
		resultado.addAttachment("lResultadosOk", lResultadosOk);
		resultado.addAttachment("lResultadosError", lResultadosError);
		resultado.addAttachment("listaMensajesError", listaMensajesError);
		return resultado;
	}

	@Override
	public ResultBean cambiarEstadoFinalizadoErrorBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<String> listaMensajesError = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numsExp = codSeleccionados.split("_");
			for (String numExpediente : numsExp) {
				try {
					ResultBean resultCambioEstado = servicioTramiteTrafico.cambiarEstadoFinalizadoError(new BigDecimal(numExpediente), idUsuario);
					if (resultCambioEstado.getError()) {
						lResultadosError++;
						listaMensajesError.add(resultCambioEstado.getMensaje());
					} else {
						lResultadosOk++;
						listaMensajesOK.add("El estado del expediente " + numExpediente + " se ha cambiado correctamente a 'Iniciado' ");
					}
				} catch (Exception e) {
					lResultadosError++;
					listaMensajesError.add("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente);
					log.error("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente + ", error: ", e, numExpediente);
				}
			}
		}
		resultado.addAttachment("listaMensajesOK", listaMensajesOK);
		resultado.addAttachment("lResultadosOk", lResultadosOk);
		resultado.addAttachment("lResultadosError", lResultadosError);
		resultado.addAttachment("listaMensajesError", listaMensajesError);
		return resultado;
	}

	@Override
	public ResultBean liberarDocBaseNive(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<String> listaMensajesError = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		for (String numExpediente : numsExpedientes) {
			try {
				ResultBean resultCambioDocBaseNive = servicioTramiteTrafico.liberarDocBaseNive(new BigDecimal(numExpediente), idUsuario);
				if (resultCambioDocBaseNive.getError()) {
					lResultadosError++;
					listaMensajesError.add(resultCambioDocBaseNive.getMensaje());
				} else {
					lResultadosOk++;
					listaMensajesOK.add("El número de expediente " + numExpediente + " se ha liberado correctamente del Documento Base ");
				}
			} catch (Exception e) {
				lResultadosError++;
				listaMensajesError.add("Ha ocurrido un error a la hora de modificar el documento base del tramite: " + numExpediente);
				log.error("Ha sucedido un error a la hora de modificar el documento base del tramite: " + numExpediente + ", error: ", e, numExpediente);
			}
		}
		resultado.addAttachment("listaMensajesOK", listaMensajesOK);
		resultado.addAttachment("lResultadosOk", lResultadosOk);
		resultado.addAttachment("lResultadosError", lResultadosError);
		resultado.addAttachment("listaMensajesError", listaMensajesError);
		return resultado;
	}

	@Override
	public ResultBean liberarDocBaseNive(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<String> listaMensajesError = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numsExp = codSeleccionados.split("_");
			for (String numExpediente : numsExp) {
				try {
					ResultBean resultCambioDocBaseNive = servicioTramiteTrafico.liberarDocBaseNive(new BigDecimal(numExpediente), idUsuario);
					if (resultCambioDocBaseNive.getError()) {
						lResultadosError++;
						listaMensajesError.add(resultCambioDocBaseNive.getMensaje());
					} else {
						lResultadosOk++;
						listaMensajesOK.add("El número de expediente " + numExpediente + " se ha liberado correctamente del Documento Base ");
					}
				} catch (Exception e) {
					lResultadosError++;
					listaMensajesError.add("Ha ocurrido un error a la hora de modificar el documento base del tramite: " + numExpediente);
					log.error("Ha sucedido un error a la hora de modificar el documento base del tramite: " + numExpediente + ", error: ", e, numExpediente);
				}
			}
		}
		resultado.addAttachment("listaMensajesOK", listaMensajesOK);
		resultado.addAttachment("lResultadosOk", lResultadosOk);
		resultado.addAttachment("lResultadosError", lResultadosError);
		resultado.addAttachment("listaMensajesError", listaMensajesError);
		return resultado;
	}

	@Override
	public ResultadoConsultaEEFF consultaEEFFBloque(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultado = new ResultadoConsultaEEFF();
		try {
			for (String numExpediente : numsExpedientes) {
				try {
					ResultBean resultConsultaEEFF = servicioEEFF.realizarConsultaEEFFTramiteTrafico(new BigDecimal(numExpediente), idUsuario);
					if (resultConsultaEEFF.getError()) {
						resultado.addError();
						resultado.addListaError(resultConsultaEEFF.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("Para el número de expediente " + numExpediente + " se ha realizado la consulta EEFF.");
					}
				} catch (Exception e) {
					resultado.addError();
					resultado.addListaError("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente);
					log.error("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente + ", error: ", e, numExpediente);
				}
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las consultas EEFF.");
			log.error("Ha sucedido un error a la hora de realizar las consultas EEFF, error: ", e);
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaEEFF consultaEEFFBloque(String numsExpedientes, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultado = new ResultadoConsultaEEFF();
		try {
			if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
				String[] numsExp = numsExpedientes.split("_");
				for (String numExpediente : numsExp) {
					try {
						ResultBean resultConsultaEEFF = servicioEEFF.realizarConsultaEEFFTramiteTrafico(new BigDecimal(numExpediente), idUsuario);
						if (resultConsultaEEFF.getError()) {
							resultado.addError();
							resultado.addListaError(resultConsultaEEFF.getMensaje());
						} else {
							resultado.addOk();
							resultado.addListaOk("Para el número de expediente " + numExpediente + " se ha realizado la consulta EEFF.");
						}
					} catch (Exception e) {
						resultado.addError();
						resultado.addListaError("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente);
						log.error("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente + ", error: ", e, numExpediente);
					}
				}
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las consultas EEFF.");
			log.error("Ha sucedido un error a la hora de realizar las consultas EEFF, error: ", e);
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaEEFF liberacionEEFFBloque(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultado = new ResultadoConsultaEEFF();
		try {
			for (String numExpediente : numsExpedientes) {
				try {
					TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(numExpediente), Boolean.TRUE, Boolean.TRUE);
					if (tramiteTrafMatrDto != null) {
						ResultBean resultConsultaEEFF = servicioTramiteTraficoMatriculacion.liberarTramiteMatriculacion(tramiteTrafMatrDto, idUsuario);
						if (resultConsultaEEFF.getError()) {
							resultado.addError();
							if (resultConsultaEEFF.getListaMensajes() != null && !resultConsultaEEFF.getListaMensajes().isEmpty()) {
								resultado.addListaError("Para el número de expediente " + numExpediente + " la solicitud de liberación ha fallado por el siguiente motivo: ");
								for (String mensaje : resultConsultaEEFF.getListaMensajes()) {
									resultado.addListaError(mensaje);
								}
							} else {
								resultado.addListaError("Para el número de expediente " + numExpediente + " la solicitud de liberación ha fallado por el siguiente motivo: " + resultConsultaEEFF
										.getMensaje());
							}
						} else {
							resultado.addOk();
							resultado.addListaOk("Para el número de expediente " + numExpediente + " la solicitud de liberación se ha encolado.");
						}
					} else {
						resultado.addError();
						resultado.addListaError("Para el número de expediente " + numExpediente + " no se encuentran datos.");
					}
				} catch (Throwable e) {
					resultado.addError();
					resultado.addListaError("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente);
					log.error("Ha sucedido un error a la hora de realizar la consulta EEFF para el tramite: " + numExpediente + ", error: ", e, numExpediente);
				}
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las consultas EEFF.");
			log.error("Ha sucedido un error a la hora de realizar las consultas EEFF, error: ", e);
		}
		return resultado;
	}

	public void gestionarResultadoJustificante(ResultadoConsultaJustProfBean resultado, ResultBean resultBean, String tipoTramite) {
		if (resultBean.getError()) {
			if (tipoTramite != null) {
				if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
					resultado.addErrorTransmision();
					resultado.addListaErrorTransmision(resultBean.getMensaje());
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
					resultado.addErrorDuplicado();
					resultado.addListaErrorDuplicados(resultBean.getMensaje());
				} else {
					resultado.addListaError(resultBean.getMensaje());
					resultado.addError();
				}
			} else {
				resultado.addListaError(resultBean.getMensaje());
				resultado.addError();
			}
		} else {
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
				resultado.addOkTransmision();
				resultado.addListaOkTransmision(resultBean.getMensaje());
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
				resultado.addOkDuplicado();
				resultado.addListaOkDuplicados(resultBean.getMensaje());
			}
		}
	}

	@Override
	public ResultadoConsultaJustProfBean imprimirJustificantesProf(String[] numsExpedientes) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			if (numsExpedientes != null && numsExpedientes.length > 0) {
				Boolean sonVariosJP = false;
				url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				if (numsExpedientes.length > 1) {
					sonVariosJP = Boolean.TRUE;
					File ficheroDestino = new File(url);
					out = new FileOutputStream(ficheroDestino);
					zip = new ZipOutputStream(out);
				}
				for (String numExpediente : numsExpedientes) {
					ResultBean resultJustifProf = servicioJustificanteProfesional.getJustificanteProfPorNumExpediente(new BigDecimal(numExpediente), Boolean.FALSE, new BigDecimal(EstadoJustificante.Ok
							.getValorEnum()));
					if (!resultJustifProf.getError()) {
						JustificanteProfDto justificanteProfDto = (JustificanteProfDto) resultJustifProf.getAttachment(ServicioJustificanteProfesional.DTO_JUSTIFICANTE);
						ResultBean resultImprimirJp = servicioJustificanteProfesional.imprimirJP(justificanteProfDto.getIdJustificanteInterno());
						if (resultImprimirJp.getError()) {
							resultado.addError();
							resultado.addListaError(resultImprimirJp.getMensaje());
						} else {
							File fichero = (File) resultImprimirJp.getAttachment(ServicioJustificanteProfesional.FICHERO_JP);
							if (sonVariosJP) {
								servicioJustificanteProfesionalImprimir.addZipEntryFromFile(zip, fichero);
							} else {
								resultado.setFichero(fichero);
								resultado.setNombreFichero((String) resultImprimirJp.getAttachment(ServicioJustificanteProfesional.NOMBRE_FICHERO_JP));
							}
							resultado.addOk();
							resultado.addListaOk(resultImprimirJp.getMensaje());
						}
					} else {
						resultado.addError();
						resultado.addListaError(resultJustifProf.getMensaje());
					}
				}
				if (sonVariosJP) {
					zip.close();
					File fichero = new File(url);
					resultado.setNombreFichero(ServicioJustificanteProfesional.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
					resultado.setFichero(fichero);
					if (fichero.delete()) {
						log.info("Se ha eliminado correctamente el fichero");
					} else {
						log.info("No se ha eliminado el fichero");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún justificante.");
			}
		} catch (FileNotFoundException e) {
			Log.error("Ha sucedido un error a la hora de imprimir los justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los justificantes profesionales.");
		} catch (IOException e) {
			Log.error("Ha sucedido un error a la hora de imprimir los justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los justificantes profesionales.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				File eliminarZip = new File(url);
				eliminarZip.delete();
			}
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean generarFicheroSolicitud05DesdeConsultaTramite(String[] numExpedientes, BigDecimal idUsuarioDeSesion) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(false);
		try {
			String numColegiado = "";
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExp : numExpedientes) {
					TramiteTrafDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteDtoImpresion05(new BigDecimal(numExp), Boolean.TRUE);
					if (tramiteTraficoDto != null) {
						if (numColegiado.isEmpty()) {
							numColegiado = tramiteTraficoDto.getNumColegiado();
						}
						MarcaDgtDto marcaDgtDto = servicioMarcaDgt.getMarcaDgtDto(tramiteTraficoDto.getVehiculoDto().getCodigoMarca(), null, Boolean.TRUE);
						if (marcaDgtDto != null) {
							ResultadoFicheroSolicitud05Bean resultValidar = servicioFicheroSolicitud05.validarDatosObligatorios(tramiteTraficoDto, marcaDgtDto);
							if (!resultValidar.getError()) {
								ResultadoFicheroSolicitud05Bean resultSolic05 = servicioFicheroSolicitud05.generarDatosSolicitud05(tramiteTraficoDto, marcaDgtDto);
								if (resultSolic05.getError()) {
									resultado.addError();
									resultado.aniadirMensajeListaError(resultSolic05.getMensajeError());
								} else {
									resultado.addOk();
									resultado.aniadirMensajeListaOk("El Fichero de Solicitud 05 se ha generado correctamente para el expediente: " + numExp);
									resultado.addSfichero(resultSolic05.getsFichero());
								}
							} else {
								resultado.addError();
								String mensajeError = " ";
								for (String mensaje : resultValidar.getListaError()) {
									mensajeError += "- " + mensaje + ".";
								}
								resultado.aniadirMensajeListaError("El expediente con número" + numExp + " tiene los siguientes errores: " + mensajeError);
							}
						} else {
							resultado.addError();
							resultado.aniadirMensajeListaError("No existen datos de la marca del vehículo para el expediente con número:" + numExp);
						}
					} else {
						resultado.addError();
						resultado.aniadirMensajeListaError("No existen datos para el expediente con número:" + numExp);
					}
				}
				SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");

				String nombreFichero = ServicioFicheroSolicitud05.NOMBRE_FICHERO + numColegiado + "_" + format.format(new Date());
				if (resultado.getsFichero() != null && !resultado.getsFichero().isEmpty()) {
					ResultadoFicheroSolicitud05Bean resultFichero = servicioFicheroSolicitud05.guardarFichero(resultado.getsFichero().getBytes(), nombreFichero);
					if (resultFichero.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError(resultFichero.getMensajeError());
					} else {
						resultado.setNombreFichero(nombreFichero);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Debe seleccionar algún expediente para poder generar la solicitud 05.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar los xml de la consulta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar las solicitudes 05.");
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean generarFicheroSolicitud05DesdeConsultaTramite(String codSeleccionados, BigDecimal idContrato) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(false);
		try {
			String numColegiado = "";
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.split("_");
				for (String numExp : numsExp) {
					TramiteTrafDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteDtoImpresion05(new BigDecimal(numExp), Boolean.TRUE);
					if (tramiteTraficoDto != null) {
						if (numColegiado.isEmpty()) {
							numColegiado = tramiteTraficoDto.getNumColegiado();
						}
						MarcaDgtDto marcaDgtDto = servicioMarcaDgt.getMarcaDgtDto(tramiteTraficoDto.getVehiculoDto().getCodigoMarca(), null, Boolean.TRUE);
						if (marcaDgtDto != null) {
							ResultadoFicheroSolicitud05Bean resultValidar = servicioFicheroSolicitud05.validarDatosObligatorios(tramiteTraficoDto, marcaDgtDto);
							if (!resultValidar.getError()) {
								ResultadoFicheroSolicitud05Bean resultSolic05 = servicioFicheroSolicitud05.generarDatosSolicitud05(tramiteTraficoDto, marcaDgtDto);
								if (resultSolic05.getError()) {
									resultado.addError();
									resultado.aniadirMensajeListaError(resultSolic05.getMensajeError());
								} else {
									resultado.addOk();
									resultado.aniadirMensajeListaOk("El Fichero de Solicitud 05 se ha generado correctamente para el expediente: " + numExp);
									resultado.addSfichero(resultSolic05.getsFichero());
								}
							} else {
								resultado.addError();
								String mensajeError = " ";
								for (String mensaje : resultValidar.getListaError()) {
									mensajeError += "- " + mensaje + ".";
								}
								resultado.aniadirMensajeListaError("El expediente con número" + numExp + " tiene los siguientes errores: " + mensajeError);
							}
						} else {
							resultado.addError();
							resultado.aniadirMensajeListaError("No existen datos de la marca del vehículo para el expediente con número:" + numExp);
						}
					} else {
						resultado.addError();
						resultado.aniadirMensajeListaError("No existen datos para el expediente con número:" + numExp);
					}
				}
				SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");

				String nombreFichero = ServicioFicheroSolicitud05.NOMBRE_FICHERO + numColegiado + "_" + format.format(new Date());
				if (resultado.getsFichero() != null && !resultado.getsFichero().isEmpty()) {
					ResultadoFicheroSolicitud05Bean resultFichero = servicioFicheroSolicitud05.guardarFichero(resultado.getsFichero().getBytes(), nombreFichero);
					if (resultFichero.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError(resultFichero.getMensajeError());
					} else {
						resultado.setNombreFichero(nombreFichero);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Debe seleccionar algún expediente para poder generar la solicitud 05.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar los xml de la consulta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar las solicitudes 05.");
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean descargarFichero05(String nombreFichero) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(false);
		try {
			if (nombreFichero != null && !nombreFichero.isEmpty()) {
				ResultadoFicheroSolicitud05Bean result = servicioFicheroSolicitud05.descargarFichero(nombreFichero);
				if (result.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError(result.getMensajeError());
				} else {
					resultado.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_TXT);
					resultado.setFichero(result.getFichero());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Debe seleccionar algún expediente para poder generar la solicitud 05.");
			}
		} catch (Throwable e) {
		}
		return resultado;
	}

	public TramiteTrafDto getTramiteDto(BigDecimal numExpediente, boolean tramiteCompleto) {
		return servicioTramiteTrafico.getTramiteDto(numExpediente, tramiteCompleto);
	}

	@Override
	public ResultBean comprobarPrevioAClonarTramites(String[] codSeleccionados) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<TramiteTrafDto> listaTramites = servicioTramiteTrafico.getListaTramiteTrafico(codSeleccionados, Boolean.TRUE);
		if (listaTramites != null && !listaTramites.isEmpty()) {
			Boolean esMatw = Boolean.FALSE;
			Boolean esTrans = Boolean.FALSE;
			for (TramiteTrafDto tramiteBBDD : listaTramites) {
				if (!esMatw && !esTrans) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteBBDD.getTipoTramite())) {
						esMatw = Boolean.TRUE;
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteBBDD.getTipoTramite()) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteBBDD
							.getTipoTramite())) {
						esTrans = Boolean.TRUE;
					}
				}
				if (esMatw) {
					if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteBBDD.getTipoTramite())) {
						resultado = new ResultBean(true, "El trámite debe de estar en estado Iniciado para poder realizar la clonación.");
					} else {
						if ((!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteBBDD.getEstado()))) {
							resultado = new ResultBean(true, "El trámite debe de estar en estado Iniciado para poder realizar la clonación.");
						}
					}
				} else if (esTrans) {
					if (!TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteBBDD.getTipoTramite()) && (!TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteBBDD
							.getTipoTramite()))) {
						resultado = new ResultBean(true, "El tipo de trámite seleccionado debe ser de Transmision para poder realizar la clonación.");
					} else {
						if (!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteBBDD.getEstado())) {
							resultado = new ResultBean(true, "El trámite debe de estar en estado Finalizado PDF para poder realizar la clonación.");
						}
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido recuperar la lista con los expedientes seleccionados para clonar");
		}

		return resultado;
	}

	@Override
	public ResultBean duplicarTramite(BigDecimal[] codSeleccionados, BigDecimal idUsuario, String desasignarTasaAlDuplicar) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (codSeleccionados != null && codSeleccionados.length > 0) {
			TramiteTrafDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteDto(codSeleccionados[0], Boolean.TRUE);
			resultado = validarDuplicarTramite(tramiteTraficoDto);
			if (!resultado.getError()) {
				ResultBean result = servicioTramiteTrafico.desasignarDuplicarTramite(tramiteTraficoDto.getNumExpediente(), tramiteTraficoDto.getTipoTramite(), idUsuario, desasignarTasaAlDuplicar);
				if (result.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(result.getMensaje());
				} else {
					resultado.setMensaje(result.getMensaje());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultado.getMensaje());
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No puede duplicar más de un trámite a la vez");
		}
		return resultado;
	}

	private ResultBean validarDuplicarTramite(TramiteTrafDto tramiteTraficoDto) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (tramiteTraficoDto != null) {
			if ((TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoDto.getTipoTramite()) || TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())
					|| TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())) && (EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(
							tramiteTraficoDto.getEstado()))) {} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se permite duplicar un trámite que no sea de transmisión electrónica, baja o matriculación, y que no esté en estado Finalizado PDF");
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Hay que seleccionar un trámite para poder duplicar");
		}
		return resultado;
	}

	@Override
	public ResultadoMatriculacionBean descargarFicheros576(String[] sNumsExpedientes) {
		ResultadoMatriculacionBean resultado = new ResultadoMatriculacionBean(Boolean.FALSE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		try {
			if (sNumsExpedientes != null && sNumsExpedientes.length > 0) {
				if (sNumsExpedientes.length == 1) {
					return servicioTramiteTraficoMatriculacion.obtenerFicheroPresentacion576(sNumsExpedientes[0]);
				} else {
					url = gestorPropiedades.valorPropertie(ServicioConsultaTramiteTrafico.RUTA_FICH_TEMP) + "zip" + System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for (String numExpediente : sNumsExpedientes) {
						resultado = servicioTramiteTraficoMatriculacion.obtenerFicheroPresentacion576(numExpediente);
						if (!resultado.getError()) {
							ZipEntry zipEntry = new ZipEntry(resultado.getNombreFichero());
							zip.putNextEntry(zipEntry);
							zip.write(gestorDocumentos.transformFiletoByte(resultado.getFichero()));
							zip.closeEntry();
						} else {
							break;
						}
					}
					zip.close();
					if (!resultado.getError()) {
						File fichero = new File(url);
						resultado.setNombreFichero(ServicioVehiculoAtex5.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.setFichero(fichero);
						resultado.setEsZip(Boolean.TRUE);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para poder descargar su 576.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los ficheros de presentacion del 576, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar los ficheros de presentacion del 576.");
		}
		return resultado;
	}

	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDistintivos(String[] codSeleccionados, BigDecimal idUsuario, Boolean tienePermisoImpresionDstvB, Boolean tienePermisoImpresionDstvC,
			Boolean tienePermisoAdmin) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && codSeleccionados.length > 0) {
				for (String numExpediente : codSeleccionados) {
					try {
						ResultadoPermisoDistintivoItvBean resultSol = servicioDistintivoDgt.solicitarImpresionDistintivo(new BigDecimal(numExpediente), tienePermisoAdmin, idUsuario);
						if (resultSol.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSol.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Solicitud de impresión generada para el expediente: " + numExpediente);
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de solicitar el distintivo, error: ", e, numExpediente);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de solicitar el distintivo.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expedienete para generar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDistintivos(String codSeleccionados, BigDecimal idUsuario, Boolean tienePermisoImpresionDstvB, Boolean tienePermisoImpresionDstvC,
			Boolean tienePermisoAdmin) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.split("_");
				for (String numExpediente : numsExp) {
					try {
						ResultadoPermisoDistintivoItvBean resultSol = servicioDistintivoDgt.solicitarImpresionDistintivo(new BigDecimal(numExpediente), tienePermisoAdmin, idUsuario);
						if (resultSol.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSol.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Solicitud de impresión generada para el expediente: " + numExpediente);
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de solicitar el distintivo, error: ", e, numExpediente);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de solicitar el distintivo.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expedienete para generar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultCambioEstadoBean cambiarEstadoBloque(String codSeleccionados, String nuevoEstado, BigDecimal idUsuario) {
		ResultCambioEstadoBean resultado = new ResultCambioEstadoBean(Boolean.FALSE);
		ResultBean resultEstado = new ResultBean(Boolean.FALSE);

		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numsExp = codSeleccionados.split("_");
			for (String numExpediente : numsExp) {
				try {
					resultEstado = servicioTramiteTrafico.cambiarEstadoConGestionCreditos(numExpediente, nuevoEstado, idUsuario);
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de realizar el cambiarEstadoConGestionCreditos " + numExpediente + ", error: ", e, numExpediente);
					resultEstado.setError(true);
					resultEstado.setMensaje("Ha sucedido un error a la hora de realizar el cambio de estado para: " + numExpediente);
				}
				if (resultEstado.getError()) {
					resultado.addError();
					String mensaje = "";
					if (resultEstado.getMensaje() != null && !resultEstado.getMensaje().isEmpty()) {
						mensaje = resultEstado.getMensaje();
					} else if (resultEstado.getListaMensajes() != null && resultEstado.getListaMensajes().size() > 0) {
						mensaje = resultEstado.getListaMensajes().get(0);
					}
					resultado.addListaError("El estado del expediente: " + numExpediente + " no se ha cambiado. " + mensaje);
					log.error("Error al cambiar de estado:" + resultEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk("El estado del expediente: " + numExpediente + " se ha cambiado correctamente a " + org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(
							nuevoEstado).getNombreEnum());
					resultado.addNumExpedientesOk(new BigDecimal(numExpediente));
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultado;
	}

	@Override
	public ResultCambioEstadoBean cambiarEstadoBloque(String[] codSeleccionados, String nuevoEstado, BigDecimal idUsuario) {
		ResultCambioEstadoBean resultado = new ResultCambioEstadoBean(Boolean.FALSE);
		ResultBean resultEstado = new ResultBean(Boolean.FALSE);

		if (codSeleccionados != null && codSeleccionados.length > 0) {
			for (String numExpediente : codSeleccionados) {
				try {
					resultEstado = servicioTramiteTrafico.cambiarEstadoConGestionCreditos(numExpediente, nuevoEstado, idUsuario);
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de realizar el cambiarEstadoConGestionCreditos " + numExpediente + ", error: ", e, numExpediente);
					resultEstado.setError(true);
					resultEstado.setMensaje("Ha sucedido un error a la hora de realizar el cambio de estado para: " + numExpediente);
				}
				if (resultEstado.getError()) {
					resultado.addError();
					String mensaje = "";
					if (resultEstado.getMensaje() != null && !resultEstado.getMensaje().isEmpty()) {
						mensaje = resultEstado.getMensaje();
					} else if (resultEstado.getListaMensajes() != null && resultEstado.getListaMensajes().size() > 0) {
						mensaje = resultEstado.getListaMensajes().get(0);
					}
					resultado.addListaError("El estado del expediente: " + numExpediente + " no se ha cambiado. " + mensaje);
					log.error("Error al cambiar de estado:" + resultEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk("El estado del expediente: " + numExpediente + " se ha cambiado correctamente a " + org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(
							nuevoEstado).getNombreEnum());
					resultado.addNumExpedientesOk(new BigDecimal(numExpediente));
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultado;
	}

	@Override
	public ResultBean validarMismoTipoTramiteGenJustificante(String listaExpedientes) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
				String[] sNumExpedientes = listaExpedientes.split("-");
				String tipoTramite = null;
				for (String numExp : sNumExpedientes) {
					TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numExp), Boolean.FALSE);
					if (tramiteTrafDto != null) {
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite()) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafDto
								.getTipoTramite()) || TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
							if (tipoTramite == null) {
								tipoTramite = tramiteTrafDto.getTipoTramite();
							} else {
								if (!tipoTramite.equals(tramiteTrafDto.getTipoTramite())) {
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje("Para poder generar los justificantes en bloque todos deben de ser del mismo tipo.");
									return resultado;
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Solo se pueden obtener justificantes para tramites de Transmisión o Duplicados.");
							return resultado;
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Para el expediente: " + numExp + " no existen datos para poder generar su justificante.");
					}
				}
				resultado.addAttachment("tipoTramite", tipoTramite);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder generar su justificante");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el mismo tipo de tramite para generar los justificantes, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el mismo tipo de tramite para generar los justificantes.");
		}
		return resultado;
	}

	@Override
	public ResultadoSolicitudNRE06Bean tramitarNRE06(String[] numExpedientes, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoSolicitudNRE06Bean resultado = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		try {
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExp : numExpedientes) {
					TramiteTraficoVO tramiteTraficoVO = servicioComunTramiteTrafico.getTramite(new BigDecimal(numExp), Boolean.TRUE);
					if (tramiteTraficoVO != null) {
						ResultadoSolicitudNRE06Bean resultValidacion = resulValidacomprobarValidacionesSolicitudNRE06(tramiteTraficoVO);
						if (!resultValidacion.getError()) {
							ResultadoBean resulCola = servicioComunCola.crearSolicitud(tramiteTraficoVO.getNumExpediente().longValue(), ProcesosEnum.GEN_NRE06.getNombreEnum(), gestorPropiedades
									.valorPropertie(NOMBRE_HOST), tramiteTraficoVO.getTipoTramite(), idUsuario, new BigDecimal(tramiteTraficoVO.getContrato().getIdContrato()), null);
							servicioComunTramiteTrafico.cambiarEstado(tramiteTraficoVO.getNumExpediente(), new BigDecimal(EstadoTramiteTrafico.PendienteTramitarNRE06.getValorEnum()), idUsuario
									.longValue());
							if (resulCola.getError()) {
								resultado.setError(Boolean.TRUE);
								resultado.addNumError();
								resultado.addListaError("Ha sucedido un error a la hora de encolar la solicitud para NRE06.");
							} else {
								resultado.addNumOk();
								resultado.addListaOk("Se ha creado la cola para el proceso de solicitud NRE06");
							}
						} else {
							resultado.addNumError();
							resultado.setListaError(resultValidacion.getListaError());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar el trámite.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoSolicitudNRE06Bean resulValidacomprobarValidacionesSolicitudNRE06(TramiteTraficoVO tramiteTraficoVO) {
		ResultadoSolicitudNRE06Bean resultado = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("El trámite tiene que estar en estado Iniciado para poder Tramitar el NRE06.");
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoVO.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoVO
				.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("Para poder Tramitar el NRE06, el tipo de trámite solo puede ser de Matriculación o Transmision Eletrócnica.");
		} else if (tramiteTraficoVO.getContrato().getColegiado().getUsuario().getNif() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("El NIF del colegiado es obligatorio para poder Tramitar el NRE06.");
		} else if (tramiteTraficoVO.getContrato().getColegiado().getUsuario().getApellidosNombre() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("Los apellidos del colegiado son obligatorios para poder Tramitar el NRE06.");
		} else if (tramiteTraficoVO.getFechaPresentacion() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("La fecha de presentación es obligatorio para poder Tramitar el NRE06.");
		} else if (tramiteTraficoVO.getVehiculo() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("No existe vehiculo guardado. Es necesario tener un vehiculo con el bastidor obligatorio para poder tramitar el NRE06.");
		} else if (tramiteTraficoVO.getVehiculo() != null) {
			if (tramiteTraficoVO.getVehiculo().getBastidor() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addNumError();
				resultado.addListaError("El bastidor es obligatorio para poder Tramitar el NRE06.");
			}
		} else if (StringUtils.isNotBlank(tramiteTraficoVO.getNre())) {
			resultado.setError(Boolean.TRUE);
			resultado.addNumError();
			resultado.addListaError("Ya existe una solicitud NRE creada para el expediente " + tramiteTraficoVO.getNumExpediente()
					+ ".Puede imprimirla desde consulta de trámitese e imprimir documentos.");
		}
		for (IntervinienteTraficoVO tramiteVo : tramiteTraficoVO.getIntervinienteTraficosAsList()) {
			if (TipoInterviniente.Titular.getValorEnum().equals(tramiteVo.getTipoIntervinienteVO().getTipoInterviniente())) {
				if (TipoPersona.Fisica.getValorEnum().equals(tramiteVo.getPersona().getTipoPersona())) {
					if (tramiteVo.getPersona().getApellido1RazonSocial() == null || tramiteVo.getPersona().getApellido2() == null) {
						resultado.setError(Boolean.TRUE);
						resultado.addNumError();
						resultado.addListaError("Los apellidos del titular son obligatorios para poder Tramitar el NRE06.");
					}
				} else if (TipoPersona.Juridica.getValorEnum().equals(tramiteVo.getPersona().getTipoPersona())) {
					if (tramiteVo.getPersona().getApellido1RazonSocial() == null) {
						resultado.setError(Boolean.TRUE);
						resultado.addNumError();
						resultado.addListaError("Para personas Juridicas, la razón social del titular es obligatorio para poder Tramitar el NRE06.");
					}
				}
				if (tramiteVo.getPersona().getId().getNif() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.addNumError();
					resultado.addListaError("El NIF del titular es obligatorio para poder Tramitar el NRE06.");
				}
			}
		}

		return resultado;
	}

	@Override
	public ResultadoBean cambiarFechaPresentacion(String[] numExpedientes, BigDecimal idUsuario, Long idContrato) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExp : numExpedientes) {
					TramiteTraficoVO tramiteTraficoVO = servicioComunTramiteTrafico.getTramite(new BigDecimal(numExp), Boolean.TRUE);
					if (tramiteTraficoVO != null) {
						ResultadoBean resultValidacion = validarActualizarFechaHoy(tramiteTraficoVO);
						if (!resultValidacion.getError()) {
							servicioComunTramiteTrafico.actualizarFechaDiaHoy(tramiteTraficoVO.getNumExpediente(), idUsuario.longValue());
							String[] numExpSolici = new String[1];
							numExpSolici[0] = numExp;
							ResultadoImpresionBean resultEnv = servicioImpresionTrafico.crearImpresion(numExpSolici, idContrato, idUsuario.longValue(), TipoImpreso.MatriculacionPDF417.toString(),
									tramiteTraficoVO.getTipoTramite(), null);
							if (resultEnv != null && !resultEnv.getError()) {
								resultado.setMensaje("Se ha actualizado la fecha correctamente. En unos minutos recibirá el correo con el PDF.");
							} else {
								resultado.setMensaje("Se ha actualizado la fecha correctamente.");
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultValidacion.getMensaje());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar el trámite.");
		}
		return resultado;
	}

	private ResultadoBean validarActualizarFechaHoy(TramiteTraficoVO tramiteTraficoVO) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se puede actualizar la fecha a día de hoy de los tramites que están en estado Finalizado PDF.");
		}
		return resultado;
	}

	@Override
	public ResultadoBean desasignarTasaCau(String[] numExpedientes, BigDecimal idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExp : numExpedientes) {
					TramiteTraficoVO tramiteTraficoVO = servicioComunTramiteTrafico.getTramite(new BigDecimal(numExp), Boolean.TRUE);
					if(tramiteTraficoVO != null) {
						TasaVO tasaBBDD = servicioComunTasa.getTasa(tramiteTraficoVO.getTasa().getCodigoTasa(), tramiteTraficoVO.getNumExpediente(), tramiteTraficoVO.getTasa().getTipoTasa());
						ResultadoBean resultValidacion = validarDesasignarTasa(tramiteTraficoVO,tasaBBDD);
						if(!resultValidacion.getError()) {
							ResultadoBean resultTasaTramite = servicioComunTramiteTrafico.desasignarTasaTramiteTrafico(new BigDecimal(numExp), idUsuario.longValue());
							if(!resultTasaTramite.getError()) {
								ResultadoBean resultTasaBBDD = servicioComunTasa.desasignarTasaBBDD(tasaBBDD.getCodigoTasa(), tasaBBDD.getNumExpediente(), 
								tasaBBDD.getContrato().getIdContrato().longValue(), tasaBBDD.getTipoTasa(), idUsuario.longValue());
								if(!resultTasaBBDD.getError()) {
									resultado.setMensaje("La tasa " + tasaBBDD.getCodigoTasa() + " se ha desasignado correctamente.");
								}else {
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje(resultTasaBBDD.getMensaje());
								}
								
							}else {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje(resultTasaTramite.getMensaje());
							}
						}else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultValidacion.getMensaje());
						}
					}
				}
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar el trámite.");
		}	
		return resultado;
	}

	private ResultadoBean validarDesasignarTasa(TramiteTraficoVO tramiteTraficoVO, TasaVO tasaBBDD) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if(tasaBBDD != null	&& !EstadoTasaBloqueo.BLOQUEADA.getValorEnum().equals(tasaBBDD.getBloqueada())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para desbloquear el combo de tasas tiene que estar bloqueado previamente.");
		}
		return resultado;
	}
	@Override
	public ResultadoFicheroSolicitud05Bean generarFicheroMOVE(String codSeleccionados, List<ResumenErroresFicheroMOVE> listaResumenErroresFicheroMOVE) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(false);
		String lineas = "";
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.replaceAll(" ", "").split(",");
				for (String numExp : numsExp) {
					TramiteTrafMatrDto tramiteTraficoDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(numExp),false,
							Boolean.TRUE);
					if (tramiteTraficoDto != null) {
						MarcaDgtDto marcaDgtDto = null; 
						if(null != tramiteTraficoDto.getVehiculoDto()) {
							marcaDgtDto = servicioMarcaDgt.getMarcaDgtDto(tramiteTraficoDto.getVehiculoDto().getCodigoMarca(), null, Boolean.TRUE);
						}
						if (marcaDgtDto != null) {
							ResumenErroresFicheroMOVE resultValidar = validarDatosObligatorios(tramiteTraficoDto,
									marcaDgtDto);
							if (!resultValidar.isError()) {
								ResumenErroresFicheroMOVE resultMove = generarDatosMove(tramiteTraficoDto, marcaDgtDto);
								if (resultMove.isError()) {
									//resultado.addError();
									//resultado.aniadirMensajeListaError(resultMove.getDescripcionError() + " Expediente:" + numExp);
									ResumenErroresFicheroMOVE elemen = new ResumenErroresFicheroMOVE();
									elemen.setDescripcionError(resultMove.getDescripcionError());
									elemen.setNumExpedienteError(numExp);
									elemen.setError(true);
									listaResumenErroresFicheroMOVE.add(elemen);
								} else {
									//resultado.addOk();
									//resultado.aniadirMensajeListaOk("El Fichero de MOVE se ha generado correctamente para el expediente: " + numExp);
									ResumenErroresFicheroMOVE elemen = new ResumenErroresFicheroMOVE();
									elemen.setDescripcionError("Linea generada correctamente");
									elemen.setNumExpedienteError(numExp);
									elemen.setError(false);
									lineas += resultMove.getLinea();
									listaResumenErroresFicheroMOVE.add(elemen);
								}
							} else {
//								resultado.addError();
//								String mensajeError = "- " + resultValidar.getDescripcionError() + ".";
//								resultado.aniadirMensajeListaError("El expediente con nÃºmero" + numExp
//										+ " tiene los siguientes errores: " + mensajeError);
								ResumenErroresFicheroMOVE elemen = new ResumenErroresFicheroMOVE();
								elemen.setDescripcionError(resultValidar.getDescripcionError());
								elemen.setNumExpedienteError(numExp);
								elemen.setError(true);
								listaResumenErroresFicheroMOVE.add(elemen);
							}
						} else {
//							resultado.addError();
//							resultado.aniadirMensajeListaError(
//									"No existen datos de la marca del vehÃ­culo para el expediente con nÃºmero:"
//											+ numExp);
							ResumenErroresFicheroMOVE elemen = new ResumenErroresFicheroMOVE();
							elemen.setDescripcionError("No existen datos de la marca del vehículo");
							elemen.setNumExpedienteError(numExp);
							elemen.setError(true);
							listaResumenErroresFicheroMOVE.add(elemen);
						}
					} else {
//						resultado.addError();
//						resultado.aniadirMensajeListaError("No existen datos para el expediente con nÃºmero:" + numExp);
						ResumenErroresFicheroMOVE elemen = new ResumenErroresFicheroMOVE();
						elemen.setDescripcionError("No existen datos para el expediente");
						elemen.setNumExpedienteError(numExp);
						elemen.setError(true);
						listaResumenErroresFicheroMOVE.add(elemen);
					}
				}
				String nombreFichero = "FicheroMOVE";
				resultado.setNombreFichero(nombreFichero);
				resultado.setsFichero(lineas);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Debe seleccionar algÃºn expediente para poder generar el fichero.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero MOVE: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el fichero MOVE.");
		}
		return resultado;
	}

	private ResumenErroresFicheroMOVE generarDatosMove(TramiteTrafMatrDto tramiteTraficoDto, MarcaDgtDto marcaDgtDto) {
		ResumenErroresFicheroMOVE result = new ResumenErroresFicheroMOVE();
		result.setError(false);
		SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HHMM");
		String fechaPresentacion;
		String horaPresentacion;
		String domicilio = "";
		String domicilioVehiculo = "";
		String lineas = "";
		try {
			fechaPresentacion = sdfFecha.format(tramiteTraficoDto.getFechaPresentacion().getDate());
			horaPresentacion = sdfHora.format(tramiteTraficoDto.getFechaPresentacion().getDate());
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getIdTipoVia() ? tramiteTraficoDto.getTitular().getDireccion().getIdTipoVia() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getNombreVia() ? tramiteTraficoDto.getTitular().getDireccion().getNombreVia() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getNumero() ? tramiteTraficoDto.getTitular().getDireccion().getNumero() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getKm() ? tramiteTraficoDto.getTitular().getDireccion().getKm() + " " : ""; 
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getHm() ? tramiteTraficoDto.getTitular().getDireccion().getHm() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getBloque() ? tramiteTraficoDto.getTitular().getDireccion().getBloque() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getPortal() ? tramiteTraficoDto.getTitular().getDireccion().getPortal() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getEscalera() ? tramiteTraficoDto.getTitular().getDireccion().getEscalera() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getPlanta() ? tramiteTraficoDto.getTitular().getDireccion().getPlanta() + " " : "";
			domicilio += null != tramiteTraficoDto.getTitular().getDireccion().getPuerta() ? tramiteTraficoDto.getTitular().getDireccion().getPuerta() : "";

			if(null != tramiteTraficoDto.getVehiculoDto().getDireccion()) {
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getIdTipoVia() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getIdTipoVia() + " " : ""; 
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getNombreVia() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getNombreVia() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getNumero() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getNumero() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getKm() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getKm() + " " : ""; 
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getHm() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getHm() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getBloque() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getBloque() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getPortal() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getPortal() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getEscalera() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getEscalera() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getPlanta() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getPlanta() + " " : "";
				domicilioVehiculo += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getPuerta() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getPuerta() : "";
			}else {
				domicilioVehiculo = domicilio;
			}

			lineas += "A";
			lineas += "|";
			lineas += "1";
			lineas += "|";
			lineas += fechaPresentacion;
			lineas += "|";
			lineas += horaPresentacion;
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getPersona().getNif() ? tramiteTraficoDto.getTitular().getPersona().getNif() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getPersona().getApellido1RazonSocial() ? tramiteTraficoDto.getTitular().getPersona().getApellido1RazonSocial() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getPersona().getApellido2() ? tramiteTraficoDto.getTitular().getPersona().getApellido2() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getPersona().getNombre() ? tramiteTraficoDto.getTitular().getPersona().getNombre() : "";
			lineas += "|";
			String nombreMunicipio = "";
			String nombreProvincia = "";
			if (tramiteTraficoDto.getTitular().getDireccion().getIdProvincia() != null) {
				if (tramiteTraficoDto.getTitular().getDireccion().getIdMunicipio() != null) {
					nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(tramiteTraficoDto.getTitular().getDireccion().getIdMunicipio(), tramiteTraficoDto.getTitular().getDireccion().getIdProvincia());
				}
				nombreProvincia = servicioDireccion.obtenerNombreProvincia(tramiteTraficoDto.getTitular().getDireccion().getIdProvincia());
			}
			lineas += nombreProvincia;
			lineas += "|";
			lineas += nombreMunicipio;
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getDireccion().getPuebloCorreos() ? tramiteTraficoDto.getTitular().getDireccion().getPuebloCorreos() : "";
			lineas += "|";
			lineas += domicilio;
			lineas += "|";
			lineas += null != tramiteTraficoDto.getTitular().getDireccion().getCodPostal() ? tramiteTraficoDto.getTitular().getDireccion().getCodPostal() : "";
			lineas += "|";
			if(null != tramiteTraficoDto.getVehiculoDto().getDireccion()) {
				String nombreMunicipioVehiculo = "";
				String nombreProvinciaVehiculo = "";
				if (tramiteTraficoDto.getVehiculoDto().getDireccion().getIdProvincia() != null) {
					if (tramiteTraficoDto.getVehiculoDto().getDireccion().getIdMunicipio() != null) {
						nombreMunicipioVehiculo = servicioDireccion.obtenerNombreMunicipio(tramiteTraficoDto.getVehiculoDto().getDireccion().getIdMunicipio(), tramiteTraficoDto.getTitular().getDireccion().getIdProvincia());
					}
					nombreProvinciaVehiculo = servicioDireccion.obtenerNombreProvincia(tramiteTraficoDto.getVehiculoDto().getDireccion().getIdProvincia());
				}
				lineas += nombreProvinciaVehiculo;
				lineas += "|";
				lineas += nombreMunicipioVehiculo;
				lineas += "|";
				lineas += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getPuebloCorreos() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getPuebloCorreos() : "";
			}else {
				lineas += nombreProvincia;
				lineas += "|";
				lineas += nombreMunicipio;
				lineas += "|";
				lineas += null != tramiteTraficoDto.getTitular().getDireccion().getPuebloCorreos() ? tramiteTraficoDto.getTitular().getDireccion().getPuebloCorreos() : "";
			}
			lineas += "|";
			lineas += domicilioVehiculo;
			lineas += "|";
			if(null != tramiteTraficoDto.getVehiculoDto().getDireccion()) {
				lineas += null != tramiteTraficoDto.getVehiculoDto().getDireccion().getCodPostal() ? tramiteTraficoDto.getVehiculoDto().getDireccion().getCodPostal() : "";
			}else {
				lineas += null != tramiteTraficoDto.getTitular().getDireccion().getCodPostal() ? tramiteTraficoDto.getTitular().getDireccion().getCodPostal() : "";
			}
			lineas += "|";

			lineas += null != tramiteTraficoDto.getVehiculoDto().getMatricula() ? tramiteTraficoDto.getVehiculoDto().getMatricula() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getBastidor() ? tramiteTraficoDto.getVehiculoDto().getBastidor() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getNive() ? tramiteTraficoDto.getVehiculoDto().getNive() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion() ? sdfFecha.format(tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion().getDate()) : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getFechaPrimMatri() ? sdfFecha.format(tramiteTraficoDto.getVehiculoDto().getFechaPrimMatri().getDate()) : "";
			lineas += "|";
			lineas += null != marcaDgtDto ? marcaDgtDto.getMarca() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getModelo() ? tramiteTraficoDto.getVehiculoDto().getModelo() : "";
			lineas += "|";
			if(null != tramiteTraficoDto.getVehiculoDto().getServicioTrafico()) {
				lineas += null != tramiteTraficoDto.getVehiculoDto().getServicioTrafico().getIdServicio() ? tramiteTraficoDto.getVehiculoDto().getServicioTrafico().getIdServicio() : "";
			}
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getTipoVehiculo() ? tramiteTraficoDto.getVehiculoDto().getTipoVehiculo() : "";
			lineas += "|";
			TipoVehiculoVO tipoVehiculo = null;
			String descripcion = "";
			if(null != tramiteTraficoDto.getVehiculoDto().getTipoVehiculo()) {
				tipoVehiculo = servicioTipoVehiculo.getTipoVehiculo(tramiteTraficoDto.getVehiculoDto().getTipoVehiculo());
			}
			if(null != tipoVehiculo) {
				descripcion = tipoVehiculo.getDescripcion();
			}
			lineas += null != descripcion ? descripcion : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getTipoIndustria() ? tramiteTraficoDto.getVehiculoDto().getTipoIndustria() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getIdDirectivaCee() ? tramiteTraficoDto.getVehiculoDto().getIdDirectivaCee() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getCilindrada() ? tramiteTraficoDto.getVehiculoDto().getCilindrada() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getPotenciaFiscal() ? tramiteTraficoDto.getVehiculoDto().getPotenciaFiscal() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getPotenciaNeta() ? tramiteTraficoDto.getVehiculoDto().getPotenciaNeta() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getPesoMma() ? tramiteTraficoDto.getVehiculoDto().getPesoMma() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getMom() ? tramiteTraficoDto.getVehiculoDto().getMom() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getPlazas() ? tramiteTraficoDto.getVehiculoDto().getPlazas() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getCarburante() ? tramiteTraficoDto.getVehiculoDto().getCarburante() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getTipoAlimentacion() ? tramiteTraficoDto.getVehiculoDto().getTipoAlimentacion() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getConsumo() ? tramiteTraficoDto.getVehiculoDto().getConsumo() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getNivelEmisiones() ? tramiteTraficoDto.getVehiculoDto().getNivelEmisiones() : "";
			lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getCo2() ? tramiteTraficoDto.getVehiculoDto().getCo2() : "";
			lineas += "|";
			lineas += "N|N|N|N|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getMatriculaOrigen() ? tramiteTraficoDto.getVehiculoDto().getMatriculaOrigen() : "";
			lineas += "|";
			//lineas += null != tramiteTraficoDto.getVehiculoDto().getAutonomiaElectrica() ? tramiteTraficoDto.getVehiculoDto().getAutonomiaElectrica() : "";
			//lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getCategoriaElectrica() ? tramiteTraficoDto.getVehiculoDto().getCategoriaElectrica() : "";
			lineas += "|";
			//lineas += null != tramiteTraficoDto.getTipoDistintivo() ? tramiteTraficoDto.getTipoDistintivo() : "";
			//lineas += "|";
			lineas += null != tramiteTraficoDto.getVehiculoDto().getAutonomiaElectrica() ? tramiteTraficoDto.getVehiculoDto().getAutonomiaElectrica() : "";
			lineas += "\n";
			result.setLinea(lineas);
			
		} catch (ParseException e) {
			result.setError(true);
			result.setDescripcionError("Error en los datos");
			result.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
		}
		return result;
	}

	private ResumenErroresFicheroMOVE validarDatosObligatorios(TramiteTrafDto tramiteTraficoDto,
			MarcaDgtDto marcaDgtDto) throws OegamExcepcion {
		ResumenErroresFicheroMOVE resultBean = new ResumenErroresFicheroMOVE();

		if (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTraficoDto.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTraficoDto.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTraficoDto.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTraficoDto.getEstado())) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("Error, el tramite no esta FINALIZADO");
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("El Trámite no es de Matriculación");
		} else if (tramiteTraficoDto.getTitular() == null || tramiteTraficoDto.getTitular().getPersona() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("El nombre del Titular es un campo obligatorio");
		} else if (TipoPersona.Fisica.getValorEnum()
				.equals(tramiteTraficoDto.getTitular().getPersona().getTipoPersona())) {
			if (tramiteTraficoDto.getTitular().getPersona().getNombre() == null) {
				resultBean.setError(Boolean.TRUE);
				resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
				resultBean.setDescripcionError("El nombre del Titular es un campo obligatorio");
			}
		} else if (tramiteTraficoDto.getTitular().getPersona().getApellido1RazonSocial() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("El primer apellido / razón social del titular es un campo obligatorio");
		}else if (tramiteTraficoDto.getVehiculoDto() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("No tiene datos del vehículo");
		}else if (tramiteTraficoDto.getVehiculoDto().getModelo() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("El modelo del vehículo es un campo obligatorio");
		} else if (marcaDgtDto.getMarcaSinEditar() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("La marca del vehículo es un campo obligatorio");
		} else if (tramiteTraficoDto.getVehiculoDto().getBastidor() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setNumExpedienteError(tramiteTraficoDto.getNumExpediente().toString());
			resultBean.setDescripcionError("El bastidor del vehículo es un campo obligatorio");
		}

		return resultBean;
	}
	
	@Override
	public ResultBean modificarReferenciaPropia(String[] numsExpedientes, BigDecimal idUsuario, String nuevaReferenciaPropia) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		List<String> listaMensajesError = new ArrayList<String>();
		List<String> listaMensajesOK = new ArrayList<String>();
		long lResultadosOk = 0;
		long lResultadosError = 0;
		for (String numExpediente : numsExpedientes) {
			try {
				ResultBean resultCambioEstado = servicioTramiteTrafico.modificarReferenciaPropia(new BigDecimal(numExpediente), idUsuario, nuevaReferenciaPropia);
				if (resultCambioEstado.getError()) {
					lResultadosError++;
					listaMensajesError.add(resultCambioEstado.getMensaje());
				} else {
					lResultadosOk++;
					listaMensajesOK.add("La referencia propia del expediente " + numExpediente + " se ha cambiado correctamente");
				}
			} catch (Exception e) {
				lResultadosError++;
				listaMensajesError.add("Ha sucedido un error a la hora de cambiar la referencia propia del trámite: " + numExpediente);
				log.error("Ha sucedido un error a la hora de cambiar la referencia propia del tramite: " + numExpediente + ", error: ", e, numExpediente);
			}
		}
		resultado.addAttachment("listaMensajesOK", listaMensajesOK);
		resultado.addAttachment("lResultadosOk", lResultadosOk);
		resultado.addAttachment("lResultadosError", lResultadosError);
		resultado.addAttachment("listaMensajesError", listaMensajesError);
		return resultado;
	}
	
	@Override
	public ResultBean asignarTasaXml(String[] numExpedientes, BigDecimal idUsuario, Long idContrato) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExp : numExpedientes) {
					TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(numExp), Boolean.TRUE);
					if (tramiteTraficoVO != null) {
						Fecha fecha = Utilidades.transformExpedienteFecha(numExp);
						FileResultBean ficheroXml = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.ENVIO, fecha, ConstantesGestorFicheros.NOMBRE_CTIT+numExp, ConstantesGestorFicheros.EXTENSION_XML);
						leerXml(ficheroXml.getFile(),resultado);
						if(!resultado.getError()) {
							// Antes de intentar desasignar la tasa que se envió en el XML a DGT veo si está asignada o no a otro expediente
							TasaVO tasaEnviadaEnXML = servicioTasa.getTasaVO(resultado.getCodigoTasa());
							boolean tasaSinExpedienteAsignado = tasaEnviadaEnXML != null && tasaEnviadaEnXML.getNumExpediente() == null;
							ResultadoBean resultGestionarTasa = new ResultadoBean(Boolean.FALSE);
							if (!tasaSinExpedienteAsignado) { // Si se detecta que la tasa no tiene expediente asignado nos ahorramos la gestión de desasignación
								resultGestionarTasa = servicioTramiteTrafico.gestionDesasignarTasaXMLExpediente(tasaEnviadaEnXML, idContrato, null, idUsuario.longValue());
							}
							// Se asigna la tasa que se envió a DGT si la desasignación de tasa ha ido bien o si la tasa no estaba asignada a ningún expediente
							if(!resultGestionarTasa.getError() || tasaSinExpedienteAsignado) {
								if (resultGestionarTasa.getListaMensajes() != null && !resultGestionarTasa.getListaMensajes().isEmpty()) {
									resultado.addListaMensajes(resultGestionarTasa.getListaMensajes());
								}
								ResultBean resultadoAsig = servicioTasa.asignarTasa(tasaEnviadaEnXML.getCodigoTasa(), tramiteTraficoVO.getNumExpediente());
								if(!resultadoAsig.getError()) {
									TasaVO tasaVO = servicioTasa.getTasaVO(tasaEnviadaEnXML.getCodigoTasa());
									tramiteTraficoVO.setTasa(tasaVO);
									servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
									resultado.addListaMensajes(resultadoAsig.getListaMensajes());
								}
							}else {
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeALista(resultGestionarTasa.getMensaje());
							}
						}else {
							resultado.setError(Boolean.TRUE);
							resultado.addMensajeALista(resultado.getMensaje());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar el trámite.");
		}
		return resultado;
	}

	private void leerXml(File archivoXml, ResultBean resultado) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(archivoXml);
	        doc.getDocumentElement().normalize();
	
	        String etiquetaBuscada = "Tasa_Tramite";

            // Busca la etiqueta en el documento XML
            NodeList nodeList = doc.getElementsByTagName(etiquetaBuscada);

            if (nodeList.getLength() > 0) {
                Node node = nodeList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String valor = element.getTextContent();
                    resultado.setCodigoTasa(valor);
                    log.debug("Valor de la etiqueta " + etiquetaBuscada + ": " + valor);
                }
            } else {
            	log.error("La etiqueta " + etiquetaBuscada + " no se encontró en el archivo XML.");
    			resultado.setError(Boolean.TRUE);
    			resultado.setMensaje("No se encontró la etiqueta Tasa en el XML");
            }
	      
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de leer el XML, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de leer el XML");
		}
		
	}
}