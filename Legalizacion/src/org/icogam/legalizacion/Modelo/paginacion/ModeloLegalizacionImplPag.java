package org.icogam.legalizacion.Modelo.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.icogam.legalizacion.ModeloImpl.ModeloLegalizacionImpl;
import org.icogam.legalizacion.beans.paginacion.LegalizacionBean;
import org.icogam.legalizacion.utiles.TipoSiNo;

import trafico.dao.implHibernate.AliasQueryBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;

public class ModeloLegalizacionImplPag extends ModeloSkeletonPaginatedListImpl{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloLegalizacionImpl.class);
	
	private static final int ESTADO = 1;
	
	public ModeloLegalizacionImplPag(AbstractFactoryPaginatedList factoria) {
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
		if (beanCriterios!=null){
			criterion = createCriterion((LegalizacionBean)beanCriterios);
		}
		
		return criterion;
	}
	
	public List<Criterion> createCriterion(LegalizacionBean legalizacionCita){
		
		List<Criterion> criterionList= new ArrayList<Criterion>();
		if (legalizacionCita!=null){
			if (legalizacionCita.getNumColegiado()!=null && !legalizacionCita.getNumColegiado().equals("")){
				criterionList.add(Restrictions.eq("numColegiado",legalizacionCita.getNumColegiado()));
			}
			if (legalizacionCita.getNombre()!=null && !legalizacionCita.getNombre().equals("")){
				criterionList.add(Restrictions.eq("nombre",legalizacionCita.getNombre().toUpperCase()));
			}
			if (legalizacionCita.getFechaFiltradoLegalizacion()!=null){
				FechaFraccionada fecha = legalizacionCita.getFechaFiltradoLegalizacion();
				anadirCriterioFecha(criterionList, fecha, "fechaLegalizacion");
			}
			if (legalizacionCita.getTipoDocumento()!=null && !legalizacionCita.getTipoDocumento().equals("-1") && !legalizacionCita.getTipoDocumento().equals("")){
				criterionList.add(Restrictions.eq("tipoDocumento",legalizacionCita.getTipoDocumento()));
			}
			if (legalizacionCita.getClaseDocumento()!=null && !legalizacionCita.getClaseDocumento().equals("-1") && !legalizacionCita.getClaseDocumento().equals("")){
				criterionList.add(Restrictions.eq("claseDocumento",legalizacionCita.getClaseDocumento()));
			}
			if (legalizacionCita.getPais()!=null && !legalizacionCita.getPais().equals("")){
				criterionList.add(Restrictions.eq("pais",legalizacionCita.getPais()));
			}
			if (legalizacionCita.getFicheroAdjunto()!=null && !legalizacionCita.getFicheroAdjunto().equals("-1")){
				Criterion rest5 = null;
				if (legalizacionCita.getFicheroAdjunto().equals(TipoSiNo.SI.toString())){
					rest5 = Restrictions.eq("ficheroAdjunto",1);
				}else{
					rest5 = Restrictions.eq("ficheroAdjunto",0);
				}
				criterionList.add(rest5);
			}
			if (legalizacionCita.getSolicitado()!=null && !legalizacionCita.getSolicitado().equals("-1")){
				Criterion rest6 = null;
				if (legalizacionCita.getSolicitado().equals(TipoSiNo.SI.toString())){
					rest6 = Restrictions.eq("solicitado",1);
				}else{
					rest6 = Restrictions.eq("solicitado",0);
				}
				criterionList.add(rest6);
			}
			
			if (legalizacionCita.getReferencia()!=null && !legalizacionCita.getReferencia().equals("")){
				criterionList.add(Restrictions.eq("referencia",legalizacionCita.getReferencia()));
			}
			if (legalizacionCita.getEstadoPeticion()!=null && legalizacionCita.getEstadoPeticion()!=-1){
				criterionList.add(Restrictions.eq("estadoPeticion",legalizacionCita.getEstadoPeticion()));
			}
			/*
			 * Anulado SI: campo estado == 0 en base de datos
			 * Anulado NO: campo estado == 1 en base de datos
			 */
			if (legalizacionCita.getEstado()!=null && !legalizacionCita.getEstado().equals("-1")){
				Criterion rest7 = null;
				if (legalizacionCita.getEstado().equals(TipoSiNo.SI.toString())){
					rest7 = Restrictions.eq("estado",0);
				}else{
					rest7 = Restrictions.eq("estado",1);
				}
				criterionList.add(rest7);
			}else{
				criterionList.add(Restrictions.eq("estado",ESTADO));
			}
		}
		return criterionList;
	}
	
	
	
}
