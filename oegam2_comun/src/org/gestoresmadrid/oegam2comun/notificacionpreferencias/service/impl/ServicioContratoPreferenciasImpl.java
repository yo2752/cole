package org.gestoresmadrid.oegam2comun.notificacionpreferencias.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.oegam2comun.notificacionpreferencias.dao.ContratoPreferenciasSegSocDAO;
import org.gestoresmadrid.oegam2comun.notificacionpreferencias.service.ServicioContratoPreferencias;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoPreferenciaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioContratoPreferenciasImpl implements ServicioContratoPreferencias {

	private static final long serialVersionUID = -933278229047266236L;

	@Autowired
	private ContratoPreferenciasSegSocDAO contratoPreferenciasDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public OrdenDocBaseEnum obtenerOrdenDocBase(Long idContrato) {
		BigDecimal ordenDocBase = contratoPreferenciasDao.obtenerOrdenDocBase(idContrato);
		if (ordenDocBase != null) {
			return OrdenDocBaseEnum.convertir(ordenDocBase.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public void guardar(ContratoPreferenciaDto contratoPreferencias) {
		if (contratoPreferencias != null) {
			insertOrUpdate(conversor.transform(contratoPreferencias, ContratoPreferenciaVO.class));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ContratoPreferenciaDto getContratoPreferencias(Long idContrato) {
		ContratoPreferenciaVO contratoPreferencia = contratoPreferenciasDao.obtenerContratoPreferenciaByIdContrato(idContrato);
		if (contratoPreferencia != null) {
			return conversor.transform(contratoPreferencia, ContratoPreferenciaDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ContratoPreferenciaVO obtenerContratoPreferenciaByIdContrato(Long idContrato) {
		return contratoPreferenciasDao.obtenerContratoPreferenciaByIdContrato(idContrato);
	}

	@Override
	@Transactional
	public Object insertOrUpdate(ContratoPreferenciaVO contratoPreferencias) {
		return contratoPreferenciasDao.insertOrUpdate(contratoPreferencias);
	}
}
