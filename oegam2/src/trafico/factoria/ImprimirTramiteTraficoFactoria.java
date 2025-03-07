package trafico.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.beans.paginacion.ImprimirTramiteTraficoBean;
import trafico.decorators.ImprimirTramiteTraficoDecorator;
import trafico.modelo.paginacion.ImprimirTraficoModeloPaginacion;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.trafico.TramiteTrafico;

public class ImprimirTramiteTraficoFactoria implements AbstractFactoryPaginatedList{

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ImprimirTraficoModeloPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new ImprimirTramiteTraficoBean();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return new ConsultaTramiteTraficoFilaTablaResultadoBean();
	}

	@Override
	public Object crearEntityModelo() {
		return new TramiteTrafico();
	}

	@Override
	public String decorator() {
		return ImprimirTramiteTraficoDecorator.class.getName();
	}

	@Override
	public String getAction() {
		return "ImprimirAction.action";
	}

}
