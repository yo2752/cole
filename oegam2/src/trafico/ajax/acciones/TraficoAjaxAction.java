package trafico.ajax.acciones;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioLocalidadDgt;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.model.service.ServicioOficinaLiquidadora620;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasaPegatina;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.dto.TasaPegatinaDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioConstruccion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioUtilizacion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaCam;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioModeloCam;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoReduccion;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioConstruccionDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioUtilizacionDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.DirectivaCeeDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.FabricanteDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.ModeloCamDto;
import org.gestoresmadrid.oegam2comun.view.dto.OficinaLiquidadora620Dto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import general.beans.RespuestaGenerica;
import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;
import oegam.constantes.ConstantesPQ;
import trafico.ajax.daos.DaoAjax;
import trafico.beans.EstacionITVBean;
import trafico.modelo.ModeloTrafico;
import trafico.utiles.enumerados.ProvinciasEstacionITV;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class TraficoAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 6927356467801804850L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(TraficoAjaxAction.class);

	private static final String CADENA_VACIA = "";
	private static final String TOKEN_SEPARADOR = "||";

	private static String DiaUnoMesUno = "0101";

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private List<MunicipioDto> municipiosDto;
	private List<TipoViaDto> tipoVias;
	private List<ColegioDto> colegios;
	private List<JefaturaTraficoDto> jefaturas;
	private List<ModeloCamDto> modelos;
	private List<TasaDto> tasas;
	private List<OficinaLiquidadora620Dto> oficinasLiquidadoras;
	private List<PuebloDto> pueblos;
	private List<String> localidadDGT;
	private List<ModeloCamDto> datosVehiculo;
	private String codigoPostal;
	private List<ViaDto> nombreVias;
	private List<FabricanteDto> nombreFabricantes;
	private List<MarcaDgtDto> listaMarcas;
	private List<CriterioConstruccionDto> listaCriteriosConstruccion;
	private List<CriterioUtilizacionDto> listaCriteriosUtilizacion;
	private String sitexCTITPrevio;
	private List<DirectivaCeeDto> listaHomologaciones;
	private List<PaisDto> paises;

	private String resultadoEnvio = "";

	private List<TasaPegatinaDto> codigosTasasPegatina;

	@Autowired
	private ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina;

	@Autowired
	private ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private ServicioTipoVia servicioTipoVia;

	@Autowired
	private ServicioLocalidadDgt servicioLocalidadDgt;

	@Autowired
	private ServicioFabricante servicioFabricante;

	@Autowired
	private ServicioCriterioConstruccion servicioCriterioConstruccion;

	@Autowired
	private ServicioCriterioUtilizacion servicioCriterioUtilizacion;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	private ServicioEstacionITV servicioEstacionITV;

	@Autowired
	private ServicioPueblo servicioPueblo;

	@Autowired
	private ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	private ServicioVia servicioVia;

	@Autowired
	private ServicioMarcaCam servicioMarcaCam;

	@Autowired
	private ServicioModeloCam servicioModeloCam;

	@Autowired
	private ServicioTipoReduccion servicioTipoReduccion;

	@Autowired
	private ServicioColegioProvincia servicioColegioProvincia;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioOficinaLiquidadora620 servicioOficinaLiquidadora620;

	@Autowired
	private ServicioPais servicioPais;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ModeloTrafico modeloTrafico;

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

	public String inicio() {
		log.debug("inicio TraficoAjaxAction");
		return completarFormulario(true);
	}

	private String completarFormulario(boolean formularioInicial) {
		if (formularioInicial) {
			limpiarCamposSession();
		}
		log.info("completar formulario");
		return "altasMatriculacion";
	}

	@Override
	public String execute() throws Exception {
		log.debug("execute");
		return "altasMatriculacion";
	}

	public void recuperarMarcasCamPorTipoVehiculoYFechaDevengo() throws Throwable {
		log.debug("recuperarMarcasPorTipoVehiculoYFechaDevengo");
		String resultado = "";

		String tipoVehiculoSeleccionado = getServletRequest().getParameter("tipoVehiculoSeleccionado");
		String anioDevengo = getServletRequest().getParameter("anioDevengo");
		log.debug("tipoVehiculoSeleccionado=" + tipoVehiculoSeleccionado);
		log.debug("anioDevengo=" + anioDevengo);

		List<MarcaCamVO> marcas = servicioMarcaCam.listaMarcaCam(tipoVehiculoSeleccionado, null, anioDevengo + DiaUnoMesUno);
		if (marcas != null && !marcas.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (MarcaCamVO marca : marcas) {
				respuesta.append(marca.getDsMarca() + ";" + marca.getId().getCdMarca() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	/**
	 * Método para obtener porcentaje de reducción asociado al tipo de reducción seleccionado
	 * @throws Throwable
	 */
	public void recuperarPorcentajeReduccion() throws Throwable {
		log.debug("recuperarPorcentajeReduccion");
		String resultado = "";

		String tipoReduccionSeleccionado = getServletRequest().getParameter("tipoReduccionSeleccionado");
		log.debug("tipoReduccionSeleccionado=" + tipoReduccionSeleccionado);

		TipoReduccionVO reduccion = servicioTipoReduccion.getTipoReduccion(tipoReduccionSeleccionado);
		if (reduccion != null) {
			StringBuilder respuesta = new StringBuilder();
			respuesta.append(reduccion.getTipoReduccion() + ";" + reduccion.getPorcentajeReduccion());
			resultado = respuesta.substring(0, respuesta.length());
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarMunicipiosPorProvincia() throws Throwable {
		log.debug("recuperarMunicipiosPorProvincia");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + optionSelected);

		municipiosDto = servicioMunicipio.listaMunicipios(optionSelected);
		if (municipiosDto != null && municipiosDto.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (MunicipioDto mb : municipiosDto) {
				respuesta.append(mb.getNombre() + ";" + mb.getIdMunicipio() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarTipoViaPorProvincia() throws Throwable {
		log.debug("recuperarTipoViaPorProvincia");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + optionSelected);

		tipoVias = servicioTipoVia.listadoTipoVias(optionSelected);

		if (tipoVias != null && tipoVias.isEmpty()) {
			tipoVias = servicioTipoVia.listadoTipoVias(null);
		}

		if (tipoVias != null && tipoVias.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (TipoViaDto tipoVia : tipoVias) {
				respuesta.append(tipoVia.getNombre() + ";" + tipoVia.getIdTipoVia() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void obtenerEstacionesItvDeProvincia() throws Throwable {
		log.debug("obtenerEstacionesItvDeProvincia");

		String optionSelected = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + optionSelected);

		String idProvincia = ProvinciasEstacionITV.getIdProvincia(optionSelected);
		List<EstacionITVBean> estacionesItv = servicioEstacionITV.getEstacionesItv(idProvincia);

		String resultado = transformarEnStringEstacionesItv(estacionesItv);
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	/**
	 * Método para obtener los colegios por provincia
	 * @throws Throwable
	 */
	public void recuperarColegiosPorProvincia() throws Throwable {
		log.debug("recuperarColegiosPorProvincia");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + optionSelected);

		colegios = servicioColegioProvincia.listadoColegio(optionSelected);

		if (colegios != null && colegios.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (ColegioDto colegio : colegios) {
				respuesta.append(colegio.getNombre() + ";" + colegio.getColegio() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	/**
	 * Método para obtener las jefaturas por provincia
	 * @throws Throwable
	 */
	public void recuperarJefaturasPorProvincia() throws Throwable {
		log.debug("recuperarJefaturasPorProvincia");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + optionSelected);

		jefaturas = servicioJefaturaTrafico.listadoJefaturas(optionSelected);

		if (jefaturas != null && jefaturas.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (JefaturaTraficoDto jefatura : jefaturas) {
				respuesta.append(jefatura.getDescripcion() + ";" + jefatura.getJefaturaProvincial() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarPueblosPorMunicipio() throws Throwable {
		log.debug("recuperarPueblosPorMunicipio");
		String resultado = "";

		String idProvincia = getServletRequest().getParameter("provinciaSeleccionado");
		log.debug("provinciaSeleccionada=" + idProvincia);
		String idMunicipio = getServletRequest().getParameter("municipioSeleccionado");
		log.debug("municipioSeleccionado=" + idMunicipio);

		pueblos = servicioPueblo.listaPueblos(idProvincia, idMunicipio);
		if (pueblos != null && pueblos.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (PuebloDto pu : pueblos) {
				respuesta.append(pu.getPueblo() + ";" + pu.getPueblo() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarPueblosDGTPorMunicipio() throws Throwable {
		log.debug("recuperarPueblosDGTPorMunicipio");
		String resultado = "";

		String idProvincia = getServletRequest().getParameter("provinciaSeleccionado");
		log.debug("provinciaSeleccionada=" + idProvincia);
		String idMunicipio = getServletRequest().getParameter("municipioSeleccionado");
		log.debug("municipioSeleccionado=" + idMunicipio);

		localidadDGT = servicioLocalidadDgt.listaLocalidades(idProvincia + idMunicipio);
		if (localidadDGT != null && localidadDGT.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (String localidad : localidadDGT) {
				respuesta.append(localidad + ";" + localidad + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarCodigoPostalPorMunicipio() throws Throwable {
		log.debug("recuperarCodigoPostalPorMunicipio");

		String idProvincia = getServletRequest().getParameter("provinciaSeleccionado");
		log.debug("provinciaSeleccionada=" + idProvincia);
		String idMunicipio = getServletRequest().getParameter("municipioSeleccionado");
		log.debug("municipioSeleccionado=" + idMunicipio);

		codigoPostal = servicioDireccion.obtenerCodigoPostal(idMunicipio, idProvincia);
		String resultado = "";
		if (codigoPostal != null) {
			resultado = codigoPostal;
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarNombreViasPorProvinciaMunicipioYTipoVia() throws Throwable {
		log.debug("recuperarNombreViasPorProvinciaMunicipioYTipoVia");
		String resultado = "";

		String idProvincia = getServletRequest().getParameter("provinciaSeleccionada");
		log.debug("provinciaSeleccionada=" + idProvincia);
		String idMunicipio = getServletRequest().getParameter("municipioSeleccionado");
		log.debug("municipioSeleccionado=" + idMunicipio);
		String idTipoVia = getServletRequest().getParameter("tipoViaSeleccionado");
		log.debug("tipoViaSelecionado=" + idTipoVia);

		nombreVias = servicioVia.listadoVias(idProvincia, idMunicipio, idTipoVia);
		if (nombreVias != null && nombreVias.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (ViaDto nombreVia : nombreVias) {
				respuesta.append(nombreVia.getVia() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarModelosPorMarca() throws Throwable {
		log.debug("recuperarModelosPorMarca");
		String resultado = "";

		String idMarca = getServletRequest().getParameter("marcaSeleccionada");
		String tipoVehiculoSeleccionado = getServletRequest().getParameter("tipoVehiculoSeleccionado");
		String anioDevengo = getServletRequest().getParameter("anioDevengo");
		log.debug("marcaSeleccionada=" + idMarca);
		log.debug("tipoVehiculoSeleccionado=" + tipoVehiculoSeleccionado);
		log.debug("anioDevengo=" + anioDevengo);

		modelos = servicioModeloCam.listaModeloCam(tipoVehiculoSeleccionado, anioDevengo + DiaUnoMesUno, idMarca, null);
		if (modelos != null && !modelos.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (ModeloCamDto modelo : modelos) {
				respuesta.append(modelo.getDsModVeh() + ";" + modelo.getCdModVeh() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void sitexComprobarCTITPrevio() throws Throwable {
		log.debug("sitexComprobarCTITPrevio");

		String matricula = getServletRequest().getParameter("matricula");

		boolean result = servicioTramiteTraficoTransmision.sitexComprobarCTITPrevio(matricula);

		sitexCTITPrevio = result ? "OK" : "KO";

		String resultado = sitexCTITPrevio;

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarFabricantes() throws Throwable {
		log.debug("recuperarFabricantes");
		String resultado = "";

		String codigoMarca = getServletRequest().getParameter("codigoMarca");

		nombreFabricantes = servicioFabricante.listaFabricantesPorMarca(codigoMarca);
		if (nombreFabricantes != null && !nombreFabricantes.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (FabricanteDto f : nombreFabricantes) {
				sb.append(f.getFabricante().toUpperCase()).append(TOKEN_SEPARADOR);
			}
			resultado = sb.substring(0, sb.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarFabricantesInactivos() throws Throwable {
		log.debug("recuperarFabricantesInactivos");
		String resultado = "";

		nombreFabricantes = servicioFabricante.listaFabricantesInactivos();
		if (nombreFabricantes != null && nombreFabricantes.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (FabricanteDto f : nombreFabricantes) {
				sb.append(f.getFabricante().toUpperCase()).append(TOKEN_SEPARADOR);
			}
			resultado = sb.substring(0, sb.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarComentariosTasa() throws Throwable {
		log.debug("recuperarcomentariosTasa");

		String codigoTasa = getServletRequest().getParameter("codigoTasa");

		String comentariosTasa = null;
		TasaDto tasaDto = servicioTasa.getTasaCodigoTasa(codigoTasa);
		if (tasaDto != null) {
			comentariosTasa = tasaDto.getComentarios();
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(comentariosTasa != null ? comentariosTasa : CADENA_VACIA);
	}

	public void recuperarMarcas() throws Throwable {
		log.debug("recuperarMarcas");
		String resultado = "";

		String nombreMarca = getServletRequest().getParameter("nombreMarca") + "%";
		String versionMatriculacion = getServletRequest().getParameter("versionMatriculacion");
		boolean versionMatw = false;

		if (versionMatriculacion != null && versionMatriculacion.equalsIgnoreCase("MATW")) {
			versionMatw = true;
		}

		listaMarcas = servicioMarcaDgt.listaMarcas(nombreMarca, versionMatw);
		if (listaMarcas != null && listaMarcas.size() > 0) {
			StringBuilder resultadoA = new StringBuilder();
			StringBuilder resultadoB = new StringBuilder();

			for (MarcaDgtDto m : listaMarcas) {
				if (m.getMarca() != null && !m.getMarca().isEmpty() && m.getCodigoMarca() != null) {
					resultadoA.append(m.getMarca().replaceAll(",", " ")).append(TOKEN_SEPARADOR);
					resultadoB.append(m.getCodigoMarca()).append(TOKEN_SEPARADOR);
				}
			}
			resultado = resultadoA.substring(0, resultadoA.length() - 2) + "[#]" + resultadoB.substring(0, resultadoB.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarCriteriosConstruccion() throws Throwable {
		log.debug("recuperarCriteriosConstruccion");
		String resultado = "";

		String tipoVehiculo = getServletRequest().getParameter("tipoVehiculo");

		listaCriteriosConstruccion = servicioCriterioConstruccion.listadoCriterioConstruccion(tipoVehiculo);
		if (listaCriteriosConstruccion != null && listaCriteriosConstruccion.size() > 0) {
			StringBuilder indices = new StringBuilder();
			StringBuilder valores = new StringBuilder();
			for (CriterioConstruccionDto cc : listaCriteriosConstruccion) {
				indices.append(cc.getIdCriterioConstruccion()).append(TOKEN_SEPARADOR);
				valores.append(cc.getDescripcion()).append(TOKEN_SEPARADOR);
			}
			resultado = indices.substring(0, indices.length() - 2) + "[#]" + valores.substring(0, valores.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarCriteriosUtilizacion() throws Throwable {
		log.debug("recuperarCriteriosUtilizacion");
		String resultado = "";

		String tipoVehiculo = getServletRequest().getParameter("tipoVehiculo");

		listaCriteriosUtilizacion = servicioCriterioUtilizacion.listadoCriterioUtilizacion(tipoVehiculo);
		if (listaCriteriosUtilizacion != null && listaCriteriosUtilizacion.size() > 0) {
			StringBuilder indices = new StringBuilder();
			StringBuilder valores = new StringBuilder();
			for (CriterioUtilizacionDto cc : listaCriteriosUtilizacion) {
				indices.append(cc.getIdCriterioUtilizacion()).append(TOKEN_SEPARADOR);
				valores.append(cc.getDescripcion()).append(TOKEN_SEPARADOR);
			}
			resultado = indices.substring(0, indices.length() - 2) + "[#]" + valores.substring(0, valores.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarHomologacionesEU() throws Throwable {
		log.debug("recuperarHomologacionesEU");
		String resultado = "";

		String criterioConstruccion = getServletRequest().getParameter("criterioConstruccion");

		listaHomologaciones = servicioDirectivaCee.listadoDirectivaCee(criterioConstruccion);
		if (listaHomologaciones != null && listaHomologaciones.size() > 0) {
			StringBuilder indices = new StringBuilder();
			StringBuilder valores = new StringBuilder();

			for (DirectivaCeeDto dir : listaHomologaciones) {
				indices.append(dir.getIdDirectivaCEE()).append(TOKEN_SEPARADOR);
				valores.append(dir.getDescripcion()).append(TOKEN_SEPARADOR);
			}

			resultado = indices.substring(0, indices.length() - 2) + "[#]" + valores.substring(0, valores.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarCodigosTasaLibresPorTipoTasa() throws Throwable {
		log.debug("recuperarCodigosTasaPorTipoTasa");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("tipoTasaSeleccionado");
		String idContratoTramite = getServletRequest().getParameter("idContrato");
		Long idContrato = null;
		if (idContratoTramite != null && !"".equals(idContratoTramite) && !"null".equals(idContratoTramite)) {
			idContrato = Long.valueOf(idContratoTramite);
		} else {
			idContrato = utilesColegiado.getIdContratoSession();
		}
		log.debug("tipoTasaSeleccionado=" + optionSelected);
		log.debug("idContrato=" + idContratoTramite);

		String gestionTasaAlmacen = gestorPropiedades.valorPropertie("gestion.almacen.tasa");
		if ("SI".equals(gestionTasaAlmacen)) {
			if(TipoTasaDGT.UnoUno.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoDos.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasMatwContrato(idContrato, optionSelected);
			} else if (TipoTasaDGT.UnoCinco.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoSeis.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoDos.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasCtitContrato(idContrato, optionSelected);
			} else if (TipoTasaDGT.CuatroUno.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasBajaContrato(idContrato, optionSelected);
			} else if (TipoTasaDGT.CuatroCuatro.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasDuplicadoContrato(idContrato, optionSelected);
			} else if (TipoTasaDGT.CuatroCinco.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasPermIntContrato(idContrato, optionSelected);
			}
		} else {
			tasas = servicioTasa.obtenerTasasContrato(idContrato, optionSelected);
		}
		if (tasas != null && !tasas.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (TasaDto tasa : tasas) {
				respuesta.append(tasa.getCodigoTasa() + ";" + tasa.getCodigoTasa()).append(TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarCodTasaLibresPorTipoTasa() throws Throwable {
		log.debug("recuperarCodigosTasaPorTipoTasa");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("tipoTasaSeleccionado");
		String idContratoTramite = getServletRequest().getParameter("idContrato");
		Long idContrato = null;
		if (idContratoTramite != null && !"".equals(idContratoTramite) && !"null".equals(idContratoTramite)) {
			idContrato = Long.valueOf(idContratoTramite);
		} else {
			idContrato = utilesColegiado.getIdContratoSession();
		}
		log.debug("tipoTasaSeleccionado=" + optionSelected);
		log.debug("idContrato=" + idContratoTramite);
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie("gestion.almacen.tasa");
		if ("SI".equals(gestionTasaAlmacen)) {
			if(TipoTasaDGT.UnoUno.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoDos.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasMatwContrato(idContrato, optionSelected);
			}else if(TipoTasaDGT.UnoCinco.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoSeis.getValorEnum().equals(optionSelected) ||
					TipoTasaDGT.UnoDos.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasCtitContrato(idContrato, optionSelected);
			}else if(TipoTasaDGT.CuatroUno.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasBajaContrato(idContrato, optionSelected);
			}else if(TipoTasaDGT.CuatroCuatro.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasDuplicadoContrato(idContrato, optionSelected);
			}else if(TipoTasaDGT.CuatroCinco.getValorEnum().equals(optionSelected)) {
				tasas = servicioTasa.obtenerTasasPermIntContrato(idContrato, optionSelected);
			}
		}else {
			tasas = servicioTasa.obtenerTodasTasasContrato(idContrato, optionSelected);
		}

		if (tasas != null && !tasas.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (TasaDto tasa : tasas) {
				respuesta.append(tasa.getCodigoTasa() + ";");
			}
			resultado = respuesta.substring(0, respuesta.length() - 1);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarDatosVehiculo620() throws Throwable {
		log.debug("recuperarDatosVehiculo620");
		String resultado = "";

		String idTipoVehiculo = getServletRequest().getParameter("idTipoVehiculo");
		String anioDevengo = getServletRequest().getParameter("anioDevengo");
		String marcaCamSeleccionada = getServletRequest().getParameter("marcaCamSeleccionada");
		String modeloCamSeleccionado = getServletRequest().getParameter("modeloCamSeleccionado");
		log.debug("idTipoVehiculo=" + idTipoVehiculo);
		log.debug("anioDevengo=" + anioDevengo);
		log.debug("marcaCamSeleccionada=" + marcaCamSeleccionada);
		log.debug("modeloCamSeleccionado=" + modeloCamSeleccionado);

		datosVehiculo = servicioModeloCam.listaModeloCam(idTipoVehiculo, anioDevengo + DiaUnoMesUno, marcaCamSeleccionada, modeloCamSeleccionado);
		if (datosVehiculo != null && datosVehiculo.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (ModeloCamDto modelo : datosVehiculo) {
				if (modelo.getvPotFisc() == null) {
					modelo.setvPotFisc(new BigDecimal(0));
				}
				if (modelo.getvCilindr() == null) {
					modelo.setvCilindr(new BigDecimal(0));
				}
				if (modelo.getNumCilin() == null) {
					modelo.setNumCilin(new BigDecimal(0));
				}
				respuesta.append(modelo.getvPotFisc() + ";;" + modelo.getvCilindr() + ";;" + modelo.getNumCilin() + ";;" + modelo.getDsTecNic() + ";;" + modelo.getvPreVehi() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarOficinaLiquidadoraPorMunicipio() throws Throwable {
		log.debug("recuperarOficinaLiquidadoraPorMunicipio");
		String resultado = "";

		String optionSelected = getServletRequest().getParameter("municipioSeleccionado");
		log.debug("municipioSeleccionado=" + optionSelected);

		oficinasLiquidadoras = servicioOficinaLiquidadora620.listadoOficinasLiquidadoras(optionSelected);
		if (oficinasLiquidadoras != null && oficinasLiquidadoras.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (OficinaLiquidadora620Dto oficina : oficinasLiquidadoras) {
				respuesta.append(oficina.getNombreOficinaLiq() + ";" + oficina.getOficinaLiquidadora()).append(TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	private String transformarEnStringEstacionesItv(List<EstacionITVBean> options) {
		String respuesta = "";

		for (EstacionITVBean estacionITV : options) {
			respuesta = respuesta + estacionITV.getEstacionITV() + ";" + estacionITV.getMunicipio() + TOKEN_SEPARADOR;
		}

		if (respuesta.length() >= 2)
			respuesta = respuesta.substring(0, respuesta.length() - 2);
		log.debug("respuesta transformarEnStringEstacionesItv " + respuesta);
		return respuesta;
	}

	public void confirmarValidezMarcas() throws Throwable {
		log.debug("confirmarValidezMarcas");
		boolean versionMatw = false;
		String resultado = "";

		String nombreMarca = getServletRequest().getParameter("nombreMarca");
		String marcasDgt = getServletRequest().getParameter("marcasDgt");

		if (marcasDgt != null && marcasDgt.equalsIgnoreCase("MATW")) {
			versionMatw = true;
		}

		if (nombreMarca != null && !nombreMarca.isEmpty()) {
			listaMarcas = servicioMarcaDgt.listaMarcas(nombreMarca, versionMatw);
			if (listaMarcas != null && listaMarcas.size() > 0) {
				StringBuilder resultadoA = new StringBuilder();
				StringBuilder resultadoB = new StringBuilder();

				for (MarcaDgtDto m : listaMarcas) {
					if (m.getMarca() != null && !m.getMarca().isEmpty() && m.getCodigoMarca() != null) {
						resultadoA.append(m.getMarca().replaceAll(",", " ")).append(TOKEN_SEPARADOR);
						resultadoB.append(m.getCodigoMarca()).append(TOKEN_SEPARADOR);
					}
				}
				resultado = resultadoA.substring(0, resultadoA.length() - 2) + "[#]" + resultadoB.substring(0, resultadoB.length() - 2);
			}
		} else {
			resultado = "[#]";
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}
	
	public void confirmarValidezPaisesProcedencia() throws Throwable {
		log.debug("confirmarValidezPaisesProcedencia");
		String resultado = "";

		String nombrePais = getServletRequest().getParameter("nombrePais");

		if (StringUtils.isNotBlank(nombrePais)) {
			paises = servicioPais.paisesPorNombre(nombrePais);
			if (paises != null && !paises.isEmpty()) {
				StringBuilder resultadoA = new StringBuilder();
				StringBuilder resultadoB = new StringBuilder();

				for (PaisDto pa : paises) {
					if (StringUtils.isNotBlank(pa.getNombre()) && StringUtils.isNotBlank(pa.getSigla3())) {
						resultadoA.append(pa.getNombre()).append(TOKEN_SEPARADOR);
						resultadoB.append(pa.getSigla3()).append(TOKEN_SEPARADOR);
					}
				}
				resultado = resultadoA.substring(0, resultadoA.length() - 2) + "[#]"
						+ resultadoB.substring(0, resultadoB.length() - 2);
			}
		} else {
			resultado = "[#]";
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}
	
	public void recuperarPaisesProcedencia() throws Throwable {
		log.debug("recuperarPaisesProcedencia");
		String resultado = "";

		String nombrePais = getServletRequest().getParameter("nombrePais") + "%";

		paises = servicioPais.paisesPorNombre(nombrePais);
		if (paises != null && !paises.isEmpty()) {

			StringBuilder resultadoA = new StringBuilder();
			StringBuilder resultadoB = new StringBuilder();
			for (PaisDto pa : paises) {
				if (StringUtils.isNotBlank(pa.getNombre()) && StringUtils.isNotBlank(pa.getSigla3())) {
					resultadoA.append(pa.getNombre()).append(TOKEN_SEPARADOR);
					resultadoB.append(pa.getSigla3()).append(TOKEN_SEPARADOR);
				}
			}
			resultado = resultadoA.substring(0, resultadoA.length() - 2) + "[#]"
					+ resultadoB.substring(0, resultadoB.length() - 2);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		log.debug(resultado);
		out.print(resultado.toUpperCase());
	}

	/**
	 * Obtiene los procesos asociados a un patrón de procesos
	 */
	public void getProcesosPatron() {

		String patron = getServletRequest().getParameter("patron");
		String procesos = "";
		String procesosFormateados = "";
		procesos = gestorPropiedades.valorPropertie("procesos.patron." + patron);
		if (procesos != null && !procesos.equals("")) {
			procesosFormateados = procesos.replace(",", ", ");
		} else {
			procesosFormateados = "TODOS";
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			out.print(procesosFormateados);
		} catch (IOException e) {
			log.error("Error al obtener la lista de procesos asociadas al patrón.", e);
		}

	}

	/**
	 * Método para obtener la fecha de caducidad itv de un vehiculo
	 * @throws Throwable
	 */
	public void calcularFechaCaducidadItv() throws Throwable {

		String resultado = null;
		log.debug("calcularFechaCaducidadItv");

		String numExpediente = getServletRequest().getParameter("numExpediente");
		log.debug("numExpediente = " + numExpediente);

		DaoAjax daoAjax = new DaoAjax();
		RespuestaGenerica respuestaGenerica = daoAjax.calcularFechaCaducidadItv(numExpediente);

		// Recupera información de respuesta:
		BigDecimal pCodeVehiculo = (BigDecimal) respuestaGenerica.getParametro(ConstantesPQ.P_CODE);
		String sqlErrmVehiculo = (String) respuestaGenerica.getParametro(ConstantesPQ.P_SQLERRM);
		Timestamp fechaCaducidadItv = (Timestamp) respuestaGenerica.getParametro("P_FECHA_ITV");

		if ((!ConstantesPQ.pCodeOk.equals(pCodeVehiculo))) {
			resultado = sqlErrmVehiculo;
		} else {
			Fecha fecha = utilesFecha.getFechaFracionada(fechaCaducidadItv);
			resultado = fecha.getDia() + TOKEN_SEPARADOR + fecha.getMes() + TOKEN_SEPARADOR + fecha.getAnio();
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	public void recuperarTasasFacturacion() throws Throwable {
		log.debug("recuperarTasas");
		String resultado = null;

		String tipoTasa = getServletRequest().getParameter("tipoTasa");

		codigosTasasPegatina = new ArrayList<TasaPegatinaDto>();
		codigosTasasPegatina = servicioPersistenciaTasaPegatina.obtenerTasasPegatinaContrato(utilesColegiado.getIdContratoSession(), tipoTasa);

		if (codigosTasasPegatina != null && codigosTasasPegatina.size() > 0) {
			resultado = transformarEnStringCodigoTasas(codigosTasasPegatina);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	private String transformarEnStringCodigoTasas(List<TasaPegatinaDto> options) {
		String respuesta = "";
		for (TasaPegatinaDto f : options) {
			respuesta = respuesta + f.getCodigoTasa() + TOKEN_SEPARADOR;
		}

		if (respuesta.length() >= 2)
			respuesta = respuesta.substring(0, respuesta.length() - 2);
		log.debug("respuesta transformarEnStringCodigoTasas " + respuesta);
		return respuesta;
	}

	public void recuperarDescFuncion() throws Throwable {
		log.debug("recuperar descripcion funcion");

		String codigoAplicacion = getServletRequest().getParameter("codigoAplicacion");

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		MenuRepository menuRepository = (MenuRepository) Claves.getObjetoDeContextoSesion(Claves.CLAVE_ARBOL);
		if (menuRepository != null) {
			MenuComponent modulo = menuRepository.getMenu(codigoAplicacion);
			if (modulo != null && modulo.getMenuComponents() != null) {
				StringBuilder sb = new StringBuilder();
				for (MenuComponent mc : menuRepository.getMenu(codigoAplicacion).getMenuComponents()) {
					recorreMenus(sb, mc);
				}
				if (sb.length() > 0) {
					out.print(sb.substring(0, sb.length() - 2));
				}
			}
		}
		out.close();
	}

	private void recorreMenus(StringBuilder sb, MenuComponent mc) {
		if (mc.getLocation() != null && !mc.getLocation().isEmpty()) {
			sb.append(mc.getName()).append(";").append(mc.getTitle()).append(TOKEN_SEPARADOR);
		}
		if (mc.getComponents() != null) {
			for (Object mcAux : mc.getComponents()) {
				recorreMenus(sb, (MenuComponent) mcAux);
			}
		}
	}

	public void recuperarPaisesBajasTelematicas() throws Throwable {
		log.debug("recuperarPaisesBajasTelematicas");
		String resultado = "";

		BigDecimal tipoPais = new BigDecimal(new String(getServletRequest().getParameter("tipoPaisBajaSeleccionado")));

		log.debug("tipoPaisBajaSeleccionado=" + tipoPais);
		paises = servicioPais.listaPaises(tipoPais);
		if (paises!= null && paises.size() > 0) {
			StringBuilder respuesta = new StringBuilder();
			for (PaisDto pa : paises) {
				respuesta.append(pa.getIdPais() + TOKEN_SEPARADOR + pa.getNombre() + ";");
			}
			resultado = respuesta.substring(0, respuesta.length() - 1);
		}

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		log.debug(resultado);
		out.print(resultado);
	}

	public List<MunicipioDto> getMunicipiosDto() {
		return municipiosDto;
	}

	public List<TipoViaDto> getTipoVias() {
		return tipoVias;
	}

	public List<MarcaDgtDto> getListaMarcas() {
		return listaMarcas;
	}

	public String getSitexCTITPrevio() {
		return sitexCTITPrevio;
	}

	public List<TasaPegatinaDto> getCodigosTasasPegatina() {
		return codigosTasasPegatina;
	}

	public List<String> getLocalidadDGT() {
		return localidadDGT;
	}

	public List<CriterioConstruccionDto> getListaCriteriosConstruccion() {
		return listaCriteriosConstruccion;
	}

	public List<CriterioUtilizacionDto> getListaCriteriosUtilizacion() {
		return listaCriteriosUtilizacion;
	}

	public List<PuebloDto> getPueblos() {
		return pueblos;
	}

	public List<DirectivaCeeDto> getListaHomologaciones() {
		return listaHomologaciones;
	}

	public List<JefaturaTraficoDto> getJefaturas() {
		return jefaturas;
	}

	public List<FabricanteDto> getNombreFabricantes() {
		return nombreFabricantes;
	}

	public List<ViaDto> getNombreVias() {
		return nombreVias;
	}

	public List<ModeloCamDto> getDatosVehiculo() {
		return datosVehiculo;
	}

	public List<ModeloCamDto> getModelos() {
		return modelos;
	}

	public List<ColegioDto> getColegios() {
		return colegios;
	}

	public List<OficinaLiquidadora620Dto> getOficinasLiquidadoras() {
		return oficinasLiquidadoras;
	}

	public void setOficinasLiquidadoras(List<OficinaLiquidadora620Dto> oficinasLiquidadoras) {
		this.oficinasLiquidadoras = oficinasLiquidadoras;
	}

	public List<TasaDto> getTasas() {
		return tasas;
	}

	public List<PaisDto> getPaises() {
		return paises;
	}

	/* *********************************************************************** */
	/* SERVICIOS ************************************************************* */
	/* *********************************************************************** */

	public ServicioFabricante getServicioFabricante() {
		return servicioFabricante;
	}

	public void setServicioFabricante(ServicioFabricante servicioFabricante) {
		this.servicioFabricante = servicioFabricante;
	}

	public ServicioCriterioConstruccion getServicioCriterioConstruccion() {
		return servicioCriterioConstruccion;
	}

	public void setServicioCriterioConstruccion(ServicioCriterioConstruccion servicioCriterioConstruccion) {
		this.servicioCriterioConstruccion = servicioCriterioConstruccion;
	}

	public ServicioCriterioUtilizacion getServicioCriterioUtilizacion() {
		return servicioCriterioUtilizacion;
	}

	public void setServicioCriterioUtilizacion(ServicioCriterioUtilizacion servicioCriterioUtilizacion) {
		this.servicioCriterioUtilizacion = servicioCriterioUtilizacion;
	}

	public ServicioLocalidadDgt getServicioLocalidadDgt() {
		return servicioLocalidadDgt;
	}

	public void setServicioLocalidadDgt(ServicioLocalidadDgt servicioLocalidadDgt) {
		this.servicioLocalidadDgt = servicioLocalidadDgt;
	}

	public ServicioPersistenciaTasaPegatina getServicioPersistenciaTasaPegatina() {
		return servicioPersistenciaTasaPegatina;
	}

	public void setServicioPersistenciaTasaPegatina(ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina) {
		this.servicioPersistenciaTasaPegatina = servicioPersistenciaTasaPegatina;
	}

	public ServicioMarcaDgt getServicioMarcaDgt() {
		return servicioMarcaDgt;
	}

	public void setServicioMarcaDgt(ServicioMarcaDgt servicioMarcaDgt) {
		this.servicioMarcaDgt = servicioMarcaDgt;
	}

	public ServicioMunicipio getServicioMunicipio() {
		return servicioMunicipio;
	}

	public void setServicioMunicipio(ServicioMunicipio servicioMunicipio) {
		this.servicioMunicipio = servicioMunicipio;
	}

	public ServicioTipoVia getServicioTipoVia() {
		return servicioTipoVia;
	}

	public void setServicioTipoVia(ServicioTipoVia servicioTipoVia) {
		this.servicioTipoVia = servicioTipoVia;
	}

	public ServicioDirectivaCee getServicioDirectivaCee() {
		return servicioDirectivaCee;
	}

	public void setServicioDirectivaCee(ServicioDirectivaCee servicioDirectivaCee) {
		this.servicioDirectivaCee = servicioDirectivaCee;
	}

	public ServicioPueblo getServicioPueblo() {
		return servicioPueblo;
	}

	public void setServicioPueblo(ServicioPueblo servicioPueblo) {
		this.servicioPueblo = servicioPueblo;
	}

	public ServicioJefaturaTrafico getServicioJefaturaTrafico() {
		return servicioJefaturaTrafico;
	}

	public void setServicioJefaturaTrafico(ServicioJefaturaTrafico servicioJefaturaTrafico) {
		this.servicioJefaturaTrafico = servicioJefaturaTrafico;
	}

	public ServicioMarcaCam getServicioMarcaCam() {
		return servicioMarcaCam;
	}

	public void setServicioMarcaCam(ServicioMarcaCam servicioMarcaCam) {
		this.servicioMarcaCam = servicioMarcaCam;
	}

	public ServicioModeloCam getServicioModeloCam() {
		return servicioModeloCam;
	}

	public void setServicioModeloCam(ServicioModeloCam servicioModeloCam) {
		this.servicioModeloCam = servicioModeloCam;
	}

	public ServicioVia getServicioVia() {
		return servicioVia;
	}

	public void setServicioVia(ServicioVia servicioVia) {
		this.servicioVia = servicioVia;
	}

	public ServicioColegioProvincia getServicioColegioProvincia() {
		return servicioColegioProvincia;
	}

	public void setServicioColegioProvincia(ServicioColegioProvincia servicioColegioProvincia) {
		this.servicioColegioProvincia = servicioColegioProvincia;
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}

	public ServicioTramiteTraficoTransmision getServicioTramiteTraficoTransmision() {
		return servicioTramiteTraficoTransmision;
	}

	public void setServicioTramiteTraficoTransmision(ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision) {
		this.servicioTramiteTraficoTransmision = servicioTramiteTraficoTransmision;
	}

	public ServicioOficinaLiquidadora620 getServicioOficinaLiquidadora620() {
		return servicioOficinaLiquidadora620;
	}

	public void setServicioOficinaLiquidadora620(ServicioOficinaLiquidadora620 servicioOficinaLiquidadora620) {
		this.servicioOficinaLiquidadora620 = servicioOficinaLiquidadora620;
	}

	public ServicioTasa getServicioTasa() {
		return servicioTasa;
	}

	public void setServicioTasa(ServicioTasa servicioTasa) {
		this.servicioTasa = servicioTasa;
	}

	public ServicioPais getServicioPais() {
		return servicioPais;
	}

	public void setServicioPais(ServicioPais servicioPais) {
		this.servicioPais = servicioPais;
	}

	/* *************************************************************** */
	/* MODELOS ******************************************************* */
	/* *************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

}