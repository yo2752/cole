package org.icogam.legalizacion.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.icogam.legalizacion.Modelo.paginacion.ModeloLegalizacionImplPag;
import org.icogam.legalizacion.beans.LegalizacionCita;
import org.icogam.legalizacion.beans.paginacion.LegalizacionBean;
import org.icogam.legalizacion.decorator.DecoratorLegalizacion;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;

public class FactoriaLegalizacion implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloLegalizacionImplPag(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new LegalizacionBean();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public Object crearEntityModelo() {
		return new LegalizacionCita();
	}

	@Override
	public String decorator() {
		return DecoratorLegalizacion.class.getName();
	}

	@Override
	public String getAction() {
		return "ConsultaLegalizacion.action";
	}

	
	
	
	
}
