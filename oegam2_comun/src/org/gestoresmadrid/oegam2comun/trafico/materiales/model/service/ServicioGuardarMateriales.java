package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialDto;

import escrituras.beans.ResultBean;

public interface ServicioGuardarMateriales extends Serializable {
	ResultBean salvarMaterial(MaterialDto materialDto); 
	ResultBean salvarMaterial(MaterialVO materialvo);
}
