package hibernate.dao.general;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.general.PropertiesContext;
import hibernate.entities.general.Property;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;

public class PropertyDaoImpl extends GenericDaoImplHibernate<Property> {

	public PropertyDaoImpl() {
		super(new Property());
	}

	public List<Property> load(String context) throws Throwable {
		List<Criterion> listaCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(PropertiesContext.class, "propertiesContext", "propertiesContext"));
		listaCriterion.add(Restrictions.eq("propertiesContext.identificador", context));
		return buscarPorCriteria(listaCriterion, listaAlias, null);
	}

}