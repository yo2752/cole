package trafico.utiles.importacion.importadores;



import static trafico.utiles.constantes.ConstantesDGT.BEAN_PDF;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_PDF_CON_BLANCOS;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_PDF_SIN_BLANCOS;
import trafico.utiles.importacion.interfaces.IImportadorDGT;
import trafico.utiles.importacion.tramas.TramaPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;


/**
 * Clase que implementa los metodos para la validacion de la linea y la importacion del registro PDF
 * de un fichero.
 *
 */
public class ImportadorPDF implements IImportadorDGT{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportadorPDF.class);
	
	public ImportadorPDF() {
		
	}
	
	/* 
	 * @see com.tbsolutions.oegam.usuario.tramites.importacion.IImportador#validaFormato(java.lang.String, int)
	 */
	public ResultBean validaFormato(String lineaTramite, int numLinea) {
		ResultBean resultBean = new ResultBean();
		
		log.trace("Validando formato de linea PDF");
		
		if(numLinea != 1){
			log.info("Una línea distinta de la primera es de tipo PDF");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("-Linea " + numLinea + ": Sólo la primera línea puede ser un registro PDF");
		}else if(lineaTramite.length() < TAM_LINEA_PDF_SIN_BLANCOS) { //Comprobar que la longitud de la linea es correcta
			log.info("La longitud de la línea de registro PDF no es correcta");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("-Linea " + numLinea + ": La longitud no es correcta para un registro PDF");
		}else{
			if(lineaTramite.length() != TAM_LINEA_PDF_SIN_BLANCOS 
					&& lineaTramite.length() != TAM_LINEA_PDF_CON_BLANCOS){
				log.trace("La longitud de la línea PDF es " + lineaTramite.length());
			}
		}
		
		return resultBean;
	}
	
	/* 
	 * @see com.tbsolutions.oegam.usuario.tramites.importacion.IImportador#importaValores(java.lang.String)
	 */
	public ResultBean importaValores(String lineaTramite,String colegiadoCabecera) {
		ResultBean bean;
		
		//Generar el TramitesBean que contiene las parejas de identificador-valor
		bean = TramaPDF.obtenerBean(lineaTramite);
		
		return bean;

	}

	public String getKeyBean() {
		return BEAN_PDF;
	}


}
