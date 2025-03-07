package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ViaDao;
import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioViaImpl implements ServicioVia {

	private static final long serialVersionUID = 4281249928859617542L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioViaImpl.class);

	@Autowired
	private ViaDao viaDao;

	@Autowired
	private Conversor conversor;

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
			if (listaVias != null && !listaVias.isEmpty()) {
				return listaVias;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado único de vías", e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<ViaDto> listadoVias(String idProvincia, String idMunicipio, String idTipoVia) {
		try {
			return conversor.transform(viaDao.listadoVias(idProvincia, idMunicipio, idTipoVia), ViaDto.class);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de vías", e);
		}
		return null;
	}

	public ViaDao getViaDao() {
		return viaDao;
	}

	public void setViaDao(ViaDao viaDao) {
		this.viaDao = viaDao;
	}
}