package trafico.beans;

public class TramitesCampo{

	private String identificador;
	private String valor;
	
	public TramitesCampo(){
		//Constructor por defecto
	}

	/**
	 * Constructor
	 * @param identificador
	 * @param valor
	 */
	public TramitesCampo(String identificador, String valor){
		this.identificador = identificador;
		this.valor = valor;
	}
	
	/**
	 * Inicializar variables si las hay.
	 * Heredado de {@link com.tbsolutions.jmp.model.Bean com.tbsolutions.jmp.model.Bean}
	 * 
	 */
	public void init() {
		//Metodo heredado de Bean
	}

	/**
	 *  
	 * @return
	 */
	public String getIdentificador() {
		return this.identificador;
	}
	
	/**
	 * 
	 * @param identificador
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getValor() {
		return this.valor;
	}
	
	/**
	 * 
	 * @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}


	
	
	
}