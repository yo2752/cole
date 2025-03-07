package org.gestoresmadrid.core.model.enumerados;

public enum TipoInterviniente {

	SujetoPasivo("001", "Sujeto Pasivo") {
		public String toString() {
			return "001";
		}
	},
	Presentador("002", "Presentador") {
		public String toString() {
			return "002";
		}
	},
	Transmitente("003", "Transmitente") {
		public String toString() {
			return "003";
		}
	},
	Titular("004", "Titular") {
		public String toString() {
			return "004";
		}
	},
	Adquiriente("005", "Adquiriente") {
		public String toString() {
			return "005";
		}
	},
	TransmitenteTrafico("006", "Transmitente") {
		public String toString() {
			return "006";
		}
	},
	PresentadorTrafico("007", "Presentador") {
		public String toString() {
			return "007";
		}
	},

	RepresentanteAdquiriente("008", "Representante Adquiriente") {
		public String toString() {
			return "008";
		}
	},
	RepresentanteTransmitente("009", "Representante Transmitente") {
		public String toString() {
			return "009";
		}
	},

	RepresentanteTitular("010", "Representante Titular") {
		public String toString() {
			return "010";
		}
	},
	ConductorHabitual("011", "Conductor Habitual") {
		public String toString() {
			return "011";
		}
	},

	Arrendador("012", "Arrendador") {
		public String toString() {
			return "012";
		}
	},
	RepresentanteCompraventa("013", "Representante Compraventa") {
		public String toString() {
			return "013";
		}
	},

	Arrendatario("014", "Arrendatario") {
		public String toString() {
			return "014";
		}
	},
	RepresentanteArrendatario("015", "Representante Arrendatario") {
		public String toString() {
			return "015";
		}
	},

	CotitularTransmision("016", "Cotitular Transmisión") {
		public String toString() {
			return "016";
		}
	},

	Solicitante("017", "Solicitante") {
		public String toString() {
			return "017";
		}
	},

	Compraventa("018", "Compraventa") {
		public String toString() {
			return "018";
		}
	},

	RepresentantePrimerCotitularTransmision("019", "Representante Primer Cotitular Transmisión") {
		public String toString() {
			return "019";
		}
	},

	RepresentanteSegundoCotitularTransmision("020", "Representante Segundo Cotitular Transmisión") {
		public String toString() {
			return "020";
		}
	},
	
	Financiador("021", "Financiador") {
		public String toString() {
			return "021";
		}
	},
	
	RepresentanteFinanciador("022", "Representante Financiador") {
		public String toString() {
			return "022";
		}
	},
	
	Cesionario("023", "Cesionario") {
		public String toString() {
			return "023";
		}
	},
	
	RepresentanteCesionario("024", "Representante Cesionario") {
		public String toString() {
			return "024";
		}
	},
	
	RepresentanteArrendador("025", "Representante Arrendador") {
		public String toString() {
			return "025";
		}
	},
	
	Avalista("026", "Avalista") {
		public String toString() {
			return "026";
		}
	},
	
	RepresentanteAvalista("027", "Representante Avalista") {
		public String toString() {
			return "027";
		}
	},
	
	Proveedor("028", "Proveedor") {
		public String toString() {
			return "028";
		}
	},
	
	Comprador("029", "Comprador") {
		public String toString() {
			return "029";
		}
	},

	RepresentanteComprador("030", "Representante Comprador") {
		public String toString() {
			return "030";
		}
	},
	
	Vendedor("031", "Vendedor") {
		public String toString() {
			return "031";
		}
	},
	
	RepresentanteSolicitante("032", "Representante Solicitante") {
		public String toString() {
			return "032";
		}
	},
	
	CompradorArrendatario("033", "Comprador Arrendatario") {
		public String toString() {
			return "033";
		}
	},
	
	InteresadoPrincipal("034", "Interesado Principal") {
		public String toString() {
			return "034";
		}
	},
	
	OtroInteresado("035", "Otro Interesado") {
		public String toString() {
			return "035";
		}
	},
	
	RepresentanteInteresado("036", "Representante Interesado") {
		public String toString() {
			return "036";
		}
	},
	
	Notificacion("037", "Notificado") {
		public String toString() {
			return "037";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;

	private TipoInterviniente(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static TipoInterviniente convertir(String valorEnum) {

		if ("001".equals(valorEnum))
			return SujetoPasivo;

		if ("002".equals(valorEnum))
			return Presentador;

		if ("003".equals(valorEnum))
			return Transmitente;

		if ("004".equals(valorEnum))
			return Titular;

		if ("005".equals(valorEnum))
			return Adquiriente;

		if ("006".equals(valorEnum))
			return TransmitenteTrafico;

		if ("007".equals(valorEnum))
			return PresentadorTrafico;

		if ("008".equals(valorEnum))
			return RepresentanteAdquiriente;

		if ("009".equals(valorEnum))
			return RepresentanteTransmitente;

		if ("010".equals(valorEnum))
			return TipoInterviniente.RepresentanteTitular;

		if ("011".equals(valorEnum))
			return ConductorHabitual;

		if ("012".equals(valorEnum))
			return Arrendador;

		if ("013".equals(valorEnum))
			return RepresentanteCompraventa;

		if ("014".equals(valorEnum))
			return Arrendatario;

		if ("015".equals(valorEnum))
			return RepresentanteArrendatario;

		if ("016".equals(valorEnum))
			return CotitularTransmision;

		if ("017".equals(valorEnum))
			return Solicitante;

		if ("018".equals(valorEnum))
			return Compraventa;

		if ("019".equals(valorEnum))
			return RepresentantePrimerCotitularTransmision;

		if ("020".equals(valorEnum))
			return RepresentanteSegundoCotitularTransmision;
		
		if ("021".equals(valorEnum))
			return Financiador;
		
		if ("022".equals(valorEnum))
			return RepresentanteFinanciador;
		
		if ("023".equals(valorEnum))
			return Cesionario;
		
		if ("024".equals(valorEnum))
			return RepresentanteCesionario;
		
		if ("025".equals(valorEnum))
			return RepresentanteArrendador;
		
		if ("026".equals(valorEnum))
			return Avalista;
		
		if ("027".equals(valorEnum))
			return RepresentanteAvalista;
		
		if ("028".equals(valorEnum))
			return Proveedor;

		if ("029".equals(valorEnum))
			return Comprador;
		
		if ("030".equals(valorEnum))
			return RepresentanteComprador;
		
		if ("031".equals(valorEnum))
			return Vendedor;
		
		if ("032".equals(valorEnum))
			return RepresentanteSolicitante;
		
		if ("033".equals(valorEnum))
			return CompradorArrendatario;
		
		if ("034".equals(valorEnum))
			return InteresadoPrincipal;
		
		if ("035".equals(valorEnum))
			return OtroInteresado;
		
		if ("036".equals(valorEnum))
			return RepresentanteInteresado;
		
		if ("037".equals(valorEnum))
			return Notificacion;
		
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoInterviniente element : TipoInterviniente.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombreEnum) {
		for (TipoInterviniente element : TipoInterviniente.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
}
