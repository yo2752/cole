package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.DuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class DuplicadoPermisoConducirDaoImpl extends GenericDaoImplHibernate<DuplicadoPermisoConducirVO> implements DuplicadoPermisoConducirDao {

	private static final long serialVersionUID = 1954904174417218759L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public DuplicadoPermisoConducirVO getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond) {
		Criteria criteria = getCurrentSession().createCriteria(DuplicadoPermisoConducirVO.class);
		criteria.add(Restrictions.eq("idDuplicadoPermCond", idDuplicadoPermisoCond));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return (DuplicadoPermisoConducirVO) criteria.uniqueResult();
	}

	@Override
	public DuplicadoPermisoConducirVO getDuplPermCondPorNumExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(DuplicadoPermisoConducirVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return (DuplicadoPermisoConducirVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DuplicadoPermisoConducirVO> getListaDuplicadoPermisosCondDoiPorEstado(String doiTitular, String estado) {
		Criteria criteria = getCurrentSession().createCriteria(DuplicadoPermisoConducirVO.class);
		criteria.add(Restrictions.eq("doiTitular", doiTitular));
		criteria.add(Restrictions.eq("estado", estado));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String numExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		numExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFinDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);

		Criteria criteria = getCurrentSession().createCriteria(DuplicadoPermisoConducirVO.class);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<DuplicadoPermisoConducirVO> consultarDuplicadosPermConducir() {
		Criteria criteria = getCurrentSession().createCriteria(DuplicadoPermisoConducirVO.class);
		criteria.add(Restrictions.or(Restrictions.eq("estado", EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum()), Restrictions.eq("estado", EstadoTramitesInterga.Finalizado_Pdt_PDF
				.getValorEnum())));

		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites", "contratocorreosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite", "contratocorreosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite.aplicacion", "contratocorreosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
