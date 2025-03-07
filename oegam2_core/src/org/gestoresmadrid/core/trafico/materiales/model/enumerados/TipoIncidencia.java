package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum TipoIncidencia {
	Error1("Error 1", "Error en la impresión"),
	Error2("Error 2", "Error impresión tipo distintivo"),
	Error3("Error 3", "Error impresión duplicada");
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoIncidencia(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static TipoIncidencia convertir(String valorEnum) {
		if(valorEnum != null){
			if(valorEnum != null) {
				for(TipoIncidencia estado : TipoIncidencia.values()) {
					if(estado.getValorEnum().equals(valorEnum)) {
						return estado;
					}
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum){
		if(valorEnum != null){
			for(TipoIncidencia estado : TipoIncidencia.values()){
				if (estado.getValorEnum().equals(valorEnum))
					return estado.getNombreEnum();
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoIncidencia estado : TipoIncidencia.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoIncidencia estadoPedido){
		if(estadoPedido != null){
			for(TipoIncidencia estado : TipoIncidencia.values()){
				if(estado.getValorEnum() == estadoPedido.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	// Getter and Setter
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
