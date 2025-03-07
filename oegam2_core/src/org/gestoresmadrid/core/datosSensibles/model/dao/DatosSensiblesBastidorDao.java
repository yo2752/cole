package org.gestoresmadrid.core.datosSensibles.model.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DatosSensiblesBastidorDao extends GenericDao<DatosSensiblesBastidorVO> {

	List<DatosSensiblesBastidorVO> existeBastidor(String bastidor, String idGrupo);

	List<DatosSensiblesBastidorVO> getBastidores(String bastidor, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta, BigDecimal tipoBastidor);

	void borradoLogico(DatosSensiblesBastidorVO datosSensiblesBastidorVO);

	List<DatosSensiblesBastidorVO> buscarPorBastidor(String bastidor);

	List<DatosSensiblesBastidorVO> buscarPorBastidorYGrupo(DatosSensiblesBastidorVO bastidorVO);

	List<DatosSensiblesBastidorVO> getlistaGruposPorBastidor(String bastidor, BigDecimal estado);

	List<DatosSensiblesBastidorVO> getBastidorPorId(DatosSensiblesBastidorVO bastidorVO);

	List<String> getListadoBastidoresSensibles();
}
