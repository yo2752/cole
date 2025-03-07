package org.gestoresmadrid.oegamImportacion.tasa.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioEvolucionTasaImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTasaImportacionImpl implements ServicioEvolucionTasaImportacion {

	private static final long serialVersionUID = -6730577519865287495L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTasaImportacionImpl.class);

	@Autowired
	private EvolucionTasaDao evolucionTasaDao;

	@Override
	@Transactional
	public void insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, Long idUsuario) {
		try {
			EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
			evolucionTasa.setAccion(accion);
			if (numExpediente != null) {
				evolucionTasa.setNumExpediente(numExpediente);
			} else {
				evolucionTasa.setNumExpediente(null);
			}
			EvolucionTasaPK id = new EvolucionTasaPK();
			id.setCodigoTasa(codigoTasa);
			id.setFechaHora(new Date());
			evolucionTasa.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucionTasa.setUsuario(usuario);
			evolucionTasaDao.guardar(evolucionTasa);
		} catch (Exception e) {
			log.error("Error al guardar la evolución de tasas");
		}
	}
}
