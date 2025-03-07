package org.gestoresmadrid.core.modelos.model.enumerados;

public enum ErroresWSModelo600601 {
	
	Error000("000","Ejecuci�n con �xito"),
	Error001("001","Formato del mensaje de entrada incorrecto"),
	Error002("002","El invocante no dispone de permisos"),
	Error003("003","El modelo indicado no es correcto"),
	Error004("004","La firma del presentador no es v�lida"),
	Error005("005","Ya se ha realizado un pago y presentaci�n con ese identificador de autoliquidaci�n indicado en el documento XML, pero fue ordenado con otros datos (se ha modificado el CCC). Si desea obtener el NCCM y el CSO deber� volver a invocar con los datos iniciales con los que se generaron el NCCM y el CSO"),
	Error006("006","Ya se ha recibido una orden de pago y presentaci�n con ese identificador de autoliquidaci�n indicado en el documento XML, pero fue hecha con otros datos (se han modificado datos distintos al CCC). Si desea conocer el estado en que se encuentra deber� volver a invocar con los datos iniciales"),
	Error007("007","Ya se ha recibido una orden de pago y presentaci�n con ese identificador de autoliquidaci�n indicado en el documento XML, pero debido a un problema t�cnico con la entidad financiera no es posible conocer su estado. Hasta que no se resuelva el problema no puede ordenar el pago por otro CCC"),
	Error008("008","S�lo se permite el pago y presentaci�n de remesas con una autoliquidaci�n"),
	Error009("009","Error al procesar el documento XML: El contenido no es v�lido"),
	Error010("010","Error al procesar el documento XML: La informaci�n asociada a la expresi�n abreviada no es v�lida"),
	Error011("011","Error al procesar el documento XML: El NIF de algunos de los intervinientes no es v�lido"),
	Error012("012","Error al procesar el documento XML: Faltan datos de car�cter obligatorio"),
	Error013("013","No dispone de un contrato v�lido para gestionar la autoliquidaci�n"),
	Error014("014","La entidad financiera no es v�lida"),
	Error015("015","Se ha producido un error al gestionar la autoliquidaci�n"),
	Error016("016","Falta ficha notarial"),
	Error017("017","Datos de la ficha notarial no coincidentes"),
	Error018("018","Falta escritura"),
	Error100("100","Error en el pago telem�tico: La entidad financiera informa de un error en el proceso de pago"),
	Error101("101","Error en el pago telem�tico: La entidad financiera informa que el tipo de operaci�n es incorrecta o esta ausente"),
	Error102("102","Error en el pago telem�tico: La entidad financiera informa que el N.I.F. de certificado esta ausente o es an�nimo"),
	Error103("103","Error en el pago telem�tico: La entidad financiera informa que el N.I.F. de certificado corresponde a una persona jur�dica"),
	Error104("104","Error en el pago telem�tico: La entidad financiera informa que la entidad bancaria esta ausente o es err�nea"),
	Error105("105","Error en el pago telem�tico: La entidad financiera informa que la sucursal esta ausente o es err�nea"),
	Error106("106","Error en el pago telem�tico: La entidad financiera informa que el N.I.F. no tiene esta cuenta"),
	Error107("107","Error en el pago telem�tico: La entidad financiera informa que la cuenta esta ausente o es err�nea"),
	Error108("108","Error en el pago telem�tico: La entidad financiera informa que el d�gito de control esta ausente o es err�neo"),
	Error109("109","Error en el pago telem�tico: La entidad financiera informa que la cuenta no es v�lida para realizar estos cargos"),
	Error110("110","Error en el pago telem�tico: La entidad financiera informa que el c�digo de moneda es err�neo"),
	Error111("111","Error en el pago telem�tico: La entidad financiera informa que la fecha de operaci�n esta ausente o es err�nea"),
	Error113("113","Error en el pago telem�tico: La entidad financiera informa que no hay saldo suficiente para realizar la operaci�n"),
	Error186("186","Error en el pago telem�tico: La entidad financiera informa que no atiende la solicitud de cargo en este rango horario"),
	Error199("199","Error en el pago telem�tico: El NCCM obtenido de la entidad financiera es incorrecto"),
	Error400("400","Error en la presentaci�n telem�tica: No se ha podido realizar la presentaci�n por motivos t�cnicos"),
	Error401("401","Error en la generaci�n del justificante: Se ha realizado la presentaci�n pero no se ha generado el justificante o carta de pago por motivos t�cnicos"),
	Error402("402","Error en la generaci�n de la diligencia certificada de presentaci�n: Se ha realizado la presentaci�n pero no se ha generado la diligencia por motivos t�cnicos"),
	Error999("999","Error no catalogado");
	
	
	private String valorEnum;
	private String nombreEnum;
	
	
	private ErroresWSModelo600601(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public String getValorEnum() {
		return valorEnum;
	}
	
	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static String getNombrePorValor(String valor){
		if(valor != null && !valor.isEmpty()){
			for(ErroresWSModelo600601 error : ErroresWSModelo600601.values()){
				if(error.getValorEnum().equals(valor)){
					return error.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String getValorPorNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(ErroresWSModelo600601 error : ErroresWSModelo600601.values()){
				if(error.getNombreEnum().equals(nombre)){
					return error.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static ErroresWSModelo600601 convertirValor(String valor){
		if(valor != null && !valor.isEmpty()){
			for(ErroresWSModelo600601 error : ErroresWSModelo600601.values()){
				if(error.getValorEnum().equals(valor)){
					return error;
				}
			}
		}
		return null;
	}
	
	public static ErroresWSModelo600601 convertirNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(ErroresWSModelo600601 error : ErroresWSModelo600601.values()){
				if(error.getNombreEnum().equals(nombre)){
					return error;
				}
			}
		}
		return null;
	}
}
