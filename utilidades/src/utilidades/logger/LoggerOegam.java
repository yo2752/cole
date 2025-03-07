package utilidades.logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import utilidades.web.OegamExcepcion;

/**
 *  Ejemplo de uso:
 *  <p><blockquote><pre>LoggerOegam.getLogger("ProcesoCheckCTIT").info("mensaje");</pre></blockquote><p>
 *	@author ruben.diaz
 */

public class LoggerOegam implements ILoggerOegam {
	private static final String APENDER_NAME = "logger.oegam.appender.";
	private static final String APENDER_OPTION = "logger.oegam.options.";

	public static final String NUM_EXPEDIENTE = "numExpediente";

	private static final String PATTERN_CLASS_ABSOLUTE = "%C";
	private static final String PATTERN_CLASS_MINIMAL = "%c";
	private static final String PATTERN_METHOD = "%m";
	private static final String PATTERN_PROCESS = "%P";
	private static final String PATTERN_EXPEDIENTE = "%X";
	private static final String PATTERN_COLEGIADO = "%i";

	private static LoggerOegam instance = null;

	private Map<String, LoggerOegam> loggers = null;
	private Logger log = null;
	private String options = null;
	private String process = null;

	/**
	 * Metodo estático que implementa el patrón singleton.
	 * 
	 * @return
	 */
	public static ILoggerOegam getLogger() {
		return getLogger("");
	}

	/**
	 * Metodo estático que implementa el patrón singleton.
	 * 
	 * @param process
	 * @return
	 */
	public static synchronized ILoggerOegam getLogger(Class<?> class1) {
		if (instance == null) {
			instance = new LoggerOegam();
		}
		return instance.getLoggerForProcess(class1);
	}

	/**
	 * Metodo estático que implementa el patrón singleton.
	 * 
	 * @param process
	 * @return
	 */
	public static synchronized ILoggerOegam getLogger(String process) {
		if (instance == null) {
			instance = new LoggerOegam();
		}
		return instance.getLoggerForProcess(process);
	}

	/**
	 * Constructor en el que se inicaliza el mapa de loggers.
	 */
	private LoggerOegam() {
		loggers = new HashMap<String, LoggerOegam>();
		configure();
	}

	private LoggerOegam(String process, String options, Logger log) {
		this.log = log;
		this.process = process;
		this.options = options;
	}

	/**
	 * Metodo que devuelve el logger especifico para el proceso pasado como
	 * parametro (configurable)
	 * 
	 * @param process
	 * @return
	 */
	private LoggerOegam getLoggerForProcess(String process) {
		if (process != null && loggers.containsKey(process)) {
			return loggers.get(process);
		} else {
			LoggerOegam logAux = getLoggerStarted(process);
			return logAux != null ? logAux : loggers.get(DEFAULT_PROCESS);
		}
	}

	/**
	 * Metodo que devuelve el logger especifico para el proceso pasado como
	 * parametro (configurable)
	 * 
	 * @param process
	 * @return
	 */
	private LoggerOegam getLoggerForProcess(Class<?> class1) {
		if (class1 != null && loggers.containsKey(class1.getCanonicalName())) {
			return loggers.get(class1.getCanonicalName());
		} else if (class1 != null && loggers.containsKey(class1.getSimpleName())) {
			return loggers.get(class1.getSimpleName());
		} else {
			LoggerOegam logAux = getLoggerStarted(class1.getCanonicalName());
			return logAux != null ? logAux : loggers.get(DEFAULT_PROCESS);
		}
	}

	/**
	 * Si una y solo una de las claves, empieza por el proceso aportado, se
	 * devuelve ese logger. Ejemplo:
	 * trafico.acciones.ConsultaTramiteTraficoAction -> trafico
	 * 
	 * @param process
	 * @return
	 */
	private LoggerOegam getLoggerStarted(String process) {
		if (process == null) {
			return null;
		}
		LoggerOegam log = null;
		for (String key : loggers.keySet()) {
			if (process.startsWith(key)) {
				if (log == null) {
					log = loggers.get(key);
				} else {
					return null;
				}
			}
		}
		return log;
	}

