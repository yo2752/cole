package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoDaoImpl extends GenericDaoImplHibernate<VehiculoVO> implements VehiculoDao {

	private static final long serialVersionUID = -7876742445660725836L;

	@Override
	public VehiculoVO getVehiculo(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive,
			EstadoVehiculo estadoVehiculo) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (idVehiculo != null) {
			listCriterion.add(Restrictions.eq("idVehiculo", idVehiculo));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			listCriterion.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (matricula != null && !matricula.isEmpty()) {
			listCriterion.add(Restrictions.eq("matricula", matricula));
		}
		if (bastidor != null && !bastidor.isEmpty()) {
			listCriterion.add(Restrictions.eq("bastidor", bastidor));
		}
		if (nive != null && !nive.isEmpty()) {
			listCriterion.add(Restrictions.eq("nive", nive));
		}

		if (estadoVehiculo != null && EstadoVehiculo.Activo.getValorEnum().equals(estadoVehiculo.getValorEnum())) {
			listCriterion.add(Restrictions.or(Restrictions.eq("activo", EstadoVehiculo.Activo.getValorEnum()), Restrictions.isNull("activo")));
		} else if (estadoVehiculo != null && EstadoVehiculo.Desactivo.getValorEnum().equals(estadoVehiculo.getValorEnum())) {
			listCriterion.add(Restrictions.eq("activo", EstadoVehiculo.Desactivo.getValorEnum()));
		}

		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(DireccionVO.class, "direccion", "direccion", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(PersonaVO.class, "persona", "persona", CriteriaSpecification.LEFT_JOIN));

		List<String> orden = new ArrayList<>();
		orden.add("fechaUltmModif");

		List<VehiculoVO> lista = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, listaAlias, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// Mantis 14937. David Sierra: recupera de Vehiculos el bastidor pasado como parametro asociado al colegiado pasado como parametro
	// Query: SELECT bastidor FROM vehiculo where bastidor = ? AND num_colegiado = ? AND (activo = ? OR activo is null) and rownum <= ?;
	public List<Object[]> getBastidor(String bastidor, String colegiado) {
		List<Criterion> criterion = new ArrayList<>();
		criterion.add(Restrictions.eq("bastidor", bastidor));
		criterion.add(Restrictions.eq("numColegiado", colegiado));
		criterion.add(Restrictions.or(Restrictions.eq("activo", EstadoVehiculo.Activo.getValorEnum()), Restrictions.isNull("activo")));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.property("bastidor"));
		List<?> result = buscarPorCriteria(0, 2, criterion, null, null, null, listaProyecciones, null);

		return (List<Object[]>) result;
	}

	@Override
	public String obtenerMatriculaPorBastidor(String bastidor) {
		Criteria criteria = getCurrentSession().createCriteria(VehiculoVO.class);
		criteria.add(Restrictions.eq("bastidor", bastidor));
		criteria.add(Restrictions.or(Restrictions.eq("activo", EstadoVehiculo.Activo.getValorEnum()), Restrictions.isNull("activo")));
		criteria.addOrder(Order.desc("idVehiculo"));
		criteria.setProjection(Projections.property("matricula"));
		return (String) criteria.uniqueResult();
	}
}