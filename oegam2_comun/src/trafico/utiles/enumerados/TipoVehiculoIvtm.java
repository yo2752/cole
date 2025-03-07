package trafico.utiles.enumerados;
//TODO MPC. Cambio IVTM. Esta clase está bien aquí.
public enum TipoVehiculoIvtm {

	Turismo("TU"){
		public String toString(){
			return "TU";
		}
	}, Camiones("CA"){
		public String toString(){
			return "CA";
		}
	}, Autobuses("AU"){
		public String toString(){
			return "AU";
		}
	}, Ciclomotores("CI"){
		public String toString(){
			return "CI";
		}
	}, Motocicletas("MT"){
		public String toString(){
			return "MT";
		}
	}, Remolques("RE"){
		public String toString(){
			return "RE";
		}
	}, Tractores("TR"){
		public String toString(){
			return "TR";
		}
	}, Otro (""){
		public String toString(){
			return "";
		}
	};

	private String valorEnum;

	private TipoVehiculoIvtm(String valorEnum){
		this.valorEnum = valorEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

}