package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.AppException;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciasInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialesInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidoInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidosInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.Response;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StockInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StocksInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.UsuarioInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;

import utilidades.web.OegamExcepcion;

public interface ServicioCallToRestfullMateriales extends Serializable {
	
	MaterialesInfoRest llamadaWSInfoMaterial() throws AppException, OegamExcepcion;
	StocksInfoRest llamadaWSInfoStocks() throws OegamExcepcion, AppException;
	PedidosInfoRest llamadaWSInfoPedidos() throws OegamExcepcion, AppException;
	IncidenciasInfoRest llamadaWSInfoIncidencias() throws OegamExcepcion, AppException;
	UsuarioInfoRest llamadaWSInfoUsuario() throws OegamExcepcion, AppException;

	StockInfoRest llamadaWSInfoStock(Long stockId) throws OegamExcepcion, AppException;;
	PedidoInfoRest llamadaWSInfoPedido(Long pedidoId) throws OegamExcepcion, AppException;;

	Response llamadaWSCrearPedido(PedidoDto pedidoDto) throws OegamExcepcion, AppException;	
	Response llamadaWSModificarPedido(PedidoDto pedidoDto) throws OegamExcepcion, AppException;
	Response llamadaWSCrearIncidencia(IncidenciaDto incidenciaDto) throws OegamExcepcion, AppException;
	Response llamadaWSUpdateIncidencia(IncidenciaDto incidenciaDto) throws OegamExcepcion, AppException;
	Response llamadaWSActualizarStock(MaterialStockVO item) throws OegamExcepcion, AppException;
}
