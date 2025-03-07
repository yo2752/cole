package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.registradores.model.dao.SociedadCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.SociedadCargoVO;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioSociedadCargo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.SociedadCargoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioSociedadCargoImpl implements ServicioSociedadCargo {

	private static final long serialVersionUID = 3184057690525267072L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioSociedadCargoImpl.class);

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private SociedadCargoDao sociedadCargoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public SociedadCargoDto getSociedadCargo(String cif, String numColegiado, String nifCargo, String codigoCargo) {
		SociedadCargoDto result = null;
		try {
			if (cif != null && !cif.isEmpty()) {
				SociedadCargoVO sociedadCargo = sociedadCargoDao.getSociedadCargo(cif, numColegiado, nifCargo, codigoCargo);
				if (sociedadCargo != null) {
					result = conversor.transform(sociedadCargo, SociedadCargoDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar la sociedad cargo: " + cif, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarSociedadCargo(SociedadCargoDto sociedadCargoDto, BigDecimal numExpediente, String numColegiado, String cifSociedad, String tipoTramite, BigDecimal idUsuario) {
		sociedadCargoDto.getPersonaCargo().setNumColegiado(numColegiado);
		ResultBean result = servicioPersona.guardarCargo(sociedadCargoDto.getPersonaCargo(), numExpediente, tipoTramite, idUsuario);
		if (!result.getError()) {
			sociedadCargoDto.setCifSociedad(cifSociedad);
			sociedadCargoDto.setNumColegiado(numColegiado);
			sociedadCargoDto.setNifCargo(sociedadCargoDto.getPersonaCargo().getNif());
			SociedadCargoVO sociedadCargoVO = conversor.transform(sociedadCargoDto, SociedadCargoVO.class);
			modificarDatos(sociedadCargoVO);
			sociedadCargoDao.guardarOActualizar(sociedadCargoVO);
		}
		return result;
	}

	private void modificarDatos(SociedadCargoVO sociedadCargoVO) {
		if (sociedadCargoVO.getId().getNifCargo() != null) {
			sociedadCargoVO.getId().setNifCargo(sociedadCargoVO.getId().getNifCargo().toUpperCase());
		}
		if (sociedadCargoVO.getId().getCifSociedad() != null) {
			sociedadCargoVO.getId().setCifSociedad(sociedadCargoVO.getId().getCifSociedad().toUpperCase());
		}
		if (sociedadCargoVO.getIndefinido() == null || sociedadCargoVO.getIndefinido().isEmpty()) {
			sociedadCargoVO.setIndefinido("N");
		}
		if (sociedadCargoVO.getFechaValidezInicial() == null) {
			sociedadCargoVO.setFechaValidezInicial(new Date());
		}
	}

	public SociedadCargoDao getSociedadCargoDao() {
		return sociedadCargoDao;
	}

	public void setSociedadCargoDao(SociedadCargoDao sociedadCargoDao) {
		this.sociedadCargoDao = sociedadCargoDao;
	}
}
