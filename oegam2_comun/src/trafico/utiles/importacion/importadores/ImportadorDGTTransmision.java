package trafico.utiles.importacion.importadores;

import static trafico.utiles.constantes.ConstantesDGT.BEAN_PQ_TRANSMISION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CET;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_TRANSMISION;
import trafico.utiles.importacion.interfaces.IImportadorDGT;
import trafico.utiles.importacion.tramas.TramaTransmision;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

public class ImportadorDGTTransmision implements IImportadorDGT  {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportadorDGTTransmision.class);
	
	
	
	public ImportadorDGTTransmision() {
	
	}
	
	/**
	 * Validación primaria sobre el tamaño de la linea de matriculación
	 */
	public ResultBean validaFormato(String lineaTramite,int numLinea) {
		ResultBean resultBean = new ResultBean();
		
		log.trace("Validando formato de linea de transmision");
		
		if(numLinea == 1){
			log.info("La primera línea del fichero no es de tipo PDF");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe ser un registro de tipo PDF");
		}else if(!validaTamLinea(lineaTramite)){
			//Comprobar que la longitud de la linea es correcta
			log.info("La longitud de la línea de registro de Transmision no es correcta");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Linea " + numLinea + " La longitud no es correcta para un trámite de Transmision");
		}else{
			log.debug("La longitud de la línea de Transmision es " + lineaTramite.length());
		}
		
		return resultBean;
	}	
	
	/**
	 * Indica si el tamaño de la línea es correcto, teneniendo en cuenta el número de cotitulares
	 * @param lineaTramite
	 * @return
	 */
	private boolean validaTamLinea(String lineaTramite){
		if(lineaTramite.length()>=TAM_LINEA_TRANSMISION - TAM_CET){
			return true;
		}else{
			return false;
		}
	}

	
	/* 
	 * @see trafico.utiles.importacion.IImportador#importaValores(java.lang.String)
	 */
	public ResultBean importaValores(String lineaTramite, String colegiadoCabecera) {	
		ResultBean resultado = new ResultBean();
		//Nos devolverá un result bean, que sino tiene error, en su getAttachment(BEAN_PQ_BAJA) tentrá el bean del trámite
		resultado = TramaTransmision.obtenerBean(lineaTramite,colegiadoCabecera); 
		
		return resultado;
	}
	
	
	/**
	 * Nos devuelve el key donde se guardará el bean dentro del attachment del ResultBean
	 */
	public String getKeyBean() {
		return BEAN_PQ_TRANSMISION;
	}
}
