package org.gestoresmadrid.oegam2.controller.security.authentication;

import hibernate.dao.general.UsuarioDAO;
import hibernate.entities.general.SecGrupo;
import hibernate.entities.general.SecPermiso;
import hibernate.entities.general.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import trafico.utiles.constantes.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Implementación de OEGAM del authenticationProvider de Spring-Security, que
 * soporta OegamAuthenticationToken. Mediante el NIF recuperado por viafirma,
 * recupera el usuario de BBDD con su lista de ROLES
 * 
 * @author erds
 * 
 */
public class OegamAuthenticationProvider implements AuthenticationProvider {
	private static ILoggerOegam log = LoggerOegam.getLogger(OegamAuthenticationProvider.class);

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication result = null;
		if (authentication != null) {

			// Buscar usuario activo
			Usuario usuario = getUsuario(authentication);

			if (usuario != null) {

				// Se monta el AuthenticationToken
				OegamAuthenticationToken oegamAuthentication = new OegamAuthenticationToken(getAuthorities(usuario));
				oegamAuthentication.setPrincipal(new BigDecimal(usuario.getIdUsuario()));
				oegamAuthentication.setCredentials(usuario.getNif());
				oegamAuthentication.setDetails(new WebAuthenticationDetails(ServletActionContext.getRequest()));
				oegamAuthentication.setAuthenticated(true);

				result = oegamAuthentication;

				log.debug("Loggin with principal: " + authentication.getPrincipal());
				log.debug("Logged with details: " + oegamAuthentication.getDetails());
			}
		}
		return result;
	}

	/**
	 * Busca el usuario activo (si existe) con el NIF pasado en las credenciales
	 * del token authentication
	 * 
	 * @param authentication
	 * @return
	 */
	private Usuario getUsuario(Authentication authentication) {
		Usuario usuario = new Usuario();
		usuario.setNif(authentication.getCredentials().toString());
		usuario.setEstadoUsuario(new BigDecimal(Constantes.ESTADO_USUARIO_HABILITADO));
		List<Usuario> list = usuarioDAO.getListUsuarios(usuario, "secPermisos", "secGrupos.secPermisos");
		if (list != null && !list.isEmpty()) {
			usuario = list.get(0);
		} else {
			usuario = null;
		}
		return usuario;
	}

	/**
	 * Monta la lista de ROLES/PERMISOS del usuario. Si es vacia devuele null
	 * 
	 * @param usuario
	 * @return
	 */
	private List<GrantedAuthorityImpl> getAuthorities(Usuario usuario) {
		List<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>();
		for (SecPermiso permiso : usuario.getSecPermisos()) {
			if (permiso.getInactivo() == null || !permiso.getInactivo()) {
				authorities.add(new GrantedAuthorityImpl(permiso.getNombre()));
			}
		}
		for (SecGrupo grupo : usuario.getSecGrupos()) {
			for (SecPermiso permiso : grupo.getSecPermisos()) {
				if (permiso.getInactivo() == null || !permiso.getInactivo()) {
					authorities.add(new GrantedAuthorityImpl(permiso.getNombre()));
				}
			}
		}
		return authorities.isEmpty() ? null : authorities;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return authentication != null && authentication.equals(OegamAuthenticationToken.class);
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
