package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioMunicipioImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMunicipioImportacionImpl implements ServicioMunicipioImportacion {

	private static final long serialVersionUID = 1640180732220219743L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMunicipioImportacionImpl.class);

	@Autowired
	private MunicipioDao municipioDao;

	@Transactional
	@Override
	public MunicipioVO getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = municipioDao.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio, error: ", e);
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
	@Transactional(readOnly = true)
	public MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia) {
		try {
			if (nombreMunicipio != null && !nombreMunicipio.isEmpty()) {
				MunicipioVO municipioBBDD = municipioDao.getMunicipioPorNombre(nombreMunicipio.toUpperCase(), idProvincia);
				if (municipioBBDD != null) {
					return municipioBBDD;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el municipio por su nombre, error: ", e);
		}
		return null;
	}
}
