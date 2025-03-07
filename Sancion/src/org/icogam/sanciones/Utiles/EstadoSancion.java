package org.icogam.sanciones.Utiles;

public enum EstadoSancion {
		
		INICIADO("1","INICIADO"){
			public String toString() {
		        return "1";
		    }
		},
		SOLICITADO("2","SOLICITADO"){
			public String toString() {
		        return "2";
		    }
		},
		FINALIZADO("3","FINALIZADO"){
			public String toString() {
		        return "3";
		    }
		};
		
		private String valorEnum;
		private String nombreEnum;

		
		public String getValorEnum() {
			return valorEnum;
		}

		public String getNombreEnum() {
			return nombreEnum;
		}

		
		private EstadoSancion(String valorEnum, String nombreEnum) {
			this.valorEnum = valorEnum;
			this.nombreEnum = nombreEnum;
		}
		
		 public static EstadoSancion convertir(Integer valorEnum) {    
		    	if(valorEnum==null)
		    		return null;
		    	
		    	if(valorEnum.equals(1)) 
					return INICIADO;
		    	else if(valorEnum.equals(2)) 
					return SOLICITADO;
		    	else if(valorEnum.equals(3)) 
					return FINALIZADO;
		    	else
					return null;
		   }
}
