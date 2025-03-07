package general.acciones;

/**
 * Enumerado de posiciones que puede tomar un script dentro de una página
 * @author Santiago Cuenca
 *
 */
public enum TipoPosicionScript {
	
	TOP("TOP", "TOP"){
		public String toString() {
			return "TOP";
		}
	},
	BOTTOM("BOTTOM", "BOTTOM"){
		public String toString() {
			return "BOTTOM";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoPosicionScript(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;		
	}
	
	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
}
