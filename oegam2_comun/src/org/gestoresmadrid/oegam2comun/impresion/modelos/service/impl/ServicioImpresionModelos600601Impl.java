package org.gestoresmadrid.oegam2comun.impresion.modelos.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.oegam2comun.impresion.modelos.service.ServicioImpresionModelos600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionModelos600601Impl implements ServicioImpresionModelos600601{

	private static final long serialVersionUID = 8842740480641762511L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionModelos600601Impl.class);
	
	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultBean comprobaDatosObligatorios(String codSeleccionados, String impreso) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados == null || codSeleccionados.isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para imprimir.");
				return resultado;
			}
			if(impreso == null || impreso.isEmpty() || impreso.equals("")){
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar el tipo de documento que desea imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los datos obligatorios de la impresión de modelo 600/601, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los datos obligatorios de la impresión.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean comprobarPermisosModelos(String codSeleccionados,	Boolean tienePermisoAdmin, Long idContrato) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(!tienePermisoAdmin){
				List<Modelo600_601Dto> listaModelos600601 = servicioModelo600_601.getListaModelosPorExpedientesYContrato(codSeleccionados, idContrato);
				if(listaModelos600601 != null && !listaModelos600601.isEmpty()){
					if(listaModelos600601.size() != codSeleccionados.split("-").length){
						resultado.setError(true);
						resultado.addMensajeALista("No tienes permiso para trabajar con todos los modelos seleccionados.");
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No tienes permiso para trabajar con los modelos seleccionados.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los permisos del colegiado para imprimir los modelos 600/601, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los permisos del colegiado para imprimir.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean comprobarImpresion(String codSeleccionados) {
		ResultBean resultado = new ResultBean(false);
		try {
			String[] numExpediente = codSeleccionados.split("-");
			for(String numExp : numExpediente){
				ResultBean resultValidar = servicioModelo600_601.validarModeloImpresion(new BigDecimal(numExp)); 
				if(resultValidar.getError()){
					resultado.setError(true);
					for(String mensaje : resultValidar.getListaMensajes()){
						resultado.addMensajeALista(mensaje);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los modelos para su impresión, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los modelos para su impresión.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean imprimir(String codSeleccionados, String tipoFichero) {
		ResultBean resultado = new ResultBean(false);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		String nombreFichero = null;
		try {
			String[] numExpediente = codSeleccionados.split("-");
			Boolean esZip = numExpediente.length > 1;
			if(esZip){
				url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP")+"zip"+System.currentTimeMillis();
				out = new FileOutputStream(url);
				zip = new ZipOutputStream(out);
				for(String numExp : numExpediente){
					Modelo600_601VO modelo600_601vo = servicioModelo600_601.getModeloVOPorNumExpediente(new BigDecimal(numExp), true);
					for(ResultadoModelo600601VO resultadoModeloBBDD : modelo600_601vo.getListaResultadoModelo()){
						if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModeloBBDD.getCodigoResultado())){
							nombreFichero = servicioModelo600_601.getNombreFicheroModelo(modelo600_601vo.getIdModelo(),
									resultadoModeloBBDD.getJustificante(),tipoFichero);
							FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
									Utilidades.transformExpedienteFecha(numExp), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
							if(fichero != null && fichero.getFile() != null){
								ZipEntry zipEntry = new ZipEntry(nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
								zip.putNextEntry(zipEntry);
								zip.write(gestorDocumentos.transformFiletoByte(fichero.getFile()));
								zip.closeEntry();
							}else{
								resultado.setError(true);
								resultado.addMensajeALista("Para el Modelo: " + numExp + " no se encuentra el documento seleccionado para imprimir.");
							}
						}
					}
				}
				zip.close();
				File fichero = new File(url);
				resultado.addAttachment("nombreFichero", TipoFicheros.convertir(tipoFichero) + "_" + numExpediente +  ConstantesGestorFicheros.EXTENSION_ZIP);
				resultado.addAttachment("fichero",fichero);
				if(fichero.delete()){
					log.info("Se ha eliminado correctamente el fichero");
				} else{
					log.info("No se ha eliminado el fichero");
				}
			}else{
				Modelo600_601VO modelo600_601vo = servicioModelo600_601.getModeloVOPorNumExpediente(new BigDecimal(codSeleccionados), true);
				if(TipoFicheros.Escritura.getValorEnum().equalsIgnoreCase(tipoFichero)){
					resultado = dameFichero(modelo600_601vo,numExpediente[0],tipoFichero,codSeleccionados,null);
				}else{
					for(ResultadoModelo600601VO resultadoModeloBBDD : modelo600_601vo.getListaResultadoModelo()){
						if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModeloBBDD.getCodigoResultado())){
							resultado = dameFichero(modelo600_601vo,numExpediente[0],tipoFichero,codSeleccionados,resultadoModeloBBDD.getJustificante());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los modelos, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir los modelos.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de imprimir los modelos, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir los modelos.");
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				File eliminarZip = new File(url);
				eliminarZip.delete();
			}
		}
		return resultado;
	}

	private ResultBean dameFichero(Modelo600_601VO modelo600_601vo,String numExpediente, String tipoFichero, String codSeleccionados, String justificante) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(false);
		String sSubTipo = ConstantesGestorFicheros.PDF_MODELOS_600_601;
		String nombreFichero = null;
		nombreFichero = servicioModelo600_601.getNombreFicheroModelo(modelo600_601vo.getIdModelo(),justificante,tipoFichero);
		if(TipoFicheros.Escritura.getValorEnum().equals(tipoFichero)){
			nombreFichero += numExpediente;
			sSubTipo = ConstantesGestorFicheros.FORMULARIOS_CAM;
		}
		FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, sSubTipo, 
				Utilidades.transformExpedienteFecha(codSeleccionados), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
		if(fichero != null && fichero.getFile() != null){
			resultado.addAttachment("fichero", fichero.getFile());
			resultado.addAttachment("nombreFichero", nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF); 
		}else{
			resultado.setError(true);
			resultado.addMensajeALista("El fichero que desea imprimir no existe.");
		}
		return resultado;
	}
	
	
	
	
	/***
	 * 
	 * ResultBean resultado = new ResultBean(false);
		
		String url = null;
		String nombreFichero = null;
		ResultadoModelo600601Dto resultadoModelo600601Dto = null;
		try {
			if(idResultadosModelo != null && !idResultadosModelo.isEmpty() && tipoFichero != null && !tipoFichero.isEmpty()){
				String[] idResultados = idResultadosModelo.split("-");
				if(idResultados.length == 1){
					resultadoModelo600601Dto = servicioResultadoModelo600601.getResultadoModelo600601DtoPorId(Integer.parseInt(idResultados[0]));
					if(resultadoModelo600601Dto != null && resultadoModelo600601Dto.getJustificante() != null && !resultadoModelo600601Dto.getJustificante().isEmpty()){
						nombreFichero = getNombreFicheroModelo(resultadoModelo600601Dto.getModelo600601().getIdModelo(),resultadoModelo600601Dto.getJustificante(),tipoFichero);
						FileResultBean fichero = guardarDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
								Utilidades.transformExpedienteFecha(numExpediente), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							resultado.addAttachment("fichero", fichero.getFile());
							resultado.addAttachment("nombreFichero", nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF); 
						}else{
							resultado.setError(true);
							resultado.addMensajeALista("El fichero que desea imprimir no existe.");
						}
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("No se tienen datos para imprimir de ese resultado.");
					}
				}else{
					url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP")+"zip"+System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for(String idResultadoModelo : idResultados){
						resultadoModelo600601Dto = servicioResultadoModelo600601.getResultadoModelo600601DtoPorId(Integer.parseInt(idResultadoModelo));
						if(resultadoModelo600601Dto != null && resultadoModelo600601Dto.getJustificante() != null && !resultadoModelo600601Dto.getJustificante().isEmpty()){
						
						}else{
							resultado.addMensajeALista("Para el resultado con fecha: " + UtilesFecha.formatoFecha(",date,dd-MM-yyyy HH:mm:ss",resultadoModelo600601Dto.getFechaEjecucion()) 
								+ " no existen datos que imprimir.");
							break;
						}
					}
					zip.close();
					if(!resultado.getError()){
						File fichero = new File(url);
						resultado.addAttachment("nombreFichero", TipoFicheros.convertir(tipoFichero) + "_" + numExpediente +  ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.addAttachment("fichero",fichero);
						if(fichero.delete()){
							log.info("Se ha eliminado correctamente el fichero");
						} else{
							log.info("No se ha eliminado el fichero");
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir el documento.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir el documento.");
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				File eliminarZip = new File(url);
				eliminarZip.delete();
			}
		}
		return resultado;
	 */
}