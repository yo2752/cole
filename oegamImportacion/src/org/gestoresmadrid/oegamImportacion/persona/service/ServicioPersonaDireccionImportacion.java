package org.gestoresmadrid.oegamImportacion.persona.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;

public interface ServicioPersonaDireccionImportacion extends Serializable {

	public static final String PERSONADIRECCION = "PERSONADIRECCION";

	PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite);

	ResultadoBean buscarPersonaDireccionVO(String numColegiado, String nif);

	PersonaDireccionVO buscarPersonaDireccionActivaVO(String numColegiado, String nif);

	void guardarActualizar(PersonaDireccionVO personaDireccionVO);

}