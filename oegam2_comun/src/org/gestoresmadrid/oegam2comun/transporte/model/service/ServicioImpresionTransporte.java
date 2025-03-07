package org.gestoresmadrid.oegam2comun.transporte.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.transporte.view.dto.NotificacionTransporteDto;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioImpresionTransporte extends Serializable{

	ResultBean imprimirListadoNotificaciones(List<NotificacionTransporteDto> listaNotificacionesTrans, ContratoDto contrato, Date fecha);

	ResultBean impresionPoder(PoderTransporteDto poderTransporte);

}
