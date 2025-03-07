package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.model.beans.ColaBean;

import escrituras.beans.ResultBean;

public interface ServicioWebServicePedidos extends Serializable {
	
	public ResultBean generarCrearPedido(ColaBean colaBean);
	public ResultBean generarModificarPedido(ColaBean colaBean);
	public ResultBean generarCrearIncidencia(ColaBean colaBean);
	public ResultBean generarUpdateIncidencia(ColaBean colaBean);
	public ResultBean generarActualizarStock(ColaBean colaBean);
	public ResultBean generarInfoPedido(ColaBean colaBean);
	public ResultBean generarInfoStock(ColaBean colaBean);
	
	public ResultBean executeNotificacines();
	
}
