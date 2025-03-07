package org.gestoresmadrid.oegam2comun.evolucionRemesa.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.evolucionRemesas.model.dao.EvolucionRemesasDao;
import org.gestoresmadrid.core.evolucionRemesas.model.vo.EvolucionRemesasPK;
import org.gestoresmadrid.core.evolucionRemesas.model.vo.EvolucionRemesasVO;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.oegam2comun.evolucionRemesa.model.service.ServicioEvolucionRemesas;
import org.gestoresmadrid.oegam2comun.evolucionRemesa.view.bean.EvolucionRemesasBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionRemesasImpl implements ServicioEvolucionRemesas{

	private static final long serialVersionUID = 3226416334252013430L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionRemesasImpl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private EvolucionRemesasDao evolucionRemesasDao;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<EvolucionRemesasBean> convertirListaEnBeanPantalla(List<EvolucionRemesasVO> lista) {
		try {
			if(lista != null && !lista.isEmpty()){
				List<EvolucionRemesasBean> listaBeanPantalla = new ArrayList<EvolucionRemesasBean>();
				for(EvolucionRemesasVO evolucionRemesasVO : lista){
					EvolucionRemesasBean evolRemesaPantBean = conversor.transform(evolucionRemesasVO, EvolucionRemesasBean.class, "evolRemesaBeanPantalla");
					evolRemesaPantBean.setEstadoAnterior(EstadoRemesas.convertirTexto(evolucionRemesasVO.getId().getEstadoAnterior().toString()));
					evolRemesaPantBean.setEstadoNuevo(EstadoRemesas.convertirTexto(evolucionRemesasVO.getId().getEstadoNuevo().toString()));
					listaBeanPantalla.add(evolRemesaPantBean);
				}
				return listaBeanPantalla;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto EvolucionRemesasVO to EvolucionRemesasBean, error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public EvolucionRemesasVO guardarEvolucionRemesa(BigDecimal numExpediente, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		try{
			EvolucionRemesasVO evolucionRemesasVO = new EvolucionRemesasVO();
			EvolucionRemesasPK id = new EvolucionRemesasPK();
			id.setNumExpediente(numExpediente);
			id.setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			id.setEstadoNuevo(estadoNuevo);
			if(estadoAnt != null){
				id.setEstadoAnterior(estadoAnt);
			}else{
				id.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucionRemesasVO.setId(id);
			evolucionRemesasVO.setIdUsuario(idUsuario);
			evolucionRemesasDao.guardar(evolucionRemesasVO);
			return evolucionRemesasVO;		
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion de la remesa, error: ",e);
		}
		return null;
	}
}
