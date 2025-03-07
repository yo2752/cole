package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.MarcaDgtDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MarcaDgtDaoImpl extends GenericDaoImplHibernate<MarcaDgtVO> implements MarcaDgtDao {

	private static final long serialVersionUID = -5624899939217369998L;

	private Long VERSION_0 = 0L;
	private Long VERSION_1 = 1L;
	private Long VERSION_2 = 2L;

	@Override
	public MarcaDgtVO getMarcaDgtCodigo(Long codigoMarca) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		if (codigoMarca != null) {
			criteria.add(Restrictions.eq("codigoMarca", codigoMarca));
		}
		return (MarcaDgtVO)criteria.uniqueResult();
	}

	@Override
	public MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		ArrayList<Long> versiones = new ArrayList<Long>();
		if (codigoMarca != null && !"".equals(codigoMarca)) {
			criteria.add(Restrictions.eq("codigoMarca", new Long(codigoMarca)));
		}
		if (marca != null && !"".equals(marca)) {
			criteria.add(Restrictions.or(Restrictions.eq("marcaSinEditar", marca.toUpperCase().trim()), Restrictions.eq("marca", marca.toUpperCase().trim())));
		}
		if (versionMatw != null) {
			if (!versionMatw) {
				versiones.add(VERSION_0);
				versiones.add(VERSION_2);
				criteria.add(Restrictions.in("version", versiones));
			} else if (versionMatw) {
				versiones.add(VERSION_1);
				versiones.add(VERSION_2);
				criteria.add(Restrictions.in("version", versiones));
			}
		}

		@SuppressWarnings("unchecked")
		List<MarcaDgtVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<MarcaDgtVO> listaMarcas(String nombreMarca, Boolean versionMatw) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		ArrayList<Long> versiones = new ArrayList<Long>();
		if (nombreMarca != null && !"".equals(nombreMarca)) {
			criteria.add(Restrictions.like("marca", nombreMarca.toUpperCase() + "%"));
		}
		if (versionMatw != null) {
			if (!versionMatw) {
				versiones.add(VERSION_0);
				versiones.add(VERSION_2);
				criteria.add(Restrictions.in("version", versiones));
			} else if (versionMatw) {
				versiones.add(VERSION_1);
				versiones.add(VERSION_2);
				criteria.add(Restrictions.in("version", versiones));
			}
		}

		@SuppressWarnings("unchecked")
		List<MarcaDgtVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

	private String formatearMarcaaMarcaSinEditar(String marca){
		String marcaSinEditar = new String();
		marcaSinEditar = marca.replace(" ", "" )
		.replace("&", "" )
		.replace("'", "" )
		.replace("(", "" )
		.replace(")", "" )
		.replace("+", "" )
		.replace(",", "" )
		.replace("-", "" )
		.replace(".", "" )
		.replace("/", "" )
		.replace(";", "" )
		.replace("=", "" )
		.replace("Ä", "" )
		.replace("Ö", "" )
		.replace("Ü", "" )
		.replace("Ñ", "" )
		.replace("Ë", "" )
		.replace("Ï", "" );

		return marcaSinEditar;
	}

	@Override
	public MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, ArrayList<Integer> version) {
		String marcaSinEditar = new String();

		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		ArrayList<Long> versiones = new ArrayList<>();
		if (codigoMarca != null && !"".equals(codigoMarca)) {
			criteria.add(Restrictions.eq("codigoMarca", new Long(codigoMarca)));
		}

		marcaSinEditar = formatearMarcaaMarcaSinEditar(marca);

		if (marca != null && !"".equals(marca)) {
			criteria.add(Restrictions.or(Restrictions.eq("marcaSinEditar", marcaSinEditar), Restrictions.eq("marca", marca.toUpperCase().trim())));
		}

		for(int i = 0; i < version.size(); i++){
			switch(version.get(i)){
				case 0:
					versiones.add(0L);
					break;
				case 1:
					versiones.add(1L);
					break;
				case 2:
					versiones.add(2L);
					break;
			}
			criteria.add(Restrictions.in("version", versiones));
		}

		@SuppressWarnings("unchecked")
		List<MarcaDgtVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<MarcaDgtVO> getMarcaDgt(String marca) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		criteria.add(Restrictions.eq("marca", marca));
		@SuppressWarnings("unchecked")
		List<MarcaDgtVO> lista = criteria.list();
		return lista;
	}

	@Override
	public Long getCodigoFromMarca(String marca) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);
		criteria.add(Restrictions.eq("marca", marca));
		@SuppressWarnings("unchecked")
		List<MarcaDgtVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0).getCodigoMarca();
		}
		return null;
	}

}