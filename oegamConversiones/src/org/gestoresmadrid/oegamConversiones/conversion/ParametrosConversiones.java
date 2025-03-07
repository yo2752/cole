package org.gestoresmadrid.oegamConversiones.conversion;

import java.io.Serializable;

public interface ParametrosConversiones extends Serializable {

	void inicializarProvincia();

	void inicializarTablaProvincias();

	String getSiglasFromIdProvincia(String idProvincia);

}
