package org.gestoresmadrid.core.modelos.model.enumerados;

public enum ErroresWSModelo600601 {
	
	Error000("000","Ejecución con éxito"),
	Error001("001","Formato del mensaje de entrada incorrecto"),
	Error002("002","El invocante no dispone de permisos"),
	Error003("003","El modelo indicado no es correcto"),
	Error004("004","La firma del presentador no es válida"),
	Error005("005","Ya se ha realizado un pago y presentación con ese identificador de autoliquidación indicado en el documento XML, pero fue ordenado con otros datos (se ha modificado el CCC). Si desea obtener el NCCM y el CSO deberá volver a invocar con los datos iniciales con los que se generaron el NCCM y el CSO"),
	Error006("006","Ya se ha recibido una orden de pago y presentación con ese identificador de autoliquidación indicado en el documento XML, pero fue hecha con otros datos (se han modificado datos distintos al CCC). Si desea conocer el estado en que se encuentra deberá volver a invocar con los datos iniciales"),
	Error007("007","Ya se ha recibido una orden de pago y presentación con ese identificador de autoliquidación indicado en el documento XML, pero debido a un problema técnico con la entidad financiera no es posible conocer su estado. Hasta que no se resuelva el problema no puede ordenar el pago por otro CCC"),
	Error008("008","Sólo se permite el pago y presentación de remesas con una autoliquidación"),
	Error009("009","Error al procesar el documento XML: El contenido no es válido"),
	Error010("010","Error al procesar el documento XML: La información asociada a la expresión abreviada no es válida"),
	Error011("011","Error al procesar el documento XML: El NIF de algunos de los intervinientes no es válido"),
	Error012("012","Error al procesar el documento XML: Faltan datos de carácter obligatorio"),
	Error013("013","No dispone de un contrato válido para gestionar la autoliquidación"),
	Error014("014","La entidad financiera no es válida"),
	Error015("015","Se ha producido un error al gestionar la autoliquidación"),
	Error016("016","Falta ficha notarial"),
	Error017("017","Datos de la ficha notarial no coincidentes"),
	Error018("018","Falta escritura"),
	Error100("100","Error en el pago telemático: La entidad financiera informa de un error en el proceso de pago"),
	Error101("101","Error en el pago telemático: La entidad financiera informa que el tipo de operación es incorrecta o esta ausente"),
	Error102("102","Error en el pago telemático: La entidad financiera informa que el N.I.F. de certificado esta ausente o es anónimo"),
	Error103("103","Error en el pago telemático: La entidad financiera informa que el N.I.F. de certificado corresponde a una persona jurídica"),
	Error104("104","Error en el pago telemático: La entidad financiera informa que la entidad bancaria esta ausente o es errónea"),
	Error105("105","Error en el pago telemático: La entidad financiera informa que la sucursal esta ausente o es errónea"),
	Error106("106","Error en el pago telemático: La entidad financiera informa que el N.I.F. no tiene esta cuenta"),
	Error107("107","Error en el pago telemático: La entidad financiera informa que la cuenta esta ausente o es errónea"),
	Error108("108","Error en el pago telemático: La entidad financiera informa que el dígito de control esta ausente o es erróneo"),
	Error109("109","Error en el pago telemático: La entidad financiera informa que la cuenta no es válida para realizar estos cargos"),
	Error110("110","Error en el pago telemático: La entidad financiera informa que el código de moneda es erróneo"),
	Error111("111","Error en el pago telemático: La entidad financiera informa que la fecha de operación esta ausente o es errónea"),
	Error113("113","Error en el pago telemático: La entidad financiera informa que no hay saldo suficiente para realizar la operación"),
	Error186("186","Error en el pago telemático: La entidad financiera informa que no atiende la solicitud de cargo en este rango horario"),
	Error199("199","Error en el pago telemático: El NCCM obtenido de la entidad financiera es incorrecto"),
	Error400("400","Error en la presentación telemática: No se ha podido realizar la presentación por motivos técnicos"),
	Error401("401","Error en la generación del justificante: Se ha realizado la presentación pero no se ha generado el justificante o carta de pago por motivos técnicos"),
	Error402("402","Error en la generación de la diligencia certificada de presentación: Se ha realizado la presentación pero no se ha generado la diligencia por motivos técnicos"),
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
