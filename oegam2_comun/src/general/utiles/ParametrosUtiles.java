package general.utiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ParametrosUtiles {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ParametrosUtiles.class);

	/**
	 * 
	 * @param beanPQGeneral BeanPQCualquiera que hereda de beanPQGeneral
	 * @param map           HashMap resultado de un procedimiento almacenado
	 * @return Un beanPQ relleno con los datos de la hashmap.
	 */

	public static BeanPQGeneral getBeanPQRelleno(BeanPQGeneral beanPQGeneral, HashMap<String, Object> map) {
		Iterator<?> itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry e = (Map.Entry) itr.next();
			log.debug("clave: " + e.getKey() + "valor:" + e.getValue());
			if (e.getValue() != null) {
				log.debug("clase: " + e.getValue().getClass());
				Method metodoBeanPq;
				try {
					metodoBeanPq = beanPQGeneral.getClass().getMethod("set" + e.getKey(), e.getValue().getClass());
					metodoBeanPq.invoke(beanPQGeneral, e.getValue());
				} catch (SecurityException e1) {
					log.error(e1);
				} catch (NoSuchMethodException e1) {
					log.warn("no existe el set" + e1);

					try {
						metodoBeanPq = beanPQGeneral.getClass().getMethod("set" + e.getKey(), Object.class);
						metodoBeanPq.invoke(beanPQGeneral, e.getValue());
					} catch (IllegalArgumentException e2) {
						log.error(e2);
					} catch (IllegalAccessException e2) {
						log.error(e2);
					} catch (InvocationTargetException e2) {
						log.error(e2);
					} catch (SecurityException e3) {
						log.error(e3);
					} catch (NoSuchMethodException e3) {
						log.error(e3);
					}
					// TODO incluir aqui el rowid!
				} catch (IllegalArgumentException e1) {
					log.error(e1);
				} catch (IllegalAccessException e1) {
					log.error(e1);
				} catch (InvocationTargetException e1) {
					log.error(e1);
				}
			}

		}
		return beanPQGeneral;
	}

	public static Object dameValordeParametro(BeanPQGeneral p, String nombreParametro) {
		Object obj = null;

		if (null != p && null != nombreParametro) {
			try {
				String nombreParametroMayuscula = nombreParametro.substring(0, 1).toUpperCase()
						+ nombreParametro.substring(1);
				Method m = p.getClass().getMethod("get" + nombreParametroMayuscula, null);
				obj = m.invoke(p);
			} catch (SecurityException e) {
				log.error(e);
			} catch (NoSuchMethodException e) {
				log.warn(e);
			} catch (IllegalArgumentException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (InvocationTargetException e) {
				log.error(e);
			}
		}
		return obj;
	}

	/*
	 * public static Class<?> dameClasedeParametro(BeanGeneral p,String
	 * nombreParametro) throws IllegalAccessException,InvocationTargetException {
	 * 
	 * // p.getClass().getField(nombreParametro).getClass(); Class<?>
	 * claseResultado=null; if (null!=p) if (nombreParametro!=null) {try{ String
	 * nombreParametroMayuscula = nombreParametro.substring(0, 1).toUpperCase() +
	 * nombreParametro.substring(1); Method m = p.getClass().getMethod("get" +
	 * nombreParametroMayuscula,null);
	 * 
	 * if (null!=m.invoke(p)) claseResultado = m.invoke(p).getClass(); //
	 * System.out.println("resultado del invoke" + resultado); }
	 * 
	 * catch (NoSuchMethodException e) { log.error(e); } }
	 * System.out.println(claseResultado);
	 * 
	 * return claseResultado;
	 * 
	 * }
	 */

	/**
	 * Devuelve la clase correspondiente para Java dependiendo del tipo que necesite
	 * Oracle para su parámetro del procedimiento almacenado, y si es un cursor
	 * devuelve null
	 */

	public static Class<?> dameClasedeParametro(int tipoSql) {
		Class<?> clase = null;

		if (tipoSql == java.sql.Types.VARCHAR)
			clase = java.lang.String.class;
		else if (tipoSql == java.sql.Types.TIMESTAMP)
			clase = java.sql.Timestamp.class;
		else if (tipoSql == java.sql.Types.NUMERIC)
			clase = java.math.BigDecimal.class;

		return clase;
	}

	public static Class<?> dameClasedeParametro(BeanPQGeneral bean, String nombreParametro) {
		Object value;
		BeanPQGeneral entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		try {
			Method getter = null;
			try {
				getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
			} catch (Exception e) {
				log.error(e);
			}

			value = getter.invoke(entidad, new Object[0]);
			// Si el objeto obtenido es NULL, intentamos setearle uno de los parametros
			// esperados
			if (value == null) {
				Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro), String.class);
				setter.invoke(entidad, new String(""));
				getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
				value = getter.invoke(entidad, new Object[0]);
			}
			log.debug("class:" + value.getClass());
			entidad = null;
			return value.getClass();
		} catch (Exception ex) {
			try {
				Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro), BigDecimal.class);
				setter.invoke(entidad, new BigDecimal(0));
				Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
				value = getter.invoke(entidad, new Object[0]);
				log.debug("class:" + value.getClass());
				entidad = null;
				return value.getClass();
			} catch (Exception ex2) {
				try {
					Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro),
							Timestamp.class);
					setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
					value = getter.invoke(entidad, new Object[0]);
					log.debug("class:" + value.getClass());
					entidad = null;
					return value.getClass();
				} catch (Exception ex3) {
					log.error("El parametro " + nombreParametro
							+ " no tiene una clase conocida (String, BigDecimal, Timestamp");
					entidad = null;
					return null;
				}
			}
		}
	}

	public static Object dameValordeParametro(BeanPQGeneral p, String nombreParametro, int sqlType,
			String nombrePaquete) {

		Object obj = null;

		if (null != p && null != nombreParametro) {
			try {
				String nombreParametroMayuscula = nombreParametro.substring(0, 1).toUpperCase()
						+ nombreParametro.substring(1);
				Method m = p.getClass().getMethod("get" + nombreParametroMayuscula, null);
				obj = m.invoke(p);
			} catch (SecurityException e) {
				log.error(e);
			} catch (NoSuchMethodException e) {
				log.warn(e);
//			System.out.println("no existe el metodo que se invoca en el BeanPQ!");
//			System.out.println("procedemos a autogenerarlo");
//			GeneradorBeans.generarBeansPQ(nombrePaquete, "C:\\borrame\\corregidos\\");
			} catch (IllegalArgumentException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (InvocationTargetException e) {
				log.error(e);
			}
		}

		// Control de tipos con getClass
		if (sqlType == java.sql.Types.NUMERIC && obj != null) {
			if (obj.getClass().equals(BigDecimal.class)) {
				log.debug(" " + nombreParametro + " era BigDecimal, ok");
			} else {
				try {
					Integer in = (java.lang.Integer) obj;
					obj = new BigDecimal(in);
					log.warn(nombreParametro + " era Integer, pasamos a BigDecimal");
				} catch (ClassCastException e1) {
					log.error(" " + nombreParametro + "--------->  no era BigDecimal ni Integer ni casteable");
					obj = null;
					try {
						obj = new BigDecimal((String) obj);
					} catch (Exception e2) {
						log.error(
								" " + nombreParametro + "--------->  no se puede convertir de String a bigDecimal");
					}
				}
			}
		}

		if (sqlType == java.sql.Types.VARCHAR && obj != null) {
			if (obj.getClass().equals(String.class)) {
				log.debug(" " + nombreParametro + " era String, ok");
			} else {
				try {
					log.warn(" " + nombreParametro + " no era String, intentamos un cast");
					String str = (String) obj;
					obj = new String(str);
				} catch (ClassCastException e1) {
					log.error(" " + nombreParametro + "--------->  no era String, ni casteable");
					obj = null;
				}
			}
		}

		if (sqlType == java.sql.Types.TIMESTAMP && obj != null) {
			if (obj.getClass().equals(Timestamp.class)) {
				log.debug(" " + nombreParametro + " el parametro era Timestamp, ok");
			} else {
				try {
					log.error(" " + nombreParametro + " no era Timestamp");
				} catch (ClassCastException e1) {
					log.error(" " + nombreParametro + "---------> deberia ser Timestamp, tipo de dato incorrecto");
					obj = null;
				}
			}
		}

		/*
		 * //control de tipos con cast if (sqlType==java.sql.Types.NUMERIC) if
		 * (obj!=null) try{ BigDecimal bd = (BigDecimal)obj;
		 * System.out.println("el parametro era bigdecimal, ok"); }
		 * 
		 * catch (ClassCastException e) { try { Integer in = (java.lang.Integer)obj; obj
		 * = new BigDecimal(in);
		 * System.out.println("era integer, pasamos a bigdecimal"); } catch
		 * (ClassCastException e1) {
		 * 
		 * System.out.println(nombreParametro +
		 * "--------->  no era bigdecimal ni integer"); } }
		 * 
		 * if (sqlType==java.sql.Types.VARCHAR) if (obj!=null) try{ String str =
		 * (java.lang.String) obj; obj = new String(str); } catch(ClassCastException e1)
		 * { System.out.println(nombreParametro +
		 * "---------> deberia ser String, formato incorrecto"); obj = null; }
		 * 
		 * 
		 * if (sqlType==java.sql.Types.TIMESTAMP)
		 * 
		 * if (obj!=null) try{ Timestamp timestamp = (Timestamp) obj; }
		 * catch(ClassCastException e1) { System.out.println(nombreParametro +
		 * "---------> deberia ser Timestamp, tipo de dato incorrecto"); obj = null;
		 * System.out.println(""); }
		 * 
		 */

		return obj;
	}

}