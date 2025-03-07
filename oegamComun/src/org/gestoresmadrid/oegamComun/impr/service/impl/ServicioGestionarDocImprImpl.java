package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprKO;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionDocImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioGestionarDocImprImpl implements ServicioGestionarDocImpr{

	private static final long serialVersionUID = 4586818091131234146L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionarDocImprImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioImpr servicioImpr;

	@Autowired
	ServicioDocImpr servicioDocImpr;

	@Autowired
	ServicioImprKO servicioImprKO;

	@Autowired
	UtilesVistaTramites utiles;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired 
	UtilesFecha utilesFecha;

	@Override
	public List<GestionDocImprFilterBean> convertirListaEnBeanPantalla(List<DocImprVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<GestionDocImprFilterBean> listaBean = new ArrayList<GestionDocImprFilterBean>();
			for (DocImprVO docImprVO : lista) {
				GestionDocImprFilterBean imprBean = conversor.transform(docImprVO, GestionDocImprFilterBean.class);

				listaBean.add(imprBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoDocImprBean impresionManual(String codSeleccionados, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			if(StringUtils.isNotBlank(codSeleccionados)){
				String[] ids = codSeleccionados.split("_");
					for(String id : ids){
						try {
							ResultadoDocImprBean resultImprimir = servicioDocImpr.impresionManual(new Long(id), idUsuario,jefatura);
							if(resultImprimir.getError()){
								resultado.addResumenError(resultImprimir.getMensaje());
							} else {
								resultado.addResumenOK("Impresion manual con id: " + id + " generado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de imprimir manual el tramite con id: " + id + ", error: ",e);
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de imprimir manual el tramite con id: " + id);
						}
					}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder imprimir manual.");
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir manual los IMPR seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir manual los IMPR seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocImprBean descargaManual(String codSeleccionados, Boolean esEntornoAm) throws OegamExcepcion {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			if(StringUtils.isNotBlank(codSeleccionados)){
				String[] ids = codSeleccionados.split("_");
				if(ids.length > 1) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ZipOutputStream out = new ZipOutputStream(baos);
					Boolean existeDoc = Boolean.FALSE;
					for(String id : ids){
						DocImprVO docImprVO = servicioDocImpr.getDocImprPorId(new Long (id),Boolean.FALSE);
						if(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docImprVO.getEstado())) {
							FileResultBean fileResult = obtenerFicheroDocImprDescarga(docImprVO,esEntornoAm);
							if(fileResult.getFile() != null) {
								existeDoc = Boolean.TRUE;
								FileInputStream fis = new FileInputStream(fileResult.getFile());
								ZipEntry entrada = new ZipEntry("DocumentosImpresos_"+id + ConstantesGestorFicheros.EXTENSION_PDF);
								out.putNextEntry(entrada);
								byte[] buffer = new byte[1024];
								int leido = 0;
								while (0 < (leido = fis.read(buffer))) {
									out.write(buffer, 0, leido);
								}
								fis.close();
								out.closeEntry();
							}
						}
					}
					out.close();
					if(existeDoc) {
						resultado.setFicheroArray(new ByteArrayInputStream(baos.toByteArray()));
						resultado.setNombreFichero("Descarga_Documentos_Impr_" + new Random().nextInt() + ConstantesGestorFicheros.EXTENSION_PDF);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No exiset ningun documento para su descarga.");
					}
				} else {
					DocImprVO docImprVO = servicioDocImpr.getDocImprPorId(new Long(codSeleccionados), Boolean.FALSE);
					if(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docImprVO.getEstado())) {
						FileResultBean fileResult = obtenerFicheroDocImprDescarga(docImprVO,esEntornoAm);
						if (fileResult.getFile() != null) {
							resultado.setFicheroArray(new ByteArrayInputStream(gestorDocumentos.transformFiletoByte(fileResult.getFile())));
							resultado.setNombreFichero("DocumentosImpresos_" + docImprVO.getId() + ConstantesGestorFicheros.EXTENSION_PDF);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algun documento para su descarga");
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir manual los IMPR seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir manual los IMPR seleccionados.");
		}
		return resultado;
	}

	private FileResultBean obtenerFicheroDocImprDescarga(DocImprVO docImprVO, Boolean esEntornoAm) throws OegamExcepcion {
		String tipo = "", subTipo = "";
		if(TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
			tipo = ConstantesGestorFicheros.DISTINTIVOS;
		} else if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())) {
			tipo = ConstantesGestorFicheros.IMPR;
			subTipo = ConstantesGestorFicheros.PERMISOS;
		} else if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(docImprVO.getTipo())) {
			tipo = ConstantesGestorFicheros.IMPR;
			subTipo = ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA;
		}
		FileResultBean fileResult = null;
		if(!esEntornoAm && "2631".equals(docImprVO.getContrato().getColegiado().getNumColegiado())) {
			fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subTipo, utilesFecha.getFechaConDate(docImprVO.getFechaAlta())
					, "DocumentosImpresos_" + docImprVO.getId(), ConstantesGestorFicheros.EXTENSION_PDF);
		}else {
			fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subTipo, utilesFecha.getFechaConDate(docImprVO.getFechaAlta())
					, "DocumentosImpresos_" + docImprVO.getId(), ConstantesGestorFicheros.EXTENSION_PDF);
		}

		return fileResult;
	}

	@Override
	public ResultadoDocImprBean cambiarEstado(String codSeleccionados, String estadoNuevo, Long idUsuario) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)){
				if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
					String[] ids = codSeleccionados.split("_");
					for (String id : ids) {
						try {
							ResultadoDocImprBean resultValidar = servicioDocImpr.cambiarEstado(new Long(id), estadoNuevo, idUsuario);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK("Para el IMPR: " + id + " se ha cambiado el estado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de cambiar el estado del documento con id: " + id + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de cambiar el estado del documento con id: " + id);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe seleccionar algún estado nuevo para actualizar los documentos.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún documento para cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de los documentos seleccionados.");
		}
		return resultado;
	}

	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	@Override
	public ResultadoDocImprBean reactivar(String codSeleccionados, Long idUsuario, Boolean esEntornoAm) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)){
					String[] ids = codSeleccionados.split("_");
					for (String id : ids) {
						try {
							ResultadoDocImprBean resultValidar = servicioDocImpr.reactivar(new Long(id), idUsuario, esEntornoAm);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK("Documento: " + id + " reactivado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de reactivar el documento con id: " + id + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de reactivar el documento con id: " + id);
						}
					}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún documento para su reactivación.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar los documentos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar los documentos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocImprBean imprimir(String codSeleccionados, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			if(StringUtils.isNotBlank(codSeleccionados)){
				String[] ids = codSeleccionados.split("_");
					for(String id : ids){
						try {
							ResultadoDocImprBean resultImprimir = servicioDocImpr.imprimir(new Long(id), idUsuario,jefatura);
							if(resultImprimir.getError()){
								resultado.addResumenError(resultImprimir.getMensaje());
							} else {
								resultado.addResumenOK("Impresion con id: " + id + " generado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de imprimir el tramite con id: " + id + ", error: ",e);
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de imprimir el tramite con id: " + id);
						}
					}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder imprimir.");
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los IMPR seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los IMPR seleccionados.");
		}
		return resultado;
	}

}