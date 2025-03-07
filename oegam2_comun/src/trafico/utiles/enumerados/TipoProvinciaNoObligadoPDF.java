package trafico.utiles.enumerados;

import trafico.beans.TramiteTraficoTransmisionBean;

// DRC@17-04-2013 Incidencia Mantis: 4202
// Indica las provincias que no obligan a liquidar el impuesto para esos vehículos.
// Se utiliza para determinar si se utiliza las plantillas xxxxNoObligadoxxx.pdf
public enum TipoProvinciaNoObligadoPDF {
	Barcelona("08", "B") {
		public String toString() {
			return "08";
		}
	},
	Gerona("17", "GE") {
		public String toString() {
			return "17";
		}
	},
	Lerida("24", "LE") {
		public String toString() {
			return "24";
		}
	},
	Tarragona("43", "T") {
		public String toString() {
			return "43";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoProvinciaNoObligadoPDF(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static Boolean comprobarCCAA(TramiteTraficoTransmisionBean detalleTransmision) {
		return noObligadoCCAA(recuperarProvinciaCET(detalleTransmision));
	}

	// DRC@06-05-2013 Actualmente solo se han incluido las provincias de la comunidad Autónoma de Cataluña (Barcelona, Girona, Tarragona, Lérida)
	private static Boolean noObligadoCCAA(int codProvincia) {
		switch (codProvincia) {
			case 8: 
				return Boolean.TRUE;
			case 17: 
				return Boolean.TRUE;
			case 24: 
				return Boolean.TRUE;
			case 43:
				return Boolean.TRUE;
			default:
				return Boolean.FALSE;
		}
	}

	// DRC@16-04-2013 Incidencia Mantis: 4202
	private static Integer recuperarProvinciaCET(TramiteTraficoTransmisionBean detalleTransmision) {
		int respuestaCET = 0;

		if (detalleTransmision != null 
				&& detalleTransmision.getProvinciaCet() != null
				&& detalleTransmision.getProvinciaCet().getIdProvincia() != null
				&& !detalleTransmision.getProvinciaCet().getIdProvincia().equalsIgnoreCase("")
				&& !detalleTransmision.getProvinciaCet().getIdProvincia().equalsIgnoreCase("-1"))
			respuestaCET = Integer.valueOf(detalleTransmision.getProvinciaCet().getIdProvincia());
		else if(detalleTransmision != null
				&& detalleTransmision.getTramiteTraficoBean() !=null
				&& detalleTransmision.getTramiteTraficoBean().getVehiculo()!=null
				&& detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean()!=null
				&& detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio()!=null
				&& detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia()!=null
				&& detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null
				&& !detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("")
				&& !detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1"))
			respuestaCET = Integer.valueOf(detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia());
		else if (detalleTransmision != null
				&& detalleTransmision.getAdquirienteBean()!=null
				&& detalleTransmision.getAdquirienteBean().getPersona()!=null
				&& detalleTransmision.getAdquirienteBean().getPersona().getDireccion()!=null
				&& detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio()!=null
				&& detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia()!=null
				&& detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
				&& !detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("")
				&& !detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1"))
			respuestaCET = Integer.valueOf(detalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
		else if (detalleTransmision != null
				&& detalleTransmision.getPoseedorBean()!=null
				&& detalleTransmision.getPoseedorBean().getPersona()!=null
				&& detalleTransmision.getPoseedorBean().getPersona().getDireccion()!=null
				&& detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio()!=null
				&& detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia()!=null
				&& detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
				&& !detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("")
				&& !detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1"))
			respuestaCET = Integer.valueOf(detalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());

		return respuestaCET;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}