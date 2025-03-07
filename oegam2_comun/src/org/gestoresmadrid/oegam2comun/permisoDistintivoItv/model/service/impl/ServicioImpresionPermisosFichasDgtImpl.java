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
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.ServicioEvolucionDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpresionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
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
public class ServicioImpresionPermisosFichasDgtImpl implements ServicioImpresionPermisosFichasDgt{

	private static final long serialVersionUID = -8319253246484853721L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionPermisosFichasDgtImpl.class);

	@Autowired
	ServicioEvolucionDocPrmDstvFicha servicioEvolucionDocPrmDstvFicha;
	
	@Autowired
	ServicioPermisosDgt servicioPermisosDgt;
	
	@Autowired
	ServicioFichasTecnicasDgt servicioFichasTecnicasDgt;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioGestionImpr servicioGestionImpr; 
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrm;
	
	@Autowired 
	Conversor conversor;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;
	
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
	public ResultadoPermisoDistintivoItvBean impresionPermisoFichas(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(solicitud != null && solicitud.getIdTramite() != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(),Boolean.FALSE);
				List<TramiteTraficoVO> listaTramitesSinFicha = null;
				List<TramiteTraficoVO> listaTramitesSinPermiso = null;
				List<TramiteTraficoVO> listaTramites  = null;
				if(docPermDistItvVO != null){
					if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
						listaTramites = servicioPermisosDgt.getListaTramitesPorDocId(docPermDistItvVO.getIdDocPermDistItv());
						if(listaTramites != null){
							if("S".equals(docPermDistItvVO.getEsDemanda())){
								resultado = imprimirPermisos(listaTramites, docPermDistItvVO, Boolean.TRUE);
							} else {
								resultado = imprimirPermisos(listaTramites,docPermDistItvVO, Boolean.FALSE);
								if(!resultado.getError()){
									listaTramitesSinPermiso = resultado.getListaTramitesSinPermiso();
								}
							}
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se ha podido recuperar la lista de trámites por id para imprimir los permisos.");
						}
					}else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
						listaTramites = servicioFichasTecnicasDgt.getListaTramitesPorDocId(docPermDistItvVO.getIdDocPermDistItv());
						if(listaTramites != null){
							if("S".equals(docPermDistItvVO.getEsDemanda())){
								resultado = imprimirFichasTecnicas(listaTramites, docPermDistItvVO, Boolean.TRUE);
							} else {
								resultado = imprimirFichasTecnicas(listaTramites, docPermDistItvVO, Boolean.FALSE);
								if(!resultado.getError()) {
									listaTramitesSinFicha = resultado.getListaTramitesSinFicha();
								}
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
						List<TramitesPermDistItvBean> listaTramitesPdf = obtenerListaTramitesPdf(listaTramites, listaTramitesSinFicha, listaTramitesSinPermiso, docPermDistItvVO.getTipo(), solicitud.getXmlEnviar());
						resultado = servicioDocPrmDstvFicha.generarDocGestoriaPrmFichaProceso(listaTramitesPdf, docPermDistItvVO, null, Boolean.FALSE, solicitud.getXmlEnviar(), listaTramites.get(0).getTipoTramite());
						if(!resultado.getError()){
							Date fecha = new Date();	
							if((listaTramitesSinFicha != null && !listaTramitesSinFicha.isEmpty()) 
										|| (listaTramitesSinPermiso != null && !listaTramitesSinPermiso.isEmpty())){
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
												estadoAnt, EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(),docPermDistItvVO.getDocIdPerm(),"proceso");
									}
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
									//String datos = TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum() + "_" + docPermDistItvVO.getIdDocPermDistItv() + "_" + listaTramitesSinPermiso.get(0).getTipoTramite() + "_" + solicitud.getXmlEnviar();
									//servicioGestionImpr.crearPeticionImpr(contrato, ProcesosEnum.IMPR_ERRORES.getNombreEnum(),datos);
									}
								}
							}
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
	
	@Override
	public ResultadoPermisoDistintivoItvBean impresionPermisoFichasErroneos(List<byte[]> listaPdf, List<TramiteTraficoVO> listaTramites, DocPermDistItvVO docPermDistItvNuevo, String tipoTramite, ContratoVO contrato, DocPermDistItvVO docPermDistItvAnterior, String esConNive) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> tramitesSinDoc = null;
			List<TramiteTraficoVO> tramitesConDoc = null;
			resultado = imprimirPermisosError(listaPdf,listaTramites,docPermDistItvNuevo, contrato);
			Boolean existeTramOK = Boolean.FALSE;
			Boolean existeTramKO = Boolean.FALSE;
			if(!resultado.getError()){
				tramitesSinDoc = resultado.getListaTramitesSinPermiso();
				tramitesConDoc = resultado.getListaTramitesConPermiso();
				if(tramitesSinDoc != null && !tramitesSinDoc.isEmpty()){
					existeTramKO = Boolean.TRUE;
					for(TramiteTraficoVO tramiteTraficoVO : tramitesSinDoc){
						tramiteTraficoVO.setDocPermiso(null);
						tramiteTraficoVO.setDocPermisoVO(null);
						tramiteTraficoVO.setExcelKoImpr("N");
						tramiteTraficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
						servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
								new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()),
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.ERROR_GENERADO, new Date(),EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(), 
								EstadoPermisoDistintivoItv.Iniciado.getValorEnum(),null,"proceso");
					}
				}
				if(tramitesConDoc != null && !tramitesConDoc.isEmpty()){
					existeTramOK = Boolean.TRUE;
					List<TramitesPermDistItvBean> listaTramitesPdf = null;
					String docId = null;
					if(existeTramKO){
						docId = docPermDistItvNuevo.getDocIdPerm();
						listaTramitesPdf = listaTramitesPdfError(tramitesConDoc,docPermDistItvNuevo.getTipo(), esConNive);
						servicioDocPrmDstvFicha.generarDocGestoriaPrmFichaProceso(listaTramitesPdf, docPermDistItvNuevo, contrato, Boolean.TRUE, esConNive, tramitesConDoc.get(0).getTipoTramite());
					} else {
						docId = docPermDistItvAnterior.getDocIdPerm();
						listaTramitesPdf = listaTramitesPdfError(tramitesConDoc,docPermDistItvNuevo.getTipo(), esConNive);
						servicioDocPrmDstvFicha.generarDocGestoriaPrmFichaProceso(listaTramitesPdf, docPermDistItvAnterior, contrato, Boolean.TRUE, esConNive, tramitesConDoc.get(0).getTipoTramite());
					}
					for(TramiteTraficoVO tramiteTraficoVO : tramitesConDoc){
						String estadoAnt = tramiteTraficoVO.getEstadoSolPerm();
						tramiteTraficoVO.setPermiso("S");
						if(existeTramKO){
							tramiteTraficoVO.setDocPermiso(docPermDistItvNuevo.getIdDocPermDistItv());
						}
						tramiteTraficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
						tramiteTraficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
						servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
								new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()),
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.SOLICITUD, new Date(),estadoAnt, 
								EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum(),null,"proceso");
						servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
								new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()), 
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOL_IMPRESION, new Date(), 
								EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(),docId,"proceso");
					}
				}
				
				if(!existeTramOK){
					gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO,utilesFecha.getFechaConDate(docPermDistItvNuevo.getFechaAlta()), 
							NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvNuevo.getIdDocPermDistItv() + "_" + contrato.getColegiado().getNumColegiado());
					servicioDocPrmDstvFicha.borrarDoc(docPermDistItvNuevo);
					servicioDocPrmDstvFicha.generarDocGestoriaErroresTramites(tramitesSinDoc, docPermDistItvAnterior, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, tipoTramite,contrato, esConNive);
					servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvAnterior, EstadoPermisoDistintivoItv.Generado, new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()),OperacionPrmDstvFicha.GENERADO);
				} else {
					servicioDocPrmDstvFicha.generarDocGestoriaErroresTramites(tramitesSinDoc, docPermDistItvNuevo, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, tipoTramite,contrato, esConNive);
					servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvNuevo, EstadoPermisoDistintivoItv.Generado, new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()),OperacionPrmDstvFicha.GENERADO);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir la solicitud de permisos o fichas técnicas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir la solicitud de permisos o fichas técnicas.");
		}
		return resultado;
	}
	
	private List<TramitesPermDistItvBean> listaTramitesPdfError(List<TramiteTraficoVO> tramitesConDoc, String tipo, String esSinNive) {
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<TramitesPermDistItvBean>();
		int cont = 1;
		int i = 0;
		for(TramiteTraficoVO tramite : tramitesConDoc){
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipo)){
				tramitesPermDistItvBean.setPc("OK");
				if("SNV".equals(esSinNive)){
					tramitesPermDistItvBean.setEitv("PDE");
				}
			} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipo)){
				tramitesPermDistItvBean.setEitv("OK");
			}
			tramitesPermDistItvBean.setMatricula(tramite.getVehiculo().getMatricula());
			if("SNV".equals(esSinNive)){
				tramitesPermDistItvBean.setBastidor(tramite.getVehiculo().getBastidor());
			}
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramite.getNumExpediente().toString());
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramite.getFechaPresentacion()));
			listaTramitesBean.add(i++,tramitesPermDistItvBean);
		}
		return listaTramitesBean;
	}
	
	private List<TramitesPermDistItvBean> obtenerListaTramitesPdf(List<TramiteTraficoVO> listaTramites,List<TramiteTraficoVO> listaTramitesSinFicha, List<TramiteTraficoVO> listaTramitesSinPermiso, String tipoDocumento, String esConNive) {
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<TramitesPermDistItvBean>();
		int cont = 1;
		int i = 0;
		Boolean existePerm = Boolean.TRUE;
		Boolean existeFicha = Boolean.TRUE;
		for(TramiteTraficoVO tramiteBBDD : listaTramites){
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento)){
				existePerm = Boolean.FALSE;
				existeFicha = Boolean.TRUE;
				if(listaTramitesSinFicha != null && !listaTramitesSinFicha.isEmpty()){
					for(TramiteTraficoVO tramiteTrafPermiso : listaTramitesSinFicha){
						if(tramiteBBDD.getNumExpediente().toString().equals(tramiteTrafPermiso.getNumExpediente().toString())) {
							existeFicha = Boolean.FALSE;
							break;
						}
					}
				}
				if(existeFicha){
					tramitesPermDistItvBean.setEitv("OK");
				}
			} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento)){
				existePerm = Boolean.TRUE;
				existeFicha = Boolean.FALSE;
				if(listaTramitesSinPermiso != null && !listaTramitesSinPermiso.isEmpty()){
					for(TramiteTraficoVO tramiteTrafPermiso : listaTramitesSinPermiso){
						if(tramiteBBDD.getNumExpediente().toString().equals(tramiteTrafPermiso.getNumExpediente().toString())) {
							existePerm = Boolean.FALSE;
							break;
						}
					}
				}
				if(existePerm){
					tramitesPermDistItvBean.setPc("OK");
				}
			}
			if(existeFicha || existePerm){
				tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
				tramitesPermDistItvBean.setNumero(cont++);
				tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
				tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));
				listaTramitesBean.add(i++,tramitesPermDistItvBean);
			}
		}
		
		return listaTramitesBean;
	}

	private ResultadoPermisoDistintivoItvBean imprimirPermisosError(List<byte[]> listaPdf, List<TramiteTraficoVO> listaTramites, DocPermDistItvVO docPermDistItvVO, ContratoVO contrato) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		String nombreFichero = NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + contrato.getColegiado().getNumColegiado();
		List<File> listaFicheros = new ArrayList<File>();
		for(byte[] pdf : listaPdf){
			listaFicheros.add(gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, 
					utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, pdf));
		}
		resultado = mergePDF(listaFicheros, nombreFichero, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, 
				utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()));
		if(!resultado.getError()){
			resultado = ordenarPdfPermFichas(listaTramites, nombreFichero,ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
					TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum());
		}
		return resultado;
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
				if(!resultado.getError()){
					resultado = ordenarPdfPermFichas(listaTramitesPermisos, nombreFichero,ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum());
				}
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
				String matriConEspacio = "";
				for(TramiteTraficoVO tramiteTrafico : listaTramites){
					for(int i=0; i< documents.size();i++){
						PDDocument doc = documents.get(i);
						PDFTextStripper textoPdf = new PDFTextStripper();
						String pdf = textoPdf.getText(doc);
						matriConEspacio = tramiteTrafico.getVehiculo().getMatricula().substring(0) + " " + tramiteTrafico.getVehiculo().getMatricula().substring(1, tramiteTrafico.getVehiculo().getMatricula().length());
								if((pdf.contains(tramiteTrafico.getVehiculo().getMatricula()) || pdf.contains(matriConEspacio))
										&& (tramiteTrafico.getVehiculo().getBastidor() == null || pdf.contains(tramiteTrafico.getVehiculo().getBastidor().toUpperCase()))){
									existeMatricula = Boolean.TRUE;
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
					if(!existeMatricula){
						if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc)){
							resultado.addListaTramiteSinFicha(tramiteTrafico);
						} else {
							resultado.addListaTramiteSinPermiso(tramiteTrafico);
						}
					} else {
						if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc)){
							resultado.addListaTramiteConFicha(tramiteTrafico);
						} else {
							resultado.addListaTramiteConPermiso(tramiteTrafico);
						}
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
				if(!resultado.getError()){
					resultado = ordenarPdfPermFichas(listaTramitesFichas, nombreFichero,ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), 
							TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum());
				}
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
