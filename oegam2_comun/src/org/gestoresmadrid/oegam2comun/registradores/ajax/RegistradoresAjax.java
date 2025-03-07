package org.gestoresmadrid.oegam2comun.registradores.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEntidadCancelacion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EntidadCancelacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RegistradoresAjax extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 4123214301695047067L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(RegistradoresAjax.class);

	private static final String TOKEN_SEPARADOR = "||";

	TramiteRegistroDto tramiteRegistro;
	private String codigo;
	private String tipoBien;
	private List<MunicipioDto> municipiosDto;
	private List<TipoViaDto> tipoVias;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;

	@Autowired
	private ServicioBien servicioBien;

	@Autowired
	private ServicioRegistro servicioRegistro;

	@Autowired
	private ServicioEntidadCancelacion servicioEntidadCancelacion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private HttpServletRequest getServletRequest() {
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

	public void recuperarRegistros() throws Throwable {
		log.debug("recuperarRegistros");

		String idProvincia = getServletRequest().getParameter("idProvincia");
		String tipoTramiteRegistro = getServletRequest().getParameter("tipoTramiteRegistro");

		TipoRegistro registro = null;
		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_3
				.getValorEnum().equals(tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_5.getValorEnum().equals(
						tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tipoTramiteRegistro) || TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tipoTramiteRegistro)) {
			registro = TipoRegistro.REGISTRO_MERCANTIL;
		} else if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramiteRegistro)) {
			registro = TipoRegistro.REGISTRO_PROPIEDAD;
		}

		List<RegistroDto> registrosDto = servicioRegistro.getRegistro(idProvincia, registro.getValorEnum());

		String resultado = "";
		if (registrosDto != null && !registrosDto.isEmpty()) {
			Collections.sort(registrosDto, new ComparadorRegistro());
			StringBuilder respuesta = new StringBuilder();
			for (RegistroDto mb : registrosDto) {
				respuesta.append(mb.getNombre() + ";" + mb.getId() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarIdRegistro() throws Throwable {
		log.debug("recuperarIdRegistro");

		String id = getServletRequest().getParameter("id");
		RegistroDto registrosDto = null;
		if (StringUtils.isNotBlank(id) && !"-1".equals(id)) {
			registrosDto = servicioRegistro.getRegistroPorId(Long.parseLong(id));
		}

		String resultado = "";
		if (registrosDto != null) {
			resultado = registrosDto.getIdRegistro();
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void buscarEntidad() throws IOException {
		log.debug("buscarEntidad");

		String cifEntidad = getServletRequest().getParameter("cifEntidad");

		EntidadCancelacionDto entidad = servicioEntidadCancelacion.buscarPorContratoNif(utilesColegiado.getIdContratoSessionBigDecimal(), cifEntidad);
		Gson entidadJson = new Gson();
		String resultado = "";
		if (entidad != null) {
			resultado = entidadJson.toJson(entidad);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void buscarInterviniente() throws IOException {

		String nifInterviniente = getServletRequest().getParameter("nifInterviniente");

		IntervinienteRegistroDto interviniente = new IntervinienteRegistroDto();

		interviniente.setNif(nifInterviniente);
		interviniente.setPersona(servicioPersona.getPersona(interviniente.getNif(), interviniente.getNumColegiado()));
		interviniente.setDireccion(servicioPersona.getDireccionActiva(interviniente.getNif(), interviniente.getNumColegiado()));

		Gson intervinienteJson = new Gson();
		String resultado = "";
		if (null != interviniente.getPersona() && StringUtils.isNotBlank(interviniente.getPersona().getNif())) {
			interviniente.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));
			resultado = intervinienteJson.toJson(interviniente);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void buscarMunicipiosProvincia() throws IOException {
		log.debug("buscarMunicipiosProvincia");

		String codProvincia = getServletRequest().getParameter("codProvincia");

		List<MunicipioDto> municipios = servicioMunicipio.listaMunicipios(codProvincia);
		Gson municipiosJson = new Gson();
		String resultado = "";
		if (municipios != null && !municipios.isEmpty()) {
			resultado = municipiosJson.toJson(municipios);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void buscarRegistro() throws IOException {
		log.debug("buscarRegistro");

		String tipoRegistro = getServletRequest().getParameter("tipoRegistro");

		List<RegistroDto> listRegistroConsulta = servicioRegistro.getRegistro(null, tipoRegistro);
		Collections.sort(listRegistroConsulta, new ComparadorRegistro());

		String resultado = "";
		if (listRegistroConsulta != null && !listRegistroConsulta.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (RegistroDto mb : listRegistroConsulta) {
				respuesta.append(mb.getNombre() + ";" + mb.getId() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public String seleccionarInmueble() {
		if (codigo != null && !codigo.isEmpty()) {
			ResultBean resultado = servicioBien.getBienPorId(Long.parseLong(codigo));
			if (!resultado.getError()) {
				InmuebleDto inmuebleDto = new InmuebleDto();
				inmuebleDto.setBien((BienDto) resultado.getAttachment("bienDto"));
				inmuebleDto.setIdInmueble(null);
				inmuebleDto.setIdTramiteRegistro(null);

				tramiteRegistro = new TramiteRegistroDto();
				tramiteRegistro.setInmueble(inmuebleDto);
			} else {
				addActionError(resultado.getListaMensajes().get(0));
			}
		} else {
			addActionError("Debe seleccionar algún bien.");
		}
	
		return "bienInmueble";
	}

	private class ComparadorRegistro implements Comparator<RegistroDto> {
		@Override
		public int compare(RegistroDto o1, RegistroDto o2) {
			return (o1.getNombre()).compareTo(o2.getNombre());
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<MunicipioDto> getMunicipiosDto() {
		return municipiosDto;
	}

	public void setMunicipiosDto(List<MunicipioDto> municipiosDto) {
		this.municipiosDto = municipiosDto;
	}

	public List<TipoViaDto> getTipoVias() {
		return tipoVias;
	}

	public void setTipoVias(List<TipoViaDto> tipoVias) {
		this.tipoVias = tipoVias;
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

}