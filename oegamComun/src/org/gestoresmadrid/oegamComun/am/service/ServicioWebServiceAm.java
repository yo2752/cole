package org.gestoresmadrid.oegamComun.am.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioWebServiceAm extends Serializable{

	ResultadoBean validarBaja(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean validarMatw(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean validarCtit(BigDecimal numExpediente, Long idUsuario);

}
