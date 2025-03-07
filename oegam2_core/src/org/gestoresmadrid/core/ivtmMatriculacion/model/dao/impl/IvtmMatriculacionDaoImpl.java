package org.gestoresmadrid.core.ivtmMatriculacion.model.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS;
import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados.EstadoIVTM;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.trafico.TramiteTraficoIVTM;
import utilidades.estructuras.Fecha;

@Repository
public class IvtmMatriculacionDaoImpl extends GenericDaoImplHibernate<IvtmMatriculacionVO> implements IvtmMatriculacionDao {

	private static final long serialVersionUID = -509290368665919211L;

	private static final String TIPO_ID_PETICION_IVTM = "1";

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public IvtmMatriculacionVO getIvtmPorExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(IvtmMatriculacionVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		@SuppressWarnings("unchecked")
		List<IvtmMatriculacionVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public BigDecimal idPeticionMax(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(IvtmMatriculacionVO.class);
		String idPeticion = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		idPeticion += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		idPeticion += TIPO_ID_PETICION_IVTM;

		criteria.add(Restrictions.sqlRestriction( " ID_PETICION like " + " '"+idPeticion+"%'"));
		criteria.setProjection(Projections.max("idPeticion"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			return null;
		}
		return maximoExistente;
	}

	@Override
	@Transactional
	public IvtmResultadoWSMatriculasWS[] recuperarMatriculas(
			org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmDatosEntradaMatriculasWS datosEntrada) {
		IvtmResultadoWSMatriculasWS[] resultado=null;
		try{
		Criteria crit = getCurrentSession().createCriteria(TramiteTraficoIVTM.class, "tti");
		crit.createAlias("vehiculo", "vehiculo");
		crit.add(Restrictions.eq("tti.tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum()));
		crit.add(Restrictions.in("tti.estado", new Object[]{new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())}));
		crit.add(Restrictions.isNotNull("vehiculo.matricula"));
		crit.add(Restrictions.isNotNull("tti.nrc"));
		crit.add(Restrictions.isNotNull("tti.fechaPresentacion"));
		crit.add(Restrictions.in("tti.estadoIvtm", (Object[])new String[]{EstadoIVTM.Ivtm_Ok.getValorEnum(), EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum(),EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum()}));
		if (datosEntrada!=null && datosEntrada.getListaAutoliquidaciones()!=null && datosEntrada.getListaAutoliquidaciones().length>0){
			
			Object[] nrcs = new Object[datosEntrada.getListaAutoliquidaciones().length];
			for (int i=0; i<datosEntrada.getListaAutoliquidaciones().length; i++){
				nrcs[i]=datosEntrada.getListaAutoliquidaciones()[i];
			}
			crit.add(Restrictions.in("tti.nrc", nrcs));
			if (datosEntrada.getFechaInicio()!=null){
				try {
					crit.add(Restrictions.ge("tti.fechaPresentacion", datosEntrada.getFechaInicio().getDate()));
				} catch (ParseException e) {
					log.error("No se ha podido transformar la fecha inicio, y no se utilizará este criterio");
				}
				
			}
			if (datosEntrada.getFechaFin()!=null){
				try {
					crit.add(Restrictions.le("tti.fechaPresentacion", datosEntrada.getFechaFin().getDate()));
				} catch (ParseException e) {
					log.error("No se ha podido transformar la fecha fin, y no se utilizará este criterio");
				}
			}
			crit.setProjection(Projections.projectionList().add(Projections.property("nrc"),"nrc").add(Projections.property("vehiculo.matricula"),"matricula").add(Projections.property("vehiculo.bastidor"),"vehiculo.bastidor"));		
			List result = crit.list();
			resultado = new IvtmResultadoWSMatriculasWS[result.size()];
			for (int i = 0; i < result.size(); i++) {
				Object[] fila = ((Object[])result.get(i));
				IvtmResultadoWSMatriculasWS registro = new IvtmResultadoWSMatriculasWS();
				registro.setNumAutoliquidacion((String)fila[0]);
				registro.setMatricula((String)fila[1]);
				registro.setBastidor((String)fila[2]);
				resultado[i]=registro;
			}
		}else{
			return null;
		}
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return resultado;
	}
}
