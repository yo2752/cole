package utilidades.basedatos;

import utilidades.mensajes.GestorFicherosPropiedades;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class ConexionBDBean {

	private String _driverClassName = null;
	private String _connectionString = null;
	private String _usuario = null;
	private String _password = null;
	private String _jndi = null;
	private String _tipoBD = null;
	private String _tipoConexion = null;

	/**
	 * Constructor.
	 * 
	 * @param ficheroBaseDatos. Nombre del fichero de propiedades que contiene las
	 *                          características de conexión a la base de datos. Por
	 *                          ejemplo, el valor de este parámetro puede ser
	 *                          basedatos.properties
	 * @throws OegamExcepcion
	 * @throws Throwable
	 * 
	 */

	public ConexionBDBean(String ficheroBaseDatos) throws OegamExcepcion {

		try {
			GestorFicherosPropiedades gestor = new GestorFicherosPropiedades(ficheroBaseDatos);
			// GestorFicherosMensajes gestor = new GestorFicherosMensajes(ficheroBaseDatos);

			setTipoConexion(gestor.getMensaje(ConstantesBD.JDBC_TIPOCONEXION));
			setTipoBD(gestor.getMensaje(ConstantesBD.JDBC_TIPOBD));
			setDriverClassName(gestor.getMensaje(ConstantesBD.JDBC_DRIVER_CLASSNAME));
			setConnectionString(gestor.getMensaje(ConstantesBD.JDBC_CONNECTION_STRING));
			setUsuario(gestor.getMensaje(ConstantesBD.JDBC_USUARIO));
			setPassword(gestor.getMensaje(ConstantesBD.JDBC_PASSWORD));
			setJndi(gestor.getMensaje(ConstantesBD.JDBC_JNDI));
		} catch (OegamExcepcion e) {
			throw new OegamExcepcion(EnumError.error_00027, e.getMessage());
		}
	}

	public ConexionBDBean(GestorFicherosPropiedades gestor) throws OegamExcepcion {

		setTipoConexion(gestor.getMensaje(ConstantesBD.JDBC_TIPOCONEXION));
		setTipoBD(gestor.getMensaje(ConstantesBD.JDBC_TIPOBD));
		setDriverClassName(gestor.getMensaje(ConstantesBD.JDBC_DRIVER_CLASSNAME));
		setConnectionString(gestor.getMensaje(ConstantesBD.JDBC_CONNECTION_STRING));
		setUsuario(gestor.getMensaje(ConstantesBD.JDBC_USUARIO));
		setPassword(gestor.getMensaje(ConstantesBD.JDBC_PASSWORD));
		setJndi(gestor.getMensaje(ConstantesBD.JDBC_JNDI));

	}

	public ConexionBDBean(String driver, String connectionString, String usuario, String password, String jndi,
			String tipoBD, String tipoConexion) {
		_driverClassName = driver;
		_connectionString = connectionString;
		_usuario = usuario;
		_password = password;
		_jndi = jndi;
		_tipoBD = tipoBD;
		_tipoConexion = tipoConexion;
	}

	public String getTipoConexion() {
		return _tipoConexion;
	}

	public void setTipoConexion(String tipoConexion) throws OegamExcepcion {
		if (null == tipoConexion) {
			throw new OegamExcepcion(EnumError.error_00028);
		}

		if (!tipoConexion.equals("1") && !tipoConexion.equals("2")) {
			throw new OegamExcepcion(EnumError.error_00028);
		}

		_tipoConexion = tipoConexion;
	}

	public String getTipoBD() {
		return _tipoBD;
	}

	public void setTipoBD(String tipoBD) throws OegamExcepcion {
		if (null == tipoBD) {
			throw new OegamExcepcion(EnumError.error_00029);
		}

		tipoBD = tipoBD.trim();

		if (tipoBD.length() == 0) {
			throw new OegamExcepcion(EnumError.error_00029);
		}

		_tipoBD = tipoBD;
	}

	public String getDriverClassName() {
		return _driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		if (null != driverClassName) {
			driverClassName = driverClassName.trim();
		}
		_driverClassName = driverClassName;
	}

	public String getConnectionString() {
		return _connectionString;
	}

	public void setConnectionString(String connectionString) {
		if (null != connectionString) {
			connectionString = connectionString.trim();
		}
		_connectionString = connectionString;
	}

	public String getUsuario() {
		return _usuario;
	}

	public void setUsuario(String usuario) {
		if (null != usuario) {
			usuario = usuario.trim();
		}
		_usuario = usuario;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		if (null != password) {
			password = password.trim();
		}
		_password = password;
	}

	public String getJndi() {
		return _jndi;
	}

	public void setJndi(String jndi) {
		if (null != jndi) {
			jndi = jndi.trim();
		}
		_jndi = jndi;
	}

}