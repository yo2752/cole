package trafico.modelo.ivtm.paginacion;
//TODO MPC. Cambio IVTM. Clase nueva.
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import trafico.beans.ivtm.ConsultaIvtmBean;
import trafico.dao.implHibernate.AliasQueryBean;
import utiles.hibernate.CriterionUtiles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class IVTMModeloConsultaPaginacion extends ModeloSkeletonPaginatedListImpl {

	public IVTMModeloConsultaPaginacion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(IVTMModeloConsultaPaginacion.class);

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
		ConsultaIvtmBean consultaIvtmBean = (ConsultaIvtmBean) beanCriterios;
		List<Criterion> criterionList = new ArrayList<>();
		if (consultaIvtmBean!=null){
			if (consultaIvtmBean.getMatricula() != null && !"".equals(consultaIvtmBean.getMatricula())) {
				criterionList.add(Restrictions.eq("matricula", consultaIvtmBean.getMatricula()));
			}
			if (consultaIvtmBean.getFechaBusqueda() != null) {
				CriterionUtiles criterionUtiles = new CriterionUtiles();
				criterionUtiles.anadirCriterioFecha(criterionList, consultaIvtmBean.getFechaBusqueda(), "fechaReq");
			}
			if (consultaIvtmBean.getNumColegiado() != null && !"".equals(consultaIvtmBean.getNumColegiado())) {
				criterionList.add(Restrictions.eq("numColegiado",consultaIvtmBean.getNumColegiado()));
			}
		}
		return criterionList;
	}

}