package org.gestoresmadrid.core.trafico.eeff.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.eeff.model.dao.EeffLiberacionDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class EeffLiberacionDaoImpl extends GenericDaoImplHibernate<EeffLiberacionVO> implements EeffLiberacionDao {

	private static final long serialVersionUID = 3174623379052886823L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EeffLiberacionDaoImpl.class);

	@Override
	public List<EeffLiberacionVO> getEeffLiberacionPorNumExpediente(BigDecimal numExpediente) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EeffLiberacionVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		List<EeffLiberacionVO> lista = (List<EeffLiberacionVO>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<EeffLiberacionVO> busquedaPorParametro(EeffLiberacionVO liberacion) {
		Session session;
		List<EeffLiberacionVO> listaLiberacion = null;
		try {
			session = getCurrentSession();
			Criteria criteria = session.createCriteria(EeffLiberacionVO.class);

			if (liberacion.getNumExpediente() != null) {
				criteria.add(Restrictions.eq("numExpediente", liberacion.getNumExpediente()));
			}
			if (liberacion.getTarjetaBastidor() != null) {
				criteria.add(Restrictions.eq("tarjetaBastidor", liberacion.getTarjetaBastidor()));
			}
			if (liberacion.getTarjetaNive() != null) {
				criteria.add(Restrictions.eq("tarjetaNive", liberacion.getTarjetaNive()));
			}
			if (liberacion.getFirCif() != null) {
				criteria.add(Restrictions.eq("firCif", liberacion.getFirCif()));
			}
			if (liberacion.getFirMarca() != null) {
				criteria.add(Restrictions.eq("firMarca", liberacion.getFirMarca()));
			}
			if (liberacion.getNif() != null) {
				criteria.add(Restrictions.eq("nif", liberacion.getNif()));
			}
			if (liberacion.getNumFactura() != null) {
				criteria.add(Restrictions.eq("numFactura", liberacion.getNumFactura()));
			}
			if (liberacion.getNumColegiado() != null) {
				criteria.add(Restrictions.eq("numColegiado", liberacion.getNumColegiado()));
			}
			if (liberacion.getBloque() != null) {
				criteria.add(Restrictions.eq("bloque", liberacion.getBloque()));
			}
			if (liberacion.getCodPostal() != null) {
				criteria.add(Restrictions.eq("codPostal", liberacion.getCodPostal()));
			}
			if (liberacion.getEscalera() != null) {
				criteria.add(Restrictions.eq("escalera", liberacion.getEscalera()));
			}
			if (liberacion.getFechaFactura() != null) {
				criteria.add(Restrictions.eq("fechaFactura", liberacion.getFechaFactura()));
			}
			if (liberacion.getIdDireccion() != null) {
				criteria.add(Restrictions.eq("idDireccion", liberacion.getIdDireccion()));
			}
			if (liberacion.getImporte() != null) {
				criteria.add(Restrictions.eq("importe", liberacion.getImporte()));
			}
			if (liberacion.getNumero() != null) {
				criteria.add(Restrictions.eq("numero", liberacion.getNumero()));
			}
			if (liberacion.getMunicipio() != null) {
				criteria.add(Restrictions.eq("municipio", liberacion.getMunicipio()));
			}
			if (liberacion.getNombreVia() != null) {
				criteria.add(Restrictions.eq("nombreVia", liberacion.getNombreVia()));
			}
			if (liberacion.getPiso() != null) {
				criteria.add(Restrictions.eq("piso", liberacion.getPiso()));
			}
			if (liberacion.getPrimerApellido() != null) {
				criteria.add(Restrictions.eq("primerApellido", liberacion.getPrimerApellido()));
			}
			if (liberacion.getSegundoApellido() != null) {
				criteria.add(Restrictions.eq("segundoApellido", liberacion.getSegundoApellido()));
			}
			if (liberacion.getProvincia() != null) {
				criteria.add(Restrictions.eq("provincia", liberacion.getProvincia()));
			}
			if (liberacion.getPuerta() != null) {
				criteria.add(Restrictions.eq("puerta", liberacion.getPuerta()));
			}
			if (liberacion.getTipoVia() != null) {
				criteria.add(Restrictions.eq("tipoVia", liberacion.getTipoVia()));
			}
			if (liberacion.getFechaRealizacion() != null) {
				criteria.add(Restrictions.eq("fechaRealizacion", liberacion.getFechaRealizacion()));
			}
			if (liberacion.getNombre() != null) {
				criteria.add(Restrictions.eq("nombre", liberacion.getNombre()));
			}
			listaLiberacion = criteria.list();

		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con la interfaz PaginatedList");
			log.error(e.getMessage());
			throw e;
		}
		return listaLiberacion;
	}
}