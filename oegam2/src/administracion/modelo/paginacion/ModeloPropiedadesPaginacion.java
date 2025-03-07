package administracion.modelo.paginacion;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.general.PropertiesContext;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import trafico.dao.implHibernate.AliasQueryBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import administracion.beans.paginacion.BeanCriteriosPropiedades;

public class ModeloPropiedadesPaginacion extends ModeloSkeletonPaginatedListImpl {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloPropiedadesPaginacion.class);

	public ModeloPropiedadesPaginacion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(PropertiesContext.class, "propertiesContext", "propertiesContext"));
		return listaAlias;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		BeanCriteriosPropiedades criterios = (BeanCriteriosPropiedades) beanCriterios;
		List<Criterion> listaCriterion = new ArrayList<>();
		if (criterios != null) {
			listaCriterion.add(Restrictions.eq("propertiesContext.identificador", criterios.getEntorno()));
			if (criterios.getNombreFormBusquedaPropiedades() != null && !criterios.getNombreFormBusquedaPropiedades().equals("")) {
				listaCriterion.add(Restrictions.ilike("nombre", "%" + criterios.getNombreFormBusquedaPropiedades() + "%"));
			}

			if (criterios.getValorFormBusquedaPropiedades() != null && !criterios.getValorFormBusquedaPropiedades().equals("")) {
				listaCriterion.add(Restrictions.ilike("valor", "%" + criterios.getValorFormBusquedaPropiedades() + "%"));
			}
		}
		return listaCriterion;
	}

}