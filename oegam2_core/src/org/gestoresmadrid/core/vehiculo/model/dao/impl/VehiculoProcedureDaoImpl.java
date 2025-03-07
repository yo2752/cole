package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;

import org.gestoresmadrid.core.model.dao.impl.GenericProcedureDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoProcedureDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class VehiculoProcedureDaoImpl extends GenericProcedureDaoImplHibernate implements VehiculoProcedureDao {

	private static final long serialVersionUID = 5708582180869918263L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(VehiculoProcedureDaoImpl.class);

	/*
	 * CALCULO_ITV(P_ID_CRITERIO_CONSTRUCCION IN VEHICULO.ID_CRITERIO_CONSTRUCCION%TYPE, P_ID_CRITERIO_UTILIZACION IN VEHICULO.ID_CRITERIO_UTILIZACION%TYPE, P_TIPO_TRAMITE IN TRAMITE_TRAFICO.TIPO_TRAMITE%TYPE, P_FECHA_PRESENTACION IN TRAMITE_TRAFICO.FECHA_PRESENTACION%TYPE, P_FECHA_MATRICULACION IN
	 * VEHICULO.FECHA_MATRICULACION%TYPE, P_FECHA_PRIM_MATRI IN VEHICULO.FECHA_PRIM_MATRI%TYPE, P_FECHA_INSPECCION IN VEHICULO.FECHA_INSPECCION%TYPE, P_ID_SERVICIO IN VEHICULO.ID_SERVICIO%TYPE, P_ID_SERVICIO_ANTERIOR IN VEHICULO.ID_SERVICIO_ANTERIOR%TYPE, P_TIPO_VEHICULO IN
	 * VEHICULO.TIPO_VEHICULO%TYPE, P_VEHI_USADO IN VEHICULO.VEHI_USADO%TYPE, P_PESO_MMA IN VEHICULO.PESO_MMA%TYPE, P_FECHA_ITV IN OUT VEHICULO.FECHA_ITV%TYPE, P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2);
	 */

	private static final String CALCULO_ITV = "{call PQ_VEHICULOS.CALCULO_ITV(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	@Override
	public Date calculoItv(VehiculoVO vehiculo, Date fechaPresentacion, String tipoTramite) {
		CallableStatement st = null;
		try {
			st = prepareCall(CALCULO_ITV);
			st.setString(1, vehiculo.getIdCriterioConstruccion());
			st.setString(2, vehiculo.getIdCriterioUtilizacion());
			st.setString(3, tipoTramite);
			if (fechaPresentacion != null) {
				st.setDate(4, new java.sql.Date(fechaPresentacion.getTime()));
			}
			if (vehiculo.getFechaMatriculacion() != null) {
				st.setDate(5, new java.sql.Date(vehiculo.getFechaMatriculacion().getTime()));
			}
			if (vehiculo.getFechaPrimMatri() != null && (!TipoTarjetaITV.B.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.BT.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.BR.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.BL.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.D.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.DT.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.DR.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv())
					&& !TipoTarjetaITV.DL.getValorEnum().equalsIgnoreCase(vehiculo.getIdTipoTarjetaItv()))) {
				st.setDate(6, new java.sql.Date(vehiculo.getFechaPrimMatri().getTime()));
			}
			if (vehiculo.getFechaInspeccion() != null) {
				st.setDate(7, new java.sql.Date(vehiculo.getFechaInspeccion().getTime()));
			}
			st.setString(8, vehiculo.getIdServicio());
			st.setString(9, vehiculo.getIdServicioAnterior());
			st.setString(10, vehiculo.getTipoVehiculo());
			st.setString(11, vehiculo.getVehiUsado());
			st.setString(12, vehiculo.getPesoMma());
			if (vehiculo.getFechaItv() != null) {
				st.setDate(13, new java.sql.Date(vehiculo.getFechaItv().getTime()));
			}
			// st.setString(14, "code");
			// st.setString(15, "sqlerrm");

			st.registerOutParameter(1, java.sql.Types.VARCHAR);
			st.registerOutParameter(2, java.sql.Types.VARCHAR);
			st.registerOutParameter(3, java.sql.Types.VARCHAR);
			st.registerOutParameter(4, java.sql.Types.DATE);
			st.registerOutParameter(5, java.sql.Types.DATE);
			st.registerOutParameter(6, java.sql.Types.DATE);
			st.registerOutParameter(7, java.sql.Types.DATE);
			st.registerOutParameter(8, java.sql.Types.VARCHAR);
			st.registerOutParameter(9, java.sql.Types.VARCHAR);
			st.registerOutParameter(10, java.sql.Types.VARCHAR);
			st.registerOutParameter(11, java.sql.Types.VARCHAR);
			st.registerOutParameter(12, java.sql.Types.VARCHAR);
			st.registerOutParameter(13, java.sql.Types.DATE);
			st.registerOutParameter(14, java.sql.Types.NUMERIC);
			st.registerOutParameter(15, java.sql.Types.VARCHAR);

			st.execute();

			BigDecimal code = st.getBigDecimal(14);
			if (code.equals(BigDecimal.ZERO) && st.getDate(13) != null) {
				return new Date(st.getDate(13).getTime());
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				log.error("La conexión no ha podido cerrarse: " + e);
			}
		}
		return null;
	}
}