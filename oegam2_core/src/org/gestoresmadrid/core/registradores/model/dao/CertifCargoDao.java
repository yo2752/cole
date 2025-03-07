package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.CertifCargoVO;

public interface CertifCargoDao extends GenericDao<CertifCargoVO>, Serializable {

	CertifCargoVO getCertificante(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma);

	List<CertifCargoVO> getCertificantes(BigDecimal idTramiteRegistro, String nifCargo);

	List<CertifCargoVO> getCertificantesIdFirma(BigDecimal idTramiteRegistro, String nifCargo, boolean idFirmaNull);
}
