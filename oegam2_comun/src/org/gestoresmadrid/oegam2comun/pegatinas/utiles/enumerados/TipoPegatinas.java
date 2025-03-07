package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

public enum TipoPegatinas {
	
	TODOS("", "Todos"),
	DISTINTIVO_4("T0","T0 - Cero Emisiones Inverso"),
	DISTINTIVO_0("M0","M0 - Cero Emisiones Normal (Motos)"),
	DISTINTIVO_5("TE","TE - Eco Inverso"),
	DISTINTIVO_1("ME","ME - Eco Normal (Motos)"),
	DISTINTIVO_6("TC","TC - Verde Invertido"),
	DISTINTIVO_2("MC","MC - Verde Normal (Motos)"),
	DISTINTIVO_7("TB","TB - Amarillo Invertido"),
	DISTINTIVO_3("MB","MB - Amarillo Normal (Motos)");
	
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoPegatinas(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
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

}