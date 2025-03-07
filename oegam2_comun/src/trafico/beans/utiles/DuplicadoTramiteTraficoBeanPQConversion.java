package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.utiles.enumerados.DecisionTrafico;
import general.beans.RespuestaGenerica;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.daos.BeanPQTramiteDuplicado;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarInterviniente;
import trafico.beans.daos.BeanPQVehiculosGuardar;
import trafico.beans.daos.pq_tramite_trafico.BeanPQGUARDAR_DUPLICADO;
import trafico.beans.jaxb.duplicados.DUPLICADO;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class DuplicadoTramiteTraficoBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(DuplicadoTramiteTraficoBeanPQConversion.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public BeanPQTramiteDuplicado convertirTramiteDuplicadoToPQ(TramiteTraficoDuplicadoBean beanF) {
		BeanPQTramiteDuplicado beanPQ = new BeanPQTramiteDuplicado();

		beanPQ.setBeanGuardarVehiculo(vehiculoBeanPQConversion.convertirBeanToPQ(beanF.getTramiteTrafico().getVehiculo(),beanF.getTramiteTrafico(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.setBeanGuardarDuplicado(beanTramiteDuplicadoConvertirBeanPQ(beanF));
		beanF.getTitular().setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		beanF.getRepresentante().setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
		beanF.getCotitular().setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
		beanPQ.setBeanGuardarTitular(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getTitular(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.setBeanGuardarCotitular(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getCotitular(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.setBeanGuardarRepresentante(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentante(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		return beanPQ;
	}

	public BeanPQGUARDAR_DUPLICADO beanTramiteDuplicadoConvertirBeanPQ (TramiteTraficoDuplicadoBean tramite) {
		// Controlo que el interviniente no venga a null; si fuera null, tendría nullPointer Exception al hacer vehiculo.getX().
		if (tramite == null) return null;

		BeanPQGUARDAR_DUPLICADO b = new BeanPQGUARDAR_DUPLICADO();

		// Valores propios del trámite general
		if (tramite.getTramiteTrafico() != null) {
			b.setP_ID_USUARIO(null != tramite.getTramiteTrafico().getIdUsuario() ? tramite.getTramiteTrafico().getIdUsuario() : utilesColegiado.getIdUsuarioSessionBigDecimal());
			b.setP_NUM_EXPEDIENTE(null == tramite.getTramiteTrafico().getNumExpediente() ? null : tramite.getTramiteTrafico().getNumExpediente());
			b.setP_ID_CONTRATO(null != tramite.getTramiteTrafico().getIdContrato() ? tramite.getTramiteTrafico().getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal());
			b.setP_NUM_COLEGIADO(null == tramite.getTramiteTrafico().getNumColegiado() || tramite.getTramiteTrafico().getNumColegiado().equals("") ? utilesColegiado.getNumColegiadoSession():tramite.getTramiteTrafico().getNumColegiado());
			b.setP_ID_VEHICULO(null == tramite.getTramiteTrafico().getVehiculo() ? null : tramite.getTramiteTrafico().getVehiculo().getIdVehiculo());

			if (tramite.getTramiteTrafico().getEstado() != null) {
				b.setP_ESTADO(new BigDecimal(tramite.getTramiteTrafico().getEstado().getValorEnum()));
			}

			b.setP_REF_PROPIA(tramite.getTramiteTrafico().getReferenciaPropia());
			b.setP_ANOTACIONES(tramite.getTramiteTrafico().getAnotaciones());
		} else {
			b.setP_ID_USUARIO(null);
			b.setP_NUM_EXPEDIENTE(null);
			b.setP_ID_CONTRATO(null);
			b.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
			b.setP_ID_VEHICULO(null);
			b.setP_CODIGO_TASA(null);
			b.setP_ESTADO(null);
			b.setP_REF_PROPIA(null);
		}
		b.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		if (tramite.getTramiteTrafico().getTasa() != null) {
			b.setP_CODIGO_TASA("-1".equals(tramite.getTramiteTrafico().getTasa().getCodigoTasa()) ? null : tramite.getTramiteTrafico().getTasa().getCodigoTasa());
		} else {
			b.setP_CODIGO_TASA(null);
		}

		if (tramite.getTasaDuplicado() != null) {
			b.setP_TASA_IMPORTACION("-1".equals(tramite.getTasaDuplicado()) ? null : tramite.getTasaDuplicado());
		} else {
			b.setP_TASA_IMPORTACION(null);
		}

		try {
			b.setP_FECHA_ALTA(null);
			if (tramite.getTramiteTrafico() != null) {
				b.setP_FECHA_PRESENTACION(tramite.getTramiteTrafico().getFechaPresentacion() != null
						? tramite.getTramiteTrafico().getFechaPresentacion().getTimestamp()
						: null);
			}
			b.setP_FECHA_ULT_MODIF(null);
			b.setP_FECHA_IMPRESION(null);
		} catch (ParseException e) {
			log.error(e);
		}

		if (tramite.getTramiteTrafico().getJefaturaTrafico() != null) {
			b.setP_JEFATURA_PROVINCIAL("-1".equals(tramite.getTramiteTrafico().getJefaturaTrafico().getJefaturaProvincial()) ? null : tramite.getTramiteTrafico().getJefaturaTrafico().getJefaturaProvincial());
		} else {
			b.setP_JEFATURA_PROVINCIAL(null);
		}

		// Valores propios del duplicado
		b.setP_MOTIVO_DUPLICADO(tramite.getMotivoDuplicado() != null ? tramite.getMotivoDuplicado().getValorEnum() : null);
		b.setP_IMPR_PERMISO_CIRCU(null!=tramite.getImprPermisoCircu() ? (true == tramite.getImprPermisoCircu() ? "SI" : "NO") : null);
		b.setP_IMPORTACION(null != tramite.getImportacion() ? (true == tramite.getImportacion() ? "SI" : "NO") : null);
		b.setP_INFORMACION(null);

		//DRC@15-02-2013 Incidencia Mantis: 2708
		b.setP_ID_TIPO_CREACION(tramite.getIdTipoCreacion());
		return b;
	}

	public TramiteTraficoDuplicadoBean convertirPQToBean(RespuestaGenerica resultadoPQ) {
		TramiteTraficoDuplicadoBean tramite = new TramiteTraficoDuplicadoBean(true);

		// Campos genéricos del Trámite Tráfico
		tramite.getTramiteTrafico().setNumExpediente((BigDecimal)resultadoPQ.getParametro("P_NUM_EXPEDIENTE"));
		tramite.getTramiteTrafico().setIdContrato((BigDecimal)resultadoPQ.getParametro("P_ID_CONTRATO"));
		tramite.getTramiteTrafico().setNumColegiado((String)resultadoPQ.getParametro("P_NUM_COLEGIADO"));
		tramite.getTramiteTrafico().getVehiculo().setIdVehiculo((BigDecimal)resultadoPQ.getParametro("P_ID_VEHICULO"));
		tramite.getTramiteTrafico().getTasa().setCodigoTasa((String)resultadoPQ.getParametro("P_CODIGO_TASA"));
		tramite.getTramiteTrafico().getTasa().setTipoTasa((String)resultadoPQ.getParametro("P_TIPO_TASA"));
		tramite.getTramiteTrafico().setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());

		if (resultadoPQ.getParametro("P_ESTADO") != null) {
			BigDecimal estado = (BigDecimal)resultadoPQ.getParametro("P_ESTADO");
			tramite.getTramiteTrafico().setEstado(estado.toString());
		}
		tramite.getTramiteTrafico().setReferenciaPropia((String)resultadoPQ.getParametro("P_REF_PROPIA"));

		if (resultadoPQ.getParametro("P_FECHA_ALTA") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ALTA");
			tramite.getTramiteTrafico().setFechaCreacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_PRESENTACION") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_PRESENTACION");
			tramite.getTramiteTrafico().setFechaPresentacion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.getTramiteTrafico().getJefaturaTrafico().setJefaturaProvincial((String)resultadoPQ.getParametro("P_JEFATURA_PROVINCIAL"));
		tramite.getTramiteTrafico().setAnotaciones((String)resultadoPQ.getParametro("P_ANOTACIONES"));

		tramite.getTramiteTrafico().setRespuesta((String)resultadoPQ.getParametro("P_RESPUESTA"));

		// Datos perticulares del trámite de baja
		tramite.setMotivoDuplicado((String)resultadoPQ.getParametro("P_MOTIVO_DUPLICADO"));
		if (resultadoPQ.getParametro("P_IMPR_PERMISO_CIRCU") != null) {
			String duplicadoPermiso = (String)resultadoPQ.getParametro("P_IMPR_PERMISO_CIRCU");
			tramite.setImprPermisoCircu(DecisionTrafico.Si.getValorEnum().equals(duplicadoPermiso));
		}
		if (resultadoPQ.getParametro("P_IMPORTACION")!= null) {
			String importacion = (String)resultadoPQ.getParametro("P_IMPORTACION");
			if (DecisionTrafico.Si.getValorEnum().equals(importacion)) {
				tramite.setImportacion(true);
				tramite.setTasaDuplicado((String)resultadoPQ.getParametro("P_TASA_IMPORTACION"));
			} else {
				tramite.setImportacion(false);
				tramite.setTasaDuplicado(null);
			}
		}
		if (null != tramite.getMotivoDuplicado() && !tramite.getMotivoDuplicado().getValorEnum().equals("-1")) {
			tramite.setTipoDuplicado(tramite.getMotivoDuplicado().getNombreEnum());
		}

		return tramite;
	}

	//TODO ASG
	public BeanPQTramiteDuplicado convertirTramiteDuplicadoToPQ(TramiteTraficoDuplicadoBean beanF, BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {
		BeanPQTramiteDuplicado beanPQ = new BeanPQTramiteDuplicado();

		beanPQ.setBeanGuardarVehiculo(vehiculoBeanPQConversion.convertirBeanToPQ(beanF.getTramiteTrafico().getVehiculo(), beanF.getTramiteTrafico(),
				idUsuario, idContrato, numColegiado));
		beanPQ.setBeanGuardarDuplicado(beanTramiteDuplicadoConvertirBeanPQ(beanF));
		beanF.getTitular().setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		beanF.getRepresentante().setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
		beanF.getCotitular().setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
		beanPQ.setBeanGuardarTitular(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getTitular(),
				idUsuario, idContrato, numColegiado));
		beanPQ.setBeanGuardarCotitular(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getCotitular(),
				idUsuario, idContrato, numColegiado));
		beanPQ.setBeanGuardarRepresentante(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentante(),
				idUsuario, idContrato, numColegiado));
		return beanPQ;
	}

	//TODO ASG
	public static List<BeanPQTramiteDuplicado> convertirFORMATOGAtoPQ(FORMATOOEGAM2DUPLICADO formatoGA, BigDecimal idUsuario, BigDecimal idContrato) {
		List<BeanPQTramiteDuplicado> lista = new ArrayList<>();

		for (DUPLICADO duplicado : formatoGA.getDUPLICADO()) {
			BeanPQVehiculosGuardar vehiculo = new BeanPQVehiculosGuardar();
			BeanPQTramiteTraficoGuardarInterviniente titular = new BeanPQTramiteTraficoGuardarInterviniente();
			BeanPQTramiteTraficoGuardarInterviniente representanteTitular = new BeanPQTramiteTraficoGuardarInterviniente();
			BeanPQTramiteTraficoGuardarInterviniente coTitular = new BeanPQTramiteTraficoGuardarInterviniente();
			BeanPQGUARDAR_DUPLICADO tramite = new BeanPQGUARDAR_DUPLICADO();

			if (duplicado != null) {
				if (null != duplicado.getDATOSVEHICULO())
					vehiculo = convertirVehiculoGAtoPQ(formatoGA, duplicado);

				if (null != duplicado.getDATOSTITULAR())
					titular = convertirIntervinienteGAtoPQ(formatoGA, TipoInterviniente.Titular.getValorEnum(), duplicado, idUsuario, idContrato);

				if (null != duplicado.getDATOSREPRESENTANTETITULAR())
					representanteTitular = convertirIntervinienteGAtoPQ(formatoGA, TipoInterviniente.RepresentanteTitular.getValorEnum(), duplicado, idUsuario, idContrato);

				if (null != duplicado.getDATOSCOTITULAR())
					coTitular = convertirIntervinienteGAtoPQ(formatoGA, TipoInterviniente.CotitularTransmision.getValorEnum(), duplicado, idUsuario, idContrato);

				tramite = convertirTramiteDuplicadoToPQ(formatoGA, duplicado);
			}

			BeanPQTramiteDuplicado beanTramiteDupl = null;

			if (tramite != null) {
				beanTramiteDupl = new BeanPQTramiteDuplicado();
				beanTramiteDupl.setBeanGuardarDuplicado(tramite);
				beanTramiteDupl.setBeanGuardarVehiculo(vehiculo);
				beanTramiteDupl.setBeanGuardarTitular(titular);
				beanTramiteDupl.setBeanGuardarRepresentante(representanteTitular);
				beanTramiteDupl.setBeanGuardarCotitular(coTitular);
				lista.add(beanTramiteDupl);
			}
		}
		return lista;
	}

	private static BeanPQGUARDAR_DUPLICADO convertirTramiteDuplicadoToPQ(FORMATOOEGAM2DUPLICADO formatoGA, DUPLICADO duplicado) {
		BeanPQGUARDAR_DUPLICADO tramite = new BeanPQGUARDAR_DUPLICADO();

		tramite.setP_NUM_COLEGIADO(formatoGA.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());

		tramite.setP_MOTIVO_DUPLICADO(duplicado.getDATOSDUPLICADO().getMOTIVODUPLICADO());
		tramite.setP_REF_PROPIA(duplicado.getDATOSDUPLICADO().getREFERENCIAPROPIA());
		tramite.setP_IMPR_PERMISO_CIRCU(booleanToOegamString(duplicado.getDATOSDUPLICADO().isINDIMPRESIONPERMISOCIRCULACION()));
		tramite.setP_IMPORTACION(booleanToOegamString(duplicado.getDATOSDUPLICADO().isINDVEHICULOIMPORTACION()));
		tramite.setP_TASA_IMPORTACION(duplicado.getDATOSDUPLICADO().getCODIGOTASAVEHICULOIMPORTACION());
		tramite.setP_ANOTACIONES(duplicado.getDATOSDUPLICADO().getOBSERVACIONESDGT());

		String codigoTasa = duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAPERMISO() != null
				? duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAPERMISO()
				: duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAFICHA();

		tramite.setP_CODIGO_TASA(codigoTasa);
		tramite.setP_JEFATURA_PROVINCIAL(duplicado.getDATOSPAGOPRESENTACION().getJEFATURAPROVINCIAL());
		tramite.setP_FECHA_PRESENTACION(DDMMAAAAToTimestamp(duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION()));

		return tramite;
	}

	/**
	 * Devuelve un beanPQ de Vehículo a partir de la información de un trámite ga (importado XML-MATE)
	 * @param ga
	 * @return
	 */
	private static BeanPQVehiculosGuardar convertirVehiculoGAtoPQ(FORMATOOEGAM2DUPLICADO ga, DUPLICADO duplicado) {
		BeanPQVehiculosGuardar vehiculo = new BeanPQVehiculosGuardar();

		trafico.beans.jaxb.duplicados.DATOSVEHICULO vehiculoGa = duplicado.getDATOSVEHICULO();
		if (vehiculoGa == null)
			return vehiculo;

		vehiculo.setP_NUM_COLEGIADO(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
		vehiculo.setP_MATRICULA(StringUtils.isNotBlank(vehiculoGa.getMATRICULA()) ? vehiculoGa.getMATRICULA().trim() : vehiculoGa.getMATRICULA());
		vehiculo.setP_FECHA_MATRICULACION(DDMMAAAAToTimestamp(vehiculoGa.getFECHAMATRICULACION()));
		vehiculo.setP_FECHA_ITV(DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV()));

		return vehiculo;
	}

	/**
	 * Convierte una fecha String DD/MM/AAAA en un Timestamp. Ponemos manualmente la hora a 00:00:00
	 */
	private static Timestamp DDMMAAAAToTimestamp(String valor) {
		if (valor != null && (!"".equals(valor.trim()))) {
			String[] fecha = valor.split("/");
			String anyo = fecha[2];
			String mes = fecha[1].length()==2?fecha[1]:("0"+fecha[1]);
			String dia = fecha[0].length()==2?fecha[0]:("0"+fecha[0]);
			String valorToTimestamp = anyo + "-" + mes + "-" + dia + " 00:00:00";
			return Timestamp.valueOf(valorToTimestamp);
		}
		return null;
	}

	private static BeanPQTramiteTraficoGuardarInterviniente convertirIntervinienteGAtoPQ(FORMATOOEGAM2DUPLICADO ga,
			String tipoInterviniente, DUPLICADO duplicado, BigDecimal idUsuario, BigDecimal idContrato) {

		BeanPQTramiteTraficoGuardarInterviniente interviniente = new BeanPQTramiteTraficoGuardarInterviniente();

		if (TipoInterviniente.Titular.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.duplicados.DATOSTITULAR titular = duplicado.getDATOSTITULAR();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Titular.getValorEnum());
			interviniente.setP_NUM_COLEGIADO(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setP_NIF(titular.getNIFCIF());
			interviniente.setP_TIPO_PERSONA(titular.getTIPOPERSONA());
			interviniente.setP_SEXO(titular.getSEXO());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(titular.getAPELLIDO1RAZONSOCIAL());
			interviniente.setP_APELLIDO2(titular.getAPELLIDO2());
			interviniente.setP_NOMBRE(titular.getNOMBRE());

			// INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(titular.getDIRECCION().getPROVINCIA());
			interviniente.setP_ID_MUNICIPIO(titular.getDIRECCION().getMUNICIPIO());
			interviniente.setP_PUEBLO(titular.getDIRECCION().getPUEBLO());
			interviniente.setP_COD_POSTAL(titular.getDIRECCION().getCODIGOPOSTAL());
			interviniente.setP_ID_TIPO_VIA(titular.getDIRECCION().getTIPOVIA());
			interviniente.setP_NOMBRE_VIA(titular.getDIRECCION().getNOMBREVIA());
			interviniente.setP_NUMERO(titular.getDIRECCION().getNUMERO());
			interviniente.setP_LETRA(titular.getDIRECCION().getLETRA());
			interviniente.setP_ESCALERA(titular.getDIRECCION().getESCALERA());
			interviniente.setP_PLANTA(titular.getDIRECCION().getPISO());
			interviniente.setP_PUERTA(titular.getDIRECCION().getPUERTA());
			interviniente.setP_BLOQUE(titular.getDIRECCION().getBLOQUE());
			try {
				interviniente.setP_KM(new BigDecimal(titular.getDIRECCION().getKM()));
			} catch (Exception e) {
				interviniente.setP_KM(null);
			}
			try {
				interviniente.setP_HM(new BigDecimal(titular.getDIRECCION().getHM()));
			} catch (Exception e) {
				interviniente.setP_HM(null);
			}
		} else if (TipoInterviniente.RepresentanteTitular.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.duplicados.DATOSREPRESENTANTETITULAR repre = duplicado.getDATOSREPRESENTANTETITULAR();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);
			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setP_NUM_COLEGIADO(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());

			interviniente.setP_NIF(repre.getNIFCIF());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(repre.getAPELLIDO1RAZONSOCIAL());
			interviniente.setP_APELLIDO2(repre.getAPELLIDO2());
			interviniente.setP_NOMBRE(repre.getNOMBRE());
			interviniente.setP_CONCEPTO_REPRE(repre.getCONCEPTOTUTELA());
			interviniente.setP_DATOS_DOCUMENTO(repre.getDATOSDOCUMENTO());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(repre.getFECHAINICIOTUTELA()));
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(repre.getFECHAFINTUTELA()));
		} else if (TipoInterviniente.CotitularTransmision.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.duplicados.DATOSCOTITULAR coTitular = duplicado.getDATOSCOTITULAR();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Titular.getValorEnum());
			interviniente.setP_NUM_COLEGIADO(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setP_NIF(coTitular.getNIFCIF());
			interviniente.setP_TIPO_PERSONA(coTitular.getTIPOPERSONA());
			interviniente.setP_SEXO(coTitular.getSEXO());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(coTitular.getAPELLIDO1RAZONSOCIAL());
			interviniente.setP_APELLIDO2(coTitular.getAPELLIDO2());
			interviniente.setP_NOMBRE(coTitular.getNOMBRE());

			// INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(coTitular.getDIRECCION().getPROVINCIA());
			interviniente.setP_ID_MUNICIPIO(coTitular.getDIRECCION().getMUNICIPIO());
			interviniente.setP_PUEBLO(coTitular.getDIRECCION().getPUEBLO());
			interviniente.setP_COD_POSTAL(coTitular.getDIRECCION().getCODIGOPOSTAL());
			interviniente.setP_ID_TIPO_VIA(coTitular.getDIRECCION().getTIPOVIA());
			interviniente.setP_NOMBRE_VIA(coTitular.getDIRECCION().getNOMBREVIA());
			interviniente.setP_NUMERO(coTitular.getDIRECCION().getNUMERO());
			interviniente.setP_LETRA(coTitular.getDIRECCION().getLETRA());
			interviniente.setP_ESCALERA(coTitular.getDIRECCION().getESCALERA());
			interviniente.setP_PLANTA(coTitular.getDIRECCION().getPISO());
			interviniente.setP_PUERTA(coTitular.getDIRECCION().getPUERTA());
			interviniente.setP_BLOQUE(coTitular.getDIRECCION().getBLOQUE());
			try {
				interviniente.setP_KM(new BigDecimal(coTitular.getDIRECCION().getKM()));
			} catch (Exception e) {
				interviniente.setP_KM(null);
			}
			try {
				interviniente.setP_HM(new BigDecimal(coTitular.getDIRECCION().getHM()));
			} catch (Exception e) {
				interviniente.setP_HM(null);
			}
		}
		return interviniente;
	}

	private static String booleanToOegamString(Boolean b) {
		return b ? "SI" : "NO";
	}

}