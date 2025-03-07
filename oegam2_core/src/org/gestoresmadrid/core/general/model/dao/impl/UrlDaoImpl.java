package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UrlDao;
import org.gestoresmadrid.core.general.model.vo.UrlVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hibernate.entities.general.AplicacionUrl;

@Repository
public class UrlDaoImpl extends GenericDaoImplHibernate<UrlVO> implements UrlDao {

	private static final long serialVersionUID = -1512585274428479527L;

	/**
	 * Devuelve el listado de entidades Url que cumplen el filtro
	 * 
	 * @param filter      Los parametros que vengan informados, se usan en el
	 *                    Criteria
	 * @param initialized Conjunto de parametros que queremos cargar para evitar
	 *                    LazzyExceptions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UrlVO> list(UrlVO filter, String... initialized) {
		List<UrlVO> listaUrl = null;

		Criteria criteria = getCurrentSession().createCriteria(UrlVO.class);
		for (String param : initialized) {
			if (param.isEmpty()) {
				continue;
			}
			criteria.setFetchMode(param, FetchMode.JOIN);
		}

		if (filter.getCodigoUrl() != null) {
			criteria.add(Restrictions.eq("codigoUrl", filter.getCodigoUrl()));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		listaUrl = criteria.list();

		return listaUrl;
	}

	/**
	 * Devuelve el listado de URLs dadas de alta en la BBDD
	 * 
	 * @return
	 */
	public List<String> listPatronUrlsSecured() {
		Criteria criteria = getCurrentSession().createCriteria(UrlVO.class);

		List<String> result = null;
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<UrlVO> listaUrl = criteria.list();

		result = new ArrayList<String>();
		for (UrlVO url : listaUrl) {
			result.add(url.getPatronUrl().trim().replaceAll("\\*", ".\\*"));
		}

		return result;
	}

	/**
	 * Devuelve el listado de URLs a las que debe tener acceso la aplicación pasada
	 * 
	 * @param codigoAplicacion codigo de la aplicación
	 * @return
	 */
	public List<String> listPatronUrlsAplicacion(String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(UrlVO.class);

		List<String> result = null;
		if (codigoAplicacion != null && !codigoAplicacion.isEmpty()) {
			criteria.createCriteria("aplicaciones").add(Restrictions.eq("codigoAplicacion", codigoAplicacion));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<UrlVO> listaUrl = criteria.list();

		result = new ArrayList<String>();
		for (UrlVO url : listaUrl) {
			if (url.getPatronUrl() != null && !url.getPatronUrl().isEmpty()) {
				result.add(url.getPatronUrl().trim().replaceAll("\\*", ".\\*"));
			}
		}

		return result;
	}

	/**
	 * Devuelve el listado de URLs a las que debe tener acceso el contratopasado
	 * 
	 * @param idContrato identificador del contrato
	 * @param idUsuario
	 * @return
	 */
	public List<String> listPatronUrlsContrato(BigDecimal idContrato, BigDecimal idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(AplicacionUrl.class);

		List<String> result = null;

		if (idContrato != null) {
			criteria.createCriteria("aplicacion").createCriteria("contratos")
					.add(Restrictions.eq("idContrato", idContrato.longValue()));
		}

		criteria.setFetchMode("funcion", FetchMode.JOIN);
		criteria.setFetchMode("url", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<AplicacionUrl> listAplicacionUrl = criteria.list();

		if (idContrato != null) {
			Criteria criteria2 = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
			criteria2.add(Restrictions.eq("id.idContrato", idContrato.longValue()));
			criteria2.add(Restrictions.or(Restrictions.eq("id.idUsuario", 0L),
					Restrictions.eq("id.idUsuario", idUsuario.longValue())));

			@SuppressWarnings("unchecked")
			List<UsuarioFuncionSinAccesoVO> listUsuarioFuncionSinAcceso = criteria2.list();

			List<AplicacionUrl> listUrlSinAcceso = new ArrayList<AplicacionUrl>();
			for (UsuarioFuncionSinAccesoVO u : listUsuarioFuncionSinAcceso) {
				for (AplicacionUrl au : listAplicacionUrl) {
					if (au.getFuncion() != null
							&& au.getFuncion().getId().getCodigoAplicacion().equals(u.getId().getCodigoAplicacion())
							&& (au.getFuncion().getId().getCodigoFuncion().equals(u.getId().getCodigoFuncion()))
							|| "0".equals(u.getId().getCodigoFuncion())) {
						listUrlSinAcceso.add(au);
						break;
					}
				}
			}

			listAplicacionUrl.removeAll(listUrlSinAcceso);

		}

		List<UrlVO> listaUrl = new ArrayList<UrlVO>();
		for (AplicacionUrl au : listAplicacionUrl) {
			UrlVO url = new UrlVO();
			url.setCodigoUrl(au.getUrl().getCodigoUrl());
			url.setOrden(au.getUrl().getOrden());
			url.setPatronUrl(au.getUrl().getPatronUrl());
			listaUrl.add(url);
		}

		// Se ordena la lista para que evalue primero las URLs menos genéricas
		Collections.sort(listaUrl, new Comparator<UrlVO>() {
			@Override
			public int compare(UrlVO o1, UrlVO o2) {
				return (o1.getOrden() != null ? o1.getOrden() : "")
						.compareTo((o2.getOrden() != null ? o2.getOrden() : ""));
			}

		});

		result = new ArrayList<String>();
		for (UrlVO url : listaUrl) {
			if (url.getPatronUrl() != null && !url.getPatronUrl().isEmpty()) {
				result.add(url.getPatronUrl().trim().replaceAll("\\*", ".\\*"));
			}
		}

		return result;
	}
}
