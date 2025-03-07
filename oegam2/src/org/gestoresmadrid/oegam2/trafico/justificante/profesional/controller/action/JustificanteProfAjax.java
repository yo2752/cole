package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.MotivoJustificante;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class JustificanteProfAjax  extends ActionBase implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 3444031393454270977L;

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private String nifBusqueda;
	private String matriculaBusqueda;
	private String numColegiado;
	private String tipoTramite;
	private String motivo;
	private JustificanteProfDto justificanteProfDto;
	private IntervinienteTraficoDto titular;
	private String provincia;
	private String municipio;
	private static final String PAG_TITULAR = "pagTitular";
	private static final String PAG_VEHICULO = "pagVehiculo";

	private static final ILoggerOegam log = LoggerOegam.getLogger(JustificanteProfAjax.class);

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioPueblo servicioPueblo;

	@Autowired
	ServicioDireccion servicioDireccion;

	public String buscarVehiculo() {
		if (matriculaBusqueda != null && !matriculaBusqueda.equals("")) {
			ResultBean resultVehiculo = servicioJustificanteProfesional.getVehiculoColegiado(matriculaBusqueda, numColegiado);
			if (!resultVehiculo.getError()) {
				justificanteProfDto = new JustificanteProfDto();
				TramiteTrafDto tramiteTrafDto = new TramiteTrafDto();
				tramiteTrafDto.setVehiculoDto((VehiculoDto)resultVehiculo.getAttachment(ServicioJustificanteProfesional.VEHICULO_JUST_PROF));
				justificanteProfDto.setTramiteTrafico(tramiteTrafDto);
			} else {
				aniadirMensajeError(resultVehiculo);
			}
		}
		return PAG_VEHICULO;
	}

	public String consultarPersona() {
		if (nifBusqueda != null && !"".equals(nifBusqueda)) {
			ResultBean resultBusqueda = servicioJustificanteProfesional.getTitularNif(nifBusqueda,numColegiado);
			if (!resultBusqueda.getError()) {
				titular = (IntervinienteTraficoDto) resultBusqueda.getAttachment(ServicioJustificanteProfesional.TITULAR_JUST_PROF);
			} else {
				aniadirMensajeError(resultBusqueda);
			}
		}
		return PAG_TITULAR;
	}

	public void getMotivoPorTipoTramite() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			String data = "";
			if (tipoTramite != null && !tipoTramite.isEmpty() && !tipoTramite.equals("-1")) {
				for (MotivoJustificante motivo : MotivoJustificante.values()) {
					if (motivo.getTipoTramite().equals(tipoTramite)) {
						if (data == "") {
							data = motivo.getValorEnum() + ";" + motivo.getNombreEnum();
						} else {
							data += "||" + motivo.getValorEnum() + ";" + motivo.getNombreEnum();
						}
					}
				}
			}
			out.print(data);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los motivos por tipoTramite, error: ", e);
		}
	}

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

	public void cargarListaTipoVia(){
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

	public void cargarPueblo(){
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
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ",e);
		}
	}

	public void recuperarCodPostal() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			String codigoPostal = servicioDireccion.obtenerCodigoPostal(municipio, provincia);
			if (codigoPostal != null && !codigoPostal.isEmpty()) {
				out.print(codigoPostal);
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de recuperar el codigo postal, error: ",e);
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

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public String getMatriculaBusqueda() {
		return matriculaBusqueda;
	}

	public void setMatriculaBusqueda(String matriculaBusqueda) {
		this.matriculaBusqueda = matriculaBusqueda;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public JustificanteProfDto getJustificanteProfDto() {
		return justificanteProfDto;
	}

	public void setJustificanteProfDto(JustificanteProfDto justificanteProfDto) {
		this.justificanteProfDto = justificanteProfDto;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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