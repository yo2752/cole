package org.gestoresmadrid.core.ivtmMatriculacion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface IvtmMatriculacionDao extends GenericDao<IvtmMatriculacionVO>, Serializable {

	IvtmMatriculacionVO getIvtmPorExpediente(BigDecimal numExpediente);

	BigDecimal idPeticionMax(String numColegiado);

	IvtmResultadoWSMatriculasWS[] recuperarMatriculas(IvtmDatosEntradaMatriculasWS datosEntrada);
}