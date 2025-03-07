package org.gestoresmadrid.oegam2.trafico.canjes.controller.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.ServicioGestionCanjes;
import org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans.ResultadoCanjesBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class GestionarCanjesAction extends ActionBase{

	private static final long serialVersionUID = 3977115764770907187L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionarCanjesAction.class);
	
	private List<CanjesDto> listado;
	
	 private Map<String, Object> responseJson;
	 
	private Map<String, Object> session;
	
	private CanjesDto canjesDto;
	
	String idCanje;
	
	private String dninie;
	
	private TramiteTrafMatrDto trafMatrDto;
	
	@Autowired
	ServicioGestionCanjes servicioGestionCanjes;
	
	@Autowired
	UtilesColegiado utilesColegiado;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioImpresion servicioImpresion;
	
	String fileName;
	InputStream inputStream;

	
	public String anadirCanje() {
		try {
			ResultadoCanjesBean resultado = servicioGestionCanjes.anadirCanje(canjesDto,listado);
			
			if (!resultado.getError()) {
				addActionMessage("Se ha añadido un registro de canje.");
				listado = resultado.getListaCanjes();
			}else {
				if(resultado.getListaMensajes() != null) {
					for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
						addActionError(resultado.getListaMensajes().get(i));
					}
				}else {
					addActionError(resultado.getMensaje());
				}
				
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de añadir un canje, error: ", e);
			addActionError("Ha sucedido un error a la hora de añadir un canje.");
		}
		
		return SUCCESS;
	}
	
	public String imprimirCanje() throws OegamExcepcion, JsonGenerationException, JsonMappingException, IOException {
	    responseJson = new HashMap<>();
	    try {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        BufferedReader reader = request.getReader();
	        StringBuilder requestBody = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            requestBody.append(line);
	        }
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(requestBody.toString());
	        JsonNode listadoNode = jsonNode.get("listado");
	        List<CanjesDto> listadoCanjes = new ArrayList<>();
	        for (int i = 0; i < listadoNode.size(); i++) {
	            CanjesDto canje = objectMapper.treeToValue(listadoNode.get(i), CanjesDto.class);
	            listadoCanjes.add(canje);
	        }
	        ResultadoCanjesBean resultado = servicioGestionCanjes.imprimirCanje(listadoCanjes);
	        if (!resultado.getError()) {
	            byte[] archivo = (byte[]) resultado.getAttachment(ResultBean.TIPO_PDF);
	            String nombreArchivo = (String) resultado.getAttachment(ResultadoCanjesBean.NOMBRE_FICHERO);
	            fileName = new SimpleDateFormat("ddMMyy_HHmmss.S").format(new Date()) + "_" + nombreArchivo;
	            boolean guardado = guardarFichero(archivo, getFileName());

	            if (!guardado) {
	                resultado.setMensaje("Existen problemas al imprimir. Inténtelo más tarde");
	                resultado.setError(Boolean.TRUE);
	            } else {
	                ResultadoCanjesBean resultCreditos = servicioGestionCanjes.descontarCreditos(listadoCanjes);
	                if (!resultCreditos.getError()) {
	                    resultado.setMensaje("La impresión se realizó correctamente");
	                    addActionMessage("Se han impreso el listado de canjes.");
	                    resultado = servicioGestionCanjes.recuperarFichero(fileName, listadoCanjes);
	                    inputStream = new FileInputStream(resultado.getFichero());
	                    fileName = resultado.getNombreArchivoDescarga();

	                    // Agregar el nombre de archivo a la respuesta JSON
	                    responseJson.put("filename", fileName);
	                    responseJson.put("status", "success");
	                    return "descargarCanje";
	                } else {
	                	ResultadoCanjesBean result = servicioGestionCanjes.modificarImpresion(listadoCanjes);
	                	if(!result.getError()) {
		                    if (resultCreditos.getListaMensajes() != null) {
		                        responseJson.put("messages", resultCreditos.getListaMensajes());
		                    } else {
		                        responseJson.put("messages", Arrays.asList(resultCreditos.getMensaje()));
		                    }
	                	}
	                    responseJson.put("status", "error");
	                }
	            }
	        } else {
	            // Manejar los errores generales
	            addActionError(resultado.getMensaje());
	            responseJson.put("messages", getActionErrors());
	            responseJson.put("status", "error");
	        }
	    } catch (Exception e) {
	        // Manejar las excepciones
	        log.error("Ha sucedido un error a la hora de imprimir un canje, error: ", e);
	        addActionError("Ha sucedido un error a la hora de imprimir un canje.");
	        responseJson.put("messages", Arrays.asList("Ha sucedido un error a la hora de imprimir un canje."));
	        responseJson.put("status", "error");
	    }

	    // Devolver la respuesta JSON
	    HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write(new ObjectMapper().writeValueAsString(responseJson));
	    return null;
	}
	
	
	public String eliminarRegistroCanje(){
		if(dninie != null) {
			addActionMessage("El registro para el DNI/NIE " + dninie + " se ha eliminado correctamente."  );
		}
		return SUCCESS;
	}
	
	private boolean guardarFichero(byte[] archivo, String fileName) {
		boolean resultado = false;
		if (archivo != null) {
			FicheroBean fichero = new FicheroBean();
			fichero.setFicheroByte(archivo);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPRESION);
			fichero.setSubTipo(ConstantesGestorFicheros.LISTADO_CANJES);
			fichero.setNombreDocumento(fileName);
			fichero.setExtension("");

			try {
				File f = gestorDocumentos.guardarByte(fichero);
				resultado = f != null && f.exists();
			} catch (OegamExcepcion e) {
				log.error("Error guardando el fichero. No se podrá recuperar", e);
			}
		}
		return resultado;
	}
	public String inicio() {
		canjesDto = new CanjesDto();
		return SUCCESS;
	}
	public CanjesDto getCanjesDto() {
		return canjesDto;
	}

	public void setCanjesDto(CanjesDto canjesDto) {
		this.canjesDto = canjesDto;
	}

	public TramiteTrafMatrDto getTrafMatrDto() {
		return trafMatrDto;
	}

	public void setTrafMatrDto(TramiteTrafMatrDto trafMatrDto) {
		this.trafMatrDto = trafMatrDto;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getIdCanje() {
		return idCanje;
	}

	public void setIdCanje(String idCanje) {
		this.idCanje = idCanje;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public List<CanjesDto> getListado() {
		return listado;
	}
	public void setListado(List<CanjesDto> listado) {
		this.listado = listado;
	}

	public String getDninie() {
		return dninie;
	}

	public void setDninie(String dninie) {
		this.dninie = dninie;
	}

	public Map<String, Object> getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(Map<String, Object> responseJson) {
		this.responseJson = responseJson;
	}

}
