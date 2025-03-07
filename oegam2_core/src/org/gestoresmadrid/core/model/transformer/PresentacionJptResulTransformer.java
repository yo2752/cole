package org.gestoresmadrid.core.model.transformer;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.BeanPresentadoJpt;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

@Component
public class PresentacionJptResulTransformer implements ResultTransformer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2374471823479707149L;

	/**
	 * Necesita que se haya creado previamente el alias "vehiculo"
	 * 
	 * @return
	 */
	public ProjectionList getProjection() {
		ProjectionList projectionList =Projections.projectionList();
		projectionList.add(Projections.property("numColegiado"));
		projectionList.add(Projections.property("fechaPresentacion"));
		projectionList.add(Projections.property("tipoTramite"));
		projectionList.add(Projections.property("numExpediente"));
		projectionList.add(Projections.alias((Projections.property("vehiculo.matricula")), "matricula"));
		projectionList.add(Projections.alias((Projections.property("vehiculo.bastidor")), "bastidor"));
		projectionList.add(Projections.property("presentadoJpt"));
		projectionList.add(Projections.property("fechaPresentacionJpt"));

		return projectionList;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		BeanPresentadoJpt bean = new BeanPresentadoJpt();
		bean.setNumColegiado((String) tuple[0]);
		bean.setFechaPresentacion((Date) tuple[1]);
		bean.setTipoTramiteTrafico(TipoTramiteTrafico.convertir((String) tuple[2]));
		bean.setNumExpediente(tuple[3].toString());
		bean.setMatricula((String) tuple[4]);
		bean.setBastidor((String) tuple[5]);
		EstadoPresentacionJPT estadoPresentacionJPT = EstadoPresentacionJPT.convertir((Short) tuple[6]);
		bean.setPresentado(estadoPresentacionJPT != null ? estadoPresentacionJPT : EstadoPresentacionJPT.No_Presentado);
		bean.setFechaPresentacionJpt((Date) tuple[7]);
		return bean;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

}
