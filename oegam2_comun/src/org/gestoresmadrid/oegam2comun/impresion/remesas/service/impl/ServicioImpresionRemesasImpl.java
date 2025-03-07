package org.gestoresmadrid.oegam2comun.impresion.remesas.service.impl;

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
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.impresion.remesas.service.ServicioImpresionRemesas;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionRemesasImpl implements ServicioImpresionRemesas{

	private static final long serialVersionUID = -7600216395038971309L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionRemesasImpl.class);
	
	@Autowired
	private ServicioRemesas servicioRemesas;
	
	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultBean comprobarDatosMinimos(String codSeleccionados, String impreso) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados == null || codSeleccionados.isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar alguna remesa para imprimir.");
				return resultado;
			}
			if(impreso == null || impreso.isEmpty() || impreso.equals("")){
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar el tipo de documento que desea imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los datos obligatorios de la impresión de remesas 600/601, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los datos obligatorios de la impresión.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean comprobarPermisosRemesas(String codSeleccionados, Boolean tienePermisoAdmin, Long idContrato) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(!tienePermisoAdmin){
				List<RemesaDto> listaRemesasDto = servicioRemesas.getListaRemesasPorExpedientesYContrato(codSeleccionados, idContrato);
				if(listaRemesasDto != null && !listaRemesasDto.isEmpty()){
					if(listaRemesasDto.size() != codSeleccionados.split("-").length){
						resultado.setError(true);
						resultado.addMensajeALista("No tienes permiso para trabajar con todas las remesas seleccionados.");
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No tienes permiso para trabajar con las remesas seleccionados.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los permisos del colegiado para imprimir las remesas 600/601, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los permisos del colegiado para imprimir.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean comprobarImpresion(String codSeleccionados) {
		ResultBean resultado = new ResultBean(false);
		try {
			for(String numExp : codSeleccionados.split("-")){
				ResultBean resultValidar = servicioRemesas.validarRemesaImpresion(new BigDecimal(numExp));
				if(resultValidar.getError()){
					resultado.setError(true);
					for(String mensaje : resultValidar.getListaMensajes()){
						resultado.addMensajeALista(mensaje);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los datos de las remesas para imprimir, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los datos de las remesas para imprimir");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
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
					RemesaVO remesaBBDD = servicioRemesas.getRemesaVoPorExpediente(new BigDecimal(numExp), true);
					for(Modelo600_601VO modelo600_601vo : remesaBBDD.getListaModelos()){
						for(ResultadoModelo600601VO resultadoModeloBBDD : modelo600_601vo.getListaResultadoModelo()){
							if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModeloBBDD.getCodigoResultado())){
								nombreFichero = servicioModelo600_601.getNombreFicheroModelo(modelo600_601vo.getIdModelo(),
										resultadoModeloBBDD.getJustificante(),tipoFichero);
								FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
										Utilidades.transformExpedienteFecha(modelo600_601vo.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
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
				RemesaVO remesaBBDD = servicioRemesas.getRemesaVoPorExpediente(new BigDecimal(codSeleccionados), true);
				Boolean esOneDoc = remesaBBDD.getListaModelos().size() == 1;
				if(esOneDoc){
					Modelo600_601VO modelo600_601vo = remesaBBDD.getListaModelos().iterator().next();
					for(ResultadoModelo600601VO resultadoModeloBBDD : modelo600_601vo.getListaResultadoModelo()){
						if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModeloBBDD.getCodigoResultado())){
							nombreFichero = servicioModelo600_601.getNombreFicheroModelo(modelo600_601vo.getIdModelo(),
									resultadoModeloBBDD.getJustificante(),tipoFichero);
							FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
									Utilidades.transformExpedienteFecha(modelo600_601vo.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
							if(fichero != null && fichero.getFile() != null){
								resultado.addAttachment("fichero", fichero.getFile());
								resultado.addAttachment("nombreFichero", nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF); 
							}else{
								resultado.setError(true);
								resultado.addMensajeALista("El fichero que desea imprimir no existe.");
							}
							break;
						}
					}
				}else{
					url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP")+"zip"+System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for(Modelo600_601VO modelo600_601vo : remesaBBDD.getListaModelos()){
						for(ResultadoModelo600601VO resultadoModeloBBDD : modelo600_601vo.getListaResultadoModelo()){
							if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModeloBBDD.getCodigoResultado())){
								nombreFichero = servicioModelo600_601.getNombreFicheroModelo(modelo600_601vo.getIdModelo(),
										resultadoModeloBBDD.getJustificante(),tipoFichero);
								FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
										Utilidades.transformExpedienteFecha(modelo600_601vo.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
								if(fichero != null && fichero.getFile() != null){
									ZipEntry zipEntry = new ZipEntry(nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
									zip.putNextEntry(zipEntry);
									zip.write(gestorDocumentos.transformFiletoByte(fichero.getFile()));
									zip.closeEntry();
								}else{
									resultado.setError(true);
									resultado.addMensajeALista("Para la remesa: " + codSeleccionados + " y el Modelo: " + modelo600_601vo.getNumExpediente() + " no se encuentra el documento seleccionado para imprimir.");
								}
							}
						}
					}
					zip.close();
					File fichero = new File(url);
					resultado.addAttachment("nombreFichero", "Remesas_" + TipoFicheros.convertir(tipoFichero)  +  ConstantesGestorFicheros.EXTENSION_ZIP);
					resultado.addAttachment("fichero",fichero);
					if(fichero.delete()){
						log.info("Se ha eliminado correctamente el fichero");
					} else{
						log.info("No se ha eliminado el fichero");
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
}
