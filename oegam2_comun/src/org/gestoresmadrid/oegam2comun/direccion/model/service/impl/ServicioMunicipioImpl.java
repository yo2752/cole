package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMunicipioImpl implements ServicioMunicipio {

	private static final long serialVersionUID = -3756265412344454039L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMunicipioImpl.class);

	@Autowired
	private MunicipioDao municipioDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public MunicipioVO getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = municipioDao.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public MunicipioDto getMunicipioDto(String idProvincia, String idMunicipio) {
		try {
			MunicipioVO municipioVO = getMunicipio(idMunicipio, idProvincia);
			if(municipioVO != null){
				return conversor.transform(municipioVO, MunicipioDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<MunicipioDto> listaMunicipios(String idProvincia) {
		try {
			List<MunicipioVO> listaVos = municipioDao.listaMunicipios(idProvincia);
			if (listaVos != null && listaVos.size() > 0) {
				return conversor.transform(listaVos, MunicipioDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar los municipios", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<MunicipioVO> listadoOficinasLiquidadoras(String idMunicipio) {
		try {
			List<MunicipioVO> listaVos = municipioDao.listaOficinasLiquidadoras(idMunicipio);
			if (listaVos != null && listaVos.size() > 0) {
				return listaVos;
			}
		} catch (Exception e) {
			log.error("Error al recuperar las ofcinas liquidadoras", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public MunicipioDto getMunicipioPorNombre(String nombreMunicipio, String idProvincia) {
		try {
			if(nombreMunicipio != null && !nombreMunicipio.isEmpty()){
				MunicipioVO municipioBBDD = municipioDao.getMunicipioPorNombre(nombreMunicipio.toUpperCase(),idProvincia);
				if(municipioBBDD != null){
					return conversor.transform(municipioBBDD, MunicipioDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el municipio por su nombre, error: ",e);
		}
		return null;
	}

	public MunicipioDao getMunicipioDao() {
		return municipioDao;
	}

	public void setMunicipioDao(MunicipioDao municipioDao) {
		this.municipioDao = municipioDao;
	}
}
