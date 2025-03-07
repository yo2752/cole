package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioExportacionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaTramiteRegistroBean;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ResumenConsultaRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class RecuperarTramiteAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -4316686498851087476L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private static final String POP_UP_SELECCION_ESTADO = "popUpSeleccionEstado";

	private static final String EXPORTAR_TRAMITE = "exportarTramite";

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioExportacionTramiteRegistro servicioExportacionTramiteRegistro;

	@Resource
	private ModelPagination modeloTramiteRegistroPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ConsultaTramiteRegistroBean consultaTramiteRegistroBean;

	private TramiteRegistroDto tramiteRegistro;
	private TramiteRegistroDto tramiteRegistroJustificante;

	private String tipoTramite;

	private String tipoContratoRegistro;

	private ResumenConsultaRegistradores resumen;
	private Boolean resumenValidacion = false;
	private Boolean resumenCambEstado = false;
	private Boolean resumenAdjuntos = false;
	private Boolean resumenEliminacion = false;
	private Boolean resumenFirmaCertificante = false;
	private Boolean resumenFirmaAcuse = false;
	private Boolean resumenEnvioDpr = false;

	private Integer cambioEstado;
	private String idRegistradores;
	private String listaChecksRegistradores;

	private String documentoDescarga;
	private InputStream inputStream;
	private String fileName = "exportarXML";

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaTramiteRegistroBean == null) {
			consultaTramiteRegistroBean = new ConsultaTramiteRegistroBean();
		}
		consultaTramiteRegistroBean.setIdTramiteOrigen(true);
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaTramiteRegistroBean.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloTramiteRegistroPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTramiteRegistroBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaTramiteRegistroBean = (ConsultaTramiteRegistroBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaTramiteRegistroBean == null) {
			consultaTramiteRegistroBean = new ConsultaTramiteRegistroBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaTramiteRegistroBean.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}

		consultaTramiteRegistroBean.setFechaCreacion(utilesFecha.getFechaFracionadaActual());

		setSort("fechaCreacion");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public String duplicar() {
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + " duplicar");

		if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite()) && !new BigDecimal(EstadoTramiteRegistro.Confirmada_Denegacion.getValorEnum()).equals(tramiteRegistro
				.getEstado())) {
			log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_ERROR + " El estado actual: " + EstadoTramiteRegistro.convertirTexto(tramiteRegistro.getEstado().toString())
					+ " no permite el duplicado del trámite de id: " + tramiteRegistro.getIdTramiteRegistro());
			return devolverRespuesta(tramiteRegistro.getTipoTramite());
		}

		BigDecimal idTramiteRegistro = tramiteRegistro.getIdTramiteRegistro();

		TramiteRegistroDto tramiteADuplicar = servicioTramiteRegistro.getTramite(idTramiteRegistro);
		ResultBean result = servicioTramiteRegistro.duplicar(tramiteADuplicar, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (result != null && !result.getError()) {
			BigDecimal idTramiteRegistroDuplicado = (BigDecimal) result.getAttachment(ServicioTramiteRegistro.ID_TRAMITE_REGISTRO);
			addActionMessage("Duplicado correctamente. El trámite duplicado tiene el número de expediente: " + idTramiteRegistroDuplicado);
			log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INFO + " Se ha duplicado el tramite con id: " + idTramiteRegistro + " .Id del duplicado: " + idTramiteRegistroDuplicado);
		} else {
			addActionError("Error duplicando el trámite: " + idTramiteRegistro);
			String error = "";
			if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes()) {
					error += error + ". " + mensaje;
				}
			}
			log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURA_ERROR + " Error duplicando el tramite con id: " + idTramiteRegistro + " .Error: " + error);
		}

		tramiteRegistro = servicioTramiteRegistro.getTramite(idTramiteRegistro);

		return devolverRespuesta(tramiteRegistro.getTipoTramite());
	}

	public String subsanar() {
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + " subsanar");

		BigDecimal idTramiteRegistro = tramiteRegistro.getIdTramiteRegistro();

		TramiteRegistroDto tramiteASubsanar = servicioTramiteRegistro.getTramite(idTramiteRegistro);
		ResultBean result = servicioTramiteRegistro.subsanar(tramiteASubsanar, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (result != null && !result.getError()) {
			BigDecimal idTramiteRegistroSubsanado = (BigDecimal) result.getAttachment(ServicioTramiteRegistro.ID_TRAMITE_REGISTRO);
			addActionMessage("Subsanado correctamente. El trámite subsanado tiene el número de expediente: " + idTramiteRegistroSubsanado);
			log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INFO + " Se ha subsanado el tramite con id: " + idTramiteRegistro + " .Id del subsanado: " + idTramiteRegistroSubsanado);
		} else {
			addActionError("Error subsanando el trámite: " + idTramiteRegistro);
			String error = "";
			if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes()) {
					error += error + ". " + mensaje;
				}
			}
			log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURA_ERROR + " Error subsanando el tramite con id: " + idTramiteRegistro + " .Error: " + error);
		}

		tramiteRegistro = servicioTramiteRegistro.getTramite(idTramiteRegistro);
		return devolverRespuesta(tramiteRegistro.getTipoTramite());
	}

	public String cambiarEstado() {
		if (utilesColegiado.tienePermisoAdmin()) {

			List<String> listaErrores = new ArrayList<String>();
			List<String> listaOk = new ArrayList<String>();
			int numValidas = 0;
			int numErrores = 0;

			String[] idsRegistradores = idRegistradores.split(",");
			for (String idRegistro : idsRegistradores) {
				TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro));

				if (tramiteRegistroDto.getEstado() != null && tramiteRegistroDto.getEstado().equals(new BigDecimal(cambioEstado))) {
					listaErrores.add("El estado nuevo del registro: " + idRegistro + " es el mismo al actual.");
					numErrores++;
				} else {
					ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, new BigDecimal(idRegistro), tramiteRegistroDto.getEstado(), new BigDecimal(cambioEstado), false, null,
							utilesColegiado.getIdUsuarioSessionBigDecimal());

					if (respuesta != null) {
						if (!respuesta.getError()) {
							listaOk.add("El estado del registro: " + idRegistro + " se ha cambiado correctamente a " + EstadoTramiteRegistro.convertirTexto(cambioEstado.toString()) + ".");
							numValidas++;
						} else {
							listaErrores.add("El estado del registro: " + idRegistro + " no se ha cambiado. " + respuesta.getMensaje() + ".");
							numErrores++;
						}
					}
				}
			}
			resumenCambEstado = true;
			if (resumen == null) {
				resumen = new ResumenConsultaRegistradores();
			}
			resumen.setListaMensajesErrores(listaErrores);
			resumen.setListaMensajesOk(listaOk);
			resumen.setNumFallidos(numErrores);
			resumen.setNumOk(numValidas);
			resumen.setTotalFallidos(numErrores);
			resumen.setTotalOk(numValidas);
		}
		return buscar();
	}

	public String cambiarAFinalizadoLista() {

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;

		String[] idsRegistradores = listaChecksRegistradores.split(",");

		// Comprobamos que se haya seleccionado algún trámite
		if (idsRegistradores.length == 0) {
			addActionError("Debe seleccionar algún trámite");
			return buscar();
		}

		for (String idRegistro : idsRegistradores) {
			TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro.trim()));
			if (tramiteRegistroDto.getEstado() == null) {
				listaErrores.add("No se ha podido recuperar el trámite: " + idRegistro.trim() + ".");
				numErrores++;
			} else if (null != tramiteRegistroDto.getTipoTramite() && !tramiteRegistroDto.getTipoTramite().equals(TipoTramiteRegistro.MODELO_6.getValorEnum())) {
				listaErrores.add("No es posible cambiar a estado Finalizado el trámite " + idRegistro.trim() + " porque no es de tipo Envío de Escrituras.");
				numErrores++;
			} else if (null != tramiteRegistroDto.getEstado() && tramiteRegistroDto.getEstado().toString().equals(EstadoTramiteRegistro.Finalizado.getValorEnum())) {
				listaErrores.add("El trámite " + idRegistro.trim() + " ya está en estado Finalizado.");
				numErrores++;
			} else if (null != tramiteRegistroDto.getEstado() && !tramiteRegistroDto.getEstado().toString().equals(EstadoTramiteRegistro.Inscrito.getValorEnum())) {
				listaErrores.add("Para cambiar el registro " + idRegistro.trim() + " a Finalizado su estado actual debe ser Inscrito.");
				numErrores++;
			} else {
				ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, new BigDecimal(idRegistro.trim()), tramiteRegistroDto.getEstado(), new BigDecimal(EstadoTramiteRegistro.Finalizado
						.getValorEnum()), false, null, utilesColegiado.getIdUsuarioSessionBigDecimal());

				if (respuesta != null) {
					if (!respuesta.getError()) {
						listaOk.add("El estado del registro: " + idRegistro.trim() + " se ha cambiado correctamente a Finalizado.");
						numValidas++;
					} else {
						listaErrores.add("El estado del registro: " + idRegistro.trim() + " no se ha cambiado a Finalizado. " + respuesta.getMensaje() + ".");
						numErrores++;
					}
				}
			}
		}
		resumenCambEstado = true;
		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String eliminarTramites() {

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;
		String[] idsRegistradores = listaChecksRegistradores.split(",");
		for (String idRegistro : idsRegistradores) {
			TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro.trim()));
			if (tramiteRegistroDto.getEstado() == null) {
				listaErrores.add("No se ha podido recuperar el trámite: " + idRegistro + ".");
				numErrores++;
			} else if (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Anulado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idRegistro + " ya estaba eliminado.");
				numErrores++;
			} else {
				ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, new BigDecimal(idRegistro.trim()), tramiteRegistroDto.getEstado(), new BigDecimal(EstadoTramiteRegistro.Anulado
						.getValorEnum()), false, null, utilesColegiado.getIdUsuarioSessionBigDecimal());

				if (respuesta != null && !respuesta.getError()) {
					listaOk.add("El Trámite: " + idRegistro + " se ha eliminado correctamente.");
					numValidas++;
				} else {
					listaErrores.add("El trámite: " + idRegistro + " no se ha eliminado. " + respuesta.getMensaje() + ".");
					numErrores++;
				}
			}
		}

		resumenEliminacion = true;
		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String validarTramites() throws Throwable {
		String[] idsRegistradores = listaChecksRegistradores.split(",");

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;

		for (String idRegistro : idsRegistradores) {
			TramiteRegRbmDto tramiteRegRbmDto;
			TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro.trim()));
			ResultRegistro respuesta = null;
			if (tramiteRegistroDto.getEstado() == null) {
				listaErrores.add("No se ha podido recuperar el trámite: " + idRegistro);
				numErrores++;
			} else if (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idRegistro + " ya estaba validado.");
				numErrores++;
			} else if (!tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idRegistro + " tiene que estar en estado iniciado.");
				numErrores++;
			} else {
				if (tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_10.getValorEnum()) || tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(
						TipoTramiteRegistro.MODELO_13.getValorEnum())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbmCancelDesist(idRegistro.trim()).getObj();
					respuesta = servicioTramiteRegRbm.validarContrato(tramiteRegRbmDto);
				} else if (tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_7.getValorEnum()) || tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(
						TipoTramiteRegistro.MODELO_8.getValorEnum()) || tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_9.getValorEnum())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbm(idRegistro.trim()).getObj();
					respuesta = servicioTramiteRegRbm.validarContrato(tramiteRegRbmDto);
				} else {
					respuesta = servicioTramiteRegistro.validarRegistro(tramiteRegistroDto);
				}

				ResultBean resultBean = null;
				if (respuesta != null && respuesta.isError()) {
					listaErrores.add("El trámite: " + idRegistro + " no se ha validado: ");
					if (respuesta.getValidaciones() != null && !respuesta.getValidaciones().isEmpty()) {
						for (String validacion : respuesta.getValidaciones()) {
							listaErrores.add(validacion);
						}
					}
					numErrores++;
				} else {
					numValidas++;
					resultBean = servicioTramiteRegistro.cambiarEstado(true, new BigDecimal(idRegistro.trim()), tramiteRegistroDto.getEstado(), new BigDecimal(EstadoTramiteRegistro.Validado
							.getValorEnum()), false, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultBean != null && resultBean.getError()) {
						listaErrores.add("El trámite: " + idRegistro + " ha lanzado el siguiente error: " + resultBean.getMensaje());
					} else {
						listaOk.add("El Trámite: " + idRegistro + " se ha validado correctamente.");
					}
				}
			}
		}
		resumenValidacion = true;

		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String construirDprLista() {
		if (!utilesColegiado.tienePermisoEnvioDprRegistradores()) {
			addActionError("No tiene permiso para enviar a DPR");
			return buscar();
		}

		ResultRegistro resultRegistro;

		String[] idsRegistradores = listaChecksRegistradores.split(",");

		// Comprobamos que se haya seleccionado algún trámite
		if (idsRegistradores.length == 0) {
			addActionError("Debe seleccionar algún trámite");
			return buscar();
		}

		// Comprobamos que los trámites seleccionados sean del mismo colegiado
		String aux = idsRegistradores[0].substring(0, 4);
		for (String idRegistro : idsRegistradores) {
			if (!idRegistro.trim().substring(0, 4).equalsIgnoreCase(aux)) {
				addActionError("Los trámites seleccionados tienen que ser del mismo colegiado");
				return buscar();
			}
		}

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;
		String alias = utilesColegiado.getAlias();

		for (String idRegistro : idsRegistradores) {
			resultRegistro = servicioTramiteRegRbm.construirDpr(idRegistro, alias);
			if (!resultRegistro.isError()) {
				listaOk.add(resultRegistro.getMensaje());
				numValidas++;
			} else {
				if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
					for (String validacion : resultRegistro.getValidaciones()) {
						listaErrores.add(validacion);
					}
				} else {
					listaErrores.add(resultRegistro.getMensaje());
				}
				numErrores++;
			}
		}

		resumenEnvioDpr = true;
		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String firmarLista() throws Throwable {
		ResultRegistro resultado;

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;
		resumenFirmaCertificante = true;

		if (!utilesColegiado.tienePermisoFirmaRegistradores()) {
			addActionError("No tiene permiso para firmar el trámite");
			return buscar();
		}

		String[] idsRegistradores = listaChecksRegistradores.split(",");

		// Comprobamos que se haya seleccionado algún trámite
		if (idsRegistradores.length == 0) {
			addActionError("Debe seleccionar algún trámite");
			return buscar();
		}

		for (String idRegistro : idsRegistradores) {

			try {
				tramiteRegistro = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro.trim()));
				if (tramiteRegistro.getEstado() == null) {
					listaErrores.add("No se ha podido recuperar el trámite: " + idRegistro);
					numErrores++;
				} else if (tramiteRegistro.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()))) {
					listaErrores.add("El trámite: " + idRegistro + " ya estaba firmado.");
					numErrores++;
				} else if (!tramiteRegistro.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()))) {
					listaErrores.add("El trámite: " + idRegistro + " tiene que estar en estado validado.");
					numErrores++;
				} else if (!tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_1.getValorEnum()) && !tramiteRegistro.getTipoTramite().equalsIgnoreCase(
						TipoTramiteRegistro.MODELO_2.getValorEnum()) && !tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_3.getValorEnum()) && !tramiteRegistro
								.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_4.getValorEnum()) && !tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_5
										.getValorEnum())) {
					listaErrores.add("El trámite: " + idRegistro + " no es de un tipo que se pueda firmar.");
					numErrores++;
				} else {

					resultado = servicioTramiteRegistro.construirRm(tramiteRegistro, true, utilesColegiado.getAlias());

					if (!resultado.isError()) {
						listaOk.add(resultado.getMensaje());
						numValidas++;
					} else {
						if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
							for (String validacion : resultado.getValidaciones()) {
								listaErrores.add(validacion);
							}
						} else {
							listaErrores.add(resultado.getMensaje());
						}
						numErrores++;
					}
				}

			} catch (Exception ex) {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO3_ERROR + "construirRm()" + ex + "\n" + UtilesExcepciones.stackTraceAcadena(ex, 3));
				listaErrores.add("Error en la construcción del documento rm (registro mercantil).");
				numErrores++;
			}
		}

		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String firmarAcuseLista() {
		ResultRegistro resultado;

		String[] idsRegistradores = listaChecksRegistradores.split(",");

		// Comprobamos que se haya seleccionado algún trámite
		if (idsRegistradores.length == 0) {
			addActionError("Debe seleccionar algún trámite");
			return buscar();
		}

		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;

		for (String idRegistro : idsRegistradores) {
			resultado = servicioTramiteRegistro.construirAcuse(idRegistro, utilesColegiado.getAlias(), null, null);
			if (!resultado.isError()) {
				listaOk.add(resultado.getMensaje());
				numValidas++;
			} else {
				if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
					for (String validacion : resultado.getValidaciones()) {
						listaErrores.add(validacion);
					}
				} else {
					listaErrores.add(resultado.getMensaje());
				}
				numErrores++;
			}
		}

		resumenFirmaAcuse = true;
		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);

		return buscar();
	}

	public String abrirSeleccionEstado() {
		return POP_UP_SELECCION_ESTADO;
	}

	public String descargarAdjuntos() throws OegamExcepcion, IOException {
		String[] idsRegistradores = listaChecksRegistradores.split(",");
		boolean descargar = false;
		String idRegistro = null;

		List<String> listaErrores = new ArrayList<String>();
		if (resumen == null) {
			resumen = new ResumenConsultaRegistradores();
		}

		if (idsRegistradores.length == 1) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream out = new ZipOutputStream(baos);
			idRegistro = idsRegistradores[0];
			String tipoDocumento = "";
			tramiteRegistro = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro));
			if (null != tramiteRegistro && TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tipoDocumento = ConstantesGestorFicheros.ESCRITURAS;
			} else {
				tipoDocumento = ConstantesGestorFicheros.REGISTRADORES;
			}

			FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(tipoDocumento, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(idRegistro
					.trim()), idRegistro.trim());

			if (null != ficheros.getFiles() && !ficheros.getFiles().isEmpty()) {
				for (File temporal : ficheros.getFiles()) {
					descargar = true;
					FileInputStream fis = new FileInputStream(temporal);
					ZipEntry entrada = new ZipEntry(temporal.getName());
					out.putNextEntry(entrada);
					byte[] buffer = new byte[1024];
					int leido = 0;
					while (0 < (leido = fis.read(buffer))) {
						out.write(buffer, 0, leido);
					}
					fis.close();
					out.closeEntry();
				}
			}

			out.close();
			if (descargar) {
				ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
				documentoDescarga = idRegistro + ConstantesPDF.EXTENSION_ZIP;
				inputStream = stream;
			}
		} else {
			listaErrores.add("Sólo puede seleccionar un trámite para descargar adjuntos.");
			resumen.setListaMensajesErrores(listaErrores);
			resumen.setNumFallidos(1);
			resumen.setTotalFallidos(1);
			resumen.setTotalOk(0);
			resumenAdjuntos = true;
			return buscar();
		}

		if (descargar) {
			return "documento";
		} else {
			listaErrores.add("No existe documentación para descargar.");
			resumen.setListaMensajesErrores(listaErrores);
			resumen.setNumFallidos(1);
			resumen.setTotalFallidos(1);
			resumen.setTotalOk(0);
			resumenAdjuntos = true;
			return buscar();
		}
	}

	/**
	 * Exportar los trámites en un fichero XML. Sólo aceptará trámites del mismo número de colegiado y de un mismo tipo de trámite. Por ahora solo se exportan trámites de envío escrituras.
	 * @return
	 * @throws OegamExcepcion
	 * @throws Throwable
	 */
	public String exportar() throws OegamExcepcion {
		try {
			String[] idsRegistradores = listaChecksRegistradores.split(",");

			// Comprobamos que se haya seleccionado algún trámite
			if (idsRegistradores.length == 0) {
				addActionError("Debe seleccionar algún trámite");
				return buscar();
			}

			// Comprobamos que los trámites seleccionados sean del mismo colegiado
			String colegiado = idsRegistradores[0].substring(0, 4);
			for (String idRegistro : idsRegistradores) {
				if (!idRegistro.trim().substring(0, 4).equalsIgnoreCase(colegiado)) {
					addActionError("Los trámites seleccionados tienen que ser del mismo colegiado");
					return buscar();
				}
			}

			// Comprobamos que los trámites seleccionados sean del mismo tipo
			String tipo = idsRegistradores[0].substring(10, 12);
			for (String idRegistro : idsRegistradores) {
				if (!idRegistro.trim().substring(10, 12).equalsIgnoreCase(tipo)) {
					addActionError("Los trámites seleccionados tienen que ser del mismo tipo");
					return buscar();
				}
			}

			byte[] bytesSalida = null;

			if (tipo.length() == 2 && !tipo.substring(0, 1).equals("1")) {
				tipo = tipo.substring(0, 1);
			}

			log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_INICIO + " Exportar.");

			// Separaremos dependiendo del tipo de trámite a exportar.
			switch (Integer.parseInt(tipo)) {
				// Escritura
				case 6: {
					fileName += "Escritura";
					ResultBean resultadoMatriculacion;
					resultadoMatriculacion = servicioExportacionTramiteRegistro.exportarEscritura(idsRegistradores);
					bytesSalida = (byte[]) resultadoMatriculacion.getAttachment(ServicioExportacionTramiteRegistro.BYTESXML);
				}
					break;

				// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
				default: {

					addActionError("Por ahora solo se pueden exportar trámites de envío escrituras");
					log.debug("Por ahora sólo se pueden exportar trámites de envío escrituras");
					return buscar();

				}
			}

			fileName = fileName + ConstantesGestorFicheros.EXTENSION_XML;
			inputStream = new ByteArrayInputStream(bytesSalida);

			return EXPORTAR_TRAMITE;

		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	private String devolverRespuesta(String tipoTramiteRegistro) {
		if (tipoTramiteRegistro != null && !tipoTramiteRegistro.isEmpty()) {
			String returnStruts = null;
			if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo1";
			} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo2";
			} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo3";
			} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo4";
			} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo5";
			} else if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroModelo6";
			} else if (TipoTramiteRegistro.MODELO_7.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "Financiacion";
			} else if (TipoTramiteRegistro.MODELO_8.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "Leasing";
			} else if (TipoTramiteRegistro.MODELO_9.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "Renting";
			} else if (TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "Cancelacion";
			} else if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroCuenta";
			} else if (TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "tramiteRegistroLibro";
			} else if (TipoTramiteRegistro.MODELO_13.getValorEnum().equals(tipoTramiteRegistro)) {
				returnStruts = "Desistimiento";
			}
			return returnStruts;
		} else {
			return buscar();
		}
	}

	public Integer getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(Integer cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getIdRegistradores() {
		return idRegistradores;
	}

	public void setIdRegistradores(String idRegistradores) {
		this.idRegistradores = idRegistradores;
	}

	public String getListaChecksRegistradores() {
		return listaChecksRegistradores;
	}

	public void setListaChecksRegistradores(String listaChecksRegistradores) {
		this.listaChecksRegistradores = listaChecksRegistradores;
	}

	public String getDocumentoDescarga() {
		return documentoDescarga;
	}

	public void setDocumentoDescarga(String documentoDescarga) {
		this.documentoDescarga = documentoDescarga;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ConsultaTramiteRegistroBean getConsultaTramiteRegistroBean() {
		return consultaTramiteRegistroBean;
	}

	public void setConsultaTramiteRegistroBean(ConsultaTramiteRegistroBean consultaTramiteRegistroBean) {
		this.consultaTramiteRegistroBean = consultaTramiteRegistroBean;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoContratoRegistro() {
		return tipoContratoRegistro;
	}

	public void setTipoContratoRegistro(String tipoContratoRegistro) {
		this.tipoContratoRegistro = tipoContratoRegistro;
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public TramiteRegistroDto getTramiteRegistroJustificante() {
		return tramiteRegistroJustificante;
	}

	public void setTramiteRegistroJustificante(TramiteRegistroDto tramiteRegistroJustificante) {
		this.tramiteRegistroJustificante = tramiteRegistroJustificante;
	}

	public Boolean getResumenValidacion() {
		return resumenValidacion;
	}

	public void setResumenValidacion(Boolean resumenValidacion) {
		this.resumenValidacion = resumenValidacion;
	}

	public ResumenConsultaRegistradores getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaRegistradores resumen) {
		this.resumen = resumen;
	}

	public Boolean getResumenCambEstado() {
		return resumenCambEstado;
	}

	public void setResumenCambEstado(Boolean resumenCambEstado) {
		this.resumenCambEstado = resumenCambEstado;
	}

	public Boolean getResumenAdjuntos() {
		return resumenAdjuntos;
	}

	public void setResumenAdjuntos(Boolean resumenAdjuntos) {
		this.resumenAdjuntos = resumenAdjuntos;
	}

	public Boolean getResumenEliminacion() {
		return resumenEliminacion;
	}

	public void setResumenEliminacion(Boolean resumenEliminacion) {
		this.resumenEliminacion = resumenEliminacion;
	}

	public Boolean getResumenFirmaCertificante() {
		return resumenFirmaCertificante;
	}

	public void setResumenFirmaCertificante(Boolean resumenFirmaCertificante) {
		this.resumenFirmaCertificante = resumenFirmaCertificante;
	}

	public Boolean getResumenFirmaAcuse() {
		return resumenFirmaAcuse;
	}

	public void setResumenFirmaAcuse(Boolean resumenFirmaAcuse) {
		this.resumenFirmaAcuse = resumenFirmaAcuse;
	}

	public Boolean getResumenEnvioDpr() {
		return resumenEnvioDpr;
	}

	public void setResumenEnvioDpr(Boolean resumenEnvioDpr) {
		this.resumenEnvioDpr = resumenEnvioDpr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}