package org.gestoresmadrid.oegamComun.solicitudNRE06.service.impl;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.SolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.service.ServicioNRE06Web;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class ServicioNRE06WebImpl implements ServicioNRE06Web {

	private static final long serialVersionUID = -1304974991790310908L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioNRE06WebImpl.class);

	private static final String KEYSTORE_LOCATION = "nre06.keystore.location";
	private static final String KEYSTORE_PASS = "nre06.keystore.password";
	private static final String URL_INDEX = "nre06.web.url";
	private static final String CLASS_AVISOS = "[class='mensajesErrorListaNRE06']";
	private static final String NRE06_ID_VISADO = "nre06.idvisado";
	private static final String NRE06_ID_NIFT = "nre06.idnift";
	private static final String NRE06_ID_APELLT = "nre06.idapellt";
	private static final String NRE06_ID_NOMBRET = "nre06.idnombret";
	private static final String NRE06_ID_NIFC = "nre06.idnifc";
	private static final String NRE06_ID_APELLC= "nre06.idapellc";
	private static final String NRE06_ID_NOMBREC = "nre06.idnombrec";
	private static final String NRE06_ID_FECHA_DIA_VISADO = "nre06.idfechadiavisado";
	private static final String NRE06_ID_FECHA_MES_VISADO = "nre06.idfechamesvisado";
	private static final String NRE06_ID_FECHA_ANIO_VISADO = "nre06.idfechaaniovisado";
	private static final String NRE06_ID_REF_COLEGIO = "nre06.idrefcolegio";
	private static final String NRE06_ID_BASTIDOR = "nre06.idbastidor";
	private static final String NRE06_BT_ENVIO = "nre06.btenvio";

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoSolicitudNRE06Bean navegacionWeb(SolicitudNRE06Bean solicitudNRE06Bean, ResultadoSolicitudNRE06Bean resultado) {
		WebDriver driver;
		try {
			driver = iniciarNavegador();
		} catch (Exception e) {
			log.error("Ocurrio un error iniciando el navegador HtmlUnitDriver", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ocurrio un error iniciando el navegador HtmlUnitDriver.");
			return resultado;
		}
		if (!resultado.getError()) {
			rellenarDatos(driver, solicitudNRE06Bean, resultado);
		}
		if (driver != null) {
			driver.close();
		}
		return resultado;
	}

	private WebDriver iniciarNavegador() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, MalformedURLException {
		WebDriver driver = new HtmlUnitDriver(true);
		Field webClient = driver.getClass().getDeclaredField("webClient");
		webClient.setAccessible(true);
		WebClientOptions webClientOptions = ((WebClient) webClient.get(driver)).getOptions();
		webClientOptions.setSSLClientCertificate(new URL(gestorPropiedades.valorPropertie(KEYSTORE_LOCATION)), gestorPropiedades.valorPropertie(KEYSTORE_PASS), "jks");
		webClientOptions.setUseInsecureSSL(true);
		webClientOptions.setThrowExceptionOnScriptError(false);
		webClientOptions.setJavaScriptEnabled(true);
		driver.get(gestorPropiedades.valorPropertie(URL_INDEX));
		return driver;
	}

	private void rellenarDatos(WebDriver driver, SolicitudNRE06Bean solicitudNRE06Bean, ResultadoSolicitudNRE06Bean resultado) {
		try {
			String anio = solicitudNRE06Bean.getFechaVisado().substring(0, 4);
			String mes = solicitudNRE06Bean.getFechaVisado().substring(4, 6);
			String dia = solicitudNRE06Bean.getFechaVisado().substring(6, 8);
			// Rellenamos las cajas de texto de la solicitud
			seleccionarCombo(driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_VISADO))), solicitudNRE06Bean.getTramiteVisado());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_NIFT))).sendKeys(solicitudNRE06Bean.getNifTitular());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_APELLT))).sendKeys(solicitudNRE06Bean.getApellRazonSocialTitular());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_NOMBRET))).sendKeys(solicitudNRE06Bean.getNombreTitular());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_NIFC))).sendKeys(solicitudNRE06Bean.getNifColegiado());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_APELLC))).sendKeys(solicitudNRE06Bean.getApellRazonSocialColegiado());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_NOMBREC))).sendKeys(solicitudNRE06Bean.getNombreColegiado());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_FECHA_DIA_VISADO))).sendKeys(dia);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_FECHA_MES_VISADO))).sendKeys(mes);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_FECHA_ANIO_VISADO))).sendKeys(anio);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_REF_COLEGIO))).sendKeys(solicitudNRE06Bean.getFechaVisado());
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_ID_BASTIDOR))).sendKeys(solicitudNRE06Bean.getNumBastidor());
			// Hacemos click en el botón de enviar
			driver.findElement(By.id(gestorPropiedades.valorPropertie(NRE06_BT_ENVIO))).click();
			pantallaResumen(driver, resultado);
			gestionaErrores(driver, resultado);
		} catch (Exception e) {
			log.error("Ha ocurrido un error al rellenar los datos y enviar", e);
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al rellenar los datos y enviar");
			return;
		}
	}

	private void seleccionarCombo(WebElement element, String value) {
		Select select = new Select(element);
		for (WebElement options : select.getOptions()) {
			if (value.equals(options.getAttribute("value"))) {
				options.click();
			}
		}
	}

	private void gestionaErrores(WebDriver driver, ResultadoSolicitudNRE06Bean resultado) {
		try {
			List<WebElement> warnings = driver.findElements(By.cssSelector(CLASS_AVISOS));
			if (warnings != null && !warnings.isEmpty()) {
				for (WebElement warning : warnings) {
					resultado.setMensaje(warning.getText());
				}
				resultado.setError(Boolean.TRUE);
			}
		} catch (NoSuchElementException t) {
			log.debug("Continua el proceso", t);
		}
	}

	private void pantallaResumen(WebDriver driver, ResultadoSolicitudNRE06Bean resultado) {
		try {
			JavascriptExecutor javascript = (JavascriptExecutor) driver;

			javascript.executeScript("arguments[0].value='Q2861007I';", driver.findElement(By.id("FIRNIF")));
			javascript.executeScript("arguments[0].value='COL OF DE GESTORES ADMINISTRATIVOS MADRID';", driver.findElement(By.id("FIRNOMBRE")));
			javascript.executeScript("arguments[0].value='FirmaBasica';", driver.findElement(By.id("FIR")));

			driver.findElement(By.name("fenv")).submit();
			String currentURL = driver.getCurrentUrl();
			System.out.print(currentURL);
			leerDatos(driver,resultado);
		} catch (Exception e) {
			log.error("Ha ocurrido un error al dar conformidad y firmar", e);
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al dar conformidad y firmar");
		}
	}

	private void leerDatos(WebDriver driver, ResultadoSolicitudNRE06Bean resultado) {
		driver.getPageSource();
		resultado.setNre(StringUtils.substring(driver.getPageSource(),StringUtils.indexOf(driver.getPageSource(), "NRE=")+ 5,
				StringUtils.indexOf(driver.getPageSource(), "NRE=") + 27));
		resultado.setFecha(StringUtils.substring(driver.getPageSource(),StringUtils.indexOf(driver.getPageSource(), "FEC=")+ 5,
				StringUtils.indexOf(driver.getPageSource(), "FEC=") + 15));
		resultado.setHora(StringUtils.substring(driver.getPageSource(),StringUtils.indexOf(driver.getPageSource(), "HOR=")+ 5,
				StringUtils.indexOf(driver.getPageSource(), "HOR=") + 13));
		resultado.setCodElect(StringUtils.substring(driver.getPageSource(),StringUtils.indexOf(driver.getPageSource(), "CEL=")+ 5,
				StringUtils.indexOf(driver.getPageSource(), "CEL=") + 21));
		resultado.setReg(StringUtils.substring(driver.getPageSource(),StringUtils.indexOf(driver.getPageSource(), "REG=")+ 5,
				StringUtils.indexOf(driver.getPageSource(), "REG=") + 21));
	}
}