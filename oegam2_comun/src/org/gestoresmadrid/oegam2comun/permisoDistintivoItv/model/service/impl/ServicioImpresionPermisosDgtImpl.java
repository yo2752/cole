package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpresionPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionPermisosDgtImpl implements ServicioImpresionPermisosDgt{

	private static final long serialVersionUID = -7692777203974345286L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionPermisosDgtImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrm;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioPermisosDgt servicioPermisosDgt;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public void actualizarEstado(BigDecimal idDoc, EstadoPermisoDistintivoItv estado, String resultadoEjecucion, BigDecimal idUsuario) {
		Boolean error = Boolean.FALSE;
		try {
			if(idDoc != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(idDoc.longValue(),Boolean.FALSE);
				if(docPermDistItvVO != null){
					if(EstadoPermisoDistintivoItv.Generado.getValorEnum().equals(estado.getValorEnum())){
						servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvVO, estado, idUsuario, OperacionPrmDstvFicha.GENERADO);
					} else {
						servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvVO, estado, idUsuario, OperacionPrmDstvFicha.ERROR_GENERADO);
					}
				}else{
					log.error("No se encuentran datos del docPermDstv para actualizar");
					error = Boolean.TRUE;
				}
			}else{
				log.error("Debe de indicar un id del DocPermDstv para actualizar");
				error = Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado del docPermDstv, error: ", e);
			error = Boolean.TRUE;
		}
		if(error){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean impresionPermisoDgt(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(solicitud != null && solicitud.getIdTramite() != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(),Boolean.FALSE);
				List<TramiteTraficoVO> listaTramitesSinPermiso = null;
				List<TramiteTraficoVO> listaTramitesConPermiso = null;
				List<TramiteTraficoVO> listaTramites  = null;
				if(docPermDistItvVO != null){
					if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
						listaTramites = servicioPermisosDgt.getListaTramitesPorDocId(docPermDistItvVO.getIdDocPermDistItv());
						if(listaTramites != null){
							if("S".equals(docPermDistItvVO.getEsDemanda())){
								resultado = imprimirPermisos(listaTramites, docPermDistItvVO, Boolean.TRUE);
							} else {
								resultado = imprimirPermisos(listaTramites,docPermDistItvVO, Boolean.FALSE);
							}
							if(!resultado.getError()){
								listaTramitesSinPermiso = resultado.getListaTramitesSinPermiso();
								listaTramitesConPermiso = resultado.getListaTramitesConPermiso();
							}
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se ha podido recuperar la lista de trámites por id para imprimir los permisos.");
						}
					}else{
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existe tipo de documento para imprimir.");
					}
					if(!resultado.getError()){
						Date fecha = new Date();
						if(listaTramitesConPermiso != null && !listaTramitesConPermiso.isEmpty()){
							List<TramitesPermDistItvBean> listaTramitesPdf = obtenerListaTramitesPdf(listaTramitesConPermiso, docPermDistItvVO.getTipo());
							resultado = servicioDocPrmDstvFicha.generarDocGestoriaPrmFichaProceso(listaTramitesPdf, docPermDistItvVO, null, Boolean.FALSE, solicitud.getXmlEnviar(), listaTramites.get(0).getTipoTramite());
							if(!resultado.getError()){
								for(TramiteTraficoVO traficoVO : listaTramitesConPermiso){
									String estadoAnt = traficoVO.getEstadoImpPerm();
									traficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
									traficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
									if(traficoVO.getNumImpresionesPerm() == null || traficoVO.getNumImpresionesPerm() == 0){
										traficoVO.setNumImpresionesPerm(new Long(1));
									} else {
										traficoVO.setNumImpresionesPerm(traficoVO.getNumImpresionesPerm() + new Long(1));
									}
									servicioTramiteTrafico.actualizarTramite(traficoVO);
									servicioEvolucionPrm.guardarEvolucion(traficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
											TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
											estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(),docPermDistItvVO.getDocIdPerm(),"proceso");
								}
								if(listaTramitesSinPermiso != null && !listaTramitesSinPermiso.isEmpty()){
									if(!resultado.getError()){
										for(TramiteTraficoVO traficoVO : listaTramitesSinPermiso){
											String estadoAnt = traficoVO.getEstadoImpPerm();
											traficoVO.setDocPermiso(null);
											traficoVO.setDocPermisoVO(null);
											traficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
											traficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
											servicioTramiteTrafico.actualizarTramite(traficoVO);
											servicioEvolucionPrm.guardarEvolucion(traficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
													TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
													estadoAnt, EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(),docPermDistItvVO.getDocIdPerm(),"proceso");
										}
									}
								}
							}
						} else {
							if(listaTramitesSinPermiso != null && !listaTramitesSinPermiso.isEmpty()){
								if(!resultado.getError()){
									for(TramiteTraficoVO traficoVO : listaTramitesSinPermiso){
										String estadoAnt = traficoVO.getEstadoImpPerm();
										traficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
										traficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
										servicioTramiteTrafico.actualizarTramite(traficoVO);
										servicioEvolucionPrm.guardarEvolucion(traficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
												TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
												estadoAnt, EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(),docPermDistItvVO.getDocIdPerm(),"proceso");
									}
								}
							}
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No existen permisos para poder ordenarlos.");
						}
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para poder realizar la petición.");
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La cola o el numero del expediente de la solicitud esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir la solicitud de permisos o fichas técnicas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir la solicitud de permisos o fichas técnicas.");
		}
		return resultado;
	}
	
	private List<TramitesPermDistItvBean> obtenerListaTramitesPdf(List<TramiteTraficoVO> listaTramites, String tipoDocumento) {
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<TramitesPermDistItvBean>();
		int cont = 1;
		int i = 0;
		for(TramiteTraficoVO tramiteBBDD : listaTramites){
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setPc("OK");
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));
			listaTramitesBean.add(i++,tramitesPermDistItvBean);
		}
		
		return listaTramitesBean;
	}

	
	public ResultadoPermisoDistintivoItvBean imprimirPermisos(List<TramiteTraficoVO> listaTramitesPermisos,	DocPermDistItvVO docPermDistItvVO, Boolean esDemanda) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			List<File> listaFicheros = new ArrayList<File>();
			if(esDemanda){
				int i=0;
			    for(TramiteTraficoVO tramiteTraficoVO : listaTramitesPermisos){
			    	if("2631".equals(tramiteTraficoVO.getNumColegiado())) {
			    		FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipoAM(ConstantesGestorFicheros.MATE, 
								ConstantesGestorFicheros.PERMISOS_DEFINITIVO, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
								tramiteTraficoVO.getNumExpediente() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							listaFicheros.add(i++,fichero.getFile());
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se puede imprimir el permiso para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
							break;
						}
			    	}else {
			    		FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
								ConstantesGestorFicheros.PERMISOS_DEFINITIVO, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
								tramiteTraficoVO.getNumExpediente() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							listaFicheros.add(i++,fichero.getFile());
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se puede imprimir el permiso para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
							break;
						}
			    	}
					
				}
				if(!resultado.getError()){
					resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
				}
			} else {
				if(listaTramitesPermisos.size() > tamMaxLista){
					int totalDoc = new BigDecimal(listaTramitesPermisos.size()).divide(new BigDecimal(tamMaxLista), BigDecimal.ROUND_UP).intValue();
					
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
						resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
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
	
	public ResultadoPermisoDistintivoItvBean ordenarPdfPermFichas(List<TramiteTraficoVO> listaTramites, String nombreFichero, String subtipo, Fecha fecha, String tipoDoc) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		PDDocument nuevoDoc = null;
		PDDocument document = null;
		try {
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
					subtipo, fecha, nombreFichero,ConstantesGestorFicheros.EXTENSION_PDF);
			if(fichero != null && fichero.getFile() != null){
				String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.MATE, subtipo, Boolean.FALSE, fecha);
				document = PDDocument.load(fichero.getFile());
				Splitter spp = new Splitter();
				spp.setStartPage(1);
				List<PDDocument> documents = spp.split(document);
				nuevoDoc =  new PDDocument();
				nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				Boolean existeMatricula = Boolean.FALSE;
				String matriConEspacioDelante = "";
				String matriConEspacioAtras = "";
				String matriConEspacioDelanteYAtras = "";
				String matriConEspacioDelanteDoble = "";
				String matriConEspacioDelanteDobleYAtras = "";
				String matriCambioProvinciales = "";
				for(TramiteTraficoVO tramiteTrafico : listaTramites){
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
						//matriCambioProvinciales = cambioProvinciales(tramiteTrafico.getVehiculo().getMatricula());
						if(pdf.contains(tramiteTrafico.getVehiculo().getMatricula()) 
								|| pdf.contains(matriConEspacioDelante)
								|| pdf.contains(matriConEspacioAtras)
								|| pdf.contains(matriConEspacioDelanteYAtras)
								|| pdf.contains(matriConEspacioDelanteDoble)
								|| pdf.contains(matriConEspacioDelanteDobleYAtras)){
								//|| pdf.contains(matriCambioProvinciales)){
							existeMatricula = Boolean.TRUE;
							nuevoDoc.addPage(doc.getPage(0));
							nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
							break;
						}
					}
					if(!existeMatricula){
						resultado.addListaTramiteSinPermiso(tramiteTrafico);
					} else {
						resultado.addListaTramiteConPermiso(tramiteTrafico);
						existeMatricula = Boolean.FALSE;
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
	
	private String cambioProvinciales(String matricula) {
		String matriculaProvincial = "";
		String provincia = matricula.substring(0, 2);
		if ("IB".equals(provincia)) {
			matriculaProvincial = "PM" + matricula.substring(2);
		} else if ("PM".equals(provincia)) {
			matriculaProvincial = "IB" + matricula.substring(2);
		} else if ("GI".equals(provincia)) {
			matriculaProvincial = "GE" + matricula.substring(2);
		} else if ("GE".equals(provincia)) {
			matriculaProvincial = "GI" + matricula.substring(2);
		} else if ("OR".equals(provincia)) {
			matriculaProvincial = "OU" + matricula.substring(2);
		} else if ("OU".equals(provincia)) {
			matriculaProvincial = "OR" + matricula.substring(2);
		}
		return matriculaProvincial;
	}
	
	private ResultadoPermisoDistintivoItvBean mergePDF(List<File> listaFicheros, String nombreFichero, String subtipo, Fecha fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.MATE, subtipo,Boolean.FALSE, fecha);
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

}
