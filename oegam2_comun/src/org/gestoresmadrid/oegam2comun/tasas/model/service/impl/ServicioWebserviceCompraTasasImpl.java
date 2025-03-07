package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioWebserviceCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.webservice.handler.SoapTasasPagoItvFilesHandler;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.TasasSolicitadasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvServiceLocator;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.validaciones.UtilesCuentaBancaria;
import viafirma.utilidades.UtilesViafirma;
import viafirma.utilidades.ws.SoapXMLDSignerHandler;

@Service
public class ServicioWebserviceCompraTasasImpl implements ServicioWebserviceCompraTasas, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9161697923004482935L;

	// Constantes
	private final static String ERROR = "ERROR";

	private final static String FORMATO_LITERAL = "%-24S";
	private final static String FORMATO_VALOR_SALTO_LINEA = "%S%n";
	private final static String FORMATO_VALOR = "%S";
	private final static String FORMATO_VALOR_Y_GUION = "%S-";
	private final static String FORMATO_VALOR_Y_BARRA = "%S/";
	private final static String LITERAL_JUSTIFICANTE = "JUSTIFICANTE:";
	private final static String LITERAL_NIF_CIF = "NIF/CIF:";
	private final static String LITERAL_IMPORTE_INGRESO = "IMPORTE DEL INGRESO:";
	private final static String LITERAL_IBAN = "IBAN:";

	// Constantes de claves de configuracion en properties
	private final static String SERVICIO_COMPRA_TASAS_APLICACION = "servicio.compra.tasas.aplicacion";
	private final static String SERVICIO_COMPRA_TASAS_MAX_ONDEMAND = "servicio.compra.tasas.max.ondemand";
	private final static String SERVICIO_COMPRA_TASAS_APLICACION_BATCH = "servicio.compra.tasas.aplicacion.batch";
	private final static String SERVICIO_COMPRA_TASAS_URL = "servicio.compra.tasas.url";

	private final static String SERVICIO_PRESUPUESTO = "PRE";
	private final static String SERVICIO_REALIZAR_PAGO = "PAGO";
	private final static String SERVICIO_OBTENER_DATOS = "DATOS";
	private final static String SERVICIO_DESCARGA_JUSTIFICANTE = "JUST";

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioWebserviceCompraTasasImpl.class);

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public String obtenerNumeroJustificante(Long idCompra) throws RemoteException, ServiceException {
		LOG.debug("Inicio de servicio obtenerNumeroJustificante");
		return initializePagoItv(idCompra, SERVICIO_PRESUPUESTO).obtenerNumeroJustificante();
	}

	@Override
	public ResultadoOperacionCompraTasas realizarPagoTasas(ResumenCompraBean compra) throws RemoteException, ServiceException {
		LOG.debug("Inicio de PagoItv.realizarPago");
		TipoIdentificador tipoIdentificador = NIFValidator.isNifNie(compra.getCifContrato()) ? TipoIdentificador.NIF : TipoIdentificador.CIF;

		// Se transforman las tasas solicitas al formato requerido, y se cuenta la cantidad de tasas que se van a comprar.
		long cantidad = 0L;
		SolicitudTasa[] tasasSolicitadas = new SolicitudTasa[compra.getListaResumenCompraBean().size()];
		for (int i = 0; i < compra.getListaResumenCompraBean().size(); i++) {
			TasasSolicitadasBean t = compra.getListaResumenCompraBean().get(i);
			SolicitudTasa solicitudTasa = new SolicitudTasa(t.getCantidad().intValue(), t.getGrupo(), t.getCodigoTasa());
			tasasSolicitadas[i] = solicitudTasa;
			cantidad += t.getCantidad().longValue();
		}

		DatosFirma datosFirma = generateDatosFirma(compra);
		DatosCuentaCorriente datosCuentaCorriente = generateDatosCuentaCorriente(compra);
		DatosTarjeta datosTarjeta = generateDatosTarjeta(compra);
		SolicitudPagoTasasDatosAdicionalesEntry[] datosAdicionales = {};

		// Si el numero de tasas de la compra es mayor de 999 se generan en batch, y el codigo de aplicacion es otro (MASI)
		String aplicacion;
		String max = gestorPropiedades.valorPropertie(SERVICIO_COMPRA_TASAS_MAX_ONDEMAND);
		if (max != null && !max.isEmpty() && Long.parseLong(max) < cantidad) {
			aplicacion = gestorPropiedades.valorPropertie(SERVICIO_COMPRA_TASAS_APLICACION_BATCH);
		} else {
			aplicacion = gestorPropiedades.valorPropertie(SERVICIO_COMPRA_TASAS_APLICACION);
		}

		SolicitudPagoTasas solicitudpagoTasas = new SolicitudPagoTasas(aplicacion, datosAdicionales, datosCuentaCorriente, datosFirma, datosTarjeta, compra
				.getCifContrato(), compra.getImporteTotalTasas().doubleValue(), compra.getNumJustificante791(), tasasSolicitadas, tipoIdentificador);

		return initializePagoItv(compra.getIdCompra(), SERVICIO_REALIZAR_PAGO).realizarPagoTasas(solicitudpagoTasas);
	}

	@Override
	public DocumentoJustificantePago obtenerJustificantePago(Long idCompra, String cif, String autoliquidacion, String csv) throws RemoteException, ServiceException {
		SolicitudJustificantePago solicitud = new SolicitudJustificantePago(cif, autoliquidacion, csv);
		return initializePagoItv(idCompra, SERVICIO_DESCARGA_JUSTIFICANTE).obtenerJustificantePago(solicitud);
	}

	@Override
	public DatosPedidoCompleto obtenerDatosPedido(Long idCompra, String identificador, String numeroAutoliquidacion, String nrc, Double importe) throws RemoteException, ServiceException {
		SolicitudConsultaTasas solicitudConsultaTasas = new SolicitudConsultaTasas(identificador, nrc, numeroAutoliquidacion, importe, FormatoDocumento.TXT);
		return initializePagoItv(idCompra, SERVICIO_OBTENER_DATOS).obtenerDatosPedido(solicitudConsultaTasas);
	}

	/**
	 * Inicializa el cliente del WS
	 * @return
	 * @throws ServiceException
	 */
	private PagoItv initializePagoItv(Long idCompra, String servicio) throws ServiceException {
		// Recuperar URL de properties
		String address = gestorPropiedades.valorPropertie(SERVICIO_COMPRA_TASAS_URL);
		if (LOG.isDebugEnabled()) {
			LOG.info("Inicializando cliente del WS de compra de tasas de DGT con la URL " + address);
		}

		PagoItvServiceLocator pagoItvService = new PagoItvServiceLocator();
		pagoItvService.setPagoItvPortEndpointAddress(address);

		// Añadimos los handlers de firmado y logs
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(address, pagoItvService.getPagoItvPortWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = pagoItvService.getHandlerRegistry().getHandlerChain(portName);
		list.add(getSignerHandler());
		list.add(getFilesHandler(idCompra, servicio));

		return pagoItvService.getPagoItvPort();
	}

	/**
	 * Instancia y configura un handler para realizar la firma de las peticiones soap
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getSignerHandler() {
		// Configuración del almacén de claves y certificado a usar
		Map<String, String> securityConfig = new HashMap<String, String>();
		securityConfig.put("alias", gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"));

		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(SoapXMLDSignerHandler.class);
		signerHandlerInfo.setHandlerConfig(securityConfig);

		return signerHandlerInfo;
	}

	/**
	 * Instancia y configura un handler para guardar en fichero una copia de las peticiones y respuestas soap
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getFilesHandler(Long idCompra, String servicio) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapTasasPagoItvFilesHandler.PROPERTY_KEY_ID, idCompra);
		filesConfig.put(SoapTasasPagoItvFilesHandler.PROPERTY_KEY_SERVICE, servicio);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapTasasPagoItvFilesHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}

	/**
	 * Genera el tag datosFirma con los datos firmados, sin firmar, y el certificado empleado
	 * @param compra
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private DatosFirma generateDatosFirma(ResumenCompraBean compra) {
		String datosCompraSinFirmar = null;
		String datosCompraFirmados = null;
		String certificado = null;

		// Se obtiene el texto sin firmar
		byte[] origenFirma = generateOrigenFirma(compra);
		if (origenFirma != null) {
			 datosCompraSinFirmar = new String(Base64.encodeBase64(origenFirma));
		}

		// Realizar firma en viafirma
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		// Si la entidad que realiza la compra es el colegio, se usa el certificado del colegio en lugar del certificado del colegiado (presidente)
		String alias;
		if (servicioPermisos.tienePermisoElContrato(compra.getIdContrato(), utilesColegiado.PERMISO_COLEGIO, utilesColegiado.APLICACION_OEGAM_ADMIN)) {
			alias = gestorPropiedades.valorPropertie(ConstantesPDF.ALIAS_COLEGIO);
		} else {
			alias = compra.getAliasColegiado();
		}
		String idFirma = utilesViafirma.firmarPagoTasas(origenFirma, alias);

		if (LOG.isDebugEnabled()) {
			LOG.debug("IdFirma obtenido " + idFirma);
		}

		if (idFirma != null && !ERROR.equals(idFirma)) {
			// Se obtiene el texto firmado
			byte[] txtFirmado = utilesViafirma.getBytesDocumentoFirmado(idFirma);
			if (txtFirmado != null) {
				datosCompraFirmados = new String(Base64.encodeBase64(txtFirmado));
			}
			// Se obtiene el certificado firmante en formato PEM y se pasa a x509
			InputStream is = null;
			try {
				String certificadoPEM = utilesViafirma.obtieneCertificadoPEM(idFirma);
				if (certificadoPEM != null) {
					is = new ByteArrayInputStream(Base64.decodeBase64(certificadoPEM.getBytes()));
					CertificateFactory cf = CertificateFactory.getInstance("X.509");
					Certificate cert = cf.generateCertificate(is);
					certificado = new String(Base64.encodeBase64(cert.getEncoded()));
				}
			} catch (CertificateException e) {
				LOG.error("Error al obtener el certificado firmante", e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						LOG.error("Error cerrando el recurso inputStream", e);
					}
				}
			}

		} else {
			LOG.error("Los Datos de la Compra no se han podido firmar correctamente");
		}

		// Se monta el Tag datosFirma
		return new DatosFirma(certificado, datosCompraFirmados, datosCompraSinFirmar);
	}

	/**
	 * Monta el texto de origen de firma segun las especificaciones del WS conel MAP: Plataforma de pago de tasas
	 * @param compra
	 * @return
	 */
	private byte[] generateOrigenFirma(ResumenCompraBean compra) {

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);

		// JUSTIFICANTE
		formatter.format(FORMATO_LITERAL, LITERAL_JUSTIFICANTE);
		formatter.format(FORMATO_VALOR_SALTO_LINEA, compra.getNumJustificante791());

		// NIF/CIF
		formatter.format(FORMATO_LITERAL, LITERAL_NIF_CIF);
		formatter.format(FORMATO_VALOR_SALTO_LINEA, compra.getCifContrato());

		// IMPORTE DEL INGRESO
		formatter.format(FORMATO_LITERAL, LITERAL_IMPORTE_INGRESO);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("0.00", dfs);
		df.setGroupingSize(3);
		df.setGroupingUsed(true);
		formatter.format(FORMATO_VALOR_SALTO_LINEA, df.format(compra.getImporteTotalTasas().doubleValue()));

		// Segun la forma de pago, tarjeta o cuenta corriente
		if (Integer.toString(FormaPago.TARJETA.getCodigo()).equals(compra.getFormaPago())) {
			// EMISOR DE TARJETA
			formatter.format(FORMATO_LITERAL, "EMISOR DE TARJETA:");
			formatter.format(FORMATO_VALOR_SALTO_LINEA, compra.getDatosBancarios().substring(25));

			// NUMERO DE TARJETA
			formatter.format(FORMATO_LITERAL, "NUMERO DE TARJETA:");
			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(0,4));
			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(4,8));
			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(8,12));
			formatter.format(FORMATO_VALOR_SALTO_LINEA, compra.getDatosBancarios().substring(12,16));

			// CADUCA (MM/AA):
			formatter.format(FORMATO_LITERAL, "CADUCA (MM/AA):");
			formatter.format(FORMATO_VALOR_Y_BARRA, compra.getDatosBancarios().substring(19,21));
			formatter.format(FORMATO_VALOR, compra.getDatosBancarios().substring(23,25));
			
		}  else if (Integer.toString(FormaPago.CCC.getCodigo()).equals(compra.getFormaPago())) {
			// CODIGO DE LA CUENTA
			formatter.format(FORMATO_LITERAL, LITERAL_IBAN);
			
			String codigoEntidadFinanciera = compra.getDatosBancarios().substring(0, 4);
			String codigoOficina = compra.getDatosBancarios().substring(4, 8);
			String digitoDeControl = compra.getDatosBancarios().substring(8, 10);
			String numeroDeCuenta = compra.getDatosBancarios().substring(10);
			// Se calcula el IBAN para una cuenta española
			String codPais = "ES";
			String controlIBAN = UtilesCuentaBancaria.calculaDcIban(codPais, codigoEntidadFinanciera, codigoOficina, digitoDeControl, numeroDeCuenta);
			formatter.format(FORMATO_VALOR, codPais);
			formatter.format(FORMATO_VALOR, controlIBAN);
			formatter.format(FORMATO_VALOR, compra.getDatosBancarios());

//			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(4, 8));
//			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(8, 12));
//			formatter.format(FORMATO_VALOR_Y_GUION, compra.getDatosBancarios().substring(12, 14));
//			formatter.format(FORMATO_VALOR, compra.getDatosBancarios().substring(14));

		} else if (Integer.toString(FormaPago.IBAN.getCodigo()).equals(compra.getFormaPago())) {
			// CODIGO DE LA CUENTA
			formatter.format(FORMATO_LITERAL, LITERAL_IBAN);
			formatter.format(FORMATO_VALOR, compra.getDatosBancarios());
	
		}
		// TODO meter opcion de cuenta de apoderado...
