package org.gestoresmadrid.oegam2comun.paises.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.paises.model.dao.PaisDao;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPaisImpl implements ServicioPais {

	private static final long serialVersionUID = -1200391809583821411L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPaisImpl.class);

	@Autowired
	private PaisDao paisDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public PaisDto getPais(String idPais) {
		try {
			PaisVO paisVO = paisDao.getPais(idPais);
			if (paisVO != null) {
				return conversor.transform(paisVO, PaisDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el pais", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PaisDto getIdPaisPorSigla(String sigla) {
		try {
			PaisVO paisVO = paisDao.getPaisPorSigla(sigla);
			if (paisVO != null) {
				return conversor.transform(paisVO, PaisDto.class);
			}
		} catch (Exception e) {

			log.error("Error al recuperar el pais", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaisDto> listaPaises(BigDecimal tipoPais) {
		try {
			List<PaisVO> paises = paisDao.listaPaises(tipoPais);
			if (paises != null && !paises.isEmpty()) {
				return conversor.transform(paises, PaisDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de paises", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaisDto> paises() {
		try {
			List<PaisVO> paises = paisDao.paises();
			if (paises != null && !paises.isEmpty()) {
				return conversor.transform(paises, PaisDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de pueblos", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PaisDto> paisesPorNombre(String pais) {
		try {
			List<PaisVO> paises = paisDao.paisesPorNombre(pais);
			if (paises != null && !paises.isEmpty()) {
				return conversor.transform(paises, PaisDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de paises", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PaisDto getPaisPorSiglasImportDgt(String sigla) {
		if (sigla != null && !sigla.isEmpty()) {
			PaisVO paisVO = paisDao.getPaisPorSigla(sigla);
			if (paisVO != null) {
				return conversor.transform(paisVO, PaisDto.class);
			}
		}
		return null;
	}
}
