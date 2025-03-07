package grupoUsuarios.modelo.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import grupoUsuarios.beans.paginacion.GruposUsuariosBean;
import trafico.dao.implHibernate.AliasQueryBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloGrupoUsuariosImplPag extends ModeloSkeletonPaginatedListImpl {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloGrupoUsuariosImplPag.class);

	public ModeloGrupoUsuariosImplPag(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(
			BeanCriteriosSkeletonPaginatedList beanCriterios) {
		return null;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		List<Criterion> criterion = null;
		if (beanCriterios != null) {
			criterion = createCriterion((GruposUsuariosBean)beanCriterios);
		}
		return criterion;
	}

	public List<Criterion> createCriterion(GruposUsuariosBean gruposUsuariosBean) {
		return new ArrayList<>();
	}

}