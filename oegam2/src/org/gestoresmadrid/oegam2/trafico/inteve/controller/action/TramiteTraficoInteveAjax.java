package org.gestoresmadrid.oegam2.trafico.inteve.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamInteve.service.ServicioTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramiteTraficoInteveAjax extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 8382081670244509549L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(TramiteTraficoInteveAjax.class);
	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax

	private static final String PAG_SOLICITANTE = "pagSolicitanteTrIntv";
	private static final String PAG_REPRESENTANTE = "pagRepresentanteTrIntv";
	private static final String PAG_SOLICITUD = "pagSolicitudTrIntv";

	String nif;
	String idSolInteve;
	Long idContrato;
	String codigoTasa;
	TramiteTraficoInteveDto tramiteInteve;

	@Autowired
	ServicioTramiteInteve servicioTramiteInteve;

	public String buscarSolicitante() {
		if ((nif != null && !nif.isEmpty()) && (idContrato != null)) {
			ResultadoTramiteInteveBean resultado = servicioTramiteInteve.getSolicitante(nif, idContrato);
			if (!resultado.getError()) {
				tramiteInteve = new TramiteTraficoInteveDto();
				tramiteInteve.setSolicitante(resultado.getSolicitante());
			} else {
				addActionError(resultado.getMensaje());
			}
		}
		return PAG_SOLICITANTE;
	}

	public String buscarRepresentante() {
		if ((nif != null && !nif.isEmpty()) && (idContrato != null)) {
			ResultadoTramiteInteveBean resultado = servicioTramiteInteve.getRepresentante(nif, idContrato);
			if (!resultado.getError()) {
				tramiteInteve = new TramiteTraficoInteveDto();
				tramiteInteve.setRepresentante(resultado.getRepresentante());
			} else {
				addActionError(resultado.getMensaje());
			}
		}
		return PAG_REPRESENTANTE;
	}

	public String buscarSolicitudInteve() {
		if (idSolInteve != null && !idSolInteve.isEmpty()) {
			ResultadoTramiteInteveBean resultado = servicioTramiteInteve.getSolicitudInteve(new Long(idSolInteve));
			if (!resultado.getError()) {
				tramiteInteve = new TramiteTraficoInteveDto();
				tramiteInteve.setSolicitudInteve(resultado.getTramiteSolicitudInteve());
				ContratoDto contrato = new ContratoDto();
				contrato.setIdContrato(new BigDecimal(idContrato));
				tramiteInteve.setContrato(contrato);
			} else {
				addActionError(resultado.getMensaje());
			}
		}
		return PAG_SOLICITUD;
	}

	public void cargarTasa() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			ResultadoTramiteInteveBean resultado = servicioTramiteInteve.obtenerListadoTasas(idContrato, codigoTasa);
			if (!resultado.getError()) {
				String tasas = "";
				for (TasaDto tasaDto : resultado.getListaTasas()) {
					if (tasas == "") {
						tasas = tasaDto.getCodigoTasa();
					} else {
						tasas += "_" + tasaDto.getCodigoTasa();
					}
				}
				out.print(tasas);
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar las tasas, error: ", e);
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public TramiteTraficoInteveDto getTramiteInteve() {
		return tramiteInteve;
	}

	public void setTramiteInteve(TramiteTraficoInteveDto tramiteInteve) {
		this.tramiteInteve = tramiteInteve;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getIdSolInteve() {
		return idSolInteve;
	}

	public void setIdSolInteve(String idSolInteve) {
		this.idSolInteve = idSolInteve;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}
}
