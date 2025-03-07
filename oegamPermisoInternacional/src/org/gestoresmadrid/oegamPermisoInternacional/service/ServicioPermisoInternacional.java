package org.gestoresmadrid.oegamPermisoInternacional.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public interface ServicioPermisoInternacional extends Serializable {

	ResultadoPermInterBean guardar(PermisoInternacionalDto permisoInternacional, BigDecimal idUsuario, boolean cambioDomicilio);

	ResultadoPermInterBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario);

	ResultadoPermInterBean validar(Long idPermisoIntern, Long idUsuario);

	PermisoInternacionalDto getPermisoInternacional(Long idPermiso);

	ResultadoPermInterBean getTitular(String nif, String numColegiado);

	ResultadoPermInterBean eliminar(Long idPermisoIntern, Long idUsuario);

	ResultadoPermInterBean cambiarEstado(Long idPermisoIntern, String estadoNuevo, Long idUsuario, Boolean accionesAsociadas);

	ResultadoPermInterBean tramitar(Long idPermisoIntern, Long idUsuario);

	ResultadoPermInterBean imprimir(Long idPermisoIntern, Long idUsuario);

	ResultadoPermInterBean subirDocAportada(Long idPermiso, String numReferencia, File fichero, String ficheroFileName, Long idUsuario);

	ResultadoPermInterBean descargarDocAportada(Long idPermiso);

	ResultadoPermInterBean getPermisoInternPorNumExpediente(BigDecimal numExpediente);

	void finalizarConError(Long idPermiso, Long idUsuario, String respuesta);

	void generarDocDocumentacion(Long idPermiso, String estadoNuevo);

	ResultadoPermInterBean eliminarMandato(BigDecimal numExpediente);

	ResultadoPermInterBean firmarDeclaracion(PermisoInternacionalDto permiso, Long idUsuario);

	ResultadoPermInterBean firmada(BigDecimal numExpediente, Long idUsuario);

	ResultadoPermInterBean errorFirma(BigDecimal numExpediente, Long idUsuario);

	ResultadoPermInterBean enviarDeclaracion(Long idPermisoIntern, Long idUsuario);

	void enviarMailJustificanteImpresionPermisoInter(ContratoVO contrato, FicheroBean ficheroBean, String jefatura, String nombreFichero, String numExpediente);

	ResultadoPermInterBean descargarMandato(BigDecimal numExpediente, Long idUsuario);
}