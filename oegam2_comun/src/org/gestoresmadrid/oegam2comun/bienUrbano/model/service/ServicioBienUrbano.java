package org.gestoresmadrid.oegam2comun.bienUrbano.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienUrbanoBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;

import escrituras.beans.ResultBean;

public interface ServicioBienUrbano extends Serializable{

	List<BienUrbanoBean> convertirListaEnBeanPantalla(List<BienUrbanoVO> lista);

	Boolean esBienUrbano(BienDto bienUrbanoDto);

	ResultBean guardarBienUrbanoPantalla(BienDto bienDto, Long idDireccion);

	ResultBean eliminarBienUrbano(BienUrbanoVO bienVO);

	ResultBean eliminarBienesUrbanoRemesa(Long idRemesa);

	ResultBean eliminarBienUrbanoModelo(Long idModelo);

	ResultBean validarBienUrbano(BienDto bien);

}
