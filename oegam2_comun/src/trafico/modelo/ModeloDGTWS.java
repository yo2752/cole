package trafico.modelo;

import java.math.BigDecimal;
import java.util.HashMap;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.oegam2comun.comun.DatosFiscales;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.DatosCTITBean;
import trafico.beans.DatosCardMatwBean;
import trafico.beans.daos.BeanPQDatosCTIT;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.pq_dgt_ws.BeanPQDATOS_CARDMATW;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.SinDatosWSExcepcion;

public class ModeloDGTWS extends ModeloBasePQ {

	private static final String DATOS_CARD_MATW_RECUPERADOS_CORRECTAMENTE = "Datos CardMatw recuperados correctamente";
	private static final String ERROR_AL_RECUPERAR_DETALLE_CARD_MATW = "Error al recuperar detalle cardMatw";
	private static final String P_EXTERNAL_SYSTEM_FISCAL_ID = "P_EXTERNAL_SYSTEM_FISCAL_ID";
	private static final String P_AGENT_FISCAL_ID = "P_AGENT_FISCAL_ID";
	private static final String P_FORM_06_KEY = "P_FORM_06_KEY";
	private static final String P_FORM_05_KEY = "P_FORM_05_KEY";
	private static final String P_HAS_FORM_06 = "P_HAS_FORM_06";
	private static final String P_HAS_FORM_05 = "P_HAS_FORM_05";
	private static final String P_HAS_FORM_576 = "P_HAS_FORM_576";
	private static final String P_VEHICLE_SERIAL = "P_VEHICLE_SERIAL";
	private static final String P_SERIAL_CARD_ITV = "P_SERIAL_CARD_ITV";
	private static final String P_NEW_ITV_CARD = "P_NEW_ITV_CARD";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloDGTWS.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	ServicioPersistenciaTramiteTrafico servicioPersistenciaTramiteTrafico;

	@Autowired
	DatosFiscales datosFiscales;

