package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.MarcaDgtDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMarcaDgtImpl implements ServicioMarcaDgt {

	private static final long serialVersionUID = -2783678355723793540L;

	private static final String ERROR_RECUPERAR_MARCA = "Error al recuperar la marca";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMarcaDgtImpl.class);

	@Autowired
	private MarcaDgtDao marcaDgtDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtVO getMarcaDgtCodigo(Long codigoMarca) {
		try {
			return marcaDgtDao.getMarcaDgtCodigo(codigoMarca);
		} catch (Exception e) {
			log.error(ERROR_RECUPERAR_MARCA, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw) {
		try {
			MarcaDgtVO marcaDgt = marcaDgtDao.getMarcaDgt(codigoMarca, marca, versionMatw);
			if (marcaDgt != null) {
				return marcaDgt;
			}
		} catch (Exception e) {
			log.error(ERROR_RECUPERAR_MARCA, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, ArrayList<Integer> version) {
		try {
			MarcaDgtVO marcaDgt = marcaDgtDao.getMarcaDgt(codigoMarca, marca, version);
			if (marcaDgt != null) {
				return marcaDgt;
			}
		} catch (Exception e) {
			log.error(ERROR_RECUPERAR_MARCA, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtDto getMarcaDgtDto(String codigoMarca, String marca, Boolean versionMatw) {
		try {
			MarcaDgtVO marcaDgt = marcaDgtDao.getMarcaDgt(codigoMarca, marca, versionMatw);
			if (marcaDgt != null) {
				return conversor.transform(marcaDgt, MarcaDgtDto.class);
			}
		} catch (Exception e) {
			log.error(ERROR_RECUPERAR_MARCA, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<MarcaDgtDto> listaMarcas(String nombreMarca, Boolean versionMatw) {
		try {
			return conversor.transform(marcaDgtDao.listaMarcas(nombreMarca, versionMatw), MarcaDgtDto.class);
		} catch (Exception e) {
			log.error("Error al recuperar las marcas", e);
		}
		return null;
	}

	public MarcaDgtDao getMarcaDgtDao() {
		return marcaDgtDao;
	}

	public void setMarcaDgtDao(MarcaDgtDao marcaDgtDao) {
		this.marcaDgtDao = marcaDgtDao;
	}

	@Override
	@Transactional
	public MarcaDgtVO addMarca(String marca, Long version) {
		String marcaSinEditar = "";
		MarcaDgtVO marcaVO = new MarcaDgtVO();

		marcaVO.setMarca(marca);
		marcaSinEditar = formatearMarcaaMarcaSinEditar(marca);

		marcaVO.setMarcaSinEditar(marcaSinEditar);
		marcaVO.setVersion(version);
		marcaDgtDao.guardar(marcaVO);

		return marcaVO;
	}

	@Override
	@Transactional
	public MarcaDgtVO updateMarca(MarcaDgtVO marcaVO) {
		if (marcaVO != null) {
			marcaDgtDao.actualizar(marcaVO);
		}
		return marcaVO;
	}

	@Override
	@Transactional
	public MarcaDgtVO saveOrUpdateMarca(MarcaDgtVO marcaVO) {
		if (marcaVO != null) {
			marcaDgtDao.guardarOActualizar(marcaVO);
		}
		return marcaVO;
	}

	private String formatearMarcaaMarcaSinEditar(String marca) {
		String marcaSinEditar = "";
		marcaSinEditar = marca.replace(" ", "")
		.replace("&", "")
		.replace("'", "")
		.replace("(", "")
		.replace(")", "")
		.replace("+", "")
		.replace(",", "")
		.replace("-", "")
		.replace(".", "")
		.replace("/", "")
		.replace(";", "")
		.replace("=", "")
		.replace("Ä", "")
		.replace("Ö", "")
		.replace("Ü", "")
		.replace("Ñ", "")
		.replace("Ë", "")
		.replace("Ï", "");

		return marcaSinEditar;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MarcaDgtVO> getMarca(String marca) {
		return marcaDgtDao.getMarcaDgt(marca);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCodigoFromMarca(String marca) {
		return marcaDgtDao.getCodigoFromMarca(marca);
	}
}