package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum CapitaniaMaritima {

	A_CORUÑA("1","A Coruña"),
	ALGECIRAS("2","Algeciras"),
	ALICANTE("3","Alicante"),
	ALMERÍA("4","Almería"),
	AVILÉS("5","Avilés"),
	BARCELONA("6","Barcelona"),
	BILBAO("7","Bilbao"),
	BURELA("8","Burela"),
	CADIZ("9","Cadiz"),
	CARTAGENA("10","Cartagena"),
	CASTELLON("11","Castellon"),
	CEUTA("12","Ceuta"),
	FERROL("13","Ferrol"),
	GIJON("14","Gijon"),
	HUELVA("15","Huelva"),
	LAS_PALMAS("16","Las palmas"),
	MALAGA("17","Malaga"),
	MELILLA("18","Melilla"),
	MOTRIL("19","Motril"),
	PALAMOS("20","Palamos"),
	PALMA_DE_MALLORCA("21","Palma de mallorca"),
	PASAIA("22","Pasaia"),
	SANTA_CRUZ_DE_TENERIFE("23","Santa cruz de tenerife"),
	SANTANDER("24","Santander"),
	SEVILLA("25","Sevilla"),
	TARRAGONA("26","Tarragona"),
	VALENCIA("27","Valencia"),
	VIGO("28","Vigo"),
	VILLAGARCIA("29","Villagarcia"),
	ADRA("30","Adra"),
	AGUILAS("31","Aguilas"),
	ARENYS_DE_MAR("32","Arenys de mar"),
	ARRECIFE_LANZAROTE("33","Arrecife - lanzarote"),
	AYAMONTE("34","Ayamonte"),
	BAYONA("35","Bayona"),
	BERMEO("36","Bermeo"),
	BLANES_O_ROSAS_GIRONA("37","Blanes o rosas (girona)"),
	BUEU("38","Bueu"),
	CAMARIÑAS("39","Camariñas"),
	CAMBADOS("40","Cambados"),
	CANGAS("41","Cangas"),
	CASTRO_URDIALES("42","Castro urdiales"),
	CEDEIRA("43","Cedeira"),
	CORCUBION("44","Corcubion"),
	DENIA("45","Denia"),
	EL_GROVE("46","El grove"),
	ESTEPONA("47","Estepona"),
	FINISTERRE("48","Finisterre"),
	FUENGIROLA("49","Fuengirola"),
	FUENTERRABÍA_GUIPUZCOA("50","Fuenterrabía (guipuzcoa)"),
	GANDIA("51","Gandia"),
	GARRUCHA("52","Garrucha"),
	GUETARIA("53","Guetaria"),
	LA_GUARDIA("54","La guardia"),
	LAREDO("55","Laredo"),
	LASTRES("56","Lastres"),
	LEQUEITIO("57","Lequeitio"),
	LLANES("58","Llanes"),
	LOS_CRISTIANOS("59","Los cristianos"),
	LUANCO("60","Luanco"),
	LUARCA("61","Luarca"),
	MARBELLA("62","Marbella"),
	MARIN("63","Marin"),
	MAZARRON("64","Mazarron"),
	MUROS("65","Muros"),
	NOYA("66","Noya"),
	ONDARROA("67","Ondarroa"),
	PUEBLA_DEL_CARAMIÑAL("68","Puebla del caramiñal"),
	PUERTO_SANTA_MARIA("69","Puerto santa maria"),
	REDONDELA("70","Redondela"),
	REQUEJADA("71","Requejada"),
	RIBADESELLA("72","Ribadesella"),
	SADA("73","Sada"),
	SAN_CARLOS_DE_LA_RÁPITA("74","San carlos de la rápita"),
	SAN_ESTEBAN_DE_PRAVIA("75","San esteban de pravia"),
	SAN_FELIU_DE_GUIXOLS("76","San feliu de guixols"),
	SAN_LUCAR_DE_BARRAMEDA("77","San lucar de barrameda"),
	SAN_PEDRO_DEL_PINATAR("78","San pedro del pinatar"),
	SAN_VICENTE_DE_LA_BARQUERA("79","San vicente de la barquera"),
	SANJENJO_PORTONOVO("80","Sanjenjo - portonovo"),
	SANTA_POLA("81","Santa pola"),
	SANTURCE("82","Santurce"),
	TARIFA("83","Tarifa"),
	TORREVIEJA("84","Torrevieja"),
	VELEZ_MALAGA("85","Velez-malaga"),
	VILANOVA_I_LA_GELTRÚ("86","Vilanova i la geltrú"),
	VILLAJOYOSA("87","Villajoyosa"),
	VINAROZ("88","Vinaroz"),
	VIVERO("89","Vivero");

	private String valorEnum;
	private String nombreEnum;

	private CapitaniaMaritima(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getKey(){
		return getValorEnum();
	}

	@Override
	public String toString(){
		return getNombreEnum();
	}

}
