package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.FabricanteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioFabricanteImpl implements ServicioFabricante {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFabricanteImpl.class);

	@Autowired
	private FabricanteDao fabricanteDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public FabricanteVO getFabricante(String fabricante) {
		try {
			FabricanteVO fabricanteVO = fabricanteDao.getFabricante(fabricante);
			if (fabricanteVO != null) {
				return fabricanteVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el fabricante", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<FabricanteDto> listaFabricantesPorMarca(String codMarca) {
		List<FabricanteDto> listaMarcas = new ArrayList<>();
		try {
			if (codMarca != null && !codMarca.isEmpty()) {
				MarcaDgtVO marca = fabricanteDao.recuperarMarcaConFabricantes(codMarca);
				if (marca != null && marca.getFabricantes() != null) {
					Iterator<FabricanteVO> it = marca.getFabricantes().iterator();
					if (it != null) {
						while (it.hasNext()) {
							listaMarcas.add(conversor.transform(it.next(), FabricanteDto.class));
						}
					}
				}
				return listaMarcas;
			}
		} catch (Exception e) {
			log.error("Error al recuperar los fabricantes de la marca", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public List<FabricanteDto> listaFabricantesInactivos() {
		String[] cod;
		ArrayList<Long> listCodFabInactivos = new ArrayList<>();
		List<FabricanteDto> listaFabricantes = new ArrayList<>();
		try {
			// Obtenemos los códigos de fabricantes inactivos del property y los almacenamos en el array.
			String fabInac = gestorPropiedades.valorPropertie("Fabricantes.inactivos");
			// Se rellena la lista con los fabricantes inactivos
			cod = fabInac.split(",");

			for (String strCodFab : cod) {
				listCodFabInactivos.add(Long.parseLong(strCodFab));
			}
			List<FabricanteVO> listaInactivos = fabricanteDao.getFabricantesInactivos(listCodFabInactivos);
			log.debug(fabInac);
			Iterator<FabricanteVO> it = listaInactivos.iterator();
			while (it.hasNext()) {
				listaFabricantes.add(conversor.transform(it.next(), FabricanteDto.class));
			}
			return listaFabricantes;
		} catch (Exception e) {
			log.error("Error al recuperar los fabricantes inactivos");
		}
		return null;
	}

	@Override
	@Transactional
	public FabricanteVO addFabricante(String fabricante) {
		FabricanteVO fabricanteVO = new FabricanteVO();
		fabricanteVO.setFabricante(fabricante);
		fabricanteDao.guardar(fabricanteVO);
		return fabricanteVO;
	}
}