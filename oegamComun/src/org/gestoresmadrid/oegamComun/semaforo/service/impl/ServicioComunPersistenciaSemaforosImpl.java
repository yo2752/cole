package org.gestoresmadrid.oegamComun.semaforo.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.evolucionSemaforo.model.dao.EvolucionSemaforoDao;
import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.core.evolucionSemaforo.model.vo.EvolucionSemaforoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.semaforo.model.dao.SemaforoDao;
import org.gestoresmadrid.core.semaforo.model.vo.SemaforoVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunPersistenciaSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComunPersistenciaSemaforosImpl implements ServicioComunPersistenciaSemaforos {
	
	@Autowired
	SemaforoDao semaforoDao;
	
	@Autowired
	EvolucionSemaforoDao evolucionSemaforoDao;
	
	@Autowired
	Conversor conversor;

	@Override
	@Transactional
	public ResultadoSemaforosBean guardarSemaforo(SemaforoDto semaforo, OperacionEvolSemaforo operacion, Long idUsuario) throws Exception {
		ResultadoSemaforosBean resultado = new ResultadoSemaforosBean(false);
		SemaforoVO semaforoVO = conversor.transform(semaforo, SemaforoVO.class);

		semaforoVO = semaforoDao.guardarOActualizar(conversor.transform(semaforo, SemaforoVO.class));
		if (semaforoVO == null) {
			resultado.setError(true);
		} else {
			EvolucionSemaforoVO evolSemafVo = new EvolucionSemaforoVO();
			evolSemafVo.setIdSemaforo(semaforoVO.getIdSemaforo());
			UsuarioVO usuarioVo = new UsuarioVO();
			usuarioVo.setIdUsuario(idUsuario);
			evolSemafVo.setUsuario(usuarioVo);
			evolSemafVo.setFecha(new Date());
			evolSemafVo.setOperacion(operacion.getValorEnum());
			if (evolucionSemaforoDao.guardar(evolSemafVo) == null) {
				throw new Exception("Ha habido un problema durante la creación de la evolución del semáforo");
			}
			resultado.setSemaforoDto(conversor.transform(semaforoVO, SemaforoDto.class));
		}
		
		return resultado;
	}

}
