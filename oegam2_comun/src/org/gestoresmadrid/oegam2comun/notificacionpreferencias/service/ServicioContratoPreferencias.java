package org.gestoresmadrid.oegam2comun.notificacionpreferencias.service;

import java.io.Serializable;

import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoPreferenciaDto;

public interface ServicioContratoPreferencias extends Serializable {

	ContratoPreferenciaVO obtenerContratoPreferenciaByIdContrato(Long idContrato);

	Object insertOrUpdate(ContratoPreferenciaVO contratoPreferencias);

	OrdenDocBaseEnum obtenerOrdenDocBase(Long idContrato);

	ContratoPreferenciaDto getContratoPreferencias(Long idContrato);

	void guardar(ContratoPreferenciaDto contratoPreferencias);
}
