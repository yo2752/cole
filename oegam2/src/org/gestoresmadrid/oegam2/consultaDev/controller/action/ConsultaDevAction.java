package org.gestoresmadrid.oegam2.consultaDev.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ConsultaDevFilterBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResumenConsultaDevBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioConsultaDev;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaDevAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -3959631011062049690L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaDevAction.class);
	
	private static final String ALTA_CONSULTA_DEV = "irAltaDev";
	private static final String POP_UP_EVOLUCION = "irEvolucionDev";
	private static final String POP_UP_ESTADOS = "popPupCambioEstado";
	private static final String DESCARGAR_EXCEL = "descargarExcel";
	
	
	private static final String[] fetchList = {"contrato","provincia"};
	
	private ConsultaDevFilterBean consultaDevFilter;
	private ResumenConsultaDevBean resumen;
	private Long idConsultaDev;
	private String codSeleccionados;
	private String estadoNuevo;
	
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	
	@Resource
	ModelPagination modeloConsultaDevPaginatedImpl;
	
	@Autowired
	ServicioConsultaDev servicioConsultaDev;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;

	public String cambiarEstado(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaDev resultado = servicioConsultaDev.cambiarEstado(codSeleccionados,estadoNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado,true, false);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta dev para cambiar su estado.");
		}
		return actualizarPaginatedList();
	}
	
	public String consultar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaDev resultado = servicioConsultaDev.consultar(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado,true, false);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta dev para cambiar su estado.");
		}
		return actualizarPaginatedList();
	}
	
	public String eliminar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaDev resultado = servicioConsultaDev.eliminar(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado,true, false);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta dev para cambiar su estado.");
		}
		return actualizarPaginatedList();
	}
	
	private void rellenarResumen(ResultadoConsultaDev resultado, boolean esCambioEstado, boolean esConsultado) {
		resumen = new ResumenConsultaDevBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOK());
		resumen.setResumenCambioEstado(esCambioEstado);
		resumen.setResumenConsultar(esConsultado);
	}

	public String detalle(){
		if(idConsultaDev != null){
			if(!servicioConsultaDev.getEstadoEstadoAnulado(idConsultaDev)){
				return ALTA_CONSULTA_DEV;
			}else{
				addActionError("La consulta Dev se encuentra en estado Anulada y no se puede obtener su detalle.");
			}
		}else{
			addActionError("Debe seleccionar alguna consulta para poder obtener su detalle.");
		}
		return actualizarPaginatedList();
	}
	
	public String abrirEvolucion(){
		if(idConsultaDev != null){
			return POP_UP_EVOLUCION;
		}else{
			addActionError("Ha sucedido un error a la hora de obtener la evolución de la consulta Dev.");
			return buscar();
		}
	}
	
	public String exportar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultBean resultado = servicioConsultaDev.exportar(codSeleccionados,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
			
					File f = (File) resultado.getAttachment("excel");
					try {
						inputStream = new FileInputStream(f);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
			
				setFileName("export.xls");
				return DESCARGAR_EXCEL;

			}
		}else{
			addActionError("Debe seleccionar alguna consulta dev para poder exportar.");
		}
		return actualizarPaginatedList();
	}
	
	public String cargarPopUpCambioEstado(){
		return POP_UP_ESTADOS;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaDevPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(consultaDevFilter.getCif() != null && !consultaDevFilter.getCif().isEmpty()){
			consultaDevFilter.setCif(consultaDevFilter.getCif().toUpperCase());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaDevFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaDevFilter == null){
			consultaDevFilter = new ConsultaDevFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaDevFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaDevFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaDevFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaDevFilter = (ConsultaDevFilterBean) object;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaDev";
	}

	public ConsultaDevFilterBean getConsultaDevFilter() {
		return consultaDevFilter;
	}

	public void setConsultaDevFilter(ConsultaDevFilterBean consultaDevFilter) {
		this.consultaDevFilter = consultaDevFilter;
	}

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ResumenConsultaDevBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaDevBean resumen) {
		this.resumen = resumen;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
