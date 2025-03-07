package org.gestoresmadrid.core.personas.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.enumerados.EstadoPersonaDireccion;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaDireccionDaoImpl extends GenericDaoImplHibernate<PersonaDireccionVO> implements PersonaDireccionDao, Serializable {

	private static final long serialVersionUID = 3010009655269368412L;

	@Override
	public List<PersonaDireccionVO> getPersonaDireccionPorNif(String numColegiado, String nif, Long idDireccion, boolean fechaFinNull) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("id.nif", nif));
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criterions.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (idDireccion != null) {
			criterions.add(Restrictions.eq("id.idDireccion", idDireccion));
		}
		if (fechaFinNull) {
			criterions.add(Restrictions.isNull("fechaFin"));
		}

		criterions.add(Restrictions.or(Restrictions.eq("estado", EstadoPersonaDireccion.Activo.getValorEnum()), Restrictions.isNull("estado")));

		List<String> orden = new ArrayList<String>();
		orden.add("fechaInicio");

		return buscarPorCriteria(criterions, GenericDaoImplHibernate.ordenDes, orden, null, null);
	}

	@Override
	public PersonaDireccionVO buscarPersonaDireccionActiva(String numColegiado, String nif) {
		Criteria criteria = getCurrentSession().createCriteria(PersonaDireccionVO.class);
		criteria.add(Restrictions.eq("id.nif", nif));
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		criteria.add(Restrictions.isNull("fechaFin"));
		criteria.add(Restrictions.or(Restrictions.eq("estado", EstadoPersonaDireccion.Activo.getValorEnum()), Restrictions.isNull("estado")));
		return (PersonaDireccionVO) criteria.uniqueResult();
	}

	@Override
	public PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();

		if (direccionVO.getNombreVia() != null && !direccionVO.getNombreVia().isEmpty()) {
			listCriterion.add(Restrictions.eq("direccion.nombreVia", direccionVO.getNombreVia()));
		}
		if (direccionVO.getIdMunicipio() != null && !direccionVO.getIdMunicipio().isEmpty()) {
			listCriterion.add(Restrictions.eq("direccion.idMunicipio", direccionVO.getIdMunicipio()));
		}
		if (direccionVO.getIdProvincia() != null && !direccionVO.getIdProvincia().isEmpty()) {
			listCriterion.add(Restrictions.eq("direccion.idProvincia", direccionVO.getIdProvincia()));
		}
		if (direccionVO.getIdDireccion() != null) {
			listCriterion.add(Restrictions.eq("direccion.idDireccion", direccionVO.getIdDireccion()));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			listCriterion.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (nif != null && !nif.isEmpty()) {
			listCriterion.add(Restrictions.eq("id.nif", nif));
		}

		listCriterion.add(Restrictions.or(Restrictions.eq("estado", EstadoPersonaDireccion.Activo.getValorEnum()), Restrictions.isNull("estado")));

		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(DireccionVO.class, "direccion", "direccion"));

		List<String> orden = new ArrayList<String>();
		orden.add("direccion.idDireccion");

		List<PersonaDireccionVO> lista = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, listaAlias, null);
		if (lista != null && !lista.isEmpty()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite) || TIPO_TRAMITE_MANTENIMIENTO.equals(tipoTramite)) {
				if (direccionVO.getPuebloCorreos() != null && !direccionVO.getPuebloCorreos().isEmpty()) {
					listCriterion.add(Restrictions.eq("direccion.puebloCorreos", direccionVO.getPuebloCorreos()));
				} else {
					listCriterion.add(Restrictions.isNull("direccion.puebloCorreos"));
				}
			}

			if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				if (direccionVO.getPueblo() != null && !direccionVO.getPueblo().isEmpty()) {
					listCriterion.add(Restrictions.eq("direccion.pueblo", direccionVO.getPueblo()));
				} else {
					listCriterion.add(Restrictions.isNull("direccion.pueblo"));
				}
			}

			List<PersonaDireccionVO> listaPueblo = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, listaAlias, null);
			if (listaPueblo != null && !listaPueblo.isEmpty()) {
				return listaPueblo.get(0);
			} else {
				return lista.get(0);
			}
		}
		return null;
	}
}
