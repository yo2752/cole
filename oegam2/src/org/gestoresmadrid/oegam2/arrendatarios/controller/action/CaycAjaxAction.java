package org.gestoresmadrid.oegam2.arrendatarios.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class CaycAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(CaycAjaxAction.class);
	private static final String PAG_PERSONA_ARREN = "pagPersonaArrendatario";
	private static final String PAG_PERSONA_COND = "pagPersonaConductor";

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private String provincia;
	private String municipio;
	private ArrendatarioDto arrendatarioDto;
	private ConductorDto conductorDto;
	private String nif;
	private BigDecimal idContrato;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioPueblo servicioPueblo;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioContrato servicioContrato;

	public void cargarMunicipios() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<MunicipioDto> listaMunicipios = servicioMunicipio.listaMunicipios(provincia);
			String sMunicipios = "";
			if (listaMunicipios != null && !listaMunicipios.isEmpty()) {
				for (MunicipioDto municipioDto : listaMunicipios) {
					String municipio = municipioDto.getIdMunicipio() + "_" + municipioDto.getNombre();
					if (sMunicipios == "") {
						sMunicipios = municipio;
					} else {
						sMunicipios += "|" + municipio;
					}
				}
			}
			out.print(sMunicipios);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los municipios de la provincia, error: ", e);
		}
	}

	public void cargarListaTipoVia() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<TipoViaDto> listaTiposVia = servicioTipoVia.listadoTipoVias(provincia);
			String sTiposVias = "";
			if (listaTiposVia != null && !listaTiposVia.isEmpty()) {
				for (TipoViaDto tipoViaDto : listaTiposVia) {
					String tipoVia = tipoViaDto.getIdTipoVia() + "_" + tipoViaDto.getNombre();
					if (sTiposVias == "") {
						sTiposVias = tipoVia;
					} else {
						sTiposVias += "|" + tipoVia;
					}
				}
			}
			out.print(sTiposVias);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los tipos de Vias de la provincia, error: ", e);
		}
	}

	public void cargarPueblo() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<PuebloDto> listaPueblos = servicioPueblo.listaPueblos(provincia, municipio);
			String sPueblos = "";
			if (listaPueblos != null && !listaPueblos.isEmpty()) {
				for (PuebloDto puebloDto : listaPueblos) {
					if (sPueblos == "") {
						sPueblos = puebloDto.getPueblo();
					} else {
						sPueblos += "_" + puebloDto.getPueblo();
					}
				}
			}
			out.print(sPueblos);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ", e);
		}
	}

	public void obtenerCodPostal() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			String codPostal = servicioDireccion.obtenerCodigoPostal(municipio, provincia);
			if (codPostal != null && !codPostal.isEmpty()) {
				out.print(codPostal);
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ", e);
		}
	}

	public String buscarPersonaArrendatario() {
		ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);

		if (contratoDto != null && contratoDto.getColegiadoDto() != null
				&& contratoDto.getColegiadoDto().getNumColegiado() != null && nif != null && !nif.isEmpty()) {
			arrendatarioDto = new ArrendatarioDto();
			arrendatarioDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			arrendatarioDto.setNif(nif);
			arrendatarioDto.setPersona(servicioPersona.getPersonaConDireccion(arrendatarioDto.getNif(),
					arrendatarioDto.getNumColegiado()));
		}

		if (arrendatarioDto == null || arrendatarioDto.getPersona() == null) {
			addActionError("Persona no encontrada con nif: " + nif);

			// Autor: DSR
			// FECHA: 11/01/2018
			// Se añade este codigo por que si no al volver se pierde el contenido del DNI
			// en el combo
			arrendatarioDto = new ArrendatarioDto();
			PersonaDto persona = new PersonaDto();
			arrendatarioDto.setPersona(persona);
			arrendatarioDto.getPersona().setNif(nif);
		}

		return PAG_PERSONA_ARREN;
	}

	public String buscarPersonaConductor() {
		ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);

		if (contratoDto != null && contratoDto.getColegiadoDto() != null
				&& contratoDto.getColegiadoDto().getNumColegiado() != null && nif != null && !nif.isEmpty()) {
			conductorDto = new ConductorDto();
			conductorDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			conductorDto.setNif(nif);
			conductorDto.setPersona(
					servicioPersona.getPersonaConDireccion(conductorDto.getNif(), conductorDto.getNumColegiado()));
		}

		if (conductorDto == null || conductorDto.getPersona() == null) {
			addActionError("Persona no encontrada con nif: " + nif);

			// Autor: DSR
			// FECHA: 11/01/2018
			// Se añade este código porque si no al volver se pierde el contenido del DNI
			// en el combo
			conductorDto = new ConductorDto();
			PersonaDto persona = new PersonaDto();
			conductorDto.setPersona(persona);
			conductorDto.getPersona().setNif(nif);

		}

		return PAG_PERSONA_COND;
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

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public ArrendatarioDto getArrendatarioDto() {
		return arrendatarioDto;
	}

	public void setArrendatarioDto(ArrendatarioDto arrendatarioDto) {
		this.arrendatarioDto = arrendatarioDto;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public ConductorDto getConductorDto() {
		return conductorDto;
	}

	public void setConductorDto(ConductorDto conductorDto) {
		this.conductorDto = conductorDto;
	}

}