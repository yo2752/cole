package org.gestoresmadrid.core.empresaDIRe.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_AdministradorVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EmpresaDIRe_AdministradorDao extends Serializable, GenericDao<EmpresaDIRe_AdministradorVO>{
	int Guardar_Administrador(EmpresaDIRe_AdministradorVO objeto);
}