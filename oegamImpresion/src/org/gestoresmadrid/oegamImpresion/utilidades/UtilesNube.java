package org.gestoresmadrid.oegamImpresion.utilidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilesNube implements Serializable {

	private static final long serialVersionUID = 1356780400192029743L;

	@Autowired
	ServicioComunVehiculo servicioVehiculo;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	Utiles utiles;

	@Autowired
	GestorPropiedades gestorPropiedades;

	public static final boolean DETRAS = true;
	public static final char SPACE = ' ';
	public static final char ZERO = '0';
	private static final int ARRAYS_LENGTH = 200;
	private static final char[] SPACES_ARRAY = new char[ARRAYS_LENGTH];
	private static final char[] ZEROS_ARRAY = new char[ARRAYS_LENGTH];

	private static char[] OLD_CHAR = { 'Á', 'É', 'Í', 'Ó', 'Ú', 'º', 'ª', 'Ñ', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', 'À', 'È', 'Ì', 'Ò', 'Ù' };
	private static char[] NEW_CHAR = { 'A', 'E', 'I', 'O', 'U', ' ', ' ', '&', 'A', 'E', 'I', 'O', 'U', 'A', 'E', 'I', 'O', 'U' };

	private static char[] OLD_CHAR_P = { 'Á', 'É', 'Í', 'Ó', 'Ú', 'º', 'ª', 'Ñ', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', '(', ')', '=', 'À', 'È', 'Ì', 'Ò', 'Ù' };
	private static char[] NEW_CHAR_P = { 'A', 'E', 'I', 'O', 'U', ' ', ' ', '&', 'A', 'E', 'I', 'O', 'U', ' ', ' ', ' ', 'A', 'E', 'I', 'O', 'U' };

	private static char[] OLD_CHAR_MATW = { 'Á', 'É', 'Í', 'Ó', 'Ú', 'º', 'ª', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', '=', 'À', 'È', 'Ì', 'Ò', 'Ù' };
	private static char[] NEW_CHAR_MATW = { 'A', 'E', 'I', 'O', 'U', ' ', ' ', 'A', 'E', 'I', 'O', 'U', ' ', 'A', 'E', 'I', 'O', 'U' };

	private static char[] OLD_CHAR_NUEVA_APLICACION = { 'Á', 'É', 'Í', 'Ó', 'Ú', 'º', 'ª', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', 'À', 'È', 'Ì', 'Ò', 'Ù' };
	private static char[] NEW_CHAR_NUEVA_APLICACION = { 'A', 'E', 'I', 'O', 'U', ' ', ' ', 'A', 'E', 'I', 'O', 'U', 'A', 'E', 'I', 'O', 'U' };

	public static final String VALOR_CET_PREDETERMINADO = "00000000";

	static {
		// crea los arrays
		for (int i = 0; i < ARRAYS_LENGTH; i++) {
			SPACES_ARRAY[i] = SPACE;
			ZEROS_ARRAY[i] = ZERO;
		}
	}

	Map<String, Integer> stringCampoNubeLongitud = new HashMap<String, Integer>();

	// CAMPOS MANCHA PDF 1 1379 CARACTERES
	// NUBE1 -->POSICIONES DEL ARRAY [0]..[110]

	// CAMPOS MANCHA PDF 2 580 CARACTERES
	// NUBE2 --> POSICIONES DEL ARRAY [111]..[158]

	private static String[][] arrayCamposNubeMATW = { { "Nº Expediente", "20" }, { "Tasa", "12" }, { "Codigo ITV", "9" }, { "Tipo Tarjeta ITV", "2" }, { "Marca Base", "23" }, { "Marca", "23" }, {
			"Fabricante base", "70" }, { "Fabricante", "70" }, { "Modelo", "22" }, { "Color", "2" }, { "Procedencia", "3" }, { "Bastidor", "21" }, { "NIVE", "32" }, { "Tipo Tramite", "2" }, {
					"Matricula Origen", "9" }, { "Matricula Orig Extr", "9" }, { "Tipo Inspeccion", "1" }, { "Servicio", "3" }, { "Tipo veh DGT", "2" }, { "Potencia fiscal", "13" }, { "Cilindrada",
							"6" }, { "Tara", "7" }, { "MMA", "6" }, { "Plazas", "3" }, { "Tipo Carburante", "3" },

			// Datos direccion vehiculo
			{ "Tipo via veh", "5" }, { "Nombre via veh", "50" }, { "Numero via veh", "5" }, { "Kilometro veh", "5" }, { "Hectometro veh", "7" }, { "Bloque veh", "5" }, { "Portal veh", "5" }, {
					"Escalera veh", "5" }, { "Planta veh", "5" }, { "Puerta veh", "5" }, { "Provincia veh", "2" }, { "Municipio veh", "5" }, { "Pueblo veh", "74" }, { "Cpostal veh", "5" }, {
							"Pais veh", "5" },

			// Datos Titular
			{ "DOI Titular", "9" }, { "DOI Represent titular", "9" }, { "Fecha nacimiento titular", "8" }, { "Apellido1 titular", "20" }, { "Apellido2 titular", "20" }, { "Nombre Titular", "20" }, {
					"Razon social", "70" }, { "Sexo titular", "1" }, { "Cambio domic titular", "1" }, { "Tipo via tit", "5" }, { "Nombre via tit", "50" }, { "Numero via tit", "5" }, { "Kilometro tit",
							"5" }, { "Hectometro tit", "7" }, { "Bloque tit", "5" }, { "Portal tit", "5" }, { "Escalera tit", "5" }, { "Planta tit", "5" }, { "Puerta tit", "5" }, { "Provincia tit",
									"2" }, { "Municipio tit", "5" }, { "Pueblo tit", "74" }, { "Cpostal tit", "5" }, { "Pais tit", "5" },

			// Datos Tecnicos
			{ "Fecha validez ITV", "8" }, { "Fecha prim matriculacion", "8" }, { "Tutela", "1" }, { "Justificado IVTM", "1" }, { "Tipo base", "25" }, { "Tipo", "25" }, { "Variante base", "25" }, {
					"Variante", "25" }, { "Version base", "35" }, { "Version", "35" }, { "Num homologacion base", "50" }, { "Num homologacion", "50" }, { "Masa Tecnica", "6" }, { "Mom base", "7" }, {
							"Mom", "7" }, { "Potencia neta", "13" }, { "Relacion potpeso", "7" }, { "Plazas pie", "3" }, { "Carroceria", "5" }, { "Consumo", "4" }, { "Distancia Ejes", "4" }, {
									"Via anterior", "4" }, { "Via posterior", "4" }, { "Tipo Alimentacion", "1" }, { "Categoria electrica", "4" }, { "Autonomia electrica", "4" }, { "Eco innova",
											"1" }, { "Reduccion ECO", "4" }, { "Codigo ECO", "25" }, { "Nivel emisiones", "15" }, { "Subasta", "1" }, { "Importado", "1" }, { "Categoria EU", "7" }, {
													"Pais fabricacion", "1" }, { "C02", "7" }, { "Tipo veh industria", "4" },

			// Impuestos
			{ "CEM", "8" }, { "CEMA", "8" }, { "Exencion CEM", "10" }, { "Renting", "1" }, { "Kilometraje", "6" }, { "Usado", "1" }, { "Cuenta Horas", "6" }, { "Autonomo", "1" }, { "Codigo IAE",
					"4" }, { "Conductor Habitual", "1" },

			// Datos Tutor
			{ "DOI Tutor", "9" }, { "Tipo tutela", "1" },

			// DATOS NUBE 2 -->POS_NUBE2
			// Datos Arrendatario
			{ "DOI arr", "9" }, { "Fech ini Renting", "8" }, { "Fech fin Renting", "8" }, { "Hora ini Renting", "4" }, { "Hora fin Renting", "4" }, { "Fecha nacimiento arr", "8" }, { "Apellido1 arr",
					"20" }, { "Apellido2 arr", "20" }, { "Nombre arr", "20" }, { "Sexo arr", "1" }, { "Cambio domic arr", "1" }, { "Tipo via arr", "5" }, { "Nombre via arr", "50" }, {
							"Numero via arr", "5" }, { "Kilometro arr", "5" }, { "Hectometro arr", "7" }, { "Bloque arr", "5" }, { "Portal arr", "5" }, { "Escalera arr", "5" }, { "Planta arr", "5" },
			{ "Puerta arr", "5" }, { "Provincia arr", "2" }, { "Municipio arr", "5" }, { "Pueblo arr", "74" }, { "Cpostal arr", "5" }, { "Pais arr", "5" }, { "Razon social arr", "70" }, {
					"DOI Reprent arr", "9" },

			// Datos Conductor Habitual
			{ "DOI ch", "9" }, { "Fecha fin ch", "8" }, { "Hora fin ch", "4" }, { "Cambio dom ch", "1" }, { "Tipo via ch", "5" }, { "Nombre via ch", "50" }, { "Numero via ch", "5" }, { "Kilometro ch",
					"5" }, { "Hectometro ch", "7" }, { "Bloque ch", "5" }, { "Portal ch", "5" }, { "Escalera ch", "5" }, { "Planta ch", "5" }, { "Puerta ch", "5" }, { "Provincia ch", "2" }, {
							"Municipio ch", "5" }, { "Pueblo ch", "74" }, { "Cpostal ch", "5" }, { "Pais ch", "5" }

	};

	public void inicializarLongitudCamposNubeMatw() {
		for (int i = 0; i < arrayCamposNubeMATW.length; i++) {
			stringCampoNubeLongitud.put(arrayCamposNubeMATW[i][0], Integer.parseInt(arrayCamposNubeMATW[i][1]));
		}
	}

	public int getLongitudCampoNube(String campo) {
		return stringCampoNubeLongitud.get(campo);
	}

	public int getLongitudArrayCamposNube() {
		return stringCampoNubeLongitud.size();
	}

	public String getCampoValorArray(int i) {
		return arrayCamposNubeMATW[i][0].toString();
	}

	public String changeSize(String cadena, int size) {
		return changeSize(cadena, size, SPACE, DETRAS);
	}

	public String changeSize(String cadena, int size, char relleno, boolean detras) {
		String res = null;
		if (cadena == null)
			return null;

		int tam = cadena.length();

		if (size == tam) {
			return cadena;
		} else if (size > tam) {
			StringBuffer buffer = new StringBuffer(size);
			int dif = size - tam;

			char[] blancos;
			boolean isLessThan = dif < ARRAYS_LENGTH;

			if (isLessThan && relleno == SPACE) {
				blancos = SPACES_ARRAY;
			} else if (isLessThan && relleno == ZERO) {
				blancos = ZEROS_ARRAY;
			} else {
				blancos = new char[dif];
				for (int i = 0; i < dif; i++) {
					blancos[i] = relleno;
				}
			}

			if (detras)
				buffer.append(cadena).append(blancos, 0, dif);
			else
				buffer.append(blancos, 0, dif).append(cadena);
			return buffer.toString();
		} else {
			if (detras)
				res = cadena.substring(0, size);
			else
				res = cadena.substring(tam - size);
		}

		return res;
	}

	public String sustituyeCaracteres_MATW(String valor) {
		String nuevoValor = valor.toUpperCase();
		for (int i = 0; i < OLD_CHAR_MATW.length; i++) {
			nuevoValor = nuevoValor.replace(OLD_CHAR_MATW[i], NEW_CHAR_MATW[i]);
		}
		return nuevoValor;
	}

	public String sustituyeCaracteresNuevaAplicacion(String valor) {
		String nuevoValor = valor.toUpperCase();
		for (int i = 0; i < OLD_CHAR_NUEVA_APLICACION.length; i++) {
			nuevoValor = nuevoValor.replace(OLD_CHAR_NUEVA_APLICACION[i], NEW_CHAR_NUEVA_APLICACION[i]);
		}
		return nuevoValor;
	}

	public String sustituyeCaracteres_Plus(String valor) {
		String nuevoValor = valor.toUpperCase();
		for (int i = 0; i < OLD_CHAR_P.length; i++) {
			nuevoValor = nuevoValor.replace(OLD_CHAR_P[i], NEW_CHAR_P[i]);
		}
		return nuevoValor;
	}

	public String sustituyeCaracteres(String valor) {
		String nuevoValor = valor.toUpperCase();
		for (int i = 0; i < OLD_CHAR.length; i++) {
			nuevoValor = nuevoValor.replace(OLD_CHAR[i], NEW_CHAR[i]);
		}
		return nuevoValor;
	}
	
	public String sustituyeCaracteresConN(String valor) {
		if (valor.contains("Ñ")) {
			String nuevoValor = valor.toUpperCase();
			nuevoValor = nuevoValor.replace("Ñ", "N");
			return nuevoValor;
		}
		return null;
	}

	public String obtenerValorCampoNubeMatw(String campo, TramiteTrafMatrVO tramite) {
		String tcamp = "";

		IntervinienteTraficoVO titular = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Titular.getValorEnum());
		IntervinienteTraficoVO repreTitular = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.RepresentanteTitular.getValorEnum());
		IntervinienteTraficoVO conductorHabitual = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.ConductorHabitual.getValorEnum());
		IntervinienteTraficoVO arrendatario = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Arrendatario.getValorEnum());
		IntervinienteTraficoVO repreArrendatario = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.RepresentanteArrendatario.getValorEnum());

		if (campo.equalsIgnoreCase("Conductor Habitual")) {
			tcamp = conductorHabitual != null && conductorHabitual.getId().getNif() != null ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
		}

		if (campo.equalsIgnoreCase("Tutela")) {
			tcamp = repreTitular != null && repreTitular.getIdMotivoTutela() != null ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
		}

		if (campo.equalsIgnoreCase("Renting")) {
			tcamp = tramite.getRenting() != null && "SI".equals(tramite.getRenting()) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
		}

		if (campo.equalsIgnoreCase("Nº Expediente")) {
			tcamp = tramite.getNumExpediente().toString();
		}

		if (campo.equalsIgnoreCase("Tasa") && tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null) {
			tcamp = tramite.getTasa().getCodigoTasa();
		}

		if (campo.equalsIgnoreCase("Tipo Tramite") && tramite.getTipoTramiteMatr() != null) {
			tcamp = tramite.getTipoTramiteMatr();
		}

		if (campo.equalsIgnoreCase("Pais tit")) {
			tcamp = ConstantesPDF.VALOR_PAIS;
		}

		if (campo.equalsIgnoreCase("Pais veh")) {
			tcamp = ConstantesPDF.VALOR_PAIS;
		}

		if (campo.equalsIgnoreCase("CEM")) {
			if (tramite.getExentoCem() != null && "SI".equals(tramite.getExentoCem())) {
				if (StringUtils.isNotBlank(tramite.getCem())) {
					tcamp = tramite.getCem();
				} else {
					tcamp = ConstantesPDF.SIN_CEM;
				}
			} else {
				if (tramite.getCem() != null) {
					tcamp = tramite.getCem();
				} else {
					tcamp = ConstantesPDF.SIN_CEM;
				}
			}
		}

		if (campo.equalsIgnoreCase("CEMA") && tramite.getCema() != null) {
			tcamp = tramite.getCema();
		}

		if (campo.equalsIgnoreCase("Exencion CEM") && tramite.getnRegIedtm() != null) {
			tcamp = tramite.getnRegIedtm();
		}

		if (campo.equalsIgnoreCase("Justificado IVTM")) {
			tcamp = tramite.getJustificadoIvtm() != null && ("SI".equals(tramite.getJustificadoIvtm()) || "S".equals(tramite.getJustificadoIvtm())) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
		}

		if (titular != null) {
			if (campo.equalsIgnoreCase("DOI Titular") && titular.getId().getNif() != null) {
				tcamp = titular.getId().getNif();
			}

			if (campo.equalsIgnoreCase("Razon social") && TipoPersona.Juridica.getValorEnum().equals(titular.getPersona().getTipoPersona())) {
				tcamp = titular.getPersona().getApellido1RazonSocial() != null ? titular.getPersona().getApellido1RazonSocial() : "";
			}

			if (campo.equalsIgnoreCase("Nombre Titular") && TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona())) {
				tcamp = titular.getPersona().getNombre() != null ? titular.getPersona().getNombre() : "";
			}

			if (campo.equalsIgnoreCase("Apellido1 titular") && TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona())) {
				tcamp = titular.getPersona().getApellido1RazonSocial() != null ? titular.getPersona().getApellido1RazonSocial() : "";
			}

			if (campo.equalsIgnoreCase("Apellido2 titular") && TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona())) {
				tcamp = titular.getPersona().getApellido2() != null ? titular.getPersona().getApellido2() : "";
			}

			if (campo.equalsIgnoreCase("Fecha nacimiento titular") && titular.getPersona().getFechaNacimiento() != null && TipoPersona.Fisica.getValorEnum().equals(titular.getPersona()
					.getTipoPersona())) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", titular.getPersona().getFechaNacimiento());
			}

			if (campo.equalsIgnoreCase("Sexo titular") && titular.getPersona().getSexo() != null) {
				tcamp = titular.getPersona().getSexo();
			}

			if (campo.equalsIgnoreCase("Autonomo") && TipoPersona.Fisica.getValorEnum().equals(titular.getPersona().getTipoPersona())) {
				tcamp = titular.getAutonomo() != null && ("S".equals(titular.getAutonomo()) || "SI".equals(titular.getAutonomo().toUpperCase())) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
			}

			if (campo.equalsIgnoreCase("Codigo IAE") && titular.getCodigoIae() != null) {
				tcamp = titular.getCodigoIae();
			}

			if (campo.equalsIgnoreCase("Cambio domic titular")) {
				tcamp = titular.getCambioDomicilio() != null && "SI".equals(titular.getCambioDomicilio()) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
			}

			if (titular.getDireccion() != null) {
				if (campo.equalsIgnoreCase("Tipo via tit") && titular.getDireccion().getIdTipoVia() != null) {
					tcamp = titular.getDireccion().getIdTipoVia();
				}

				if (campo.equalsIgnoreCase("Nombre via tit") && titular.getDireccion().getNombreVia() != null) {
					tcamp = utilidadesConversiones.ajustarCamposIne(titular.getDireccion().getNombreVia());
				}

				if (campo.equalsIgnoreCase("Numero via tit") && titular.getDireccion().getNumero() != null) {
					tcamp = titular.getDireccion().getNumero();
				}

				if (campo.equalsIgnoreCase("Kilometro tit") && titular.getDireccion().getKm() != null) {
					tcamp = titular.getDireccion().getKm().toString();
				}

				if (campo.equalsIgnoreCase("Hectometro tit") && titular.getDireccion().getHm() != null) {
					tcamp = titular.getDireccion().getHm().toString();
				}

				if (campo.equalsIgnoreCase("Bloque tit") && titular.getDireccion().getBloque() != null) {
					tcamp = titular.getDireccion().getBloque();
				}

				if (campo.equalsIgnoreCase("Portal tit") && titular.getDireccion().getPuerta() != null) {
					tcamp = titular.getDireccion().getPuerta();
				}

				if (campo.equalsIgnoreCase("Escalera tit") && titular.getDireccion().getEscalera() != null) {
					tcamp = titular.getDireccion().getEscalera();
				}

				if (campo.equalsIgnoreCase("Planta tit") && titular.getDireccion().getPlanta() != null) {
					tcamp = titular.getDireccion().getPlanta();
				}

				if (campo.equalsIgnoreCase("Puerta tit") && titular.getDireccion().getLetra() != null) {
					tcamp = titular.getDireccion().getLetra();
				}

				if (campo.equalsIgnoreCase("Provincia tit") && titular.getDireccion().getIdProvincia() != null) {
					tcamp = utilidadesConversiones.getSiglasFromIdProvincia(titular.getDireccion().getIdProvincia());
				}

				if (campo.equalsIgnoreCase("Municipio tit") && titular.getDireccion().getIdProvincia() != null) {
					tcamp = titular.getDireccion().getIdProvincia() + titular.getDireccion().getIdMunicipio();
				}

				if (campo.equalsIgnoreCase("Pueblo tit") && titular.getDireccion().getPuebloCorreos() != null) {
					tcamp = titular.getDireccion().getPuebloCorreos();
				}

				if (campo.equalsIgnoreCase("Cpostal tit") && titular.getDireccion().getCodPostalCorreos() != null) {
					tcamp = titular.getDireccion().getCodPostalCorreos();
				}
			}
		}

		if (repreTitular != null) {
			if (campo.equalsIgnoreCase("DOI Represent titular")) {
				tcamp = repreTitular.getId().getNif();
			}

			if (campo.equalsIgnoreCase("DOI Tutor") && repreTitular.getIdMotivoTutela() != null && repreTitular.getConceptoRepre().equals(ConceptoTutela.Tutela.getValorEnum())) {
				tcamp = repreTitular.getId().getNif();
			}
			if (campo.equalsIgnoreCase("Tipo tutela") && repreTitular.getIdMotivoTutela() != null && repreTitular.getConceptoRepre().equals(ConceptoTutela.Tutela.getValorEnum())) {
				tcamp = repreTitular.getIdMotivoTutela();
			}
		}

		if (arrendatario != null) {
			if (campo.equalsIgnoreCase("DOI arr") && arrendatario.getId().getNif() != null) {
				tcamp = arrendatario.getId().getNif();
			}

			if (campo.equalsIgnoreCase("Razon social arr") && TipoPersona.Juridica.getValorEnum().equals(arrendatario.getPersona().getTipoPersona()) && arrendatario.getPersona()
					.getApellido1RazonSocial() != null) {
				tcamp = arrendatario.getPersona().getApellido1RazonSocial();
			}

			if (campo.equalsIgnoreCase("Nombre arr") && TipoPersona.Fisica.getValorEnum().equals(arrendatario.getPersona().getTipoPersona()) && arrendatario.getPersona().getNombre() != null) {
				tcamp = arrendatario.getPersona().getNombre();
			}

			if (campo.equalsIgnoreCase("Apellido1 arr") && TipoPersona.Fisica.getValorEnum().equals(arrendatario.getPersona().getTipoPersona()) && arrendatario.getPersona()
					.getApellido1RazonSocial() != null) {
				tcamp = arrendatario.getPersona().getApellido1RazonSocial();
			}

			if (campo.equalsIgnoreCase("Apellido2 arr") && TipoPersona.Fisica.getValorEnum().equals(arrendatario.getPersona().getTipoPersona()) && arrendatario.getPersona().getApellido2() != null) {
				tcamp = arrendatario.getPersona().getApellido2();
			}

			if (campo.equalsIgnoreCase("Fecha nacimiento arr") && arrendatario.getPersona().getFechaNacimiento() != null && TipoPersona.Fisica.getValorEnum().equals(arrendatario.getPersona()
					.getTipoPersona())) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", arrendatario.getPersona().getFechaNacimiento());
			}

			if (campo.equalsIgnoreCase("Sexo arr") && arrendatario.getPersona().getSexo() != null) {
				tcamp = arrendatario.getPersona().getSexo();
			}

			if (repreArrendatario != null) {
				if (campo.equalsIgnoreCase("DOI Reprent arr") && repreArrendatario != null && repreArrendatario.getId().getNif() != null) {
					tcamp = repreArrendatario.getId().getNif();
				}
			}

			if (campo.equalsIgnoreCase("Fech ini Renting") && arrendatario.getFechaInicio() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", arrendatario.getFechaInicio());
			}

			if (campo.equalsIgnoreCase("Fech fin Renting") && arrendatario.getFechaFin() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", arrendatario.getFechaFin());
			}

			if (campo.equalsIgnoreCase("Hora ini Renting") && arrendatario.getHoraInicio() != null) {
				tcamp = arrendatario.getHoraInicio().replaceAll(":", "");
			}

			if (campo.equalsIgnoreCase("Hora fin Renting") && arrendatario.getHoraFin() != null) {
				tcamp = arrendatario.getHoraFin().replaceAll(":", "");
			}

			if (campo.equalsIgnoreCase("Cambio domic arr")) {
				tcamp = arrendatario.getCambioDomicilio() != null && "SI".equals(arrendatario.getCambioDomicilio()) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
			}

			if (arrendatario.getDireccion() != null) {
				if (campo.equalsIgnoreCase("Tipo via arr") && arrendatario.getDireccion().getIdTipoVia() != null) {
					tcamp = arrendatario.getDireccion().getIdTipoVia();
				}

				if (campo.equalsIgnoreCase("Nombre via arr") && arrendatario.getDireccion().getNombreVia() != null) {
					tcamp = utilidadesConversiones.ajustarCamposIne(arrendatario.getDireccion().getNombreVia());
				}

				if (campo.equalsIgnoreCase("Numero via arr") && arrendatario.getDireccion().getNumero() != null) {
					tcamp = arrendatario.getDireccion().getNumero();
				}

				if (campo.equalsIgnoreCase("Kilometro arr") && arrendatario.getDireccion().getKm() != null) {
					tcamp = arrendatario.getDireccion().getKm().toString();
				}

				if (campo.equalsIgnoreCase("Hectometro arr") && arrendatario.getDireccion().getHm() != null) {
					tcamp = arrendatario.getDireccion().getHm().toString();
				}

				if (campo.equalsIgnoreCase("Bloque arr") && arrendatario.getDireccion().getBloque() != null) {
					tcamp = arrendatario.getDireccion().getBloque();
				}

				if (campo.equalsIgnoreCase("Portal arr") && arrendatario.getDireccion().getPuerta() != null) {
					tcamp = arrendatario.getDireccion().getPuerta();
				}

				if (campo.equalsIgnoreCase("Escalera arr") && arrendatario.getDireccion().getEscalera() != null) {
					tcamp = arrendatario.getDireccion().getEscalera();
				}

				if (campo.equalsIgnoreCase("Planta arr") && arrendatario.getDireccion().getPlanta() != null) {
					tcamp = arrendatario.getDireccion().getPlanta();
				}

				if (campo.equalsIgnoreCase("Puerta arr") && arrendatario.getDireccion().getLetra() != null) {
					tcamp = arrendatario.getDireccion().getLetra();
				}

				if (campo.equalsIgnoreCase("Provincia arr") && arrendatario.getDireccion().getIdProvincia() != null) {
					tcamp = utilidadesConversiones.getSiglasFromIdProvincia(arrendatario.getDireccion().getIdProvincia()).toString();
				}

				if (campo.equalsIgnoreCase("Municipio arr") && arrendatario.getDireccion().getIdProvincia() != null) {
					tcamp = arrendatario.getDireccion().getIdProvincia() + arrendatario.getDireccion().getIdMunicipio();
				}

				if (campo.equalsIgnoreCase("Pueblo arr")) {
					tcamp = arrendatario.getId().getNif() != null && arrendatario.getDireccion().getPuebloCorreos() != null ? arrendatario.getDireccion().getPuebloCorreos() : "";
				}

				if (campo.equalsIgnoreCase("Cpostal arr") && arrendatario.getDireccion().getCodPostalCorreos() != null) {
					tcamp = arrendatario.getDireccion().getCodPostalCorreos();
				}
			}

			if (campo.equalsIgnoreCase("Pais arr") && arrendatario.getId().getNif() != null) {
				tcamp = ConstantesPDF.VALOR_PAIS;
			}
		}

		if (conductorHabitual != null) {
			if (campo.equalsIgnoreCase("DOI ch")) {
				tcamp = conductorHabitual.getId().getNif();
			}

			if (campo.equalsIgnoreCase("Fecha fin ch") && conductorHabitual.getFechaFin() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", conductorHabitual.getFechaFin());
			}

			if (campo.equalsIgnoreCase("Hora fin ch") && null != conductorHabitual.getHoraFin()) {
				tcamp = conductorHabitual.getHoraFin().replaceAll(":", "");
			}

			if (campo.equalsIgnoreCase("Cambio dom ch")) {
				tcamp = conductorHabitual.getCambioDomicilio() != null && "SI".equals(conductorHabitual.getCambioDomicilio()) ? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
			}

			if (conductorHabitual.getDireccion() != null) {
				if (campo.equalsIgnoreCase("Tipo via ch")) {
					tcamp = conductorHabitual.getDireccion().getIdTipoVia() != null ? conductorHabitual.getDireccion().getIdTipoVia() : "";
				}

				if (campo.equalsIgnoreCase("Nombre via ch")) {
					tcamp = conductorHabitual.getDireccion().getNombreVia() != null ? utilidadesConversiones.ajustarCamposIne(conductorHabitual.getDireccion().getNombreVia()) : "";
				}

				if (campo.equalsIgnoreCase("Numero via ch")) {
					tcamp = conductorHabitual.getDireccion().getNumero();
				}

				if (campo.equalsIgnoreCase("Kilometro ch")) {
					tcamp = conductorHabitual.getDireccion().getKm() != null ? conductorHabitual.getDireccion().getKm().toString() : "";
				}

				if (campo.equalsIgnoreCase("Hectometro ch")) {
					tcamp = conductorHabitual.getDireccion().getHm() != null ? conductorHabitual.getDireccion().getHm().toString() : "";
				}

				if (campo.equalsIgnoreCase("Bloque ch")) {
					tcamp = conductorHabitual.getDireccion().getBloque() != null ? conductorHabitual.getDireccion().getBloque() : "";
				}

				if (campo.equalsIgnoreCase("Portal ch")) {
					tcamp = conductorHabitual.getDireccion().getPuerta() != null ? conductorHabitual.getDireccion().getPuerta() : "";
				}

				if (campo.equalsIgnoreCase("Escalera ch")) {
					tcamp = conductorHabitual.getDireccion().getEscalera() != null ? conductorHabitual.getDireccion().getEscalera() : "";
				}

				if (campo.equalsIgnoreCase("Planta ch")) {
					tcamp = conductorHabitual.getDireccion().getPlanta() != null ? conductorHabitual.getDireccion().getPlanta() : "";
				}

				if (campo.equalsIgnoreCase("Puerta ch")) {
					tcamp = conductorHabitual.getDireccion().getLetra() != null ? conductorHabitual.getDireccion().getLetra() : "";
				}

				if (campo.equalsIgnoreCase("Provincia ch")) {
					tcamp = conductorHabitual.getDireccion().getIdProvincia() != null ? utilidadesConversiones.getSiglasFromIdProvincia(conductorHabitual.getDireccion().getIdProvincia()) : "";
				}

				if (campo.equalsIgnoreCase("Municipio ch") && conductorHabitual.getDireccion().getIdProvincia() != null) {
					tcamp = conductorHabitual.getDireccion().getIdProvincia() + conductorHabitual.getDireccion().getIdMunicipio();
				}

				if (campo.equalsIgnoreCase("Pueblo ch")) {
					tcamp = conductorHabitual.getDireccion().getPuebloCorreos() != null ? conductorHabitual.getDireccion().getPuebloCorreos() : "";
				}

				if (campo.equalsIgnoreCase("Cpostal ch")) {
					tcamp = conductorHabitual.getDireccion().getCodPostalCorreos() != null ? conductorHabitual.getDireccion().getCodPostalCorreos() : "";
				}
			}

			if (campo.equalsIgnoreCase("Pais ch")) {
				tcamp = ConstantesPDF.VALOR_PAIS;
			}
		}

		if (tramite.getVehiculo() != null) {
			if (campo.equalsIgnoreCase("Servicio") && tramite.getVehiculo().getIdServicio() != null) {
				tcamp = tramite.getVehiculo().getIdServicio();
			}

			if (campo.equalsIgnoreCase("Fecha prim matriculacion") && tramite.getVehiculo().getFechaPrimMatri() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaPrimMatri());
			}

			if (campo.equalsIgnoreCase("Bastidor") && tramite.getVehiculo().getBastidor() != null) {
				tcamp = tramite.getVehiculo().getBastidor();
			}

			if (campo.equalsIgnoreCase("NIVE") && tramite.getVehiculo().getNive() != null) {
				tcamp = tramite.getVehiculo().getNive();
			}

			if (campo.equalsIgnoreCase("Kilometraje") && tramite.getVehiculo().getKmUso() != null) {
				tcamp = tramite.getVehiculo().getKmUso().toString();
			}

			if (campo.equalsIgnoreCase("Cuenta Horas") && tramite.getVehiculo().getHorasUso() != null) {
				tcamp = tramite.getVehiculo().getHorasUso().toString();
			}

			if (campo.equalsIgnoreCase("Fecha validez ITV") && tramite.getVehiculo().getFechaItv() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaItv());
			}

			if (campo.equalsIgnoreCase("Importado")) {
				if ((tramite.getVehiculo().getImportado() != null && ("S".equals(tramite.getVehiculo().getImportado()) || "SI".equals(tramite.getVehiculo().getImportado().toUpperCase()))) && tramite
						.getVehiculo().getFechaPrimMatri() != null && ("S".equals(tramite.getVehiculo().getVehiUsado()) || "SI".equals(tramite.getVehiculo().getVehiUsado().toUpperCase()))) {
					tcamp = ConstantesPDF.VALOR_S;
				} else {
					tcamp = ConstantesPDF.VALOR_N;
				}
			}

			if (campo.equalsIgnoreCase("Subasta")) {
				tcamp = tramite.getVehiculo().getSubastado() != null && ("S".equals(tramite.getVehiculo().getSubastado()) || "SI".equals(tramite.getVehiculo().getSubastado().toUpperCase()))
						? ConstantesPDF.VALOR_S : ConstantesPDF.VALOR_N;
			}

			if (campo.equalsIgnoreCase("Usado")) {
				if (tramite.getVehiculo().getFechaPrimMatri() != null && (tramite.getVehiculo().getVehiUsado() != null && ("S".equals(tramite.getVehiculo().getVehiUsado()) || "SI".equals(tramite
						.getVehiculo().getVehiUsado().toUpperCase())))) {
					tcamp = ConstantesPDF.VALOR_S;
				} else {
					tcamp = ConstantesPDF.VALOR_N;
				}
			}

			if (campo.equalsIgnoreCase("Matricula Orig Extr")) {
				tcamp = tramite.getVehiculo().getMatriculaOrigExtr() != null && ("S".equals(tramite.getVehiculo().getVehiUsado()) || "SI".equals(tramite.getVehiculo().getVehiUsado().toUpperCase()))
						&& ("S".equals(tramite.getVehiculo().getImportado()) || "SI".equals(tramite.getVehiculo().getImportado().toUpperCase())) ? tramite.getVehiculo().getMatriculaOrigExtr() : "";
			}

			if (campo.equalsIgnoreCase("Tipo Inspeccion") && tramite.getVehiculo().getIdMotivoItv() != null) {
				tcamp = tramite.getVehiculo().getIdMotivoItv();
			}

			if (campo.equalsIgnoreCase("Fecha prim matriculacion") && tramite.getVehiculo().getFechaPrimMatri() != null) {
				tcamp = utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaPrimMatri());
			}

			DireccionVO direccionVehiculo = null;
			if (tramite.getVehiculo().getDireccion() != null) {
				direccionVehiculo = tramite.getVehiculo().getDireccion();
			} else if (titular != null && titular.getDireccion() != null) {
				direccionVehiculo = titular.getDireccion();
			}

			if (direccionVehiculo != null) {
				if (campo.equalsIgnoreCase("Tipo via veh")) {
					if (direccionVehiculo.getIdTipoVia() != null) {
						tcamp = direccionVehiculo.getIdTipoVia();
					}
				}

				if (campo.equalsIgnoreCase("Nombre via veh")) {
					if (direccionVehiculo.getNombreVia() != null) {
						tcamp = utilidadesConversiones.ajustarCamposIne(direccionVehiculo.getNombreVia());
					}
				}

				if (campo.equalsIgnoreCase("Numero via veh")) {
					if (direccionVehiculo.getNumero() != null) {
						tcamp = direccionVehiculo.getNumero();
					}
				}

				if (campo.equalsIgnoreCase("Kilometro veh")) {
					if (direccionVehiculo.getKm() != null) {
						tcamp = direccionVehiculo.getKm().toString();
					}
				}

				if (campo.equalsIgnoreCase("Hectometro veh")) {
					if (direccionVehiculo.getHm() != null) {
						tcamp = direccionVehiculo.getHm().toString();

					}
				}

				if (campo.equalsIgnoreCase("Bloque veh")) {
					if (direccionVehiculo.getBloque() != null) {
						tcamp = direccionVehiculo.getBloque();
					}
				}

				if (campo.equalsIgnoreCase("Portal veh")) {
					if (direccionVehiculo.getPuerta() != null) {
						tcamp = direccionVehiculo.getPuerta();
					}
				}

				if (campo.equalsIgnoreCase("Escalera veh")) {
					if (direccionVehiculo.getEscalera() != null) {
						tcamp = direccionVehiculo.getEscalera();
					}
				}

				if (campo.equalsIgnoreCase("Planta veh")) {
					if (direccionVehiculo.getPlanta() != null) {
						tcamp = direccionVehiculo.getPlanta();
					}
				}

				if (campo.equalsIgnoreCase("Puerta veh")) {
					if (direccionVehiculo.getLetra() != null) {
						tcamp = direccionVehiculo.getLetra();
					}
				}

				if (campo.equalsIgnoreCase("Provincia veh")) {
					if (direccionVehiculo.getIdProvincia() != null) {
						tcamp = utilidadesConversiones.getSiglasFromIdProvincia(direccionVehiculo.getIdProvincia());
					}
				}

				if (campo.equalsIgnoreCase("Municipio veh")) {
					if (direccionVehiculo.getIdProvincia() != null && direccionVehiculo.getIdMunicipio() != null) {
						tcamp = direccionVehiculo.getIdProvincia() + direccionVehiculo.getIdMunicipio();
					}
				}

				if (campo.equalsIgnoreCase("Pueblo veh")) {
					if (direccionVehiculo.getPuebloCorreos() != null) {
						tcamp = direccionVehiculo.getPuebloCorreos();
					}
				}

				if (campo.equalsIgnoreCase("Cpostal veh")) {
					if (direccionVehiculo.getCodPostalCorreos() != null) {
						tcamp = direccionVehiculo.getCodPostalCorreos();
					}
				}
			}

			if (campo.equalsIgnoreCase("Num homologacion") && tramite.getVehiculo().getnHomologacion() != null) {
				tcamp = tramite.getVehiculo().getnHomologacion();
			}

			if (campo.equalsIgnoreCase("Codigo ITV") && StringUtils.isNotBlank(tramite.getVehiculo().getCoditv()) && !tramite.getVehiculo().getCoditv().equals(ConstantesPDF.SIN_CODIG) && !tramite
					.getVehiculo().getCoditv().equals(ConstantesPDF.SIN_CODIGO)) {
				tcamp = tramite.getVehiculo().getCoditv();
			}

			if (campo.equalsIgnoreCase("Tipo veh industria") && tramite.getVehiculo().getClasificacionItv() != null) {
				tcamp = tramite.getVehiculo().getClasificacionItv();
			}

			if (campo.equalsIgnoreCase("Tipo veh DGT") && tramite.getVehiculo().getTipoVehiculo() != null) {
				if (utilidadesConversiones.containsTipoVehiculoDGT(tramite.getVehiculo().getTipoVehiculo())) {
					tcamp = tramite.getVehiculo().getTipoVehiculo();
				}
			}

			if (campo.equalsIgnoreCase("Procedencia") && tramite.getVehiculo().getProcedencia() != null && ("S".equals(tramite.getVehiculo().getImportado()) || "SI".equals(tramite.getVehiculo()
					.getImportado().toUpperCase()))) {
				tcamp = tramite.getVehiculo().getProcedencia();
			}

			if (campo.equalsIgnoreCase("Marca") && tramite.getVehiculo().getCodigoMarca() != null) {
				tcamp = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarca(), true);
			}

			if (campo.equalsIgnoreCase("Fabricante") && tramite.getVehiculo().getFabricante() != null) {
				tcamp = tramite.getVehiculo().getFabricante();
			}

			if (campo.equalsIgnoreCase("Modelo") && tramite.getVehiculo().getModelo() != null) {
				tcamp = tramite.getVehiculo().getModelo();
			}

			if (campo.equalsIgnoreCase("Color") && tramite.getVehiculo().getIdColor() != null && !"-".equals(tramite.getVehiculo().getIdColor())) {
				tcamp = tramite.getVehiculo().getIdColor();
			}

			if (campo.equalsIgnoreCase("Tipo tarjeta itv") && tramite.getVehiculo().getIdTipoTarjetaItv() != null) {
				tcamp = tramite.getVehiculo().getIdTipoTarjetaItv();
			}

			if (campo.equalsIgnoreCase("Variante") && tramite.getVehiculo().getVariante() != null) {
				tcamp = tramite.getVehiculo().getVariante();
			}

			if (campo.equalsIgnoreCase("Version") && tramite.getVehiculo().getVersion() != null) {
				tcamp = tramite.getVehiculo().getVersion();
			}

			if (campo.equalsIgnoreCase("Potencia fiscal") && tramite.getVehiculo().getPotenciaFiscal() != null) {
				BigDecimal pf = utilidadesConversiones.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(tramite.getVehiculo().getPotenciaFiscal(), ConstantesPDF._13));
				tcamp = pf != null ? pf.toString() : tramite.getVehiculo().getPotenciaFiscal().toString();
			}

			if (campo.equalsIgnoreCase("Cilindrada") && tramite.getVehiculo().getCilindrada() != null) {
				tcamp = tramite.getVehiculo().getCilindrada();
			}

			if (campo.equalsIgnoreCase("Tara") && tramite.getVehiculo().getTara() != null) {
				tcamp = tramite.getVehiculo().getTara();
			}

			if (campo.equalsIgnoreCase("MMA") && tramite.getVehiculo().getPesoMma() != null) {
				tcamp = tramite.getVehiculo().getPesoMma();
			}

			if (campo.equalsIgnoreCase("Plazas") && tramite.getVehiculo().getPlazas() != null) {
				tcamp = tramite.getVehiculo().getPlazas().toString();
			}

			if (campo.equalsIgnoreCase("Tipo Carburante") && tramite.getVehiculo().getIdCarburante() != null && !"-1".equals(tramite.getVehiculo().getIdCarburante())) {
				tcamp = tramite.getVehiculo().getIdCarburante();
			}

			if (campo.equalsIgnoreCase("Relacion potpeso") && tramite.getVehiculo().getPotenciaPeso() != null && !"REMO".equals(tramite.getVehiculo().getTipoVehiculo())) {
				String parteEntera = "";
				String parteDecimal = "";
				BigDecimal RelPotp = tramite.getVehiculo().getPotenciaPeso();

				// Rellena con 0 la parte decimal hasta un total de 4 digitos
				if (RelPotp != null) {
					RelPotp = RelPotp.setScale(4, BigDecimal.ROUND_DOWN);
				} else {
					tramite.getVehiculo().getPotenciaPeso().setScale(4, BigDecimal.ROUND_DOWN);
				}

				if (RelPotp.toString().indexOf('.') != -1) {
					parteEntera = RelPotp.toString().substring(0, RelPotp.toString().indexOf('.'));
				} else {
					parteEntera = RelPotp.toString();
				}
				parteEntera = utiles.rellenarCeros(parteEntera, 2);
				parteDecimal = RelPotp.toString().substring(RelPotp.toString().indexOf('.') + 1);
				tcamp = parteEntera + '.' + parteDecimal;

			}

			if (campo.equalsIgnoreCase("Plazas pie")) {
				tcamp = tramite.getVehiculo().getnPlazasPie() != null ? tramite.getVehiculo().getnPlazasPie().toString() : "0";
			}

			if (campo.equalsIgnoreCase("C02") && (tramite.getVehiculo().getCo2() != null)) {
				tcamp = tramite.getVehiculo().getCo2();
			}

			if (campo.equalsIgnoreCase("Mom") && (tramite.getVehiculo().getMom() != null)) {
				tcamp = tramite.getVehiculo().getMom().toString();
			}

			if (campo.equalsIgnoreCase("Potencia neta") && (tramite.getVehiculo().getPotenciaNeta() != null)) {
				BigDecimal potencia = utiles.stringToBigDecimalTresDecimales(utiles.BigDecimalToStringTresDecimales(tramite.getVehiculo().getPotenciaNeta(), ConstantesPDF._13));
				tcamp = potencia != null ? potencia.toString() : tramite.getVehiculo().getPotenciaNeta().toString();
			}

			if (campo.equalsIgnoreCase("Carroceria") && (tramite.getVehiculo().getCarroceria() != null)) {
				tcamp = tramite.getVehiculo().getCarroceria();
			}

			if (campo.equalsIgnoreCase("Categoria EU") && (tramite.getVehiculo().getIdDirectivaCee() != null && tramite.getVehiculo().getIdDirectivaCee() != null)) {
				tcamp = tramite.getVehiculo().getIdDirectivaCee();
			}

			if (campo.equalsIgnoreCase("Distancia Ejes")) {
				tcamp = tramite.getVehiculo().getDistanciaEjes() != null ? tramite.getVehiculo().getDistanciaEjes().toString() : "0";
			}

			if (campo.equalsIgnoreCase("Masa Tecnica")) {
				tcamp = tramite.getVehiculo().getMtmaItv() != null ? tramite.getVehiculo().getMtmaItv() : "0";
			}

			if (campo.equalsIgnoreCase("Via anterior") && (tramite.getVehiculo().getViaAnterior() != null)) {
				tcamp = tramite.getVehiculo().getViaAnterior() != null ? tramite.getVehiculo().getViaAnterior().toString() : "0";
			}

			if (campo.equalsIgnoreCase("Via posterior") && (tramite.getVehiculo().getViaPosterior() != null)) {
				tcamp = tramite.getVehiculo().getViaPosterior().toString();
			}

			if (campo.equalsIgnoreCase("Codigo ECO") && (tramite.getVehiculo().getCodigoEco() != null)) {
				tcamp = tramite.getVehiculo().getCodigoEco();
			}

			if (campo.equalsIgnoreCase("Consumo")) {
				if (tramite != null && tramite.getVehiculo() != null && tramite.getVehiculo().getConsumo() != null && tramite.getVehiculo().getCategoriaElectrica() != null) {
					String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
					if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
						DecimalFormat formato4 = new DecimalFormat("#0000");
						if (tramite.getVehiculo().getConsumo() != null) {
							tcamp = formato4.format(tramite.getVehiculo().getConsumo());
						} else {
							tcamp = "";
						}
					} else {
						tcamp = tramite.getVehiculo().getConsumo().toString();
					}
				} else {
					tcamp = "";
				}
			}

			if (campo.equalsIgnoreCase("Eco innova")) {
				if (tramite.getVehiculo().getEcoInnovacion() != null && "SI".equals(tramite.getVehiculo().getEcoInnovacion().toUpperCase())) {
					tcamp = ConstantesPDF.VALOR_S;
				} else {
					tcamp = ConstantesPDF.VALOR_N;
				}
			}

			if (campo.equalsIgnoreCase("Nivel emisiones") && tramite.getVehiculo().getNivelEmisiones() != null) {
				tcamp = tramite.getVehiculo().getNivelEmisiones();
			}

			if (campo.equalsIgnoreCase("Reduccion ECO") && tramite.getVehiculo().getReduccionEco() != null) {
				tcamp = tramite.getVehiculo().getReduccionEco().toString();
			}

			if (campo.equalsIgnoreCase("Tipo Alimentacion") && tramite.getVehiculo().getTipoAlimentacion() != null) {
				tcamp = tramite.getVehiculo().getTipoAlimentacion();
			}

			if (campo.equalsIgnoreCase("Pais fabricacion") && tramite.getVehiculo().getPaisFabricacion() != null) {
				tcamp = tramite.getVehiculo().getPaisFabricacion();
			}

			if (campo.equalsIgnoreCase("Tipo") && tramite.getVehiculo().getTipoItv() != null) {
				tcamp = tramite.getVehiculo().getTipoItv();
			}

			if (tramite.getVehiculo().getIdTipoTarjetaItv() != null && ("A".equals(tramite.getVehiculo().getIdTipoTarjetaItv().substring(0, 1)) || "D".equals(tramite.getVehiculo()
					.getIdTipoTarjetaItv().substring(0, 1)))) {
				if (campo.equalsIgnoreCase("Marca Base") && tramite.getVehiculo().getCodigoMarcaBase() != null) {
					tcamp = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarcaBase(), true);
				}

				if (campo.equalsIgnoreCase("Fabricante base") && tramite.getVehiculo().getFabricanteBase() != null) {
					tcamp = tramite.getVehiculo().getFabricanteBase();
				}

				if (campo.equalsIgnoreCase("Tipo base") && tramite.getVehiculo().getTipoBase() != null) {
					tcamp = tramite.getVehiculo().getTipoBase();
				}

				if (campo.equalsIgnoreCase("Variante base") && tramite.getVehiculo().getVarianteBase() != null) {
					tcamp = tramite.getVehiculo().getVarianteBase();
				}

				if (campo.equalsIgnoreCase("Version base") && tramite.getVehiculo().getVersionBase() != null) {
					tcamp = tramite.getVehiculo().getVersionBase();
				}

				if (campo.equalsIgnoreCase("Num homologacion base") && tramite.getVehiculo().getnHomologacionBase() != null) {
					tcamp = tramite.getVehiculo().getnHomologacionBase();
				}

				if (campo.equalsIgnoreCase("Mom base") && tramite.getVehiculo().getMomBase() != null) {
					tcamp = tramite.getVehiculo().getMomBase().toString();
				}
			}

			if (campo.equalsIgnoreCase("Categoria electrica") && tramite.getVehiculo().getCategoriaElectrica() != null) {
				tcamp = tramite.getVehiculo().getCategoriaElectrica();
			}

			if (campo.equalsIgnoreCase("Autonomia electrica") && tramite.getVehiculo().getAutonomiaElectrica() != null) {
				String autonomiaElectrica = tramite.getVehiculo().getAutonomiaElectrica() != null ? tramite.getVehiculo().getAutonomiaElectrica().toString() : "";
				String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
				if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
					DecimalFormat formato4 = new DecimalFormat("#0000");
					if (autonomiaElectrica != null && !autonomiaElectrica.isEmpty()) {
						autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
					}
				}
				tcamp = autonomiaElectrica;
			}
		}

		return tcamp;
	}

	public String obtenerNubeBaja(TramiteTrafBajaVO tramite) {
		StringBuffer line = new StringBuffer();

		IntervinienteTraficoVO titular = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Titular.getValorEnum());
		IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Adquiriente.getValorEnum());

		line.append(ConstantesPDF.VERSION_BAJA);
		line.append(ConstantesPDF.PROGRAMA_BAJA);

		if (tramite.getTasa() == null || StringUtils.isBlank(tramite.getTasa().getCodigoTasa())) {
			line.append(changeSize("", ConstantesPDF.TAM_TASA));
		} else if (tramite.getTasa().getCodigoTasa().equals("EXENTO")) {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA));
		} else {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA, '0', false));
		}

		if (tramite.getFechaPresentacion() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_PRESENTACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getFechaPresentacion()), ConstantesPDF.TAM_FECHA_PRESENTACION, '0', false));
		}

		if (tramite.getVehiculo() == null || StringUtils.isBlank(tramite.getVehiculo().getMatricula())) {
			line.append(changeSize("", ConstantesPDF.TAM_MATRICULA));
		} else {
			line.append(changeSize(utilidadesConversiones.cambiaFormatoMatricula(tramite.getVehiculo().getMatricula()), ConstantesPDF.TAM_MATRICULA));
		}

		if (tramite.getVehiculo() == null || tramite.getVehiculo().getFechaMatriculacion() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_MATRICULACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaMatriculacion()), ConstantesPDF.TAM_FECHA_MATRICULACION, '0', false));
		}

		if (titular == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_TITULAR));
		} else {
			line.append(changeSize(titular.getId().getNif(), ConstantesPDF.TAM_DNI_TITULAR));
		}

		if (StringUtils.isBlank(tramite.getMotivoBaja())) {
			line.append(changeSize("", ConstantesPDF.TAM_MOTIVO_BAJA));
		} else {
			line.append(changeSize(tramite.getMotivoBaja(), ConstantesPDF.TAM_MOTIVO_BAJA));
		}

		if (adquiriente != null && StringUtils.isNotBlank(adquiriente.getPersona().getSexo())) {
			if (ConstantesPDF.PERSONA_JURIDICA.equals(adquiriente.getPersona().getSexo())) {
				if (adquiriente.getPersona().getApellido1RazonSocial() == null) {
					line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
				} else {
					line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
				}
			} else {
				if (adquiriente != null) {
					if (adquiriente.getPersona().getApellido1RazonSocial() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO));
					} else {
						line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO));
					}
					if (adquiriente.getPersona().getApellido2() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_SEGUNDO_APELLIDO));
					} else {
						line.append(changeSize(titular.getPersona().getApellido2(), ConstantesPDF.TAM_SEGUNDO_APELLIDO));
					}
					if (adquiriente.getPersona().getNombre() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_TITULAR));
					} else {
						line.append(changeSize(adquiriente.getPersona().getNombre(), ConstantesPDF.TAM_NOMBRE_TITULAR));
					}
				}
			}
		} else {
			line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
		}

		String sexo = adquiriente != null && adquiriente.getPersona().getSexo() != null ? adquiriente.getPersona().getSexo() : null;
		if (StringUtils.isBlank(sexo)) {
			line.append(changeSize("", ConstantesPDF.TAM_SEXO_TITULAR));
		} else if (ConstantesPDF.SEXO_HEMBRA.equals(sexo)) {
			line.append(changeSize(ConstantesPDF.SEXO_MUJER, ConstantesPDF.TAM_SEXO_TITULAR));
		} else {
			line.append(changeSize(adquiriente.getPersona().getSexo(), ConstantesPDF.TAM_SEXO_TITULAR));
		}

		if (adquiriente == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getId().getNif(), ConstantesPDF.TAM_DNI_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getPersona().getFechaNacimiento() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", adquiriente.getPersona().getFechaNacimiento()), ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdTipoVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdTipoVia(), ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNombreVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			String nombreVia = utilidadesConversiones.quitarCaracteresExtranios(utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getNombreVia()));
			if (nombreVia.length() > ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE);
			}
			line.append(changeSize(nombreVia, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNumero() == null || "S/N".equals(adquiriente.getDireccion().getNumero().toUpperCase()) || "SN"
				.equals(adquiriente.getDireccion().getNumero().toUpperCase())) {
			line.append(changeSize("SN", ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getNumero(), ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getKm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getKm().toString(), ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getHm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getHm().toString(), ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getBloque() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getBloque(), ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPortal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPortal(), ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getEscalera() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getEscalera(), ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPlanta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPlanta(), ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPuerta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPuerta(), ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia(), ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia() + adquiriente.getDireccion().getIdMunicipio(), ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPueblo() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPueblo(), ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getCodPostal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getCodPostal(), ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		}

		if ("B06".equals(tramite.getVehiculo().getIdServicio())) {
			String jefaturas = gestorPropiedades.valorPropertie("jefatura.cema.baja");
			if (StringUtils.isNotBlank(jefaturas) && ("TODOS".equals(jefaturas) || jefaturasCema(jefaturas, tramite.getJefaturaTrafico()))) {
				String cema = tramite.getCema();
				if (cema == null || cema.isEmpty()) {
					line.append(VALOR_CET_PREDETERMINADO);
				} else {
					line.append(changeSize(cema, ConstantesPDF.TAM_CEMA));
				}
			} else {
				if ((MotivoBaja.DefTC.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.DefE.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.DefRP.getValorEnum().equals(tramite
						.getMotivoBaja()) || MotivoBaja.DefV.getValorEnum().equals(tramite.getMotivoBaja())) && "B06".equals(tramite.getVehiculo().getIdServicio())) {
					String cema = tramite.getCema();
					if (cema == null || cema.isEmpty()) {
						line.append(VALOR_CET_PREDETERMINADO);
					} else {
						line.append(changeSize(cema, ConstantesPDF.TAM_CEMA));
					}
				}
			}
		}
		return line.toString();
	}

	private boolean jefaturasCema(String jefaturas, JefaturaTraficoVO jefaturaTrafico) {
		if (jefaturaTrafico != null) {
			String[] listaJefaturas = jefaturas.split(",");
			if (listaJefaturas != null && listaJefaturas.length > 0) {
				for (String jef : listaJefaturas) {
					if (jef != null && jef.equals(jefaturaTrafico.getJefaturaProvincial())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public String obtenerNubeCtitParaBaja(TramiteTrafTranVO tramite) {
		StringBuffer line = new StringBuffer();

		IntervinienteTraficoVO titular = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.TransmitenteTrafico.getValorEnum());
		IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Compraventa.getValorEnum());

		line.append(ConstantesPDF.VERSION_BAJA);
		line.append(ConstantesPDF.PROGRAMA_BAJA);

		if (tramite.getTasa() == null || StringUtils.isBlank(tramite.getTasa().getCodigoTasa())) {
			line.append(changeSize("", ConstantesPDF.TAM_TASA));
		} else if (tramite.getTasa().getCodigoTasa().equals("EXENTO")) {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA));
		} else {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA, '0', false));
		}

		if (tramite.getFechaPresentacion() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_PRESENTACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getFechaPresentacion()), ConstantesPDF.TAM_FECHA_PRESENTACION, '0', false));
		}

		if (tramite.getVehiculo() == null || StringUtils.isBlank(tramite.getVehiculo().getMatricula())) {
			line.append(changeSize("", ConstantesPDF.TAM_MATRICULA));
		} else {
			line.append(changeSize(utilidadesConversiones.cambiaFormatoMatricula(tramite.getVehiculo().getMatricula()), ConstantesPDF.TAM_MATRICULA));
		}

		if (tramite.getVehiculo() == null || tramite.getVehiculo().getFechaPrimMatri() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_MATRICULACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaPrimMatri()), ConstantesPDF.TAM_FECHA_MATRICULACION, '0', false));
		}

		if (titular == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_TITULAR));
		} else {
			line.append(changeSize(titular.getId().getNif(), ConstantesPDF.TAM_DNI_TITULAR));
		}

		line.append(changeSize(MotivoBaja.TempT.getValorEnum(), ConstantesPDF.TAM_MOTIVO_BAJA));

		if (adquiriente == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getId().getNif(), ConstantesPDF.TAM_DNI_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getPersona().getFechaNacimiento() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", adquiriente.getPersona().getFechaNacimiento()), ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		if (adquiriente != null && StringUtils.isNotBlank(adquiriente.getPersona().getSexo())) {
			if (ConstantesPDF.PERSONA_JURIDICA.equals(adquiriente.getPersona().getSexo())) {
				if (adquiriente.getPersona().getApellido1RazonSocial() == null) {
					line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
				} else {
					line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
				}
			} else {
				if (adquiriente != null) {
					if (adquiriente.getPersona().getApellido1RazonSocial() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO));
					} else {
						line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO));
					}
					if (adquiriente.getPersona().getApellido2() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_SEGUNDO_APELLIDO));
					} else {
						line.append(changeSize(adquiriente.getPersona().getApellido2(), ConstantesPDF.TAM_SEGUNDO_APELLIDO));
					}
					if (adquiriente.getPersona().getNombre() == null) {
						line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_TITULAR));
					} else {
						line.append(changeSize(adquiriente.getPersona().getNombre(), ConstantesPDF.TAM_NOMBRE_TITULAR));
					}
				}
			}
		} else {
			line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
		}

		String sexo = adquiriente != null && adquiriente.getPersona().getSexo() != null ? adquiriente.getPersona().getSexo() : null;
		if (StringUtils.isBlank(sexo)) {
			line.append(changeSize("", ConstantesPDF.TAM_SEXO_TITULAR));
		} else if (ConstantesPDF.SEXO_HEMBRA.equals(sexo)) {
			line.append(changeSize(ConstantesPDF.SEXO_MUJER, ConstantesPDF.TAM_SEXO_TITULAR));
		} else {
			line.append(changeSize(adquiriente.getPersona().getSexo(), ConstantesPDF.TAM_SEXO_TITULAR));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdTipoVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdTipoVia(), ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNombreVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			String nombreVia = utilidadesConversiones.quitarCaracteresExtranios(utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getNombreVia()));
			if (nombreVia.length() > ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE);
			}
			line.append(changeSize(nombreVia, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNumero() == null || "S/N".equals(adquiriente.getDireccion().getNumero().toUpperCase()) || "SN"
				.equals(adquiriente.getDireccion().getNumero().toUpperCase())) {
			line.append(changeSize("SN", ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getNumero(), ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getKm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getKm().toString(), ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getHm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getHm().toString(), ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getBloque() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getBloque(), ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPortal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPortal(), ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getEscalera() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getEscalera(), ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPlanta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPlanta(), ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPuerta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPuerta(), ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia(), ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia() + adquiriente.getDireccion().getIdMunicipio(), ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPueblo() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPueblo(), ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getCodPostal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getCodPostal(), ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		}

		if (StringUtils.isBlank(tramite.getCetItp())) {
			line.append(VALOR_CET_PREDETERMINADO);
		} else {
			line.append(changeSize(tramite.getCetItp(), ConstantesPDF.TAM_CEM));
		}

		return line.toString();
	}

	public String obtenerNubeCtit(TramiteTrafTranVO tramite, ContratoVO contrato) {
		StringBuffer line = new StringBuffer();

		IntervinienteTraficoVO transmitente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.TransmitenteTrafico.getValorEnum());

		IntervinienteTraficoVO adquiriente = null;
		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			adquiriente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Compraventa.getValorEnum());
		} else {
			adquiriente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.Adquiriente.getValorEnum());
		}

		IntervinienteTraficoVO repreAdquiriente = buscarInterviniente(tramite.getIntervinienteTraficosAsList(), TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		line.append(ConstantesPDF.VERSION_TRANSMISION);
		line.append(ConstantesPDF.PROGRAMA_TRANSMISION);

		if (tramite.getTasa() == null || StringUtils.isBlank(tramite.getTasa().getCodigoTasa())) {
			line.append(changeSize("", ConstantesPDF.TAM_TASA));
		} else if (tramite.getTasa().getCodigoTasa().equals("EXENTO")) {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA));
		} else {
			line.append(changeSize(tramite.getTasa().getCodigoTasa(), ConstantesPDF.TAM_TASA, '0', false));
		}

		if (tramite.getVehiculo() == null || StringUtils.isBlank(tramite.getVehiculo().getMatricula())) {
			line.append(changeSize("", ConstantesPDF.TAM_MATRICULA));
		} else {
			line.append(changeSize(utilidadesConversiones.cambiaFormatoMatricula(tramite.getVehiculo().getMatricula()), ConstantesPDF.TAM_MATRICULA));
		}

		if (tramite.getVehiculo() == null || tramite.getVehiculo().getFechaMatriculacion() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_MATRICULACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaMatriculacion()), ConstantesPDF.TAM_FECHA_MATRICULACION, '0', false));
		}

		if (tramite.getFechaPresentacion() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_PRESENTACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getFechaPresentacion()), ConstantesPDF.TAM_FECHA_PRESENTACION, '0', false));
		}

		if (tramite.getJefaturaTrafico() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_JEFATURA_TRAFICO));
		} else {
			line.append(changeSize(tramite.getJefaturaTrafico().getJefatura(), ConstantesPDF.TAM_JEFATURA_TRAFICO));
		}

		if (transmitente == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_TRANSMITENTE));
		} else {
			line.append(changeSize(transmitente.getId().getNif(), ConstantesPDF.TAM_DNI_TRANSMITENTE));
		}

		if (adquiriente == null) {
			line.append(changeSize("", ConstantesPDF.TAM_DNI_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getId().getNif(), ConstantesPDF.TAM_DNI_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getPersona().getFechaNacimiento() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", adquiriente.getPersona().getFechaNacimiento()), ConstantesPDF.TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		if (adquiriente == null || StringUtils.isBlank(adquiriente.getPersona().getSexo()) || ConstantesPDF.PERSONA_JURIDICA.equals(adquiriente.getPersona().getSexo())) {
			if (adquiriente == null || adquiriente.getPersona().getApellido1RazonSocial() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			if (adquiriente.getPersona().getApellido1RazonSocial() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO));
			} else {
				line.append(changeSize(adquiriente.getPersona().getApellido1RazonSocial(), ConstantesPDF.TAM_PRIMER_APELLIDO));
			}
			if (adquiriente.getPersona().getApellido2() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_SEGUNDO_APELLIDO));
			} else {
				line.append(changeSize(adquiriente.getPersona().getApellido2(), ConstantesPDF.TAM_SEGUNDO_APELLIDO));
			}
			if (adquiriente.getPersona().getNombre() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_TITULAR));
			} else {
				line.append(changeSize(adquiriente.getPersona().getNombre(), ConstantesPDF.TAM_NOMBRE_TITULAR));
			}
		}

		if (adquiriente == null || StringUtils.isBlank(adquiriente.getPersona().getSexo())) {
			line.append(changeSize("", ConstantesPDF.TAM_SEXO_ADQUIRENTE));
		} else if (ConstantesPDF.SEXO_HEMBRA.equals(adquiriente.getPersona().getSexo())) {
			line.append(changeSize(ConstantesPDF.SEXO_MUJER, ConstantesPDF.TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getPersona().getSexo(), ConstantesPDF.TAM_SEXO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdTipoVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdTipoVia(), ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNombreVia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			String nombreVia = utilidadesConversiones.quitarCaracteresExtranios(utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getNombreVia()));
			if (nombreVia.length() > ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE);
			}
			line.append(changeSize(nombreVia, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNumero() == null || "S/N".equals(adquiriente.getDireccion().getNumero().toUpperCase()) || "SN"
				.equals(adquiriente.getDireccion().getNumero().toUpperCase())) {
			line.append(changeSize("SN", ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getNumero(), ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getKm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getKm().toString(), ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getHm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getHm().toString(), ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getBloque() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getBloque(), ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPortal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPortal(), ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getEscalera() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getEscalera(), ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPlanta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPlanta(), ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPuerta() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getPuerta(), ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia(), ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getIdProvincia() + adquiriente.getDireccion().getIdMunicipio(), ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPueblo() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		} else {
			String pueblo = utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getPueblo());
			line.append(changeSize(pueblo, ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
		}

		if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getCodPostal() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(changeSize(adquiriente.getDireccion().getCodPostal(), ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
		}

		if (tramite.getVehiculo().getDireccion() == null || "SI".equals(tramite.getConsentimientocambio())) {
			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdTipoVia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getIdTipoVia(), ConstantesPDF.TAM_TIPO_VIA_DOMICILIO_ADQUIRIENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNombreVia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
			} else {
				String nombreVia = utilidadesConversiones.quitarCaracteresExtranios(utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getNombreVia()));
				if (nombreVia.length() > ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE) {
					nombreVia = nombreVia.substring(0, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE);
				}
				line.append(changeSize(nombreVia, ConstantesPDF.TAM_NOMBRE_VIA_DOMICILIO_ADQUIRIENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getNumero() == null || "S/N".equals(adquiriente.getDireccion().getNumero().toUpperCase())
					|| "SN".equals(adquiriente.getDireccion().getNumero().toUpperCase())) {
				line.append(changeSize("SN", ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getNumero(), ConstantesPDF.TAM_NUM_VIA_DOMICILIO_ADQUIRIENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getKm() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getKm().toString(), ConstantesPDF.TAM_KILOMETRO_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getHm() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getHm().toString(), ConstantesPDF.TAM_HECTOMETRO_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getBloque() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getBloque(), ConstantesPDF.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPortal() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getPortal(), ConstantesPDF.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getEscalera() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getEscalera(), ConstantesPDF.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPlanta() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getPlanta(), ConstantesPDF.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPuerta() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getPuerta(), ConstantesPDF.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getIdProvincia(), ConstantesPDF.TAM_PROVINCIA_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getIdProvincia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getIdProvincia() + adquiriente.getDireccion().getIdMunicipio(), ConstantesPDF.TAM_MUNICIPO_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getPueblo() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
			} else {
				String pueblo = utilidadesConversiones.ajustarCamposIne(adquiriente.getDireccion().getPueblo());
				line.append(changeSize(pueblo, ConstantesPDF.TAM_PUEBLO_DOMICILIO_ADQUIRENTE));
			}

			if (adquiriente == null || adquiriente.getDireccion() == null || adquiriente.getDireccion().getCodPostal() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(changeSize(adquiriente.getDireccion().getCodPostal(), ConstantesPDF.TAM_CP_DOMICILIO_ADQUIRENTE));
			}
		} else {
			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getIdTipoVia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_TIPO_VIA_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getIdTipoVia(), ConstantesPDF.TAM_TIPO_VIA_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getNombreVia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_VIA_VEHICULO));
			} else {
				String nombreVia = utilidadesConversiones.quitarCaracteresExtranios(utilidadesConversiones.ajustarCamposIne(tramite.getVehiculo().getDireccion().getNombreVia()));
				if (nombreVia.length() > ConstantesPDF.TAM_VIA_VEHICULO) {
					nombreVia = nombreVia.substring(0, ConstantesPDF.TAM_VIA_VEHICULO);
				}
				line.append(changeSize(nombreVia, ConstantesPDF.TAM_VIA_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getNumero() == null || "S/N".equals(adquiriente.getDireccion()
					.getNumero().toUpperCase()) || "SN".equals(tramite.getVehiculo().getDireccion().getNumero().toUpperCase())) {
				line.append(changeSize("SN", ConstantesPDF.TAM_NUM_VIA_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getNumero(), ConstantesPDF.TAM_NUM_VIA_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getKm() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_KM_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getKm().toString(), ConstantesPDF.TAM_KM_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getHm() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_HM_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getHm().toString(), ConstantesPDF.TAM_HM_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getBloque() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_BLOQUE_DOMICILIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getBloque(), ConstantesPDF.TAM_BLOQUE_DOMICILIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getPortal() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PORTAL_DOMICILIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getPortal(), ConstantesPDF.TAM_PORTAL_DOMICILIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getEscalera() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_ESCALERA_DOMICILIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getEscalera(), ConstantesPDF.TAM_ESCALERA_DOMICILIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getPlanta() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PLANTA_DOMICILIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getPlanta(), ConstantesPDF.TAM_PLANTA_DOMICILIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getPuerta() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PUERTA_DOMICILIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getPuerta(), ConstantesPDF.TAM_PUERTA_DOMICILIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getIdProvincia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getIdProvincia(), ConstantesPDF.TAM_PROVINCIA_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getIdProvincia() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_MUNICIPIO_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getIdProvincia() + tramite.getVehiculo().getDireccion().getIdMunicipio(), ConstantesPDF.TAM_MUNICIPIO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getPueblo() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_PUEBLO_VEHICULO));
			} else {
				String pueblo = utilidadesConversiones.ajustarCamposIne(tramite.getVehiculo().getDireccion().getPueblo());
				line.append(changeSize(pueblo, ConstantesPDF.TAM_PUEBLO_VEHICULO));
			}

			if (tramite.getVehiculo() == null || tramite.getVehiculo().getDireccion() == null || tramite.getVehiculo().getDireccion().getCodPostal() == null) {
				line.append(changeSize("", ConstantesPDF.TAM_CP_VEHICULO));
			} else {
				line.append(changeSize(tramite.getVehiculo().getDireccion().getCodPostal(), ConstantesPDF.TAM_CP_VEHICULO));
			}
		}

		if (tramite.getVehiculo() == null || tramite.getVehiculo().getFechaItv() == null) {
			line.append(changeSize("EXENTO", ConstantesPDF.TAM_FECHA_ITV));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getVehiculo().getFechaItv()), ConstantesPDF.TAM_FECHA_ITV, '0', false));
		}

		if (tramite.getVehiculo() == null || tramite.getVehiculo().getIdMotivoItv() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_MOTIVO_ITV));
		} else {
			String motivo = servicioVehiculo.obtenerDescripcionMotivoItv(tramite.getVehiculo().getIdMotivoItv());
			line.append(changeSize(motivo, ConstantesPDF.TAM_MOTIVO_ITV));
		}

		String tipoTransferencia = utilidadesConversiones.valorEnumTipoTransferenciaNotEnt(tramite.getTipoTransferencia());
		if (tramite.getTipoTransferencia() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_TIPO_TRANSFERENCIA));
		} else {
			if ("1".equals(tipoTransferencia) && (tramite.getImprPermisoCircu() == null || "NO".equals(tramite.getImprPermisoCircu()))) {
				line.append(changeSize("N", ConstantesPDF.TAM_TIPO_TRANSFERENCIA));
			} else {
				line.append(changeSize(tipoTransferencia, ConstantesPDF.TAM_TIPO_TRANSFERENCIA));
			}
		}

		if (repreAdquiriente != null && ConceptoTutela.Tutela.getValorEnum().equals(repreAdquiriente.getConceptoRepre())) {
			line.append(changeSize(ConstantesPDF.VALOR_SI, ConstantesPDF.TAM_TUTELA));
		} else {
			line.append(changeSize("", ConstantesPDF.TAM_TUTELA));
		}

		if (StringUtils.isBlank(tramite.getIedtm())) {
			line.append(changeSize("", ConstantesPDF.TAM_TIPO_LIMITACION));
		} else {
			line.append(changeSize(ConstantesPDF.VALOR_S, ConstantesPDF.TAM_TIPO_LIMITACION));
		}

		if (tramite.getFechaIedtm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FECHA_LIMITACION));
		} else {
			line.append(changeSize(utilesFecha.formatoFecha("yyyyMMdd", tramite.getFechaIedtm()), ConstantesPDF.TAM_FECHA_LIMITACION, '0', false));
		}

		if (tramite.getnRegIedtm() == null || "-1".equals(tramite.getnRegIedtm())) {
			line.append(changeSize("", ConstantesPDF.TAM_NUM_REG));
		} else {
			line.append(changeSize(tramite.getnRegIedtm(), ConstantesPDF.TAM_NUM_REG));
		}

		if (tramite.getFinancieraIedtm() == null) {
			line.append(changeSize("", ConstantesPDF.TAM_FINANCIERA_LIMITACION));
		} else {
			line.append(changeSize(tramite.getFinancieraIedtm(), ConstantesPDF.TAM_FINANCIERA_LIMITACION));
		}

		if (tramite.getCetItp() == null) {
			line.append(ConstantesPDF.VALOR_CET_PREDETERMINADO);
		} else {
			line.append(changeSize(tramite.getCetItp(), ConstantesPDF.TAM_CET));
		}

		if (StringUtils.isBlank(tramite.getCema())) {
			line.append(ConstantesPDF.VALOR_CET_PREDETERMINADO);
		} else {
			line.append(changeSize(tramite.getCema(), ConstantesPDF.TAM_CEM));
		}

		if (StringUtils.isBlank(tramite.getCetItp()) || "00000000".equals(tramite.getCetItp())) {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_CET));
		} else if (tramite.getIdProvinciaCet() != null && tramite.getIdProvinciaCet().equals(contrato.getProvincia().getIdProvincia())) {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_CET));
		} else if (tramite.getIdProvinciaCet() != null && !tramite.getIdProvinciaCet().equals(contrato.getProvincia().getIdProvincia()) && !"-1".equals(tramite.getIdProvinciaCet())) {
			line.append(changeSize(utilidadesConversiones.getSiglasFromIdProvincia(tramite.getIdProvinciaCet()), ConstantesPDF.TAM_PROVINCIA_CET));
		} else {
			line.append(changeSize("", ConstantesPDF.TAM_PROVINCIA_CET));
		}

		return line.toString();
	}

	public String getDatosNubePuntosListadoBastidores(List<TramiteTrafMatrVO> listaTramites) {
		String nube = "";
		if (listaTramites != null && !listaTramites.isEmpty()) {
			boolean primerCaracter = false;
			for (TramiteTrafMatrVO tramite : listaTramites) {
				if (primerCaracter) {
					nube = nube + ";";
				}
				if (tramite.getVehiculo() != null && tramite.getVehiculo().getMatricula() != null) {
					nube = nube + tramite.getVehiculo().getMatricula();
				}
				primerCaracter = true;
			}
		}
		return nube;
	}

	private IntervinienteTraficoVO buscarInterviniente(List<IntervinienteTraficoVO> listaIntervinientes, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (listaIntervinientes != null && !listaIntervinientes.isEmpty()) {
			for (IntervinienteTraficoVO inter : listaIntervinientes) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}
}
