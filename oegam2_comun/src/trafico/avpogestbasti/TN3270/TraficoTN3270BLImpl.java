package trafico.avpogestbasti.TN3270;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.consultasTGate.model.enumerados.TipoServicioTGate;
import org.gestoresmadrid.core.consultasTGate.model.vo.ConsultasTGateVO;
import org.gestoresmadrid.oegam2comun.consultasTGate.model.service.ServicioConsultasTGate;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.rwc3270.get3270;
import com.rwc3270.utl3270;

import general.utiles.TripleDES;
import trafico.beans.avpobastigest.ListaValoresTraficoTN3270;
import trafico.beans.avpobastigest.ParamsTraficoTN3270Bean;
import trafico.beans.avpobastigest.ResultTraficoTN3270Bean;
import trafico.beans.avpobastigest.SubListaValoresTN3270Bean;
import trafico.utiles.constantes.ConstantesTN3270;
import trafico.utiles.enumerados.AplicacionTraficoTN3270;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

public class TraficoTN3270BLImpl {

	private static final String ID_PANTALLA = "PANTALLA";
	private static final String MENSAJE_LIBRE_CARGAS = "VEHICULO LIBRE DE CARGAS";
	private static final ILoggerOegam log = LoggerOegam.getLogger(TraficoTN3270BLImpl.class);

	@Autowired
	private ServicioConsultasTGate servicioConsultasTGate;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	public TraficoTN3270BLImpl() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ResultTraficoTN3270Bean solicitudTrafico(ParamsTraficoTN3270Bean params, BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen) {
		log.trace("Entra en solicitudTrafico");

		ResultTraficoTN3270Bean result = new ResultTraficoTN3270Bean();
		String tipoServicio = "";
		try {
			if (AplicacionTraficoTN3270.BSTI.equals(params.getCodigoAplicacion())) {
				result = solicitudBSTI(params);
				tipoServicio = TipoServicioTGate.BSTI.getValorEnum();
			} else if (AplicacionTraficoTN3270.AVPO.equals(params.getCodigoAplicacion())) {
				result = solicitudAVPO(params);
				tipoServicio = TipoServicioTGate.AVPO.getValorEnum();
			} else if (AplicacionTraficoTN3270.GEST.equals(params.getCodigoAplicacion())) {
				result = solicitudGEST(params);
				tipoServicio = TipoServicioTGate.GEST.getValorEnum();
			}
			try {
				guardarConsultasTGate(numExpediente, idUsuario, numColegiado, idVehiculo, origen, tipoServicio, result.getCodigoRespuesta());
			} catch (Exception e) {
				log.error("Error al guardar en Consultas de TGate", e);
			}
		} catch (Throwable e) {
			log.error("Error: Excepción en la aplicación DGT -> " + e);
			HashMap<String, String> listaValores = new HashMap<String, String>();
			listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Error en la aplicación DGT");
			result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
		}

		log.trace("Sale de solicitudTrafico");
		return result;
	}

	private void guardarConsultasTGate(BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen, String tipoServicio, String respuesta) {
		ConsultasTGateVO consultasTGateVO = new ConsultasTGateVO();

		consultasTGateVO.setFechaHora(Calendar.getInstance().getTime());
		consultasTGateVO.setIdVehiculo(idVehiculo);
		consultasTGateVO.setNumColegiado(numColegiado);
		consultasTGateVO.setNumExpediente(numExpediente);
		consultasTGateVO.setOrigen(origen);
		consultasTGateVO.setRespuesta(respuesta);
		consultasTGateVO.setTipoServicio(tipoServicio);
		consultasTGateVO.setIdUsuario(idUsuario);

		servicioConsultasTGate.guardar(consultasTGateVO);
	}

