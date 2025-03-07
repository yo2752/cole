package org.gestoresmadrid.core.personas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;

public interface PersonaDireccionDao extends GenericDao<PersonaDireccionVO>, Serializable {
	
	public String TIPO_TRAMITE_MANTENIMIENTO = "MTO";

	List<PersonaDireccionVO> getPersonaDireccionPorNif(String numColegiado, String nif, Long idDireccion, boolean fechaFinNull);

	PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite);

	PersonaDireccionVO buscarPersonaDireccionActiva(String numColegiado, String nif);
}
