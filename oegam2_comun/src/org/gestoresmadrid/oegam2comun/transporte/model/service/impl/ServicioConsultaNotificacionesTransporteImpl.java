package org.gestoresmadrid.oegam2comun.transporte.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.transporte.model.enumeration.EstadosNotificacionesTransporte;
import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioConsultaNotificacionesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioNotificacionesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.NotificacionTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.NotificacionTransporteDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioConsultaNotificacionesTransporteImpl implements ServicioConsultaNotificacionesTransporte{

	private static final long serialVersionUID = -1864957041628735389L;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioConsultaNotificacionesTransporteImpl.class);
	
	@Autowired
	ServicioNotificacionesTransporte servicioNotificacionesTransporte;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<NotificacionTransporteBean> convertirListaEnBeanPantalla(List<NotificacionTransporteVO> list) {
		if(list != null && !list.isEmpty()){
			List<NotificacionTransporteBean> listaBean = new ArrayList<NotificacionTransporteBean>();
			for(NotificacionTransporteVO notificacionTransporteVO : list){
				NotificacionTransporteBean notificacionTransporteBean = new NotificacionTransporteBean();
				notificacionTransporteBean.setIdNotificacionTransporte(notificacionTransporteVO.getIdNotificacionTransporte());
				notificacionTransporteBean.setNifEmpresa(notificacionTransporteVO.getNifEmpresa());
				notificacionTransporteBean.setNombreEmpresa(notificacionTransporteVO.getNombreEmpresa());
				notificacionTransporteBean.setEstado(EstadosNotificacionesTransporte.convertirTexto(notificacionTransporteVO.getEstado()));
				String desContrato = notificacionTransporteVO.getContrato().getProvincia().getNombre() + " - " + notificacionTransporteVO.getContrato().getVia();
				notificacionTransporteBean.setContrato(desContrato);
				listaBean.add(notificacionTransporteBean);
			}
			return listaBean;
		} 
		return Collections.emptyList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultadoTransporteBean imprimirNotificacionesBloque(String codSeleccionados, BigDecimal idUsuario, BigDecimal idContratoSession, Boolean esAdmin) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		Date fecha = null;
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] idsNotificaciones = codSeleccionados.split("_");
				ResultBean resultListaNot = servicioNotificacionesTransporte.getListadoNotificacionesTransImprimir(idsNotificaciones, idContratoSession, esAdmin);
				if(!resultListaNot.getError()){
					fecha = new Date();
					List<NotificacionTransporteDto> listaNotificacionesDto = (List<NotificacionTransporteDto>) resultListaNot.getAttachment(ServicioNotificacionesTransporte.LISTA_NOT_TRANS_DTO);
					ContratoDto contrato = servicioContrato.getContratoDto(idContratoSession);
					resultado = servicioNotificacionesTransporte.imprimirNotificaciones(listaNotificacionesDto, contrato,fecha);
					if(!resultado.getError()){
						ResultBean resultCambEstado = servicioNotificacionesTransporte.cambiarEstadoNotificacionesImpresion(listaNotificacionesDto,idUsuario,fecha,resultado.getNombreFichero());
						if(resultCambEstado.getError()){
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultCambEstado.getMensaje());
						} else {
							resultado.addListaOk("El fichero se ha generado correctamente.");
							resultado.addOk();
						}
					}
				} else {
					resultado.addError();
					resultado.addListaError(resultListaNot.getMensaje());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna Notificación para poder imprimirla.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de imprimir las notificaciones en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las notificaciones en bloque.");
		}
		if(resultado.getError()){
			if(resultado.getNombreFichero() != null && !resultado.getNombreFichero().isEmpty()){
				try {
					gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.NOTIFICACIONES, new Fecha(fecha), 
							resultado.getNombreFichero(),ConstantesGestorFicheros.EXTENSION_PDF);
				} catch (OegamExcepcion e) {
					LOG.error("Ha sucedido un error a la hora de borrar el pdf de notificaciones cuando se ha producido un error controlado, error: ",e);
				}
			}
		}
		return resultado;
	}
	
	@Override
	public ResultadoTransporteBean rechazarNotificacionesBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] idsNotificaciones = codSeleccionados.split("_");
			for(String idNotificacion : idsNotificaciones){
				try {
					ResultBean resultRechazar = servicioNotificacionesTransporte.rechazarNotificaciones(Long.parseLong(idNotificacion), idUsuario);
					String nifEmpresa = (String) resultRechazar.getAttachment(ServicioNotificacionesTransporte.NIF_EMPRESA);
					if(resultRechazar.getError()){
						String mensajeError = "";
						if(nifEmpresa != null && !nifEmpresa.isEmpty()){
							 mensajeError = "La notificacion de la empresa con NIF: " + nifEmpresa + " ha fallado por el siguiente motivo: ";
						}
						resultado.addError();
						resultado.addListaError(mensajeError + resultRechazar.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("La notificación para la empresa con NIF: " + nifEmpresa + " se ha rechazado correctamente.");
					}
				} catch (Exception e) {
					LOG.error("Ha sucedido un error a la hora de rechazar una de las notificación",e);
					resultado.addError(); 
					resultado.addListaError("Ha sucedido un error a la hora de rechazar una de las notificación.");
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar alguna Notificación para poder rechazarla.");
		}
		return resultado;
	}

	@Override
	public ResultadoTransporteBean descargarPdf(String nombreFichero) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		try {
			if(nombreFichero != null){
				String[] nombre = nombreFichero.split("_");
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.NOTIFICACIONES, 
						utilesFecha.getFechaConDate(new SimpleDateFormat("yyyyMMdd").parse(nombre[3].substring(0, 8))), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
				if(fileResultBean.getFile() != null){
					resultado.setFichero(fileResultBean.getFile());
					resultado.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El fichero que desea descargar no existe.");
				}
			} else { 
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe nombre del fichero para poder descargarlo.");
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de descargar el pdf de notificaciones, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de notificaciones.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoTransporteBean descargarNotificacionesBloque(String idsNotificacionesTransporte) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			if(idsNotificacionesTransporte != null && !idsNotificacionesTransporte.isEmpty()){
				String[] idsNotificaciones = idsNotificacionesTransporte.split("_");
				List<String> listaFicherosPdf = servicioNotificacionesTransporte.getListaFicherosPdfPorIdTramite(idsNotificaciones);
				if(listaFicherosPdf != null && !listaFicherosPdf.isEmpty()){
					Boolean sonVariosNot = false;
					url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
					if(listaFicherosPdf.size() > 1){
						sonVariosNot = Boolean.TRUE;
						File ficheroDestino = new File(url);
						out = new FileOutputStream(ficheroDestino);
						zip = new ZipOutputStream(out);
					}
					for (String nombrePdf : listaFicherosPdf) {
						ResultadoTransporteBean resultDescargar = descargarPdf(nombrePdf);
						if(resultDescargar.getError()){
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultDescargar.getMensaje());
							break;
						} else {
							if(sonVariosNot){
								addZipEntryFromFile(zip,resultDescargar.getFichero());
							} else{
								resultado.setFichero(resultDescargar.getFichero());
								resultado.setNombreFichero(resultDescargar.getNombreFichero());
							}
						}
					}
					if (sonVariosNot) {
						zip.close();
						File fichero = new File(url);
						resultado.setNombreFichero("NOTIFICACIONES" + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.setFichero(fichero);
						resultado.setEsZip(Boolean.TRUE);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Las notificaciones seleccionadas no tienen todavía un documento pdf generado para su descargar.");
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún poder.");
			}
		} catch (FileNotFoundException e) {
			LOG.error("Ha sucedido un error a la hora de descargar las notificaciones, error: ");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar las notificaciones.");
		} catch (IOException e) {
			LOG.error("Ha sucedido un error a la hora de descargar las notificaciones, error: ");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar las notificaciones.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}
	
	@Override
	public void borrarZip(File ficheroZip) {
		try {
			if(ficheroZip != null){
				ficheroZip.delete();
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de borrar el zip temporal, error: ",e);
		}
	}
	
	public void addZipEntryFromFile(ZipOutputStream zip, File file) {
		if (file != null && file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			} catch (IOException e) {
				LOG.error("Error al añadir una entrada al zip del fichero: " + file.getName(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						LOG.error("Error cerrando FileInputStream", e);
					}
				}
			}
		}
	}
	
}
