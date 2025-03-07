package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioEvolucionTasa;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTasaImpl implements ServicioEvolucionTasa {

	private static final long serialVersionUID = 2314081917531413264L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTasaImpl.class);

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private EvolucionTasaDao evolucionTasaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Transactional
	@Override
	public ResultBean insertarEvolucionTasa(TasaVO tasa, String accion) {
		EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
		ResultBean resultado = new ResultBean();
		try {
			resultado.setError(false);
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
			evolucionTasa.setAccion(accion);
			if (tasa.getNumExpediente() != null) {
				evolucionTasa.setNumExpediente(tasa.getNumExpediente());
			} else {
				evolucionTasa.setNumExpediente(null);
			}
			evolucionTasa.setTasa(tasa);
			EvolucionTasaPK id = new EvolucionTasaPK();
			id.setCodigoTasa(tasa.getCodigoTasa());
			id.setFechaHora(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionTasa.setId(id);
			evolucionTasa.setUsuario(conversor.transform(usuario, UsuarioVO.class));

			evolucionTasaDao.guardar(evolucionTasa);
		} catch (Exception e) {
			log.error("Error ak guardar la evolución de tasas");
		} finally {
			evolucionTasaDao.evict(tasa);
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, String motivo, Date fecha, BigDecimal idUsuario){
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
			evolucionTasa.setAccion(accion);
			evolucionTasa.setMotivoBloqueo(motivo);
			if (numExpediente != null) {
				evolucionTasa.setNumExpediente(numExpediente);
			} else {
				evolucionTasa.setNumExpediente(null);
			}
			EvolucionTasaPK id = new EvolucionTasaPK();
			id.setCodigoTasa(codigoTasa);
			id.setFechaHora(fecha);
			evolucionTasa.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionTasa.setUsuario(usuario);
			evolucionTasaDao.guardar(evolucionTasa);
		} catch (Exception e) {
			log.error("Error al guardar la evolución de tasas");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar la evolución de tasas");
		}
		return resultado;
	}

}
