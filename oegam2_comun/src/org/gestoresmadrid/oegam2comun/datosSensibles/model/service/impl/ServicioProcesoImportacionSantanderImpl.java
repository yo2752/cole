package org.gestoresmadrid.oegam2comun.datosSensibles.model.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioProcesoImportacionSantander;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ImportarBastidorDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioProcesoImportacionSantanderImpl implements ServicioProcesoImportacionSantander{

	private static final long serialVersionUID = -2703517858226890647L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioProcesoImportacionSantanderImpl.class);
	
	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;
	
	@Autowired
	BuildExcelSantanderFTP buildExcelSantanderFtp;
	
	@Autowired
	ServicioCorreo servicioCorreo;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoImportacionSantanderBean procesarImportacionSantander() {
		ResultadoImportacionSantanderBean resultado = new ResultadoImportacionSantanderBean(Boolean.FALSE);
		try {
			List<FicheroBean> listaFicheros = rescuperarFicherosFTP();
			if(listaFicheros != null && !listaFicheros.isEmpty()){
				List<FicheroBean> listaFicherosCart = new ArrayList<FicheroBean>();
				List<FicheroBean> listaFicherosRetail = new ArrayList<FicheroBean>();
				Fecha fecha = utilesFecha.getFechaHoraActual();
				ordenarFicheros(resultado,listaFicheros, listaFicherosCart,listaFicherosRetail);
				procesarFicherosImportacion(resultado, listaFicherosCart, listaFicherosRetail);
				if(!resultado.getError()){
					guardarFicherosImportacion(listaFicheros,fecha);
					borrarFicheroFTP(listaFicheros);
					generarExcels(resultado,fecha);
					if(!resultado.getError()){
						enviarCorreoImportacionCart(resultado);
						enviarCorreoImportacionRetail(resultado);
					}
				}
			} else {
				resultado.setError(Boolean.FALSE);
				resultado.setMensajeError("No existen ficheros para importar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la importacion de santander, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de procesar la importacion de santander, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMensajeError1()));
		}
		if(!resultado.getError()){
			resultado.setMensajeError("Ficheros de Importación Procesados correctamente.");
		}
		return resultado;
	}

	private void borrarFicheroFTP(List<FicheroBean> listaFicheros) {
		String nombreFichAltaRetail = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomAltaRetail");
		String nombreFichBajaRetail = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomBajaRetail");
		String nombreFichAltaCart = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomAltaCart");
		String nombreFichBajaCart = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomBajaCart");

		try {
			for (FicheroBean ficheroFTP : listaFicheros) {
				if(ficheroFTP.getNombreDocumento().toUpperCase().contains(nombreFichAltaCart)){
					gestorDocumentos.borrarFicheroSantander(ficheroFTP.getNombreYExtension());
				} else if(ficheroFTP.getNombreDocumento().toUpperCase().contains(nombreFichAltaRetail)){
					gestorDocumentos.borrarFicheroSantander(ficheroFTP.getNombreYExtension());
				} else if(ficheroFTP.getNombreDocumento().toUpperCase().contains(nombreFichBajaRetail)){
					gestorDocumentos.borrarFicheroSantander(ficheroFTP.getNombreYExtension());
				} else if(ficheroFTP.getNombreDocumento().toUpperCase().contains(nombreFichBajaCart)){
					gestorDocumentos.borrarFicheroSantander(ficheroFTP.getNombreYExtension());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void guardarFicherosImportacion(List<FicheroBean> listaFicheros, Fecha fecha) {
		for(FicheroBean ficheroImportacion :listaFicheros){
			ficheroImportacion.setTipoDocumento(ConstantesGestorFicheros.TIPO_DOC_IMPORTACION_DATOS_SENSIBLES);
			ficheroImportacion.setFecha(fecha);
			ficheroImportacion.setNombreDocumento(ficheroImportacion.getNombreDocumento().replace(gestorPropiedades.valorPropertie("documentos.ftp.santander"), ""));
			ficheroImportacion.setSubTipo("IMPORTACION DATOS SENSIBLES");
			try {
				gestorDocumentos.guardarByte(ficheroImportacion);
				if (log.isInfoEnabled()) {
					log.info("Archivo " + ficheroImportacion.getNombreDocumento() + " custodiado correctamente.");
				}
			} catch (OegamExcepcion e) {
				log.error("Ha ocurrido un error custodiando el archivo " + ficheroImportacion.getNombreDocumento() + " de importacion de datos sensibles", e);
			}
		}
	}

	private void enviarCorreoImportacionRetail(ResultadoImportacionSantanderBean resultado) {
		if(resultado.getTextoCorreoRetail() != null){
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("La importacion de los bastidores ha finalizado. "
					+ "Estado del FTP Correcto.<br>"
					+ "Se han encontrado los siguientes archivos:<br><ul>");
			
			texto.append(resultado.getTextoCorreoRetail());
			
			String destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioRetail");
			String destinatarioOculto =  gestorPropiedades.valorPropertie("destinatario.correo.bastidores.oculto");
			
			try {
				ResultBean resultEnvio = servicioCorreo.enviarCorreo(texto.toString(), null, null,  gestorPropiedades.valorPropertie("proceso.santander.email.informe.asunto"), destinatario, null, destinatarioOculto, null, resultado.getFicheroExcelRetail());
				if (resultEnvio.getError()) {
					log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles: " + resultEnvio.getMensaje());
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se ha enviado el correo con el informe de la importacion de datos sensibles: " + resultEnvio.getMensaje());
				}
			} catch (Throwable e) {
				log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles, error: " ,e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha enviado el correo con el informe de la importacion de datos sensibles.");
			}
		}
		
	}

	private void enviarCorreoImportacionCart(ResultadoImportacionSantanderBean resultado) {
		if(resultado.getTextoCorreoCart() != null){
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("La importacion de los bastidores ha finalizado. "
					+ "Estado del FTP Correcto.<br>"
					+ "Se han encontrado los siguientes archivos:<br><ul>");
			
			texto.append(resultado.getTextoCorreoCart());
			
			String destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioCart");
			String destinatarioOculto =  gestorPropiedades.valorPropertie("destinatario.correo.bastidores.oculto");
			String copia = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.copiaDestinatarioCart");
			
			try {
				ResultBean resultEnvio = servicioCorreo.enviarCorreo(texto.toString(), null, null,  gestorPropiedades.valorPropertie("proceso.santander.email.informe.asunto"), destinatario, copia, destinatarioOculto, null, resultado.getFicheroExcelCart());
				if (resultEnvio.getError()) {
					log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles: " + resultEnvio.getMensaje());
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se ha enviado el correo con el informe de la importacion de datos sensibles: " + resultEnvio.getMensaje());
				}
			} catch (Throwable e) {
				log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles, error: " ,e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha enviado el correo con el informe de la importacion de datos sensibles.");
			}
		}
	}

	private void generarExcels(ResultadoImportacionSantanderBean resultado, Fecha fecha) {
		if((resultado.getListaResultadoAltaBastCart() != null && !resultado.getListaResultadoAltaBastCart().isEmpty()) ||
				(resultado.getListaResultadoBajaBastCart() != null && !resultado.getListaResultadoBajaBastCart().isEmpty())){
			String nombreFichero = "Informe_Cart_" + fecha.getAnio() + "_" + fecha.getMes() + "_" + fecha.getDia() + 
					"_" + fecha.getHora() + "_" + fecha.getMinutos() + "_" + fecha.getSegundos();
			buildExcelSantanderFtp.generarExcel(resultado, fecha, nombreFichero, Boolean.FALSE);
			
		} 
		if((resultado.getListaResultadoAltaBastRetail() != null && !resultado.getListaResultadoAltaBastRetail().isEmpty()) ||
				(resultado.getListaResultadoBajaBastRetail() != null && !resultado.getListaResultadoBajaBastRetail().isEmpty())){
			String nombreFichero = "Informe_Retail_" + fecha.getAnio() + "_" + fecha.getMes() + "_" + fecha.getDia() + 
					"_" + fecha.getHora() + "_" + fecha.getMinutos() + "_" + fecha.getSegundos();
			buildExcelSantanderFtp.generarExcel(resultado, fecha, nombreFichero, Boolean.TRUE);
		} 
	}

	private void ordenarFicheros(ResultadoImportacionSantanderBean resultado, List<FicheroBean> listaFicheros, List<FicheroBean> listaFicherosCart, List<FicheroBean> listaFicherosRetail) {
		String nombreFichAltaRetail = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomAltaRetail");
		String nombreFichBajaRetail = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomBajaRetail");
		String nombreFichAltaCart = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomAltaCart");
		String nombreFichBajaCart = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.nomBajaCart");
		int posFichCart = 0;
		int posFichRt = 0;
		for(FicheroBean ficheroCart : listaFicheros){
			if(ficheroCart.getNombreDocumento().toUpperCase().contains(nombreFichAltaCart)){
				listaFicherosCart.add(posFichCart++,ficheroCart);
				resultado.setNombreFichAltaCart(ficheroCart.getNombreDocumento());
				break;
			}
		}
		for(FicheroBean ficheroCart : listaFicheros){
			if(ficheroCart.getNombreDocumento().toUpperCase().contains(nombreFichBajaCart)){
				listaFicherosCart.add(posFichCart++,ficheroCart);
				resultado.setNombreFichBajaCart(ficheroCart.getNombreDocumento());
				break;
			}
		}
		for(FicheroBean ficheroRt : listaFicheros){
			if(ficheroRt.getNombreDocumento().toUpperCase().contains(nombreFichAltaRetail)){
				listaFicherosRetail.add(posFichRt++,ficheroRt);
				resultado.setNombreFichAltaRetail(ficheroRt.getNombreDocumento());
				break;
			}
		}
		for(FicheroBean ficheroRt : listaFicheros){
			if(ficheroRt.getNombreDocumento().toUpperCase().contains(nombreFichBajaRetail)){
				listaFicherosRetail.add(posFichRt++,ficheroRt);
				resultado.setNombreFichBajaRetail(ficheroRt.getNombreDocumento());
				break;
			}
		}
	}

	private void procesarFicherosImportacion(ResultadoImportacionSantanderBean resultado, List<FicheroBean> listaFicheroCart, List<FicheroBean> listaFicheroRetail) throws OegamExcepcion {
		for(FicheroBean ficheroCart : listaFicheroCart){
			getListaBastidoresFichero(ficheroCart,resultado);
			if(!resultado.getError()){
				servicioDatosSensibles.importarBastidoresFTPSantander(ficheroCart,resultado, Boolean.FALSE);
			} else {
				//Mantis #18011. No quieren que se envie un correo cuando el archivo esta vacío
				if(!resultado.getMensajeError().contains("vacio")){
					enviarCorreoImportacionProcesoError(resultado.getMensajeError(), ficheroCart.getNombreYExtension(),Boolean.FALSE);
				}
			}
			procesarResultadoTextoCorreoCart(resultado, ficheroCart.getNombreDocumento());
		}
		for(FicheroBean ficheroRt : listaFicheroRetail){
			getListaBastidoresFichero(ficheroRt,resultado);
			if(!resultado.getError()){
				servicioDatosSensibles.importarBastidoresFTPSantander(ficheroRt,resultado, Boolean.TRUE);
			} else {
				//Mantis #18011. No quieren que se envie un correo cuando el archivo esta vacío
				if(!resultado.getMensajeError().contains("vacio")){
					enviarCorreoImportacionProcesoError(resultado.getMensajeError(), ficheroRt.getNombreYExtension(),Boolean.TRUE);
				}
			}
			procesarResultadoTextoCorreoRetail(resultado, ficheroRt.getNombreDocumento());
		}
	}

	private void procesarResultadoTextoCorreoCart(ResultadoImportacionSantanderBean resultado, String nombreFichero) {
		resultado.addTextoCorreoCart("<li>");
		resultado.addTextoCorreoCart(nombreFichero);
		
		if(resultado.getError() && resultado.getMensajeError() != null && !resultado.getMensajeError().isEmpty()
				&& resultado.getMensajeError().contains("vacio")){
			resultado.addTextoCorreoCart(" no contiene bastidores");
		}else if(resultado.getError() && resultado.getMensajeError() != null && !resultado.getMensajeError().isEmpty()){
			resultado.addTextoCorreoCart(" no ha sido procesado porque se ha producido el siguiente error en la importación: " + resultado.getMensajeError());
		} else if(resultado.getError()){
			resultado.addTextoCorreoCart(" no ha sido procesado porque se ha producido un error en la importación.");
		} else {
			resultado.addTextoCorreoCart(" contiene ");
			resultado.addTextoCorreoCart(resultado.getNumBastidoresCart() +  " bastidores");	
		}
		resultado.addTextoCorreoCart("</li>");
		resultado.setError(Boolean.FALSE);
		resultado.setMensajeError(null);
		
	}
	
	private void procesarResultadoTextoCorreoRetail(ResultadoImportacionSantanderBean resultado, String nombreFichero) {
		resultado.addTextoCorreoRetail("<li>");
		resultado.addTextoCorreoRetail(nombreFichero);
		
		if(resultado.getError() && resultado.getMensajeError() != null && !resultado.getMensajeError().isEmpty()
				&& resultado.getMensajeError().contains("vacio")){
			resultado.addTextoCorreoRetail(" no contiene bastidores");
		}else if(resultado.getError() && resultado.getMensajeError() != null && !resultado.getMensajeError().isEmpty()){
			resultado.addTextoCorreoRetail(" no ha sido procesado porque se ha producido el siguiente error en la importación: " + resultado.getMensajeError());
		} else if(resultado.getError()){
			resultado.addTextoCorreoRetail(" no ha sido procesado porque se ha producido un error en la importación.");
		} else {
			resultado.addTextoCorreoRetail(" contiene ");
			resultado.addTextoCorreoRetail(resultado.getNumBastidoresCart() +  " bastidores");	
		}
		resultado.addTextoCorreoRetail("</li>");
		resultado.setError(Boolean.FALSE);
		resultado.setMensajeError(null);
		
	}

	private void enviarCorreoImportacionProcesoError(String mensaje, String nombreYExtension, Boolean esRetail) throws OegamExcepcion {
		String mensajeEmail = "<br> El fichero " + nombreYExtension + " no se puede importar en la aplicación por el siguiente motivo: <br>  - " + mensaje + "<br>";
		
		String subject = gestorPropiedades.valorPropertie("asunto.correo.datosSensibles.errorFichero");
		String destinatario = null;
		String destinatarioOculto =  gestorPropiedades.valorPropertie("destinatario.correo.bastidores.oculto");
		String copia = null;
		if(!esRetail){
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioCart");
			copia =  gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.copiaDestinatarioCart");
		} else {
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioRetail");
			
		}
		ResultBean resultEnvio;
		try {
			resultEnvio = servicioCorreo.enviarCorreo(mensajeEmail, null, null, subject, destinatario, copia, destinatarioOculto, null, null);
			if(resultEnvio==null || resultEnvio.getError())
					throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);			
		} catch (IOException e) {
				throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);			
		}
	}
	
	private void getListaBastidoresFichero(FicheroBean fichero, ResultadoImportacionSantanderBean resultado) {
		BufferedReader inputAnterior = null;
		String tipoBastidor = null;
		try {
			InputStream fin = fichero.getInputStreamFromBytesOrFile();
			if(fin != null){
				inputAnterior = new BufferedReader(new InputStreamReader(fin, Claves.ENCODING_ISO88591));
				String ln = null;
				if(inputAnterior != null){
					while ((ln = inputAnterior.readLine()) != null) {
						int inicio = 0;
						int iniFin = 1;
						if(ln.length() <= 28 && ln.length()>=11){
							ImportarBastidorDto importarBastidorDto = new ImportarBastidorDto();
							String tipoRegistro = ln.substring(inicio, iniFin).toUpperCase();
							if("A".equals(tipoRegistro) || "B".equals(tipoRegistro)){
								importarBastidorDto.setTipoRegistro(tipoRegistro);
								inicio = iniFin;
								iniFin = 3;
								String tipoImport = ln.substring(inicio, iniFin).toUpperCase();
								importarBastidorDto.setTipoImportado(TipoBastidor.getValorEnumPorPorNombreEnum(tipoImport));
								tipoBastidor = importarBastidorDto.getTipoImportado();
								if(tipoBastidor != null){
									inicio = iniFin;
									iniFin = 8 + iniFin;
									String fechaEnv = ln.substring(inicio, iniFin);
									if(utilesFecha.comprobarFecha(fechaEnv)){
										importarBastidorDto.setFechaEnvio(fechaEnv);
										String bast = ln.substring(iniFin).toUpperCase();
										if(!bast.contains(" ") && bast.length() <= 21){
											importarBastidorDto.setBastidor(bast);
											resultado.addListaBastidoresImportar(importarBastidorDto);
										}else{
											resultado.addListaLineaError("La Linea: " + ln + " contiene un bastidor con un formato incorrecto.");
										}
									}else{
										resultado.addListaLineaError("La Linea: " + ln + " no contiene una fecha correcta.");
									}
								}else{
									resultado.addListaLineaError("La Linea: " + ln + " no contiene un tipo de importación permitida.");
								}
							}else{
								resultado.addListaLineaError("La Linea: " + ln + " no contiene un tipo de registro permitido.");
							}
						}else{
							resultado.addListaLineaError("La Linea: " + ln + " tiene mas caracteres de los permitidos por linea.");
						}
					}
					if(resultado.getListaBastidoresImportar().size() > MAX_LINEAS_FICHERO_IMPORTAR){
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("No se puede importar un fichero con un tamaño superior 5000 lineas.");
					}
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("El fichero que desea importar se encuentra vacio.");
			}
		} catch (FileNotFoundException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("El fichero que desea importar esta vacio o tiene un formato erroneo.");
		} catch (IOException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("El fichero que desea importar esta vacio o tiene un formato erroneo.");
		}finally{
			try {
				if(inputAnterior != null){
					inputAnterior.close();
				}
			} catch (IOException e) {
				log.error("Error a la hora de recuperar el fichero, error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("El fichero que desea importar esta vacio o tiene un formato erroneo.");
			}
		}
		
	}

	private List<FicheroBean> rescuperarFicherosFTP() throws IOException, OegamExcepcion {
		List<FicheroBean> listaFicheros = null;
		
		List<String> listadoDeArchivos = gestorDocumentos.listarFicherosSantander(gestorPropiedades.valorPropertie("documentos.ftp.santander"));
		if(listadoDeArchivos != null && !listadoDeArchivos.isEmpty()){
			listaFicheros = new ArrayList<FicheroBean>();
			for(String nombreFichero : listadoDeArchivos){
				FicheroBean fichero = descargar(nombreFichero);
				if(fichero != null){
					listaFicheros.add(fichero);
				}
			}
		} else {
			listaFicheros = Collections.emptyList();
		}
		return listaFicheros;
	}
	
	public List<String> listarFicherosSantander() throws Exception{
		List<String> fileList = gestorDocumentos.listarFicherosSantander(gestorPropiedades.valorPropertie("documentos.ftp.santander"));
		return fileList;
	}
	
	public FicheroBean descargar(String path) throws OegamExcepcion{
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setNombreYExtension(path);
			fichero.setFicheroByte(gestorDocumentos.transformFiletoByte(gestorDocumentos.buscarFicheroPorUrl(path)));
			return fichero;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hoara de obtener el fichero, error: ",e);
		}
		return null;
	}

	@Override
	public void borrar(String nombreYExtension) {
		gestorDocumentos.borrarFicheroSantander(nombreYExtension);
		
	}

}
