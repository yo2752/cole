package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;

public interface ServicioImportacionCet extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero);

	ResultadoValidacionImporBean validarAutoliquidacion(AutoliquidacionBean autoliquidacion);

	ResultadoImportacionBean autoliquidacion(AutoliquidacionBean autoliquidacion, ContratoVO contratoBBDD);
}
