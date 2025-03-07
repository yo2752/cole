package org.gestoresmadrid.oegamPermisoInternacional.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoDeclaracion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.PermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioEvolucionPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPersistenciaPermInter;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaPermInterImpl implements ServicioPersistenciaPermInter {

	private static final long serialVersionUID = -7434100925827047878L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaPermInterImpl.class);

	@Autowired
	ServicioEvolucionPermisoInternacional servicioEvolucionPermisoInternacional;

	@Autowired
	PermisoInternacionalDao permisoInternacionalDao;

	@Override
	@Transactional
	public void eliminarPermisoIntern(Long idPermiso) {
		PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacional(idPermiso);
		permisoInternacionalVO.setEstado(EstadoTramitesInterga.Anulado.getValorEnum());
		permisoInternacionalVO.setFechaUltModif(new Date());
		permisoInternacionalVO.setCodigoTasa(null);
		permisoInternacionalDao.actualizar(permisoInternacionalVO);

	}

	@Override
	@Transactional
	public void actualizarSubidaDoc(Long idPermiso, String numReferencia) {
		PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacional(idPermiso);
		permisoInternacionalVO.setFechaUltModif(new Date());
		permisoInternacionalVO.setDocumentacionAportada("S");
		permisoInternacionalVO.setEstadoImpresion(EstadoTramitesInterga.Doc_Subida.getValorEnum());
		permisoInternacionalVO.setNumReferencia(numReferencia);
		permisoInternacionalDao.actualizar(permisoInternacionalVO);
	}

	@Override
	@Transactional
	public void actualizar(PermisoInternacionalVO permisoInternacionalVO) {
		permisoInternacionalDao.actualizar(permisoInternacionalVO);
	}

	@Override
	@Transactional
	public void actualizarEstadosYRespuesta(Long idPermisoIntern, String estadoNuevo, String estadoImpNuevo, String respuesta) {
		PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacional(idPermisoIntern);
		permisoInternacionalVO.setFechaUltModif(new Date());
		permisoInternacionalVO.setEstadoImpresion(estadoImpNuevo);
		permisoInternacionalVO.setEstado(estadoNuevo);
		permisoInternacionalVO.setRespuesta(respuesta);
		permisoInternacionalDao.actualizar(permisoInternacionalVO);
	}

	@Override
	@Transactional
	public void actualizarEstado(Long idPermisoIntern, String estadoNuevo) {
		PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacional(idPermisoIntern);
		permisoInternacionalVO.setEstado(estadoNuevo);
		permisoInternacionalVO.setFechaUltModif(new Date());
		permisoInternacionalDao.actualizar(permisoInternacionalVO);
	}

	@Override
	@Transactional
	public void actualizarEstadoImpresion(Long idPermisoIntern, String estadoNuevo) {
		PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacional(idPermisoIntern);
		permisoInternacionalVO.setFechaUltModif(new Date());
		permisoInternacionalVO.setEstadoImpresion(estadoNuevo);
		permisoInternacionalDao.actualizar(permisoInternacionalVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeTramiteDoiPendienteDgt(String doiTitular, String estado) {
		List<PermisoInternacionalVO> listaPermisosDoi = permisoInternacionalDao.getListaPermisosDoiPorEstado(doiTitular, estado);
		if (listaPermisosDoi != null && !listaPermisosDoi.isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public PermisoInternacionalVO getPermisoInternacional(Long idPermiso) {
		try {
			return permisoInternacionalDao.getPermisoInternacional(idPermiso);
		} catch (Exception e) {
			log.error("Error al recuperar el permiso internacional, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PermisoInternacionalVO> consultarPermisosInternacionales() {
		try {
			return permisoInternacionalDao.consultarPermisosInternacionales();
		} catch (Exception e) {
			log.error("Error al consultar los permisos internacionales, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PermisoInternacionalVO getPermisoInternacionPorExpediente(BigDecimal numExpediente) {
		try {
			return permisoInternacionalDao.getPermisoInternacionalPorExpediente(numExpediente);
		} catch (Exception e) {
			log.error("Error al recuperar el permiso internacional, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoPermInterBean guardarPermiso(PermisoInternacionalVO permisoInternacionalVO, BigDecimal idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (permisoInternacionalVO.getIdPermiso() == null) {
				BigDecimal numExpediente = permisoInternacionalDao.generarNumExpediente(permisoInternacionalVO.getNumColegiado());
				permisoInternacionalVO.setTipoTramite(TipoTramiteInterga.Permiso_Internacional.getTipo());

				permisoInternacionalVO.setNumExpediente(numExpediente);
				permisoInternacionalVO.setDocumentacionAportada("N");
				permisoInternacionalVO.setEstadoDeclaracion(EstadoDeclaracion.No_Enviado.getValorEnum());
				permisoInternacionalVO.setEstado(EstadoTramitesInterga.Iniciado.getValorEnum());
				permisoInternacionalVO.setEstadoImpresion(EstadoTramitesInterga.Iniciado.getValorEnum());
				permisoInternacionalVO.setFechaUltModif(new Date());
				permisoInternacionalVO.setFechaAlta(new Date());
				if (StringUtils.isNotBlank(permisoInternacionalVO.getDoiTitular())) {
					permisoInternacionalVO.setDoiTitular(permisoInternacionalVO.getDoiTitular().toUpperCase());
				}
				Long idPermiso = (Long) permisoInternacionalDao.guardar(permisoInternacionalVO);
				permisoInternacionalVO.setIdPermiso(idPermiso);
				servicioEvolucionPermisoInternacional.guardar(idPermiso, null, EstadoTramitesInterga.Iniciado.getValorEnum(), idUsuario.longValue(), TipoActualizacion.CRE.getValorEnum());
			} else {
				permisoInternacionalVO.setFechaUltModif(new Date());
				if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(permisoInternacionalVO.getEstado()) && !EstadoTramitesInterga.Declaracion_Firmada.getValorEnum().equals(permisoInternacionalVO
						.getEstado())) {
					servicioEvolucionPermisoInternacional.guardar(permisoInternacionalVO.getIdPermiso(), permisoInternacionalVO.getEstado(), EstadoTramitesInterga.Declaracion_Firmada.getValorEnum(),
							idUsuario.longValue(), TipoActualizacion.MOD.getValorEnum());
					permisoInternacionalVO.setEstado(EstadoTramitesInterga.Declaracion_Firmada.getValorEnum());
					if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(permisoInternacionalVO.getEstadoImpresion())) {
						servicioEvolucionPermisoInternacional.guardar(permisoInternacionalVO.getIdPermiso(), permisoInternacionalVO.getEstadoImpresion(), EstadoTramitesInterga.Iniciado.getValorEnum(),
								idUsuario.longValue(), TipoActualizacion.MOD.getValorEnum());
						permisoInternacionalVO.setEstadoImpresion(EstadoTramitesInterga.Iniciado.getValorEnum());
					}
				}
				permisoInternacionalDao.actualizar(permisoInternacionalVO);
			}
			result.setIdPermiso(permisoInternacionalVO.getIdPermiso());
			result.setNumExpediente(permisoInternacionalVO.getNumExpediente());
		} catch (Exception e) {
			log.error("Error al guardar el permiso internacional: " + permisoInternacionalVO.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, permisoInternacionalVO.getNumExpediente()
					.toString());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		if (result != null && result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}
}