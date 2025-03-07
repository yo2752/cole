package trafico.evolucion.modelo.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.Utiles;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.general.Usuario;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.evolucion.beans.paginacion.CriteriosBusquedaEvolucionTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloEvolucionTramiteTraficoPaginacion extends ModeloSkeletonPaginatedListImpl {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloEvolucionTramiteTraficoPaginacion.class);
	
	public ModeloEvolucionTramiteTraficoPaginacion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Autowired
	Utiles utiles;

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
		if (beanCriterios!=null){
			CriteriosBusquedaEvolucionTramiteTrafico criterios = (CriteriosBusquedaEvolucionTramiteTrafico) beanCriterios;
			if (criterios!=null){
				if (criterios.getNumExpediente()!=null && !criterios.getNumExpediente().equals("")){
					String numExpediente = criterios.getNumExpediente();
					long expediente = utiles.stringToLong(numExpediente);
					listaCriterios.add(Restrictions.eq("id.numExpediente", expediente));
				}
			}
		}
		return listaCriterios;
	}

}
