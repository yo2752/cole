package org.gestoresmadrid.oegamComun.semaforo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.evolucionSemaforo.model.dao.EvolucionSemaforoDao;
import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.core.semaforo.model.dao.SemaforoDao;
import org.gestoresmadrid.core.semaforo.model.vo.SemaforoVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunPersistenciaSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunValidacionSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComunSemaforosImpl implements ServicioComunSemaforos {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8455996454905288922L;
	
	@Autowired
	ServicioComunPersistenciaSemaforos servicioPersistenciaSemaforos;
	
	@Autowired
	ServicioComunValidacionSemaforos servicioComunValidacionSemaforos;
	
	@Autowired
	SemaforoDao semaforoDao;
	
	@Autowired
	EvolucionSemaforoDao evolucionSemaforoDao;
	
	@Autowired
	Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public SemaforoDto obtenerSemaforo(SemaforoDto semaforo) {
		SemaforoDto resultado = null;
		SemaforoVO semaforoVO = conversor.transform(semaforo, SemaforoVO.class);
		
		SemaforoVO semaforoVoRes = semaforoDao.obtenerSemaforoVO(semaforoVO);
		if (semaforoVoRes != null) {
			resultado = conversor.transform(semaforoVoRes, SemaforoDto.class);
		}
		
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public SemaforoDto obtenerSemaforo(Long idSemaforo) {
		SemaforoDto resultado = null;
		
		SemaforoVO semaforoVoRes = semaforoDao.obtenerSemaforoVO(idSemaforo);
		if (semaforoVoRes != null) {
			resultado = conversor.transform(semaforoVoRes, SemaforoDto.class);
		}
		
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SemaforoDto> obtenerListaSemaforosSes(String nodo) {
		List<SemaforoVO> listaSemaforosVO = semaforoDao.obtenerListadoSemaforosSes(nodo);
		
		return conversor.transform(listaSemaforosVO, SemaforoDto.class);
	}

	@Override
	public ResultadoSemaforosBean guardarSemaforo(SemaforoDto semaforo, SemaforoDto semaforoExist,
			OperacionEvolSemaforo operacion, Long idUsuario) throws Exception {
		ResultadoSemaforosBean resultado = new ResultadoSemaforosBean(false);
		ResultadoSemaforosBean resultadoValidacion = servicioComunValidacionSemaforos.validacionGuardarSemaforo(semaforo, semaforoExist);
		if (resultadoValidacion.getError()) {
			resultado.setError(true);
			resultado.setMensaje(resultadoValidacion.getMensaje());
		} else {
			resultado = servicioPersistenciaSemaforos.guardarSemaforo(semaforo, operacion, idUsuario);
			if (resultado.getError()) {
				resultado.setError(true);
				resultado.setMensaje("Ha habido un problema al crear el semáforo");
			}
		}
		
		return resultado;
	}

	@Override
	public ResultadoSemaforosBean cambiarEstadoSemaforos(List<SemaforoDto> listaSemaforos, Integer estado, Long idUsuario) throws Exception {
		ResultadoSemaforosBean resultado = new ResultadoSemaforosBean(false);

		for (SemaforoDto semaforo : listaSemaforos) {
			if (semaforo != null && semaforo.getIdSemaforo() != null) {
				semaforo.setEstado(estado);
				ResultadoSemaforosBean resultadoGuardar = servicioPersistenciaSemaforos.guardarSemaforo(semaforo, 
						OperacionEvolSemaforo.Cambio_Estado, 
						idUsuario);
				if (resultadoGuardar.getError()) {
					if (resultado.getListaMensajesError() == null) {
						resultado.setError(true);
						resultado.setListaMensajesError(new ArrayList<String>(1));
					}
					resultado.getListaMensajesError().add(
							"Ha habido un problema al intentar cambiar el estado del semáforo" + semaforo.getIdSemaforo());
				} else {
					if (resultado.getListaMensajesAviso() == null) {
						resultado.setListaMensajesAviso(new ArrayList<String>(1));
					}
					resultado.getListaMensajesAviso().add("Semáforo " + semaforo.getIdSemaforo() + " cambiado de estado correctamente");
				}
			} else {
				if (resultado.getListaMensajesError() == null) {
					resultado.setError(true);
					resultado.setListaMensajesError(new ArrayList<String>(1));
				}
				resultado.getListaMensajesError().add("El semáforo " + semaforo.getIdSemaforo() + " no existe");
			}
		}
		
		return resultado;
	}

	@Override
	public ResultadoSemaforosBean deshabilitarSemaforos(List<SemaforoDto> listaSemaforos, Long idUsuario) throws Exception {
		ResultadoSemaforosBean resultado = new ResultadoSemaforosBean(false);

		for (SemaforoDto semaforo : listaSemaforos) {
			if (semaforo != null && semaforo.getIdSemaforo() != null) {
				semaforo.setEstado(0);
				ResultadoSemaforosBean resultadoGuardar = servicioPersistenciaSemaforos.guardarSemaforo(semaforo, 
						OperacionEvolSemaforo.Inhabilitar, 
						idUsuario);
				if (resultadoGuardar.getError()) {
					if (resultado.getListaMensajesError() == null) {
						resultado.setError(true);
						resultado.setListaMensajesError(new ArrayList<String>(1));
					}
					resultado.getListaMensajesError().add(
							"Ha habido un problema al intentar deshabilitar el semáforo" + semaforo.getIdSemaforo());
				} else {
					if (resultado.getListaMensajesAviso() == null) {
						resultado.setListaMensajesAviso(new ArrayList<String>(1));
					}
					resultado.getListaMensajesAviso().add("Semáforo " + semaforo.getIdSemaforo() + " deshabilitado correctamente");
				}
			} else {
				if (resultado.getListaMensajesError() == null) {
					resultado.setError(true);
					resultado.setListaMensajesError(new ArrayList<String>(1));
				}
				resultado.getListaMensajesError().add("El semáforo " + semaforo.getIdSemaforo() + " no existe");
			}
		}
		
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SemaforoDto> obtenerSemaforosSelec(String[] codigosSelec) {
		List<SemaforoDto> listaSemaforos = new ArrayList<>(1);

		for (String codigo : codigosSelec) {
			listaSemaforos.add(obtenerSemaforo(Long.valueOf(codigo))) ;
		}
		
		return listaSemaforos;
	}

}
