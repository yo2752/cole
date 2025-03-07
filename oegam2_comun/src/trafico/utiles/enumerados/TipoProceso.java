package trafico.utiles.enumerados;

public enum TipoProceso {

	Primera_Matriculacion("FIRSTMATE", "PRIMERA MATRICULACION") {
		public String toString() {
			return "FIRSTMATE";
		}
	},
	Ultima_Matriculacion("ULTMATE", "ULTIMA MATRICULACION") {
		public String toString() {
			return "ULTMATE";
		}
	},
	Estadisticas("ESTADISTICAS", "ESTADISTICAS") {
		public String toString() {
			return "ESTADISTICAS";
		}
	},
	Excel_Bajas_Madrid("EXCELBAJAMADRID", "EXCEL BAJAS MADRID") {
		public String toString() {
			return "EXCELBAJAMADRID";
		}
	},
	Excel_Duplicados_Madrid("EXCELDUPLICADOMADRID", "EXCEL DUPLICADOS MADRID") {
		public String toString() {
			return "EXCELDUPLICADOMADRID";
		}
	},
	Excel_Bajas_Alcorcon("EXCELBAJAALCORCON", "EXCEL BAJAS ALCORCON") {
		public String toString() {
			return "EXCELBAJAALCORCON";
		}
	},
	Excel_Duplicados_Alcorcon("EXCELDUPLICADOALCORCON", "EXCEL DUPLICADOS ALCORCON") {
		public String toString() {
			return "EXCELDUPLICADOALCORCON";
		}
	},
	Excel_Bajas_Alcala("EXCELBAJAALCALA", "EXCEL BAJAS ALCALA") {
		public String toString() {
			return "EXCELBAJAALCALA";
		}
	},
	Excel_Duplicados_Alcala("EXCELDUPLICADOALCALA", "EXCEL DUPLICADOS ALCALA") {
		public String toString() {
			return "EXCELDUPLICADOALCALA";
		}
	},
	Excel_Bajas_Avila("EXCELBAJAAVILA", "EXCEL BAJAS AVILA") {
		public String toString() {
			return "EXCELBAJAAVILA";
		}
	},
	Excel_Duplicados_Avila("EXCELDUPLICADOAVILA", "EXCEL DUPLICADOS AVILA") {
		public String toString() {
			return "EXCELDUPLICADOAVILA";
		}
	},
	Excel_Bajas_Ciudad_Real("EXCELBAJACIUDADREAL", "EXCEL BAJAS CIUDAD REAL") {
		public String toString() {
			return "EXCELBAJACIUDADREAL";
		}
	},
	Excel_Duplicados_Ciudad_Real("EXCELDUPLICADOCIUDADREAL", "EXCEL DUPLICADOS CIUDAD REAL") {
		public String toString() {
			return "EXCELDUPLICADOCIUDADREAL";
		}
	},
	Excel_Bajas_Cuenca("EXCELBAJACUENCA", "EXCEL BAJAS CUENCA") {
		public String toString() {
			return "EXCELBAJACUENCA";
		}
	},
	Excel_Duplicados_Cuenca("EXCELDUPLICADOCUENCA", "EXCEL DUPLICADOS CUENCA") {
		public String toString() {
			return "EXCELDUPLICADOCUENCA";
		}
	},
	Excel_Bajas_Guadalajara("EXCELBAJAGUADALAJARA", "EXCEL BAJAS GUADALAJARA") {
		public String toString() {
			return "EXCELBAJAGUADALAJARA";
		}
	},
	Excel_Duplicados_Guadalajara("EXCELDUPLICADOGUADALAJARA", "EXCEL DUPLICADOS GUADALAJARA") {
		public String toString() {
			return "EXCELDUPLICADOGUADALAJARA";
		}
	},
	Excel_Bajas_Segovia("EXCELBAJASEGOVIA", "EXCEL BAJAS SEGOVIA") {
		public String toString() {
			return "EXCELBAJASEGOVIA";
		}
	},
	Excel_Duplicados_Segovia("EXCELDUPLICADOSEGOVIA", "EXCEL DUPLICADOS SEGOVIA") {
		public String toString() {
			return "EXCELDUPLICADOSEGOVIA";
		}
	},
	Anuntis_Tarde("ANUNTIST", "ANUNTIS TARDE") {
		public String toString() {
			return "ANUNTIST";
		}
	},
	Anuntis_Maniana("ANUNTISM", "ANUNTIS MANIANA") {
		public String toString() {
			return "ANUNTISM";
		}
	},
	EntidadesFinancieras("ENTIDADESFINANCIERAS", "ETNIDADES FINANCIERAS") {
		public String toString() {
			return "ENTIDADESFINANCIERAS";
		}
	},
	CADUCIDAD_CERTIFICADOS("CADCERTS", "Alerta dias validez certificados") {
		public String toString() {
			return "CADCERTS";
		}
	},
	Generador_de_Documentos_Base("DOCUMENTOBASE", "Generador de Documentos Base Estándar") {
		public String toString() {
			return "DOC_BASE_GENERATOR";
		}
	},
	Generador_de_Documentos_Base_Demanda("DOCUMENTOBASE_DEMAND", "Generador de Documentos Base a Demanda") {
		public String toString() {
			return "DOC_BASE_GENERATOR_DEMAND";
		}
	},
	Impresion_de_Documentos_Base("DOCUMENTOBASEPDF", "Impresion PDF de Documentos Base") {
		public String toString() {
			return "DOCUMENTOBASEPDF";
		}
	},
	Presentacion_Jpt("PRESENTACIONJPT", "Presentación de documentos en Jefatura Provincial de Tráfico") {
		public String toString() {
			return "PRESENTACIONJPT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoProceso(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoProceso convertir(String valorEnum) {
		TipoProceso result = null;
		for (TipoProceso tipoProceso : TipoProceso.values()) {
			if (tipoProceso.getValorEnum().equals(valorEnum)) {
				result = tipoProceso;
				break;
			}
		}
		return result;
	}

	public static String convertirTexto2(String valorEnum) {
		TipoProceso tipoProceso = convertir(valorEnum);
		return tipoProceso != null ? tipoProceso.nombreEnum : null;
	}

}