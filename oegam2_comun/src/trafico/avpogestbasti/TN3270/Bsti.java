package trafico.avpogestbasti.TN3270;

import java.math.BigDecimal;
import java.util.HashMap;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.avpobastigest.BstiBean;
import trafico.beans.avpobastigest.ParamsTraficoTN3270Bean;
import trafico.beans.avpobastigest.ResultTraficoTN3270Bean;
import trafico.utiles.constantes.ConstantesTN3270;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.AplicacionTraficoTN3270;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class Bsti {
	// Log
	private static final ILoggerOegam log = LoggerOegam.getLogger(Bsti.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public Bsti() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public BstiBean obtenerMatricula(String numBastidor, BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, Long idVehiculo, String origen) {
		log.trace("Entra en obtenerMatricula");

		BstiBean bean = new BstiBean();

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

		HashMap<String, String> params = new HashMap<>();
		params.put(ConstantesTN3270.BASTIDOR, numBastidor);
		ParamsTraficoTN3270Bean paramsBean = new ParamsTraficoTN3270Bean(AplicacionTraficoTN3270.BSTI, params);

		TraficoTN3270BLImpl traficoTN3270BL = new TraficoTN3270BLImpl();

		ResultTraficoTN3270Bean resultTraficoBean = traficoTN3270BL.solicitudTrafico(paramsBean, numExpediente, idUsuario, numColegiado, idVehiculo, origen);

		if (resultTraficoBean == null) {
			log.error("Error en la invocación");
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
		} else if (ConstantesTN3270.OK.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.info("Respuesta OK. Matricula: "+resultTraficoBean.getListaValores().get(ConstantesTN3270.MATRICULA));
			bean.setMatricula(resultTraficoBean.getListaValores().get(ConstantesTN3270.MATRICULA));
			bean.setError(Boolean.FALSE);
		} else if (ConstantesTN3270.NO_EXISTEN_DATOS.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.info("No existe ningún vehículo matriculado para el bastidor indicado");
			bean.setMensaje("No existe ningún vehículo matriculado para el bastidor indicado.");
			bean.setError(Boolean.TRUE);
		} else if (ConstantesTN3270.ERROR_PARAMETROS.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.error("Error en los parámetros: " + resultTraficoBean.getListaValores().get(ConstantesTN3270.MENSAJE_ERROR));
			bean.setMensaje("Error en los parámetros.");
			bean.setError(Boolean.TRUE);
		} else if (ConstantesTN3270.ERROR_CONEXION.equals(resultTraficoBean.getCodigoRespuesta())) {
			log.error("Error en la conexión con el servicio de tráfico");
			bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setError(Boolean.TRUE);
		}
		/*	else if(ERROR_TIMEOUT==resultTraficoBean.getCodigoRespuesta()){
				log.error("Timeout en la conexión con el servicio de tráfico");
				bean.setMensaje("Timeout en la conexión con el servicio de tráfico");
				bean.setError(Boolean.TRUE);
			} else if (ERROR_TECNICO == resultTraficoBean.getCodigoRespuesta()) {
				log.error("Error en la invocación: "+resultTraficoBean.getListaValores().get(MENSAJE_ERROR));
				if (resultTraficoBean.getListaValores().get(MENSAJE_ERROR) != null) {
					bean.setMensaje(resultTraficoBean.getListaValores().get(MENSAJE_ERROR));
				} else {
					bean.setMensaje(Literales.getLiteral(UserMessages.ERROR_DGT_INVOCACION));
				}
				bean.setError(Boolean.TRUE);
			}*/
		else {
			log.error("Error en la respuesta: " + resultTraficoBean.getCodigoRespuesta());
			//bean.setMensaje("Se ha producido un error en la invocación al servicio de la D.G.T.");
			bean.setMensaje(resultTraficoBean.getCodigoRespuesta());
			bean.setError(Boolean.TRUE);
		}

		log.trace("Sale de obtenerMatricula");
		return bean;
	}
}