package org.gestoresmadrid.oegamInterga.rest.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamInterga.restWS.request.FileRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.SendFilesRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.UpdateRequest;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;

public interface ServicioIntergaRestWS extends Serializable {

	public String PDF_ORIGINAL = "pdf";
	public String PDF_REDIMENSIONADO = "epdf";
	public String PDF_ESCANEADO = "spdf";
	public String PDF_JUSTIFICANTE_CAMBIO_DOMICILIO = "jpdf";
	public String PDF_DECLARACION_COLEGIO = "cpdf";
	public String PDF_DECLARACION_GESTOR = "gpdf";
	public String PDF_SOLICITUD = "solicitud";
	public String PDF_MANDATO = "mpdf";
	public String PDF_LIBRE_DISPOSICION = "apdf";

	public String TIPO_PERMISO_INTERNACIONAL = "PERMISO INTERNACIONAL";

	ResultadoIntergaBean enviar(SendFilesRequest request);

	ResultadoIntergaBean consultar(FileRequest request, BigDecimal numExpediente, String tipoPdf);

	ResultadoIntergaBean subidaDocumentacion(UpdateRequest request, String tipoPdf);
}
