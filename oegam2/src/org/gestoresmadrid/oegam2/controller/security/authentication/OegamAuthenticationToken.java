package org.gestoresmadrid.oegam2.controller.security.authentication;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OegamAuthenticationToken extends AbstractAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6201678170923442550L;
	private BigDecimal principal;
	private String credentials;
	private boolean cambioContrato = false;

	public OegamAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public boolean isCambioContrato() {
		return cambioContrato;
	}

	public void setCambioContrato(boolean cambioContrato) {
		this.cambioContrato = cambioContrato;
	}

}
