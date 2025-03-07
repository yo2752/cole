package trafico.tasas.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioConsultaTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioGeneracionEtiquetasTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ConsultaTasaBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.GeneracionEtiquetasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.DecisionTrafico;
import general.utiles.UtilesTrafico;
import general.utiles.UtilidadIreport;
import trafico.beans.ResumenCertificadoTasas;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQTasasDesasignar;
import trafico.beans.daos.BeanPQTasasDetalle;
import trafico.beans.daos.BeanPQTasasEliminar;
import trafico.beans.daos.pq_facturacion.TablaFacturacionBean;
import trafico.beans.daos.pq_tasas.BeanPQDETALLE;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.modelo.ModeloTasas;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.exportacion.TramaTasa;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@SuppressWarnings("serial")
public class ConsultaTasaAction extends AbstractPaginatedListAction {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTasaAction.class);

	private Integer formato;
	private String almacenNuevo;
	private String codSeleccionados;

	private DecisionTrafico asignada;
	private String idCodigoTasaSeleccion;
	private String idCodigoTasaPegatinaSeleccion;

	private String motivo;

	// Fichero de errores a descargar
	private InputStream ficheroTasas;
	private boolean ficheroOK = false;
	private String numsExpediente;
	private ResumenCertificadoTasas resumenCertificadoTasas = new ResumenCertificadoTasas();
	Date inicio = null;
	Date fin = null;
	private ArrayList<TablaFacturacionBean> tablaFacturacionBeanLista = new ArrayList<>();
	int tasas = 0;
	private Boolean resumenCertificadoTasasFlag = false;
	private SolicitudDatosBean solicitud;

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en
										// PDF del action
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	private String ficheroDescarga;

	private final static String RESULTADO_POR_DEFECTO = "consultaTasa";
	private final static String POP_UP_MOTIVO = "popPupMotivo";
	private final static String POP_UP_ALMACEN = "popUpCambioAlmacen";

	private ConsultaTasaBean consultaTasaBean;

	private TasaDto detalle;

	@Resource
	ModelPagination modelTasaPagination;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas;

	@Autowired
	ServicioConsultaTasa servicioConsultaTasa;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ModeloTrafico modeloTrafico;
	private ModeloTasas modeloTasas;
	private ModeloMatriculacion modeloMatriculacion;
	private ModeloSolicitudDatos modeloSolicitudDatos;
	private ModeloDuplicado modeloDuplicado;
	private ModeloTransmision modeloTransmision;
	private UtilidadIreport utilidadIreport;
	// JMBC Añado variable para generar los certificados de numExpediente ya que
	// el check cambia
	private String listaChecksConsultaTasa;
	private String listaChecksConsultaTasaPegatina;

	public String detalle() throws Throwable {
		// El idCodigoTasaSeleccion sólo será de una tasa concreta
		String idCodigoTasa = getIdCodigoTasaSeleccion();

		if (!FormatoTasa.PEGATINA.equals(FormatoTasa.convertir(formato))) {
			detalle = servicioTasa.getTasaCodigoTasa(idCodigoTasa);
		} else {
			detalle = servicioTasa.getTasaPegatinaCodigoTasa(idCodigoTasa);
		}
		return "detalleTasa";
	}

	@SuppressWarnings("unchecked")
	public String generarTasasPegatinas() {
		String[] numTasas = null;
		ResultBean resultado = servicioGeneracionEtiquetasTasas.comprobarDatosObligatoriosGeneracionTasasEtiquetas(idCodigoTasaSeleccion, idCodigoTasaPegatinaSeleccion);
		if (resultado == null) {
			numTasas = idCodigoTasaPegatinaSeleccion.split("-");
			resultado = servicioGeneracionEtiquetasTasas.generarListadoEtiquetas(numTasas);
			if (resultado != null && !resultado.getError()) {
				List<String> listaErrores = (List<String>) resultado.getAttachment("listaErrores");
				BigDecimal idContratoTasas = (BigDecimal) resultado.getAttachment("idContrato");
				List<GeneracionEtiquetasBean> listaGenEtiquetasBean = (List<GeneracionEtiquetasBean>) resultado.getAttachment("listaGeneracionEtiquetas");
				if (listaGenEtiquetasBean != null && !listaGenEtiquetasBean.isEmpty()) {
					resultado = servicioGeneracionEtiquetasTasas.crearDocumentoSolicitudColaPegatinas(listaGenEtiquetasBean, idContratoTasas, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado == null || !resultado.getError()) {
						addActionMessage("La solicitud para generar las tasas de pegatinas se ha encolado correctamente.");
					}
				}
				if (resultado != null && resultado.getError()) {
					for (String error : resultado.getListaMensajes()) {
						addActionError(error);
					}
				} else {
					if (listaErrores != null && !listaErrores.isEmpty()) {
						for (String error : listaErrores) {
							addActionError(error);
						}
					}
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError(resultado.getListaMensajes().get(0));
		}
		return navegar();
	}

	public String desasignar() throws Throwable {
		// El idCodigoTasaSeleccion puede ser de varias tasas
		String idCodigoTasa = getIdCodigoTasaSeleccion();

		String[] tasasSeleccionadas = idCodigoTasa.split("-");

		for (int i = 0; i < tasasSeleccionadas.length; i++) {
			BeanPQTasasDesasignar beanDesasignar = new BeanPQTasasDesasignar();
			beanDesasignar.setP_CODIGO_TASA(tasasSeleccionadas[i]);
			if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoColegio()) {
				beanDesasignar.setP_NUM_COLEGIADO(null);
			}
			beanDesasignar.setP_ID_CONTRATO(new BigDecimal(utilesColegiado.getIdContratoSession()));
			beanDesasignar.setP_ID_CONTRATO_SESSION(new BigDecimal(utilesColegiado.getIdContratoSession()));
			beanDesasignar.setP_ID_USUARIO(new BigDecimal(utilesColegiado.getIdUsuarioSession()));
			String resultado = getModeloTasas().desasignarTasa(beanDesasignar);
			if (!resultado.equals("OK")) {
				addActionError("Error al desasignar la tasa " + tasasSeleccionadas[i] + ": " + resultado);
			} else {
				addActionMessage("La tasa " + tasasSeleccionadas[i] + " ha sido desasignada");
			}
		}
		return navegar();
	}

	public String cambiarFormato() throws Throwable {
		ResultBean respuesta = servicioTasa.cambiarFormato(idCodigoTasaSeleccion, formato, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (respuesta != null && !respuesta.getError()) {
			if (respuesta.getListaMensajes() != null && !respuesta.getListaMensajes().isEmpty()) {
				addActionMessage("Cambios de formato realizados con los siguientes errores:");
				for (String mensaje : respuesta.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				addActionMessage("Cambios de formato realizados correctamente.");
			}
		} else {
			addActionMessage("No se ha podido realizar los cambios de formato");
		}
		return navegar();
	}

	public String cambiarAlmacen() throws Throwable {
		ResultadoTasaBean resultado = servicioTasa.cambiarAlmacen(codSeleccionados, almacenNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
		gestionarResultado(resultado);
		return navegar();
	}

	private void gestionarResultado(ResultadoTasaBean resultado) {
		if (resultado.getListaMensajesOk() != null && !resultado.getListaMensajesOk().isEmpty()) {
			for (String mensaje : resultado.getListaMensajesOk()) {
				addActionMessage(mensaje);
			}
		}
		if (resultado.getListaMensajesError() != null && !resultado.getListaMensajesError().isEmpty()) {
			for (String mensaje : resultado.getListaMensajesError()) {
				addActionError(mensaje);
			}
		}
		if(resultado.getMensaje() != null && !resultado.getMensaje().isEmpty()) {
			resultado.setMensaje(resultado.getMensaje());
		}
	}

	public String bloquearTasa() throws Throwable {
		ResultBean respuesta = servicioTasa.bloquearTasa(idCodigoTasaSeleccion, motivo, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (respuesta != null && !respuesta.getError()) {
			if (respuesta.getListaMensajes() != null && !respuesta.getListaMensajes().isEmpty()) {
				addActionMessage("Tasas bloqueadas con los siguientes errores:");
				for (String mensaje : respuesta.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				addActionMessage("Tasas bloqueadas correctamente.");
			}
		} else {
			addActionMessage("No se ha podido realizar los bloqueos de tasas");
		}
		return navegar();
	}

	public String desbloquearTasa() throws Throwable {
		ResultBean respuesta = servicioTasa.desbloquearTasa(idCodigoTasaSeleccion, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (respuesta != null && !respuesta.getError()) {
			if (respuesta.getListaMensajes() != null && !respuesta.getListaMensajes().isEmpty()) {
				addActionMessage("Tasas desbloqueadas con los siguientes errores:");
				for (String mensaje : respuesta.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				addActionMessage("Tasas desbloqueadas correctamente.");
			}
		} else {
			addActionMessage("No se ha podido realizar los desbloqueos de tasas");
		}
		return navegar();
	}

	public String exportar() throws Throwable {
		// El idCodigoTasaSeleccion puede ser de varias tasas
		String idCodigoTasa = getIdCodigoTasaSeleccion();

		String[] tasasSeleccionadas = idCodigoTasa.split("-");
		ArrayList<BeanPQTasasDetalle> listaTasas = new ArrayList<>();
		BeanPQTasasDetalle beanDetalle;

		// Obtenemos la lista de tasas
		for (int i = 0; i < tasasSeleccionadas.length; i++) {
			String codTasa = tasasSeleccionadas[i];
			beanDetalle = new BeanPQTasasDetalle();
			beanDetalle.setP_CODIGO_TASA(codTasa);
			beanDetalle.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
			beanDetalle.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
			beanDetalle = getModeloTasas().detalleTasa(beanDetalle);
			listaTasas.add(beanDetalle);
		}

		// Comprobamos que son del mismo contrato
		BigDecimal contratoTasa = null;
		for (int i = 0; i < listaTasas.size(); i++) {
			BeanPQTasasDetalle tasa = listaTasas.get(i);
			if (contratoTasa == null)
				contratoTasa = tasa.getP_ID_CONTRATO();
			else if ("0".equals(contratoTasa.compareTo(tasa.getP_ID_CONTRATO()))) {
				addActionError("Las tasas seleccionadas no son todas del mismo contrato");
				return navegar();
			}
		}

		// Si son del mismo contrato, las convertimos en el String del fichero
		// de salida
		ArrayList<String> lineasFichero = TramaTasa.getLineas(listaTasas);

		try {
			String idSession = ServletActionContext.getRequest().getSession().getId();
			// creamos el fichero con los errores
			// Gestor de ficheros nueva historificación
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXPORTAR);
			fichero.setSubTipo(ConstantesGestorFicheros.TASAS);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
			fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_FICHERO_TASA + idSession);
			fichero.setSobreescribir(true);
			fichero.setFecha(utilesFecha.getFechaActual());
			File file = gestorDocumentos.guardaFicheroEnviandoStrings(fichero, lineasFichero);

			// y a los 5 minutos, lo borraremos
			BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath());
			hiloBorrar.start();
			log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
			addActionMessage("Tasas exportadas correctamente");
			ficheroOK = true;
		} catch (Exception e) {
			addActionError("Error al crear el fichero");
		}
		return navegar();
	}

	public String descargarFichero() {
		// String ruta = "C:/temp/importacion/";
		String idSession = ServletActionContext.getRequest().getSession().getId();

		try {
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXPORTAR, ConstantesGestorFicheros.TASAS, utilesFecha.getFechaActual(),
					ConstantesGestorFicheros.NOMBRE_FICHERO_TASA + idSession, ConstantesGestorFicheros.EXTENSION_TXT);
			if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				addActionError(fileResultBean.getMessage());
			} else {
				ficheroTasas = new FileInputStream(fileResultBean.getFile());
			}

		} catch (FileNotFoundException e) {
			log.error(e);
			return navegar();
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el archivo", e);
			return navegar();
		}
		return "ficheroDownload";
	}

	public String eliminar() throws Throwable {
		if (getIdCodigoTasaSeleccion() != null && !getIdCodigoTasaSeleccion().isEmpty()) {
			String[] tasasSeleccionadas = getIdCodigoTasaSeleccion().split("-");
			for (int i = 0; i < tasasSeleccionadas.length; i++) {
				BeanPQTasasEliminar beanEliminar = new BeanPQTasasEliminar();
				beanEliminar.setP_CODIGO_TASA(tasasSeleccionadas[i]);
				if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoColegio()) {
					beanEliminar.setP_NUM_COLEGIADO(null);
				}
				beanEliminar.setP_ID_CONTRATO(new BigDecimal(utilesColegiado.getIdContratoSession()));
				beanEliminar.setP_ID_CONTRATO_SESSION(new BigDecimal(utilesColegiado.getIdContratoSession()));
				beanEliminar.setP_ID_USUARIO(new BigDecimal(utilesColegiado.getIdUsuarioSession()));
				String resultado = getModeloTasas().eliminarTasa(beanEliminar);
				if (!resultado.equals("OK")) {
					addActionError("Error al eliminar la tasa " + tasasSeleccionadas[i] + ": " + resultado);
				} else {
					addActionMessage("La tasa " + tasasSeleccionadas[i] + " ha sido eliminada");
					log.error("La tasa con codigo " + tasasSeleccionadas[i] + ", ha sido eliminada por el usuario: " + 
							utilesColegiado.getIdUsuarioSession());
				}
			}
		}
		if (getIdCodigoTasaPegatinaSeleccion() != null && !getIdCodigoTasaPegatinaSeleccion().isEmpty()) {
			for (String codigoTasaPegatina : getIdCodigoTasaPegatinaSeleccion().split("-")) {
				String resultado = servicioTasa.eliminarTasaPegatina(codigoTasaPegatina);

				if (!resultado.equals("OK")) {
					addActionError("Error al eliminar la tasa " + codigoTasaPegatina + ": " + resultado);
				} else {
					addActionMessage("La tasa " + codigoTasaPegatina + " ha sido eliminada");
					log.error("La tasa con codigo " + codigoTasaPegatina + ", ha sido eliminada por el usuario: " + utilesColegiado.getIdUsuarioSession());
				}
			}
		}
		return navegar();
	}

	public String generarCertificados() {
		try {
			/*
			 * String[] codSeleccionados = null; String esNuevo = gestorPropiedades.valorPropertie("certificados.tasas.nuevo"); if(esNuevo != null && !esNuevo.isEmpty() && "SI".equals(esNuevo)){
			 */
			ResultadoCertificadoTasasBean resultado = servicioConsultaTasa.generarCertificadoTasas(getListaChecksConsultaTasa(), getListaChecksConsultaTasaPegatina(), utilesColegiado.getIdContratoSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensajeError());
			} else {
				resumenCertificadoTasas.setNumFallidos(resultado.getNumError());
				resumenCertificadoTasas.setNumTasasCertificado(resultado.getNumOk());
				resumenCertificadoTasas.setListaMensajesOk(resultado.getListaOk());
				resumenCertificadoTasas.setListaMensajesError(resultado.getListaError());
				resumenCertificadoTasasFlag = true;
				ficheroDescarga = resultado.getNombreFichero();
			}
			buscar();
			/*
			 * }else{ List<String> listaMensajesOk = new ArrayList<String>(); List<String> listaMensajesError = new ArrayList<String>(); if (getListaChecksConsultaTasa() != null && !getListaChecksConsultaTasa().isEmpty()) { codSeleccionados = getListaChecksConsultaTasa().replaceAll(" ",
			 * "").split(","); setNumsExpediente(getListaChecksConsultaTasa()); } else if (getNumsExpediente() != null && !getNumsExpediente().isEmpty()) { codSeleccionados = getNumsExpediente().replaceAll(" ", "").split(","); } else { codSeleccionados = new String[1]; codSeleccionados[0] =
			 * consultaTasaBean.getNumExpediente(); setNumsExpediente(consultaTasaBean.getNumExpediente()); } for (String codigoTasa: codSeleccionados) { if( codigoTasa.isEmpty()){ continue; } TramiteTraficoBean linea = getModeloTrafico().buscarTramitePorCodigoTasa(codigoTasa); if (linea == null) {
			 * // Probamos si es una tasa de cambio de servicio de transmisión if (generarCertificadoTasasCambioServicio(codigoTasa)) { continue; } // Probamos is es una tasa de consulta. if (generarCertificadoTasasTramiteSolicitud(codigoTasa)) { continue; } listaMensajesError.add(
			 * "No se ha encontrado el expediente de la tasa: " + codigoTasa); resumenCertificadoTasas.addFallido(); continue; } if (linea.getTipoTramite().getValorEnum().equals("T11")) { listaMensajesError.add( "No se pueden generar certificados para las tasas consumidas por anuntis. Tasa: " +
			 * linea.getTasa().getCodigoTasa()); resumenCertificadoTasas.addFallido(); continue; } // Recupera los detalles de la tasa: BeanPQDETALLE beanPQ = new BeanPQDETALLE(); if (linea.getTasa() == null || linea.getTasa().getCodigoTasa() == null) { listaMensajesError.add(
			 * "No se ha recuperado el código de tasa del expediente: " + codigoTasa); resumenCertificadoTasas.addFallido(); continue; } beanPQ.setP_CODIGO_TASA(linea.getTasa().getCodigoTasa()); beanPQ = getModeloTasas().detalleTasaNew(beanPQ); if (!"0".equals(beanPQ.getP_CODE().toString())){
			 * listaMensajesError.add( "No se han recuperado los detalles de la tasa del expediente: " + codigoTasa); resumenCertificadoTasas.addFallido(); log.error(beanPQ.getP_SQLERRM()); continue; } Tasa tasa = UtilesTrafico.convertir(beanPQ); // Tasa tasa = Tasa.convertir(beanPQ); if
			 * (!UtilesVistaTrafico.getInstance().esUsuarioColegio(tasa. getIdUsuario().longValue())) { listaMensajesError.add( "La tasa del trámite con nº de expediente " + linea.getNumExpediente() + " no ha sido importada desde el ICOGAM"); resumenCertificadoTasas.addFallido(); continue; }
			 * linea.setTasa(tasa); Map<String, Object> resultadoMetodo = null; Persona titularVehiculo = null; ResultBean resultado = null; Tasa tasaCambioServicio = null; // Recupera el detalle dependiendo del tipo: switch (Integer.parseInt(linea.getTipoTramite().getValorEnum().substring (1))) {
			 * // Matriculación case 1: { resultadoMetodo = getModeloMatriculacion().obtenerDetalle(linea.getNumExpediente(), utilesColegiado.getNumColegiado(), utilesColegiado.getIdContrato()); resultado = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN); if (resultado.getError()) {
			 * addActionError(resultado.getMensaje()); } // Verifica el estado según el tipo: if (linea.getEstado() != EstadoTramiteTrafico.Finalizado_PDF && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Excel_Impreso && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Telematicamente &&
			 * linea.getEstado() != EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso) { listaMensajesError.add("El estado de la tasa " + linea.getTasa().getCodigoTasa() + " no permite su inclusión en el certificado."); resumenCertificadoTasas.addFallido(); continue; } titularVehiculo =
			 * linea.getNombreTitular(); break; } // Transmisión case 2: case 7: { TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean(); resultadoMetodo = getModeloTransmision().obtenerDetalle(linea.getNumExpediente()); resultado = (ResultBean)
			 * resultadoMetodo.get(ConstantesPQ.RESULTBEAN); if (resultado.getError()) { addActionError(resultado.getMensaje()); } tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA); // Verifica el estado según el tipo: if
			 * (linea.getEstado() != EstadoTramiteTrafico.Finalizado_PDF && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Excel_Impreso && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Telematicamente && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso) {
			 * listaMensajesError.add("El estado de la tasa" + linea.getTasa().getCodigoTasa() + " no permite su inclusión en el certificado."); resumenCertificadoTasas.addFallido(); continue; } titularVehiculo = linea.getNombreTitular(); try { tasaCambioServicio =
			 * getTasaCambioServicioVehiculo(tramiteTraficoTransmisionBean); } catch (Exception e) { log.error( "Error tratando la tasa de cambio de servicio"); } break; } // Bajas case 3: { TramiteTrafBajaDto tramiteTrafBajaDto = servicioTramiteTraficoBaja.getTramiteBaja(linea.getNumExpediente( ),
			 * false); if (tramiteTrafBajaDto == null) { addActionError( "No existe el tramite de baja"); } // Verifica el estado según el tipo: if (linea.getEstado() != EstadoTramiteTrafico.Finalizado_PDF && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Excel && linea.getEstado() !=
			 * EstadoTramiteTrafico.Finalizado_Excel_Impreso) { listaMensajesError.add("El estado de la tasa " + linea.getTasa().getCodigoTasa() + " no permite su inclusión en el certificado."); resumenCertificadoTasas.addFallido(); continue; } titularVehiculo = linea.getNombreTitular(); break; } //
			 * Duplicados case 8: { resultadoMetodo = getModeloDuplicado().obtenerDetalle(linea.getNumExpediente(), utilesColegiado.getNumColegiado(), utilesColegiado.getIdContrato()); resultado = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN); if (resultado.getError()) {
			 * addActionError(resultado.getMensaje()); } // Verifica el estado según el tipo: if (linea.getEstado() != EstadoTramiteTrafico.Finalizado_PDF && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Excel && linea.getEstado() != EstadoTramiteTrafico.Finalizado_Excel_Impreso) {
			 * listaMensajesError.add("El estado de la tasa " + linea.getNumExpediente() + " no permite su inclusión en el certificado."); resumenCertificadoTasas.addFallido(); continue; } titularVehiculo = linea.getNombreTitular(); break; } } /* Los trámites de tipo Duplicado no tienen que tener la
			 * pestaña Facturación rellena
			 */
			/*
			 * Persona titularFacturacion = null; if (!(Integer.parseInt(linea.getTipoTramite().getValorEnum(). substring(1)) == 8)) { // Recupera el nif facturacion de la tasa: String nifFacturacion = getNifFacturacion_CODIGO_TASA(tasa.getCodigoTasa()); if (nifFacturacion == null ||
			 * nifFacturacion.equals("")) { listaMensajesError.add( "No se ha recuperado el nif del titular de facturación para el expediente " + linea.getNumExpediente()); resumenCertificadoTasas.addFallido(); continue; } String numColegiadoExpediente =
			 * linea.getNumExpediente().toString().substring(0, 4); titularFacturacion = ModeloPersona.obtenerDetallePersonaCompleto(nifFacturacion, numColegiadoExpediente); } // Establece las fechas de inicio y fin de las operaciones a registrar:
			 * ajustarFechasCertificado(linea.getTasa().getFechaAsignacion()); // Debe haber o matricula o bastidor. Establece uno de los dos, dando // preferencia a la matricula: String matricula = linea.getVehiculo().getMatricula(); if (matricula == null || (matricula != null &&
			 * matricula.equals(""))) { matricula = linea.getVehiculo().getBastidor(); } // if (linea.getNumExpediente() == null) { if (linea.getTasa().getFechaAsignacion() == null) { listaMensajesError.add( "No se ha encontrado expendiente o fecha de asignacion para la tasa " +
			 * linea.getTasa().getCodigoTasa()); resumenCertificadoTasas.addFallido(); continue; } } TablaFacturacionBean tablaFacturacionBean = null; if (titularFacturacion != null) { tablaFacturacionBean = new TablaFacturacionBean(titularVehiculo, linea.getTasa(), titularFacturacion, matricula); }
			 * else { titularFacturacion = new Persona(); titularFacturacion.setNombre( "Duplicado sin Titular de Facturación"); titularFacturacion.setNif(""); tablaFacturacionBean = new TablaFacturacionBean(titularVehiculo, linea.getTasa(), titularFacturacion, matricula); }
			 * tablaFacturacionBeanLista.add(tablaFacturacionBean); tasas++; listaMensajesOk.add(linea.getTasa().getCodigoTasa()); if (tasaCambioServicio != null) { tablaFacturacionBeanLista.add(new TablaFacturacionBean(titularVehiculo, tasaCambioServicio, titularFacturacion, matricula)); tasas++;
			 * listaMensajesOk.add(tasaCambioServicio.getCodigoTasa()); } } if (getListaChecksConsultaTasaPegatina()!= null && !getListaChecksConsultaTasaPegatina().isEmpty() ) { for (String codPegatina : getListaChecksConsultaTasaPegatina().split(",")) { TasaDto tasadto =
			 * servicioTasa.getTasaPegatinaCodigoTasa(codPegatina.trim()); if (tasadto != null && tasadto.getUsuario() != null && tasadto.getUsuario().getIdUsuario() != null) { if (!UtilesVistaTrafico.getInstance().esUsuarioColegio(tasadto. getUsuario().getIdUsuario().longValue())) {
			 * listaMensajesError.add("La tasa de pegatina con código " + codPegatina + " no ha sido importada desde el ICOGAM"); resumenCertificadoTasas.addFallido(); continue; } Tasa tasa = new Tasa(); tasa.setCodigoTasa(tasadto.getCodigoTasa());
			 * tasa.setFechaAlta(tasadto.getFechaAlta().getFecha()); tasa.setFechaFinVigencia(tasadto.getFechaFinVigencia().getFecha() ); tasa.setIdContrato(tasadto.getContrato().getIdContrato().intValue ()); tasa.setIdUsuario(tasadto.getUsuario().getIdUsuario().intValue()) ;
			 * tasa.setPrecio(tasadto.getPrecio()); tasa.setTipoTasa(tasadto.getTipoTasa()); String matricula = null; Persona titularFacturacion = new Persona(); // Se comprueba si existe factura de tasa TramiteTrafFacturacionDto factura = servicioFacturacionTasa.getFacturacionTasa(null,
			 * tasadto.getCodigoTasa()); if (factura == null) { // Se comprueba si la factura es obligatoria para ese tipo de tasa de pegatina if (TipoTasaDGT.UnoTres.getValorEnum().equals(tasadto.getTipoTasa()) ) { listaMensajesError.add("La tasa de pegatina con código " + codPegatina +
			 * " no está asociada a un tramite de facturación de tasas"); resumenCertificadoTasas.addFallido(); continue; } } else { tasa.setFechaAsignacion(factura.getFechaAplicacion() != null ? factura.getFechaAplicacion().getDate() : null); if (factura.getMatricula() != null &&
			 * !factura.getMatricula().isEmpty()) { matricula = factura.getMatricula(); } else { matricula = factura.getBastidor(); } titularFacturacion.setNif(factura.getNif()); } Date fechaTasa = tasa.getFechaAsignacion() != null ? tasa.getFechaAsignacion() : tasa.getFechaAlta(); // Establece las
			 * fechas de inicio y fin de las operaciones a registrar: if (inicio == null && fin == null) { // Fecha de la primera tasa: inicio = fechaTasa; fin = fechaTasa; } else { if (fechaTasa.getTime() < inicio.getTime()) { inicio = fechaTasa; } if (fechaTasa.getTime() > fin.getTime()) { fin =
			 * fechaTasa; } } // Si la tasa de pegatina no tiene facturacion asociada, no se dispone de matricula/bastidor ni titular de facturacion if (matricula == null) { matricula = ""; } TablaFacturacionBean tablaFacturacionBean = new TablaFacturacionBean(new Persona(), tasa,
			 * titularFacturacion, matricula); tablaFacturacionBeanLista.add(tablaFacturacionBean); tasas++; listaMensajesOk.add(tasa.getCodigoTasa()); } else { listaMensajesError.add( "Error recuperando la tasa de pegatina con codigo " + codPegatina); resumenCertificadoTasas.addFallido(); continue;
			 * } } } // Se han establecido los parámetros requeridos if (inicio != null && fin != null && tablaFacturacionBeanLista != null && tablaFacturacionBeanLista.size() >= 0) { SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); String cadFechaInicio = format.format(inicio); String
			 * cadFechaFin = format.format(fin); String nombreColegiado = utilesColegiado.getContrato().get_razon_social(); // Invocacion a la lógica del ireport ficheroDescarga = getUtilidadIreport().generarPdfCertificadoTasas(nombreColegiado, utilesColegiado.getNumColegiado(), cadFechaInicio,
			 * cadFechaFin, tablaFacturacionBeanLista); } resumenCertificadoTasasFlag = true; resumenCertificadoTasas.setListaMensajesError(listaMensajesError) ; resumenCertificadoTasas.setListaMensajesOk(listaMensajesOk); resumenCertificadoTasas.setNumFallidos(resumenCertificadoTasas.
			 * getNumFallidos()); resumenCertificadoTasas.setNumTasasCertificado(tasas); }
			 */
			return "descargarCertificadoDeTasas";
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
			/*
			 * } catch (OegamExcepcion ex) { log.error(ex); addActionError(ex.toString()); return Action.ERROR; } catch (Throwable th) { log.error("ERROR: ", th); addActionError(th.toString()); return Action.ERROR;
			 */
		}
	}

	private Tasa getTasaCambioServicioVehiculo(TramiteTraficoTransmisionBean tramite) throws Exception {
		String codigoTasa = tramite.getCodigoTasaCambioServicio();
		if (codigoTasa == null || codigoTasa.isEmpty() || "0".equals(codigoTasa)) {
			return null;
		}
		BeanPQDETALLE beanPQ = new BeanPQDETALLE();
		beanPQ.setP_CODIGO_TASA(codigoTasa);
		beanPQ = getModeloTasas().detalleTasaNew(beanPQ);
		if (!beanPQ.getP_CODE().toString().equals("0")) {
			addActionError("No se han recuperado los detalles de la tasa de cambio de servicio del expediente: " + tramite.getTramiteTraficoBean().getNumExpediente());
			resumenCertificadoTasas.addFallido();
			log.error(beanPQ.getP_SQLERRM());
			return null;
		}
		Tasa tasa = UtilesTrafico.convertir(beanPQ);
		// Tasa tasa = Tasa.convertir(beanPQ);

		// Verifica que la tasa ha sido importada por el colegio:
		if (!UtilesVistaTrafico.getInstance().esUsuarioColegio(tasa.getIdUsuario().longValue())) {
			addActionError("La tasa del cambio servicio del trámite con nº de expediente " + tramite.getTramiteTraficoBean().getNumExpediente() + " no ha sido importada desde el ICOGAM");
			resumenCertificadoTasas.addFallido();
			return null;
		}
		return tasa;
	}

	private void ajustarFechasCertificado(Date fecha) {
		if (inicio == null && fin == null) {
			// Fecha de la primera tasa:
			inicio = fecha;
			fin = fecha;
		} else {
			if (fecha.getTime() < inicio.getTime()) {
				inicio = fecha;
			}
			if (fecha.getTime() > fin.getTime()) {
				fin = fecha;
			}
		}
	}

	public String descargarCertificado() {
		try {
			String esNuevo = gestorPropiedades.valorPropertie("certificados.tasas.nuevo");
			File fichero = null;
			if (esNuevo != null && !esNuevo.isEmpty() && esNuevo.equals("SI")) {
				fichero = servicioConsultaTasa.getFicheroCertificadosTasa(ficheroDescarga);
				if (fichero == null) {
					addActionError("Ha sucedido un error a la hora de imprimir el certificado de tasas.");
				}
			} else {
				// Recupera el certificado:
				fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TASAS, ConstantesGestorFicheros.CERTIFICADO_TASAS, utilesFecha.getFechaActual(), ficheroDescarga,
						ConstantesGestorFicheros.EXTENSION_PDF).getFile();
				if (fichero == null) {
					addActionError("No se ha recuperado el pdf del certificado de tasas de la sesión");
					return "descargarCertificadoDeTasas";
				}
			}
			if (fichero != null) {
				inputStream = new FileInputStream(fichero);
				ficheroDescarga = fichero.getName();
			}
			return "descargarCertificadoTasas";
		} catch (OegamExcepcion e) {
			log.error(e);
			addActionError("No se ha recuperado el pdf del certificado de tasas");
			return "descargarCertificadoDeTasas";
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return ERROR;

		}
	}

	private String getNifFacturacion_NUM_EXPEDIENTE(String numeroExpediente) throws Exception {
		return servicioTramiteTrafico.getNifFacturacionPorNumExp(numeroExpediente);
	}

	public Integer getFormato() {
		return formato;
	}

	public void setFormato(Integer formato) {
		this.formato = formato;
	}

	public InputStream getFicheroTasas() {
		return ficheroTasas;
	}

	public void setFicheroTasas(InputStream ficheroTasas) {
		this.ficheroTasas = ficheroTasas;
	}

	public boolean isFicheroOK() {
		return ficheroOK;
	}

	public boolean getFicheroOK() {
		return ficheroOK;
	}

	public void setFicheroOK(boolean ficheroOK) {
		this.ficheroOK = ficheroOK;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public ResumenCertificadoTasas getResumenCertificadoTasas() {
		return resumenCertificadoTasas;
	}

	public void setResumenCertificadoTasas(ResumenCertificadoTasas resumenCertificadoTasas) {
		this.resumenCertificadoTasas = resumenCertificadoTasas;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public ArrayList<TablaFacturacionBean> getTablaFacturacionBeanLista() {
		return tablaFacturacionBeanLista;
	}

	public void setTablaFacturacionBeanLista(ArrayList<TablaFacturacionBean> tablaFacturacionBeanLista) {
		this.tablaFacturacionBeanLista = tablaFacturacionBeanLista;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getResumenCertificadoTasasFlag() {
		return resumenCertificadoTasasFlag;
	}

	public void setResumenCertificadoTasasFlag(Boolean resumenCertificadoTasasFlag) {
		this.resumenCertificadoTasasFlag = resumenCertificadoTasasFlag;
	}

	public String getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(String ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public String getListaChecksConsultaTasa() {
		return listaChecksConsultaTasa;
	}

	public void setListaChecksConsultaTasa(String listaChecksConsultaTasa) {
		this.listaChecksConsultaTasa = listaChecksConsultaTasa;
	}

	public String getListaChecksConsultaTasaPegatina() {
		return listaChecksConsultaTasaPegatina;
	}

	public void setListaChecksConsultaTasaPegatina(String listaChecksConsultaTasaPegatina) {
		this.listaChecksConsultaTasaPegatina = listaChecksConsultaTasaPegatina;
	}

	public DecisionTrafico getAsignada() {
		return asignada;
	}

	public void setAsignada(DecisionTrafico asignada) {
		this.asignada = asignada;
	}

	public String getIdCodigoTasaSeleccion() {
		return idCodigoTasaSeleccion;
	}

	public void setIdCodigoTasaSeleccion(String idCodigoTasaSeleccion) {
		this.idCodigoTasaSeleccion = idCodigoTasaSeleccion;
	}

	public String getIdCodigoTasaPegatinaSeleccion() {
		return idCodigoTasaPegatinaSeleccion;
	}

	public void setIdCodigoTasaPegatinaSeleccion(String idCodigoTasaPegatinaSeleccion) {
		this.idCodigoTasaPegatinaSeleccion = idCodigoTasaPegatinaSeleccion;
	}

	public TasaDto getDetalle() {
		return detalle;
	}

	public void setDetalle(TasaDto detalle) {
		this.detalle = detalle;
	}

	public ConsultaTasaBean getConsultaTasaBean() {
		return consultaTasaBean;
	}

	public void setConsultaTasaBean(ConsultaTasaBean consultaTasaBean) {
		this.consultaTasaBean = consultaTasaBean;
	}

	/*
	 * ***********************************************************************
	 */
	/*
	 * MODELOS ***************************************************************
	 */
	/*
	 * ***********************************************************************
	 */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloTasas getModeloTasas() {
		if (modeloTasas == null) {
			modeloTasas = new ModeloTasas();
		}
		return modeloTasas;
	}

	public void setModeloTasas(ModeloTasas modeloTasas) {
		this.modeloTasas = modeloTasas;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}

	public ModeloDuplicado getModeloDuplicado() {
		if (modeloDuplicado == null) {
			modeloDuplicado = new ModeloDuplicado();
		}
		return modeloDuplicado;
	}

	public void setModeloDuplicado(ModeloDuplicado modeloDuplicado) {
		this.modeloDuplicado = modeloDuplicado;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public UtilidadIreport getUtilidadIreport() {
		if (utilidadIreport == null) {
			utilidadIreport = new UtilidadIreport();
		}
		return utilidadIreport;
	}

	public void setUtilidadIreport(UtilidadIreport utilidadIreport) {
		this.utilidadIreport = utilidadIreport;
	}

	public ModelPagination getModelTasaPagination() {
		return modelTasaPagination;
	}

	public void setModelTasaPagination(ModelPagination modelTasaPagination) {
		this.modelTasaPagination = modelTasaPagination;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return RESULTADO_POR_DEFECTO;
	}
	
	public String cargarPopUpMotivo() {
		return POP_UP_MOTIVO;
	}
	
	public String cargarPopUpCambioAlmacen() {
		return POP_UP_ALMACEN;
	}

	@Override
	protected ModelPagination getModelo() {
		return modelTasaPagination;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTasaBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaTasaBean = new ConsultaTasaBean();
		consultaTasaBean.setFormato(FormatoTasa.ELECTRONICO.getCodigo());
		consultaTasaBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTasaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaTasaBean = (ConsultaTasaBean) object;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTasas";
	}

	@Override
	public boolean isBuscarInicio() {
		return false;
	}

	public ServicioTasa getServicioTasa() {
		return servicioTasa;
	}

	public void setServicioTasa(ServicioTasa servicioTasa) {
		this.servicioTasa = servicioTasa;
	}

	public ServicioGeneracionEtiquetasTasas getServicioGeneracionEtiquetasTasas() {
		return servicioGeneracionEtiquetasTasas;
	}

	public void setServicioGeneracionEtiquetasTasas(ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas) {
		this.servicioGeneracionEtiquetasTasas = servicioGeneracionEtiquetasTasas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getAlmacenNuevo() {
		return almacenNuevo;
	}

	public void setAlmacenNuevo(String almacenNuevo) {
		this.almacenNuevo = almacenNuevo;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}
}