package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;

public interface ServicioImpresionAtex5 extends Serializable{

	public static final String RUTA_PDF_VEHICULO_ATEX5 = "ruta.plantilla.vehiculo.atex5";
	public static final String RUTA_PDF_PERSONA_ATEX5 = "ruta.plantilla.persona.atex5";
	
	ResultadoAtex5Bean imprimirPdfVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5, String ficheroXml);

	ResultadoAtex5Bean imprimirPdfPersonaAtex5(ConsultaPersonaAtex5VO consultaPersonaAtex5VO, File ficheroXml);

}
