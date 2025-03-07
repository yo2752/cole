package org.gestoresmadrid.oegamComun.tasa.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioPersistenciaTasasPegatinas extends Serializable{

	void borrarTasa(String codigoTasa);

	TasaPegatinaVO getTasaPegatina(String codigoTasa);

	TasaPegatinaVO getTasaVO(String codigoTasa);

	List<TasaPegatinaVO> getListaTasasPorCodigos(List<String> listaTasas);

	String eliminar(String codigoTasa);

	ResultadoBean cambiarFormatoATasaPegatina(TasaPegatinaVO tasaPegatina, Long idUsuario);

}
