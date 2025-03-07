package org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados;

public enum MotivoJustificante {

	TransferenciaCambioTitularidadCtit("TRCT","TRANSFERENCIA CAMBIO DE TITULARIDAD","T7"){
		public String toString(){
			return "TRCT"; 
		}
	}, 
	
	TransferenciaUltimacionCtit("TRUT", "TRANSFERENCIA ULTIMACION","T7"){
		public String toString() {
	        return "TRUT";
	    }
	},
	TransferenciaNotificacionVentaCtit("TRNO", "TRANSFERENCIA NOTIFICACION DE VENTA","T7")
	{
		public String toString() {
	        return "TRNO";
	    }
	},
	TransferenciaCambioTitularidadTrans("TRCT","TRANSFERENCIA CAMBIO DE TITULARIDAD","T2"){
		public String toString(){
			return "TRCT"; 
		}
	}, 
	
	TransferenciaUltimacionTrans("TRUT", "TRANSFERENCIA ULTIMACION","T2"){
		public String toString() {
	        return "TRUT";
	    }
	},
	TransferenciaNotificacionVentaTrans("TRNO", "TRANSFERENCIA NOTIFICACION DE VENTA","T2")
	{
		public String toString() {
	        return "TRNO";
	    }
	},
	DuplicadoCambioDomicilio("DUCAMBDOM", "DUPLICADO DEL P.C., CAMBIO DE DOMICILIO","T8"){
		public String toString() {
	        return "DUCAMBDOM";
	    }
	}, 
	DuplicadoDeterioro("DUDETE","DUPLICADO DEL P.C., DETERIORO","T8"){
		public String toString(){
			return "DUDETE"; 
		}
	},
	DuplicadoCambioDatos("DUCAMBDAT","DUPLICADO DEL P.C., CAMBIO DE DATOS","T8"){
		public String toString(){
			return "DUCAMBDAT"; 
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String tipoTramite;
	
	private MotivoJustificante(String valorEnum, String nombreEnum, String tipoTramite) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.tipoTramite = tipoTramite;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public static MotivoJustificante convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(MotivoJustificante motivo : MotivoJustificante.values()){
				if(motivo.getValorEnum().equals(valorEnum)){
					return motivo;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(MotivoJustificante motivo : MotivoJustificante.values()){
				if(motivo.getValorEnum().equals(valor)){
					return motivo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(MotivoJustificante motivo : MotivoJustificante.values()){
				if(motivo.getNombreEnum().equals(nombre)){
					return motivo.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(MotivoJustificante motivoJustificante){
		if(motivoJustificante != null){
			for(MotivoJustificante motivo : MotivoJustificante.values()){
				if(motivo.getValorEnum() == motivoJustificante.getValorEnum()){
					return motivo.getNombreEnum();
				}
			}
		}
		return null;
	}
}
