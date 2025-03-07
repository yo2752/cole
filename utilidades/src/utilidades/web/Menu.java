package utilidades.web;

import java.io.Serializable;
import java.math.BigDecimal;

public class Menu implements Serializable {

	private static final long serialVersionUID = -6266035946932293934L;

	private String _codigo_aplicacion; 
    private String _codigo_funcion; 
    private String _desc_funcion; 
    private String _codigo_funcion_padre; 
    private String _url; 
    private BigDecimal _nivel; 
    private BigDecimal _orden; 
    private String _tipo; 
    
    public String getCodigo_aplicacion() {
		return _codigo_aplicacion;
	}
	public void setCodigo_aplicacion(String codigoAplicacion) {
		_codigo_aplicacion = codigoAplicacion;
	}
	public String getCodigo_funcion() {
		return _codigo_funcion;
	}
	public void setCodigo_funcion(String codigoFuncion) {
		_codigo_funcion = codigoFuncion;
	}
	public String getDesc_funcion() {
		return _desc_funcion;
	}
	public void setDesc_funcion(String descFuncion) {
		_desc_funcion = descFuncion;
	}
	public String getCodigo_funcion_padre() {
		return _codigo_funcion_padre;
	}
	public void setCodigo_funcion_padre(String codigoFuncionPadre) {
		_codigo_funcion_padre = codigoFuncionPadre;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		_url = url;
	}
	public BigDecimal getNivel() {
		return _nivel;
	}
	public void setNivel(BigDecimal nivel) {
		_nivel = nivel;
	}
	public BigDecimal getOrden() {
		return _orden;
	}
	public void setOrden(BigDecimal orden) {
		_orden = orden;
	}
	public String getTipo() {
		return _tipo;
	}
	public void setTipo(String tipo) {
		_tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((_codigo_aplicacion == null) ? 0 : _codigo_aplicacion
						.hashCode());
		result = prime * result
				+ ((_codigo_funcion == null) ? 0 : _codigo_funcion.hashCode());
		result = prime
				* result
				+ ((_codigo_funcion_padre == null) ? 0 : _codigo_funcion_padre
						.hashCode());
		result = prime * result
				+ ((_desc_funcion == null) ? 0 : _desc_funcion.hashCode());
		result = prime * result + ((_nivel == null) ? 0 : _nivel.hashCode());
		result = prime * result + ((_orden == null) ? 0 : _orden.hashCode());
		result = prime * result + ((_tipo == null) ? 0 : _tipo.hashCode());
		result = prime * result + ((_url == null) ? 0 : _url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (_codigo_aplicacion == null) {
			if (other._codigo_aplicacion != null)
				return false;
		} else if (!_codigo_aplicacion.equals(other._codigo_aplicacion))
			return false;
		if (_codigo_funcion == null) {
			if (other._codigo_funcion != null)
				return false;
		} else if (!_codigo_funcion.equals(other._codigo_funcion))
			return false;
		if (_codigo_funcion_padre == null) {
			if (other._codigo_funcion_padre != null)
				return false;
		} else if (!_codigo_funcion_padre.equals(other._codigo_funcion_padre))
			return false;
		if (_desc_funcion == null) {
			if (other._desc_funcion != null)
				return false;
		} else if (!_desc_funcion.equals(other._desc_funcion))
			return false;
		if (_nivel == null) {
			if (other._nivel != null)
				return false;
		} else if (!_nivel.equals(other._nivel))
			return false;
		if (_orden == null) {
			if (other._orden != null)
				return false;
		} else if (!_orden.equals(other._orden))
			return false;
		if (_tipo == null) {
			if (other._tipo != null)
				return false;
		} else if (!_tipo.equals(other._tipo))
			return false;
		if (_url == null) {
			if (other._url != null)
				return false;
		} else if (!_url.equals(other._url))
			return false;
		return true;
	}

}
