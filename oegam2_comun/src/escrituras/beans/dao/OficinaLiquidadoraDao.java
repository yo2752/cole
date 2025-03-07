package escrituras.beans.dao;

/**
 * Bean que almacena datos de la tabla OFICINA_LIQUIDADORA.
 *
 */
public class OficinaLiquidadoraDao {
	
	 private String oficinaLiquidadora;
	 private String nombreOficinaLiq;
	 private String idProvincia;
	 
	 public OficinaLiquidadoraDao() {
		
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

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

}
