package org.gestoresmadrid.core.model.enumerados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TipoFicheroLogEnum {

	TRAFICO("trafico.log","FRT"){
		public String toString(){
			return "trafico";
		}
	},LOGING("loging.log","FRT"){
		public String toString(){
			return "loging";
		}
	},PROC_TRANSMISION("proc_transmision.log","PRC"){
		public String toString(){
			return "proc_transmision";
		}
	},PROC_JUSTIFICANTES("proc_justificantes.log","PRC"){
		public String toString(){
			return "proc_justificantes";
		}
	},PROC_INFO_WS("proc_info_ws.log","PRC"){
		public String toString(){
			return "proc_info_ws";
		}
	},PROC_DUPLICADOS("proc_duplicados.log","PRC"){
		public String toString(){
			return "proc_duplicados";
		}
	},PROC_BAJAS("proc_bajas.log","PRC"){
		public String toString(){
			return "proc_bajas";
		}
	},PROC_576("proc_576.log","PRC"){
		public String toString(){
			return "proc_576";
		}
	},PROC_MATRICULACION("proc_matriculacion.log","PRC"){
		public String toString(){
			return "proc_matriculacion";
		}
	},PROC_YERBABUENA("proc_yerbabuena.log","PRC"){
		public String toString(){
			return "proc_yerbabuena";
		}
	},PROC_DATAS_SENSIBLES("proc_datos_sensibles.log","PRC"){
		public String toString(){
			return "proc_datos_sensibles";
		}
	},PROC_CARGA_DATOS("proc_carga_datos.log","PRC"){
		public String toString(){
			return "proc_carga_datos";
		}
	};
	
	private String valorEnum;
	private String tipoEnum;
	
	private TipoFicheroLogEnum(String valorEnum, String tipoEnum) {
		this.valorEnum = valorEnum;
		this.tipoEnum = tipoEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	
	public String getTipoEnum() {
		return tipoEnum;
	}

	public void setTipoEnum(String tipoEnum) {
		this.tipoEnum = tipoEnum;
	}

	public static TipoFicheroLogEnum convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoFicheroLogEnum tipoFichero : TipoFicheroLogEnum.values()){
				if(tipoFichero.getValorEnum().equals(valorEnum)){
					return tipoFichero;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(TipoFicheroLogEnum tipoFichero : TipoFicheroLogEnum.values()){
				if(tipoFichero.getValorEnum().equals(valor)){
					return tipoFichero.getTipoEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(TipoFicheroLogEnum tipoFicheroEnum){
		if(tipoFicheroEnum != null){
			for(TipoFicheroLogEnum tipoFichero : TipoFicheroLogEnum.values()){
				if(tipoFichero.getValorEnum().equals(tipoFicheroEnum.getValorEnum())){
					return tipoFichero.getTipoEnum();
				}
			}
		}
		return null;
	}
	
	public static List<TipoFicheroLogEnum> getListTiposFicherosPorEntorno(String entorno){
		if(entorno != null && !entorno.isEmpty()){
			TipoFicheroLogEnum[] arrayTipoFicheros = getArrayTiposFicherosPorEntorno(entorno);
			if(arrayTipoFicheros != null){
				List<TipoFicheroLogEnum> lista = new ArrayList<TipoFicheroLogEnum>();
				for(TipoFicheroLogEnum tipoFicheroLog : arrayTipoFicheros){
					lista.add(tipoFicheroLog);
				}
				return lista;
			}
		}
		return Collections.emptyList();
	}
	
	public static TipoFicheroLogEnum[] getArrayTiposFicherosPorEntorno(String entorno){
		if(entorno != null && !entorno.isEmpty()){
			if(entorno.equals("PRC")){
				return TipoFicheroLogEnum.values();
			}
			List<TipoFicheroLogEnum> lista = new ArrayList<TipoFicheroLogEnum>();
			for(TipoFicheroLogEnum tipo : TipoFicheroLogEnum.values()){
				if(tipo.getTipoEnum().equals(entorno)){
					lista.add(tipo);
				}
			}
			if(lista != null && !lista.isEmpty()){
				TipoFicheroLogEnum[] tiposFicheros = new TipoFicheroLogEnum[lista.size()];
				for(int i=0;i<lista.size();i++){
					tiposFicheros[i] = lista.get(i);
				}
				return tiposFicheros;
			}
		}
		return null;
	}
}
