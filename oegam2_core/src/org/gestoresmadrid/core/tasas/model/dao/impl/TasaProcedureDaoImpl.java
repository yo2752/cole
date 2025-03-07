package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.gestoresmadrid.core.model.dao.impl.GenericProcedureDaoImplHibernate;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.dao.TasaProcedureDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.springframework.stereotype.Repository;

@Repository
public class TasaProcedureDaoImpl extends GenericProcedureDaoImplHibernate implements TasaProcedureDao {

	private static final long serialVersionUID = -615412525389008470L;

	/*
	 * PROCEDURE GUARDAR (P_ID_CONTRATO_SESSION IN TASA.ID_CONTRATO%TYPE, P_CODIGO_TASA IN OUT TASA.CODIGO_TASA%TYPE, P_TIPO_TASA IN OUT TASA.TIPO_TASA%TYPE, P_ID_CONTRATO IN OUT TASA.ID_CONTRATO%TYPE, P_PRECIO IN OUT TASA.PRECIO%TYPE, P_FECHA_ALTA IN OUT TASA.FECHA_ALTA%TYPE, P_FECHA_ASIGNACION IN
	 * OUT TASA.FECHA_ASIGNACION%TYPE, P_FECHA_FIN_VIGENCIA IN OUT TASA.FECHA_FIN_VIGENCIA%TYPE, P_ID_USUARIO IN OUT TASA.ID_USUARIO%TYPE, P_NUM_COLEGIADO OUT CONTRATO_COLEGIADO.NUM_COLEGIADO%TYPE, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2);
	 */
	private static final String GUARDAR = "{call PQ_TASAS.GUARDAR(:idContratoSession, :codigoTasa, :tipoTasa, :idContrato, :precio, :fechaAlta, :fechaAsignacion, :fechaFinVigencia, :idUsuario, :numColegiado, :importadoIcogam, :code, :sqlerrm)}";

	@Override
	public String guardar(TasaVO tasa) {
		CallableStatement st = null;
		try {
			st = prepareCall(GUARDAR);

			st.registerOutParameter("idContratoSession", java.sql.Types.NUMERIC);
			st.registerOutParameter("codigoTasa", java.sql.Types.VARCHAR);
			st.registerOutParameter("tipoTasa", java.sql.Types.VARCHAR);
			st.registerOutParameter("idContrato", java.sql.Types.NUMERIC);
			st.registerOutParameter("precio", java.sql.Types.NUMERIC);
			st.registerOutParameter("fechaAlta", java.sql.Types.DATE);
			st.registerOutParameter("fechaAsignacion", java.sql.Types.DATE);
			st.registerOutParameter("fechaFinVigencia", java.sql.Types.DATE);
			st.registerOutParameter("idUsuario", java.sql.Types.NUMERIC);
			st.registerOutParameter("numColegiado", java.sql.Types.VARCHAR);
			st.registerOutParameter("importadoIcogam", java.sql.Types.NUMERIC);
			st.registerOutParameter("code", java.sql.Types.NUMERIC);
			st.registerOutParameter("sqlerrm", java.sql.Types.VARCHAR);

			BigDecimal idContrato = null;
			if (tasa.getContrato() != null && tasa.getContrato().getIdContrato() != null) {
				idContrato = new BigDecimal(tasa.getContrato().getIdContrato());
			}
			BigDecimal idUsuario = null;
			if (tasa.getUsuario() != null && tasa.getUsuario().getIdUsuario() != null) {
				idUsuario = new BigDecimal(tasa.getUsuario().getIdUsuario());
			}
			st.setBigDecimal("idContratoSession", idContrato);
			st.setString("codigoTasa", tasa.getCodigoTasa());
			st.setString("tipoTasa", tasa.getTipoTasa());
			st.setBigDecimal("idContrato", idContrato);
			st.setBigDecimal("precio", tasa.getPrecio());
			st.setDate("fechaAlta", tasa.getFechaAlta() != null ? new java.sql.Date(tasa.getFechaAlta().getTime()) : null);
			st.setDate("fechaAsignacion", null);
			st.setDate("fechaFinVigencia", null);
			st.setBigDecimal("idUsuario", idUsuario);
			st.setString("numColegiado", null);
			st.setBigDecimal("importadoIcogam", tasa.getImportadoIcogam());

			st.execute();

			// Actualizar la tasa
			tasa.setFechaAlta(st.getDate("fechaAlta"));
			tasa.setFechaAsignacion(st.getDate("fechaAsignacion"));
			tasa.setFechaFinVigencia(st.getDate("fechaFinVigencia"));

			BigDecimal code = st.getBigDecimal("code");
			if (code == null || !code.equals(BigDecimal.ZERO)) {
				String respuesta = st.getString("sqlerrm");
				if (respuesta == null || respuesta.isEmpty()) {
					return "";
				} else {
					return respuesta;
				}
			}

		} catch (SQLException e) {
			throw new TransactionalException(e);
		}finally {
			try {
				if(st != null) {
					st.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("La conexión no ha podido cerrarse: " + e);
			}
		}
		return null;
	}
}
