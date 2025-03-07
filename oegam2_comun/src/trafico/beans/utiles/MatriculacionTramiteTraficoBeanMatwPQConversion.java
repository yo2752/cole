package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.Direccion;
import escrituras.utiles.enumerados.Decision;
import escrituras.utiles.enumerados.DecisionTrafico;
import general.beans.RespuestaGenerica;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.daos.BeanPQAltaMatriculacion;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarMatriculacion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.utiles.constantes.ConstantesDGT;
import trafico.utiles.enumerados.TipoLimitacionDisposicionIEDTM;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class MatriculacionTramiteTraficoBeanMatwPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(MatriculacionTramiteTraficoBeanMatwPQConversion.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public TramiteTraficoMatriculacionBean convertirPQToBean(RespuestaGenerica resultadoPQ) {

		TramiteTraficoMatriculacionBean tramite = new TramiteTraficoMatriculacionBean(true);

		// Campos genéricos del Trámite Tráfico
//		tramite.getTramiteTraficoBean().setRowIdTraficoTramite((String)resultadoPQ.getParametro("ROWID_TRAMITE"));
		tramite.getTramiteTraficoBean().setNumExpediente((BigDecimal)resultadoPQ.getParametro("P_NUM_EXPEDIENTE"));
		tramite.getTramiteTraficoBean().setIdContrato((BigDecimal)resultadoPQ.getParametro("P_ID_CONTRATO"));
		tramite.getTramiteTraficoBean().setNumColegiado((String)resultadoPQ.getParametro("P_NUM_COLEGIADO"));
		tramite.getTramiteTraficoBean().getVehiculo().setIdVehiculo((BigDecimal)resultadoPQ.getParametro("P_ID_VEHICULO"));
		tramite.getTramiteTraficoBean().getTasa().setCodigoTasa((String)resultadoPQ.getParametro("P_CODIGO_TASA"));
		tramite.getTramiteTraficoBean().getTasa().setTipoTasa((String)resultadoPQ.getParametro("P_TIPO_TASA"));

		if (resultadoPQ.getParametro("P_ESTADO") != null){
			BigDecimal estado = (BigDecimal)resultadoPQ.getParametro("P_ESTADO");
			tramite.getTramiteTraficoBean().setEstado(estado.toString());
		}
		tramite.getTramiteTraficoBean().setReferenciaPropia((String)resultadoPQ.getParametro("P_REF_PROPIA"));

		if (resultadoPQ.getParametro("P_FECHA_ALTA") != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ALTA");
			tramite.getTramiteTraficoBean().setFechaCreacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_PRESENTACION") != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_PRESENTACION");
			tramite.getTramiteTraficoBean().setFechaPresentacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if(resultadoPQ.getParametro("P_ENTREGA_FACT_MATRICULACION") != null && !"".equals(resultadoPQ.getParametro("P_ENTREGA_FACT_MATRICULACION"))){
			tramite.setEntregaFactMatriculacion(("SI".equals((String)resultadoPQ.getParametro("P_ENTREGA_FACT_MATRICULACION"))
					|| TipoSN.S.value().equals((String)resultadoPQ.getParametro("P_ENTREGA_FACT_MATRICULACION")))?"true":"false");
		}

		if (resultadoPQ.getParametro("P_FECHA_ULT_MODIF") != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ULT_MODIF");
			tramite.getTramiteTraficoBean().setFechaUltModif(utilesFecha.getFechaFracionada(fecha));
		}

		if (resultadoPQ.getParametro("P_FECHA_IMPRESION") != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IMPRESION");
			tramite.getTramiteTraficoBean().setFechaImpresion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.getTramiteTraficoBean().getJefaturaTrafico().setJefaturaProvincial((String)resultadoPQ.getParametro("P_JEFATURA_PROVINCIAL"));
		tramite.getTramiteTraficoBean().setAnotaciones((String)resultadoPQ.getParametro("P_ANOTACIONES"));
		tramite.getTramiteTraficoBean().setRenting("SI".equals((String)resultadoPQ.getParametro("P_RENTING"))?"true":"false");

		tramite.getTramiteTraficoBean().setCarsharing("S".equals((String)resultadoPQ.getParametro("P_CARSHARING"))?"true":"false");

		//cambio de domicilio no hace falta en este caso
		tramite.getTramiteTraficoBean().setIedtm((String)resultadoPQ.getParametro("P_IEDTM"));
		if(null!=(String)resultadoPQ.getParametro("P_IEDTM")){
			tramite.setTipoLimitacionDisposicionIEDTM((String)resultadoPQ.getParametro("P_IEDTM"));
		}
		tramite.getTramiteTraficoBean().setModeloIedtm((String)resultadoPQ.getParametro("P_MODELO_IEDTM"));

		if (resultadoPQ.getParametro("P_FECHA_IEDTM") != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IEDTM");
			tramite.getTramiteTraficoBean().setFechaIedtm((utilesFecha.getFechaFracionada(fecha)));
		}
		tramite.getTramiteTraficoBean().setNumRegIedtm((String)resultadoPQ.getParametro("P_N_REG_IEDTM"));
		tramite.getTramiteTraficoBean().setFinancieraIedtm((String)resultadoPQ.getParametro("P_FINANCIERA_IEDTM"));
		tramite.getTramiteTraficoBean().setExentoIedtm((String)resultadoPQ.getParametro("P_EXENTO_IEDTM"));
		tramite.getTramiteTraficoBean().setNoSujetoIedtm((String)resultadoPQ.getParametro("P_NO_SUJECION_IEDTM"));
		tramite.getTramiteTraficoBean().setCemIedtm((String)resultadoPQ.getParametro("P_CEM"));
		tramite.getTramiteTraficoBean().setCema((String)resultadoPQ.getParametro("P_CEMA"));

		tramite.getTramiteTraficoBean().setRespuesta((String)resultadoPQ.getParametro("P_RESPUESTA"));
		//Datos específicos del Trámite de Matriculación

		tramite.setBaseImponible576((BigDecimal)resultadoPQ.getParametro("P_BASE_IMPONIBLE_576")) ;

		if (resultadoPQ.getParametro("P_REDUCCION_576")!= null){
			String reduccion = (String)resultadoPQ.getParametro("P_REDUCCION_576");
			if(Decision.Si.getValorEnum().equals(reduccion)){
				tramite.setReduccion(true);
			}else{
				tramite.setReduccion(false);
			}
		}
		tramite.setBaseImponibleReducida576((BigDecimal)resultadoPQ.getParametro("P_BASE_IMPO_REDUCIDA_576")) ;
		tramite.setTipoGravamen((BigDecimal)resultadoPQ.getParametro("P_TIPO_GRAVAMEN_576"));
		tramite.setCuota576((BigDecimal)resultadoPQ.getParametro("P_CUOTA_576"));
		tramite.setDeduccionLineal576((BigDecimal)resultadoPQ.getParametro("P_DEDUCCION_LINEAL_576"));
		tramite.setCuotaIngresar576((BigDecimal)resultadoPQ.getParametro("P_CUOTA_INGRESAR_576"));
		tramite.setDeducir576((BigDecimal)resultadoPQ.getParametro("P_A_DEDUCIR_576"));
		tramite.setLiquidacion576((BigDecimal)resultadoPQ.getParametro("P_LIQUIDACION_576"));
		tramite.setImporte576((BigDecimal)resultadoPQ.getParametro("P_IMPORTE_576"));
		tramite.setNumDeclaracionComplementaria576((String)resultadoPQ.getParametro("P_N_DECLARACION_COMP_576"));

		BigDecimal ejercicio = (BigDecimal)resultadoPQ.getParametro("P_EJERCICIO_576");
		if(ejercicio != null){
			tramite.setEjercicioDevengo576(new Integer(ejercicio.toString()));
		}

		String causa = (String)resultadoPQ.getParametro("P_CAUSA_HECHO_IMPON_576");
		if(causa != null){
			tramite.setCausaHechoImponible576(causa.toString());
		}

		tramite.setObservaciones576((String)resultadoPQ.getParametro("P_OBSERVACIONES_576"));
		tramite.setExento576(DecisionTrafico.Si.getValorEnum().equals((String)resultadoPQ.getParametro("P_EXENTO_576"))?"true":"false");
		tramite.setNrc576((String)resultadoPQ.getParametro("P_NRC_576"));

		if (resultadoPQ.getParametro("P_FECHA_PAGO_576") != null){
			Timestamp  fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_PAGO_576");
			tramite.setFechaPago576(utilesFecha.getFechaFracionada(fecha));
		}
		tramite.setIdReduccion05((String)resultadoPQ.getParametro("P_ID_REDUCCION_05"));
		tramite.setIdNoSujeccion06((String)resultadoPQ.getParametro("P_ID_NO_SUJECCION_06"));
		tramite.getTramiteTraficoBean().setExentoCem("SI".equals((String)resultadoPQ.getParametro("P_EXENTO_CEM"))?"true":"false");		

		//JMC Cemu y Satisfecho_Cet Desapareces de la primera versión de Matw
//		tramite.getTramiteTraficoBean().setCemu((String)resultadoPQ.getParametro("P_CEMU"));
//		String satisfechoCet = (String) resultadoPQ.getParametro("P_SATISFECHO_CET");
//		if(satisfechoCet != null && satisfechoCet.equalsIgnoreCase("S")){
//			tramite.getTramiteTraficoBean().setSatisfechoCet(Boolean.TRUE.toString());
//		}else if(satisfechoCet != null && satisfechoCet.equalsIgnoreCase("S")){
//			tramite.getTramiteTraficoBean().setSatisfechoCet(Boolean.FALSE.toString());
//		}
		tramite.setTipoTramiteMatriculacion((String)resultadoPQ.getParametro("P_TIPO_TRAMITE_MATR"));
		tramite.setJustificado_IVTM("S".equals((String)resultadoPQ.getParametro("P_JUSTIFICADO_IVTM")) ||
				"SI".equals((String)resultadoPQ.getParametro("P_JUSTIFICADO_IVTM"))?"true":"false");

		tramite.setRespuestaEitv((String)resultadoPQ.getParametro("P_RESPUESTA_EITV"));
		tramite.setIdTipoCreacion((BigDecimal) resultadoPQ.getParametro("P_ID_TIPO_CREACION"));
		tramite.setPermiso((String)resultadoPQ.getParametro("P_PERMISO"));
		tramite.setDistintivo((String)resultadoPQ.getParametro("P_DISTINTIVO"));
		tramite.setCheckJustifIvtm((String)resultadoPQ.getParametro("P_CHECK_JUSTIF_IVTM"));
		return tramite;
	}

	public BeanPQTramiteTraficoGuardarMatriculacion convertirBeanToPQ (TramiteTraficoMatriculacionBean tramite, BigDecimal idContratoSesion, BigDecimal idContrato, BigDecimal idUsuario)  {
		//Controlo que el interviniente no venga a null; si fuera null, tendría nullPointer Exception al hacer vehiculo.getX().
		if (tramite==null) return null;

		BeanPQTramiteTraficoGuardarMatriculacion tramitePQ = new BeanPQTramiteTraficoGuardarMatriculacion();

//		tramitePQ.setROWID_TRAMITE(tramite.getTramiteTraficoBean()!=null && tramite.getTramiteTraficoBean().getRowIdTraficoTramite()!= null ?tramite.getTramiteTraficoBean().getRowIdTraficoTramite():null);
		// Valores propios del tramite general
		try{
			if (tramite.getTramiteTraficoBean()!=null){
				tramitePQ.setP_ID_USUARIO(null!=tramite.getTramiteTraficoBean().getIdUsuario()?tramite.getTramiteTraficoBean().getIdUsuario():idUsuario);
				tramitePQ.setP_NUM_EXPEDIENTE(null==tramite.getTramiteTraficoBean().getNumExpediente()?null:tramite.getTramiteTraficoBean().getNumExpediente());
				tramitePQ.setP_ID_CONTRATO(null!=tramite.getTramiteTraficoBean().getIdContrato()?tramite.getTramiteTraficoBean().getIdContrato():idContrato);
				tramitePQ.setP_ID_CONTRATO_SESSION(idContratoSesion);
				tramitePQ.setP_NUM_COLEGIADO(tramite.getTramiteTraficoBean().getNumColegiado()!=null&&!tramite.getTramiteTraficoBean().getNumColegiado().equals("")?tramite.getTramiteTraficoBean().getNumColegiado():utilesColegiado.getNumColegiadoSession());

				tramitePQ.setP_REDUCCION_576(tramite.isReduccion()?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum());	

				if (tramite.getTramiteTraficoBean().getEstado() != null){
					tramitePQ.setP_ESTADO(new BigDecimal(tramite.getTramiteTraficoBean().getEstado().getValorEnum()));
				}
				tramitePQ.setP_REF_PROPIA(tramite.getTramiteTraficoBean().getReferenciaPropia()!=null && !tramite.getTramiteTraficoBean().getReferenciaPropia().equals("")?tramite.getTramiteTraficoBean().getReferenciaPropia():null);
				tramitePQ.setP_ANOTACIONES(tramite.getTramiteTraficoBean().getAnotaciones()!=null && !tramite.getTramiteTraficoBean().getAnotaciones().equals("")?tramite.getTramiteTraficoBean().getAnotaciones():null);
				if (tramite.getTramiteTraficoBean().getFechaPresentacion()!=null){
					tramitePQ.setP_FECHA_PRESENTACION(null==tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp()?null:tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp());
				}

				Fecha fechaUltM = tramite.getTramiteTraficoBean().getFechaUltModif();
				if (fechaUltM != null){
					tramitePQ.setP_FECHA_ULT_MODIF(fechaUltM!= null ? fechaUltM.getTimestamp() :null);
				}

				Fecha fechaImpresion = tramite.getTramiteTraficoBean().getFechaImpresion();
				if (fechaImpresion != null){
					tramitePQ.setP_FECHA_IMPRESION(fechaImpresion.getTimestamp());
				}
				tramitePQ.setP_RENTING(tramite.getTramiteTraficoBean().getRenting()!=null && !tramite.getTramiteTraficoBean().getRenting().equals("")?(tramite.getTramiteTraficoBean().getRenting().equals("true")?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);
				tramitePQ.setP_CARSHARING(tramite.getTramiteTraficoBean().getCarsharing()!=null && !tramite.getTramiteTraficoBean().getCarsharing().equals("")?(tramite.getTramiteTraficoBean().getCarsharing().equals("true")?"S":"N"):null);
				tramitePQ.setP_IEDTM(tramite.getTramiteTraficoBean().getIedtm()!=null && !tramite.getTramiteTraficoBean().getIedtm().equals("")?(tramite.getTramiteTraficoBean().getIedtm().equals(TipoLimitacionDisposicionIEDTM.ImpuestoEspecial.getValorEnum())?TipoLimitacionDisposicionIEDTM.ImpuestoEspecial.getValorEnum():""):null);
				tramitePQ.setP_MODELO_IEDTM(tramite.getTramiteTraficoBean().getModeloIedtm()!=null && !tramite.getTramiteTraficoBean().getModeloIedtm().equals("") && !tramite.getTramiteTraficoBean().getModeloIedtm().equals("-1")?tramite.getTramiteTraficoBean().getModeloIedtm():null);
				if (tramite.getTramiteTraficoBean().getFechaIedtm()!=null){
					tramitePQ.setP_FECHA_IEDTM(null==tramite.getTramiteTraficoBean().getFechaIedtm().getTimestamp()?null:tramite.getTramiteTraficoBean().getFechaIedtm().getTimestamp());
				}
				tramitePQ.setP_N_REG_IEDTM(tramite.getTramiteTraficoBean().getNumRegIedtm()!=null && !tramite.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("-1")?tramite.getTramiteTraficoBean().getNumRegIedtm().getValorEnum():null);
				tramitePQ.setP_FINANCIERA_IEDTM(tramite.getTramiteTraficoBean().getFinancieraIedtm()!=null && !tramite.getTramiteTraficoBean().getFinancieraIedtm().equals("")?tramite.getTramiteTraficoBean().getFinancieraIedtm():null);
				tramitePQ.setP_EXENTO_IEDTM(tramite.getTramiteTraficoBean().getExentoIedtm()!=null && !tramite.getTramiteTraficoBean().getExentoIedtm().equals("")?(tramite.getTramiteTraficoBean().getExentoIedtm().equals("true")?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);
				tramitePQ.setP_NO_SUJECION_IEDTM(tramite.getTramiteTraficoBean().getNoSujetoIedtm()!=null && !tramite.getTramiteTraficoBean().getNoSujetoIedtm().equals("")?(tramite.getTramiteTraficoBean().getNoSujetoIedtm().equals("true")?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);
				tramitePQ.setP_RESPUESTA(tramite.getTramiteTraficoBean().getRespuesta()); //PREGUNTAR
				tramitePQ.setP_EXENTO_CEM(tramite.getTramiteTraficoBean().getExentoCem()!=null && !tramite.getTramiteTraficoBean().getExentoCem().equals("")?(tramite.getTramiteTraficoBean().getExentoCem().equals("true")?"SI":"NO"):null);
			}
		} catch (ParseException e) {
			log.error(e);
		}

		if (tramite.getTramiteTraficoBean().getTasa()!=null){
			tramitePQ.setP_CODIGO_TASA(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa()!=null && !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("") && !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("-1")?tramite.getTramiteTraficoBean().getTasa().getCodigoTasa():null);
		}else{
			tramitePQ.setP_CODIGO_TASA(null);
		}

		if (tramite.getTramiteTraficoBean().getTasa()!=null){
			tramitePQ.setP_TIPO_TASA(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa()!=null && !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("") && !tramite.getTramiteTraficoBean().getTasa().getTipoTasa().equals("-1")?tramite.getTramiteTraficoBean().getTasa().getTipoTasa():null);
		}else{
			tramitePQ.setP_TIPO_TASA(null);
		}

		if (tramite.getTramiteTraficoBean().getJefaturaTrafico()!=null){
			tramitePQ.setP_JEFATURA_PROVINCIAL(tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial()!=null && !tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial().equals("") && !tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial().equals("-1")?tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial():null);
		}else{
			tramitePQ.setP_JEFATURA_PROVINCIAL(null);
		}

		tramitePQ.setP_CEM(tramite.getTramiteTraficoBean().getCemIedtm()!=null && !tramite.getTramiteTraficoBean().getCemIedtm().equals("")?tramite.getTramiteTraficoBean().getCemIedtm():null);
		tramitePQ.setP_CEMA(tramite.getTramiteTraficoBean().getCema()!=null && !tramite.getTramiteTraficoBean().getCema().equals("")?tramite.getTramiteTraficoBean().getCema():null);		

		if(tramite.getTramiteTraficoBean().getVehiculo()!=null){
			//Si hay datos de domicilio de vehículo es que es distinto al del titular...
			tramitePQ.setP_CAMBIO_DOMICILIO(tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()!=null && tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal()!=null && !tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal().equals("")?DecisionTrafico.Si.getValorEnum():null);
			tramitePQ.setP_ID_VEHICULO(null==tramite.getTramiteTraficoBean().getVehiculo().getIdVehiculo()?null:tramite.getTramiteTraficoBean().getVehiculo().getIdVehiculo());
		}

		tramitePQ.setP_INFORMACION(null);
		tramitePQ.setP_A_DEDUCIR_576(tramite.getDeducir576());
		tramitePQ.setP_BASE_IMPO_REDUCIDA_576(tramite.getBaseImponibleReducida576());
		tramitePQ.setP_BASE_IMPONIBLE_576(tramite.getBaseImponible576());
		tramitePQ.setP_CAUSA_HECHO_IMPON_576(tramite.getCausaHechoImponible576() != null ?tramite.getCausaHechoImponible576().getValorEnum():null);
		tramitePQ.setP_CUOTA_576(tramite.getCuota576());
		tramitePQ.setP_CUOTA_INGRESAR_576(tramite.getCuotaIngresar576());
		tramitePQ.setP_DEDUCCION_LINEAL_576(tramite.getDeduccionLineal576());
		tramitePQ.setP_LIQUIDACION_576(tramite.getLiquidacion576());
		tramitePQ.setP_IMPORTE_576(tramite.getImporte576());
		tramitePQ.setP_N_DECLARACION_COMP_576(tramite.getNumDeclaracionComplementaria576()); 
		tramitePQ.setP_EJERCICIO_576(tramite.getEjercicioDevengo576() != null ? tramite.getEjercicioDevengo576():null);
		tramitePQ.setP_ESTADO(tramite.getTramiteTraficoBean().getEstado() != null ? new BigDecimal(tramite.getTramiteTraficoBean().getEstado().getValorEnum().toString()):null);
		tramitePQ.setP_EXENTO_576("true".equals(tramite.getExento576())?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum());
		tramitePQ.setP_NRC_576(tramite.getNrc576()); 

		try {
			tramitePQ.setP_FECHA_ALTA(tramite.getTramiteTraficoBean().getFechaCreacion() != null ? tramite.getTramiteTraficoBean().getFechaCreacion().getTimestamp():null);			
		} catch (ParseException e) {
			log.error("Error en la fecha Alta " + e.toString().substring(49));
		}

		try {
			tramitePQ.setP_FECHA_PAGO_576(tramite.getFechaPago576() != null ? tramite.getFechaPago576().getTimestamp():null);
		} catch (ParseException e) {
			log.error("Error en la fecha Pago 576 " + e.toString().substring(49));
		}

		tramitePQ.setP_ID_NO_SUJECCION_06(tramite.getIdNoSujeccion06()!= null ? tramite.getIdNoSujeccion06().getValorEnum():null);
		tramitePQ.setP_ID_REDUCCION_05(tramite.getIdReduccion05()!=null ? tramite.getIdReduccion05().getValorEnum():null); 

		tramitePQ.setP_OBSERVACIONES_576(tramite.getObservaciones576() != null ? tramite.getObservaciones576().getValorEnum():null);
		tramitePQ.setP_TIPO_GRAVAMEN_576(tramite.getTipoGravamen());

		//DRC@15-02-2013 Incidencia Mantis: 2708
		tramitePQ.setP_ID_TIPO_CREACION(tramite.getIdTipoCreacion());

		//CFS@24-05-2013 Se añade nueva entrada por check de entrega de factura, que se guarda en BBDD
		tramitePQ.setP_ENTREGA_FACT_MATRICULACION("true".equals(tramite.getEntregaFactMatriculacion())?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum());

		// JMC Cemu y Satisfecho_Cet Desapareces de la primera versión de Matw
//		tramitePQ.setP_CEMU(tramite.getTramiteTraficoBean().getCemu()!=null && !tramite.getTramiteTraficoBean().getCemu().equals("")?tramite.getTramiteTraficoBean().getCemu():null);
//		if(tramite.getTramiteTraficoBean().getSatisfechoCet()!=null && !tramite.getTramiteTraficoBean().getSatisfechoCet().equals("")){
//			if(tramite.getTramiteTraficoBean().getSatisfechoCet().equalsIgnoreCase("true")){
//				tramitePQ.setP_SATISFECHO_CET(TipoSN.S.toString());
//			}else if(tramite.getTramiteTraficoBean().getSatisfechoCet().equalsIgnoreCase("false")){
//				tramitePQ.setP_SATISFECHO_CET(TipoSN.N.toString());
//			}
//		}
		tramitePQ.setP_TIPO_TRAMITE_MATR(tramite.getTipoTramiteMatriculacion()!=null && !tramite.getTipoTramiteMatriculacion().equals("")?tramite.getTipoTramiteMatriculacion().getValorEnum():null);
		tramitePQ.setP_JUSTIFICADO_IVTM(tramite.getJustificado_IVTM()!=null && !tramite.getJustificado_IVTM().equals("")?(tramite.getJustificado_IVTM().equals("true")?"S":"N"):null);
		tramitePQ.setP_RESPUESTA_EITV(tramite.getRespuestaEitv()!=null && !tramite.getRespuestaEitv().equals("")?tramite.getRespuestaEitv().toString():null);

		return tramitePQ;
	}

	public BeanPQAltaMatriculacion convertirTramiteMatriculacionToPQ(TramiteTraficoMatriculacionBean traficoMatriculacionBean, String numColegiado, BigDecimal idContratoSesion, BigDecimal idUsuario, BigDecimal idContrato) {
		BeanPQAltaMatriculacion beanPQ = new BeanPQAltaMatriculacion();

		//ATRIBUTOS DEL BEAN de TramiteTraficoMatriculacionBean
		//traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo(); //VEHICULO
		traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().setDireccionBean(aniadirCorreos(traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean()));
		beanPQ.setBeanGuardarVehiculo(vehiculoBeanPQConversion.beanVehiculoPreverConvertirBeanPQ(traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo(), 
					traficoMatriculacionBean.getVehiculoPreverBean(),  
					traficoMatriculacionBean.getTramiteTraficoBean(), 
					idUsuario, idContrato, numColegiado));
		//traficoMatriculacionBean.getTramiteTraficoBean(); // TRAMITE
		beanPQ.setBeanGuardarMatriculacion(convertirBeanToPQ(traficoMatriculacionBean,idContratoSesion,idContrato,idUsuario));
		//traficoMatriculacionBean.getTitularBean(); //TITULAR
		traficoMatriculacionBean.getTitularBean().getPersona().setDireccion(aniadirCorreos(traficoMatriculacionBean.getTitularBean().getPersona().getDireccion()));
		beanPQ.setBeanGuardarTitular(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getTitularBean(),
				idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarTitular().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_TITULAR);
		//traficoMatriculacionBean.getRepresentanteTitularBean(); //REPRESENTANTE
		traficoMatriculacionBean.getRepresentanteTitularBean().getPersona().setDireccion(aniadirCorreos(traficoMatriculacionBean.getRepresentanteTitularBean().getPersona().getDireccion()));
		beanPQ.setBeanGuardarRepresentante(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getRepresentanteTitularBean(),
				idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarRepresentante().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR);
		//traficoMatriculacionBean.getConductorHabitualBean(); //CONDUCTOR HABITUAL
		traficoMatriculacionBean.getConductorHabitualBean().getPersona().setDireccion(aniadirCorreos(traficoMatriculacionBean.getConductorHabitualBean().getPersona().getDireccion()));
		beanPQ.setBeanGuardarConductorHabitual(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getConductorHabitualBean(),
				idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarConductorHabitual().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_CONDUCTOR_HABITUAL);
		//traficoMatriculacionBean.getArrendatarioBean(); //ARRENDATARIO
		traficoMatriculacionBean.getArrendatarioBean().getPersona().setDireccion(aniadirCorreos(traficoMatriculacionBean.getArrendatarioBean().getPersona().getDireccion()));
		beanPQ.setBeanGuardarArrendatario(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getArrendatarioBean(),
				idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarArrendatario().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_ARRENDATARIO);
		// traficoMatriculacionBean.getRepresentanteConductorHabitualBean(); //REPRESENTANTE CONDUCTOR HABITUAL
		/*beanPQ.setBeanGuardarRepresentanteConductorHabitual(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getRepresentanteConductorHabitualBean(),
				idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarRepresentanteConductorHabitual().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_CONDUCTOR_HABITUAL);*/
		// traficoMatriculacionBean.getRepresentanteArrendatarioBean(); //REPRESENTANTE ARRENDATARIO
		traficoMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().setDireccion(aniadirCorreos(traficoMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getDireccion()));
		beanPQ.setBeanGuardarRepresentanteArrendatario(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getRepresentanteArrendatarioBean(),
			idUsuario, idContrato, numColegiado));
		beanPQ.getBeanGuardarRepresentanteArrendatario().setP_TIPO_INTERVINIENTE(ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_ARRENDATARIO);

		beanPQ.getBeanGuardarMatriculacion().setP_CHECK_JUSTIF_FICHERO_IVTM(traficoMatriculacionBean.getCheckJustifIvtm());	

		return beanPQ;
	}

	private static Direccion aniadirCorreos (Direccion direccion) {
		if (direccion!=null) {
			direccion.setCodPostal(direccion.getCodPostalCorreos());
			direccion.setPueblo(direccion.getPuebloCorreos());
		}
		return direccion;
	}

}