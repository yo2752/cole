package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum EstadoIncidencia {
	Borrador("0", "Borrador"),
	Activo("1", "Activo");

	private String valorEnum;
	private String nombreEnum;
	
	private EstadoIncidencia(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadoIncidencia convertir(Long valorEnum) {
		
		if(valorEnum != null) {
			for(EstadoIncidencia estado : EstadoIncidencia.values()) {
				if(Integer.parseInt(estado.getValorEnum()) == valorEnum.intValue()) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valor){
		if(valor != null){
			for(EstadoIncidencia estado : EstadoIncidencia.values()){
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
			for(EstadoIncidencia estado : EstadoIncidencia.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return new Long(estado.getValorEnum());
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoIncidencia estadoPedido){
		if(estadoPedido != null){
			for(EstadoIncidencia estado : EstadoIncidencia.values()){
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
