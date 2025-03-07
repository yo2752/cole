package org.gestoresmadrid.oegam2.evolucionUsuario.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.evolucionUsuario.view.bean.EvolucionUsuarioFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionUsuarioAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -4228408102204760841L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionUsuarioAction.class);

	private static final String[] fetchList = { "nif" };

	private EvolucionUsuarioFilterBean evolUsuarioFilterBean;
	private Long idContrato;
	private Long idUsuario;
	private String nif;
	private Long idContratoAnt;

	@Resource
	ModelPagination modeloEvolucionUsuarioPaginatedImpl;

	public String consultar() {
		cargarFiltroInicial();
		evolUsuarioFilterBean.setNif(nif);
		return buscar();
	}

	public String consultarAnt() {
		idContrato = idContratoAnt;
		cargarFiltroInicial();
		evolUsuarioFilterBean.setIdUsuario(idUsuario);

		return buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionUsuarioPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {

	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolUsuarioFilterBean == null) {
			evolUsuarioFilterBean = new EvolucionUsuarioFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("desc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolUsuarioFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolUsuarioFilterBean = (EvolucionUsuarioFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdContratoAnt() {
		return idContratoAnt;
	}

	public void setIdContratoAnt(Long idContratoAnt) {
		this.idContratoAnt = idContratoAnt;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ModelPagination getModeloEvolucionUsuarioPaginatedImpl() {
		return modeloEvolucionUsuarioPaginatedImpl;
	}

	public void setModeloEvolucionUsuarioPaginatedImpl(ModelPagination modeloEvolucionUsuarioPaginatedImpl) {
		this.modeloEvolucionUsuarioPaginatedImpl = modeloEvolucionUsuarioPaginatedImpl;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
