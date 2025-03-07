package utilidades.web;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que se utiliza para guardar en sesión el listado de IRLs que tienen
 * seguridad activa, y el listado de URLs sobre las que el usuario tiene permiso
 * de acceso.
 * 
 * @author 1
 * 
 */
public class UserAuthorizations implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> securalizedUrls;
	List<String> authorizedUrls;

	public UserAuthorizations(List<String> securalizedUrls, List<String> authorizedUrls) {
		this.securalizedUrls = securalizedUrls;
		this.authorizedUrls = authorizedUrls;
	}

	/**
	 * Comprueba si el usuario tienen permiso para acceder a esta url
	 * 
	 * @param url
	 * @return
	 */
	public boolean isAllowed(String url) {
		if (isSecuralizedUrl(url) && !isAuthorizedUrl(url)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Comprueba que la URL pertenece al conjunto de URLs con seguridad activa
	 * 
	 * @param url
	 * @return
	 */
	public boolean isSecuralizedUrl(String url) {
		if (securalizedUrls != null && url != null) {
			url = url.trim();
			for (String s : securalizedUrls) {
				if (url.matches(s)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba que la URL pertenece al conjunto de URLs sobre las que el usuario tiene permisos de acceso
	 * 
	 * @param url
	 * @return
	 */
	public boolean isAuthorizedUrl(String url) {
		if (authorizedUrls != null && url != null) {
			url = url.trim();
			for (String s : authorizedUrls) {
				if (url.matches(s)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getSecuralizedUrls() {
		return securalizedUrls;
	}

	public void setSecuralizedUrls(List<String> securalizedUrls) {
		this.securalizedUrls = securalizedUrls;
	}

	public List<String> getAuthorizedUrls() {
		return authorizedUrls;
	}

	public void setAuthorizedUrls(List<String> authorizedUrls) {
		this.authorizedUrls = authorizedUrls;
	}

}
