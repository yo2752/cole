package general.utiles;

public enum NombresEnumerados {

	AcreditacionTrafico("AcreditacionTrafico") {
		public String toString() {
			return "AcreditacionTrafico";
		}
	},
	Carburante("Carburante") {
		public String toString() {
			return "Carburante";
		}
	},
	CausaHechoImponible("CausaHechoImponible") {
		public String toString() {
			return "CausaHechoImponible";
		}
	},
	Color("Color") {
		public String toString() {
			return "Color";
		}
	},
	ConceptoTutela("ConceptoTutela") {
		public String toString() {
			return "ConceptoTutela";
		}
	},
	ContratoFactura("ContratoFactura") {
		public String toString() {
			return "ContratoFactura";
		}
	},
	DatosTarjetaITV("DatosTarjetaITV") {
		public String toString() {
			return "DatosTarjetaITV";
		}
	},
	EjercicioDevengo("EjercicioDevengo") {
		public String toString() {
			return "EjercicioDevengo";
		}
	},
	Epigrafe("Epigrafe") {
		public String toString() {
			return "Epigrafe";
		}
	},
	EstadoTramiteTrafico("EstadoTramiteTrafico") {
		public String toString() {
			return "EstadoTramiteTrafico";
		}
	},
	LugarAdquisicion("LugarAdquisicion") {
		public String toString() {
			return "LugarAdquisicion";
		}
	},
	LugarPrimeraMatriculacion("LugarPrimeraMatriculacion") {
		public String toString() {
			return "LugarPrimeraMatriculacion";
		}
	},
	ModeloIEDTM("ModeloIEDTM") {
		public String toString() {
			return "ModeloIEDTM";
		}
	},
	ModeloISD("ModeloISD") {
		public String toString() {
			return "ModeloISD";
		}
	},
	ModeloITP("ModeloITP") {
		public String toString() {
			return "ModeloITP";
		}
	},
	MotivoBaja("MotivoBaja") {
		public String toString() {
			return "MotivoBaja";
		}
	},
	NoSujeccionOExencion("NoSujeccionOExencion") {
		public String toString() {
			return "NoSujeccionOExencion";
		}
	},
	Observaciones("Observaciones") {
		public String toString() {
			return "Observaciones";
		}
	},
	Procedencia("Procedencia") {
		public String toString() {
			return "Procedencia";
		}
	},
	ReduccionNoSujeccionOExencion05("ReduccionNoSujeccionOExencion05") {
		public String toString() {
			return "ReduccionNoSujeccionOExencion05";
		}
	},
	ResultadoITV("ResultadoITV") {
		public String toString() {
			return "ResultadoITV";
		}
	},
	TiposReduccion576("TiposReduccion576") {
		public String toString() {
			return "TiposReduccion576";
		}
	},
	TipoTarjetaITV("TipoTarjetaITV") {
		public String toString() {
			return "TipoTarjetaITV";
		}
	},
	TipoTasa("TipoTasa") {
		public String toString() {
			return "TipoTasa";
		}
	},
	TipoTramiteTrafico("TipoTramiteTrafico") {
		public String toString() {
			return "TipoTramiteTrafico";
		}
	},
	TipoTransferencia("TipoTransferencia") {
		public String toString() {
			return "TipoTransferencia";
		}
	},
	TipoTutela("TipoTutela") {
		public String toString() {
			return "TipoTutela";
		}
	},
	TipoVehiculoCam("TipoVehiculoCam") {
		public String toString() {
			return "TipoVehiculoCam";
		}
	};

	private String valor;

	private NombresEnumerados(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}