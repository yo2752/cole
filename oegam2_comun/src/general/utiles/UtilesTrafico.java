package general.utiles;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.pq_tasas.BeanPQDETALLE;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesTrafico {

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger("UtilesTrafico");

	// Constantes para analizar una matrícula
	/* Tipos de matrícula */
	public static final String TIPO_MATRICULA_ORDINARIA = "ordinaria";
	public static final String TIPO_MATRICULA_TRACTOR = "tractor";
	public static final String TIPO_MATRICULA_CICLOMOTOR = "ciclomotor";
	/* Enteros */
	public static final Integer CERO =		0;
	public static final Integer UNO =		1;
	public static final Integer DOS =		2;
	public static final Integer TRES =		3;
	public static final Integer CUATRO =	4;
	public static final Integer CINCO =		5;
	public static final Integer SEIS =		6;
	public static final Integer SIETE =		7;
	public static final Integer OCHO =		8;
	public static final Integer NUEVE =		9;
	public static final Integer DIEZ =		10;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	public UtilesTrafico() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public Fecha calcularFechaITV(Fecha fechaPresentacion, VehiculoBean vehiculo) {

		try {
			Fecha wFechaItv = null;
			int pesoMma = Integer.parseInt(vehiculo.getPesoMma());

			if (vehiculo.getFechaInspeccion().getFecha() != null) {
				wFechaItv = vehiculo.getFechaInspeccion();
			} else if (vehiculo.getFechaMatriculacion().getFecha() != null) {
				wFechaItv = vehiculo.getFechaMatriculacion();
			} else if (vehiculo.getFechaPrimMatri().getFecha() != null) {
				wFechaItv = vehiculo.getFechaPrimMatri();
			} else if (fechaPresentacion.getFecha() != null) {
				wFechaItv = fechaPresentacion;
			} else {
				wFechaItv = utilesFecha.getFechaActual();
			}
			if ((vehiculo.getVehiUsado() == null) || ("NO".equals(vehiculo.getVehiUsado()))
					|| ("false".equals(vehiculo.getVehiUsado()))) {

				if ((("0".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo().substring(0, 1)))
						|| ("1".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo().substring(0, 1))))
						&& !"18".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo())) {

					if (pesoMma > 3500) {
						return utilesFecha.sumaAnio(wFechaItv, 1);
					} else {
						return utilesFecha.sumaAnio(wFechaItv, 2);
					}
				} else if ("22".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo())) {

					return utilesFecha.sumaAnio(wFechaItv, 1);
				} else if (("20".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("21".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("23".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("24".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))) {

					if (pesoMma > 3500) {
						return utilesFecha.sumaAnio(wFechaItv, 1);
					} else {
						return utilesFecha.sumaAnio(wFechaItv, 2);
					}
				} else if ("3".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo().substring(0, 1))) {
					return utilesFecha.sumaAnio(wFechaItv, 1);
				} else if (("25".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("92".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("91".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("7A".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("40".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("5".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo().substring(0, 1)))) {
					if (("A00".equals(vehiculo.getServicioTraficoBean().getIdServicio()))
							|| ("A04".equals(vehiculo.getServicioTraficoBean().getIdServicio()))) {
						return utilesFecha.sumaAnio(wFechaItv, 1);
					} else if (("A01".equals(vehiculo.getServicioTraficoBean().getIdServicio()))
							|| ("A02".equals(vehiculo.getServicioTraficoBean().getIdServicio()))
							|| ("A03".equals(vehiculo.getServicioTraficoBean().getIdServicio()))
							|| ("B03".equals(vehiculo.getServicioTraficoBean().getIdServicio()))) {
						return utilesFecha.sumaAnio(wFechaItv, 2);
					} else {
						return utilesFecha.sumaAnio(wFechaItv, 4);
					}
				} else if ("90".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo())) {
					return utilesFecha.sumaAnio(wFechaItv, 3);
				} else if (("80".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("81".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
						|| ("82".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))) {
					if (("B06".equals(vehiculo.getServicioTraficoBean().getIdServicio()))
							|| ("A01".equals(vehiculo.getServicioTraficoBean().getIdServicio()))) {
						return utilesFecha.sumaAnio(wFechaItv, 8);
					} else {
						return utilesFecha.sumaAnio(wFechaItv, 1);
					}
				}
			}
			return null;
		} catch (Exception e) {
			log.error("Error al calcular la fechaITV. Detalle:" + e.getMessage());
			return null;
		}
	}

	/**
	 * Comprueba si la solicitud de información a DGT (BASTI, GEST Y AVPO) está
	 * deshabilitada desde el properties por estar el servicio caído
	 * 
	 * @return resultBean con true y mensaje si está deshabilitado.
	 */
	public ResultBean comprobarDisponibilidadSolicitudDGT() {
		ResultBean bean = new ResultBean();
		String desactivar = gestorPropiedades.valorPropertie(ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVAR);
		if (desactivar != null && desactivar.equalsIgnoreCase("si")) {
			String mensaje = ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVADA_MENSAJE;
			if (mensaje != null && !mensaje.equals("")) {
				bean.setMensaje(mensaje);
			} else {
				bean.setMensaje(ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_MENSAJE_X_DEFECTO);
			}
			bean.setError(true);
			return bean;
		}
		return bean;
	}

	/**
	 * Asigna a una instancia de Tasa los valores de atributos de BeanPQDETALLE del
	 * PQ_TASAS
	 * 
	 * @param beanDetalle
	 * @return tasa
	 */
	public static Tasa convertir(BeanPQDETALLE beanDetalle) throws Exception {
		Tasa tasa = new Tasa();
		tasa.setCodigoTasa(beanDetalle.getP_CODIGO_TASA());
		tasa.setFechaAlta(beanDetalle.getP_FECHA_ALTA());
		tasa.setFechaAsignacion(beanDetalle.getP_FECHA_ASIGNACION());
		tasa.setFechaFinVigencia(beanDetalle.getP_FECHA_FIN_VIGENCIA());
		if (beanDetalle.getP_ID_CONTRATO() != null) {
			tasa.setIdContrato(beanDetalle.getP_ID_CONTRATO().intValue());
		}
		if (beanDetalle.getP_ID_USUARIO() != null) {
			tasa.setIdUsuario(beanDetalle.getP_ID_USUARIO().intValue());
		}
		tasa.setPrecio(beanDetalle.getP_PRECIO());
		tasa.setTipoTasa(beanDetalle.getP_TIPO_TASA());
		return tasa;
	}

	/**
	 * Se analiza la matrícula pasada como parámetro y devuelve a qué tipo de
	 * vehiculo pertenece: ordinaria, tractor o ciclomotor La implementacion de este
	 * metodo se ha recuperado de PlacasMatriculacionServiceImpl, pero como el
	 * proyecto al que pertenece no tiene visibilidad desde oegam2_comun se ha
	 * creado uno nuevo en UtilesTrafico con su contenido
	 * 
	 * @param matricula
	 * @return tipoMatricula
	 */
	public static String analizarMatriculaVehiculo(String matricula) {
		UtilesConversiones utiles = ContextoSpring.getInstance().getBean(UtilesConversiones.class);
		String trozo1;
		String trozo2;
		String trozo3;
		String tipoMatricula = TIPO_MATRICULA_ORDINARIA;

		// Análisis matrícula ordinaria
		if (matricula.length() == SIETE.intValue()) {
			trozo1 = matricula.substring(0, 4);
			trozo2 = matricula.substring(4, 7);

			if (utiles.comprobarNumero(trozo1) && !utiles.comprobarNumero(trozo2)) {
				return tipoMatricula;
			}
		}

		if (matricula.length() == OCHO.intValue()) {
			// Análisis tipo matrícula antigua

			// Análisis matrícula tractor y agrícolas
			trozo1 = matricula.substring(0, 1);
			trozo2 = matricula.substring(1, 5);
			trozo3 = matricula.substring(5, 8);

			if (!utiles.comprobarNumero(trozo1) && trozo1.equals("E") && utiles.comprobarNumero(trozo2)
					&& !utiles.comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_TRACTOR;
				return tipoMatricula;
			}

			// Análisis matrícula ciclomotores
			trozo1 = matricula.substring(0, 2);
			trozo2 = matricula.substring(2, 5);
			trozo3 = matricula.substring(5, 8);

			if (!utiles.comprobarNumero(trozo1) && trozo1.substring(0, 1).equals("C") && utiles.comprobarNumero(trozo2)
					&& !utiles.comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_CICLOMOTOR;
				return tipoMatricula;
			}
		}

		return tipoMatricula;
	}

}