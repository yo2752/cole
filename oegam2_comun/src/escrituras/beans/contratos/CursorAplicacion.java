package escrituras.beans.contratos;
/**
 * Cursor para recoger los datos de las aplicaciones relacionadas con los contratos.
 * @author juan.gomez
 *
 *   
 *   
 *  Open  C_Aplicaciones For
 *         Select A.Codigo_Aplicacion, A.Desc_Aplicacion, 
 *         decode(Nvl(C.Id_Contrato,0),0,0,1) Asignada 
 */
public class CursorAplicacion {

	private String Codigo_Aplicacion;
	private String Desc_Aplicacion;
	private Integer Asignada;
	
	
	public CursorAplicacion() {
	}
	
	public String getCodigo_Aplicacion() {
		return Codigo_Aplicacion;
	}
	public void setCodigo_Aplicacion(String codigoAplicacion) {
		Codigo_Aplicacion = codigoAplicacion;
	}
	public String getDesc_Aplicacion() {
		return Desc_Aplicacion;
	}
	public void setDesc_Aplicacion(String descAplicacion) {
		Desc_Aplicacion = descAplicacion;
	}
	public Integer getAsignada() {
		return Asignada;
	}
	public void setAsignada(Integer asignada) {
		Asignada = asignada;
	}
	
	
}
