package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.CertifCargoVO;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;

import escrituras.beans.ResultBean;

public interface ServicioCertifCargo extends Serializable {
	
	public String FIRMADO = "firmado";

	CertifCargoVO getCertificante(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma);

	CertifCargoDto getCertificanteDto(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma);

	List<CertifCargoDto> getCertificantes(BigDecimal idTramiteRegistro);

	void guardarCertifCargo(CertifCargoDto certifCargoDto);

	void eliminarCertifCargo(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo);

	ResultBean modificarIdFirma(BigDecimal idTramiteRegistro, String nifCargo, String idFirma);
}
