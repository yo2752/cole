package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;

public interface ServicioSolicitudesMasivasDstv extends Serializable {

	ResultadoDistintivoDgtBean tratarSolicitudesMasivas(File fichero, String nombrFichero, String tipoSolicitud ,Long idContrato, BigDecimal idUsuario);

}