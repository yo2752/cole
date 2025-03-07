package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.CertifCargoDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonalidad;
import org.gestoresmadrid.core.registradores.model.vo.CertifCargoVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCertifCargo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioCertifCargoImpl implements ServicioCertifCargo {

	private static final long serialVersionUID = -6579432013622718616L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioCertifCargoImpl.class);

	@Autowired
	private CertifCargoDao certifCargoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public CertifCargoVO getCertificante(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma) {
		return certifCargoDao.getCertificante(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, idFirma);
	}

	@Override
	@Transactional
	public CertifCargoDto getCertificanteDto(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma) {
		CertifCargoVO vo = getCertificante(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, idFirma);
		return conversor.transform(vo, CertifCargoDto.class);
	}

	@Override
	@Transactional
	public List<CertifCargoDto> getCertificantes(BigDecimal idTramiteRegistro) {
		List<CertifCargoDto> result = null;
		try {
			if (idTramiteRegistro != null) {
				List<CertifCargoVO> list = certifCargoDao.getCertificantes(idTramiteRegistro, null);
				if (list != null && !list.isEmpty()) {
					result = new ArrayList<CertifCargoDto>();
					for (CertifCargoVO vo : list) {
						CertifCargoDto dto = conversor.transform(vo, CertifCargoDto.class);
						String apellidos = dto.getSociedadCargo().getPersonaCargo().getApellido1RazonSocial();
						if (dto.getSociedadCargo().getPersonaCargo().getApellido2() != null && !dto.getSociedadCargo().getPersonaCargo().getApellido2().isEmpty()) {
							apellidos += " " + dto.getSociedadCargo().getPersonaCargo().getApellido2();
						}
						dto.setApellidos(apellidos);
						dto.setTipoDocumento(TipoDocumento.NIF.getValorEnum());
						dto.setTipoPersonalidad(TipoPersonalidad.Fisica.getValorEnum());
						dto.setDescCargo(TipoCargo.convertirTextoXml(dto.getCodigoCargo()));
						result.add(dto);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los certificantes: " + idTramiteRegistro, e);
		}
		return result;
	}

	@Override
	@Transactional
	public void guardarCertifCargo(CertifCargoDto certifCargoDto) {
		CertifCargoVO certifCargoVO = conversor.transform(certifCargoDto, CertifCargoVO.class);
		certifCargoDao.guardarOActualizar(certifCargoVO);
	}

	@Override
	@Transactional
	public void eliminarCertifCargo(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo) {
		CertifCargoVO certifCargoVO = getCertificante(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, null);
		certifCargoDao.borrar(certifCargoVO);
	}

	@Override
	@Transactional
	public ResultBean modificarIdFirma(BigDecimal idTramiteRegistro, String nifCargo, String idFirma) {
		ResultBean result = new ResultBean();
		if (idTramiteRegistro != null) {
			List<CertifCargoVO> list = certifCargoDao.getCertificantes(idTramiteRegistro, nifCargo);
			if (list != null && !list.isEmpty()) {
				for (CertifCargoVO vo : list) {
					vo.setIdFirma(idFirma);
					certifCargoDao.guardarOActualizar(vo);
				}
				list = certifCargoDao.getCertificantesIdFirma(idTramiteRegistro, null, true);
				if (list != null && !list.isEmpty()) {
					result.addAttachment(FIRMADO, Boolean.FALSE);
				} else {
					result.addAttachment(FIRMADO, Boolean.TRUE);
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("El certificado con el que quiere firmar no corresponde con ningún certificante del trámite");
			}
		}
		return result;
	}
}
