package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ViaDao;
import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioViaImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioViaImportacionImpl implements ServicioViaImportacion {

	private static final long serialVersionUID = 8023127029035785374L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioViaImportacionImpl.class);

	@Autowired
	private ViaDao viaDao;

	@Transactional
	@Override
	public ViaVO getVia(String idProvincia, String idMunicipio, String idTipoVia, String via) {
		try {
			ViaVO viaVO = viaDao.getVia(idProvincia, idMunicipio, idTipoVia, via);
			if (viaVO != null) {
				return viaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la vía", e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<String> listadoViasUnicasPorTipoVia(String idProvincia) {
		try {
			List<String> listaVias = viaDao.listadoViasUnicasPorTipoVia(idProvincia);
			if (listaVias != null && listaVias.size() > 0) {
				return listaVias;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado único de vías", e);
		}
		return null;
	}

}
