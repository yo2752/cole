package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionDocPrmDstvFichas;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionDocPrmDstvFichasImpl implements ServicioGestionDocPrmDstvFichas{

	private static final long serialVersionUID = -6208596498690514033L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionDocPrmDstvFichasImpl.class);
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioFichasTecnicasDgt servicioFichasTecnicasDgt;
	
	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoPermisoDistintivoItvBean impresionAutoImpr(String docsId, String jefaturaImpr, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docsId, Boolean.TRUE);
			if(docPermDistItvVO != null){
				ResultadoPermisoDistintivoItvBean resultImprimir = null;
				if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
					resultImprimir = servicioDocPrmDstvFicha.imprimirDocDistintivoJefatura(docPermDistItvVO,idUsuario,jefaturaImpr);
					if(resultImprimir.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("DocId: " + docsId + " impreso correctamente.");
					}
				} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
					resultImprimir = servicioDocPrmDstvFicha.imprimirDocPermisoJefatura(docPermDistItvVO,idUsuario, jefaturaImpr);
					if(resultImprimir.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("DocId: " + docsId + " impreso correctamente.");
					}
				} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
					resultImprimir = servicioDocPrmDstvFicha.imprimirDocFichasJefatura(docPermDistItvVO,idUsuario, jefaturaImpr);
					if(resultImprimir.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("DocId: " + docsId + " impreso correctamente.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos asociados al documento");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los documentos seleccionados.");
		}
		return resultado;
	}
	
	
	@Override
	public List<GestionPermisoDistintivoItvBean> convertirListaEnBeanPantalla(List<DocPermDistItvVO> listaBBDD) {
		if (listaBBDD != null && !listaBBDD.isEmpty()) {
			List<GestionPermisoDistintivoItvBean> listaPrmDstv = new ArrayList<GestionPermisoDistintivoItvBean>();
			for(DocPermDistItvVO docPermDistItvVO : listaBBDD){
				GestionPermisoDistintivoItvBean gestionPermisoDistintivoItvBean = conversor.transform(docPermDistItvVO, GestionPermisoDistintivoItvBean.class);
				gestionPermisoDistintivoItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
				gestionPermisoDistintivoItvBean.setContrato(docPermDistItvVO.getContrato().getColegiado().getNumColegiado() + "-" + docPermDistItvVO.getContrato().getVia());
				gestionPermisoDistintivoItvBean.setFechaAlta(new SimpleDateFormat("dd/MM/yyyy").format(docPermDistItvVO.getFechaAlta()));
				gestionPermisoDistintivoItvBean.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
				gestionPermisoDistintivoItvBean.setEstado(EstadoPermisoDistintivoItv.convertirTexto(docPermDistItvVO.getEstado().toString()));
				if(docPermDistItvVO.getTipoDistintivo() != null && !docPermDistItvVO.getTipoDistintivo().isEmpty()){
					gestionPermisoDistintivoItvBean.setTipoDistintivo(TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()));
				}
				if(docPermDistItvVO.getJefatura() != null && !docPermDistItvVO.getJefatura().isEmpty()){
					JefaturaTraficoDto jefatura = servicioJefaturaTrafico.getJefatura(docPermDistItvVO.getJefatura());
					if(jefatura != null){
						gestionPermisoDistintivoItvBean.setJefatura(jefatura.getDescripcion());
					}
				}
				if(docPermDistItvVO.getFechaImpresion() != null){
					gestionPermisoDistintivoItvBean.setFechaImpresion(utilesFecha.formatoFecha("dd/MM/yyyy", docPermDistItvVO.getFechaImpresion()));
				}
				listaPrmDstv.add(gestionPermisoDistintivoItvBean);
			}
			return listaPrmDstv;
		}
		return Collections.emptyList();
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean imprimirDoc(String docsId, String jefaturaImpr, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String[] sDocId = docsId.split(";");
			resultado = servicioDocPrmDstvFicha.validarDocMismoTipo(sDocId);
			if(!resultado.getError()){
				for(String docId : sDocId){
					try {
						DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docId, Boolean.TRUE);
						if(docPermDistItvVO != null){
							ResultadoPermisoDistintivoItvBean resultImprimir = null;
							if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
								resultImprimir = servicioDocPrmDstvFicha.imprimirDocDistintivo(docPermDistItvVO,idUsuario, jefaturaImpr, ipConexion);
								if(resultImprimir.getError()){
									resultado.getResumen().addNumError();
									resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
								} else {
									resultado.getResumen().addNumOk();
									resultado.getResumen().addListaMensajeOk("DocId: " + docId + " impreso correctamente.");
								}
							} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
								resultImprimir = servicioDocPrmDstvFicha.imprimirDocPermiso(docPermDistItvVO,idUsuario, jefaturaImpr, ipConexion);
								if(resultImprimir.getError()){
									resultado.getResumen().addNumError();
									resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
								} else {
									resultado.getResumen().addNumOk();
									resultado.getResumen().addListaMensajeOk("DocId: " + docId + " impreso correctamente.");
								}
							} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
								resultImprimir = servicioDocPrmDstvFicha.imprimirDocFichas(docPermDistItvVO,idUsuario, Boolean.FALSE, Boolean.FALSE, ipConexion);
								if(resultImprimir.getError()){
									resultado.getResumen().addNumError();
									resultado.getResumen().addListaMensajeError(resultImprimir.getMensaje());
								} else {
									resultado.getResumen().addNumOk();
									resultado.getResumen().addListaMensajeOk("DocId: " + docId + " impreso correctamente.");
								}
							}
						} else {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("No existen datos para imprimir del docId: " + docId);
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de imprimir el docId: " + docId + ",error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de imprimir el docId: " + docId);
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los documentos seleccionados.");
		}
		return resultado;
	}
	
	public ResultadoPermisoDistintivoItvBean descargarDoc(String sDocIds) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		try {
			if (sDocIds != null && !sDocIds.isEmpty()) {
				String[] docIds = sDocIds.split(";");
				if(docIds.length  > 1){
					url = gestorPropiedades.valorPropertie(ServicioDistintivoDgt.RUTA_FICH_TEMP) + "zip" +System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for(String docId : docIds){
						DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docId, Boolean.FALSE);
						if(docPermDistItvVO != null){
							if(docPermDistItvVO.getNumDescarga() == null 
									|| docPermDistItvVO.getNumDescarga().compareTo(new Long(0)) == 0 
									|| TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
								if(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
									resultado = servicioDocPrmDstvFicha.descargarPdfFichasPermisos(docPermDistItvVO);
									if (!resultado.getError()) {
										for (Map.Entry<String, File> entry : resultado.getListaFicheros().entrySet()) {
											ZipEntry zipEntry = new ZipEntry(entry.getKey()+ ConstantesGestorFicheros.EXTENSION_PDF);
											zip.putNextEntry(zipEntry);
											zip.write(gestorDocumentos.transformFiletoByte(entry.getValue()));
											zip.closeEntry();
										}
									} else {
										break;
									}
								} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())
									|| TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
										resultado = servicioDocPrmDstvFicha.descargarPdf(docPermDistItvVO);
										if (!resultado.getError()) {
												ZipEntry zipEntry = new ZipEntry(resultado.getNombreFichero()+ ConstantesGestorFicheros.EXTENSION_PDF);
												zip.putNextEntry(zipEntry);
												zip.write(gestorDocumentos.transformFiletoByte(resultado.getFichero()));
												zip.closeEntry();
										} else {
											break;
										}
								} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
									resultado = servicioDocPrmDstvFicha.descargarPdf(docPermDistItvVO);
									if (!resultado.getError()) {
										ZipEntry zipEntry = new ZipEntry(resultado.getNombreFichero()+ ConstantesGestorFicheros.EXTENSION_PDF);
										zip.putNextEntry(zipEntry);
										zip.write(gestorDocumentos.transformFiletoByte(resultado.getFichero()));
										zip.closeEntry();
									}
								}
								if(!resultado.getError()){
									ResultadoPermisoDistintivoItvBean resultEnvioMail = servicioDocPrmDstvFicha.enviarMailImpresionDocumento(docPermDistItvVO);
									if(resultEnvioMail.getError()){
										resultado.setError(Boolean.TRUE);
										resultado.setMensaje(resultEnvioMail.getMensaje());
										break;
									}
								}
							} else{
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("El documento con docId:" + docId + " no se puede volver a descargar porque ya ha sido realizada su descarga.");
								break;
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No existen datos para el documento: " + docId);
							break;
						}
					}
					zip.close();
					if(!resultado.getError()){
						File fichero = new File(url);
						resultado.setEsZip(Boolean.TRUE);
						resultado.setNombreFichero(ServicioDistintivoDgt.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.setFichero(fichero);
					}
				} else {
					DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docIds[0], Boolean.FALSE);
					if(docPermDistItvVO != null){
						if(docPermDistItvVO.getNumDescarga() == null || docPermDistItvVO.getNumDescarga().compareTo(new Long(0)) == 0){
							if(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
								url = gestorPropiedades.valorPropertie(ServicioDistintivoDgt.RUTA_FICH_TEMP) + "zip" +System.currentTimeMillis();
								out = new FileOutputStream(url);
								zip = new ZipOutputStream(out);
								resultado = servicioDocPrmDstvFicha.descargarPdfFichasPermisos(docPermDistItvVO);
								if (!resultado.getError()) {
									for (Map.Entry<String, File> entry : resultado.getListaFicheros().entrySet()) {
										ZipEntry zipEntry = new ZipEntry(entry.getKey()+ ConstantesGestorFicheros.EXTENSION_PDF);
										zip.putNextEntry(zipEntry);
										zip.write(gestorDocumentos.transformFiletoByte(entry.getValue()));
										zip.closeEntry();
									}
								}
								zip.close();
								if(!resultado.getError()){
									File fichero = new File(url);
									resultado.setEsZip(Boolean.TRUE);
									resultado.setNombreFichero(ServicioDistintivoDgt.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
									resultado.setFichero(fichero);
								}
							} else{
								resultado = servicioDocPrmDstvFicha.descargarPdf(docPermDistItvVO);
							}
							if(!resultado.getError()){
								ResultadoPermisoDistintivoItvBean resultEnvioMail = servicioDocPrmDstvFicha.enviarMailImpresionDocumento(docPermDistItvVO);
								if(resultEnvioMail.getError()){
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje(resultEnvioMail.getMensaje());
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El documento con docId:" + docPermDistItvVO.getDocIdPerm() + " no se puede volver a descargar porque ya ha sido realizada su descarga.");
						}
					}
				}
 			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar alguna consulta para poder realizar la descarga.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los xml de la consulta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar los xml de la consulta.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean reactivarDocJefatura(String codSeleccionados, String jefaturaImpr, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] sDocId = codSeleccionados.split(";");
				for(String docId : sDocId){
					try {
						ResultadoPermisoDistintivoItvBean resultReactivar = servicioDocPrmDstvFicha.reactivarDocJefatura(docId, idUsuario, jefaturaImpr);
						if(resultReactivar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultReactivar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Documento: " + docId + " reactivado correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reactivar el docId: " + docId + ",error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de reactivar el docId: " + docId);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder reactivarlos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de reactivar los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar los documentos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean reiniciarDoc(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] sDocId = codSeleccionados.split(";");
				for(String docId : sDocId){
					try {
						ResultadoPermisoDistintivoItvBean resultReactivar = servicioDocPrmDstvFicha.reiniciarDocImpr(docId, idUsuario);
						if(resultReactivar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultReactivar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Documento: " + docId + " reiniciado correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reiniciar el docId: " + docId + ",error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de reiniciar el docId: " + docId);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún documento para reiniciar.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de reiniciar los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reiniciar los documentos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean reactivarDoc(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] sDocId = codSeleccionados.split(";");
				for(String docId : sDocId){
					try {
						ResultadoPermisoDistintivoItvBean resultReactivar = servicioDocPrmDstvFicha.reactivarDoc(docId, idUsuario);
						if(resultReactivar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultReactivar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Documento: " + docId + " reactivado correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reactivar el docId: " + docId + ",error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de reactivar el docId: " + docId);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder reactivarlos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los documentos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean cambiarEstado(String codSeleccionados, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] sDocId = codSeleccionados.split(";");
				for(String docId : sDocId){
					try {
						ResultadoPermisoDistintivoItvBean resultReactivar = servicioDocPrmDstvFicha.cambiarEstado(docId, estadoNuevo, idUsuario);
						if(resultReactivar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultReactivar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Documento: " + docId + " cambiado de estado correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar el estado del docId: " + docId + ",error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de cambiar el estado del docId: " + docId);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder reactivarlos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los documentos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	
	@Override
	@Transactional(readOnly=true)
	public Boolean esDocPermisos(String codSeleccionados) {
		try {
			DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(codSeleccionados, Boolean.FALSE);
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo()) 
					|| TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
					return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el tipo de documento, error: ", e);
		}
		
		return Boolean.FALSE;
	}


	@Override
	public ResultadoPermisoDistintivoItvBean anular(String codSeleccionados, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] docIds = codSeleccionados.split(";");
			for(String docId : docIds) {
				try {
					ResultadoPermisoDistintivoItvBean resultAnular = servicioDocPrmDstvFicha.anular(docId, idUsuario, ipConexion);
					if(resultAnular.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultAnular.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("Documento: " + docId + " se anuló correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de anular el documento: " + docId + ", error: ",e, docId);
					resultado.setError(Boolean.TRUE);;
					resultado.setMensaje("Ha sucedido un error a la hora de anular el documento: " + docId);
				}
			}
		}
		return resultado;
	}
	
	
}
