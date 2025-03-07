package general.accionTramite.modelo.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import general.accionTramite.beans.paginacion.BeanCriteriosAccionTramite;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.general.Usuario;
import trafico.dao.implHibernate.AliasQueryBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloBuscadorAccionTramite extends ModeloSkeletonPaginatedListImpl {

	public ModeloBuscadorAccionTramite(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloBuscadorAccionTramite.class);
	
	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(Usuario.class, "usuario", "usuario"));
		return listaAlias;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		String numExpediente = ((BeanCriteriosAccionTramite)beanCriterios).getNumExpediente();
		long expediente = ContextoSpring.getInstance().getBean(Utiles.class).stringToLong(numExpediente);
		listaCriterios.add(Restrictions.eq("id.numExpediente", expediente));
		return listaCriterios;
	}

}
