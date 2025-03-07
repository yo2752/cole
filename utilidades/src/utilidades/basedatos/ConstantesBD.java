package utilidades.basedatos;

public class ConstantesBD {

	// direcciones y nombres de los ficheros en el shared lib del Tomcat.
	public static String RUTA_BD_PROPERTIES = "basedatos.properties";
	// public static String RUTA_BD_PROPERTIES_ESCRITURAS="basedatosEscrituras.properties";

	// Nombres de las propiedades dentro de los ficheros properties de bases de
	// datos. Podrían extraerse a clases de constantes.
	public static String JDBC_TIPOCONEXION = "jdbc.tipoConexion";
	public static String JDBC_TIPOBD = "jdbc.tipoBD";
	public static String JDBC_DRIVER_CLASSNAME = "jdbc.driverClassName";
	public static String JDBC_CONNECTION_STRING = "jdbc.connectionString";
	public static String JDBC_USUARIO = "jdbc.usuario";
	public static String JDBC_PASSWORD = "jdbc.password";
	public static String JDBC_JNDI = "jdbc.jndi";

//	// Nombres de los proyectos/bases de datos
//	public static String BDGENERICO="BDGenerico"; 
//	public static String BDESCRITURAS="BDEscrituras"; 
//	public static String BDREGISTRADORES="BDRegistradores";

	public static final String jndiDs = "java:comp/env/jdbc/";

	public static final String tipoConexionJNDI = "1";
	public static final String tipoConexionJDBC = "2";

	public static final String BD_ORACLE10 = "oracle10";

}