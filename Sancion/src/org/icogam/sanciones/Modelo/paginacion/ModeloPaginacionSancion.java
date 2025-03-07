package org.icogam.sanciones.Modelo.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.icogam.sanciones.DTO.BeanCriteriosSancion;

import trafico.dao.implHibernate.AliasQueryBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;

public class ModeloPaginacionSancion extends ModeloSkeletonPaginatedListImpl {
	
	private static final int ESTADO = 1;
	
	public ModeloPaginacionSancion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloPaginacionSancion.class);

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
		List<Criterion> criterionList= new ArrayList<Criterion>();
		if (beanCriterios!=null && beanCriterios instanceof BeanCriteriosSancion){
			BeanCriteriosSancion sancion = (BeanCriteriosSancion) beanCriterios;
			
			if (sancion.getNombre()!=null && !sancion.getNombre().equals("")) {
				criterionList.add(Restrictions.eq("nombre",sancion.getNombre().toUpperCase()));
			}
			
			if (sancion.getApellidos()!=null && !sancion.getApellidos().equals("")) {
				criterionList.add(Restrictions.eq("apellidos",sancion.getApellidos().toUpperCase()));
			}
			
			if (sancion.getDni()!=null && !sancion.getDni().equals("")) {
				criterionList.add(Restrictions.eq("dni",sancion.getDni().toUpperCase()));
			}
			
			if (sancion.getMotivo()!=null && !sancion.getMotivo().equals(-1)){
				Criterion rest4 = Restrictions.eq("motivo",sancion.getMotivo());
				criterionList.add(rest4);
			}
			
			criterionList.add(Restrictions.eq("estado", ESTADO));
			
			if (sancion.getEstadoSancion()!=null && !sancion.getEstadoSancion().equals(-1)){
				Criterion rest6 = Restrictions.eq("estadoSancion",sancion.getEstadoSancion());
				criterionList.add(rest6);
			}
			if (sancion.getFechaFiltradoPresentacion()!=null){
				FechaFraccionada fecha = sancion.getFechaFiltradoPresentacion();
				anadirCriterioFecha(criterionList, fecha, "fechaPresentacion");
			}
			
			if (sancion.getNumColegiado()!=null && !sancion.getNumColegiado().equals("")){
				criterionList.add(Restrictions.eq("numColegiado",sancion.getNumColegiado()));
			}
		}
		return criterionList;
	}

}
