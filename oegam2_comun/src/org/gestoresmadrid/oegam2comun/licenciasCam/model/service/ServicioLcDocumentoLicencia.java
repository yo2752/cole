package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDocumentoLicenciaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;

import utilidades.web.OegamExcepcion;

public interface ServicioLcDocumentoLicencia extends Serializable {

	public static final String ID_DOCUMENTO_LICENCIA = "idDocumentoLicencia";

	LcDocumentoLicenciaVO getLcDocumentoLicencia(Long idDocumentoLicencia);

	List<LcDocumentoLicenciaVO> getLcDocumentoLicenciaPorNumExp(BigDecimal numExpediente);

	ResultadoLicenciasBean guardar(BigDecimal numExpediente, Long idContrato, String tipoDocumento);

	ResultadoLicenciasBean eliminar(String nombreFichero);

	ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente);

	File guardarFichero(File fileUpload, BigDecimal numExpediente, String extension, String tipoDocumento, Long idFichero) throws OegamExcepcion, IOException;

	int obtenerTipoDocumento(String nombreFichero);

	String obtenerDescripcionTipoDocumento(String nombreFichero);
}
