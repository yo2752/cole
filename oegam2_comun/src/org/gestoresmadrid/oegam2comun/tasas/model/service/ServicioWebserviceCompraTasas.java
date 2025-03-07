package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;

import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas;

public interface ServicioWebserviceCompraTasas {

	/**
	 * Funcion que genera un numero de justificante 791 .
	 * 
	 * @param idCompra - Identificador de la petici�n de compra, si es que tiene
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	String obtenerNumeroJustificante(Long idCompra) throws RemoteException, ServiceException;

	/**
	 * Realizar un pago de tasas. Se necesita el numero de justificante 791 obtenida en el metodo {@link ServicioWebserviceCompraTasas#generarPresupuesto()}.
	 * @param compra
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	ResultadoOperacionCompraTasas realizarPagoTasas(ResumenCompraBean compra) throws RemoteException, ServiceException;

	/**
	 * Obtiene el justificante de pago de tasas
	 * 
	 * @param idCompra - Identificador de la petici�n de compra, si es que tiene
	 * @param cif - El identificador del comprador de las tasas
	 * @param autoliquidacion - El n�mero de autoliquidaci�n de la compra
	 * @param csv - El codigo CSV para obtener el documento en el registro electr�nico de la DGT
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	DocumentoJustificantePago obtenerJustificantePago(Long idCompra, String cif, String autoliquidacion, String csv) throws RemoteException, ServiceException;

	/**
	 * Devuelve la relaci�n de tasas a partir del nrc o la autoliquidaci�n
	 * 
	 * @param idCompra - Identificador de la petici�n de compra, si es que tiene
	 * @param identificador - El identificador del comprador de las tasas
	 * @param numeroAutoliquidacion - El n�mero de autoliquidaci�n de la compra
	 * @param nrc - El n�mero nrc de la compra
	 * @param importe - El importe del pedido realizado
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	DatosPedidoCompleto obtenerDatosPedido(Long idCompra, String identificador, String numeroAutoliquidacion, String nrc, Double importe) throws RemoteException, ServiceException;

}