	/**
	 * Inicializa la sesión con los valores necesarios para la conexión, así como el xml de uso de la aplicación que se vaya a invocar.
	 * @param session
	 * @param xml
	 */
	private void initSession(get3270 session, String xml) throws Throwable {
		// Se lee el fichero secret.key para obtener la clave de cifrado de contraseñas.
		FileInputStream fis = new FileInputStream(gestorPropiedades.valorPropertie("secret.key.url"));
		byte[] datos = new byte[fis.available()];
		fis.read(datos);
		// Clave de cifrado
		String claveCifrado = new String(datos);

		// String p = TripleDES.encrypt(claveCifrado, "5LDJ", log);
		// log.error("Calve cifrada\n\n"+p+"\n\n*******************************************************");

		// Se descifra la contraseña de conexión
		String passwordEnClaro = TripleDES.decrypt(claveCifrado, gestorPropiedades.valorPropertie("dgt.aplicacion.password"), log);

		session.open(xml);
		session.setValue(":HOST", gestorPropiedades.valorPropertie("dgt.aplicacion.host"));
		session.setValue(":APLICACION", gestorPropiedades.valorPropertie("dgt.aplicacion.name"));
		session.setValue(":USUARIO", gestorPropiedades.valorPropertie("dgt.aplicacion.user"));
		session.setValue(":CLAVE", passwordEnClaro);
	}

	/**
	 * Devuelve el valor de un campo quitando los espacios que sobran. En caso de que sea un campo vacío devuelve null.
	 * @param session
	 * @param campo
	 * @return
	 */
	private String getValor(get3270 session, String campo) {
		String valor = session.getValue(campo);
		if (valor != null && !"".equals(valor.trim())) {
			return valor.trim();
		}
		return null;
	}

