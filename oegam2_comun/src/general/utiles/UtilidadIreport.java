package general.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.itextpdf.text.Image;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import facturacion.beans.ConceptoIReport;
import facturacion.utiles.enumerados.EmisorFactura;
import facturacion.utiles.enumerados.EstadoFacturacion;
import general.beans.IreportTablaCertificadoTasasBean;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaGasto;
import hibernate.entities.facturacion.FacturaHonorario;
import hibernate.entities.facturacion.FacturaOtro;
import hibernate.entities.facturacion.FacturaProvFondo;
import hibernate.entities.facturacion.FacturaSuplido;
import hibernate.entities.general.Colegiado;
import hibernate.entities.personas.Direccion;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import trafico.beans.daos.pq_facturacion.TablaFacturacionBean;
import trafico.modelo.ModeloTrafico;
import trafico.utiles.PdfMaker;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class UtilidadIreport {

	private ModeloTrafico modeloTrafico;

	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public UtilidadIreport() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	// log
	private static ILoggerOegam log = LoggerOegam.getLogger(UtilidadIreport.class);

	public String generarPdfCertificadoTasas(String nombreColegiado, String numeroColegiado,
			String fechaInicio, String fechaFin, ArrayList<TablaFacturacionBean> tablaFacturacionBeanLista)
					throws Exception, OegamExcepcion {

		//String rutaPdf = gestorPropiedades.valorPropertie("trafico.ruta.certificado.tasas.pdf");
		String rutaPlantilla = gestorPropiedades.valorPropertie("trafico.ruta.certificado.tasas.plantilla");

		String firmaGestor = "";
		String nombreSecretario = gestorPropiedades.valorPropertie("ICOGAM.secretario.junta.nombre");
		String nifSecretario = gestorPropiedades.valorPropertie("ICOGAM.secretario.junta.nif");
		if(nombreSecretario == null || nombreSecretario.equals("")||
				nifSecretario == null || nifSecretario.equals("")){
			throw new Exception("No se han configurado correctamente los datos del secretario de la junta " + 
					"del ICOGAM requeridos para el certificado de tasas");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaDeHoy = sdf.format(new Date());

		firmaGestor += "#" + nifSecretario + "#" + fechaDeHoy;
		firmaGestor = utiles.cifrarHMACSHA1(firmaGestor,"ClaveCifrado");
		firmaGestor = utiles.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0,16);

		HashMap<String,Object> parametros = new HashMap<String,Object>();
		parametros.put("nombreColegiado", nombreColegiado);
		parametros.put("numeroColegiado", numeroColegiado);
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFin",fechaFin);
		parametros.put("nombreSecretario", nombreSecretario);
		// Comentado hasta que se implemente la lógica de generación del barcode mediante Barcode4J
		//parametros.put("firmaGestor",firmaGestor);

		IreportTablaCertificadoTasasBean[] arrayIreport = convertirBeansCertificadoTasasTabla(tablaFacturacionBeanLista);

		JRBeanArrayDataSource arrayBeans = new JRBeanArrayDataSource(arrayIreport);
		JasperPrint print = JasperFillManager.fillReport(rutaPlantilla, parametros, arrayBeans);
		String nombreFichero = "certificadoTasas_" + utilesColegiado.getNumColegiadoSession() +"_" + new Date().getTime();
//		JasperExportManager.exportReportToPdfFile(print, rutaPdf + nombreFichero);
		
		byte[] byteFinal = JasperExportManager.exportReportToPdf(print);
		
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TASAS, ConstantesGestorFicheros.CERTIFICADO_TASAS, new Fecha(new Date()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, byteFinal);
		return nombreFichero;
	}

	private IreportTablaCertificadoTasasBean[] convertirBeansCertificadoTasasTabla(
			ArrayList<TablaFacturacionBean>tablaFacturacionBeanLista)throws Exception{
		IreportTablaCertificadoTasasBean[] arrayReturn = new IreportTablaCertificadoTasasBean[tablaFacturacionBeanLista.size()];
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		int cont = 0;
		for(TablaFacturacionBean iteracion:tablaFacturacionBeanLista){
			IreportTablaCertificadoTasasBean iReportBean = new IreportTablaCertificadoTasasBean();
			iReportBean.setCodigoTasa(iteracion.getTasa().getCodigoTasa());
			String fechaAplicacion = iteracion.getTasa().getFechaAsignacion()!=null?format.format(iteracion.getTasa().getFechaAsignacion()):"";
			iReportBean.setFechaAplicacion(fechaAplicacion);
			String fechaCompra = format.format(iteracion.getTasa().getFechaAlta());
			iReportBean.setFechaCompra(fechaCompra);
			iReportBean.setImporteTasa(iteracion.getTasa().getPrecio().toString() + " €");
			iReportBean.setMatricula(iteracion.getMatricula());
			iReportBean.setTipoTasa(iteracion.getTasa().getTipoTasa());

			String titularVehiculo = "";
			if(iteracion.getTitularVehiculo().getNombre() != null){
				titularVehiculo = iteracion.getTitularVehiculo().getNombre();
			}
			if(iteracion.getTitularVehiculo().getApellido1RazonSocial() != null){
				titularVehiculo += " " + iteracion.getTitularVehiculo().getApellido1RazonSocial();
			}
			if(iteracion.getTitularVehiculo().getApellido2() != null){
				titularVehiculo += " " + iteracion.getTitularVehiculo().getApellido2();
			}
			iReportBean.setTitularVehiculo(titularVehiculo);

			String titularFacturacion = "";
			if(iteracion.getTitularFacturacion().getNombre() != null){
				titularFacturacion = iteracion.getTitularFacturacion().getNombre();
			}
			if(iteracion.getTitularFacturacion().getApellido1RazonSocial() != null){
				titularFacturacion += " " + iteracion.getTitularFacturacion().getApellido1RazonSocial();
			}
			if(iteracion.getTitularFacturacion().getApellido2() != null){
				titularFacturacion += " " + iteracion.getTitularFacturacion().getApellido2();
			}
			if(iteracion.getTitularFacturacion().getNif() != null) {
				titularFacturacion += " " + iteracion.getTitularFacturacion().getNif();
			}
			iReportBean.setTitularFacturacion(titularFacturacion);

			arrayReturn[cont] = iReportBean;
			cont++;
		}

		return arrayReturn;
	}

	public void insertarBarcode(File pdf, String firmaGestor)throws Exception{

		PdfMaker pdfMaker = new PdfMaker();
		Image codigoBarras = pdfMaker.crearCodigoBarras128(firmaGestor, 1, 20, false);	
		FileInputStream input = new FileInputStream(pdf);
		byte[] pdfImagen = pdfMaker.insertarImagen(IOUtils.toByteArray(input), codigoBarras,new int[]{1}, 310, 160, 100);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(pdf);
			out.write(pdfImagen);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	public ConceptoIReport convertirBeanFacturaSuplido(FacturaSuplido suplido, boolean isPrinted){//Float totalSuplidos, int cantidadSuplidos){
		ConceptoIReport conceptoIReport = new ConceptoIReport();

		if (suplido.getSuplido()!=null){
			if(!isPrinted){
				conceptoIReport.setTitulo("Suplidos: ");
			}else{
				conceptoIReport.setTitulo("");
			}
			if (suplido.getSuplidoDescripcion() != null) {
				conceptoIReport.setConcepto(suplido.getSuplidoDescripcion());
				conceptoIReport.setCantidad("1");
			} else {
				conceptoIReport.setConcepto("");
				conceptoIReport.setCantidad("");
			}
			if (suplido.getSuplido() != null) {
				conceptoIReport.setImporte(getValorFormateadoStringPDF(suplido.getSuplido().toString()));
				conceptoIReport.setTotal(getValorFormateadoStringPDF(suplido.getSuplidoTotal().toString()));
				conceptoIReport.setIvaImporte("0");
				conceptoIReport.setIvaPorcentaje("EXT");
			} else {
				conceptoIReport.setImporte("");
				conceptoIReport.setTotal("");
			}

			if (suplido.getSuplidoDescuento() != null && suplido.getSuplidoDescuento()!=0F)
				conceptoIReport.setDescuento(getValorFormateadoStringPDF(suplido.getSuplidoDescuento().toString())+" %");
			else
				conceptoIReport.setDescuento("");
			conceptoIReport.setIsPrinted(Boolean.valueOf(isPrinted));
		} else {
			conceptoIReport.setTitulo("");
			conceptoIReport.setConcepto("");
			conceptoIReport.setCantidad("");
			conceptoIReport.setImporte("");
			conceptoIReport.setTotal("");
			conceptoIReport.setIvaImporte("");
			conceptoIReport.setIvaPorcentaje("");
			conceptoIReport.setIsPrinted(Boolean.valueOf(isPrinted));
		}

		return conceptoIReport;
	}

	public ConceptoIReport convertirBeanFacturaHonorario(FacturaHonorario concepto, boolean isPrinted){
		ConceptoIReport conceptoIReport = new ConceptoIReport();

		if(!isPrinted){
			conceptoIReport.setTitulo("Honorarios: ");
		}else{
			conceptoIReport.setTitulo("");
		}
		conceptoIReport.setConcepto(concepto.getHonorarioDescripcion());
		conceptoIReport.setCantidad("1");
		conceptoIReport.setImporte(getValorFormateadoStringPDF(String.valueOf(concepto.getHonorario())));
		if(concepto.getHonorarioCheckIva().equals(new BigDecimal("1"))){
			conceptoIReport.setIvaPorcentaje(getValorFormateadoStringPDF(String.valueOf(concepto.getHonorarioIva()))+" %");
			conceptoIReport.setIvaImporte(getValorFormateadoStringPDF(String.valueOf(concepto.getHonorarioTotalIva())));
		}else{
			conceptoIReport.setIvaImporte("EXT");
			conceptoIReport.setIvaPorcentaje("EXT");
		}

		if (concepto.getHonorarioDescuento() != null && concepto.getHonorarioDescuento()!=0F)
			conceptoIReport.setDescuento(getValorFormateadoStringPDF(String.valueOf(concepto.getHonorarioDescuento()))+" %");
		else
			conceptoIReport.setDescuento("");
		conceptoIReport.setTotal(getValorFormateadoStringPDF(String.valueOf(concepto.getHonorarioTotal())));
		conceptoIReport.setIsPrinted(new Boolean(isPrinted));
		return conceptoIReport;
	}

	public ConceptoIReport convertirBeanFacturaGasto(FacturaGasto concepto, boolean isPrinted){
		ConceptoIReport conceptoIReport = new ConceptoIReport();

		if(!isPrinted){
			conceptoIReport.setTitulo("Gastos: ");
		}else{
			conceptoIReport.setTitulo("");
		}
		conceptoIReport.setConcepto(concepto.getGastoDescripcion());
		conceptoIReport.setImporte(getValorFormateadoStringPDF(concepto.getGasto().toString()));
		
		if(concepto.getGastoCheck().equals("1")){
			// Mantis 13737. David Sierra: Se controlan posibles valores nulos provenientes de BBDD
			if(String.valueOf(concepto.getGastoIva()) !=null && !String.valueOf(concepto.getGastoIva()).isEmpty()) {
				conceptoIReport.setIvaPorcentaje(getValorFormateadoStringPDF(String.valueOf(concepto.getGastoIva()))+" %");
			};
			if(concepto.getGastoTotalIva().toString() !=null && !concepto.getGastoTotalIva().toString().isEmpty()) {
				conceptoIReport.setIvaImporte(getValorFormateadoStringPDF(concepto.getGastoTotalIva().toString()));
			};
			
		}else{
			conceptoIReport.setIvaPorcentaje("EXT");
			conceptoIReport.setIvaImporte("0");
		}
		conceptoIReport.setDescuento("");
		conceptoIReport.setCantidad("");
		conceptoIReport.setTotal(getValorFormateadoStringPDF(concepto.getGastoTotal().toString()));
		conceptoIReport.setIsPrinted(Boolean.valueOf(isPrinted));
		return conceptoIReport;
	}

	/**
	 * Método que serializa los datos de una entidad de facturación (concepto, suplidos, honorarios y gastos)
	 * en un bean que se enviará a jasper para que genere la factura con los datos serializados 
	 * 
	 * @author Carlos Fernández Sánchez
	 * @since 30/08/2012
	 * */
	public ConceptoIReport convertirBeanFacturaConcepto(FacturaConcepto concepto, boolean isPrinted, int numConcepto){
		ConceptoIReport conceptoIReport = new ConceptoIReport();
		//Imprimimos la linea de concepto
		if(concepto.getConcepto()!=null) {
			if(!concepto.getConcepto().equalsIgnoreCase("")) {
				if(!isPrinted) 
					conceptoIReport.setTitulo("Concepto "+String.valueOf(numConcepto)+":");
				else
					conceptoIReport.setTitulo("");

				conceptoIReport.setConcepto(concepto.getConcepto());
				conceptoIReport.setCantidad("");
				conceptoIReport.setDescuento("");
				conceptoIReport.setFacturaRectificativa("");
				conceptoIReport.setImporte("");
				conceptoIReport.setIsPrinted(new Boolean(isPrinted));
				conceptoIReport.setIvaImporte("");
				conceptoIReport.setIvaPorcentaje("");
				conceptoIReport.setTotal("");
			}
		}
		return conceptoIReport;
	}

	public ConceptoIReport convertirBeanFacturaOtros(FacturaOtro concepto, boolean isPrinted){
		ConceptoIReport conceptoIReport = new ConceptoIReport();

		if(concepto.getOtro() > 0F){//Impresión de linea de Otros
			if(!isPrinted)
				conceptoIReport.setTitulo("Otros: ");
			else
				conceptoIReport.setTitulo("");

			conceptoIReport.setConcepto(concepto.getOtroDescripcion());
			conceptoIReport.setCantidad("1");//Ver lo de la propiedad
			if (concepto.getOtroDescuento() != 0F)
				conceptoIReport.setDescuento(getValorFormateadoStringPDF(String.valueOf(concepto.getOtroDescuento()))+" %");
			else
				conceptoIReport.setDescuento("");
			conceptoIReport.setFacturaRectificativa("");
			conceptoIReport.setImporte(getValorFormateadoStringPDF(String.valueOf(concepto.getOtro())));
			conceptoIReport.setIsPrinted(new Boolean(isPrinted));
			if(concepto.getOtroCheckIva().intValue() == 1){
				conceptoIReport.setIvaImporte(getValorFormateadoStringPDF(String.valueOf(concepto.getOtroTotalIva())));
				conceptoIReport.setIvaPorcentaje(getValorFormateadoStringPDF(String.valueOf(concepto.getOtroIva()))+" %");
			}else{
				conceptoIReport.setIvaImporte("0");
				conceptoIReport.setIvaPorcentaje("EXT");
			}
			conceptoIReport.setTotal(getValorFormateadoStringPDF(String.valueOf(concepto.getOtroTotal())));
		}
		return conceptoIReport;
	}

	public ConceptoIReport convertirBeanFacturaProvision(FacturaProvFondo concepto, boolean isPrinted){
		ConceptoIReport conceptoIReport = new ConceptoIReport();

		if(!isPrinted)
			conceptoIReport.setTitulo("Provision Fondos: ");
		else
			conceptoIReport.setTitulo("");

		conceptoIReport.setConcepto(concepto.getFondoDescripcion());
		conceptoIReport.setDescuento("");
		conceptoIReport.setFacturaRectificativa("");
		conceptoIReport.setCantidad("1");
		conceptoIReport.setImporte(getValorFormateadoStringPDF(String.valueOf(concepto.getFondo())));
		conceptoIReport.setIsPrinted(new Boolean(isPrinted));
		//				if(concepto.getFondoCheckIva().intValue() == 1){
		//					conceptoIReport.setIvaImporte(getValorFormateadoStringPDF(concepto.getFondoTotalIva()));
		//					conceptoIReport.setIvaPorcentaje(getValorFormateadoStringPDF(concepto.getFondoIva())+" %");
		//				}else{
		//					conceptoIReport.setIvaImporte("0");
		//					conceptoIReport.setIvaPorcentaje("EXT");
		//				}
		conceptoIReport.setIvaImporte(" ");
		conceptoIReport.setIvaPorcentaje("EXT");
		conceptoIReport.setTotal(getValorFormateadoStringPDF(String.valueOf(concepto.getFondoTotal())));
		return conceptoIReport;
	}

	// DRC@26-07-2012 Se encarga de recuperar los datos del Emisor y el Receptor de la Factura
	public Map<String,Object> cargarParametrosFactura(EmisorFactura emisor, Factura factura, Float totalSuplidos, ArrayList<ConceptoIReport> listaConceptoIRepot) throws Exception, OegamExcepcion{

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String,Object> parametros = new HashMap<String,Object>();

		//	List<FacturaProvFondo> provisiones = factura.getFacturaProvFondo();
		FacturaProvFondo provisionFondo = factura.getFacturaProvFondo();
		//	List<FacturaOtro> otros = factura.getFacturaOtro();
		FacturaOtro otroFactura = factura.getFacturaOtro();
		//	ConceptoIReport concept = null;
		ConceptoIReport iva21 = null;
		ConceptoIReport iva10 = null;
		ConceptoIReport iva4 = null;

		ConceptoIReport irpf9 = null;
		ConceptoIReport irpf21 = null;

		// MANTIS 7192. Ricardo Rodríguez. Determina el emisor de la factura, para rellenar los datos según sea gestor o gestoría:
		ContratoVO contrato = utilesColegiado.getDetalleColegiado(factura.getId().getNumColegiado());
		if(contrato == null){
			throw new Exception("No se ha podido recuperar el contrato del colegiado para rellenar los datos del emisor en el pdf de la factura");
		}

		String nombre = new String();
		String nifCif = new String();
		// El número de colegiado y la dirección no difieren dependiendo del emisor indicado
		String numColegiado = convertirNulltoString(factura.getId().getNumColegiado());
		String direccion = contrato.getIdTipoVia() + " " + contrato.getVia() + " " + contrato.getNumero() + " " ;
		if (contrato.getLetra()!=null && !contrato.getLetra().equals(""))
			direccion += "Letra: " + contrato.getLetra() + " ";
		if (contrato.getEscalera()!=null && !contrato.getEscalera().equals(""))
			direccion += "Esc: " + contrato.getEscalera() + " ";
		if (contrato.getPiso()!=null && !contrato.getPiso().equals(""))
			direccion += "Piso: " + contrato.getPiso() + " ";
		if (contrato.getPuerta()!=null && !contrato.getPuerta().equals(""))
			direccion += "Puerta: " + contrato.getPuerta() + " ";
		direccion += contrato.getProvincia().getNombre();

		if(emisor != null && emisor.getNombre().equals(EmisorFactura.GESTORIA.getNombre())){
			// Si el emisor es la gestoría, obtiene el nombre y el cif de los datos del contrato del colegiado
			nombre = convertirNulltoString(contrato.getRazonSocial());
			nifCif = convertirNulltoString(contrato.getCif());
		}else if(emisor != null && emisor.getNombre().equals(EmisorFactura.GESTOR.getNombre())){
			Colegiado colegiado = utilesColegiado.getColegiado(numColegiado);
			// Si el emisor es el gestor, obtiene el nombre y el cif de los datos del colegiado del contrato
			nombre = convertirNulltoString(colegiado.getUsuario().getApellidosNombre());
			nifCif = convertirNulltoString(colegiado.getUsuario().getNif());
		}else{
			throw new Exception("No se ha especificado quién debe constar como emisor de la factura. Gestor o gestoría.");
		}

		parametros.put("nombreApellidosRazonSocial", "Nombre: " + nombre);
		parametros.put("cifNif", "NIF/CIF: " + nifCif);
		parametros.put("numColegiado", "Nº Colegiado: " + numColegiado);
		parametros.put("direccion", "Dirección: " + direccion );
		// fin MANTIS 7192

		// Num y Fecha Factura
		parametros.put("numFactura", getFormatoNumFactura(factura));
		parametros.put("fechaFactura", formato.format(factura.getFechaFactura()));

		// Cliente
		parametros.put("nombreApellidosRazonSocialCliente", "Nombre: " + getNombreCliente(factura));
		parametros.put("nifCifCliente", "NIF/CIF: " + factura.getPersona().getId().getNif());
		parametros.put("direccionCliente",  "Dirección: " + construyeStringDireccion(factura.getPersona().getPersonaDireccions().get(0).getDireccion()));
		parametros.put("codigoPostalPoblacionProvinciaCliente", "Código Postal: " + construyeStringCodPosPoblacionEtc(factura.getPersona().getPersonaDireccions().get(0).getDireccion()));

		// Factura Rectificativa
		parametros.put("facturaRectificativa", getFormatoFacturaRectificativa(factura));

		if (factura.getFacAnulada()!=null && (!factura.getCheckPdf().equals("2") || !factura.getCheckPdf().equals("4")))
			parametros.put("facturaAnulada", factura.getFacAnulada());

		// DRC@26-07-2012 Se encarga de recuperar los valores totales de la factura
		// Calculos para recuperar el valor de Total Exento
		Float honorario = new Float(0);
		Float honorarioTotal = new Float(0);
		Float honorarioIva21 = new Float(0);
		Float honorarioIva10 = new Float(0);
		Float honorarioIva4 = new Float(0);
		Float honorarioIrpf = new Float(0);
		Float honorarioIrpfSuma21 = new Float(0);
		Float honorarioIrpfSuma9 = new Float(0);
		Float otro = new Float(0);
		Float otroTotal = new Float(0);
		Float otroIva21 = new Float(0);
		Float otroIva10 = new Float(0);
		Float otroIva4 = new Float(0);
		Float gasto = new Float(0);
		Float gastoTotal = new Float(0);
		Float gastoIva21 = new Float(0);
		Float gastoIva10 = new Float(0);
		Float gastoIva4 = new Float(0);
		Float provision = new Float(0);
		Float provisionTotal = new Float(0);
		Float suma21 = new Float(0);
		Float suma10 = new Float(0);
		Float suma4 = new Float(0);
		Float valorIva = new Float(0);
		Float valorIrpf21 = new Float(0);
		Float valorIrpf9 = new Float(0);
		Float suplidoExentoTotal = new Float(0);
		Float honorarioExentoTotal = new Float(0);
		Float gastoExentoTotal = new Float(0);
		Float provExentoTotal = new Float(0);
		Float otroExentoTotal = new Float(0);

		Float honorarioIrpf21Total = new Float(0);
		Float honorarioIrpf09Total = new Float(0);

		Float honorarioIva21Total = new Float(0);
		Float honorarioIva10Total = new Float(0);
		Float honorarioIva4Total = new Float(0);

		Float gastoIva21Total = new Float(0);
		Float gastoIva10Total = new Float(0);
		Float gastoIva4Total = new Float(0);

		Float otroIva21Total = new Float(0);
		Float otroIva10Total = new Float(0);
		Float otroIva4Total = new Float(0);

		for (FacturaConcepto facturaConcepto : factura.getFacturaConceptos()){

			List<FacturaSuplido> suplidos = facturaConcepto.getFacturaSuplidos();
			List<FacturaHonorario> honorarios = facturaConcepto.getFacturaHonorarios();
			List<FacturaGasto> gastos = facturaConcepto.getFacturaGastos();	

			for(FacturaSuplido suplidoAux : suplidos){
				if(suplidoAux.getSuplido()!=null){
					suplidoExentoTotal+=suplidoAux.getSuplidoTotal();
					if(!suplidoAux.getSuplidoCheckDescuento().equals("0")){
						Float vSuplido = suplidoAux.getSuplido();
					}
				}
			}
			for(FacturaHonorario honAux : honorarios){
				if(honAux.getHonorario()!=null){
					honorarioTotal += honAux.getHonorario();
					if(honAux.getHonorarioCheckIva().intValue() == 0){
						honorario += honAux.getHonorario(); 
						honorarioExentoTotal+=honAux.getHonorarioTotal();
					}else{
						if(honAux.getHonorarioIva()==0){
							valorIva = Float.valueOf(honAux.getHonorarioIva());
							honorarioExentoTotal+=honAux.getHonorarioTotal();
						}else{
							//Comprueba si el iva es el 21%
							if (honAux.getHonorarioIva()==21){
								honorarioIva21Total += honAux.getHonorario();
								honorarioIva21 += honAux.getHonorarioTotalIva();
							}
							//Comprueba si el iva es el 10%
							if(honAux.getHonorarioIva()==10){
								honorarioIva10Total += honAux.getHonorario();
								honorarioIva10 += honAux.getHonorarioTotalIva();
							}
							//Comprueba si el iva es el 04%
							if(honAux.getHonorarioIva() == 4){
								honorarioIva4Total += honAux.getHonorario();
								honorarioIva4 += honAux.getHonorarioTotalIva();
							}
						}
					}
					if(honAux.getHonorarioCheckIrpf().intValue() == 1){
						if(!(honAux.getHonorarioIrpf() == 0)){
							//Comprueba si el irpf es el 21%
							if (honAux.getHonorarioIrpf() == 21){
								honorarioIrpf21Total += honAux.getHonorario();
								valorIrpf21 += honAux.getHonorarioTotalIrpf();
								honorarioIrpfSuma21 += honAux.getHonorario();
							}
							//Comprueba si el irpf es el 10%
							if(honAux.getHonorarioIrpf() == 9){
								honorarioIrpf09Total += honAux.getHonorario();
								valorIrpf9 += honAux.getHonorarioTotalIrpf();
								honorarioIrpfSuma9 += honAux.getHonorario();
							}
						}
						honorarioIrpf += honAux.getHonorarioTotalIrpf();

					}
				}
			}

			for(FacturaGasto gastoAux : gastos){
				if(gastoAux.getGasto()!=null){
					gastoTotal += gastoAux.getGasto();
					if(gastoAux.getGastoCheck().equals("0")){
						gasto += gastoAux.getGasto();
						gastoExentoTotal+=gastoAux.getGastoTotal();
					}else{
						if (gastoAux.getGastoTotalIva() == null){
							gastoIva21 += Float.parseFloat("0.00");
							gastoIva10 += Float.parseFloat("0.00");
							gastoIva4 += Float.parseFloat("0.00");
						}

						//Comprueba si el iva es el 21%
						if (gastoAux.getGastoIva() == 21){
							gastoIva21Total += gastoAux.getGasto();
							gastoIva21 += gastoAux.getGastoTotalIva();
						}
						//Comprueba si el iva es el 10%
						if(gastoAux.getGastoIva() == 10){
							gastoIva10Total += gastoAux.getGasto();
							gastoIva10 += gastoAux.getGastoTotalIva();
						}
						//Comprueba si el iva es el 04%
						if(gastoAux.getGastoIva() == 4){
							gastoIva4Total += gastoAux.getGasto();
							gastoIva4 += gastoAux.getGastoTotalIva();
						}
					}
				}
			}
		}


		if(otroFactura!=null){
			otroTotal += otroFactura.getOtro();
			if(otroFactura.getOtroCheckIva().intValue()==0){
				otro += otroFactura.getOtro();
				otroExentoTotal=+otroFactura.getOtroTotal();
			}else{
				if (otroFactura.getOtroIva() == 21){
					otroIva21Total += otroFactura.getOtro();
					otroIva21 += otroFactura.getOtroTotalIva();
				}
				if (otroFactura.getOtroIva() == 10){
					otroIva10Total += otroFactura.getOtro();
					otroIva10 += otroFactura.getOtroTotalIva();
				}
				if (otroFactura.getOtroIva() == 4){
					otroIva4Total += otroFactura.getOtro();
					otroIva4 += otroFactura.getOtroTotalIva();
				}
			}
			if(otroFactura.getOtroCheckDescuento()!=null && otroFactura.getOtroCheckDescuento().intValue() == 1){
			}
		}




		if(provisionFondo!=null){
			provisionTotal += provisionFondo.getFondo();
			provision += provisionFondo.getFondo();
			provExentoTotal+= provisionFondo.getFondoTotal();
		}

		float totalExento = suplidoExentoTotal + honorarioExentoTotal + gastoExentoTotal + otroExentoTotal;

		float BaseImponible21 = honorarioIva21Total + gastoIva21Total + otroIva21Total;
		float BaseImponible10 = honorarioIva10Total + gastoIva10Total + otroIva10Total;
		float BaseImponible4 = honorarioIva4Total + gastoIva4Total + otroIva4Total;

		if(BaseImponible21 != 0.0){
			iva21= new ConceptoIReport();
			iva21.setBaseImponible(getValorFormateadoFloatPDF(BaseImponible21));
			iva21.setIvaPercentage("21");
			iva21.setIsSummary(Boolean.TRUE);
			iva21.setIsPrinted(Boolean.FALSE);
		}
		if(BaseImponible10 != 0.0){
			iva10 = new ConceptoIReport();
			iva10.setBaseImponible(getValorFormateadoFloatPDF(BaseImponible10));
			iva10.setIvaPercentage("10");
			iva10.setIsSummary(Boolean.TRUE);
			if(iva21!=null){
				iva10.setIsPrinted(Boolean.TRUE);
			}else{
				iva10.setIsPrinted(Boolean.FALSE);
			}
		}
		if(BaseImponible4 != 0.0){
			iva4 = new ConceptoIReport();
			iva4.setBaseImponible(getValorFormateadoFloatPDF(BaseImponible4));
			iva4.setIvaPercentage("4");
			iva4.setIsSummary(Boolean.TRUE);
			if(iva21!=null || iva10!=null){//Que no se imprima el detalle con el total Exento
				iva4.setIsPrinted(Boolean.TRUE);
			}else{
				iva4.setIsPrinted(Boolean.FALSE);
			}
		}

		parametros.put("totalExento", getValorFormateadoFloatPDF(totalExento));

		// Calculos para recuperar el valor del Iva acumulado 
		suma21 = new Float(0);
		suma21 = honorarioIva21 + gastoIva21 + otroIva21;
		if(suma21!=0.0 && iva21 !=null){
			iva21.setIvaTotal(getValorFormateadoFloatPDF(suma21));
			listaConceptoIRepot.add(iva21);
		}
		suma10 = new Float(0);
		suma10 = honorarioIva10 + gastoIva10 + otroIva10;
		if(suma10!=0.0 && iva10 !=null){
			iva10.setIvaTotal(getValorFormateadoFloatPDF(suma10));
			listaConceptoIRepot.add(iva10);
		}
		suma4 = new Float(0);
		suma4 = honorarioIva4 + gastoIva4 + otroIva4;
		if(suma4!=0.0 && iva4 !=null){
			iva4.setIvaTotal(getValorFormateadoFloatPDF(suma4));
			listaConceptoIRepot.add(iva4);
		}

		// Calculos para recuperar el valor de la Base Imponible de Retencion del Honorario
		if(honorarioIrpfSuma21 != 0.0 && valorIrpf21 != 0.0){
			irpf21 = new ConceptoIReport();
			irpf21.setBaseImpRetencion(getValorFormateadoFloatPDF(honorarioIrpf21Total));
			irpf21.setIrpfPercentage("21");
			irpf21.setIrpfTotal(getValorFormateadoFloatPDF(valorIrpf21));
			irpf21.setIsSummary(Boolean.TRUE);
			if(iva21!=null || iva10 !=null || iva4 != null){
				irpf21.setIsPrinted(Boolean.TRUE);
			}else{
				irpf21.setIsPrinted(Boolean.FALSE);
			}
			listaConceptoIRepot.add(irpf21);
		}
		if(honorarioIrpfSuma9 != 0.0 && valorIrpf9 != 0.0){
			irpf9 = new ConceptoIReport();
			irpf9.setBaseImpRetencion(getValorFormateadoFloatPDF(honorarioIrpfSuma9));
			irpf9.setIrpfPercentage("9");
			irpf9.setIrpfTotal(getValorFormateadoFloatPDF(valorIrpf9));
			irpf9.setIsSummary(Boolean.TRUE);
			if(iva21!=null || iva10 !=null || iva4 != null){
				irpf9.setIsPrinted(Boolean.TRUE);
			}else{
				irpf9.setIsPrinted(Boolean.FALSE);
			}
			listaConceptoIRepot.add(irpf9);
		}
		listaConceptoIRepot.get(listaConceptoIRepot.size()-1).setIsLastLine(Boolean.TRUE);
		listaConceptoIRepot.get(listaConceptoIRepot.size()-1).setIsSummary(Boolean.TRUE);

		// Calculos para recuperar el valor Total de la Factura
		if (factura.getImporteTotal()!=0)		
			parametros.put("totalFactura", getValorFormateadoStringPDF(String.valueOf(factura.getImporteTotal())) + " €");	
		else
			parametros.put("totalFactura", "0 €");

		// Intenta recuperar el logo asociado al colegiado, si no tiene establece la ruta del logo por defecto:
		String logoColegiado = utilesColegiado.getRutaLogo();
		String rutaLogoXdefecto = null;
		if(logoColegiado == null || logoColegiado.equals("")){
			rutaLogoXdefecto = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.logo.por.defecto");
			parametros.put("rutaImagen",rutaLogoXdefecto);
		}else{
			parametros.put("rutaImagen",logoColegiado);
		}

		String logoPDFColegiado = utilesColegiado.getRutaLogo();
		String rutaLogoColegio = null;
		if(logoPDFColegiado == null || logoPDFColegiado.equals("")){
			rutaLogoColegio = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.logo.pdf.colegio");
			parametros.put("rutaFondo", rutaLogoColegio);
		}else{
			parametros.put("rutaFondo", logoPDFColegiado);
		}		

		return parametros;

	}

	public File generarPdfFactura(List<ConceptoIReport>listaConceptoIReport,
			Map<String,Object> parametros, String tipo)throws Exception, OegamExcepcion{

		String rutaPdf = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.ficheros");
		String rutaPlantilla = "";
		if (tipo.equals("pdf")) {
			rutaPlantilla = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.plantilla");
		} else if (tipo.equals("borrador")) {
			rutaPlantilla = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.borrador");
		}else if (tipo.equals("rectificativa")){
			rutaPlantilla = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.rectificativa");
		}else if (tipo.equals("anulada")){
			rutaPlantilla = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.anulada");
		}else if (tipo.equals("abono")){
			rutaPlantilla = gestorPropiedades.valorPropertie("factura.ruta.general") + gestorPropiedades.valorPropertie("factura.ruta.abono");	
		} else {
			throw new Exception("No se ha establecido el parametro del tipo de factura (borrador o pdf) ");
		}

		//JRBeanArrayDataSource arrayBeans = new JRBeanArrayDataSource(listaConceptoIReport.toArray());
		JasperPrint print = JasperFillManager.fillReport(rutaPlantilla, parametros, new JRBeanCollectionDataSource(listaConceptoIReport));
		String nombreFichero = parametros.get("numFactura") + ".pdf";
		JasperExportManager.exportReportToPdfFile(print, rutaPdf + nombreFichero);
		File pdf = new File(rutaPdf + nombreFichero);
		return pdf;
	}

	private String construyeStringCodPosPoblacionEtc(Direccion direccionCliente){
		String direccion = "";
		if(direccionCliente.getMunicipio().getProvincia().getIdProvincia() != null && !direccionCliente.getMunicipio().getProvincia().getIdProvincia().equals("")){
			String Prov = getModeloTrafico().obtenerDescripcionProvincia(direccionCliente.getMunicipio().getProvincia().getIdProvincia());
			if (Prov != null)
				direccion += Prov + " ";
			else
				direccion += direccionCliente.getMunicipio().getProvincia().getIdProvincia() + " ";
		}
		if(direccionCliente.getMunicipio() != null && !direccionCliente.getMunicipio().equals("")){
			String Muni = getModeloTrafico().obtenerDescripcionMunicipio(direccionCliente.getMunicipio().getId().getIdMunicipio(), direccionCliente.getMunicipio().getProvincia().getIdProvincia());
			if (Muni != null)
				direccion += Muni + " ";
			else
				direccion += direccionCliente.getMunicipio().getId().getIdMunicipio() + " ";
		}
		if(direccionCliente.getPueblo() != null && !direccionCliente.getPueblo().equals("")){
			direccion += direccionCliente.getPueblo() + " ";
		}
		if(direccionCliente.getCodPostal() != null && !direccionCliente.getCodPostal().equals("")){
			direccion += direccionCliente.getCodPostal() + " ";
		}
		return direccion;
	}

	private String construyeStringDireccion(Direccion direccionCliente){
		String direccion = "";
		if(direccionCliente.getIdTipoVia() != null && !direccionCliente.getIdTipoVia().equals("")){
			direccion += direccionCliente.getIdTipoVia() + " ";
		}
		if(direccionCliente.getNombreVia() != null && !direccionCliente.getNombreVia().equals("")){
			direccion += direccionCliente.getNombreVia() + " ";
		}
		if(direccionCliente.getNumero() != null && !direccionCliente.getNumero().equals("")){
			direccion += direccionCliente.getNumero() + " ";
		}
		if(direccionCliente.getLetra() != null && !direccionCliente.getLetra().equals("")){
			direccion += "letra: " + direccionCliente.getLetra() + " ";
		}
		if(direccionCliente.getEscalera() != null && !direccionCliente.getEscalera().equals("")){
			direccion += "Esc: " +direccionCliente.getEscalera() + " ";
		}
		if(direccionCliente.getPlanta() != null && !direccionCliente.getPlanta().equals("")){
			direccion += "Planta: " + direccionCliente.getPlanta() + " ";
		}
		if(direccionCliente.getPuerta() != null && !direccionCliente.getPuerta().equals("")){
			direccion += "Puerta: " + direccionCliente.getPuerta() + " ";
		}
		if(direccionCliente.getBloque() != null && !direccionCliente.getBloque().equals("")){
			direccion += "Bloque: " + direccionCliente.getBloque() + " ";
		}
		if(direccionCliente.getKm() != null && !direccionCliente.getKm().equals("")){
			direccion += "Km: " + direccionCliente.getKm() + " ";
		}
		if(direccionCliente.getHm() != null && !direccionCliente.getHm().equals("")){
			direccion += "Hm: " + direccionCliente.getHm() + " ";
		}
		return direccion;
	}

	public String getFormatoNumFactura (Factura factura){
		String numFactura = "";		
		if (factura.getId().getNumFactura() != null) 
			numFactura = factura.getId().getNumFactura();  

		return numFactura;	
	}

	public String getFormatoFacturaRectificativa (Factura factura) throws OegamExcepcion{
		String textFacturaRectificativa = "";		
		if (factura.getFacAnulada() != null) {
			if (factura.getCheckPdf().equals(EstadoFacturacion.EstadoFacturaRectificativa.getNombreEnum())) {				
				textFacturaRectificativa += "La factura rectificativa";
				textFacturaRectificativa += " " + factura.getId().getNumFactura() + " ";
				textFacturaRectificativa += "anula la factura";
				textFacturaRectificativa += " " + factura.getFacAnulada() + ".";
			} else if (factura.getCheckPdf().equals(EstadoFacturacion.EstadoFacturaAnulada.getNombreEnum())) {
				textFacturaRectificativa += factura.getId().getNumFactura() + " ";
				textFacturaRectificativa += factura.getFacAnulada() + ".";				
			}
		} 
		return textFacturaRectificativa;	
	}

	//public NumberFormat getFormatoCampos () {
	public NumberFormat getFormatoCampos () {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(Integer.parseInt(gestorPropiedades.valorPropertie("facturacion.num.decimales")));
		nf.setGroupingUsed(true);			
		return nf;
	}

	//public NumberFormat getFormatoCampos () {
	public String getHonorarioMasIva (String Importe, String Iva) {
		String resultado = "";
		try {
			Double HonorarioMasIva = Double.valueOf(Importe) + Double.valueOf(Iva);
			resultado = getValorFormateadoStringPDF(String.valueOf(HonorarioMasIva));
		} catch (Exception e) {
			log.error("Erro al realizar la suma del importe mas el iva de los honorarios: " + e);			
		}			
		return resultado;
	}

	public String getBaseImponibleCalcularIRPF (String Base, String Irpf) {
		String resultado = "";
		try {
			Double totalIRPF = ((Double.valueOf(Base) * Double.valueOf(Irpf)) / 100);
			resultado = getValorFormateadoStringPDF(String.valueOf(totalIRPF));
		} catch (Exception e) {
			log.error("Erro al realizar la suma del importe mas el iva de los honorarios: " + e);			
		}			
		return resultado;
	}

	// DRC@01-08-2012 Formatea los valores recogidos en la cadena, y lo formatea para mostrarlo en PDF
	public String getValorFormateadoStringPDF (String cadena) {

		String decimales = "";
		NumberFormat nf = getFormatoCampos();
		int numDeci = nf.getMaximumFractionDigits();
		String b = ".";

		for (int a = 0; a < numDeci ; a++){
			b += "0";
		}

		try {			
			if (cadena.indexOf(",") > 0) {
				decimales = cadena.substring(cadena.length()-(numDeci+1), cadena.length());
				cadena = cadena.substring(0, cadena.length() - (numDeci+1));
			}

			if (cadena.length() > numDeci) {
				if (cadena.substring(cadena.length()-(numDeci+1), cadena.length()-numDeci).equalsIgnoreCase(".")) {
					decimales = cadena.substring(cadena.length() - (numDeci+1), cadena.length());
					cadena = cadena.substring(0, cadena.length() - (numDeci+1));
				}
			}

			cadena = nf.format(Double.valueOf(cadena));
			if (!decimales.equalsIgnoreCase("") && !decimales.equalsIgnoreCase(b))
				cadena += "," + decimales.substring(1, decimales.length());

			return cadena;
		} catch (Exception e) {
			log.error("Al formatear el valor " + cadena + " para generar el PDF: " + e);
			return cadena;
		}		
	}

	public String getValorFormateadoFloatPDF (Float cadena) {
		NumberFormat nf = getFormatoCampos();

		try {
			String valor = nf.format(cadena);
			return valor;
		} catch (Exception e) {
			log.error("Al formatear el valor " + cadena + " para generar el PDF: " + e);
			return cadena.toString();
		}		
	}

	public String getNombreCliente (Factura factura) {
		String Nombre = "";
		if (factura.getPersona().getApellido1RazonSocial() != null)
			Nombre += " " + factura.getPersona().getApellido1RazonSocial(); 

		if ( factura.getPersona().getApellido2() != null)
			Nombre += " " + factura.getPersona().getApellido2();

		if (factura.getPersona().getNombre() != null)
			Nombre += ", " + factura.getPersona().getNombre();
		return Nombre;
	}	

	// DRC@12-09-2012 Se encarga de convertir los campos null a vacio
	public String convertirNulltoString (String cadena) {
		try {
			if (cadena != null)
				return cadena;
			else
				return "";  			
		} catch (Exception e) {
			log.error("Al formatear el valor " + cadena + " para generar el PDF: " + e);
			return "";	
		}  		
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

}				