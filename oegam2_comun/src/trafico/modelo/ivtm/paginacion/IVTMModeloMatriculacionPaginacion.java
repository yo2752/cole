package trafico.modelo.ivtm.paginacion;
//TODO MPC. Cambio IVTM. Clase nueva
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.personas.Direccion;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TramiteTrafico;
import trafico.beans.ivtm.ModificacionIvtmBean;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.utiles.constantes.ConstantesIVTM;
import utiles.hibernate.CriterionUtiles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class IVTMModeloMatriculacionPaginacion extends ModeloSkeletonPaginatedListImpl{

	public IVTMModeloMatriculacionPaginacion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(IVTMModeloMatriculacionPaginacion.class);

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		return null;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		ModificacionIvtmBean bean = (ModificacionIvtmBean) beanCriterios;
		List<Criterion> listCriterion= new ArrayList<Criterion>();
		if (bean!=null){
			if (bean.getNumExpediente()!=null && !"".equals(bean.getNumExpediente())){
				listCriterion.add(Restrictions.eq("numExpediente", bean.getNumExpediente()));
			}

			if (bean.getAutoliquidacion()!=null && !"".equals(bean.getAutoliquidacion())){
				listCriterion.add(Restrictions.eq("nrc", bean.getAutoliquidacion()));
			} else {
				listCriterion.add(Restrictions.isNotNull("nrc"));
			}

			if (bean.getNumColegiado()!=null && !"".equals(bean.getNumColegiado())){
				DetachedCriteria subquery = DetachedCriteria.forClass(TramiteTrafico.class)
						.add(Restrictions.eq("numColegiado", bean.getNumColegiado()))
						.setProjection(Projections.property("numExpediente"));
				listCriterion.add(Subqueries.propertyIn("numExpediente", subquery));
			}

			if (bean.getFechaBusqueda()!=null){
				CriterionUtiles criterionUtiles = new CriterionUtiles();
				criterionUtiles.anadirCriterioFecha(listCriterion, bean.getFechaBusqueda(), "fechaPago");
			}

			DetachedCriteria subquery2 = DetachedCriteria.forClass(Direccion.class)
					.add(Restrictions.eq("municipio.id.idProvincia", ConstantesIVTM.PROVINCIA_MADRID))
					.add(Restrictions.eq("municipio.id.idMunicipio", ConstantesIVTM.MUNICIPIO_MADRID))
					.setProjection(Projections.property("idDireccion"));

			DetachedCriteria subquery = DetachedCriteria.forClass(IntervinienteTrafico.class)
					.add(Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()))
					.add(Subqueries.propertyIn("personaDireccion.id.idDireccion", subquery2))
					.setProjection(Projections.property("id.numExpediente"));
			listCriterion.add(Subqueries.propertyIn("numExpediente", subquery));
		}
		return listCriterion;
	}

}