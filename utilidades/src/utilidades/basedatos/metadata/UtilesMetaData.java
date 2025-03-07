package utilidades.basedatos.metadata;



import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.basedatos.ConstantesTiposDatos;
import utilidades.basedatos.ParametroProcedimientoAlmacenado.EnumTipoUsoParametro;

@Component
public class UtilesMetaData {
	
	private static final String STRING = "String";
	private static final String FINAL = "final";
	private static final String STATIC = "static";
	private static final String VALORES_SCHEMAS_SCHEMA = "ValoresSchemas.getSchema()";
	private static final String TRUE = "true";
	private static final String COMA = ",";
	private static final String PROTECTED = "protected";
	private static final String PAQUETEBEANPQGENERAL = "trafico.beans.daos";
	private static final String NULL = "null";
	private static final String EJECUTAR = "execute";
	private static final String RESPUESTA_GENERICA = "RespuestaGenerica";
	private static final String CLASE_CURSOR = "claseCursor";
	private static final String IMPORT_GENERAL_MODELO_MODELO_GENERICO_EJECUTAR_PROC = "import static general.modelo.ModeloGenerico.ejecutarProc;";
	private static final String PROCEDURE = "PROCEDURE";
	private static final String CATALOG = "CATALOG";
	private static final String SCHEMA = "SCHEMA";
	private static final String PUBLIC_STATIC_FINAL = "public static final";
	private static final String BEANS_TABLAS = "beansTablas";
	private static final String IMPORT_JAVA_SQL_TIMESTAMP = "import java.sql.Timestamp";
	private static final String IMPORT_JAVA_MATH_BIG_DECIMAL = "import java.math.BigDecimal";
	private static final String VOID = "void";
	private static final String EQUALS = "=";
	private static final String SET = "set";
	private static final String GET = "get";
	private static final String THIS = "this";
	private static final String TAB = "	";
	private static final String RETURN = "return";
	private static final String OBJECT = "Object";
	private static final String PRIVATE = "private";
	private static final String BLANCO = " ";
	private static final String PUNTO = ".";
	private static final String PACKAGE = "package";
	private static final String PUBLIC = "public";
	private static final String JAVA = "java";
	private static final String CLASS = "class";
	private static final String BEANPQ = "BeanPQ";
	private static final String ABRELLAVE ="{";
	private static final String CIERRALLAVE ="}";
	private static final String EXTENDS = "extends";
	private static final String BEANPQGENERAL = "BeanPQGeneral"; 
//	private static final String packageBeanPQGeneral= "beanPQGeneral";
	private static final String PUNTOCOMA=";";
	public static final String P_INFORMACION="P_INFORMACION"; 
	public static final String P_SQLERRM = "P_SQLERRM"; 
	public static final String P_CODE = "P_CODE"; 
	
	
	@Autowired
	private Utiles utiles;

	public void generarBeanPQGeneral(String ruta) {
		ArrayList<BeanInfoParametro> arrayInfoParametros = new ArrayList<BeanInfoParametro>(); 
		BeanInfoParametro beanInfoParametro = new BeanInfoParametro(); 
		
		beanInfoParametro.setNombre(P_INFORMACION); 
		beanInfoParametro.setSqlTypeName(ConstantesTiposDatos.VARCHAR);
		beanInfoParametro.setTipoInOutParametro(EnumTipoUsoParametro.OUT.getValor()); 
		
		arrayInfoParametros.add(beanInfoParametro); 
		
		BeanInfoParametro beanInfoParametro1 = new BeanInfoParametro();
		
		beanInfoParametro1.setNombre(P_SQLERRM); 
		beanInfoParametro1.setSqlTypeName(ConstantesTiposDatos.VARCHAR);
		beanInfoParametro1.setTipoInOutParametro(EnumTipoUsoParametro.OUT.getValor()); 
		
		arrayInfoParametros.add(beanInfoParametro1); 
		
		BeanInfoParametro beanInfoParametro2 = new BeanInfoParametro();
		
		beanInfoParametro2.setNombre(P_CODE); 
		beanInfoParametro2.setSqlTypeName(ConstantesTiposDatos.NUMBER);
		beanInfoParametro2.setTipoInOutParametro(EnumTipoUsoParametro.OUT.getValor()); 
		
		arrayInfoParametros.add(beanInfoParametro2); 
		
		generarBeanPQGeneral(arrayInfoParametros,ruta); 
		
		
		
	}

