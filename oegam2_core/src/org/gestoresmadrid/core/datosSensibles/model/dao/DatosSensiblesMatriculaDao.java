package org.gestoresmadrid.core.datosSensibles.model.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DatosSensiblesMatriculaDao extends GenericDao<DatosSensiblesMatriculaVO> {

	List<DatosSensiblesMatriculaVO> existeMatricula(String matricula, String idGrupo);

	List<DatosSensiblesMatriculaVO> getListaMatriculas(String matricula, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta);

	void borradoLogico(DatosSensiblesMatriculaVO datosSensiblesMatriculaVO);

	List<DatosSensiblesMatriculaVO> buscarPorMatricula(DatosSensiblesMatriculaVO matriculaVO);

	List<DatosSensiblesMatriculaVO> getListaGruposPorMatricula(String matricula, BigDecimal estado);

	List<DatosSensiblesMatriculaVO> getMatriculaPorId(DatosSensiblesMatriculaVO datosSensiblesMatriculaVO);

	List<DatosSensiblesMatriculaVO> buscarPorMatricula(String matricula);

	List<String> getListadoMatriculasSensibles();
}
