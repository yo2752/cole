package general.accionTramite.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import general.accionTramite.beans.paginacion.BeanCriteriosAccionTramite;
import general.accionTramite.modelo.paginacion.ModeloBuscadorAccionTramite;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.general.AccionTramite;

public class FactoriaAccionTramite implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloBuscadorAccionTramite(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new BeanCriteriosAccionTramite();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public Object crearEntityModelo() {
		return new AccionTramite();
	}

	@Override
	public String decorator() {
		return null;
	}

	@Override
	public String getAction() {
		return "Acciones.action";
	}

}
