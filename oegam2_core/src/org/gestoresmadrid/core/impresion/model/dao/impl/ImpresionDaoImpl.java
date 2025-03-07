package org.gestoresmadrid.core.impresion.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.dao.ImpresionDao;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImpresionDaoImpl extends GenericDaoImplHibernate<ImpresionVO> implements ImpresionDao {

	private static final long serialVersionUID = 3971270027237485115L;

	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ImpresionVO getImpresion(Long idImpresion) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionVO.class);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.colegio", "colegio");
		criteria.createAlias("contrato.colegiado.usuario", "usuario");
		criteria.add(Restrictions.eq("idImpresion", idImpresion));
		return (ImpresionVO) criteria.uniqueResult();
	}

	@Override
	public ImpresionVO getImpresionPorNombreDocumento(String nombreDocumento) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionVO.class);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("nombreDocumento", nombreDocumento));
		criteria.add(Restrictions.ne("estado", EstadosImprimir.Eliminado.getValorEnum()));
		criteria.addOrder(Order.desc("fechaCreacion"));
		@SuppressWarnings("unchecked")
		List<ImpresionVO> list = (List<ImpresionVO>) criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ImpresionVO getImpresionPorNombreDocumentoConEstados(String nombreDocumento, List<String> estados) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionVO.class);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("nombreDocumento", nombreDocumento));
		if (estados != null && !estados.isEmpty()) {
			criteria.add(Restrictions.in("estado", estados));
		}
		criteria.addOrder(Order.desc("idImpresion"));
		@SuppressWarnings("unchecked")
		List<ImpresionVO> list = (List<ImpresionVO>) criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int numeroElementos(String nombreDocumento, String tipoDocumento, String estado, Long idContrato, Date fechaDesde, Date fechaHasta, String tipoTramite, BigDecimal numExpediente) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionVO.class);

		if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
			criteria.add(Restrictions.eq("nombreDocumento", nombreDocumento));
		}
		
		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}

		if (tipoDocumento != null && !tipoDocumento.isEmpty()) {
			criteria.add(Restrictions.eq("tipoDocumento", tipoDocumento));
		} else {
			criteria.add(Restrictions.ne("tipoDocumento", TipoDocumentoImpresiones.DocumentoBase.getValorEnum()));
			criteria.add(Restrictions.ne("tipoDocumento", TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.getValorEnum()));
			criteria.add(Restrictions.ne("tipoDocumento", TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getValorEnum()));
			criteria.add(Restrictions.ne("tipoDocumento", TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.getValorEnum()));
			criteria.add(Restrictions.ne("tipoDocumento", TipoDocumentoImpresiones.SolicitudPermisoInter.getValorEnum()));
		}

		if (estado != null && !estado.isEmpty()) {
			criteria.add(Restrictions.eq("estado", estado));
		}

		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}

		if (fechaDesde != null) {
			criteria.add(Restrictions.ge("fechaCreacion", fechaDesde));
		}

		if (fechaHasta != null) {
			utilesFecha.setHoraEnDate(fechaHasta, horaFInDia);
			utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaCreacion", fechaHasta));
		}

		if (numExpediente != null) {
			criteria.createAlias("impresionTramites", "impresionTramites", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("impresionTramites.numExpediente", numExpediente));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImpresionVO> buscar(String nombreDocumento, String tipoDocumento, String estado, Long idContrato, Date fechaDesde, Date fechaHasta, String tipoTramite, BigDecimal numExpediente, int firstResult,
			int maxResult) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionVO.class);

		if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
			criteria.add(Restrictions.eq("nombreDocumento", nombreDocumento));
		}
		
		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}

		if (tipoDocumento != null && !tipoDocumento.isEmpty()) {
			criteria.add(Restrictions.eq("tipoDocumento", tipoDocumento));
		} else {
			ArrayList<String> tiposDocDescartgados = new ArrayList<String>();
			tiposDocDescartgados.add(TipoDocumentoImpresiones.DocumentoBase.getValorEnum());
			tiposDocDescartgados.add(TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.getValorEnum());
			tiposDocDescartgados.add(TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getValorEnum());
			tiposDocDescartgados.add(TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.getValorEnum());
			criteria.add(Restrictions.not(Restrictions.in("tipoDocumento", tiposDocDescartgados)));
		}

		if (estado != null && !estado.isEmpty()) {
			criteria.add(Restrictions.eq("estado", estado));
		}

		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}

		if (fechaDesde != null) {
			criteria.add(Restrictions.ge("fechaCreacion", fechaDesde));
		}

		if (fechaHasta != null) {
			utilesFecha.setHoraEnDate(fechaHasta, horaFInDia);
			utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaCreacion", fechaHasta));
		}

		if (numExpediente != null) {
			criteria.createAlias("impresionTramites", "impresionTramites", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("impresionTramites.numExpediente", numExpediente));
		}

		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResult > 0) {
			criteria.setMaxResults(maxResult);
		}

		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("idImpresion"));
		return (List<ImpresionVO>) criteria.list();
	}
}
