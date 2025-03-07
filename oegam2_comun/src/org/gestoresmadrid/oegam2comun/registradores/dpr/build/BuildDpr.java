package org.gestoresmadrid.oegam2comun.registradores.dpr.build;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.registradores.model.enumerados.Inmatriculada;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoEntrada;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoFormaPago;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonalidad;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TiposFormato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.corpme.BienMuebleType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEEDoc;
import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEEDoc.Documentos;
import org.gestoresmadrid.oegam2comun.registradores.corpme.ConfirmacionPagoFacturasType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.ConfirmacionPagoFacturasType.Facturas;
import org.gestoresmadrid.oegam2comun.registradores.corpme.ConfirmacionPagoFacturasType.Facturas.Factura;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DatosContactoType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DatosOperacionMercantilType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DatosPRIVADO.EnvioTramite.Objetos;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DatosPRIVADO.EnvioTramite.Objetos.BienesMuebles;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DepositanteType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DepositoCuentasType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DocumentoType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DocumentoType.Ficheros;
import org.gestoresmadrid.oegam2comun.registradores.corpme.DomicilioType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.FicheroType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.FicheroType.Firma;
import org.gestoresmadrid.oegam2comun.registradores.corpme.FicheroType.TimeStamp;
import org.gestoresmadrid.oegam2comun.registradores.corpme.FirmaDatosMensajeType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.IdentificacionFacturaType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.IdentificacionSujetoType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.IdentificacionSujetoType.IdentificadorAdministrativo;
import org.gestoresmadrid.oegam2comun.registradores.corpme.LegalizacionLibrosType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.LegalizacionLibrosType.Libros;
import org.gestoresmadrid.oegam2comun.registradores.corpme.LibroType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.NumeroEntradaBienesMueblesType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.NumeroEntradaMercantilType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.NumeroEntradaPropiedadType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.ObjectFactory;
import org.gestoresmadrid.oegam2comun.registradores.corpme.SociedadType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.TipoOperacionType;
import org.gestoresmadrid.oegam2comun.registradores.corpme.TipoOperacionType.Mercantil;
import org.gestoresmadrid.oegam2comun.registradores.corpme.TipoOperacionType.Propiedad;
import org.gestoresmadrid.oegam2comun.registradores.corpme.xml.XmlCORPMEFactory;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCategoria;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FacturaRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.LibroRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;
import viafirma.utilidades.XmlBuilderSupportUtils;

@Component
public class BuildDpr implements Serializable {

	private static final long serialVersionUID = 2902218536085049120L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;

	public BuildDpr() {}

	public ResultBean construirDPR(TramiteRegistroDto tramiteRegistro, String alias) throws Throwable {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "construirDpr.");
		ResultBean result = new ResultBean();
		ObjectFactory factory = new ObjectFactory();
		CORPMEEDoc corpmeEdoc = factory.createCORPMEEDoc();
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");

		corpmeEdoc = construirCorpmeDpr(tramiteRegistro, corpmeEdoc, factory, alias);


		if (null != tramiteRegistro.getFechaDocumento()) {
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setFechaDocumento(tramiteRegistro.getFechaDocumento().toString());
		} else {
			SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setFechaDocumento(sm.format(new Date()));
		}

		String mensajeDPR = recuperarStringXML(corpmeEdoc, context, xmlFactory);

		if (!xmlFactory.validar(mensajeDPR, context, "CORPME_eDoc")) {
			log.error("No se ha validado correctamente el XML contra el XSD");
			result.setError(true);
			result.setMensaje("No se ha validado correctamente el XML contra el XSD");
			return result;
		}

		mensajeDPR = incorporarAtributos(mensajeDPR);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDpr(mensajeDPR.getBytes("UTF-8"), tramiteRegistro.getIdTramiteRegistro().toString(), alias);
		if (idFirma == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido un error durante la firma digital del documento para el registro");
			result.setError(true);
			result.setMensaje("Ha ocurrido un error durante la firma digital del documento para el registro");
			return result;
		}

		File ficheroDPR = incluirBloqueFirma(corpmeEdoc, idFirma, tramiteRegistro.getIdTramiteRegistro().toString(), "DPR", factory, xmlFactory, context, utilesViafirma);

		result.addAttachment("nombreFichero", ficheroDPR.getName());

