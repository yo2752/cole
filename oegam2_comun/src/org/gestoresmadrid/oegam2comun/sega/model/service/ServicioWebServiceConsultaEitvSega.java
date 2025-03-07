package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoConsultaEitvBean;

import escrituras.beans.ResultBean;

public interface ServicioWebServiceConsultaEitvSega extends Serializable{

	public final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.sega.eitv.view.xml";
	public final String NAME_CONTEXT_VEHICULO = "trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos";
	public final String XML_ENCODING = "UTF-8";
	public final String TIMEOUT_PROP_EITV = "webservice.eitv.timeOut";
	public final String WEBSERVICE_CONSULTA_TARJETA_EITV = "webService.url.sega.consultaEitv";
	public final int TIMEOUT_MATEGE = 120000;
	public final String UTF_8 = "UTF-8";
	public final String TEXTO_CONSULTAEITV_LOG = "WS_CONSULTA_TARJETA_EITV:";
	public final String MATEGE_NODO_FIRMAR_ID = "D0";
	public final String MENSAJE_RESPUESTA_OK ="OK";
	
	ResultadoConsultaEitvBean generarConsultaEitv(ColaBean solicitud);
	
	Object getBeanToXml(String xml, String nameContext) throws JAXBException;
	
	ResultadoConsultaEitvBean llamadaWS(ConsultaTarjeta consultaTarjeta, String xml, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin);

	ResultadoConsultaEitvBean llamadaWSPreMatriculados(ConsultaTarjeta consultaTarjetaEitv, String xml,VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idUsuario, BigDecimal idContrato);

	String xmlFiletoString(File ficheroAenviar) throws JAXBException;
	
	void cambiarEstadoTramiteProceso(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario);
	
	String getXmlConsultaTarjetaSega(ConsultaTarjeta consultaTarjetaEitvSega)throws JAXBException, UnsupportedEncodingException;

	ResultBean firmarConsultaTarjetaEitvSega(ConsultaTarjeta consultaTarjetaEitvSega,TramiteTraficoVO tramiteTraficoVO);
	
}
