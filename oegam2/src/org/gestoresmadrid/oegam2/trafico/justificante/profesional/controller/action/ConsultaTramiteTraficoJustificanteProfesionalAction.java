package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaJustificanteProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaTramiteTraficoJustificanteProfesionalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -9220722202152034879L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTramiteTraficoJustificanteProfesionalAction.class);

	private static final String MOSTRAR_DOCUMENTO = "mostrarDocumentoJustificante";

	@Resource
	private ModelPagination modeloJustificanteProfPaginated;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ConsultaJustificanteProfBean consultaJustificanteProfBean;

	private Long[] idJustificanteInternos;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	private static final String[] fetchList = { "tramiteTrafico", "tramiteTrafico.vehiculo" };

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaJustificanteProfBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			consultaJustificanteProfBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		if (new BigDecimal(-1).equals(consultaJustificanteProfBean.getEstado())) {
			consultaJustificanteProfBean.setEstado(null);
		}
		consultaJustificanteProfBean.setCodigoVerificacion(true);
	}

	public String pendienteAutorizacionColegio() {
		try {
			for (Long idJustificanteInterno : idJustificanteInternos) {
				JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(idJustificanteInterno);

				if (justificanteProfDto != null) {
					if (!justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Finalizado_Con_Error.getValorEnum())
							&& !justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Iniciado.getValorEnum())) {
						addActionError("No se pudo cambiar el estado del justificante del expediente " + justificanteProfDto.getNumExpediente() + " a 'Pendiente autorización colegio' "
								+ " asegúrese de que está 'Iniciado' o 'Finalizado con Error' y que no está pendiente de envío a SEA (encolado)");
					} else {
						ResultBean result = servicioJustificanteProfesional.guardarJustificanteProfesional(justificanteProfDto, justificanteProfDto.getNumExpediente(), utilesColegiado
								.getIdUsuarioSessionBigDecimal(), justificanteProfDto.getEstado(), new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum()), null);
						if (!result.getError()) {
							addActionMessage("El estado del justificante del expediente " + justificanteProfDto.getNumExpediente() + " ha pasado a 'Pendiente autorización colegio'");
						} else {
							addActionError("Error al cambiar el estado del justificante del expediente  " + justificanteProfDto.getNumExpediente());
						}
					}
				}
			}
		} catch (Exception e) {
			addActionError("Error al cambiar los estados de los justificantes");
			log.error("Error al cambiar los estados de los justificantes", e);
		}
		return inicio();
	}

	public String forzar() throws OegamExcepcion {
		try {
			for (Long idJustificanteInterno : idJustificanteInternos) {
				JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(idJustificanteInterno);

				if (justificanteProfDto != null) {
					if (!justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum())) {
						addActionError("El justificante del expediente " + justificanteProfDto.getNumExpediente() + " debe estar en Pendiente de Autorización del Colegio");
					} else {
						ResultBean result = servicioJustificanteProfesional.guardarJustificanteProfesional(
								justificanteProfDto, justificanteProfDto.getNumExpediente(),
								utilesColegiado.getIdUsuarioSessionBigDecimal(), justificanteProfDto.getEstado(),
								new BigDecimal(EstadoJustificante.Autorizado_icogam.getValorEnum()), null);
						if (!result.getError()) {
							result = servicioJustificanteProfesional.crearSolicitud(
									ProcesosEnum.EMISIONJPROF.toString(),
									justificanteProfDto.getIdJustificanteInterno(),
									utilesColegiado.getIdUsuarioSessionBigDecimal(),
									utilesColegiado.getIdContratoSessionBigDecimal(), null);
							if (!result.getError()) {
								addActionMessage("Solicitud de justificante enviada: " + justificanteProfDto.getNumExpediente());
							} else {
								addActionError(result.getMensaje() + ": " + justificanteProfDto.getNumExpediente());
							}
						} else {
							addActionError("Error al cambiar el estado del justificante del expediente  " + justificanteProfDto.getNumExpediente());
						}
					}
				}
			}
		} catch (Exception e) {
			addActionError("Error al forzar el envío de los justificantes");
			log.error("Error al forzar el envío de los justificantes", e);
		}
		return inicio();
	}

	public String imprimir() throws Throwable {
		List<String> listaIdSinJustificantes = servicioJustificanteProfesional
				.obtenerExpedientesSinJustificantesEnEstado(idJustificanteInternos, EstadoJustificante.Ok);
		if (listaIdSinJustificantes != null && !listaIdSinJustificantes.isEmpty()) {
			addActionError(
					"No se pueden imprimir los justificantes de los expedientes seleccionados. Los siguientes expedientes no tienen ningún justificante en estado 'recibido': "
							+ utiles.transformListaToString(listaIdSinJustificantes, "", "", ""));
			return inicio();
		}
		ByteArrayInputStreamBean byteArrayInputStreamBean = servicioJustificanteProfesional.imprimirJustificantes(idJustificanteInternos);
		if (byteArrayInputStreamBean != null) {
			if (FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus())) {
				addActionError("No se han encontrado los ficheros para ninguno de los justificantes seleccionados.");
				return buscar();
			}
			inputStream = byteArrayInputStreamBean.getByteArrayInputStream();
			fileName = byteArrayInputStreamBean.getFileName();
		}
		if (inputStream == null) {
			return inicio();
		}
		return MOSTRAR_DOCUMENTO;
	}

	public String recuperar() throws OegamExcepcion {
		try {
			for (Long idJustificanteInterno : idJustificanteInternos) {
				JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(idJustificanteInterno);

				if (justificanteProfDto != null) {
					if (justificanteProfDto.getIdJustificante() == null) {
						addActionError("El justificante del expediente " + justificanteProfDto.getNumExpediente() + " no se puede recuperar");
					} else {
						ResultBean result = servicioJustificanteProfesional.guardarJustificanteProfesional(
								justificanteProfDto, justificanteProfDto.getNumExpediente(),
								utilesColegiado.getIdUsuarioSessionBigDecimal(), justificanteProfDto.getEstado(),
								new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum()), null);
						if (!result.getError()) {
							result = servicioJustificanteProfesional.crearSolicitud(ProcesosEnum.EMISIONJPROF.toString(), justificanteProfDto.getIdJustificanteInterno(), utilesColegiado
									.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(),null);
							if (!result.getError()) {
								addActionMessage("Solicitud de justificante enviada: " + justificanteProfDto.getNumExpediente());
							} else {
								addActionError(result.getMensaje() + ": " + justificanteProfDto.getNumExpediente());
							}
						} else {
							addActionError("Error al cambiar el estado del justificante del expediente  " + justificanteProfDto.getNumExpediente());
						}
					}
				}
			}
		} catch (Exception e) {
			addActionError("Error al recuperar un justificante");
			log.error("Error al recuperar un justificante", e);
		}
		return inicio();
	}
	// Mantis 20494. David Sierra: Se agrega para Consulta Justificantes Profesionales Nuevo la anulación de justificantes en estado iniciado o Pendiente autorización colegio
	public String anular() throws OegamExcepcion {
		try {
			for (Long idJustificanteInterno : idJustificanteInternos) {
				JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(idJustificanteInterno);

				if (justificanteProfDto != null) {
					if (!justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Iniciado.getValorEnum())
							&& !justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum())) {
						addActionError("No se pudo cambiar el estado del justificante del expediente " + justificanteProfDto.getNumExpediente() + " a 'Pendiente autorización colegio' "
								+ " asegúrese de que está 'Iniciado' o 'Pendiente autorizacion colegio'");
					} else {
						ResultBean result = servicioJustificanteProfesional.cambiarEstadoAnularJustificante(justificanteProfDto, justificanteProfDto.getNumExpediente());
						if (!result.getError()) {
							if (!result.getError()) {
								addActionMessage("Justificante anulado: " + justificanteProfDto.getNumExpediente());
							} else {
								addActionError(result.getMensaje() + ": " + justificanteProfDto.getNumExpediente());
							}
						} else {
							addActionError("Error al cambiar el estado del justificante del expediente  " + justificanteProfDto.getNumExpediente());
						}
					}
				}
			}
		} catch (Exception e) {
			addActionError("Error al recuperar un justificante");
			log.error("Error al recuperar un justificante", e);
		}
		return inicio();
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
		consultaJustificanteProfBean.setCodigoVerificacion(true);
		setSort("idJustificanteInterno");
		setDir(GenericDaoImplHibernate.ordenDes);
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

	public Long[] getIdJustificanteInternos() {
		return idJustificanteInternos;
	}

	public void setIdJustificanteInternos(Long[] idJustificanteInternos) {
		this.idJustificanteInternos = idJustificanteInternos;
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
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

	public ModelPagination getModeloJustificanteProfPaginated() {
		return modeloJustificanteProfPaginated;
	}

	public void setModeloJustificanteProfPaginated(
			ModelPagination modeloJustificanteProfPaginated) {
		this.modeloJustificanteProfPaginated = modeloJustificanteProfPaginated;
	}

}