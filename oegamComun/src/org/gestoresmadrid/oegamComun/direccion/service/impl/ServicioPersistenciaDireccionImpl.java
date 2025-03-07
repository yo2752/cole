package org.gestoresmadrid.oegamComun.direccion.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.DireccionDao;
import org.gestoresmadrid.core.direccion.model.dao.MunicipioDao;
import org.gestoresmadrid.core.direccion.model.dao.MunicipioSitesDao;
import org.gestoresmadrid.core.direccion.model.dao.ProvinciaDao;
import org.gestoresmadrid.core.direccion.model.dao.TipoViaDao;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioPersistenciaDireccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaDireccionImpl implements ServicioPersistenciaDireccion{

	private static final long serialVersionUID = -4236038146433212323L;

	@Autowired
	DireccionDao direccionDao;

	@Autowired
	TipoViaDao tipoViaDao;

	@Autowired
	ProvinciaDao provinciaDao;

	@Autowired
	MunicipioDao municipioDao;

	@Autowired
	MunicipioSitesDao municipioSitexDao;
	
	@Override
	@Transactional(readOnly=true)
	public DireccionVO getDireccion(Long idDireccion) {
		return direccionDao.getDireccion(idDireccion);
	}
	
	@Override
	@Transactional
	public DireccionVO guardarOActualizar(DireccionVO direccion) {
		return direccionDao.guardarOActualizar(direccion);
	}
	
	@Override
	@Transactional
	public Long guardar(DireccionVO direccionVO) {
		return (Long) direccionDao.guardar(direccionVO);
	}
	
	@Override
	@Transactional
	public void evict(DireccionVO direccionVO) {
		direccionDao.evict(direccionVO);
	}
	
	@Override
	@Transactional(readOnly=true)
	public TipoViaVO getTipoVia(String idTipoVia) {
		return tipoViaDao.getTipoVia(idTipoVia);
	}
	
	@Override
	@Transactional(readOnly=true)
	public ProvinciaVO getProvincia(String idProvincia) {
		return provinciaDao.getProvincia(idProvincia);
	}
	
	@Override
	@Transactional(readOnly=true)
	public MunicipioVO getMunicipio(String idMunicipio, String idProvincia) {
		return municipioDao.getMunicipio(idMunicipio, idProvincia);
	}
	
	@Override
	@Transactional(readOnly=true)
	public MunicipioSitesVO getMunicipioSites(String idMunicipio, String idProvincia) {
		return municipioSitexDao.getMunicipioSites(idMunicipio, idProvincia);
	}
}
