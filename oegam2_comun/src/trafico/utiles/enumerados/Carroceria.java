package trafico.utiles.enumerados;

import trafico.beans.CarroceriaBean;

public enum Carroceria {

	BERLINA("AA", "Berlina"){
		public String toString() {
			return "AA";
		}
	},
	BERLINA_PONTON("AB", "Berlina con pontón trasero") {
		public String toString() {
			return "AB";
		}
	},
	FAMILIAR("AC", "Familiar") {
		public String toString() {
			return "AC";
		}
	},
	CUPE("AD", "Cupé") {
		public String toString() {
			return "AD";
		}
	},
	DESCAPOTABLE("AE", "Descapotable") {
		public String toString() {
			return "AE";
		}
	},
	MULTIUSOS("AF", "Multiusos") {
		public String toString() {
			return "AF";
		}
	},
	CAMIONETA_FAMILIAR("AG", "Camioneta familiar") {
		public String toString() {
			return "AG";
		}
	},
	CAMION("BA", "Camión") {
		public String toString() {
			return "BA";
		}
	},
	FURGONETA("BB", "Furgoneta") {
		public String toString() {
			return "BB";
		}
	},
	TRACTORA_SEMIRREMOLQUE("BC", "Tractora semirremolque") {
		public String toString() {
			return "BC";
		}
	},
	TRACTORA_REMOLQUE("BD", "Tractora remolque") {
		public String toString() {
			return "BD";
		}
	},
	PICK_UP("BE", "Pick up") {
		public String toString() {
			return "BE";
		}
	},
	BASTIDOR_CABINA_O_CUBIERTA("BX", "Bastidor con cabina o con cubierta") {
		public String toString() {
			return "BX";
		}
	},
	VEHICULO_UN_PISO("CA", "Vehículo de un solo piso") {
		public String toString() {
			return "CA";
		}
	},
	VEHICULO_DOS_PISOS("CB", "Vehículo de dos pisos") {
		public String toString() {
			return "CB";
		}
	},
	ARTICULADO_UN_PISO("CC", "Articulado de un solo piso") {
		public String toString() {
			return "CC";
		}
	},
	CHASIS_AUTOBUS("CX", "Chasis de autobús") {
		public String toString() {
			return "CX";
		}
	},
	ARTICULADO_DOS_PISOS("CD", "Articulado de dos pisos") {
		public String toString() {
			return "CD";
		}
	},
	UN_PISO_SUELO_BAJO("CE", "De un solo piso con suelo bajo") {
		public String toString() {
			return "CE";
		}
	},
	DOS_PISOS_SUELO_BAJO("CF", "De dos pisos con suelo bajo") {
		public String toString() {
			return "CF";
		}
	},
	ARTICULADO_UN_PISO_SUELO_BAJO("CG", "Articulado de un solo piso suelo bajo") {
		public String toString() {
			return "CG";
		}
	},
	ARTICULADO_DOS_PISOS_SUELO_BAJO("CH", "Articulado de dos pisos suelo bajo") {
		public String toString() {
			return "CH";
		}
	},
	UN_PISO_TECHO_ABIERTO("CI", "De un solo piso techo abierto") {
		public String toString() {
			return "CI";
		}
	},
	DOS_PISOS_TECHO_ABIERTO("CJ", "De dos pisos techo abierto") {
		public String toString() {
			return "CJ";
		}
	},
	SEMIRREMOLQUE("DA", "Semirremolque") {
		public String toString() {
			return "DA";
		}
	},
	REMOLQUE_BARRAS_TRACCION("DB", "Remolque con barras de tracción") {
		public String toString() {
			return "DB";
		}
	},
	REMOLQUE_EJE_CENTRAL("DC", "Remolque de eje central") {
		public String toString() {
			return "DC";
		}
	},
	REMOLQUE_BARRA_TRACCION_RIGIDA("DE", "Remolque con barra de tracción rígida") {
		public String toString() {
			return "DE";
		}
	},
	AUTO_CARAVANA("SA", "Auto - caravana") {
		public String toString() {
			return "SA";
		}
	},
	VEHICULO_BLINDADO("SB", "Vehículo blindado") {
		public String toString() {
			return "SB";
		}
	},
	AMBULANCIA("SC", "Ambulancia") {
		public String toString() {
			return "SC";
		}
	},
	VEHICULO_FUNERARIO("SD", "Vehículo funerario") {
		public String toString() {
			return "SD";
		}
	},
	CARAVANA("SE", "Caravana") {
		public String toString() {
			return "SE";
		}
	},
	GRUA_MOVIL("SF", "Grúa móvil") {
		public String toString() {
			return "SF";
		}
	},
	GRUPO_ESPECIAL("SG", "Grupo especial") {
		public String toString() {
			return "SG";
		}
	},
	VEHICULO_ACCESIBLE_SILLA_RUEDAS("SH", "Vehículo accesible silla ruedas") {
		public String toString() {
			return "SH";
		}
	},
	REMOLQUE_CONVERTIDOR("SJ", "Remolque convertidor") {
		public String toString() {
			return "SJ";
		}
	},
	REMOLQUE_TRANSPORTE_CARGA_EXPCEPCIONAL("SK", "Remolque transporte carga excepcional") {
		public String toString() {
			return "SK";
		}
	},
	NO_DISPONIBLES("ND", "NO DISPONIBLE") {
		public String toString() {
			return "ND";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Carroceria(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static CarroceriaBean convertir(String idCarroceriaParam){
		if(idCarroceriaParam == null || idCarroceriaParam.length() < 2){
			return new CarroceriaBean();
		}

		/*
		 * Nuevos subtipos de carrocería DGT. Se pone temporalmente por enumerado hasta que se planifique un
		 * desarrollo por BBDD.
		 */
		if ((idCarroceriaParam.substring(0, 2).equals(Carroceria.CAMION.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.BASTIDOR_CABINA_O_CUBIERTA.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.SEMIRREMOLQUE.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.REMOLQUE_BARRAS_TRACCION.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.REMOLQUE_EJE_CENTRAL.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.REMOLQUE_BARRA_TRACCION_RIGIDA.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.FURGONETA.getValorEnum())
			|| idCarroceriaParam.substring(0, 2).equals(Carroceria.PICK_UP.getValorEnum())
				&& idCarroceriaParam.length() > 2
				&& (idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.BASURERO.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CAJA_ABIERTA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CAJA_ACONDICIONADA_CON_PAREDES_AISLADAS_Y_SISTEMA_DE_REFRIGERACION.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CAJA_ACONDICIONADA_CON_PAREDES_AISLADAS_Y_SIN_SISTEMA_DE_REFRIGERACION.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CAJA_CERRADA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CAMION_GRUA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CISTERNA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CISTERNA_PREPARADA_PARA_ADR.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.COMPRESOR.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CON_BOMBA_DE_HORMIGONAR.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CON_ESCALERA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CON_GRUA_DE_ELEVACION.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.CON_LONAS_LATERALES.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.DE_ASISTENCIA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.DE_CAJA_MOVIL.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.DE_EXTINCION_DE_INCENDIOS.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.HORMIGONERA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.NO_INCLUIDA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PARA_BARRER_LIMPIAR_Y_SECAR_LA_VIA_PUBLICA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PARA_EL_COMERCIO_AL_POR_MENOR_O_COMO_EXPOSITOR.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PARA_TRANSPORTE_DE_EMBARCACIONES.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PARA_TRANSPORTE_DE_PLANEADORES.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PERFORADORA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PLATAFORMA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PORTA_CONTENEDORES.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.PORTAVEHICULOS.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.REMOLQUE_DE_PISO_BAJO.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.TRANSPORTE_DE_CRISTALES.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.TRANSPORTE_DE_GANADO.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.TRANSPORTE_DE_MADERA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.VEHICULO_CON_PLATAFORMA_AEREA.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.VOLQUETE.getValorEnum())
					|| idCarroceriaParam.substring(3).equals(CarroceriaSubtipos.LONA_LATERAL_ABATIBLE.getValorEnum())
						))
				){
			return new CarroceriaBean(idCarroceriaParam.substring(0, 2) + idCarroceriaParam.substring(2), "");
		}
		/* Fin nuevos subtipos de carrocería DGT */

		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.AMBULANCIA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.AMBULANCIA.getValorEnum(),
					Carroceria.AMBULANCIA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.ARTICULADO_DOS_PISOS.getValorEnum())) {
			return new CarroceriaBean(Carroceria.ARTICULADO_DOS_PISOS.getValorEnum(),
					Carroceria.ARTICULADO_DOS_PISOS.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.ARTICULADO_DOS_PISOS_SUELO_BAJO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.ARTICULADO_DOS_PISOS_SUELO_BAJO.getValorEnum(),
					Carroceria.ARTICULADO_DOS_PISOS_SUELO_BAJO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.ARTICULADO_UN_PISO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.ARTICULADO_UN_PISO.getValorEnum(),
					Carroceria.ARTICULADO_UN_PISO.getNombreEnum());
		}
		if(idCarroceriaParam.equalsIgnoreCase(Carroceria.ARTICULADO_UN_PISO_SUELO_BAJO.getValorEnum())){
			return new CarroceriaBean(Carroceria.ARTICULADO_UN_PISO_SUELO_BAJO.getValorEnum(),
					Carroceria.ARTICULADO_UN_PISO_SUELO_BAJO.getNombreEnum());
		}
		if(idCarroceriaParam.equalsIgnoreCase(Carroceria.AUTO_CARAVANA.getValorEnum())){
			return new CarroceriaBean(Carroceria.AUTO_CARAVANA.getValorEnum(),
					Carroceria.AUTO_CARAVANA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.BASTIDOR_CABINA_O_CUBIERTA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.BASTIDOR_CABINA_O_CUBIERTA.getValorEnum(),
					Carroceria.BASTIDOR_CABINA_O_CUBIERTA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.BERLINA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.BERLINA.getValorEnum(),
					Carroceria.BERLINA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.BERLINA_PONTON.getValorEnum())) {
			return new CarroceriaBean(Carroceria.BERLINA_PONTON.getValorEnum(),
					Carroceria.BERLINA_PONTON.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.CAMION.getValorEnum())) {
			return new CarroceriaBean(Carroceria.CAMION.getValorEnum(),
					Carroceria.CAMION.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.CAMIONETA_FAMILIAR.getValorEnum())) {
			return new CarroceriaBean(Carroceria.CAMIONETA_FAMILIAR.getValorEnum(),
					Carroceria.CAMIONETA_FAMILIAR.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.CARAVANA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.CARAVANA.getValorEnum(),
					Carroceria.CARAVANA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.CHASIS_AUTOBUS.getValorEnum())) {
			return new CarroceriaBean(Carroceria.CHASIS_AUTOBUS.getValorEnum(),
					Carroceria.CHASIS_AUTOBUS.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.CUPE.getValorEnum())) {
			return new CarroceriaBean(Carroceria.CUPE.getValorEnum(),
					Carroceria.CUPE.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.DESCAPOTABLE.getValorEnum())) {
			return new CarroceriaBean(Carroceria.DESCAPOTABLE.getValorEnum(),
					Carroceria.DESCAPOTABLE.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.DOS_PISOS_SUELO_BAJO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.DOS_PISOS_SUELO_BAJO.getValorEnum(),
					Carroceria.DOS_PISOS_SUELO_BAJO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.DOS_PISOS_TECHO_ABIERTO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.DOS_PISOS_TECHO_ABIERTO.getValorEnum(),
					Carroceria.DOS_PISOS_TECHO_ABIERTO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.FAMILIAR.getValorEnum())) {
			return new CarroceriaBean(Carroceria.FAMILIAR.getValorEnum(),
					Carroceria.FAMILIAR.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.FURGONETA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.FURGONETA.getValorEnum(),
					Carroceria.FURGONETA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.GRUA_MOVIL.getValorEnum())) {
			return new CarroceriaBean(Carroceria.GRUA_MOVIL.getValorEnum(),
					Carroceria.GRUA_MOVIL.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.GRUPO_ESPECIAL.getValorEnum())) {
			return new CarroceriaBean(Carroceria.GRUPO_ESPECIAL.getValorEnum(),
					Carroceria.GRUPO_ESPECIAL.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.MULTIUSOS.getValorEnum())) {
			return new CarroceriaBean(Carroceria.MULTIUSOS.getValorEnum(),
					Carroceria.MULTIUSOS.getNombreEnum());
		}
		if(idCarroceriaParam.equalsIgnoreCase(Carroceria.NO_DISPONIBLES.getValorEnum())){
			return new CarroceriaBean(Carroceria.NO_DISPONIBLES.getValorEnum(),
					Carroceria.NO_DISPONIBLES.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.PICK_UP.getValorEnum())) {
			return new CarroceriaBean(Carroceria.PICK_UP.getValorEnum(),
					Carroceria.PICK_UP.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.REMOLQUE_BARRA_TRACCION_RIGIDA.getValorEnum())) {
			return new CarroceriaBean(Carroceria.REMOLQUE_BARRA_TRACCION_RIGIDA.getValorEnum(),
					Carroceria.REMOLQUE_BARRA_TRACCION_RIGIDA.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.REMOLQUE_BARRAS_TRACCION.getValorEnum())) {
			return new CarroceriaBean(Carroceria.REMOLQUE_BARRAS_TRACCION.getValorEnum(),
					Carroceria.REMOLQUE_BARRAS_TRACCION.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.REMOLQUE_CONVERTIDOR.getValorEnum())) {
			return new CarroceriaBean(Carroceria.REMOLQUE_CONVERTIDOR.getValorEnum(),
					Carroceria.REMOLQUE_CONVERTIDOR.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.REMOLQUE_EJE_CENTRAL.getValorEnum())) {
			return new CarroceriaBean(Carroceria.REMOLQUE_EJE_CENTRAL.getValorEnum(),
					Carroceria.REMOLQUE_EJE_CENTRAL.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.REMOLQUE_TRANSPORTE_CARGA_EXPCEPCIONAL.getValorEnum())) {
			return new CarroceriaBean(Carroceria.REMOLQUE_TRANSPORTE_CARGA_EXPCEPCIONAL.getValorEnum(),
					Carroceria.REMOLQUE_TRANSPORTE_CARGA_EXPCEPCIONAL.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.SEMIRREMOLQUE.getValorEnum())) {
			return new CarroceriaBean(Carroceria.SEMIRREMOLQUE.getValorEnum(),
					Carroceria.SEMIRREMOLQUE.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.TRACTORA_SEMIRREMOLQUE.getValorEnum())) {
			return new CarroceriaBean(Carroceria.TRACTORA_SEMIRREMOLQUE.getValorEnum(),
					Carroceria.TRACTORA_SEMIRREMOLQUE.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.TRACTORA_REMOLQUE.getValorEnum())) {
			return new CarroceriaBean(Carroceria.TRACTORA_REMOLQUE.getValorEnum(),
					Carroceria.TRACTORA_REMOLQUE.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.UN_PISO_SUELO_BAJO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.UN_PISO_SUELO_BAJO.getValorEnum(),
					Carroceria.UN_PISO_SUELO_BAJO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.UN_PISO_TECHO_ABIERTO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.UN_PISO_TECHO_ABIERTO.getValorEnum(),
					Carroceria.UN_PISO_TECHO_ABIERTO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.VEHICULO_ACCESIBLE_SILLA_RUEDAS.getValorEnum())) {
			return new CarroceriaBean(Carroceria.VEHICULO_ACCESIBLE_SILLA_RUEDAS.getValorEnum(),
					Carroceria.VEHICULO_ACCESIBLE_SILLA_RUEDAS.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.VEHICULO_BLINDADO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.VEHICULO_BLINDADO.getValorEnum(),
					Carroceria.VEHICULO_BLINDADO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.VEHICULO_DOS_PISOS.getValorEnum())) {
			return new CarroceriaBean(Carroceria.VEHICULO_DOS_PISOS.getValorEnum(),
					Carroceria.VEHICULO_DOS_PISOS.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.VEHICULO_FUNERARIO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.VEHICULO_FUNERARIO.getValorEnum(),
					Carroceria.VEHICULO_FUNERARIO.getNombreEnum());
		}
		if (idCarroceriaParam.equalsIgnoreCase(Carroceria.VEHICULO_UN_PISO.getValorEnum())) {
			return new CarroceriaBean(Carroceria.VEHICULO_UN_PISO.getValorEnum(),
					Carroceria.VEHICULO_UN_PISO.getNombreEnum());
		}
		return null;
	}

	public static Carroceria convertirCarroceria(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (Carroceria carroceria: Carroceria.values()) {
				if (carroceria.getValorEnum().equals(valorEnum)) {
					return carroceria;
				}
			}
			return null;
		}
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}