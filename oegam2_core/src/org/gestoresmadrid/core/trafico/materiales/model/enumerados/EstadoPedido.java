package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum EstadoPedido {
	Iniciado("0", "Iniciado"),
	Borrador("1", "Borrador"),
	Solicitado_Consejo("2", "Solicitado al Consejo/DGT"),
	Solicitado_FNMT("3", "Solicitado FNMT"),
	Recepcionado_Consejo("4", "Recepcionado Consejo"),
	Enviado_Colegio("5", "Enviado Colegio"),
	Entregado("6", "Entregado");

	private String valorEnum;
	private String nombreEnum;
	
	private EstadoPedido(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadoPedido convertir(Long valorEnum) {
		
		if(valorEnum != null) {
			for(EstadoPedido estado : EstadoPedido.values()) {
				if(Integer.parseInt(estado.getValorEnum()) == valorEnum.intValue()) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valor){
		if(valor != null){
			for(EstadoPedido estado : EstadoPedido.values()){
				boolean igual = true;
				try {
					igual = (Integer.parseInt(estado.getValorEnum()) == valor.intValue());
					if (igual) return estado.getNombreEnum();
				} catch (Exception ex) {
					// TODO poner log
					//log.debug("No se ha podido parsear");
				}
			}
		}
		return null;
	}
	
	public static Long convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoPedido estado : EstadoPedido.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return new Long(estado.getValorEnum());
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoPedido estadoPedido){
		if(estadoPedido != null){
			for(EstadoPedido estado : EstadoPedido.values()){
				if(estado.getValorEnum() == estadoPedido.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoLong(Long estado){
		if(estado != null){
			return convertirTexto(estado);
		}
		return "";
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

}
