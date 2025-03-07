package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.procedure.bean.ValidacionCredito;

public interface CreditoProcedureDao extends Serializable {

	ValidacionCredito validarCreditos(BigDecimal idContrato, String tipoTramite, BigDecimal numero);
}
