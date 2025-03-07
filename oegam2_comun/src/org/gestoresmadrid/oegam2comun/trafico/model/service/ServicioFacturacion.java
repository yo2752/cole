package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;

import escrituras.beans.ResultBean;
import hibernate.entities.facturacion.FacturaIrpf;
import hibernate.entities.facturacion.FacturaIva;

public interface ServicioFacturacion extends Serializable {

	TramiteTrafFacturacionDto getTramiteTraficoFact(BigDecimal numExpediente, String codigoTasa);
	
	ResultBean eliminarFacturacionTramite(BigDecimal numExpediente, String codigoTasa);

	ResultBean guardar(TramiteTrafFacturacionDto datosFacturacionDto, BigDecimal idUsuario, boolean utilizarTitular);

	ResultBean guardarMultiple(List<TramiteTrafFacturacionDto> listaFacturacion, BigDecimal idUsuario, PersonaDto persona, DireccionDto direccion);

	List<FacturaIva> listaIvas();
	
	List<FacturaIrpf> listaIrpfs();

}
