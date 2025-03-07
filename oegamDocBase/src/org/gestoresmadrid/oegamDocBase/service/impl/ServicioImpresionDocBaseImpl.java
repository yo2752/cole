package org.gestoresmadrid.oegamDocBase.service.impl;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.docbase.enumerados.TipoTramiteDocBase;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.impresion.model.dao.EvolucionImpresionDao;
import org.gestoresmadrid.core.impresion.model.dao.ImpresionDao;
import org.gestoresmadrid.core.impresion.model.dao.ImpresionTramiteTraficoDao;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.core.impresion.model.vo.EvolucionImpresionVO;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionTramiteTraficoVO;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.enumerados.TasaSimultaneaEnum;
import org.gestoresmadrid.oegamDocBase.service.ServicioImpresionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.DocumentoBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.TramiteBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.gestoresmadrid.utilidades.listas.GenericComparator;
import org.gestoresmadrid.utilidades.listas.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.zxing.WriterException;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import net.sf.jasperreports.engine.JRException;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionDocBaseImpl implements ServicioImpresionDocBase{

	private static final long serialVersionUID = -3548212754683597883L;
	
	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionDocBaseImpl.class);

	@Autowired
	ServicioComunCola servicioCola;
	
	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioComunContrato servicioContrato;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ImpresionDao impresionDao;
	
	@Autowired
	ImpresionTramiteTraficoDao impresionTramiteTraficoDao;
	
	@Autowired
	EvolucionImpresionDao evolucionImpresionDao;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoDocBaseBean imprimirDocBase(DocumentoBaseVO docBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			DocBaseBean docBaseBean = setDatosDocBase(docBase);
			mapTramitesToDocBase(docBase, docBaseBean);
			String subtipoDocumento = obtenerSubtipoDocumentoFromTipoCarpeta(docBase.getCarpeta());
			OrdenDocBaseEnum ordenDocBase = null;
			if (docBase.getOrdenDocbase() == null) {
				ordenDocBase = obtenerCriterioOrdenacionTramitesDocBase(docBase.getIdContrato());			
			} else {
				ordenDocBase = OrdenDocBaseEnum.convertir(docBase.getOrdenDocbase().toString());
			}
			ordenarTramites(docBaseBean, ordenDocBase);
			byte[] byteFinal = generarInformeDocBase(docBaseBean);
			if(byteFinal != null){
				guardarDocBasePDF(subtipoDocumento, docBase.getDocId(), byteFinal, docBase.getFechaPresentacion());
				resultado.setDocBaseFinal(docBaseBean);
			} else {
				resultado.setMensaje("Ha sucedido un error a la hora de generar el documento base fisico con docId: " + docBase.getDocId() +".");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento base con docId: " + docBase.getDocId() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el documento base con docId: " + docBase.getDocId());
		}
		return resultado;
	}
	
	private void guardarDocBasePDF(String subtipoDocumento, String docId, byte[] byteFinal, Date fecha) throws OegamExcepcion {
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.TIPO_DOC_GESTION_DOCUMENTAL);
		fichero.setSubTipo(subtipoDocumento);
		fichero.setFecha(utilesFecha.getFechaConDate(fecha));
		fichero.setNombreDocumento(docId);
		fichero.setSobreescribir(Boolean.TRUE);
		fichero.setFicheroByte(byteFinal);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		gestorDocumentos.guardarByte(fichero);
	}

	private int obtenerNumMaximoExpedientesPorPaginaPDFDocBase() {
		String numMaxTramitesPaginaDoc = gestorPropiedades.valorPropertie(NUM_MAXIMO_EXPEDIENTES_PAGINA_PDF_DOC_BASE);
		int numTramites = (numMaxTramitesPaginaDoc != null) ? new BigDecimal(numMaxTramitesPaginaDoc).intValue() : NUM_MAXIMO_EXPEDIENTES_PAGINA_PDF_DOC_BASE_DEFECTO;
		return numTramites;
	}
	
	private String obtenerPlantillaDocBase(String carpeta) {
		if (TipoCarpeta.CARPETA_CTIT.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_TIPO_A.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_TIPO_B.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_TIPO_I.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_FUSION.getValorEnum().equals(carpeta)) {
			return "documentoBaseTransmisionNuevo";
		} else if (TipoCarpeta.CARPETA_MATE.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_MATR_PDF.getValorEnum().equals(carpeta) ||
				TipoCarpeta.CARPETA_MATR_TIPOA.getValorEnum().equals(carpeta)) {
			return "documentoBaseMatriculacionNuevo";
		} else if(TipoCarpeta.CARPETA_BTV.getValorEnum().equals(carpeta)){
			return "documentoBaseBajas";
		}
		return null;
	}
	
	public String toXML(DocBaseBean documentoBase) {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocBaseBean.class);
		xml += xStream.toXML(documentoBase);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}
	
	private byte[] generarInformeDocBase(DocBaseBean docBaseBean) {
		// ID del Documento Base
		String docId = docBaseBean.getDocId();
		// Código QR
		String qrCode = docBaseBean.getQrCode();
		// Numero de trámites y trámites por página
		int numeroTramites = docBaseBean.getTramites().size();
		int numTramitesPagina = obtenerNumMaximoExpedientesPorPaginaPDFDocBase();
		// Plantilla del documento base
		String plantillaDocBase = obtenerPlantillaDocBase(docBaseBean.getCarpeta());
		if (plantillaDocBase == null) {
			log.error("ERROR: no se ha encontrado la plantilla del documento base '" + docBaseBean.getDocId() + "'");
			return null;
		}
		// Se convierte el bean a XML
		String xml = toXML(docBaseBean);
		// QR del Documento Base (a partir del código QR)
		BufferedImage qrcodeImage = null;
		try {
			qrcodeImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(
											new com.google.zxing.qrcode.QRCodeWriter().encode(qrCode, com.google.zxing.BarcodeFormat.QR_CODE, 600, 600));
		} catch (WriterException e) {
			log.error("No se pudo generar el código QR del documento " + docBaseBean.getDocId(), e);
		}
		// Bar_Code del Documento Base (a partir del docId)
		BufferedImage barcodeImage = null;
		try {
			barcodeImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(
											new com.google.zxing.MultiFormatWriter().encode(docId, com.google.zxing.BarcodeFormat.CODE_128, 1200, 400));
		} catch (WriterException e) {
			log.error("No se pudo generar el BAR_CODE del documento " + docBaseBean.getDocId(), e);
		}
		
		//HashMap<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numeroTramites", numeroTramites);
		params.put("numTramitesPagina", numTramitesPagina);
		params.put("qrcodeImage", qrcodeImage);
		params.put("barcodeImage", barcodeImage);
		params.put("pFechaPresentacion", docBaseBean.getFechaPresentacion());
		//nube de puntos con los expedientes en cada pagina
		String nubeMatriculasTransf = gestorPropiedades.valorPropertie("nube.matriculas.docBase");
		String colegiadoNubeMatriculasTransf = gestorPropiedades.valorPropertie("colegiado.nube.matriculas.docBase");
		if("SI".equalsIgnoreCase(nubeMatriculasTransf) && colegiadoNubeMatriculasTransf.contains(docBaseBean.getNumColegiado())){
			String datosNubePuntos = getDatosNubePuntosListadoMatriculas(docBaseBean.getTramites());
			if(datosNubePuntos != null && !datosNubePuntos.isEmpty()){
				java.awt.Image nubePuntos417;
				try {
					nubePuntos417 = getBarcode(datosNubePuntos);
				} catch (Exception e) {
					log.error("Error al generar el PDF del listado de matriculas.\n" + e);
					return null;
				}
				params.put("nubePuntos", nubePuntos417);
			}
		}
		// Se genera el PDF a través de la plantilla
		ReportExporter re = new ReportExporter();
		byte[] byteFinal = null;
		try {
			// DOC_BASE
			System.out.println(xml);
			System.out.println(gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("docbase.plantillas.rutaPlantillas"));
			byteFinal = re.generarInforme(gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("docbase.plantillas.rutaPlantillas"), 
					plantillaDocBase, "pdf", xml, "DocumentoBase", params, null);
		} catch (JRException e) {
			log.error(e);
		} catch (ParserConfigurationException e) {
			log.error(e);
		}
		return byteFinal;
	}

	private String getDatosNubePuntosListadoMatriculas(List<TramiteBaseBean> tramites) {
		String nube = "";
		if (!tramites.isEmpty()) {
			int i=0;
			for(TramiteBaseBean tramite : tramites){
				if(tramite.getEsNubePuntos()){
					if (i > 0) {
						nube = nube + ";";
					}
					if (tramite.getMatricula() != null) {
						nube = nube + tramite.getMatricula();
						i++;
					}
				}
			}
		}
		return nube;
	}
	
	private Image getBarcode(String datosNubePuntos) {
		BarcodePDF417 barcode = new BarcodePDF417();
		barcode.setText(datosNubePuntos);
		barcode.setAspectRatio(.25f);
		return barcode.createAwtImage(Color.BLACK, Color.WHITE);
	}
	
	private void ordenarTramites(DocBaseBean docBaseBean, OrdenDocBaseEnum ordenDocBase) {
		String[] properties = getPropertiesOrdenacion(ordenDocBase);
		List<TramiteBaseBean> listaInicialTramites = docBaseBean.getTramites();
		List<TramiteBaseBean> listaSimultaneas = new ArrayList<TramiteBaseBean>();
		List<TramiteBaseBean> listaTramitesNumeros = new ArrayList<TramiteBaseBean>();		
		List<TramiteBaseBean> listaTramitesLetras = new ArrayList<TramiteBaseBean>();
		List<TramiteBaseBean> listaFinalTramites = new ArrayList<TramiteBaseBean>();
		String[] criterioOrdenacionSimultaneas = new String[] {"tipoTasa", "matricula"};
		for (TramiteBaseBean tramiteBaseBean : listaInicialTramites) {
			Boolean esTasaSimultanea = TasaSimultaneaEnum.esTasaSimultanea(tramiteBaseBean.getTipoTasa());
			if (esTasaSimultanea) {
				listaSimultaneas.add(tramiteBaseBean);
			} else if(!Arrays.asList(properties).contains("matricula")){
				listaTramitesNumeros.add(tramiteBaseBean);
			} else {
				// Primer caracter de la matricula
				Character primerCaracterMatricula = (tramiteBaseBean.getMatricula() != null) ? tramiteBaseBean.getMatricula().charAt(0) : null;
				// Si es una matricula que empieza por letra
				if (primerCaracterMatricula != null && (Character.isUpperCase(primerCaracterMatricula) || Character.isLowerCase(primerCaracterMatricula))) {
					listaTramitesLetras.add(tramiteBaseBean);
				// Si no, la añadimos a la lista de las matriculas que empieza por numero
				} else {
					listaTramitesNumeros.add(tramiteBaseBean);
				}
			}
		}
		// Ordenamos cada una de las listas según su criterio de ordenación
		Collections.sort(listaSimultaneas, new GenericComparator<TramiteBaseBean>(criterioOrdenacionSimultaneas, OrderType.asc));
		Collections.sort(listaTramitesLetras, new GenericComparator<TramiteBaseBean>(properties, OrderType.asc));
		Collections.sort(listaTramitesNumeros, new GenericComparator<TramiteBaseBean>(properties, OrderType.asc));
		// Unimos ambas listas en una lista final (primero simultáneas, luego letras y luego números)
		listaFinalTramites.addAll(listaSimultaneas);
		listaFinalTramites.addAll(listaTramitesLetras);
		listaFinalTramites.addAll(listaTramitesNumeros);
		int numeroTramite = 1;
		for (TramiteBaseBean tramiteBaseBean : listaFinalTramites) {
			tramiteBaseBean.setNumero(numeroTramite++);
		}
		// Seteamos la lista final
		docBaseBean.setTramites(listaFinalTramites);
	}

	private String[] getPropertiesOrdenacion(OrdenDocBaseEnum criterioOrdenacionDocBase) {
		String[] properties = null;
		// Orden del documento base por matrícula
		if(criterioOrdenacionDocBase.equals(OrdenDocBaseEnum.Matricula)) {
			properties = new String[]{"matricula"};
		
		// Orden del documento base por número de expediente
		} else if(criterioOrdenacionDocBase.equals(OrdenDocBaseEnum.Numero_Expediente)) {
			properties = new String[]{"numExpediente"};

		// Orden del documento base por referencia propia del trámite
		}
		else if(criterioOrdenacionDocBase.equals(OrdenDocBaseEnum.Referencia_propia)) {
			properties = new String[]{"refPropia", "numExpediente"};
		}
		return properties;
	}

	private OrdenDocBaseEnum obtenerCriterioOrdenacionTramitesDocBase(Long idContrato) {
		OrdenDocBaseEnum ordenDocBase =  servicioContrato.obtenerOrdenDocBase(idContrato);
		return (ordenDocBase != null) ? ordenDocBase : OrdenDocBaseEnum.Matricula;
	}

	@Override
	public String obtenerSubtipoDocumentoFromTipoCarpeta(String tipoCarpeta) {
		String subtipoDocumento = null;
		// Subtipo de documento de transmisión
		if (TipoCarpeta.CARPETA_CTIT.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_TIPO_A.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_TIPO_B.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_TIPO_I.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_FUSION.getValorEnum().equals(tipoCarpeta)) {
			subtipoDocumento = ConstantesGestorFicheros.SUBTIPO_DOC_TRANSMISION;
		} else if (TipoCarpeta.CARPETA_MATE.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_MATR_PDF.getValorEnum().equals(tipoCarpeta) ||
				TipoCarpeta.CARPETA_MATR_TIPOA.getValorEnum().equals(tipoCarpeta)) {
			subtipoDocumento = ConstantesGestorFicheros.SUBTIPO_DOC_MATRICULACION;
		} else if (TipoCarpeta.CARPETA_BTV.getValorEnum().equals(tipoCarpeta)) {
			subtipoDocumento = ConstantesGestorFicheros.SUBTIPO_DOC_BAJAS;
		} else if (TipoCarpeta.CARPETA_DUPLICADO.getValorEnum().equals(tipoCarpeta)) {
			subtipoDocumento = ConstantesGestorFicheros.SUBTIPO_DOC_DUPLICADO;
		}
		return subtipoDocumento;
	}
	
	private void mapTramitesToDocBase(DocumentoBaseVO docBase, DocBaseBean docBaseBean) {
		if (TipoCarpeta.CARPETA_CTIT.getValorEnum().equals(docBase.getCarpeta()) ||
			TipoCarpeta.CARPETA_TIPO_A.getValorEnum().equals(docBase.getCarpeta()) ||
			TipoCarpeta.CARPETA_TIPO_B.getValorEnum().equals(docBase.getCarpeta()) ||
			TipoCarpeta.CARPETA_TIPO_I.getValorEnum().equals(docBase.getCarpeta()) ||
			TipoCarpeta.CARPETA_FUSION.getValorEnum().equals(docBase.getCarpeta())) {
			asignarTramitesDocBase(docBaseBean,tratarSimultaneasTramitesDocBase(docBase.getTramitesTraficoAsList(), docBase.getCarpeta()));
		} else if (TipoCarpeta.CARPETA_MATE.getValorEnum().equals(docBase.getCarpeta()) ||
				TipoCarpeta.CARPETA_MATR_PDF.getValorEnum().equals(docBase.getCarpeta()) ||
				TipoCarpeta.CARPETA_MATR_TIPOA.getValorEnum().equals(docBase.getCarpeta())
				|| TipoCarpeta.CARPETA_BTV.getValorEnum().equals(docBase.getCarpeta())) {
			docBaseBean.setTramites(tratarTramiteBaseBean(docBase.getTramitesTraficoAsList(), docBase.getCarpeta()));
		}
	}

	private void asignarTramitesDocBase(DocBaseBean docBase, DocumentoBaseBean documentoBase) {
		if(documentoBase.getListaTramitesBaseSimultaneas() != null && !documentoBase.getListaTramitesBaseSimultaneas().isEmpty()){
			for(TramiteBaseBean tramiteBaseBean : documentoBase.getListaTramitesBaseSimultaneas()){
				docBase.addListaTramitesBaseBean(tramiteBaseBean);
			}
		}
		if(documentoBase.getListaTramitesBase() != null && !documentoBase.getListaTramitesBase().isEmpty()){
			for(TramiteBaseBean tramiteBaseBean : documentoBase.getListaTramitesBase	()){
				docBase.addListaTramitesBaseBean(tramiteBaseBean);
			}
		}
	}


	private List<TramiteBaseBean> tratarTramiteBaseBean(List<TramiteTraficoVO> listaTramitesTrafico, String carpeta) {
		List<TramiteBaseBean> listaTramitesBean = new ArrayList<TramiteBaseBean>();
		for(TramiteTraficoVO tramiteTraficoVO : listaTramitesTrafico){
			listaTramitesBean.add(generarTramiteBaseBean(tramiteTraficoVO, carpeta));
		}
		return listaTramitesBean;
	}

	private DocumentoBaseBean tratarSimultaneasTramitesDocBase(List<TramiteTraficoVO> listaTramites, String carpeta) {
		DocumentoBaseBean documentoBaseBean = new DocumentoBaseBean();
		List<String> listaMatriculas = new ArrayList<String>();
		for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
			listaMatriculas.add(tramiteTraficoVO.getVehiculo().getMatricula());
		}
		Set<String> listaAux = new HashSet<String>(listaMatriculas);		
		listaMatriculas.clear();
		listaMatriculas.addAll(listaAux);
		List<TramiteTraficoVO> listaAuxTram = null;
		for(String matricula : listaMatriculas){
			listaAuxTram = new ArrayList<TramiteTraficoVO>();
			for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
				if(tramiteTraficoVO.getVehiculo().getMatricula().equals(matricula)){
					listaAuxTram.add(tramiteTraficoVO); 
				}
			}
			if(listaAuxTram.size() == 1){
				documentoBaseBean.addListaTramitesBase(generarTramiteBaseBean(listaAuxTram.get(0), carpeta));
			} else {
				documentoBaseBean.addListaTramitesBaseSimultaneas(fusionarTramitesBaseBean(listaAuxTram, carpeta));
			}
		}
		return documentoBaseBean;
	}

	private TramiteBaseBean fusionarTramitesBaseBean(List<TramiteTraficoVO> listaTramites, String carpeta) {
		TramiteBaseBean tramiteBaseSimultanea = new TramiteBaseBean();
		if (listaTramites.size() > 1) {
			Collections.sort(listaTramites, new GenericComparator<TramiteTraficoVO>("fechaAlta"));
		}
		for(TramiteTraficoVO tramiteAux : listaTramites){
			tramiteBaseSimultanea.addListaTramitesSimultaneos(tramiteAux.getNumExpediente());
		}
		TramiteTraficoVO primerTramite = listaTramites.get(0);
		if (listaTramites.size() > 1) {
			tramiteBaseSimultanea.setTipoTasa(TasaSimultaneaEnum.obtenerTipoTasaSimultanea(listaTramites.size()).getValorEnum());
		} else {
			String tipoTasa = (primerTramite.getTasa() != null) ? primerTramite.getTasa().getTipoTasa() : "";
			tramiteBaseSimultanea.setTipoTasa(tipoTasa);
		}
		tramiteBaseSimultanea.setBastidor(primerTramite.getVehiculo() != null ? primerTramite.getVehiculo().getBastidor() : null);
		tramiteBaseSimultanea.setMatricula(primerTramite.getVehiculo() != null ? primerTramite.getVehiculo().getMatricula() : null);
		tramiteBaseSimultanea.setNumColegiado(primerTramite.getNumColegiado());
		tramiteBaseSimultanea.setNumExpediente((primerTramite.getNumExpediente() != null) ? primerTramite.getNumExpediente().toString() : "");
		tramiteBaseSimultanea.setRefPropia(primerTramite.getRefPropia());
		if(listaTramites.size() > 1){
			TramiteTraficoVO ultimoTramite = listaTramites.get(listaTramites.size()-1);
			tramiteBaseSimultanea.setFechaPresentacion(ultimoTramite.getFechaPresentacion() != null ? 
					utilesFecha.formatoFecha("dd-MM-yyyy",ultimoTramite.getFechaPresentacion()) : null);
		} else {
			tramiteBaseSimultanea.setFechaPresentacion(utilesFecha.formatoFecha("dd-MM-yyyy",primerTramite.getFechaPresentacion()));
		}
		nubePuntosSimDocBase(listaTramites, tramiteBaseSimultanea, carpeta);
		//tratarIntervinientes(tramiteBaseSimultanea, listaTramites);
		tramiteBaseSimultanea.setCmc(obtenerCodigoMandatoInterviniente(listaTramites, TipoInterviniente.Adquiriente.getValorEnum()));
		tramiteBaseSimultanea.setCmv(obtenerCodigoMandatoVendedor(listaTramites));
		return tramiteBaseSimultanea;
	}
	
	private String obtenerCodigoMandatoVendedor(List<TramiteTraficoVO> listaTramites) {
		List<IntervinienteTraficoVO> listaIntervinientes = null;
		for (TramiteTraficoVO tramite : listaTramites) {
			TramiteTrafTranVO tramiteTran = esTramiteTransmision(tramite);				
			if (tramiteTran != null && TipoTransferencia.tipo5.getValorEnum().equals(tramiteTran.getTipoTransferencia())) {
				listaIntervinientes = tramiteTran.getIntervinienteTraficosAsList();
				for(IntervinienteTraficoVO intervinienteTraficoVO : listaIntervinientes){
					if(TipoInterviniente.Compraventa.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
						return intervinienteTraficoVO.getPersona().getCodigoMandato();
					}
				}
			}
		}
		for(IntervinienteTraficoVO intervinienteTraficoVO : listaTramites.get(0).getIntervinienteTraficosAsList()){
			if(TipoInterviniente.TransmitenteTrafico.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				return intervinienteTraficoVO.getPersona().getCodigoMandato();
			}
		}
		return null;
	}
	
	private String obtenerCodigoMandatoInterviniente(List<TramiteTraficoVO> listaTramites, String tipoInterviniente) {
		List<IntervinienteTraficoVO> listaIntervinientes = null;
		if (TipoInterviniente.Adquiriente.getValorEnum().equals(tipoInterviniente)) {
			// Adquiriente (comprador): el del ultimo trámite
			listaIntervinientes = listaTramites.get(listaTramites.size()-1).getIntervinienteTraficosAsList();
		} else if (TipoInterviniente.Compraventa.getValorEnum().equals(tipoInterviniente)) {
			// Compraventa : del trámite con TipoTransferencia=5 (entrega a compraventa)
			for (TramiteTraficoVO tramite : listaTramites) {
				TramiteTrafTranVO tramiteTran = esTramiteTransmision(tramite);				
				if (tramiteTran != null && TipoTransferencia.tipo5.getValorEnum().equals(tramiteTran.getTipoTransferencia())) {
					listaIntervinientes = tramiteTran.getIntervinienteTraficosAsList();
					break;
				}
			}
		}
		if(listaIntervinientes != null && !listaIntervinientes.isEmpty()){
			for(IntervinienteTraficoVO intervinienteTraficoVO : listaIntervinientes){
				if(tipoInterviniente.equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
					return intervinienteTraficoVO.getPersona().getCodigoMandato();
				}
			}
		}
		return null;
	}

	/*private void tratarIntervinientes(TramiteBaseBean tramiteBaseSimultanea, List<TramiteTraficoVO> listaTramites) {
		IntervinienteBaseBean adquirienteBaseBean = mapToIntervinienteBaseBean(obtenerIntervinienteSimultanea(listaTramites, TipoInterviniente.Adquiriente.getValorEnum()));
		IntervinienteBaseBean transmitenteBaseBean = mapToIntervinienteBaseBean(obtenerIntervinienteSimultanea(listaTramites, TipoInterviniente.TransmitenteTrafico.getValorEnum()));
		IntervinienteBaseBean compraventaBaseBean = mapToIntervinienteBaseBean(obtenerIntervinienteSimultanea(listaTramites, TipoInterviniente.Compraventa.getValorEnum()));
		HashMap<TipoInterviniente, IntervinienteBaseBean> intervinientes = new HashMap<TipoInterviniente, IntervinienteBaseBean>();
		if (adquirienteBaseBean != null) {
			intervinientes.put(adquirienteBaseBean.getTipoInterviniente(), adquirienteBaseBean);
		}
		
		if (transmitenteBaseBean != null) {
			intervinientes.put(transmitenteBaseBean.getTipoInterviniente(), transmitenteBaseBean);
		}
		if (compraventaBaseBean != null) {
			intervinientes.put(compraventaBaseBean.getTipoInterviniente(), compraventaBaseBean);
		}
		tramiteBaseSimultanea.setIntervinientes(intervinientes);
	}*/

	
	private void informarCodigosMandato(TramiteBaseBean tramiteBase, String carpeta, List<IntervinienteTraficoVO> listaIntervinientes) {
		if (listaIntervinientes != null && !listaIntervinientes.isEmpty()) {
			for(IntervinienteTraficoVO intervinienteTraficoVO : listaIntervinientes){
				if(TipoInterviniente.Adquiriente.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
					if(TipoCarpeta.CARPETA_BTV.getValorEnum().equals(carpeta)){
						tramiteBase.setCm(intervinienteTraficoVO.getPersona().getCodigoMandato());
					} else {
						tramiteBase.setCmc(intervinienteTraficoVO.getPersona().getCodigoMandato());
					}
				} else if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
					if(TipoCarpeta.CARPETA_BTV.getValorEnum().equals(carpeta)){
						tramiteBase.setCm(intervinienteTraficoVO.getPersona().getCodigoMandato());
					} else {
						tramiteBase.setCmc(intervinienteTraficoVO.getPersona().getCodigoMandato());
					}
				} else if(TipoInterviniente.TransmitenteTrafico.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
					tramiteBase.setCmv(intervinienteTraficoVO.getPersona().getCodigoMandato());
				} 
			}
		}
	}

	/*private IntervinienteBaseBean mapToIntervinienteBaseBean(IntervinienteTraficoVO intervinienteBBDD) {
		IntervinienteBaseBean intervinienteBase = null;
		if (intervinienteBBDD != null) {
			intervinienteBase = new IntervinienteBaseBean();
			intervinienteBase.setNif(intervinienteBBDD.getId().getNif());
			intervinienteBase.setNumColegiado(intervinienteBBDD.getId().getNumColegiado());
			BigDecimal poderesEnFicha = (intervinienteBBDD.getPersona() != null) ? intervinienteBBDD.getPersona().getPoderesEnFicha() : null;
			intervinienteBase.setPoderesFicha(poderesEnFicha);
			String codigoMandato = (intervinienteBBDD.getPersona() != null) ? intervinienteBBDD.getPersona().getCodigoMandato() : null;
			intervinienteBase.setCodigoMandato(codigoMandato);
			intervinienteBase.setTipoInterviniente(TipoInterviniente.convertir(intervinienteBBDD.getTipoIntervinienteVO().getTipoInterviniente()));
		}
		return intervinienteBase;
	}*/

	private TramiteTrafTranVO esTramiteTransmision(TramiteTraficoVO tramiteTrafico) {
		TramiteTrafTranVO tramiteTrafTran = null;
		if (tramiteTrafico instanceof TramiteTrafTranVO) {
			tramiteTrafTran = (TramiteTrafTranVO) tramiteTrafico;
		}
		return tramiteTrafTran;
	}

	private void nubePuntosSimDocBase(List<TramiteTraficoVO> listaTramites, TramiteBaseBean tramiteBaseSimultanea, String carpeta) {
		String nubeMatriculasTransf = gestorPropiedades.valorPropertie("nube.matriculas.docBase");
		String colegiadoNubeMatriculasTransf = gestorPropiedades.valorPropertie("colegiado.nube.matriculas.docBase");
		if("SI".equalsIgnoreCase(nubeMatriculasTransf) && colegiadoNubeMatriculasTransf.contains(listaTramites.get(0).getNumColegiado())){
			if(TipoTramiteTrafico.Transmision.getValorEnum().equals(listaTramites.get(0).getTipoTramite()) 
					|| TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(listaTramites.get(0).getTipoTramite())){
				for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
					if(TipoTasa.UnoCinco.getValorEnum().equals(tramiteTraficoVO.getTasa().getTipoTasa())){
						TramiteTrafTranVO tramiteTrafTranVO = esTramiteTransmision(tramiteTraficoVO);
						if(TipoCarpeta.CARPETA_TIPO_B.getValorEnum().equals(carpeta)){
							if(tramiteTrafTranVO.getFactura() != null && !tramiteTrafTranVO.getFactura().isEmpty()){
								if(tramiteTrafTranVO != null && tramiteTrafTranVO.getImprPermisoCircu() != null && 
										!tramiteTrafTranVO.getImprPermisoCircu().isEmpty() && "SI".equalsIgnoreCase(tramiteTrafTranVO.getImprPermisoCircu())){
									tramiteBaseSimultanea.setEsNubePuntos(Boolean.TRUE);
									break;
								}
							}
						} else if(TipoCarpeta.CARPETA_TIPO_A.getValorEnum().equals(carpeta) || TipoCarpeta.CARPETA_TIPO_I.getValorEnum().equals(carpeta)
								|| TipoCarpeta.CARPETA_CTIT.getValorEnum().equals(carpeta)) {
							if(tramiteTrafTranVO != null && tramiteTrafTranVO.getImprPermisoCircu() != null && 
									!tramiteTrafTranVO.getImprPermisoCircu().isEmpty() && "SI".equalsIgnoreCase(tramiteTrafTranVO.getImprPermisoCircu())){
								tramiteBaseSimultanea.setEsNubePuntos(Boolean.TRUE);
								break;
							}
						}
					}
				}
			}
		}
	}

	private TramiteBaseBean generarTramiteBaseBean(TramiteTraficoVO tramiteTrafico, String carpeta) {
		TramiteBaseBean tramiteBase = new TramiteBaseBean();
		tramiteBase.setBastidor(tramiteTrafico.getVehiculo() != null ? tramiteTrafico.getVehiculo().getBastidor() : null);
		tramiteBase.setFechaPresentacion(tramiteTrafico.getFechaPresentacion() != null ? utilesFecha.formatoFecha("dd-MM-yyyy",tramiteTrafico.getFechaPresentacion()) : null);
		tramiteBase.setMatricula(tramiteTrafico.getVehiculo() != null ? tramiteTrafico.getVehiculo().getMatricula() : null);
		tramiteBase.setNumColegiado(tramiteTrafico.getNumColegiado());
		tramiteBase.setNumExpediente(String.valueOf(tramiteTrafico.getNumExpediente()));
		tramiteBase.setTipoTasa(tramiteTrafico.getTasa() != null ? tramiteTrafico.getTasa().getTipoTasa() : null);
		tramiteBase.setRefPropia(tramiteTrafico.getRefPropia());
		nubePuntosDocBase(tramiteTrafico, tramiteBase, carpeta);
		informarCodigosMandato(tramiteBase, carpeta, tramiteTrafico.getIntervinienteTraficosAsList());
		return tramiteBase;
	}

	private void nubePuntosDocBase(TramiteTraficoVO tramiteTrafico, TramiteBaseBean tramiteBase, String carpeta) {
		String nubeMatriculasTransf = gestorPropiedades.valorPropertie("nube.matriculas.docBase");
		String colegiadoNubeMatriculasTransf = gestorPropiedades.valorPropertie("colegiado.nube.matriculas.docBase");
		if("SI".equalsIgnoreCase(nubeMatriculasTransf) && colegiadoNubeMatriculasTransf.contains(tramiteTrafico.getNumColegiado())){
			if(TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite()) 
					|| TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite())){
				if(TipoTasa.UnoCinco.getValorEnum().equals(tramiteTrafico.getTasa().getTipoTasa())){
					TramiteTrafTranVO tramiteTrafTranVO = esTramiteTransmision(tramiteTrafico);
					if(TipoCarpeta.CARPETA_TIPO_B.getValorEnum().equals(carpeta)){
						if(tramiteTrafTranVO.getFactura() != null && !tramiteTrafTranVO.getFactura().isEmpty()){
							if(tramiteTrafTranVO != null && tramiteTrafTranVO.getImprPermisoCircu() != null && 
									!tramiteTrafTranVO.getImprPermisoCircu().isEmpty() && "SI".equalsIgnoreCase(tramiteTrafTranVO.getImprPermisoCircu())){
								tramiteBase.setEsNubePuntos(Boolean.TRUE);
							}
						}
					} else if(TipoCarpeta.CARPETA_TIPO_A.getValorEnum().equals(carpeta) || TipoCarpeta.CARPETA_TIPO_I.getValorEnum().equals(carpeta)
							|| TipoCarpeta.CARPETA_CTIT.getValorEnum().equals(carpeta)) {
						if(tramiteTrafTranVO != null && tramiteTrafTranVO.getImprPermisoCircu() != null && 
								!tramiteTrafTranVO.getImprPermisoCircu().isEmpty() && "SI".equalsIgnoreCase(tramiteTrafTranVO.getImprPermisoCircu())){
							tramiteBase.setEsNubePuntos(Boolean.TRUE);
						}
					}
				}
			}
		}
	}

	private DocBaseBean setDatosDocBase(DocumentoBaseVO docBase) {
		DocBaseBean docBaseBean = new DocBaseBean();
		docBaseBean.setDocId(docBase.getDocId());
		docBaseBean.setQrCode(docBase.getQrCode());
		docBaseBean.setCarpeta(docBase.getCarpeta());
		docBaseBean.setFechaPresentacion(utilesFecha.formatoFecha("dd-MM-yyyy",docBase.getFechaPresentacion()));
		docBaseBean.setNumColegiado(docBase.getContrato().getColegiado().getNumColegiado());
		docBaseBean.setNifGestor(docBase.getContrato().getColegiado().getUsuario().getNif());
		docBaseBean.setGestoria(docBase.getContrato().getColegiado().getUsuario().getApellidosNombre());
		return docBaseBean;
	}

	
	@Override
	@Transactional(readOnly=true)
	public ResultadoDocBaseBean obtenerDatosIdImpresionDocBase(Long idImpresion) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			if(idImpresion != null){
				ImpresionVO impresionVO = impresionDao.getImpresion(idImpresion);
				if(impresionVO != null){
					List<BigDecimal> listaExpedientes = impresionTramiteTraficoDao.obtenerNumExpedientes(idImpresion);
					if(listaExpedientes != null && !listaExpedientes.isEmpty()){
						List<TramiteTraficoVO> listaTramitesBBDD = servicioTramiteTrafico.getListaTramitesTraficoVO(utiles.convertirBigDecimalListToBigDecimalArray(listaExpedientes), Boolean.TRUE);
						if(listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()){
							resultado.setListaTramites(listaTramitesBBDD);
							resultado.setTipoCarpeta(impresionVO.getTipoCarpeta());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existen datos de los expedientes asociados a la Impresion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para el id de Impresion: " + idImpresion);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un id para obtener los datos de su impresión.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos de la impresion: " + idImpresion +", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos de la impresion.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Boolean existeTramiteDocBasePdtImprimir(BigDecimal numExpediente) {
		ImpresionTramiteTraficoVO impresionTramiteTraficoVO = impresionTramiteTraficoDao.getTramiteImpresion(numExpediente, "DOC_BASE");
		if(impresionTramiteTraficoVO != null){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	@Transactional
	public ResultadoDocBaseBean borrarDatosImpresionDocBase(Long idImpresion, Long idUsuario) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			ImpresionVO impresionVO = impresionDao.getImpresion(idImpresion);
			if(impresionVO != null){
				Date fecha = new Date();
				impresionVO.setEstado(EstadosImprimir.Generado.getValorEnum());
				impresionVO.setFechaGenerado(fecha);
				guardarEvolucion(impresionVO.getIdImpresion(), idUsuario, fecha, EstadosImprimir.Generado.getValorEnum());
				List<ImpresionTramiteTraficoVO> listaImpresionTramites = impresionTramiteTraficoDao.getImpresionesTramiteTrafPorIdImpresion(idImpresion);
				if(listaImpresionTramites != null && !listaImpresionTramites.isEmpty()){
					for(ImpresionTramiteTraficoVO impresionTramiteTraficoVO : listaImpresionTramites){
						impresionTramiteTraficoDao.borrar(impresionTramiteTraficoVO);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la impresión para poder borrar.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de borrar los datos asociados a la solicitud de impresion del documento base con idImpresion: "+ idImpresion + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de borrar los datos asociados a la solicitud de impresion del documento base.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoDocBaseBean generarSolicitudImpresionDocBase(List<TramiteTraficoVO> listaExpedientes, TipoCarpeta tipoCarpeta, BigDecimal idUsuario, Long idContrato, Date fechaPresentacion) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			Date fechaAlta = new Date();
			ImpresionVO impresionVO = generarImpresionVO(tipoCarpeta.getValorEnum(), idContrato, idUsuario.longValue(), fechaAlta);
			impresionDao.guardar(impresionVO);
			guardarEvolucion(impresionVO.getIdImpresion(), idUsuario.longValue(), fechaAlta, EstadosImprimir.Creacion.getValorEnum());
			guardarTramitesImpresion(impresionVO.getIdImpresion(), listaExpedientes);
			String xmlEnviar = impresionVO.getIdImpresion().toString() + "_" + utilesFecha.formatoFecha("ddMMyyyy", fechaPresentacion);
			ResultadoBean resultBean = servicioCola.crearSolicitud(impresionVO.getIdImpresion(),ProcesosEnum.GENERAR_DOC_BASE.getNombreEnum(),
					gestorPropiedades.valorPropertie("nombreHostProceso"),    
					TipoTramiteDocBase.GENERACION.getValorEnum(), idUsuario, new BigDecimal(idContrato), xmlEnviar);
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para el proceso de generacion de doc.Base para el tipo: " + tipoCarpeta.getNombreEnum() + getExpedientesError(listaExpedientes));
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de crear la solicitud de impresion del documento base del tipo: "+ tipoCarpeta.getNombreEnum() +", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud de impresion del documento base del tipo: "+ tipoCarpeta.getNombreEnum() + getExpedientesError(listaExpedientes));
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		}
		return resultado;
	}
	
	private String getExpedientesError(List<TramiteTraficoVO> listaTramites){
		String mensaje = "";
		for(TramiteTraficoVO tramite : listaTramites){
			if(mensaje.isEmpty()){
				mensaje = " para los expedientes: "+ tramite.getNumExpediente().toString();
			} else{
				mensaje += ", " + tramite.getNumExpediente().toString();
			}
		}
		return mensaje;
	}

	private void guardarEvolucion(Long idImpresion, Long idUsuario, Date fecha, String estado) {
		EvolucionImpresionVO evolucionImpresion = new EvolucionImpresionVO();
		evolucionImpresion.setFechaCambio(fecha);
		evolucionImpresion.setIdImpresion(idImpresion);
		evolucionImpresion.setIdUsuario(idUsuario);
		evolucionImpresion.setTipoActuacion(estado);
		evolucionImpresionDao.guardar(evolucionImpresion);
	}

	private void guardarTramitesImpresion(Long idImpresion, List<TramiteTraficoVO> listaExpedientes) {
		for(TramiteTraficoVO tramiteTraficoVO : listaExpedientes){
			ImpresionTramiteTraficoVO impresionTramiteTraficoVO = new ImpresionTramiteTraficoVO();
			impresionTramiteTraficoVO.setIdImpresion(idImpresion);
			impresionTramiteTraficoVO.setNumExpediente(tramiteTraficoVO.getNumExpediente());
			impresionTramiteTraficoVO.setTipoImpresion("DOC_BASE");
			impresionTramiteTraficoDao.guardar(impresionTramiteTraficoVO);
		}
	}


	private ImpresionVO generarImpresionVO(String tipoCarpeta, Long idContrato, Long idUsuario, Date fechaAlta) {
		ImpresionVO impresion = new ImpresionVO();
		impresion.setEstado(EstadosImprimir.Creacion.getValorEnum());
		impresion.setFechaCreacion(fechaAlta);
		impresion.setIdContrato(idContrato);
		impresion.setIdUsuario(idUsuario);
		impresion.setTipoCarpeta(tipoCarpeta);
		impresion.setTipoDocumento("DOC_BASE");
		impresion.setTipoTramite(TipoTramiteDocBase.GENERACION.getValorEnum());
		return impresion;
	}
}
