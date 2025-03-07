package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.vehiculo.model.dao.MarcaFabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricantePK;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaFabricanteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMarcaFabricanteImpl implements ServicioMarcaFabricante {

	private static final long serialVersionUID = 8922710885989250509L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMarcaFabricanteImpl.class);

	@Autowired
	private MarcaFabricanteDao marcaFabricanteDao;

	@Autowired
	private ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	private ServicioFabricante servicioFabricante;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public MarcaFabricanteDto getMarcaFabricante(String codigoMarca, Long codFabricante) {
		try {
			MarcaFabricanteVO marcaFabricante = marcaFabricanteDao.getMarcaFabricante(codigoMarca, codFabricante);
			if (marcaFabricante != null) {
				return conversor.transform(marcaFabricante, MarcaFabricanteDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca fabricante", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean[] addMarcaFabricante(String marca, String fabricante, Long version) {
		ResultBean[] sB = new ResultBean[2];

		sB[0] = new ResultBean();
		sB[1] = new ResultBean();

		// Actualizamos MARCA

		MarcaDgtVO marcaVO = null;
		ArrayList<Integer> versionConsulta = new ArrayList<>();

		// En caso de que la marca se introduzca con versión 2(ctit + matr)
		// se busca que haya una marca con versión 0 ó 1 para actualizarlo.
		if (version.equals(BigDecimal.valueOf(2))) {
			versionConsulta.add(0);
			versionConsulta.add(1);
			marcaVO = servicioMarcaDgt.getMarcaDgt(null, marca, versionConsulta);
			if (marcaVO != null) {
				servicioMarcaDgt.updateMarca(marcaVO);
				sB[0].addMensajeALista("Se ha actualizado la marca " + marcaVO.getMarca() + " con el código de marca " + marcaVO.getCodigoMarca() + ".");
			} else {
				marcaVO = servicioMarcaDgt.addMarca(marca, version);
				sB[0].addMensajeALista("Se ha añadido la marca " + marcaVO.getMarca() + " con el código de marca " + marcaVO.getCodigoMarca() + ".");
			}
		} else {
		// Si es una marca con versión 0 ó 1 se comprueba que no exista y si no existe
		// se mete
			versionConsulta.add(version.intValue());
			marcaVO = servicioMarcaDgt.getMarcaDgt(null, marca, versionConsulta);
			if (marcaVO == null) {
				marcaVO = servicioMarcaDgt.addMarca(marca, version);
				sB[0].addMensajeALista("Se ha añadido la marca " + marcaVO.getMarca() + " con el código de marca " + marcaVO.getCodigoMarca() + ".");
			}else{
				sB[1].addMensajeALista("No se ha podido añadido la marca " + marca + " debido a que ya existe.");
			}
		}

		// Actualizamos FABRICANTE

		FabricanteVO fabricanteVO = servicioFabricante.getFabricante(fabricante);

		if (fabricanteVO == null) {
			fabricanteVO = servicioFabricante.addFabricante(fabricante);
			sB[0].addMensajeALista("Se ha añadido el fabricante " + fabricanteVO.getFabricante() + " con el código de fabricante " + fabricanteVO.getCodFabricante() + ".");
		} else {
			sB[1].addMensajeALista("No se ha podido añadido el fabricante " + fabricante + " debido a que ya existe.");
		}

		//Actualizamos MARCA/FABRICANTE

		if (marcaFabricanteDao.getMarcaFabricante(String.valueOf(marcaVO.getCodigoMarca()),
				fabricanteVO.getCodFabricante()) == null) {
			MarcaFabricanteVO marcaFabricanteVO = new MarcaFabricanteVO();
			MarcaFabricantePK mfPK = new MarcaFabricantePK();
			mfPK.setCodFabricante(fabricanteVO.getCodFabricante());
			mfPK.setCodigoMarca(marcaVO.getCodigoMarca());
			marcaFabricanteVO.setId(mfPK);
			marcaFabricanteDao.guardar(marcaFabricanteVO);

			sB[0].addMensajeALista("Se ha añadido la relación marca fabricante de marca:" + marcaVO.getMarca() + ", fabricante:" + fabricanteVO.getFabricante() + ".");
		} else {
			sB[1].addMensajeALista("No se ha podido añadido la relación marca-fabricante " + marca + " - " + fabricante + " debido a que ya existe.");
		}

		return sB;
	}
}