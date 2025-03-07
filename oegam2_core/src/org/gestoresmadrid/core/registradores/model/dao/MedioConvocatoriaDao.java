package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.MedioConvocatoriaVO;

public interface MedioConvocatoriaDao extends GenericDao<MedioConvocatoriaVO>, Serializable {

	MedioConvocatoriaVO getMediosConvocatoria(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion);

	List<MedioConvocatoriaVO> getMediosConvocatorias(BigDecimal idTramiteRegistro);
}
