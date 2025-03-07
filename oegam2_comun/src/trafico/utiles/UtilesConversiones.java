package trafico.utiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDgtMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDgtProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtProvinciaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import trafico.beans.ServicioTraficoBean;
import trafico.modelo.ModeloTrafico;

@Component
public class UtilesConversiones implements Serializable{

	private static final long serialVersionUID = -77043436751338457L;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioDgtProvincia servicioDgtProvincia;

	@Autowired
	ServicioDgtMunicipio ServicioDgtMunicipio;

	Map<String,String> provinciaDGTtoTabla = new HashMap<String,String>();
	Map<String,String> provinciaTablatoDGT = new HashMap<String,String>();
	Map<String,String> stringTipoViaDGTtoTipoViaDGT = new HashMap<String,String>();
	Map<String,String> stringIdTipoViaToIdTipoDGT = new HashMap<String,String>();

	private ModeloTrafico modeloTrafico;

	public UtilesConversiones() {
		inicializarProvincia();
		inicializarTipoViaDGT();
		inicializarTablaProvincias();
		inicializarIdTipoViaToIdTipoDGT();
	}

	private void inicializarTipoViaDGT() {
		stringTipoViaDGTtoTipoViaDGT.put("AC","60");
		stringTipoViaDGTtoTipoViaDGT.put("AL","39"); // ALAMEDA
		stringTipoViaDGTtoTipoViaDGT.put("AP","40"); // APARTAMENTO
		stringTipoViaDGTtoTipoViaDGT.put("AV","2"); // AVENIDA
		stringTipoViaDGTtoTipoViaDGT.put("BJ","67"); // BAJADA
		stringTipoViaDGTtoTipoViaDGT.put("BL","42"); // BLOQUE
		stringTipoViaDGTtoTipoViaDGT.put("BO","4"); // BARRIO
		stringTipoViaDGTtoTipoViaDGT.put("BR","68"); // BARRANCO
		stringTipoViaDGTtoTipoViaDGT.put("C","6"); // CALLE
		stringTipoViaDGTtoTipoViaDGT.put("CA","79"); // CANAL (53)
		stringTipoViaDGTtoTipoViaDGT.put("CH","43"); // CHALET
		stringTipoViaDGTtoTipoViaDGT.put("CJ","51"); // CALLEJON
		stringTipoViaDGTtoTipoViaDGT.put("CL","41"); // CALLE
		stringTipoViaDGTtoTipoViaDGT.put("CM","5"); // CAMINO
		stringTipoViaDGTtoTipoViaDGT.put("CN","44"); // CAMINO
		stringTipoViaDGTtoTipoViaDGT.put("CO","45"); // COLONIA
		stringTipoViaDGTtoTipoViaDGT.put("CR","7"); //CARRETERA

		stringTipoViaDGTtoTipoViaDGT.put("CS","46"); // CASERIO
		stringTipoViaDGTtoTipoViaDGT.put("CT","15"); // CUESTA
		stringTipoViaDGTtoTipoViaDGT.put("ED","47"); // EDIFICIO
		stringTipoViaDGTtoTipoViaDGT.put("GL","48"); // GLORIETA
		stringTipoViaDGTtoTipoViaDGT.put("GR","49"); // GRUPO
		stringTipoViaDGTtoTipoViaDGT.put("GV","9"); // GRAN VIA
		stringTipoViaDGTtoTipoViaDGT.put("LG","50");// LUGAR

		// NUEVA
		//stringTipoViaDGTtoTipoViaDGT.put("LI","54");// LITORAL

		stringTipoViaDGTtoTipoViaDGT.put("ME","10"); //MERCADO
		stringTipoViaDGTtoTipoViaDGT.put("MU","21"); //MUNICIPIO
		stringTipoViaDGTtoTipoViaDGT.put("MZ","22"); // MANZANA
		stringTipoViaDGTtoTipoViaDGT.put("OT","23"); // OTROS
		stringTipoViaDGTtoTipoViaDGT.put("PA","24"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("PB","25"); // POBLADO
		stringTipoViaDGTtoTipoViaDGT.put("PD","26"); // PARTIDA
		stringTipoViaDGTtoTipoViaDGT.put("PG","27"); // POLIGONO
		stringTipoViaDGTtoTipoViaDGT.put("PJ","28"); //PASAJE
		stringTipoViaDGTtoTipoViaDGT.put("PL","29"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("PQ","30"); // PARQUE
		stringTipoViaDGTtoTipoViaDGT.put("PR","31"); // PROLONGACION
		stringTipoViaDGTtoTipoViaDGT.put("PS","32"); // PASEO
		stringTipoViaDGTtoTipoViaDGT.put("PT","33"); // PARTIDA
		stringTipoViaDGTtoTipoViaDGT.put("PU","122"); // PUENTE (58)
		stringTipoViaDGTtoTipoViaDGT.put("PZ","34"); // PLAZA
		stringTipoViaDGTtoTipoViaDGT.put("RB","35"); // RAMBLA
		stringTipoViaDGTtoTipoViaDGT.put("RD","36"); // RONDA
		// NUEVAS
		//stringTipoViaDGTtoTipoViaDGT.put("SE","55"); // SENDERO
		//stringTipoViaDGTtoTipoViaDGT.put("SU","60"); // SUBIDA
		//stringTipoViaDGTtoTipoViaDGT.put("TN","59"); // TRANSITO

		stringTipoViaDGTtoTipoViaDGT.put("TR","37"); //TRAVESIA
		stringTipoViaDGTtoTipoViaDGT.put("UR","38"); // URBANIZACION

		stringTipoViaDGTtoTipoViaDGT.put("AC","60");
		stringTipoViaDGTtoTipoViaDGT.put("AD","61");
		stringTipoViaDGTtoTipoViaDGT.put("AR","63");
		stringTipoViaDGTtoTipoViaDGT.put("AU","65");
		stringTipoViaDGTtoTipoViaDGT.put("AV","66");
		stringTipoViaDGTtoTipoViaDGT.put("BD","69");
		stringTipoViaDGTtoTipoViaDGT.put("CP","77");
		stringTipoViaDGTtoTipoViaDGT.put("LG","78");
		//stringTipoViaDGTtoTipoViaDGT.put("CR","80");
		stringTipoViaDGTtoTipoViaDGT.put("CI","84");
		stringTipoViaDGTtoTipoViaDGT.put("CV","87");
		stringTipoViaDGTtoTipoViaDGT.put("CN","89");
		stringTipoViaDGTtoTipoViaDGT.put("EN","97");
		stringTipoViaDGTtoTipoViaDGT.put("ES","99");
		stringTipoViaDGTtoTipoViaDGT.put("EX","100");
		stringTipoViaDGTtoTipoViaDGT.put("EM","101");
		stringTipoViaDGTtoTipoViaDGT.put("ER","102");
		stringTipoViaDGTtoTipoViaDGT.put("FN","103");
		stringTipoViaDGTtoTipoViaDGT.put("GA","104");
		stringTipoViaDGTtoTipoViaDGT.put("GR","105");
		stringTipoViaDGTtoTipoViaDGT.put("JR","106");
		stringTipoViaDGTtoTipoViaDGT.put("LD","107");
		stringTipoViaDGTtoTipoViaDGT.put("MT","110");
		stringTipoViaDGTtoTipoViaDGT.put("ML","111");
		stringTipoViaDGTtoTipoViaDGT.put("PE","113");
		stringTipoViaDGTtoTipoViaDGT.put("PU","122");
		stringTipoViaDGTtoTipoViaDGT.put("PT","124");
		stringTipoViaDGTtoTipoViaDGT.put("RM","126");
		stringTipoViaDGTtoTipoViaDGT.put("RR","127");
		stringTipoViaDGTtoTipoViaDGT.put("RC","128");
		stringTipoViaDGTtoTipoViaDGT.put("SC","131");
		stringTipoViaDGTtoTipoViaDGT.put("SD","132");
		stringTipoViaDGTtoTipoViaDGT.put("SD","133");
		stringTipoViaDGTtoTipoViaDGT.put("SB","134");
		stringTipoViaDGTtoTipoViaDGT.put("DE","135");
		stringTipoViaDGTtoTipoViaDGT.put("TR","136");
		stringTipoViaDGTtoTipoViaDGT.put("VR","139");
		stringTipoViaDGTtoTipoViaDGT.put("VI","140");
		stringTipoViaDGTtoTipoViaDGT.put("ZN","143");
		stringTipoViaDGTtoTipoViaDGT.put("AY","144");
	}

	/*private void inicializarIdTipoViaToIdTipoDGT(){
		stringIdTipoViaToIdTipoDGT.put("ALAM","39");
		stringIdTipoViaToIdTipoDGT.put("APTOS","40");
		stringIdTipoViaToIdTipoDGT.put("AVDA","2");
		stringIdTipoViaToIdTipoDGT.put("BARRO","4");
		stringIdTipoViaToIdTipoDGT.put("BLQUE","42");
		stringIdTipoViaToIdTipoDGT.put("CALLE","41");
		stringIdTipoViaToIdTipoDGT.put("CMNO","5");
		stringIdTipoViaToIdTipoDGT.put("COL","45");
		stringIdTipoViaToIdTipoDGT.put("CSRIO","46");
		stringIdTipoViaToIdTipoDGT.put("CTRA","7");
		stringIdTipoViaToIdTipoDGT.put("CUSTA","15");
		stringIdTipoViaToIdTipoDGT.put("GTA","48");
		stringIdTipoViaToIdTipoDGT.put("LUGAR","50");
		stringIdTipoViaToIdTipoDGT.put("PARTI","43");
		stringIdTipoViaToIdTipoDGT.put("PASEO","32");
		stringIdTipoViaToIdTipoDGT.put("PBDO","25");
		stringIdTipoViaToIdTipoDGT.put("PLAZA","29");
		stringIdTipoViaToIdTipoDGT.put("POLIG","27");
		stringIdTipoViaToIdTipoDGT.put("PQUE","30");
		stringIdTipoViaToIdTipoDGT.put("PROL","31");
		stringIdTipoViaToIdTipoDGT.put("PSAJE","28");
		stringIdTipoViaToIdTipoDGT.put("RBLA","35");
		stringIdTipoViaToIdTipoDGT.put("RONDA","36");
		stringIdTipoViaToIdTipoDGT.put("TRVA","37");
		stringIdTipoViaToIdTipoDGT.put("URB","38");
		stringIdTipoViaToIdTipoDGT.put("GRUPO","49");
		stringIdTipoViaToIdTipoDGT.put(".","23");
		stringIdTipoViaToIdTipoDGT.put("G.V.","9");
		stringIdTipoViaToIdTipoDGT.put("PTDA","33");
		stringIdTipoViaToIdTipoDGT.put("ACCES","60");
		stringIdTipoViaToIdTipoDGT.put("ALDEA","61");
		stringIdTipoViaToIdTipoDGT.put("ALTO","41");
		stringIdTipoViaToIdTipoDGT.put("ARRAL","63");
		stringIdTipoViaToIdTipoDGT.put("ATAJO","41");
		stringIdTipoViaToIdTipoDGT.put("AUTO","65");
		stringIdTipoViaToIdTipoDGT.put("AVIA","66");
		stringIdTipoViaToIdTipoDGT.put("BJADA","67");
		stringIdTipoViaToIdTipoDGT.put("BRANC","68");
		stringIdTipoViaToIdTipoDGT.put("BARDA","69");
		stringIdTipoViaToIdTipoDGT.put("BULEV","41");
		stringIdTipoViaToIdTipoDGT.put("CLLJA","71");
		stringIdTipoViaToIdTipoDGT.put("CLLON","41");
		stringIdTipoViaToIdTipoDGT.put("CJLA","41");
		stringIdTipoViaToIdTipoDGT.put("CZADA","41");
		stringIdTipoViaToIdTipoDGT.put("CAMI","5");
		stringIdTipoViaToIdTipoDGT.put("CAMIN","5");
		stringIdTipoViaToIdTipoDGT.put("CAMPA","77");
		stringIdTipoViaToIdTipoDGT.put("CANAL","78");
		stringIdTipoViaToIdTipoDGT.put("CÑADA","79");
		stringIdTipoViaToIdTipoDGT.put("CRA","7");
		stringIdTipoViaToIdTipoDGT.put("CTRIN","7");
		stringIdTipoViaToIdTipoDGT.put("CRRIL","62");
		stringIdTipoViaToIdTipoDGT.put("CERRO","41");
		stringIdTipoViaToIdTipoDGT.put("CINT","84");
		stringIdTipoViaToIdTipoDGT.put("C.H.","5");
		stringIdTipoViaToIdTipoDGT.put("C.N.","5");
		stringIdTipoViaToIdTipoDGT.put("C.V.","87");
		stringIdTipoViaToIdTipoDGT.put("COMPJ","89");
		stringIdTipoViaToIdTipoDGT.put("CJTO","89");
		stringIdTipoViaToIdTipoDGT.put("CRRAL","41");
		stringIdTipoViaToIdTipoDGT.put("CRRLO","41");
		stringIdTipoViaToIdTipoDGT.put("CRRDE","41");
		stringIdTipoViaToIdTipoDGT.put("CRRDO","41");
		stringIdTipoViaToIdTipoDGT.put("CRTJO","46");
		stringIdTipoViaToIdTipoDGT.put("COSTA","41");
		stringIdTipoViaToIdTipoDGT.put("CSTAN","41");
		stringIdTipoViaToIdTipoDGT.put("ENTD","97");
		stringIdTipoViaToIdTipoDGT.put("ESCA","99");
		stringIdTipoViaToIdTipoDGT.put("ESCAL","99");
		stringIdTipoViaToIdTipoDGT.put("EXPLA","100");
		stringIdTipoViaToIdTipoDGT.put("EXTRM","101");
		stringIdTipoViaToIdTipoDGT.put("EXTRR","102");
		stringIdTipoViaToIdTipoDGT.put("FINCA","103");
		stringIdTipoViaToIdTipoDGT.put("GALE","104");
		stringIdTipoViaToIdTipoDGT.put("GRUP","105");
		stringIdTipoViaToIdTipoDGT.put("JDIN","106");
		stringIdTipoViaToIdTipoDGT.put("LDERA","107");
		stringIdTipoViaToIdTipoDGT.put("MALEC","41");
		stringIdTipoViaToIdTipoDGT.put("MIRAD","41");
		stringIdTipoViaToIdTipoDGT.put("MONTE","110");
		stringIdTipoViaToIdTipoDGT.put("MUELL","111");
		stringIdTipoViaToIdTipoDGT.put("PAGO","41");
		stringIdTipoViaToIdTipoDGT.put("PRAJE","113");
		stringIdTipoViaToIdTipoDGT.put("PARKE","30");
		stringIdTipoViaToIdTipoDGT.put("PARC","30");
		stringIdTipoViaToIdTipoDGT.put("PZO","28");
		stringIdTipoViaToIdTipoDGT.put("PSLLO","28");
		stringIdTipoViaToIdTipoDGT.put("PISTA","28");
		stringIdTipoViaToIdTipoDGT.put("PLCET","29");
		stringIdTipoViaToIdTipoDGT.put("PZTA","29");
		stringIdTipoViaToIdTipoDGT.put("PLZLA","29");
		stringIdTipoViaToIdTipoDGT.put("PTLLO","122");
		stringIdTipoViaToIdTipoDGT.put("PRAZA","29");
		stringIdTipoViaToIdTipoDGT.put("PNTE","124");
		stringIdTipoViaToIdTipoDGT.put("PTO","111");
		stringIdTipoViaToIdTipoDGT.put("RAMAL","126");
		stringIdTipoViaToIdTipoDGT.put("RBRA","127");
		stringIdTipoViaToIdTipoDGT.put("RCON","128");
		stringIdTipoViaToIdTipoDGT.put("RCDA","128");
		stringIdTipoViaToIdTipoDGT.put("RTDA","48");
		stringIdTipoViaToIdTipoDGT.put("SECT","131");
		stringIdTipoViaToIdTipoDGT.put("SENDA","132");
		stringIdTipoViaToIdTipoDGT.put("SEND","133");
		stringIdTipoViaToIdTipoDGT.put("SBIDA","134");
		stringIdTipoViaToIdTipoDGT.put("TRAS","135");
		stringIdTipoViaToIdTipoDGT.put("TRAV","136");
		stringIdTipoViaToIdTipoDGT.put("VALLE","110");
		stringIdTipoViaToIdTipoDGT.put("VEGA","110");
		stringIdTipoViaToIdTipoDGT.put("VREDA","139");
		stringIdTipoViaToIdTipoDGT.put("VIA","140");
		stringIdTipoViaToIdTipoDGT.put("VCTO","79");
		stringIdTipoViaToIdTipoDGT.put("VIAL","41");
		stringIdTipoViaToIdTipoDGT.put("ZONA","143");
		stringIdTipoViaToIdTipoDGT.put("ARRYO","144");
	}*/

	private void inicializarIdTipoViaToIdTipoDGT(){
		stringIdTipoViaToIdTipoDGT.put("ALAM","39"); // ALAMEDA
		stringIdTipoViaToIdTipoDGT.put("APTOS","40"); // APARTAMENTOS
		stringIdTipoViaToIdTipoDGT.put("AVIA","66"); // AUTOVIA
		stringIdTipoViaToIdTipoDGT.put("AVDA","2"); // AVENIDA
		stringIdTipoViaToIdTipoDGT.put("BJADA","67"); // BAJADA
		stringIdTipoViaToIdTipoDGT.put("BARRO","4"); // BARRIO
		stringIdTipoViaToIdTipoDGT.put("BLQUE","42");// BLOQUE
		stringIdTipoViaToIdTipoDGT.put("CLLON","51"); // CALLEJON
		stringIdTipoViaToIdTipoDGT.put("CALLE","41"); // CALLE
		stringIdTipoViaToIdTipoDGT.put("CMNO","5");// CAMINO
		stringIdTipoViaToIdTipoDGT.put("CMN0","44"); // CAMINO
		stringIdTipoViaToIdTipoDGT.put("COL","45"); // COLONIA
		stringIdTipoViaToIdTipoDGT.put("CTRA","7"); // CARRETERA
		stringIdTipoViaToIdTipoDGT.put("CSRIO","46");// CASERIO
		stringIdTipoViaToIdTipoDGT.put("CUSTA","15"); // CUESTA

		// NUEVA
		//stringIdTipoViaToIdTipoDGT.put("ED","47"); // EDIFICIO
		stringIdTipoViaToIdTipoDGT.put("GTA","48"); // GLORIETA
		stringIdTipoViaToIdTipoDGT.put("CJTO","75"); // GRUPO
		stringIdTipoViaToIdTipoDGT.put("VIA","11"); // VIA (9)
		stringIdTipoViaToIdTipoDGT.put("COSTA","54"); // COSTA

		// NUEVA
		//stringIdTipoViaToIdTipoDGT.put("MERCADO","10"); // MERCADO
		stringIdTipoViaToIdTipoDGT.put("ZONA","12"); // ZONA (21)

		// NUEVAS
		//stringIdTipoViaToIdTipoDGT.put("MANZANA","22");
		//stringIdTipoViaToIdTipoDGT.put("OT","23");

		stringIdTipoViaToIdTipoDGT.put("PBDO","25"); // POBLADO

		// NUEVA
		//stringIdTipoViaToIdTipoDGT.put("PTDA","26");
		stringIdTipoViaToIdTipoDGT.put("POLIG","27"); // POLIGONO
		stringIdTipoViaToIdTipoDGT.put("PSAJE","28"); // PASAJE
		stringIdTipoViaToIdTipoDGT.put("PLAZA","29"); // PLAZA
		stringIdTipoViaToIdTipoDGT.put("PQUE","30"); // PARQUE
		stringIdTipoViaToIdTipoDGT.put("PROL","31"); // PROLONGACION
		stringIdTipoViaToIdTipoDGT.put("PASEO","32"); // PASEO
		stringIdTipoViaToIdTipoDGT.put("PTDA","33"); // PARTIDA
		stringIdTipoViaToIdTipoDGT.put("PNTE","88");// PUENTE (58)
		stringIdTipoViaToIdTipoDGT.put("RBLA","35"); // RAMBLA
		stringIdTipoViaToIdTipoDGT.put("RONDA","36"); // RONDA
		stringIdTipoViaToIdTipoDGT.put("CRRDO","41");// SENDERO (55)
		stringIdTipoViaToIdTipoDGT.put("SBIDA","3"); // SUBIDA (60)
		stringIdTipoViaToIdTipoDGT.put("PSLLO","28"); // TRANSITO
		stringIdTipoViaToIdTipoDGT.put("TRVA","37"); // TRAVESIA
		stringIdTipoViaToIdTipoDGT.put("URB","38"); // URBANIZACION

		stringIdTipoViaToIdTipoDGT.put("LUGAR","50");
		stringIdTipoViaToIdTipoDGT.put("PARTI","43");
		stringIdTipoViaToIdTipoDGT.put("GRUPO","49");
		stringIdTipoViaToIdTipoDGT.put(".","23");
		stringIdTipoViaToIdTipoDGT.put("G.V.","9");

		stringIdTipoViaToIdTipoDGT.put("ACCES","60");
		stringIdTipoViaToIdTipoDGT.put("ALDEA","61");
		stringIdTipoViaToIdTipoDGT.put("ALTO","41");
		stringIdTipoViaToIdTipoDGT.put("ARRAL","63");
		stringIdTipoViaToIdTipoDGT.put("ATAJO","41");
		stringIdTipoViaToIdTipoDGT.put("AUTO","65");

		stringIdTipoViaToIdTipoDGT.put("BRANC","68");
		stringIdTipoViaToIdTipoDGT.put("BARDA","69");
		stringIdTipoViaToIdTipoDGT.put("BULEV","41");
		stringIdTipoViaToIdTipoDGT.put("CLLJA","71");

		stringIdTipoViaToIdTipoDGT.put("CJLA","41");
		stringIdTipoViaToIdTipoDGT.put("CZADA","41");
		stringIdTipoViaToIdTipoDGT.put("CAMI","5");
		stringIdTipoViaToIdTipoDGT.put("CAMIN","5");
		stringIdTipoViaToIdTipoDGT.put("CAMPA","77");
		stringIdTipoViaToIdTipoDGT.put("CANAL","72");
		stringIdTipoViaToIdTipoDGT.put("CÑADA","73");
		stringIdTipoViaToIdTipoDGT.put("CRA","7");
		stringIdTipoViaToIdTipoDGT.put("CTRIN","7");
		stringIdTipoViaToIdTipoDGT.put("CRRIL","62");
		stringIdTipoViaToIdTipoDGT.put("CERRO","41");
		stringIdTipoViaToIdTipoDGT.put("CINT","84");
		stringIdTipoViaToIdTipoDGT.put("C.H.","5");
		stringIdTipoViaToIdTipoDGT.put("C.N.","5");
		stringIdTipoViaToIdTipoDGT.put("C.V.","74");
		stringIdTipoViaToIdTipoDGT.put("COMPJ","89");

		stringIdTipoViaToIdTipoDGT.put("CRRAL","41");
		stringIdTipoViaToIdTipoDGT.put("CRRLO","41");
		stringIdTipoViaToIdTipoDGT.put("CRRDE","41");

		stringIdTipoViaToIdTipoDGT.put("CRTJO","46");

		stringIdTipoViaToIdTipoDGT.put("CSTAN","41");
		stringIdTipoViaToIdTipoDGT.put("ENTD","97");
		stringIdTipoViaToIdTipoDGT.put("ESCA","99");
		stringIdTipoViaToIdTipoDGT.put("ESCAL","99");
		stringIdTipoViaToIdTipoDGT.put("EXPLA","100");
		stringIdTipoViaToIdTipoDGT.put("EXTRM","76");
		stringIdTipoViaToIdTipoDGT.put("EXTRR","80");
		stringIdTipoViaToIdTipoDGT.put("FINCA","81");
		stringIdTipoViaToIdTipoDGT.put("GALE","104");
		stringIdTipoViaToIdTipoDGT.put("GRUP","105");
		stringIdTipoViaToIdTipoDGT.put("JDIN","82");
		stringIdTipoViaToIdTipoDGT.put("LDERA","107");
		stringIdTipoViaToIdTipoDGT.put("MALEC","41");
		stringIdTipoViaToIdTipoDGT.put("MIRAD","41");
		stringIdTipoViaToIdTipoDGT.put("MONTE","83");
		stringIdTipoViaToIdTipoDGT.put("MUELL","85");
		stringIdTipoViaToIdTipoDGT.put("PAGO","41");
		stringIdTipoViaToIdTipoDGT.put("PRAJE","86");
		stringIdTipoViaToIdTipoDGT.put("PARKE","30");
		stringIdTipoViaToIdTipoDGT.put("PARC","30");
		stringIdTipoViaToIdTipoDGT.put("PZO","28");

		stringIdTipoViaToIdTipoDGT.put("PISTA","28");
		stringIdTipoViaToIdTipoDGT.put("PLCET","29");
		stringIdTipoViaToIdTipoDGT.put("PZTA","29");
		stringIdTipoViaToIdTipoDGT.put("PLZLA","29");
		stringIdTipoViaToIdTipoDGT.put("PTLLO","122");
		stringIdTipoViaToIdTipoDGT.put("PRAZA","29");

		stringIdTipoViaToIdTipoDGT.put("PTO","111");
		stringIdTipoViaToIdTipoDGT.put("RAMAL","126");
		stringIdTipoViaToIdTipoDGT.put("RBRA","127");
		stringIdTipoViaToIdTipoDGT.put("RCON","128");
		stringIdTipoViaToIdTipoDGT.put("RCDA","90");
		stringIdTipoViaToIdTipoDGT.put("RTDA","48");
		stringIdTipoViaToIdTipoDGT.put("SECT","91");
		stringIdTipoViaToIdTipoDGT.put("SENDA","92");
		stringIdTipoViaToIdTipoDGT.put("SEND","64");

		stringIdTipoViaToIdTipoDGT.put("TRAS","135");
		stringIdTipoViaToIdTipoDGT.put("TRAV","136");
		stringIdTipoViaToIdTipoDGT.put("VALLE","110");
		stringIdTipoViaToIdTipoDGT.put("VEGA","110");
		stringIdTipoViaToIdTipoDGT.put("VREDA","8");

		stringIdTipoViaToIdTipoDGT.put("VCTO","79");
		stringIdTipoViaToIdTipoDGT.put("VIAL","41");

		stringIdTipoViaToIdTipoDGT.put("ARRYO","13");
	}

	public void inicializarProvincia() {
		provinciaDGTtoTabla.put("VI","01");
		provinciaDGTtoTabla.put("AB","02");
		provinciaDGTtoTabla.put("A","03");
		provinciaDGTtoTabla.put("AL","04");
		provinciaDGTtoTabla.put("AV","05");
		provinciaDGTtoTabla.put("BA","06");
		provinciaDGTtoTabla.put("PM","07");
		//Nuevos Campos Matw
	//	provinciaDGTtoTabla.put("IB","07");

		provinciaDGTtoTabla.put("B","08");
		provinciaDGTtoTabla.put("BU","09");
		provinciaDGTtoTabla.put("CC","10");
		provinciaDGTtoTabla.put("CA","11");
		provinciaDGTtoTabla.put("CS","12");
		provinciaDGTtoTabla.put("CR","13");
		provinciaDGTtoTabla.put("CO","14");
		provinciaDGTtoTabla.put("C","15");
		provinciaDGTtoTabla.put("CU","16");
		provinciaDGTtoTabla.put("GE","17");
		//Nuevos Campos Matw
//		provinciaDGTtoTabla.put("GI","17");

		provinciaDGTtoTabla.put("GR","18");
		provinciaDGTtoTabla.put("GU","19");
		provinciaDGTtoTabla.put("SS","20");
		provinciaDGTtoTabla.put("H","21");
		provinciaDGTtoTabla.put("HU","22");
		provinciaDGTtoTabla.put("J","23");
		provinciaDGTtoTabla.put("LE","24");
		provinciaDGTtoTabla.put("L","25");
		provinciaDGTtoTabla.put("LO","26");
		provinciaDGTtoTabla.put("LU","27");
		provinciaDGTtoTabla.put("M","28");
		provinciaDGTtoTabla.put("MA","29");
		provinciaDGTtoTabla.put("MU","30");
		provinciaDGTtoTabla.put("NA","31");
		provinciaDGTtoTabla.put("OR","32");
		//Nuevos Campos Matw
	//	provinciaDGTtoTabla.put("OU","32");

		provinciaDGTtoTabla.put("O","33");
		provinciaDGTtoTabla.put("P","34");
		provinciaDGTtoTabla.put("GC","35");
		provinciaDGTtoTabla.put("PO","36");
		provinciaDGTtoTabla.put("SA","37");
		provinciaDGTtoTabla.put("TF","38");
		provinciaDGTtoTabla.put("S","39");
		provinciaDGTtoTabla.put("SG","40");
		provinciaDGTtoTabla.put("SE","41");
		provinciaDGTtoTabla.put("SO","42");
		provinciaDGTtoTabla.put("T","43");
		provinciaDGTtoTabla.put("TE","44");
		provinciaDGTtoTabla.put("TO","45");
		provinciaDGTtoTabla.put("V","46");
		provinciaDGTtoTabla.put("VA","47");
		provinciaDGTtoTabla.put("BI","48");
		provinciaDGTtoTabla.put("ZA","49");
		provinciaDGTtoTabla.put("Z","50");
		provinciaDGTtoTabla.put("CE","51");
		provinciaDGTtoTabla.put("ML","52");
	}

	public void inicializarTablaProvincias() {
		provinciaTablatoDGT.put("01","VI");
		provinciaTablatoDGT.put("02","AB");
		provinciaTablatoDGT.put("03","A");
		provinciaTablatoDGT.put("04","AL");
		provinciaTablatoDGT.put("05","AV");
		provinciaTablatoDGT.put("06","BA");
		provinciaTablatoDGT.put("07","PM");
		//Nuevos Campos Matw
		//provinciaTablatoDGT.put("07","IB");

		provinciaTablatoDGT.put("08","B");
		provinciaTablatoDGT.put("09","BU");
		provinciaTablatoDGT.put("10","CC");
		provinciaTablatoDGT.put("11","CA");
		provinciaTablatoDGT.put("12","CS");
		provinciaTablatoDGT.put("13","CR");
		provinciaTablatoDGT.put("14","CO");
		provinciaTablatoDGT.put("15","C");
		provinciaTablatoDGT.put("16","CU");
		provinciaTablatoDGT.put("17","GE");
		//Nuevos Campos Matw
		//provinciaTablatoDGT.put("17","GI");

		provinciaTablatoDGT.put("18","GR");
		provinciaTablatoDGT.put("19","GU");
		provinciaTablatoDGT.put("20","SS");
		provinciaTablatoDGT.put("21","H");
		provinciaTablatoDGT.put("22","HU");
		provinciaTablatoDGT.put("23","J");
		provinciaTablatoDGT.put("24","LE");
		provinciaTablatoDGT.put("25","L");
		provinciaTablatoDGT.put("26","LO");
		provinciaTablatoDGT.put("27","LU");
		provinciaTablatoDGT.put("28","M");
		provinciaTablatoDGT.put("29","MA");
		provinciaTablatoDGT.put("30","MU");
		provinciaTablatoDGT.put("31","NA");
		provinciaTablatoDGT.put("32","OR");
		//Nuevos Campos Matw
		//provinciaTablatoDGT.put("32","OU");

		provinciaTablatoDGT.put("33","O");
		provinciaTablatoDGT.put("34","P");
		provinciaTablatoDGT.put("35","GC");
		provinciaTablatoDGT.put("36","PO");
		provinciaTablatoDGT.put("37","SA");
		provinciaTablatoDGT.put("38","TF");
		provinciaTablatoDGT.put("39","S");
		provinciaTablatoDGT.put("40","SG");
		provinciaTablatoDGT.put("41","SE");
		provinciaTablatoDGT.put("42","SO");
		provinciaTablatoDGT.put("43","T");
		provinciaTablatoDGT.put("44","TE");
		provinciaTablatoDGT.put("45","TO");
		provinciaTablatoDGT.put("46","V");
		provinciaTablatoDGT.put("47","VA");
		provinciaTablatoDGT.put("48","BI");
		provinciaTablatoDGT.put("49","ZA");
		provinciaTablatoDGT.put("50","Z");
		provinciaTablatoDGT.put("51","CE");
		provinciaTablatoDGT.put("52","ML");
	}

	/**
	 * Devuelve el código de la tabla de provincia según unas siglas DGT
	 */
	public String getIdProvinciaFromSiglas(String sigla) {
		if (sigla==null) return "";
		return provinciaDGTtoTabla.get(sigla.trim());
	}

	/**
	 * Devuelve el código de la tabla de tipo de vía según el código de BBDD.
	 */
	public String getIdTipoViaDGTFromIdTipoVia(String idTipoVia) {
		String tipo = stringIdTipoViaToIdTipoDGT.get(idTipoVia);
		if (null == tipo || Integer.parseInt(tipo)>=93) {
			return "";
		}
		else return tipo;
	}

	/**
	 * Devuelve el código de la tabla de tipo de vía según el código de DGT.
	 */
	public String getIdTipoViaDGTFromNumeroViaDGT(String numeroVia) {
		Iterator<String> it = stringTipoViaDGTtoTipoViaDGT.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = stringTipoViaDGTtoTipoViaDGT.get(key);
			if(value.equals(numeroVia)){
				return key;
			}
		}
		return "-1";
	}

	public String getIdDGTFromKey(String tipo) {
		String tipoDGT = stringTipoViaDGTtoTipoViaDGT.get(tipo);
		if (tipoDGT!=null)
			return tipoDGT;
		else
			return "-1";
	}

	/**
	 * Devuelve el código de las siglas DGT según el código de la tabla de provincias. 
	 */
	public String getSiglasFromIdProvincia(String idProvincia) {
		if (idProvincia==null) return "";
		return provinciaTablatoDGT.get(idProvincia.trim());
	}

	public String getIdProvinciaFromNombre(String nombre) {
		if (StringUtils.isNotBlank(nombre)) {
			String nombreProvincia = nombre.toUpperCase();
			if(nombreProvincia.length() > 8) {
				nombreProvincia = nombreProvincia.substring(0, 8);
			}
			ProvinciaDto provinciaDto = servicioProvincia.getProvinciaPorNombre(nombreProvincia);
			return provinciaDto.getIdProvincia();
		}
		return null;
	}
	
	/**
	 * Devuelve las siglas de la provincia a partir de su nombre completo
	 * @param nombre
	 * @return
	 */
	public String getSiglaProvinciaFromNombre(String nombre) {
		String idProvincia = this.getIdProvinciaFromNombre(nombre);
		String siglas = getSiglasFromIdProvincia(idProvincia);
		return siglas;
	}

	/**
	 * Busca en base de datos el nombre de una provincia para un idProvincia pasado por parámetro
	 * @param idProvincia
	 * @return
	 */
	public String getNombreProvincia(String idProvincia) {
		ProvinciaVO provincia = servicioProvincia.getProvincia(idProvincia);
		return provincia.getNombre();
	}

	/**
	 * 
	 * @param un domicilio que puede tener el tipo de vía, calle, número, piso...
	 * @return Devuelve en un hashmap los valores "tipoVia", "calle", "numero" y "puerta"
	 */
	public HashMap<String,String> separarDomicilio(String valor) {
		if ("".equals(valor) || null==valor) {
			HashMap<String,String> respuesta = new HashMap<String,String>();
			respuesta.put("tipoVia", "");
			respuesta.put("calle", "");
			respuesta.put("numero", "");
			respuesta.put("puerta", "");
			return respuesta;
		}
		String[] palabras = valor.trim().split(" ");
		String tipoVia = "";
		String calle = "";
		String numero = "";
		String puerta = "";
		boolean hayTipoVia = false;
		boolean hayNumero = false;
		int posicionNumero = 0;
		if (esUnTipoVia(palabras[0])) {
			tipoVia = stringTipoViaDGTtoTipoViaDGT.get(palabras[0]);
			hayTipoVia = true;
		}
		int i = 0;
		if (hayNumero) i++;
		while (!hayNumero && i<palabras.length) {
			if (comprobarNumero(palabras[i])) {
				hayNumero = true;
				posicionNumero = i;
				numero = palabras[i];
				for (int j=i+1;j<palabras.length;j++) {
					if (j==palabras.length) puerta += palabras[j];
					else puerta+= palabras[j] + " ";
				}
			}
			i++;
		}
		int empiezaCalle = 0;
		int terminaCalle = 0;
		if (hayTipoVia) empiezaCalle = 1;
		if (hayNumero) terminaCalle = posicionNumero;
		else terminaCalle = palabras.length;
		int j = empiezaCalle;
		while (j<terminaCalle) {
			if ("".equals(calle)) calle = palabras[j];
			else calle += " " + palabras[j];
			j++;
		}
		HashMap<String,String> respuesta = new HashMap<String,String>();
		if ("6".equals(tipoVia)) tipoVia="41"; //calle
		if ("44".equals(tipoVia)) tipoVia="5"; //camino
		if ("24".equals(tipoVia)) tipoVia="29"; //plaza
		if (tipoVia==null || (!tipoVia.equals("") && Integer.parseInt(tipoVia)>=60)) tipoVia=""; //si es mayor de 60 no es tipoViaDgtImportacion, por tanto va a blanco.
		respuesta.put("tipoVia", tipoVia);
		respuesta.put("calle", calle);
		respuesta.put("numero", numero);
		respuesta.put("puerta", puerta.trim());
		return respuesta;
	}

	public boolean esUnTipoVia(String valor) {
		String tipoVia = stringTipoViaDGTtoTipoViaDGT.get(valor.trim());
		if (null==tipoVia || "".equals(tipoVia)) return false;
		return true;
	}

	public boolean comprobarNumero(String cadena) {
		try {
			Integer.parseInt(cadena);
		} catch (Exception e) {
			if ("SN".equals(cadena)) return true;
			else return false;
		}
		return true;
	}

	public String convertirModoAdjudicacion(String valor) {
		if (valor==null || "".equals(valor)) return "";
		try{
			switch (Integer.parseInt(valor)) {
				case 1:
					return "1";
				case 2:
					return "3";
				case 3:
					return "3";
				case 4:
					return "2";
				case 5:
					return "";
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	public String convertirTipoTasa(String tipoTasa) {
		if ("0".equals(tipoTasa.trim())) {
			return "";
		}
		if ("1".equals(tipoTasa.trim())) {
			return "1.1";
		}
		if ("2".equals(tipoTasa.trim())) {
			return "1.2";
		}
		if ("3".equals(tipoTasa.trim())) {
			return "1.4";
		}
		if ("4".equals(tipoTasa.trim())) {
			return "1.5";
		}
		if ("5".equals(tipoTasa.trim())) {
			return "1.6";
		}
		if ("6".equals(tipoTasa.trim())) {
			return "4.1";
		}
		return "";
	}

	public String tipVehiNuevo(String tipVehiAntiguo) {
		if ("T".equals(tipVehiAntiguo)) return "A";
		else if ("X".equals(tipVehiAntiguo)) return "B";
		else if ("M".equals(tipVehiAntiguo)) return "C";
		else if ("S".equals(tipVehiAntiguo)) return "D";
		else if ("E".equals(tipVehiAntiguo)) return "E";
		else if ("F".equals(tipVehiAntiguo)) return "F";
		else return "";
	}

	public String tipVehiAntiguo(String tipVehiNuevo) {
		if ("A".equals(tipVehiNuevo)) return "T";
		else if ("B".equals(tipVehiNuevo)) return "X";
		else if ("C".equals(tipVehiNuevo)) return "M";
		else if ("D".equals(tipVehiNuevo)) return "S";
		else if ("E".equals(tipVehiNuevo)) return "E";
		else if ("F".equals(tipVehiNuevo)) return "F";
		else return "";
	}

	public String servicioDestinoNuevo(String servicioAntiguo) {
		if ("0".equals(servicioAntiguo)) return "B00";
		else if ("1".equals(servicioAntiguo)) return "A00";
		else if ("2".equals(servicioAntiguo)) return "A04";
		else if ("3".equals(servicioAntiguo)) return "A02";
		else if ("4".equals(servicioAntiguo)) return "A01";
		else if ("5".equals(servicioAntiguo)) return "A03";
		else if ("6".equals(servicioAntiguo)) return "B06";
		else if ("7".equals(servicioAntiguo)) return "B09";
		else if ("8".equals(servicioAntiguo)) return "A12";
		else if ("9".equals(servicioAntiguo)) return "A10";
		else {
			List<ServicioTraficoBean> servicios = getModeloTrafico().obtenerServiciosTrafico(null);
			if(null != servicios){
				for(int i = 0; i < servicios.size(); i ++){
					if(servicioAntiguo.equals(servicios.get(i))){
						return servicioAntiguo;
					}
				}
				return "";
			}
		}
		return "";
	}

	public String servicioIdBDtoCodXML(String idBD) {
		if ("B00".equals(idBD)) return "0";
		else if ("A00".equals(idBD)) return "1";
		else if ("A04".equals(idBD)) return "2";
		else if ("A02".equals(idBD)) return "3";
		else if ("A01".equals(idBD)) return "4";
		else if ("A03".equals(idBD)) return "5";
		else if ("B06".equals(idBD)) return "6";
		else if ("B09".equals(idBD)) return "7";
		else if ("A12".equals(idBD)) return "8";
		else if ("A10".equals(idBD)) return "9";
		else return "";
	}

	public boolean isNifNie(String nif){
		if (nif==null) return false;
		if ("".equals(nif.trim())) return false;
		// Si es NIE, eliminar la x,y,z inicial para tratarlo como NIF
		if (nif.toUpperCase().startsWith("X")||nif.toUpperCase().startsWith("Y")||nif.toUpperCase().startsWith("Z"))
			nif = nif.substring(1);

		Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if(m.matches()){
			String letra = m.group(2);
			//Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni,dni+1);

			if (reference.equalsIgnoreCase(letra)){
	//			System.out.println("Es persona física. "+letra+" "+reference);
				return true;
			}else{
	//			System.out.println("NO es persona física. "+letra+" "+reference);
				return false;
			}
		} else
			return false;
		}

	public String numRegistroLimitacionToMotivo(String valor,String tipo) {
		String respuesta = "";
		if ("05".equals(tipo)){
			if ("ALQU".equals(valor)) respuesta = "ER3";
			if ("FAMN".equals(valor)) respuesta = "RE1";
			if ("MINU".equals(valor)) respuesta = "ER4";
			if ("AESC".equals(valor)) respuesta = "ER2";
		} else if ("06".equals(tipo) && "RESI".equals(valor)){
			respuesta = "ET4";
		}
		return respuesta;
	}

	/**
	 * Método para convertir el municipio en el nombre antiguo para las manchas de transmision y bajas de las antiguas.
	 * @param codIne
	 * @return
	 */
	public String convertirMunicipioAntigua(String idMunicipio, String idProvincia) {
		if (StringUtils.isNotBlank(idMunicipio) && StringUtils.isNotBlank(idProvincia)) {
			DgtMunicipioVO municipio = ServicioDgtMunicipio.getDgtMunicipioPorCodigoIne(idProvincia, idMunicipio);
			return municipio.getMunicipio();
		}
		return null;
	}

	/**
	 * Método para convertir la provincia en el nombre antiguo para las manchas de
	 * transmision y bajas de las antiguas.
	 * 
	 * @param codIne
	 * @return
	 */
	public String convertirProvinciasAntigua(String idProvincia) {
		if (StringUtils.isNotBlank(idProvincia)) {
			DgtProvinciaDto provincia = servicioDgtProvincia.getDgtProvincia(idProvincia);
			return provincia.getProvincia();
		}
		return null;
	}

	/**
	 * Método para cambiar de los campos INE los determinantes al principio.
	 * @param campo
	 * @return
	 */
	public String ajustarCamposIne(String campo){
		String cadena = campo;
		if(null!=campo && !campo.equals("")){
			if(campo.endsWith(" (LA)")){
				cadena = "LA " + campo.substring(0, campo.length() - 5);
			}else if(campo.endsWith(" (EL)")){
				cadena = "EL " + campo.substring(0, campo.length() - 5);
			}else if(campo.endsWith(" (LAS)")){
				cadena = "LAS " + campo.substring(0, campo.length() - 6);
			}else if(campo.endsWith(" (LOS)")){
				cadena = "LOS " + campo.substring(0, campo.length() - 6);
			}else if(campo.endsWith(" (DE LOS)")){
				cadena = "DE LOS " + campo.substring(0, campo.length() - 9);
			}else if(campo.endsWith(" (DE LAS)")){
				cadena = "DE LAS " + campo.substring(0, campo.length() - 9);
			}else if(campo.endsWith(" (DEL)")){
				cadena = "DEL " + campo.substring(0, campo.length() - 6);
			}else if(campo.endsWith(" (DE LA)")){
				cadena = "DE LA " + campo.substring(0, campo.length() - 8);
			}else if(campo.endsWith(" (DE)")){
				cadena = "DE " + campo.substring(0, campo.length() - 5);
			}

			return cadena;
		}
		return campo;
	}

	/**
	 * 
	 * @return Realiza la conversion del combustible para MATEW
	 */
	public String convertirTipoCombustible(String valor) {
		if (valor==null || "".equals(valor)) return "";
		try{
			if ("G".equals(valor))
				return "GA";
			else if ("D".equals(valor))
				return "GO";
			else if ("E".equals(valor))
				return "EL";
			else
				return "OT";
		} catch (Exception e) {
			return "";
		}
	}

	public String getCodigoMunicipo(String idDireccion) {
		DireccionVO direccionVO = servicioDireccion.getDireccionVO(Long.parseLong(idDireccion));
		String codMuni = null;
		if(direccionVO != null){
			codMuni = direccionVO.getIdProvincia() + direccionVO.getIdMunicipio();
		}
		return codMuni;
	}

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