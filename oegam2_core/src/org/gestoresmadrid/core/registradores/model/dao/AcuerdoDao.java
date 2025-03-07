package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.AcuerdoVO;

public interface AcuerdoDao extends GenericDao<AcuerdoVO>, Serializable {

	AcuerdoVO getAcuerdo(Long idAcuerdo);

	AcuerdoVO getAcuerdoPorSociedadCargo(String numColegiado, String cifSociedad, String nifCargo, String codigoCargo, BigDecimal idTramiteRegistro, String tipoAcuerdo);

	List<AcuerdoVO> getAcuerdos(BigDecimal idTramiteRegistro);
}
