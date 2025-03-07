package org.gestoresmadrid.core.docbase.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DocumentoBaseDao extends GenericDao<DocumentoBaseVO>,Serializable{

	DocumentoBaseVO getDocBase(Long idDocBase, Boolean docBaseCompleto);

	List<String> obtenerDocIdMax(Date fecha);

	List<DocumentoBaseVO> getListaDocBaseWS(Long idContrato, Date fechaDocBase, String docId, String carpeta);

	DocumentoBaseVO docBasePorColegiadoYFechaP(String numColegiado, Date fechaPresentacion);

	DocumentoBaseVO docBasePorIdMacroExpdiente(String idMacroExpediente);

	DocumentoBaseVO docBasePorIdMacroExpdienteYMatricula(String idMacroExpediente, String matricula);


}
