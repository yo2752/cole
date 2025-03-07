package org.gestoresmadrid.oegamConversiones.conversion.impl;

import java.util.HashMap;
import java.util.Map;

import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.springframework.stereotype.Component;

@Component
public class ParametrosConversionesImpl implements ParametrosConversiones {

	private static final long serialVersionUID = -2855750633130497814L;
	
	private Map<String, String> provinciaDGTtoTabla = new HashMap<String, String>();
	private Map<String, String> provinciaTablatoDGT = new HashMap<String, String>();
	private Map<String, String> stringTipoViaDGTtoTipoViaDGT = new HashMap<String, String>();
	private Map<String, String> stringIdTipoViaToIdTipoDGT = new HashMap<String, String>();


	public ParametrosConversionesImpl() {
		inicializarProvincia();
		inicializarTipoViaDGT();
		inicializarTablaProvincias();
		inicializarIdTipoViaToIdTipoDGT();
	}

	private void inicializarTipoViaDGT() {
		stringTipoViaDGTtoTipoViaDGT.put("AC", "60");
		stringTipoViaDGTtoTipoViaDGT.put("AL", "39"); // ALAMEDA
		stringTipoViaDGTtoTipoViaDGT.put("AP", "40"); // APARTAMENTO
		stringTipoViaDGTtoTipoViaDGT.put("AV", "2"); // AVENIDA
		stringTipoViaDGTtoTipoViaDGT.put("BJ", "67"); // BAJADA
		stringTipoViaDGTtoTipoViaDGT.put("BL", "42"); // BLOQUE
		stringTipoViaDGTtoTipoViaDGT.put("BO", "4"); // BARRIO
		stringTipoViaDGTtoTipoViaDGT.put("BR", "68"); // BARRANCO
		stringTipoViaDGTtoTipoViaDGT.put("C", "6"); // CALLE
		stringTipoViaDGTtoTipoViaDGT.put("CA", "79"); // CANAL (53)
		stringTipoViaDGTtoTipoViaDGT.put("CH", "43"); // CHALET
		stringTipoViaDGTtoTipoViaDGT.put("CJ", "51"); // CALLEJON
		stringTipoViaDGTtoTipoViaDGT.put("CL", "41"); // CALLE
		stringTipoViaDGTtoTipoViaDGT.put("CM", "5"); // CAMINO
		stringTipoViaDGTtoTipoViaDGT.put("CN", "44"); // CAMINO
		stringTipoViaDGTtoTipoViaDGT.put("CO", "45"); // COLONIA
		stringTipoViaDGTtoTipoViaDGT.put("CR", "7"); // CARRETERA

		stringTipoViaDGTtoTipoViaDGT.put("CS", "46"); // CASERIO
		stringTipoViaDGTtoTipoViaDGT.put("CT", "15"); // CUESTA
		stringTipoViaDGTtoTipoViaDGT.put("ED", "47"); // EDIFICIO
		stringTipoViaDGTtoTipoViaDGT.put("GL", "48"); // GLORIETA
		stringTipoViaDGTtoTipoViaDGT.put("GR", "49"); // GRUPO
		stringTipoViaDGTtoTipoViaDGT.put("GV", "9"); // GRAN VIA
		stringTipoViaDGTtoTipoViaDGT.put("LG", "50");// LUGAR

		stringTipoViaDGTtoTipoViaDGT.put("ME", "10"); // MERCADO
		stringTipoViaDGTtoTipoViaDGT.put("MU", "21"); // MUNICIPIO
		stringTipoViaDGTtoTipoViaDGT.put("MZ", "22"); // MANZANA
		stringTipoViaDGTtoTipoViaDGT.put("OT", "23"); // OTROS
		stringTipoViaDGTtoTipoViaDGT.put("PA", "24"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("PB", "25"); // POBLADO
		stringTipoViaDGTtoTipoViaDGT.put("PD", "26"); // PARTIDA
		stringTipoViaDGTtoTipoViaDGT.put("PG", "27"); // POLIGONO
		stringTipoViaDGTtoTipoViaDGT.put("PJ", "28"); // PASAJE
		stringTipoViaDGTtoTipoViaDGT.put("PL", "29"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("PQ", "30"); // PARQUE
		stringTipoViaDGTtoTipoViaDGT.put("PR", "31"); // PROLONGACION
		stringTipoViaDGTtoTipoViaDGT.put("PS", "32"); // PASEO
		stringTipoViaDGTtoTipoViaDGT.put("PT", "33"); // PARTIDA
		stringTipoViaDGTtoTipoViaDGT.put("PU", "122"); // PUENTE (58)
		stringTipoViaDGTtoTipoViaDGT.put("PZ", "29"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("RB", "35"); // RAMBLA
		stringTipoViaDGTtoTipoViaDGT.put("RD", "36"); // RONDA

		stringTipoViaDGTtoTipoViaDGT.put("TR", "37"); // TRAVESIA
		stringTipoViaDGTtoTipoViaDGT.put("UR", "38"); // URBANIZACION

		stringTipoViaDGTtoTipoViaDGT.put("AC", "60");
		stringTipoViaDGTtoTipoViaDGT.put("AD", "61");
		stringTipoViaDGTtoTipoViaDGT.put("AR", "63");
		stringTipoViaDGTtoTipoViaDGT.put("AU", "65");
		stringTipoViaDGTtoTipoViaDGT.put("AV", "66");
		stringTipoViaDGTtoTipoViaDGT.put("BD", "69");
		stringTipoViaDGTtoTipoViaDGT.put("CP", "77");
		stringTipoViaDGTtoTipoViaDGT.put("LG", "78");
		stringTipoViaDGTtoTipoViaDGT.put("CI", "84");
		stringTipoViaDGTtoTipoViaDGT.put("CV", "87");
		stringTipoViaDGTtoTipoViaDGT.put("CN", "89");
		stringTipoViaDGTtoTipoViaDGT.put("EN", "97");
		stringTipoViaDGTtoTipoViaDGT.put("ES", "99");
		stringTipoViaDGTtoTipoViaDGT.put("EX", "100");
		stringTipoViaDGTtoTipoViaDGT.put("EM", "101");
		stringTipoViaDGTtoTipoViaDGT.put("ER", "102");
		stringTipoViaDGTtoTipoViaDGT.put("FN", "103");
		stringTipoViaDGTtoTipoViaDGT.put("GA", "104");
		stringTipoViaDGTtoTipoViaDGT.put("GR", "105");
		stringTipoViaDGTtoTipoViaDGT.put("JR", "106");
		stringTipoViaDGTtoTipoViaDGT.put("LD", "107");
		stringTipoViaDGTtoTipoViaDGT.put("MT", "110");
		stringTipoViaDGTtoTipoViaDGT.put("ML", "111");
		stringTipoViaDGTtoTipoViaDGT.put("PE", "113");
		stringTipoViaDGTtoTipoViaDGT.put("PU", "122");
		stringTipoViaDGTtoTipoViaDGT.put("PT", "124");
		stringTipoViaDGTtoTipoViaDGT.put("RM", "126");
		stringTipoViaDGTtoTipoViaDGT.put("RR", "127");
		stringTipoViaDGTtoTipoViaDGT.put("RC", "128");
		stringTipoViaDGTtoTipoViaDGT.put("SC", "131");
		stringTipoViaDGTtoTipoViaDGT.put("SD", "132");
		stringTipoViaDGTtoTipoViaDGT.put("SD", "133");
		stringTipoViaDGTtoTipoViaDGT.put("SB", "134");
		stringTipoViaDGTtoTipoViaDGT.put("DE", "135");
		stringTipoViaDGTtoTipoViaDGT.put("TR", "136");
		stringTipoViaDGTtoTipoViaDGT.put("VR", "139");
		stringTipoViaDGTtoTipoViaDGT.put("VI", "140");
		stringTipoViaDGTtoTipoViaDGT.put("ZN", "143");
		stringTipoViaDGTtoTipoViaDGT.put("AY", "144");
	}

	private void inicializarIdTipoViaToIdTipoDGT() {
		stringIdTipoViaToIdTipoDGT.put("ALAM", "39"); // ALAMEDA
		stringIdTipoViaToIdTipoDGT.put("APTOS", "40"); // APARTAMENTOS
		stringIdTipoViaToIdTipoDGT.put("AVIA", "66"); // AUTOVIA
		stringIdTipoViaToIdTipoDGT.put("AVDA", "2"); // AVENIDA
		stringIdTipoViaToIdTipoDGT.put("BJADA", "67"); // BAJADA
		stringIdTipoViaToIdTipoDGT.put("BARRO", "4"); // BARRIO
		stringIdTipoViaToIdTipoDGT.put("BLQUE", "42");// BLOQUE
		stringIdTipoViaToIdTipoDGT.put("CLLON", "51"); // CALLEJON
		stringIdTipoViaToIdTipoDGT.put("CALLE", "41"); // CALLE
		stringIdTipoViaToIdTipoDGT.put("CMNO", "5");// CAMINO
		stringIdTipoViaToIdTipoDGT.put("CMN0", "44"); // CAMINO
		stringIdTipoViaToIdTipoDGT.put("COL", "45"); // COLONIA
		stringIdTipoViaToIdTipoDGT.put("CTRA", "7"); // CARRETERA
		stringIdTipoViaToIdTipoDGT.put("CSRIO", "46");// CASERIO
		stringIdTipoViaToIdTipoDGT.put("CUSTA", "15"); // CUESTA

		stringIdTipoViaToIdTipoDGT.put("GTA", "48"); // GLORIETA
		stringIdTipoViaToIdTipoDGT.put("CJTO", "75"); // GRUPO
		stringIdTipoViaToIdTipoDGT.put("VIA", "11"); // VIA (9)
		stringIdTipoViaToIdTipoDGT.put("COSTA", "54"); // COSTA

		stringIdTipoViaToIdTipoDGT.put("ZONA", "12"); // ZONA (21)

		stringIdTipoViaToIdTipoDGT.put("PLAZA", "29"); // PLAZA (24)
		stringIdTipoViaToIdTipoDGT.put("PBDO", "25"); // POBLADO

		stringIdTipoViaToIdTipoDGT.put("POLIG", "27"); // POLIGONO
		stringIdTipoViaToIdTipoDGT.put("PSAJE", "28"); // PASAJE
		stringIdTipoViaToIdTipoDGT.put("PLAZA", "29"); // PLAZA
		stringIdTipoViaToIdTipoDGT.put("PQUE", "30"); // PARQUE
		stringIdTipoViaToIdTipoDGT.put("PROL", "31"); // PROLONGACION
		stringIdTipoViaToIdTipoDGT.put("PASEO", "32"); // PASEO
		stringIdTipoViaToIdTipoDGT.put("PTDA", "33"); // PARTIDA
		stringIdTipoViaToIdTipoDGT.put("PNTE", "88");// PUENTE (58)
		stringIdTipoViaToIdTipoDGT.put("RBLA", "35"); // RAMBLA
		stringIdTipoViaToIdTipoDGT.put("RONDA", "36"); // RONDA
		stringIdTipoViaToIdTipoDGT.put("CRRDO", "41");// SENDERO (55)
		stringIdTipoViaToIdTipoDGT.put("SBIDA", "3"); // SUBIDA (60)
		stringIdTipoViaToIdTipoDGT.put("PSLLO", "28"); // TRANSITO
		stringIdTipoViaToIdTipoDGT.put("TRVA", "37"); // TRAVESIA
		stringIdTipoViaToIdTipoDGT.put("URB", "38"); // URBANIZACION

		stringIdTipoViaToIdTipoDGT.put("LUGAR", "50");
		stringIdTipoViaToIdTipoDGT.put("PARTI", "43");
		stringIdTipoViaToIdTipoDGT.put("GRUPO", "49");
		stringIdTipoViaToIdTipoDGT.put(".", "23");
		stringIdTipoViaToIdTipoDGT.put("G.V.", "9");

		stringIdTipoViaToIdTipoDGT.put("ACCES", "60");
		stringIdTipoViaToIdTipoDGT.put("ALDEA", "61");
		stringIdTipoViaToIdTipoDGT.put("ALTO", "41");
		stringIdTipoViaToIdTipoDGT.put("ARRAL", "63");
		stringIdTipoViaToIdTipoDGT.put("ATAJO", "41");
		stringIdTipoViaToIdTipoDGT.put("AUTO", "65");

		stringIdTipoViaToIdTipoDGT.put("BRANC", "68");
		stringIdTipoViaToIdTipoDGT.put("BARDA", "69");
		stringIdTipoViaToIdTipoDGT.put("BULEV", "41");
		stringIdTipoViaToIdTipoDGT.put("CLLJA", "71");

		stringIdTipoViaToIdTipoDGT.put("CJLA", "41");
		stringIdTipoViaToIdTipoDGT.put("CZADA", "41");
		stringIdTipoViaToIdTipoDGT.put("CAMI", "5");
		stringIdTipoViaToIdTipoDGT.put("CAMIN", "5");
		stringIdTipoViaToIdTipoDGT.put("CAMPA", "77");
		stringIdTipoViaToIdTipoDGT.put("CANAL", "72");
		stringIdTipoViaToIdTipoDGT.put("CÑADA", "73");
		stringIdTipoViaToIdTipoDGT.put("CRA", "7");
		stringIdTipoViaToIdTipoDGT.put("CTRIN", "7");
		stringIdTipoViaToIdTipoDGT.put("CRRIL", "62");
		stringIdTipoViaToIdTipoDGT.put("CERRO", "41");
		stringIdTipoViaToIdTipoDGT.put("CINT", "84");
		stringIdTipoViaToIdTipoDGT.put("C.H.", "5");
		stringIdTipoViaToIdTipoDGT.put("C.N.", "5");
		stringIdTipoViaToIdTipoDGT.put("C.V.", "74");
		stringIdTipoViaToIdTipoDGT.put("COMPJ", "89");

		stringIdTipoViaToIdTipoDGT.put("CRRAL", "41");
		stringIdTipoViaToIdTipoDGT.put("CRRLO", "41");
		stringIdTipoViaToIdTipoDGT.put("CRRDE", "41");

		stringIdTipoViaToIdTipoDGT.put("CRTJO", "46");

		stringIdTipoViaToIdTipoDGT.put("CSTAN", "41");
		stringIdTipoViaToIdTipoDGT.put("ENTD", "97");
		stringIdTipoViaToIdTipoDGT.put("ESCA", "99");
		stringIdTipoViaToIdTipoDGT.put("ESCAL", "99");
		stringIdTipoViaToIdTipoDGT.put("EXPLA", "100");
		stringIdTipoViaToIdTipoDGT.put("EXTRM", "76");
		stringIdTipoViaToIdTipoDGT.put("EXTRR", "80");
		stringIdTipoViaToIdTipoDGT.put("FINCA", "81");
		stringIdTipoViaToIdTipoDGT.put("GALE", "104");
		stringIdTipoViaToIdTipoDGT.put("GRUP", "105");
		stringIdTipoViaToIdTipoDGT.put("JDIN", "82");
		stringIdTipoViaToIdTipoDGT.put("LDERA", "107");
		stringIdTipoViaToIdTipoDGT.put("MALEC", "41");
		stringIdTipoViaToIdTipoDGT.put("MIRAD", "41");
		stringIdTipoViaToIdTipoDGT.put("MONTE", "83");
		stringIdTipoViaToIdTipoDGT.put("MUELL", "85");
		stringIdTipoViaToIdTipoDGT.put("PAGO", "41");
		stringIdTipoViaToIdTipoDGT.put("PRAJE", "86");
		stringIdTipoViaToIdTipoDGT.put("PARKE", "30");
		stringIdTipoViaToIdTipoDGT.put("PARC", "30");
		stringIdTipoViaToIdTipoDGT.put("PZO", "28");

		stringIdTipoViaToIdTipoDGT.put("PISTA", "28");
		stringIdTipoViaToIdTipoDGT.put("PLCET", "29");
		stringIdTipoViaToIdTipoDGT.put("PZTA", "29");
		stringIdTipoViaToIdTipoDGT.put("PLZLA", "29");
		stringIdTipoViaToIdTipoDGT.put("PTLLO", "122");
		stringIdTipoViaToIdTipoDGT.put("PRAZA", "29");

		stringIdTipoViaToIdTipoDGT.put("PTO", "111");
		stringIdTipoViaToIdTipoDGT.put("RAMAL", "126");
		stringIdTipoViaToIdTipoDGT.put("RBRA", "127");
		stringIdTipoViaToIdTipoDGT.put("RCON", "128");
		stringIdTipoViaToIdTipoDGT.put("RCDA", "90");
		stringIdTipoViaToIdTipoDGT.put("RTDA", "48");
		stringIdTipoViaToIdTipoDGT.put("SECT", "91");
		stringIdTipoViaToIdTipoDGT.put("SENDA", "92");
		stringIdTipoViaToIdTipoDGT.put("SEND", "64");

		stringIdTipoViaToIdTipoDGT.put("TRAS", "135");
		stringIdTipoViaToIdTipoDGT.put("TRAV", "136");
		stringIdTipoViaToIdTipoDGT.put("VALLE", "110");
		stringIdTipoViaToIdTipoDGT.put("VEGA", "110");
		stringIdTipoViaToIdTipoDGT.put("VREDA", "8");

		stringIdTipoViaToIdTipoDGT.put("VCTO", "79");
		stringIdTipoViaToIdTipoDGT.put("VIAL", "41");

		stringIdTipoViaToIdTipoDGT.put("ARRYO", "13");
	}

	@Override
	public void inicializarProvincia() {
		provinciaDGTtoTabla.put("VI", "01");
		provinciaDGTtoTabla.put("AB", "02");
		provinciaDGTtoTabla.put("A", "03");
		provinciaDGTtoTabla.put("AL", "04");
		provinciaDGTtoTabla.put("AV", "05");
		provinciaDGTtoTabla.put("BA", "06");
		provinciaDGTtoTabla.put("IB", "07");

		provinciaDGTtoTabla.put("B", "08");
		provinciaDGTtoTabla.put("BU", "09");
		provinciaDGTtoTabla.put("CC", "10");
		provinciaDGTtoTabla.put("CA", "11");
		provinciaDGTtoTabla.put("CS", "12");
		provinciaDGTtoTabla.put("CR", "13");
		provinciaDGTtoTabla.put("CO", "14");
		provinciaDGTtoTabla.put("C", "15");
		provinciaDGTtoTabla.put("CU", "16");
		provinciaDGTtoTabla.put("GI", "17");

		provinciaDGTtoTabla.put("GR", "18");
		provinciaDGTtoTabla.put("GU", "19");
		provinciaDGTtoTabla.put("SS", "20");
		provinciaDGTtoTabla.put("H", "21");
		provinciaDGTtoTabla.put("HU", "22");
		provinciaDGTtoTabla.put("J", "23");
		provinciaDGTtoTabla.put("LE", "24");
		provinciaDGTtoTabla.put("L", "25");
		provinciaDGTtoTabla.put("LO", "26");
		provinciaDGTtoTabla.put("LU", "27");
		provinciaDGTtoTabla.put("M", "28");
		provinciaDGTtoTabla.put("MA", "29");
		provinciaDGTtoTabla.put("MU", "30");
		provinciaDGTtoTabla.put("NA", "31");
		provinciaDGTtoTabla.put("OU", "32");

		provinciaDGTtoTabla.put("O", "33");
		provinciaDGTtoTabla.put("P", "34");
		provinciaDGTtoTabla.put("GC", "35");
		provinciaDGTtoTabla.put("PO", "36");
		provinciaDGTtoTabla.put("SA", "37");
		provinciaDGTtoTabla.put("TF", "38");
		provinciaDGTtoTabla.put("S", "39");
		provinciaDGTtoTabla.put("SG", "40");
		provinciaDGTtoTabla.put("SE", "41");
		provinciaDGTtoTabla.put("SO", "42");
		provinciaDGTtoTabla.put("T", "43");
		provinciaDGTtoTabla.put("TE", "44");
		provinciaDGTtoTabla.put("TO", "45");
		provinciaDGTtoTabla.put("V", "46");
		provinciaDGTtoTabla.put("VA", "47");
		provinciaDGTtoTabla.put("BI", "48");
		provinciaDGTtoTabla.put("ZA", "49");
		provinciaDGTtoTabla.put("Z", "50");
		provinciaDGTtoTabla.put("CE", "51");
		provinciaDGTtoTabla.put("ML", "52");
	}

	@Override
	public void inicializarTablaProvincias() {
		provinciaTablatoDGT.put("01", "VI");
		provinciaTablatoDGT.put("02", "AB");
		provinciaTablatoDGT.put("03", "A");
		provinciaTablatoDGT.put("04", "AL");
		provinciaTablatoDGT.put("05", "AV");
		provinciaTablatoDGT.put("06", "BA");
		provinciaTablatoDGT.put("07","IB");

		provinciaTablatoDGT.put("08", "B");
		provinciaTablatoDGT.put("09", "BU");
		provinciaTablatoDGT.put("10", "CC");
		provinciaTablatoDGT.put("11", "CA");
		provinciaTablatoDGT.put("12", "CS");
		provinciaTablatoDGT.put("13", "CR");
		provinciaTablatoDGT.put("14", "CO");
		provinciaTablatoDGT.put("15", "C");
		provinciaTablatoDGT.put("16", "CU");
		provinciaTablatoDGT.put("17", "GI");

		provinciaTablatoDGT.put("18", "GR");
		provinciaTablatoDGT.put("19", "GU");
		provinciaTablatoDGT.put("20", "SS");
		provinciaTablatoDGT.put("21", "H");
		provinciaTablatoDGT.put("22", "HU");
		provinciaTablatoDGT.put("23", "J");
		provinciaTablatoDGT.put("24", "LE");
		provinciaTablatoDGT.put("25", "L");
		provinciaTablatoDGT.put("26", "LO");
		provinciaTablatoDGT.put("27", "LU");
		provinciaTablatoDGT.put("28", "M");
		provinciaTablatoDGT.put("29", "MA");
		provinciaTablatoDGT.put("30", "MU");
		provinciaTablatoDGT.put("31", "NA");
		provinciaTablatoDGT.put("32","OU");

		provinciaTablatoDGT.put("33", "O");
		provinciaTablatoDGT.put("34", "P");
		provinciaTablatoDGT.put("35", "GC");
		provinciaTablatoDGT.put("36", "PO");
		provinciaTablatoDGT.put("37", "SA");
		provinciaTablatoDGT.put("38", "TF");
		provinciaTablatoDGT.put("39", "S");
		provinciaTablatoDGT.put("40", "SG");
		provinciaTablatoDGT.put("41", "SE");
		provinciaTablatoDGT.put("42", "SO");
		provinciaTablatoDGT.put("43", "T");
		provinciaTablatoDGT.put("44", "TE");
		provinciaTablatoDGT.put("45", "TO");
		provinciaTablatoDGT.put("46", "V");
		provinciaTablatoDGT.put("47", "VA");
		provinciaTablatoDGT.put("48", "BI");
		provinciaTablatoDGT.put("49", "ZA");
		provinciaTablatoDGT.put("50", "Z");
		provinciaTablatoDGT.put("51", "CE");
		provinciaTablatoDGT.put("52", "ML");
	}

	/**
	 * Devuelve el código de las siglas DGT según el código de la tabla de provincias.
	 */
	@Override
	public String getSiglasFromIdProvincia(String idProvincia) {
		if (idProvincia == null)
			return "";
		return provinciaTablatoDGT.get(idProvincia.trim());
	}

}
