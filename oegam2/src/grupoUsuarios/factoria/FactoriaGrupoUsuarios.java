package grupoUsuarios.factoria;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;
import grupoUsuarios.beans.paginacion.GruposUsuariosBean;
import grupoUsuarios.modelo.Impl.ModeloGrupoUsuariosImplPag;
import hibernate.entities.general.Grupo;

public class FactoriaGrupoUsuarios implements AbstractFactoryPaginatedList {

	@Override
	public ModeloSkeletonPaginatedList crearModelo() {
		return new ModeloGrupoUsuariosImplPag(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList crearCriterios() {
		return new GruposUsuariosBean();
	}

	@Override
	public BeanResultTransformPaginatedList crearTransformer() {
		return null;
	}

	@Override
	public Object crearEntityModelo() {
		return new Grupo();
	}

	@Override
	public String decorator() {
		return "";
	}

	@Override
	public String getAction() {
		return "GrupoUsuarios.action";
	}

}
