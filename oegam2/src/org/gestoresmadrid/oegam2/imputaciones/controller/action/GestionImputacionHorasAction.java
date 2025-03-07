package org.gestoresmadrid.oegam2.imputaciones.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.imputaciones.model.service.ServicioImputacionHorasInt;
import org.gestoresmadrid.oegam2comun.imputaciones.views.bean.ImputacionHorasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.Usuario;

public class GestionImputacionHorasAction extends AbstractPaginatedListAction implements ServletRequestAware, ServletResponseAware{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ServicioImputacionHorasInt servicioImputacionHorasImpl;
	
	@Resource
	private ModelPagination modeloImputacionHorasPaginated;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	private ImputacionHorasBean imputacionHorasBean;
	
	private UsuarioVO usuario;
	
	private Long idTipoImputacion;
	
	private List<String> listaPorImputacion;
	private List<Usuario> usuariosQueImputan;
	
	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; //para ajax
	private String resultadosPorPagina = "100";
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionImputacionHorasAction.class);
	
//	public String cargarHoras(){
//		
//		if(getEsAdmin())
//			usuariosQueImputan = utilesColegiado.getListaUsuariosColegio();
//	
//		return "imputar";
//	}
	
	public String consultar(){
		
		String resultado= "";
		if(!getEsAdmin() && imputacionHorasBean == null)
			resultado = "llevarACargar";
		else
			resultado = "imputar";
		
		if(imputacionHorasBean == null)
			return resultado;
		
		if(imputacionHorasBean.getFecha().getDiaInicio() == null 	  	  || imputacionHorasBean.getFecha().getDiaInicio().equals("") 
				|| imputacionHorasBean.getFecha().getMesInicio() == null  || imputacionHorasBean.getFecha().getMesInicio().equals("")
				|| imputacionHorasBean.getFecha().getAnioInicio() == null || imputacionHorasBean.getFecha().getAnioInicio().equals("")){
				addActionError("Es necesario proporcionar una fecha para realizar la consulta.");
				return resultado; 
		}
		
		return buscar();
	}
	
	public String guardar(){
		ResultBean resultBean = comprobarDatosImputacion();
		
		if (resultBean.getError()){
			for(String error:resultBean.getListaMensajes())
				addActionError(error);
			return "imputar"; 
		}
		
		resultBean = servicioImputacionHorasImpl.guardarImputacion(imputacionHorasBean);
		
		if(resultBean.getError())
			addActionError(resultBean.getMensaje());
		else
			addActionMessage(resultBean.getMensaje());
		
		return buscar();
	}
	
	private ResultBean comprobarDatosImputacion() {
		ResultBean resultBean = new ResultBean();
		
		if(imputacionHorasBean.getIdTipoImputacion() == null || imputacionHorasBean.getIdTipoImputacion().equals(new Long(-1))){
			resultBean.setError(true);
			resultBean.addMensajeALista("Es obligatorio seleccionar un tipo de imputación.");
		}
		
		if((imputacionHorasBean.getIdTipoImputacion() == null || imputacionHorasBean.getIdTipoImputacion().equals(new Long(4))) 
				&& (imputacionHorasBean.getDescImputacion() == null || imputacionHorasBean.equals("")) ){
			resultBean.setError(true);
			resultBean.addMensajeALista("Para esta imputacion, debe escribir una descripcion.");
		}
		
		if(imputacionHorasBean.getFecha().getDiaInicio() == null 	  || imputacionHorasBean.getFecha().getDiaInicio().equals("") 
			|| imputacionHorasBean.getFecha().getMesInicio() == null  || imputacionHorasBean.getFecha().getMesInicio().equals("")
			|| imputacionHorasBean.getFecha().getAnioInicio() == null || imputacionHorasBean.getFecha().getAnioInicio().equals("")){
			resultBean.setError(true);
			resultBean.addMensajeALista("Es obligatorio rellenar la fecha para la imputación.");
		}else{
			Date fechaAimputar = imputacionHorasBean.getFecha().getFechaInicio();
			if(fechaAimputar.after(new Date())){
				resultBean.setError(true);
				resultBean.addMensajeALista("No se puede imputar para una fecha futura");
			}
		}
		
		if(imputacionHorasBean.getHoras() == null){
			resultBean.setError(true);
			resultBean.addMensajeALista("Es obligatorio rellenar las horas para la imputación.");
		}
				
		return resultBean;
	}

	public void obtenerDescripcion() throws IOException{		
		String descripcion = servicioImputacionHorasImpl.getDescripcionTipoImputacion(idTipoImputacion); 
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(descripcion);
	}
	
	public String borrar(){
		ResultBean result = new ResultBean();
		if(listaPorImputacion == null || listaPorImputacion.size() == 0){
			addActionError("Debe seleccionar alguna imputacion para borrarla.");
			return buscar();
		}	
		
		result = servicioImputacionHorasImpl.borrarImputaciones(listaPorImputacion);

		if(!result.getError()){
			addActionMessage("Imputaciones eliminadas correctamente.");
		}
		
		return buscar();
	}
	
	public String navegarGestion(){
		return "imputar";
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return "imputar";
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImputacionHorasPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(utilesColegiado.tienePermisoColegio()){
//			imputacionHorasBean.setDescImputacion(null);
//			imputacionHorasBean.setHoras(null);
//			imputacionHorasBean.setIdImputacion(null);
//			imputacionHorasBean.setIdTipoImputacion(null);
			imputacionHorasBean.setFechaConsulta(imputacionHorasBean.getFecha().getFechaInicio());
//			imputacionHorasBean.setFecha(null);
			if(imputacionHorasBean.getIdUsuario() == null || imputacionHorasBean.getIdUsuario().equals(0L))
				imputacionHorasBean.setIdUsuario(utilesColegiado.getIdUsuarioSession());
		}
		
	}

	@Override
	protected void cargarFiltroInicial() {
		imputacionHorasBean.setDescImputacion(null);
		imputacionHorasBean.setHoras(null);
		imputacionHorasBean.setIdImputacion(null);
		imputacionHorasBean.setIdTipoImputacion(null);
		imputacionHorasBean.setFechaConsulta(imputacionHorasBean.getFecha().getFechaInicio());
		imputacionHorasBean.setFecha(null);
		if(imputacionHorasBean.getIdUsuario() == null || imputacionHorasBean.getIdUsuario().equals(0L))
			imputacionHorasBean.setIdUsuario(utilesColegiado.getIdUsuarioSession());
	}
	
	@Override
	protected Object getBeanCriterios() {
		return imputacionHorasBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.imputacionHorasBean = (ImputacionHorasBean) object;
		
	}
	
	@Override
	public String getDecorator(){
		return  "org.gestoresmadrid.oegam2.view.decorator.DecoratorImputacionHoras";
	}

	public UsuarioVO getUsuario() {
		if (this.usuario == null)
			usuario = servicioImputacionHorasImpl.getUsuarioAcceso();
		return usuario;
	}


	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getIdTipoImputacion() {
		return idTipoImputacion;
	}

	public void setIdTipoImputacion(Long idTipoImputacion) {
		this.idTipoImputacion = idTipoImputacion;
	}
	
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}
	
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public ImputacionHorasBean getImputacionHorasBean() {
		return imputacionHorasBean;
	}

	public void setImputacionHorasBean(ImputacionHorasBean imputacionHorasBean) {
		this.imputacionHorasBean = imputacionHorasBean;
	}

	public List<String> getListaPorImputacion() {
		return listaPorImputacion;
	}

	public void setListaPorImputacion(List<String> listaPorImputacion) {
		this.listaPorImputacion = listaPorImputacion;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public Boolean getEsAdmin() {
		//return utilesColegiado.esADMIN(getUsuario().getIdUsuario());
		return true;
	}

	public List<Usuario> getUsuariosQueImputan() {
		return usuariosQueImputan;
	}

	public void setUsuariosQueImputan(List<Usuario> usuariosQueImputan) {
		this.usuariosQueImputan = usuariosQueImputan;
	}
	
	
	
	
}
