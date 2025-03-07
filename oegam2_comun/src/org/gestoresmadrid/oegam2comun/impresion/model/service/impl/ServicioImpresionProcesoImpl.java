package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.impresion.masiva.model.service.ServicioImpresionMasiva;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionBaja;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionDuplicados;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionMatriculacion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionProceso;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionTransmision;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesSession;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;

@Service
public class ServicioImpresionProcesoImpl implements ServicioImpresionProceso {

	private static final long serialVersionUID = 6979184008878726964L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionProcesoImpl.class);

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioImpresionMatriculacion servicioImpresionMatriculacion;

	@Autowired
	private ServicioImpresionBaja servicioImpresionBaja;

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioImpresionMasiva servicioImpresionMasiva;

	@Autowired
	private ServicioImpresionTransmision servicioImpresionTransmision;

	@Autowired
	private ServicioImpresionDuplicados servicioImpresionDuplicados;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	private ServicioCredito servicioCreditoImpl;

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

	private static final String MAIL_HABILITADO = "impresionMasiva.correo.habilitado";
	private static final String RECIPIENT = "impresionMasiva.correo.recipient";
	private static final String DIRECCIONES_OCULTAS = "impresionMasiva.correo.direccionesOcultas";
	private static final String COLEGIADOS_SIN_CORREO = "impresionMasiva.colegiadosSinCorreo";

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean tratarPeticionPorProceso(String[] numsExpedientes,
			CriteriosImprimirTramiteTraficoBean criteriosImprimir, ImprimirTramiteTraficoDto imprimirTramiteTrafico,
			BigDecimal idContrato, BigDecimal idUsuario, String numColegiado) throws OegamExcepcion {
		ResultBean resultado = null;
		if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDF417.toString())) {
			ResultBean result = servicioCreditoImpl.descontarCreditosPorExpedientes(numsExpedientes, imprimirTramiteTrafico, idContrato, idUsuario);
			if (result == null) {
				resultado = new ResultBean(true, "Existen problemas al descontar los créditos. Inténtelo más tarde.");
				log.error("Existen problemas al descontar créditos");
				return resultado;
			} else if (result.getError()) {
				log.error("Ha habido problemas con los créditos");
				resultado = new ResultBean();
				resultado.setError(true);
				resultado.setListaMensajes(result.getListaMensajes());
				return resultado;
			}
		}

		// Llamamos a imprimir
		ResultBean result = null;
		try {
			result = imprimirTramitesPorProceso(numsExpedientes, criteriosImprimir, numColegiado, idUsuario, idContrato);
		} catch (OegamExcepcion e) {
			throw new OegamExcepcion(e.getMessage());
		}

		if ((result != null && result.getError()) && criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDF417.toString())) {
			// Si es un 417 y no se ha podido imprimir, devolvemos los créditos
			ResultBean rs = servicioCreditoImpl.descontarCreditosPorExpedientes(numsExpedientes, imprimirTramiteTrafico, idContrato, idUsuario);
			if (rs == null) {
				resultado = new ResultBean(true, "Existen problemas técnicos. Se le ha descontado los créditos, pero no se ha podido generar el documento. Contacte con el Colegio.");
				log.error("ERROR MUY GRAVE. Se han descontado " + numsExpedientes.length + " créditos y no se ha generado el documento del usuario " + utilesColegiado.getIdUsuarioSessionBigDecimal());
				return resultado;
			} else if (rs.getError()) {
				resultado = new ResultBean(true, "Existen problemas técnicos. Se le ha descontado los créditos, pero no se ha podido generar el documento. Contacte con el Colegio.");
				log.error("ERROR MUY GRAVE. Se han descontado " + numsExpedientes.length + " créditos y no se ha generado el documento del usuario " + utilesColegiado.getIdUsuarioSessionBigDecimal());
				return resultado;
			}

		}
		if (result == null) {
			resultado = new ResultBean(false, "Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
			log.info("Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
		} else if (result.getError()) {
			for (String sResult : result.getListaMensajes()) {
				log.error("Existen errores en la solicitud de impresión: " + sResult);
			}
			return result;
		}
		return resultado;
	}

	private ResultBean imprimirTramitesPorProceso(String[] numsExpedientes,
			CriteriosImprimirTramiteTraficoBean criteriosImprimir, String numColegiado, BigDecimal idUsuario,
			BigDecimal idContrato) throws OegamExcepcion {
		ResultBean result = null;
		TipoTramiteTrafico tipoTramite = criteriosImprimir.getTipoTramite();
		TipoTramiteMatriculacion tipoTramiteMatriculacion = criteriosImprimir.getTipoTramiteMatriculacion();
		String tipoImpreso = criteriosImprimir.getTipoImpreso();

		if (tipoTramite == TipoTramiteTrafico.Matriculacion && tipoTramiteMatriculacion != null) {
			// Borrador
			if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417Matw.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} // 417
			else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417Matw.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} // Telemática
			else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			}
			// Lista bastidores
			else if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
				ResultBean rs = servicioImpresionMatriculacion.imprimirEnProcesoListaBastidores(numsExpedientes);
				if (rs == null) {
					result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
				} else {
					result = rs;
				}
			}
		} else if (tipoTramite == TipoTramiteTrafico.Transmision || tipoTramite == TipoTramiteTrafico.TransmisionElectronica) {
			// Borrador
			if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} // 417
			else if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} // Telemática
			else if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} // Lista bastidores
			else if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
				ResultBean rs = servicioImpresionTransmision.imprimirEnProcesoListaBastidores(numsExpedientes);
				if (rs == null) {
					result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
				} else {
					result = rs;
				}
			}
		} else if (tipoTramite == TipoTramiteTrafico.Baja) {
			// Borrador
			if (tipoImpreso.equals(TipoImpreso.BajasBorradorPDF417.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			}
			// 417
			else if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			}
			// Lista bastidores
			else if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			} else if(TipoImpreso.BajasTelematicas.toString().equals(tipoImpreso)) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			}
		} else if (tipoTramite == TipoTramiteTrafico.Duplicado) {
			if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
				result = imprimirEnProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado, idUsuario, idContrato);
			}
		}
		return result;
	}

	private ResultBean imprimirEnProceso(String[] numsExpedientes, String tipoImpreso, TipoTramiteTrafico tipoTramite,
			String numColegiado, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		ResultBean result = null;
		String nombreFichero = crearFicheroProceso(numsExpedientes, tipoImpreso, tipoTramite, numColegiado);
		if (!crearColaProceso(nombreFichero, numColegiado, idUsuario, idContrato)) {
			result = new ResultBean(true, "No se ha podido crear la llamada al proceso");
		}
		return result;
	}

	private boolean crearColaProceso(String nombreFichero, String numColegiado, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		String idTramite = numColegiado + utilesFecha.formatoFecha("ddMMyyHHmmss", new Date());
		String xmlEnviar = nombreFichero + ".txt";
		String nodo = null;
		String entornoProceso = gestorPropiedades.valorPropertie("entornoProcesos");
		ProcesoVO procesoVO = null;
		if ("DESARROLLO".equals(entornoProceso)) {
			nodo = getNodoSolicitud2();
		} else {
			String tomarNodoProceso = gestorPropiedades.valorPropertie("tomar.nodo.proceso");
			if ("SI".equals(tomarNodoProceso)) {
				procesoVO = servicioProcesos.getProceso(ProcesosEnum.IMPRIMIRTRAMITES.getNombreEnum());
				if (procesoVO != null && procesoVO.getId() != null) {
					nodo = procesoVO.getId().getNodo();
				}
			} else {
				nodo = getNodoSolicitud2();
			}
		}
		ColaVO colaVO = servicioCola.nuevaCola(ProcesosEnum.IMPRIMIRTRAMITES.getNombreEnum(), nodo,
				ConstantesSession.TIPO_TRAMITE_IMPRESION_MASIVA, idTramite, idUsuario, xmlEnviar, null, idContrato);
		colaVO = servicioCola.generarCola(colaVO);
		return (colaVO.getIdEnvio() != null);
	}

	private String crearFicheroProceso(String[] numExpedientes, String tipoImpreso, TipoTramiteTrafico tipoTramite, String numColegiado) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		String fecha = utilesFecha.formatoFecha("ddMMyyyyHHmmss", new Date());
		String nombreFicheroAEnviar = "ImprimirMasivo_" + numColegiado + "_" + fecha;

		String tImpreso = tipoImpreso + "\n";
		String tTramite = tipoTramite.getNombreEnum() + "\n";
		String numColeg = numColegiado + "\n";
		try {
			bos.write(tImpreso.getBytes());
			bos.write(tTramite.getBytes());
			bos.write(numColeg.getBytes());
			// Recorremos lista de trámites
			for (int i = 0; i < numExpedientes.length; i++) {
				String linea = numExpedientes[i] + "\n";
				bos.write(linea.getBytes());
			}
			guardarFicheroProceso(bos.toByteArray(), nombreFicheroAEnviar, ConstantesGestorFicheros.IMPRESION_MASIVA,
					ConstantesGestorFicheros.PETICIONES, ConstantesGestorFicheros.EXTENSION_TXT);
			return nombreFicheroAEnviar;
		} catch (IOException e) {
			log.error("Error generando documento de impresión masivo por proceso", e);
			log.error(e);
		} catch (Exception e) {
			log.error("Error generando documento de impresión masivo por proceso", e);
			log.error(e);
		} catch (OegamExcepcion e) {
			log.error("Error generando documento de impresión masivo por proceso", e);
			log.error(e);
		}
		return null;
	}

	public File guardarFicheroProceso(byte[] bytesFichero, String nombreFichero, String tipoDocumento, String subTipo, String extension) throws Exception, IOException, OegamExcepcion {
		log.info(" Guardar Documentos del Proceso impresión masiva");

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(tipoDocumento);
		fichero.setSubTipo(subTipo);
		fichero.setSobreescribir(true);
		fichero.setSubCarpetaDia(true);

		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setNombreDocumento(nombreFichero);

		fichero.setFicheroByte(bytesFichero);
		fichero.setExtension(extension);
		return gestorDocumentos.guardarByte(fichero);
	}

	public String getNodoSolicitud2() {
		return gestorPropiedades.valorPropertie("nombreHostSolicitudProcesos2");
	}

	@Override
	public File buscarFichero(ColaBean colaBean) throws OegamExcepcion {
		String[] separadorExtension = colaBean.getXmlEnviar().split("\\.");
		String fechaHora = separadorExtension[0].split("\\_")[2];
		Fecha fecha = utilesFecha.getFechaConDate(utilesFecha.getFechaHoraDate(fechaHora));

		return gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_MASIVA, ConstantesGestorFicheros.PETICIONES, fecha, separadorExtension[0], ".txt");
	}

	@Override
	public void enviarCorreo(String nombreFichero, ResultBean result, ColaBean colaBean, String[] numExpedientes, String tipoImpreso, String tipoTramite, String numColegiado) {
		String subject = null;
		String recipent = null;
		String direccionesOcultas = null;
		try {
			// Si el colegiado está en la propertie no se envía el correo
			String colegiadosSinCorreo = gestorPropiedades.valorPropertie(COLEGIADOS_SIN_CORREO);
			if (colegiadosSinCorreo == null || (colegiadosSinCorreo != null && !colegiadosSinCorreo.contains(numColegiado))) {
				subject = result.getError() ? "Fallo en Impresión Masiva" : "Impresión Masiva disponible";

				String entorno = gestorPropiedades.valorPropertie("Entorno");
				if (!"PRODUCCION".equals(entorno)) {
					subject = entorno + ": " + subject;
				}

				StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">La impresión masiva de ");
				if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
					texto.append("los Borradores 417 ");
				} else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417.toString())) {
					texto.append("los PDF 417 ");
				} else if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
					texto.append("la Relación de Matrículas ");
				} else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
					texto.append("los Documentos Telemáticos ");
				}

				if (result.getError()) {
					texto.append("ha fallado:<br><u>Mensaje error:</u><br>");
					texto.append(result.getMensaje());
				} else {
					texto.append("está disponible para ser impresa.<br><br><u>Números de expedientes:</u><br>");
					for (String numExpediente : numExpedientes) {
						texto.append(" - " + numExpediente + "<br>");
					}
				}

				texto.append("</span>");

				if (result.getError()) {
					texto.append("<br><b>(" + nombreFichero + ".pdf)</b><br><br>");
				}

				String correoHabilitado = gestorPropiedades.valorPropertie(MAIL_HABILITADO);
				if (correoHabilitado != null && !"".equals(correoHabilitado) && correoHabilitado.equals("SI")) {
					ContratoVO contratoVO = servicioContrato.getContrato(colaBean.getIdContrato());

					recipent = contratoVO.getCorreoElectronico();
					if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
						for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
							if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
								recipent = correoContratoTramite.getCorreoElectronico();
								break;
							}
						}
					}

					direccionesOcultas = gestorPropiedades.valorPropertie(DIRECCIONES_OCULTAS);
				} else {
					recipent = gestorPropiedades.valorPropertie(RECIPIENT);
				}

				ResultBean resultado;

				resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null, null);

				if (resultado.getError()) {
					log.error("No se ha enviado el correo para las impresiones masivas. Error: " + resultado.getMensaje());
				}
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de la impresión masiva", e);
		}
	}

	@Override
	public ResultBean procesar(ColaBean colaBean, String tipoImpreso, String tipoTramite, String numColegiado, String[] numExpedientes) throws OegamExcepcion {
		String[] separadorExtension = colaBean.getXmlEnviar().split("\\.");
		String fechaHora = separadorExtension[0].split("\\_")[2];
		ResultBean result = null;
		String nombreFichero = null;
		try {
			nombreFichero = tipoImpreso + "_" + numColegiado + "_" + fechaHora;
			byte[] byteFinal = null;
			ResultBean rs = null;
			if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
				rs = procesarNumExpedientesALaVez(colaBean, tipoTramite, numColegiado, numExpedientes);
			} else {
				rs = procesarNumExpedientesUnoAUno(colaBean, tipoTramite, numColegiado, numExpedientes, tipoImpreso);
			}

			String textoNotificacion = "";
			if (rs != null && !rs.getError()) {
				byteFinal = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
				File ficheroGuardado = guardarDocumento(byteFinal, nombreFichero, tipoImpreso);

				if (ficheroGuardado != null) {
					rs = servicioImpresionMasiva.guardarImpresionMasiva(ficheroGuardado.getPath(), ficheroGuardado.getName(), fechaHora, tipoImpreso, numColegiado);
				}

				rs = cambioEstado(colaBean, numExpedientes, tipoImpreso);
				if (ficheroGuardado != null && rs != null && rs.getError()) {
					borradoRecursivoFichero(ficheroGuardado);
					return rs;
				}
				textoNotificacion = "IMPRESIÓN MASIVA CORRECTA";
			} else {
				textoNotificacion = "FALLO IMPRESIÓN MASIVA";
				return new ResultBean(true, textoNotificacion);
			}

			result = new ResultBean(false, textoNotificacion);
			result.addAttachment("nombreFichero", nombreFichero);
			return result;
		} catch (CambiarEstadoTramiteTraficoExcepcion e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		} catch (OegamExcepcion e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
	}

	@Override
	public BigDecimal[] convertirNumExp(String[] numExpedientes) {
		BigDecimal[] numExpe = null;
		if (numExpedientes != null) {
			numExpe = new BigDecimal[numExpedientes.length];
			for (int i = 0; i < numExpedientes.length; i++) {
				numExpe[i] = new BigDecimal(numExpedientes[i]);
			}
		}
		return numExpe;
	}

	@Override
	@Transactional
	public ResultBean devolverCreditos(String tipoTramite, String tipoImpreso, BigDecimal[] numerosExpedientes, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean result = null;
		if (TipoImpreso.MatriculacionPDF417.toString().equals(tipoImpreso)) {
			TipoTransferenciaActual tipoTransmisionActual = null;
			TipoTransferencia tipoTransmision = null;
			ConceptoCreditoFacturado concepto = null;

			if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(tipoTramite)) {
				tipoTransmisionActual = TipoTransferenciaActual.convertir(servicioTramiteTrafico.getMismoTipoCreditoTramiteTransmision(numerosExpedientes, TipoTramiteTrafico.Transmision));
			} else if (TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(tipoTramite)) {
				tipoTransmision = TipoTransferencia.convertir(servicioTramiteTrafico.getMismoTipoCreditoTramiteTransmision(numerosExpedientes, TipoTramiteTrafico.TransmisionElectronica));
			}

			if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(tipoTramite)) {
				concepto = ConceptoCreditoFacturado.DITM;
			} else if (TipoTramiteTrafico.Baja.getNombreEnum().equals(tipoTramite)) {
				concepto = ConceptoCreditoFacturado.DITB;
			} else if (TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(tipoTramite)) {
				concepto = ConceptoCreditoFacturado.DITT;
			}

			TipoTramiteTrafico tipoTramiteTrafico = servicioCreditoImpl.getTipoCreditosACobrar(TipoTramiteTrafico.convertirPorNombre(tipoTramite), tipoTransmisionActual, tipoTransmision);
			result = servicioCreditoImpl.devolverCreditos(tipoTramiteTrafico.getValorEnum(), idContrato, numerosExpedientes.length, idUsuario, concepto, utiles
					.convertirBigDecimalArrayToStringArray(numerosExpedientes));
		}
		return result;
	}

	private ResultBean cambioEstado(ColaBean colaBean, String[] numExpedientes, String tipoImpreso) throws CambiarEstadoTramiteTraficoExcepcion {
		ResultBean resultado = null;
		ResultBean result = null;
		// Cambio de estado para el PDF417 y telemática
		if (TipoImpreso.MatriculacionPDF417Matw.toString().equals(tipoImpreso) || TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString().equals(tipoImpreso)) {
			for (String numExp : numExpedientes) {
				if (TipoImpreso.MatriculacionPDF417Matw.toString().equals(tipoImpreso)) {
					result = servicioTramiteTrafico.validarPDF417(new String[] { numExp });
					if (result != null && !result.getError()) {
						EstadoTramiteTrafico[] estados = new EstadoTramiteTrafico[2];
						estados[0] = EstadoTramiteTrafico.Validado_PDF;
						estados[1] = EstadoTramiteTrafico.Validado_Telematicamente;
						resultado = servicioTramiteTrafico.cambiarEstadoConEvolucionEstadosPermitidos(new BigDecimal(numExp), EstadoTramiteTrafico.Finalizado_PDF, estados, true,
								TextoNotificacion.Cambio_Estado.getValorEnum(), colaBean.getIdUsuario());
					}
				} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString().equals(tipoImpreso)) {
					result = servicioTramiteTrafico.validarPDFPresentacionTelematica(new String[] { numExp });
					if (result != null && !result.getError()) {
						resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(new BigDecimal(numExp), EstadoTramiteTrafico.Finalizado_Telematicamente,
								EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso, true, TextoNotificacion.Cambio_Estado.getValorEnum(), colaBean.getIdUsuario());
					}
				}
			}
			if (resultado != null && resultado.getError()) {
				throw new CambiarEstadoTramiteTraficoExcepcion(resultado.getMensaje());
			}
		}
		return resultado;
	}

	private File guardarDocumento(byte[] bytePdf, String nombreFichero, String tipoImpreso) throws OegamExcepcion {
		File fichero = null;
		try {
			// Borrador
			if (TipoImpreso.MatriculacionBorradorPDF417Matw.toString().equals(tipoImpreso)) {
				fichero = servicioImpresion.guardarFicheroProceso(bytePdf, nombreFichero, ConstantesGestorFicheros.IMPRESION_MASIVA, ConstantesGestorFicheros.BORRADOR_PDF_417,
						ConstantesGestorFicheros.EXTENSION_PDF);
			} // 417
			else if (TipoImpreso.MatriculacionPDF417Matw.toString().equals(tipoImpreso)) {
				fichero = servicioImpresion.guardarFicheroProceso(bytePdf, nombreFichero, ConstantesGestorFicheros.IMPRESION_MASIVA, ConstantesGestorFicheros.PDF_417,
						ConstantesGestorFicheros.EXTENSION_PDF);
			} // Telemática
			else if (TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString().equals(tipoImpreso)) {
				fichero = servicioImpresion.guardarFicheroProceso(bytePdf, nombreFichero, ConstantesGestorFicheros.IMPRESION_MASIVA, ConstantesGestorFicheros.PDF_PRESENTACION_TELEMATICA,
						ConstantesGestorFicheros.EXTENSION_PDF);
				// Lista bastidores (la impresión se realiza con todos los expedientes juntos)
			} else if (TipoImpreso.MatriculacionListadoBastidores.toString().equals(tipoImpreso)) {
				fichero = servicioImpresion.guardarFicheroProceso(bytePdf, nombreFichero, ConstantesGestorFicheros.IMPRESION_MASIVA, ConstantesGestorFicheros.LISTADO_BASTIDORES,
						ConstantesGestorFicheros.EXTENSION_PDF);
			}
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
		return fichero;
	}

	private ResultBean procesarNumExpedientesUnoAUno(ColaBean colaBean, String tipoTramite, String numColegiado, String[] numExpedientes, String tipoImpreso) throws OegamExcepcion {
		ResultBean resultadoImpresion = null;
		try {
			TipoImpreso tipoImp = TipoImpreso.convertirPorTexto(tipoImpreso);
			if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(tipoTramite)) {
				resultadoImpresion = servicioImpresionMatriculacion.imprimirMatwProceso(numExpedientes, colaBean.getIdUsuario(), tipoImp);
			} else if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(tipoTramite)) {
				resultadoImpresion = servicioImpresionTransmision.imprimirTransmision(numExpedientes, numColegiado, colaBean.getIdContrato(), colaBean.getIdUsuario(), TipoTramiteTrafico.Transmision,
						tipoImp, true);
			} else if (TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(tipoTramite)) {
				resultadoImpresion = servicioImpresionTransmision.imprimirTransmision(numExpedientes, numColegiado, colaBean.getIdContrato(), colaBean.getIdUsuario(),
						TipoTramiteTrafico.TransmisionElectronica, tipoImp, true);
			} else if (TipoTramiteTrafico.Baja.getNombreEnum().equals(tipoTramite)) {
				resultadoImpresion = servicioImpresionBaja.imprimirBaja(numExpedientes, colaBean.getIdUsuario(), tipoImp, true);
			}
		} catch (Throwable e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
		return resultadoImpresion;
	}

	private ResultBean procesarNumExpedientesALaVez(ColaBean colaBean, String tipoTramite, String numColegiado, String[] numExpedientes) {
		ResultBean resultado = null;
		try {
			if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(tipoTramite)) {
				resultado = servicioImpresionMatriculacion.imprimirListadoBastidores(numExpedientes);
			} else if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(tipoTramite)) {
				resultado = servicioImpresionTransmision.imprimirListadoBastidores(numExpedientes, numColegiado, colaBean.getIdContrato(), TipoTramiteTrafico.Transmision);
			} else if (TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(tipoTramite)) {
				resultado = servicioImpresionTransmision.imprimirListadoBastidores(numExpedientes, numColegiado, colaBean.getIdContrato(), TipoTramiteTrafico.TransmisionElectronica);
			} else if (TipoTramiteTrafico.Baja.getNombreEnum().equals(tipoTramite)) {
				resultado = servicioImpresionBaja.impresionListadoBastidoresBaja(numExpedientes, colaBean.getIdUsuario());
			} else if (TipoTramiteTrafico.Duplicado.getNombreEnum().equals(tipoTramite)) {
				resultado = servicioImpresionDuplicados.imprimirListadoMatriculas(numExpedientes, colaBean.getIdUsuario());
			}
		} catch (OegamExcepcion e) {
			log.error("Error procesando los expedientes a la vez en el proceso impresiones masivas, error: " + e.getMessage(), e);
			resultado = new ResultBean(true, "Error procesando los expedientes a la vez en el proceso impresiones masivas");
			return resultado;
		} catch (Throwable e) {
			log.error("Error procesando los expedientes a la vez en el proceso impresiones masivas, error: " + e.getMessage(), e);
			resultado = new ResultBean(true, "Error procesando los expedientes a la vez en el proceso impresiones masivas");
			return resultado;
		}
		return resultado;
	}

	@Override
	public void borradoRecursivoFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	@Override
	public ResultBean leerFichero(File fichero) throws Exception {
		ResultBean result = null;
		FileInputStream fin = new FileInputStream(fichero);
		BufferedReader inputAnterior = new BufferedReader(new InputStreamReader(fin, Claves.ENCODING_ISO88591));
		String ln = null;
		List<String> numExpedientes = new ArrayList<>();
		if (inputAnterior != null) {
			result = new ResultBean();
			result.addAttachment("tipoImpreso", inputAnterior.readLine());
			result.addAttachment("tipoTramite", inputAnterior.readLine());
			result.addAttachment("numColegiado", inputAnterior.readLine());
			while ((ln = inputAnterior.readLine()) != null) {
				numExpedientes.add(ln);
			}
			String[] numExp = utiles.transformListaToArrayString(numExpedientes);
			result.addAttachment("numExpedientes", numExp);
			inputAnterior.close();
		}
		return result;
	}
}