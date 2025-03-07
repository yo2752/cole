package general.modelo;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.modelo.impl.PaginatedListImpl;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Una implementación del buscador de los paginadores.
 * @author ext_mpc
 *
 */
public abstract class ModeloSkeletonPaginatedListImpl implements ModeloSkeletonPaginatedList{

	/**
	 * Factoria concreta que utiliza este buscador
	 */
	protected AbstractFactoryPaginatedList factoria;
	
	/**
	 * Hora final del día, utiliza para la búsqueda por fechas.
	 */
	public static final String horaFInDia = "23:59";
	
	/**
	 * Segundos finales del día, utilizado para las búsquedas por fechas. Es un complemento del campo horaFInDia
	 */
	public static final int N_SEGUNDOS = 59;
	
	/**
	 * Constructor privado, para forzar que se indique la factoría.
	 */
	private ModeloSkeletonPaginatedListImpl(){}
	
	/**
	 * Constructor con la factoría a utilizar.
	 * @param factoria
	 */
	protected ModeloSkeletonPaginatedListImpl(AbstractFactoryPaginatedList factoria){
		setFactoria(factoria);
	}
	
	/**
	 * Genera el paginatedList con los criterios de búsqueda indicados.
	 */
	@Override
	public PaginatedList buscarPag(BeanCriteriosSkeletonPaginatedList beanCriterios, int resPag, int page, String dir, String sort) throws OegamExcepcion {
		try{
			List<Criterion> criterion = null;
			List<AliasQueryBean> listaAlias = null;
			ProjectionList listaProyecciones = null;
			BeanResultTransformPaginatedList resultTransform = getFactoria().crearTransformer();
			listaProyecciones = crearListaProyecciones(resultTransform);
			Object entity = getFactoria().crearEntityModelo();
			criterion =crearCriteriosBusqueda(beanCriterios);
			listaAlias = crearListaAlias(beanCriterios);
			return getPaginatedList(entity,resPag,page,criterion,dir, sort,listaAlias,listaProyecciones, resultTransform);
		}catch(HibernateException e){
			 getLog().error("Un error ha ocurrido al hacer una búsqueda por criterios con la interfaz PaginatedList de "+getClass());
	         getLog().error(e.getMessage());
	         throw e;			
		}
	}

	/**
	 * Devuelve el paginatedList a partir de los criterios de búsqueda.
	 * @param entity
	 * @param resPag
	 * @param page
	 * @param criterion
	 * @param dir
	 * @param sort
	 * @param listaAlias
	 * @param listaProyecciones
	 * @param resultTransform
	 * @return
	 */
	protected PaginatedList getPaginatedList(Object entity, int resPag, int page, List<Criterion> criterion, String dir, String sort, List<AliasQueryBean> listaAlias, ProjectionList listaProyecciones, BeanResultTransformPaginatedList resultTransform) {
		return new PaginatedListImpl(getConsultaInicial(), entity,resPag,page,criterion,dir, sort,listaAlias,listaProyecciones, resultTransform);
	}

	/**
	 * Indica si la búsqueda se realizará al generar el paginatedlist, o cuando se necesiten datos del modelo
	 * @return
	 */
	protected boolean getConsultaInicial() {
		return false;
	}

	protected AbstractFactoryPaginatedList getFactoria(){
		return factoria;	
	}
	
	protected void setFactoria (AbstractFactoryPaginatedList factoria){
		this.factoria = factoria;
	}
	
	protected abstract ILoggerOegam getLog();

	/**
	 * Genera los alias utilizados para realizar la búsqueda, según el GenericDao.
	 * @param beanCriterios
	 * @return
	 */
	protected abstract List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios);

	/**
	 * Genera los criterios utilizados para realizar la búsqueda, según el GenericDao, y los criterios del búscados.
	 * Es una conversión de los criterios de busquedado (bena) a criterios de hibernate.
	 * @param beanCriterios
	 * @return
	 */
	protected abstract List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios);

	/**
	 * Crea las proyecciones, según el genericDao, para no obtener todo el entity.
	 * @param consultaTramiteTraficoFilaTablaResultadoBean
	 * @return
	 */
	protected ProjectionList crearListaProyecciones(BeanResultTransformPaginatedList consultaTramiteTraficoFilaTablaResultadoBean){
		if (consultaTramiteTraficoFilaTablaResultadoBean==null){
			return null;
		}
		Vector<String> proyecciones = consultaTramiteTraficoFilaTablaResultadoBean.crearProyecciones();
		return crearListaProyecciones(proyecciones);
	}
	
	/**
	 * Genera las proyecciones, según se necesitan en el GenericDao, a partir de una lista de atributos
	 * @param proyecciones
	 * @return
	 */
	protected ProjectionList crearListaProyecciones(Vector<String> proyecciones){
		ProjectionList lista = Projections.projectionList();
		for (String campo: proyecciones){
			lista.add(Projections.property(campo));
		}
		return lista;
	}
	
	/**
	 * Genera un criterio de fecha, a partir de los criterios del bean.
	 * @param criterionList
	 * @param fecha 
	 * @param tipo Campo del entity sobre el que filtra por fecha.
	 */
	protected void anadirCriterioFecha(List<Criterion> criterionList, FechaFraccionada fecha, String tipo){
		if (fecha!=null){
			Date fechaIni = fecha.getFechaMinInicio();
			Date fechaFin = fecha.getFechaMaxFin();
			if (!fecha.isfechaInicioNula() && !fecha.isfechaFinNula()) {
				Criterion rest2 = Restrictions.between(tipo, fechaIni, fechaFin);
				criterionList.add(rest2);
			} else if (!fecha.isfechaInicioNula()){
				Criterion rest2 = Restrictions.ge(tipo, fechaIni);
				criterionList.add(rest2);
			} else if (!fecha.isfechaFinNula()){
				Criterion rest2 = Restrictions.le(tipo, fechaFin);
				criterionList.add(rest2);
			}
		}
	}

}
