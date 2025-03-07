package org.gestoresmadrid.procesos.model.jobs.atex5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.consultasTGate.model.enumerados.OrigenTGate;
import org.gestoresmadrid.core.consultasTGate.model.enumerados.TipoServicioTGate;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioWebServiceVehiculosAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import trafico.utiles.UtilesWSMatw;
import trafico.utiles.enumerados.TipoAccion;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoAvpoAtex5 extends AbstractProcesoBase {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoAvpoAtex5.class);

	@Autowired
	private ServicioWebServiceVehiculosAtex5 servicioWebServiceVehiculosAtex5;

	@Autowired
	private ServicioVehiculoAtex5 servicioVehiculoAtex5;

	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		String resultadoEjecucion = null;
		String excepcion = null;
		boolean guardar = true;
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
				new UtilesWSMatw().cargarAlmacenesTrafico();

				ResultBean result = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(colaBean.getIdTramite());
				if (result != null && !result.getError()) {
					TramiteTrafSolInfoDto tramiteTrafSolInfoDto = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
					if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null && !tramiteTrafSolInfoDto.getSolicitudes().isEmpty()) {
						try {
							servicioAccionTramite.crearAccionTramite(colaBean.getIdUsuario(), colaBean.getIdTramite(), TipoAccion.SolicitudAVPO.getValorEnum(), utilesFecha.formatoFecha("dd/MM/yyyy",
									utilesFecha.getFechaHoy()), null);
						} catch (OegamExcepcion e) {
							log.error("No se han podido guardar las acciones");
						}

						for (SolicitudInformeVehiculoDto solicitud : tramiteTrafSolInfoDto.getSolicitudes()) {
							if (solicitud.getVehiculo() != null
									&& ((solicitud.getVehiculo().getBastidor() != null && !solicitud.getVehiculo().getBastidor().isEmpty()) || (solicitud.getVehiculo().getMatricula() != null && !solicitud
											.getVehiculo().getMatricula().isEmpty()))) {
								ResultadoWSAtex5Bean resultado = servicioWebServiceVehiculosAtex5.generarConsultaVehiculoAvpoAtex5(colaBean, solicitud.getVehiculo().getMatricula(), solicitud
										.getVehiculo().getBastidor());
								if (resultado.getExcepcion() != null) {
									throw resultado.getExcepcion();
								} else if (resultado.getError()) {
									resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
									String mensaje = "";
									if (resultado.getListaMensajes() != null) {
										for (String mnsj : resultado.getListaMensajes()) {
											mensaje += mnsj + ". ";
										}
									} else {
										mensaje = resultado.getMensajeError();
									}
									colaBean.setRespuesta(mensaje);
									try {
										finalizarErrorNoRecuperable(colaBean, resultado.getMensajeError(), tramiteTrafSolInfoDto.getSolicitudes().size());
										guardar = false;
										break;
									} catch (BorrarSolicitudExcepcion e) {
										log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
										resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
										excepcion = e.toString();
										guardar = false;
										break;
									}
								} else {
									if (resultado.getRespuestaWS() != null && !resultado.getRespuestaWS().isEmpty()) {
										ResultadoAtex5Bean resultObtener = servicioVehiculoAtex5.obtenerXmlProceso(colaBean.getIdTramite());
										if (resultObtener != null && !resultObtener.getError()) {
											if (resultObtener.getFicheroDescarga() != null) {
												procesarFichero(resultObtener.getFicheroDescarga(), tramiteTrafSolInfoDto.getTasa().getCodigoTasa(), solicitud, map);
											} else {
												resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA + " - Sin fichero guardado";
												colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
												finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
												guardar = false;
												break;
											}
										} else {
											resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA + " - Sin fichero guardado";
											colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
											finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
											guardar = false;
											break;
										}
										try {
											servicioWebServiceVehiculosAtex5.guardarConsultasTGate(colaBean.getIdTramite(), colaBean.getIdUsuario(), colaBean.getNumColegiado(), null, resultado
													.getBastidor(), OrigenTGate.SolicitudAvpo.getValorEnum(), TipoServicioTGate.AVPO.getValorEnum(), colaBean.getRespuesta());
										} catch (Exception e) {
											log.error("Error al guardar en Consultas de TGate", e);
										}
									} else {
										resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA + " - Sin respuesta";
										colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
										finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
										guardar = false;
										break;
									}
								}
							}
						}
						if (guardar) {
							if (map != null) {
								FicheroBean fichero = new FicheroBean();
								fichero.setSobreescribir(false);
								fichero.setTipoDocumento(ConstantesGestorFicheros.SOLICITUD_INFORMACION);
								fichero.setFecha(Utilidades.transformExpedienteFecha(tramiteTrafSolInfoDto.getNumExpediente().toString()));
								fichero.setNombreZip("Consultas_" + tramiteTrafSolInfoDto.getNumExpediente().toString());
								fichero.setListaByte((HashMap<String, byte[]>) map);

								gestorDocumentos.empaquetarEnZipByte(fichero);
							}
						}
						servicioAccionTramite.cerrarAccionTramite(colaBean.getIdUsuario(), colaBean.getIdTramite(), TipoAccion.SolicitudAVPO.getValorEnum(), resultadoEjecucion);
					}
				} else {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA + " - No se encuentra el trámite";
					colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
					finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
				}
				actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de consulta dev", e);
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					if (messageException.length() > 500) {
						messageException = messageException.substring(0, 500);
					}
					finalizarTransaccionErrorRecuperableConErrorServico(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, messageException, "0");
			}
		} catch (OegamExcepcion e1) {
			log.error("Ocurrio un error no controlado en el proceso de consulta avpo atex5 al cargar los almacenes de claves", e1);
		}
	}

	private void procesarFichero(File fichero, String codigoTasa, SolicitudInformeVehiculoDto solicitud, Map<String, byte[]> map) {
		String ruta = "C:/Datos/plantillasPDF/avpo/";
		BufferedReader br = null;
		try {
			ReportExporter re = new ReportExporter();
			br = new BufferedReader(new FileReader(fichero));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("TASA", codigoTasa);
			params.put("IMG_DIR", ruta);
			params.put("COLEGIADO", "2056");
			params.put("GESTOR", "RUMBO RUFO");

			Calendar calendar = Calendar.getInstance();
			params.put("HORAINFORME", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
			params.put("FECHAINFORME", utilesFecha.getFechaHoy());

			try {
				byte[] byteFinal = re.generarInforme(ruta, "avpo", "pdf", sb.toString(), "cabecera", params, null);
				String nombreFichero = "";
				if (solicitud.getVehiculo().getMatricula() != null && !solicitud.getVehiculo().getMatricula().isEmpty()) {
					nombreFichero = solicitud.getVehiculo().getMatricula();
				} else if (solicitud.getVehiculo().getBastidor() != null && !solicitud.getVehiculo().getBastidor().isEmpty()) {
					nombreFichero = solicitud.getVehiculo().getBastidor();
				} else if (solicitud.getVehiculo().getNive() != null && !solicitud.getVehiculo().getNive().isEmpty()) {
					nombreFichero = solicitud.getVehiculo().getNive();
				}
				map.put(nombreFichero, byteFinal);
			} catch (JRException e) {}
		} catch (Exception e) {} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el bufferedReader", e);
				}
			}
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.AVPO.getNombreEnum();
	}

	private void finalizarErrorNoRecuperable(ColaBean cola, String respuestaError, int numeroSolicitudes) throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceVehiculosAtex5.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(), EstadoAtex5.Finalizado_Con_Error, respuestaError);
		servicioWebServiceVehiculosAtex5.devolverCreditos(cola.getIdTramite(), cola.getIdContrato(), cola.getIdUsuario(), TipoTramiteTrafico.Solicitud.getValorEnum(), ConceptoCreditoFacturado.DAVP,
				numeroSolicitudes);
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceVehiculosAtex5.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(), EstadoAtex5.Finalizado_PDF, cola.getRespuesta());
	}
}
