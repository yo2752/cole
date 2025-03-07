package org.gestoresmadrid.oegam2.contrato.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioConsultaContrato;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoFilterBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoUsuarioFilterBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ResultadoConsultaContratoBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ResumenContratoBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaContratosAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -5049212516329682542L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaContratosAction.class);

	ContratoFilterBean contratoFilter;
	
	ContratoUsuarioFilterBean contratoUsuarioFilter;
	
	private static final String POP_UP_EVOLUCION = "irEvolucionContrato";
	private static final String POP_UP_MOTIVO = "popPupMotivo";
	private static final String POP_UP_ACTUALIZAR_ALIAS = "popPupActualizarAlias";
	
	
	private static final String[] fetchList = {"colegiado","provincia"};
	
	//private static final String[] fetchList = {"colegiado","provincia","idContrato"};
	private Long idContrato;
	//private Long idUsuario;
	private String accion;
	private String motivo;
	private String solicitante;
	private String codSeleccionados;
	private String alias;
	private FechaFraccionada fecha;
	private ResumenContratoBean resumen;
	
	@Resource
	ModelPagination modeloContratoPaginatedImpl;
	
	@Autowired
	ServicioConsultaContrato servicioConsultaContrato;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private Utiles utiles;
	
	@Override
	protected String getResultadoPorDefecto() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getNumColegiadoSession())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}
	
	public String habilitar(){
		if(comprobarDatosMinimosAccionesContrato()){
			ResultadoConsultaContratoBean resultado = servicioConsultaContrato.habilitarListaContratos(codSeleccionados,motivo,solicitante, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				resumen = new ResumenContratoBean();
				resumen.setResumenHabilitar(Boolean.TRUE);
				resumen.setNumError(resultado.getNumError());
				resumen.setNumOk(resultado.getNumOk());
				resumen.setListaErrores(resultado.getListaErrores());
				resumen.setListaOk(resultado.getListaOK());
			}
		}
		return actualizarPaginatedList();
	}
	
	public String deshabilitar(){
		if(comprobarDatosMinimosAccionesContrato()){
			ResultadoConsultaContratoBean resultado = servicioConsultaContrato.deshabilitarListaContratos(codSeleccionados,motivo,solicitante, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				resumen = new ResumenContratoBean();
				resumen.setResumenDeshabilitar(Boolean.TRUE);
				resumen.setNumError(resultado.getNumError());
				resumen.setNumOk(resultado.getNumOk());
				resumen.setListaErrores(resultado.getListaErrores());
				resumen.setListaOk(resultado.getListaOK());
			}
		}
		return actualizarPaginatedList();
	}
	
	public String eliminar(){
		if(comprobarDatosMinimosAccionesContrato()){
			ResultadoConsultaContratoBean resultado = servicioConsultaContrato.eliminarListaContratos(codSeleccionados,motivo,solicitante, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				resumen = new ResumenContratoBean();
				resumen.setResumenEliminar(Boolean.TRUE);
				resumen.setNumError(resultado.getNumError());
				resumen.setNumOk(resultado.getNumOk());
				resumen.setListaErrores(resultado.getListaErrores());
				resumen.setListaOk(resultado.getListaOK());
			}
		}
		return actualizarPaginatedList();
	}
	
	public String actualizarAlias(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaContratoBean resultado = servicioConsultaContrato.actualizarListaContratos(codSeleccionados,alias,fecha, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				resumen = new ResumenContratoBean();
				resumen.setResumenDeshabilitar(Boolean.TRUE);
				resumen.setNumError(resultado.getNumError());
				resumen.setNumOk(resultado.getNumOk());
				resumen.setListaErrores(resultado.getListaErrores());
				resumen.setListaOk(resultado.getListaOK());
			}
		}else{
			addActionError("Tiene que seleccionar un contrato para actualizar el alias");
		}
		return actualizarPaginatedList();
	}

	
	
	private boolean comprobarDatosMinimosAccionesContrato() {
		if(codSeleccionados == null || codSeleccionados.isEmpty()){
			addActionError("Debe seleccionar algún contrato.");
			return false;
		}else if(motivo == null || motivo.isEmpty()){
			addActionError("Debe rellenar el motivo.");
			return false;
		}else if(solicitante == null || solicitante.isEmpty()){
			addActionError("Debe seleccionar algún solicitante.");
			return false;
		}else if(!utilesColegiado.tienePermisoAdmin()){
			addActionError("El Usuario no tiene permisos suficientes para realizar dicha acción.");
			return false;
		}
		return true;
	}

	public String abrirEvolucion(){
		if(idContrato != null){
			return POP_UP_EVOLUCION;
		}
		addActionError("No ha seleccionado ningún contrato para obtener su evolucion.");
		return actualizarPaginatedList();
	}
	
	
	
	public String cargarPopUpMotivo(){
		return POP_UP_MOTIVO;
	}
	
	
	public String cargarPopUpActualizarAlias(){
		return POP_UP_ACTUALIZAR_ALIAS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloContratoPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(contratoFilter.getCif() != null && !contratoFilter.getCif().isEmpty()){
			contratoFilter.setCif(contratoFilter.getCif().toUpperCase());
		}
		if(contratoFilter.getRazonSocial() != null && !contratoFilter.getRazonSocial().isEmpty()){
			contratoFilter.setRazonSocial(contratoFilter.getRazonSocial().toUpperCase());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}
	
/*	@Override
	protected void cargaRestricciones() {
		if(contratoUsuarioFilter.getCif() != null && !contratoUsuarioFilter.getCif().isEmpty()){
			contratoUsuarioFilter.setCif(contratoUsuarioFilter.getCif().toUpperCase());
		}
		if(contratoUsuarioFilter.getRazonSocial() != null && !contratoUsuarioFilter.getRazonSocial().isEmpty()){
			contratoUsuarioFilter.setRazonSocial(contratoUsuarioFilter.getRazonSocial().toUpperCase());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiado());
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getContrato().getId_contrato().toString());
		}
	}
	
	
	@Override
	protected void cargarFiltroInicial() {
		if(contratoUsuarioFilter == null){
			contratoUsuarioFilter = new ContratoUsuarioFilterBean();
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getContrato().toString());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiado());
			//contratoUsuarioFilter.setEstadoUsuarioContrato(new String(1));
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getContrato().getId_contrato().toString());
		}
	}

	
	
	*/
	
	
	

	@Override
	protected void cargarFiltroInicial() {
		if(contratoFilter == null){
			contratoFilter = new ContratoFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			contratoFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return contratoFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		contratoFilter = (ContratoFilterBean) object;
	}
	
/*	
	@Override
	protected Object getBeanCriterios() {
		return contratoUsuarioFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		contratoUsuarioFilter = (ContratoUsuarioFilterBean) object;
	}
	
	*/
	
	
	
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

	public ContratoFilterBean getContratoFilter() {
		return contratoFilter;
	}

	public void setContratoFilter(ContratoFilterBean contratoFilter) {
		this.contratoFilter = contratoFilter;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}


	public static String getPopUpActualizarAlias() {
		return POP_UP_ACTUALIZAR_ALIAS;
	}

	public static String getPopUpEvolucion() {
		return POP_UP_EVOLUCION;
	}

	public static String getPopUpMotivo() {
		return POP_UP_MOTIVO;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

}
