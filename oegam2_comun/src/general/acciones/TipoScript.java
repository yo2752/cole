package general.acciones;

/**
 * Enumerado para definir los diferentes tipos de scripts que se pueden añadir dinámicamente desde los Action
 * @author Santiago Cuenca
 *
 */
public enum TipoScript {
	
	CSS("CSS", "CSS"){
		public String toString() {
			return "CSS";
		}
	},
	JS("JS", "JAVASCRIPT"){
		public String toString() {
			return "JS";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoScript(String valorEnum, String nombreEnum){
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
