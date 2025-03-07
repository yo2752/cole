package trafico.utiles;

import java.util.HashMap;
import java.util.Map;

public class UtilesMATW {

	Map<String,Integer> stringCampoNubeLongitud = new HashMap<>();

	// CAMPOS MANCHA PDF 1 1379 CARACTERES
	// NUBE1 -->POSICIONES DEL ARRAY [0]..[110]

	// CAMPOS MANCHA PDF 2 580 CARACTERES
	// NUBE2 --> POSICIONES DEL ARRAY [111]..[158]

	private static String[][] arrayCamposNubeMATW = {{"Nº Expediente","20"},{"Tasa", "12"},{"Codigo ITV","9"},{"Tipo Tarjeta ITV","2"},{"Marca Base", "23"},{"Marca", "23"},
		{"Fabricante base","70"},{"Fabricante","70"},{"Modelo", "22"},{"Color","2"},{"Procedencia", "3"},{"Bastidor","21"},{"NIVE", "32"},{"Tipo Tramite", "2"},
		{"Matricula Origen","9"},{"Matricula Orig Extr","9"},{"Tipo Inspeccion", "1"},{"Servicio","3"},{"Tipo veh DGT","2"},{"Potencia fiscal", "13"},{"Cilindrada","6"},
		{"Tara", "7"},{"MMA", "6"},{"Plazas","3"},{"Tipo Carburante","3"},
		// Datos dirección vehículo
		{"Tipo via veh", "5"},{"Nombre via veh", "50"},{"Numero via veh","5"},{"Kilometro veh", "5"},{"Hectometro veh","7"},{"Bloque veh", "5"},
		{"Portal veh", "5"},{"Escalera veh","5"},{"Planta veh", "5"},{"Puerta veh","5"},{"Provincia veh", "2"},{"Municipio veh", "5"},{"Pueblo veh","74"},
		{"Cpostal veh", "5"},{"Pais veh","5"},

		// Datos Titular
		{"DOI Titular","9"},{"DOI Represent titular", "9"},{"Fecha nacimiento titular","8"},{"Apellido1 titular","20"},{"Apellido2 titular", "20"},
		{"Nombre Titular", "20"},{"Razon social", "70"},{"Sexo titular", "1"},{"Cambio domic titular","1"},		
		{"Tipo via tit", "5"},{"Nombre via tit", "50"},{"Numero via tit","5"},{"Kilometro tit", "5"},{"Hectometro tit","7"},{"Bloque tit", "5"},{"Portal tit", "5"},
		{"Escalera tit","5"},{"Planta tit", "5"},{"Puerta tit","5"},{"Provincia tit", "2"},{"Municipio tit", "5"},{"Pueblo tit","74"},{"Cpostal tit", "5"},
		{"Pais tit","5"},

		// Datos Técnicos
		{"Fecha validez ITV", "8"},{"Fecha prim matriculacion", "8"},{"Tutela", "1"},{"Justificado IVTM","1"},{"Tipo base", "25"},{"Tipo", "25"},{"Variante base", "25"},{"Variante", "25"},
		{"Version base","35"},{"Version","35"},{"Num homologacion base", "50"},{"Num homologacion", "50"},{"Masa Tecnica", "6"},{"Mom base", "7"},{"Mom", "7"},{"Potencia neta","13"},{"Relacion potpeso", "7"},{"Plazas pie","3"},
		{"Carroceria", "5"},{"Consumo", "4"},{"Distancia Ejes", "4"},{"Via anterior","4"},{"Via posterior", "4"},{"Tipo Alimentacion","1"},{"Categoria electrica", "4"},{"Autonomia electrica", "4"}, {"Eco innova", "1"},
		{"Reduccion ECO", "4"},{"Codigo ECO","25"},{"Nivel emisiones","15"},{"Subasta", "1"},{"Importado","1"},{"Categoria EU","7"},{"Pais fabricacion", "1"},
		{"C02", "7"},{"Tipo veh industria", "4"},

		// Impuestos
		{"CEM","8"},{"CEMA", "8"},{"Exencion CEM", "10"},
		{"Renting","1"},{"Kilometraje", "6"},{"Usado", "1"},{"Cuenta Horas","6"},
		{"Autonomo","1"},{"Codigo IAE", "4"},
		{"Conductor Habitual", "1"},

		// Datos Tutor
		{"DOI Tutor","9"},{"Tipo tutela", "1"},

		// DATOS NUBE 2 -->POS_NUBE2
		// Datos Arrendatario
		{"DOI arr","9"},{"Fech ini Renting","8"},{"Fech fin Renting", "8"},{"Hora ini Renting","4"},{"Hora fin Renting", "4"},{"Fecha nacimiento arr","8"},
		{"Apellido1 arr","20"},{"Apellido2 arr", "20"},{"Nombre arr", "20"},{"Sexo arr", "1"},{"Cambio domic arr","1"},
		{"Tipo via arr", "5"},{"Nombre via arr", "50"},{"Numero via arr","5"},{"Kilometro arr", "5"},{"Hectometro arr","7"},{"Bloque arr", "5"},
		{"Portal arr", "5"},{"Escalera arr","5"},{"Planta arr", "5"},{"Puerta arr","5"},{"Provincia arr", "2"},{"Municipio arr", "5"},{"Pueblo arr","74"},
		{"Cpostal arr", "5"},{"Pais arr","5"},{"Razon social arr", "70"},{"DOI Reprent arr", "9"},

		// Datos Conductor Habitual
		{"DOI ch","9"},{"Fecha fin ch", "8"},{"Hora fin ch", "4"},{"Cambio dom ch","1"},
		{"Tipo via ch", "5"},{"Nombre via ch", "50"},{"Numero via ch","5"},{"Kilometro ch", "5"},{"Hectometro ch","7"},{"Bloque ch", "5"},{"Portal ch", "5"},
		{"Escalera ch","5"},{"Planta ch", "5"},{"Puerta ch","5"},{"Provincia ch", "2"},{"Municipio ch", "5"},{"Pueblo ch","74"},{"Cpostal ch", "5"},{"Pais ch","5"}
	};

	public UtilesMATW() {
		inicializarLongitudCamposNube();
	}

	private void inicializarLongitudCamposNube() {
		// Inicialización de los campos de las nube
		for (int i=0; i< arrayCamposNubeMATW.length; i++) {
			stringCampoNubeLongitud.put(arrayCamposNubeMATW[i][0],Integer.parseInt(arrayCamposNubeMATW[i][1]));
		}
	}

	/**
	 * Devuelve la longitud del campo pasado por parámetro
	 */
	public int getLongitudCampoNube(String campo) {
		return stringCampoNubeLongitud.get(campo);
	}

	/**
	 * Devuelve la longitud de la matriz de campos
	 */
	public int getLongitudArrayCamposNube() {
		return stringCampoNubeLongitud.size();
	}

	public String getCampoValorArray(int i) {
		return arrayCamposNubeMATW[i][0];
	}

}