package trafico.modelo.paginacion;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.Vehiculo;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;

import trafico.beans.paginacion.ImprimirTramiteTraficoBean;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.modelo.impl.PaginatedListImplTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImprimirTraficoModeloPaginacion extends ModeloSkeletonPaginatedListImpl{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirTraficoModeloPaginacion.class);

	public ImprimirTraficoModeloPaginacion(AbstractFactoryPaginatedList factoria){
		super(factoria);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected PaginatedList getPaginatedList(Object entity, int resPag, int page, List<Criterion> criterion, String dir, String sort, List<AliasQueryBean> listaAlias, ProjectionList listaProyecciones, BeanResultTransformPaginatedList resultTransform) {
		return new PaginatedListImplTramiteTrafico(getConsultaInicial(), entity,resPag,page,criterion,dir, sort,listaAlias,listaProyecciones, resultTransform);
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		ArrayList<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(Vehiculo.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(Tasa.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		List<Criterion> listaCriterion = new ArrayList<>();
		Long[] numsExpedientes = new Long[((ImprimirTramiteTraficoBean)beanCriterios).getNumExpedientes().length];
		for(int i=0;i<numsExpedientes.length;i++){
			numsExpedientes[i]=new Long(((ImprimirTramiteTraficoBean)beanCriterios).getNumExpedientes()[i]);
		}
		Criterion c = Restrictions.in("numExpediente", numsExpedientes);
		listaCriterion.add(c);
		return listaCriterion;
	}

}