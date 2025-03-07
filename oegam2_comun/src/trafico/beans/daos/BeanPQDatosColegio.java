package trafico.beans.daos;
/*****************************************************
   PROCEDURE: Datos_colegio
   PARAMETROS:
   DESCRIPCION:
 *****************************************************************************************************/   
 /* PROCEDURE DATOS_COLEGIO (P_COLEGIO IN OUT COLEGIO.COLEGIO%TYPE,
                           P_NOMBRE OUT COLEGIO.NOMBRE%TYPE,
                           P_CIF OUT COLEGIO.CIF%TYPE,
                           P_CORREO_ELECTRONICO OUT COLEGIO.CORREO_ELECTRONICO%TYPE,
                           P_CODE OUT NUMBER,
                           P_SQLERRM OUT VARCHAR2);*/
 
 /*****************************************************
   PROCEDURE: detalle
   PARAMETROS:               
               contrato* información de salida con la información asociada del contrato
               colegiado* información de salida con la información asociada del colegiado
               P_CODE: Parámetro de salida, numerico 0 si todo es correcto SQLCODE si se produce un error
               P_SQLERRM: Parámetro de salida, varchar2 'Correcto' si todo es correcto SQLERRM si se produce un error
               c_aplicaciones: Cursor de salida, devolvera la información asociada a las aplicaciones a las que tenga acceso y no tenga acceso el contrato
   DESCRIPCION:
  Con el Rowid del contrato se accederá a recoger toda la información del mismo y del colegiado al que pertenece.
  También se devolverán todas las aplicaciones del sistema indicando si el contrato tiene acceso a las mismas o no.*/



public class BeanPQDatosColegio extends BeanPQGeneral {
	
		
	private Object P_COLEGIO ;
	private Object P_NOMBRE ;
	private Object P_CIF ;
	private Object P_CORREO_ELECTRONICO ;
	
	
	public Object getP_COLEGIO() {
		return P_COLEGIO;
	}
	public void setP_COLEGIO(Object pCOLEGIO) {
		P_COLEGIO = pCOLEGIO;
	}
	public Object getP_NOMBRE() {
		return P_NOMBRE;
	}
	public void setP_NOMBRE(Object pNOMBRE) {
		P_NOMBRE = pNOMBRE;
	}
	public Object getP_CIF() {
		return P_CIF;
	}
	public void setP_CIF(Object pCIF) {
		P_CIF = pCIF;
	}
	public Object getP_CORREO_ELECTRONICO() {
		return P_CORREO_ELECTRONICO;
	}
	public void setP_CORREO_ELECTRONICO(Object pCORREOELECTRONICO) {
		P_CORREO_ELECTRONICO = pCORREOELECTRONICO;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}