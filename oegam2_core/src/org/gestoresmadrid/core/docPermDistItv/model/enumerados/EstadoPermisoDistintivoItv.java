package org.gestoresmadrid.core.docPermDistItv.model.enumerados;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public enum EstadoPermisoDistintivoItv {
	Iniciado("0", "Iniciado"){
		public String toString() {
	        return "0";
	    }
	},
	Generado("1", "Generado"){
		public String toString() {
	        return "1";
	    }
	},
	Solcitud_Impresion("2", "Solicitud Impresion"){
		public String toString() {
	        return "2";
	    }
	},Pendiente_Respuesta("3", "Pendiente Respuesta"){
		public String toString() {
	        return "3";
	    }
	},Doc_Recibido("4", "Documentación Recibida"){
		public String toString() {
	        return "4";
	    }
	},Doc_Recibido_Parcialmente("5", "Documentación Recibida Parcialmente"){
		public String toString() {
	        return "5";
	    }
	},Finalizado_Error("6", "Finalizado Error"){
		public String toString() {
	        return "6";
	    }
	}, Pendiente_Impresion("7", "Pendiente Impresion"){
		public String toString() {
	        return "7";
	    }
	}, Impresion_OK("8", "Impresion OK"){
		public String toString() {
	        return "8";
	    }
	},IMPR_No_Encontrado("9", "IMPR No Encontrado"){
		public String toString() {
	        return "9";
	    }
	},IMPRIMIENDO("10", "Imprimiendo"){
		public String toString() {
	        return "10";
	    }
	},GENERADO_KO("11", "Generado KO"){
		public String toString() {
	        return "11";
	    }
	},PDTE_CONF_COLEGIADO("12", "Pendiente Confirmacion Colegiado"){
		public String toString() {
	        return "12";
	    }
	},NO_GENERABLE("13", "No Generable"){
		public String toString() {
	        return "13";
	    }	
	},SOLICITANDO_IMPR("14", "Solicitando IMPR"){
		public String toString() {
	        return "14";
	    }
	}, ORDENADO_DOC("15", "Ordenando Documento"){
		public String toString() {
	        return "15";
	    }
	}, IMPRIMIENDO_GESTORIA("16", "Imprimiendo Gestoria"){
		public String toString() {
	        return "16";
	    }
	}, IMPRESO_GESTORIA("17", "Impreso Gestoria"){
		public String toString() {
	        return "17";
	    }
	}, GENERADO_JEFATURA("18", "Generado Jefatura"){
		public String toString() {
	        return "18";
	    }
	}, ANULADO("19", "Anulado"){
		public String toString() {
	        return "19";
	    }	
	}, DOCUMENTO_ORDENADO("20", "Documento Ordenado"){
		public String toString() {
	        return "20";
	    }	
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoPermisoDistintivoItv(String valorEnum, String nombreEnum) {
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
	
	public static EstadoPermisoDistintivoItv convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoPermisoDistintivoItv estado : EstadoPermisoDistintivoItv.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(EstadoPermisoDistintivoItv estado : EstadoPermisoDistintivoItv.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoPermisoDistintivoItv estado : EstadoPermisoDistintivoItv.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoPermisoDistintivoItv estadoPrmDstv){
		if(estadoPrmDstv != null){
			for(EstadoPermisoDistintivoItv estado : EstadoPermisoDistintivoItv.values()){
				if(estado.getValorEnum() == estadoPrmDstv.getValorEnum()){
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

	public static List<EstadoPermisoDistintivoItv> getEstadosConsulta() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Respuesta);
		listaEstados.add(EstadoPermisoDistintivoItv.Doc_Recibido);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR);
		return listaEstados;
	}

	public static List<EstadoPermisoDistintivoItv> getEstadosEitvConsulta() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Respuesta);
		listaEstados.add(EstadoPermisoDistintivoItv.Doc_Recibido);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.GENERADO_KO);
		listaEstados.add(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR);
		return listaEstados;
	}

	public static List<EstadoPermisoDistintivoItv> getEstadosImpresiones() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Generado);
		listaEstados.add(EstadoPermisoDistintivoItv.ORDENADO_DOC);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Impresion);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.Impresion_OK);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRESO_GESTORIA);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO_GESTORIA);
		listaEstados.add(EstadoPermisoDistintivoItv.PDTE_CONF_COLEGIADO);
		listaEstados.add(EstadoPermisoDistintivoItv.ANULADO);
		listaEstados.add(EstadoPermisoDistintivoItv.DOCUMENTO_ORDENADO);
		return listaEstados;
	}

	public static List<EstadoPermisoDistintivoItv> getEstadosImpresionesJefatura() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Generado);
		listaEstados.add(EstadoPermisoDistintivoItv.GENERADO_JEFATURA);
		listaEstados.add(EstadoPermisoDistintivoItv.ORDENADO_DOC);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Impresion);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.Impresion_OK);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRESO_GESTORIA);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO_GESTORIA);
		listaEstados.add(EstadoPermisoDistintivoItv.PDTE_CONF_COLEGIADO);
		listaEstados.add(EstadoPermisoDistintivoItv.ANULADO);
		listaEstados.add(EstadoPermisoDistintivoItv.DOCUMENTO_ORDENADO);
		return listaEstados;
	}
	
	public static List<EstadoPermisoDistintivoItv> getEstadosJustificante() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Generado);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.Impresion_OK);
		return listaEstados;
	}
}
