package trafico.utiles.imprimir;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Image;

import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.avpo.ImpresionAnuntisAvpo;
import trafico.beans.avpobastigest.AvpoBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

public class ImprimirTramitesSolicitud extends ImprimirGeneral{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirTramitesSolicitud.class);
	//MÉTODOS PRINPCIPALES

	private ModeloSolicitudDatos modeloSolicitudDatos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	/**
	 * Imprime una Solicitud avpo con un informe de error
	 * @param detalleSolicitud
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean  solicitudAvpoError(SolicitudVehiculoBean detalleSolicitud) 
		throws OegamExcepcion {
		
			ResultBean resultadoMetodo = new ResultBean();
			String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
			Boolean error = false;
			String mensaje = "";
			
			byte[] byte1 = null;
			PdfMaker pdf = null;
			pdf = new PdfMaker();
			ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
			
			UtilResources util = new UtilResources();
			String file = util.getFilePath(ruta + TipoImpreso.SolicitudBorradorPDF417.getNombreEnum());
			
			//Abro la plantilla del PDF que corresponda
			byte1 = pdf.abrirPdf(file);

			//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
			Set<String> camposPlantilla = pdf.getAllFields(byte1);
			
			for (String nombreCampo : camposPlantilla) {
				log.debug(nombreCampo);
			}
			
			//Datos presentacion
			if(null==detalleSolicitud.getNumExpediente()){
				error = true;
				mensaje = "Los datos del trámite están vacíos";
			}
			else{
				
				CampoPdfBean campoAux = null;
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MATRICULA, 
						detalleSolicitud.getVehiculo().getMatricula().toString(), false, false, 8);
				camposFormateados.add(campoAux);

				if (camposPlantilla.contains(ConstantesTrafico.ID_TASA)) {
					if (detalleSolicitud.getTasa().getCodigoTasa() != null) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_TASA,
								detalleSolicitud.getTasa().getCodigoTasa(), false,
								false, 12);
						camposFormateados.add(campoAux);
					}
				}
				
				if (camposPlantilla.contains(ConstantesTrafico.ID_ERROR)) {
					if (detalleSolicitud.getResultado() != null) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_ERROR,
								detalleSolicitud.getResultado(), false,
								false, 12);
						camposFormateados.add(campoAux);
					}
				}
				
				if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_COLEGIADO)) {
					if (utilesColegiado.getNumColegiadoSession() != null) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_COLEGIADO,
								utilesColegiado.getNumColegiadoSession(), false,
								false, 5);
						camposFormateados.add(campoAux);
					}
				}
				
				if (camposPlantilla.contains(ConstantesTrafico.ID_COLEGIADO)) {
					if (utilesColegiado.getApellidosNombreUsuario() != null) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_COLEGIADO,
								utilesColegiado.getApellidosNombreUsuario(), true,
								false, 40);
						camposFormateados.add(campoAux);
					}
				}
				

				byte1 = pdf.setCampos(byte1, camposFormateados);	

			
			}
			
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			
			//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
			//y lo pasamos el resultado del método
			if(error==true || byte1 == null){
				error = true;
				mensaje += ". Error al imprimir el PDF";
			}
			
			resultadoMetodo.setError(error);
			resultadoMetodo.setMensaje(mensaje);
			
			resultadoMetodo.addAttachment("pdf", byte1);  
			
			return resultadoMetodo;
	}
	
	

	 /**
	  * Imprime todo el lote de avpos en un zip
	 * @param listaAvpo
	 * @return
	 * @throws OegamExcepcion
	 */
	public Map<String, byte[]> imprimirLoteConsultaAVPO(List<AvpoBean> listaAvpo) throws OegamExcepcion{
	    	Map<String, byte[]> map = new HashMap<String, byte[]>();
	    	
	    	//int i=1;
	    	for(AvpoBean avpoBean: listaAvpo){
		    	String[] nombreFicheroPdf = new String[1];
		    	byte[] pdf = imprimirConsultaAVPO(avpoBean, nombreFicheroPdf);
		    	map.put(nombreFicheroPdf[0], pdf);
		    //	i++;
	    	}
	    	
	    	return map;
	    }

	/**
	 * Genera el documento PDF correspondiente al de trámite solicitud AVPO de Anuntis
	 * 
	 * @param bean Bean que contiene los datos de consulta a imprimir.
	 * @param template Ruta de la plantilla PDF
	 * @return Chorro de bytes con el contido del PDF generado
	 * @throws OegamExcepcion
	 */
	public byte[] imprimirConsultaAVPO(ImpresionAnuntisAvpo bean, String template) throws OegamExcepcion {
		log.trace("Entra en imprimirConsultaAVPO del modulo de Anuntis");;

		PdfMaker pdf = new PdfMaker();

		// Abro la plantilla del PDF que corresponda
		byte[] byte1 = pdf.abrirPdf(template);
		//
		// //Obtenemos todos los campos de la plantilla correspondiente y los
		// mapeamos con los valores del bean.
		// Set<String> camposPlantilla = pdf.getAllFields(byte1);

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		// Titulo
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MATRICULA_TIT, bean.getMatricula() != null ? bean.getMatricula() : "", true, false, ConstantesPDF._21));
		// Cuadro principal
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_BASTIDOR, bean.getBastidor() != null ? bean.getBastidor() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MATRICULA, bean.getMatricula() != null ? bean.getMatricula() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PRIMERA_MATRICULACION, bean.getFechaPrimeraMatriculacion() != null ? bean.getFechaPrimeraMatriculacion() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MATRICULACION, bean.getFechaMatriculacion() != null ? bean.getFechaMatriculacion() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MARCA, bean.getMarca() != null ? bean.getMarca() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MODELO, bean.getModelo() != null ? bean.getModelo() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PROCEDENCIA, bean.getProcedencia() != null ? bean.getProcedencia() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PROPULSION, bean.getPropulsion() != null ? bean.getPropulsion() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PLAZAS, bean.getPlazas() != null ? bean.getPlazas() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_CVF, bean.getCvf() != null ? bean.getCvf() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_CILINDRADA, bean.getCilindrada() != null ? bean.getCilindrada() : "", false, false, ConstantesPDF._12));
//		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_M_MAXIMA, bean.getmMaxima() != null ? bean.getmMaxima() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_TIPO_VARIANTE_VERSION, bean.getTipoVarianteVersion() != null ? bean.getTipoVarianteVersion() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_POTENCIA_NETA_MAXIMA, bean.getPotenciaNetaMaxima() != null ? bean.getPotenciaNetaMaxima() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MASA_CIRCULACION, bean.getMasaCirculacion() != null ? bean.getMasaCirculacion() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MMA, bean.getMma() != null ? bean.getMma() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_CO2, bean.getCo2() != null ? bean.getCo2() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PLAZA_PIE, bean.getPlazasPie() != null ? bean.getPlazasPie() : "", false, false, ConstantesPDF._12));

		// Datos de Localizacion
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_BASTIDOR_LOC, bean.getBastidor() != null ? bean.getBastidor() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MATRICULA_LOC, bean.getMatricula() != null ? bean.getMatricula() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PROVINCIA, bean.getProvincia() != null ? bean.getProvincia() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_NUM_TRANSFERENCIAS, bean.getNumTransferencias() != null ? bean.getNumTransferencias() : "", false, false, ConstantesPDF._12));

		// Datos ITV
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_CONCEPTO_ITV, bean.getConceptoItv() != null ? bean.getConceptoItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_FECHA_ITV, bean.getFechaItv() != null ? bean.getFechaItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_FECHA_CADUC_ITV, bean.getFechaCaducidadItv() != null ? bean.getFechaCaducidadItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_ESTACION_ITV, bean.getEstacionItv() != null ? bean.getEstacionItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_PROVINCIA_ITV, bean.getProvinciaItv() != null ? bean.getProvinciaItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_MOTIVO_ITV, bean.getMotivoItv() != null ? bean.getMotivoItv() : "", false, false, ConstantesPDF._12));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_DEFECTOS_ITV, bean.getDefectosItv() != null ? bean.getDefectosItv() : "", false, false, ConstantesPDF._12));

		// Incidencias
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_INCIDENCIAS, bean.getIncidencias() != null ? bean.getIncidencias() : "", false, false, ConstantesPDF._9));

		// Fecha y hora
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_ANUNTIS_TIMESTAMP, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), true, false, ConstantesPDF._9));

		byte1 = pdf.setCampos(byte1, camposFormateados);
		log.trace("Sale de imprimirConsultaAVPO ");
		return byte1;
	}

	 public byte[] imprimirConsultaAVPO(AvpoBean avpoBean, String[] nombreFichero, Boolean... firmar) throws OegamExcepcion {

			log.trace("Entra en imprimirConsultaAVPO");
			
			byte[] byteFinal = generarDocumentoConsulta(avpoBean, nombreFichero, firmar);
			log.trace("Sale de imprimirConsultaAVPO ");
			
			return byteFinal;
	}
	 
	 /**
		 * Genera el documento PDF correspondiente al tipo de trámite solicitado
		 * @param avpoBean Bean que contiene los datos de consulta a imprimir.
		 * @param nombreFichero Nombre del fichero del documento generado.
		 * @return
	 * @throws OegamExcepcion 
		 * @throws JMPException
		 */
		private byte[] generarDocumentoConsulta(AvpoBean avpoBean, String[] nombreFichero, Boolean... firmar) throws OegamExcepcion {
			log.trace("Entra en generarDocumentoConsulta");
			
			int[] vectPags;
			byte[] pdfCompleto = null;
			byte[] byteFinal = null;
			
			// Generamos el Pdf.
			PdfMaker pdf = new PdfMaker();


			Calendar fechaInforme = new GregorianCalendar();
			
			fechaInforme.setTime(avpoBean.getFechaInforme());
			/* Se elimina la fecha del nombre del archivo pdf
			 * String fecha = String.valueOf(fechaInforme.get(Calendar.YEAR)) 
						+ String.valueOf(fechaInforme.get(Calendar.MONTH)+1)
						+ String.valueOf(fechaInforme.get(Calendar.DATE))
						+ String.valueOf(fechaInforme.get(Calendar.HOUR_OF_DAY))
						+ String.valueOf(fechaInforme.get(Calendar.MINUTE));*/
			String hayError =avpoBean.getMensaje()!= null && !avpoBean.getMensaje().equals("")?"ERROR_":"";
			
			nombreFichero[0] = hayError + avpoBean.getMatricula();
			
			if (avpoBean.getBastidor()!= null)
						nombreFichero[0] = nombreFichero[0] + "_" + avpoBean.getBastidor();
			
			nombreFichero[0] = nombreFichero[0] +  ConstantesPDF.EXTENSION_PDF;	
			
			
			// En que pagina insertamos las imagenes
			vectPags = new int[1];
			vectPags[0] = 1;
			String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
			//String password = ficheroPropiedades.getMensaje(PASSWORD);
			String rutaPlantillas = ruta;
			
			UtilResources util = new UtilResources();
			
			String rutaMarcaAgua ="";
			if(!avpoBean.getError().booleanValue()){
				//Generamos el Pdf.				
				String file = util.getFilePath(ruta + TipoImpreso.SolicitudAvpo.getNombreEnum());
				pdfCompleto = pdf.abrirPdf(file);
				if(pdfCompleto!=null){
					pdfCompleto = completaDocumentoAVPO(pdfCompleto, pdf, avpoBean);
				}else{
					log.error("No se pudo encontrar la plantilla");
					return null;
				}
						
				rutaMarcaAgua = util.getFilePath(rutaPlantillas + ConstantesPDF.RUTA_MARCA_AGUA);
				Image img = pdf.cargarImagen(rutaMarcaAgua);
				pdfCompleto = pdf.insertarMarcaDeAgua(pdfCompleto, img, vectPags, 110, 250, 45);
		
				if((avpoBean.getListaDatos()!=null && avpoBean.getListaDatos().size()>0)){
					pdfCompleto = completaDocumentoAVPOAnexo(pdfCompleto, pdf, avpoBean);
				}
			}else{
				String file = util.getFilePath(ruta + TipoImpreso.SolicitudAvpoError.getNombreEnum());
				log.debug(ruta);
				//String file = util.getFilePath(ruta + "exampleSign.pdf");
				pdfCompleto = pdf.abrirPdf(file);
				if(pdfCompleto!=null){
				pdfCompleto = completaDocumentoAVPOError(pdfCompleto, pdf, avpoBean);
				}else{
					log.error("No se pudo encontrar la plantilla");
					return null;
				}
				rutaMarcaAgua = util.getFilePath(rutaPlantillas + ConstantesPDF.RUTA_MARCA_AGUA);
				
				Image img = pdf.cargarImagen(rutaMarcaAgua);
				
				pdfCompleto = pdf.insertarMarcaDeAgua(pdfCompleto, img, vectPags, 110, 250, 45);
			}
			
			
			//byteFinal = PdfMaker.encriptarPdf(pdfCompleto, "", "", true, true, true, true, true);
			byteFinal = pdfCompleto;
			UtilesViafirma utilesViafirma=new UtilesViafirma();
			//TODO FIRMA EL PDF --> NOSOTROS CON VIAFIRMA LA COMENTO PORQUE NO FUNCIONA TODAVÍA
			if(!avpoBean.getError().booleanValue()){
				try {
					String aliasColegio = gestorPropiedades.valorPropertie(ConstantesPDF.ALIAS_COLEGIO);
					String passwordColegio = gestorPropiedades.valorPropertie(ConstantesPDF.PASSWORD_COLEGIO);
					//byteFinal = utilesViafirma.firmarPDFServidor(pdfCompleto,aliasColegio);
					String rutaMarcaAguaPeque = util.getFilePath(rutaPlantillas + ConstantesPDF.RUTA_MARCA_AGUA_PEQUEÑA);
					if(firmar.length<=0 || firmar[0]){
						byteFinal = utilesViafirma.firmarPDFServidorImagen(pdfCompleto, aliasColegio, passwordColegio, rutaMarcaAguaPeque);
					}
						//firmaAdobeAVPO(byteFinal);
				} catch (Exception e) {

					log.error("Error al firmar AVPO: " + e.getMessage());
					return null;
				}
			}
			//byteFinal = PdfMaker.encriptarPdf(byteFinal, "", "", true, true, true, false, true);
			log.trace("Sale de generarDocumentoConsulta");
			return byteFinal;
		}
		
		
	
		
		
		/**
		 * Copmpleta la primera página del documento de consulta AVPO.
		 * @param byte1
		 * @param pdf
		 * @param avpoBean
		 */
		private byte[] completaDocumentoAVPO(byte[] byte1, PdfMaker pdf, AvpoBean avpoBean){
			log.trace("Entra en completaDocumentoAVPO");

			//Map donde se relaciona el nombre del campo con el valor
			HashMap<String, String> tabla = new HashMap<String, String>();
			
			//Se recorren todas los campos de consulta y se rellena el map de datos.
			tabla.put("ID_MATRICULA", avpoBean.getMatricula());
			tabla.put("ID_TASA", avpoBean.getTasa());
			
			//Lista de campos que contiene el PDF
			ArrayList<CampoPdfBean> campos = new ArrayList<CampoPdfBean>();
			//Campo del pdf
			CampoPdfBean campo;
			
			//Obtener el nombre de todos los campos del PDF.
			Set<String> nombresCampos = pdf.getAllFields(byte1);
			//A cada campo del PDF se le da su valor correspondiente (siempre que campo no se encuentre entre los tratados anteriormente)
			for(String nombre:nombresCampos){
				if(!nombre.contains("COLEGIADO")){
					campo = new CampoPdfBean(nombre, tabla.get(nombre));
					campos.add(campo);
				}
			}
			
			//NºColegiado
			campo = new CampoPdfBean("ID_NUM_COLEGIADO", "Nº COLEGIADO: " + utilesColegiado.getNumColegiadoSession(), true, false, 10);
			campos.add(campo);
			
			//Nombre Colegiado
			ModeloColegiado modelo = new ModeloColegiado();
			Persona colegiado =modelo.obtenerColegiadoCompleto(utilesColegiado.getIdContratoSessionBigDecimal());
			campo = new CampoPdfBean("ID_COLEGIADO", colegiado.getApellido1RazonSocial()+" "+colegiado.getApellido2()+", "+colegiado.getNombre()+" ", true, false, 10);
			campos.add(campo);

			
			//Insertar el valor de todos los campos
			byte1 = pdf.setCampos(byte1, campos);
			
			//Escribe la fecha de expedicion en las dos páginas
			String fecha = utilesFecha.getTextoFechaFormato(avpoBean.getFechaInforme(),new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
			String dia = fecha.substring(0,10);
			String hora = fecha.substring(11);
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, 9);
			byte1 = pdf.escribirTexto(byte1, dia, 1, 420, 61);
			byte1 = pdf.escribirTexto(byte1, hora, 1, 494, 61);
			
			log.trace("Sale de completaDocumentoAVPO ");
			return byte1;
		}

		/**
		 * Copmpleta la página del documento de consulta AVPO erróneo.
		 * @param byte1
		 * @param pdf
		 * @param avpoBean
		 */
		private byte[] completaDocumentoAVPOError(byte[] byte1, PdfMaker pdf, AvpoBean avpoBean){
			log.trace("Entra en completaDocumentoAVPOError");

			//Map donde se relaciona el nombre del campo con el valor
			HashMap<String, String> tabla = new HashMap<String, String>();

			//Se recorren todas los campos de consulta y se rellena el map de datos.
			tabla.put("ID_MATRICULA", avpoBean.getMatricula());
			tabla.put("ID_TASA", avpoBean.getTasa());
			tabla.put("ID_ERROR", avpoBean.getMensaje());
			
			//Lista de campos que contiene el PDF
			ArrayList<CampoPdfBean> campos = new ArrayList<CampoPdfBean>();
			//Campo del pdf
			CampoPdfBean campo;
			
			//Obtener el nombre de todos los campos del PDF.
			Set<String> nombresCampos = pdf.getAllFields(byte1);
			//A cada campo del PDF se le da su valor correspondiente (siempre que campo no se encuentre entre los tratados anteriormente)
			for(String nombre:nombresCampos){
				if(!nombre.contains("COLEGIADO")){
					campo = new CampoPdfBean(nombre, tabla.get(nombre));
					campos.add(campo);
				}
			}
			
			//NºColegiado
			campo = new CampoPdfBean("ID_NUM_COLEGIADO", "Nº COLEGIADO: " + utilesColegiado.getNumColegiadoSession(), true, false, 10);
			campos.add(campo);
			//Nombre Colegiado
			campo = new CampoPdfBean("ID_COLEGIADO", utilesColegiado.getApellidosNombreUsuario(), true, false, 10);
			campos.add(campo);

			//Insertar el valor de todos los campos
			byte1 = pdf.setCampos(byte1, campos);
			
			//Escribe la fecha de expedicion en las dos páginas
			//String fecha = UtilesVista.getTextoFechaHora(avpoBean.getFechaInforme());
			String fecha = utilesFecha.getTextoFechaFormato(avpoBean.getFechaInforme(),new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
			String dia = fecha.substring(0,10);
			String hora = fecha.substring(11);
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, 9);
			byte1 = pdf.escribirTexto(byte1, dia, 1, 420, 61);
			byte1 = pdf.escribirTexto(byte1, hora, 1, 494, 61);
			
			//escribe los números de página
			byte1 = escribirNumPagina(pdf, byte1, 1);

			log.trace("Sale de completaDocumentoAVPOError");
			return byte1;
		}
		
		 private byte[] escribirNumPagina(PdfMaker pdfMaker, byte[] pagina, int numPaginas){
		    	try{
		    		
		    		pdfMaker.establecerFuente(ConstantesPDF.HELVETICA, false, false, 9);
		    		for(int numeroPagina=1; 
		    		numeroPagina<=numPaginas; numeroPagina++){
		    			String texto = "Página " + numeroPagina + " de " + numPaginas;
		   				pagina = pdfMaker.escribirTexto(pagina, texto, numeroPagina,  30 , 810);
		    		}
		    		
		    		return pagina;
		    	}catch(Throwable e){
		    		log.error("Error al escribir los números de página: " + e);
		    		return null;
		    	}     
		    }
		 
		 /**
			 * Completa las hojas de anexo necesarias para completar el documento de consulta AVPO con los datos de tranferencias,
			 * inspecciones técnicas o cargas.
			 * 
			 * @param pdfMaker
			 * @param pdfCompleto
			 * @param avpoBean
			 * @return
			 */
		    private byte[] completaDocumentoAVPOAnexo(byte[] pdfCompleto, PdfMaker pdfMaker, AvpoBean avpoBean){
				log.trace("Entra en completaDocumentoAVPOAnexo");

		    	try{
		    		//Calcular el numero de paginas necesarias para poder escribir todos los anexos
					int numeroPaginas = calcularNumeroPaginas(avpoBean);
		    		
		    		//Insertamos las paginas necesarias para escribir todo 
		    		//(hay q tener en cuenta la que ya es la primera hoja)
		    		pdfCompleto = pdfMaker.insertarPagina(pdfCompleto, pdfCompleto, numeroPaginas-1);
		    			    		
		   			//escribo en la pagina las casillas correspondientes
		   			pdfCompleto = escribirTextoAnexos(pdfMaker, pdfCompleto, avpoBean);
		   			
		   			//escribe los números de página
		   			pdfCompleto = escribirNumPagina(pdfMaker, pdfCompleto, numeroPaginas);

		    	}catch(Throwable e){
		    		log.error("Error al completar anexo AVPO: " + e);
		    		return null;
		    	}

		    	log.trace("Sale de completaDocumentoAVPOAnexo");
		    	return pdfCompleto;
		    }
		 

		    /**
		     * Calcula el número de páginas de anexo necesarias para incluir todos los datos de transferencias, i.t. y cargas
		     * 
		     * @param avpoBean
		     * @return
		     */
		    private int calcularNumeroPaginas(AvpoBean avpoBean){
		    	int numeroPaginas = 0;
		    	int numeroFilas = 0;
		    	
		    	try{
		    		numeroFilas = avpoBean.getListaDatos().size();
		    		numeroPaginas = numeroFilas / ConstantesPDF.NUMERO_LINEAS_POR_PAGINA;
		    		int resto = numeroFilas % ConstantesPDF.NUMERO_LINEAS_POR_PAGINA;
		    		if (resto > 0){
		    			numeroPaginas = numeroPaginas +1;	    			    			    	
		    		}
		    	}catch(Throwable e){
		    		log.error("Error al calcular número de páginas AVPO: " + e);
		    		return 0;
		    	}
				log.trace("Número de páginas del anexo: " + numeroPaginas);

		    	return numeroPaginas;	    
		    }
		    
		    /**
		     * Escribe en las páginas de anexo las listas de transferencias, i.t. y cargas
		     * 
		     * @param pdfMaker
		     * @param pagina
		     * @param avpoBean
		     * @return
		     */
		    private byte[] escribirTextoAnexos(PdfMaker pdfMaker, byte[] pagina, AvpoBean avpoBean){
		    	try{
		    		int numeroPagina=1;
		    		int contLineas = 0; //Contador de líneas, para saber cuando tiene que pasar a la siguiente página
		    		float xInicial;
		    		float yInicial;
		    		StringBuffer buffer = new StringBuffer();
		    		
		    		//Posicion inicial
		    		xInicial = 50;
		    		yInicial = 760;
		    		
		    		pdfMaker.establecerFuente(ConstantesPDF.COURIER, false, false, 9);
		    		for(String linea: avpoBean.getListaDatos()){
		    			contLineas++;
		    			
		    			buffer.append(linea + "\n");
		    			//para separar las pantallas:
		    			if(contLineas == ConstantesPDF.NUMERO_LINEAS_POR_PAGINA/2){
		    				buffer.append("\n\n\n");
		    			}
		    			
		    			//Controla si quedan menos de _3 líneas para el final de la página y la línea actual es en blanco, pasa a la siguiente página
		    			if(contLineas == ConstantesPDF.NUMERO_LINEAS_POR_PAGINA){
		    				pagina = pdfMaker.escribirTextoColumna(pagina, buffer.toString(), numeroPagina, xInicial, yInicial, 600, 10, 12);
		    				contLineas = 0;
		    				numeroPagina++;
		    				buffer = new StringBuffer();
		    			}
		    		}
		    		if(buffer.length()!=0){
		    			pagina = pdfMaker.escribirTextoColumna(pagina, buffer.toString(), numeroPagina, xInicial, yInicial, 600, 10, 12);
		    		}
		    		
		    		return pagina;
		    	}catch(Throwable e){
		    		log.error("Error al escribir anexos AVPO: " + e);
		    		return null;
		    	}     
		    }
		    
		    
		  	    
		 
	/**
	 * Método para imprimir mandatos genericos. 
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean solicitudMandatoGenerico(String numExpediente) throws OegamExcepcion {
				HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
				ResultBean resultadoMetodo = new ResultBean();
				resultadoDetalle = getModeloSolicitudDatos().obtenerDetalle(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
				resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
				SolicitudDatosBean detalleSolicitud = (SolicitudDatosBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
				
				//String password = ficheroPropiedades.getMensaje(PASSWORD);
				String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
				Boolean error = false;
				String mensaje = "";
				
				//Obtenemos el detalle del trámite
				//resultadoDetalle = ModeloMatriculacion.obtenerDetalleConDescripciones(new BigDecimal(numExpediente));
				//resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
				//detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
				
				if(resultadoMetodo.getError()){
					return resultadoMetodo;
				}	
				
				if (detalleSolicitud.getSolicitante()== null || detalleSolicitud.getSolicitante().getPersona() == null 
						|| detalleSolicitud.getSolicitante().getPersona().getNif()== null || "".equals(detalleSolicitud.getSolicitante().getPersona().getNif())){
					resultadoMetodo.setError(true);
					resultadoMetodo.setMensaje("No hay Solicitante. No se puede obtener Mandato Genérico");
					return resultadoMetodo;
				}
				
				byte[] byte1 = null;
				PdfMaker pdf = null;
				pdf = new PdfMaker();
				ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
				
				//Abro la plantilla del PDF que corresponda
					ruta += TipoImpreso.SolicitudMandatoGenerico.getNombreEnum();

				UtilResources util = new UtilResources();
				String file = util.getFilePath(ruta);
				
				//Abro la plantilla del PDF que corresponda
				byte1 = pdf.abrirPdf(file);

				//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
				Set<String> camposPlantilla = pdf.getAllFields(byte1);
				
				
				//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
				if(null==detalleSolicitud){
					error = true;
					mensaje = "Los datos del trámite están vacíos";
				}
				else{
					camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, detalleSolicitud.getSolicitante(),new IntervinienteTrafico(true), detalleSolicitud.getTramiteTrafico().getIdContrato(), camposPlantilla));
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
				
				resultadoMetodo.addAttachment("pdf", byte1);  
				
				return resultadoMetodo;
		}
			
			
	/**
	 * Método para imprimir mandatos específicos.
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean solicitudMandatoEspecifico(String numExpediente) throws OegamExcepcion {
		HashMap<String, Object> resultadoDetalle = new HashMap<String, Object>();
		ResultBean resultadoMetodo = new ResultBean();
		resultadoDetalle = getModeloSolicitudDatos().obtenerDetalle(new BigDecimal(numExpediente),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		SolicitudDatosBean detalleSolicitud = (SolicitudDatosBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS); 
		Boolean error = false;
		String mensaje = "";
				
		//Obtenemos el detalle del trámite
		//resultadoDetalle = ModeloMatriculacion.obtenerDetalleConDescripciones(new BigDecimal(numExpediente));
		//resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		//detalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
				
		if(resultadoMetodo.getError()){
				return resultadoMetodo;
		}	
				
		if (detalleSolicitud.getSolicitante()== null || detalleSolicitud.getSolicitante().getPersona() == null 
				|| detalleSolicitud.getSolicitante().getPersona().getNif()== null || "".equals(detalleSolicitud.getSolicitante().getPersona().getNif())){
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No hay Solicitante. No se puede obtener Mandato Específico");
			return resultadoMetodo;
		}
		
		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = new CampoPdfBean();
				
			//Abro la plantilla del PDF que corresponda
		ruta += TipoImpreso.SolicitudMandatoEspecifico.getNombreEnum();

				UtilResources util = new UtilResources();
				String file = util.getFilePath(ruta);
				
				//Abro la plantilla del PDF que corresponda
				byte1 = pdf.abrirPdf(file);

				//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
				Set<String> camposPlantilla = pdf.getAllFields(byte1);
				
				
				//Datos presentacion en Tramitetrafico Bean, Común para todos los métodos
				if(null==detalleSolicitud){
					error = true;
					mensaje = "Los datos del trámite están vacíos";
				}
				else{
					camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, detalleSolicitud.getSolicitante(),new IntervinienteTrafico(true), detalleSolicitud.getTramiteTrafico().getIdContrato(), camposPlantilla));

						byte1 = pdf.setCampos(byte1, camposFormateados);	

				}	
				
				//Va sin acento por que no la muestra en la plantilla.
				if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)){
					campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "Solicitud de Informes: ", 
							false, false, ConstantesPDF._11);
					camposFormateados.add(campoAux);
				}
				
				String asuntoExplicacion = "";
				int cont = 1;
				//el asunto son todas las matriculas y bastidores
				for (SolicitudVehiculoBean solicitudVehiculo : detalleSolicitud.getSolicitudesVehiculos()) {
					
					if (solicitudVehiculo.getVehiculo().getMatricula()!= null && !"".equals(solicitudVehiculo.getVehiculo().getMatricula())){
						asuntoExplicacion+=solicitudVehiculo.getVehiculo().getMatricula();
					}else if (solicitudVehiculo.getVehiculo().getBastidor()!= null && !"".equals(solicitudVehiculo.getVehiculo().getBastidor())){
						asuntoExplicacion+=solicitudVehiculo.getVehiculo().getBastidor();
					}
					if (cont<detalleSolicitud.getSolicitudesVehiculos().size()){
						asuntoExplicacion+=", ";
					}
					cont++;
				}
				
				if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)){
					campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, 
							asuntoExplicacion, 
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
				
				resultadoMetodo.addAttachment("pdf", byte1);  
				
				return resultadoMetodo;
		}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}

}
