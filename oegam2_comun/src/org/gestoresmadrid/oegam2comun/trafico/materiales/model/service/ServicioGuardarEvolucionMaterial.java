package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;

import escrituras.beans.ResultBean;

public interface ServicioGuardarEvolucionMaterial extends Serializable {
	ResultBean guardarEvolucionMaterial(MaterialVO materialVO, EstadoMaterial estado);
}
