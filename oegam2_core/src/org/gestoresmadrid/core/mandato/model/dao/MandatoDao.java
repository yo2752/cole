package org.gestoresmadrid.core.mandato.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.mandato.model.vo.MandatoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface MandatoDao extends Serializable, GenericDao<MandatoVO> {

	MandatoVO getMandato(Long idMandato);

	String obtenerCodigoMandato(String numColegiado);

	List<String> getListaColegiadosConMandatos();

	List<String> getListaCodMandatosPorColegiados(String numColegiado);

	List<MandatoVO> getListaMandatosPorColegiados(String numColegiado);

	MandatoVO getMandatoPorCodigoYColegiado(String codigoMandato, String numColegiado);
	
	MandatoVO getMandatoPorCodigo(String codigoMandato);
}
