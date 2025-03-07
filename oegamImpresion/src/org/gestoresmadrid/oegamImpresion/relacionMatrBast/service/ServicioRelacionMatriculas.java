package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioRelacionMatriculas extends Serializable {

	ResultadoImpresionBean imprimirRelacionMatrBast(Long idImpresion);
}
