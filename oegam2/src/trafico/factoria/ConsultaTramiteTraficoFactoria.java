package trafico.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.beans.paginacion.ConsultaTramiteTraficoBean;
import trafico.modelo.paginacion.ModeloConsultaTramiteTraficoPaginacion;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.trafico.TramiteTrafico;

public class ConsultaTramiteTraficoFactoria implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloConsultaTramiteTraficoPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new ConsultaTramiteTraficoBean();
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
		return "trafico.utiles.DecoradorTablasConsultaTramiteTrafico";
	}

	@Override
	public String getAction() {
		return "ConsultaTramiteTrafico.action";
	}

}
