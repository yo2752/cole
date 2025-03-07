package org.gestoresmadrid.oegamComun.datosSensibles.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

public interface ServicioComunDatosSensibles extends Serializable {

	public static final String PROPERTY_KEY_DATOSSENSIBLES_GRUPO = "importacion.datossensibles.usuario.grupo";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO = "importacion.datossensibles.usuario.idcontrato";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO = "importacion.datossensibles.usuario.numcolegiado";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO = "importacion.datossensibles.usuario.idusuario";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE = "importacion.datossensibles.usuario.nombre";
	public static final String PORPERTY_GRUPO_VOVN = "importacion.datossensibles.usuario.grupo";

	boolean existeDatosSensible(TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario);
}