	/**
	 * Metodo privado que lee la configuración de properties para inicializar el
	 * logger.
	 */
	private void configure() {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j.properties"));
			for (String propertyName : properties.stringPropertyNames()) {
				if (propertyName.startsWith(APENDER_NAME)) {
					String appender = propertyName.substring(APENDER_NAME.length());
					String processes = properties.getProperty(propertyName);
					String options = properties.getProperty(APENDER_OPTION + appender);
					Logger loggerAux = Logger.getLogger(appender);
					for (String process : Arrays.asList(processes.split(","))) {
						loggers.put(process.trim(), new LoggerOegam(process.trim(), options!=null?options:"", loggerAux));
					}
				}
			}
		} catch (Throwable e) {
			// Chungo, no vamos a tener logs.
		}
		if (!loggers.containsKey(DEFAULT_PROCESS)) {
			loggers.put(DEFAULT_PROCESS, new LoggerOegam(DEFAULT_PROCESS, null,
					Logger.getLogger(DEFAULT_PROCESS)));
		}
	}

	/**
	 * Formatea la traza de log, incluyendo las opciones configuradas para el
	 * appender %C - Nombre de la clase %c - Nombre canonico de la clase %m -
	 * Nombre del metódo %p - proceso %X - número de expediente %i - número de
	 * colegiado.
	 * 
	 * @param message
	 * @param locator
	 * @return
	 */
	private String formatMessage(Object message, String locator) {
		StackTraceElement ste = null;
		try {
			String result = options + " " + (message != null ? message.toString() : "null");
			if (result.contains(PATTERN_CLASS_ABSOLUTE)) {
				try {
					if (ste == null) {
						ste = getStackTraceElementOrigin();
					}
					result = result.replaceAll(PATTERN_CLASS_ABSOLUTE, ste.getClassName());
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_CLASS_ABSOLUTE, "-");
				}
			} else if (result.contains(PATTERN_CLASS_MINIMAL)) {
				try {
					if (ste == null) {
						ste = getStackTraceElementOrigin();
					}
					result = result.replaceAll(PATTERN_CLASS_MINIMAL, ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1));
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_CLASS_MINIMAL, "-");
				}
			}
			if (result.contains(PATTERN_METHOD)) {
				try {
					if (ste == null) {
						ste = getStackTraceElementOrigin();
					}
					result = result.replaceAll(PATTERN_METHOD, ste.getMethodName());
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_METHOD, "-");
				}
			}
			if (result.contains(PATTERN_PROCESS)) {
				try {
					result = result.replaceAll(PATTERN_PROCESS, process);
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_PROCESS, "-");
				}
			}
			if (result.contains(PATTERN_COLEGIADO)) {
				try {
					
					//String col = ((GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL)).getUsuario().getNum_colegiado();
					//result = result.replaceAll(PATTERN_COLEGIADO,  String.format("%4s", col!=null?col:""));
					result = result.replaceAll(PATTERN_COLEGIADO,  String.format("%4s", ""));
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_COLEGIADO, "    ");
				}
			}
			if (result.contains(PATTERN_EXPEDIENTE)) {
				try {
					String exp = locator;
					result = result.replaceAll(PATTERN_EXPEDIENTE, (exp != null ? exp : ""));
				} catch (Exception e) {
					result = result.replaceAll(PATTERN_EXPEDIENTE, "");
				}
			}
			return result.trim();
		} catch (Exception e) {
			return message != null ? message.toString().trim() : "null";
		}
	}
	
	private StackTraceElement getStackTraceElementOrigin() {
		StackTraceElement[] stes = new Exception().getStackTrace();
		for (StackTraceElement ste : stes) {
			if (!this.getClass().getCanonicalName().equals(ste.getClassName())) {
				return ste;
			}
		}
		return stes[0];
	}

	/**
	 * Formatea la traza de log, incluyendo las opciones configuradas para el
	 * appender %C - Nombre de la clase %c - Nombre canonico de la clase %m -
	 * Nombre del metódo %p - proceso %X - número de expediente %i - número de
	 * colegiado.
	 * 
	 * @param message
	 * @return
	 */
	private String formatMessage(Object message) {
		return formatMessage(message,"");
	}

	public void trace(Object message) {
		log.trace(formatMessage(message));
	}

	public void trace(Object message, Throwable t) {
		log.trace(formatMessage(message), t);
	}
	
	public void trace(Object message, String locator) {
		log.trace(formatMessage(message, locator));
	}

	public void debug(Object message) {
		log.debug(formatMessage(message));
	}

	public void debug(Object message, Throwable t) {
		log.debug(formatMessage(message), t);
	}
	
	public void debug(Object message,  String locator) {
		log.debug(formatMessage(message, locator));
	}

	public void info(Object message) {
		log.info(formatMessage(message));
	}

	public void info(Object message, Throwable t) {
		log.info(formatMessage(message), t);
	}
	
	public void info(Object message,  String locator) {
		log.info(formatMessage(message, locator));
	}

	public void warn(Object message) {
		log.warn(formatMessage(message));
	}

	public void warn(Object message, Throwable t) {
		log.warn(formatMessage(message), t);
	}
	
	public void warn(Object message, String locator) {
		log.warn(formatMessage(message, locator));
	}

	public void warn(Throwable t) {
		log.warn("", t);
	}

	public void error(Object message) {
		log.error(formatMessage(message));
	}

	public void error(Object message, Throwable t, String locator) {
		log.error(formatMessage(message, locator), t);
	}
	
	public void error(Object message, Throwable t) {
		log.error(formatMessage(message), t);
	}
	
	public void error(Object message, String locator) {
		log.error(formatMessage(message, locator));
	}

	public void error(Throwable e) {
		log.error("", e);
	}

	public void fatal(Object message) {
		log.fatal(formatMessage(message));
	}

	public void fatal(Object message, Throwable t) {
		log.fatal(formatMessage(message), t);
	}
	
	public void fatal(Object message, String locator) {
		log.fatal(formatMessage(message, locator));
	}

	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public Level getEffectiveLevel() {
		return log.getEffectiveLevel();
	}

	public Level getLevel() {
		return log.getLevel();
	}

	public void logarOegamExcepcion(String mensaje, OegamExcepcion e) {
		if (e != null) {
			error("mensaje de error " + e.getMensajeError1());
			error("codigo de error " + e.getCodigoError());
			error(mensaje!=null?mensaje:"", e, "");
		} else if (mensaje != null) {
			error(mensaje);
		}
	}

}
