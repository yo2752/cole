package administracion.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import administracion.beans.paginacion.BeanCriteriosPropiedades;
import administracion.modelo.paginacion.ModeloPropiedadesPaginacion;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.general.Property;

public class FactoriaPropiedades implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloPropiedadesPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new BeanCriteriosPropiedades();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public Object crearEntityModelo() {
		return new Property();
	}

	@Override
	public String decorator() {
		return "administracion.decorators.PropiedadesDecorator";
	}

	@Override
	public String getAction() {
		return "Propiedades.action";
	}

}
