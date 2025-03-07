package org.gestoresmadrid.core.atex5.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaVehiculoAtex5Dao extends Serializable, GenericDao<ConsultaVehiculoAtex5VO> {

	ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorId(Long idConsultaVehiculoAtex5, Boolean tramiteCompleto,	Boolean filtrarPorTasa);

	List<ConsultaVehiculoAtex5VO> getListaConsultaFinalizada();
}
