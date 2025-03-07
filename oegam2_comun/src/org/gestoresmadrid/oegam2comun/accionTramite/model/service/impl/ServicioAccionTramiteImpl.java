package org.gestoresmadrid.oegam2comun.accionTramite.model.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.accionTramite.model.dao.AccionTramiteDao;
import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramitePK;
import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioAccionTramiteImpl implements ServicioAccionTramite {

	@Autowired
	private AccionTramiteDao accionTramiteDaoImpl;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioAccionTramiteImpl.class);

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public void crearAccionTramite(BigDecimal idUsuario, BigDecimal idExpediente, String accion, Date fechaFin, String respuesta) throws OegamExcepcion {
		try {
			AccionTramiteVO accionTramiteVO = setAccionTramite(idUsuario, idExpediente, accion, fechaFin, respuesta);

			List<AccionTramiteVO> listaAccionTramite = accionTramiteDaoImpl.existeAccionTramiteSinFechaFin(accionTramiteVO);

			if (listaAccionTramite != null && !listaAccionTramite.isEmpty()) {
				accionTramiteDaoImpl.guardar(accionTramiteVO);
			}
		}catch(Exception e){
			log.error("Error a la hora de crear la accion para el tramite");
			throw new OegamExcepcion("Error a la hora de crear la accion para el tramite");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void crearCerrarAccionTramite(BigDecimal idUsuario, BigDecimal idExpediente, String accion, String respuesta) {
		if (idUsuario == null || idExpediente == null || accion == null || accion.isEmpty()) {
			return;
		}
		AccionTramiteVO accionTramiteVO = setAccionTramite(idUsuario, idExpediente, accion, null, respuesta);
		List<AccionTramiteVO> listaAccionTramite = accionTramiteDaoImpl.existeAccionTramiteSinFechaFin(accionTramiteVO);

		Calendar calendar = Calendar.getInstance();
		if (listaAccionTramite == null || listaAccionTramite.isEmpty()) {
			// No existe Acción previa, así que se guarda con la fecha de fin ya actualizada
			accionTramiteVO.setFechaFin(calendar.getTime());
			accionTramiteDaoImpl.guardar(accionTramiteVO);
		} else {
			// Se actualiza la fecha de fin de los existentes
			for (AccionTramiteVO accionExistente : listaAccionTramite) {
				accionExistente.setFechaFin(calendar.getTime());
				accionTramiteDaoImpl.guardar(accionExistente);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cerrarAccionTramite(BigDecimal idUsuario, BigDecimal idExpediente, String accion, String respuesta) {
		if (idUsuario == null || idExpediente == null || accion == null || accion.isEmpty()) {
			return;
		}
		AccionTramiteVO accionTramiteVO = setAccionTramite(idUsuario, idExpediente, accion, null, respuesta);
		List<AccionTramiteVO> listaAccionTramite = accionTramiteDaoImpl.existeAccionTramiteSinFechaFin(accionTramiteVO);

		if (listaAccionTramite != null && !listaAccionTramite.isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			// Se actualiza la fecha de fin de los existentes
			for (AccionTramiteVO accionExistente : listaAccionTramite) {
				accionExistente.setFechaFin(calendar.getTime());
				accionTramiteDaoImpl.guardar(accionExistente);
			}
		}
	}

	@Override
	public AccionTramiteVO setAccionTramite(BigDecimal idUsuario, BigDecimal numExpediente, String accion,
			Date fechaFin, String respuesta) {
		AccionTramiteVO accionTramiteVO = new AccionTramiteVO();
		AccionTramitePK id = new AccionTramitePK();
		id.setAccion(accion);
		if (numExpediente != null) {
			id.setNumExpediente(numExpediente.longValue());
		}
		id.setFechaInicio(Calendar.getInstance().getTime());

		accionTramiteVO.setId(id);

		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(idUsuario.longValue());
		accionTramiteVO.setUsuario(usuarioVO);

		accionTramiteVO.setId(id);

		if (fechaFin != null) {
			accionTramiteVO.setFechaFin(fechaFin);
		}

		if (respuesta != null) {
			accionTramiteVO.setRespuesta(respuesta);
		}

		return accionTramiteVO;
	}

	@Override
	@Transactional
	public void guardarAccion(AccionTramiteVO accionTramiteVO) {
		accionTramiteDaoImpl.guardar(accionTramiteVO);
	}

}