package escrituras.beans;

public class UnidadPoblacional {


	private Provincia provincia; 
	private MunicipioBean municipioBean; 
	private String idUnidadPoblacional; 
	private String municipio; 
	private String entidadColectiva; 
	//private String entidadSingular; 
	private String pueblo; 
	private String nucleo;
	
	public UnidadPoblacional(boolean inicializar)
	{
		this();
		if (inicializar){
			provincia = new Provincia();
			municipioBean = new MunicipioBean(); 
		}
	}
	
	
	public UnidadPoblacional() {
		
	}


	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public MunicipioBean getMunicipioBean() {
		return municipioBean;
	}
	public void setMunicipioBean(MunicipioBean municipioBean) {
		this.municipioBean = municipioBean;
	}
	public String getIdUnidadPoblacional() {
		return idUnidadPoblacional;
	}
	public void setIdUnidadPoblacional(String idUnidadPoblacional) {
		this.idUnidadPoblacional = idUnidadPoblacional;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getEntidadColectiva() {
		return entidadColectiva;
	}
	public void setEntidadColectiva(String entidadColectiva) {
		this.entidadColectiva = entidadColectiva;
	}
	/*public String getEntidadSingular() {
		return entidadSingular;
	}
	public void setEntidadSingular(String entidadSingular) {
		this.entidadSingular = entidadSingular;
	}*/
	public String getNucleo() {
		return nucleo;
	}
	public void setNucleo(String nucleo) {
		this.nucleo = nucleo;
	}


	public String getPueblo() {
		return pueblo;
	}


	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	} 
	
}
