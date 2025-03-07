package org.gestoresmadrid.core.trafico.empresa.telematica.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.vo.EmpresaTelematicaVO;

public interface EmpresaTelematicaDao extends Serializable, GenericDao<EmpresaTelematicaVO>{
	
	List<EmpresaTelematicaVO> getListaEmpresas(String nombreEmpresa, String cifEmpresa, String codPostal,
			String numColegiado, Long idContrato, String idMunicipio, String idProvincia);
	EmpresaTelematicaVO getEmpresa(String id);
	List<EmpresaTelematicaVO> getListaEmpresasContrato(Long idContrato);

}
