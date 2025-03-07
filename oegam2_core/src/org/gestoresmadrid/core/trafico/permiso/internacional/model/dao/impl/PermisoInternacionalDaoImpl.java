package org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.PermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class PermisoInternacionalDaoImpl extends GenericDaoImplHibernate<PermisoInternacionalVO> implements PermisoInternacionalDao {

	private static final long serialVersionUID = -5098872165229511923L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@SuppressWarnings("unchecked")
	@Override
	public List<PermisoInternacionalVO> getListaPermisosDoiPorEstado(String doiTitular, String estado) {
		Criteria criteria = getCurrentSession().createCriteria(PermisoInternacionalVO.class);
		criteria.add(Restrictions.eq("doiTitular", doiTitular));
		criteria.add(Restrictions.eq("estado", estado));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public PermisoInternacionalVO getPermisoInternacional(Long idPermiso) {
		Criteria criteria = getCurrentSession().createCriteria(PermisoInternacionalVO.class);
		criteria.add(Restrictions.eq("idPermiso", idPermiso));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites", "contratocorreosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite", "contratocorreosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite.aplicacion", "contratocorreosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return (PermisoInternacionalVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PermisoInternacionalVO> consultarPermisosInternacionales() {
		Criteria criteria = getCurrentSession().createCriteria(PermisoInternacionalVO.class);
		criteria.add(Restrictions.or(Restrictions.eq("estado", EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum()), Restrictions.eq("estado",
				EstadoTramitesInterga.Finalizado_Pdt_PDF.getValorEnum())));

		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public PermisoInternacionalVO getPermisoInternacionalPorExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(PermisoInternacionalVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return (PermisoInternacionalVO) criteria.uniqueResult();
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String numExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		numExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFinDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);

		Criteria criteria = getCurrentSession().createCriteria(PermisoInternacionalVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(numExpediente + "000");
		}
		maximoExistente = new BigDecimal(maximoExistente.longValue() + 1);
		return maximoExistente;
	}

}
