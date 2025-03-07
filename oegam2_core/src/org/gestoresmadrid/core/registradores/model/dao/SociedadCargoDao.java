package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.SociedadCargoVO;

public interface SociedadCargoDao extends GenericDao<SociedadCargoVO>, Serializable {

	SociedadCargoVO getSociedadCargo(String cif, String numColegiado, String nifCargo, String codigoCargo);
}
