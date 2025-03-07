package org.gestoresmadrid.oegam2comun.codigoIAE.model.service;

import java.io.Serializable;
import java.util.List;

import trafico.beans.IAEBean;

public interface ServicioCodigoIae extends Serializable {

	public List<IAEBean> obtenerCodigosIAE();
}
