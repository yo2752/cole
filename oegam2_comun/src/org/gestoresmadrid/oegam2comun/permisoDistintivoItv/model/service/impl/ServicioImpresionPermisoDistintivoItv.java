package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Component
public class ServicioImpresionPermisoDistintivoItv implements Serializable{

	private static final long serialVersionUID = 5969245862109365339L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionPermisoDistintivoItv.class);
	public static final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public static final String NOMBRE_FICHERO_IMPRESION_MASIVA = "ImpresionMasivaDistintivos_";
	public static final String NOMBRE_FICHERO_IMPRESION = "ImpresionDistintivos_";
	public static final String NOMBRE_PERM_DSTV_EITV_IMPRESION = "DocumentosImpresos_";
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	public ResultadoPermisoDistintivoItvBean crearFicheroImpresionProceso(List<String> listaExpImprimir, String nombreFichero, Date fecha, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			String tUsuarioCreacion = "Usuario:"+ idUsuario + "\n";
			String tContratoCreacion = "Contrato:"+ idContrato + "\n";
			bos.write(tUsuarioCreacion.getBytes());
			bos.write(tContratoCreacion.getBytes());
			// Recorremos lista de trámites
			for (String numExpediente : listaExpImprimir) {
				String linea = numExpediente + "\n";
				bos.write(linea.getBytes());
			}
			File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO_IMPR_MASV_DIST, utilesFecha.getFechaConDate(fecha), nombreFichero, 
					ConstantesGestorFicheros.EXTENSION_TXT, bos.toByteArray());
			if(fichero == null){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de guardar el fichero de para la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos");
		}
		return resultado;
	}
	
	public List<BigDecimal> leerFichero(File fichero) throws Exception {
		List<BigDecimal> numExpedientes = new ArrayList<BigDecimal>();
		try {
			FileInputStream fin = new FileInputStream(fichero);
			BufferedReader inputAnterior = new BufferedReader(new InputStreamReader(fin, Claves.ENCODING_ISO88591));
			String ln = null;
			if (inputAnterior != null) {
				log.info("Fichero: " + fichero.getName() + ", Id_Usuario creacion:" + inputAnterior.readLine());
				log.info("Fichero: " + fichero.getName() + ", Id_Contrato creacion:" + inputAnterior.readLine());
				while ((ln = inputAnterior.readLine()) != null) {
					numExpedientes.add(new BigDecimal(ln));
				}
				inputAnterior.close();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de leer el fichero para la impresión de los distintivos, error: ",e);
		}
		
		return numExpedientes;
	}
	
	
	public File recogerFicheroImpresion(String nombreFichero) {
		FileResultBean ficheroAEnviar = null; 
		try {
			String[] sNombreFich = nombreFichero.split("_");
			ficheroAEnviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE,ConstantesGestorFicheros.ENVIO_IMPR_MASV_DIST, 
					new Fecha(utilesFecha.formatoFecha("ddMMyyyyHHmmss", sNombreFich[1])), nombreFichero, ConstantesGestorFicheros.EXTENSION_TXT);
			if(ficheroAEnviar != null && ficheroAEnviar.getFile() != null){
				return ficheroAEnviar.getFile();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e);
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e);
		}
		return null;
	}

	public ResultadoPermisoDistintivoItvBean guardarImpresionMasivaDistintivos(byte[] byteFichero, Long maxIdDocImpr, Date fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFicheroByte(byteFichero);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ficheroBean.setFecha(new Fecha(fecha));
			ficheroBean.setNombreDocumento(NOMBRE_FICHERO_IMPRESION + maxIdDocImpr);
			ficheroBean.setSubTipo(null);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.DISTINTIVOS);
			File fichero = gestorDocumentos.guardarByte(ficheroBean);
			if(fichero == null){
				resultado.setError(Boolean.TRUE);
				resultado.setExcepcion(new Exception("No se ha podido guardar el pdf con los distintivos"));
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de guardar los distintivos impresos masivamente, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los distintivos impresos.");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean concatPdf(List<FileInputStream> listaFicheros){
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(); 
		Document document = new Document();
		 String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + "_" + System.currentTimeMillis() + ".pdf";
         ByteArrayOutputStream  output = null;
         try {
        	 List<PdfReader> readers = new ArrayList<PdfReader>();
	         int totalPages = 0;
	            
	         for(InputStream pdf : listaFicheros){
	        	 PdfReader pdfReader = new PdfReader(pdf);
		         readers.add(pdfReader);
		         totalPages += pdfReader.getNumberOfPages();
	         }
	         
	         output = new ByteArrayOutputStream();
	         PdfWriter writer = PdfWriter.getInstance(document, output);
	 
	         document.open();
	         PdfContentByte cb = writer.getDirectContent();
	 
	         PdfImportedPage page;
	         int currentPageNumber = 0;
	         int pageOfCurrentReaderPDF = 0;
	         for(PdfReader pdfReader : readers){
	        	 while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	 
	        		 Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
	        		 document.setPageSize(rectangle);
	                 document.newPage();
	 
	                 pageOfCurrentReaderPDF++;
	                 currentPageNumber++;
	                 page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
	                 switch (rectangle.getRotation()) {
	                 	case 0:
	                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
	                        break;
	                    case 90:
	                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
	                                .getPageSizeWithRotation(1).getHeight());
	                        break;
	                    case 180:
	                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
	                        break;
	                    case 270:
	                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
	                                .getPageSizeWithRotation(1).getWidth(), 0);
	                        break;
	                    default:
	                        break;
	                 }
                     cb.beginText();
                     cb.getPdfDocument().getPageSize();
                     cb.endText();
	        	 }
	             pageOfCurrentReaderPDF = 0;
	         }
	         output.flush();
	         document.close();
	         output.close();
         } catch (Exception e) {
        	 log.error("Ha sucedido un error a la hora de imprimirlos distintivos, error: ",e);
        	 resultado.setError(Boolean.TRUE);
        	 resultado.setMensaje("Ha sucedido un error a la hora de imprimirlos distintivos.");
         } finally {
        	 if (document.isOpen())
        		 document.close();
        	 	try {
	                if (output != null)
	                	output.close();
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
         }
	     if(!resultado.getError()){
	        resultado.setByteFichero(output.toByteArray());
	        resultado.setNombreFichero(nombreFichero);
	     }
	     return resultado;
	}
	
	/*public ResultadoPermisoDistintivoItvBean crearPdfOrdenado(List<FileInputStream> listaFicheros, List<TramiteTraf>){
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(); 
		Document document = new Document();
		 String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + "_" + System.currentTimeMillis() + ".pdf";
         String url = gestorPropiedades.valorPropertie(ServicioConsultaPermisoDistintivoItv.RUTA_FICH_TEMP) + nombreFichero;
         ByteArrayOutputStream  output = null;
         try {
        	 if()
        	 List<PdfReader> readers = new ArrayList<PdfReader>();
	         int totalPages = 0;
	            
	         for(InputStream pdf : listaFicheros){
	        	 PdfReader pdfReader = new PdfReader(pdf);
		         readers.add(pdfReader);
		         totalPages += pdfReader.getNumberOfPages();
	         }
	         
	         output = new ByteArrayOutputStream();
	         PdfWriter writer = PdfWriter.getInstance(document, output);
	 
	         document.open();
	         PdfContentByte cb = writer.getDirectContent();
	 
	         PdfImportedPage page;
	         int currentPageNumber = 0;
	         int pageOfCurrentReaderPDF = 0;
	         for(PdfReader pdfReader : readers){
	        	 while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	 
	        		 Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
	        		 document.setPageSize(rectangle);
	                 document.newPage();
	 
	                 pageOfCurrentReaderPDF++;
	                 currentPageNumber++;
	                 page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
	                 switch (rectangle.getRotation()) {
	                 	case 0:
	                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
	                        break;
	                    case 90:
	                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
	                                .getPageSizeWithRotation(1).getHeight());
	                        break;
	                    case 180:
	                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
	                        break;
	                    case 270:
	                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
	                                .getPageSizeWithRotation(1).getWidth(), 0);
	                        break;
	                    default:
	                        break;
	                 }
                     cb.beginText();
                     cb.getPdfDocument().getPageSize();
                     cb.endText();
	        	 }
	             pageOfCurrentReaderPDF = 0;
	         }
	         output.flush();
	         document.close();
	         output.close();
         } catch (Exception e) {
        	 log.error("Ha sucedido un error a la hora de imprimirlos distintivos, error: ",e);
        	 resultado.setError(Boolean.TRUE);
        	 resultado.setMensaje("Ha sucedido un error a la hora de imprimirlos distintivos.");
         } finally {
        	 if (document.isOpen())
        		 document.close();
        	 	try {
	                if (output != null)
	                	output.close();
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
         }
	     if(!resultado.getError()){
	        resultado.setByteFichero(output.toByteArray());
	        resultado.setNombreFichero(nombreFichero);
	     }
	     return resultado;
	}*/
	
	
	
	
	

	public ResultadoPermisoDistintivoItvBean imprimirPermisos(List<TramiteTraficoVO> listaTramitesPermiso, Date fecha, String numColegiado, Long idDocPrm) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
		    List<FileInputStream> listaFicheros = new ArrayList<FileInputStream>();
			for(TramiteTraficoVO tramiteTraficoVO : listaTramitesPermiso){
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
						ConstantesGestorFicheros.PERMISOS_DEFINITIVO, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
						tramiteTraficoVO.getNumExpediente() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero != null && fichero.getFile() != null){
					listaFicheros.add(new FileInputStream(fichero.getFile()));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir el permiso para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
					break;
				}
			}
			if(!resultado.getError()){
				resultado = concatPdf(listaFicheros);
				if(!resultado.getError()){
					try {
						String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + idDocPrm + "_" + numColegiado;
						File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaActual(), 
								nombreFichero , ConstantesGestorFicheros.EXTENSION_PDF, resultado.getByteFichero());
						if(fichero == null){
							resultado.setError(Boolean.TRUE);
							resultado.setExcepcion(new Exception("No se ha podido guardar el pdf con los permisos"));
						}
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de guardar los permisos impresos, error: ",e);
						resultado.setError(Boolean.TRUE);
						resultado.setExcepcion(new Exception(e.getMessage()));
						resultado.setMensaje("Ha sucedido un error a la hora de guardar los permisos impresos.");
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de los permisos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de los permisos");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean imprimirPermisosNocturno(List<TramiteTraficoVO> listaTramitesPermisos,	DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			if(listaTramitesPermisos.size() > tamMaxLista){
				int totalDoc = new BigDecimal(listaTramitesPermisos.size()).divide(new BigDecimal(tamMaxLista), BigDecimal.ROUND_UP).intValue();
				List<File> listaFicheros = new ArrayList<File>();
				for(int i=0;i<totalDoc;i++){
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
							ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
							docPermDistItvVO.getIdDocPermDistItv() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum() + "_" +i ,ConstantesGestorFicheros.EXTENSION_PDF);
					if(fichero != null && fichero.getFile() != null){
						listaFicheros.add(i,fichero.getFile());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede imprimir los permiso para el docId: " + docPermDistItvVO.getDocIdPerm() + " porque no se encuentra alguno de sus pdfs.");
						break;
					}
				}
				if(!resultado.getError()){
					resultado = mergePDF(listaFicheros, nombreFichero,ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
				}
			} else {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
						ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
						docPermDistItvVO.getIdDocPermDistItv() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero == null || fichero.getFile() == null){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude imprimir el permiso para el docId: " + docPermDistItvVO.getDocIdPerm() + " porque no se encuentra su pdf.");
				} else {
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, 
							utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF,
							gestorDocumentos.transformFiletoByte(fichero.getFile()));
				}
			}
			if(!resultado.getError()){
				resultado = ordenarPdfPermFichas(listaTramitesPermisos, nombreFichero,ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de los permisos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de los permisos");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean imprimirFichasNocturno(List<TramiteTraficoVO> listaTramitesFichas,	DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			if(listaTramitesFichas.size() > tamMaxLista){
				int totalDoc = new BigDecimal(listaTramitesFichas.size()).divide(new BigDecimal(tamMaxLista), BigDecimal.ROUND_UP).intValue();
				List<File> listaFicheros = new ArrayList<File>();
				for(int i=0;i<totalDoc;i++){
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
							ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
							docPermDistItvVO.getIdDocPermDistItv() + "_" + TipoImpreso.SolicitudFichaTecnica.getNombreEnum() + "_" +i ,ConstantesGestorFicheros.EXTENSION_PDF);
					if(fichero != null && fichero.getFile() != null){
						listaFicheros.add(fichero.getFile());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede imprimir las fichas técnicas para el docId: " + docPermDistItvVO.getDocIdPerm() + " porque no se encuentra alguno de sus pdfs.");
						break;
					}
				}
				if(!resultado.getError()){
					resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
				}
			} else {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
						ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
						docPermDistItvVO.getIdDocPermDistItv() + "_" + TipoImpreso.SolicitudFichaTecnica.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero == null || fichero.getFile() == null){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude imprimir las fichas técnicas para el docId: " + docPermDistItvVO.getDocIdPerm() + " porque no se encuentra su pdf.");
				} else {
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
							nombreFichero,ConstantesGestorFicheros.EXTENSION_PDF, gestorDocumentos.transformFiletoByte(fichero.getFile()));
				}
			}
			if(!resultado.getError()){
				resultado = ordenarPdfPermFichas(listaTramitesFichas, nombreFichero,ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
						TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de las fichas técnicas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de las fichas técnicas");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean imprimirDstv(List<TramiteTrafMatrVO> listaTramitesDistintivo, List<VehNoMatOegamVO> listaDuplicados, Date fecha, DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
		    List<File> listaFicheros = new ArrayList<File>();
		    String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			if(listaTramitesDistintivo != null && !listaTramitesDistintivo.isEmpty()){
			    for(TramiteTrafMatrVO tramiteTrafMatr : listaTramitesDistintivo){
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DISTINTIVOS, 
							null, Utilidades.transformExpedienteFecha(tramiteTrafMatr.getNumExpediente()), 
							TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + tramiteTrafMatr.getNumExpediente() ,ConstantesGestorFicheros.EXTENSION_PDF);
					if(fichero != null && fichero.getFile() != null){
						listaFicheros.add(fichero.getFile());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se pude imprimir el distintivo para el expediente: " + tramiteTrafMatr.getNumExpediente() + " porque no se encuentra su pdf.");
						break;
					}
				}
			}
			if(listaDuplicados != null && !listaDuplicados.isEmpty()){
				for(VehNoMatOegamVO vehNoMatOegamVO : listaDuplicados){
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DISTINTIVOS, 
							null, utilesFecha.getFechaConDate(vehNoMatOegamVO.getFechaAlta()), 
							TipoImpreso.SolicitudDistintivo.getNombreEnum()+ "_" +vehNoMatOegamVO.getMatricula().trim() + "_" + vehNoMatOegamVO.getId() 
							,ConstantesGestorFicheros.EXTENSION_PDF);
					if(fichero != null && fichero.getFile() != null){
						listaFicheros.add(fichero.getFile());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se pude imprimir el distintivo para el duplicado: " + vehNoMatOegamVO.getMatricula() + " porque no se encuentra su pdf.");
						break;
					}
				}
			}
			if(!resultado.getError()){
				resultado = mergePDF(listaFicheros, nombreFichero,  ConstantesGestorFicheros.DISTINTIVOS, null, utilesFecha.getFechaActual());
			}
			
			if(!resultado.getError()){
				resultado = ordenarDstv(listaTramitesDistintivo, listaDuplicados, nombreFichero,ConstantesGestorFicheros.DISTINTIVOS, utilesFecha.getFechaConDate(fecha));
			}
			
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos");
		}
		
		
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean imprimirFichaTecnica(List<TramiteTraficoVO> listaTramites, Date fecha, String numColegiado, Long idDocFicha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
		    List<FileInputStream> listaFicheros = new ArrayList<FileInputStream>();
			for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
						ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
						tramiteTraficoVO.getNumExpediente() + "_" +TipoImpreso.SolicitudFichaTecnica.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero != null && fichero.getFile() != null){
					listaFicheros.add(new FileInputStream(fichero.getFile()));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir la ficha técnica para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
					break;
				}
			}
			if(!resultado.getError()){
				resultado = concatPdf(listaFicheros);
				if(!resultado.getError()){
					try {
						String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + idDocFicha + "_" + numColegiado;
						File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaActual(), 
								nombreFichero , ConstantesGestorFicheros.EXTENSION_PDF, resultado.getByteFichero());
						if(fichero == null){
							resultado.setError(Boolean.TRUE);
							resultado.setExcepcion(new Exception("No se ha podido guardar el pdf con las fichas tecnicas"));
						}
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de guardar las fichas tecnicas impresas, error: ",e);
						resultado.setError(Boolean.TRUE);
						resultado.setExcepcion(new Exception(e.getMessage()));
						resultado.setMensaje("Ha sucedido un error a la hora de guardar las fichas tecnicas impresas.");
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de las fichas técnicas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de las fichas técnicas.");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean ordenarPdfPermFichas(List<TramiteTraficoVO> listaTramites, String nombreFichero, String subtipo, Fecha fecha, String tipoDoc) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		PDDocument nuevoDoc = null;
		try {
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
					subtipo, fecha, nombreFichero,ConstantesGestorFicheros.EXTENSION_PDF);
			if(fichero != null && fichero.getFile() != null){
				String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.MATE, subtipo, Boolean.FALSE, fecha);
				PDDocument document = PDDocument.load(fichero.getFile());
				Splitter spp = new Splitter();
				spp.setStartPage(1);
				List<PDDocument> documents = spp.split(document);
				nuevoDoc =  new PDDocument();
				nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				for(TramiteTraficoVO tramiteTrafico : listaTramites){
					for(int i=0; i< documents.size();i++){
						PDDocument doc = documents.get(i);
						PDFTextStripper textoPdf = new PDFTextStripper();
						String pdf = textoPdf.getText(doc);
						if(pdf.contains(tramiteTrafico.getVehiculo().getMatricula())){
							if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc)){
								nuevoDoc.addPage(documents.get(i - 1).getPage(0));
								nuevoDoc.addPage(doc.getPage(0));
							} else {
								nuevoDoc.addPage(doc.getPage(0));
							}
							nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
							break;
						}
					}
				}
				nuevoDoc.close();
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado ningún fichero para poder ordenar su contenido.");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de ordenar el contenido del pdf.");
			if(nuevoDoc != null){
				try {
					nuevoDoc.close();
				} catch (IOException e1) {
					log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e1);
				}
			}
		}
		return resultado;
	}
	
	private ResultadoPermisoDistintivoItvBean ordenarDstvDuplicados(List<VehNoMatOegamVO> listaVehiculos, String nombreFichero, Fecha fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		PDDocument nuevoDoc = null;
		PDDocument document = null;
		try {
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DISTINTIVOS, 
					null, fecha, nombreFichero,ConstantesGestorFicheros.EXTENSION_PDF);
			if(fichero != null && fichero.getFile() != null){
				String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.DISTINTIVOS, null, Boolean.FALSE, fecha);
				document = PDDocument.load(fichero.getFile());
				Splitter spp = new Splitter();
				spp.setStartPage(1);
				List<PDDocument> documents = spp.split(document);
				nuevoDoc =  new PDDocument();
				nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				String matriConEspacioDelante = "";
				String matriConEspacioAtras = "";
				String matriConEspacioDelanteYAtras = "";
				String matriConEspacioDelanteDoble = "";
				String matriConEspacioDelanteDobleYAtras = "";
				for(VehNoMatOegamVO vehNoMatOegamVO : listaVehiculos){
					for(int i=0; i< documents.size();i++){
						PDDocument doc = documents.get(i);
						PDFTextStripper textoPdf = new PDFTextStripper();
						String pdf = textoPdf.getText(doc);
						matriConEspacioDelante = vehNoMatOegamVO.getMatricula().substring(0, 1) + " " + vehNoMatOegamVO.getMatricula().substring(1, vehNoMatOegamVO.getMatricula().length());
						matriConEspacioDelanteDoble = vehNoMatOegamVO.getMatricula().substring(0, 2) + " " + vehNoMatOegamVO.getMatricula().substring(2, vehNoMatOegamVO.getMatricula().length());
						matriConEspacioAtras = vehNoMatOegamVO.getMatricula().substring(0, vehNoMatOegamVO.getMatricula().length() - 1) + " " + vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length())  ;
						matriConEspacioDelanteYAtras = vehNoMatOegamVO.getMatricula().substring(0, 1) + " " 
						+ vehNoMatOegamVO.getMatricula().substring(1, vehNoMatOegamVO.getMatricula().length() - 1) + " "
						+ vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length());
						matriConEspacioDelanteDobleYAtras = vehNoMatOegamVO.getMatricula().substring(0, 2) + " " 
								+ vehNoMatOegamVO.getMatricula().substring(2, vehNoMatOegamVO.getMatricula().length() - 1) + " "
								+ vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length());
						if(pdf.contains(vehNoMatOegamVO.getMatricula()) 
								|| pdf.contains(matriConEspacioDelante)
								|| pdf.contains(matriConEspacioAtras)
								|| pdf.contains(matriConEspacioDelanteYAtras)
								|| pdf.contains(matriConEspacioDelanteDoble)
								|| pdf.contains(matriConEspacioDelanteDobleYAtras)){
							nuevoDoc.addPage(doc.getPage(0));
							nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
							break;
						}
					}
				}
				nuevoDoc.close();
				document.close();
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado ningún fichero para poder ordenar su contenido.");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de ordenar el contenido del pdf.");
			if(nuevoDoc != null){
				try {
					nuevoDoc.close();
				} catch (IOException e1) {
					log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e1);
				}
			}
			if(document != null){
				try {
					document.close();
				} catch (IOException e1) {
					log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e1);
				}
			}
		}
		return resultado;
	}

	
	private ResultadoPermisoDistintivoItvBean ordenarDstv(List<TramiteTrafMatrVO> listaTramitesDistintivo,	List<VehNoMatOegamVO> listaDuplicados, String nombreFichero, String subtipo, Fecha fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		PDDocument nuevoDoc = null;
		PDDocument document = null;
		try {
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DISTINTIVOS, 
					null, fecha, nombreFichero,ConstantesGestorFicheros.EXTENSION_PDF);
			if(fichero != null && fichero.getFile() != null){
				String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.DISTINTIVOS, null, Boolean.FALSE, fecha);
				document = PDDocument.load(fichero.getFile());
				Splitter spp = new Splitter();
				spp.setStartPage(1);
				List<PDDocument> documents = spp.split(document);
				nuevoDoc =  new PDDocument();
				nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				String matriConEspacioDelante = "";
				String matriConEspacioAtras = "";
				String matriConEspacioDelanteYAtras = "";
				String matriConEspacioDelanteDoble = "";
				String matriConEspacioDelanteDobleYAtras = "";
				if(listaTramitesDistintivo != null && !listaTramitesDistintivo.isEmpty()){
					for(TramiteTrafMatrVO tramiteTrafico : listaTramitesDistintivo){
						for(int i=0; i< documents.size();i++){
							PDDocument doc = documents.get(i);
							PDFTextStripper textoPdf = new PDFTextStripper();
							String pdf = textoPdf.getText(doc);
							matriConEspacioDelante = tramiteTrafico.getVehiculo().getMatricula().substring(0, 1) + " " + tramiteTrafico.getVehiculo().getMatricula().substring(1, tramiteTrafico.getVehiculo().getMatricula().length());
							matriConEspacioDelanteDoble = tramiteTrafico.getVehiculo().getMatricula().substring(0, 2) + " " + tramiteTrafico.getVehiculo().getMatricula().substring(2, tramiteTrafico.getVehiculo().getMatricula().length());
							matriConEspacioAtras = tramiteTrafico.getVehiculo().getMatricula().substring(0, tramiteTrafico.getVehiculo().getMatricula().length() - 1) + " " + tramiteTrafico.getVehiculo().getMatricula().substring(tramiteTrafico.getVehiculo().getMatricula().length() - 1, tramiteTrafico.getVehiculo().getMatricula().length())  ;
							matriConEspacioDelanteYAtras = tramiteTrafico.getVehiculo().getMatricula().substring(0, 1) + " " 
							+ tramiteTrafico.getVehiculo().getMatricula().substring(1, tramiteTrafico.getVehiculo().getMatricula().length() - 1) + " "
							+ tramiteTrafico.getVehiculo().getMatricula().substring(tramiteTrafico.getVehiculo().getMatricula().length() - 1, tramiteTrafico.getVehiculo().getMatricula().length());
							matriConEspacioDelanteDobleYAtras = tramiteTrafico.getVehiculo().getMatricula().substring(0, 2) + " " 
									+ tramiteTrafico.getVehiculo().getMatricula().substring(2, tramiteTrafico.getVehiculo().getMatricula().length() - 1) + " "
									+ tramiteTrafico.getVehiculo().getMatricula().substring(tramiteTrafico.getVehiculo().getMatricula().length() - 1, tramiteTrafico.getVehiculo().getMatricula().length());
							if((pdf.contains(tramiteTrafico.getVehiculo().getMatricula().trim())) 
									|| (pdf.contains(matriConEspacioDelante))
									|| (pdf.contains(matriConEspacioAtras))
									|| (pdf.contains(matriConEspacioDelanteYAtras))
									|| (pdf.contains(matriConEspacioDelanteDoble))
									|| (pdf.contains(matriConEspacioDelanteDobleYAtras))){
								nuevoDoc.addPage(doc.getPage(0));
								nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
								break;
							}
						}
					}
				}
				if(listaDuplicados != null && !listaDuplicados.isEmpty()){
					for(VehNoMatOegamVO vehNoMatOegamVO : listaDuplicados){
						for(int i=0; i< documents.size();i++){
							PDDocument doc = documents.get(i);
							PDFTextStripper textoPdf = new PDFTextStripper();
							String pdf = textoPdf.getText(doc);
							matriConEspacioDelante = vehNoMatOegamVO.getMatricula().substring(0, 1) + " " + vehNoMatOegamVO.getMatricula().substring(1, vehNoMatOegamVO.getMatricula().length());
							matriConEspacioDelanteDoble = vehNoMatOegamVO.getMatricula().substring(0, 2) + " " + vehNoMatOegamVO.getMatricula().substring(2, vehNoMatOegamVO.getMatricula().length());
							matriConEspacioAtras = vehNoMatOegamVO.getMatricula().substring(0, vehNoMatOegamVO.getMatricula().length() - 1) + " " + vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length())  ;
							matriConEspacioDelanteYAtras = vehNoMatOegamVO.getMatricula().substring(0, 1) + " " 
							+ vehNoMatOegamVO.getMatricula().substring(1, vehNoMatOegamVO.getMatricula().length() - 1) + " "
							+ vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length());
							matriConEspacioDelanteDobleYAtras = vehNoMatOegamVO.getMatricula().substring(0, 2) + " " 
									+ vehNoMatOegamVO.getMatricula().substring(2, vehNoMatOegamVO.getMatricula().length() - 1) + " "
									+ vehNoMatOegamVO.getMatricula().substring(vehNoMatOegamVO.getMatricula().length() - 1, vehNoMatOegamVO.getMatricula().length());
							if((pdf.contains(vehNoMatOegamVO.getMatricula().trim())) 
									|| (pdf.contains(matriConEspacioDelante))
									|| (pdf.contains(matriConEspacioAtras))
									|| (pdf.contains(matriConEspacioDelanteYAtras))
									|| (pdf.contains(matriConEspacioDelanteDoble))
									|| (pdf.contains(matriConEspacioDelanteDobleYAtras))){
								nuevoDoc.addPage(doc.getPage(0));
								nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
								break;
							}
						}
					}
				}
				nuevoDoc.close();
				document.close();
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado ningún fichero para poder ordenar su contenido.");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de ordenar el contenido del pdf.");
			if(nuevoDoc != null){
				try {
					nuevoDoc.close();
				} catch (IOException e1) {
					log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e1);
				}
			}
			if(document != null){
				try {
					document.close();
				} catch (IOException e1) {
					log.error("Ha sucedido un error a la hora de ordenar el contenido del pdf, error: ",e1);
				}
			}
		}
		return resultado;
	}
	
	
	private ResultadoPermisoDistintivoItvBean mergePDF(List<File> listaFicheros, String nombreFichero, String tipo, String subtipo, Fecha fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String ruta = gestorDocumentos.obtenerRutaFichero(tipo,subtipo,Boolean.FALSE, fecha);
			if(ruta != null && !ruta.isEmpty()){
				PDFMergerUtility pdfMerge = new PDFMergerUtility();
				pdfMerge.setDestinationFileName(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				for(File fichPdf : listaFicheros){
					pdfMerge.addSource(fichPdf);
				}
				pdfMerge.mergeDocuments(null);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido determinar la ruta para guardar los PDF's.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar el merge de los PDF, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el merge de los PDF.");
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean imprimirDuplicadosDstv(List<VehNoMatOegamVO> listaVehiculos, DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
		    List<File> listaFicheros = new ArrayList<File>();
		    String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" 
		    		+ docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
		    for(VehNoMatOegamVO vehNoMatOegamVO : listaVehiculos){
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DISTINTIVOS, 
						null, utilesFecha.getFechaConDate(vehNoMatOegamVO.getFechaAlta()), 
						TipoImpreso.SolicitudDistintivo.getNombreEnum()+ "_" +vehNoMatOegamVO.getMatricula() + "_" + vehNoMatOegamVO.getId()  
						,ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero != null && fichero.getFile() != null){
					listaFicheros.add(fichero.getFile());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude imprimir el distintivo para el vehiculo: " + vehNoMatOegamVO.getMatricula() + " porque no se encuentra su pdf.");
					break;
				}
			}
			if(!resultado.getError()){
				resultado = mergePDF(listaFicheros, nombreFichero,  ConstantesGestorFicheros.DISTINTIVOS,null, utilesFecha.getFechaActual());
			}
			
			if(!resultado.getError()){
				resultado = ordenarDstvDuplicados(listaVehiculos, nombreFichero, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el fichero para la impresión de los distintivos");
		}
		return resultado;
	}

}
