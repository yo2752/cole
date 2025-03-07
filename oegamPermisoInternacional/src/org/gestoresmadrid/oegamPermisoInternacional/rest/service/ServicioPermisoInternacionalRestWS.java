package org.gestoresmadrid.oegamPermisoInternacional.rest.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;

public interface ServicioPermisoInternacionalRestWS extends Serializable {

	ResultadoIntergaBean enviarRest(Long idPermiso, Long idUsuario);

	ResultadoIntergaBean consultarRest();

	ResultadoIntergaBean subidaDocumentacionEscaneada(PermisoInternacionalVO permiso, File fichero, String numReferencia);

	ResultadoIntergaBean subidaDocumentacionDeclaracionColegio(PermisoInternacionalVO permiso, File fichero);

	ResultadoIntergaBean subidaDocumentacionDeclaracionGestor(PermisoInternacionalVO permiso, File fichero);

	String enviarDeclaracionResponsabilidad(PermisoInternacionalVO permiso, String estadoDeclaracion, Long idUsuario);}
