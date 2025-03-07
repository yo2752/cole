package org.gestoresmadrid.oegam2.remesa.controller.action;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioConsultaRemesa;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.RemesaFilterBean;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.ResumenRemesas;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaRemesasAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 8301989464302168505L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaRemesasAction.class);
	
	private static final String ALTA_REMESA = "irAltaRemesa";
	private static final String POP_UP_CAMBIAR_ESTADO = "popUpCambioEstadoRemesa";
	private static final String POP_UP_EVOLUCION = "irEvolucionRemesa";
	private static final String[] fetchList = {"modelo","concepto","notario","contrato"};
	
	private ResumenRemesas resumen; 
	private Boolean resumenValidacion = false;
	private Boolean resumenEliminacion = false;
	private Boolean resumenCambEstado = false;
	private Boolean resumenGenerarModelo = false;
	private Boolean resumenPresentacion = false;
	private RemesaFilterBean remesaFilter;
	private String codSeleccionados;
	private BigDecimal numExpediente;
	private String estadoRemesaNuevo;
	private DatosBancariosFavoritosDto datosBancarios;
	
	@Autowired
	private ServicioConsultaRemesa servicioConsultaRemesa;
	
	@Resource
	private ModelPagination modeloRemesaPaginatedImpl;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String detalle(){
		if(numExpediente != null){
			ResultBean resultado = servicioConsultaRemesa.consultarEstadoRemesa(numExpediente);
			if(!resultado.getError()){
				return ALTA_REMESA;
			}else{
				aniadirMensajeError(resultado);
			}
		}else{
			addActionError("Debe seleccionar un numero de expediente.");
		}
		return inicio();
	}
	
	public String validar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultValidacion = servicioConsultaRemesa.validarRemesaPorBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultValidacion.getError()){
				addActionError(resultValidacion.getListaMensajes().get(0));
			}else{
				rellenarResumenRemesas(resultValidacion);
				resumenValidacion = true;
			}
		}else{
			addActionError("Debe seleccionar alguna remesa para validar.");
		}
		return buscar();
	}
	
	public String eliminar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultEliminar = servicioConsultaRemesa.anularRemesaPorBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultEliminar.getError()){
				addActionError(resultEliminar.getListaMensajes().get(0));
			}else{
				rellenarResumenRemesas(resultEliminar);
				resumenEliminacion = true;
			}
		}else{
			addActionError("Debe seleccionar alguna remesa para eliminar.");
		}
		return buscar();
	}
	
	
	public String cambiarEstado(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty() && estadoRemesaNuevo != null && !estadoRemesaNuevo.isEmpty()){
			ResultBean resultCambEstado = servicioConsultaRemesa.cambiarEstadoBloque(codSeleccionados,estadoRemesaNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultCambEstado != null && !resultCambEstado.getError()){
				rellenarResumenRemesas(resultCambEstado);
				resumenCambEstado = true;
			}else{
				addActionError(resultCambEstado.getListaMensajes().get(0));
			}
		}else{
			addActionError("Debe seleccionar un estado para poder realizar el cambio.");
		}
		return buscar();
	}
	
	public String generarModelosBloque(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultGenerar = servicioConsultaRemesa.generarModelosRemesasBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultGenerar != null && !resultGenerar.getError()){
				rellenarResumenRemesas(resultGenerar);
				resumenGenerarModelo = true;
			}else{
				addActionError(resultGenerar.getListaMensajes().get(0));
			}
		}else{
			addActionError("Debe seleccionar alguna Remesa para poder generarla.");
		}
		return buscar();
	}
	
	public String cargarPopUpCambioEstado(){
		return POP_UP_CAMBIAR_ESTADO;
	}
	
	public String abrirEvolucion(){
		if(numExpediente != null){
			return POP_UP_EVOLUCION;
		}else{
			addActionError("Ha sucedido un error a la hora de obtener la evolución del expediente.");
			return buscar();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void rellenarResumenRemesas(ResultBean resultValidacion) {
		List<String> listaErrores = (List<String>) resultValidacion.getAttachment("listaErrores");
		List<String> listaOk = (List<String>) resultValidacion.getAttachment("listaOk");
		int numOk600 = (Integer) resultValidacion.getAttachment("numOk600");
		int numOk601 = (Integer) resultValidacion.getAttachment("numOk601");
		int numErrores600 = (Integer) resultValidacion.getAttachment("numErrores600");
		int numErrores601 = (Integer) resultValidacion.getAttachment("numErrores601");
		if(resumen == null){
			resumen = new ResumenRemesas();
		}
		resumen.setListaMensajesErrores(listaErrores);
		resumen.setListaMensajesOk(listaOk);
		resumen.setNumFallidos600(numErrores600);
		resumen.setNumFallidos601(numErrores601);
		resumen.setNumOk600(numOk600);
		resumen.setNumOk601(numOk601);
		resumen.setTotalFallidos(numErrores600 + numErrores601);
		resumen.setTotalOk(numOk600 + numOk601);
	}
	
	public String presentarBloque(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultado = servicioConsultaRemesa.presentarEnBloque(codSeleccionados,datosBancarios,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(!resultado.getError()){
				rellenarResumenRemesas(resultado);
				resumenPresentacion = true;
			}else{
				aniadirMensajeError(resultado);
			}
		}else{
			addActionError("Debe seleccionar algún modelo para presentar.");
		}
		return buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloRemesaPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()){
			remesaFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(remesaFilter == null){
			remesaFilter = new RemesaFilterBean();
		}
		remesaFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if(!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()){
			remesaFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		remesaFilter.setVersion("A1");
	}

	@Override
	protected Object getBeanCriterios() {
		return remesaFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		remesaFilter = (RemesaFilterBean) object;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorRemesa";
	}

	public RemesaFilterBean getRemesaFilter() {
		return remesaFilter;
	}

	public void setRemesaFilter(RemesaFilterBean remesaFilter) {
		this.remesaFilter = remesaFilter;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenRemesas getResumen() {
		return resumen;
	}

	public void setResumen(ResumenRemesas resumen) {
		this.resumen = resumen;
	}

	public String getEstadoRemesaNuevo() {
		return estadoRemesaNuevo;
	}

	public void setEstadoRemesaNuevo(String estadoRemesaNuevo) {
		this.estadoRemesaNuevo = estadoRemesaNuevo;
	}

	public Boolean getResumenValidacion() {
		return resumenValidacion;
	}

	public void setResumenValidacion(Boolean resumenValidacion) {
		this.resumenValidacion = resumenValidacion;
	}

	public Boolean getResumenCambEstado() {
		return resumenCambEstado;
	}

	public void setResumenCambEstado(Boolean resumenCambEstado) {
		this.resumenCambEstado = resumenCambEstado;
	}

	public Boolean getResumenGenerarModelo() {
		return resumenGenerarModelo;
	}

	public void setResumenGenerarModelo(Boolean resumenGenerarModelo) {
		this.resumenGenerarModelo = resumenGenerarModelo;
	}

	public Boolean getResumenPresentacion() {
		return resumenPresentacion;
	}

	public void setResumenPresentacion(Boolean resumenPresentacion) {
		this.resumenPresentacion = resumenPresentacion;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public Boolean getResumenEliminacion() {
		return resumenEliminacion;
	}

	public void setResumenEliminacion(Boolean resumenEliminacion) {
		this.resumenEliminacion = resumenEliminacion;
	}

}
