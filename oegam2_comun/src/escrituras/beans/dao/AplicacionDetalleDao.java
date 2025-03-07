package escrituras.beans.dao;



/**
 * Bean que almacena datos de la aplicacion
 *
 */
/*  Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
                decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada*/

public class AplicacionDetalleDao {

	
	private String codigo_Aplicacion;
	private String desc_Aplicacion;
	private Integer asignada;
	public String getCodigo_Aplicacion() {
		return codigo_Aplicacion;
	}
	public void setCodigo_Aplicacion(String codigoAplicacion) {
		codigo_Aplicacion = codigoAplicacion;
	}
	public String getDesc_Aplicacion() {
		return desc_Aplicacion;
	}
	public void setDesc_Aplicacion(String descAplicacion) {
		desc_Aplicacion = descAplicacion;
	}
	public Integer getAsignada() {
		return asignada;
	}
	public void setAsignada(Integer asignada) {
		this.asignada = asignada;
	}
	
	
}