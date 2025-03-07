package org.gestoresmadrid.oegam2.transporte.controller.action;

import general.acciones.ActionBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PoderesTransporteAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 5099797418980994447L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(PoderesTransporteAjaxAction.class);
	
	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax
	
	private String provincia;
	private String municipio;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioTipoVia servicioTipoVia;
	
	@Autowired
	ServicioPueblo servicioPueblo;
	
	@Autowired
	ServicioDireccion servicioDireccion;
	
	public void cargarMunicipios(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<MunicipioDto> listaMunicipios = servicioMunicipio.listaMunicipios(provincia);
			String sMunicipios = "";
			if(listaMunicipios != null && !listaMunicipios.isEmpty()){
				for(MunicipioDto municipioDto : listaMunicipios){
					String municipio = municipioDto.getIdMunicipio() + "_" + municipioDto.getNombre();
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
	
	public void cargarListaTipoVia(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<TipoViaDto> listaTiposVia = servicioTipoVia.listadoTipoVias(provincia);
			String sTiposVias = "";
			if(listaTiposVia != null && !listaTiposVia.isEmpty()){
				for(TipoViaDto tipoViaDto : listaTiposVia){
					String tipoVia = tipoViaDto.getIdTipoVia() + "_" + tipoViaDto.getNombre();
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
	
	public void obtenerCodPostal(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			String codPostal = servicioDireccion.obtenerCodigoPostal(municipio, provincia);
			if(codPostal != null && !codPostal.isEmpty()){
				out.print(codPostal);
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ",e);
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
	

}
