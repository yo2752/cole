package org.gestoresmadrid.oegam2comun.evolucionConsultaDev.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionConsultaDev.model.dao.EvolucionConsultaDevDao;
import org.gestoresmadrid.core.evolucionConsultaDev.model.vo.EvolucionConsultaDevPK;
import org.gestoresmadrid.core.evolucionConsultaDev.model.vo.EvolucionConsultaDevVO;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.model.service.ServicioEvolucionConsultaDev;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.view.dto.EvolucionConsultaDevDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEvolucionConsultaDevImpl implements ServicioEvolucionConsultaDev{

	private static final long serialVersionUID = 2045535340894510852L;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	EvolucionConsultaDevDao evolucionConsultaDevDao;
	
	@Override
	public List<EvolucionConsultaDevDto> convertirListaVOToDto(List<EvolucionConsultaDevVO> list) {
		if(list != null && !list.isEmpty()){
			return conversor.transform(list, EvolucionConsultaDevDto.class);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public void guardarEvolucion(Long idConsultaDev, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion) {
		EvolucionConsultaDevVO evolucionConsultaDevVO = rellenarEvolucion(idConsultaDev,idUsuario, fecha, estadoAnt, estadoNuevo,tipoActuacion);
		if(evolucionConsultaDevVO != null){
			evolucionConsultaDevDao.guardar(evolucionConsultaDevVO);
		}
		
	}

	private EvolucionConsultaDevVO rellenarEvolucion(Long idConsultaDev, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion) {
		EvolucionConsultaDevVO evolucionConsultaDevVO = new EvolucionConsultaDevVO();
		EvolucionConsultaDevPK id = new EvolucionConsultaDevPK();
		id.setIdConsultaDev(idConsultaDev);
		id.setFechaCambio(fecha);
		id.setIdUsuario(idUsuario);
		evolucionConsultaDevVO.setId(id);
		evolucionConsultaDevVO.setEstadoAnt(estadoAnt);
		evolucionConsultaDevVO.setEstadoNuevo(estadoNuevo);
		evolucionConsultaDevVO.setTipoActuacion(tipoActuacion);
		return evolucionConsultaDevVO;
	}

	@Override
	@Transactional
	public int getNumPeticionesWS(Long idConsultaDev) {
		return evolucionConsultaDevDao.getNumPeticionesWS(idConsultaDev);
	}
	
}
