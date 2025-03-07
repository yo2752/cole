package org.gestoresmadrid.oegam2.modelos.controller.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioConsultaModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.bean.Modelo600_601FilterBean;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.bean.ResumenModelo600601;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaModelosAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -2507857721782885154L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaModelosAction.class);

	private static final String ALTA_MODELOS = "irAltaModelos";
	private static final String POP_UP_CAMBIAR_ESTADO = "popUpCambioEstadoModelo";
	private static final String POP_UP_PRESENTAR = "popPupPresentarModelo";
	private static final String POP_UP_EVOLUCION = "irEvolucionModelo";

	private static final String[] fetchList = {"modelo","concepto","notario","remesa","contrato"};

	private Modelo600_601FilterBean modeloFilter;
	private BigDecimal numExpediente;
	private String codSeleccionados;
	private String estadoModeloNuevo;
	private Boolean resumenValidacion = false;
	private Boolean resumenCambEstado = false;
	private Boolean resumenAutoLiq = false;
	private Boolean resumenPresentacion = false;
	private Boolean resumenEliminacion = false;
	private ResumenModelo600601 resumen;
	private DatosBancariosFavoritosDto datosBancarios;
	private File fichero;

	@Autowired
	private ServicioConsultaModelo600_601 servicioConsultaModelo600_601;

	@Resource
	private ModelPagination modeloModelosPaginatedImpl;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String detalle(){
		if(numExpediente != null){
			ResultBean resultadoVal = servicioConsultaModelo600_601.consultarEstadoAnuladoModelo(numExpediente);
			if(!resultadoVal.getError()){
				return ALTA_MODELOS;
			}else{
				aniadirMensajeError(resultadoVal);
			}
		}else{
			addActionError("Debe seleccionar un numero de expediente.");
		}
		return inicio();
	}

	public String validar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty() ){
			ResultBean resultValidacion = servicioConsultaModelo600_601.validarModeloEnBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultValidacion.getError()){
				aniadirMensajeError(resultValidacion);
			}else{
				rellenarResumenModelos(resultValidacion);
				resumenValidacion = true;
			}
		}else{
			addActionError("Debe seleccionar algún modelo para validar.");
		}
		return buscar();
	}

	public String eliminar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty() ){
			ResultBean resultElim = servicioConsultaModelo600_601.anularModelosEnBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultElim.getError()){
				aniadirMensajeError(resultElim);
			}else{
				rellenarResumenModelos(resultElim);
				resumenEliminacion = true;
			}
		}else{
			addActionError("Debe seleccionar algún modelo para eliminar.");
		}
		return buscar();
	}

	public String cambiarEstado(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty() && estadoModeloNuevo != null && !estadoModeloNuevo.isEmpty()){
			ResultBean resultCambEstado = servicioConsultaModelo600_601.cambiarEstadoBloque(codSeleccionados,estadoModeloNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultCambEstado != null && !resultCambEstado.getError()){
				rellenarResumenModelos(resultCambEstado);
				resumenCambEstado = true;
			}else{
				addActionError(resultCambEstado.getListaMensajes().get(0));
			}
		}else{
			addActionError("Debe seleccionar un estado para poder realizar el cambio.");
		}
		return buscar();
	}

	public String autoliquidar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultAutoLiq = servicioConsultaModelo600_601.autoLiquidarModelosEnBloque(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(!resultAutoLiq.getError()){
				rellenarResumenModelos(resultAutoLiq);
				resumenAutoLiq = true;
			}else{
				addActionError(resultAutoLiq.getListaMensajes().get(0));
			}
		}else{
			addActionError("Debe seleccionar algún modelo para poder realizar la autoliquidación.");
		}
		return buscar();
	}

	public String presentarBloque(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultado = servicioConsultaModelo600_601.presentarEnBloque(codSeleccionados,datosBancarios,utilesColegiado.getIdUsuarioSessionBigDecimal(), fichero);
			if(!resultado.getError()){
				rellenarResumenModelos(resultado);
				resumenPresentacion = true;
			}else{
				aniadirMensajeError(resultado);
			}
		}else{
			addActionError("Debe seleccionar algún modelo para presentar.");
		}
		return buscar();
	}

	public String cargarPopUpPresentar(){
		return POP_UP_PRESENTAR;
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
	private void rellenarResumenModelos(ResultBean resultValidacion) {
		List<String> listaErrores = (List<String>) resultValidacion.getAttachment("listaErrores");
		List<String> listaOk = (List<String>) resultValidacion.getAttachment("listaOk");
		int numOk600 = (Integer) resultValidacion.getAttachment("numOk600");
		int numOk601 = (Integer) resultValidacion.getAttachment("numOk601");
		int numErrores600 = (Integer) resultValidacion.getAttachment("numErrores600");
		int numErrores601 = (Integer) resultValidacion.getAttachment("numErrores601");
		if(resumen == null){
			resumen = new ResumenModelo600601();
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

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloModelosPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()){
			modeloFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(modeloFilter == null){
			modeloFilter = new Modelo600_601FilterBean();
		}
		modeloFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if(!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()){
			modeloFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		modeloFilter.setVersion("A1");
	}

	@Override
	protected Object getBeanCriterios() {
		return modeloFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		modeloFilter = (Modelo600_601FilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorModelo600601";
	}

	public Modelo600_601FilterBean getModeloFilter() {
		return modeloFilter;
	}

	public void setModeloFilter(Modelo600_601FilterBean modeloFilter) {
		this.modeloFilter = modeloFilter;
	}

	public ModelPagination getModeloModelosPaginatedImpl() {
		return modeloModelosPaginatedImpl;
	}

	public void setModeloModelosPaginatedImpl(
			ModelPagination modeloModelosPaginatedImpl) {
		this.modeloModelosPaginatedImpl = modeloModelosPaginatedImpl;
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

	public String getEstadoModeloNuevo() {
		return estadoModeloNuevo;
	}

	public void setEstadoModeloNuevo(String estadoModeloNuevo) {
		this.estadoModeloNuevo = estadoModeloNuevo;
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

	public ResumenModelo600601 getResumen() {
		return resumen;
	}

	public void setResumen(ResumenModelo600601 resumen) {
		this.resumen = resumen;
	}

	public Boolean getResumenAutoLiq() {
		return resumenAutoLiq;
	}

	public void setResumenAutoLiq(Boolean resumenAutoLiq) {
		this.resumenAutoLiq = resumenAutoLiq;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public Boolean getResumenPresentacion() {
		return resumenPresentacion;
	}

	public void setResumenPresentacion(Boolean resumenPresentacion) {
		this.resumenPresentacion = resumenPresentacion;
	}

	public Boolean getResumenEliminacion() {
		return resumenEliminacion;
	}

	public void setResumenEliminacion(Boolean resumenEliminacion) {
		this.resumenEliminacion = resumenEliminacion;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

}