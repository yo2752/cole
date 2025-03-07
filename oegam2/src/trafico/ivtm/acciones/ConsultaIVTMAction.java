package trafico.ivtm.acciones;

import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

//TODO MPC. Cambio IVTM. Esta clase es la que está bien.
import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;
import general.acciones.factorias.FactoriaConsultaIVTM;
import hibernate.entities.trafico.IvtmConsultaMatriculacion;
import trafico.beans.ivtm.ConsultaIvtmBean;
import trafico.modelo.ivtm.IVTMConsModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMConsModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

public class ConsultaIVTMAction extends PaginatedListActionSkeleton implements SessionAware {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaIVTMAction.class);

	// Modelo
	private IVTMConsModeloMatriculacionInterface modeloIvtmConsulta = new IVTMConsModeloMatriculacionImpl();
	private IvtmConsultaMatriculacionDto ivtmConsultaDto = new IvtmConsultaMatriculacionDto();

	private static final String COLUMDEFECT = "fechaReq";
	private static final String CRITERIO_PAGINATED_LIST = "criterioPaginatedListConsultaIvtm";
	private static final String CRITERIO_SESSION = "consultaIvtmBean";

	protected static final String CONSULTA_IVTM_LLAMA_PROCESO = "trafico.ivtm.consultaIVTMLlamaProceso";
	protected static final String TIPOTRAMITECONSULTAIVTM = "T14";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String detalle() throws Throwable {
		recargarPaginatedList();
		boolean consultaIVTMLlamaProceso = Boolean
				.parseBoolean(gestorPropiedades.valorPropertie(ConstantesIVTM.CONSULTA_IVTM_LLAMA_PROCESO));
		try {
			// Se hace la comprobación de los permisos
			if (!utilesColegiado.tienePermisoIvtm()) {
				addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_CONSULTA);
				log.error("El colegiado " + utilesColegiado.getNumColegiadoSession()
						+ " ha intentado consultar la matricula en IVTM y no tiene permisos");
				return ConstantesIVTM.RESULTADO_CORRECTO_ACTION_CONSULTA_IVTM;
			}
			// Se inicializa el objeto ivtmConsulta
			ivtmConsultaDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			ivtmConsultaDto.setFechaReq(new Date());
			ivtmConsultaDto.setIdPeticion((modeloIvtmConsulta.generarIdPeticion(utilesColegiado.getNumColegiadoSession())));
			// No está haciendo nada ahora mismo.
			// Se validan los datos de entrada
			// List<String> erroresValidacion =
			// modeloIvtmConsulta.validarDatos(ivtmConsultaDto);
			// if (erroresValidacion !=null && erroresValidacion.size()>0) {
			// addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_VALIDACION_CONSULTA);
			// for (int i=0; i<erroresValidacion.size(); i++){
			// addActionError(gestorPropiedades.valorPropertie(erroresValidacion.get(i)));
			// }
			// return ConstantesIVTM.RESULTADO_CORRECTO_ACTION_CONSULTA_IVTM;
			// }
			// Se hace y se encola la solicitud
			consultaIVTMLlamaProceso = modeloIvtmConsulta.solicitarConsulta(ivtmConsultaDto);
			if (consultaIVTMLlamaProceso) {
				ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA);
				// Se guarda el trámite
				modeloIvtmConsulta.guardarDatosConsulta(ivtmConsultaDto);
				addActionMessage(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA);
			} else {
				ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
				// Se guarda el trámite
				modeloIvtmConsulta.guardarDatosConsulta(ivtmConsultaDto);
				addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
			}
		} catch (CrearSolicitudExcepcion e) {
			ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR);
			UtilesExcepciones.logarOegamExcepcion(e, "No se pudo crear la solicitud de consulta de IVTM", log);
			addActionError(e.getMensajeError1());
		} catch (Exception e) {
			ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR);
			addActionError(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR);
		} finally {
			if (!consultaIVTMLlamaProceso) {
				modeloIvtmConsulta.guardarDatosConsulta(ivtmConsultaDto);
			}
		}
		return "consultaIVTM";
	}

	public String popupMatricula() {
		return "popupMatricula";
	}

	/**
	 * Este método hace la petición al WebService del Ayuntamiento de Madrid.
	 * 
	 * @param consultaIVTM
	 * @throws OegamExcepcion
	 * @throws ServiceException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws JAXBException
	 */
	public void llamadaWS(IvtmConsultaMatriculacion ivtmConsultaMatriculacion)
			throws OegamExcepcion, ServiceException, ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException, JAXBException {
		// TODO
	}

	public IvtmConsultaMatriculacionDto getIvtmConsultaDto() {
		return ivtmConsultaDto;
	}

	public void setIvtmConsultaDto(IvtmConsultaMatriculacionDto ivtmConsultaDto) {
		this.ivtmConsultaDto = ivtmConsultaDto;
	}

	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaConsultaIVTM();
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	public String getResultadoPorDefecto() {
		return ConstantesIVTM.RESULTADO_CORRECTO_ACTION_CONSULTA_IVTM;
	}

	@Override
	public String getCriterioPaginatedList() {
		return CRITERIO_PAGINATED_LIST;
	}

	@Override
	public String getCriteriosSession() {
		return CRITERIO_SESSION;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			((ConsultaIvtmBean) beanCriterios).setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaConsultaIVTM";
	}

	public String getOrdenPorDefecto() {
		return ORDEN_DESC;
	}

}