	public ModeloDGTWS() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden) {
		return null;
	}

	public HashMap<String, Object> datosCardMatw(BeanPQDATOS_CARDMATW beanPQDatosCardMatw) {
		ResultBean resultBean = new ResultBean();
		DatosCardMatwBean datosCardMatwBean = new DatosCardMatwBean();

		RespuestaGenerica respuestaDatosCardMatw = ejecutarProc(beanPQDatosCardMatw, valoresSchemas.getSchema(), ValoresCatalog.PQ_DGT_WS, "DATOS_CARDMATE", BeanPQGeneral.class);

		log.debug(ConstantesPQ.LOG_P_CODE + respuestaDatosCardMatw.getParametro(ConstantesPQ.P_CODE));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + respuestaDatosCardMatw.getParametro(ConstantesPQ.P_INFORMACION));
		log.debug(ConstantesPQ.LOG_P_SQLERRM + respuestaDatosCardMatw.getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaDatosCardMatw.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje(ERROR_AL_RECUPERAR_DETALLE_CARD_MATW);
			resultBean.setError(true);
			resultBean.setMensaje(resultBean.getMensaje() + " ");
		} else {
			resultBean.setMensaje(DATOS_CARD_MATW_RECUPERADOS_CORRECTAMENTE);
			resultBean.setError(false);
			datosCardMatwBean.setSerialNumber((String) respuestaDatosCardMatw.getParametro(P_VEHICLE_SERIAL));
			String hasForm576 = (String) respuestaDatosCardMatw.getParametro(P_HAS_FORM_576);
			if (hasForm576 != null) {
				if (hasForm576.equalsIgnoreCase(Boolean.TRUE.toString())) {
					datosCardMatwBean.setHasForm576(Boolean.TRUE);
				} else if (hasForm576.equalsIgnoreCase(Boolean.FALSE.toString())) {
					datosCardMatwBean.setHasForm576(Boolean.FALSE);
				}
			} else {
				datosCardMatwBean.setHasForm576(Boolean.FALSE);
			}
			String hasForm05 = (String) respuestaDatosCardMatw.getParametro(P_HAS_FORM_05);
			if (hasForm05 != null) {
				if (hasForm05.equalsIgnoreCase(Boolean.TRUE.toString())) {
					datosCardMatwBean.setHasForm05(Boolean.TRUE);
				} else if (hasForm05.equalsIgnoreCase(Boolean.FALSE.toString())) {
					datosCardMatwBean.setHasForm05(Boolean.FALSE);
				}
			} else {
				datosCardMatwBean.setHasForm05(Boolean.FALSE);
			}
			String hasForm06 = (String) respuestaDatosCardMatw.getParametro(P_HAS_FORM_06);
			if (hasForm06 != null) {
				if (hasForm06.equalsIgnoreCase(Boolean.TRUE.toString())) {
					datosCardMatwBean.setHasForm06(Boolean.TRUE);
				} else if (hasForm06.equalsIgnoreCase(Boolean.FALSE.toString())) {
					datosCardMatwBean.setHasForm06(Boolean.FALSE);
				}
			} else {
				datosCardMatwBean.setHasForm06(Boolean.FALSE);
			}
			datosCardMatwBean.setForm05Key((String) respuestaDatosCardMatw.getParametro(P_FORM_05_KEY));
			datosCardMatwBean.setForm06ExcemptionType((String) respuestaDatosCardMatw.getParametro(P_FORM_06_KEY));
			datosCardMatwBean.setForm06ExcemptionValue(null);

			datosCardMatwBean.setAgentFiscalId((String) respuestaDatosCardMatw.getParametro(P_AGENT_FISCAL_ID));
			datosCardMatwBean.setExternalSystemFiscalId((String) respuestaDatosCardMatw.getParametro(P_EXTERNAL_SYSTEM_FISCAL_ID));

			if (respuestaDatosCardMatw.getParametro(P_SERIAL_CARD_ITV) != null) {
				datosCardMatwBean.setSerialCardITV((String) respuestaDatosCardMatw.getParametro(P_SERIAL_CARD_ITV));
			}

			if (respuestaDatosCardMatw.getParametro(P_NEW_ITV_CARD) != null) {
				datosCardMatwBean.setItvCardNew((BigDecimal) respuestaDatosCardMatw.getParametro(P_NEW_ITV_CARD));
			}
		}

		HashMap<String, Object> resultadoCardMatw = new HashMap<String, Object>();

		resultadoCardMatw.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoCardMatw.put(ConstantesPQ.BEANPANTALLA, datosCardMatwBean);
		resultadoCardMatw.put(ConstantesPQ.P_CODE, respuestaDatosCardMatw.getParametro(ConstantesPQ.P_CODE));

		return resultadoCardMatw;
	}

	public DatosCardMatwBean datosCardMatw(BigDecimal idTramite) throws SinDatosWSExcepcion {
		BeanPQDATOS_CARDMATW beanPQDatosCardMatw = new BeanPQDATOS_CARDMATW();
		beanPQDatosCardMatw.setP_NUM_EXPEDIENTE(idTramite);
		HashMap<String, Object> respuestaCardMatw = datosCardMatw(beanPQDatosCardMatw);
		ResultBean resultBeanDetalleCardMatw = (ResultBean) respuestaCardMatw.get(ConstantesPQ.RESULTBEAN);
		if (resultBeanDetalleCardMatw.getError())
			throw new SinDatosWSExcepcion(resultBeanDetalleCardMatw.getMensaje());
		else
			return (DatosCardMatwBean) respuestaCardMatw.get(ConstantesPQ.BEANPANTALLA);
	}

	/**
	 * @author cesar.sanchez
	 * @param BeanPQDatosCTIT
	 * @return HashMap <String, Object>
	 */
	/*
	 * Metodo para obtener los datos necesarios para llamar al webservice CTIT
	 */
	public HashMap<String, Object> datosCTIT(BeanPQDatosCTIT beanPQDatosCTIT) {

		ResultBean resultBean = new ResultBean();
		DatosCTITBean datosCTITBean = new DatosCTITBean();

		RespuestaGenerica respuestaDatosCTIT = ejecutarProc(beanPQDatosCTIT, valoresSchemas.getSchema(), ValoresCatalog.PQ_DGT_WS, "DATOS_CTIT", BeanPQGeneral.class);

		log.debug(ConstantesPQ.LOG_P_CODE + respuestaDatosCTIT.getParametro(ConstantesPQ.P_CODE));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + respuestaDatosCTIT.getParametro(ConstantesPQ.P_INFORMACION));
		log.debug(ConstantesPQ.LOG_P_SQLERRM + respuestaDatosCTIT.getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaDatosCTIT.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje("ERROR_AL_RECUPERAR_DETALLE_CTIT");
			resultBean.setError(true);
			resultBean.setMensaje(resultBean.getMensaje() + " ");
		} else {
			resultBean.setMensaje("DATOS_CTIT_RECUPERADOS_CORRECTAMENTE");
			resultBean.setError(false);
		}

		datosCTITBean.setCustomDossierNumber(beanPQDatosCTIT.getP_NUM_EXPEDIENTE().toString());
		datosCTITBean.setAgencyFiscalId((String) respuestaDatosCTIT.getParametro("P_AGENCYFISCALLD"));
		datosCTITBean.setExternalSystemFiscalID((String) respuestaDatosCTIT.getParametro("P_EXTERNALSYSTEMFISCALL"));
		datosCTITBean.setPasos((String) respuestaDatosCTIT.getParametro("P_NPASOS"));
		datosCTITBean.setNumColegiado((String) respuestaDatosCTIT.getParametro("P_NUM_COLEGIADO"));
		datosCTITBean.setConsentimiento((String) respuestaDatosCTIT.getParametro("P_CONSENTIMIENTO") != null ? (String) respuestaDatosCTIT.getParametro("P_CONSENTIMIENTO") : ConstantesProcesos.NO);

		HashMap<String, Object> resultadoCTIT = new HashMap<String, Object>();
		resultadoCTIT.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoCTIT.put(ConstantesPQ.BEANPANTALLA, datosCTITBean);
		resultadoCTIT.put(ConstantesPQ.P_CODE, respuestaDatosCTIT.getParametro(ConstantesPQ.P_CODE));

		return resultadoCTIT;
	}

	public DatosCTITBean datosCTIT(BigDecimal numExpediente) throws SinDatosWSExcepcion {
		BeanPQDatosCTIT beanPQDatosCTIT = new BeanPQDatosCTIT();
		beanPQDatosCTIT.setP_NUM_EXPEDIENTE(numExpediente);
		DatosCTITBean datosCTITBean = new DatosCTITBean();

		RespuestaGenerica respuestaDatosCTIT = ejecutarProc(beanPQDatosCTIT, valoresSchemas.getSchema(), ValoresCatalog.PQ_DGT_WS, "DATOS_CTIT", BeanPQGeneral.class);

		log.info(ConstantesPQ.LOG_P_CODE + respuestaDatosCTIT.getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaDatosCTIT.getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaDatosCTIT.getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaDatosCTIT.getParametro(ConstantesPQ.P_CODE)))) {
			throw new SinDatosWSExcepcion((String) respuestaDatosCTIT.getParametro(ConstantesPQ.P_SQLERRM));
		} else {
			datosCTITBean.setCustomDossierNumber(beanPQDatosCTIT.getP_NUM_EXPEDIENTE().toString());
			datosCTITBean.setAgencyFiscalId((String) respuestaDatosCTIT.getParametro("P_AGENCYFISCALLD"));
			datosCTITBean.setExternalSystemFiscalID((String) respuestaDatosCTIT.getParametro("P_EXTERNALSYSTEMFISCALL"));
			datosCTITBean.setPasos((String) respuestaDatosCTIT.getParametro("P_NPASOS"));
			datosCTITBean.setNumColegiado((String) respuestaDatosCTIT.getParametro("P_NUM_COLEGIADO"));
			datosCTITBean
					.setConsentimiento((String) respuestaDatosCTIT.getParametro("P_CONSENTIMIENTO") != null ? (String) respuestaDatosCTIT.getParametro("P_CONSENTIMIENTO") : ConstantesProcesos.NO);

			datosCTITBean.setPlazas((BigDecimal) respuestaDatosCTIT.getParametro("P_PLAZAS"));
			datosCTITBean.setTara((String) respuestaDatosCTIT.getParametro("P_TARA"));
			datosCTITBean.setPesoMma((String) respuestaDatosCTIT.getParametro("P_MMA"));
			datosCTITBean.setIdServicio((String) respuestaDatosCTIT.getParametro("P_ID_SERVICIO"));

			try {
				TramiteTrafTranVO tramite = servicioPersistenciaTramiteTrafico.getTramiteTransmision(numExpediente, Boolean.FALSE);

				if (tramite.getTipoTransferencia() != null && !tramite.getTipoTransferencia().equals(TipoTransferencia.tipo2.getValorEnum())) {
					if (respuestaDatosCTIT.getParametro("P_SELLER_INE_CODE") != null && !respuestaDatosCTIT.getParametro("P_SELLER_INE_CODE").equals("")
							&& !respuestaDatosCTIT.getParametro("P_SELLER_INE_CODE").equals("-1")) {
						datosCTITBean.setSellerINECode(respuestaDatosCTIT.getParametro("P_SELLER_INE_CODE").toString());
					} else {
						datosCTITBean.setSellerINECode("ND");
					}
				} else {
					// Si el tipo de transferencia es: "Cambio de titularidad completo (2)" siempre se pone ND
					datosCTITBean.setSellerINECode("ND");
				}

			} catch (Exception e) {
				log.error("Proceso FullCTIT: " + e.getMessage());
				return null;
			}

			Object firstMatriculation = respuestaDatosCTIT.getParametro("P_FIRST_MATRICULATION_INE_CODE");
			// provinciaNoValida = -1;
			if (firstMatriculation != null && !firstMatriculation.toString().isEmpty() && !"-1".equals(firstMatriculation.toString())) {
				int codigoProvinciaPrimeraMatricula = Integer.parseInt(firstMatriculation.toString());
				if (codigoProvinciaPrimeraMatricula < 10 && codigoProvinciaPrimeraMatricula > 0) {
					datosCTITBean.setFirstMatriculationINECode("0" + firstMatriculation.toString());
				} else {
					datosCTITBean.setFirstMatriculationINECode(firstMatriculation.toString());
				}
			} else {
				datosCTITBean.setFirstMatriculationINECode("ND");
			}
		}
		return datosCTITBean;
	}

	public BeanDatosFiscales datosMateEitv(BigDecimal idTramite) throws SinDatosWSExcepcion {
		return datosFiscales.obtenerDatosFiscales(idTramite);
	}
}