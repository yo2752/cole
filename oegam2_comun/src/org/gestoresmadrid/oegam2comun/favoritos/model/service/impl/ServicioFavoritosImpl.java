package org.gestoresmadrid.oegam2comun.favoritos.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.dao.FavoritosContratoDao;
import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.general.model.vo.FuncionPK;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.oegam2comun.favoritos.model.service.ServicioFavoritos;
import org.gestoresmadrid.oegam2comun.favoritos.views.beans.FavoritosBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;

@Service
@Transactional
public class ServicioFavoritosImpl implements ServicioFavoritos {

	@Autowired
	private FavoritosContratoDao favoritosContratoDao;

	public List<ContratoFavoritosVO> recuperarFavoritos(BigDecimal idContrato) {
		// Se recuperan los favoritos guardados en funcion del contrato del usuario
		return favoritosContratoDao.recuperarFavoritos(idContrato);
	}

	public ContratoFavoritosVO convertirBeanToVO(FavoritosBean favoritosBean, BigDecimal idContrato) {
		ContratoFavoritosVO contratoFavoritosVO = new ContratoFavoritosVO();
		contratoFavoritosVO.setContrato(idContrato);
		contratoFavoritosVO.setFuncion(new FuncionVO());
		contratoFavoritosVO.getFuncion().setId(new FuncionPK());
		contratoFavoritosVO.getFuncion().getId().setCodigoAplicacion(favoritosBean.getCodigoAplicacion());
		contratoFavoritosVO.getFuncion().getId().setCodigoFuncion(favoritosBean.getCodigoFuncion());
		return contratoFavoritosVO;
	}

	public FavoritosBean convertirFuncionVOToBean(FuncionVO funcionVO) {
		FavoritosBean favoritosBean = new FavoritosBean();
		favoritosBean.setDescFuncion(funcionVO.getDescFuncion());
		return favoritosBean;
	}

	public ResultBean guardarFavoritos(FavoritosBean favoritosBean, BigDecimal idContrato) {
		ResultBean resultado = new ResultBean();
		ContratoFavoritosVO contratoFavoritos = convertirBeanToVO(favoritosBean, idContrato);
		if (favoritosContratoDao.guardar(contratoFavoritos) == null) {
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al guardar el favorito");
		} else {
			resultado.setMensaje("Favorito guardado correctamente");
		}
		return resultado;
	}

	public List<FuncionVO> getFuncionCompleta(BigDecimal idContrato) {
		List<FuncionVO> lista = new ArrayList<>();
		List<ContratoFavoritosVO> listaContratoFavorito = recuperarFavoritos(idContrato);
		if (listaContratoFavorito!=null) {
			for (ContratoFavoritosVO contratoFavoritos : listaContratoFavorito) {
				lista.add(contratoFavoritos.getFuncion());
			}
		}
		return lista;
	}

	public ResultBean eliminarFavorito(Long idFavorito) {
		ResultBean resultado = new ResultBean();
		ContratoFavoritosVO contratoFavoritos = new ContratoFavoritosVO();
		contratoFavoritos.setIdFavorito(idFavorito);
		if (!favoritosContratoDao.borrar(contratoFavoritos)) {
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al eliminar el favorito");
		}
		return resultado;
	}

}