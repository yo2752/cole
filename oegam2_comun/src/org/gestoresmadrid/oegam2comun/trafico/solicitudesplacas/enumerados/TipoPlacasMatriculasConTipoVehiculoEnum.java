package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados;

public enum TipoPlacasMatriculasConTipoVehiculoEnum {
	
	Turismo_Ordinaria_Larga("1", "Turismo Ordinaria Larga", "520", "100", "ambos","1"){
		public String toString() {
			return "1";
		}
	},
	Turismo_Ordinaria_Corta("2", "Turismo Ordinaria Corta", "340", "111", "ambos","2"){
		public String toString() {
			return "2";
		}
	},

	Motocicleta("3", "Motocicleta", "220", "160", "ambos","3"){
		public String toString() {
			return "3";
		}
	},

	Moto_Corta("4", "Moto Corta", "132", "96", "ambos","4"){
		public String toString() {
			return "4";
		}
	},

	Tractor("5", "Tractor", "280", "200", "ambos","5"){
		public String toString() {
			return "5";
		}
	},

	Ciclomotor("6", "Ciclomotor", "100", "168", "ambos","6"){
		public String toString() {
			return "6";
		}
	},
	Turismo_Ordinaria_Alta("7", "Turismo Ordinaria Alta", "340", "220", "trasera","1"){
		public String toString() {
			return "7";
		}
	},
	TaxiVTC("8", "Taxi / VTC", "520", "110", "trasera","1"){
		public String toString() {
			return "8";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String ancho;
	private String alto;
	private String location;
	private String tipoVehiclo;
	

	private TipoPlacasMatriculasConTipoVehiculoEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	private TipoPlacasMatriculasConTipoVehiculoEnum(String valorEnum, String nombreEnum, String ancho, String alto, String location,
			String tipoVehiculo) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.alto = alto;
		this.ancho = ancho;
		this.location = location;
		this.tipoVehiclo = tipoVehiculo;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public String getTipoVehiclo() {
		return tipoVehiclo;
	}

	public void setTipoVehiclo(String tipoVehiclo) {
		this.tipoVehiclo = tipoVehiclo;
	}

	public String getAncho() {
		return ancho;
	}

	public String getAlto() {
		return alto;
	}

	public static TipoPlacasMatriculasConTipoVehiculoEnum convertir(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		switch (numero) {
		case 1: 
			return Turismo_Ordinaria_Larga;
		case 2: 
			return Turismo_Ordinaria_Corta;
		case 3:
			return Motocicleta;
		case 4:
			return Moto_Corta;
		case 5:
			return Tractor;
		case 6:
			return Ciclomotor;
		case 7:
			return Turismo_Ordinaria_Alta;	
		case 8:
			return TaxiVTC; 
		default:
			return null;
		}
	}
	
	public static String convertir(Integer valorEnum) {    

		if(valorEnum==null)
			return null;

		switch (valorEnum) {
		case 1: 
			return "Turismo Ordinaria Larga";
		case 2: 
			return "Turismo Ordinaria Corta";
		case 3:
			return "Motocicleta";
		case 4:
			return "Moto Corta";
		case 5:
			return "Tractor";
		case 6:
			return "Ciclomotor";
		case 7:
			return "Turismo Ordinaria Alta";		
		case 8:
			return "Taxi / VTC";
		default:
			return null;
		}
	}
	
	public static String convertirTexto(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		return convertir(numero);
	}
	
	public static String tranlacion(String tipoVehiculoExterno) {    
         String retTipoVehiculo=null;
		//1,2,3,4,5,6,7
		/************************************************
		RA	REMOLQUE VIVIENDA O CARAVANA
		RC	REMOLQUE HORMIGONERA
		RD	REMOLQUE VOLQUETE DE CANTERA
		RE	REMOLQUE DE GRUA
		RF	REMOLQUE CONTRA INCENDIOS
		RH	MAQUINARIA AGRICOLA ARRASTRADA DE 2 EJES
		R0	REMOLQUE
		R1	REMOLQUE PLATAFORMA
		R2	REMOLQUE CAJA
		R3	REMOLQUE FURGON
		R4	REMOLQUE BOTELLERO
		R5	REMOLQUE CISTERNA
		R6	REMOLQUE JAULA
		R7	REMOLQUE FRIGORIFICO
		R8	REMOLQUE TALLER
		SA	SEMIRREMOLQUE VIVIENDA O CARAVANA
		SC	SEMIRREMOLQUE HORMIGONERA
		SD	SEMIRREMOLQUE VOLQUETE DE CANTERA
		SE	SEMIRREMOLQUE DE GRUA
		SF	SEMIRREMOLQUE CONTRA INCENDIOS
		SH	MAQUINARIA AGRICOLA ARRASTRADA DE 1 EJE
		S0	SEMIRREMOLQUE
		S1	SEMIRREMOLQUE PLATAFORMA
		S2	SEMIRREMOLQUE CAJA
		S3	SEMIRREMOLQUE FURGON
		S4	SEMIRREMOLQUE BOTELLERO
		S5	SEMIRREMOLQUE CISTERNA
		S6	SEMIRREMOLQUE JAULA
		S7	SEMIRREMOLQUE FRIGORIFICO
		S8	SEMIRREMOLQUE TALLER
		0A	CAMION PORTAVEHICULOS
		0B	CAMION MIXTO
		0C	CAMION PORTACONTENEDORES
		0D	CAMION BASURERO
		0E	CAMION ISOTERMO
		0F	CAMION SILO
		0G	VEHICULO MIXTO ADAPTABLE
		00	CAMION
		01	CAMION PLATAFORMA
		02	CAMION CAJA ABIERTA
		03	CAMION FURGON
		04	CAMION BOTELLERO
		05	CAMION CISTERNA
		06	CAMION JAULA
		07	CAMION FRIGORIFICO
		08	CAMION TALLER
		1D	CAMION ARTICULADO VOLQUETE
		1E	CAMION ARTICULADO GRUA
		1F	CAMION ARTICULADO CONTRA INCENDIOS
		20	FURGONETA
		22	AMBULANCIA
		23	COCHE FUNEBRE
		25	TODO TERRENO
		30	AUTOBUS
		31	AUTOBUS ARTICULADO
		32	AUTOBUS MIXTO
		33	BIBLIOBUS
		35	AUTOBUS TALLER
		36	AUTOBUS SANITARIO
		40	TURISMO
		50	MOTOCICLETA DE 2 RUEDAS SIN SIDECAR
		51	MOTOCICLETA CON SIDECAR
		52	MOTOCARRO
		53	AUTOMOVIL DE 3 RUEDAS
		54	CUATRICICLO
		60	COCHE DE INVALIDO
		7A	VIVIENDA
		7B	BARREDORA
		7C	HORMIGONERA
		7D	VEHICULO BASCULANTE
		7E	GRUA
		7F	SERVICIO CONTRA INCENDIOS
		7G	ASPIRADORA DE FANGOS
		7H	MOTOCULTOR
		7I	MAQUINARIA AGRICOLA AUTOMOTRIZ
		7J	PALA CARGADORA-RETROEXCAVADORA
		7K	TREN HASTA 160 PLAZAS
		70	VEHICULO ESPECIAL
		71	PALA CARGADORA
		72	PALA EXCAVADORA
		73	CARRETILLA ELEVADORA
		74	MOTONIVELADORA
		75	COMPACTADORA
		79	QUITANIEVES
		80	TRACTOR
		81	TRACTOCAMION
		82	TRACTOCARRO
		90	CICLOMOTOR DE 2 RUEDAS
		91	CICLOMOTOR DE 3 RUEDAS
		92	CUADRICICLO LIGERO
		***************************************************/
		
		
		
		if("RA".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}	
		
		if("RC".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}	
		if("RD".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}	
		if("RE".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("RF".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("RH".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R0".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R1".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R2".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R3".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R4".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R5".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R6".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R7".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("R8".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SA".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SC".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SD".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SE".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SF".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("SH".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S0".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S1".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S2".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S3".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S4".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S5".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S6".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S7".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("S8".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0A".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0B".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0C".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0D".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0E".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0F".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("0G".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("00".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("01".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("02".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("03".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("04".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("05".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("06".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("07".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("08".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("1D".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("1E".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("1F".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("20".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("22".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("23".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("25".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("30".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("31".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("32".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("33".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("35".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("36".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("40".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("50".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("51".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("52".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("53".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("54".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("60".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7A".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7B".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7C".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7D".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7E".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7F".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7G".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7H".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7I".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7J".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("7K".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("70".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("71".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("72".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("73".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("74".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("75".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("79".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("80".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("81".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("82".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("90".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("91".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		if("92".equals(tipoVehiculoExterno))   {retTipoVehiculo="1";}
		
		
		
		return retTipoVehiculo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
