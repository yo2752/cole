package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;

import escrituras.beans.ResultBean;

public interface ServicioCrearSolicitudesPedido extends Serializable {
	ResultBean crearSolicitudCrearPedido(Long pedidoId);
	ResultBean crearSolicitudActualizarPedido(Long pedidoId);
	ResultBean crearSolicitudAddMaterialPedido(Long pedidoId, Long MaterialId);
	ResultBean crearSolicitudRemoveMaterialPedido(Long pedidoId, Long MaterialId);
	ResultBean crearSolicitudIncidencia(Long incidenciaId);
	ResultBean crearSolicitudActualizarIncidencia(Long incidenciaId);
	
	ResultBean crearSolicitudInformacionPedido(List<Long> pedidosId);
	ResultBean crearSolicitudInformacionStock(List<Long> stocksId, ColaBean colaBean);
	ResultBean crearSolicitudActualizarStock(Long stockId);
}
