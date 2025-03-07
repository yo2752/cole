package general.acciones.factorias;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
//TODO MPC. Cambio IVTM. Clase nueva.
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import trafico.beans.ivtm.ModificacionIvtmBean;
import trafico.modelo.ivtm.paginacion.IVTMModeloMatriculacionPaginacion;

public class FactoriaModificacionIVTM implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new IVTMModeloMatriculacionPaginacion(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new ModificacionIvtmBean();
	}

	@Override
	public Object crearEntityModelo() {
		return new IvtmMatriculacionVO();
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
		return "ModificacionIVTM.action";
	}

}
