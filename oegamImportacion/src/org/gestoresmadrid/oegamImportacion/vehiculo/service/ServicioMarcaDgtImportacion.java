package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;

public interface ServicioMarcaDgtImportacion extends Serializable {

	MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw);

	String getCodMarcaPorMarcaSinEditar(String marcaSinEditar, Boolean versionMatw);
}
