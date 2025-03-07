package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import escrituras.beans.ResultBean;

/**
 * Servicio que gestiona la compra de Tasas de DGT mediante WS
 */
public interface ServicioCompraTasas {

	/**
	 * Función que realiza el proceso de compra de Tasas de la DGT según el pedido guardado en BBDD
	 * @param idCompra
	 * @return
	 * @throws ServiceException
	 */
	RespuestaTasas realizarCompraTasas(Long idCompra);

	/**
	 * Inicia un nuevo bean con las tasas de DGT almacenadas en BBDD
	 * @return
	 */
	ResumenCompraBean iniciarNuevaCompra();

	/**
	 * Guarda una solicitud de compra de tasas
	 * @param tasaSolicitadas
	 * @return
	 */
	public Long guardaCompra(ResumenCompraBean resumenCompraBean);

	/**
	 * Método para mostrar la pantalla de confirmación de la compra con el resumen de esta.
	 * @param resumenCompraBean
	 * @param contrato
	 * @return
	 */
	public ResumenCompraBean resumenCompra(ResumenCompraBean resumenCompraBean);

	/**
	 * Recuperar el detalle de una compra.
	 * @param idContrato Identificador del contrato (opcional)
	 */
	public ResumenCompraBean detalleCompra(Long idCompra, Long idContrato);

	/**
	 * Cambia el estado de una compra
	 * @param estado nuevo estado
	 * @param idCompra Identificador unico de la compra que se actualiza
	 * @param respuesta Respuesta que motiva el cambio de estado
	 * @return true si se actualiza una compra, false si no
	 */
	boolean actualizarEstado(EstadoCompra estado, Long idCompra, String respuesta);

	/**
	 * Obtiene el array de bytes del fichero de justificante de pago de tasas, si son varios ficheros devuelve un zip
	 * @param idsCompras
	 * @param idContrato Identificador del contrato (opcional)
	 * @return
	 */
	ByteArrayInputStreamBean descargarJustificanteTasas(String[] idsCompras, Long idContrato);

	/**
	 * Obtiene el array de bytes del fichero de tasas, si son varios ficheros devuelve un zip
	 * @param idsCompras
	 * @param idContrato Identificador del contrato (opcional)
	 * @return
	 */
	ByteArrayInputStreamBean descargarFicheroTasas(String[] idsCompras, Long idContrato);

	/**
	 * Cambia la referencia propia de una compra
	 * @param refPropia nueva referencia propia
	 * @param idCompra Identificador unico de la compra que se actualiza
	 * @return true si se actualiza una compra, false si no
	 */
	boolean actualizarReferenciaPropia(String refPropia, Long idCompra);

	/**
	 * Borrado logico (estado Anulado), si no se ha realizado pago
	 * @param idCompra Identificador unico de la compra que se anula
	 * @param idContrato Identificador del contrato (opcional)
	 * @return true si se borra la compra, false si no
	 */
	boolean anular(Long idCompra, Long idContrato);

	/**
	 * Realiza la logica para importar el fichero de tasas asociado a la compra al contrato seleccionado
	 * 
	 * @param idCompra
	 * @param usuario
	 * @param contrato
	 * @param idSession
	 * @param formato
	 * @param numColegiado
	 * @param isAdmin
	 * @param tienePermisoColegio 
	 * @return
	 */
	RespuestaTasas importarTasas(Long idCompra, Long idUsuario, BigDecimal contrato, String idSession, FormatoTasa formato, String numColegiado, Boolean isAdmin, Boolean tienePermisoColegio);

	/**
	 * Crea la peticion en cola, comprobando si debe descontarse credito
	 * 
	 * @param resumenCompraBean
	 * @param idUsuario
	 * @return
	 */
	ResultBean crearSolicitud(ResumenCompraBean resumenCompraBean, BigDecimal idUsuario);

	/**
	 * Comprueba si debe devolverse el credito (no se ha obtenido NRC ni Autoliquidacion),
	 * y realiza la devolucion
	 * 
	 * @param idCompra
	 * @param idUsuario
	 */
	void tratarDevolverCredito(Long idCompra, BigDecimal idUsuario);

	/**
	 * Realiza la importacion de un tramite de compra de tasas, que se haya realizado por fuera de Oegam
	 * 
	 * @param resumenCompraBean
	 * @param ficheroTasas
	 * @param ficheroJustificante
	 * @return
	 */
	ResultBean importarCompraTasas(ResumenCompraBean resumenCompraBean, File ficheroTasas, File ficheroJustificante);

}
