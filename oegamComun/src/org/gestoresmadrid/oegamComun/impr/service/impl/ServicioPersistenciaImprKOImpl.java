package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.enumerados.EstadoImprKo;
import org.gestoresmadrid.core.impr.model.dao.ImprKoDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprKoVO;
import org.gestoresmadrid.core.impr.model.vo.ImprKoVO;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvolucionImprKo;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaImprKO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaImprKOImpl implements ServicioPersistenciaImprKO{

	private static final long serialVersionUID = -188131562257005838L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaImprKOImpl.class);

	@Autowired
	ImprKoDao imprKoDao;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioEvolucionImprKo servicioEvolucionImprKo;

	@Override
	@Transactional
	public void crearKo(ImprVO imprVO, Long idUsuario) {
		crearKOEstado(imprVO, idUsuario, EstadoImprKo.Creado.getValorEnum());
	}

	@Override
	@Transactional
	public void crearKoTramite(BigDecimal numExpediente,String tipoImpr, Long idUsuario) {
		crearKOEstadoTramite(numExpediente, idUsuario,tipoImpr, EstadoImprKo.Creado.getValorEnum());
	}

	private ImprKoVO crearKOEstadoTramite(BigDecimal numExpediente, Long idUsuario, String tipoImpr, String estado) {
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			ImprKoVO imprKO = getImprKo(numExpediente, tipoImpr);
			if (imprKO == null) {
				imprKO = rellenarImprKoTramite(tramite, estado, tipoImpr);
				imprKoDao.guardar(imprKO);
				servicioEvolucionImprKo.guardarEvolucion(rellenarEvolucionImprKo(imprKO, idUsuario, null, estado));
			} else {
				String estadoAnt = imprKO.getEstado();
				imprKO.setEstado(estado);
				imprKoDao.actualizar(imprKO);
				servicioEvolucionImprKo.guardarEvolucion(rellenarEvolucionImprKo(imprKO, idUsuario, estadoAnt, estado));
			}
			return imprKO;
		} catch (Exception e) {
			log.error("Error al guardar el KO");
		}
		return null;
	}

	private ImprKoVO rellenarImprKoTramite(TramiteTraficoVO tramite, String estado, String tipoImpr) {
		ImprKoVO imprKo = new ImprKoVO();
		imprKo.setFechaAlta(new Date());
		imprKo.setFechaPresentacion(tramite.getFechaPresentacion());
		imprKo.setEstado(estado);
		imprKo.setNumExpediente(tramite.getNumExpediente());
		imprKo.setMatricula(tramite.getVehiculo().getMatricula());
		imprKo.setBastidor(tramite.getVehiculo().getBastidor());
		imprKo.setTipoImpr(tipoImpr);
		imprKo.setTipoTramite(tramite.getTipoTramite());
		imprKo.setJefatura(tramite.getJefaturaTrafico().getJefaturaProvincial());
		imprKo.setIdImpr(null);
		imprKo.setIdContrato(tramite.getContrato().getIdContrato());
		return imprKo;
	}

	private ImprKoVO crearKOEstado(ImprVO imprVO, Long idUsuario, String estado) {
		try {
			ImprKoVO imprKO = getImprKo(imprVO.getNumExpediente(), imprVO.getTipoImpr());
			if (imprKO == null) {
				imprKO = rellenarImprKo(imprVO, estado);
				imprKoDao.guardar(imprKO);
				servicioEvolucionImprKo.guardarEvolucion(rellenarEvolucionImprKo(imprKO, idUsuario, null, estado));
			} else {
				String estadoAnt = imprKO.getEstado();
				imprKO.setEstado(estado);
				imprKoDao.actualizar(imprKO);
				servicioEvolucionImprKo.guardarEvolucion(rellenarEvolucionImprKo(imprKO, idUsuario, estadoAnt, estado));
			}
			return imprKO;
		} catch (Exception e) {
			log.error("Error al guardar el KO");
		}
		return null;
	}

	private EvolucionImprKoVO rellenarEvolucionImprKo(ImprKoVO imprKO, Long idUsuario, String estadoAnt, String estadoNuevo) {
		EvolucionImprKoVO evolucionImprKoVO = new EvolucionImprKoVO();
		evolucionImprKoVO.setIdImprKo(imprKO.getId());
		evolucionImprKoVO.setEstadoAnterior(estadoAnt);
		evolucionImprKoVO.setEstadoNuevo(estadoNuevo);
		evolucionImprKoVO.setIdUsuario(idUsuario);
		evolucionImprKoVO.setTipoImpr(imprKO.getTipoImpr());
		evolucionImprKoVO.setFecha(new Date());
		return evolucionImprKoVO;
	}

	@Override
	@Transactional
	public ImprKoVO getImprKo(BigDecimal numExpediente, String tipoImpr) {
		return imprKoDao.getImprKoTramite(numExpediente, tipoImpr);
	}

	private ImprKoVO rellenarImprKo(ImprVO imprVO, String estado) {
		ImprKoVO imprKo = new ImprKoVO();
		imprKo.setFechaAlta(new Date());
		imprKo.setFechaPresentacion(imprVO.getFechaPresentacion());
		imprKo.setEstado(estado);
		imprKo.setNumExpediente(imprVO.getNumExpediente());
		imprKo.setMatricula(imprVO.getMatricula());
		imprKo.setBastidor(imprVO.getBastidor());
		imprKo.setTipoImpr(imprVO.getTipoImpr());
		imprKo.setTipoTramite(imprVO.getTipoTramite());
		imprKo.setJefatura(imprVO.getJefatura());
		imprKo.setIdImpr(imprVO.getId());
		imprKo.setIdContrato(imprVO.getIdContrato());
		return imprKo;
	}

}