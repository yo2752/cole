package trafico.transmision.acciones;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaJustificanteProfBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import escrituras.beans.ResultBean;
import hibernate.entities.general.Colegiado;
import oegam.constantes.ConstantesPQ;
import trafico.beans.ResumenTramitacionTelematica;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloTransmision;
import trafico.servicio.interfaz.ServicioJustificanteProInt;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

public class JustificanteProfesionalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 8631922075304230093L;

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(JustificanteProfesionalAction.class);

	private String [] numExpedientes;
	private InputStream inputStream;	// Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir
	private String codigoSeguroVerif;	// Código Seguro de Verificación de un justificante

	private static final String[] fetchList = { "tramiteTrafico", "tramiteTrafico.vehiculo" };
	private ConsultaJustificanteProfBean consultaJustificanteProfBean;

	@Resource
	private ModelPagination modeloJustificanteProfPaginated;

	private ModeloTransmision modeloTransmision;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private ModeloDuplicado modeloDuplicado;

	private Long[] idJustificanteInternos;
	private String codSeleccionados;

	@Autowired
	private ServicioJustificanteProInt servicioJustificanteProImpl;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	// MÉTODOS

	/**
	 * Método que se ejecuta al principio de la consulta, que inicializará los parámetros de búsqueda.
	 */
	@Override
	protected String getResultadoPorDefecto() {
		return "consultaJustificantesProfesionales";
	}

	@Override
	protected void cargaRestricciones() {
		if(consultaJustificanteProfBean == null){
			consultaJustificanteProfBean = new ConsultaJustificanteProfBean();
		}
		if (new BigDecimal(-1).equals(consultaJustificanteProfBean.getEstado())) {
			consultaJustificanteProfBean.setEstado(null);
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaJustificanteProfBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			consultaJustificanteProfBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		// Se descartan las tuplas con código de verificación, porque son las correspondientes a verificación
		consultaJustificanteProfBean.setCodigoVerificacion(true);
		if (consultaJustificanteProfBean.getFechaIni() != null) {
			consultaJustificanteProfBean.setFechaMaxIni(consultaJustificanteProfBean.getFechaIni().getFechaMaxFin());
		}
		if (consultaJustificanteProfBean.getFechaFin() != null) {
			consultaJustificanteProfBean.setFechaMinFin(consultaJustificanteProfBean.getFechaFin().getFechaMinInicio());
		}
	}

	/**
	 * Función del action para imprimir un trámite, para el que se pase el número de expediente.
	 * Redirigirá a la página de impresión seleccionando el trámite indicado.
	 * Mantis 0004830. SCL. Modificación general para gestionar los expedientes con varios justificantes asociados, respetando la forma antigua de almacenarlos
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String imprimir() throws Throwable {
		String codint;
		int pos;
		ArrayList<String> numExpedientes = new ArrayList<>();
		String[] codSeleccionados = utiles.quitarDuplicados(getNumExpedientes());

		for (int i=0; i<codSeleccionados.length; i++) { // TODO: aquí puede dar NullPointerException
			pos = codSeleccionados[i].indexOf("_");
			codint = codSeleccionados[i].substring(pos+1, codSeleccionados[i].length());
			numExpedientes.add(codint);
		}

		// INICIO MANTIS 0011927: impresión de justificantes profesionales
		if (numExpedientes.isEmpty()) {
			addActionError("Debe seleccionar algún justificante para imprimir.");
		}
		// Eliminamos los expedientes seleccionados que estén duplicados
		String[] expedientes = utiles.quitarDuplicados(numExpedientes.toArray(new String[numExpedientes.size()]));

		// VALIDACIONES DE ESTADO DE LOS JUSTIFICANTES DE LOS EXPEDIENTES
		List<String> listaNumExpedientesSinJustificantes = servicioJustificanteProImpl.obtenerExpedientesSinJustificantesEnEstado(expedientes, EstadoJustificante.Ok);
		if (!listaNumExpedientesSinJustificantes.isEmpty()) {
			addActionError("No se pueden imprimir los justificantes de los expedientes seleccionados. Los siguientes expedientes no tienen ningún justificante en estado 'recibido': " + utiles.transformListaToString(listaNumExpedientesSinJustificantes, "", "", ""));
			return buscar();
		}

		// IMPRESIÓN DE LOS JUSTIFICANTES DE LOS EXPEDIENTES SELECCIONADOS
		ByteArrayInputStreamBean byteArrayInputStreamBean = servicioJustificanteProImpl.imprimirJustificantes(expedientes);
		if (byteArrayInputStreamBean != null) {
			if (FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus())) {
				addActionError("No se han encontrado los ficheros para ninguno de los justificantes seleccionados.");
				return buscar();
			}
			setInputStream(byteArrayInputStreamBean.getByteArrayInputStream());
			setFileName(byteArrayInputStreamBean.getFileName());
		}

		// Si no encuentra justificante profesional para imprimir, vuelve a la
		// pantalla de consulta, donde muestra el mensaje de error
		if (getInputStream() == null) {
			return buscar();
		}
		return "mostrarDocumentoJustificante";
		// FIN MANTIS 0011927
	}

	/**
	 * Método que se ejecuta para cargar la jsp de la verificación de un justificante profesional
	 */
	
	public String inicioVerificar() throws Throwable {
		return "verificacionJustificantesProfesionales";
	}

	public String buscarVerificar() throws Throwable {
		if (getCodigoSeguroVerif() == null) {
			addActionError("Tiene que indicar un Código Seguro de Verificación de un justificante para buscar.");
			return "verificacionJustificantesProfesionales";
		}

		try {
			JustificanteProfVO justificanteProfesional = getModeloJustificanteProfesional().buscarPorCodigoVerificacion(getCodigoSeguroVerif());
			if (justificanteProfesional != null) {
				if (StringUtils.isNotBlank(justificanteProfesional.getVerificado()) && "SI".equals(justificanteProfesional.getVerificado()))
					addActionMessage("El justificante es válido.");
				else
					addActionMessage("El justificante no es válido.");
			} else {
				addActionError("No se ha encontrado un justificante asociado al código de seguridad introducido, si no ha solicitado su verificación pinche en el botón verificar para validarlo en SEA, de lo contrario espere a que se actualice en sistema.");
			}
		} catch (OegamExcepcion oe) {
			log.error("Error al intentar buscar un justificante profesional por código de verificacfión. Detalle: " + oe.getMessage(), oe);
			addActionError("Ocurrió un error al intentar buscar el justificante profesional asociado al código de verificación.");
			addActionError(oe.getMessage());
		}
		return "verificacionJustificantesProfesionales";
	}

	public String verificar() throws Throwable {
		if (getCodigoSeguroVerif() == null) {
			addActionError("Tiene que indicar un Código Seguro de Verificación de un justificante para validar.");
			return "verificacionJustificantesProfesionales";
		}

		try {
			JustificanteProfVO justificanteProfesional = getModeloJustificanteProfesional().buscarPorCodigoVerificacion(getCodigoSeguroVerif());
			if (justificanteProfesional != null) {
				if (StringUtils.isNotBlank(justificanteProfesional.getVerificado()) && "SI".equals(justificanteProfesional.getVerificado()))
					addActionMessage("El justificante ya ha sido verificado a través de Oegam y es válido.");
				else
					addActionMessage("El justificante ya ha sido verificado a través de Oegam y no es válido.");
			} else{
				getModeloJustificanteProfesional().crearSolicitudVerificar(getCodigoSeguroVerif(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				addActionMessage("Solicitud de verificación de justificante firmada y enviada, espere a recibir la notificación para buscarlo en el sistema.");
			}
		} catch (OegamExcepcion oe) {
			log.error("Error al intentar verificar un justificante profesional. Detalle: " + oe.getMessage(), oe);
			addActionError("Ocurrió un error al intentar verificar el justificante profesional.");
			addActionError(oe.getMessage());
		}
		return "verificacionJustificantesProfesionales";
	}

	public String generarJustificanteProfesionalForzado() {
		// No se utiliza
		List<TramiteTraficoVO> beans = new ArrayList<>();

		ArrayList<String> numExpedientes = new ArrayList<>();
		String codint;
		int pos;
		String[] codSeleccionados = utiles.quitarDuplicados(getNumExpedientes());

		for (int i=0;i<codSeleccionados.length;i++) {
			pos = codSeleccionados[i].indexOf("_") ;
			codint = codSeleccionados[i].substring(pos+1,codSeleccionados[i].length());
			numExpedientes.add(codint);
		}

		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");

		// Obtenemos los trámites, para ver si existen y de qué tipo son, más adelante obtendremos el detalle dependiendo del tipo
		for (int i=0; i<numExpedientes.size(); i++) {
			// Comprueba que los trámites tienen el JP en el nuevo estado "Pendiente autorizacion colegio - 5

			if (!servicioJustificanteProfesional.hayJustificante(null, new BigDecimal(numExpedientes.get(i)), EstadoJustificante.Pendiente_autorizacion_colegio)){
				addActionError("Error al forzar la generación del Justificante Profesional para el expediente: " + numExpedientes.get(i).toString() + ". " + "Para forzar la generación del Justificante profesional este debe estar en estado " +
						"Pendiente autorización del colegio");
				log.error("Error al forzar la generación del Justificante Profesional: " + numExpedientes.get(i) + ". " + "Para forzar la generación del Justificante profesional este debe estar en estado " +
						"Pendiente autorización del colegio");
				resumenTransmisionTelematica.addFallido();
				resumenTotal.addFallido();
				continue;
			} else {
				TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(new BigDecimal(numExpedientes.get(i)), Boolean.FALSE);
				beans.add(tramiteTrafico);
			}
		}

		// Hay que comprobar si al menos hay 1 trámite que se encuentre
		for (int i=0; i<beans.size(); i++) {
			//Hay que recoger la información del colegiado que generó el JP originalmente.
			BigDecimal idUsuario = new BigDecimal(0);
			BigDecimal idContrato = new BigDecimal(0);
			String numColegiado = "";
			String alias = "";
			String colegioDelContrato = "";

			if (beans.get(i).getNumColegiado() == null || beans.get(i).getNumColegiado().equals("")) {
				addActionError("No se ha podido obtener el número de colegiado para el expediente: "
						+ beans.get(i).getNumExpediente());
				log.error("No se ha podido obtener el número de colegiado para el expediente: "
						+ beans.get(i).getNumExpediente());
				resumenTransmisionTelematica.addFallido();
				resumenTotal.addFallido();
				continue;
			}
			Colegiado colegiado = utilesColegiado.getColegiado(beans.get(i).getNumColegiado());
			idUsuario = new BigDecimal(colegiado.getUsuario().getIdUsuario());
			alias = colegiado.getAlias();

			idContrato = new BigDecimal(beans.get(i).getContrato().getIdContrato());
			numColegiado = beans.get(i).getNumColegiado();

			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
			if (contratoDto != null && contratoDto.getColegioDto() != null && StringUtils.isNotBlank(contratoDto.getColegioDto().getColegio())) {
				colegioDelContrato = contratoDto.getColegioDto().getColegio();
			} else {
				addActionError("Error al obtener el colegio del contrato que intentó crear el Justificante Profesional.");
				log.error("Error al forzar la generación del Justificante Profesional para el expediente");
				resumenTransmisionTelematica.addFallido();
				resumenTotal.addFallido();
				continue;
			}

			// TRANSMISIÓN ELECTRÓNICA
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(beans.get(i).getTipoTramite())) {
				Map<String,Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
				// Recogemos los valores del modelo
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean)tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

				// Comprobamos que se realizó bien
				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmisionTelematica.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.TransmisionElectronica, alias, tramite.getTramiteTraficoBean(),
								tramite.getAdquirienteBean().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
								ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

						addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " +"Solicitud de Justificante firmada y enviada.");
						resumenTransmisionTelematica.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (OegamExcepcion e) {
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += " - " + e.getMensajeError1();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (Exception e) {
						log.error("Error al generar el xml de envio", e);
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (Throwable e) {
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += " - " + e.getMessage();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						log.error("Error inesperado al generar XML de envio", e);
					}
				}
			}
			// TRANSMISIÓN
			if (TipoTramiteTrafico.Transmision.getValorEnum().equals(beans.get(i).getTipoTramite())) {
				Map<String,Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
				//Recogemos los valores del modelo
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean)tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

				//Comprobamos que se realizó bien
				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmisionTelematica.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Transmision, alias, tramite.getTramiteTraficoBean(),
								tramite.getAdquirienteBean().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
								ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

						addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " +"Solicitud de Justificante firmada y enviada.");
						resumenTransmisionTelematica.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (OegamExcepcion e) {
						String error = "Trámite " + beans.get(i).getNumExpediente() + ": Error en la generación de Justificante. ";
						error += " - " + e.getMensajeError1();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (Exception e) {
						log.error("Error al guardar el XML de envio", e);
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generación de Justificante. ";
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (Throwable e) {
						String error = "Trámite " + beans.get(i).getNumExpediente() + ": Error en la generación de Justificante. ";
						error += " - " + e.getMessage();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						log.error("Error al generar XML", e);
					}
				}
			}
			// DUPLICADOS
			if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(beans.get(i).getTipoTramite())) {
				Map<String,Object> tramiteDetalle = getModeloDuplicado().obtenerDetalle(beans.get(i).getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
				// Recogemos los valores del modelo
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoDuplicadoBean tramite = (TramiteTraficoDuplicadoBean)tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

				// Comprobamos que se realizó bien
				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmision.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Duplicado, alias, tramite.getTramiteTrafico(),
								tramite.getTitular().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
								ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

						addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " +"Solicitud de Justificante firmada y enviada.");
						resumenTransmision.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (OegamExcepcion e) {
						log.error("Error generando XML de envio", e);
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += " - " + e.getMensajeError1();
						addActionError(error);
						resumenDuplicado.addFallido();
						resumenTotal.addFallido();
					} catch (Exception e) {
						log.error("Error al guardar el XML de envio", e);
						String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (Throwable e) {
						String error = "Trámite " + beans.get(i).getNumExpediente() + ": Error en la generación de Justificante. ";
						error += " - " + e.getMessage();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						log.error("Error no contralado en la generacion del XML de envio", e);
					}
				}
			} else if (!TipoTramiteTrafico.Baja.getValorEnum().equals(beans.get(i).getTipoTramite())
					&& !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(beans.get(i).getTipoTramite())
					&& !TipoTramiteTrafico.Transmision.getValorEnum().equals(beans.get(i).getTipoTramite())
					&& !TipoTramiteTrafico.Duplicado.getValorEnum().equals(beans.get(i).getTipoTramite())) {
				addActionError("Tramite de tipo " + beans.get(i).getTipoTramite() + " no tramitable telemáticamente actualmente");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
			}
		}
		return navegar();
	}

	public String pendienteAutorizacionColegio() {
		try {
			ArrayList<String> numExpedientes = new ArrayList<>();
			String codint;
			int pos;
			String[] codSeleccionados = utiles.quitarDuplicados(getNumExpedientes());

			for (int i=0; i<codSeleccionados.length; i++) {
				pos = codSeleccionados[i].indexOf("_") ;
				codint = codSeleccionados[i].substring(pos+1,codSeleccionados[i].length());
				numExpedientes.add(codint);
			}
			for (int i = 0; i < numExpedientes.size(); i ++) {
				boolean cambiado = servicioJustificanteProfesional.cambiarEstadoConEvolucionPorNumExpediente(new BigDecimal(numExpedientes.get(i)), EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (cambiado) {
					addActionMessage("El estado del justificante del expediente " + numExpedientes.get(i) + " ha pasado a 'Pendiente autorización colegio'");
				} else if (!cambiado) {
					addActionError("No se pudo cambiar el estado del justificante del expediente " + numExpedientes.get(i) + " a 'Pendiente autorización colegio' " +
							 " asegúrese de que está 'Iniciado' y que no está pendiente de envío a SEA (encolado)");
				}
			}
		} catch (Throwable ex) {
			addActionError(ex.getMessage());
			log.error("Ocurrio un error al cambiar el estado del justificante a pendiente autorizacion colegio", ex);
		}
		return navegar();
	}

	// Mantis 18257. David Sierra: Se modifica el estado del justificante de iniciado y Pte autorizacion Colegio a anulado
	public String anularJustificanteProfesional() {
		String[] codSeleccionados = utiles.quitarDuplicados(getNumExpedientes());
		ArrayList<Long> internos = new ArrayList<>();
		ArrayList<Long> expedientes = new ArrayList<>();
		String codint;
		String exp;
		int pos;
		for (int i=0; i<codSeleccionados.length; i++) {
			pos = codSeleccionados[i].indexOf("_") ;
			codint = codSeleccionados[i].substring(0, pos);
			exp = codSeleccionados[i].substring(pos+1,codSeleccionados[i].length());
			internos.add(Long.parseLong(codint));
			expedientes.add(Long.parseLong(exp));
		}

		for (int i=0; i<internos.size(); i++) {
			ResultBean resultado = servicioJustificanteProfesional.anularJP(internos.get(i), utilesColegiado.getIdUsuarioSessionBigDecimal(), Boolean.FALSE);

			if (!resultado.getError()) {
				addActionMessage("Justificante "+ internos.get(i) +"_" +codSeleccionados[i].substring(6,codSeleccionados[i].length()) +" anulado correctamente");
			} else {
				aniadirMensajeError(resultado);
			}
		}
		return navegar();
	}

	public String[] getNumExpedientes() {
		return numExpedientes;
	}

	public void setNumExpedientes(String[] numExpedientes) {
		this.numExpedientes = numExpedientes;
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

	public String getCodigoSeguroVerif() {
		return codigoSeguroVerif;
	}

	public void setCodigoSeguroVerif(String codigoSeguroVerif) {
		this.codigoSeguroVerif = codigoSeguroVerif;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloJustificanteProfPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaJustificanteProfBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaJustificanteProfBean = (ConsultaJustificanteProfBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaJustificanteProfBean == null) {
			consultaJustificanteProfBean = new ConsultaJustificanteProfBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaJustificanteProfBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			consultaJustificanteProfBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		consultaJustificanteProfBean.setFechaIni(utilesFecha.getFechaFracionadaActual());
		consultaJustificanteProfBean.setFechaFin(utilesFecha.getFechaFracionadaActual());

		setSort("idJustificanteInterno");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		consultaJustificanteProfBean = (ConsultaJustificanteProfBean) object;
		if (consultaJustificanteProfBean.getNumExpediente() == null
				&& StringUtils.isBlank(consultaJustificanteProfBean.getMatricula())
				&& consultaJustificanteProfBean.getIdJustificante() == null) {
			if (consultaJustificanteProfBean.getFechaIni() == null
					&& consultaJustificanteProfBean.getFechaFin() == null) {
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas
						+ " días para poder obtener los justificantes.");
			} else {
				if (consultaJustificanteProfBean.getFechaIni() != null
						&& !consultaJustificanteProfBean.getFechaIni().isfechaNula()
						&& consultaJustificanteProfBean.getFechaIni().getFechaInicio() != null
						&& consultaJustificanteProfBean.getFechaFin().getFechaInicio() != null
						&& StringUtils.isNotBlank(valorRangoFechas)) {
					esRangoValido = utilesFecha.comprobarRangoFechas(
							consultaJustificanteProfBean.getFechaIni().getFechaInicio(),
							consultaJustificanteProfBean.getFechaFin().getFechaInicio(),
							Integer.parseInt(valorRangoFechas));
				}
				if (!esRangoValido) {
					addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas
							+ " días para poder obtener los justificantes.");
				}
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public boolean isBuscarInicio() {
		return false;
	}

	public ConsultaJustificanteProfBean getConsultaJustificanteProfBean() {
		return consultaJustificanteProfBean;
	}

	public void setConsultaJustificanteProfBean(ConsultaJustificanteProfBean consultaJustificanteProfBean) {
		this.consultaJustificanteProfBean = consultaJustificanteProfBean;
	}

	/* ************************************************ */
	/* MODELOS **************************************** */
	/* ************************************************ */

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public Long[] getIdJustificanteInternos() {
		return idJustificanteInternos;
	}

	public void setIdJustificanteInternos(Long[] idJustificanteInternos) {
		this.idJustificanteInternos = idJustificanteInternos;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
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

}