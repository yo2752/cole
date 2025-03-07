package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.AplicacionDao;
import org.gestoresmadrid.core.general.model.dao.ContratoAplicacionDao;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionPK;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service(value="servicioAplicacion")
public class ServicioAplicacionImpl implements ServicioAplicacion {

	private static final long serialVersionUID = 4541566862418078115L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioAplicacionImpl.class);

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private AplicacionDao aplicacionDao;

	@Autowired
	private ContratoAplicacionDao contratoAplicacionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional(readOnly = true)
	public List<AplicacionVO> getAplicacionByCodigo(String codigoAplicacion) {
		return aplicacionDao.getAplicacionByCodigo(codigoAplicacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AplicacionDto> getAplicaciones() {
		List<AplicacionVO> aplicaciones = aplicacionDao.getAplicaciones();
		if (aplicaciones != null && !aplicaciones.isEmpty()) {
			return conversor.transform(aplicaciones, AplicacionDto.class);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContratoAplicacionVO> getAplicacionesPorContrato(Long idContrato) {
		return contratoAplicacionDao.getAplicacionesPorContrato(idContrato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getCodigosAplicacionPorContrato(Long idContrato) {
		return contratoAplicacionDao.getCodigosAplicacionPorContrato(idContrato);
	}

	@Override
	@Transactional
	public ResultBean asociarAplicacionContrato(String codigoAplicacion, BigDecimal idContrato, String numColegiado, boolean asignar) {
		ResultBean result = new ResultBean();
		try {
			if (asignar) {
				ContratoAplicacionVO contratoAplicacionVO = new ContratoAplicacionVO();
				ContratoAplicacionPK id = new ContratoAplicacionPK();
				id.setCodigoAplicacion(codigoAplicacion);
				id.setIdContrato(idContrato.longValue());
				contratoAplicacionVO.setId(id);
				contratoAplicacionDao.guardarOActualizar(contratoAplicacionVO);

				desasignarPermisosDefecto(codigoAplicacion, idContrato, numColegiado);

				desasignarPermisosImpr(idContrato, numColegiado);
			} else {
				servicioPermisos.asignarPermiso(idContrato, "", codigoAplicacion, numColegiado, 0L);
				ContratoAplicacionVO contratoAplicacionVO = new ContratoAplicacionVO();
				ContratoAplicacionPK id = new ContratoAplicacionPK();
				id.setCodigoAplicacion(codigoAplicacion);
				id.setIdContrato(idContrato.longValue());
				contratoAplicacionVO.setId(id);
				contratoAplicacionDao.borrar(contratoAplicacionVO);
			}
		} catch (Exception e) {
			log.error("Error al asociar una aplicacion con el contrato", e);
			result.setError(true);
			result.setMensaje("Error al asociar una aplicacion con el contrato: " + e.getMessage());
		}
		return result;
	}

	private void desasignarPermisosDefecto(String codigoAplicacion, BigDecimal idContrato, String numColegiado) {
		if ("OEGAM_TRAF".equals(codigoAplicacion)) {
			String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.trafico");
			if (permisosPorDefecto != null) {
				String[] permisos = permisosPorDefecto.split(",");
				for (String permiso : permisos) {
					servicioPermisos.desasignarPermiso(idContrato, permiso, codigoAplicacion, numColegiado, 0L);
				}
			}
		}
		if ("OEGAM_SEGSOC".equals(codigoAplicacion)) {
			String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.seguridadsocial");
			if (permisosPorDefecto != null) {
				String[] permisos = permisosPorDefecto.split(",");
				for (String permiso : permisos) {
					servicioPermisos.desasignarPermiso(idContrato, permiso, codigoAplicacion, numColegiado, 0L);
				}
			}
		}
		if ("OEGAM_GENE".equals(codigoAplicacion)) {
			String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.general");
			if (permisosPorDefecto != null) {
				String[] permisos = permisosPorDefecto.split(",");
				for (String permiso : permisos) {
					servicioPermisos.desasignarPermiso(idContrato, permiso, codigoAplicacion, numColegiado, 0L);
				}
			}
		}
		if ("OEGAM_DIG".equals(codigoAplicacion)) {
			String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.digitalizacion");
			if (permisosPorDefecto != null) {
				String[] permisos = permisosPorDefecto.split(",");
				for (String permiso : permisos) {
					servicioPermisos.desasignarPermiso(idContrato, permiso, codigoAplicacion, numColegiado, 0L);
				}
			}
		}
	}

	private void desasignarPermisosImpr(BigDecimal idContrato, String numColegiado) {
		servicioPermisos.desasignarPermiso(idContrato, "OA993", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA994", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA995", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA996", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA997", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA998", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA999", "OEGAM_ADMIN", numColegiado, 0L);
		servicioPermisos.desasignarPermiso(idContrato, "OA9910", "OEGAM_ADMIN", numColegiado, 0L);
	}
}
