package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.MarcaFabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaFabricanteImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMarcaFabricanteImportacionImpl implements ServicioMarcaFabricanteImportacion {

	private static final long serialVersionUID = -8466105953335255693L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMarcaFabricanteImportacionImpl.class);

	@Autowired
	MarcaFabricanteDao marcaFabricanteDao;

	@Override
	@Transactional
	public MarcaFabricanteVO getMarcaFabricante(String codigoMarca, Long codFabricante) {
		try {
			MarcaFabricanteVO marcaFabricante = marcaFabricanteDao.getMarcaFabricante(codigoMarca, codFabricante);
			if (marcaFabricante != null) {
				return marcaFabricante;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca fabricante", e);
		}
		return null;
	}
}
