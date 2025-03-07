package org.gestoresmadrid.core.modelo600_601.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;

public interface Modelo600_601Dao extends GenericDao<Modelo600_601VO>, Serializable{

	Modelo600_601VO getModeloById(Long idModelo);
	
	void cambiarEstadoModelo (Long idModelo, int estado);
	
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	List<Modelo600_601VO> getListaModelosPorRemesa(Long idRemesa,boolean completo);

	Modelo600_601VO getModeloPorExpediente(BigDecimal numExpediente, Boolean modeloCompleto);
	
	Modelo600_601VO getModelo(BigDecimal numExpediente, Long idModelo, Boolean modeloCompleto);

	Modelo600_601VO getModeloPorId(Long idModelo, boolean completo);

	List<Modelo600_601VO> getListaModelosPorExpedientesYContrato(BigDecimal[] numExpedientes, Long idContrato);

}
