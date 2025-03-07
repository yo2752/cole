package org.gestoresmadrid.oegam2comun.santanderWS.views.enums;

public enum ResultadoSantanderWSEnum {

	OK("000", "Procesado Correctamente"),
	KO("111", "KO"),
	STDWS001("001", "El contenido de la request esta vacio."),
	STDWS002("002", "El codigo de envio agrupado esta vacio."),
	STDWS003("003", "El codigo de envio agrupado debe de ser numerico."),
	STDWS004("004", "Debe de rellenar alguna lista de bastidores para poder realizar las acciones oportunas."),
	STDWS005("005", "El n�mero de bastidor esta vac�o."),
	STDWS006("006", "El tipo de veh�culo esta vac�o."),
	STDWS007("007", "La fecha y hora de envio esta vac�a."),
	STDWS008("008", "El n�mero de bastidor no existe."),
	STDWS009("009", "El tipo de veh�culo no existe."),
	STDWS010("010", "Ha sucedido un error a la hora de procesar los bastidores."),
	STDWS011("011", "Error controlado en la aplicaci�n a la hora de buscar la matr�cula por el bastidor."),
	STDWS012("012", "Error controlado a la hora de procesar el bastidor."),
	STDWS013("013", "Ha sucedido un error a la hora de encolar la solicitud para el bastidor."),
	STDWS014("014", "El bastidor ya se encuentra dado de alta y activo."),
	STDWS015("015", "No se encuentran datos del bastidor para dar de baja."),
	STDWS016("016", "El bastidor ya se encuentra dado de baja."),
	STDWS017("017", "Se han producido errores a la hora de procesar los listado de bastidores."),
	STDWS018("018", "El bastidor ya se encuentra dado de baja e inactivo."),
	STDWS019("019", "Se ha producido un error controlado a la hora de dar de alta el bastidor."),
	STDWS020("020", "Se ha producido un error controlado a la hora de dar de baja el bastidor."),
	STDWS021("021", "El tama�o del bastidor no puede exceder de 21 caracteres."),
	STDWS022("022", "El formato de la fecha de envio no es el correcto('ddMMyyyy')."),
	STDWS023("023", "No se ha encontrado ninguna matr�cula para ese bastidor."),
	STDWS024("024", "El tipo de operaci�n esta vac�o."),
	STDWS025("025", "El tipo de financiacion esta vac�o."),
	STDWS026("026", "El tipo de alerta esta vac�o."),
	STDWS027("027", "El tipo de financiacion no es v�lido."),
	STDWS028("028", "La lista de bastidores est� vac�a"),
	STDWS777("777", "Esta intentando dar de alta o baja un bastidor sin datos."),
	STDWS999("999", "El WS ha terminado correctamente aunque algunos de los bastidores no se han procesado correctamente.");

	private String valorEnumn;
	private String nombreEnum;

	private ResultadoSantanderWSEnum(String valorEnumn, String nombreEnum) {
		this.valorEnumn = valorEnumn;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnumn() {
		return valorEnumn;
	}

	public void setValorEnumn(String valorEnumn) {
		this.valorEnumn = valorEnumn;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

}
