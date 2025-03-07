package utilidades.logger;

import org.apache.log4j.Level;

import utilidades.web.OegamExcepcion;

public interface ILoggerOegam {

	// Procesos
	static final String DEFAULT_PROCESS = "file";

	void trace(Object message);

	void trace(Object message, Throwable t);
	
	/**
	 * Registra un evento de tipo trace
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void trace(Object message, String locator);

	void debug(Object message);

	void debug(Object message, Throwable t);
	
	/**
	 * Registra un evento de tipo debug
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void debug(Object message, String locator);

	void info(Object message);

	void info(Object message, Throwable t);
	
	/**
	 * Registra un evento de tipo info
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void info(Object message, String locator);

	void warn(Object message);

	void warn(Object message, Throwable t);
	
	/**
	 * Registra un evento de tipo warn
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void warn(Object message, String locator);

	void warn(Throwable t);

	void error(Object message);
	
	void error(Object message, Throwable e);

	void error(Object message, Throwable e, String locator);
	
	/**
	 * Registra un evento de tipo error
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void error(Object message, String locator);

	void error(Throwable e);

	void fatal(Object message);

	void fatal(Object message, Throwable t);
	
	/**
	 * Registra un evento de tipo fatal
	 * @param message cuerpo del mensaje
	 * @param locator cadena que ayuda a localizar el caso concreto donde se ha 
	 * producido el evento, como por ejemplo: numExpediente, dni... etc. 
	 */
	void fatal(Object message, String locator);

	boolean isTraceEnabled();

	boolean isDebugEnabled();

	boolean isInfoEnabled();

	Level getEffectiveLevel();

	Level getLevel();

	void logarOegamExcepcion(String mensaje, OegamExcepcion e);
}
