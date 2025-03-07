package org.gestoresmadrid.core.model.enumerados;

public enum TipoTutela {

	MinoriaEdad("M", "MINORÍA DE EDAD") {
		public String toString() {
			return "M";
		}
	},
	MenorEmancipado("E", "MENOR EMANCIPADO") {
		public String toString() {
			return "E";
		}
	},
	Otros("O", "OTRAS CAUSAS") {
		public String toString() {
			return "O";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTutela(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTutela convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if (valorEnum.equals("M"))
			return MinoriaEdad;
		else if (valorEnum.equals("E"))
			return MenorEmancipado;
		else if (valorEnum.equals("O"))
			return Otros;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if (valorEnum.equals("M"))
			return MinoriaEdad.getNombreEnum();
		else if (valorEnum.equals("E"))
			return MenorEmancipado.getNombreEnum();
		else if (valorEnum.equals("O"))
			return Otros.getNombreEnum();
		else
			return null;
	}
	
	public static String convertirValor(String valorEnum){
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoTutela tipoTutela : TipoTutela.values()){
				if(tipoTutela.getValorEnum().equals(valorEnum)){
					return tipoTutela.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}
