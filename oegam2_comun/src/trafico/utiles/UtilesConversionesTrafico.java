package trafico.utiles;


import java.math.BigDecimal;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;

import trafico.beans.TramiteTraficoBean;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;

/**
 * Encapsula operaciones útiles de conversión entre beans de los action y beans de invocación al modelo
 * para trámites de tráfico
 * @author GLOBALTMS ESPAÑA
 */
public class UtilesConversionesTrafico {
	
	/**
	 * Convierte un TramiteTraficoBean en un BeanPQCAMB_ESTADO
	 * @param tramiteTrafico
	 * @return BeanPQCAMB_ESTADO
	 * @throws Exception 
	 */
	public static BeanPQCAMBIAR_ESTADO_S_V convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(TramiteTraficoBean tramiteTrafico)
		throws Exception{

		BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = new BeanPQCAMBIAR_ESTADO_S_V();
		beanCambEstado.setP_ID_USUARIO(getUtilesColegiado().getIdUsuarioSessionBigDecimal());
		beanCambEstado.setP_NUM_EXPEDIENTE(tramiteTrafico.getNumExpediente());
		beanCambEstado.setP_ESTADO_ANTERIOR(tramiteTrafico.getEstado()!=null?new BigDecimal(tramiteTrafico.getEstado().getValorEnum()):null);
		return beanCambEstado;
	}
	
	public static BeanPQCAMBIAR_ESTADO_S_V convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(TramiteTraficoBean tramiteTrafico, BigDecimal idUsuario)
			throws Exception{

			BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = new BeanPQCAMBIAR_ESTADO_S_V();
			beanCambEstado.setP_ID_USUARIO(idUsuario);
			beanCambEstado.setP_NUM_EXPEDIENTE(tramiteTrafico.getNumExpediente());
			beanCambEstado.setP_ESTADO_ANTERIOR(tramiteTrafico.getEstado()!=null?new BigDecimal(tramiteTrafico.getEstado().getValorEnum()):null);
			return beanCambEstado;
	}
	
	private static UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}
}
