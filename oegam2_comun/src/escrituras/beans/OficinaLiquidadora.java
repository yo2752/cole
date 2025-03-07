package escrituras.beans;

/**
 * Bean que almacena datos de la tabla OFICINA_LIQUIDADORA.
 *
 */
public class OficinaLiquidadora {
	
	 private String oficinaLiquidadora;
	 private String nombreOficinaLiq;
	 private Provincia provincia;
	 
	 public OficinaLiquidadora() {

	}
	 
	public OficinaLiquidadora(boolean inicializar) {

		provincia = new Provincia();
	}

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	

	public String getNombreOficinaLiq() {
		return nombreOficinaLiq;
	}

	public void setNombreOficinaLiq(String nombreOficinaLiq) {
		this.nombreOficinaLiq = nombreOficinaLiq;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String imprimir() {
		return "OficinaLiquidadora [nombreOficinaLiq=" + nombreOficinaLiq
				+ ", oficinaLiquidadora=" + oficinaLiquidadora + ", provincia="
				+ provincia.imprimir()+"]";
	}

	  

}
