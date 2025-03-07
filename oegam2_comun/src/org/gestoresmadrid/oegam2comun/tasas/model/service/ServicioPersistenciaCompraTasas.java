package org.gestoresmadrid.oegam2comun.tasas.model.service;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;

public interface ServicioPersistenciaCompraTasas {

	/**
	 * Realiza la persistencia en base de datos de una solicitud de compra de tasas
	 * @param resumenCompraBean
	 * @return Identificador de la compra
	 * @throws TransactionalException Si ocurre algun error
	 */
	Long guardaCompra(ResumenCompraBean resumenCompraBean);

	/**
	 * Realiza el cambio de estado de una solicitud de compra de tasas
	 * @param estado Nuevo estado para la solicitud de compra de tasas
	 * @param idCompra identificador de la compra
	 * @param respuesta Causa del cambio de estado, si fuese necesario (mensajes de ws)
	 * @return true si se realizo correctamente el cambio de estado
	 * @throws TransactionalException Si ocurre algun error
	 */
	boolean actualizarEstado(EstadoCompra estado, Long idCompra, String respuesta);

	/**
	 * Recupera una compra previamente guardada
	 * @param idCompra identificador de la compra
	 * @param idContrato identificador del contrato (opcional)
	 * @param desglose indica si se debe recuperar o no el detalle de la compra (cada una de las tasas solicitadas)
	 * @return ResumenCompraBean resumenCompraBean con la compra recuperada, null si no la puede encontrar
	 */
	ResumenCompraBean getCompra(Long idCompra, Long idContrato, boolean desglose);

	/**
	 * Método para mostrar la pantalla de confirmación de la compra con el resumen de esta.
	 * @param resumenCompraBean
	 * @return
	 */
	ResumenCompraBean resumenCompra(ResumenCompraBean resumenCompraBean);

	/**
	 * Inicia un nuevo bean con las tasas de DGT almacenadas en BBDD
	 * @return
	 */
	ResumenCompraBean iniciarNuevaCompra();

	/**
	 * Actualiza unicamente la referencia propia de la compra de tasas
	 * @param refPropia referencia propia
	 * @param idCompra identificador de la compra
	 * @return true si se realizo correctamente el cambio de estado
	 * @throws TransactionalException Si ocurre algun error
	 */
	boolean actualizarReferenciaPropia(String refPropia, Long idCompra);

	/**
	 * Realiza el borrado logico (anula) la compra si no se realizo pago (no hay csv, nrc, autoliquidacion, y el estado no es finalizado_ok)
	 * @param idCompra identificador de la compra
	 * @param idContrato identificador del contrato (opcional)
	 * @return true si se anulo correctamente
	 * @throws TransactionalException Si ocurre algun error
	 */
	boolean anular(Long idCompra, Long idContrato);

}
