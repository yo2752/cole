package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.AsistenteDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonalidad;
import org.gestoresmadrid.core.registradores.model.vo.AsistenteVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAsistente;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AsistenteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioAsistenteImpl implements ServicioAsistente {

	private static final long serialVersionUID = 500842140400597114L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioAsistenteImpl.class);

	@Autowired
	private AsistenteDao asistenteDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public AsistenteVO getAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion) {
		return asistenteDao.getAsistente(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, idReunion);
	}

	@Override
	@Transactional
	public AsistenteDto getAsistenteDto(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion) {
		AsistenteVO vo = getAsistente(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, idReunion);
		return conversor.transform(vo, AsistenteDto.class);
	}

	@Override
	@Transactional
	public List<AsistenteDto> getAsistentes(BigDecimal idTramiteRegistro) {
		List<AsistenteDto> result = null;
		try {
			if (idTramiteRegistro != null) {
				List<AsistenteVO> list = asistenteDao.getAsistentes(idTramiteRegistro, null);
				if (list != null && !list.isEmpty()) {
					result = new ArrayList<AsistenteDto>();
					for (AsistenteVO vo : list) {
						AsistenteDto dto = conversor.transform(vo, AsistenteDto.class);
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
			log.error("Error al recuperar los asistentes: " + idTramiteRegistro, e);
		}
		return result;
	}

	@Override
	@Transactional
	public void guardarAsistente(AsistenteDto asistenteDto) {
		AsistenteVO asistenteVO = conversor.transform(asistenteDto, AsistenteVO.class);
		asistenteDao.guardarOActualizar(asistenteVO);
	}

	@Override
	@Transactional
	public void eliminarAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo) {
		AsistenteVO asistenteVO = getAsistente(idTramiteRegistro, cifSociedad, nifCargo, codigoCargo, null);
		asistenteDao.borrar(asistenteVO);
	}
}