	private void generarBeanPQGeneral(
			ArrayList<BeanInfoParametro> arrayInfoParametros,String ruta) {

	
	List<String> listaString = new ArrayList<String>();
//	String autogenerado = "/**"; 
	String nombreClase = BEANPQGENERAL; 
	
	//generar cabecera de la clase.
	String strPackage = PACKAGE + BLANCO + PAQUETEBEANPQGENERAL + PUNTOCOMA;
	listaString.add(strPackage); 
	listaString.add(BLANCO); 
	String lineaImportBigDecimal =IMPORT_JAVA_MATH_BIG_DECIMAL + PUNTOCOMA;
	listaString.add(lineaImportBigDecimal); 
	
	listaString.add(BLANCO);
	String importSchema = "import oegam.constantes.ValoresSchemas;";
	listaString.add(importSchema); 
	
	listaString.add(BLANCO);
	String strPublicClass = PUBLIC + BLANCO + CLASS + BLANCO + nombreClase + BLANCO + ABRELLAVE; 
	listaString.add(strPublicClass);
	listaString.add(BLANCO); 
	listaString.add(BLANCO); 
	
	String constanteSchema = TAB + PROTECTED + BLANCO + "String SCHEMA=" +VALORES_SCHEMAS_SCHEMA + PUNTOCOMA;
	listaString.add(constanteSchema);
	listaString.add(BLANCO); 
	
	
//lista de atributos	
	for (int i = 0; i < arrayInfoParametros.size(); i++) 
		{
		BeanInfoParametro beanInfoParametro = arrayInfoParametros.get(i); 
		
		String tipoDato=getTipoDato(beanInfoParametro.getSqlTypeName()); 
		String lineaAtributo = TAB + PRIVATE + BLANCO + tipoDato + BLANCO + beanInfoParametro.getNombre() + PUNTOCOMA;
		listaString.add(lineaAtributo); 
		// fin de atributos
		
		listaString.add(BLANCO); 
		}
	
	
//lista de metodos
	for (int i = 0; i < arrayInfoParametros.size(); i++) 
		{
		BeanInfoParametro beanInfoParametro = arrayInfoParametros.get(i);
		String tipoDato=getTipoDato(beanInfoParametro.getSqlTypeName()); 
		String cabeceraGet=TAB + PUBLIC +  BLANCO + tipoDato+ BLANCO + GET + beanInfoParametro.getNombre() + "(" + ")" + ABRELLAVE;  
		listaString.add(cabeceraGet);
		String cuerpoGet = TAB + TAB + RETURN + BLANCO +  beanInfoParametro.getNombre() + PUNTOCOMA +  CIERRALLAVE;
		listaString.add(cuerpoGet); 
		listaString.add(BLANCO); 
		String cabeceraSet = TAB + PUBLIC + BLANCO + VOID + BLANCO + SET + beanInfoParametro.getNombre() +"(" + tipoDato + BLANCO + beanInfoParametro.getNombre()+")" + ABRELLAVE; 
		listaString.add(cabeceraSet);
		String cuerpoSet= TAB + TAB + THIS + PUNTO + beanInfoParametro.getNombre() + EQUALS + beanInfoParametro.getNombre() + PUNTOCOMA + CIERRALLAVE;
		listaString.add(cuerpoSet); 
		listaString.add(BLANCO);
		}
	listaString.add(CIERRALLAVE); 
	
//	utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, "C:\\workspace\\oegam2_comun\\src\\trafico\\beans\\daos\\", listaString);
	utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, ruta, listaString);
	
	
	//fin de la clase
	
	}

	
	private String getPackage(String ruta){
		String rutaAux=""; 
		StringTokenizer str = new StringTokenizer(ruta, "\\"); 
		while (str.hasMoreTokens())
			rutaAux = rutaAux + (str.nextToken()) +".";
		
		int inicio = rutaAux.indexOf(".src.");
		if (inicio!=-1) //si se ha encontrado src
			{
			inicio = rutaAux.indexOf(".src.")+5;
			rutaAux = rutaAux.substring(inicio, ruta.length()-1);
			}
		
		else 
			{
			inicio = rutaAux.indexOf(":"); 
			rutaAux = rutaAux.substring(inicio+2, ruta.length()-1); 
			
			}
		return rutaAux;
	}
	
	/**
	 * 
	 * @param arrayInfoParametros
	 * @param catalog
	 * @param beanMetadataProcedure
	 */
	
	public void generarBeanPQ(
			ArrayList<BeanInfoParametro> arrayInfoParametros,String catalog,BeanMetadataProcedure beanMetadataProcedure,String ruta) {

		List<String> listaString = new ArrayList<String>();
//		String autogenerado = "/**"; 
		String nombreClase = BEANPQ + beanMetadataProcedure.getNombreProcedure(); 
		
		String paquete=getPackage(ruta); 
		
		//generar cabecera de la clase.
		String strPackage = PACKAGE + BLANCO + paquete + PUNTO + catalog.toLowerCase()+ PUNTOCOMA;
		listaString.add(strPackage); 
		listaString.add(BLANCO); 
		listaString.add(IMPORT_GENERAL_MODELO_MODELO_GENERICO_EJECUTAR_PROC);
		listaString.add(BLANCO); 
		listaString.add("import"+ BLANCO+  PAQUETEBEANPQGENERAL+ PUNTO + "BeanPQGeneral;");
		listaString.add(BLANCO); 
		
		listaString.add("import general.beans.RespuestaGenerica;");
		listaString.add(BLANCO); 
		listaString.add("import java.util.List;");
		listaString.add(BLANCO); 
		listaString.add(IMPORT_JAVA_MATH_BIG_DECIMAL + PUNTOCOMA); 
		listaString.add(BLANCO);
		listaString.add(IMPORT_JAVA_SQL_TIMESTAMP + PUNTOCOMA);
		listaString.add(BLANCO);
		String strPublicClass = PUBLIC + BLANCO + CLASS + BLANCO + nombreClase + BLANCO + EXTENDS + BLANCO + BEANPQGENERAL+ABRELLAVE; 
		listaString.add(strPublicClass);
		listaString.add(BLANCO); 
		listaString.add(BLANCO); 
		
		//constantes
		
		String constanteCatalog = PUBLIC_STATIC_FINAL + BLANCO + "String CATALOG=\""+ beanMetadataProcedure.getCatalog() +"\""+ PUNTOCOMA;
		listaString.add(constanteCatalog);
		listaString.add(BLANCO); 
		String constanteProcedure = PUBLIC_STATIC_FINAL + BLANCO + "String PROCEDURE=\""+ beanMetadataProcedure.getNombreProcedure()+"\""+ PUNTOCOMA; 
		listaString.add(constanteProcedure); 
		listaString.add(BLANCO);
		
		//metodos generales de ejecucion de procedimiento almacenado. 
		
		//metodo con cursor
		String metodoEjecutarConCursor =PUBLIC +BLANCO +  "List<Object>" +BLANCO +  "execute(Class<?>" + BLANCO +  CLASE_CURSOR + ")" + ABRELLAVE;   
		listaString.add(metodoEjecutarConCursor); 
		
		
		
		String primeraLinea ="RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,CATALOG,PROCEDURE,claseCursor,true);"; 
		String segundaLinea = "return respuestaGenerica.getListaCursor();";
		listaString.add(primeraLinea);
		listaString.add(segundaLinea);
//		String metodoEjecutarProcCursor = RETURN +BLANCO +  "ejecutarProc(this," +SCHEMA +COMA +CATALOG + COMA + PROCEDURE + COMA + CLASE_CURSOR + COMA + TRUE+")" + PUNTOCOMA;  
//		listaString.add(metodoEjecutarProcCursor);
		listaString.add(CIERRALLAVE); 
		
//		RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,CATALOG,PROCEDURE,claseCursor,true);
//		return respuestaGenerica.getListaCursor();
		
		//primero va sin cursor
		String metodoEjecutar =PUBLIC +BLANCO +  VOID +BLANCO +  EJECUTAR+"()" + ABRELLAVE;   
		listaString.add(metodoEjecutar); 
		String metodoEjecutarProc = "ejecutarProc(this," +SCHEMA +COMA +CATALOG + COMA + PROCEDURE + COMA + NULL  + COMA + TRUE+")" + PUNTOCOMA;  
		listaString.add(metodoEjecutarProc);
		listaString.add(CIERRALLAVE); 
		
		
		//atributos
		for (int i = 0; i < arrayInfoParametros.size(); i++) 
			{
			BeanInfoParametro beanInfoParametro = arrayInfoParametros.get(i); 
			
			String tipoDato=getTipoDato(beanInfoParametro.getSqlTypeName()); 
			String lineaAtributo = TAB + PRIVATE + BLANCO + tipoDato + BLANCO + beanInfoParametro.getNombre() + PUNTOCOMA;
			listaString.add(lineaAtributo); 
			// fin de atributos
			// utiles.crearFicheroFisicoConStrings(beanInfoParametro.getNombre(),".class", "C:\\OEGAM2BEANS\\" + catalog + "\\");
			listaString.add(BLANCO); 
			}
		
		for (int i = 0; i < arrayInfoParametros.size(); i++) 
		{
			BeanInfoParametro beanInfoParametro = arrayInfoParametros.get(i);
			String tipoDato=getTipoDato(beanInfoParametro.getSqlTypeName()); 
			String cabeceraGet=TAB + PUBLIC +  BLANCO + tipoDato+ BLANCO + GET + beanInfoParametro.getNombre() + "(" + ")" + ABRELLAVE;  
			listaString.add(cabeceraGet);
			String cuerpoGet = TAB + TAB + RETURN + BLANCO +  beanInfoParametro.getNombre() + PUNTOCOMA +  CIERRALLAVE;
			listaString.add(cuerpoGet); 
			listaString.add(BLANCO); 
			String cabeceraSet = TAB + PUBLIC + BLANCO + VOID + BLANCO + SET + beanInfoParametro.getNombre() +"(" + tipoDato + BLANCO + beanInfoParametro.getNombre()+")" + ABRELLAVE; 
			listaString.add(cabeceraSet);
			String cuerpoSet= TAB + TAB + THIS + PUNTO + beanInfoParametro.getNombre() + EQUALS + beanInfoParametro.getNombre() + PUNTOCOMA + CIERRALLAVE;
			listaString.add(cuerpoSet); 
			listaString.add(BLANCO);
		}
		listaString.add(CIERRALLAVE); 
		
//		utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, "C:\\workspace\\oegam2_comun\\src\\trafico\\beans\\daos\\"+ catalog.toLowerCase()+"\\", listaString);
		utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, ruta+ catalog.toLowerCase()+"\\", listaString);
		//fin de la clase
		
		
	}

	
	
	public String inicialMayuscula(String palabra){
		
		return  palabra.substring(0,1).toUpperCase()+palabra.substring(1,palabra.length());
	}
	
	
	public void generarBeanTable(
			ArrayList<BeanInfoColumna> arrayInfoColumnas,String nombreTabla,String ruta) {

		List<String> listaString = new ArrayList<String>();
//		String autogenerado = "/**"; 
		String nombreClase = nombreTabla; 
		
		listaString = listarCabeceraTable(listaString, nombreClase); 
		
		//definicion de atributos
		listaString = listarAtributos(arrayInfoColumnas, listaString);
		//getters y setters
		listaString = listarGetSet(arrayInfoColumnas, listaString);
		listaString.add(CIERRALLAVE); 
		
		utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, ruta, listaString);
		
		//fin de la clase
		
		
	}

	private List<String> listarCabeceraTable(List<String> listaString,
			String nombreClase) {
		//generar cabecera de la clase.
		String strPackage = PACKAGE + BLANCO + BEANS_TABLAS + PUNTOCOMA;
		listaString.add(strPackage); 
		listaString.add(BLANCO); 
		listaString.add(IMPORT_JAVA_MATH_BIG_DECIMAL + PUNTOCOMA); 
		listaString.add(BLANCO);
		listaString.add(IMPORT_JAVA_SQL_TIMESTAMP + PUNTOCOMA);
		listaString.add(BLANCO);
		String strPublicClass = PUBLIC + BLANCO + CLASS + BLANCO + nombreClase + ABRELLAVE; 
		listaString.add(strPublicClass);
		listaString.add(BLANCO); 
		listaString.add(BLANCO);
		return listaString; 
	}

	private List<String> listarGetSet(
			ArrayList<BeanInfoColumna> arrayInfoColumnas,
			List<String> listaString) {
		for (int i = 0; i < arrayInfoColumnas.size(); i++) 
		{
			BeanInfoColumna beanInfoColumna= arrayInfoColumnas.get(i);
			

			beanInfoColumna.setNombreColumna(inicialMayuscula(beanInfoColumna.getNombre())); 
			
			String tipoDato=getTipoDato(beanInfoColumna.getTipo()); 
			String cabeceraGet=TAB + PUBLIC +  BLANCO + tipoDato+ BLANCO + GET + beanInfoColumna.getNombre() + "(" + ")" + ABRELLAVE;  
			listaString.add(cabeceraGet);
			String cuerpoGet = TAB + TAB + RETURN + BLANCO +  beanInfoColumna.getNombre().toLowerCase() + PUNTOCOMA +  CIERRALLAVE;
			listaString.add(cuerpoGet); 
			listaString.add(BLANCO); 
			String cabeceraSet = TAB + PUBLIC + BLANCO + VOID + BLANCO + SET + beanInfoColumna.getNombre() +"(" + tipoDato + BLANCO + beanInfoColumna.getNombre()+")" + ABRELLAVE; 
			listaString.add(cabeceraSet);
			String cuerpoSet= TAB + TAB + THIS + PUNTO + beanInfoColumna.getNombre().toLowerCase() + EQUALS + beanInfoColumna.getNombre() + PUNTOCOMA + CIERRALLAVE;
			listaString.add(cuerpoSet); 
			listaString.add(BLANCO);
		}
		return listaString;
	}

	private List<String> listarAtributos(
			ArrayList<BeanInfoColumna> arrayInfoColumnas,
			List<String> listaString) {
		for (int i = 0; i < arrayInfoColumnas.size(); i++) 
			{
			BeanInfoColumna beanInfoColumna = arrayInfoColumnas.get(i); 
			
			String tipoDato=getTipoDato(beanInfoColumna.getTipo()); 
			String lineaAtributo = TAB + PRIVATE + BLANCO + tipoDato + BLANCO + beanInfoColumna.getNombre().toLowerCase() + PUNTOCOMA;
			listaString.add(lineaAtributo); 
			// fin de atributos
			listaString.add(BLANCO); 
			
			
			}
		
		return listaString;
	}
	

	private String getTipoDato(String sqlTypeName) {
		
		
		if (sqlTypeName.equals(ConstantesTiposDatos.NUMBER)) 
			return "BigDecimal"; 
		
		if (sqlTypeName.equals(ConstantesTiposDatos.NUMERIC)) 
			return "BigDecimal";
		
		if (sqlTypeName.equals(ConstantesTiposDatos.CHAR))
			return STRING; 
		
		if (sqlTypeName.equals(ConstantesTiposDatos.VARCHAR))
			return STRING; 
		
		if (sqlTypeName.equals(ConstantesTiposDatos.VARCHAR2))
			return STRING;
		
		if (sqlTypeName.equals(ConstantesTiposDatos.NVARCHAR))
			return STRING;
		
		if (sqlTypeName.equals(ConstantesTiposDatos.NVARCHAR2))
			return STRING;
			
		if (sqlTypeName.equals(ConstantesTiposDatos.DATE))
			return "Timestamp"; 
		
		if (sqlTypeName.equals(ConstantesTiposDatos.TIMESTAMP))
			return "Timestamp"; 
			
		return OBJECT; 
		
		
	}

	public void generarValoresCatalog(ArrayList<String> catalogs,String ruta) {

		List<String> listaString = new ArrayList<String>();
//		String autogenerado = "/**"; 
		String nombreClase = "ValoresCatalog"; 
		String paquete = getPackage(ruta);
		//generar cabecera de la clase.
		String strPackage = PACKAGE + BLANCO + paquete+  PUNTOCOMA;
		listaString.add(strPackage); 
		listaString.add(BLANCO); 
		String strPublicClass = PUBLIC + BLANCO + CLASS + BLANCO + nombreClase + ABRELLAVE; 
		listaString.add(strPublicClass);
		listaString.add(BLANCO); 
		listaString.add(BLANCO); 
		
		for (int i = 0; i < catalogs.size(); i++) 
			{
			String lineaConstante = TAB + PUBLIC + BLANCO + STATIC + BLANCO + FINAL + BLANCO + STRING + BLANCO + catalogs.get(i) + BLANCO + "=" + "\"" + catalogs.get(i)+"\"" +PUNTOCOMA;
			listaString.add(lineaConstante); 
			listaString.add(BLANCO); 
			}
		
		listaString.add(CIERRALLAVE); 
		
		utiles.crearFicheroFisicoConStrings(nombreClase, JAVA, ruta, listaString);
		
		//fin de la clase
		
	}

}