//		formatter.format("%n%S", "Realizo el cargo con apoderamiento en la cuenta del obligado");

		formatter.close();
		return sb.toString().getBytes();
	}

	private DatosTarjeta generateDatosTarjeta(ResumenCompraBean compra) {
		DatosTarjeta datosTarjeta = null;
		if (Integer.toString(FormaPago.TARJETA.getCodigo()).equals(compra.getFormaPago())) {

			String idEntidad = compra.getDatosBancarios().substring(25);
			String numeroTarjeta1 = compra.getDatosBancarios().substring(0, 4);
			String numeroTarjeta2 = compra.getDatosBancarios().substring(4, 8);
			String numeroTarjeta3 = compra.getDatosBancarios().substring(8, 12);
			String numeroTarjeta4 = compra.getDatosBancarios().substring(12, 16);
			String cvv = compra.getDatosBancarios().substring(16, 19);
			String mes = compra.getDatosBancarios().substring(19, 21);
			String anho = compra.getDatosBancarios().substring(23, 25);

			datosTarjeta = new DatosTarjeta(anho, cvv, idEntidad, mes, numeroTarjeta1, numeroTarjeta2, numeroTarjeta3, numeroTarjeta4);
		}
		return datosTarjeta;
	}

	private DatosCuentaCorriente generateDatosCuentaCorriente(ResumenCompraBean compra) {
		DatosCuentaCorriente datosCuentaCorriente = null;

		if (Integer.toString(FormaPago.CCC.getCodigo()).equals(compra.getFormaPago()) && compra.getDatosBancarios().length() == 20) {
			// Codigo cuenta cliente (CCC)
			String codigoEntidadFinanciera = compra.getDatosBancarios().substring(0, 4);
			String codigoOficina = compra.getDatosBancarios().substring(4, 8);
			String digitoDeControl = compra.getDatosBancarios().substring(8, 10);
			String numeroDeCuenta = compra.getDatosBancarios().substring(10);
			// Se calcula el IBAN para una cuenta española
			String codPais = "ES";
			String controlIBAN = UtilesCuentaBancaria.calculaDcIban(codPais, codigoEntidadFinanciera, codigoOficina, digitoDeControl, numeroDeCuenta);
			datosCuentaCorriente = new DatosCuentaCorriente(codPais, codigoEntidadFinanciera, codigoOficina, controlIBAN, digitoDeControl, numeroDeCuenta);

		} else if (Integer.toString(FormaPago.IBAN.getCodigo()).equals(compra.getFormaPago()) && compra.getDatosBancarios().length() == 24) {
			// Codigo internacional de cuenta bancaria (IBAN)
			String codPais = compra.getDatosBancarios().substring(0, 2);
			String controlIBAN = compra.getDatosBancarios().substring(2, 4);
			String codigoEntidadFinanciera = compra.getDatosBancarios().substring(4, 8);
			String codigoOficina = compra.getDatosBancarios().substring(8, 12);
			String digitoDeControl = compra.getDatosBancarios().substring(12, 14);
			String numeroDeCuenta = compra.getDatosBancarios().substring(14);
			datosCuentaCorriente = new DatosCuentaCorriente(codPais, codigoEntidadFinanciera, codigoOficina, controlIBAN, digitoDeControl, numeroDeCuenta);
		}
		return datosCuentaCorriente;
	}

}
