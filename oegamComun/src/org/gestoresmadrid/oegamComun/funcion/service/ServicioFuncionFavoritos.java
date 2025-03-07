package org.gestoresmadrid.oegamComun.funcion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;

public interface ServicioFuncionFavoritos extends Serializable{

	List<ContratoFavoritosVO> obtenerListaFavoritosContrato(Long idContrato);

}
