package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.util.List;

public interface MatriculaVehiculoDaoInt extends Serializable {

	List<Object[]> getMatriculaExpedienteDao(Long numExpediente);

}
