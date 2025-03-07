package org.gestoresmadrid.core.springContext;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Clase singleton para recuperar el contexto de Spring y poder inyectar objetos desde clases que no tienen Spring
 * en sus clases padre
 * @author ext_jrg
 *
 */
public class ContextoSpring implements ApplicationContextAware {

	private static ContextoSpring contextoSpring;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ContextoSpring.class);

	private static final String CARGAR_CONTEXTO_SPRING = "cargar.contexto.spring";

	private ContextoSpring(){
		super();
	}

	/**
	 * Método que devuelve instancia del contexto de Spring
	 * Si ya fue instanciado devuelve ese. Si no, lo instancia
	 *  
	 * @return SpringContext
	 */
	public static ContextoSpring getInstance(){
		
		if (contextoSpring==null){
			contextoSpring = new ContextoSpring();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(contextoSpring);
		}
		return contextoSpring;
	}

	/**
	 * Método que devuelve la instancia del bean con el nombre indicado
	 * 
	 * @param beanName
	 *            el nombre del bean para recuperar
	 * @return una instancia del bean
	 * 
	 * @see ApplicationContext#getBean(String)
	 */
	public Object getBean(String beanName){
		
		if (applicationContext!=null){
			try {
				return applicationContext.getBean(beanName);
			} catch (BeansException e) {
				LOG.error("No se ha encontrado el bean " + beanName, e);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve la instancia del bean con nombre y tipo pasados por
	 * parametros
	 * 
	 * @param beanName
	 *            el nombre del bean para recuperar
	 * @param requiredType
	 *            Clase o interfaz del bean, propia o heredada. Si no se indica
	 *            puede ser null
	 * @return una instancia del bean
	 * 
	 * @see ApplicationContext#getBean(String, Class)
	 */
	public <T> T getBean(String beanName, Class<T> requiredType){
		
		if (applicationContext!=null){
			try {
				return applicationContext.getBean(beanName, requiredType);
			} catch (BeansException e) {
				LOG.error("No se ha encontrado el bean " + beanName + " de tipo "
							+ requiredType != null ? requiredType.getCanonicalName() : "cualquiera", e);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve la instancia del bean del tipo indicado
	 * 
	 * @param requiredType
	 *            Clase o interfaz del bean, propia o heredada
	 * @return una instancia del bean
	 * 
	 * @see ApplicationContext#getBean(String)
	 */
	public <T> T getBean(Class<T> requiredType){
		
		if (applicationContext!=null){
			try {
				return applicationContext.getBean(requiredType);
			} catch (BeansException e) {
				LOG.error("No se ha encontrado el bean de tipo " + requiredType.getCanonicalName(), e);
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
}
