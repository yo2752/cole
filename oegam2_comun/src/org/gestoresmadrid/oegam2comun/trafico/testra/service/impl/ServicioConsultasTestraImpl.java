package org.gestoresmadrid.oegam2comun.trafico.testra.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.ColegiadoDao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.trafico.testra.model.dao.ConsultaTestraDao;
import org.gestoresmadrid.core.trafico.testra.model.vo.ConsultaTestraVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.ServicioConsultasTestra;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoTramiteTestra;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.dto.ConsultaTestraDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioConsultasTestraImpl implements ServicioConsultasTestra {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultasTestraImpl.class);

	public static final String KEY_RESULT = "consulta";
	private static final String EXPRESION_MATRICULA = "(([A-Z]{1,2})([0-9]{4})([A-Z]{0,2}))|(([0-9]{4})([A-Z]{3}))";

	@Autowired
	ConsultaTestraDao consultaTestraDao;

	@Autowired
	ColegiadoDao colegiadoDao;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	ServicioColegiado servicioColegiado;

	@Autowired
	Conversor conversor;

	WebDriver driver = null;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public String consultaTresta(List<String> numerosColegiado) {
		Date fechaHoy = new Date();
		boolean enviadoCorreo = false;
		String resultadoEjecucion = null;

		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		for (String numColegiado : numerosColegiado) {
			enviadoCorreo = consultarMatriculasDeColegiado(numColegiado, fechaHoy, enviadoCorreo);
		}

		if (!enviadoCorreo) {
			String texto = "NO HAY SANCIONES TESTRA PARA EL DÍA: " + utilesFecha.formatoFecha(fechaHoy);
			enviarCorreoDiario(texto);
			resultadoEjecucion = "NO HAY SANCIONES";
		} else {
			resultadoEjecucion = "HAY SANCIONES";
		}

		driver.quit();
		driver = null;

		return resultadoEjecucion;
	}

	private boolean consultarMatriculasDeColegiado(String numColegiado, Date fechaHoy, boolean enviadoCorreo) {
		List<ConsultaTestraVO> datos = getConsultasByColegiado(numColegiado);
		List<ConsultaTestraVO> sanciones = new ArrayList<>();

		for (ConsultaTestraVO consultaTestraVO : datos) {
			if (isSancionadoIntentos(consultaTestraVO.getDato(), consultaTestraVO.getFechaUltimaSancion())) {
				sanciones.add(consultaTestraVO);
			}
		}

		if (!sanciones.isEmpty()) {
			ContratoDto contrato = servicioContrato.getContratoPorColegiado(sanciones.get(0).getNumColegiado());
			enviadoCorreo = enviarCorreo(sanciones, contrato, fechaHoy, enviadoCorreo);
			for (ConsultaTestraVO consultaTestraVO : sanciones) {
				consultaTestraVO.setFechaUltimaSancion(fechaHoy);
				updateConsultaTestra(consultaTestraVO);
			}

			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("testra.consume.credito"))) {
				Long idUsuario = servicioColegiado.getIdColegiado(sanciones.get(0).getNumColegiado());
				servicioCredito.descontarCreditos(TipoTramiteTestra.notificacion.getValorEnum(), contrato.getIdContrato(), BigDecimal.valueOf(sanciones.size()), BigDecimal.valueOf(idUsuario),
						ConceptoCreditoFacturado.TRA);
			}
		}

		return enviadoCorreo;
	}

	private boolean enviarCorreo(List<ConsultaTestraVO> sanciones, ContratoDto contrato, Date fechaHoy, boolean enviadoCorreo) {
		String subject = null;
		String recipient = null;
		String direccionesOcultas = null;
		StringBuilder texto = null;

		try {
			subject = gestorPropiedades.valorPropertie(SUBJECT);

			List<ConsultaTestraVO> matriculasSancionadas = new ArrayList<>();
			List<ConsultaTestraVO> nifsSancionados = new ArrayList<>();

			for (ConsultaTestraVO sancion : sanciones) {
				if (TipoConsultaTestraEnum.M.getClave().equals(sancion.getTipo())) {
					matriculasSancionadas.add(sancion);
				} else if (TipoConsultaTestraEnum.C.getClave().equals(sancion.getTipo())) {
					nifsSancionados.add(sancion);
				}
			}

			texto = new StringBuilder("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos que para el Colegiado: ").append(contrato.getColegiadoDto().getNumColegiado());
			texto.append(" se han encontrado hoy, ").append(utilesFecha.formatoFecha(fechaHoy)).append(", sanciones en el portal TESTRA.").append("<BR><BR>");
			if (!matriculasSancionadas.isEmpty()) {
				texto.append("Las siguientes matrículas tienen sanciones").append(":<br>");

				for (ConsultaTestraVO matricula : matriculasSancionadas) {
					if (matricula.getCorreoElectronico() != null && !matricula.getCorreoElectronico().isEmpty()) {
						texto.append(matricula.getDato() + " - " + "Correo electrónico:  " + matricula.getCorreoElectronico()).append("<br>");
					} else {
						texto.append(matricula.getDato()).append("<br>");
					}

				}
			}

			if (!nifsSancionados.isEmpty()) {
				texto.append("Los siguientes NIF/CIF(s) tienen sanciones").append(":<br>");

				for (ConsultaTestraVO nif : nifsSancionados) {
					if (nif.getCorreoElectronico() != null && !nif.getCorreoElectronico().isEmpty()) {
						texto.append(nif.getDato() + " - " + "Correo electrónico:  " + nif.getCorreoElectronico()).append("<br>");
					} else {
						texto.append(nif.getDato()).append("<br>");
					}
				}
			}

			String testraURL = gestorPropiedades.valorPropertie("testra.web.url");
			texto.append("</span>");
			texto.append("Puede obtener más información de la sanción en la URL: " + "<BR>" + testraURL + "<BR><BR>");
			recipient = gestorPropiedades.valorPropertie("matriculasTestra.correo.destinatario.pruebas");

			if (recipient == null) {
				recipient = contrato.getCorreoElectronico();
				direccionesOcultas = gestorPropiedades.valorPropertie(RECIPIENT);
			}

			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}

			ResultBean resultado;

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, direccionesOcultas, null, null);

			if (resultado.getError()) {
				String textoEjecucion = "Ha fallado el envio del correo que contiene las sanciones. Error: " + resultado.getMensaje();
				enviarCorreoDiario(textoEjecucion);
				log.error("Ha fallado el envio del correo que contiene las sanciones. Error: " + resultado.getMensaje());
			}

			enviadoCorreo = true;

		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de la consulta de matriculas TESTRA", e);
		}

		return enviadoCorreo;
	}

	public void enviarCorreoDiario(String mensaje) {
		String asunto = "RESULTADO EJECUCIÓN DIARIA PROCESO TESTRA";
		String entorno = gestorPropiedades.valorPropertie("Entorno");
		if (!"PRODUCCION".equals(entorno)) {
			asunto = entorno + ": " + "RESULTADO EJECUCIÓN DIARIA PROCESO TESTRA";
		}
		String direccciones = gestorPropiedades.valorPropertie(RECIPIENT);
		try {
			ResultBean resultEnvio;
			resultEnvio = servicioCorreo.enviarCorreo(mensaje, null, null, asunto, direccciones, null, null, null, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Error en la notificacion de error servicio");

		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo al administrador informando del resultado de la ejecucion. Error: " + e.toString());
		}
	}

	private boolean isSancionadoIntentos(String datoTestra, Date ultima) {
		boolean resultado = false;
		boolean ejecucionCorrecta = false;
		int numIntentos = 0;

		while (numIntentos < 3 && !ejecucionCorrecta) {
			try {
				resultado = isSancionEncontrada(datoTestra, ultima);
				ejecucionCorrecta = true;
			} catch (Exception e) {
				numIntentos++;
				log.error(e.getMessage(), e);
			}
		}

		if (!ejecucionCorrecta) {
			log.error("No se han podido obtener datos de: " + datoTestra);
		}
		return resultado;
	}

	private boolean isSancionEncontrada(String dato, Date ultima) throws InterruptedException {
		boolean resultado = false;

		WebElement input = null;
		WebElement submit = null;
		WebElement resultadosDiv = null;

		String texto = null;

		driver.get(gestorPropiedades.valorPropertie("testra.web.url"));

		input = driver.findElement(By.id(gestorPropiedades.valorPropertie("testra.web.input")));
		submit = driver.findElement(By.name(gestorPropiedades.valorPropertie("testra.web.submit")));

		input.clear();
		input.sendKeys(dato.toUpperCase());
		submit.click();

		resultadosDiv = findElement(By.id(gestorPropiedades.valorPropertie("testra.web.list")));

		if (resultadosDiv != null) {
			texto = resultadosDiv.getText();
			Date fechaPublicacion = null;
			int index = texto.indexOf(gestorPropiedades.valorPropertie("testra.web.textoFecha")) + gestorPropiedades.valorPropertie("testra.web.textoFecha").length();
			fechaPublicacion = utilesFecha.convertirStringtoDate(texto.substring(index, index + gestorPropiedades.valorPropertie("testra.web.formatoFecha").length()));
			resultado = fechaPublicacion != null && (ultima == null || fechaPublicacion.after(ultima));
		}

		return resultado;
	}

	private WebElement findElement(By by) {
		WebElement element = null;
		try {
			element = driver.findElement(by);
		} catch (Throwable e) {
			log.error("Elemento no encontrado: " + by.toString());
		}
		return element;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaTestraVO> findAllConsultasTestra() {
		return consultaTestraDao.buscar(null);
	}

	@Override
	@Transactional
	public ResultBean guardarConsulta(ConsultaTestraDto consultaTestra) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado = validateDatoTestra(consultaTestra);
			if (!resultado.getError()) {
				Date fecha = new Date();
				ConsultaTestraVO consultaTestraBBDD = consultaTestraDao.getConsultaTestraPorTipoDatoNumColegiado(consultaTestra.getTipo(), consultaTestra.getDato(), consultaTestra.getNumColegiado());
				if (consultaTestraBBDD != null) {
					consultaTestraBBDD.setCorreoElectronico(consultaTestra.getCorreoElectronico());
					// Se añade un campo moficación ya que no tiene evolución
					consultaTestraBBDD.setFechaModif(new Date());
					resultado.setMensaje("Se ha actualizado ");
				} else {
					consultaTestraBBDD = conversor.transform(consultaTestra, ConsultaTestraVO.class);
					consultaTestraBBDD.setFechaAlta(fecha);
					resultado.setMensaje("Se ha dado de alta ");
				}
				consultaTestraBBDD.setActivo(EstadoVehiculo.Activo.getValorEnum());
				consultaTestraDao.guardarOActualizar(consultaTestraBBDD);
				gestionarResultado(consultaTestraBBDD, resultado);
			}
		} catch (Exception e) {
			log.error("");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void gestionarResultado(ConsultaTestraVO consultaTestra, ResultBean resultado) {
		resultado.addAttachment(ServicioConsultasTestraImpl.KEY_RESULT, conversor.transform(consultaTestra, ConsultaTestraDto.class));
		TipoConsultaTestraEnum tipo = TipoConsultaTestraEnum.getTipoConsulta(consultaTestra.getTipo());
		switch (tipo) {
			case M:
				resultado.setMensaje(resultado.getMensaje() + "la " + tipo.getDescripcion().toLowerCase() + ": " + consultaTestra.getDato());
				break;
			case C:
				resultado.setMensaje(resultado.getMensaje() + "el " + tipo.getDescripcion().toLowerCase() + ": " + consultaTestra.getDato());
				break;
			default:
				resultado.setMensaje(resultado.getMensaje() + "un/a " + tipo.getDescripcion().toLowerCase() + " con valor: " + consultaTestra.getDato());
				break;
		}
	}

	private ResultBean validateDatoTestra(ConsultaTestraDto consultaTestra) {
		ResultBean resultado = new ResultBean();
		try {
			if (StringUtils.isEmpty(consultaTestra.getTipo())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Debe introducir el tipo de dato");
			} else if (TipoConsultaTestraEnum.M.getClave().equals(consultaTestra.getTipo())) {
				if (StringUtils.isEmpty(consultaTestra.getDato())) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Debe introducir una matrícula");
				} else if (!consultaTestra.getDato().trim().matches(EXPRESION_MATRICULA)) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Formato de matrícula incorrecto");
				}
			} else if (TipoConsultaTestraEnum.C.getClave().equals(consultaTestra.getTipo())) {
				if (StringUtils.isEmpty(consultaTestra.getDato())) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Debe introducir un NIF/CIF");
				} else if (NIFValidator.validarNif(consultaTestra.getDato().trim()).intValue() <= 0) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Formato de NIF/CIF incorrecto");
				}
			}
			if (StringUtils.isEmpty(consultaTestra.getNumColegiado())) {
				resultado.setError(Boolean.TRUE);
				resultado.setListaMensajes(new ArrayList<String>());
				resultado.getListaMensajes().add("Debe introducir un número de colegiado");
			} else if (colegiadoDao.getColegiado(consultaTestra.getNumColegiado()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("No existe el colegiado con número " + consultaTestra.getNumColegiado());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getConsultaTestraDto(Long idConsultaTestra) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idConsultaTestra != null) {
				ConsultaTestraVO consultaTestraVO = consultaTestraDao.getConsultaById(idConsultaTestra);
				if (consultaTestraVO != null) {
					resultado.addAttachment(ServicioConsultasTestraImpl.KEY_RESULT, conversor.transform(consultaTestraVO, ConsultaTestraDto.class));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se encuentran datos para la consulta TESTRA con id: " + idConsultaTestra);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El id para la consulta TESTRA es obligatorio. El id recibido es: " + idConsultaTestra);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el DTO de consulta TESTRA con id: " + idConsultaTestra, e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al recuperar la consulta TESTRA con id: " + idConsultaTestra);
		}
		return resultado;
	}

	@Override
	@Transactional
	public void updateConsultaTestra(ConsultaTestraVO consultaTestra) {
		consultaTestraDao.actualizar(consultaTestra);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> obtenerNumerosColegiados() {
		return consultaTestraDao.getNumerosColegiados();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaTestraVO> getConsultasByColegiado(String numColegiado) {
		return consultaTestraDao.getConsultasByColegiado(numColegiado);
	}

	@Override
	@Transactional
	public boolean actualizarActivacion(Short activo, Long idConsulta) {
		ConsultaTestraVO consultaTestra = consultaTestraDao.getConsultaById(idConsulta);
		if (consultaTestra != null) {
			consultaTestra.setActivo(activo);
			consultaTestra.setFechaModif(new Date());
			consultaTestraDao.actualizar(consultaTestra);
			return true;
		}
		return false;
	}

	@Override
	public List<ConsultaTestraDto> lecturaFichero(File ficheroTestra) {
		List<ConsultaTestraDto> result = new ArrayList<>();
		InputStream is = null;
		Reader reader = null;
		BufferedReader input = null;
		try {
			is = new FileInputStream(ficheroTestra);
			reader = new InputStreamReader(is);
			input = new BufferedReader(reader);
			String line = null;
			String email = null;
			String dato = null;
			while ((line = input.readLine()) != null) {
				if (!line.isEmpty()) {
					ConsultaTestraDto consulta = new ConsultaTestraDto();

					// 28761: Se añade la funcionalidad de gestionar un correo electronico.
					String[] partes = line.split(";");
					dato = partes[0];
					if (partes.length > 1 && UtilesValidaciones.validarMail(partes[1])) {
						consulta.setCorreoElectronico(email = partes[1]);
					}

					consulta.setDato(dato.toUpperCase());
					consulta.setTipo(getTipoDato(consulta.getDato()));

					if (consulta.getTipo() == null) {
						consulta.setActivo(EstadoVehiculo.Desactivo.getValorEnum());
					} else {
						consulta.setActivo(EstadoVehiculo.Activo.getValorEnum());
					}

					result.add(consulta);
				}
			}
		} catch (FileNotFoundException e) {
			log.error("No se encuentra el fichero TESTRA con los datos a importar", e);
		} catch (IOException e) {
			log.error("Error al leer el fichero TESTRA con los datos a importar", e);
		} catch (RuntimeException e) {
			log.error("Error inesperado al tratar el fichero TESTRA de importacion", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso BufferedReader", e);
			}
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso InputStreamReader", e);
			}
			try {
				is.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso FileInputStream", e);
			}
		}
		return result;
	}

	private String getTipoDato(String line) {
		String tipo = null;
		if (line.trim().matches(EXPRESION_MATRICULA)) {
			tipo = TipoConsultaTestraEnum.M.getClave();
		} else if (NIFValidator.validarNif(line.trim()).intValue() > 0) {
			tipo = TipoConsultaTestraEnum.C.getClave();
		}
		return tipo;
	}

	@Override
	@Transactional
	public ConsultaTestraVO getConsultaByID(int id) {
		Long idLong = (long) id;
		return consultaTestraDao.getConsultaById(idLong);
	}
}