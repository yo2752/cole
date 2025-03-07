package general.acciones.factorias;
//TODO MPC. Cambio IVTM. Clase nueva.

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.beans.ivtm.ConsultaIvtmBean;
import trafico.modelo.ivtm.paginacion.IVTMModeloConsultaPaginacion;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import hibernate.entities.trafico.IvtmConsultaMatriculacion;

public class FactoriaConsultaIVTM implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new IVTMModeloConsultaPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new ConsultaIvtmBean();
	}

	@Override
	public Object crearEntityModelo() {
		return new IvtmConsultaMatriculacion();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public String decorator() {
		return null;
	}

	@Override
	public String getAction() {
		return "ConsultaIVTM.action";
	}

}