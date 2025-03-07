package utilidades.validaciones;



public class FiltroJavascript {

	//	Log

	
	public FiltroJavascript() {
		
	}
	
	/* Lista blanca de admitidos:
	 * 
	 * [a-zA-Z]							->	Cualquier caracter de la "a" a la "z" (ambos incluidos) tanto en minúsculas como en 
	 * 										mayúsculas.
	 * [\d]								->	Cualquier número del 0 al 9 (ambos incluidos)
	 * [\s]								->	El caracter espacio
	 * [á[é[í[ó[ú[Á[É[Í[Ó[Ú]]]]]]]]]]	-> 	Vocales acentuadas tanto en minúsculas como en mayúsculas
	 * [/]								->	El caracter "/", necesario por ejemplo para cuando nos introducen una dirección http
	 * [:]								->	El caracter ":", idem al anterior
	 * [_]								->	El caracter "_", idem al anterior
	 * [&]								->	El caracter "&", necesario por ejemplo para URLs con variables:
	 * 										new=1&psc=false&source=arbolCERe&control=null
	 * [\\\\]							->	El caracter "\", necesario por ejemplo para un patrón: \"?([^\"]*)\"?
	 * ["]								->	El caracter   ", idem al anterior, también aparece por ejemplo en los certificados del DNI,
	 * 										en la función findScriptCodeWhitelist no se admiten, se admiten en la función
	 * 										findScriptCodeWhitelistWithQuotes
	 * [?]								->	El caracter "?", idem al anterior
	 * [\\[]							->	El caracter "[", idem al anterior
	 * [\\]]							->	El caracter "]", idem al anterior
	 * [\\^]							->	El caracter "^", idem al anterior
	 * [\\$]							->	El caracter "$", idem al anterior
	 * [#]								->	El caracter "#", se decide no incluirlo, puede haber URLs (sobretodo internas) que lo
	 *										necesiten pero es un caso rebuscado y no poniéndolo evitamos todas las sustituciones,
	 *										ejemplo: &#60 por <
	 *										Esta decisión implica el no análisis de las extensiones SubjectAlternativeNames e
	 *										IssuerAlternativeNames, ya que en sus valores existen #, se podría hacer un filtro mas
	 *										delicado que permitiese la aparición de # pero solo si y solo si lleva un = delante, esto
	 *										permitiría el análisis de SubjectAlternativeNames e IssuerAlternativeNames y no daría
	 *										cabida a las construcciones &#, pero se prefiere dejar mas restrictivo teniendo en cuenta
	 *										que ni SubjectAlternativeNames ni IssuerAlternativeNames son mostrados en la consola en
	 *										ningún punto.
	 * [*]								->	El caracter "*", idem al anterior
	 * [;]								->	El caracter ";", necesario por ejemplo para cuando nos introducen una ruta de BDD:
	 * 										jdbc:microsoft:sqlserver://zaz-des-ms01:1433;databaseName=ASF
	 * [=]								->	El caracter "=", necesario por ejemplo para el CN de los certificados
	 * [,]								->	El caracter ",", idem al anterior 
	 * [-]								->	El caracter "-", necesario por ejemplo para el CN de los certificados de la FNMT: - NIF
	 * [@]								->	El caracter "@", necesario por ejemplo para el E de los certificados de la FNMT
	 * [.]								->	El caracter ".", idem al anterior
	 * [(]								->	El caracter "(", necesario por ejemplo para el CN de los certificados eDNI:
	 * 										(AUTENTICACIÓN) y (FIRMA)
	 * [)]								->	El caracter ")", idem al anterior
	 */
	public static boolean findScriptCodeWhitelist(String sPossibleScriptCode) 
	{	
		String sWhitelistWithoutQuotes = "[a-zA-Z[\\d[\\s[\\Ç[\\ë[\\Ë[\\¥[á[é[í[ó[ú[Á[É[Í[Ó[Ú[/[:[_[&[\\\\[?[\\[[\\][\\^[\\$[*[;[=[,[-[@[.[([)[']]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]*";
		return findScriptCodeWhitelistCommon(sPossibleScriptCode, sWhitelistWithoutQuotes);
	}  
	
	public static boolean findScriptCodeWhitelistWithQuotes(String sPossibleScriptCode) 
	{
		String sWhitelistWithQuotes = "[a-zA-Z[\\d[\\s[\\Ç[\\ë[\\Ë[\\¥[á[é[í[ó[ú[Á[É[Í[Ó[Ú[/[:[_[&[\\\\[\"[?[\\[[\\][\\^[\\$[*[;[=[,[-[@[.[([)[']]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]*";
		return findScriptCodeWhitelistCommon(sPossibleScriptCode, sWhitelistWithQuotes);
	}
	
	public static boolean findScriptCodeWhitelistPlus(String sPossibleScriptCode) 
	{
		String sWhitelistWithQuotes = "[a-zA-Z[\\d[\\s[\\Ç[\\ë[\\Ë[\\ä[\\Ä[\\ï[\\Ï[\\ö[\\Ö[\\¥[á[é[í[ó[ú[ü[ñ[Á[É[Í[Ó[Ú[Ü[Ñ[à[è[ì[ò[ù[À[È[Ì[Ò[Ù[º[ª[/[:[_[&[#[\\\\[?[\\[[\\][\\^[\\$[*[+[;[=[,[-[@[.[([)['[³[%]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]*";
		return findScriptCodeWhitelistCommon(sPossibleScriptCode, sWhitelistWithQuotes);
	}
	
	private static boolean findScriptCodeWhitelistCommon(String sPossibleScriptCode, String sWhitelist) 
	{
		boolean bFindScriptCode = false;
		if (sPossibleScriptCode != null && !"".equals(sPossibleScriptCode)){
			bFindScriptCode = !java.util.regex.Pattern.matches(sWhitelist, sPossibleScriptCode.replaceAll("<BR>", "").replaceAll("<b>", "").replaceAll("</b>", "").replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", ""));
		}
                
		return bFindScriptCode;
	}
}
