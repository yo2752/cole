package org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados;


public enum EstadoDatosBancarios {

	HABILITADO("0","HABILITADO"),
	DESHABILITADO("1","DESHABILITADO"),
	ELIMINIADO("2","ELIMINIADO");
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoDatosBancarios(String valorEnum, String nombreEnum) {
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
		if(valor != null){
			for(EstadoDatosBancarios estadoDatosBancarios : EstadoDatosBancarios.values()){
				if(estadoDatosBancarios.getValorEnum().equals(valor)){
					return estadoDatosBancarios.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String getValorPorNombre(String nombre){
		if(nombre != null){
			for(EstadoDatosBancarios estadoDatosBancarios : EstadoDatosBancarios.values()){
				if(estadoDatosBancarios.getNombreEnum().equals(nombre)){
					return estadoDatosBancarios.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static EstadoDatosBancarios convertirTexto(String nombre){
		if(nombre != null){
			for(EstadoDatosBancarios estadoDatosBancarios : EstadoDatosBancarios.values()){
				if(estadoDatosBancarios.getNombreEnum().equals(nombre)){
					return estadoDatosBancarios;
				}
			}
		}
		return null;
	}
	
	public static EstadoDatosBancarios convertirValor(String valor){
		if(valor != null){
			for(EstadoDatosBancarios estadoDatosBancarios : EstadoDatosBancarios.values()){
				if(estadoDatosBancarios.getValorEnum().equals(valor)){
					return estadoDatosBancarios;
				}
			}
		}
		return null;
	}
}
