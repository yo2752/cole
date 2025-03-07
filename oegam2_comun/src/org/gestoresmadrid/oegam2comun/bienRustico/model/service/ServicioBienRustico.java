package org.gestoresmadrid.oegam2comun.bienRustico.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienRusticoBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;

import escrituras.beans.ResultBean;

public interface ServicioBienRustico extends Serializable{

	List<BienRusticoBean> convertirListaEnBeanPantalla(List<BienRusticoVO> list);

	Boolean esBienRustico(BienDto bienDto);

	ResultBean guardarBienRusticoPantalla(BienDto bienDto, Long idDireccion);

	ResultBean eliminarBienRustico(BienRusticoVO bienVO);

	ResultBean eliminarBienesRusticosRemesa(Long idRemesa);

	ResultBean eliminarBienesRusticosModelo(Long idModelo);

	ResultBean validarBienRustico(BienDto bien);

}
