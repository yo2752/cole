package trafico.avpogestbasti.TN3270;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.avpobastigest.CargaGestBean;
import trafico.beans.avpobastigest.EmbargoGestBean;
import trafico.beans.avpobastigest.GestBean;
import trafico.beans.avpobastigest.ListaValoresTraficoTN3270;
import trafico.beans.avpobastigest.ParamsTraficoTN3270Bean;
import trafico.beans.avpobastigest.ResultTraficoTN3270Bean;
import trafico.beans.avpobastigest.SubListaValoresTN3270Bean;
import trafico.utiles.constantes.ConstantesTN3270;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.AplicacionTraficoTN3270;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class Gest {

	// Log
	private static final ILoggerOegam log = LoggerOegam.getLogger(Gest.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public Gest() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public GestBean obtenerCargas(String matricula, String nifTitular, BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen) {

		log.trace("Entra en obtenerCargas");
		GestBean bean = new GestBean();

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

		HashMap<String, String> params = new HashMap<>();
		params.put(ConstantesTN3270.MATRICULA, matricula);
		params.put(ConstantesTN3270.NIF_TITULAR, nifTitular);

		ParamsTraficoTN3270Bean paramsBean = new ParamsTraficoTN3270Bean(AplicacionTraficoTN3270.GEST, params);

		TraficoTN3270BLImpl traficoTN3270BL = new TraficoTN3270BLImpl();

		ResultTraficoTN3270Bean resultTraficoBean = traficoTN3270BL.solicitudTrafico(paramsBean, numExpediente, idUsuario, numColegiado, idVehiculo, origen);

		if (resultTraficoBean == null) {
			log.error("Error en la invocación");
			bean.setMensaje("Error en la invocación");
			bean.setError(Boolean.TRUE);
		} else if (ConstantesTN3270.OK.equals(resultTraficoBean.getCodigoRespuesta())) {
			List<CargaGestBean> listaCargas = new ArrayList<>();
			List<EmbargoGestBean> listaEmbargos = new ArrayList<>();

			List<SubListaValoresTN3270Bean> sublistas = resultTraficoBean.getSublistasValores();
			if (sublistas != null) {
				for (SubListaValoresTN3270Bean sublista : sublistas) {
					if (sublista.getIdTipoLista().equals(ListaValoresTraficoTN3270.CARGA)) {
						HashMap<String, String> listaValores = sublista.getListaValores();
						CargaGestBean cargaGestBean = new CargaGestBean();
						cargaGestBean.setTipo(listaValores.get(ConstantesTN3270.CARGA_TIPO));
						String fecha = listaValores.get(ConstantesTN3270.CARGA_FECHA);
						cargaGestBean.setFecha(fecha);
						cargaGestBean.setNumRegistro(listaValores.get(ConstantesTN3270.CARGA_NUM_REGISTRO));
						cargaGestBean.setFinancieraYDomicilio(listaValores.get(ConstantesTN3270.CARGA_FINANCIERA_DOMICILIO));
						listaCargas.add(cargaGestBean);
					} else if (sublista.getIdTipoLista().equals(ListaValoresTraficoTN3270.EMBARGO)) {
						HashMap<String, String> listaValores = sublista.getListaValores();
						EmbargoGestBean embargoGestBean = new EmbargoGestBean();
						embargoGestBean.setConcepto(listaValores.get(ConstantesTN3270.EMBARGO_CONCEPTO));
						embargoGestBean.setExpediente(listaValores.get(ConstantesTN3270.EMBARGO_EXPEDIENTE));
						String fecha = listaValores.get(ConstantesTN3270.EMBARGO_FECHA);
						embargoGestBean.setFecha(fecha);
						fecha = listaValores.get(ConstantesTN3270.EMBARGO_FMATER);
						embargoGestBean.setFmateri(fecha);
						embargoGestBean.setAutoridad(listaValores.get(ConstantesTN3270.EMBARGO_AUTORIDAD));
						listaEmbargos.add(embargoGestBean);
					}
				}
				bean.setCargas(listaCargas);
				bean.setEmbargos(listaEmbargos);
				bean.setMatricula(matricula);
			} else {
				bean.setMensaje("El vehículo con matrícula " + matricula + " está exento de cargas.");
			}

			bean.setError(Boolean.FALSE);
		} else if (ConstantesTN3270.ERROR_PARAMETROS.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.error("Error en los parámetros: " + resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR));
			if (resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR) != null) {
				bean.setMensaje(resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR));
			} else {
				bean.setMensaje("Error en los parámetros.");
			}
			bean.setError(Boolean.TRUE);
		} else if (ConstantesTN3270.ERROR_CONEXION.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.error("Error en la conexión con el servicio de tráfico");
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
		}
		/*
		 * else if(ERROR_TIMEOUT==resultTraficoBean.getCodigoRespuesta()){ log.error("Timeout en la conexión con el servicio de tráfico"); bean.setMensaje(Literales.getLiteral(UserMessages.ERROR_DGT_INVOCACION)); bean.setError(Boolean.TRUE); } else
		 * if(ERROR_TECNICO==resultTraficoBean.getCodigoRespuesta()){ log.error("Error en la invocación: "+resultTraficoBean.getListaValores().get(MENSAJE_ERROR)); if(resultTraficoBean.getListaValores().get(MENSAJE_ERROR)!=null){
		 * bean.setMensaje(resultTraficoBean.getListaValores().get(MENSAJE_ERROR)); }else{ bean.setMensaje(Literales.getLiteral(UserMessages.ERROR_DGT_INVOCACION)); } bean.setError(Boolean.TRUE); }
		 */
		else {
			log.error("Error en la respuesta: " + resultTraficoBean.getCodigoRespuesta());
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
		}
		log.trace("Sale de obtenerCargas");
		return bean;
	}

}