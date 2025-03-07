package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.gestoresmadrid.core.general.model.dao.CreditoProcedureDao;
import org.gestoresmadrid.core.general.model.procedure.bean.ValidacionCredito;
import org.gestoresmadrid.core.model.dao.impl.GenericProcedureDaoImplHibernate;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class CreditoProcedureDaoImpl extends GenericProcedureDaoImplHibernate implements CreditoProcedureDao {

	private static final long serialVersionUID = -132251964146374666L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(CreditoProcedureDaoImpl.class);

	/*
	 * VALIDAR_CREDITOS (P_ID_CONTRATO IN CREDITOS.ID_CONTRATO%TYPE, P_TIPO_TRAMITE IN TIPO_TRAMITE.TIPO_TRAMITE%TYPE, NUMERO IN NUMBER, P_CREDITOS OUT NUMBER, P_CREDITOS_DISPONIBLES OUT NUMBER, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2)
	 */
	private static final String VALIDAR_CREDITOS = "{call PQ_CREDITOS.VALIDAR_CREDITOS(?,?,?,?,?,?,?)}";

	@Override
	public ValidacionCredito validarCreditos(BigDecimal idContrato, String tipoTramite, BigDecimal numero) {
		ValidacionCredito validado = new ValidacionCredito();
		try {
			CallableStatement st = prepareCall(VALIDAR_CREDITOS);
			st.setBigDecimal(1, idContrato);
			st.setString(2, tipoTramite);
			st.setBigDecimal(3, numero);
			// st.setString(4, "creditos");
			// st.setString(5, "creditosDisponibles");
			// st.setString(6, "code");
			// st.setString(7, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.NUMERIC);
			st.registerOutParameter(2, java.sql.Types.VARCHAR);
			st.registerOutParameter(3, java.sql.Types.NUMERIC);
			st.registerOutParameter(4, java.sql.Types.NUMERIC);
			st.registerOutParameter(5, java.sql.Types.NUMERIC);
			st.registerOutParameter(6, java.sql.Types.VARCHAR);
			st.registerOutParameter(7, java.sql.Types.VARCHAR);
			st.execute();

			validado.setIdContrato(st.getBigDecimal(1));
			validado.setTipoTramite(st.getString(2));
			validado.setNumero(st.getBigDecimal(3));
			validado.setCreditos(st.getBigDecimal(4));
			validado.setCreditosDisponibles(st.getBigDecimal(5));
			validado.setCode(st.getString(6));
			validado.setSqlerrm(st.getString(7));
		} catch (SQLException e) {
			log.error(e);
			validado = null;
		}
		return validado;
	}
}
