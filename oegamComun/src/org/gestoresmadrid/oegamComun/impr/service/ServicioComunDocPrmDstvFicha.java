package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;

public interface ServicioComunDocPrmDstvFicha extends Serializable {

	ResultadoDocImprBean generarDocImpr(Long idContrato, Date fechaImpr, String tipoImpr, String jefatura,
			Long idUsuario, String tipoTramite, String referenciaDocumento);

}