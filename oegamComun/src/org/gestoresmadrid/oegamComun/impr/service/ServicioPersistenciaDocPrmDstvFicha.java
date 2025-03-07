package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;



public interface ServicioPersistenciaDocPrmDstvFicha extends Serializable{

	Long guardar(DocPermDistItvVO docImpr);

	void guardarDocId(DocPermDistItvVO docImpr, Long idUsuario);


}