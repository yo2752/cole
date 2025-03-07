package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.general.model.dao.OficinaLiquidadora620Dao;
import org.gestoresmadrid.core.general.model.vo.OficinaLiquidadora620VO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.model.service.ServicioOficinaLiquidadora620;
import org.gestoresmadrid.oegam2comun.view.dto.OficinaLiquidadora620Dto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioOficinaLiquidadora620Impl implements ServicioOficinaLiquidadora620 {

	private static final long serialVersionUID = -8495392782125357300L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioOficinaLiquidadora620Impl.class);

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private OficinaLiquidadora620Dao oficinaLiquidadora620Dao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public List<OficinaLiquidadora620Dto> listadoOficinasLiquidadoras(String idMunicipio) {
		List<OficinaLiquidadora620Dto> listadoOficinas = new ArrayList<>();
		try {
			List<MunicipioVO> listaMunicipios = servicioMunicipio.listadoOficinasLiquidadoras(idMunicipio);
			if (listaMunicipios != null && !listaMunicipios.isEmpty()) {
				for (MunicipioVO municipio : listaMunicipios) {
					if (municipio.getOficinaLiquidadora() != null && !municipio.getOficinaLiquidadora().isEmpty()) {
						List<OficinaLiquidadora620VO> lista = oficinaLiquidadora620Dao.listadoOficinasLiquidadoras(municipio.getOficinaLiquidadora());
						if (lista != null && !lista.isEmpty()) {
							listadoOficinas.addAll(conversor.transform(lista, OficinaLiquidadora620Dto.class));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de oficinas liquidadoras", e);
		}
		return listadoOficinas;
	}

	@Override
	@Transactional
	public List<OficinaLiquidadora620VO> getOficinasLiquidadoras(String oficinaLiquidadora) {
		try {
			return oficinaLiquidadora620Dao.listadoOficinasLiquidadoras(oficinaLiquidadora);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de oficinas liquidadoras", e);
			return null;
		}
	}

	public ServicioMunicipio getServicioMunicipio() {
		return servicioMunicipio;
	}

	public void setServicioMunicipio(ServicioMunicipio servicioMunicipio) {
		this.servicioMunicipio = servicioMunicipio;
	}

	public OficinaLiquidadora620Dao getOficinaLiquidadora620Dao() {
		return oficinaLiquidadora620Dao;
	}

	public void setOficinaLiquidadora620Dao(OficinaLiquidadora620Dao oficinaLiquidadora620Dao) {
		this.oficinaLiquidadora620Dao = oficinaLiquidadora620Dao;
	}
}