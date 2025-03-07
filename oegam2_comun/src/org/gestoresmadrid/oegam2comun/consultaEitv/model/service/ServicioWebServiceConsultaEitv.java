package org.gestoresmadrid.oegam2comun.consultaEitv.model.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.gescogroup.blackbox.EitvQueryWSResponse;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.dto.RespuestaConsultaEitv;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;

import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import escrituras.beans.ResultBean;

public interface ServicioWebServiceConsultaEitv {
	
	public final String NAME_CONTEXT = "trafico.beans.schemas.generated.eitv.generated";
	public final String NAME_CONTEXT_VEHICULO = "trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos";
	public final String XML_ENCODING = "UTF-8";
	public final String TIMEOUT_PROP_EITV = "webservice.eitv.timeOut";
	public final String WEBSERVICE_CONSULTA_TARJETA_EITV = "webservice.eitv.nuevo.url";
	public final int TIMEOUT_MATEGE = 120000;
	public final String UTF_8 = "UTF-8";
	public final String TEXTO_CONSULTAEITV_LOG = "WS_CONSULTA_TARJETA_EITV:";
	public final String MATEGE_NODO_FIRMAR_ID = "D0";
	public final String MENSAJE_RESPUESTA_OK ="OK";

RespuestaConsultaEitv llamadaWS(ConsultaTarjeta consultaTarjeta, String xml, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin);
	
	RespuestaConsultaEitv gestionRespuestaWS(EitvQueryWSResponse respuestaWS, ConsultaTarjeta consultaTarjeta, BigDecimal idUsuario, TramiteTraficoVO tramiteTraficoVO, boolean admin);

	ResultBean firmarConsultaTarjetaEitv(ConsultaTarjeta consultaTarjeta, TramiteTraficoVO tramiteTraficoVO);
	
	String firmarConsultaTarjetaEitvPreMatriculados(ConsultaTarjeta consultaTarjeta, VehiculoPrematriculadoDto vehiculoPrematriculado, ColegiadoVO colegiadoVO);

	String xmlFiletoString(File ficheroAenviar) throws PropertyException, JAXBException;

	Object getBeanToXml(String xml, String nameContext) throws JAXBException;

	RespuestaConsultaEitv llamadaWSPreMatriculados(ConsultaTarjeta consultaTarjetaEitv, String xml, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idUsuario, BigDecimal idContrato);

	String getXmlConsultaTarjeta(ConsultaTarjeta consultaTarjetaEitv)throws JAXBException, UnsupportedEncodingException;
	

	
}
