package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impr.model.dao.EvolucionDocImprDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionDocImprVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvoDocImpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEvoDocImprImpl implements ServicioEvoDocImpr{

	private static final long serialVersionUID = 7396463711306578107L;
	
	@Autowired
	EvolucionDocImprDao evolucionDocImprDao; 

	@Override
	@Transactional(readOnly = true)
	public List<EvolucionDocImprVO> obtenerEvolucionDocImpr(Long docId, Boolean completo) {
		return evolucionDocImprDao.obtenerEvolucionDocImpr(docId, completo);
	}
	
	@Override
	@Transactional
	public void guardarEvolucion(Long docId, String tipo, String operacion, String estadoAnt, String estadoNuevo, Long idUsuario) {
		evolucionDocImprDao.guardar(rellenarEvolucionDocImpr(docId,tipo, operacion, estadoAnt, estadoNuevo, idUsuario));
	}

	private EvolucionDocImprVO rellenarEvolucionDocImpr(Long docId, String tipo, String operacion, String estadoAnt, String estadoNuevo, Long idUsuario) {
		EvolucionDocImprVO evolucionDocImprVO = new EvolucionDocImprVO();
		evolucionDocImprVO.setDocId(docId);
		evolucionDocImprVO.setEstadoAnterior(estadoAnt);
		evolucionDocImprVO.setEstadoNuevo(estadoNuevo);
		evolucionDocImprVO.setFechaCambio(new Date());
		evolucionDocImprVO.setIdUsuario(idUsuario);
		evolucionDocImprVO.setOperacion(operacion);
		evolucionDocImprVO.setTipoDocumento(tipo);
		return evolucionDocImprVO;
	}
	
	@Override
	@Transactional
	public void borrarEvolucionImpr(Long docId) {
		List<EvolucionDocImprVO> listaEvo = obtenerEvolucionDocImpr(docId, Boolean.FALSE);
		if(listaEvo != null && !listaEvo.isEmpty()){
			for(EvolucionDocImprVO evo : listaEvo){
				evolucionDocImprDao.borrar(evo);
			}
		}
	}

}
