package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDocumentoLicenciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcDocumentoLicenciaDao extends GenericDao<LcDocumentoLicenciaVO>, Serializable {

	LcDocumentoLicenciaVO getLcDocumentoLicencia(Long idDocumentoLicencia);

	List<LcDocumentoLicenciaVO> getLcDocumentoLicenciaPorNumExp(BigDecimal numExpediente);
}
