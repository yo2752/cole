package org.gestoresmadrid.oegam2.controller.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegam2comun.model.service.ServicioMonitor;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ChartDataDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import colas.beans.ProcesoMonitorizado;
import general.acciones.ActionBase;

public class MonitorizacionAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 5634472172338281246L;

	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;

	private List<ProcesoMonitorizado> listaProcesosTrafico;
	private List<ProcesoMonitorizado> listaProcesosGDoc;
	private Date fechaActualizacion;
	private String proceso;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");

	@Autowired
	private ServicioMonitor servicio;

	public String procesos() {
		listaProcesosTrafico = servicio.monitorizaProcesosTrafico();
		listaProcesosGDoc = servicio.monitorizaProcesosGDoc();

		return "monitorizaProcesos";
	}

	public String estadistica() {
		return "estadisticaProcesos";
	}

	public void cargarSolicitudesPorHilo() throws IOException{
		proceso = servletRequest.getParameter("proceso");
		ChartDataDto data = servicio.listarSolicitudes(proceso);
		String json = new Gson().toJson(data);
		servletResponse.setContentType("application/json");
		servletResponse.getWriter().write(json);
	}

	public void cargarFinalizacionesProceso() throws IOException{
		proceso = servletRequest.getParameter("proceso");
		ChartDataDto data = servicio.listarFinalizaciones(proceso);
		String json = new Gson().toJson(data);
		servletResponse.setContentType("application/json");
		servletResponse.getWriter().write(json);
	}

	public void cargarEstadisticasGeneral() throws IOException{
		ChartDataDto data = servicio.peticionesTotales();
		String json = new Gson().toJson(data);
		servletResponse.setContentType("application/json");
		servletResponse.getWriter().write(json);
	}

	public List<ProcesoMonitorizado> getListaProcesosTrafico() {
		return listaProcesosTrafico;
	}

	public void setListaProcesosTrafico(List<ProcesoMonitorizado> listaProcesosTrafico) {
		this.listaProcesosTrafico = listaProcesosTrafico;
	}

	public List<ProcesoMonitorizado> getListaProcesosGDoc() {
		return listaProcesosGDoc;
	}

	public void setListaProcesosGDoc(List<ProcesoMonitorizado> listaProcesosGDoc) {
		this.listaProcesosGDoc = listaProcesosGDoc;
	}

	public String getFechaActualizacion() {
		fechaActualizacion = new Date();
		return (fechaActualizacion == null ? "" : formatoFecha.format(fechaActualizacion));
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

}