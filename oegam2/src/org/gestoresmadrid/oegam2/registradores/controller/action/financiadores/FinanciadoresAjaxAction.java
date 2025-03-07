package org.gestoresmadrid.oegam2.registradores.controller.action.financiadores;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FinanciadoresAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = -6082640433217635744L;
	private static final String RESULTADO_EDIT = "editar";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_FINANCIADOR = "popUpRepresentanteFinanciadorRegistro";

	private static ILoggerOegam log = LoggerOegam.getLogger(FinanciadoresAjaxAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private String identificador;
	private String idFinanciador;
	public String getIdFinanciador() {
		return idFinanciador;
	}

	public void setIdFinanciador(String idFinanciador) {
		this.idFinanciador = idFinanciador;
	}

	private String idRepresentante;
	private String financiadorNif;
	private String representanteNif;
	public String getRepresentanteNif() {
		return representanteNif;
	}

	public void setRepresentanteNif(String representanteNif) {
		this.representanteNif = representanteNif;
	}

	private IntervinienteRegistroDto financiador;
	private List<IntervinienteRegistroDto> representantes;
	private IntervinienteRegistroDto representante;

	@Autowired
	private ServicioFinanciador servicioFinanciador;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Resource
	private ModelPagination modeloFinanciadoresPaginated;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public IntervinienteRegistroDto getFinanciador() {
		return financiador;
	}

	public void setFinanciador(IntervinienteRegistroDto financiador) {
		this.financiador = financiador;
	}

	public String buscarPersona(){
		log.debug("Buscando financiador por nif...");
		financiador = new IntervinienteRegistroDto();
		financiador.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		financiador.setTipoInterviniente(TipoInterviniente.Financiador.getValorEnum());
		financiador.setNif(financiadorNif);
		financiador.setPersona(servicioPersona.getPersona(financiador.getNif(), financiador.getNumColegiado()));
		if (null != financiador.getPersona() && StringUtils.isNotBlank(financiador.getPersona().getTipoPersona())){
			financiador.setTipoPersona(TipoPersonaRegistro.convertirValorXml(financiador.getPersona().getTipoPersona()));
		} else {
			addActionError("Interviniente no encontrado");
		}
		financiador.setDireccion(servicioPersona.getDireccionActiva(financiador.getNif(), financiador.getNumColegiado()));
		if (0 != financiador.getIdInterviniente())
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		return RESULTADO_EDIT;
	}

	public String buscarPersonaRepresentante(){
		log.debug("Buscando representante financiador por nif...");
		representante = new IntervinienteRegistroDto();
		representante.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		representante.setTipoInterviniente(TipoInterviniente.RepresentanteFinanciador.getValorEnum());
		representante.setNif(representanteNif);
		representante.setPersona(servicioPersona.getPersona(representante.getNif(), representante.getNumColegiado()));
		if (null != representante.getPersona() && StringUtils.isNotBlank(representante.getPersona().getTipoPersona())){
			representante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));
		}
		representante.setDireccion(servicioPersona.getDireccionActiva(representante.getNif(), representante.getNumColegiado()));
		return POP_UP_ANIADIR_REPRESENTANTE_FINANCIADOR;
	}

	public List<IntervinienteRegistroDto> getRepresentantes() {
		return representantes;
	}

	public void setRepresentantes(List<IntervinienteRegistroDto> representantes) {
		this.representantes = representantes;
	}

	public IntervinienteRegistroDto getRepresentante() {
		return representante;
	}

	public void setRepresentante(IntervinienteRegistroDto representante) {
		this.representante = representante;
	}

	public String getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(String idRepresentante) {
		this.idRepresentante = idRepresentante;
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

	public String getFinanciadorNif() {
		return financiadorNif;
	}

	public void setFinanciadorNif(String financiadorNif) {
		this.financiadorNif = financiadorNif;
	}

}