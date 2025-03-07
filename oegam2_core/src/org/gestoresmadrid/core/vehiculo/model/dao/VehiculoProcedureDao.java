package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

public interface VehiculoProcedureDao extends Serializable {

	Date calculoItv(VehiculoVO vehiculo, Date fechaPresentacion, String tipoTramite);
}
