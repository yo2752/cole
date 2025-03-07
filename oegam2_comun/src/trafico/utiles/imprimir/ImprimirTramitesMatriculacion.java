package trafico.utiles.imprimir;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Image;

import escrituras.beans.Direccion;
import escrituras.beans.ResultBean;
import general.utiles.UtilesCadenaCaracteres;
import oegam.constantes.ConstantesPQ;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TiposReduccion576;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImprimirTramitesMatriculacion extends ImprimirGeneral{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirTramitesMatriculacion.class);
	
	private ModeloMatriculacion modeloMatriculacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	//MÉTODOS PRINPCIPALES
	
	/**
	 * Método que devolerá byte[] con la impresión para matriculacion PDF417 borrador.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean matriculacionBorradorPDF417(String numExpediente) throws OegamExcepcion {

		TramiteTraficoMatriculacionBean detalleMatriculacion = new TramiteTraficoMatriculacionBean(true);
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		//String password = ficheroPropiedades.getMensaje(PASSWORD);
		Boolean error = false;
		String mensaje = "";
		int[] vectPags;
		vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		
		//Obtenemos el detalle del trámite
		resultadoDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}	
		
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = new CampoPdfBean();
		
		//Abro la plantilla del PDF que corresponda
		if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive()!=null){
			ruta += TipoImpreso.MatriculacionBorradorPDF417.getNombreEnum();
		}else if(detalleMatriculacion.getTramiteTraficoBean().getRenting().equals("false"))
		{
			ruta += TipoImpreso.MatriculacionBorradorPDF417.getNombreEnum();
		}
		else 
		{
			ruta += TipoImpreso.MatriculacionBorradorPDF417_2.getNombreEnum();
		}

		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);
		
		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);
		
		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);
		
		if(camposPlantilla.contains(ConstantesPDF.ID_IVTM)){
			//TODO MPC. Cambio IVTM.
			String IVTM = "DOCUMENTO ESCANEADO EN LA PLATAFORMA";
			if (null != detalleMatriculacion.getTramiteTraficoBean()) {
				IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
				IvtmMatriculacionDto ivtmMatriculacion =  modeloIVTM.buscarIvtmNumExp(detalleMatriculacion.getTramiteTraficoBean().getNumExpediente());
				if (ivtmMatriculacion != null && ivtmMatriculacion.getNrc()!=null) {
					IVTM = ivtmMatriculacion.getNrc();
				}
			}
			campoAux = new CampoPdfBean(ConstantesPDF.ID_IVTM, IVTM, 
					false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		
		byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		//Datos presentacion en Tramitetraficobean, Común para todos los métodos
		if(null==detalleMatriculacion.getTramiteTraficoBean()){
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresCampos(ConstantesPDF._11, camposPlantilla, detalleMatriculacion.getTramiteTraficoBean()));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);	
		}
		
		//Introducimos los datos de los intervinientes.
		if(null==detalleMatriculacion.getTitularBean()){
			mensaje = "Los datos del titular están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getTitularBean(), ConstantesPDF._TITULAR, camposPlantilla));
			
				//Comprobamos direccion del vehiculo y si no metemos la del titular
				// Dirección del vehículo
				VehiculoBean vehiculo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo();
				if (null == vehiculo.getDireccionBean().getNombreVia()) {
					Direccion direccion = detalleMatriculacion.getTitularBean().getPersona().getDireccion();

					camposFormateados.addAll(obtenerValoresDireccion(ConstantesPDF._8,
							direccion, ConstantesPDF._VEHICULO, camposPlantilla));
				}
				byte1 = pdf.setCampos(byte1, camposFormateados);
		}
		
		if(null==detalleMatriculacion.getArrendatarioBean()){
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getArrendatarioBean(), ConstantesPDF._ARRENDATARIO, camposPlantilla));
			

				byte1 = pdf.setCampos(byte1, camposFormateados);	

		
		}
		
		if(null==detalleMatriculacion.getConductorHabitualBean()){
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getConductorHabitualBean(), ConstantesPDF._CONDUCTOR_HABITUAL, camposPlantilla));
			
				byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		}
		
		if(null==detalleMatriculacion.getRepresentanteTitularBean()||
				null==detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre()||
				detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre().getValorEnum()!=ConceptoTutela.Tutela.getValorEnum()){
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getRepresentanteTitularBean(), ConstantesPDF._REPRESENTANTE, camposPlantilla));
			

			byte1 = pdf.setCampos(byte1, camposFormateados);	

		
		}
		
		//Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.
		
		pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
		byte1 = pdf.setCampo(byte1, ConstantesTrafico.ID_NUBE_PUNTOS, "BORRADOR");
		if(camposPlantilla.contains(ConstantesTrafico.ID_NUBE_PUNTOS_2)){
			byte1 = pdf.setCampo(byte1, ConstantesTrafico.ID_NUBE_PUNTOS_2, "BORRADOR");
		}
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		
		
		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del método
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}

		//Textos externos en el impreso
		//Comprabmos que sea imprimible telemáticamente y en el caso de que no lo sea, lo mostramos en letras grandes cruzadas
		//con la información de porque no ha sido validado telemáticamente.
		if(!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())){

				String mensajeNoValidacion = detalleMatriculacion.getTramiteTraficoBean().getRespuesta();

				if( null != mensajeNoValidacion && !"".equals(mensajeNoValidacion)){
					campoAux = new CampoPdfBean(ConstantesPDF.ID_ERRORES_MATE, "NO MATRICULABLE TELEMÁTICAMENTE: " + 
							mensajeNoValidacion, true, false, ConstantesPDF._10);
					camposFormateados.add(campoAux);	
				}
				
				
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}
		
		//Decidirá que tipo de impreso es, esto se coloca en el impreso abajo a la derecha.
		String mensajeExencion = "";
		if(null != detalleMatriculacion.getIdReduccion05() && !detalleMatriculacion.getIdReduccion05().equals("")){
			mensajeExencion = "MODELO 05 - " + 
				detalleMatriculacion.getIdReduccion05().getNombreEnum();
		}else
		if(null != detalleMatriculacion.getIdNoSujeccion06() && !detalleMatriculacion.getIdNoSujeccion06().equals("")){
			mensajeExencion = "MODELO 06 - " +
				detalleMatriculacion.getIdNoSujeccion06().getNombreEnum();
		}else
		if(detalleMatriculacion.isReduccion()){
			mensajeExencion = "MODELO 576 - " + 
				TiposReduccion576.FamiliaNumerosa.getNombreEnum();
		}else
			if(null != detalleMatriculacion.getTramiteTraficoBean().getIedtm() && !detalleMatriculacion.getTramiteTraficoBean().getIedtm().equals("")){
			mensajeExencion = "NO TIENE MODELO 576";
		}else{
			mensajeExencion = "MODELO 576";
		}
		
		if(!"".equals(mensajeExencion)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_EXENCION, 
					mensajeExencion, true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);	
		}

		
		//fin textos externos en impreso
			
		if (null!=detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula()){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, "MATRICULA: " + 
					detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula(), true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		} else {//Entrará el objeto de la causa de porqué no se ha matriculado.
			if (error != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, mensaje, true, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}
		}
		
		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() != null &&
				null != detalleMatriculacion.getOtrosSinCodig() && !"".equals(detalleMatriculacion.getOtrosSinCodig())) {
		
			String sinCodigTexto =  "DIFERENCIAS EN CÓDIGO ITV: " + detalleMatriculacion.getOtrosSinCodig();
			sinCodigTexto = sinCodigTexto.substring(0,sinCodigTexto.lastIndexOf(","));
			campoAux = new CampoPdfBean(ConstantesPDF.ID_SINCODIG, sinCodigTexto, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
		
		
		byte1 = pdf.setCampos(byte1, camposFormateados);
		
		byte1 = 
			insertarFirmasColegiadoYColegio(detalleMatriculacion.getTramiteTraficoBean(), detalleMatriculacion.getTitularBean(), byte1, vectPags, pdf);
		
		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		
		resultadoMetodo.addAttachment("pdf", byte1);  
		
		return resultadoMetodo;
	}

	
	
	/**
	 * Método que generará el PDF de Matriculación 417 para un trámite validado.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean matriculacionPDF417(String numExpediente) throws OegamExcepcion {
		TramiteTraficoMatriculacionBean detalleMatriculacion = new TramiteTraficoMatriculacionBean(true);
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		String rutaPlantillas = ruta;
		Boolean error = false;
		String mensaje = "";
		
		//Obtenemos el detalle del trámite
		resultadoDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}	
		
		// En que pagina insertamos las imagenes
		int[] vectPags;
		vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		String nube = null;
		String nube2 = null;
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = new CampoPdfBean();
		
		//Abro la plantilla del PDF que corresponda
		if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive()!=null){
			ruta += TipoImpreso.MatriculacionPDF417.getNombreEnum();
		}else if(detalleMatriculacion.getTramiteTraficoBean().getRenting().equals("false"))
		{
			ruta += TipoImpreso.MatriculacionPDF417.getNombreEnum();
		}
		else 
		{
			ruta += TipoImpreso.MatriculacionPDF417_2.getNombreEnum();
		}	
		
		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);
		
		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);
		
		if(camposPlantilla.contains(ConstantesPDF.ID_IVTM)){
			//TODO MPC. Cambio IVTM.
			String IVTM = "DOCUMENTO ESCANEADO EN LA PLATAFORMA";
			if (null != detalleMatriculacion.getTramiteTraficoBean()) {
				IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
				IvtmMatriculacionDto ivtmMatriculacion =  modeloIVTM.buscarIvtmNumExp(detalleMatriculacion.getTramiteTraficoBean().getNumExpediente());
				if (ivtmMatriculacion != null && ivtmMatriculacion.getNrc()!=null) {
					IVTM = ivtmMatriculacion.getNrc();
				}
			}
			campoAux = new CampoPdfBean(ConstantesPDF.ID_IVTM, IVTM, 
					false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		
			byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
		if(null==detalleMatriculacion.getTramiteTraficoBean()){
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresCampos(ConstantesPDF._11, camposPlantilla, detalleMatriculacion.getTramiteTraficoBean()));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);	

		}
		
		//Introducimos los datos de los intervinientes.
		if(null==detalleMatriculacion.getTitularBean()){
			mensaje = "Los datos del titular están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getTitularBean(), ConstantesPDF._TITULAR, camposPlantilla));
			
				//Comprobamos direccion del vehiculo y si no metemos la del titular
				// Dirección del vehículo
				VehiculoBean vehiculo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo();
				if (null == vehiculo.getDireccionBean().getNombreVia()) {
					Direccion direccion = detalleMatriculacion.getTitularBean().getPersona().getDireccion();

					camposFormateados.addAll(obtenerValoresDireccion(ConstantesPDF._8,
							direccion, ConstantesPDF._VEHICULO, camposPlantilla));
				}
				byte1 = pdf.setCampos(byte1, camposFormateados);
		}
		
		if(detalleMatriculacion.getArrendatarioBean() != null){
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getArrendatarioBean(), ConstantesPDF._ARRENDATARIO, camposPlantilla));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		}
		
		if(detalleMatriculacion.getConductorHabitualBean() != null){
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getConductorHabitualBean(), ConstantesPDF._CONDUCTOR_HABITUAL, camposPlantilla));
			
				byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		}
		
		if(null==detalleMatriculacion.getRepresentanteTitularBean()||
				null==detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre()||
				detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre().getValorEnum()!=ConceptoTutela.Tutela.getValorEnum()){
			//mensaje = "Los datos del Representante del titular están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getRepresentanteTitularBean(), ConstantesPDF._REPRESENTANTE, camposPlantilla));
			

			byte1 = pdf.setCampos(byte1, camposFormateados);	

		}
		
		// Ricardo Rodriguez. Incidencia Mantis id = 2295
		// Resumen : Excepción sin capturar (trámite renting sin conductor habitual y arrendatario imprimir pdf)
		TramiteTraficoBean tramiteTrafico = detalleMatriculacion.getTramiteTraficoBean();
		if(tramiteTrafico != null && tramiteTrafico.getRenting() != null){
			if(tramiteTrafico.getRenting().equalsIgnoreCase("true")){
				// Tiene renting marcado. Debe llevar o arrendatario o conductor habitual
				boolean faltaArrendatario = false;
				boolean faltaConductorHabitual = false;
				if(detalleMatriculacion.getArrendatarioBean() == null ||
						detalleMatriculacion.getArrendatarioBean().getPersona() == null ||
						detalleMatriculacion.getArrendatarioBean().getPersona().getNif() == null){
					faltaArrendatario = true;
				}
				if(detalleMatriculacion.getConductorHabitualBean() == null ||
						detalleMatriculacion.getConductorHabitualBean().getPersona() == null ||
						detalleMatriculacion.getConductorHabitualBean().getPersona().getNif() == null){
					faltaConductorHabitual = true;
				}
				if(faltaConductorHabitual && faltaArrendatario){
					error = true;
					mensaje = "Con renting hay que especificar 'arrendatario' o 'conductor habitual'.";
				}
			}
		}
		// FIN Incidencia Mantis id = 2295
		
		//Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.		
		
		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del método
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}
		
		if (null!=detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula()){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, "MATRICULA: " + 
					detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula(), true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		} else {
			if (error != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, mensaje, true, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}
		}
		
		//Genero y formateo las nubes de puntos
		if(!error && byte1!=null){
			// Creamos el texto de la nube de puntos
			nube = obtenerNubeMatriculacion(detalleMatriculacion);
			
			//PDF Nive
			if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive()!=null){
				nube = obtenerNubeMatriculacionNive(detalleMatriculacion);
			}
			
//			nube = UtilesCadenaCaracteres.sustituyeCaracteres_Plus(nube);
			nube = UtilesCadenaCaracteres.sustituyeCaracteres(nube);
			
			//Inserta las nubes de puntos
			byte1 = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS, byte1, vectPags, nube, pdf);
			
			if(camposPlantilla.contains(ConstantesTrafico.ID_NUBE_PUNTOS_2)){
				nube2 = obtenerNubeMatriculacion2(detalleMatriculacion);
				nube2 = UtilesCadenaCaracteres.sustituyeCaracteres(nube2);
				byte1 = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS_2, byte1, vectPags, nube2, pdf);
			}
			
		}
		
		byte1 = 
			insertarFirmasColegiadoYColegio(detalleMatriculacion.getTramiteTraficoBean(), detalleMatriculacion.getTitularBean(), byte1, vectPags, pdf);
		
		//Textos externos en el impreso
		//Comprabmos que sea imprimible telemáticamente y en el caso de que no lo sea, lo mostramos en letras grandes cruzadas
		//con la información de porque no ha sido validado telemáticamente.
		if(!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())){

				String mensajeNoValidacion = detalleMatriculacion.getTramiteTraficoBean().getRespuesta();

				if( null != mensajeNoValidacion && !"".equals(mensajeNoValidacion)){
					campoAux = new CampoPdfBean(ConstantesPDF.ID_ERRORES_MATE, "NO MATRICULABLE TELEMÁTICAMENTE: " + 
							mensajeNoValidacion, true, false, ConstantesPDF._10);
					camposFormateados.add(campoAux);	
				}
				
				
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}

			
		//Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.
		String rutaMarcaAgua = util.getFilePath(rutaPlantillas + ConstantesPDF.RUTA_MARCA_AGUA);
		
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		//SINCODIG_DEBUG comentar línea siguiente para debug
		byte1 = pdf.insertarMarcaDeAgua(byte1, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);		

		
		
		//Decidirá que tipo de impreso es, esto se coloca en el impreso abajo a la derecha.
		String mensajeExencion = "";
		if(null != detalleMatriculacion.getIdReduccion05() && !detalleMatriculacion.getIdReduccion05().equals("")){
			mensajeExencion = "MODELO 05 - " + 
				detalleMatriculacion.getIdReduccion05().getNombreEnum();
		}else
		if(null != detalleMatriculacion.getIdNoSujeccion06() && !detalleMatriculacion.getIdNoSujeccion06().equals("")){
			mensajeExencion = "MODELO 06 - " +
				detalleMatriculacion.getIdNoSujeccion06().getNombreEnum();
		}else
		if(detalleMatriculacion.isReduccion()){
			mensajeExencion = "MODELO 576 - " + 
				TiposReduccion576.FamiliaNumerosa.getNombreEnum();
		}else
			if(null != detalleMatriculacion.getTramiteTraficoBean().getIedtm() && !detalleMatriculacion.getTramiteTraficoBean().getIedtm().equals("")){
			mensajeExencion = "NO TIENE MODELO 576";
		}else{
			mensajeExencion = "MODELO 576";
		}
		
		if(!"".equals(mensajeExencion)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_EXENCION,
					mensajeExencion, true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);	
		}

		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() != null &&
				null != detalleMatriculacion.getOtrosSinCodig() && !"".equals(detalleMatriculacion.getOtrosSinCodig())) {
		
			String sinCodigTexto =  "DIFERENCIAS EN CÓDIGO ITV: " + detalleMatriculacion.getOtrosSinCodig();
			sinCodigTexto = sinCodigTexto.substring(0,sinCodigTexto.lastIndexOf(","));
			campoAux = new CampoPdfBean(ConstantesPDF.ID_SINCODIG, sinCodigTexto, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
		
		byte1 = pdf.setCampos(byte1, camposFormateados);
		//fin textos externos en impreso
		
		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		
		resultadoMetodo.addAttachment("pdf", byte1);  
		
		return resultadoMetodo;
	}
	
	
	
	

	/**
	 * Método para obtener el impreso PDF correspondiente a la presentación telemática.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean matriculacionPDFPresentacionTelematica(
			String numExpediente) throws OegamExcepcion {
		TramiteTraficoMatriculacionBean detalleMatriculacion = new TramiteTraficoMatriculacionBean(true);
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		//String password = ficheroPropiedades.getMensaje(PASSWORD);
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		String rutaPlantillas = ruta;
		Boolean error = false;
		String mensaje = "";
		
		//Obtenemos el detalle del trámite
		resultadoDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}	
		
		// En que pagina insertamos las imagenes
		int[] vectPags;
		vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		String nube = null;
		String nube2 = null;
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = new CampoPdfBean();
		
		//Abro la plantilla del PDF que corresponda
		// DRC@19-12-2012 Inciencia Mantis: 3127
		if ((detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive() != null && detalleMatriculacion.getTramiteTraficoBean().getRenting().equals("false")) ||
		  	 detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive() == null && detalleMatriculacion.getTramiteTraficoBean().getRenting().equals("false")) {
			ruta += TipoImpreso.MatriculacionPDFPresentacionTelematica.getNombreEnum();
		} else {
			ruta += TipoImpreso.MatriculacionPDFPresentacionTelematica_2.getNombreEnum();
		}
		
		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);
		
		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);
		
		if(camposPlantilla.contains(ConstantesPDF.ID_IVTM)){
			//TODO MPC. Cambio IVTM.
			String IVTM = "DOCUMENTO ESCANEADO EN LA PLATAFORMA";
			if (null != detalleMatriculacion.getTramiteTraficoBean()) {
				IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
				IvtmMatriculacionDto ivtmMatriculacion =  modeloIVTM.buscarIvtmNumExp(detalleMatriculacion.getTramiteTraficoBean().getNumExpediente());
				if (ivtmMatriculacion != null && ivtmMatriculacion.getNrc()!=null) {
					IVTM = ivtmMatriculacion.getNrc();
				}
			}
			campoAux = new CampoPdfBean(ConstantesPDF.ID_IVTM, IVTM, 
					false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		
			byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
		if(null==detalleMatriculacion.getTramiteTraficoBean()){
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresCampos(ConstantesPDF._11, camposPlantilla, detalleMatriculacion.getTramiteTraficoBean()));
			
				byte1 = pdf.setCampos(byte1, camposFormateados);	
		}
		
		//Introducimos los datos de los intervinientes.
		if(null==detalleMatriculacion.getTitularBean()){
			mensaje = "Los datos del titular están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getTitularBean(), ConstantesPDF._TITULAR, camposPlantilla));
				//Comprobamos direccion del vehiculo y si no metemos la del titular
				// Dirección del vehículo
				VehiculoBean vehiculo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo();
				if (null == vehiculo.getDireccionBean().getNombreVia()) {
					Direccion direccion = detalleMatriculacion.getTitularBean().getPersona().getDireccion();

					camposFormateados.addAll(obtenerValoresDireccion(ConstantesPDF._8,
							direccion, ConstantesPDF._VEHICULO, camposPlantilla));
				}
				byte1 = pdf.setCampos(byte1, camposFormateados);
		}
		
		if(null==detalleMatriculacion.getArrendatarioBean()){
			//mensaje = "Los datos del Arrendatario están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getArrendatarioBean(), ConstantesPDF._ARRENDATARIO, camposPlantilla));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);	

		}
		
		if(null==detalleMatriculacion.getConductorHabitualBean()){
			//mensaje = "Los datos del Conductor habitual están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getConductorHabitualBean(), ConstantesPDF._CONDUCTOR_HABITUAL, camposPlantilla));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);
		}
		//Repasar Mensajes de errores
		if(null==detalleMatriculacion.getRepresentanteTitularBean()||
				null==detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre()||
				detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre().getValorEnum()!=ConceptoTutela.Tutela.getValorEnum()){
			//mensaje = "Los datos del Representante del titular están vacíos";
			mensaje = "No tutelado.";
		}
		else{
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._8, detalleMatriculacion.getRepresentanteTitularBean(), ConstantesPDF._REPRESENTANTE, camposPlantilla));
			
				byte1 = pdf.setCampos(byte1, camposFormateados);	
		
		}
		
		//Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.		
		
		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del método
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}
		
		if (null!=detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula()){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, "MATRICULA: " + 
					detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatricula(), true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		} else {
			if (error != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TELEMATICO, mensaje, true, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}
		}
		
		//Genero y formateo las nubes de puntos
		if(!error && byte1!=null){
			// Creamos el texto de la nube de puntos
			nube = obtenerNubeMatriculacion(detalleMatriculacion);
			
			if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive()!=null){
				nube = obtenerNubeMatriculacionNive(detalleMatriculacion);
			}

			nube = UtilesCadenaCaracteres.sustituyeCaracteres(nube);
			
			//Inserta las nubes de puntos
			byte1 = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS, byte1, vectPags, nube, pdf);
			
			if(camposPlantilla.contains(ConstantesTrafico.ID_NUBE_PUNTOS_2)) {
				nube2 = obtenerNubeMatriculacion2(detalleMatriculacion);
				if (null != nube2){
					nube2 = UtilesCadenaCaracteres.sustituyeCaracteres(nube2);
					byte1 = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS_2, byte1, vectPags, nube2, pdf);
				}
			}
			
		}

		
		byte1 = insertarFirmasColegiadoYColegio(detalleMatriculacion.getTramiteTraficoBean(), detalleMatriculacion.getTitularBean(), byte1, vectPags, pdf);
		
		//Textos externos en el impreso
		//Comprabmos que sea imprimible telemáticamente y en el caso de que no lo sea, lo mostramos en letras grandes cruzadas
		//con la información de porque no ha sido validado telemáticamente.
		if(!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()) &&
				!detalleMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())){

				String mensajeNoValidacion = detalleMatriculacion.getTramiteTraficoBean().getRespuesta();

				if( null != mensajeNoValidacion && !"".equals(mensajeNoValidacion) ){
					campoAux = new CampoPdfBean(ConstantesPDF.ID_ERRORES_MATE, "NO MATRICULABLE TELEMÁTICAMENTE: " + 
							mensajeNoValidacion, true, false, ConstantesPDF._10);
					camposFormateados.add(campoAux);	
				}
				
				
				
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}

			
		//Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.
		String rutaMarcaAgua = util.getFilePath(rutaPlantillas + ConstantesPDF.RUTA_MARCA_AGUA);
		
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		//SINCODIG_DEBUG comentar línea siguiente para debug
		byte1 = pdf.insertarMarcaDeAgua(byte1, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);		
			
		
		//Decidirá que tipo de impreso es, esto se coloca en el impreso abajo a la derecha.
		String mensajeExencion = "";
		if(null != detalleMatriculacion.getIdReduccion05() && !detalleMatriculacion.getIdReduccion05().equals("")){
			mensajeExencion = "MODELO 05 - " + 
				detalleMatriculacion.getIdReduccion05().getNombreEnum();
		}else
		if(null != detalleMatriculacion.getIdNoSujeccion06() && !detalleMatriculacion.getIdNoSujeccion06().equals("")){
			mensajeExencion = "MODELO 06 - " +
				detalleMatriculacion.getIdNoSujeccion06().getNombreEnum();
		}else
		if(detalleMatriculacion.isReduccion()){
			mensajeExencion = "MODELO 576 - " + 
				TiposReduccion576.FamiliaNumerosa.getNombreEnum();
		}else
		if(null != detalleMatriculacion.getTramiteTraficoBean().getIedtm() && !detalleMatriculacion.getTramiteTraficoBean().getIedtm().equals("")){
			mensajeExencion = "NO TIENE MODELO 576";
		}else{
			mensajeExencion = "MODELO 576";
		}
		
		if(!"".equals(mensajeExencion)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_EXENCION,
					mensajeExencion, true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);	
		}

		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() != null &&
				null != detalleMatriculacion.getOtrosSinCodig() && !"".equals(detalleMatriculacion.getOtrosSinCodig())) {
		
			String sinCodigTexto =  "DIFERENCIAS EN CÓDIGO ITV: " + detalleMatriculacion.getOtrosSinCodig();
			sinCodigTexto = sinCodigTexto.substring(0,sinCodigTexto.lastIndexOf(","));
			campoAux = new CampoPdfBean(ConstantesPDF.ID_SINCODIG, sinCodigTexto, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
		
		byte1 = pdf.setCampos(byte1, camposFormateados);
		//fin textos externos en impreso
		
		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		
		resultadoMetodo.addAttachment("pdf", byte1);  
		
		return resultadoMetodo;
	}


	/**
	 * Método para imprimir mandatos genericos.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion 
	 */
	public ResultBean matriculacionMandatoGenerico(String numExpediente) throws OegamExcepcion {
		TramiteTraficoMatriculacionBean detalleMatriculacion = new TramiteTraficoMatriculacionBean(true);
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		Boolean error = false;
		String mensaje = "";
		
		//Obtenemos el detalle del trámite
		resultadoDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}	
		
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		
		//Abro la plantilla del PDF que corresponda
			ruta += TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum();

		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);
		
		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);
		
		
		//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
		if(null==detalleMatriculacion){
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, detalleMatriculacion.getTitularBean(),detalleMatriculacion.getRepresentanteTitularBean(), detalleMatriculacion.getTramiteTraficoBean().getIdContrato(), camposPlantilla));
			
			byte1 = pdf.setCampos(byte1, camposFormateados);	
		}	
		
		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del método
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}
		
		//fin textos externos en impreso
		
		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byte1);  
		resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "MandatoGenerico.pdf");
		return resultadoMetodo;
	}
	
	/**
	 * Método para imprimir mandatos específicos.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion 
	 */
	public ResultBean matriculacionMandatoEspecifico(String numExpediente) throws OegamExcepcion {
		TramiteTraficoMatriculacionBean detalleMatriculacion = new TramiteTraficoMatriculacionBean(true);
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		Boolean error = false;
		String mensaje = "";
		
		//Obtenemos el detalle del trámite
		resultadoDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}	
		
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = new CampoPdfBean();
		
		//Abro la plantilla del PDF que corresponda
			ruta += TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum();

		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);
		
		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);
		
		
		//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
		if(null==detalleMatriculacion){
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}
		else{
			camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, detalleMatriculacion.getTitularBean(),detalleMatriculacion.getRepresentanteTitularBean(), detalleMatriculacion.getTramiteTraficoBean().getIdContrato(), camposPlantilla));
			
				byte1 = pdf.setCampos(byte1, camposFormateados);
		}	
		
		//Va sin acento por que no la muestra en la plantilla.
		if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "MATRICULACION: ", 
					false, false, ConstantesPDF._11);
			camposFormateados.add(campoAux);
		}
		
		if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, 
					detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getBastidor(), 
					false, false, ConstantesPDF._11);
			camposFormateados.add(campoAux);
		}
		
		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del método
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}
		
		//Genero y formateo las nubes de puntos
		
		byte1 = pdf.setCampos(byte1, camposFormateados);
		
		if(null==byte1){
			error = true;
			mensaje = "No se ha cargado la plantilla de Mandato.";
		}
		//fin textos externos en impreso
		
		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byte1);
		resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "MandatoEspecifico.PDF");
		
		return resultadoMetodo;
	}

	/**
	 * Método que generará el PDF con las pegatinas para los trámites que le pasemos como parámetro.
	 * @param matrYBasti
	 * @return
	 */
	public ResultBean matriculacionPegatinasEtiquetaMatricula(
			ParametrosPegatinaMatriculacion parametrosPegatinas) {

		ResultBean resultadoImpreso = new ResultBean();
		byte[] byte1 = null;
		resultadoImpreso.setError(false);
		String datos =  parametrosPegatinas.getMatryBast();
		String[] listaMatriculaBastidor = datos.split("_");
		
		//Obtenemos las matrículas y los bastidores del String que recibimos
		//Vendrá separado por "_" un trámite y por ";" la matrícula del bastidor dentro del trámite.
		try {
			byte1 =	generarPdfEtiquetas(parametrosPegatinas, listaMatriculaBastidor);
		} catch (Exception e) {
			log.error(e);
		}		

		if(null == byte1){
			resultadoImpreso.setError(true);
			resultadoImpreso.setMensaje("No se generado correctamente el documento de etiquetas.");	
			return resultadoImpreso;			
		}
		
		resultadoImpreso.addAttachment(ResultBean.TIPO_PDF, byte1);
		resultadoImpreso.addAttachment(ResultBean.NOMBRE_FICHERO, "pegatinasEtiquetaMatricula.pdf");
		return resultadoImpreso;
	}


	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

}
