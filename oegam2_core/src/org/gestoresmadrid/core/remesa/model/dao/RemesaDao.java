package org.gestoresmadrid.core.remesa.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;

public interface RemesaDao extends GenericDao<RemesaVO>, Serializable{

	public BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	public RemesaVO getRemesaPorExpediente(BigDecimal numExpediente, boolean remesaCompleta);

	public RemesaVO getRemesaPorID(Long idRemesa, boolean remesaCompleta);

	public List<RemesaVO> getListaRemesasPorExpedientesYContrato(BigDecimal[] numExpedientes, Long idContrato);
}
