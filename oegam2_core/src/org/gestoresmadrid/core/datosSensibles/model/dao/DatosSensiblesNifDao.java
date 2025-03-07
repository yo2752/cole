package org.gestoresmadrid.core.datosSensibles.model.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DatosSensiblesNifDao extends GenericDao<DatosSensiblesNifVO> {

	List<DatosSensiblesNifVO> existeNif(String nif, String idGrupo);

	List<DatosSensiblesNifVO> getListaNif(String nif, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta);

	void borradoLogico(DatosSensiblesNifVO datosSensiblesNifVO);

	List<DatosSensiblesNifVO> getListaGruposPorNif(String nif, BigDecimal estado);

	List<DatosSensiblesNifVO> getNif(DatosSensiblesNifVO datosSensiblesNifVO);

	List<DatosSensiblesNifVO> getNifPorId(DatosSensiblesNifVO datosSensiblesNifVO);

	List<DatosSensiblesNifVO> buscarporNif(String dni);

	List<String> getListadoNifsSensibles();
}
