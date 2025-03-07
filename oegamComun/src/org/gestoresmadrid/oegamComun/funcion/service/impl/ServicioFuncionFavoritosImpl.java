package org.gestoresmadrid.oegamComun.funcion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.favoritos.model.dao.FavoritosContratoDao;
import org.gestoresmadrid.core.favoritos.model.dao.FavoritosFuncionDao;
import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioFuncionFavoritos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioFuncionFavoritosImpl implements ServicioFuncionFavoritos{

	private static final long serialVersionUID = -1446596482798415117L;

	@Autowired
	FavoritosFuncionDao favoritosFuncionDao;
	
	@Autowired
	FavoritosContratoDao favoritosContratoDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<ContratoFavoritosVO> obtenerListaFavoritosContrato(Long idContrato) {
		return favoritosContratoDao.getListaFavoritosPorContrato(idContrato);
	}
}
