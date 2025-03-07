package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;

public interface ServicioDuplicadoPermCond extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";

	ResultadoDuplPermCondBean getDuplPermCondPorNumExpediente(BigDecimal numExpediente);

	ResultadoDuplPermCondBean guardar(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto, BigDecimal idUsuario);

	DuplicadoPermisoConducirDto getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond);

	ResultadoDuplPermCondBean validar(Long idDuplicadoPermisoCond, Long idUsuario);

	ResultadoDuplPermCondBean tramitar(Long idDuplicadoPermisoCond, Long idUsuario);

	ResultadoDuplPermCondBean cambiarEstado(Long idDuplPermCond, String estadoNuevo, Long idUsuario, Boolean accionesAsociadas);

	ResultadoDuplPermCondBean eliminar(Long idDuplPermCond, Long idUsuario);

	ResultadoDuplPermCondBean getTitular(String nif, String numColegiado);

	void finalizarConError(Long idDuplPermCond, Long idUsuario, String respuesta);

	ResultadoDuplPermCondBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario);

	ResultadoDuplPermCondBean eliminarMandato(BigDecimal numExpediente);

	ResultadoDuplPermCondBean descargarMandato(BigDecimal numExpediente, Long idUsuario);

	ResultadoDuplPermCondBean descargarDocAportada(String nombreDocumento, BigDecimal numExpediente);

	ResultadoDuplPermCondBean subirDocAportada(Long idDuplicadoPermisoConducir, String tipoDocumento, File fichero, String ficheroFileName, Long idUsuario);

	ResultadoDuplPermCondBean imprimir(Long idDuplicadoPermisoConducir, Long idUsuario);

	ResultadoDuplPermCondBean firmarDeclYSolicitud(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto, Long idUsuario);

	ResultadoDuplPermCondBean firmadaSolicitud(BigDecimal numExpediente, Long idUsuario);

	ResultadoDuplPermCondBean firmadaDeclaracion(BigDecimal numExpediente, Long idUsuario);

	ResultadoDuplPermCondBean errorFirmaDocumentos(BigDecimal numExpediente, Long idUsuario);

	ResultadoDuplPermCondBean descargarFichero(String nombreDocumento, Long idUsuario);

	ResultadoDuplPermCondBean eliminarDocAportada(String nombreDocumento, String tipoDocumento, BigDecimal numExpediente);

	ResultadoDuplPermCondBean enviarDocumentacion(Long idDuplicadoPermisoCond, Long idUsuario);
}
