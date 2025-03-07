package org.gestoresmadrid.core.docPermDistItv.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;

public interface VehNoMatOegamDao extends GenericDao<VehNoMatOegamVO>, Serializable {

	VehNoMatOegamVO getVehNoMatrOegamPorId(Long idVehNotMatOegam);

	List<VehNoMatOegamVO> getListaVehiculosPorListaIds(Long[] idsVehsNotMatOegam);

	List<VehNoMatOegamVO> getListaVehiculoPorDocId(Long idDocPermDistItv);

	List<VehNoMatOegamVO> getListaVehiculosPdteImpresionPorContrato(Long idContrato);

	Integer getCountNumVehNotMatOegamDstv(Long idDocPermDistItv);

	List<Long> getListaIdsContratosConDistintivosDuplicados();

	int comprobarExisteMatricula(String matricula);

	VehNoMatOegamVO getVehNoMatOegamVOPorMatricula(String matricula);

	HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos);

	List<ConsumoMaterialValue> getListaConsumoDstvDuplicadosJefaturaPorDia(String jefatura, Date fecha);

	VehNoMatOegamVO getVehNoMatOegamVOPorMatriculaColegiadoPendiente(String matricula, Long idContrato);

	List<VehNoMatOegamVO> getListaDuplicadosPorMatricula(String matricula);

	List<VehNoMatOegamVO> getListaVehNoMatOegamVOPorMatriculaContrato(String matricula, Long idContrato);
	
	List<String> listaMatriculasPorDocDisintintivo(Long docDistintivo);

	List<VehNoMatOegamVO> getListaDuplicadosWS(Long idContrato, Date fechaAltaInicio, Date fechaAltaFin, String matricula,
			String bastidor, String tipoDistintivo, String nive, Long[] listaIdVehNoMatOegam);
	
}
