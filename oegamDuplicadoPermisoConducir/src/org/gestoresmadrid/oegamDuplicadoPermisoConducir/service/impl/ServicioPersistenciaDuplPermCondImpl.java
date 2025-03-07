package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.DuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioEvoDuplicadoPermisoConducir;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioPersistenciaDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaDuplPermCondImpl implements ServicioPersistenciaDuplPermCond {

	private static final long serialVersionUID = -7434100925827047878L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaDuplPermCondImpl.class);

	@Autowired
	DuplicadoPermisoConducirDao duplicadoPermisoConducirDao;

	@Autowired
	ServicioEvoDuplicadoPermisoConducir servicioEvoDuplicadoPermisoConducir;

	@Override
	@Transactional(readOnly = true)
	public DuplicadoPermisoConducirVO getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond) {
		try {
			return duplicadoPermisoConducirDao.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
		} catch (Exception e) {
			log.error("Error al recuperar el duplicado permiso conducir, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public DuplicadoPermisoConducirVO getDuplPermCondPorNumExpediente(BigDecimal numExpediente) {
		try {
			return duplicadoPermisoConducirDao.getDuplPermCondPorNumExpediente(numExpediente);
		} catch (Exception e) {
			log.error("Error al recuperar el duplicado permiso conducir, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoDuplPermCondBean guardarPermisoCond(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, BigDecimal idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (duplicadoPermisoConducirVO.getIdDuplicadoPermCond() == null) {
				BigDecimal numExpediente = duplicadoPermisoConducirDao.generarNumExpediente(duplicadoPermisoConducirVO.getNumColegiado());
				duplicadoPermisoConducirVO.setTipoTramite(TipoTramiteInterga.Duplicado_Permiso_Conducir.getTipo());
				duplicadoPermisoConducirVO.setNumExpediente(numExpediente);
				duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Iniciado.getValorEnum());
				duplicadoPermisoConducirVO.setEstadoImpresion(EstadoTramitesInterga.Iniciado.getValorEnum());
				duplicadoPermisoConducirVO.setFechaUltModif(new Date());
				duplicadoPermisoConducirVO.setFechaAlta(new Date());
				if (StringUtils.isNotBlank(duplicadoPermisoConducirVO.getDoiTitular())) {
					duplicadoPermisoConducirVO.setDoiTitular(duplicadoPermisoConducirVO.getDoiTitular().toUpperCase());
				}
				Long idDuplicadoPermisoCond = (Long) duplicadoPermisoConducirDao.guardar(duplicadoPermisoConducirVO);
				duplicadoPermisoConducirVO.setIdDuplicadoPermCond(idDuplicadoPermisoCond);
				servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermisoCond, null, EstadoTramitesInterga.Iniciado.getValorEnum(), idUsuario.longValue(), TipoActualizacion.CRE.getValorEnum());
			} else {
				duplicadoPermisoConducirVO.setFechaUltModif(new Date());
				if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
					servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Iniciado
							.getValorEnum(), idUsuario.longValue(), TipoActualizacion.MOD.getValorEnum());
					duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Iniciado.getValorEnum());
					if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoImpresion())) {
						servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), duplicadoPermisoConducirVO.getEstadoImpresion(), EstadoTramitesInterga.Iniciado
								.getValorEnum(), idUsuario.longValue(), TipoActualizacion.MOD.getValorEnum());
						duplicadoPermisoConducirVO.setEstadoImpresion(EstadoTramitesInterga.Iniciado.getValorEnum());
					}
				}
				duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);
			}
			result.setIdDuplicadoPermisoCond(duplicadoPermisoConducirVO.getIdDuplicadoPermCond());
			result.setNumExpediente(duplicadoPermisoConducirVO.getNumExpediente());
		} catch (Exception e) {
			log.error("Error al guardar el duplicado permiso conducir: " + duplicadoPermisoConducirVO.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, duplicadoPermisoConducirVO
					.getNumExpediente().toString());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		if (result != null && result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public void actualizarEstado(Long idDuplicadoPermisoCond, String estadoNuevo) {
		DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = duplicadoPermisoConducirDao.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
		duplicadoPermisoConducirVO.setEstado(estadoNuevo);
		duplicadoPermisoConducirVO.setFechaUltModif(new Date());
		duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);
	}

	@Override
	@Transactional
	public void actualizarEstadoImpresion(Long idDuplicadoPermisoCond, String estadoNuevo) {
		DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = duplicadoPermisoConducirDao.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
		duplicadoPermisoConducirVO.setFechaUltModif(new Date());
		duplicadoPermisoConducirVO.setEstadoImpresion(estadoNuevo);
		duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);

	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeTramiteDoiPendienteDgt(String doiTitular, String estado) {
		List<DuplicadoPermisoConducirVO> lista = duplicadoPermisoConducirDao.getListaDuplicadoPermisosCondDoiPorEstado(doiTitular, estado);
		if (lista != null && !lista.isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional
	public void eliminarDuplPermConducir(Long idDuplicadoPermisoCond) {
		DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = duplicadoPermisoConducirDao.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
		duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Anulado.getValorEnum());
		duplicadoPermisoConducirVO.setFechaUltModif(new Date());
		duplicadoPermisoConducirVO.setCodigoTasa(null);
		duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);

	}

	@Override
	@Transactional
	public void actualizar(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);
	}

	@Override
	@Transactional
	public void actualizarEstadosYRespuesta(Long idDuplicadoPermCond, String estadoNuevo, String estadoImpNuevo, String respuesta) {
		DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = duplicadoPermisoConducirDao.getDuplicadoPermisoConducir(idDuplicadoPermCond);
		duplicadoPermisoConducirVO.setFechaUltModif(new Date());
		duplicadoPermisoConducirVO.setEstadoImpresion(estadoImpNuevo);
		duplicadoPermisoConducirVO.setEstado(estadoNuevo);
		duplicadoPermisoConducirVO.setRespuesta(respuesta);
		duplicadoPermisoConducirDao.actualizar(duplicadoPermisoConducirVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DuplicadoPermisoConducirVO> consultarDuplicadosPermConducir() {
		try {
			return duplicadoPermisoConducirDao.consultarDuplicadosPermConducir();
		} catch (Exception e) {
			log.error("Error al consultar los duplicados permisos conducir, error: ", e);
		}
		return null;
	}
}