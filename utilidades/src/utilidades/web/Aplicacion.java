package utilidades.web;

public class Aplicacion {

	private String _codigo_aplicacion = null;
	private String _desc_aplicacion = null;
	private String _alias = null;

	public String getCodigo_aplicacion() {
		return _codigo_aplicacion;
	}

	public void setCodigo_aplicacion(String codigoAplicacion) {
		_codigo_aplicacion = codigoAplicacion;
	}

	public String getDesc_aplicacion() {
		return _desc_aplicacion;
	}

	public void setDesc_aplicacion(String descAplicacion) {
		_desc_aplicacion = descAplicacion;
	}

	public String getAlias() {
		return _alias;
	}

	public void setAlias(String alias) {
		_alias = alias;
	}

}