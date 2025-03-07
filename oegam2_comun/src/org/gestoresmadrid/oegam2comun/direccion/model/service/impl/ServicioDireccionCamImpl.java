package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioCamVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaCamVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaCamVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccionCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvinciaCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDireccionCamImpl implements ServicioDireccionCam{

	private static final long serialVersionUID = 4991650752822565404L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDireccionCamImpl.class);

	@Autowired
	private ServicioProvinciaCam servicioProvinciaCam;

	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;

	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly=true)
	public String obtenerNombreMunicipio(String idProvincia, String idMunicipio) {
		try {
			MunicipioCamDto municipioDto = getMunicipio(idMunicipio, idProvincia);
			if (municipioDto != null) {
				return municipioDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre municipio", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly=true)
	public String getIdMunicipioCam(String idProvincia, String idMunicipio) {
		try {
			MunicipioCamVO municipioVO = servicioMunicipioCam.getMunicipio(idMunicipio,idProvincia);
			if (municipioVO != null) {
				return municipioVO.getIdMunicipioCam();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el id del municipio CAM", e);
		}
		return "";
	}

	@Transactional(readOnly=true)
	@Override
	public MunicipioCamDto getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioCamVO municipioVO = servicioMunicipioCam.getMunicipio(idMunicipio,idProvincia);
			if (municipioVO != null) {
				return conversor.transform(municipioVO, MunicipioCamDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public String obtenerNombreProvincia(String idProvincia) {
		try {
			ProvinciaCamDto provinciaDto = getProvincia(idProvincia);
			if (provinciaDto != null) {
				return provinciaDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la provincia", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly=true)
	public ProvinciaCamDto getProvincia(String idProvincia) {
		try {
			ProvinciaCamVO provinciaVO = servicioProvinciaCam.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return conversor.transform(provinciaVO, ProvinciaCamDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public String obtenerNombreTipoVia(String idTipoVia) {
		try {
			TipoViaCamDto tipoViaCamDto = getTipoVia(idTipoVia);
			if (tipoViaCamDto != null) {
				return tipoViaCamDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre del tipo via", e);
		}
		return "";
	}

	@Transactional(readOnly=true)
	@Override
	public TipoViaCamDto getTipoVia(String idTipoVia) {
		try {
			TipoViaCamVO tipoViaCamVO = servicioTipoViaCam.getTipoVia(idTipoVia);
			if (tipoViaCamVO != null) {
				return conversor.transform(tipoViaCamVO, TipoViaCamDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo via", e);
		}
		return null;
	}
}