package org.gestoresmadrid.oegam2.licenciasCam.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ConsultaLicenciasCamBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResumenConsultaLicencias;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaLicenciasCamAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5912966301027066493L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("LicenciasCam");

	private static final String POP_UP_SELECCION_ESTADO = "popUpSeleccionEstado";

	private ConsultaLicenciasCamBean consultaLicenciasCamBean;

	@Resource
	private ModelPagination licenciasPaginatedImpl;

	@Autowired
	private ServicioLcTramite servicioLcTramite;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private LcTramiteDto lcTramiteDto;
	private String listaChecksLicencias;
	private Integer nuevoEstado;
	private ResumenConsultaLicencias resumen;

	private Boolean resumenCambEstado = false;
	private Boolean resumenEliminacion = false;
	private Boolean resumenValidacion = false;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaLicenciasCamBean == null) {
			consultaLicenciasCamBean = new ConsultaLicenciasCamBean();
		}

		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaLicenciasCamBean.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}
	}

	@Override
	public ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return licenciasPaginatedImpl;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaLicenciasCamBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaLicenciasCamBean = (ConsultaLicenciasCamBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaLicenciasCamBean == null) {
			consultaLicenciasCamBean = new ConsultaLicenciasCamBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaLicenciasCamBean.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}

		consultaLicenciasCamBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());

		setSort("fechaAlta");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public String abrirSeleccionEstado() {
		return POP_UP_SELECCION_ESTADO;
	}

	public String validar() {
		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;

		String[] listaLicencias = listaChecksLicencias.split(",");
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		for (String idLicencia : listaLicencias) {
			BigDecimal numExpediente = new BigDecimal(idLicencia.trim());

			resultado = servicioLcTramite.getTramiteLc(numExpediente);
			LcTramiteDto tramiteDto = (LcTramiteDto) resultado.getObj();

			if (tramiteDto.getEstado() == null) {
				listaErrores.add("No se ha podido recuperar el trámite: " + idLicencia);
				numErrores++;
			} else if (tramiteDto.getEstado().equals(new BigDecimal(EstadoLicenciasCam.Validado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idLicencia + " ya estaba validado.");
				numErrores++;
			} else if (!tramiteDto.getEstado().equals(new BigDecimal(EstadoLicenciasCam.Iniciado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idLicencia + " tiene que estar en estado iniciado.");
				numErrores++;
			} else {
				resultado = servicioLcTramite.validarTramite(tramiteDto);
				if (resultado != null && resultado.getError()) {
					listaErrores.add("El trámite: " + idLicencia + " no se ha validado: " + resultado.getMensaje());
					if (resultado != null && resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
						for (String validacion : resultado.getValidaciones()) {
							addActionError(validacion);
							numErrores++;
						}
					}
				} else {
					numValidas++;
					resultado = servicioLcTramite.cambiarEstado(true, new BigDecimal(idLicencia.trim()), tramiteDto.getEstado(), new BigDecimal(EstadoLicenciasCam.Validado.getValorEnum()),
							utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado != null && resultado.getError()) {
						listaErrores.add("El trámite: " + idLicencia + " ha lanzado el siguiente error: " + resultado.getMensaje());
					} else {
						listaOk.add("El Trámite: " + idLicencia + " se ha validado correctamente.");
					}
				}
			}
		}
		resumenValidacion = true;

		if (resumen == null) {
			resumen = new ResumenConsultaLicencias();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);
		return buscar();
	}

	public String cambiarEstado() {
		if (utilesColegiado.tienePermisoAdmin()) {
			List<String> listaErrores = new ArrayList<String>();
			List<String> listaOk = new ArrayList<String>();
			int numValidas = 0;
			int numErrores = 0;
			String[] listaLicencias = listaChecksLicencias.split(",");
			for (String idLicencia : listaLicencias) {
				ResultadoLicenciasBean resultado = servicioLcTramite.getTramiteLc(new BigDecimal(idLicencia.trim()));
				if (!resultado.getError()) {
					LcTramiteDto tramiteDto = (LcTramiteDto) resultado.getObj();
					if (tramiteDto.getEstado() != null && tramiteDto.getEstado().equals(new BigDecimal(nuevoEstado))) {
						listaErrores.add("El estado nuevo de la licencia: " + idLicencia + " es el mismo al actual.");
						numErrores++;
					} else {
						ResultadoLicenciasBean respuesta = servicioLcTramite.cambiarEstado(true, new BigDecimal(idLicencia), tramiteDto.getEstado(), new BigDecimal(nuevoEstado), utilesColegiado
								.getIdUsuarioSessionBigDecimal());
						if (!respuesta.getError()) {
							listaOk.add("El estado de la licencia: " + idLicencia + " se ha cambiado correctamente a " + EstadoLicenciasCam.convertirTexto(nuevoEstado.toString()) + ".");
							numValidas++;
						} else {
							listaErrores.add("El estado de la licencia: " + idLicencia + " no se ha cambiado. " + respuesta.getMensaje() + ".");
							numErrores++;
						}
					}
				}
			}
			resumenCambEstado = true;
			if (resumen == null) {
				resumen = new ResumenConsultaLicencias();
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

	public String eliminarLicencia() {
		List<String> listaErrores = new ArrayList<String>();
		List<String> listaOk = new ArrayList<String>();
		int numValidas = 0;
		int numErrores = 0;
		String[] listaLicencias = listaChecksLicencias.split(",");
		for (String idLicencia : listaLicencias) {
			ResultadoLicenciasBean resultado = servicioLcTramite.getTramiteLc(new BigDecimal(idLicencia.trim()));
			LcTramiteDto tramiteDto = (LcTramiteDto) resultado.getObj();
			if (tramiteDto.getEstado() == null) {
				listaErrores.add("No se ha podido recuperar el trámite: " + idLicencia + ".");
				numErrores++;
			} else if (tramiteDto.getEstado().equals(new BigDecimal(EstadoLicenciasCam.Anulado.getValorEnum()))) {
				listaErrores.add("El trámite: " + idLicencia + " ya estaba eliminado.");
				numErrores++;
			} else {
				ResultadoLicenciasBean respuesta = servicioLcTramite.cambiarEstado(true, new BigDecimal(idLicencia.trim()), tramiteDto.getEstado(), new BigDecimal(EstadoLicenciasCam.Anulado
						.getValorEnum()), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuesta != null && !respuesta.getError()) {
					listaOk.add("El Trámite: " + idLicencia + " se ha eliminado correctamente.");
					numValidas++;
				} else {
					listaErrores.add("El trámite: " + idLicencia + " no se ha eliminado. " + respuesta.getMensaje() + ".");
					numErrores++;
				}
			}
		}
		resumenEliminacion = true;
		if (resumen == null) {
			resumen = new ResumenConsultaLicencias();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos(numErrores);
		resumen.setNumOk(numValidas);
		resumen.setTotalFallidos(numErrores);
		resumen.setTotalOk(numValidas);
		return buscar();
	}

	public LcTramiteDto getLcTramiteDto() {
		return lcTramiteDto;
	}

	public void setLcTramiteDto(LcTramiteDto lcTramiteDto) {
		this.lcTramiteDto = lcTramiteDto;
	}

	public ConsultaLicenciasCamBean getConsultaLicenciasCamBean() {
		return consultaLicenciasCamBean;
	}

	public void setConsultaLicenciasCamBean(ConsultaLicenciasCamBean consultaLicenciasCamBean) {
		this.consultaLicenciasCamBean = consultaLicenciasCamBean;
	}

	public ModelPagination getLicenciasPaginatedImpl() {
		return licenciasPaginatedImpl;
	}

	public void setLicenciasPaginatedImpl(ModelPagination licenciasPaginatedImpl) {
		this.licenciasPaginatedImpl = licenciasPaginatedImpl;
	}

	public Integer getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(Integer nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public ResumenConsultaLicencias getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaLicencias resumen) {
		this.resumen = resumen;
	}

	public Boolean getResumenCambEstado() {
		return resumenCambEstado;
	}

	public void setResumenCambEstado(Boolean resumenCambEstado) {
		this.resumenCambEstado = resumenCambEstado;
	}

	public Boolean getResumenEliminacion() {
		return resumenEliminacion;
	}

	public void setResumenEliminacion(Boolean resumenEliminacion) {
		this.resumenEliminacion = resumenEliminacion;
	}

	public String getListaChecksLicencias() {
		return listaChecksLicencias;
	}

	public void setListaChecksLicencias(String listaChecksLicencias) {
		this.listaChecksLicencias = listaChecksLicencias;
	}

	public Boolean getResumenValidacion() {
		return resumenValidacion;
	}

	public void setResumenValidacion(Boolean resumenValidacion) {
		this.resumenValidacion = resumenValidacion;
	}
}
