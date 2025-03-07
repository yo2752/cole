package org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.trafico.canjes.model.vo.CanjesVO;
import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.ServicioGestionCanjes;
import org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.ServicioPersistenciaCanjes;
import org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans.ResultadoCanjesBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioGestionCanjesImpl implements ServicioGestionCanjes{

	private static final long serialVersionUID = 1270314136112017970L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionCanjesImpl.class);
	
	private static final String CABECERA = "canje";
	
	private Map<String, Object> session;
	
	@Autowired
	ServicioPersistenciaCanjes servicioPersistenciaCanjes;
	
	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	UtilesColegiado utilesColegiado;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Override
	public ResultadoCanjesBean anadirCanje(CanjesDto canjesDto,List<CanjesDto> listado) {
	    ResultadoCanjesBean result = comprobarDatosCanje(canjesDto);

	    if (!result.getError()) {
	    	if(listado == null) {
				listado = new ArrayList<>();
			}
	        if (listado.size() <= MAX_REGISTROS) {
	            listado.add(canjesDto);
	            result.setMensaje("Se ha añadido un registro de canje.");
	        } else {
	            result.setMensaje("Se ha alcanzado el límite máximo de canjes.");
	            result.setError(true);
	        }
	    }
	    result.setListaCanjes(listado);
	    return result;
	}
	

	private ResultadoCanjesBean comprobarDatosCanje(CanjesDto canjesDto) {
		ResultadoCanjesBean result = new ResultadoCanjesBean(Boolean.FALSE);
		String fechaRegex = "^\\d{2}/\\d{2}/\\d{4}$";
	    Pattern pattern = Pattern.compile(fechaRegex);
		if(StringUtils.isBlank(canjesDto.getDninie())){
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Debe indicar el DNI/NIE de la persona.");
		}
		if (StringUtils.isBlank(canjesDto.getFechaExp())) {
	        result.setError(Boolean.TRUE);
	        result.addMensajeALista("Debe indicar la fecha de expedición.");
	    } else if (!pattern.matcher(canjesDto.getFechaExp()).matches()) {
	        result.setError(Boolean.TRUE);
	        result.addMensajeALista("El formato de la fecha de expedición debe ser dd/MM/aaaa.");
	    }
		 if (StringUtils.isBlank(canjesDto.getFechaNac())) {
		        result.setError(Boolean.TRUE);
		        result.addMensajeALista("Debe indicar la fecha de nacimiento.");
		    } else if (!pattern.matcher(canjesDto.getFechaNac()).matches()) {
		        result.setError(Boolean.TRUE);
		        result.addMensajeALista("El formato de la fecha de nacimiento debe ser dd/MM/aaaa.");
		    }
		if(StringUtils.isBlank(canjesDto.getLugarExp())){
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Debe indicar el lugar de expedición.");
		}
		if(StringUtils.isBlank(canjesDto.getNombreapell())){
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Debe indicar el nombre y los apellidos de la persona.");
		}
		if(StringUtils.isBlank(canjesDto.getNumCarnet())){
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Debe indicar el número de carnet.");
		}
		if(StringUtils.isBlank(canjesDto.getPais())){
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Debe indicar el país.");
		}
		
		return result;
	}

	@Override
	public ResultadoCanjesBean imprimirCanje(List<CanjesDto> listado) throws Exception {
		ResultadoCanjesBean result = new ResultadoCanjesBean(Boolean.FALSE);
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		try {
			String idCanje = servicioPersistenciaCanjes.generarIdCanje();
			for (CanjesDto canjesDto : listado) {
					canjesDto.setIdCanje(idCanje);
					canjesDto.setNumColegiado(numColegiado);
					ResultadoCanjesBean resultGuardar = servicioPersistenciaCanjes.guardarCanje(canjesDto);
				 if(resultGuardar.getError()) {
					 result.setMensaje("Ha sucedido un error a la hora de guardar el canje");
					 result.setError(Boolean.TRUE);
				 }else {
					result.setMensaje(idCanje);
				 }
			}
			if(listado != null && !listado.isEmpty()) {
				byte[] bytePdf = imprimir(listado);
				if (bytePdf != null) {
					result.addAttachment(ResultadoCanjesBean.TIPO_PDF, bytePdf);
					result.addAttachment(ResultadoCanjesBean.NOMBRE_FICHERO, "Listado_Canjes.zip");
					result.setPdf(bytePdf);
					
				}else {
					result.setError(Boolean.TRUE);
					result.setMensaje("No se ha generado nada para imprimir.");
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("Sin trámites para realizar la impresión.");
			}
		
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el listado, error: ",e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de imprimir el listado.");
		}
		
		return result;
	}
	
	@Override
	public ResultadoCanjesBean modificarImpresion(List<CanjesDto> listadoCanjes) {
		ResultadoCanjesBean result = new ResultadoCanjesBean(Boolean.FALSE);
		try {
			for (CanjesDto canjesDto : listadoCanjes) {
				ResultadoCanjesBean resultModificar = servicioPersistenciaCanjes.modificarImpresion(canjesDto);
				if(resultModificar.getError()) {
					result.setError(Boolean.TRUE);
					result.setMensaje("Ha sucedido un error a la hora de  poner a NO el campo Impreso.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de poner a NO el campo Impreso, error: ",e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de  poner a NO el campo Impreso.");
		}
		return result;
	}
	
	@Override
	public ResultadoCanjesBean recuperarFichero(String fileName,List<CanjesDto> listadoCanjes) throws OegamExcepcion {
		ResultadoCanjesBean result = new ResultadoCanjesBean(Boolean.FALSE);
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		String numColegiado = listadoCanjes.get(0).getNumColegiado();
		try {
			url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
			File ficheroDestino = new File(url);
			out = new FileOutputStream(ficheroDestino);
			zip = new ZipOutputStream(out);
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPRESION, ConstantesGestorFicheros.LISTADO_CANJES, null, fileName, null);
			if (fichero != null && fichero.getFile() != null) {
				
				addZipEntryFromFile(zip, fichero.getFile(), fileName);
			}
			zip.close();
			if (!result.getError()) {
				File fich = new File(url);
				result.setNombreFichero("Canjes_" + numColegiado + System.currentTimeMillis() + ConstantesGestorFicheros.EXTENSION_ZIP);
				result.setFichero(fich);
				result.setNombreArchivoDescarga(fileName);
			}
		} catch (Exception e) {
			log.error("Existe un error al tratar de descargar el fichero.", e);
			result.setMensaje("Existe un error al tratar de descargar el fichero.");
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
    
	private void addZipEntryFromFile(ZipOutputStream zip, File file, String fileName) {
		 if (file != null && file.exists()) {
		        FileInputStream is = null;
		        try {
		            is = new FileInputStream(file);
		            if (isZipFile(file)) {
		                extractZipContents(zip, is);
		            } else {
		                ZipEntry zipEntry = new ZipEntry(fileName);
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
		            }
		        } catch (IOException e) {
		            log.error("Error al añadir una entrada al zip del fichero: " + file.getName(), e);
		        } finally {
		            if (is != null) {
		                try {
		                    is.close();
		                } catch (IOException e) {
		                    log.error("Error cerrando FileInputStream", e);
		                }
		            }
		        }
		    }
	}
		 private boolean isZipFile(File file) {
			    return file.getName().toLowerCase().endsWith(".zip");
		}
		 private void extractZipContents(ZipOutputStream zip, InputStream inputStream) throws IOException {
			    try (ZipInputStream zipInput = new ZipInputStream(inputStream)) {
			        ZipEntry entry;
			        while ((entry = zipInput.getNextEntry()) != null) {
			            zip.putNextEntry(new ZipEntry(entry.getName()));
			            byte[] buffer = new byte[2048];
			            int bytesRead;
			            while ((bytesRead = zipInput.read(buffer)) != -1) {
			                zip.write(buffer, 0, bytesRead);
			            }
			            zip.closeEntry();
			        }
			    }
			} 

	@Override
	public ResultadoCanjesBean descontarCreditos(List<CanjesDto> listadoCanjes) {
		ResultadoCanjesBean result = new ResultadoCanjesBean(Boolean.FALSE);
		 int numCreditos = listadoCanjes.size();
			ResultBean resultCreditos = servicioCredito.cargoCreditos("CT11", utilesColegiado.getIdContratoSessionBigDecimal(), -numCreditos, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultCreditos != null && resultCreditos.getError()) {
				result.setListaMensajes(resultCreditos.getListaMensajes());
				result.setError(Boolean.TRUE);
				log.error("ERROR DESCONTAR CREDITOS CANJES");
			}
		return result;
	}

	private byte[] imprimir(List<CanjesDto> listado) throws ParserConfigurationException, JRException {
		Map<String, Object> params = obtenerParametros(listado);
		String xmlDatos = generaXML(listado);
		byte [] pdfListadoColegio = generarInforme(getRuta(), getNombreInformeColegio(), "pdf", xmlDatos, CABECERA, params, null);
		byte [] pdfListadoColegiado = generarInforme(getRuta(), getNombreInformeColegiado(), "pdf", xmlDatos, CABECERA, params, null);
		
		return agregarPDFsAZip(pdfListadoColegio, pdfListadoColegiado);
	}

	private byte[] agregarPDFsAZip(byte[] pdfListadoColegio, byte[] pdfListadoColegiado) {
		 try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
	             ZipOutputStream zipOut = new ZipOutputStream(baos)) {

	            // Agregar el primer PDF al archivo ZIP
	            agregarPDFAZip(pdfListadoColegio, "InformeColegio.pdf", zipOut);

	            // Agregar el segundo PDF al archivo ZIP
	            agregarPDFAZip(pdfListadoColegiado, "InformeColegiado.pdf", zipOut);

	            zipOut.finish();
	            return baos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	private void agregarPDFAZip(byte[] pdf, String nombrePDF, ZipOutputStream zipOut) throws IOException {
		try {
			 zipOut.putNextEntry(new ZipEntry(nombrePDF));
		        zipOut.write(pdf);
		        zipOut.closeEntry();
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de agregar pdf al ZIP, error: ",e);
		}
		 
		
	}

	private byte[] generarInforme(String ruta, String nombreInforme, String formato, String xmlDatos,
			String cabecera, Map<String, Object> params, Object object) throws ParserConfigurationException, JRException {
		ReportExporter re = new ReportExporter();
        return re.generarInforme(ruta, nombreInforme, formato, xmlDatos, cabecera, params, null);
	}

	private String generaXML(List<CanjesDto> listado) {
		String xml = "";

		xml = "<?xml version='1.0' encoding='UTF-8' standalone='no' ?> <" + CABECERA + "s>";
		
		for (CanjesDto canjesDto : listado) {
			xml = xml + "<" + CABECERA + ">";
			xml = xml + "<dninie>";
			xml = xml + canjesDto.getDninie();
			xml = xml + "</dninie>";
			xml = xml + "<nombreapell>";
			xml = xml + canjesDto.getNombreapell();
			xml = xml + "</nombreapell>";
			xml = xml + "<fechanac>";
			xml = xml + canjesDto.getFechaNac();
			xml = xml + "</fechanac>";
			xml = xml + "<numcarnet>";
			xml = xml + canjesDto.getNumCarnet();
			xml = xml + "</numcarnet>";
			xml = xml + "<categorias>";
			xml = xml + canjesDto.getCategorias();
			xml = xml + "</categorias>";
			xml = xml + "<fechaexp>";
			xml = xml + canjesDto.getFechaExp();
			xml = xml + "</fechaexp>";
			xml = xml + "<lugarexp>";
			xml = xml + canjesDto.getLugarExp();
			xml = xml + "</lugarexp>";
			xml = xml + "<pais>";
			xml = xml + canjesDto.getPais();
			xml = xml + "</pais>";
			xml = xml + "</" + CABECERA + ">";
		}

		xml = xml + "</" + CABECERA + "s>";
		return xml;
	}

	private Map<String, Object> obtenerParametros(List<CanjesDto> listado) {
		Map<String, Object> params = new HashMap<>();
		params.put("IMG_DIR", getRuta());
		params.put("GESTOR", listado.get(0).getNumColegiado() + "");
		params.put("TOTAL_CANJES", listado.size() + "");
		params.put("FECHA_PRESENTACION", utilesFecha.getFechaHoy());

		return params;
	}

	private String getRuta() {
		return gestorPropiedades.valorPropertie("canje.plantillas");
	}
	
	private String getNombreInformeColegio() {
		return gestorPropiedades.valorPropertie("canjes.plantillas.relacionCanjes");
	}
	private String getNombreInformeColegiado() {
		return gestorPropiedades.valorPropertie("canjes.plantillas.relacionCanjes.gestoria");
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
