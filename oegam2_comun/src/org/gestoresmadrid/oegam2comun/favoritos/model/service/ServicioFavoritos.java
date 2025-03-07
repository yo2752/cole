package org.gestoresmadrid.oegam2comun.favoritos.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.oegam2comun.favoritos.views.beans.FavoritosBean;

import escrituras.beans.ResultBean;

public interface ServicioFavoritos {
	
	ContratoFavoritosVO convertirBeanToVO(FavoritosBean favoritosBean, BigDecimal idContrato);
	
	FavoritosBean convertirFuncionVOToBean(FuncionVO funcionVO);
	
	ResultBean guardarFavoritos(FavoritosBean favoritosBean, BigDecimal idContrato);
	
	List<ContratoFavoritosVO> recuperarFavoritos(BigDecimal idContrato);
	
	List<FuncionVO> getFuncionCompleta(BigDecimal idContrato);
	
	ResultBean eliminarFavorito (Long idFavorito);
	
}
