package org.gestoresmadrid.oegam2comun.presentacion05.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.presentacion05.view.bean.ResultadoPresentacion05Bean;

public interface ServicioPresentacion05 extends Serializable {

	ResultadoPresentacion05Bean presentacionJnlp(BigDecimal numExpediente);
}
