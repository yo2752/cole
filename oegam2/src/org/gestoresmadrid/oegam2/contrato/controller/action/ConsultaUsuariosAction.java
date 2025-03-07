package org.gestoresmadrid.oegam2.contrato.controller.action;

import javax.annotation.Resource;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoUsuarioFilterBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ResumenContratoBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaUsuariosAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -5049212516329682542L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaContratosAction.class);
	
	ContratoUsuarioFilterBean contratoUsuarioFilter;
	
	private static final String[] fetchList = {"colegiado","provincia"};
	
	//private static final String[] fetchList = {"colegiado","provincia","idContrato"};
	private Long idContrato;
	//private Long idUsuario;
	private String accion;
	
	private String codSeleccionados;
	private ResumenContratoBean resumen;
	
	@Resource
	ModelPagination modeloUsuarioPaginatedImpl;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloUsuarioPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}


	
	@Override
	protected void cargaRestricciones() {
		if(contratoUsuarioFilter.getCif() != null && !contratoUsuarioFilter.getCif().isEmpty()){
			contratoUsuarioFilter.setCif(contratoUsuarioFilter.getCif().toUpperCase());
		}
		if(contratoUsuarioFilter.getRazonSocial() != null && !contratoUsuarioFilter.getRazonSocial().isEmpty()){
			contratoUsuarioFilter.setRazonSocial(contratoUsuarioFilter.getRazonSocial().toUpperCase());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
	}
	
	
	@Override
	protected void cargarFiltroInicial() {
		if(contratoUsuarioFilter == null){
			contratoUsuarioFilter = new ContratoUsuarioFilterBean();
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			//contratoUsuarioFilter.setEstadoUsuarioContrato(new String(1));
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
	}

	
	
	@Override
	protected Object getBeanCriterios() {
		return contratoUsuarioFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		contratoUsuarioFilter = (ContratoUsuarioFilterBean) object;
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

	
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenContratoBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenContratoBean resumen) {
		this.resumen = resumen;
	}

	public ContratoUsuarioFilterBean getContratoUsuarioFilter() {
		return contratoUsuarioFilter;
	}

	public void setContratoUsuarioFilter(ContratoUsuarioFilterBean contratoUsuarioFilter) {
		this.contratoUsuarioFilter = contratoUsuarioFilter;
	}

	
	
	
}
