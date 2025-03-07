package org.gestoresmadrid.core.enumerados;

/**
 * Encapsula los tipos de fichero que se pueden importar a oegam de tr�fico.
 * 
 * La propiedad valorEnum contiene la correspondencia en la tabla FUNCION, 
 * y en USUARIO_FUNCION_SIN_ACCESO para determinar si un usuario tiene o no 
 * permisos para importar ese tipo de ficheros en OEGAM
 * 
 * La propiedad nombreEnum contiene la referencia en las properties 
 * al mensaje de error asociado a la falta de permiso de un
 * usuario para importar el fichero especificado.
 *
 */
public enum FicherosImportacion {
	
	IMPORTAR_DGT("Fichero dgt no v�lido", "OTIM01"){
		public String toString() {
	        return ".DGT";
	    }
	},
	IMPORTAR_DGT_TRANSMISION_ACTUAL("No tiene permisos para importar un tramite de este tipo", "OTIM011"){
		public String toString() {
	        return ".DGT TRANSMISION ACTUAL";
	    }
	},
	IMPORTAR_XML_MATRICULACION("Fichero xml de matriculaci�n no v�lido", "OTIM02")
	{
		public String toString() {
	        return ".XML MATRICULACIӓN";
	    }
	},
	IMPORTAR_XML_MATRICULACION_MATW("Fichero xml de matriculaci�n no v�lido", "OTIM92")
	{
		public String toString() {
	        return ".XML MATRICULACIӓN";
	    }
	},
	IMPORTAR_XML_TRANSMISION("Fichero xml de transmisi�n no v�lido", "OTIM03"){
		public String toString() {
	        return ".XML TRANSMISI�N";
	    }
	}, 
	IMPORTAR_XML_TRANSMISION_ELECTRONICA("Error t�cnico","OTIM04"){
		public String toString(){
			return ".XML TRANSMISI�N ELECTR�NICA"; 
		}
	},
	IMPORTAR_XML_CET("Fichero xml de cet no v�lido","OTIM05"){
		public String toString(){
			return ".XML CET"; 
		}
	},
	IMPORTAR_XML_BAJA("Fichero xml de baja no v�lido","OTIM06"){
		public String toString(){
			return ".XML BAJA"; 
		}
	},
	IMPORTAR_XLS_BAJA("Fichero xls de baja no v�lido","OTIM07"){
		public String toString(){
			return ".XLS BAJA"; 
		}
	},
	IMPORTAR_XSL_DUPLICADO("Fichero xls de duplicado no v�lido","OTIM08"){
		public String toString(){
			return ".XLS DUPLICADO"; 
		}
	},
	IMPORTAR_XSL_CAMBIOSERVICIO("Fichero xls de cambio de servicio no v�lido","OTIM093"){
		public String toString(){
			return ".XLS CAMBIOSERVICIO"; 
		}
	},
	IMPORTAR_SOLICITUD("Fichero de solicitud no v�lido","OTIM09"){
		public String toString(){
			return "SOLICITUD"; 
		}
	},
	IMPORTAR_PEGATINAS("Fichero de pegatinas para la Etiqueta de la matr�cula no v�lido","OTIM091"){
		public String toString(){
			return "PEGATINAS"; 
		}
	},
	IMPORTAR_INTEVE("Fichero de solicitudes inteve es valido","OTIM10"){
		public String toString(){
			return "PEGATINAS"; 
		}
	},
	IMPORTAR_XML_DUPLICADO("No tiene permisos para importar tr�mites de este tipo","OTIM092"){
		public String toString(){
			return ".XML DUPLICADO"; 
		}
	}
	;
	
	private String nombreEnum;
	private String valorEnum;
	
	private FicherosImportacion(String nombreEnum, String valorEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
}
