package org.gestoresmadrid.oegam2.modelos.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.Provincias;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaModelosAction extends ActionBase{

	private static final long serialVersionUID = 6446143564255694912L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaModelosAction.class);

	private static final String ALTA_MODELO_600 = "altaModelo600";
	private static final String ALTA_MODELO_601 = "altaModelo601";
	private static final String DESCARGA_DOC = "descargarDoc";

	private Modelo600_601Dto modeloDto;
	private BigDecimal numExpediente;
	private String tipoModelo;
	private String codigo;
	private String tipoFichero;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private File fichero;
	private String ficheroFileName; 
	private String ficheroContentType;
	private String ficheroFileSize;
	private Boolean menorTamMax;
	private String estado;
	private boolean mostrarEscritura;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioNotario servicioNotario;

	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String alta(){
		if (numExpediente != null) {
			ResultBean resultado = obtenerModelo(numExpediente);
			if (resultado.getError()) {
				aniadirMensajeError(resultado);
			}
		} else {
			cargarValoresIniciales();
		}
		return getPaginaPorModelo();
	}

	public String eliminarBien(){
		try {
			if (codigo != null && !codigo.isEmpty()) {
				ResultBean resultado = servicioModelo600_601.eliminarBien(modeloDto.getIdModelo(), codigo, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado == null || !resultado.getError()) {
					addActionMessage("Los bienes se han eliminado correctamente.");
				} else {
					aniadirMensajeError(resultado);
				}
			} else {
				addActionError("Debe seleccionar algún bien para eliminar.");
			}
			ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
			if (resultObtModelo.getError()) {
				aniadirMensajeError(resultObtModelo);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes de la remesa, error: ",e);
			addActionError("Ha sucedido un error a la hora de eliminar los bienes de la remesa.");
			modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}

	public String notarioNuevo() {
		if (codigo != null && !codigo.isEmpty()) {
			NotarioDto notarioDto = servicioNotario.getNotarioPorId(codigo);
			if (notarioDto == null) {
				addActionError("El notario seleccionado no existe.");
			}
			ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
			if (resultObtModelo.getError()) {
				aniadirMensajeError(resultObtModelo);
			}
			modeloDto.setNotario(notarioDto);
		} else {
			addActionError("Debe seleccionar algun notario.");
			modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}

	public String guardarBienes(){
		if (fichero != null && !utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero es erroneo");
			modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
		} else {
			if (codigo != null && !codigo.isEmpty()) {
				String numColegiado = null;
				if (modeloDto.getNumColegiado() != null && !modeloDto.getNumColegiado().isEmpty()) {
					numColegiado = modeloDto.getNumColegiado();
				} else {
					numColegiado = utilesColegiado.getNumColegiadoSession();
				}
				ResultBean resultado = servicioModelo600_601.guardarBienesPantalla(modeloDto, codigo, utilesColegiado.getIdUsuarioSessionBigDecimal(), numColegiado, fichero, ficheroFileName);
				BigDecimal numExpediente = null;
				if (resultado.getError()) {
					addActionError(resultado.getListaMensajes().get(0));
					numExpediente = modeloDto.getNumExpediente();
				} else {
					addActionMessage("Los bienes se han guardado correctamente");
					if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
						addActionMessage("El modelo se ha guardado con los siguientes errores: ");
						for (String mensaje : resultado.getListaMensajes()) {
							addActionError(mensaje);
						}
					}
					numExpediente = (BigDecimal)resultado.getAttachment("numExpediente");
				}
				ResultBean resultObtModelo = obtenerModelo(numExpediente);
				if (resultObtModelo.getError()) {
					aniadirMensajeError(resultObtModelo);
				}
			} else {
				addActionError("Debe seleccionar algun bien para poder guardarlo");
				modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
			}
		}
		return getPaginaPorModelo();
	}

	public String guardar(){
		try {
			if (fichero != null && !comprobarFicheroValido(fichero, ficheroFileName)) {
				addActionError("No es un archivo PDF, su tamaño es superior a 20MB o no es un nombre válido");
				fichero = null;
				ficheroFileName = "";
			} else {
				ResultBean resultado = servicioModelo600_601.guardarModelo(modeloDto,
						utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
						fichero, ficheroFileName, mostrarEscritura);
				BigDecimal numExpediente = null;

				if (resultado.getError()) {
					aniadirMensajeError(resultado);
					tipoModelo = modeloDto.getModelo().getModelo();
					cargarValoresIniciales();
				} else {
					gestionarMensajesResult(resultado, "El modelo se ha guardado correctamente",
							"El modelo se ha guardado con los siguientes errores: ");
					numExpediente = (BigDecimal) resultado.getAttachment("numExpediente");
					ResultBean resultObtModelo = obtenerModelo(numExpediente);
					if (resultObtModelo.getError()) {
						aniadirMensajeError(resultObtModelo);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el modelo de pantalla, error: ", e);
			addActionError("Ha sucedido un error a la hora de guardar el modelo");
			modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
		}
		mostrarEscritura = false;
		return getPaginaPorModelo();
	}

	public String validarModelo() {
		if (fichero != null && !utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero es erroneo");
			tipoModelo = modeloDto.getModelo().getModelo();
			cargarValoresIniciales();
		} else {
			ResultBean resultGuardarMod = servicioModelo600_601.guardarModelo(modeloDto, modeloDto.getNumColegiado(), utilesColegiado.getIdUsuarioSessionBigDecimal(), fichero, ficheroFileName,false);
			if (!resultGuardarMod.getError()) {
				ResultBean resultBean = servicioModelo600_601.gestionarConceptoModelo((Long)resultGuardarMod.getAttachment("idModelo"));
				if (resultBean != null && resultBean.getError()) {
					aniadirMensajeError(resultBean);
					tipoModelo = modeloDto.getModelo().getModelo();
					cargarValoresIniciales();
				} else {
					ResultBean resultValidar = servicioModelo600_601.validarModelo(modeloDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
					gestionarMensajesResult(resultValidar, "El modelo se ha validado correctamente.","El modelo se ha validado correctamente con las siguientes advertencias: ");
					ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
					List<String> listaTipos = Arrays.asList("TU1", "TU2", "TU5", "TU6" , "TR0", "PH1", "PH2", "PH3", "PH4");
					if ((modeloDto.getNombreEscritura() == null || modeloDto.getNombreEscritura().isEmpty())
							&& !listaTipos.contains(modeloDto.getConcepto().getConcepto())) {
						resultObtModelo.getListaMensajes().add("No se ha subido escritura");
						resultObtModelo.setError(true);
					}
					if (resultObtModelo.getError()) {
						aniadirMensajeError(resultObtModelo);
					}
				}
			} else {
				aniadirMensajeError(resultGuardarMod);
				tipoModelo = modeloDto.getModelo().getModelo();
				cargarValoresIniciales();
			}
		}
		return getPaginaPorModelo();
	}

	private void gestionarMensajesResult(ResultBean resultBean, String mensajeOk, String mensajeOkErrores) {
		if (resultBean == null || !resultBean.getError()) {
			if (resultBean.getListaMensajes() != null && !resultBean.getListaMensajes().isEmpty()) {
				addActionMessage(mensajeOkErrores);
				for(String mensaje : resultBean.getListaMensajes()){
					addActionMessage(mensaje);
				}
			} else {
				addActionMessage(mensajeOk);
			}
		} else {
			aniadirMensajeError(resultBean);
		}

	}

	public String autoLiquidar(){
		if (fichero != null && !comprobarFicheroValido(fichero, ficheroFileName)){
			addActionError("No es un archivo PDF, su tamaño es superior a 20MB o tiene un nombre incorrecto");
		} else {
			ResultBean resultAutoLiq = servicioModelo600_601.autoLiquidarModelo(modeloDto.getNumExpediente(),utilesColegiado.getIdUsuarioSessionBigDecimal(), modeloDto);
			gestionarMensajesResult(resultAutoLiq, "El modelo se ha autoliquidado correctamente.", "El modelo se ha autoliquidado correctamente con las siguientes advertencias: ");
			ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
			if (resultObtModelo.getError()) {
				aniadirMensajeError(resultObtModelo);
			}
		}
		return getPaginaPorModelo();
	}

	public String calcularLiquidacion() {
		ResultBean resultAutoLiq = servicioModelo600_601.calcularLiquidacion(modeloDto);
		gestionarMensajesResult(resultAutoLiq, "El modelo se ha autoliquidado correctamente.", "El modelo se ha autoliquidado correctamente con las siguientes advertencias: ");
		ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
		if (resultObtModelo.getError()) {
			aniadirMensajeError(resultObtModelo);
		}
		return getPaginaPorModelo();
	}

	public String presentar() {
		if (fichero != null && !comprobarFicheroValido(fichero, ficheroFileName)) {
			addActionError("No es un archivo PDF, su tamaño es superior a 20MB o tiene un nombre incorrecto");
		} else {
			try {
				ResultBean resultPresentar = servicioModelo600_601.presentar(modeloDto.getNumExpediente(), modeloDto.getDatosBancarios(),utilesColegiado.getIdUsuarioSessionBigDecimal(), fichero, ficheroFileName);
				if (resultPresentar.getError()) {
					aniadirMensajeError(resultPresentar);
					servicioModelo600_601.borrarXmlPresentacionSiexiste((String)resultPresentar.getAttachment("nombreXml"),modeloDto.getNumExpediente());
				} else {
					addActionMessage("Solicitud de presentación de modelo generada.");
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de presentar el modelo, error: ", e);
				addActionError("Ha sucedido un error a la hora de presentar el modelo");
				servicioModelo600_601.borrarXmlPresentacionSiexiste(ServicioModelo600_601.NOMBRE_FICHERO + modeloDto.getNumExpediente(), modeloDto.getNumExpediente());
			}
		}
		ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
		if (resultObtModelo.getError()) {
			aniadirMensajeError(resultObtModelo);
		}
		return getPaginaPorModelo();
	}

	private boolean comprobarFicheroValido(File fichero, String ficheroFileName) {
		boolean esCorrecto = true;
		String ext = FilenameUtils.getExtension(ficheroFileName);
		if (!utiles.esNombreFicheroValido(ficheroFileName) || !"pdf".equals(ext) || fichero.length() > 20971520) {
			esCorrecto = false;
		}
		return esCorrecto;
	}

	private ResultBean obtenerModelo(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		if (numExpediente != null) {
			resultado = servicioModelo600_601.getModeloDto(numExpediente, null, Boolean.TRUE);
			if (resultado != null && !resultado.getError()) {
				modeloDto = (Modelo600_601Dto) resultado.getAttachment("modeloDto");
			} else if (resultado == null || resultado.getError()) {
				modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
			}
		} else {
			modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(modeloDto.getModelo().getModelo())));
		}
		return resultado;
	}

	private void cargarValoresIniciales() {
		if (modeloDto == null) {
			modeloDto = new Modelo600_601Dto();
		}
		// Se carga directo Madrid 
		OficinaLiquidadoraDto oficinaLiquidadora = new OficinaLiquidadoraDto();
		oficinaLiquidadora.setIdProvincia(String.valueOf(Provincias.Madrid.getValorEnum()));
		modeloDto.setOficinaLiquidadora(oficinaLiquidadora);
		modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(tipoModelo)));
		modeloDto.setPresentador(servicioModelo600_601.getPresentadorContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal())));
		modeloDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		modeloDto.setContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal()));
	}

	public String imprimirDoc(){
		ResultBean resultado = null;
		String pagina = "";
		try {
			resultado = servicioModelo600_601.imprimirDocumento(modeloDto.getNumExpediente(), codigo,tipoFichero);
			if (!resultado.getError()) {
				inputStream = new FileInputStream((File)resultado.getAttachment("fichero"));
				fileName = (String)resultado.getAttachment("nombreFichero");
				pagina = DESCARGA_DOC;
			} else {
				aniadirMensajeError(resultado);
				ResultBean resultModelo = obtenerModelo(modeloDto.getNumExpediente());
				if (resultModelo.getError()) {
					aniadirMensajeError(resultModelo);
				}
				pagina = getPaginaPorModelo();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir la " + TipoFicheros.convertirTexto(codigo) + " error: ", e);
		}
		return pagina;
	}

	private String getPaginaPorModelo(){
		String pagina = "";
		if (Modelo.Modelo600.getValorEnum().equals(modeloDto.getModelo().getModelo())) {
			pagina = ALTA_MODELO_600;
		} else if (Modelo.Modelo601.getValorEnum().equals(modeloDto.getModelo().getModelo())) {
			pagina = ALTA_MODELO_601;
		}
		return pagina;
	}

	public String verEscritura() {
		modeloDto.setEstado(estado);
		if (modeloDto.getNombreEscritura().trim().isEmpty()) {
			mostrarEscritura = true;
			guardar();
			if (fichero != null) {
				try {
					inputStream = new FileInputStream(fichero);
					return DESCARGA_DOC;
				} catch (FileNotFoundException e) {
					addActionMessage("Error al recuperar el fichero");
				}
			} else {
				addActionMessage("No se ha subido escritura");
			}
		} else {
			ResultBean resultado = servicioModelo600_601.imprimirDocumento(modeloDto.getNumExpediente(), null, "3");
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream((File)resultado.getAttachment("fichero"));
					fileName = (String)resultado.getAttachment("nombreFichero");
					return DESCARGA_DOC;
				} catch (FileNotFoundException e) {
					log.error("Ha sucedido un error a la hora de imprimir la " + TipoFicheros.convertirTexto(codigo) + " error: ", e);
					addActionMessage("Error al imprimir la escritura");
				}
			} else {
				aniadirMensajeError(resultado);
				ResultBean resultModelo = obtenerModelo(modeloDto.getNumExpediente());
				if (resultModelo.getError()) {
					aniadirMensajeError(resultModelo);
				}
			}
		}
		ResultBean resultObtModelo = obtenerModelo(modeloDto.getNumExpediente());
		if (resultObtModelo.getError()) {
			aniadirMensajeError(resultObtModelo);
		}
		return getPaginaPorModelo();
	}

	public Modelo600_601Dto getModeloDto() {
		return modeloDto;
	}

	public void setModeloDto(Modelo600_601Dto modeloDto) {
		this.modeloDto = modeloDto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(String tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoFichero() {
		return tipoFichero;
	}

	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getFicheroFileName() {
		return ficheroFileName;
	}

	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	public String getFicheroFileSize() {
		return ficheroFileSize;
	}

	public void setFicheroFileSize(String ficheroFileSize) {
		this.ficheroFileSize = ficheroFileSize;
	}

	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	public void setMenorTamMax(Boolean menorTamMax) {
		this.menorTamMax = menorTamMax;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the guardarEscritura
	 */
	public boolean isGuardarEscritura() {
		return mostrarEscritura;
	}

	/**
	 * @param guardarEscritura the guardarEscritura to set
	 */
	public void setGuardarEscritura(boolean guardarEscritura) {
		this.mostrarEscritura = guardarEscritura;
	}

}