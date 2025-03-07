package org.gestoresmadrid.oegam2.circulares.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ConsultaCircularFilterBean;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResumenCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaCircularAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2556091465728455694L;
	
	private static final String POPUP_CAMBIO_ESTADO = "popUP";

	private static final String POPUP_ACEPTAR = "popUpModalCircular";
	
	
	@Resource
	private ModelPagination modeloCircularPaginated;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ConsultaCircularAction.class);

	private ConsultaCircularFilterBean consultaCircularFilterBean;
	
	@Autowired
	ServicioCircular servicioCircular;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	private String codSeleccionados;
	private String estadoNuevo;
	
	private ResumenCircularBean resumen;
	private CircularDto circularDto;
	
	public String abrirPopUp(){
		
		return POPUP_CAMBIO_ESTADO;
	}
	
	public String cambiarEstados(){
		try{
			ResultadoCircularBean resultado = servicioCircular.cambiarEstados(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
				
			}else{
				resumen = resultado.getResumen();
			}
		}catch(Exception e){
			LOG.error("Ha sucedido un error a la hora de cambiar de estado, error: ",e);
			addActionError("Ha sucedido un error a la hora de cambiar de estado");
		}
		
		return actualizarPaginatedList();
	}

	public String revertir() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoCircularBean resultado = servicioCircular.revertir(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			LOG.error("No se ha podido revertir la circular seleccionada,error:", e);
			addActionError("No se ha podido revertir la circular seleccionada.");
		}
		return actualizarPaginatedList();
	}

	public String mostrarCircular() {
		try {
			ResultadoCircularBean resultado = servicioCircular.gestionarCircularesContrato(utilesColegiado.getIdContratoSession(), Boolean.FALSE);
			if(!resultado.getError()) {
				circularDto = resultado.getCircular();
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de mostrar las circulares, error: ",e);
			return "redirect";
		}
		
		return "popUpModalCircular";
	}
	
	
	private String[] prepararExpedientesSeleccionados() {
		String[] codSelecc = null;
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			codSelecc = codSeleccionados.split(";");
		}
		return codSelecc;
	}
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloCircularPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaCircularFilterBean == null) {
			consultaCircularFilterBean = new ConsultaCircularFilterBean();
		}

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaCircularFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaCircularFilterBean = (ConsultaCircularFilterBean) object;

	}

	public ConsultaCircularFilterBean getConsultaCircularFilterBean() {
		return consultaCircularFilterBean;
	}

	public void setConsultaCircularFilterBean(ConsultaCircularFilterBean consultaCircularFilterBean) {
		this.consultaCircularFilterBean = consultaCircularFilterBean;
	}

	public ModelPagination getModeloCircularPaginated() {
		return modeloCircularPaginated;
	}

	public void setModeloCircularPaginated(ModelPagination modeloCircularPaginated) {
		this.modeloCircularPaginated = modeloCircularPaginated;
	}

	public ServicioCircular getServicioCircular() {
		return servicioCircular;
	}

	public void setServicioCircular(ServicioCircular servicioCircular) {
		this.servicioCircular = servicioCircular;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public static String getPopupCambioEstado() {
		return POPUP_CAMBIO_ESTADO;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ResumenCircularBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenCircularBean resumen) {
		this.resumen = resumen;
	}

	public static String getPopupAceptar() {
		return POPUP_ACEPTAR;
	}

	public CircularDto getCircularDto() {
		return circularDto;
	}

	public void setCircularDto(CircularDto circularDto) {
		this.circularDto = circularDto;
	}
}
