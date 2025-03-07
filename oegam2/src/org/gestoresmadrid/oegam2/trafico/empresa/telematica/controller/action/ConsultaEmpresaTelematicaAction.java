package org.gestoresmadrid.oegam2.trafico.empresa.telematica.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.ConsultaEmpresaTelematicaViewBean;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.ResultadoEmpresaTelematicaBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEmpresaTelematicaAction extends AbstractPaginatedListAction {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ConsultaEmpresaTelematicaAction.class);

	private static final long serialVersionUID = -8188768526824633055L;

	private static final String POPUP_CAMBIO_ESTADO = "popUP";

	private static final String[] fetchList = { "contrato", "municipio", "provincia" };

	@Resource
	private ModelPagination modeloConsultaEmpresaTelematica;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ConsultaEmpresaTelematicaViewBean bean;

	@Autowired
	private ServicioEmpresaTelematica servicioEmpresaTel;

	private String codSeleccionados;
	private String valorEstadoCambio;

	public String abrirPopUp() {
		return POPUP_CAMBIO_ESTADO;
	}

	public String cambiarEstados() {
		ResultadoEmpresaTelematicaBean salida = null;
		try {
			salida = servicioEmpresaTel.cambiarEstados(getCodSeleccionados(), getValorEstadoCambio(),
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (salida.getError()) {
				for (String mensaje : salida.getListaMensajeError()) {
					addActionError(mensaje);
				}
			} else {
				for (String mensaje : salida.getListaMensajeOK()) {
					addActionMessage(mensaje);
				}
			}
		} catch (Exception e) {
			addActionError("Ha sucedido un error a la hora de cambiar de estado");
		}
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaEmpresaTelematica;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {
		if (bean == null) {
			bean = new ConsultaEmpresaTelematicaViewBean();
		}
		if (null != bean.getEstado() && bean.getEstado() == -1) {
			bean.setEstado(null);
		}
		if (null != bean.getIdContrato() && bean.getIdContrato() == -1) {
			bean.setIdContrato(null);
		}
		if (null != bean.getMunicipio() && bean.getMunicipio().equals("-1")) {
			bean.setMunicipio("");
		}
		if (null != bean.getProvincia() && bean.getProvincia().equals("-1")) {
			bean.setProvincia("");
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (bean == null) {
			bean = new ConsultaEmpresaTelematicaViewBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return bean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.bean = (ConsultaEmpresaTelematicaViewBean) object;
	}

	public ConsultaEmpresaTelematicaViewBean getBean() {
		return bean;
	}

	public void setBean(ConsultaEmpresaTelematicaViewBean bean) {
		this.bean = bean;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public String getValorEstadoCambio() {
		return valorEstadoCambio;
	}

	public void setValorEstadoCambio(String valorEstadoCambio) {
		this.valorEstadoCambio = valorEstadoCambio;
	}

	@Override
	public String[] getListInitializedOnePath() {
		return fetchList;
	}

	public static String[] getFetchlist() {
		return fetchList;
	}

}