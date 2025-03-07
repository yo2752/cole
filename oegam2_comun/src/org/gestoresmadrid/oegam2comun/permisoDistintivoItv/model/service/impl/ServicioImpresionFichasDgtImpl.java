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
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.ServicioEvolucionDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpresionFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
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
public class ServicioImpresionFichasDgtImpl implements ServicioImpresionFichasDgt{

	private static final long serialVersionUID = -3271169238346757487L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionFichasDgtImpl.class);

	@Autowired
	ServicioEvolucionDocPrmDstvFicha servicioEvolucionDocPrmDstvFicha;
	
	@Autowired
	ServicioFichasTecnicasDgt servicioFichasTecnicasDgt;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrm;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;
	
	@Autowired
	ServicioCola servicioCola;
	
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
	public ResultadoPermisoDistintivoItvBean impresionFichasDgt(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String ip = getIpConexion();
			if(solicitud != null && solicitud.getIdTramite() != null){
				String xmlEnviarPermisos = null;
				if(solicitud.getXmlEnviar() != null && !solicitud.getXmlEnviar().isEmpty()){
					xmlEnviarPermisos = solicitud.getXmlEnviar().split("_")[0] + "_" + ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
				}
				if(!servicioCola.comprobarColasProcesoActivas(ProcesosEnum.IMP_PRM_MATW.getNombreEnum(), solicitud.getIdContrato(), xmlEnviarPermisos)){
					DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(),Boolean.FALSE);
					List<TramiteTraficoVO> listaTramitesSinFicha = null;
					List<TramiteTraficoVO> listaTramitesConFicha = null;
					List<TramiteTraficoVO> listaTramites  = null;
					if(docPermDistItvVO != null){
						if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
							listaTramites = servicioFichasTecnicasDgt.getListaTramitesPorDocId(docPermDistItvVO.getIdDocPermDistItv());
							if(listaTramites != null){
								if("S".equals(docPermDistItvVO.getEsDemanda())){
									resultado = imprimirFichasTecnicas(listaTramites, docPermDistItvVO, Boolean.TRUE);
								} else {
									resultado = imprimirFichasTecnicas(listaTramites, docPermDistItvVO, Boolean.FALSE);
								}
								if(!resultado.getError()) {
									listaTramitesSinFicha = resultado.getListaTramitesSinFicha();
									listaTramitesConFicha = resultado.getListaTramitesConFicha();
								}
							}else{
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("No se ha podido recuperar la lista de trámites por id para imprimir las fichas.");
							}
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No existe tipo de documento para imprimir.");
						}
						if(!resultado.getError()){
							Date fecha = new Date();
							if(listaTramitesConFicha != null && !listaTramitesConFicha.isEmpty()){
								List<TramitesPermDistItvBean> listaTramitesPdf = obtenerListaTramitesPdf(listaTramitesConFicha, docPermDistItvVO.getTipo());
								resultado = servicioDocPrmDstvFicha.generarDocGestoriaPrmFichaProceso(listaTramitesPdf, docPermDistItvVO, null, Boolean.FALSE, solicitud.getXmlEnviar(), listaTramites.get(0).getTipoTramite());
								if(!resultado.getError()){
									for(TramiteTraficoVO tramiteTraficoVO : listaTramitesConFicha){
										String estadoAnt = tramiteTraficoVO.getEstadoImpFicha();
										tramiteTraficoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
										tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
										if(tramiteTraficoVO.getNumImpresionesFicha() == null || tramiteTraficoVO.getNumImpresionesFicha() == 0){
											tramiteTraficoVO.setNumImpresionesFicha(new Long(1));
										} else {
											tramiteTraficoVO.setNumImpresionesFicha(tramiteTraficoVO.getNumImpresionesFicha() + new Long(1));
										}
										servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
										servicioEvolucionPrm.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
												TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
												estadoAnt, EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum(),docPermDistItvVO.getDocIdPerm(),ip);
									}
									
									if(listaTramitesSinFicha != null && !listaTramitesSinFicha.isEmpty()){
										for(TramiteTraficoVO tramiteTraficoVO : listaTramitesSinFicha){
											String estadoAnt = tramiteTraficoVO.getEstadoImpPerm();
											tramiteTraficoVO.setDocFichaTecnica(null);
											tramiteTraficoVO.setDocFichaTecnicaVO(null);
											tramiteTraficoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
											tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
											servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
											
											servicioEvolucionPrm.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
													TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
													estadoAnt, EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(),docPermDistItvVO.getDocIdPerm(),ip);
										}
									}
								}
							} else {
								if(listaTramitesSinFicha != null && !listaTramitesSinFicha.isEmpty()){
									for(TramiteTraficoVO tramiteTraficoVO : listaTramitesSinFicha){
										String estadoAnt = tramiteTraficoVO.getEstadoImpPerm();
										tramiteTraficoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
										tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
										servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
										servicioEvolucionPrm.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), solicitud.getIdUsuario(), 
												TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
												estadoAnt, EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(),docPermDistItvVO.getDocIdPerm(),ip);
									}
								}	
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("No existen fichas para poder ordenarlos.");
							}
						}
					} else {
						resultado.setExisteImprPermisosMatw(Boolean.TRUE);
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
			log.error("Ha sucedido un error a la hora de imprimir la solicitud de fichas técnicas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir la solicitud de fichas técnicas.");
		}
		return resultado;
	}
	
	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();
		
		 if(ServletActionContext.getRequest().getHeader("client-ip") != null){
			 ipConexion = ServletActionContext.getRequest().getHeader("client-ip"); 
		 }
		 
		return ipConexion;
	}
	
	private List<TramitesPermDistItvBean> obtenerListaTramitesPdf(List<TramiteTraficoVO> listaTramites,String tipoDocumento) {
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<TramitesPermDistItvBean>();
		int cont = 1;
		int i = 0;
		for(TramiteTraficoVO tramiteBBDD : listaTramites){
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setEitv("OK");
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));
			listaTramitesBean.add(i++,tramitesPermDistItvBean);
		}
		return listaTramitesBean;
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
						if((pdf.contains(tramiteTrafico.getVehiculo().getMatricula()) || pdf.contains(matriConEspacioDelante)
								|| pdf.contains(matriConEspacioAtras)
								|| pdf.contains(matriConEspacioDelanteYAtras)
								|| pdf.contains(matriConEspacioDelanteDoble)
								|| pdf.contains(matriConEspacioDelanteDobleYAtras))
								&& (tramiteTrafico.getVehiculo().getNive() == null || pdf.contains(tramiteTrafico.getVehiculo().getNive().toUpperCase()))){
							existeMatricula = Boolean.TRUE;
							nuevoDoc.addPage(documents.get(i - 1).getPage(0));
							nuevoDoc.addPage(doc.getPage(0));
							nuevoDoc.save(ruta + nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
							break;
						}
					}
					if(!existeMatricula){
						resultado.addListaTramiteSinFicha(tramiteTrafico);
					} else {
						resultado.addListaTramiteConFicha(tramiteTrafico);
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
	
	public ResultadoPermisoDistintivoItvBean imprimirFichasTecnicas(List<TramiteTraficoVO> listaTramitesFichas,	DocPermDistItvVO docPermDistItvVO, Boolean esDemanda) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			List<File> listaFicheros = new ArrayList<File>();
			if(esDemanda){
				int i= 0;
				for(TramiteTraficoVO tramiteTraficoVO : listaTramitesFichas){
					if("2631".equals(tramiteTraficoVO.getNumColegiado())) {
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipoAM(ConstantesGestorFicheros.MATE, 
								ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
								tramiteTraficoVO.getNumExpediente() + "_" +TipoImpreso.SolicitudFichaTecnica.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							listaFicheros.add(i++,fichero.getFile());
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se puede imprimir la ficha técnica para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
							break;
						}
					}else {
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
								ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()), 
								tramiteTraficoVO.getNumExpediente() + "_" +TipoImpreso.SolicitudFichaTecnica.getNombreEnum() ,ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							listaFicheros.add(i++,fichero.getFile());
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se puede imprimir la ficha técnica para el expediente: " + tramiteTraficoVO.getNumExpediente() + " porque no se encuentra su pdf.");
							break;
						}
					}
					
				}
				if(!resultado.getError()){
					resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
				}
			} else {
				if(listaTramitesFichas.size() > tamMaxLista){
					int totalDoc = new BigDecimal(listaTramitesFichas.size()).divide(new BigDecimal(tamMaxLista), BigDecimal.ROUND_UP).intValue();
					
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
						resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
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
