package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFicherosCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioFicherosCompraTasasImpl implements ServicioFicherosCompraTasas, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193369161653848934L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioFicherosCompraTasasImpl.class);
	private static final String PREFIJO_FICHERO_JUSTIFICANTE_PAGO = "justificantepago";
	private static final String PREFIJO_FICHERO_TASAS = "tasas";

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public boolean guardarJustificantePago(Long idCompra, DocumentoJustificantePago documento, Fecha fecha, String numAutoliquidacion) {
		boolean exito = false;
		if (documento != null && documento.getDocumento() != null && documento.getFormato() != null) {
			// Nombre del documento
			String nombreFichero = PREFIJO_FICHERO_JUSTIFICANTE_PAGO + idCompra.toString();
			
			String extension = null;
			if (documento.getFormato()!=null) {
				extension = "." + documento.getFormato().toLowerCase();
			}

			// Obtener FicheroBean para enviar a guardar
			FicheroBean fichero = formarFicheroBean(ConstantesGestorFicheros.JUSTIFICANTE, fecha, nombreFichero, extension, documento.getDocumento());
			try {
				// Escritura en disco
				File file = gestorDocumentos.guardarByte(fichero);
				exito = file != null && file.exists();
			} catch (OegamExcepcion e) {
				LOG.error("Ocurrio un error al guardar el justificante de pago de la compra de tasas con id " + idCompra, e);
			}
		}
		return exito;
	}

	@Override
	public boolean guardarRelacionTasas(ResumenCompraBean compra, Map<String, TreeMap<String, Tasa>> mapaTasas, int numTasasCompradas) {
		boolean exito = false;
		// Nombre del fichero
		String nombreFichero = PREFIJO_FICHERO_TASAS + compra.getIdCompra().toString();

		// Generar Contenido
		OutputStream os = new ByteArrayOutputStream();
		Formatter formatter = new Formatter(os);
		// Primera linea

		// Numero de autoliquidacion
		formatter.format("%12S", compra.getNAutoliquidacion());
		// Fecha compra
		formatter.format("%8S", new SimpleDateFormat("yyyyMMdd").format(compra.getFechaCompra()));
		// CIF
		formatter.format("%9S", StringUtils.leftPad(compra.getCifContrato(), 9, "0"));
		// Importe
		formatter.format("%010.2f", compra.getImporteTotalTasas().doubleValue());
		formatter.format("%03d", numTasasCompradas);

		// Resto de lineas
		for (TreeMap<String, Tasa> tasas : mapaTasas.values()) {
			for(Tasa t : tasas.values()){
				formatter.format("%1S", "\r\n");
				formatter.format("%12S", t.getNumeroTasa());
				formatter.format("%-4S", t.getTipoTasa().replaceAll("\\.", " "));
				formatter.format("%4S", "");
				formatter.format("%09.2f", t.getImporte());
			}
		}
		formatter.close();
		byte[] contenido = ((ByteArrayOutputStream) os).toByteArray();
		// Obtener FicheroBean para enviar a guardar
		FicheroBean fichero = formarFicheroBean(ConstantesGestorFicheros.TASAS, utilesFecha.getFechaConDate(compra.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_TXT,  contenido);
		try {
			// Escritura en disco
			File file = gestorDocumentos.guardarByte(fichero);
			exito = file != null && file.exists();
		} catch (OegamExcepcion e) {
			LOG.error("Ocurrio un error al guardar el fichero de tasas con id " + compra.getIdCompra().toString(), e);
		}
		return exito;
	}

	@Override
	public boolean guardarFicheroTasas(ResumenCompraBean compra, byte[] contenido) {
		boolean exito = false;
		// Nombre del fichero
		String nombreFichero = PREFIJO_FICHERO_TASAS + compra.getIdCompra().toString();

		// Obtener FicheroBean para enviar a guardar
		FicheroBean fichero = formarFicheroBean(ConstantesGestorFicheros.TASAS, utilesFecha.getFechaConDate(compra.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_TXT,  contenido);
		try {
			// Escritura en disco
			File file = gestorDocumentos.guardarByte(fichero);
			exito = file != null && file.exists();
		} catch (OegamExcepcion e) {
			LOG.error("Ocurrio un error al guardar el fichero de tasas con id " + compra.getIdCompra().toString(), e);
		}
		return exito;
	}

	@Override
	public FileResultBean getJustificantePago(Long idCompra, Fecha fecha) {
		FileResultBean result;
		try {
			// Nombre del zip
			String nombreFichero = PREFIJO_FICHERO_JUSTIFICANTE_PAGO + idCompra.toString() + ConstantesGestorFicheros.EXTENSION_PDF;
			result = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.COMPRA_TASAS, ConstantesGestorFicheros.JUSTIFICANTE, fecha, nombreFichero, null);
		} catch (OegamExcepcion e) {
			LOG.error("Ocurrio un error al recuperar el justificante de pago de la compra de tasas con id " + idCompra, e);
			result = new FileResultBean();
			result.setStatus(FileResultStatus.ERROR);
			result.setMessage("En estos momentos no es posible obtener el justificante de pago solicitado, intentelo de nuevo transcurridos unos minutos.");
		}
		return result;
	}

	@Override
	public FileResultBean getRelacionTasas(Long idCompra, Fecha fechaConDate) {
		FileResultBean result;
		try {
			String nombreFichero = PREFIJO_FICHERO_TASAS + idCompra.toString() + ConstantesGestorFicheros.EXTENSION_TXT;
			result = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.COMPRA_TASAS, ConstantesGestorFicheros.TASAS, fechaConDate, nombreFichero, null);
		} catch (OegamExcepcion e) {
			LOG.error("Ocurrio un error al recuperar el fichero de tasas de la compra de tasas con id " + idCompra, e);
			result = new FileResultBean();
			result.setStatus(FileResultStatus.ERROR);
			result.setMessage("En estos momentos no es posible obtener el fichero de tasas solicitado, intentelo de nuevo transcurridos unos minutos.");
		}
		return result;
	}

	@Override
	public boolean guardarFicheroTasas(ResumenCompraBean resumenCompraBean, File ficheroTasas) {
		boolean guardado = false;
		if (ficheroTasas != null) {
			// Nombre del fichero
			String nombreFichero = PREFIJO_FICHERO_TASAS + resumenCompraBean.getIdCompra().toString();
	
			// Obtener FicheroBean para enviar a guardar
			FicheroBean fichero = formarFicheroBean(ConstantesGestorFicheros.TASAS, utilesFecha.getFechaConDate(resumenCompraBean.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_TXT,
					null);
			fichero.setFichero(ficheroTasas);
			try {
				// Escritura en disco
				File file = gestorDocumentos.guardarFichero(fichero);
				guardado = file != null && file.exists();
			} catch (OegamExcepcion e) {
				LOG.error("Ocurrio un error al guardar el fichero de tasas con id " + resumenCompraBean.getIdCompra().toString(), e);
			}
		}
		return guardado;
	}

	@Override
	public boolean guardarJustificantePago(ResumenCompraBean resumenCompraBean, File ficheroJustificante) {
		boolean guardado = false;
		if (ficheroJustificante != null) {
			// Nombre del documento
			String nombreFichero = PREFIJO_FICHERO_JUSTIFICANTE_PAGO + resumenCompraBean.getIdCompra().toString();
	
			// Obtener FicheroBean para enviar a guardar
			FicheroBean fichero = formarFicheroBean(ConstantesGestorFicheros.JUSTIFICANTE, utilesFecha.getFechaConDate(resumenCompraBean.getFechaAlta()), nombreFichero,
					ConstantesGestorFicheros.EXTENSION_PDF, null);
			fichero.setFichero(ficheroJustificante);
			try {
				// Escritura en disco
				File file = gestorDocumentos.guardarFichero(fichero);
				guardado = file != null && file.exists();
			} catch (OegamExcepcion e) {
				LOG.error("Ocurrio un error al guardar el justificante de pago de la compra de tasas con id " + resumenCompraBean.getIdCompra(), e);
			}
		}	
		return guardado;
	}

	/**
	 * Forma el FicheroBean que se envía al gestor de ficheros para guardar/consultar
	 * @param subTipo
	 * @param fecha
	 * @param nombreFichero
	 * @param entradasZip
	 * @param ficheroByte
	 * @param fichero
	 * @return
	 */
	private FicheroBean formarFicheroBean(String subTipo, Fecha fecha, String nombreFichero, String extension, byte[] ficheroByte) {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.COMPRA_TASAS);
		ficheroBean.setSubTipo(subTipo);
		ficheroBean.setFecha(fecha);
		ficheroBean.setNombreDocumento(nombreFichero);
		ficheroBean.setExtension(extension);
		ficheroBean.setSobreescribir(true);
		ficheroBean.setFicheroByte(ficheroByte);
		return ficheroBean;
	}

}
