package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

public interface TasaDao extends GenericDao<TasaVO>, Serializable {

	TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa, int maxResult);

	TasaVO getTasaInteve(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	TasaVO getTasaLibre(Long idContrato, String tipoTasa);

	List<TasaVO> getTasasLibres(Long idContrato, String tipoTasa);

	List<TasaVO> getTasasLibresMatriculacion(Long idContrato);

	List<TasaVO> getTasasPorNumExpediente(BigDecimal numExpediente);
}