	/**
	 * Invoca a la aplicación de tráfico con los parámetros del BSTI
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	private ResultTraficoTN3270Bean solicitudBSTI(ParamsTraficoTN3270Bean params) throws Throwable {
		log.trace("Se trata de una invocación a la aplicación BSTI");
		ResultTraficoTN3270Bean result = null;
		get3270 session = new get3270();
		// Asignamos los valores de conexión a la DGT
		initSession(session, gestorPropiedades.valorPropertie("dgt.xml.bsti.url"));

		// Pasamos los parametros de la consulta
		session.setValue(":BASTIDOR", params.getListaParametros().get(ConstantesTN3270.BASTIDOR));

		boolean capturaCorrecta;

		capturaCorrecta = session.capture(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.host"), Integer.parseInt(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.port")));

		if (capturaCorrecta) {
			String existe = getValor(session, "existe");
			if ("EXISTE".equals(existe)) {
				String[] matcod = session.getValues("matcod");
				String matriculas;
				String matricula = "";
				if (matcod.length == 0) {
					log.error("Error: No existen matrículas");
					HashMap<String, String> listaValores = new HashMap<String, String>();
					listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Error en la lectura de las matrículas");
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.NO_EXISTEN_DATOS, listaValores);
				} else if (matcod.length > 1) {
					matriculas = matcod[0].concat(", ");
					log.error("Error: Existe más de una matrícula para el número de bastidor indicado");
					HashMap<String, String> listaValores = new HashMap<>();
					listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Existe más de una matrícula con ese número de bastidor");

					for (int i = 1; i < matcod.length; i++) {
						matricula = matriculas.concat(matcod[i].trim());
					}
					result = new ResultTraficoTN3270Bean("Existe más de una matrícula con ese número de bastidor".concat(matricula));
				} else {
					HashMap<String, String> listaValores = new HashMap<>();
					listaValores.put(ConstantesTN3270.MATRICULA, matcod[0].trim().replaceAll(" ", ""));
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.OK, listaValores);
				}
			} else if ("NO EXISTE".equals(existe)) {
				log.error("Error: No existen datos para el numero de bastidor indicado");
				HashMap<String, String> listaValores = new HashMap<>();
				listaValores.put(ConstantesTN3270.MENSAJE_ERROR, existe);
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.NO_EXISTEN_DATOS, listaValores);
			} else {
				log.error("Error: Fallo en la lectura de los datos");
				HashMap<String, String> listaValores = new HashMap<String, String>();
				listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Error en la captura de los datos");
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
			}
		} else {
			log.error("Identificador del error devuelto: " + session.getError());
			// Mantis 16559. David Sierra: Control errores devueltos por GEST
			if (session.getErrMsg() == null) {
				log.error("Error en la conexión con la DGT, se ha devuelto un valor nulo con el codigo de aplicacion " + params.getCodigoAplicacion() + " y el bastidor "
						+ params.getListaParametros().get(ConstantesTN3270.BASTIDOR));
			} else {
				log.error("Error en la conexión con la DGT: " + session.getErrMsg());
			}
			switch (session.getError()) {
			// Traza del error 1 devuelto por GEST
				case 1:
					log.error("Error de tipo " + session.getError() + " con el codigo de aplicacion " + params.getCodigoAplicacion() + " y el bastidor "
							+ params.getListaParametros().get(ConstantesTN3270.BASTIDOR));
					break;
				// Traza del error -2 devuelto por GEST
				case -2:
					log.error("Error de tipo " + session.getError() + " con el codigo de aplicacion " + params.getCodigoAplicacion() + " y el bastidor "
							+ params.getListaParametros().get(ConstantesTN3270.BASTIDOR));
					break;
				// Traza del error -4 devuelto por GEST
				case -4:
					log.error("Error de tipo " + session.getError() + " con el codigo de aplicacion " + params.getCodigoAplicacion() + " y el bastidor "
							+ params.getListaParametros().get(ConstantesTN3270.BASTIDOR));
					break;
				// Traza de otro tipo de error devuelto por GEST
				default:
					log.error("Error de tipo " + session.getError() + " con el codigo de aplicacion " + params.getCodigoAplicacion() + " y el bastidor "
							+ params.getListaParametros().get(ConstantesTN3270.BASTIDOR));
					break;
			// Fin Mantis
			}
			HashMap<String, String> listaValores = new HashMap<String, String>();
			listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Error en la invocación al servicio de la DGT");
			result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
		}

		/*
		 * if("LBXPCNJM26X002129".equals(params.getListaParametros().get(BASTIDOR))){ HashMap<CasillaTraficoTN3270, String> listaValores = new HashMap<CasillaTraficoTN3270, String>(); listaValores.put(MATRICULA, "6134GGT"); result = new ResultTraficoTN3270Bean(OK, listaValores); }else
		 * if("WF0DXXGAJD6B45542".equals(params.getListaParametros().get(BASTIDOR))){ HashMap<CasillaTraficoTN3270, String> listaValores = new HashMap<CasillaTraficoTN3270, String>(); listaValores.put(MATRICULA, "6900GMC"); result = new ResultTraficoTN3270Bean(OK, listaValores); }else
		 * if("WVGZZZ5NZ8W030450".equals(params.getListaParametros().get(BASTIDOR))){ HashMap<CasillaTraficoTN3270, String> listaValores = new HashMap<CasillaTraficoTN3270, String>(); listaValores.put(MATRICULA, "2542FGC"); result = new ResultTraficoTN3270Bean(OK, listaValores); }else
		 * if("LBXPCNJM26X002129".equals(params.getListaParametros().get(BASTIDOR))){ HashMap<CasillaTraficoTN3270, String> listaValores = new HashMap<CasillaTraficoTN3270, String>(); listaValores.put(MATRICULA, "3535GGG"); result = new ResultTraficoTN3270Bean(OK, listaValores); }else{
		 * log.error("Error: No existen datos para el numero de bastidor indicado"); HashMap<CasillaTraficoTN3270, String> listaValores = new HashMap<CasillaTraficoTN3270, String>(); listaValores.put(MENSAJE_ERROR, "No existen datos para el numero de bastidor indicado"); result = new
		 * ResultTraficoTN3270Bean(NO_EXISTEN_DATOS, listaValores); }
		 */

