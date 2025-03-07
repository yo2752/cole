package org.gestoresmadrid.oegam2comun.notificacionpreferencias.dao;

import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface ContratoPreferenciasSegSocDAO extends GenericDao<ContratoPreferenciaVO> {
	
	public ContratoPreferenciaVO obtenerContratoPreferenciaByIdContrato(Long idContrato);

	public Object insertOrUpdate(ContratoPreferenciaVO contratoPreferencias);

	BigDecimal obtenerOrdenDocBase(Long idContrato);
}
