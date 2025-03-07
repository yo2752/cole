package escrituras.beans.dao;

public class UnidadPoblacionalDao {

	
	
	private String id_Provincia; 
	private String id_Municipio; 
	private String id_UnidadPoblacional; 
	private String municipio; 
	private String entidad_Colectiva; 
	//private String entidadSingular; 
	private String nucleo;
	private String pueblo;
	
	
	
	/*public String getEntidadSingular() {
		return entidadSingular;
	}
	public void setEntidadSingular(String entidadSingular) {
		this.entidadSingular = entidadSingular;
	}*/
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getNucleo() {
		return nucleo;
	}
	public void setNucleo(String nucleo) {
		this.nucleo = nucleo;
	}
	public String getId_Provincia() {
		return id_Provincia;
	}
	public void setId_Provincia(String idProvincia) {
		id_Provincia = idProvincia;
	}
	public String getId_Municipio() {
		return id_Municipio;
	}
	public void setId_Municipio(String idMunicipio) {
		id_Municipio = idMunicipio;
	}
	public String getId_UnidadPoblacional() {
		return id_UnidadPoblacional;
	}
	public void setId_UnidadPoblacional(String idUnidadPoblacional) {
		id_UnidadPoblacional = idUnidadPoblacional;
	}
	public String getEntidad_Colectiva() {
		return entidad_Colectiva;
	}
	public void setEntidad_Colectiva(String entidadColectiva) {
		entidad_Colectiva = entidadColectiva;
	}
	public String getPueblo() {
		return pueblo;
	}
	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}	
	
}
