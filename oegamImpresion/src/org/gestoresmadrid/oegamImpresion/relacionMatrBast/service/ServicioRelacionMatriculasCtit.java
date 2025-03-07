package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioRelacionMatriculasCtit extends Serializable {

	ResultadoImpresionBean imprimirRelacionMatricula(List<BigDecimal> listaExpediente);
}