		return result;
	}

	/**
	 * Invoca a la aplicación de tráfico con los parámetros del AVPO
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	private ResultTraficoTN3270Bean solicitudAVPO(ParamsTraficoTN3270Bean params) throws Throwable {

		log.trace("Se trata de una invocación a la aplicación AVPO");
		ResultTraficoTN3270Bean result = new ResultTraficoTN3270Bean();
		get3270 session = new get3270();
		// Asignamos los valores de conexión a la DGT
		initSession(session, gestorPropiedades.valorPropertie("dgt.xml.avpo.url"));

		// Pasamos los parametros de la consulta

		String matricula = utiles.cambiaFormatoMatricula(params.getListaParametros().get(ConstantesTN3270.MATRICULA));
		String campoAux = "";
		String matr = "";
		if (matricula.length() > 8) {
			matr = matricula.substring(0, 8);
			campoAux = matricula.substring(8);
		} else {
			matr = matricula;
		}
		session.setValue(":MATRICULA", matr);
		session.setValue(":CAMPOAUX", campoAux);

		if (params.getListaParametros().get(ConstantesTN3270.CODIGO_TASA) != null) {
			session.setValue(":TASA", params.getListaParametros().get(ConstantesTN3270.CODIGO_TASA));
		} else {
			session.setValue(":TASA", "");
		}

		boolean capturaCorrecta = false;

		capturaCorrecta = session.capture(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.host"), Integer.parseInt(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.port")));

		if (capturaCorrecta) {
			String mensaje = getValor(session, "mensaje");
			if (mensaje != null && (mensaje.contains("ERROR") || mensaje.contains("VEHICULO SIN ANTECEDENTES") || mensaje.contains("TASA") || mensaje.contains("OPERADOR NO AUTORIZADO"))) {
				HashMap<String, String> listaValores = new HashMap<String, String>();
				listaValores.put(ConstantesTN3270.MENSAJE_ERROR, mensaje.trim());
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_PARAMETROS, listaValores);
			} else {
				HashMap<String, String> listaValores = new HashMap<>();

				List<String> listaDatos = new ArrayList<String>();
				String pantallas = session.getValue("screen.values");
				if (pantallas != null && !"".equals(pantallas)) {
					int numPantallas = Integer.parseInt(pantallas);
					int index = 0;
					while (index < numPantallas) {
						String pantalla = session.getValue("screen." + index);
						String line[] = utl3270.lines(pantalla);
						// System.out.println("Pantalla: ");
						// for(String linea: line){
						// System.out.println(linea);
						// }
						// System.out.println("\n\n");
						if (line[0].contains(ID_PANTALLA)) {
							int y = 0;
							// No leemos la última línea
							while (y < line.length - 1) {
								listaDatos.add(line[y]);
								y++;
							}
						}
						index++;
					}
				}
				if (listaDatos.isEmpty()) {
					listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "SIN INFORME [" + mensaje + "]");
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_PARAMETROS, listaValores);
				} else {
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.OK, listaDatos, listaValores);
				}
			}
		} else {
			log.error("Identificador del error devuelto: " + session.getError());
			log.error("Error en la conexión con la DGT: " + session.getErrMsg());

			HashMap<String, String> listaValores = new HashMap<>();
			listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "No se han podido leer las limitaciones de disposición");
			result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
		}

		return result;
	}

	/**
	 * Invoca a la aplicación de tráfico con los parámetros del GEST
	 * @param params
	 * @return
	 * @throws Throwable
	 */

	private ResultTraficoTN3270Bean solicitudGEST(ParamsTraficoTN3270Bean params) throws Throwable {
		log.trace("Se trata de una invocación a la aplicación GEST");
		ResultTraficoTN3270Bean result = null;
		get3270 session = new get3270();
		// Asignamos los valores de conexión a la DGT
		initSession(session, gestorPropiedades.valorPropertie("dgt.xml.gest.url"));

		// Pasamos los parametros de la consulta
		session.setValue(":MATRICULA", utiles.cambiaFormatoMatricula(params.getListaParametros().get(ConstantesTN3270.MATRICULA)));

		String nifTitular = params.getListaParametros().get(ConstantesTN3270.NIF_TITULAR);
		String inicioNif = nifTitular.substring(0, 8);
		String finNif = "";
		if (NIFValidator.isValidCIF(nifTitular) || NIFValidator.isValidNIE(nifTitular) || NIFValidator.isValidEspanolNoResidente(nifTitular) || NIFValidator.isValidExtranjeroSinNIE(nifTitular)
				|| NIFValidator.isValidMenor14(nifTitular)) {
			finNif = nifTitular.substring(8);
		}
		session.setValue(":NIF_TITULAR", inicioNif);
		session.setValue(":ULT_NIF", finNif);

		boolean capturaCorrecta = false;

		// if(INDICADOR_ENTORNO_PRODUCCION.toBoolean()){
		capturaCorrecta = session.capture(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.host"), Integer.parseInt(gestorPropiedades.valorPropertie("dgt.aplicacion.socket.port")));
		// }else{
		// capturaCorrecta = session.capture();
		// }

		if (capturaCorrecta) {
			String[] tipos = session.getValues("TIPO_CARGA");
			String[] registros = session.getValues("REGISTRO_CARGA");
			String[] fechas = session.getValues("FECHA_CARGA");
			String[] financieras = session.getValues("FINANCIERA_CARGA");
			String[] cargas = session.getValues("ATMV");
			String[] conceptos = session.getValues("CONCEPTO_EMBARGO");
			String[] exped = session.getValues("EXPED_EMBARGO");
			String[] fembargo = session.getValues("FECHA_EMBARGO");
			String[] fmateri = session.getValues("FMAT_EMBARGO");
			String[] autoridad = session.getValues("AUTORIDAD_EMBARGO");

			/*
			 * if(cargas!= null){ List<SubListaValoresTN3270Bean> sublistasValores = new ArrayList<SubListaValoresTN3270Bean>(); HashMap<String, String> sublistaLD = new HashMap<String, String>(); sublistaLD.put(ConstantesTN3270.CARGA_TIPO, cargas[0].trim()); SubListaValoresTN3270Bean ld = new
			 * SubListaValoresTN3270Bean(); ld.setIdTipoLista(ListaValoresTraficoTN3270.CARGA); sublistasValores.add(ld); result = new ResultTraficoTN3270Bean(ConstantesTN3270.OK, new HashMap<String, String>(), sublistasValores); return result; }
			 */
			// Si la variable de sesión ATMV no es nula ni vacia, sino es así existe información de pendiente de pago de I.V.T.M
			if (cargas != null && cargas.length != 0)
				tipos = cargas;
			if ((tipos != null && tipos.length != 0) || (conceptos != null && conceptos.length != 0)) {

				List<SubListaValoresTN3270Bean> sublistasValores = new ArrayList<>();

				int i = 0;
				if (tipos != null) {
					for (String tipo : tipos) {
						// La primera línea son las rayas que delimitan la cabecera
						if (i != 0) {
							if (!"".equals(tipo.trim()) || !"".equals(fechas[i].trim()) || !"".equals(registros[i].trim()) || !"".equals(financieras[i].trim())) {
								HashMap<String, String> sublistaLD = new HashMap<>();
								sublistaLD.put(ConstantesTN3270.CARGA_TIPO, tipo.trim());
								if (fechas != null && i < fechas.length) {
									sublistaLD.put(ConstantesTN3270.CARGA_FECHA, fechas[i].trim());
								}
								if (registros != null && i < registros.length) {
									sublistaLD.put(ConstantesTN3270.CARGA_NUM_REGISTRO, registros[i].trim());
								}
								if (financieras != null && i < financieras.length) {
									sublistaLD.put(ConstantesTN3270.CARGA_FINANCIERA_DOMICILIO, financieras[i].trim());
								}
								SubListaValoresTN3270Bean ld = new SubListaValoresTN3270Bean();
								ld.setIdTipoLista(ListaValoresTraficoTN3270.CARGA);
								ld.setListaValores(sublistaLD);
								sublistasValores.add(ld);
							}
						}
						if (cargas != null && cargas.length != 0) {
							HashMap<String, String> sublistaLD = new HashMap<>();
							sublistaLD.put(ConstantesTN3270.CARGA_TIPO, tipo.trim());
							SubListaValoresTN3270Bean ld = new SubListaValoresTN3270Bean();
							ld.setIdTipoLista(ListaValoresTraficoTN3270.CARGA);
							ld.setListaValores(sublistaLD);
							sublistasValores.add(ld);
							break;
						}
						i++;
					}
				}
				i = 0;
				if (conceptos != null) {
					for (String concepto : conceptos) {
						// La primera línea son las rayas que delimitan la cabecera
						if (i != 0) {
							if (!"".equals(concepto.trim()) || !"".equals(exped[i].trim()) || !"".equals(fembargo[i].trim()) || !"".equals(fmateri[i].trim()) || !"".equals(autoridad[i].trim())) {
								HashMap<String, String> sublistaLD = new HashMap<>();
								sublistaLD.put(ConstantesTN3270.EMBARGO_CONCEPTO, concepto.trim());
								if (exped != null && i < exped.length) {
									sublistaLD.put(ConstantesTN3270.EMBARGO_EXPEDIENTE, exped[i].trim());
								}
								if (fembargo != null && i < fembargo.length) {
									sublistaLD.put(ConstantesTN3270.EMBARGO_FECHA, fembargo[i].trim());
								}
								if (fmateri != null && i < fmateri.length) {
									sublistaLD.put(ConstantesTN3270.EMBARGO_FMATER, fmateri[i].trim());
								}
								if (autoridad != null && i < autoridad.length) {
									sublistaLD.put(ConstantesTN3270.EMBARGO_AUTORIDAD, autoridad[i].trim());
								}
								SubListaValoresTN3270Bean ld = new SubListaValoresTN3270Bean();
								ld.setIdTipoLista(ListaValoresTraficoTN3270.EMBARGO);
								ld.setListaValores(sublistaLD);
								sublistasValores.add(ld);
							}
						}
						i++;
					}
				}
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.OK, new HashMap<String, String>(), sublistasValores);
			} else {
				log.error("Error en la lectura de las limitaciones de disposición");
				HashMap<String, String> listaValores = new HashMap<>();
				listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "No se han podido leer las limitaciones de disposición");
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
			}
		} else {
			String mensaje = getValor(session, "mensaje");
			if (session.getError() == 1 && mensaje != null) {
				if (mensaje.contains(MENSAJE_LIBRE_CARGAS)) {
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.OK);
				} else {
					log.error("Identificador del error devuelto: " + session.getError());
					log.error("Error en la conexión con la DGT: " + session.getErrMsg());
					HashMap<String, String> listaValores = new HashMap<>();
					listaValores.put(ConstantesTN3270.MENSAJE_ERROR, mensaje.trim());
					result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_PARAMETROS, listaValores);
				}
			} else {
				log.error("Identificador del error devuelto: " + session.getError());
				log.error("Error en la conexión con la DGT: " + session.getErrMsg());
				HashMap<String, String> listaValores = new HashMap<>();
				listaValores.put(ConstantesTN3270.MENSAJE_ERROR, "Error en la invocación al servicio de la DGT");
				result = new ResultTraficoTN3270Bean(ConstantesTN3270.ERROR_CONEXION, listaValores);
			}
		}
		return result;
	}

	public ServicioConsultasTGate getServicioConsultasTGate() {
		return servicioConsultasTGate;
	}

	public void setServicioConsultasTGate(ServicioConsultasTGate servicioConsultasTGate) {
		this.servicioConsultasTGate = servicioConsultasTGate;
	}
}