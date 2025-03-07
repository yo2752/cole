package trafico.evolucion.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.evolucion.beans.paginacion.CriteriosBusquedaEvolucionTramiteTrafico;
import trafico.evolucion.modelo.paginacion.ModeloEvolucionTramiteTraficoPaginacion;
import trafico.evolucion.resultTransform.ResultTransformEvoulucionTramiteTrafico;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.trafico.EvolucionTramiteTrafico;

public class FactoriaEvolucionTramiteTrafico implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloEvolucionTramiteTraficoPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new CriteriosBusquedaEvolucionTramiteTrafico();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return new ResultTransformEvoulucionTramiteTrafico();
	}

	@Override
	public Object crearEntityModelo() {
		return new EvolucionTramiteTrafico();
	}

	@Override
	public String decorator() {
		return null;
	}

	@Override
	public String getAction() {
		return "EvolucionTramiteTrafico.action";
	}

}
