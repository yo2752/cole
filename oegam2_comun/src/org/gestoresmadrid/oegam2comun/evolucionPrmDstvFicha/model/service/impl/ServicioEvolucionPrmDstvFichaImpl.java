package org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.dao.EvolucionPrmDstvFichaDao;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionPrmDstvFichaVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.view.bean.EvolucionPrmDstvFichaBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionPrmDstvFichaImpl implements ServicioEvolucionPrmDstvFicha{

	private static final long serialVersionUID = -7237517614863318444L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionPrmDstvFichaImpl.class);

	@Autowired
	EvolucionPrmDstvFichaDao evolucionPrmDstvFichaDao;
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean guardarEvolucion(BigDecimal numExpediente, BigDecimal idUsuario,
			TipoDocumentoImprimirEnum tipoDocumento, OperacionPrmDstvFicha operacion, Date fecha, String estadoAnt,	String estadoNuevo, String docId, String ip) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			EvolucionPrmDstvFichaVO evolucionPrmDstvFichaVO = new EvolucionPrmDstvFichaVO();
			if(estadoAnt != null && !estadoAnt.isEmpty()){
				evolucionPrmDstvFichaVO.setEstadoAnterior(new BigDecimal(estadoAnt));
			}
			if(estadoNuevo != null && !estadoNuevo.isEmpty()){
				evolucionPrmDstvFichaVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
			}
			evolucionPrmDstvFichaVO.setFechaCambio(fecha);
			evolucionPrmDstvFichaVO.setIdUsuario(idUsuario);
			evolucionPrmDstvFichaVO.setNumExpediente(numExpediente);
			evolucionPrmDstvFichaVO.setOperacion(operacion.getValorEnum());
			evolucionPrmDstvFichaVO.setTipoDocumento(tipoDocumento.getValorEnum());
			evolucionPrmDstvFichaVO.setDocID(docId);
			evolucionPrmDstvFichaVO.setIp(ip);
			evolucionPrmDstvFichaDao.guardar(evolucionPrmDstvFichaVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del PrmDstvFicha, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la evolución.");
		}
		return resultado;
	}
	
	@Override
	public List<EvolucionPrmDstvFichaBean> convertirListaEnBeanPantallaConsulta(List<EvolucionPrmDstvFichaVO> lista) {
		List<EvolucionPrmDstvFichaBean> listaPantalla = new ArrayList<EvolucionPrmDstvFichaBean>();
		for(EvolucionPrmDstvFichaVO evolucionPrmDstvFichaVO : lista){
			EvolucionPrmDstvFichaBean evolucionPrmDstvFichaBean = new EvolucionPrmDstvFichaBean();
			evolucionPrmDstvFichaBean.setDocId(evolucionPrmDstvFichaVO.getDocID());
			if(evolucionPrmDstvFichaVO.getEstadoAnterior() != null){
				evolucionPrmDstvFichaBean.setEstadoAnterior(EstadoPermisoDistintivoItv.convertirEstadoBigDecimal(evolucionPrmDstvFichaVO.getEstadoAnterior()));
			}
			evolucionPrmDstvFichaBean.setEstadoNuevo(EstadoPermisoDistintivoItv.convertirEstadoBigDecimal(evolucionPrmDstvFichaVO.getEstadoNuevo()));
			evolucionPrmDstvFichaBean.setFechaCambio(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(evolucionPrmDstvFichaVO.getFechaCambio()));
			evolucionPrmDstvFichaBean.setNumExpediente(evolucionPrmDstvFichaVO.getNumExpediente());
			evolucionPrmDstvFichaBean.setOperacion(OperacionPrmDstvFicha.convertirValor(evolucionPrmDstvFichaVO.getOperacion()));
			evolucionPrmDstvFichaBean.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(evolucionPrmDstvFichaVO.getTipoDocumento()));
			evolucionPrmDstvFichaBean.setUsuario(evolucionPrmDstvFichaVO.getUsuario().getApellidosNombre());
			listaPantalla.add(evolucionPrmDstvFichaBean);
		}
		return listaPantalla;
	}
}
