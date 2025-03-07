package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import general.beans.RespuestaGenerica;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.daos.BeanPQSolicitudDatosGuardar;
import trafico.beans.daos.SolicitudVehiculoCursor;
import trafico.beans.daos.pq_tramite_trafico.BeanPQDETALLE_SOLICITUD;
import trafico.beans.daos.pq_tramite_trafico.BeanPQELIMINAR_SOL_VEHICULO;
import trafico.beans.daos.pq_tramite_trafico.BeanPQGUARDAR_SOL_VEHICULO;
import trafico.utiles.enumerados.TipoTasa;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class SolicitudDatosBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(SolicitudDatosBeanPQConversion.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public BeanPQSolicitudDatosGuardar convertirBeanToPQ(TramiteTraficoBean tramiteTrafico, BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {
		BeanPQSolicitudDatosGuardar solicitudPQ = new BeanPQSolicitudDatosGuardar();

		if (tramiteTrafico == null) return null;

		solicitudPQ.setP_REF_PROPIA(tramiteTrafico.getReferenciaPropia());
		solicitudPQ.setP_ANOTACIONES(tramiteTrafico.getAnotaciones());
		try {
			solicitudPQ.setP_FECHA_PRESENTACION(tramiteTrafico.getFechaPresentacion() != null ? tramiteTrafico.getFechaPresentacion().getTimestamp() : null);
		} catch (ParseException e1) {
			log.error(e1);
		}

		solicitudPQ.setP_ID_USUARIO(null == tramiteTrafico.getIdUsuario() ? idUsuario : tramiteTrafico.getIdUsuario());
		solicitudPQ.setP_NUM_EXPEDIENTE(null == tramiteTrafico.getNumExpediente() ? null : tramiteTrafico.getNumExpediente());
		solicitudPQ.setP_ID_CONTRATO(null == tramiteTrafico.getIdContrato() ? idContrato : tramiteTrafico.getIdContrato());
		solicitudPQ.setP_ID_CONTRATO_SESSION(idContrato);
		solicitudPQ.setP_NUM_COLEGIADO(null == tramiteTrafico.getNumColegiado() || tramiteTrafico.getNumColegiado().equals("") ? numColegiado:tramiteTrafico.getNumColegiado());
		if (tramiteTrafico.getEstado() != null) {
			solicitudPQ.setP_ESTADO(new BigDecimal(tramiteTrafico.getEstado().getValorEnum()));
		}

		try {
			solicitudPQ.setP_FECHA_ALTA(tramiteTrafico.getFechaCreacion() != null ? tramiteTrafico.getFechaCreacion().getTimestamp() : null);
			solicitudPQ.setP_FECHA_ULT_MODIF(tramiteTrafico.getFechaUltModif() != null ? tramiteTrafico.getFechaUltModif().getTimestamp() : null);
			solicitudPQ.setP_FECHA_IMPRESION(tramiteTrafico.getFechaImpresion() != null ? tramiteTrafico.getFechaImpresion().getTimestamp() : null);
		} catch (ParseException e) {
			log.error(e);
		}
		// DRC@15-02-2013 Incidencia Mantis: 2708
		solicitudPQ.setP_ID_TIPO_CREACION(tramiteTrafico.getIdTipoCreacion());
		return solicitudPQ;
	}

	public SolicitudDatosBean convertirPQToBean(RespuestaGenerica resultadoPQ) {
		SolicitudDatosBean tramite = new SolicitudDatosBean(true);

		// Campos genéricos del Trámite Tráfico
		tramite.getTramiteTrafico().setNumExpediente((BigDecimal)resultadoPQ.getParametro("P_NUM_EXPEDIENTE"));
		tramite.getTramiteTrafico().setIdContrato((BigDecimal)resultadoPQ.getParametro("P_ID_CONTRATO"));
		tramite.getTramiteTrafico().setNumColegiado((String)resultadoPQ.getParametro("P_NUM_COLEGIADO"));
		tramite.getTramiteTrafico().getVehiculo().setIdVehiculo((BigDecimal)resultadoPQ.getParametro("P_ID_VEHICULO"));
		tramite.getTramiteTrafico().getTasa().setCodigoTasa((String)resultadoPQ.getParametro("P_CODIGO_TASA"));
		tramite.getTramiteTrafico().getTasa().setTipoTasa((String)resultadoPQ.getParametro("P_TIPO_TASA"));

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

		if (resultadoPQ.getParametro("P_FECHA_ULT_MODIF") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ULT_MODIF");
			tramite.getTramiteTrafico().setFechaUltModif((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_IMPRESION") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IMPRESION");
			tramite.getTramiteTrafico().setFechaImpresion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.getTramiteTrafico().getJefaturaTrafico().setJefaturaProvincial((String)resultadoPQ.getParametro("P_JEFATURA_PROVINCIAL"));
		tramite.getTramiteTrafico().setAnotaciones((String)resultadoPQ.getParametro("P_ANOTACIONES"));
		tramite.getTramiteTrafico().setRenting((String)resultadoPQ.getParametro("P_RENTING"));
		// Cambio de domicilio no hace falta en este caso
		tramite.getTramiteTrafico().setIedtm((String)resultadoPQ.getParametro("P_IEDTM"));
		tramite.getTramiteTrafico().setModeloIedtm((String)resultadoPQ.getParametro("P_MODELO_IEDTM"));
		tramite.getTramiteTrafico().setModeloIedtm((String)resultadoPQ.getParametro("P_MODELO_IEDTM"));

		if (resultadoPQ.getParametro("P_FECHA_IEDTM") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IEDTM");
			tramite.getTramiteTrafico().setFechaIedtm((utilesFecha.getFechaFracionada(fecha)));
		}
		tramite.getTramiteTrafico().setNumRegIedtm((String)resultadoPQ.getParametro("P_N_REG_IEDTM"));
		tramite.getTramiteTrafico().setFinancieraIedtm((String)resultadoPQ.getParametro("P_FINANCIERA_IEDTM"));
		tramite.getTramiteTrafico().setExentoIedtm((String)resultadoPQ.getParametro("P_EXENTO_IEDTM"));
		tramite.getTramiteTrafico().setNoSujetoIedtm((String)resultadoPQ.getParametro("P_NO_SUJECION_IEDTM"));
		tramite.getTramiteTrafico().setCemIedtm((String)resultadoPQ.getParametro("P_CEM"));
		tramite.getTramiteTrafico().setRespuesta((String)resultadoPQ.getParametro("P_RESPUESTA"));

		return tramite;
	}

	public SolicitudDatosBean convertirPQToBeanDetalle(BeanPQDETALLE_SOLICITUD resultadoPQ, List <Object> listaSolicitudVehiculos) {
		SolicitudDatosBean tramite = new SolicitudDatosBean(true);

		// Campos genéricos del Trámite Tráfico
		tramite.getTramiteTrafico().setNumExpediente(resultadoPQ.getP_NUM_EXPEDIENTE());
		tramite.getTramiteTrafico().setIdContrato(resultadoPQ.getP_ID_CONTRATO());
		tramite.getTramiteTrafico().setNumColegiado(resultadoPQ.getP_NUM_COLEGIADO());

		if (resultadoPQ.getP_ESTADO() != null) {
			BigDecimal estado = resultadoPQ.getP_ESTADO();
			tramite.getTramiteTrafico().setEstado(estado.toString());
		}
		tramite.getTramiteTrafico().setReferenciaPropia(resultadoPQ.getP_REF_PROPIA());

		if (resultadoPQ.getP_FECHA_ALTA() != null) {
			Timestamp fecha = resultadoPQ.getP_FECHA_ALTA();
			tramite.getTramiteTrafico().setFechaCreacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getP_FECHA_PRESENTACION() != null) {
			Timestamp fecha = resultadoPQ.getP_FECHA_PRESENTACION();
			tramite.getTramiteTrafico().setFechaPresentacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getP_FECHA_ULT_MODIF() != null) {
			Timestamp fecha = resultadoPQ.getP_FECHA_ULT_MODIF();
			tramite.getTramiteTrafico().setFechaUltModif((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getP_FECHA_IMPRESION() != null) {
			Timestamp fecha = resultadoPQ.getP_FECHA_IMPRESION();
			tramite.getTramiteTrafico().setFechaImpresion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.getTramiteTrafico().getJefaturaTrafico().setJefaturaProvincial(resultadoPQ.getP_JEFATURA_PROVINCIAL());
		tramite.getTramiteTrafico().setAnotaciones(resultadoPQ.getP_ANOTACIONES());

		List<SolicitudVehiculoBean> lista = new ArrayList<>();

		for (Object object : listaSolicitudVehiculos) {
			SolicitudVehiculoCursor solicitudVehiculoPQ = (SolicitudVehiculoCursor)object;
			SolicitudVehiculoBean solicitudVehiculoBean = new SolicitudVehiculoBean(true);
			solicitudVehiculoBean.setIdContrato(tramite.getTramiteTrafico().getIdContrato());
			solicitudVehiculoBean.setNumExpediente(solicitudVehiculoPQ.getNUM_EXPEDIENTE());
			solicitudVehiculoBean.setResultado(solicitudVehiculoPQ.getRESULTADO());
			solicitudVehiculoBean.setEstado(solicitudVehiculoPQ.getESTADO() == null ? null : solicitudVehiculoPQ.getESTADO().toString());
			solicitudVehiculoBean.getVehiculo().setIdVehiculo(solicitudVehiculoPQ.getID_VEHICULO());
			solicitudVehiculoBean.getVehiculo().setBastidor(solicitudVehiculoPQ.getBASTIDOR());
			solicitudVehiculoBean.getVehiculo().setMatricula(solicitudVehiculoPQ.getMATRICULA());
			solicitudVehiculoBean.getTasa().setCodigoTasa(solicitudVehiculoPQ.getCODIGO_TASA());
			solicitudVehiculoBean.getTasa().setTipoTasa(TipoTasa.CuatroUno.getValorEnum());
			solicitudVehiculoBean.getVehiculo().setNumColegiado(tramite.getTramiteTrafico().getNumColegiado());
			solicitudVehiculoBean.setMotivoInteve(solicitudVehiculoPQ.getMOTIVO_INTEVE());
			solicitudVehiculoBean.getVehiculo().setNive(solicitudVehiculoPQ.getNIVE());
			solicitudVehiculoBean.setTipoInforme(solicitudVehiculoPQ.getTIPO_INFORME());
			lista.add(solicitudVehiculoBean);
		}

		tramite.setSolicitudesVehiculos(lista);

		return tramite;
	}

	public BeanPQGUARDAR_SOL_VEHICULO convertirSolicitudVehiculoToPQ(
			SolicitudVehiculoBean solicitudVehiculo, BigDecimal numExpediente, String numColegiado,
			BigDecimal idUsuario, BigDecimal idContrato, String numColegiadoDeUtiles) {
		BeanPQGUARDAR_SOL_VEHICULO pqGuardar = new BeanPQGUARDAR_SOL_VEHICULO();

		if (solicitudVehiculo.getVehiculo().getBastidor() != null) {
			pqGuardar.setP_BASTIDOR(solicitudVehiculo.getVehiculo().getBastidor().toUpperCase());
		}
		if (solicitudVehiculo.getVehiculo().getMatricula() != null) {
			pqGuardar.setP_MATRICULA(solicitudVehiculo.getVehiculo().getMatricula().toUpperCase());
		}
		pqGuardar.setP_ID_CONTRATO_SESSION(idContrato);
		pqGuardar.setP_ID_USUARIO(idUsuario);
		pqGuardar.setP_NUM_COLEGIADO(null==numColegiado || numColegiado.equals("")?numColegiadoDeUtiles:numColegiado);
		pqGuardar.setP_NUM_EXPEDIENTE(numExpediente);
		pqGuardar.setP_CODIGO_TASA(solicitudVehiculo.getTasa().getCodigoTasa());
		pqGuardar.setP_ID_VEHICULO(solicitudVehiculo.getVehiculo().getIdVehiculo());
		pqGuardar.setP_TIPO_TRAMITE(TipoTramiteTrafico.Solicitud.getValorEnum());
		pqGuardar.setP_ESTADO(solicitudVehiculo.getEstado() != null ? new BigDecimal(solicitudVehiculo.getEstado().getValorEnum()) : null);
		pqGuardar.setP_RESULTADO(solicitudVehiculo.getResultado());
		pqGuardar.setP_REFERENCIA(solicitudVehiculo.getReferenciaAtem());
		pqGuardar.setP_MOTIVO_INTEVE(solicitudVehiculo.getMotivoInteve());
		pqGuardar.setP_TIPO_INFORME(solicitudVehiculo.getTipoInforme());

		if (solicitudVehiculo.getVehiculo().getNive() != null) {
			pqGuardar.setP_NIVE(solicitudVehiculo.getVehiculo().getNive().toUpperCase());
		}

		return pqGuardar;
	}

	public SolicitudVehiculoBean convertirSolicitudVehiculoPQToBean(BeanPQGUARDAR_SOL_VEHICULO solicitudVehiculoPQ) {
		SolicitudVehiculoBean beanGuardar = new SolicitudVehiculoBean(true);
		beanGuardar.getVehiculo().setBastidor(solicitudVehiculoPQ.getP_BASTIDOR());
		beanGuardar.getVehiculo().setMatricula(solicitudVehiculoPQ.getP_MATRICULA());
		beanGuardar.setIdContrato(solicitudVehiculoPQ.getP_ID_CONTRATO_SESSION());
		beanGuardar.getVehiculo().setNumColegiado(solicitudVehiculoPQ.getP_NUM_COLEGIADO());
		beanGuardar.setNumExpediente(solicitudVehiculoPQ.getP_NUM_EXPEDIENTE());
		beanGuardar.getTasa().setCodigoTasa(solicitudVehiculoPQ.getP_CODIGO_TASA());
		beanGuardar.getVehiculo().setIdVehiculo(solicitudVehiculoPQ.getP_ID_VEHICULO());
		beanGuardar.setTipoTramite(TipoTramiteTrafico.Solicitud.getValorEnum());
		beanGuardar.setEstado(solicitudVehiculoPQ.getP_ESTADO() != null ? solicitudVehiculoPQ.getP_ESTADO().toString() : "0");
		beanGuardar.setResultado(solicitudVehiculoPQ.getP_RESULTADO());
		beanGuardar.setReferenciaAtem(solicitudVehiculoPQ.getP_REFERENCIA());
		beanGuardar.setMotivoInteve(solicitudVehiculoPQ.getP_MOTIVO_INTEVE());
		beanGuardar.setTipoInforme(solicitudVehiculoPQ.getP_TIPO_INFORME());
		beanGuardar.getVehiculo().setNive(solicitudVehiculoPQ.getP_NIVE());
		return beanGuardar;
	}

	public BeanPQELIMINAR_SOL_VEHICULO convertirSolicitudVehiculoBorrarToPQ(SolicitudVehiculoBean solicitudBorrar) {
		BeanPQELIMINAR_SOL_VEHICULO pqBorrar = new BeanPQELIMINAR_SOL_VEHICULO();
		pqBorrar.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		pqBorrar.setP_ID_CONTRATO(solicitudBorrar.getIdContrato());
		pqBorrar.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		pqBorrar.setP_NUM_COLEGIADO(solicitudBorrar.getVehiculo().getNumColegiado());
		pqBorrar.setP_NUM_EXPEDIENTE(solicitudBorrar.getNumExpediente());
		pqBorrar.setP_ID_VEHICULO(solicitudBorrar.getVehiculo().getIdVehiculo());
		return pqBorrar;
	}
}