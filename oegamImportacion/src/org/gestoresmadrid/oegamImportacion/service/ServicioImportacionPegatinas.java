package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.PegatinaBean;

public interface ServicioImportacionPegatinas extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero);

	ResultadoImportacionBean generarPdfEtiquetas(ParametrosPegatinaMatriculacion parametrosPegatina, List<PegatinaBean> listaPegatinas);
}
