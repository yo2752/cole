package org.gestoresmadrid.oegam2comun.trafico.testra.view.beans;

public enum TipoTramiteTestra {

	notificacion("TES1", "Notificacion de sancion publicada en TESTRA") {
		public String toString() {
			return "TES1";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteTestra(String valorEnum, String nombreEnum) {
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
