package general.beans;

/**
 * Bean (pantalla) de los buscadores. Son los criterios de b�squeda que define el colegiado, o se definen como restricciones por la aplicaci�n.
 * @author ext_mpc
 *
 */
public interface BeanCriteriosSkeletonPaginatedList {

	/**
	 * Inicializa el bean, con los criterios de b�squeda que se pretenden se carguen la primera vez que se accede al buscador.
	 * @return
	 */
	public BeanCriteriosSkeletonPaginatedList iniciarBean();
	
}
