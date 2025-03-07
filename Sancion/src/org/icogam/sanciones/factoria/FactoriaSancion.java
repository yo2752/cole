package org.icogam.sanciones.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.icogam.sanciones.DTO.BeanCriteriosSancion;
import org.icogam.sanciones.Modelo.paginacion.ModeloPaginacionSancion;
import org.icogam.sanciones.beans.Sancion;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;

public class FactoriaSancion implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloPaginacionSancion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new BeanCriteriosSancion();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public Object crearEntityModelo() {
		return new Sancion();
	}

	@Override
	public String decorator() {
		return null;
	}

	@Override
	public String getAction() {
		return "ConsultaSancion.action";
	}

}
