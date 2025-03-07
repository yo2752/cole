package org.gestoresmadrid.core.contrato.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ContratoDao extends GenericDao<ContratoVO>, Serializable {

	ContratoVO getContrato(Long idContrato, boolean contratoCompleto);

	List<DatoMaestroBean> getComboContratosHabilitados();
	
	List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato);

	List<DatoMaestroBean> getComboContratosHabilitadosColegiado(String numColegiado);

	List<ContratoVO> getContratosPorColegiado(String numColegiado);
	
	List<ContratoVO> getContratosPorAplicacion(String codigoAplicacion);

	ContratoVO getContratoPorCifProcesos(String cifContrato);
	
	ContratoVO obtenerContratoPorId(Long idContrato);

	List<ContratoVO> getListaContratosPorId(List<Long> listaIdsContratos);

	String getJefaturaContrato(Long idContrato);

	List<AplicacionVO> getAplicacionesPorContrato(BigDecimal idContrato);

	List<ContratoVO> getListaContratosColegiado(String numColegiado, BigDecimal estado);

	List<ContratoVO> getListGrupo(String idGrupo);

	Boolean isContratoDeGrupo(Long idContrato, String idGrupo);

}
