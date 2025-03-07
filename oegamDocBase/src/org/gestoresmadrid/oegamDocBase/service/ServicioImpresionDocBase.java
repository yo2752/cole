package org.gestoresmadrid.oegamDocBase.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;

public interface ServicioImpresionDocBase extends Serializable{

	public static final String NUM_MAXIMO_EXPEDIENTES_PAGINA_PDF_DOC_BASE = "gdoc.numexpedientes.pagina.pdf.docbase";
	public static final int NUM_MAXIMO_EXPEDIENTES_PAGINA_PDF_DOC_BASE_DEFECTO = 20; // para el iReport
	
	ResultadoDocBaseBean generarSolicitudImpresionDocBase(List<TramiteTraficoVO> listaExpedientes,
			TipoCarpeta tipoCarpeta, BigDecimal idUsuario, Long idContrato, Date fechaPresentacion);

	Boolean existeTramiteDocBasePdtImprimir(BigDecimal numExpediente);

	ResultadoDocBaseBean obtenerDatosIdImpresionDocBase(Long idImpresion);

	ResultadoDocBaseBean imprimirDocBase(DocumentoBaseVO docBase);

	ResultadoDocBaseBean borrarDatosImpresionDocBase(Long idImpresion, Long idUsuario);

	String obtenerSubtipoDocumentoFromTipoCarpeta(String tipoCarpeta);

}
