package trafico.dao.implHibernate.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.trafico.IvtmMatriculacion;
import hibernate.entities.trafico.TramiteTraficoIVTM;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.ivtm.IvtmMatriculacionDaoInterface;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class IvtmMatriculacionDaoImplHibernate extends GenericDaoImplHibernate<IvtmMatriculacion> implements IvtmMatriculacionDaoInterface {

	private static final ILoggerOegam log = LoggerOegam.getLogger(IvtmMatriculacionDaoImplHibernate.class);

	public IvtmMatriculacionDaoImplHibernate(IvtmMatriculacion t) {
		super(t);
	}

	@Override
	public BigDecimal recuperarIdPeticion(BigDecimal numExpediente) {
		IvtmMatriculacion ivtm =this.buscarPorExpediente(numExpediente);
		if (ivtm != null && ivtm.getIdPeticion() != null) {
			return ivtm.getIdPeticion();
		}
		return null;
	}

	@Override
	public IvtmResultadoWSMatriculasWS[] recuperarMatriculas(IvtmDatosEntradaMatriculasWS datosEntrada) {
		IvtmResultadoWSMatriculasWS[] resultado = null;
		try {
			Session session = createSession();
			Criteria crit = session.createCriteria(TramiteTraficoIVTM.class, "tti");
			crit.createAlias("vehiculo", "vehiculo");
			crit.add(Restrictions.eq("tti.tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum()));
			crit.add(Restrictions.in("tti.estado", new Object[]{new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())}));
			crit.add(Restrictions.isNotNull("vehiculo.matricula"));
			crit.add(Restrictions.isNotNull("tti.nrc"));
			crit.add(Restrictions.isNotNull("tti.fechaPresentacion"));
			crit.add(Restrictions.in("tti.estadoIvtm", (Object[])new String[]{EstadoIVTM.Ivtm_Ok.getValorEnum(), EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum(),EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum()}));
			if (datosEntrada != null) {
				if (datosEntrada.getListaAutoliquidaciones()!=null && datosEntrada.getListaAutoliquidaciones().length > 0) {
					Object[] nrcs = new Object[datosEntrada.getListaAutoliquidaciones().length];
					for (int i=0; i<datosEntrada.getListaAutoliquidaciones().length; i++) {
						nrcs[i]=datosEntrada.getListaAutoliquidaciones()[i];
					}
					crit.add(Restrictions.in("tti.nrc", nrcs));
				}
				if (datosEntrada.getFechaInicio() != null) {
					try {
						crit.add(Restrictions.ge("tti.fechaPresentacion", datosEntrada.getFechaInicio().getDate()));
					} catch (ParseException e) {
						log.error("No se ha podido transformar la fecha inicio, y no se utilizará este criterio");
					}
				}
				if (datosEntrada.getFechaFin() != null) {
					try {
						crit.add(Restrictions.le("tti.fechaPresentacion", datosEntrada.getFechaFin().getDate()));
					} catch (ParseException e) {
						log.error("No se ha podido transformar la fecha fin, y no se utilizará este criterio");
					}
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

}