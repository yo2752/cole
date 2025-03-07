/**
 * 
 */
package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.impl;

import java.io.File;
import java.util.List;

import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioProcesoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * @author ext_fjcl
 *
 */
@Service
public class ServicioProcesoMensajeErrorServicioImpl implements ServicioProcesoMensajeErrorServicio {

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioMensajeErrorServicio servicioMensaje;
	
	@Autowired
	private ServicioCorreo servicioCorreo;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioProcesoMensajeErrorServicioImpl.class);
	private final String DESTINARIOS_FICHERO_PROCESO_ERROR_SERVICIO = "destinatarios.fichero.proceso.error.servicio";

	@Override
	public ResultadoMensajeErrorServicio generarExcel() {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		try {
			List<MensajeErrorServicioDto> lista = servicioMensaje.getListaMensajeErrorServicio(null);
			if(lista.isEmpty()){
				resultado.setError(true);
				resultado.setMensaje("No se ha recuperado ningun registro");
			}else{
				resultado = servicioMensaje.generarExcel(lista);				
				if(!resultado.isError()){
					resultado = servicioMensaje.guardarFichero(resultado.getFichero());
					if(!resultado.isError()){
						if (resultado.getFile()!= null){
							enviarCorreoFicheroErrorServicio(resultado.getFile());						
						}						
						resultado = servicioMensaje.borrar(lista);
					}
				}
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje(e.getMessage());
		}
		return resultado;
	}
	
	@Override
	public ResultBean enviarCorreoFicheroErrorServicio(File ficheroErrorServicio) {
		ResultBean resultEnvio = new ResultBean();
		
		if (ficheroErrorServicio!=null){		
								
			try {				
				FicheroBean adjunto = new FicheroBean();					
				adjunto.setFichero(ficheroErrorServicio);
				adjunto.setNombreYExtension(ficheroErrorServicio.getName());			
				
				StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
				texto.append("Se adjunta fichero de Error Servicio generado por el proceso.<br>");	
				
				String destinatarios = gestorPropiedades.valorPropertie(DESTINARIOS_FICHERO_PROCESO_ERROR_SERVICIO);
				String asunto = "Fichero adjunto Error Servicio";					
				
				resultEnvio = servicioCorreo.enviarCorreo(texto.toString(), null, null, asunto, destinatarios, null, null, null, adjunto);
				if (resultEnvio.getError()) {
					log.error("No se ha enviado el correo con el fichero adjunto de Error Servicio: " + resultEnvio.getMensaje());
					resultEnvio.setError(Boolean.TRUE);
					resultEnvio.addError("No se ha enviado el correo con el fichero adjunto de Error Servicio: " + resultEnvio.getMensaje());
				}
				
			} catch (Throwable e) {
				log.error("No se ha enviado el correo con el fichero adjunto de Error Servicio:, error: " ,e);
				resultEnvio.setError(Boolean.TRUE);
				resultEnvio.addError("No se ha enviado el correo con el fichero adjunto de Error Servicio.");
			}
			
		}else{
			resultEnvio.setError(true);
			resultEnvio.addError("Error envio correo de error servicio : El fichero es null");
		}
		
		return resultEnvio;
	}
	

}
