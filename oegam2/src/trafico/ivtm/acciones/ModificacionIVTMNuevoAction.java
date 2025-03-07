package trafico.ivtm.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ModificacionIVTMViewBean;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.ivtm.constantes.ConstantesIVTMWS;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import hibernate.entities.trafico.IntervinienteTrafico;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

public class ModificacionIVTMNuevoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1L;

	private static final String MODIFICA_DATOS_IVTM = "modificaDatosIVTM";
	private static final String MODIFICA_IVTM_DESCARGADOCUMENTO = "descargarDocAutoliquidacionIVTM";
	private static final String COLUMNA_POR_DEFECTO = "numExpediente";
	private static final String RESULTADO_POR_DEFECTO = "modificacionIVTM";

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en
										// PDF del action
	private String fileName; // Nombre del fichero a imprimir

	private BigDecimal numExpediente;

	private TramiteTraficoDto tramiteTraficoDto;

	private IntervinienteTrafico titularTramite;

	private IntervinienteTrafico representanteTramite;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Resource
	private ModelPagination modeloModificacionIVTMPaginatedImpl;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ModificacionIVTMViewBean ivtmViewBean;

	// jbc Modelo de Ivtm y modelo tramite.
	private IVTMModeloMatriculacionInterface ivtmModelo = new IVTMModeloMatriculacionImpl();
	private ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();

	// Log
	protected static final ILoggerOegam log = LoggerOegam.getLogger(ModificacionIVTMNuevoAction.class);

	public String detalle() throws Exception {
		try {
			if (!utilesColegiado.tienePermisoAdmin() || !utilesColegiado.tienePermisoIvtm()){
				addActionError("No tiene permisos para realizar esta operación");
				return ERROR;
			}
			tramiteTraficoDto = modeloTramite
					.recuperarDtoPorNumExp(numExpediente);
			titularTramite = null;
			representanteTramite = null;

			for (int i = 0; i < tramiteTraficoDto.getIntervinienteTraficos()
					.size() && titularTramite == null; i++) {
				if (null != tramiteTraficoDto.getIntervinienteTraficos().get(i)
						.getTipoIntervinienteBean()
						&& tramiteTraficoDto
								.getIntervinienteTraficos()
								.get(i)
								.getTipoIntervinienteBean()
								.getTipoInterviniente()
								.equals(TipoInterviniente.Titular
										.getValorEnum())) {
					titularTramite = tramiteTraficoDto
							.getIntervinienteTraficos().get(i);
				}
			}

			for (int i = 0; i < tramiteTraficoDto.getIntervinienteTraficos()
					.size() && representanteTramite == null; i++) {
				if (null != tramiteTraficoDto.getIntervinienteTraficos().get(i)
						.getTipoIntervinienteBean()
						&& tramiteTraficoDto
								.getIntervinienteTraficos()
								.get(i)
								.getTipoIntervinienteBean()
								.getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular
										.getValorEnum())) {
					representanteTramite = tramiteTraficoDto
							.getIntervinienteTraficos().get(i);
				}
			}

			if (tramiteTraficoDto == null) {
				addActionError("Se ha producido un error al recuperar los datos.");
				return getResultadoPorDefecto();
			}

		} catch (Exception e) {
			addActionError("Se ha producido un error al recuperar los datos.");
			getLog().error(e.getMessage());
			return getResultadoPorDefecto();
		}
		return MODIFICA_DATOS_IVTM;
	}

	// Poner Modificación en Proceso y Salvar los Datos
	public String modificar() throws Throwable {
		if (!utilesColegiado.tienePermisoAdmin() || !utilesColegiado.tienePermisoIvtm()){
			addActionError("No tiene permisos para realizar esta operación");
			return ERROR;
		}
		IvtmMatriculacionDto ivtmDto = tramiteTraficoDto.getTramiteTrafMatr()
				.getIvtmMatriculacionDto();
		tramiteTraficoDto = modeloTramite.recuperarDtoPorNumExp(numExpediente);
		// Hago esto por que se pierden datos en el jsp, y para no llenar de
		// hidden el jsp lo vuelvo a cargar ya que continuan los datos de la
		// BBDD
		ivtmDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
		ivtmDto.setNumExpediente(numExpediente);
		tramiteTraficoDto.getTramiteTrafMatr().setIvtmMatriculacionDto(ivtmDto);
		List<String> erroresValidacion = ivtmModelo.validarModificacion(
				tramiteTraficoDto, tramiteTraficoDto.getTramiteTrafMatr()
						.getIvtmMatriculacionDto());
		if (ivtmDto.getEstadoIvtm() == null	|| ivtmDto.getEstadoIvtm().equals(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum())|| ivtmDto.getEstadoIvtm() == new BigDecimal(EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto.getValorEnum())) {
			addActionError("Mientras el trámite se encuentre pendiente de respuesta, no se podrá modificar");
			// Recargo los datos iniciales del ivtm sin modificar.
			tramiteTraficoDto.getTramiteTrafMatr().setIvtmMatriculacionDto(
					ivtmModelo.recuperarPorNumExp(new BigDecimal(
							tramiteTraficoDto.getNumExpediente())));
			return MODIFICA_DATOS_IVTM;
		}

		// Guardamos la fecha de Petición del Trámite.
		if (erroresValidacion.isEmpty()) {
			ivtmDto.setFechaReq(new Fecha(new SimpleDateFormat("dd/MM/yyyy")
					.format(new Date())));
			if (tramiteTraficoDto.getNumColegiado() != null
					&& !tramiteTraficoDto.getNumColegiado().equals("")) {
				// Aquí se genera el nuevo IDPEticionIVTM
				ivtmDto.setIdPeticion(ivtmModelo
						.generarIdPeticion(tramiteTraficoDto.getNumColegiado()));
			} else {
				// Aquí se genera el nuevo IDPEticionIVTM
				ivtmDto.setIdPeticion(ivtmModelo
						.generarIdPeticion(utilesColegiado.getNumColegiadoSession()));
			}
			ivtmDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto.getValorEnum()));
		}
		// Antes se guardaba el trámite pero al solo hacer cambios en el IVTM
		// solo voy a guardar el IVTM para control de datos.

		if (ivtmModelo.actualizar(ivtmDto) != null) {
			addActionMessage("Trámite guardado.");
			tramiteTraficoDto = modeloTramite
					.recuperarDtoPorNumExp(numExpediente);
			if (!erroresValidacion.isEmpty()) {
				addActionError("Errores en la validacion de datos IVTM");
				for (String error : erroresValidacion) {
					addActionError(error);
				}
				addActionError("No se puede realizar la Autoliquidación del IVTM debido a los errores");
			} else {
				addActionMessage("Validacion Modificación Autoliquidación IVTM correcta");
				try {
					new ModeloSolicitud().crearSolicitud(new BigDecimal(
							tramiteTraficoDto.getNumExpediente()),
							utilesColegiado.getIdUsuarioSessionBigDecimal(),
							TipoTramiteTrafico.Matriculacion,
							ConstantesProcesos.PROCESO_IVTM,
							ConstantesIVTM.TIPO_MODIFICACION_IVTM_WS);
					addActionMessage("Solicitud de Modificación de Autoliquidación IVTM en Cola");
				} catch (CrearSolicitudExcepcion e) {
					UtilesExcepciones.logarOegamExcepcion( e, "No se pudo crear la solicitud de Modificación de IVTM", getLog());
					addActionError(e.getMensajeError1());
				} catch (Exception e) {
					addActionError("Ha ocurrido un error interno tramitando la solicitud");
				}
			}
		} else {
			addActionError("Trámite no guardado. No se puede modificar la Autoliquidación del IVTM");
		}
		return MODIFICA_DATOS_IVTM;
	}

	public String consultaPeticion() {
		try {
			detalle();
		} catch (Exception e) {
		}
		return generaDocumento(ConstantesIVTMWS.IVTMMATRICULACIONPETICION_GESTOR_FICHEROS);
	}

	public String consultaRespuesta() {
		try {
			detalle();
		} catch (Exception e) {
		}
		return generaDocumento(ConstantesIVTMWS.IVTMMATRICULACIONRESPUESTA_GESTOR_FICHEROS);
	}

	public String generaDocumento(String tipoPeticion) {
		if (!utilesColegiado.tienePermisoAdmin() || !utilesColegiado.tienePermisoIvtm()){
			addActionError("No tiene permisos para realizar esta operación");
			return ERROR;
		}
		String resultado = MODIFICA_DATOS_IVTM;
		try {
			// Debe de traer el XML, para que se usa
			IvtmMatriculacionDto ivtmDto = ivtmModelo
					.recuperarPorNumExp(numExpediente);
			BigDecimal numIdPet = ivtmDto.getIdPeticion();

			if (numIdPet != null) {
				List<File> ficheros = gestorDocumentos
						.buscarFicheroPorNumExpTipo(
								ConstantesIVTMWS.TIPO_IVTM_GESTOR_FICHEROS,
								tipoPeticion,
								Utilidades.transformExpedienteFecha(numIdPet),
								null).getFiles();

				if (ficheros != null && !ficheros.isEmpty()) {
					if (ficheros.get(0) != null) {
						FileInputStream fis = leerFichero(ficheros.get(0)
								.getAbsolutePath());
						setInputStream(fis);
						setFileName(ficheros.get(0).getName());
					}
				} else {
					addActionError("No existe el fichero o no se ha guardado.");
					return MODIFICA_DATOS_IVTM;
				}

				resultado = MODIFICA_IVTM_DESCARGADOCUMENTO;
			} else {
				addActionError("Error: No existe Petición");
				return MODIFICA_DATOS_IVTM;
			}

		} catch (OegamExcepcion e) {
			getLog().error("Error al generar el Documento" + e);
		}
		return resultado;
	}

	public FileInputStream leerFichero(String pathCompleto) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(pathCompleto));
		} catch (Exception e) {
			getLog().error("Error al intentar obtener el fichero: " + pathCompleto);
		}
		return fis;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public TramiteTraficoDto getTramiteTraficoDto() {
		return tramiteTraficoDto;
	}

	public void setTramiteTraficoDto(TramiteTraficoDto tramiteTraficoDto) {
		this.tramiteTraficoDto = tramiteTraficoDto;
	}

	public IntervinienteTrafico getTitularTramite() {
		return titularTramite;
	}

	public void setTitularTramite(IntervinienteTrafico titularTramite) {
		this.titularTramite = titularTramite;
	}

	public IntervinienteTrafico getRepresentanteTramite() {
		return representanteTramite;
	}

	public void setRepresentanteTramite(
			IntervinienteTrafico representanteTramite) {
		this.representanteTramite = representanteTramite;
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMNA_POR_DEFECTO;
	}

	@Override
	public String getResultadoPorDefecto() {
		return RESULTADO_POR_DEFECTO;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String inicio(){
		if (!utilesColegiado.tienePermisoAdmin() || !utilesColegiado.tienePermisoIvtm()) {
			addActionError("No tiene permisos para realizar esta operación");
			return ERROR;
		}
		return super.inicio();
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloModificacionIVTMPaginatedImpl;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (ivtmViewBean == null) {
			ivtmViewBean = new ModificacionIVTMViewBean();
		}

		ivtmViewBean.setFechaRequerida(utilesFecha.getFechaFracionadaActual());
	}

	public ModelPagination getModeloModificacionIVTMPaginatedImpl() {
		return modeloModificacionIVTMPaginatedImpl;
	}

	public void setModeloModificacionIVTMPaginatedImpl(ModelPagination modeloModificacionIVTMPaginatedImpl) {
		this.modeloModificacionIVTMPaginatedImpl = modeloModificacionIVTMPaginatedImpl;
	}

	public ModificacionIVTMViewBean getIvtmViewBean() {
		return ivtmViewBean;
	}

	public void setIvtmViewBean(ModificacionIVTMViewBean ivtmViewBean) {
		this.ivtmViewBean = ivtmViewBean;
	}

	@Override
	protected Object getBeanCriterios() {
		return ivtmViewBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.ivtmViewBean = (ModificacionIVTMViewBean) object;
	}

	@Override
	protected void cargaRestricciones() {
		if (ivtmViewBean == null) {
			ivtmViewBean = new ModificacionIVTMViewBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			ivtmViewBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

}