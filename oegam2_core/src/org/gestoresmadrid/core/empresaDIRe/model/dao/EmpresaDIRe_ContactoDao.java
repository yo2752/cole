package org.gestoresmadrid.core.empresaDIRe.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_ContactoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EmpresaDIRe_ContactoDao extends Serializable, GenericDao<EmpresaDIRe_ContactoVO> {

	int Guardar_Contacto(EmpresaDIRe_ContactoVO objeto);

}