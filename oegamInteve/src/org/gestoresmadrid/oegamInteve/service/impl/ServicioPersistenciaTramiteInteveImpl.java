package org.gestoresmadrid.oegamInteve.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoSolInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamInteve.service.ServicioPersistenciaTramiteInteve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaTramiteInteveImpl implements ServicioPersistenciaTramiteInteve {

	private static final long serialVersionUID = 7158449642288689089L;

	@Autowired
	TramiteTraficoInteveDao tramiteTraficoInteveDao;

	@Autowired
	TramiteTraficoSolInteveDao tramiteTraficoSolInteveDao;

	@Autowired
	EvolucionTramiteTraficoDao evolucionTramiteTraficoDao;

	@Autowired
	ServicioComunTasa servicioComunTasa;

	@Override
	@Transactional
	public void guardar(TramiteTraficoInteveVO tramiteInteveVO) {
		tramiteTraficoInteveDao.guardar(tramiteInteveVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoSolInteveVO> getListaTramitesSolInteve(BigDecimal numExpediente) {
		return tramiteTraficoSolInteveDao.getListaTramitesSolInteve(numExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoInteveVO getTramiteInteve(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoInteveDao.getTramiteInteve(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional
	public void guardarOactualizarTramiteInteve(TramiteTraficoInteveVO tramiteInteveVO) {
		tramiteTraficoInteveDao.guardarOActualizar(tramiteInteveVO);
	}

	@Override
	@Transactional
	public void guardarEvolucionTramite(EvolucionTramiteTraficoVO evolucion) {
		evolucionTramiteTraficoDao.guardar(evolucion);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoSolInteveVO getTramiteSolInteve(BigDecimal numExpediente, String matricula, String bastidor, String nive) {
		return tramiteTraficoSolInteveDao.getTramiteSolInteve(numExpediente, matricula, bastidor, nive);
	}

	@Override
	@Transactional
	public void guardarOactualizarTramiteSolInteve(TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO) {
		tramiteTraficoSolInteveDao.guardarOActualizar(tramiteTraficoSolInteveVO);
	}

	@Override
	@Transactional
	public void actualizarSolicitudesNoFinalizadas(BigDecimal numExpediente, String estadoNuevo) {
		List<TramiteTraficoSolInteveVO> listaSolicitudesBBDD = tramiteTraficoSolInteveDao.getListaSolicitudesNoFinalizadas(numExpediente);
		if (listaSolicitudesBBDD != null && !listaSolicitudesBBDD.isEmpty()) {
			for (TramiteTraficoSolInteveVO tramite : listaSolicitudesBBDD) {
				tramite.setEstado(estadoNuevo);
				tramiteTraficoSolInteveDao.actualizar(tramite);
			}
		}
	}

	@Override
	@Transactional
	public void actualizarEstadoTramiteInteve(BigDecimal numExpediente, String estadoNuevo) {
		TramiteTraficoInteveVO tramiteTraficoInteveVO = getTramiteInteve(numExpediente, Boolean.FALSE);
		if (tramiteTraficoInteveVO != null) {
			tramiteTraficoInteveVO.setEstado(new BigDecimal(estadoNuevo));
			if (estadoNuevo.equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum())
				|| estadoNuevo.equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) {
				tramiteTraficoInteveVO.setFechaPresentacion(new Date());
			} else {
				tramiteTraficoInteveVO.setFechaPresentacion(null);
			}
			tramiteTraficoInteveDao.actualizar(tramiteTraficoInteveVO);
		}
	}

	@Override
	@Transactional
	public void actualizarEstadoTramiteSolInteve(Long idTramiteSolInteve, String estadoNuevo) {
		TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = getTramiteSolIntevePorId(idTramiteSolInteve);
		if (tramiteTraficoSolInteveVO != null) {
			tramiteTraficoSolInteveVO.setEstado(estadoNuevo);
			if (estadoNuevo.equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) {
				tramiteTraficoSolInteveVO.setFechaPresentacion(new Date());
			} else {
				tramiteTraficoSolInteveVO.setFechaPresentacion(null);
			}
			tramiteTraficoSolInteveDao.actualizar(tramiteTraficoSolInteveVO);
		}
	}

	@Override
	@Transactional
	public void actualizarCambioEstadoTramiteSolInteve(TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO, String estadoAnteriorTramite, String estadoNuevo) {
		if (!estadoAnteriorTramite.equals(tramiteTraficoSolInteveVO.getEstado())) {
			if (estadoNuevo.equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum())
					|| estadoNuevo.equalsIgnoreCase(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) {
				tramiteTraficoSolInteveVO.setFechaPresentacion(new Date());
			}
			tramiteTraficoSolInteveVO.setEstado(estadoNuevo);
			tramiteTraficoSolInteveDao.actualizar(tramiteTraficoSolInteveVO);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoSolInteveVO getTramiteSolIntevePorId(Long idTramiteSolInteve) {
		return tramiteTraficoSolInteveDao.getTramiteSolIntevePorId(idTramiteSolInteve);
	}

	@Override
	@Transactional
	public void finalizarTramiteSolInteveWS(Long idTramiteInteve, String estado, String resultado) {
		TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = getTramiteSolIntevePorId(idTramiteInteve);
		if (tramiteTraficoSolInteveVO != null) {
			tramiteTraficoSolInteveVO.setEstado(estado);
			tramiteTraficoSolInteveVO.setRespuestaDgt(resultado);
			tramiteTraficoSolInteveVO.setFechaPresentacion(new Date());
			tramiteTraficoSolInteveDao.actualizar(tramiteTraficoSolInteveVO);
		}
	}

	@Override
	@Transactional
	public void eliminarSolicitud(Long idTramiteInteve, Long idContrato, Long idUsuario) {
		TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO = getTramiteSolIntevePorId(idTramiteInteve);
		if (tramiteTraficoSolInteveVO != null) {
			if (StringUtils.isNotBlank(tramiteTraficoSolInteveVO.getCodigoTasa())) {
				ResultadoBean resultTasa = servicioComunTasa.desasignarTasaExpediente(tramiteTraficoSolInteveVO.getCodigoTasa(), tramiteTraficoSolInteveVO.getNumExpediente(), idContrato,
						TipoTasa.CuatroUno.getValorEnum(), idUsuario);
				if (resultTasa != null && !resultTasa.getError()) {
					tramiteTraficoSolInteveDao.borrar(tramiteTraficoSolInteveVO);
				}
			} else {
				tramiteTraficoSolInteveDao.borrar(tramiteTraficoSolInteveVO);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoSolInteveVO> getDuplicados(Long idContrato, String matricula, String bastidor, String nive) {
		return tramiteTraficoSolInteveDao.getDuplicados(idContrato, matricula, bastidor, nive);
	}

}