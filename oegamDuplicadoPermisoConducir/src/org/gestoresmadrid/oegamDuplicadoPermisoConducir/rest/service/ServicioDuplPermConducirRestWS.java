package org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;

public interface ServicioDuplPermConducirRestWS extends Serializable {

	ResultadoIntergaBean enviarRest(Long idDuplicadoPermCond, Long idUsuario);

	ResultadoIntergaBean consultarRest();

	ResultadoIntergaBean subidaDocumentacion(DuplicadoPermisoConducirVO duplicado, File fichero, String tipo);

	ResultadoIntergaBean enviarDeclaracion(DuplicadoPermisoConducirVO duplicado, Long idUsuario);

	ResultadoIntergaBean enviarSolicitud(DuplicadoPermisoConducirVO duplicado, Long idUsuario);

	ResultadoIntergaBean enviarDeclaracionTitular(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario);

	ResultadoIntergaBean enviarMandato(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario);

	ResultadoIntergaBean enviarOtroDocumento(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario);

	ResultadoDuplPermCondBean descargarDocAportada(String nombreDocumento, BigDecimal numExpediente);

	ResultadoDuplPermCondBean enviarDocumentos(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idUsuario);
}
