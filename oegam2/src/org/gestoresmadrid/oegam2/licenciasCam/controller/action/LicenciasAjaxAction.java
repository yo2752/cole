package org.gestoresmadrid.oegam2.licenciasCam.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcIntervinienteDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcPersonaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class LicenciasAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -5012465665876135582L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LicenciasAjaxAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private String tipoInterviniente;

	private String nif;

	private String numColegiado;

	private LcTramiteDto lcTramiteDto;

	private boolean utilizarTitular;

	@Autowired
	ServicioLcInterviniente servicioLcInterviniente;

	public String buscarPersona() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		String pagina = "";
		if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioLcInterviniente.buscarPersona(nif, numColegiado);
			lcTramiteDto = new LcTramiteDto();
			if (resultado != null && !resultado.getError() && resultado.getInterviniente() != null) {
				lcTramiteDto.setIntervinienteInteresado(resultado.getInterviniente());
				lcTramiteDto.getIntervinienteInteresado().setTipoInterviniente(TipoInterviniente.InteresadoPrincipal.getValorEnum());
			} else {
				lcTramiteDto.setIntervinienteInteresado(new LcIntervinienteDto());
				lcTramiteDto.getIntervinienteInteresado().setLcPersona(new LcPersonaDto());
				lcTramiteDto.getIntervinienteInteresado().getLcPersona().setNif(nif);
			}
			pagina = "pagInteresadoPrincipal";
		} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioLcInterviniente.buscarPersona(nif, numColegiado);
			lcTramiteDto = new LcTramiteDto();
			if (resultado != null && !resultado.getError() && resultado.getInterviniente() != null) {
				lcTramiteDto.setIntervinienteRepresentante(resultado.getInterviniente());
				lcTramiteDto.getIntervinienteRepresentante().setTipoInterviniente(TipoInterviniente.RepresentanteInteresado.getValorEnum());
			} else {
				lcTramiteDto.setIntervinienteInteresado(new LcIntervinienteDto());
				lcTramiteDto.getIntervinienteInteresado().setLcPersona(new LcPersonaDto());
				lcTramiteDto.getIntervinienteInteresado().getLcPersona().setNif(nif);
			}
			pagina = "pagRepInteresado";
		} else if (TipoInterviniente.OtroInteresado.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioLcInterviniente.buscarPersona(nif, numColegiado);
			lcTramiteDto = new LcTramiteDto();
			if (resultado != null && !resultado.getError() && resultado.getInterviniente() != null) {
				lcTramiteDto.setIntervinienteOtrosInteresados(resultado.getInterviniente());
				lcTramiteDto.getIntervinienteOtrosInteresados().setTipoInterviniente(TipoInterviniente.OtroInteresado.getValorEnum());
			} else {
				lcTramiteDto.setIntervinienteInteresado(new LcIntervinienteDto());
				lcTramiteDto.getIntervinienteInteresado().setLcPersona(new LcPersonaDto());
				lcTramiteDto.getIntervinienteInteresado().getLcPersona().setNif(nif);
			}
			pagina = "pagOtrInteresado";
		} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioLcInterviniente.buscarPersona(nif, numColegiado);
			utilizarTitular = true;
			lcTramiteDto = new LcTramiteDto();
			if (resultado != null && !resultado.getError() && resultado.getInterviniente() != null) {
				lcTramiteDto.setIntervinienteNotificacion(resultado.getInterviniente());
				lcTramiteDto.getIntervinienteNotificacion().setTipoInterviniente(TipoInterviniente.Notificacion.getValorEnum());
			} else {
				lcTramiteDto.setIntervinienteInteresado(new LcIntervinienteDto());
				lcTramiteDto.getIntervinienteInteresado().setLcPersona(new LcPersonaDto());
				lcTramiteDto.getIntervinienteInteresado().getLcPersona().setNif(nif);
			}
			pagina = "pagNotificacion";
		}
		return pagina;
	}

	public String copiarTitular() {
		ResultadoLicenciasBean resultado = null;
		lcTramiteDto = new LcTramiteDto();
		if (!StringUtils.isBlank(nif)) {
			resultado = servicioLcInterviniente.buscarPersona(nif, numColegiado);
		}
		if (resultado != null) {
			lcTramiteDto.setIntervinienteNotificacion(resultado.getInterviniente());
			lcTramiteDto.getIntervinienteNotificacion().setTipoInterviniente(TipoInterviniente.Notificacion.getValorEnum());
		}
		return "pagNotificacion";
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

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public LcTramiteDto getLcTramiteDto() {
		return lcTramiteDto;
	}

	public void setLcTramiteDto(LcTramiteDto lcTramiteDto) {
		this.lcTramiteDto = lcTramiteDto;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public boolean isUtilizarTitular() {
		return utilizarTitular;
	}

	public void setUtilizarTitular(boolean utilizarTitular) {
		this.utilizarTitular = utilizarTitular;
	}
}