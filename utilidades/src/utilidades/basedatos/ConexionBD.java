package utilidades.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

class ConexionBD {

	private static ConexionBD instance = null;
	private static InitialContext ctx;
	private OracleDataSource ds;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConexionBD.class);
	private HashMap<String, DataSource> hmds;

	public static ConexionBD getInstance() throws NamingException, OegamExcepcion {
		log.trace("getInstance ConexionBD");
		if (instance == null)
			instance = new ConexionBD();
		log.trace("devolvemos instancia jndi");
		return instance;
	}

	public ConexionBD() throws OegamExcepcion, NamingException {
		log.info("creación ConexionBD");
		ctx = new InitialContext();
		hmds = new HashMap<String, DataSource>();

	}

	public ConexionBD(ConexionBDBean bdBean) throws OegamExcepcion, NamingException {
		log.info("creación ConexionBD");
		ctx = new InitialContext();
		if (bdBean == null) {
			throw new OegamExcepcion(EnumError.error_00017);
		}

		try {
			String strJNDI = bdBean.getJndi();
			if (null != bdBean && null != strJNDI && strJNDI.length() > 0) {
				if (ctx == null) {
					throw new OegamExcepcion(EnumError.error_00018);
				}
//no necesita ampliar la hashmap
				if (hmds.get(strJNDI) == null) {
//				setDs((DataSource)ctx.lookup(strJNDI));
					hmds.put(strJNDI, (OracleDataSource) ctx.lookup(ConstantesBD.jndiDs + strJNDI));
					log.trace("lookup al Tomcat de :" + (ConstantesBD.jndiDs + strJNDI));
					if (hmds.get(strJNDI) == null) {
						throw new OegamExcepcion(EnumError.error_00019);
					}
				}
			}
		} catch (NamingException e) {
			throw new OegamExcepcion(EnumError.error_00020, e.getMessage());
		}
	}

	public OracleDataSource getDs() {
		return ds;
	}

	public void setDs(OracleDataSource ds) {
		this.ds = ds;
	}

	public Connection getJNDIConexion() throws OegamExcepcion {
		try {
			if (getDs() != null) {
				log.trace("damos conexion desde el pool");
				return getDs().getConnection();
			} else {
				throw new OegamExcepcion(EnumError.error_00021);
			}
		} catch (SQLException e) {
			throw new OegamExcepcion(EnumError.error_00023, e.getMessage());
		}
	}

	public Connection getJNDIConexion(String nombreDataSource) throws Throwable {
		log.trace("getJDNIConexion(String nombreDataSource) de " + nombreDataSource);
		if (hmds.get(nombreDataSource) == null) {
			hmds.put(nombreDataSource, (DataSource) ctx.lookup(ConstantesBD.jndiDs + nombreDataSource));
			log.trace("lookup al Tomcat de :" + (ConstantesBD.jndiDs + nombreDataSource));
			if (hmds.get(nombreDataSource) == null) {
				throw new OegamExcepcion(EnumError.error_00019);
			}
		}
		log.trace("damos conexion desde el pool");
		return hmds.get(nombreDataSource).getConnection();
	}

	private static synchronized DataSource getJNDIDataSource(ConexionBDBean bdBean) throws OegamExcepcion {

		if (bdBean == null) {
			throw new OegamExcepcion(EnumError.error_00017);
		}

		try {
			DataSource ds = null;
			String strJNDI = bdBean.getJndi();
			if (null != bdBean && null != strJNDI && strJNDI.length() > 0) {
				ctx = new InitialContext();
				if (ctx == null) {
					throw new OegamExcepcion(EnumError.error_00018);
				}
				ds = (DataSource) ctx.lookup(strJNDI);
				if (ds == null) {
					throw new OegamExcepcion(EnumError.error_00019);
				}
				return ds;
			}
			return ds;
		} catch (NamingException e) {
			throw new OegamExcepcion(EnumError.error_00020, e.getMessage());
		}
	}

	public Connection getJDBCConexion(ConexionBDBean beanBD) throws OegamExcepcion {

		if (null == beanBD) {
			throw new OegamExcepcion(EnumError.error_00024);
		}

		try {
			Class.forName(beanBD.getDriverClassName()).newInstance();
		} catch (Throwable e) {
			throw new OegamExcepcion(EnumError.error_00025);
		}

		try {
			return DriverManager.getConnection(beanBD.getConnectionString(), beanBD.getUsuario(), beanBD.getPassword());
		} catch (Throwable e) {
			throw new OegamExcepcion(EnumError.error_00026);
		}
	}
}