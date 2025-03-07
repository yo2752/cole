package trafico.avpogestbasti.TN3270;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.avpobastigest.AvpoBean;
import trafico.beans.avpobastigest.ContadoresUsoBean;
import trafico.beans.avpobastigest.ParamsTraficoTN3270Bean;
import trafico.beans.avpobastigest.ResultTraficoTN3270Bean;
import trafico.utiles.constantes.ConstantesTN3270;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.AplicacionTraficoTN3270;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class Avpo {

	private static final int _17 = 17;
	private static final ILoggerOegam log = LoggerOegam.getLogger(Avpo.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public Avpo() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public AvpoBean obtenerDatos(String matricula, String codigoTasa, BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen) {
		log.trace("Entra en obtenerDatos");

		AvpoBean bean = new AvpoBean();

		// Ricardo Rodriguez. Incidencia 3061. 04/12/2012
		// Resumen : Saltar la invocación de solicitud de información a dgt:
		String desactivar = gestorPropiedades.valorPropertie(ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVAR);
		if (desactivar != null && desactivar.equalsIgnoreCase("si")) {
			String mensaje = ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVADA_MENSAJE;
			if (mensaje != null && !mensaje.equals("")) {
				bean.setMensaje(mensaje);
			} else {
				bean.setMensaje(ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_MENSAJE_X_DEFECTO);
			}
			bean.setDesactivadaConsulta(true);
			bean.setError(true);
			return bean;
		}
		// Fin. Ricardo Rodriguez. Incidencia 3061. 04/12/2012

		bean.setMatricula(matricula);
		bean.setTasa(codigoTasa);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(ConstantesTN3270.MATRICULA, matricula.trim());
		params.put(ConstantesTN3270.CODIGO_TASA, codigoTasa);

		TraficoTN3270BLImpl traficoTN3270BL = new TraficoTN3270BLImpl();

		ParamsTraficoTN3270Bean paramsBean = new ParamsTraficoTN3270Bean(AplicacionTraficoTN3270.AVPO, params);

		ResultTraficoTN3270Bean resultTraficoBean = new ResultTraficoTN3270Bean();
		resultTraficoBean = traficoTN3270BL.solicitudTrafico(paramsBean, numExpediente, idUsuario, numColegiado, idVehiculo, origen);

		if (resultTraficoBean == null) {
			log.error("Error en la invocación");
			bean.setMensaje("Error en la invocación");
			bean.setError(Boolean.TRUE);
			bean.setRepetir(true);
		} else if (ConstantesTN3270.OK.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.info("Respuesta OK. Matricula: " + matricula);

			List<String> listaDatos = new ArrayList<>();

			List<String> sublistas = resultTraficoBean.getListas();
			if (sublistas != null) {
				for (String sublista : sublistas) {
					listaDatos.add(sublista);
				}
			}

			bean.setListaDatos(listaDatos);
			bean.setRepetir(false);
			bean.setError(Boolean.FALSE);
		} else if (ConstantesTN3270.ERROR_PARAMETROS.equals(resultTraficoBean.getCodigoRespuesta())) {
			// Se añade un contador de uso si es el caso
			log.error("Error en los parámetros: " + resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR));
			if (resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR) != null) {
				bean.setMensaje(resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR));
			} else {
				bean.setMensaje("Error en los parámetros.");
			}
			bean.setError(Boolean.TRUE);
			bean.setRepetir(true);

		} else if (ConstantesTN3270.ERROR_CONEXION.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.error("Error en la conexión con el servicio de tráfico");
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
			bean.setRepetir(true);
		} else {
			log.error("Error en la respuesta: " + resultTraficoBean.getCodigoRespuesta());
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
			bean.setRepetir(true);
		}

		log.trace("Sale de obtenerDatos");
		return bean;
	}

	public ResultBean asignarTasa(String codigoTasa, Boolean tasaAuto) {
		log.trace("Entra en asignarTasa");
		ResultBean resultBean = new ResultBean();
		resultBean.setError(true);

		/*
		 * if(!tasaAuto && (codigoTasa==null || "".equals(codigoTasa))){ resultBean.setMensaje("El código de tasa es obligatorio"); }else if(tasaAuto){ resultBean = traficoTasaBL.asignarTasaAutoSolicitud(); }else{ String mensaje = DGTValidacionesRegistros.validarCodigoTasa(codigoTasa, false);
		 * if(mensaje!=null){ resultBean.setMensaje(mensaje); }else{ resultBean = traficoTasaBL.asignarTasaSolicitud(codigoTasa); } }
		 */

		log.trace("Sale de asignarTasa");

		return resultBean;
	}

	public void desasignarTasa(String codigoTasa) {
		/*
		 * try{ traficoTasaBL.desasignarTasaSolicitud(codigoTasa); }catch(JMPException e){ log.error("Error desasignando tasa: " + codigoTasa); log.error("Error desasignando tasa: " + e.getStackTraceToString()); }
		 */
	}

	public String obtenerTasaAVPO(String codigoTasa, String tipoTramite) {
		log.trace("Entra en obtenerTasaAVPO, codigoTasa: " + codigoTasa + ", Tipo de Tramite : " + tipoTramite);

		String tasa = "";

		// Obtener tasa
		// tasa = traficoTasaBL.obtenerTasaAVPO(codigoTasa, tipoTramite);

		log.trace("Sale de obtenerTasaAVPO");
		return tasa;
	}

	public void eliminarContadorUso(BigDecimal idContadorUso) {
		try {
			ContadoresUsoBean bean = new ContadoresUsoBean();
			bean.setIdContadoresUso(idContadorUso);
			// contadoresUsoBL.delete(bean);
		} catch (Throwable e) {
			log.error("Error eliminando contador de uso: " + idContadorUso);
		}
	}

	public ResultBean listadoLoteAVPO(List<TramiteTraficoBean> listaBeans) {
		ResultBean resultBean = new ResultBean();
		int contadorTasas = 0; // Cuenta el número de tasas libres necesarias en el almacén

		int i = 2;
		for (TramiteTraficoBean bean : listaBeans) {
			if (bean != null) {
				String matricula = bean.getVehiculo().getMatricula();
				String numBastidor = bean.getVehiculo().getBastidor();
				String codigoTasa = bean.getTasa().getCodigoTasa();

				if (matricula != null /* && !DGTValidacionesRegistros.validarMatricula(matricula) */) {
					resultBean.setMensaje("Línea " + i + ": Matrícula no válida");
				}

				if (numBastidor != null && numBastidor.length() > _17) {
					resultBean.setMensaje("Línea " + i + ": Longitud de bastidor no válida");
				}

				if (codigoTasa != null) {
					// Se comprueba que sea un código de tasa válido
					String mensaje = null/* DGTValidacionesRegistros.validarCodigoTasa(codigoTasa, false) */;
					if (mensaje != null) {
						resultBean.setMensaje("Línea " + i + ": " + mensaje);
					} else {
						// Se comprueba que la tasa no está asignada y existe en el almacén del contrato
						mensaje = null; /* traficoTasaBL.comprobarTasaAVPO(codigoTasa); */
						if (mensaje != null) {
							resultBean.setMensaje("Línea " + i + ": " + mensaje);
						}
					}
				} else {
					contadorTasas++;
				}
			} else {
				resultBean.setMensaje("Línea " + i + ": El contenido no es correcto");
			}
			i++;
		}

		// Si no hay errores se asignan tasas a los registros que no tengan.
		if (resultBean.getMensaje() == null) {
			if (contadorTasas != 0) {
				List<String> listaTasas = obtenerTasasLoteAVPO(contadorTasas);
				if (listaTasas != null) {
					int y = 0;
					for (TramiteTraficoBean bean : listaBeans) {
						if (bean.getTasa().getCodigoTasa() == null) {
							bean.getTasa().setCodigoTasa(listaTasas.get(y));
							y++;
						}
					}
				} else {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje("No hay tasas libres suficientes en el almacén del contrato");
				}
			}
		} else {
			resultBean.setError(Boolean.TRUE);
		}

		return resultBean;
	}

	public List<String> obtenerTasasLoteAVPO(int cantidadTasas) {
		log.trace("Entra en obtenerTasasLoteAVPO");

		List<String> listaTasas = null;

		// listaTasas = traficoTasaBL.obtenerTasasLoteAVPO(cantidadTasas);

		log.trace("Sale de obtenerTasasLoteAVPO");
		return listaTasas;
	}

	/*
	 * public List<AvpoBean> obtenerConsultaLote(List<TramiteTraficoBean> listaMatriculas){ List<AvpoBean> listaAvpo = new ArrayList<AvpoBean>(); AvpoBean avpoBean = null; ResultBean resultBean = null; for(TramiteTraficoBean bean: listaMatriculas){ resultBean =
	 * asignarTasa(bean.getTasa().getCodigoTasa(), Boolean.FALSE); if(resultBean.getError()){ avpoBean = new AvpoBean(); avpoBean.setMatricula(bean.getVehiculo().getMatricula()); avpoBean.setTasa(bean.getTasa().getCodigoTasa()); avpoBean.setError(true); avpoBean.setMensaje(resultBean.getMensaje());
	 * }else{ // Si estamos en PRE y es el contrato del Colegio no se envía la tasa al avpo" boolean enviarTasa = true; if("0000".equals(userProfile.getNumColegiado())){ enviarTasa = false; } avpoBean = obtenerDatos(bean.getVehiculo().getMatricula(), bean.getTasa().getCodigoTasa());
	 * //log.error("AVPO Matrícula: " + bean.getVehiculo().getMatricula() + " Colegiado: " + userProfile.getNumColegiado()); if(avpoBean.getError()){ desasignarTasa(bean.getTasa().getCodigoTasa()); log.error("Fallo impresión de AVPO - Matrícula: " + bean.getVehiculo().getMatricula() + "Error: " +
	 * avpoBean.getMensaje()); } } //Se completan los datos para insertar un registro con la consulta avpoBean.setBastidor(bean.getVehiculo().getBastidor()); avpoBean.setFechaInforme(new Date()); //guardarConsulta(avpoBean, userProfile); listaAvpo.add(avpoBean); } return listaAvpo; }
	 */

	public AvpoBean obtenerConsulta(String matricula, String numBastidor, String tasa, BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen) {
		AvpoBean avpoBean = null;
		ResultBean resultBean = null;

		resultBean = asignarTasa(tasa, Boolean.FALSE);
		if (resultBean.getError()) {
			avpoBean = new AvpoBean();
			avpoBean.setMatricula(matricula);
			avpoBean.setTasa(tasa);
			avpoBean.setError(true);
			avpoBean.setMensaje(resultBean.getMensaje());
		} else {
			// Si estamos en PRE y es el contrato del Colegio no se envía la tasa al avpo"
			/*
			 * if("0000".equals(userProfile.getNumColegiado())){ enviarTasa = false; }
			 */

			avpoBean = obtenerDatos(matricula, tasa, numExpediente, idUsuario, numColegiado, idVehiculo, origen);
			// log.error("AVPO Matrícula: " + matricula + " Colegiado: " + userProfile.getNumColegiado());

			if (avpoBean.getError()) {
				desasignarTasa(tasa);
				log.error("Fallo impresión de AVPO - Matrícula: " + matricula + " Error: " + avpoBean.getMensaje());
			}
		}

		// Se completan los datos para insertar un registro con la consulta
		avpoBean.setBastidor(numBastidor);
		avpoBean.setFechaInforme(new Date());
		// guardarConsulta(avpoBean, userProfile);

		return avpoBean;
	}

	/*
	 * private void guardarConsulta(AvpoBean avpoBean, UsrUserProfile userProfile){ try{ TraficoInformeAvpoBean informeAvpoBean = new TraficoInformeAvpoBean(); informeAvpoBean.setMatricula(avpoBean.getMatricula()); informeAvpoBean.setNumBastidor(avpoBean.getBastidor());
	 * informeAvpoBean.setCodigoTasa(avpoBean.getTasa()); informeAvpoBean.setResultado(!avpoBean.getError()); informeAvpoBean.setFecha(avpoBean.getFechaInforme()); informeAvpoBean.setIdContrato(userProfile.getIdContrato()); informeAvpoBean.setIdUsuario(userProfile.getIdUsuario());
	 * if(avpoBean.getListaDatos()!=null && avpoBean.getListaDatos().size()!=0){ StringBuffer datosInforme = new StringBuffer(); for(String datos: avpoBean.getListaDatos()){ datosInforme.append(datos + "\n"); } log.debug("Datos del informe: " + datosInforme); informeAvpoBean.setDatosInforme(new
	 * Blob(datosInforme.toString())); }else{ informeAvpoBean.setDatosInforme(new Blob(avpoBean.getMensaje())); } traficoInformeAvpoBL.insert(informeAvpoBean); }catch (Exception e){ log.error("Error al almacenar los datos del informe en TRAFICO_INFORME_AVPO - Matrícula: " + avpoBean.getMatricula() +
	 * " ID_USUARIO: " + userProfile.getIdUsuario()); log.error(e.getStackTrace()); } }
	 */

	public AvpoBean obtenerAvpoHistorico(Long idTraficoInformeAvpo) {
		AvpoBean avpoBean = new AvpoBean();
		/*
		 * TraficoInformeAvpoBean informeAvpoBean = new TraficoInformeAvpoBean(); informeAvpoBean.setIdTraficoInformeAvpo(idTraficoInformeAvpo); informeAvpoBean= traficoInformeAvpoBL.findByPrimaryKey(informeAvpoBean); log.info("Imprimiendo histórico AVPO - IdTraficoInformeAvpo: " +
		 * idTraficoInformeAvpo); avpoBean.setIdContrato(informeAvpoBean.getIdContrato()); avpoBean.setMatricula(informeAvpoBean.getMatricula()); avpoBean.setTasa(informeAvpoBean.getCodigoTasa()); avpoBean.setFechaInforme(informeAvpoBean.getFecha());
		 * if(informeAvpoBean.getResultado().booleanValue()){ avpoBean.setError(Boolean.FALSE); List<String> listaDatos = new ArrayList<String>(); if(informeAvpoBean.getDatosInforme()!=null){ String datosInforme = new String(informeAvpoBean.getDatosInforme().getBytes()); String [] datos =
		 * datosInforme.split("\n"); for(String linea: datos){ listaDatos.add(linea); } } avpoBean.setListaDatos(listaDatos); }else{ avpoBean.setError(Boolean.TRUE); if(informeAvpoBean.getDatosInforme()!=null){ avpoBean.setMensaje(new String(informeAvpoBean.getDatosInforme().getBytes())); } }
		 */

		return avpoBean;
	}

	public List<AvpoBean> obtenerConsultaLoteHistorico(Long[] listaIds) {
		List<AvpoBean> listaAvpo = new ArrayList<>();

		for (Long idTraficoInformeAvpo : listaIds) {
			listaAvpo.add(obtenerAvpoHistorico(idTraficoInformeAvpo));
		}

		return listaAvpo;
	}

}