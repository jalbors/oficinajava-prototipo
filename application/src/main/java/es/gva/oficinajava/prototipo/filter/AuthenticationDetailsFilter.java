/**
 * Copyright (c) 2017 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.gva.oficinajava.prototipo.filter;

import es.gva.gvlogin.sso.addon.model.SSOAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtro que establece la propiedad details de Authentication en la seguridad.
 * 
 * @author jrcasanya at http://www.disid.com[DISID Corporation S.L.]
 */
public class AuthenticationDetailsFilter extends GenericFilterBean {

	/**
	 * {@inheritDoc}
	 *
	 * Establece la propiedad details en la instancia de Authentication.
	 */
	@Override
	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse,
			FilterChain paramFilterChain) throws IOException, ServletException {

		SSOAuthenticationToken authentication = (SSOAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication.getPrincipal() == null) {
			authentication.setDetails(authentication.getDatosId().getDni());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
	}

}
