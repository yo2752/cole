package org.gestoresmadrid.oegam2comun.mandato.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionMandato extends Serializable {

	ResultBean guardarEvolucionMandato(Long idMandato, String codigoMandato, BigDecimal idUsuario, TipoActualizacion tipoActualizacion, String descripcion);
}
