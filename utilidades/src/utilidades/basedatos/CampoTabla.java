package utilidades.basedatos;

import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class CampoTabla {

	private Integer _orden = null;
	private String _nombre = null;
	private Object _valor = null;
	private Class<?> _clase = null;

	public CampoTabla(String nombreCampo, Object valor, Integer orden) throws OegamExcepcion {
		_nombre = validarNombre(nombreCampo);
		if (null == valor) {
			throw new OegamExcepcion(EnumError.error_00001);
		}
		if (null == orden) {
			throw new OegamExcepcion(EnumError.error_00002);
		}

		_valor = valor;
		_clase = valor.getClass();
		_orden = orden;
	}

	public CampoTabla(String nombreCampo, Class<?> clase, Integer orden) throws OegamExcepcion {
		_nombre = validarNombre(nombreCampo);
		if (null == clase) {
			throw new OegamExcepcion(EnumError.error_00016);
		}
		if (null == orden) {
			throw new OegamExcepcion(EnumError.error_00002);
		}

		_clase = clase;
		_orden = orden;
	}

	public Integer getOrden() {
		return _orden;
	}

	private String validarNombre(String nombreCampo) throws OegamExcepcion {
		if (null == nombreCampo || nombreCampo.trim().length() == 0) {
			throw new OegamExcepcion(EnumError.error_00044);
		}
		return nombreCampo.trim();
	}

	public String getNombre() {
		return _nombre;
	}

	public Object getValor() {
		return _valor;
	}

	public Class<?> getClase() {
		return _clase;
	}

}