package trafico.utiles.importacion.importadores;

import static trafico.utiles.constantes.ConstantesDGT.BEAN_PQ_ALTA_MATRICULACION_IMPORT;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TOTAL_TITULAR_2;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TOTAL_TITULAR_3;
import trafico.utiles.importacion.interfaces.IImportadorDGT;
import trafico.utiles.importacion.tramas.TramaMatriculacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

/**
 * Clase que implementa los metodos para la validacion de la linea y la importacion del tramite de
 * matriculacion de un fichero.
 * 
 * @author TB�Solutions
 *
 */
public class ImportadorDGTMatriculacion implements IImportadorDGT {

	private static final int _1277 = 1277;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportadorDGTMatriculacion.class);
	
	private static int POS_NUM_COTITULARES = _1277;
	
	
	public ImportadorDGTMatriculacion() {
		
	}
	
	/**
	 * Validaci�n primaria sobre el tama�o de la linea de matriculaci�n
	 */
	public ResultBean validaFormato(String lineaTramite,int numLinea) {
		ResultBean resultBean = new ResultBean();
		
		log.trace("Validando formato de linea de matriculacion");
		
		if(numLinea == 1){
			log.info("La primera l�nea del fichero no es de tipo PDF");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe ser un registro de tipo PDF");
		}else if(!validaTamLinea(lineaTramite)){
			//Comprobar que la longitud de la linea es correcta
			log.info("La longitud de la l�nea de registro de Matriculaci�n no es correcta");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Linea " + numLinea + " La longitud no es correcta para un tr�mite de Matriculaci�n");
		}else{
			log.debug("La longitud de la l�nea de Matriculaci�n es " + lineaTramite.length());
		}
		
		return resultBean;
	}	
	
	/**
	 * Indica si el tama�o de la l�nea es correcto, teneniendo en cuenta el n�mero de cotitulares
	 * @param lineaTramite
	 * @return
	 */
	private boolean validaTamLinea(String lineaTramite){
		if(lineaTramite.length()>=TAM_LINEA_MATRICULACION){
			return true;
		}else if(lineaTramite.length()==TAM_LINEA_MATRICULACION-TAM_TOTAL_TITULAR_3
				&& "1".equals(getNumCotitulares(lineaTramite))){
			return true;
		}else if(lineaTramite.length()==TAM_LINEA_MATRICULACION-TAM_TOTAL_TITULAR_3-TAM_TOTAL_TITULAR_2
				&& "0".equals(getNumCotitulares(lineaTramite))){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Obtiene de la l�nea importada, el n�mero de cotitulares
	 * @param lineaTramite
	 * @return
	 */
	private String getNumCotitulares(String lineaTramite){
		if(lineaTramite.length()>=POS_NUM_COTITULARES+1){
			return lineaTramite.substring(POS_NUM_COTITULARES, POS_NUM_COTITULARES+1);
		}else{
			return "-1";
		}
	}
	
	/* 
	 * @see trafico.utiles.importacion.IImportador#importaValores(java.lang.String)
	 */
	public ResultBean importaValores(String lineaTramite, String colegiadoCabecera) {		
		//Nos devolver� un result bean, que sino tiene error, en su getAttachment(BEAN_PQ_ALTA_MATRICULACION) tentr� el bean del tr�mite
		ResultBean resultado = TramaMatriculacion.obtenerBean(lineaTramite,colegiadoCabecera);		
		/*
		 * if (resultadoDevuelto.getError()) eliminaFechasPorDefecto(resultado.getAttachment(BEAN_PQ_ALTA_MATRICULACION), CASILLAS_FECHAS_MATRICULACION);
		 */
		
		return resultado;
	}
	
	
	/**
	 * Nos devuelve el key donde se guardar� el bean dentro del attachment del ResultBean
	 */
	public String getKeyBean() {
		return BEAN_PQ_ALTA_MATRICULACION_IMPORT;
	}
	
	
	
	
	/**
	 * Lee las casillas de un tr�mite que correspondan a una fecha, y las que tengan el valor de fecha por defecto
	 * de SIGA, las completa con espacios.
	 * @param bean
	 * @param casillasFechas
	 */
	/*public static void eliminaFechasPorDefecto(ImportacionDGTMatriculacionBean bean, CasillasTramiteTrafico[] casillasFechas){
		for(CasillasTramiteTrafico casilla:casillasFechas){
			String valor = bean.getValor(casilla.longValue());
			if(valor!=null && valor.equals(FECHA_DEFECTO_SIGA)){
				valor = changeSize("", TAM_FECHA_DEFECTO_SIGA);
				log.info("Valor cambiado a: "+valor);
			}
			bean.addCampo(casilla.longValue(), null, valor);
		}
	}*/
	
	

	private static String changeSize(String vacio, int tam) {
		String cadena = "";
		for (int i=0;i<tam;i++) {
			cadena = cadena + " ";
		}
		return cadena;
	}
	
	private static String changeSize(String campo, int tam, char a, boolean b) {
		String cadena = "";
		for (int i=campo.length();i<tam;i++) {
			cadena = cadena + " ";
		}
		return campo+cadena;
	}
	
}
