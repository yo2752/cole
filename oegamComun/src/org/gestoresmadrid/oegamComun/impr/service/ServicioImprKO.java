package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;

public interface ServicioImprKO extends Serializable{

	ResultadoImprBean generarKoTramite(BigDecimal id, Long idUsuario);

}
