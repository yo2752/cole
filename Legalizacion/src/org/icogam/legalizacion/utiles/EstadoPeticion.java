package org.icogam.legalizacion.utiles;

public enum EstadoPeticion {
		
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
		INCIDENCIA("5","INCIDENCIA"){
			public String toString() {
		        return "5";
		    }
		},
		NOENTREGADO("3","NO ENTREGADO"){
			public String toString() {
		        return "3";
		    }
		},
		FINALIZADO("4","FINALIZADO"){
			public String toString() {
		        return "4";
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

		
		private EstadoPeticion(String valorEnum, String nombreEnum) {
			this.valorEnum = valorEnum;
			this.nombreEnum = nombreEnum;
		}
		
		
		 public static EstadoPeticion convertir(Integer valorEnum) {    
		        
		    	if(valorEnum==null)
		    		return null;
		    	
		    	if(valorEnum.equals(1)) 
					return INICIADO;
		    	else if(valorEnum.equals(2)) 
					return SOLICITADO;
		    	else if(valorEnum.equals(3)) 
					return NOENTREGADO;
		    	else if(valorEnum.equals(4)) 
					return FINALIZADO;
		    	else if(valorEnum.equals(5)) 
					return INCIDENCIA;
		    	else
					return null;
		   }
}
