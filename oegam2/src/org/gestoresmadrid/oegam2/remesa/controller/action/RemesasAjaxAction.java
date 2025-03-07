package org.gestoresmadrid.oegam2.remesa.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienRemesaDto;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.ServicioIntervinienteModelos;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RemesasAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -5012465665876135582L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(RemesasAjaxAction.class);

	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax

	private static final String PAG_SUJ_PASIVO = "pagSujetoPasivo";
	private static final String PAG_TRANSMITENTE = "pagTransmitente";
	private static final String PAG_BIEN_RUSTICO = "pestaniaBienRustico";
	private static final String PAG_BIEN_URBANO = "pestaniaBienUrbano";
	private static final String PAG_OTRO_BIEN = "pestaniaOtroBien";
	private static final String POP_UP_PRESENTAR = "popPupPresentarRemesa";
	private static final String PAG_BIEN_600 = "pagBien600";
	private static final String PAG_BIEN_601 = "pagBien601";
	private static final String PAG_NOTARIO_600 = "pagNotario600";
	private static final String PAG_NOTARIO_601 = "pagNotario601";

	private String codigo;
	private Long idRemesa;
	private String tipoModelo;
	private String tipoBien;
	private String tipoInterviniente;
	private BigDecimal idContrato;
	private String nif;
	private String modelo;
	private RemesaDto remesa;
	private UtilesRemesas utilesRemesas;
	private DatosBancariosFavoritosDto datosBancarios;
	private String provincia;
	private String municipio;

	@Autowired
	private ServicioRemesas servicioRemesas;

	@Autowired
	private ServicioIntervinienteModelos servicioIntervinienteModelos;

	@Autowired
	private ServicioBien servicioBien;

	@Autowired
	private ServicioConcepto servicioConcepto;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;

	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;

	@Autowired
	private ServicioPueblo servicioPueblo;

	@Autowired
	private ServicioNotario servicioNotario;

	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String modificarBien() throws Throwable {
		String pagina = null;
		if (codigo != null && !codigo.isEmpty()) {
			remesa = new RemesaDto();
			// obtener de base de datos el bien
			BienRemesaDto bienRemesaDto = servicioBien.getBienRemesaPorBienYRemesa(Long.parseLong(codigo), idRemesa);
			if (bienRemesaDto == null || bienRemesaDto.getBien() == null) {
				addActionError("Debe seleccionar algun bien.");
			} else {
				remesa.setModelo(bienRemesaDto.getRemesa().getModelo());
				if (TipoBien.Rustico.getValorEnum().equals(bienRemesaDto.getBien().getTipoBien())) {
					remesa.setBienRusticoDto(bienRemesaDto.getBien());
					pagina = PAG_BIEN_RUSTICO;
				} else if (TipoBien.Urbano.getValorEnum().equals(bienRemesaDto.getBien().getTipoBien())) {
					remesa.setBienUrbanoDto(bienRemesaDto.getBien());
					pagina = PAG_BIEN_URBANO;
				} else {
					remesa.setOtroBienDto(bienRemesaDto.getBien());
					pagina = PAG_OTRO_BIEN;
				}
			}
		}
		return pagina;
	}

	public String buscarPersona() {
		String pagina = "";
		ResultBean resultado = null;
		if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioRemesas.buscarPersona(nif, idContrato, idRemesa, tipoInterviniente);
			remesa = new RemesaDto();
			if (!resultado.getError()) {
				remesa.setSujetoPasivo(rellenarNifIntervinienteSiEsNulo((IntervinienteModelosDto) resultado.getAttachment("intervinienteDto"), nif));
				remesa.getSujetoPasivo().setTipoInterviniente(TipoInterviniente.SujetoPasivo.getValorEnum());
			}
			pagina = PAG_SUJ_PASIVO;
		} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipoInterviniente)) {
			resultado = servicioRemesas.buscarPersona(nif, idContrato, idRemesa, tipoInterviniente);
			remesa = new RemesaDto();
			if (!resultado.getError()) {
				remesa.setTransmitente(rellenarNifIntervinienteSiEsNulo((IntervinienteModelosDto) resultado.getAttachment("intervinienteDto"), nif));
				remesa.getTransmitente().setTipoInterviniente(TipoInterviniente.Transmitente.getValorEnum());
			}
			pagina = PAG_TRANSMITENTE;
		}
		if (resultado.getError()) {
			addActionError(resultado.getListaMensajes().get(0));
		}
		return pagina;
	}

	private IntervinienteModelosDto rellenarNifIntervinienteSiEsNulo(IntervinienteModelosDto interviniente, String nif) {
		if (interviniente != null && interviniente.getPersona() == null) {
			PersonaDto persona = new PersonaDto();
			persona.setNif(nif);
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	public String modificarPersona() {
		ResultBean resultado = null;
		String pagina = "";
		if (codigo != null && !codigo.isEmpty()) {
			if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipoInterviniente)) {
				resultado = servicioIntervinienteModelos.getIntervinientesRemesaPorId(Long.parseLong(codigo));
				if (resultado != null && !resultado.getError()) {
					remesa = new RemesaDto();
					remesa.setSujetoPasivo((IntervinienteModelosDto) resultado.getAttachment("intervMDto"));
				} else if (resultado != null && resultado.getError()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					addActionError("a sucedido un error a la hora de recargar el sujeto pasivo.");
				}
				pagina = PAG_SUJ_PASIVO;
			} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipoInterviniente)) {
				resultado = servicioIntervinienteModelos.getIntervinientesRemesaPorId(Long.parseLong(codigo));
				if (resultado != null && !resultado.getError()) {
					remesa = new RemesaDto();
					remesa.setTransmitente((IntervinienteModelosDto) resultado.getAttachment("intervMDto"));
				} else if (resultado != null && resultado.getError()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					addActionError("a sucedido un error a la hora de recargar el sujeto pasivo.");
				}
				pagina = PAG_TRANSMITENTE;
			}

		} else {
			if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipoInterviniente)) {
				addActionError("Debe seleccionar un sujeto pasivo para modificar.");
				pagina = PAG_SUJ_PASIVO;
			} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipoInterviniente)) {
				addActionError("Debe seleccionar un transmitente para modificar.");
				pagina = PAG_TRANSMITENTE;
			}
		}
		return pagina;
	}

	public void getGrupoValidacion() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			out.print(servicioRemesas.getGrupoValidacion(codigo));
		} catch (IOException e) {
			log.error("Ha sucedido en error a la hora de obtener el grupo de validacion del concepto, error: ", e);
		}
	}

	public void getMontoBonificacion() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			out.print(servicioRemesas.getMontoBonificacion(codigo));
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de obtener el monto de la bonificación, error: ", e);
		}
	}

	public void getConceptoPorModelo() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<ConceptoDto> lista = servicioConcepto.getlistaConceptosPorModelo(modelo, "A1");
			String data = "";
			for (ConceptoDto concepto : lista) {
				if (data == "") {
					data = concepto.getConcepto() + ";" + concepto.getDescConcepto();
				} else {
					data += "||" + concepto.getConcepto() + ";" + concepto.getDescConcepto();
				}
			}
			out.print(data);
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los conceptos por modelo, error: ", e);
		}
	}

	public void comprobarEstadosModelos() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			if (codigo != null && !codigo.isEmpty()) {
				ResultBean resultado = servicioRemesas.comprobarEstadosModelosGenerarPorBloque(codigo);
				if (resultado.getError()) {
					out.print(false);
				} else {
					out.print(true);
				}
			} else {
				out.print(false);
			}
		} catch (IOException e) {
			log.error("Ha sucedido en error a la hora de comprobar los estados de los modelos, error: ", e);
		}
	}

	public String cargarDatosBancarios() {
		if (codigo != null) {
			ResultBean resultDatosBancarios = servicioDatosBancariosFavoritos.getDatoBancario(Long.parseLong(codigo));
			if (!resultDatosBancarios.getError()) {
				datosBancarios = (DatosBancariosFavoritosDto) resultDatosBancarios.getAttachment("datosBancariosDto");
			} else {
				addActionError(resultDatosBancarios.getListaMensajes().get(0));
			}
		}
		return POP_UP_PRESENTAR;
	}

	public String bienNuevo() {
		String pagina = "";
		if (codigo != null && !codigo.isEmpty()) {
			ResultBean resultado = servicioBien.getBienPorId(Long.parseLong(codigo));
			if (!resultado.getError()) {
				
				remesa = new RemesaDto();
				remesa.setModelo(new ModeloDto());
				remesa.getModelo().setModelo(tipoModelo);

				if (TipoBien.Rustico.getValorEnum().equals(tipoBien)) {
					remesa.setBienRusticoDto((BienDto) resultado.getAttachment("bienDto"));
				} else if (TipoBien.Urbano.getValorEnum().equals(tipoBien)) {
					remesa.setBienUrbanoDto((BienDto) resultado.getAttachment("bienDto"));
				} else if (TipoBien.Otro.getValorEnum().equals(tipoBien)) {
					remesa.setOtroBienDto((BienDto) resultado.getAttachment("bienDto"));
				}
			} else {
				addActionError(resultado.getListaMensajes().get(0));
			}
			if (Modelo.Modelo600.getValorEnum().equals(tipoModelo)) {
				pagina = PAG_BIEN_600;
			} else if (Modelo.Modelo601.getValorEnum().equals(tipoModelo)) {
				pagina = PAG_BIEN_601;
			}
		} else {
			addActionError("Debe seleccionar algun bien.");
		}
		return pagina;
	}

	public String notarioNuevo() {
		String pagina = "";
		if (codigo != null && !codigo.isEmpty()) {
			NotarioDto notarioDto = servicioNotario.getNotarioPorId(codigo);
			if (notarioDto == null) {
				addActionError("El notario seleccionado no existe.");
			} else {
				remesa = new RemesaDto();
				remesa.setNotario(notarioDto);
				remesa.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(tipoModelo)));
			}
			if (Modelo.Modelo600.getValorEnum().equals(tipoModelo)) {
				pagina = PAG_NOTARIO_600;
			} else if (Modelo.Modelo601.getValorEnum().equals(tipoModelo)) {
				pagina = PAG_NOTARIO_601;
			}
		} else {
			addActionError("Debe seleccionar algun notario.");
		}
		return pagina;
	}

	public void buscarNotario() throws IOException {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		NotarioDto notario = null;
		notario = servicioNotario.getNotarioPorId(codigo);
		out = servletResponse.getWriter();
		if (notario == null) {
			notario = new NotarioDto();
			notario.setCodigoNotario("Error");
		}
		out.print(notario.toString());
	}

	public void comprobarContratosRemesas() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			boolean esOk = true;
			if (codigo != null && !codigo.isEmpty()) {
				if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
					ResultBean resultado = servicioRemesas.validarContratosPresentacionEnBloque(codigo, utilesColegiado.getIdContratoSessionBigDecimal());
					if (resultado.getError()) {
						esOk = false;
					}
				}
			} else {
				esOk = false;
			}
			out.print(esOk);
		} catch (IOException e) {
			log.error("Ha sucedido en error a la hora de comprobar los contratos de los modelos, error: ", e);
		}
	}

	public void cargarMunicipios() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<MunicipioCamDto> listaMunicipios = servicioMunicipioCam.getListaMunicipiosPorProvincia(provincia);
			String sMunicipios = "";
			if (listaMunicipios != null && !listaMunicipios.isEmpty()) {
				for (MunicipioCamDto municipioCamDto : listaMunicipios) {
					String municipio = municipioCamDto.getIdMunicipio() + "_" + municipioCamDto.getNombre();
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

	public void cargarListaTipoViaCam() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			List<TipoViaCamDto> listaTiposVia = servicioTipoViaCam.getListaTipoVias();
			String sTiposVias = "";
			if (listaTiposVia != null && !listaTiposVia.isEmpty()) {
				for (TipoViaCamDto tipoViaCamDto : listaTiposVia) {
					String tipoVia = tipoViaCamDto.getIdTipoViaCam() + "_" + tipoViaCamDto.getNombre();
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

	public String getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(String tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public ServicioRemesas getServicioRemesas() {
		return servicioRemesas;
	}

	public void setServicioRemesas(ServicioRemesas servicioRemesas) {
		this.servicioRemesas = servicioRemesas;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public UtilesRemesas getUtilesRemesas() {
		if (utilesRemesas == null) {
			utilesRemesas = new UtilesRemesas();
		}
		return utilesRemesas;
	}

	public void setUtilesRemesas(UtilesRemesas utilesRemesas) {
		this.utilesRemesas = utilesRemesas;
	}

	public RemesaDto getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
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
