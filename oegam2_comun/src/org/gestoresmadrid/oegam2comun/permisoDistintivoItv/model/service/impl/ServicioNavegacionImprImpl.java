package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioNavegacionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.jfree.util.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.google.common.collect.Lists;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Scope("prototype")
public class ServicioNavegacionImprImpl implements ServicioNavegacionImpr {

	private static final long serialVersionUID = -8181624194214524751L;

	public static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioNavegacionImprImpl.class);

	HashMap<String, byte[]> pdfFichas = new HashMap<String, byte[]>();

	HashMap<String, byte[]> pdfPermisos = new HashMap<String, byte[]>();

	byte[] pdfDemanda = null;

	String ventanaPrincipal = new String();

	String ventanaSecundaria = new String();

	String idPdf = new String();

	WebDriver driver = null;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoPermisoDistintivoItvBean iniciarNavegador(ResultadoPermisoDistintivoItvBean resultado, String tipo) {
		try {
			// Iniciamos el navegador
			driver = new HtmlUnitDriver(true);
			Field webClient = driver.getClass().getDeclaredField("webClient");
			webClient.setAccessible(true);
			WebClientOptions webClientOptions = ((WebClient) webClient.get(driver)).getOptions();
			webClientOptions.setUseInsecureSSL(true);
			webClientOptions.setThrowExceptionOnScriptError(false);
			webClientOptions.setJavaScriptEnabled(true);
			addHandlerDemanda((WebClient) webClient.get(driver));
			driver.get(gestorPropiedades.valorPropertie(EITV_URL_LOGIN));
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de iniciar el navegador, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de iniciar el navegador."));
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean doLogin(ResultadoPermisoDistintivoItvBean resultado, String jefatura) {
		try {
			if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_MADRID));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_MADRID));
			} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_ALCORCON));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_ALCORCON));
			} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_ALCALA));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_ALCALA));
			} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_AVILA));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_AVILA));
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_CDREAL));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_CDREAL));
			} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_CUENCA));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_CUENCA));
			} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_SEGOVIA));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_SEGOVIA));
			} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura)) {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_GUADALAJARA));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_GUADALAJARA));
			} else {
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER));
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS));
			}
			driver.findElement(By.id(gestorPropiedades.valorPropertie(ETIV_LOGIN_FORM_SUBMIT))).click();
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de realizar el Login, error: ", e);
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de realizar el Login."));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean comprobarSessionActivaYSegundoLogin(ResultadoPermisoDistintivoItvBean resultado, String jefatura) {
		try {
			driver.manage().getCookies();
			Cookie cookie = new Cookie.Builder("DSheckBrowser", "activex").domain("movilidad.dgt.es").isSecure(true).path("/").build();
			driver.manage().addCookie(cookie);
			Boolean existeErrorDdl = Boolean.FALSE;
			String textoBoton = "";
			if (driver.getPageSource().contains("hacer clic aquí")) {
				existeErrorDdl = Boolean.TRUE;
				textoBoton = "hacer clic aquí";
			} else if (driver.getPageSource().contains("here")) {
				existeErrorDdl = Boolean.TRUE;
				textoBoton = "here";
			}
			if (existeErrorDdl) {
				driver.findElement(By.partialLinkText(textoBoton)).click();
				int i = 0;
				do {
					Thread.sleep(500);
					i++;
				}
				while (driver.findElements(By.partialLinkText(textoBoton)).size() > 1 || i < 120);

				driver.findElements(By.partialLinkText(textoBoton)).get(2).click();
				if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))) {
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_MADRID));
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_MADRID));
					driver.findElement(By.id(gestorPropiedades.valorPropertie(ETIV_LOGIN_FORM_SUBMIT))).click();
				}
			}
		} catch (Exception e) {
			LOG.error(driver.getPageSource());
			LOG.error("Ha sucedido un error a la hora de comprobar el error de la dll, error: ", e);
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de comprobar el error de la dll."));
			resultado.setError(Boolean.TRUE);
		}
		if (!resultado.getError()) {
			try {
				if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_LOGIN_CHECK_SESION))) {
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("document.getElementById(\"postfixSID_1\").checked = true;");
					jse.executeScript("document.getElementById(\"btnContinue\").disabled = false;");
					jse.executeScript("document.getElementById(\"btnContinue\").click();");
					jse.executeScript("checkSelected();");
					if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_LOGIN_CHECK_SESION))) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido cerrar la sesión anterior");
					}
				}
			} catch (Exception e) {
				LOG.error("Ha sucedido un error a la hora de comprobar la session activa, error: ", e);
				resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de comprobar la session activa."));
				resultado.setError(Boolean.TRUE);
			}
			if (!resultado.getError()) {
				try {
					if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_TEXT_WEB_IMPRESION))) {
						driver.findElement(By.partialLinkText(gestorPropiedades.valorPropertie(EITV_TEXT_WEB_IMPRESION))).click();
						cambiarVentana(driver);

						if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_MADRID));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_MADRID));
						} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_ALCORCON));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_ALCORCON));
						} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_ALCALA));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_ALCALA));
						} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_AVILA));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_AVILA));
						} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_CDREAL));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_CDREAL));
						} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_CUENCA));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_CUENCA));
						} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_SEGOVIA));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_SEGOVIA));
						} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura)) {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER_GUADALAJARA));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS_GUADALAJARA));
						} else {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_USER));
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_PASS))).sendKeys(gestorPropiedades.valorPropertie(EITV_LOGIN_PASS));
						}
						driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_SUBMIT))).submit();
					} else {
						resultado.setMensaje("No se encuentra el link de WEB-IMPR para poder continuar.");
						resultado.setError(Boolean.TRUE);
					}
				} catch (Exception e) {
					LOG.error("Ha sucedido un error a la hora de realizar el segundo login, error: ", e);
					resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de realizar el segundo login."));
					resultado.setError(Boolean.TRUE);
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean obtenerPermisoDemanda(ResultadoPermisoDistintivoItvBean resultado, String tipoEjecucion) {
		if (!resultado.getError()) {
			WebElement linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS));
			if(linkPermisos == null) {
				linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS2));
			}
			try {
				if(linkPermisos != null) {
					linkPermisos.click();
				} else {
					linkPermisos = driver.findElement(By.id("tablaResul:0:cmpdf_outputTextt_"));
					if(linkPermisos != null) {
						linkPermisos.click();
					} else {
						resultado.setMensaje("No se ha obtenido ningún PDF para su descarga.");
						resultado.setError(Boolean.TRUE);
					}
				}
			} catch (Exception e) {
				LOG.error("Ha sucedido un error a la hora de descargar los permisos, error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de descargar los permisos.");
				resultado.setError(Boolean.TRUE);
			}
			if (!resultado.getError()) {
				resultado.addPdfListaPermisos(pdfDemanda);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean obtenerFichaDemanda(ResultadoPermisoDistintivoItvBean resultado, String tipoEjecucion) {
		if (!resultado.getError()) {
			try {
				driver.findElement(By.partialLinkText(gestorPropiedades.valorPropertie(EITV_BOTON_FICHAS_TECNICAS))).click();
			} catch (Exception e) {
				LOG.error("Ha sucedido un error a la hora de descargar las fichas técnicas, error: ", e);
				resultado.setMensajeErrorFichas("Ha sucedido un error a la hora de descargar las fichas técnicas.");
				resultado.setErrorFichas(Boolean.TRUE);
			}
			if (!resultado.getErrorFichas()) {
				resultado.setByteFichero(pdfDemanda);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean buscarDocImpr(String matricula, String dniColegiado, BigDecimal numExpediente, ResultadoPermisoDistintivoItvBean resultado, String tipoTramite,
			Date fecha) {
		try {
			// Vamos por url para mostrar el formulario de busqueda
			driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
			// Introducimos los valores del gestor y del dia y pulsamos buscar
			driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_DOI))).sendKeys(dniColegiado);
			driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_MATRICULAS))).sendKeys(matricula);
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
				for (WebElement options : select.getOptions()) {
					if ("1".equals(options.getAttribute("value"))) {
						options.click();
					}
				}
			} else {
				Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
				for (WebElement options : select.getOptions()) {
					if ("5".equals(options.getAttribute("value"))) {
						options.click();
					}
				}
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_INICIO))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_FIN))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
			}
			driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON))).click();
			if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS))) {
				WebElement tabla = driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS)));
				tabla.findElement(By.cssSelector(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_INPUT))).click();
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR))).click();
				idPdf = numExpediente.toString();
			} else {
				resultado.setMensaje("No hay documentacion en IMPR para el expediente: " + numExpediente + " con matricula: " + matricula);
				resultado.setError(Boolean.TRUE);
				resultado.setNoExistenDoc(Boolean.TRUE);
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener la documentación de IMPR, error: ", e, numExpediente.toString());
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de obtener la documentación de IMPR para el expediente: " + numExpediente + " con matricula: " + matricula + "."));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean buscarDocImprNocturnoError(List<String> listaMatriculas, BigDecimal idContrato, String nif, ResultadoPermisoDistintivoItvBean resultado,
			String tipoTramite, String tipoDocumento) {
		try {
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			int cont = 0;
			if (listaMatriculas.size() > tamMaxLista) {
				final List<List<String>> auxListas = Lists.partition(listaMatriculas, tamMaxLista);
				for (List<String> listaMatr : auxListas) {
					String matriculas = crearStringMatriculas(listaMatr);
					if (cont == 0) {
						// Vamos por url para mostrar el formulario de busqueda
						driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
						cont++;
					}
					// Introducimos los valores del gestor y del dia y pulsamos buscar
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_DOI))).sendKeys(nif);
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_MATRICULAS))).sendKeys(matriculas);
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
						Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
						for (WebElement options : select.getOptions()) {
							if ("1".equals(options.getAttribute("value"))) {
								options.click();
							}
						}
					} else {
						Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
						for (WebElement options : select.getOptions()) {
							if ("5".equals(options.getAttribute("value"))) {
								options.click();
							}
						}
					}
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON))).click();
					if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS))) {
						WebElement tabla = driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS)));
						tabla.findElement(By.cssSelector(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_INPUT))).click();
						driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR))).click();
						if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento)) {
							try {
								driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_BOTON_FICHAS_TECNICAS))).click();
							} catch (Exception e) {
								LOG.error("Ha sucedido un error a la hora de descargar las fichas técnicas, error: ", e);
								resultado.setMensaje("Ha sucedido un error a la hora de descargar las fichas técnicas.");
								resultado.setError(Boolean.TRUE);
							}
							if (!resultado.getError()) {
								resultado.setNoExistenDoc(Boolean.FALSE);
								resultado.addPdfListaFichas(pdfDemanda);
							}
						} else {
							try {
								driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS))).click();
							} catch (Exception e) {
								LOG.error("Ha sucedido un error a la hora de descargar los permisos, error: ", e);
								resultado.setMensaje("Ha sucedido un error a la hora de descargar los permisos.");
								resultado.setError(Boolean.TRUE);
							}
							if (!resultado.getError()) {
								resultado.setNoExistenDoc(Boolean.FALSE);
								resultado.addPdfListaPermisos(pdfDemanda);
							}
						}
						LOG.info(driver.getPageSource());
						if (resultado.getError()) {
							break;
						}
						driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_VOLVER_IMPR_BOTON))).click();
						driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
					} else {
						resultado.setNoExistenDoc(Boolean.TRUE);
					}
				}
			} else {
				String matriculas = crearStringMatriculas(listaMatriculas);
				// Vamos por url para mostrar el formulario de busqueda
				driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
				// Introducimos los valores del gestor y del dia y pulsamos buscar
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_DOI))).sendKeys(nif);
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_MATRICULAS))).sendKeys(matriculas);
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
					for (WebElement options : select.getOptions()) {
						if ("1".equals(options.getAttribute("value"))) {
							options.click();
						}
					}
				} else {
					Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
					for (WebElement options : select.getOptions()) {
						if ("5".equals(options.getAttribute("value"))) {
							options.click();
						}
					}
				}
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON))).click();
				if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS))) {
					WebElement tabla = driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS)));
					tabla.findElement(By.cssSelector(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_INPUT))).click();
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR))).click();
					if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento)) {
						try {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_BOTON_FICHAS_TECNICAS))).click();
						} catch (Exception e) {
							LOG.error("Ha sucedido un error a la hora de descargar las fichas técnicas, error: ", e);
							resultado.setMensaje("Ha sucedido un error a la hora de descargar las fichas técnicas.");
							resultado.setNoExistenDoc(Boolean.TRUE);
						}
						if (!resultado.getError()) {
							resultado.setNoExistenDoc(Boolean.FALSE);
							resultado.addPdfListaFichas(pdfDemanda);
						}
					} else {
						try {
							driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS))).click();
						} catch (Exception e) {
							LOG.error("Ha sucedido un error a la hora de descargar los permisos, error: ", e);
							resultado.setMensaje("Ha sucedido un error a la hora de descargar los permisos.");
							resultado.setNoExistenDoc(Boolean.TRUE);
						}
						if (!resultado.getError()) {
							resultado.setNoExistenDoc(Boolean.FALSE);
							resultado.addPdfListaPermisos(pdfDemanda);
						}
					}
				} else {
					resultado.setNoExistenDoc(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener la documentación de IMPR, error: ", e);
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de obtener la documentación de IMPR para el contrato: " + idContrato));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean buscarDocImprNocturno(List<String> listaMatriculas, BigDecimal idContrato, String nif, 
			ResultadoPermisoDistintivoItvBean resultado, String tipoTramite, Date fecha, String tipoTramiteSolicitud) {
		try {
			int tamMaxLista = Integer.parseInt(gestorPropiedades.valorPropertie("tamaño.listaMatriculas.impr"));
			int cont = 0;
			if (listaMatriculas.size() > tamMaxLista) {
				final List<List<String>> auxListas = Lists.partition(listaMatriculas, tamMaxLista);
				for (List<String> listaMatr : auxListas) {
					String matriculas = crearStringMatriculas(listaMatr);
					if (cont == 0) {
						// Vamos por url para mostrar el formulario de busqueda
						driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
						cont++;
					}
					// Introducimos los valores del gestor y del dia y pulsamos buscar
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_DOI))).sendKeys(nif);
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_MATRICULAS))).sendKeys(matriculas);
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
						Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
						for (WebElement options : select.getOptions()) {
							if ("1".equals(options.getAttribute("value"))) {
								options.click();
							}
						}
					} else {
						Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
						for (WebElement options : select.getOptions()) {
							if ("5".equals(options.getAttribute("value"))) {
								options.click();
							}
						}
						driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_INICIO))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
						driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_FIN))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
					}
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON))).click();
					if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS))) {
						WebElement tabla = driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS)));
						tabla.findElement(By.cssSelector(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_INPUT))).click();
						driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR))).click();
						if(TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)){
							WebElement linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS));
							if(linkPermisos == null) {
								linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS2));
							}
							try {
								if(linkPermisos != null) {
									linkPermisos.click();
								} else {
									linkPermisos = driver.findElement(By.id("tablaResul:0:cmpdf_outputTextt_"));
									if(linkPermisos != null) {
										linkPermisos.click();
									} else {
										resultado.setMensaje("No se ha obtenido ningún PDF para su descarga.");
										resultado.setError(Boolean.TRUE);
									}
								}
							} catch (Exception e) {
								LOG.error("Ha sucedido un error a la hora de descargar los permisos, error: ", e);
								resultado.setMensaje("Ha sucedido un error a la hora de descargar los permisos.");
								resultado.setError(Boolean.TRUE);
							}
							if (!resultado.getError()) {
								resultado.addPdfListaPermisos(pdfDemanda);
							}
						} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)){
							try {
								driver.findElement(By.partialLinkText(gestorPropiedades.valorPropertie(EITV_BOTON_FICHAS_TECNICAS))).click();
							} catch (Exception e) {
								LOG.error("Ha sucedido un error a la hora de descargar las fichas técnicas, error: ", e);
								resultado.setMensaje("Ha sucedido un error a la hora de descargar las fichas técnicas.");
								resultado.setError(Boolean.TRUE);
							}
							if (!resultado.getError()) {
								resultado.addPdfListaFichas(pdfDemanda);
							}
						}
						LOG.info(driver.getPageSource());
						if (resultado.getError()) {
							break;
						}
						driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_VOLVER_IMPR_BOTON))).click();
						driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
					} else {
						resultado.setMensaje("No hay documentacion en IMPR del tipo " + TipoTramiteTrafico.convertirTexto(tipoTramiteSolicitud) + " para el contrato: " + idContrato);
						resultado.setError(Boolean.TRUE);
						resultado.setNoExistenDoc(Boolean.TRUE);
					}
				}
			} else {
				String matriculas = crearStringMatriculas(listaMatriculas);
				// Vamos por url para mostrar el formulario de busqueda
				driver.get(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS));
				// Introducimos los valores del gestor y del dia y pulsamos buscar
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_DOI))).sendKeys(nif);
				driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_MATRICULAS))).sendKeys(matriculas);
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
					for (WebElement options : select.getOptions()) {
						if ("1".equals(options.getAttribute("value"))) {
							options.click();
						}
					}
				} else {
					Select select = new Select(driver.findElement(By.name("bt_form:nar_selectOneMenu")));
					for (WebElement options : select.getOptions()) {
						if ("5".equals(options.getAttribute("value"))) {
							options.click();
						}
					}
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_INICIO))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
					driver.findElement(By.name(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_INPUT_FECHA_FIN))).sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
				}
				driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON))).click();
				if (driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS))) {
					WebElement tabla = driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS)));
					tabla.findElement(By.cssSelector(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_TABLA_INPUT))).click();
					driver.findElement(By.id(gestorPropiedades.valorPropertie(EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR))).click();
					if(TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)){
						WebElement linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS));
						if(linkPermisos == null) {
							linkPermisos = obtenerWebElementLink(gestorPropiedades.valorPropertie(EITV_BOTON_PERMISOS2));
						}
						try {
							if(linkPermisos != null) {
								linkPermisos.click();
							} else {
								linkPermisos = driver.findElement(By.id("tablaResul:0:cmpdf_outputTextt_"));
								if(linkPermisos != null) {
									linkPermisos.click();
								} else {
									resultado.setMensaje("No se ha obtenido ningún PDF para su descarga.");
									resultado.setError(Boolean.TRUE);
								}
							}
						} catch (Exception e) {
							LOG.error("Ha sucedido un error a la hora de descargar los permisos, error: ", e);
							resultado.setMensaje("Ha sucedido un error a la hora de descargar los permisos.");
							resultado.setError(Boolean.TRUE);
						}
						if (!resultado.getError()) {
							resultado.addPdfListaPermisos(pdfDemanda);
						}
					} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)){
						try {
							driver.findElement(By.partialLinkText(gestorPropiedades.valorPropertie(EITV_BOTON_FICHAS_TECNICAS))).click();
						} catch (Exception e) {
							LOG.error("Ha sucedido un error a la hora de descargar las fichas técnicas, error: ", e);
							resultado.setMensaje("Ha sucedido un error a la hora de descargar las fichas técnicas.");
							resultado.setError(Boolean.TRUE);
						}
						if (!resultado.getError()) {
							resultado.addPdfListaFichas(pdfDemanda);
						}
					}
				} else {
					resultado.setMensaje("No hay documentacion en IMPR del tipo " + TipoTramiteTrafico.convertirTexto(tipoTramiteSolicitud) + " para el contrato: " + idContrato);
					resultado.setError(Boolean.TRUE);
					resultado.setNoExistenDoc(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener la documentación de IMPR del tipo " + TipoTramiteTrafico.convertirTexto(tipoTramiteSolicitud) + ", error: ", e);
			resultado.setExcepcion(new Exception("Ha sucedido un error a la hora de obtener la documentación de IMPR del tipo " + TipoTramiteTrafico.convertirTexto(tipoTramiteSolicitud) + " para el contrato: " + idContrato));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private String crearStringMatriculas(List<String> listaMatriculas) {
		String matriculas = "";
		for (String matricula : listaMatriculas) {
			if (matriculas.isEmpty()) {
				matriculas = matricula.trim().toUpperCase();
			} else {
				matriculas += "," + matricula.trim().toUpperCase();
			}
		}
		return matriculas;
	}
	
	private WebElement obtenerWebElementLink(String valorLink) {
		try {
			return 	driver.findElement(By.partialLinkText(valorLink));
		} catch (Throwable e) {
			LOG.error("No se ha encontrado el link:" + valorLink);
		}
		return null;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean cerrarSession() {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (driver != null) {
				driver.get("https://movilidad.dgt.es/dana-na/auth/logout.cgi");
				driver = null;
				pdfFichas = null;
				pdfPermisos = null;
				pdfDemanda = null;
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de cerrar la session de IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error: " + e.getMessage());
		}
		return resultado;
	}

	private void cambiarVentana(WebDriver driver) {
		// Al hacer click levanta un pop-up sobre el que continuamos la navegacion
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> iterator = windowId.iterator();
		ventanaPrincipal = iterator.next();
		ventanaSecundaria = iterator.next();
		driver.switchTo().window(ventanaSecundaria);
		if (!driver.getPageSource().contains(gestorPropiedades.valorPropertie(EITV_LOGIN_FORM2_USER))) {
			driver.switchTo().window(ventanaPrincipal);
		}
	}

	protected WebClient addHandlerDemanda(WebClient client) {
		ConfirmHandler okHandler = new ConfirmHandler() {
			public boolean handleConfirm(Page page, String message) {
				return true;
			}
		};
		client.setConfirmHandler(okHandler);
		client.addWebWindowListener(new WebWindowListener() {
			public void webWindowOpened(WebWindowEvent event) {}

			public void webWindowContentChanged(WebWindowEvent event) {
				WebResponse response = event.getWebWindow().getEnclosedPage().getWebResponse();
				if (response.getContentType().equals(MIME_TYPE_PDF)) {
					try {
						pdfDemanda = IOUtils.toByteArray(response.getContentAsStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			public void webWindowClosed(WebWindowEvent event) {}
		});
		return client;
	}

	protected WebClient addHandlersNocturno(WebClient client) {
		ConfirmHandler okHandler = new ConfirmHandler() {
			public boolean handleConfirm(Page page, String message) {
				return true;
			}
		};
		client.setConfirmHandler(okHandler);
		client.addWebWindowListener(new WebWindowListener() {
			public void webWindowOpened(WebWindowEvent event) {}

			public void webWindowContentChanged(WebWindowEvent event) {
				WebResponse response = event.getWebWindow().getEnclosedPage().getWebResponse();
				if (response.getContentType().equals(MIME_TYPE_PDF)) {
					try {
						if (pdfFichas.containsKey(idPdf)) {
							pdfPermisos.put(idPdf, IOUtils.toByteArray(response.getContentAsStream()));
						} else {
							pdfFichas.put(idPdf, IOUtils.toByteArray(response.getContentAsStream()));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			public void webWindowClosed(WebWindowEvent event) {}
		});
		return client;
	}
}
