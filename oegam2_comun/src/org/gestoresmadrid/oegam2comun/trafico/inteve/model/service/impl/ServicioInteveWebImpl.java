package org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.ServicioInteveWeb;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioInteveWebImpl implements ServicioInteveWeb {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioInteveWebImpl.class);

	/*
	 * Claves de properties
	 */
	private static final String KEYSTORE_LOCATION = "inteve3.keystore.location";
	private static final String KEYSTORE_PASS = "inteve3.keystore.password";
	private static final String URL_INDEX = "inteve3.web.url";
	private static final String PDF_ENCODING = "inteve3.web.pdf.encodign";
	private static final String CLASS_AVISOS = "inteve3.web.class.avisos";
	private static final String VERIFICAR_IN_MATRICULA = "inteve3.web.verificar.in.matricula";
	private static final String VERIFICAR_BT_CONSULTAR = "inteve3.web.verificar.bt.consultar";
	private static final String VERIFICAR_BT_SOLICITAR = "inteve3.web.verificar.bt.solicitar";
	private static final String SOLICITUD_CB_INFORME = "inteve3.web.solicitud.cb.informe";
	private static final String SOLICITUD_CB_INFORME_VALOR = "inteve3.web.solicitud.cb.informe.valor";
	private static final String SOLICITUD_CB_MOTIVO = "inteve3.web.solicitud.cb.motivo";
	private static final String SOLICITUD_CH_MATRICULA = "inteve3.web.solicitud.ch.matricula";
	private static final String SOLICITUD_IN_MATRICULA = "inteve3.web.solicitud.in.matricula";
	private static final String SOLICITUD_CH_BASTIDOR = "inteve3.web.solicitud.ch.bastidor";
	private static final String SOLICITUD_IN_BASTIDOR = "inteve3.web.solicitud.in.bastidor";
	private static final String SOLICITUD_CH_NIVE = "inteve3.web.solicitud.ch.nive";
	private static final String SOLICITUD_IN_NIVE = "inteve3.web.solicitud.in.nive";
	private static final String SOLICITUD_BT_SOLICITAR = "inteve3.web.solicitud.bt.solicitar";
	private static final String SOLICITUD_HD_IDVEHICULO = "inteve3.web.solicitud.hd.idvehiculo";
	private static final String RESUMEN_CH_PREVIO = "inteve3.web.resumen.ch.previo";
	private static final String RESUMEN_BT_CONTINUAR = "inteve3.web.resumen.bt.continuar";
	private static final String PAGO_IN_TASA = "inteve3.web.pago.in.tasa";
	private static final String PAGO_BT_SOLICITAR = "inteve3.web.pago.bt.solicitar";
	private static final String PAGO_BT_CONFIRMAR = "inteve3.web.pago.bt.confirmar";
	private static final String DESCARGA_BT_DESCARGAR = "inteve3.web.descarga.bt.descargar";
	private static final String INICIAR_FORMULARIO = "inteve3.web.iniciarformulario";
	private static final String NRC = "inteve3.web.nrc";
	private static final String TIPO_SELECT_INFORME = "inteve3.web.tipo.seleccion.informe";


	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultInteveBean navegacionweb(SolicitudInformeVehiculoDto solicitudInforme, String numColegiado, ResultInteveBean resultBean) {
		WebDriver driver;
		try {
			driver = iniciarNavegador();
		} catch (Exception e) {
			LOG.error("Ocurrio un error iniciando el navegador HtmlUnitDriver", e);
			resultBean.setException(e);
			return resultBean;
		}
		if ("SI".equals(gestorPropiedades.valorPropertie(INICIAR_FORMULARIO))) {
			verificarMatricula(driver, solicitudInforme.getVehiculo().getMatricula(), resultBean);
		}
		if (!resultBean.isError()) {
			datosSolicitud(driver, solicitudInforme, resultBean);
		}
		if (!resultBean.isError()) {
			// ver si ya se ha solicitado (el check)
			try {
				if (!comprobarInformePrevio(driver, solicitudInforme.getVehiculo(), numColegiado)) {
					datosPago(driver, solicitudInforme.getTasa().getCodigoTasa(), resultBean);
				}
			} catch (NoSuchElementException e) {
				LOG.error("Ocurrio un error", e);
				resultBean.setError(true);
				resultBean.setMensaje("Error al descargar el informe");
			}
		}
		if (!resultBean.isError()) {
			try {
				descargaInforme(driver, resultBean);
			} catch (UnsupportedEncodingException e) {
				LOG.error("Ocurrio un error, la codificacion del PDF no es la esperada", e);
				resultBean.setException(e);
			}
		}
		if (driver != null) {
			driver.close();
		}
		return resultBean;
	}

	/**
	 * Inicia el navegador HtmlUnitDriver
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws MalformedURLException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private WebDriver iniciarNavegador() throws SecurityException, NoSuchFieldException, MalformedURLException, IllegalArgumentException, IllegalAccessException {
		// Iniciamos el navegador
		WebDriver driver = new HtmlUnitDriver();
		// Por reflexion, recuperamos el atributo webClient del objeto HtmlUnitDriver para obtener su webClientOptions y poder establecer el SSLClientCertificate
		Field webClient = driver.getClass().getDeclaredField("webClient");
		webClient.setAccessible(true);
		WebClientOptions webClientOptions = ((WebClient) webClient.get(driver)).getOptions();
		webClientOptions.setSSLClientCertificate(new URL(gestorPropiedades.valorPropertie(KEYSTORE_LOCATION)), gestorPropiedades.valorPropertie(KEYSTORE_PASS), "jks");
		webClientOptions.setUseInsecureSSL(true);

		driver.get(gestorPropiedades.valorPropertie(URL_INDEX));
		return driver;
	}

	/**
	 * Primera pestania, verificar matricula
	 * @param driver
	 * @param matricula
	 * @param resultBean
	 */
	private void verificarMatricula(WebDriver driver, String matricula, ResultInteveBean resultBean) {
		try {
			driver.findElement(By.id(gestorPropiedades.valorPropertie(VERIFICAR_IN_MATRICULA))).sendKeys(matricula);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(VERIFICAR_BT_CONSULTAR))).click();
		} catch (NoSuchElementException t) {
			LOG.error("No encuentra el elemento html", t);
			resultBean.setError(true);
			resultBean.setMensaje("No encuentra elemento HTML en la pantalla de verificar matricula");
			return;
		}
		// Comprueba los mensajes de warnings para comprobar el flujo
		gestionaErrores(driver, resultBean);

		if (!resultBean.isError()) {
			try {
				driver.findElement(By.name(gestorPropiedades.valorPropertie(VERIFICAR_BT_SOLICITAR))).click();
			} catch (NoSuchElementException t) {
				LOG.error("No encuentra el elemento html en verificar matricula", t);
				resultBean.setError(true);
				resultBean.setMensaje("No encuentra el boton solicitar informe en la pantalla de verificar matricula");
			}
		}
	}

	/**
	 * Segunda pestania, datos de la solicitud
	 * @param driver
	 * @param solicitudInforme
	 * @param resultBean
	 */
	private void datosSolicitud(WebDriver driver, SolicitudInformeVehiculoDto solicitudInforme, ResultInteveBean resultBean) {
		try {
			
			if("radio".equalsIgnoreCase(gestorPropiedades.valorPropertie(TIPO_SELECT_INFORME))){
				seleccionarRadioMotivo(driver, solicitudInforme);
				seleccionarRadioInforme(driver, solicitudInforme);
			} else if("combo".equalsIgnoreCase(gestorPropiedades.valorPropertie(TIPO_SELECT_INFORME))){
				seleccionarCombo(driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_CB_INFORME))), solicitudInforme.getTipoInforme());	
				seleccionarCombo(driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_CB_MOTIVO))), solicitudInforme.getMotivoInteve());
			}
			if (solicitudInforme.getVehiculo().getMatricula() != null && !solicitudInforme.getVehiculo().getMatricula().isEmpty()) {
				// Solicitar por matricula
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_CH_MATRICULA))).click();
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_HD_IDVEHICULO))).sendKeys("matricula");
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_IN_MATRICULA))).sendKeys(solicitudInforme.getVehiculo().getMatricula());

			} else if (solicitudInforme.getVehiculo().getBastidor() != null && !solicitudInforme.getVehiculo().getBastidor().isEmpty()) {
				// Solicitar por bastidor
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_CH_BASTIDOR))).click();
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_HD_IDVEHICULO))).sendKeys("bastidor");
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_IN_BASTIDOR))).sendKeys(solicitudInforme.getVehiculo().getBastidor());

			} else if (solicitudInforme.getVehiculo().getNive() != null && !solicitudInforme.getVehiculo().getNive().isEmpty()) {
				// Solicitar por NIVE
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_CH_NIVE))).click();
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_HD_IDVEHICULO))).sendKeys("nive");
				driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_IN_NIVE))).sendKeys(solicitudInforme.getVehiculo().getNive());

			}

			// Pulsar boton solictar
			driver.findElement(By.id(gestorPropiedades.valorPropertie(SOLICITUD_BT_SOLICITAR))).click();

			// Comprueba los mensajes de warnings para comprobar el flujo
			gestionaErrores(driver, resultBean);

		} catch (NoSuchElementException t) {
			LOG.error("No encuentra el elemento html en datos solicitud", t);
			resultBean.setError(true);
			resultBean.setMensaje("No encuentra elemento HTML en la pantalla de datos solicitud");
			return;
		}
	}

	/**
	 * Tercera pestania, resumen, posibles informes previos
	 * @param driver
	 * @param vehiculoDto.getMatricula(), vehiculoDto.getBastidor(), vehiculoDto.getNive()
	 * @param numColegiado
	 * @return
	 */
	private boolean comprobarInformePrevio(WebDriver driver, VehiculoDto vehiculoDto, String numColegiado) {
		boolean informePrevio = false;
		try {
			WebElement element = driver.findElement(By.id(gestorPropiedades.valorPropertie(RESUMEN_CH_PREVIO)));
			if (element != null	&& !comprobarSolicitudInformeOtroColegiado(vehiculoDto.getMatricula(), vehiculoDto.getBastidor(), vehiculoDto.getNive(), numColegiado)) {
				/* SIEMPRE VAMOS CON TASA YA QUE AL UTILIZAR EL CERTIFICADO DEL COLEGIO SIEMPRE VAN A APARECER CON INFORME PREVIO*/
				driver.findElement(By.name(gestorPropiedades.valorPropertie(RESUMEN_BT_CONTINUAR))).click();
				LOG.info("Existe informe previo, no se solicita se pide nueva tasa");
				informePrevio = false;
			}

			driver.findElement(By.name(gestorPropiedades.valorPropertie(RESUMEN_BT_CONTINUAR))).click();	
		} catch (NoSuchElementException t) {
			LOG.debug("No tiene informe previo", t);
		}
		return informePrevio;
	}

	private boolean comprobarNRC(WebDriver driver){
		boolean nrc = false;
		try {
			WebElement element = driver.findElement(By.id(gestorPropiedades.valorPropertie(NRC)));
			if(element==null){
				return true;
			}else{
				return false;
			}	
		}catch (NoSuchElementException e) {
			LOG.debug("No hay NRC", e);
		}
		return nrc;
	}
	
	/**
	 * Cuarta pestania, datos del pago
	 * @param driver
	 * @param codTasa
	 * @param resultBean
	 */
	private void datosPago(WebDriver driver, String codTasa, ResultInteveBean resultBean) {
		try {
			// Incluir tasa
			driver.findElement(By.id(gestorPropiedades.valorPropertie(PAGO_IN_TASA))).sendKeys(codTasa);
			// Boton Solicitar
			driver.findElement(By.name(gestorPropiedades.valorPropertie(PAGO_BT_SOLICITAR))).click();
			
			if(driver.findElement(By.name(gestorPropiedades.valorPropertie(PAGO_BT_CONFIRMAR))) != null){
				// Boton solicitar de resumen
				driver.findElement(By.name(gestorPropiedades.valorPropertie(PAGO_BT_CONFIRMAR))).click();
			}
			// Comprueba los mensajes de warnings para comprobar el flujo
			gestionaErrores(driver, resultBean);
		} catch (NoSuchElementException t) {
			LOG.error("No encuentra el elemento html en datos pago", t);
			resultBean.setError(true);
			resultBean.setMensaje("No encuentra elemento HTML en la pantalla de datos pago");
		}
	}

	/**
	 * Quinta pestania, descarga del informe PDF
	 * @param driver
	 * @param resultBean
	 * @throws UnsupportedEncodingException
	 */
	private void descargaInforme(WebDriver driver, ResultInteveBean resultBean) throws UnsupportedEncodingException {
		driver.findElement(By.name(gestorPropiedades.valorPropertie(DESCARGA_BT_DESCARGAR))).click();
		resultBean.setInforme(driver.getPageSource().getBytes(gestorPropiedades.valorPropertie(PDF_ENCODING)));
	}

	/**
	 * Comprueba errores o avisos en la pagina actual
	 * @param driver
	 * @param resultBean
	 */
	private void gestionaErrores(WebDriver driver, ResultInteveBean resultBean) {
		try {
			List<WebElement> warnings = driver.findElements(By.cssSelector(gestorPropiedades.valorPropertie(CLASS_AVISOS)));
			if (warnings != null && !warnings.isEmpty()) {
				for (WebElement warning : warnings) {
					resultBean.setMensaje(warning.getText());
				}
				resultBean.setError(true);
			}
		} catch (NoSuchElementException t) {
			LOG.debug("Continua el proceso", t);
		}
	}

	/**
	 * En caso de que exista informe previo, comprueba si otro colegiado ha realizado peticion sobre la misma matricula
	 * @param matricula
	 * @param numColegiado
	 * @return
	 */
	private boolean comprobarSolicitudInformeOtroColegiado(String matricula, String bastidor, String nive, String numColegiado) {
		return servicioTramiteTraficoSolInfo.isSolicitudesOtrosColegiados(matricula, bastidor, nive, numColegiado);
	}

	/**
	 * Sobre el combo "element", establece la opcion con valor "value"
	 * 
	 * @param element
	 * @param value
	 */
	private void seleccionarCombo (WebElement element, String value) {
		Select select = new Select(element);
		for(WebElement options : select.getOptions()) {
			if (value.equals(options.getAttribute("value"))) {
				options.click();
			}
		}
	}
	
	private void seleccionarRadioMotivo (WebDriver driver, SolicitudInformeVehiculoDto solicitudInforme) {
		
	    List<WebElement>radioButton = driver.findElements(By.name(gestorPropiedades.valorPropertie(SOLICITUD_CB_MOTIVO)));
		for(WebElement options : radioButton) {
			if (options.getAttribute("value").equals(solicitudInforme.getMotivoInteve())){
				options.click();
			}
		}
		
	}
	
	private void seleccionarRadioInforme (WebDriver driver, SolicitudInformeVehiculoDto solicitudInforme) {
		
	    List<WebElement>radioButton = driver.findElements(By.name(gestorPropiedades.valorPropertie(SOLICITUD_CB_INFORME)));
		for(WebElement options : radioButton) {
			if (options.getAttribute("value").equals(solicitudInforme.getTipoInforme())){
				options.click();
			}
		}
		
	}

}