		return result;
	}

	public ResultBean construirDPRRegistro(TramiteRegRbmDto tramiteRegistro, String alias) throws Throwable {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "construirDpr.");
		ResultBean result = new ResultBean();
		ObjectFactory factory = new ObjectFactory();
		CORPMEEDoc corpmeEdoc = factory.createCORPMEEDoc();
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");

		corpmeEdoc = construirCorpmeDpr(tramiteRegistro, corpmeEdoc, factory, alias);
		if (null != tramiteRegistro.getFechaDocumento()) {
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setFechaDocumento(tramiteRegistro.getFechaDocumento().toString());
		} else {
			SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setFechaDocumento(sm.format(new Date()));
		}
		String mensajeDPR = recuperarStringXML(corpmeEdoc, context, xmlFactory);

		if (!xmlFactory.validar(mensajeDPR, context, "CORPME_eDoc")) {
			log.error("No se ha validado correctamente el XML contra el XSD");
			result.setError(true);
			result.setMensaje("No se ha validado correctamente el XML contra el XSD");
			return result;
		}

		mensajeDPR = incorporarAtributos(mensajeDPR);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDpr(mensajeDPR.getBytes("UTF-8"), tramiteRegistro.getIdTramiteRegistro().toString(), alias);
		if (idFirma == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido un error durante la firma digital del documento para el registro");
			result.setError(true);
			result.setMensaje("Ha ocurrido un error durante la firma digital del documento para el registro");
			return result;
		}

		File ficheroDPR = incluirBloqueFirma(corpmeEdoc, idFirma, tramiteRegistro.getIdTramiteRegistro().toString(), "DPR", factory, xmlFactory, context, utilesViafirma);

		result.addAttachment("nombreFichero", ficheroDPR.getName());

		return result;
	}

	public ResultBean construirAcuse(TramiteRegistroDto tramiteRegistro, String tipoMensaje, String tipoPdf, String alias) throws Throwable {
		ResultBean result = new ResultBean();
		ObjectFactory factory = new ObjectFactory();
		CORPMEEDoc corpmeEdoc = factory.createCORPMEEDoc();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();

		FileResultBean pdfContenido = null;
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			pdfContenido = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.ESCRITURAS, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(
					tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro().toString() + "_" + tipoPdf);
		} else {
			pdfContenido = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(
					tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro().toString() + "_" + tipoPdf);
		}

		File ficheroAFirmar = null;
		// File pdfContenido =
		// modeloGuardar.extraerFicheroZipFile(zip.getFile(),
		// tramiteRegistro.getIdTramiteRegistro() + "_" + tipoPdf);
		if (pdfContenido == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "No se ha encontrado documentación para firmar");
			result.setError(true);
			result.setMensaje("No se ha encontrado documentación para firmar");
			return result;
		} else {
			// Recorremos la lista de ficheros y tomamos el que no sea XML (pdf, rtf...)
			if (pdfContenido.getFiles() != null && !pdfContenido.getFiles().isEmpty()) {
				for (int i = 0; i < pdfContenido.getFiles().size(); i++) {
					if (pdfContenido.getFiles().get(i).getName().toUpperCase().indexOf(".XML") == -1) {
						ficheroAFirmar = pdfContenido.getFiles().get(i);
						break;
					}
				}

			} else {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "No se ha encontrado documentación para firmar");
				result.setError(true);
				result.setMensaje("No se ha encontrado documentación para firmar");
				return result;
			}
		}

		corpmeEdoc = construirCorpmeAcuse(tramiteRegistro, tipoMensaje, ficheroAFirmar, corpmeEdoc, factory, alias);

		String mensajeAcuse = recuperarStringXML(corpmeEdoc, context, xmlFactory);

		if (!xmlFactory.validar(mensajeAcuse, context, "CORPME_eDoc")) {
			log.error("No se ha validado correctamente el XML contra el XSD");
			result.setError(true);
			result.setMensaje("No se ha validado correctamente el XML contra el XSD");
			return result;
		}

		mensajeAcuse = incorporarAtributos(mensajeAcuse);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDpr(mensajeAcuse.getBytes("UTF-8"), tramiteRegistro.getIdTramiteRegistro().toString(), alias);
		if (idFirma == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido un error durante la firma digital del documento para el registro");
			result.setError(true);
			result.setMensaje("Ha ocurrido un error durante la firma digital del documento para el registro");
			return result;
		}

		File ficheroAcuse = incluirBloqueFirma(corpmeEdoc, idFirma, tramiteRegistro.getIdTramiteRegistro().toString(), "notificacion", factory, xmlFactory, context, utilesViafirma);

		result.addAttachment("nombreFichero", ficheroAcuse.getName());

		return result;
	}

	public ResultBean construirAcuse(TramiteRegRbmDto tramiteRegistro, String tipoMensaje, String tipoPdf, String alias) throws Throwable {
		ResultBean result = new ResultBean();
		ObjectFactory factory = new ObjectFactory();
		CORPMEEDoc corpmeEdoc = factory.createCORPMEEDoc();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();

		FileResultBean pdfContenido = null;

		pdfContenido = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(
				tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro().toString() + "_" + tipoPdf);

		File ficheroAFirmar = null;
		// File pdfContenido =
		// modeloGuardar.extraerFicheroZipFile(zip.getFile(),
		// tramiteRegistro.getIdTramiteRegistro() + "_" + tipoPdf);
		if (pdfContenido == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "No se ha encontrado documentación para firmar");
			result.setError(true);
			result.setMensaje("No se ha encontrado documentación para firmar");
			return result;
		} else {
			// Recorremos la lista de ficheros y tomamos el que no sea XML (pdf, rtf...)
			if (pdfContenido.getFiles() != null && !pdfContenido.getFiles().isEmpty()) {
				for (int i = 0; i < pdfContenido.getFiles().size(); i++) {
					if (pdfContenido.getFiles().get(i).getName().toUpperCase().indexOf(".XML") == -1) {
						ficheroAFirmar = pdfContenido.getFiles().get(i);
						break;
					}
				}

			} else {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "No se ha encontrado documentación para firmar");
				result.setError(true);
				result.setMensaje("No se ha encontrado documentación para firmar");
				return result;
			}
		}

		corpmeEdoc = construirCorpmeAcuse(tramiteRegistro, tipoMensaje, ficheroAFirmar, corpmeEdoc, factory, alias);

		String mensajeAcuse = recuperarStringXML(corpmeEdoc, context, xmlFactory);

		if (!xmlFactory.validar(mensajeAcuse, context, "CORPME_eDoc")) {
			log.error("No se ha validado correctamente el XML contra el XSD");
			result.setError(true);
			result.setMensaje("No se ha validado correctamente el XML contra el XSD");
			return result;
		}

		mensajeAcuse = incorporarAtributos(mensajeAcuse);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDpr(mensajeAcuse.getBytes("UTF-8"), tramiteRegistro.getIdTramiteRegistro().toString(), alias);
		if (idFirma == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido un error durante la firma digital del documento para el registro");
			result.setError(true);
			result.setMensaje("Ha ocurrido un error durante la firma digital del documento para el registro");
			return result;
		}

		File ficheroAcuse = incluirBloqueFirma(corpmeEdoc, idFirma, tramiteRegistro.getIdTramiteRegistro().toString(), "notificacion", factory, xmlFactory, context, utilesViafirma);

		result.addAttachment("nombreFichero", ficheroAcuse.getName());

		return result;
	}


	public ResultBean construirConfirmacionFactura(FacturaRegistroDto facturaRegistro, TramiteRegistroDto tramiteRegistro, String alias) throws Throwable {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "construir Confirmacion factura.");
		ResultBean result = new ResultBean();
		ObjectFactory factory = new ObjectFactory();
		CORPMEEDoc corpmeEdoc = factory.createCORPMEEDoc();
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");

		corpmeEdoc = conversor.transform(tramiteRegistro, CORPMEEDoc.class);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Facturas facturas = new Facturas();

		Factura factura = new Factura();
		factura.setFechaPago(sdf.format(facturaRegistro.getFechaPago()));
		factura.setIdentificadorTransferencia(facturaRegistro.getIdTransferencia());
		factura.setFacturaPagada(new IdentificacionFacturaType());
		factura.getFacturaPagada().setCif(facturaRegistro.getCifEmisor());
		factura.getFacturaPagada().setSerieFactura(facturaRegistro.getNumSerie());
		factura.getFacturaPagada().setEjercicioFactura(facturaRegistro.getEjercicio());
		factura.getFacturaPagada().setNumeroFactura(facturaRegistro.getNumFactura());
		factura.getFacturaPagada().setFechaFactura(facturaRegistro.getFechaFactura().toString());
		facturas.getFactura().add(factura);

		facturas.setNumeroFacturas(BigInteger.ONE);
		corpmeEdoc.getDatosPRIVADO().setConfirmacionPago(new ConfirmacionPagoFacturasType());
		corpmeEdoc.getDatosPRIVADO().getConfirmacionPago().setFacturas(facturas);

		establecerAtributosCabecera(corpmeEdoc, tramiteRegistro, "CPR");

		String mensajeFactura = recuperarStringXML(corpmeEdoc, context, xmlFactory);

		if (!xmlFactory.validar(mensajeFactura, context, "CORPME_eDoc")) {
			log.error("No se ha validado correctamente el XML contra el XSD");
			result.setError(true);
			result.setMensaje("No se ha validado correctamente el XML contra el XSD");
			return result;
		}

		mensajeFactura = incorporarAtributos(mensajeFactura);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDpr(mensajeFactura.getBytes("UTF-8"), tramiteRegistro.getIdTramiteRegistro().toString(), alias);
		if (idFirma == null) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido un error durante la firma digital del documento para el registro");
			result.setError(true);
			result.setMensaje("Ha ocurrido un error durante la firma digital del documento para el registro");
			return result;
		}

		File ficheroCPR = incluirBloqueFirma(corpmeEdoc, idFirma, tramiteRegistro.getIdTramiteRegistro().toString(), "CPR", factory, xmlFactory, context, utilesViafirma);

		result.addAttachment("nombreFichero", ficheroCPR.getName());

		return result;
	}


	private CORPMEEDoc construirCorpmeDpr(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory, String alias) throws Throwable {
		// comprimirSiTieneDocumentacionAdjunta(tramiteRegistro);

		rellenarDatos(tramiteRegistro, corpmeEdoc);

		corpmeEdoc = conversor.transform(tramiteRegistro, CORPMEEDoc.class);

		incluirDomicilioPresentador(tramiteRegistro, corpmeEdoc, factory);
		if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			incluirSociedad(tramiteRegistro, corpmeEdoc, factory);
		}
		incluirNumeroEntradaSubsanacion(tramiteRegistro, corpmeEdoc, factory);
		incluirTipoOperacion(tramiteRegistro, corpmeEdoc, factory);

		establecerAtributosCabecera(corpmeEdoc, tramiteRegistro, "DPR");
		corpmeEdoc.setDocumentos(establecerDocumentos(tramiteRegistro, factory, alias));

		return corpmeEdoc;
	}

	private CORPMEEDoc construirCorpmeDpr(TramiteRegRbmDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory, String alias) throws Throwable {
		comprimirSiTieneDocumentacionAdjunta(tramiteRegistro);

		rellenarDatos(tramiteRegistro);

		corpmeEdoc = conversor.transform(tramiteRegistro, CORPMEEDoc.class, "dozerRbmConverter");

		incluirDomicilioPresentador(tramiteRegistro, corpmeEdoc, factory);
		incluirNumeroEntradaSubsanacion(tramiteRegistro, corpmeEdoc, factory);
		incluirTipoOperacionRBM(tramiteRegistro, corpmeEdoc, factory);
		incluirBienesRBM(tramiteRegistro, corpmeEdoc, factory);

		establecerAtributosCabecera(corpmeEdoc, tramiteRegistro, "DPR");
		corpmeEdoc.setDocumentos(establecerDocumentos(tramiteRegistro, factory, alias));

		return corpmeEdoc;
	}

	private CORPMEEDoc construirCorpmeAcuse(TramiteRegistroDto tramiteRegistro, String tipoMensaje, File pdfContenido, CORPMEEDoc corpmeEdoc, ObjectFactory factory, String alias) throws Throwable {
		establecerAtributosCabecera(corpmeEdoc, tramiteRegistro, tipoMensaje);

		byte[] hashMd5 = UtilesRegistradores.obtenerHashMd5(pdfContenido);
		String contenidoCodificado = UtilesRegistradores.doBase64Encode(hashMd5);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDocNotificacion(hashMd5, tramiteRegistro.getIdTramiteRegistro().toString(), utiles.dameExtension(pdfContenido.getName(), true), alias);

		byte[] firmaBytes = XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma);
		String firmaCodificada = UtilesRegistradores.doBase64Encode(firmaBytes);
		byte[] selloBytes = XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma);
		String selloCodificado = UtilesRegistradores.doBase64Encode(selloBytes);

		String extension = "TXT";
		if (pdfContenido.getName() != null && !pdfContenido.getName().isEmpty()) {
			extension = pdfContenido.getName().substring(pdfContenido.getName().lastIndexOf(".")).toUpperCase();
			extension = extension.substring(1);
		}

		corpmeEdoc = construirElementoDocumentosAcuse(corpmeEdoc, contenidoCodificado, firmaCodificada, selloCodificado, factory, extension);

		return corpmeEdoc;
	}

	public CORPMEEDoc construirElementoDocumentosAcuse(CORPMEEDoc corpmeEdoc, String contenido, String firma, String sello, ObjectFactory factory, String extension) {
		CORPMEEDoc.Documentos corpmeEdocDocumentos = factory.createCORPMEEDocDocumentos();
		DocumentoType documentoType = factory.createDocumentoType();
		DocumentoType.Ficheros documentoTypeFicheros = factory.createDocumentoTypeFicheros();
		documentoType.setFirmaElectronica("S");
		documentoTypeFicheros.setNumeroFicheros(BigInteger.ONE);
		FicheroType ficheroType = factory.createFicheroType();
		ficheroType.setTipoFormato(extension);
		ficheroType.setBase64("S");
		ficheroType.setContenido(contenido);
		FicheroType.Firma ficheroTypeFirma = factory.createFicheroTypeFirma();
		ficheroTypeFirma.setValue(firma);
		(ficheroType.getFirma()).add(ficheroTypeFirma);
		FicheroType.TimeStamp ficheroTypeTimeStamp = factory.createFicheroTypeTimeStamp();
		ficheroTypeTimeStamp.setValue(sello);
		(ficheroType.getTimeStamp()).add(ficheroTypeTimeStamp);
		documentoTypeFicheros.getFichero().add(ficheroType);
		documentoType.setFicheros(documentoTypeFicheros);
		corpmeEdocDocumentos.getDocumento().add(documentoType);
		corpmeEdoc.setDocumentos(corpmeEdocDocumentos);
		corpmeEdoc.getDocumentos().setNumeroDocumentos(BigInteger.ONE);
		return corpmeEdoc;
	}

	public String recuperarStringXML(CORPMEEDoc corpmeEdoc, JAXBContext context, XmlCORPMEFactory xmlFactory) throws Exception {
		return xmlFactory.toXML(corpmeEdoc, context);
	}

	public String incorporarAtributos(String cadenaXML) throws OegamExcepcion {
		String urlXSD = gestorPropiedades.valorPropertie("registradores.urlXSD");
		String atributos = " xsi:noNamespaceSchemaLocation=\"" + urlXSD + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
		StringBuilder modificada = new StringBuilder();
		// Para poder hacer inserciones en la cadena:
		modificada.append(cadenaXML);
		// Localiza la posición exacta de la segunda inserción:
		int pos = modificada.indexOf("\">");
		// Inserta la cadena en la posición:
		modificada.insert(pos + 1, atributos);
		return modificada.toString();
	}

	public File incluirBloqueFirma(CORPMEEDoc corpmeEdoc, String idFirma, String idTramite, String tipoMensaje, ObjectFactory factory, XmlCORPMEFactory xmlFactory, JAXBContext context,
			UtilesViafirma utilesViafirma) throws Exception, OegamExcepcion {
		byte[] firmaDprBytes = XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma);
		byte[] selloDprBytes = XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma);

		FirmaDatosMensajeType fdmt = factory.createFirmaDatosMensajeType();
		fdmt.setFirma(firmaDprBytes);
		fdmt.setTimeStamp(selloDprBytes);
		corpmeEdoc.setFirmaDatosMensaje(fdmt);

		if (tipoMensaje.equalsIgnoreCase("DPR")) {
			FicheroBean ficheroB = new FicheroBean();
			if (TipoRegistro.REGISTRO_PROPIEDAD.getNumeroEnum().equals(corpmeEdoc.getTipoRegistro())) {
				ficheroB.setTipoDocumento(ConstantesGestorFicheros.ESCRITURAS);
			} else {
				ficheroB.setTipoDocumento(ConstantesGestorFicheros.REGISTRADORES);
			}

			ficheroB.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_XML);
			ficheroB.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroB.setNombreDocumento(idTramite + "_" + corpmeEdoc.getTipoMensaje().toUpperCase() + "_CHECK");
			ficheroB.setFichero(new File(""));
			ficheroB.setSobreescribir(true);
			ficheroB.setFecha(Utilidades.transformExpedienteFecha(idTramite));
			ficheroB.setFichero(gestorDocumentos.guardarFichero(ficheroB));
			// Graba el marshal del objeto en un fichero de prueba:
			xmlFactory.grabarEnFichero(corpmeEdoc, true, context, ficheroB.getFichero());
		}
		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeEdoc, context);
		// De momento, añado desde aquí los atributos requeridos ya que no he
		// podido modificar el xsd:
		cadenaXML = incorporarAtributos(cadenaXML);
		cadenaXML = xmlFactory.niSaltosNiEspacios(cadenaXML);
		FicheroBean ficheroBean = new FicheroBean();
		if (TipoRegistro.REGISTRO_PROPIEDAD.getNumeroEnum().equals(corpmeEdoc.getTipoRegistro())) {
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ESCRITURAS);
		} else {
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.REGISTRADORES);
		}
		ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_XML);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setNombreDocumento(idTramite + "_" + corpmeEdoc.getTipoMensaje().toUpperCase());
		ficheroBean.setFicheroByte(cadenaXML.getBytes("UTF-8"));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(idTramite));

		return gestorDocumentos.guardarFichero(ficheroBean);
	}

	private void incluirDomicilioPresentador(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {
		if (tramiteRegistro.getPresentador() != null && tramiteRegistro.getPresentador().getDireccionDto() != null) {
			DatosContactoType datosContactoType = factory.createDatosContactoType();
			DomicilioType domicilioType = factory.createDomicilioType();
			if (tramiteRegistro.getPresentador().getDireccionDto().getIdProvincia() != null) {
				domicilioType.setCodProvincia(new BigInteger(tramiteRegistro.getPresentador().getDireccionDto().getIdProvincia()));
			}
			if (tramiteRegistro.getPresentador().getDireccionDto().getIdMunicipio() != null) {
				domicilioType.setCodMunicipio(new BigInteger(tramiteRegistro.getPresentador().getDireccionDto().getIdMunicipio()));
			}
			domicilioType.setCodTipoVia(tramiteRegistro.getPresentador().getDireccionDto().getIdTipoVia());
			domicilioType.setNombreVia(tramiteRegistro.getPresentador().getDireccionDto().getNombreVia());
			domicilioType.setCodigoPostal(tramiteRegistro.getPresentador().getDireccionDto().getCodPostal());
			domicilioType.setNumero(tramiteRegistro.getPresentador().getDireccionDto().getNumero());
			domicilioType.setPais(tramiteRegistro.getPresentador().getDireccionDto().getPais());
			datosContactoType.setDomicilio(domicilioType);
			datosContactoType.setCorreoElectronico(tramiteRegistro.getPresentador().getCorreoElectronico());
			datosContactoType.setTelefono(tramiteRegistro.getPresentador().getTelefonos());
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionPresentante().getDatosContacto().add(datosContactoType);
		}
	}

	private void incluirNumeroEntradaSubsanacion(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {
		if ("S".equalsIgnoreCase(tramiteRegistro.getSubsanacion())){
			String tipoRegistro = TipoRegistro.convertirTipoTramiteToTipoRegistro(tramiteRegistro.getTipoTramite()).getValorEnum();
			if(tipoRegistro.equals(TipoRegistro.REGISTRO_MERCANTIL.getValorEnum())){
				if (tramiteRegistro.getLibroRegSub() != null && tramiteRegistro.getAnioRegSub() != null && tramiteRegistro.getNumRegSub() != null) {
					NumeroEntradaMercantilType numeroEntradaMercantilType = factory.createNumeroEntradaMercantilType();
					numeroEntradaMercantilType.setAnyo(tramiteRegistro.getAnioRegSub().toBigInteger());
					numeroEntradaMercantilType.setLibro(tramiteRegistro.getLibroRegSub().toBigInteger());
					numeroEntradaMercantilType.setNumero(tramiteRegistro.getNumRegSub().toBigInteger());

					corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().getEntrada().setNumEntradaMercantil(numeroEntradaMercantilType);
				}
			}
			if(tipoRegistro.equals(TipoRegistro.REGISTRO_PROPIEDAD.getValorEnum())){
				if (tramiteRegistro.getAnioRegSub() != null && tramiteRegistro.getNumRegSub() != null) {
					NumeroEntradaPropiedadType numeroEntradaPropiedadType = factory.createNumeroEntradaPropiedadType();
					numeroEntradaPropiedadType.setAnyo(tramiteRegistro.getAnioRegSub().toBigInteger());
					numeroEntradaPropiedadType.setNumero(tramiteRegistro.getNumRegSub().toBigInteger());

					corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().getEntrada().setNumEntradaPropiedad(numeroEntradaPropiedadType);
				}
			}

			if(tipoRegistro.equals(TipoRegistro.REGISTRO_RBM.getValorEnum())){
				if (tramiteRegistro.getNumRegSub() != null) {
					NumeroEntradaBienesMueblesType numeroEntradaBienesMueblesType = factory.createNumeroEntradaBienesMueblesType();
					numeroEntradaBienesMueblesType.setNumero(tramiteRegistro.getNumRegSub().toBigInteger());

					corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().getEntrada().setNumEntradaBienesMuebles(numeroEntradaBienesMueblesType);
				}
			}
		}
	}

	private void incluirTipoOperacion(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {

		TipoOperacionType tipoOperacionType = factory.createTipoOperacionType();

		String tipoRegistro = TipoRegistro.convertirTipoTramiteToTipoRegistro(tramiteRegistro.getTipoTramite()).getValorEnum();

		if (tipoRegistro.equals(TipoRegistro.REGISTRO_MERCANTIL.getValorEnum())) {
			Mercantil mercantil = factory.createTipoOperacionTypeMercantil();
			DatosOperacionMercantilType datosOperacionMercantil = factory.createDatosOperacionMercantilType();

			if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.setOperacion(gestorPropiedades.valorPropertie("identificacionDocumento.operacionCuenta"));
			} else if (TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.setOperacion(gestorPropiedades.valorPropertie("identificacionDocumento.operacionLibro"));
			} else if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tramiteRegistro.getTipoTramite()) || TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())
					|| TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistro.getTipoTramite()) || TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tramiteRegistro.getTipoTramite())
					|| TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				if (tramiteRegistro.getCeses() == null || tramiteRegistro.getCeses().isEmpty()) {
					tramiteRegistro.setOperacion(gestorPropiedades.valorPropertie("identificacionDocumento.operacionNombramiento"));
				} else if (tramiteRegistro.getNombramientos() == null || tramiteRegistro.getNombramientos().isEmpty()) {
					tramiteRegistro.setOperacion(gestorPropiedades.valorPropertie("identificacionDocumento.operacionCese"));
				} else {
					tramiteRegistro.setOperacion(gestorPropiedades.valorPropertie("identificacionDocumento.operacionNombramiento"));
				}
			}

			if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite()) || TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				IdentificadorAdministrativo identificadorAdministrativo = factory.createIdentificacionSujetoTypeIdentificadorAdministrativo();
				identificadorAdministrativo.setTipo("2");

				if (null != tramiteRegistro.getSociedad() && StringUtils.isNotBlank(tramiteRegistro.getSociedad().getNif())) {
					identificadorAdministrativo.setNumeroDocumento(tramiteRegistro.getSociedad().getNif());
				}

				IdentificacionSujetoType identificacionSujeto = factory.createIdentificacionSujetoType();
				identificacionSujeto.setIdentificadorAdministrativo(identificadorAdministrativo);
				identificacionSujeto.setTipoPersonalidad("3");

				if (null != tramiteRegistro.getSociedad() && StringUtils.isNotBlank(tramiteRegistro.getSociedad().getApellido1RazonSocial())) {
					identificacionSujeto.setNombre(tramiteRegistro.getSociedad().getApellido1RazonSocial());
					// En libros y cuentas el número de documento es el nombre de la sociedad.
					corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setNumeroDocumento(tramiteRegistro.getSociedad().getApellido1RazonSocial());
				}

				DepositanteType depositante = factory.createDepositanteType();
				depositante.setIdentificacionDepositante(identificacionSujeto);
				depositante.setDatosContacto(factory.createDatosContactoType());

				LegalizacionLibrosType legalizacionLibros = factory.createLegalizacionLibrosType();

				if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					DepositoCuentasType depositoCuentas = factory.createDepositoCuentasType();
					depositoCuentas.setClaseCuentas(tramiteRegistro.getClaseCuenta());
					depositoCuentas.setEjercicio(tramiteRegistro.getEjercicioCuenta());
					depositoCuentas.setDepositante(depositante);
					datosOperacionMercantil.setDepositoCuentas(depositoCuentas);
				}

				if (TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					LibroType libro;
					Libros libros = factory.createLegalizacionLibrosTypeLibros();
					List<LibroType> listaLibros = factory.createLegalizacionLibrosTypeLibros().getLibro();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					if (null != tramiteRegistro.getLibrosRegistro() && !tramiteRegistro.getLibrosRegistro().isEmpty()) {
						for (LibroRegistroDto libroDto : tramiteRegistro.getLibrosRegistro()) {
							libro = factory.createLibroType();
							libro.setNombre(libroDto.getNombreLibro());
							libro.setNombreFichero(libroDto.getNombreFichero());
							libro.setNumero(libroDto.getNumero().toBigInteger());
							libro.setFechaApertura(sdf.format(libroDto.getFecApertura()));
							libro.setFechaCierre(sdf.format(libroDto.getFecCierre()));
							if (null != libroDto.getFecCierreAnterior())
								libro.setFechaCierreAnterior(sdf.format(libroDto.getFecCierreAnterior()));
							listaLibros.add(libro);
						}
					}

					libros.setLibro(listaLibros);
					libros.setNumeroLibros(new BigInteger(String.valueOf(listaLibros.size())));
					legalizacionLibros.setLibros(libros);

					legalizacionLibros.setDepositante(depositante);
					datosOperacionMercantil.setLegalizacionLibros(legalizacionLibros);
				}

				mercantil.setDatosOperacion(datosOperacionMercantil);

			}

			mercantil.setOperacion(tramiteRegistro.getOperacion());
			tipoOperacionType.setMercantil(mercantil);

		} else if (tipoRegistro.equals(TipoRegistro.REGISTRO_PROPIEDAD.getValorEnum())) {
			Propiedad propiedad = factory.createTipoOperacionTypePropiedad();
			propiedad.setOperacion(tramiteRegistro.getOperacion());
			tipoOperacionType.setPropiedad(propiedad);

		}

		corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setTipoOperacion(tipoOperacionType);

	}

	private void incluirTipoOperacionRBM(TramiteRegRbmDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {

		TipoOperacionType tipoOperacionType = factory.createTipoOperacionType();

		// Registro Bienes Muebles
		TipoOperacionType.BienesMuebles bienesMuebles = factory.createTipoOperacionTypeBienesMuebles();
		bienesMuebles.setOperacion(tramiteRegistro.getTipoOperacion());
		bienesMuebles.setTipoContrato(tramiteRegistro.getTipoContrato());
		tipoOperacionType.setBienesMuebles(bienesMuebles);

		corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getIdentificacionDocumento().setTipoOperacion(tipoOperacionType);

	}

	private void incluirBienesRBM(TramiteRegRbmDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {

		BienesMuebles bienesMuebles = factory.createDatosPRIVADOEnvioTramiteObjetosBienesMuebles();

		if (null != tramiteRegistro.getPropiedades() && !tramiteRegistro.getPropiedades().isEmpty()) {
			String numeroMuebles = Integer.toString(tramiteRegistro.getPropiedades().size());
			bienesMuebles.setNumeroMuebles(new BigInteger(numeroMuebles));

			for (PropiedadDto propiedad : tramiteRegistro.getPropiedades()) {
				BienMuebleType bienMueble = factory.createBienMuebleType();
				BienMuebleType.DesBienMueble desBienMueble = new BienMuebleType.DesBienMueble();
				BienMuebleType.DesBienMueble.DatosCategoria datosCategoria = new BienMuebleType.DesBienMueble.DatosCategoria();
				if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.AERONAVES.getValorEnum()))
					datosCategoria.getAeronave().add(conversor.transform(propiedad.getAeronave(), org.gestoresmadrid.oegam2comun.registradores.corpme.AeronaveType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.BUQUES.getValorEnum()))
					datosCategoria.getBuque().add(conversor.transform(propiedad.getBuque(), org.gestoresmadrid.oegam2comun.registradores.corpme.BuqueType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.ESTABLECIMIENTOS_MERCANTILES.getValorEnum()))
					datosCategoria.getEstablecimiento().add(conversor.transform(propiedad.getEstablecimiento(), org.gestoresmadrid.oegam2comun.registradores.corpme.EstablecimientoType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INDUSTRIAL.getValorEnum()))
					datosCategoria.getPropiedadIndustrial().add(conversor.transform(propiedad.getPropiedadIndustrial(),
							org.gestoresmadrid.oegam2comun.registradores.corpme.PropiedadIndustrialType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INTELECTUAL.getValorEnum()))
					datosCategoria.getPropiedadIntelectual().add(conversor.transform(propiedad.getPropiedadIntelectual(),
							org.gestoresmadrid.oegam2comun.registradores.corpme.PropiedadIntelectualType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.VEHICULOS_MOTOR.getValorEnum()))
					datosCategoria.getVehiculo().add(conversor.transform(propiedad.getVehiculo(), org.gestoresmadrid.oegam2comun.registradores.corpme.VehiculoType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.MAQUINARIA_INDUSTRIAL.getValorEnum()))
					datosCategoria.getMaquinaria().add(conversor.transform(propiedad.getMaquinaria(), org.gestoresmadrid.oegam2comun.registradores.corpme.MaquinariaType.class));
				else if (propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.OTROS.getValorEnum()))
					datosCategoria.getOtrosBienes().add(conversor.transform(propiedad.getOtrosBienes(), org.gestoresmadrid.oegam2comun.registradores.corpme.OtrosBienesType.class));

				desBienMueble.setDatosCategoria(datosCategoria);
				desBienMueble.setCategoria(propiedad.getCategoria());
				bienMueble.setDesBienMueble(desBienMueble);
				if (StringUtils.isNotBlank(propiedad.getNumeroRegistral())) {
					bienMueble.setNumeroRegistralBien(new BigInteger(propiedad.getNumeroRegistral()));
				}
				bienesMuebles.getBienMueble().add(bienMueble);
			}

		} else {
			bienesMuebles.setNumeroMuebles(BigInteger.ZERO);
		}

		corpmeEdoc.getDatosPRIVADO().getEnvioTramite().setObjetos(new Objetos());
		corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getObjetos().setBienesMuebles(bienesMuebles);

	}

	private void incluirSociedad(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc, ObjectFactory factory) {
		if (tramiteRegistro.getSociedad() != null) {
			SociedadType sociedadType = factory.createSociedadType();
			sociedadType.setCIF(tramiteRegistro.getSociedad().getNif());
			sociedadType.setDenominacionSocial(tramiteRegistro.getSociedad().getApellido1RazonSocial());
			if (tramiteRegistro.getSociedad().getSeccion() != null) {
				sociedadType.setSeccion(tramiteRegistro.getSociedad().getSeccion().toBigInteger());
			}
			if (tramiteRegistro.getSociedad().getHoja() != null) {
				sociedadType.setHoja(tramiteRegistro.getSociedad().getHoja().toBigInteger());
			}
			sociedadType.setHojaBis(tramiteRegistro.getSociedad().getHojaBis());
			if (tramiteRegistro.getSociedad().getIus() != null) {
				sociedadType.setIUS(tramiteRegistro.getSociedad().getIus().toBigInteger());
			}
			corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getObjetos().getSociedades().getSociedad().add(sociedadType);
		}
	}

	private Documentos establecerDocumentos(TramiteRegistroDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {
		Ficheros documentoTypeFicheros = factory.createDocumentoTypeFicheros();
		Documentos corpmeEdocDocumentos;

		if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite()) && !TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite())
				&& !TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			documentoTypeFicheros.getFichero().add(incluirRm(tramiteRegistro, factory));
		}

		corpmeEdocDocumentos = incluirFicherosAdjuntos(tramiteRegistro, factory, alias);

		return corpmeEdocDocumentos;
	}

	private Documentos establecerDocumentos(TramiteRegRbmDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {
		Ficheros documentoTypeFicheros = factory.createDocumentoTypeFicheros();

		documentoTypeFicheros.getFichero().add(incluirRm(tramiteRegistro, factory, alias));

		FicheroType ficheroType = incluirPosiblesFicherosAdjuntos(tramiteRegistro, factory, alias);
		if (ficheroType != null) {
			documentoTypeFicheros.getFichero().add(ficheroType);
		}

		ficheroType = incluirFicheroPdfResumen(tramiteRegistro, factory, alias);
		if (ficheroType != null) {
			documentoTypeFicheros.getFichero().add(ficheroType);
		}

		Documentos corpmeEdocDocumentos = factory.createCORPMEEDocDocumentos();
		DocumentoType documentoType = factory.createDocumentoType();

		Integer numFichero = documentoTypeFicheros.getFichero().size();
		documentoTypeFicheros.setNumeroFicheros(new BigInteger(numFichero.toString()));
		documentoType.setFicheros(documentoTypeFicheros);
		documentoType.setFirmaElectronica("S");
		documentoType.setTipoDocumento(TiposFormato.XML.getNombreEnum());
		corpmeEdocDocumentos.getDocumento().add(documentoType);
		corpmeEdocDocumentos.setNumeroDocumentos(BigInteger.ONE);

		return corpmeEdocDocumentos;
	}

	private FicheroType incluirRm(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) throws Throwable {
		FicheroType ficheroType = factory.createFicheroType();
		List<Firma> listFirma = new ArrayList<Firma>();
		List<TimeStamp> listTimeStamp = new ArrayList<TimeStamp>();

		if (null != tramiteRegistro.getCertificantes()) {
			for (CertifCargoDto certif : tramiteRegistro.getCertificantes()) {
				Firma ficheroTypeFirma = factory.createFicheroTypeFirma();
				TimeStamp ficheroTypeTimeStamp = factory.createFicheroTypeTimeStamp();
				if (certif.getIdentificador() != null) {
					ficheroTypeFirma.setIdFirma(certif.getIdentificador().toBigInteger());
					ficheroTypeTimeStamp.setIdFirma(certif.getIdentificador().toBigInteger());
				}
				ficheroTypeFirma.setValue(XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStamp(certif.getIdFirma()));
				listFirma.add(ficheroTypeFirma);
				ficheroTypeTimeStamp.setValue(XmlBuilderSupportUtils.getTimeStampFromCMS(certif.getIdFirma()));
				listTimeStamp.add(ficheroTypeTimeStamp);
			}
		}
		ficheroType.setTipoFormato(TiposFormato.XML.getNombreEnum());
		ficheroType.setBase64("S");

		FileResultBean xmlSource = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_XML, Utilidades.transformExpedienteFecha(
				tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_RM, ConstantesGestorFicheros.EXTENSION_XML);
		FileInputStream fis = new FileInputStream(xmlSource.getFile());
		byte[] bytesContenido = IOUtils.toByteArray(fis);
		String contenidoBase64 = UtilesRegistradores.doBase64Encode(bytesContenido);
		ficheroType.setContenido(contenidoBase64);

		ficheroType.getFirma().addAll(listFirma);
		ficheroType.getTimeStamp().addAll(listTimeStamp);
		return ficheroType;
	}

	private FicheroType incluirRm(TramiteRegRbmDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {
		FicheroType ficheroType = factory.createFicheroType();
		List<Firma> listFirma = new ArrayList<Firma>();
		List<TimeStamp> listTimeStamp = new ArrayList<TimeStamp>();

		ficheroType.setTipoFormato(TiposFormato.XML.getNombreEnum());
		ficheroType.setBase64("S");

		FileResultBean xmlSource = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_XML, Utilidades.transformExpedienteFecha(
				tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_RM, ConstantesGestorFicheros.EXTENSION_XML);
		FileInputStream fis = new FileInputStream(xmlSource.getFile());
		byte[] bytesContenido = IOUtils.toByteArray(fis);
		String contenidoBase64 = UtilesRegistradores.doBase64Encode(bytesContenido);
		ficheroType.setContenido(contenidoBase64);
		// En RBM no hay certificantes por lo que en caso de que no haya se
		// llama al metodo de firmar para firmar el documento
		if (tramiteRegistro.getCertificantes() != null) {
			for (CertifCargoDto certif : tramiteRegistro.getCertificantes()) {
				Firma ficheroTypeFirma = factory.createFicheroTypeFirma();
				TimeStamp ficheroTypeTimeStamp = factory.createFicheroTypeTimeStamp();
				if (certif.getIdentificador() != null) {
					ficheroTypeFirma.setIdFirma(certif.getIdentificador().toBigInteger());
					ficheroTypeTimeStamp.setIdFirma(certif.getIdentificador().toBigInteger());
				}
				ficheroTypeFirma.setValue(XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStamp(certif.getIdFirma()));
				listFirma.add(ficheroTypeFirma);
				ficheroTypeTimeStamp.setValue(XmlBuilderSupportUtils.getTimeStampFromCMS(certif.getIdFirma()));
				listTimeStamp.add(ficheroTypeTimeStamp);
			}
		} else {
			firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType, xmlSource.getFile().getName(), factory, alias);
		}
		return ficheroType;
	}

	// private FicheroType incluirPosiblesFicherosAdjuntos(TramiteRegistroDto tramiteRegistro, ObjectFactory factory,
	// String alias) throws Throwable {
	// if (tramiteRegistro.getFicheroAEnviar() != null) {
	// FicheroType ficheroType = factory.createFicheroType();
	// ficheroType.setBase64("S");
	// FileInputStream fis = new FileInputStream(tramiteRegistro.getFicheroAEnviar());
	// byte[] bytesContenido = IOUtils.toByteArray(fis);
	// String contenidoCodificado = UtilesRegistradores.doBase64Encode(bytesContenido);
	// ficheroType.setContenido(contenidoCodificado);
	// ficheroType.setTipoFormato(tramiteRegistro.getExtensionFicheroEnviado().toUpperCase());
	// firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType,
	// tramiteRegistro.getFicheroAEnviar().getName(), factory, alias);
	// return ficheroType;
	// }
	// return null;
	// }

	private FicheroType incluirPosiblesFicherosAdjuntos(TramiteRegRbmDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {
		if (tramiteRegistro.getFicheroAEnviar() != null) {
			FicheroType ficheroType = factory.createFicheroType();
			ficheroType.setBase64("S");
			FileInputStream fis = new FileInputStream(tramiteRegistro.getFicheroAEnviar());
			byte[] bytesContenido = IOUtils.toByteArray(fis);
			String contenidoCodificado = UtilesRegistradores.doBase64Encode(bytesContenido);
			ficheroType.setContenido(contenidoCodificado);
			ficheroType.setTipoFormato(tramiteRegistro.getExtensionFicheroEnviado().toUpperCase());
			firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType, tramiteRegistro.getFicheroAEnviar().getName(), factory, alias);
			return ficheroType;
		}
		return null;
	}

	// private FicheroType incluirFicheroPdfResumen(TramiteRegistroDto tramiteRegistro, ObjectFactory factory,
	// String alias) throws Throwable {
	// FileReader fr = null;
	// BufferedReader br = null;
	// StringBuffer resultado = new StringBuffer();
	// try {
	// FileResultBean file = modeloGuardar.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.REGISTRADORES,
	// ConstantesGestorFicheros.REGISTRADORES_HTML,
	// Utilidades.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro()),
	// tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_DOC,
	// ConstantesGestorFicheros.EXTENSION_HTML);
	// if (file != null && file.getFile() != null) {
	// fr = new FileReader(file.getFile());
	// br = new BufferedReader(fr);
	// String linea = null;
	// int numLineas = 0;
	// while ((linea = br.readLine()) != null) {
	// formateoHtmlToPdf(resultado, linea, numLineas);
	// numLineas++;
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (fr != null)
	// fr.close();
	// }
	//
	// byte[] pdf = generarPDFTemporal(resultado);
	// if (pdf != null) {
	// File filePdf = modeloGuardar.guardarFichero(ConstantesGestorFicheros.REGISTRADORES,
	// ConstantesGestorFicheros.REGISTRADORES_HTML,
	// Utilidades.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro()),
	// tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_DOC,
	// ConstantesGestorFicheros.EXTENSION_PDF, pdf);
	//
	// String habilitarPdfResumen = gestorPropiedades.valorPropertie("registradores.habilitar.pdf.resumen");
	// if (habilitarPdfResumen != null && "SI".equals(habilitarPdfResumen)) {
	// if (filePdf != null) {
	// FicheroType ficheroType = factory.createFicheroType();
	// ficheroType.setBase64("S");
	// FileInputStream fis = new FileInputStream(filePdf);
	// byte[] bytesContenido = IOUtils.toByteArray(fis);
	// String contenidoCodificado = UtilesRegistradores.doBase64Encode(bytesContenido);
	// ficheroType.setContenido(contenidoCodificado);
	// ficheroType.setTipoFormato("PDF");
	// firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType, filePdf.getName(), factory, alias);
	// return ficheroType;
	// }
	// }
	// } else {
	// log.error("No se ha podido adjuntar el PDF Resumen");
	// }
	// return null;
	// }

	private FicheroType incluirFicheroPdfResumen(TramiteRegRbmDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {
		FileReader fr = null;
		BufferedReader br = null;
		StringBuffer resultado = new StringBuffer();
		try {
			FileResultBean file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_HTML, Utilidades.transformExpedienteFecha(
					tramiteRegistro.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_DOC, ConstantesGestorFicheros.EXTENSION_HTML);
			if (file != null && file.getFile() != null) {
				fr = new FileReader(file.getFile());
				br = new BufferedReader(fr);
				String linea = null;
				int numLineas = 0;
				while ((linea = br.readLine()) != null) {
					formateoHtmlToPdf(resultado, linea, numLineas);
					numLineas++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				fr.close();
		}

		byte[] pdf = generarPDFTemporal(resultado);
		if (pdf != null) {
			File filePdf = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_HTML, Utilidades.transformExpedienteFecha(tramiteRegistro
					.getIdTramiteRegistro()), tramiteRegistro.getIdTramiteRegistro() + ConstantesGestorFicheros.NOMBRE_DOC, ConstantesGestorFicheros.EXTENSION_PDF, pdf);

			String habilitarPdfResumen = gestorPropiedades.valorPropertie("registradores.habilitar.pdf.resumen");
			if (habilitarPdfResumen != null && "SI".equals(habilitarPdfResumen)) {
				if (filePdf != null) {
					FicheroType ficheroType = factory.createFicheroType();
					ficheroType.setBase64("S");
					FileInputStream fis = new FileInputStream(filePdf);
					byte[] bytesContenido = IOUtils.toByteArray(fis);
					String contenidoCodificado = UtilesRegistradores.doBase64Encode(bytesContenido);
					ficheroType.setContenido(contenidoCodificado);
					ficheroType.setTipoFormato("PDF");
					firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType, filePdf.getName(), factory, alias);
					return ficheroType;
				}
			}
		} else {
			log.error("No se ha podido adjuntar el PDF Resumen");
		}
		return null;
	}

	private void firmarDocumentos(TramiteRegistroDto tramiteRegistro, byte[] byteContenido, FicheroType ficheroType, String name, ObjectFactory factory, String alias) throws Throwable {
		List<Firma> listFirma = new ArrayList<Firma>();
		List<TimeStamp> listTimeStamp = new ArrayList<TimeStamp>();

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDocNotificacion(byteContenido, tramiteRegistro.getIdTramiteRegistro().toString(), utiles.dameExtension(name, true), alias);

		Firma ficheroTypeFirma = factory.createFicheroTypeFirma();
		byte[] firma = XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma);
		String firmaCodificada = UtilesRegistradores.doBase64Encode(firma);
		ficheroTypeFirma.setValue(firmaCodificada);
		listFirma.add(ficheroTypeFirma);

		TimeStamp ficheroTypeTimeStamp = factory.createFicheroTypeTimeStamp();
		byte[] sello = XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma);
		String selloCodificado = UtilesRegistradores.doBase64Encode(sello);
		ficheroTypeTimeStamp.setValue(selloCodificado);
		listTimeStamp.add(ficheroTypeTimeStamp);

		ficheroType.getFirma().addAll(listFirma);
		ficheroType.getTimeStamp().addAll(listTimeStamp);
	}

	private void firmarDocumentos(TramiteRegRbmDto tramiteRegistro, byte[] byteContenido, FicheroType ficheroType, String name, ObjectFactory factory, String alias) throws Throwable {
		List<Firma> listFirma = new ArrayList<Firma>();
		List<TimeStamp> listTimeStamp = new ArrayList<TimeStamp>();

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.signHsmDocNotificacion(byteContenido, tramiteRegistro.getIdTramiteRegistro().toString(), utiles.dameExtension(name, true), alias);

		Firma ficheroTypeFirma = factory.createFicheroTypeFirma();
		byte[] firma = XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma);
		String firmaCodificada = UtilesRegistradores.doBase64Encode(firma);
		ficheroTypeFirma.setValue(firmaCodificada);
		listFirma.add(ficheroTypeFirma);

		TimeStamp ficheroTypeTimeStamp = factory.createFicheroTypeTimeStamp();
		byte[] sello = XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma);
		String selloCodificado = UtilesRegistradores.doBase64Encode(sello);
		ficheroTypeTimeStamp.setValue(selloCodificado);
		listTimeStamp.add(ficheroTypeTimeStamp);

		ficheroType.getFirma().addAll(listFirma);
		ficheroType.getTimeStamp().addAll(listTimeStamp);
	}

	private void establecerAtributosCabecera(CORPMEEDoc corpmeEdoc, TramiteRegistroDto tramiteRegistro, String tipoMensaje) {

		if (tramiteRegistro.getIdTramiteCorpme() != null && !tramiteRegistro.getIdTramiteCorpme().isEmpty()) {
			corpmeEdoc.setIdTramite(tramiteRegistro.getIdTramiteCorpme());
		} else {
			corpmeEdoc.setIdTramite(tramiteRegistro.getIdTramiteRegistro().toString());
		}

		BigInteger tipoRegistro = TipoRegistro.convertirTipoTramiteToTipoRegistro(tramiteRegistro.getTipoTramite()).getNumeroEnum();

		if (tipoRegistro == null) {
			Long tipoRegistroPredefinido = Long.parseLong(gestorPropiedades.valorPropertie("corpmeEdoc.tipoRegistro"));
			corpmeEdoc.setTipoRegistro(BigInteger.valueOf(tipoRegistroPredefinido));
		} else {
			corpmeEdoc.setTipoRegistro(tipoRegistro);
		}

		// Establecemos el registro indicado en el trámite.
		corpmeEdoc.setCodigoRegistro(tramiteRegistro.getRegistro().getIdRegistro());

		if (tramiteRegistro.getTipoTramite().equals(TipoTramiteRegistro.MODELO_10.getValorEnum())) {
			if (gestorPropiedades.valorPropertie("establecer.registro.bienes.muebles.cancelaciones").equalsIgnoreCase("SI")) {
				corpmeEdoc.setCodigoRegistro(gestorPropiedades.valorPropertie("codigo.registro.bienes.muebles.cancelaciones"));
			}
		} else {
			if (gestorPropiedades.valorPropertie("establecer.codigo.registro.integracion").equalsIgnoreCase("si")) {
				if (TipoRegistro.REGISTRO_PROPIEDAD.getNumeroEnum().equals(tipoRegistro)) {
					corpmeEdoc.setCodigoRegistro(gestorPropiedades.valorPropertie("corpmeEdoc.codigoRegistro.propiedad"));
				} else {
					corpmeEdoc.setCodigoRegistro(gestorPropiedades.valorPropertie("corpmeEdoc.codigoRegistro"));
				}
			}

		}
		corpmeEdoc.setCodigoEntidad(gestorPropiedades.valorPropertie("corpmeEdoc.codigoEntidad"));
		corpmeEdoc.setTipoEntidad(gestorPropiedades.valorPropertie("corpmeEdoc.tipoEntidad"));
		corpmeEdoc.setVersion(gestorPropiedades.valorPropertie("corpmeEdoc.version"));
		corpmeEdoc.setTipoMensaje(tipoMensaje);
	}

	private void rellenarDatos(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc) {
		rellenarTipoEntrada(tramiteRegistro);

		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			tramiteRegistro.setAplicarIrpf("N");
		}

		if (StringUtils.isNotBlank(tramiteRegistro.getPorcentajeIrpf())) {
			tramiteRegistro.setPorcentajeIrpf(String.valueOf(tramiteRegistro.getPorcentajeIrpf()).replace(".", ","));
			if(!tramiteRegistro.getPorcentajeIrpf().contains(",")){
				tramiteRegistro.setPorcentajeIrpf(tramiteRegistro.getPorcentajeIrpf() + ",00");
			}
		}

		if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			tramiteRegistro.setNumSociedades(BigDecimal.ONE);
		} else {
			if (Inmatriculada.S.getValorEnum().equals(tramiteRegistro.getInmatriculada())) {
				if (tramiteRegistro.getInmuebles() != null && !tramiteRegistro.getInmuebles().isEmpty()) {
					tramiteRegistro.setNumInmuebles(new BigDecimal(tramiteRegistro.getInmuebles().size()));
				}
			} else {
				tramiteRegistro.setNumInmuebles(BigDecimal.ONE);
				InmuebleDto inmueble = new InmuebleDto();
				inmueble.setInmatriculada(Inmatriculada.N.getValorEnum());
				ArrayList<InmuebleDto> inmuebles = new ArrayList<InmuebleDto>();
				inmuebles.add(inmueble);
				tramiteRegistro.setInmuebles(inmuebles);
			}
		}
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			tramiteRegistro.setVersion(gestorPropiedades.valorPropertie("envioEscrituras.corpmeEdoc.datosPrivado.atributo.version"));
		} else {
			tramiteRegistro.setVersion(gestorPropiedades.valorPropertie("datosPrivado.version"));
		}
		rellenarPresentadorYDestinatario(tramiteRegistro);

		// Rellenamos datos de forma de pago
		if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			tramiteRegistro.setFormaPago(new BigDecimal(TipoFormaPago.USUARIO_ABONADO.getValorEnum()));
		} else {
			rellenarDatosFormaPago(tramiteRegistro, corpmeEdoc);
		}

	}

	private void rellenarDatos(TramiteRegRbmDto tramiteRegistro) {
		rellenarTipoEntrada(tramiteRegistro);
		tramiteRegistro.setFormaPago(new BigDecimal(TipoFormaPago.USUARIO_ABONADO.getValorEnum()));

		if (StringUtils.isNotBlank(tramiteRegistro.getPorcentajeIrpf())) {
			tramiteRegistro.setPorcentajeIrpf(String.valueOf(tramiteRegistro.getPorcentajeIrpf()).replace(".", ","));
			if(!tramiteRegistro.getPorcentajeIrpf().contains(",")){
				tramiteRegistro.setPorcentajeIrpf(tramiteRegistro.getPorcentajeIrpf() + ",00");
			}
		}

		tramiteRegistro.setVersion(gestorPropiedades.valorPropertie("datosPrivado.version"));

		rellenarPresentadorYDestinatario(tramiteRegistro);
	}

	private void rellenarTipoEntrada(TramiteRegistroDto tramiteRegistro) {
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			if (tramiteRegistro.isCheckSubsanacion()) {
				tramiteRegistro.setTipoEntrada(TipoEntrada.SUBSANACION.getValorEnum());
			} else {
				tramiteRegistro.setTipoEntrada(TipoEntrada.PRIMERA.getValorEnum());
			}
		} else {
			if ("S".equals(tramiteRegistro.getSubsanacion()) || "SI".equals(tramiteRegistro.getSubsanacion())) {
				tramiteRegistro.setTipoEntrada(TipoEntrada.SUBSANACION.getValorEnum());
			} else {
				tramiteRegistro.setTipoEntrada(TipoEntrada.PRIMERA.getValorEnum());
			}
		}
	}

	private void rellenarTipoEntrada(TramiteRegRbmDto tramiteRegistro) {
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			if (tramiteRegistro.isCheckSubsanacion()) {
				tramiteRegistro.setTipoEntrada(TipoEntrada.SUBSANACION.getValorEnum());
			} else {
				tramiteRegistro.setTipoEntrada(TipoEntrada.PRIMERA.getValorEnum());
			}
		} else {
			if ("S".equals(tramiteRegistro.getSubsanacion()) || "SI".equals(tramiteRegistro.getSubsanacion())) {
				tramiteRegistro.setTipoEntrada(TipoEntrada.SUBSANACION.getValorEnum());
			} else {
				tramiteRegistro.setTipoEntrada(TipoEntrada.PRIMERA.getValorEnum());
			}
		}
	}

	private void rellenarPresentadorYDestinatario(TramiteRegistroDto tramiteRegistro) {
		PersonaDto presentador = null;
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			presentador = servicioPersona.getPersonaConDireccion(tramiteRegistro.getCifTitularCuenta(), tramiteRegistro.getNumColegiado());
			PersonaDto destinatario = tramiteRegistro.getSociedad();
			normalizarPersona(presentador);
			normalizarPersona(destinatario);
			tramiteRegistro.setPresentador(presentador);
			tramiteRegistro.setDestinatario(destinatario);
		}else if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite())
				|| TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			presentador = servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato());
			PersonaDto destinatario;
			//Los datos de facturación pueden ir a nombre del presentante o de la sociedad, según se haya indicado.
			if("PRESENTADOR".equalsIgnoreCase(tramiteRegistro.getTipoDestinatario())){
				destinatario = presentador;
			} else {
				destinatario = (PersonaDto)tramiteRegistro.getSociedad().clone();
			}
			normalizarPersona(presentador);
			normalizarPersona(destinatario);
			tramiteRegistro.setPresentador(presentador);
			tramiteRegistro.setDestinatario(destinatario);
		} else { 
			presentador = servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato());
			normalizarPersona(presentador);
			tramiteRegistro.setPresentador(presentador);
			tramiteRegistro.setDestinatario(presentador);
		}
	}

	private void rellenarPresentadorYDestinatario(TramiteRegRbmDto tramiteRegistro) {
		PersonaDto presentador = null;

		presentador = servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato());
		normalizarPersona(presentador);
		tramiteRegistro.setPresentador(presentador);
		tramiteRegistro.setDestinatario(presentador);

	}

	private PersonaDto normalizarPersona(PersonaDto persona) {
		if (persona != null) {
			if (TipoPersona.Fisica.getValorEnum().equals(persona.getTipoPersona())) {
				persona.setTipoPersonalidad(TipoPersonalidad.Fisica.getValorEnum());
				persona.setTipoDocumento(TipoDocumento.NIF.getValorEnum());
				if (persona.getApellido2() != null) {
					persona.setApellidos(persona.getApellido1RazonSocial() + " " + persona.getApellido2());
				} else {
					persona.setApellidos(persona.getApellido1RazonSocial());
				}
			} else if (TipoPersona.Juridica.getValorEnum().equals(persona.getTipoPersona())) {
				persona.setTipoDocumento(TipoDocumento.CIF.getValorEnum());
				if (SubtipoPersona.Privada.getNombreEnum().equals(persona.getSubtipo())) {
					persona.setTipoPersonalidad(TipoPersonalidad.JuridicaPrivada.getValorEnum());
				} else if (SubtipoPersona.Publica.getNombreEnum().equals(persona.getSubtipo())) {
					persona.setTipoPersonalidad(TipoPersonalidad.JuridicaPublica.getValorEnum());
				}

				persona.setNombre(persona.getApellido1RazonSocial());
				persona.setApellido1RazonSocial("");
			}

			if (persona.getDireccionDto() != null) {
				persona.getDireccionDto().setPais("ES");
			}
		}
		return persona;
	}

	private void formateoHtmlToPdf(StringBuffer resultado, String linea, int numLineas) {
		if (numLineas != 0 && numLineas != 2 && numLineas != 3 && numLineas != 4) {
			if (linea.contains("&aacute;")) {
				linea = linea.replace("&aacute;", "á");
			}
			if (linea.contains("&Aacute;")) {
				linea = linea.replace("&Aacute;", "Á");
			}
			if (linea.contains("&eacute;")) {
				linea = linea.replace("&eacute;", "é");
			}
			if (linea.contains("&Eacute;")) {
				linea = linea.replace("&Eacute;", "É");
			}
			if (linea.contains("&iacute;")) {
				linea = linea.replace("&iacute;", "í");
			}
			if (linea.contains("&Iacute;")) {
				linea = linea.replace("&Iacute;", "Í");
			}
			if (linea.contains("&oacute;")) {
				linea = linea.replace("&oacute;", "ó");
			}
			if (linea.contains("&Oacute;")) {
				linea = linea.replace("&Oacute;", "Ó");
			}
			if (linea.contains("&uacute;")) {
				linea = linea.replace("&uacute;", "ú");
			}
			if (linea.contains("&Uacute;")) {
				linea = linea.replace("&Uacute;", "Ú");
			}
			if (linea.contains("&nbsp;")) {
				linea = linea.replace("&nbsp;", " ");
			}
			if (linea.contains("<font color=\"#990000\">")) {
				linea = linea.replace("<font color=\"#990000\">", "<span style=\"color:#990000\">");
			}
			if (linea.contains("</font>")) {
				linea = linea.replace("</font>", "</span>");
			}
			if (linea.contains("border=\"1\"")) {
				linea = linea.replace("border=\"1\"", "");
			}
			if (linea.contains("&deg;")) {
				linea = linea.replace("&deg;", "º");
			}
			if (linea.contains("&ntilde;")) {
				linea = linea.replace("&ntilde;", "ñ");
			}
			resultado.append(linea);
		} else if (numLineas == 0) {
			resultado.append("<html>");
		}
	}

	private byte[] generarPDFTemporal(StringBuffer resultado) {
		OutputStream outputStream = null;
		byte[] result = null;
		try {
			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocumentFromString(resultado.toString());
			iTextRenderer.layout();
			outputStream = new ByteArrayOutputStream();
			iTextRenderer.createPDF(outputStream);
			result = ((ByteArrayOutputStream) outputStream).toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
		return result;
	}

	// private void comprimirSiTieneDocumentacionAdjunta(TramiteRegistroDto tramiteRegistro) throws Throwable {
	// ArrayList<FicheroInfo> ficherosSubidos = tramiteRegistro.getFicherosSubidos();
	// if (ficherosSubidos != null && !ficherosSubidos.isEmpty()) {
	// File completo = null;
	// if (ficherosSubidos.size() > 1) {
	// GuardarDocumentosInterfaz modeloGuardar = ContextoSpring.getInstance()
	// .getBean(GuardarDocumentosInterfaz.class);
	//
	// String carpeta = "";
	// String subCarpeta = "";
	// if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
	// carpeta = ConstantesGestorFicheros.ESCRITURAS;
	// subCarpeta = ConstantesGestorFicheros.ESCRITURAS_DOCUMENTACION_ENVIADA;
	// } else {
	// carpeta = ConstantesGestorFicheros.REGISTRADORES;
	// subCarpeta = ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION_ENVIADA;
	// }
	//
	// completo = empaquetarEnZip(modeloGuardar.crearRuta(carpeta, subCarpeta,
	// Utilidades.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro()))
	// + "fichero_registro_" + new Date().getTime() + "_" + tramiteRegistro.getIdTramiteRegistro(),
	// ficherosSubidos);
	//
	// File[] ficherosABorrar = new File[ficherosSubidos.size()];
	// for (int i = 0; i < ficherosSubidos.size(); i++) {
	// ficherosABorrar[i] = ficherosSubidos.get(i).getFichero();
	// }
	// for (int i = 0; i < ficherosSubidos.size(); i++) {
	// ficherosABorrar[i].delete();
	// }
	// tramiteRegistro.setExtensionFicheroEnviado("zip");
	// } else {
	// completo = ficherosSubidos.get(0).getFichero();
	// tramiteRegistro.setExtensionFicheroEnviado(ficherosSubidos.get(0).getExtension());
	// }
	// if (completo != null) {
	// tramiteRegistro.setFicheroAEnviar(completo);
	// ArrayList<FicheroInfo> nuevaLista = new ArrayList<FicheroInfo>();
	// nuevaLista.add(new FicheroInfo(completo, 0));
	// tramiteRegistro.setFicherosSubidos(nuevaLista);
	// tramiteRegistro.setFicheroSubido("si");
	// }
	// }
	// }

	private void comprimirSiTieneDocumentacionAdjunta(TramiteRegRbmDto tramiteRegistro) throws Throwable {
		ArrayList<FicheroInfo> ficherosSubidos = tramiteRegistro.getFicherosSubidos();
		if (ficherosSubidos != null && !ficherosSubidos.isEmpty()) {
			File completo = null;
			if (ficherosSubidos.size() > 1) {
				String carpeta = "";
				String subCarpeta = "";
				if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					carpeta = ConstantesGestorFicheros.ESCRITURAS;
					subCarpeta = ConstantesGestorFicheros.ESCRITURAS_DOCUMENTACION_ENVIADA;
				} else {
					carpeta = ConstantesGestorFicheros.REGISTRADORES;
					subCarpeta = ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION_ENVIADA;
				}

				completo = empaquetarEnZip(gestorDocumentos.crearRuta(carpeta, subCarpeta, Utilidades.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro())) + "fichero_registro_" + new Date()
						.getTime() + "_" + tramiteRegistro.getIdTramiteRegistro(), ficherosSubidos);

				File[] ficherosABorrar = new File[ficherosSubidos.size()];
				for (int i = 0; i < ficherosSubidos.size(); i++) {
					ficherosABorrar[i] = ficherosSubidos.get(i).getFichero();
				}
				for (int i = 0; i < ficherosSubidos.size(); i++) {
					ficherosABorrar[i].delete();
				}
				tramiteRegistro.setExtensionFicheroEnviado("zip");
			} else {
				completo = ficherosSubidos.get(0).getFichero();
				tramiteRegistro.setExtensionFicheroEnviado(ficherosSubidos.get(0).getExtension());
			}
			if (completo != null) {
				tramiteRegistro.setFicheroAEnviar(completo);
				ArrayList<FicheroInfo> nuevaLista = new ArrayList<FicheroInfo>();
				nuevaLista.add(new FicheroInfo(completo, 0));
				tramiteRegistro.setFicherosSubidos(nuevaLista);
				tramiteRegistro.setFicheroSubido("si");
			}
		}
	}

	private File empaquetarEnZip(String nombreDelZip, ArrayList<FicheroInfo> ficheros) throws IOException {
		if (ficheros.size() <= 1) {
			return null;
		}
		File destino = new File(nombreDelZip + ".zip");
		FileOutputStream out = new FileOutputStream(destino);
		ZipOutputStream zip = new ZipOutputStream(out);
		for (FicheroInfo file : ficheros) {
			FileInputStream is = new FileInputStream(file.getFichero());
			ZipEntry zipEntry = new ZipEntry(file.getNombre());
			zip.putNextEntry(zipEntry);
			byte[] buffer = new byte[2048];
			int byteCount;
			while (-1 != (byteCount = is.read(buffer))) {
				zip.write(buffer, 0, byteCount);
			}
			zip.closeEntry();
			is.close();
		}
		zip.close();

		return destino;
	}

	private Documentos incluirFicherosAdjuntos(TramiteRegistroDto tramiteRegistro, ObjectFactory factory, String alias) throws Throwable {

		Documentos corpmeEdocDocumentos = factory.createCORPMEEDocDocumentos();

		if (null != tramiteRegistro.getFicherosSubidos() && !tramiteRegistro.getFicherosSubidos().isEmpty()) {
			for (FicheroInfo elemento : tramiteRegistro.getFicherosSubidos()) {
				Ficheros documentoTypeFicheros = factory.createDocumentoTypeFicheros();
				FicheroType ficheroType = factory.createFicheroType();
				DocumentoType documentoType = factory.createDocumentoType();
				ficheroType.setBase64("S");
				FileInputStream fis = new FileInputStream(elemento.getFichero());
				byte[] bytesContenido = IOUtils.toByteArray(fis);
				String contenidoCodificado = UtilesRegistradores.doBase64Encode(bytesContenido);
				ficheroType.setContenido(contenidoCodificado);
				ficheroType.setTipoFormato(elemento.getExtension().toUpperCase());

				// Si es un trámite de Libro o Cuenta se añade al fichero el nombre y la ocupación en Bytes
				if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite()) || TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					if ("zip".equalsIgnoreCase(elemento.getExtension())) {
						ficheroType.setNombreFichero(elemento.getNombre().substring(0, 16).toUpperCase() + "." + elemento.getExtension().toUpperCase());
					}
					ficheroType.setOcupacionBytes(BigInteger.valueOf(bytesContenido.length));
				}

				firmarDocumentos(tramiteRegistro, bytesContenido, ficheroType, elemento.getNombre(), factory, alias);
				documentoTypeFicheros.getFichero().add(ficheroType);
				documentoType.setFicheros(documentoTypeFicheros);
				documentoType.setFirmaElectronica("S");
				
				// Si no es un trámite de Libro o Cuenta el tipo de documento
				if (!TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite()) && !TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					documentoType.setTipoDocumento(ficheroType.getTipoFormato());
				}

				Integer numFichero = documentoTypeFicheros.getFichero().size();
				documentoTypeFicheros.setNumeroFicheros(new BigInteger(numFichero.toString()));
				corpmeEdocDocumentos.getDocumento().add(documentoType);
			}
			Integer numDocumentos = tramiteRegistro.getFicherosSubidos().size();
			corpmeEdocDocumentos.setNumeroDocumentos(new BigInteger(numDocumentos.toString()));

			return corpmeEdocDocumentos;
		}
		return null;
	}

	public void rellenarDatosFormaPago(TramiteRegistroDto tramiteRegistro, CORPMEEDoc corpmeEdoc) {
		if (TipoFormaPago.CUENTA.getValorEnum().equals(tramiteRegistro.getFormaPago().toString())) {
			ResultBean resultBean = servicioDatosBancariosFavoritos.getDatoBancarioRegistradores(tramiteRegistro.getDatosBancarios().getIdDatosBancarios());
			tramiteRegistro.setDatosBancarios((DatosBancariosFavoritosDto) resultBean.getAttachment("datosBancariosDto"));
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().setCuentaBancaria(new CuentaBancariaType());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setNombreTitular(tramiteRegistro.getDatosBancarios().getNombreTitular());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().getLocalidadTitular().setCodigoProvincia(tramiteRegistro.getDatosBancarios().getIdProvincia());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().getLocalidadTitular().setCodigoMunicipio(tramiteRegistro.getDatosBancarios().getIdMunicipio());

			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setIBAN(tramiteRegistro.getDatosBancarios().getIban());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setCodigoEntidad(tramiteRegistro.getDatosBancarios().getEntidad());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setCodigoSucursal(tramiteRegistro.getDatosBancarios().getOficina());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setDigitoControl(tramiteRegistro.getDatosBancarios().getDc());
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().getCuentaBancaria().setNumeroCuenta(tramiteRegistro.getDatosBancarios().getNumeroCuentaPago());

		} else if (TipoFormaPago.TRANSFERENCIA.getValorEnum().equals(tramiteRegistro.getFormaPago().toString()) && null != tramiteRegistro.getFacturasRegistro() && !tramiteRegistro
				.getFacturasRegistro().isEmpty()) {
			tramiteRegistro.setDatosBancarios(null);
			// Facturas facturas = new Facturas();
			//
			//
			// int numFacturas = tramiteRegistro.getFacturasRegistro().size();
			// for (int i = 0; i < numFacturas; i++) {
			// Factura factura = new Factura();
			// factura.setFechaPago(tramiteRegistro.getFacturasRegistro().get(i).getFechaPago().toString());
			// factura.setIdentificadorTransferencia(tramiteRegistro.getFacturasRegistro().get(i).getIdTransferencia());
			// factura.setFacturaPagada(new IdentificacionFacturaType());
			// factura.getFacturaPagada().setCif(tramiteRegistro.getFacturasRegistro().get(i).getCifEmisor());
			// factura.getFacturaPagada().setSerieFactura(tramiteRegistro.getFacturasRegistro().get(i).getNumSerie());
			// factura.getFacturaPagada().setEjercicioFactura(tramiteRegistro.getFacturasRegistro().get(i).getEjercicio());
			// factura.getFacturaPagada().setNumeroFactura(tramiteRegistro.getFacturasRegistro().get(i).getNumFactura());
			// factura.getFacturaPagada().setFechaFactura(tramiteRegistro.getFacturasRegistro().get(i).getFechaFactura().toString());
			// facturas.getFactura().add(factura);
			// }
			//
			// facturas.setNumeroFacturas(BigInteger.valueOf(tramiteRegistro.getFacturasRegistro().size()));
			// corpmeEdoc.getDatosPRIVADO().setConfirmacionPago(new ConfirmacionPagoFacturasType());
			// corpmeEdoc.getDatosPRIVADO().getConfirmacionPago().setFacturas(facturas);
		} else if (TipoFormaPago.USUARIO_ABONADO.getValorEnum().equals(tramiteRegistro.getFormaPago().toString())) {
			// corpmeEdoc.getDatosPRIVADO().getEnvioTramite().getDatosFacturacion().getFormaPagoFactura().setUsuarioAbonadoCorpme(tramiteRegistro.getIdentificacionCorpme());
			tramiteRegistro.setDatosBancarios(null);
		}
	}

}
