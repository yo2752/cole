package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.TipoCuentaBancaria;
import org.gestoresmadrid.core.registradores.constantes.ConstantesRegistradores;
import org.gestoresmadrid.core.registradores.model.enumerados.Aceptacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.Inmatriculada;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoFormaPago;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoReunionJunta;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TiposDuracion;
import org.gestoresmadrid.core.registradores.model.enumerados.TiposMedio;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.registradores.clases.adicionales.TipoViaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.enums.CapitaniaMaritima;
import org.gestoresmadrid.oegam2comun.registradores.enums.CausaCancelacion;
import org.gestoresmadrid.oegam2comun.registradores.enums.ClaseDerecho;
import org.gestoresmadrid.oegam2comun.registradores.enums.ClasePropiedadIndustrial;
import org.gestoresmadrid.oegam2comun.registradores.enums.ClasePropiedadIntelectual;
import org.gestoresmadrid.oegam2comun.registradores.enums.DocumentType;
import org.gestoresmadrid.oegam2comun.registradores.enums.EstadoBuque;
import org.gestoresmadrid.oegam2comun.registradores.enums.ExtensionDerecho;
import org.gestoresmadrid.oegam2comun.registradores.enums.ListaMaritima;
import org.gestoresmadrid.oegam2comun.registradores.enums.RegistroAdministrativo;
import org.gestoresmadrid.oegam2comun.registradores.enums.SituacionJuridica;
import org.gestoresmadrid.oegam2comun.registradores.enums.SubClasePropiedadIndustrial;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCategoria;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCesion;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoClausula;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoComision;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoContrato;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCuadroAmortizacion;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoDefecto;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoInteresReferencia;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoIntervencion;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoMoneda;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoOperacion;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoOperacionRegistral;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoOtrosImportes;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoPacto;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoRegistroAdministrativo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioGrupoBien;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOperacionRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPaisRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTipoCargo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.GrupoBienDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.NotarioRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OperacionRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PaisRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TipoCargoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class UtilesVistaRegistradores {

	private static ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private static UtilesVistaRegistradores utilesVistaRegistradores;

	private static boolean iniciado;

	private List<OperacionRegistroDto> operacionesPropiedad;
	private List<TipoCargoDto> tipoCargos;
	private List<ProvinciaDto> provinciasBienes;
	private List<PaisRegistroDto> listPaisRegistro;
	private List<TipoViaRegistro> listTipoViaRegistro;

	private List<GrupoBienDto> listGrupoBien;
	private List<RegistroDto> listRegistroMercantil;
	private List<RegistroDto> listRegistroBienesMuebles;
	private List<RegistroDto> listRegistroPropiedad;
	private static Map<String, String> mapaProvincias;
	private static Map<String, Map<String, String>> listMunicipios;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioRegistro servicioRegistro;

	@Autowired
	ServicioPais servicioPais;

	@Autowired
	ServicioPaisRegistro servicioPaisRegistro;

	@Autowired
	ServicioFinanciador servicioFinanciador;

	@Autowired
	private ServicioTipoCargo servicioTipoCargo;

	@Autowired
	private ServicioGrupoBien servicioGrupoBien;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private UtilesVistaRegistradores() {
		super();
	}

	public static UtilesVistaRegistradores getInstance() {
		if (utilesVistaRegistradores == null) {
			utilesVistaRegistradores = new UtilesVistaRegistradores();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaRegistradores);
		}
		return utilesVistaRegistradores;
	}

	private void iniciarCombos() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "iniciarCombos");
		try {
			ServicioOperacionRegistro servicioOperacionRegistro = ContextoSpring.getInstance().getBean(ServicioOperacionRegistro.class);
			if (operacionesPropiedad == null) {
				operacionesPropiedad = servicioOperacionRegistro.getOperacionesRegistro();
				if (operacionesPropiedad == null) {
					operacionesPropiedad = Collections.emptyList();
				}
			}
			if (tipoCargos == null) {
				tipoCargos = servicioTipoCargo.getTipoCargo();
				if (tipoCargos == null) {
					tipoCargos = Collections.emptyList();
				}
			}
			if (provinciasBienes == null) {
				provinciasBienes = servicioProvincia.getListaProvincias();
				if (provinciasBienes == null) {
					provinciasBienes = Collections.emptyList();
				}
			}
			cargarListaMunicipios();

			iniciado = true;
		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Error al obtener la conexion");
		}
	}

	public List<PaisRegistroDto> getListPaisRegistro() {
		if (listPaisRegistro == null) {
			listPaisRegistro = servicioPaisRegistro.getPaisesRegistro();
		}
		return listPaisRegistro;
	}

	public List<TipoViaRegistro> getListTipoViaRegistro() {
		if (listTipoViaRegistro == null) {
			List<TipoViaDto> tipoVias = servicioTipoVia.listadoTipoVias(null);
			listTipoViaRegistro = new ArrayList<TipoViaRegistro>();
			for (TipoViaDto tipoVia : tipoVias) {
				TipoViaRegistro tipo = new TipoViaRegistro();
				tipo.setKey(tipoVia.getIdTipoVia());
				tipo.setName(tipoVia.getNombre());
				listTipoViaRegistro.add(tipo);
			}
		}
		return listTipoViaRegistro;
	}

	public void setListTipoViaRegistro(List<TipoViaRegistro> listTipoViaRegistro) {
		this.listTipoViaRegistro = listTipoViaRegistro;
	}

	public List<IntervinienteRegistroDto> getListFinanciadores() {
		List<IntervinienteRegistroDto> listFinanciadores = servicioFinanciador.getFinanciadoresColegiado(utilesColegiado.getNumColegiadoSession());
		if (listFinanciadores == null || listFinanciadores.isEmpty()) {
			listFinanciadores = Collections.emptyList();
		}
		return listFinanciadores;
	}

	public List<GrupoBienDto> getListGrupoBien() {
		if (listGrupoBien == null) {
			listGrupoBien = servicioGrupoBien.getListaGrupoBien();
		}
		return listGrupoBien;
	}

	public List<RegistroDto> getListRegistroMercantil() {
		if (listRegistroMercantil == null) {
			listRegistroMercantil = servicioRegistro.getRegistro(null, "RM");
		}
		return listRegistroMercantil;
	}

	public List<RegistroDto> getListRegistroPropiedad() {
		if (listRegistroPropiedad == null) {
			listRegistroPropiedad = servicioRegistro.getRegistro(null, "RP");
		}
		return listRegistroPropiedad;
	}

	public List<RegistroDto> getListRegistroBienesMuebles() {
		if (listRegistroBienesMuebles == null) {
			listRegistroBienesMuebles = servicioRegistro.getRegistro(null, "RB");
		}
		return listRegistroBienesMuebles;
	}

	public List<RegistroDto> getListRegistroConsulta(String tipoRegistro) {
		if (StringUtils.isBlank(tipoRegistro)) {
			return new ArrayList<>();
		}
		return servicioRegistro.getRegistro(null, tipoRegistro);
	}

	private void cargarListaMunicipios() {

		if (listMunicipios == null) {
			listMunicipios = new HashMap<String, Map<String, String>>();
			List<MunicipioDto> municipios = servicioMunicipio.listaMunicipios(null);
			for (MunicipioDto elemento : municipios) {
				if (!listMunicipios.containsKey(elemento.getIdProvincia())) {
					// Primer municipio de provincia. Inicializa la lista de municipios:
					listMunicipios.put(elemento.getIdProvincia(), new HashMap<String, String>());
				}
				listMunicipios.get(elemento.getIdProvincia()).put(elemento.getIdMunicipio(), elemento.getNombre());
			}
			// Ordena los mapas:
			for (String key : listMunicipios.keySet()) {
				TreeMap<String, String> mapaOrdenado = new TreeMap<String, String>(listMunicipios.get(key));
				listMunicipios.put(key, mapaOrdenado);
			}
		}
	}

	public Map<String, Map<String, String>> getListMunicipios() {
		if (null == listMunicipios) {
			cargarListaMunicipios();
		}
		return listMunicipios;
	}

	public Map<String, String> getProvincias() {
		cargarListaMunicipios();
		List<ProvinciaDto> provincias = servicioProvincia.getListaProvincias();
		if (mapaProvincias == null) {
			HashMap<String, String> mapResource = new HashMap<String, String>();
			for (ProvinciaDto elemento : provincias) {
				mapResource.put(elemento.getNombre(), elemento.getIdProvincia());
			}
			mapaProvincias = new TreeMap<String, String>(mapResource);
		}
		return mapaProvincias;
	}

	public List<MunicipioCamDto> getMunicipios(IntervinienteRegistroDto interviniente) {
		if (interviniente != null && interviniente.getDireccion() != null && interviniente.getDireccion().getIdProvincia() != null && !interviniente.getDireccion().getIdProvincia().isEmpty()) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(interviniente.getDireccion().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<MunicipioCamDto> getMunicipiosNotario(NotarioRegistroDto notario) {
		if (notario != null && notario.getCodProvincia() != null && !notario.getCodProvincia().isEmpty()) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(notario.getCodProvincia());
		}
		return Collections.emptyList();
	}

	public List<MunicipioCamDto> getMunicipiosPropiedad(PropiedadDto propiedad) {
		if (propiedad != null && propiedad.getDireccion() != null && propiedad.getDireccion().getIdProvincia() != null && !propiedad.getDireccion().getIdProvincia().isEmpty()) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(propiedad.getDireccion().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<MunicipioCamDto> getMunicipiosInmueble(InmuebleDto inmueble) {
		if (inmueble != null && inmueble.getBien() != null && inmueble.getBien().getDireccion() != null && inmueble.getBien().getDireccion().getIdProvincia() != null && !inmueble.getBien()
				.getDireccion().getIdProvincia().isEmpty()) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(inmueble.getBien().getDireccion().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<MunicipioCamDto> getMunicipiosPorProvincia(String idProvincia) {
		if (StringUtils.isNotBlank(idProvincia)) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(idProvincia);
		}
		return Collections.emptyList();
	}

	public String getMunicipioNombre(String idMunicipio, String idProvincia) {
		if (StringUtils.isNotBlank(idProvincia)) {
			return servicioMunicipioCam.getMunicipioNombre(idMunicipio, idProvincia);
		}
		return "";
	}

	public List<OperacionRegistroDto> getOperacionesPropiedad() {
		if (null == operacionesPropiedad || operacionesPropiedad.isEmpty()) {
			ServicioOperacionRegistro servicioOperacionRegistro = ContextoSpring.getInstance().getBean(ServicioOperacionRegistro.class);
			operacionesPropiedad = servicioOperacionRegistro.getOperacionesRegistro();
			if (operacionesPropiedad == null) {
				operacionesPropiedad = Collections.emptyList();
			}
		}
		return operacionesPropiedad;
	}

	public List<TipoCargoDto> getTipoCargos() {
		if (!iniciado) {
			iniciarCombos();
		}
		return tipoCargos;
	}

	public List<ProvinciaDto> getProvinciasBienes() {
		if (!iniciado) {
			iniciarCombos();
		}
		return provinciasBienes;
	}

	public List<TipoTramiteRegistro> getComboTipoTramites() {
		List<TipoTramiteRegistro> listTipoTramiteRegistro = new ArrayList<TipoTramiteRegistro>();
		List<String> codigosFuncion = utilesColegiado.dameListaCodigoFuncion("OEGAM_REGI");
		for (TipoTramiteRegistro tipo : Arrays.asList(TipoTramiteRegistro.values())) {
			if (codigosFuncion.contains(tipo.getCodigoFuncion())) {
				listTipoTramiteRegistro.add(tipo);
			}
		}
		Collections.sort(listTipoTramiteRegistro, new ComparadorTipoTramite());
		return listTipoTramiteRegistro;
	}

	public List<EstadoTramiteRegistro> getComboEstadoTramite() {
		List<EstadoTramiteRegistro> listEstadoTramiteRegistro = Arrays.asList(EstadoTramiteRegistro.values());
		Collections.sort(listEstadoTramiteRegistro, new ComparadorEstadoTramites());
		return listEstadoTramiteRegistro;
	}

	public Inmatriculada[] getInmatriculada() {
		return Inmatriculada.values();
	}

	public Aceptacion[] getAceptacion() {
		return Aceptacion.values();
	}

	public TiposDuracion[] getTiposDuracion() {
		return TiposDuracion.values();
	}

	public TiposMedio[] getTiposMedio() {
		return TiposMedio.values();
	}

	public TipoReunionJunta[] getTipoReunionJunta() {
		return TipoReunionJunta.values();
	}

	public List<TipoPersonaRegistro> getListCorpmePersonTYpe() {
		return Arrays.asList(TipoPersonaRegistro.values());
	}

	public List<TipoCesion> getListCessionType() {
		return Arrays.asList(TipoCesion.values());
	}

	public List<TipoComision> getListCommission() {
		return Arrays.asList(TipoComision.values());
	}

	public List<TipoOtrosImportes> getListOtherImportsType() {
		return Arrays.asList(TipoOtrosImportes.values());
	}

	public List<TipoIntervencion> getListInterventionType() {
		return Arrays.asList(TipoIntervencion.values());
	}

	public List<TipoCuadroAmortizacion> getListAmortizationsScheduleType() {
		return Arrays.asList(TipoCuadroAmortizacion.values());
	}

	public List<TipoMoneda> getListCurrency() {
		return Arrays.asList(TipoMoneda.values());
	}

	public List<TipoClausula> getListClauseType() {
		return Arrays.asList(TipoClausula.values());
	}

	public List<TipoPacto> getListPactType() {
		return Arrays.asList(TipoPacto.values());
	}

	public List<TipoCategoria> getListCategory() {
		return Arrays.asList(TipoCategoria.values());
	}

	public List<TipoContrato> getListContractType() {
		return Arrays.asList(TipoContrato.values());
	}

	public List<EstadoBuque> getListEstadoBuque() {
		return Arrays.asList(EstadoBuque.values());
	}

	public List<TipoContrato> getListFinancialContractType() {
		List<TipoContrato> result = new ArrayList<TipoContrato>();
		for (TipoContrato ct : TipoContrato.values()) {
			if (ct.getKey().startsWith("B")) {
				result.add(ct);
			}
		}
		return result;
	}

	public List<TipoContrato> getListLeasingRentingContractType() {
		List<TipoContrato> result = new ArrayList<TipoContrato>();
		for (TipoContrato ct : TipoContrato.values()) {
			if (ct.getKey().startsWith("L") || ct.getKey().startsWith("R")) {
				result.add(ct);
			}
		}
		return result;
	}

	public List<TipoContrato> getListLeasingContractType() {
		List<TipoContrato> result = new ArrayList<TipoContrato>();
		for (TipoContrato ct : TipoContrato.values()) {
			if (ct.getKey().startsWith("L")) {
				result.add(ct);
			}
		}
		return result;
	}

	public List<TipoContrato> getListRentingContractType() {
		List<TipoContrato> result = new ArrayList<TipoContrato>();
		for (TipoContrato ct : TipoContrato.values()) {
			if (ct.getKey().startsWith("R")) {
				result.add(ct);
			}
		}
		return result;
	}

	public List<TipoOperacion> getListOperationType() {
		return Arrays.asList(TipoOperacion.values());
	}

	public List<TipoOperacion> getListTipoOperacionCancelacion() {
		List<TipoOperacion> result = new ArrayList<TipoOperacion>();
		for (TipoOperacion ct : TipoOperacion.values()) {
			if (ct.toString().contains("Cancelación")) {
				result.add(ct);
			}
		}
		return result;
	}

	public List<DocumentType> getListDocumentType() {
		return Arrays.asList(DocumentType.values());
	}

	public List<TipoInteresReferencia> getListInterestTypeReference() {
		return Arrays.asList(TipoInteresReferencia.values());
	}

	public String convertirTextoOtherImportsType(String valorEnum) {
		return TipoOtrosImportes.convertirTexto(valorEnum);
	}

	public String convertirTextoCategory(String valorEnum) {
		return TipoCategoria.convertirTexto(valorEnum);
	}

	public String convertirTextInterestTypeReference(String valorEnum) {
		return TipoInteresReferencia.convertirTexto(valorEnum);
	}

	public List<ClasePropiedadIndustrial> getListClasePropiedadIndustrial() {
		return Arrays.asList(ClasePropiedadIndustrial.values());
	}

	public List<SubClasePropiedadIndustrial> getListSubClasePropiedadIndustrial() {
		return Arrays.asList(SubClasePropiedadIndustrial.values());
	}

	public List<ClasePropiedadIntelectual> getListClasePropiedadIntelectual() {
		return Arrays.asList(ClasePropiedadIntelectual.values());
	}

	public List<CapitaniaMaritima> getListCapitaniaMaritima() {
		return Arrays.asList(CapitaniaMaritima.values());
	}

	public List<ListaMaritima> getListListaMaritima() {
		return Arrays.asList(ListaMaritima.values());
	}

	public List<RegistroAdministrativo> getListRegistroAdministrativo() {
		return Arrays.asList(RegistroAdministrativo.values());
	}

	public List<ExtensionDerecho> getListExtensionDerecho() {
		return Arrays.asList(ExtensionDerecho.values());
	}

	public List<ClaseDerecho> getListClaseDerecho() {
		return Arrays.asList(ClaseDerecho.values());
	}

	public List<SituacionJuridica> getListSituacionJuridica() {
		List<SituacionJuridica> result = new ArrayList<SituacionJuridica>();
		for (SituacionJuridica situacion : SituacionJuridica.values()) {
			if (situacion.getActivo().equals("0")) {
				result.add(situacion);
			}
		}
		return result;
	}

	public List<CausaCancelacion> getListCausaCancelacion() {
		return Arrays.asList(CausaCancelacion.values());
	}

	public List<TipoDefecto> getListTipoDefecto() {
		return Arrays.asList(TipoDefecto.values());
	}

	public List<TipoOperacionRegistral> getListTipoOperacionRegistral() {
		return Arrays.asList(TipoOperacionRegistral.values());
	}

	public List<TipoRegistroAdministrativo> getListTipoRegistroAdministrativo() {
		return Arrays.asList(TipoRegistroAdministrativo.values());
	}

	public List<TipoFormaPago> getListFormaPago() {
		return Arrays.asList(TipoFormaPago.values());
	}

	public Boolean esEditable(String estado, String numEntrada) {

		return (null == estado || estado.equalsIgnoreCase(EstadoTramiteRegistro.Iniciado.getValorEnum()) || estado.equalsIgnoreCase(EstadoTramiteRegistro.Validado.getValorEnum()) || (estado
				.equalsIgnoreCase(EstadoTramiteRegistro.Finalizado_error.getValorEnum()) && StringUtils.isBlank(numEntrada)));
	}

	public Boolean esPermitidoFirmarAcuseFueraSecuencia(String estado, String tipoMensaje) {

		return ((!ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS.equalsIgnoreCase(tipoMensaje) && !ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL.equalsIgnoreCase(tipoMensaje)
				&& !ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL.equalsIgnoreCase(tipoMensaje)) || (null != estado && !EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total
						.getValorEnum().equals(estado) && !EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estado)
						&& !EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estado) && !EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion.getValorEnum()
								.equals(estado)));

	}

	public Boolean esPermitidoConfirmarFactura(String estado) {
		return (null != estado && (EstadoTramiteRegistro.Inscrito.getValorEnum().equals(estado) || EstadoTramiteRegistro.Finalizado.getValorEnum().equals(estado)));
	}

	public Boolean esAnulado(String estado) {
		return (estado.equalsIgnoreCase(EstadoTramiteRegistro.Anulado.getValorEnum()));
	}

	private class ComparadorEstadoTramites implements Comparator<EstadoTramiteRegistro> {
		@Override
		public int compare(EstadoTramiteRegistro o1, EstadoTramiteRegistro o2) {
			return (o1.getNombreEnum()).compareTo(o2.getNombreEnum());
		}
	}

	private class ComparadorTipoTramite implements Comparator<TipoTramiteRegistro> {
		@Override
		public int compare(TipoTramiteRegistro o1, TipoTramiteRegistro o2) {
			return (o1.getNombreEnum()).compareTo(o2.getNombreEnum());
		}
	}

	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritos(TramiteRegistroDto tramiteRegistroDto) {
		if (null != tramiteRegistroDto && null != tramiteRegistroDto.getIdContrato()) {
			return servicioDatosBancariosFavoritos.getListaDatosBancariosFavoritosRegistradoresPorContrato(tramiteRegistroDto.getIdContrato().longValue());
		}
		return Collections.emptyList();
	}

	public TipoCuentaBancaria[] getTiposCuentasBancarias() {
		return TipoCuentaBancaria.values();
	}

}
