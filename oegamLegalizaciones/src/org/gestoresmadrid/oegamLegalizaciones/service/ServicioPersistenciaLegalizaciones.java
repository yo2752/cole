package org.gestoresmadrid.oegamLegalizaciones.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;

public interface ServicioPersistenciaLegalizaciones extends Serializable {

	public LegalizacionCitaVO obtenerLegalizacionPorId(int idPeticion);

	public LegalizacionCitaVO guardarOActualizar(LegalizacionCitaVO legalizacionCitaVO);

	public boolean esPosiblePeticion(String numColegiado);

	public List<LegalizacionCitaVO> listadoDiario(String numColegiado, Date fechaLegalizacion);
}
