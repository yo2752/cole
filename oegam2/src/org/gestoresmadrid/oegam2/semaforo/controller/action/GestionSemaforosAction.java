package org.gestoresmadrid.oegam2.semaforo.controller.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunPersistenciaSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunValidacionSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.SemaforoFilterBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import oegam.constantes.ConstantesSession;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionSemaforosAction extends AbstractPaginatedListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9222930805226132604L;
	
	private SemaforoDto semaforo;
	private List<SemaforoDto> listaSemaforos;
	private String semaforosChequeados;
	private String estadoSemaforoSel;
	private SemaforoFilterBean semaforoFilterBean;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionSemaforosAction.class);
	
	@Resource
	ModelPagination modeloSemaforoPaginated;
	
	@Autowired
	ServicioComunSemaforos servicioSemaforos;
	
	@Autowired
	ServicioComunPersistenciaSemaforos servicioPersistenciaSemaforos;
	
	@Autowired
	ServicioComunValidacionSemaforos servicioComunValidacionSemaforos;
	
	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	protected String getResultadoPorDefecto() {
		return "gestionSemaforo";
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSemaforoPaginated;
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
		if (semaforoFilterBean == null) {
			semaforoFilterBean = new SemaforoFilterBean();
		}
		semaforoFilterBean.setNodo(
				gestorPropiedades.valorPropertie("nombreHostProceso"));
	}
	
	public String navegar() {
		String resultadosPorPagina = ServletActionContext.getRequest().getParameter("resultadosPorPagina");
		super.setResultadosPorPagina(resultadosPorPagina);
		
		return super.navegar();
	}
	
	public String cambiarEstado() {
		return "popUpCambioEstado";
	}
	
	public String realizarCambioEstado() {
		String [] codigosSelec = semaforosChequeados.split("_");
		try {
			List<SemaforoDto> listaSemaforos = servicioSemaforos.obtenerSemaforosSelec(codigosSelec);
			ResultadoSemaforosBean resultado = servicioSemaforos.cambiarEstadoSemaforos(listaSemaforos, 
					Integer.parseInt(estadoSemaforoSel), 
					Long.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal().toString()));
			setActionErrors(resultado.getListaMensajesError());
			setActionMessages(resultado.getListaMensajesAviso());
			String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
			List<SemaforoDto> listaSemaforosSesion = servicioSemaforos.obtenerListaSemaforosSes(nodo);
			getSession().put(ConstantesSession.LISTA_SEMAFOROS_NODO, listaSemaforosSesion);
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		
		return super.buscar();
	}
	
	public String deshabilitar() {
		String [] codigosSelec = semaforosChequeados.split("_");
		try {
			List<SemaforoDto> listaSemaforos = servicioSemaforos.obtenerSemaforosSelec(codigosSelec);
			ResultadoSemaforosBean resultado = servicioSemaforos.deshabilitarSemaforos(listaSemaforos, 
					Long.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal().toString()));
			setActionErrors(resultado.getListaMensajesError());
			setActionMessages(resultado.getListaMensajesAviso());
			String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
			List<SemaforoDto> listaSemaforosSesion = servicioSemaforos.obtenerListaSemaforosSes(nodo);
			getSession().put(ConstantesSession.LISTA_SEMAFOROS_NODO, listaSemaforosSesion);
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		
		return super.buscar();
	}

	@Override
	protected Object getBeanCriterios() {
		return semaforoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		semaforoFilterBean = (SemaforoFilterBean)object;
	}

	public ModelPagination getModeloSemaforoPaginated() {
		return modeloSemaforoPaginated;
	}

	public void setModeloSemaforoPaginated(ModelPagination modeloSemaforoPaginated) {
		this.modeloSemaforoPaginated = modeloSemaforoPaginated;
	}

	public SemaforoDto getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(SemaforoDto semaforo) {
		this.semaforo = semaforo;
	}

	public List<SemaforoDto> getListaSemaforos() {
		return listaSemaforos;
	}

	public void setListaSemaforos(List<SemaforoDto> listaSemaforos) {
		this.listaSemaforos = listaSemaforos;
	}

	public String getSemaforosChequeados() {
		return semaforosChequeados;
	}

	public void setSemaforosChequeados(String semaforosChequeados) {
		this.semaforosChequeados = semaforosChequeados;
	}

	public String getEstadoSemaforoSel() {
		return estadoSemaforoSel;
	}

	public void setEstadoSemaforoSel(String estadoSemaforoSel) {
		this.estadoSemaforoSel = estadoSemaforoSel;
	}

	public SemaforoFilterBean getSemaforoFilterBean() {
		return semaforoFilterBean;
	}

	public void setSemaforoFilterBean(SemaforoFilterBean semaforoFilterBean) {
		this.semaforoFilterBean = semaforoFilterBean;
	}

}
