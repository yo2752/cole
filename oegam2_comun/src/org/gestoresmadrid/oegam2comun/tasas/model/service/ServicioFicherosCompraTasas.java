package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;

import utilidades.estructuras.Fecha;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa;

public interface ServicioFicherosCompraTasas {

	/**
	 * Guarda el justificante de pago obtenido
	 * @param documento
	 * @param fecha
	 * @param numAutoliquidacion
	 * @return
	 */
	boolean guardarJustificantePago(Long idCompra, DocumentoJustificantePago documento, Fecha fecha, String numAutoliquidacion);

	/**
	 * Recupera un justificante de pago guardado en disco
	 * @param idCompra
	 * @param fecha
	 * @return
	 */
	FileResultBean getJustificantePago(Long idCompra, Fecha fecha);

	/**
	 * Genera el fichero de tasas compradas, para su posterior importacion en el sistema
	 * @param compra
	 * @param numTasasCompradas 
	 * @param tasasCompradas
	 * @return
	 */
	boolean guardarRelacionTasas(ResumenCompraBean compra, Map<String, TreeMap<String, Tasa>> mapa, int numTasasCompradas);


	/**
	 * Recupera el fichero de tasas compradas
	 * @param idCompra
	 * @param fecha
	 * @return
	 */
	FileResultBean getRelacionTasas(Long idCompra, Fecha fechaConDate);

	/**
	 * Guarda el fichero de tasas obtenido en el servicio web
	 * @param compra
	 * @param contenido
	 * @return
	 */
	boolean guardarFicheroTasas(ResumenCompraBean compra, byte[] contenido);


	/**
	 * Guarda el fichero de tasas asociado a una compra
	 * @param resumenCompraBean
	 * @param ficheroTasas
	 * @return
	 */
	boolean guardarFicheroTasas(ResumenCompraBean resumenCompraBean, File ficheroTasas);


	/**
	 * Guarda el justificante de pago asociado a una compra
	 * 
	 * @param resumenCompraBean
	 * @param ficheroJustificante
	 * @return
	 */
	boolean guardarJustificantePago(ResumenCompraBean resumenCompraBean, File ficheroJustificante);


}
