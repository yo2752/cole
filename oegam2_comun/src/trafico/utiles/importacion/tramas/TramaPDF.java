package trafico.utiles.importacion.tramas;


import static trafico.utiles.constantes.ConstantesDGT.BEAN_PDF;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_PROFESIONAL;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_PROFESIONAL;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_REG;
import trafico.beans.ImportacionDGTPDFBean;
import escrituras.beans.ResultBean;

/**
 * Clase que contiene los metodos para formar la trama para la exportacion u obtener los campos a
 * partir de la trama de un registro PDF.
 * 
 * @author TB·Solutions
 *
 */
public class TramaPDF {
	
	public TramaPDF() {
		
	}
	
	/**
	 * Metodo que obtiene el TramitesBean que contiene los campos de una linea de registro PDF
	 * 
	 * @param linea
	 * @return Devuelve el bean que contiene una linea con tipo de registro PDF
	 */
	public static final ResultBean obtenerBean(String linea){
		ImportacionDGTPDFBean bean = new ImportacionDGTPDFBean();
		int pos=0;
		
		String valor = linea.substring(pos, pos + TAM_TIPO_REG);
		bean.setRegPDF(valor.trim());
		
		pos = pos + TAM_TIPO_REG;
		
		valor = linea.substring(pos, pos + TAM_NUM_PROFESIONAL);
		bean.setNumColegiado((new Integer(valor.trim())).toString());
		
		pos = pos + TAM_NUM_PROFESIONAL;
		
		valor = linea.substring(pos, pos + TAM_NOMBRE_PROFESIONAL);
		bean.setNombre(valor.trim());
		
		ResultBean respuesta = new ResultBean();
		respuesta.addAttachment(BEAN_PDF, bean);
		
		return respuesta;	
	}
	
	
	/**
	 * Metodo que a partir de un TramitesBean de registro PDF devuelve el String que contiene la linea a 
	 * almacenar en el fichero a exportar. Completa los campos para que tengan el tamaño necesario.
	 * 
	 * @param bean
	 * @return Devuelve el String con la linea de registro PDF
	 */
	public static final String obtenerLinea(ImportacionDGTPDFBean bean){
		StringBuffer line = new StringBuffer();
		String campo;
		
		campo = bean.getRegPDF();
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_TIPO_REG));
		}else{
			line.append(campo);
		}
		
		campo = bean.getNumColegiado();
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_NUM_PROFESIONAL));
		}else{
			line.append(changeSize(campo, TAM_NUM_PROFESIONAL, ' ', false));
		}
		
		campo = bean.getNombre();
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_NOMBRE_PROFESIONAL));
		}else{
			line.append(changeSize(campo, TAM_NOMBRE_PROFESIONAL, ' ', false));
		}
		
		return line.toString();
	}
	
	
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
