package escrituras.beans.dao;



/**
 * Bean que almacena datos de la aplicacion
 *
 */
/*  Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
                decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada*/

public class ApContratoDao {

	
	private String codigo_Aplicacion;
	private String desc_aplicacion;
	private String alias;
	private String cif;
	private String razon_social;
	private String anagrama_contrato;
	public String getCodigo_Aplicacion() {
		return codigo_Aplicacion;
	}
	public void setCodigo_Aplicacion(String codigoAplicacion) {
		codigo_Aplicacion = codigoAplicacion;
	}
	public String getDesc_aplicacion() {
		return desc_aplicacion;
	}
	public void setDesc_aplicacion(String descAplicacion) {
		desc_aplicacion = descAplicacion;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razonSocial) {
		razon_social = razonSocial;
	}
	public String getAnagrama_contrato() {
		return anagrama_contrato;
	}
	public void setAnagrama_contrato(String anagramaContrato) {
		anagrama_contrato = anagramaContrato;
	}
	
}