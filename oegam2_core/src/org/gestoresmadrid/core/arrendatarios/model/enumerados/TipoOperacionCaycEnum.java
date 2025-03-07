package org.gestoresmadrid.core.arrendatarios.model.enumerados;

import java.math.BigDecimal;

public enum TipoOperacionCaycEnum {
	
    
    // AUTOR: DSR
    // FECHA: 12/01/2018
    // --------------------------------------------------------
    //         IMPORTANTE
    // --------------------------------------------------------
    // Si se añaden mas tipos de operacion hay que tener en cuenta que para poder diferenciar los
    // tipos de operacion que son para Arrendatarios y cuales son para Conductor, actualmente se hace
    // buscando en la descripcion "Arre" y "Conduc" escrito como esta con la primera en mayusculas
    // En caso de no respetar esta premisa no saldra en los combos dicho valor.
    
    
	Alta_Arrendatario("AA", "Alta Arrendatario","1"){
		public String toString() {
	        return "AA";
	    }
	},
	Modif_Arrendatario("MA", "Modificacion Arrendatario","2"){
		public String toString() {
	        return "MA";
	    }
	},
	Alta_Conductor("AC", "Alta Conductor","3"){
		public String toString() {
	        return "AC";
	    }
	},
	Modif_Conductor("MC", "Modificacion Conductor","4"){
		public String toString() {
	        return "MC";
		}
	},
		
		Alta_EmpresaDIRe("AE", "Alta EmpresaDIRe","5"){
			public String toString() {
		        return "AE";
		    }
		},
		Modif_EmpresaDIRe("ME", "Modificacion EmpresaDIRe","6"){
			public String toString() {
		        return "ME";
		    }
		
		
		
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String tipo;
	
	private TipoOperacionCaycEnum(String valorEnum, String nombreEnum, String tipo) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.tipo = tipo;
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
	
	public static TipoOperacionCaycEnum convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoOperacionCaycEnum estado : TipoOperacionCaycEnum.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(TipoOperacionCaycEnum estado : TipoOperacionCaycEnum.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoOperacionCaycEnum estado : TipoOperacionCaycEnum.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(TipoOperacionCaycEnum estadoDev){
		if(estadoDev != null){
			for(TipoOperacionCaycEnum estado : TipoOperacionCaycEnum.values()){
				if(estado.getValorEnum() == estadoDev.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoBigDecimal(BigDecimal estado){
		if(estado != null){
			return convertirTexto(estado.toString());
		}
		return "";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
