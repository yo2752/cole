package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum TipoDocumentoEnum {
	Distintivo(1L, "Distintivo") {
		public String toString() {
	        return "1";
	    }
	},
	PermisoConducir(2L, "Permiso Conducir")  {
		public String toString() {
	        return "2";
	    }
	},
	FichaTecnica(3L, "Ficha Técnica")  {
		public String toString() {
	        return "3";
	    }
	};
	
	private Long   valorEnum;
	private String nombreEnum;
	
	private TipoDocumentoEnum(Long valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static TipoDocumentoEnum convertir(Long valorEnum) {
		if(valorEnum != null){
			if(valorEnum != null) {
				for(TipoDocumentoEnum tipoDocumento : TipoDocumentoEnum.values()) {
					if(tipoDocumento.getValorEnum().equals(valorEnum)) {
						return tipoDocumento;
					}
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valorEnum){
		if(valorEnum != null){
			for(TipoDocumentoEnum tipoDocumento : TipoDocumentoEnum.values()){
				if (tipoDocumento.getValorEnum().equals(valorEnum))
					return tipoDocumento.getNombreEnum();
			}
		}
		return null;
	}
	
	public static Long convertirNombre(String nombre){
		if(nombre != null){
			for(TipoDocumentoEnum tipoDocumento : TipoDocumentoEnum.values()){
				if(tipoDocumento.getNombreEnum().equals(nombre)){
					return tipoDocumento.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoDocumentoEnum tipoDocumento){
		if(tipoDocumento != null){
			for(TipoDocumentoEnum item : TipoDocumentoEnum.values()){
				if(item.getValorEnum() == tipoDocumento.getValorEnum()){
					return item.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static Long convertirLong(TipoDocumentoEnum tipoDocumento){
		if(tipoDocumento != null){
			for(TipoDocumentoEnum item : TipoDocumentoEnum.values()){
				if(item.getValorEnum() == tipoDocumento.getValorEnum()){
					return item.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoLong(Long tipoDocumento){
		if(tipoDocumento != null){
			return convertirTexto(tipoDocumento);
		}
		return "";
	}

	// Getter and Setter
	public Long getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(Long valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	

}
