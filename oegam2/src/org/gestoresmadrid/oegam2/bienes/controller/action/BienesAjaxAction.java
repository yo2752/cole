package org.gestoresmadrid.oegam2.bienes.controller.action;

import general.acciones.ActionBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.oegam2.modelos.controller.action.ModelosAjaxAction;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.model.service.ServicioTipoInmueble;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class BienesAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 3812244036015068347L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModelosAjaxAction.class);

	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax
	
	private String provincia;
	private String municipio;
	private String tipoBien;
	
	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;
	
	@Autowired
	private ServicioTipoInmueble servicioTipoInmueble;
	
	@Autowired
	private ServicioPueblo servicioPueblo;
	
	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;
	
	public void cargarMunicipios(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<MunicipioCamDto> listaMunicipios = servicioMunicipioCam.getListaMunicipiosPorProvincia(provincia);
			String sMunicipios = "";
			if(listaMunicipios != null && !listaMunicipios.isEmpty()){
				for(MunicipioCamDto municipioCamDto : listaMunicipios){
					String municipio = municipioCamDto.getIdMunicipio() + "_" + municipioCamDto.getNombre();
					if(sMunicipios == ""){
						sMunicipios = municipio;
					}else{
						sMunicipios += "|" + municipio;
					}
				}
			}
			out.print(sMunicipios);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los municipios de la provincia, error: ",e);
		}
	}
	
	public void cargarListaTipoViaCam(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<TipoViaCamDto> listaTiposVia = servicioTipoViaCam.getListaTipoVias();
			String sTiposVias = "";
			if(listaTiposVia != null && !listaTiposVia.isEmpty()){
				for(TipoViaCamDto tipoViaCamDto : listaTiposVia){
					String tipoVia = tipoViaCamDto.getIdTipoViaCam() + "_" + tipoViaCamDto.getNombre();
					if(sTiposVias == ""){
						sTiposVias = tipoVia;
					}else{
						sTiposVias += "|" + tipoVia;
					}
				}
			}
			out.print(sTiposVias);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los tipos de Vias de la provincia, error: ",e);
		}
	}
	
	public void cargarPueblo(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<PuebloDto> listaPueblos = servicioPueblo.listaPueblos(provincia, municipio);
			String sPueblos = "";
			if(listaPueblos != null && !listaPueblos.isEmpty()){
				for(PuebloDto puebloDto : listaPueblos){
					if(sPueblos == ""){
						sPueblos = puebloDto.getPueblo();
					}else{
						sPueblos += "_" + puebloDto.getPueblo();
					}
				}
			}
			out.print(sPueblos);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ",e);
		}
	}
	
	public void cargarListaTipoInmuebles(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<TipoInmuebleDto> listaTiposInmuebles = servicioTipoInmueble.getListaTiposInmueblesPorTipo(TipoBien.convertirValor(tipoBien));
			String sTiposInmuebles = "";
			if(listaTiposInmuebles != null && !listaTiposInmuebles.isEmpty()){
				for(TipoInmuebleDto tipoInmuebleDto : listaTiposInmuebles){
					String tipoInmueble = tipoInmuebleDto.getIdTipoInmueble() + "_" + tipoInmuebleDto.getDescTipoInmueble();
					if(sTiposInmuebles == ""){
						sTiposInmuebles = tipoInmueble;
					}else{
						sTiposInmuebles += "|" + tipoInmueble;
					}
				}
			}
			out.print(sTiposInmuebles);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los tipos de inmuebles dependiendo del tipo de bien, error: ",e);
		}
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
	
	public static ILoggerOegam getLog() {
		return log;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}
	
	
}
