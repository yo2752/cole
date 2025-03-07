package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericProcedureDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class TramiteTraficoProcedureDaoImpl extends GenericProcedureDaoImplHibernate implements TramiteTraficoProcedureDao {

	private static final long serialVersionUID = -5130495835461223968L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(TramiteTraficoProcedureDaoImpl.class);

	@Autowired
	private Utiles utiles;

	/*
	 * VALIDAR_TRAMITE_MATRICULACION (P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE, P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE, P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE, P_ID_CONTRATO_SESSION IN
	 * TRAMITE_TRAFICO.ID_CONTRATO%TYPE, P_REVISADO IN OUT VARCHAR2, P_INFORMACION OUT NVARCHAR2, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_TRAMITE_MATRICULACION = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_MATRICULACION(?,?,?,?,?,?,?,?,?)}";

	/*
	 * VALIDAR_TRAMITE_CTIT (P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE, P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE, P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE, P_ID_CONTRATO_SESSION IN
	 * TRAMITE_TRAFICO.ID_CONTRATO%TYPE, P_REVISADO IN OUT VARCHAR2, P_INFORMACION OUT NVARCHAR2, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_TRAMITE_CTIT = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_CTIT(?,?,?,?,?,?,?,?,?)}";
	/*
	 * VALIDAR_TRAMITE_BAJA (P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE, P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE, P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE, P_ID_CONTRATO_SESSION IN
	 * TRAMITE_TRAFICO.ID_CONTRATO%TYPE, P_REVISADO IN OUT VARCHAR2, P_INFORMACION OUT NVARCHAR2, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_TRAMITE_BAJA = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_BAJA(?,?,?,?,?,?,?,?,?)}";
	/*
	 * VALIDAR_TRAMITE_BAJA (P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE, P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE, P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE, P_ID_CONTRATO_SESSION IN
	 * TRAMITE_TRAFICO.ID_CONTRATO%TYPE, P_REVISADO IN OUT VARCHAR2, P_INFORMACION OUT NVARCHAR2, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_TRAMITE_DUPLICADO = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_DUPLICADO(?,?,?,?,?,?,?,?,?)}";
	/*
	 * VALIDAR_TRAMITE_BTV (P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE, P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE, P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE, P_ID_CONTRATO_SESSION IN
	 * TRAMITE_TRAFICO.ID_CONTRATO%TYPE, P_REVISADO IN OUT VARCHAR2, P_INFORMACION OUT NVARCHAR2, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_TRAMITE_BTV = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_BTV(?,?,?,?,?,?,?,?,?)}";

	@Override
	public ValidacionTramite validarTramiteMatriculacion(TramiteTrafMatrVO tramite) {
		ValidacionTramite validado = new ValidacionTramite();
		CallableStatement st = null;
		try {
			st = prepareCall(VALIDAR_TRAMITE_MATRICULACION);
			st.setBigDecimal(1, tramite.getNumExpediente());
			st.setBigDecimal(2, tramite.getEstado());
			st.setDate(3, new Date(tramite.getFechaUltModif().getTime()));
			st.setLong(4, tramite.getUsuario().getIdUsuario());
			st.setLong(5, tramite.getContrato().getIdContrato());
			st.setString(6, "SI");
			// st.setString(7, "informacion");
			// st.setString(8, "code");
			// st.setString(9, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.NUMERIC);
			st.registerOutParameter(3, java.sql.Types.DATE);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.execute();

			validado.setNumExpediente(st.getLong(1));
			validado.setEstado(st.getLong(2));

			String codigoSalidaPQ = st.getString(8);
			String sqlMensaje = st.getString(9);
			if ((StringUtils.isNotBlank(codigoSalidaPQ) && codigoSalidaPQ.equals("0"))
					|| (StringUtils.isNotBlank(sqlMensaje) && sqlMensaje.equals("Correcto"))) {
				String colegiado = tramite.getNumColegiado();
				if ("SI".equalsIgnoreCase(tramite.getVehiculo().getVehiUsado()) && utiles.esUsuarioMatw(colegiado)) {
					validado.setEstado(new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
				}
				/*else if("SI".equalsIgnoreCase(tramite.getVehiculo().getVehiUsado()) && tramite.getVehiculo() != null && 
						(TipoTarjetaITV.B.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.BL.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv()) 
						|| TipoTarjetaITV.BR.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.BT.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						||TipoTarjetaITV.D.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.DL.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv()) 
						|| TipoTarjetaITV.DR.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.DT.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv()))) {
					
					validado.setEstado(new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
				}*/
				if (TipoTarjetaITV.A.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.AT.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.AR.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())
						|| TipoTarjetaITV.AL.getValorEnum().equalsIgnoreCase(tramite.getVehiculo().getIdTipoTarjetaItv())) {
					Long estadoNuevo = null;
					if (utiles.esUsuarioMatw(colegiado)) {
						estadoNuevo = new Long(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum());
					} else {
						estadoNuevo = new Long(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
					}
					validado.setEstado(estadoNuevo);
				}
			}

			validado.setFechaUltModif(st.getDate(3));
			validado.setIdUsuario(st.getLong(4));
			validado.setIdContrato(st.getLong(5));
			validado.setRevisado(st.getString(6));
			validado.setInformacion(st.getString(7));
			validado.setCode(st.getString(8));
			validado.setSqlerrm(st.getString(9));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}finally {
			try {
				if(st != null) {
					st.close();
				}
			} catch (SQLException e) {
				log.error("La conexión no ha podido cerrarse: " + e);
			}
		}
		return validado;
	}

	@Override
	public ValidacionTramite validarTramiteCTIT(TramiteTrafTranVO trafTranVO) {
		ValidacionTramite validado = new ValidacionTramite();
		CallableStatement st = null;
		try {
			st = prepareCall(VALIDAR_TRAMITE_CTIT);
			st.setBigDecimal(1, trafTranVO.getNumExpediente());
			st.setBigDecimal(2, trafTranVO.getEstado());
			st.setDate(3, new Date(trafTranVO.getFechaUltModif().getTime()));
			st.setLong(4, trafTranVO.getUsuario().getIdUsuario());
			st.setLong(5, trafTranVO.getContrato().getIdContrato());
			st.setString(6, "SI");
			// st.setString(7, "informacion");
			// st.setString(8, "code");
			// st.setString(9, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.NUMERIC);
			st.registerOutParameter(3, java.sql.Types.DATE);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.execute();

			validado.setNumExpediente(st.getLong(1));
			validado.setEstado(st.getLong(2));
			validado.setFechaUltModif(st.getDate(3));
			validado.setIdUsuario(st.getLong(4));
			validado.setIdContrato(st.getLong(5));
			validado.setRevisado(st.getString(6));
			validado.setInformacion(st.getString(7));
			validado.setCode(st.getString(8));
			validado.setSqlerrm(st.getString(9));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}finally {
			try {
				if(st != null) {
					st.close();
				}
			} catch (SQLException e) {
				log.error("La conexión no ha podido cerrarse: " + e);
			}
		}
		return validado;
	}

	@Override
	public ValidacionTramite validarTramiteBaja(TramiteTrafBajaVO tramite) {
		ValidacionTramite validado = new ValidacionTramite();
		try {
			CallableStatement st = prepareCall(VALIDAR_TRAMITE_BAJA);
			st.setBigDecimal(1, tramite.getNumExpediente());
			st.setBigDecimal(2, tramite.getEstado());
			st.setDate(3, new Date(tramite.getFechaUltModif().getTime()));
			st.setLong(4, tramite.getUsuario().getIdUsuario());
			st.setLong(5, tramite.getContrato().getIdContrato());
			// st.setString(6, "SI");
			// st.setString(7, "informacion");
			// st.setString(8, "code");
			// st.setString(9, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.NUMERIC);
			st.registerOutParameter(3, java.sql.Types.DATE);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.execute();

			validado.setNumExpediente(st.getLong(1));
			validado.setEstado(st.getLong(2));
			validado.setFechaUltModif(st.getDate(3));
			validado.setIdUsuario(st.getLong(4));
			validado.setIdContrato(st.getLong(5));
			validado.setRevisado(st.getString(6));
			validado.setInformacion(st.getString(7));
			validado.setCode(st.getString(8));
			validado.setSqlerrm(st.getString(9));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}
		return validado;
	}

	@Override
	public ValidacionTramite validarTramiteDuplicado(TramiteTrafDuplicadoVO tramite) {
		ValidacionTramite validado = new ValidacionTramite();
		CallableStatement st = null;
		try {
			st = prepareCall(VALIDAR_TRAMITE_DUPLICADO);
			st.setBigDecimal(1, tramite.getNumExpediente());
			st.setBigDecimal(2, tramite.getEstado());
			st.setDate(3, new Date(tramite.getFechaUltModif().getTime()));
			st.setLong(4, tramite.getUsuario().getIdUsuario());
			st.setLong(5, tramite.getContrato().getIdContrato());
			st.setString(6, "SI");
			// st.setString(7, "informacion");
			// st.setString(8, "code");
			// st.setString(9, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.NUMERIC);
			st.registerOutParameter(3, java.sql.Types.DATE);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.execute();

			validado.setNumExpediente(st.getLong(1));
			validado.setEstado(st.getLong(2));
			validado.setFechaUltModif(st.getDate(3));
			validado.setIdUsuario(st.getLong(4));
			validado.setIdContrato(st.getLong(5));
			validado.setRevisado(st.getString(6));
			validado.setInformacion(st.getString(7));
			validado.setCode(st.getString(8));
			validado.setSqlerrm(st.getString(9));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}finally {
			try {
				if(st != null) {
					st.close();
				}
			} catch (SQLException e) {
				log.error("La conexión no ha podido cerrarse: " + e);
			}
		}
		return validado;
	}

	@Override
	public ValidacionTramite validarTramiteTelematicamenteBaja(TramiteTrafBajaVO tramite) {
		ValidacionTramite validado = new ValidacionTramite();
		try {
			CallableStatement st = prepareCall(VALIDAR_TRAMITE_BTV);
			st.setBigDecimal(1, tramite.getNumExpediente());
			st.setBigDecimal(2, tramite.getEstado());
			st.setDate(3, new Date(tramite.getFechaUltModif().getTime()));
			st.setLong(4, tramite.getUsuario().getIdUsuario());
			st.setLong(5, tramite.getContrato().getIdContrato());
			st.setString(6, "SI");
			// st.setString(7, "informacion");
			// st.setString(8, "code");
			// st.setString(9, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.NUMERIC);
			st.registerOutParameter(3, java.sql.Types.DATE);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.execute();

			validado.setNumExpediente(st.getLong(1));
			validado.setEstado(st.getLong(2));
			String codigoSalidaPQ = st.getString(8);
			String sqlMensaje = st.getString(9);
			if ((StringUtils.isNotBlank(codigoSalidaPQ) && codigoSalidaPQ.equals("0"))
					|| (StringUtils.isNotBlank(sqlMensaje) && sqlMensaje.equals("Correcto"))) {
				if(MotivoBaja.TempV.getValorEnum().equalsIgnoreCase(tramite.getMotivoBaja()) || MotivoBaja.TempR.getValorEnum().equalsIgnoreCase(tramite.getMotivoBaja())) {
					validado.setEstado(new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
				}
			}
			validado.setFechaUltModif(st.getDate(3));
			validado.setIdUsuario(st.getLong(4));
			validado.setIdContrato(st.getLong(5));
			validado.setRevisado(st.getString(6));
			validado.setInformacion(st.getString(7));
			validado.setCode(st.getString(8));
			validado.setSqlerrm(st.getString(9));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}
		return validado;
	}
}