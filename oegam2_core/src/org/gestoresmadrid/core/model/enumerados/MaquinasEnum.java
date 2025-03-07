package org.gestoresmadrid.core.model.enumerados;

public enum MaquinasEnum {

	DESARROLLO("C://datos//logs","Desarrollo","PRC"){
		public String toString(){
			return "";
		}
	},
	PRE_12("192.168.50.12","Pre Windows","PRC"){
		public String toString(){
			return "192.168.50.12";
		}
	},
	PRE_48("192.168.50.48","Pre Ubuntu","FRT"){
		public String toString(){
			return "192.168.50.48";
		}
	},
	TEST_96("192.168.50.96","Test 96","FRT"){
		public String toString(){
			return "192.168.50.96";
		}
	},
	TEST_52("192.168.50.52","Test Procesos 52","PRC"){
		public String toString(){
			return "192.168.50.52";
		}
	},
	TEST_53("192.168.50.53","Test Procesos 53","PRC"){
		public String toString(){
			return "192.168.50.53";
		}
	},
	PRO_91("192.168.50.91","Pro 91","FRT"){
		public String toString(){
			return "192.168.50.91";
		}
	},
	PRO_92("192.168.50.92","Pro 92","FRT"){
		public String toString(){
			return "192.168.50.92";
		}
	},
	PRO_93("192.168.50.93","Pro 93","FRT"){
		public String toString(){
			return "192.168.50.93";
		}
	},
	PRO_94("192.168.50.94","Pro 94","FRT"){
		public String toString(){
			return "192.168.50.94";
		}
	},
	PRO_95("192.168.50.95","Pro 95","FRT"){
		public String toString(){
			return "192.168.50.95";
		}
	},
	PRO_98("192.168.50.98","Pro Procesos 98","PRC"){
		public String toString(){
			return "192.168.50.98";
		}
	},
	PRO_100("192.168.50.100","Pro Procesos 100","PRC"){
		public String toString(){
			return "192.168.50.100";
		}
	},
	PRO_138("192.168.50.138","Pro Procesos 138","PRC"){
		public String toString(){
			return "192.168.50.138";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String tipoEnum;

	private MaquinasEnum(String valorEnum, String nombreEnum, String tipoEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.tipoEnum = tipoEnum;
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

	public String getTipoEnum() {
		return tipoEnum;
	}

	public void setTipoEnum(String tipoEnum) {
		this.tipoEnum = tipoEnum;
	}

	public static MaquinasEnum convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(MaquinasEnum maquina : MaquinasEnum.values()){
				if(maquina.getValorEnum().equals(valorEnum)){
					return maquina;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null){
			for(MaquinasEnum maquina : MaquinasEnum.values()){
				if(maquina.getValorEnum().equals(valor)){
					return maquina.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(MaquinasEnum maquinaEnum){
		if(maquinaEnum != null){
			for(MaquinasEnum maquina : MaquinasEnum.values()){
				if(maquina.getValorEnum() == maquinaEnum.getValorEnum()){
					return maquina.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static MaquinasEnum[] getListaMaquinasDesa(){
		MaquinasEnum[] maquinasPre = new MaquinasEnum[1];
		maquinasPre[0] = MaquinasEnum.DESARROLLO;
		return maquinasPre;
	}

	public static MaquinasEnum[] getListaMaquinasPre(){
		MaquinasEnum[] maquinasPre = new MaquinasEnum[2];
		maquinasPre[0] = MaquinasEnum.PRE_12;
		maquinasPre[1] = MaquinasEnum.PRE_48;
		return maquinasPre;
	}

	public static MaquinasEnum[] getListaMaquinasTest(){
		MaquinasEnum[] maquinasPre = new MaquinasEnum[3];
		maquinasPre[0] = MaquinasEnum.TEST_96;
		maquinasPre[1] = MaquinasEnum.TEST_52;
		maquinasPre[2] = MaquinasEnum.TEST_53;
		return maquinasPre;
	}

	public static MaquinasEnum[] getListaMaquinasPro(){
		MaquinasEnum[] maquinasPre = new MaquinasEnum[8];
		maquinasPre[0] = MaquinasEnum.PRO_91;
		maquinasPre[1] = MaquinasEnum.PRO_92;
		maquinasPre[2] = MaquinasEnum.PRO_93;
		maquinasPre[0] = MaquinasEnum.PRO_94;
		maquinasPre[1] = MaquinasEnum.PRO_95;
		maquinasPre[2] = MaquinasEnum.PRO_98;
		maquinasPre[0] = MaquinasEnum.PRO_100;
		maquinasPre[1] = MaquinasEnum.PRO_138;
		return maquinasPre;
	}
}