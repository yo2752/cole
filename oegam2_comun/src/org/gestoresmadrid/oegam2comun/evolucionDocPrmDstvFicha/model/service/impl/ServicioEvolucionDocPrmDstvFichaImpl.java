package org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.evolucionDocPrmDstvFicha.model.dao.EvolucionDocPrmDstvFichaDao;
import org.gestoresmadrid.core.evolucionDocPrmDstvFicha.model.vo.EvolucionDocPrmDstvFichaVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.ServicioEvolucionDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.view.bean.EvolucionDocPrmDstvFichaBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionDocPrmDstvFichaImpl implements ServicioEvolucionDocPrmDstvFicha{

	private static final long serialVersionUID = -9024576260147809094L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionDocPrmDstvFichaImpl.class);
	
	@Autowired
	EvolucionDocPrmDstvFichaDao evolucionDocPrmDstvFichaDao;
	
	@Override
	public List<EvolucionDocPrmDstvFichaBean> convertirListaEnBeanPantallaConsulta(List<EvolucionDocPrmDstvFichaVO> lista) {
		List<EvolucionDocPrmDstvFichaBean> listaEvo = new ArrayList<EvolucionDocPrmDstvFichaBean>();
		for(EvolucionDocPrmDstvFichaVO evDstvFichaVO : lista){
			EvolucionDocPrmDstvFichaBean bean = new EvolucionDocPrmDstvFichaBean();
			bean.setDocId(evDstvFichaVO.getDocID());
			bean.setDocumento(TipoDocumentoImprimirEnum.convertirTexto(evDstvFichaVO.getTipoDocumento()));
			bean.setFecha(new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(evDstvFichaVO.getFechaCambio()));
			if(evDstvFichaVO.getEstadoAnterior() != null){
				bean.setEstadoAnterior(EstadoPermisoDistintivoItv.convertirEstadoBigDecimal(evDstvFichaVO.getEstadoAnterior()));
			}
			bean.setEstadoNuevo(EstadoPermisoDistintivoItv.convertirEstadoBigDecimal(evDstvFichaVO.getEstadoNuevo()));
			bean.setOperacion(OperacionPrmDstvFicha.convertirValor(evDstvFichaVO.getOperacion()));
			bean.setUsuario(evDstvFichaVO.getUsuario().getApellidosNombre());
			listaEvo.add(bean);
		}
		return listaEvo;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean guardarEvolucion(String documento, Date fecha, 
			String estadoAnt,String estadoNuevo, OperacionPrmDstvFicha operacion, String docId, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			EvolucionDocPrmDstvFichaVO evoDocPrmDstvFichaVO = new EvolucionDocPrmDstvFichaVO();
			if(estadoAnt != null && !estadoAnt.isEmpty()){
				evoDocPrmDstvFichaVO.setEstadoAnterior(new BigDecimal(estadoAnt));
			}
			if(estadoNuevo != null && !estadoNuevo.isEmpty()){
				evoDocPrmDstvFichaVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
			}
			evoDocPrmDstvFichaVO.setDocID(docId);
			evoDocPrmDstvFichaVO.setFechaCambio(fecha);
			evoDocPrmDstvFichaVO.setIdUsuario(idUsuario);
			evoDocPrmDstvFichaVO.setOperacion(operacion.getValorEnum());
			evoDocPrmDstvFichaVO.setTipoDocumento(documento);
			evolucionDocPrmDstvFichaDao.guardar(evoDocPrmDstvFichaVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del docPrmDstvFicha con docId: " + docId +", error: ", e);
		}
		return resultado;
	}
	
}
