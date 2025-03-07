package org.gestoresmadrid.core.favoritos.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface FavoritosContratoDao extends GenericDao<ContratoFavoritosVO>, Serializable {
	  
	List<ContratoFavoritosVO> recuperarFavoritos(BigDecimal idContrato);

	List<ContratoFavoritosVO> getListaFavoritosPorContrato(Long idContrato);
	
	
}
