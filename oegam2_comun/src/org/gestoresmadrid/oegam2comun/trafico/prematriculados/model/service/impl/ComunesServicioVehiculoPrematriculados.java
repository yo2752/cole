package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl;

import java.io.UnsupportedEncodingException;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import hibernate.entities.general.Colegiado;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.beans.schemas.generated.eitv.generated.ObjectFactory;
import trafico.beans.schemas.generated.eitv.generated.TipoTextoLegal;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import trafico.utiles.XmlAFirmar;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ComunesServicioVehiculoPrematriculados {

	private static ILoggerOegam log = LoggerOegam.getLogger(ComunesServicioVehiculoPrematriculados.class);
	private static final String UTF_8 = "UTF-8";

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ConsultaTarjeta anhadirFirmasHsm(ConsultaTarjeta consultaTarjeta,String numColegiado) throws UnsupportedEncodingException {

		ResultBean resultFirmasBean=new ResultBean();

		/**
		 * Firma del XML por el Colegiado
		 */
		String xml = new XMLConsultaTarjetaEITVFactory().toXML(consultaTarjeta);
		ResultBean resultFirmaColegiadoHsm = (ResultBean)XmlAFirmar.realizarFirmaDatosFirmadosHsm(xml, utilesColegiado.getAlias(numColegiado));
		String xmlFirmadoColegiado = resultFirmaColegiadoHsm.getMensaje(); 
		log.debug("XML firmado por el Colegiado:" + xmlFirmadoColegiado);

		if(!resultFirmaColegiadoHsm.getError()) {
			/**
			 * Se añade la firma del gestor al Registro de Entrada
			 */
			consultaTarjeta.setFirmaGestor(xmlFirmadoColegiado.getBytes(UTF_8));
			/**
			 * Firma del XML por el Colegio
			 */
		} else{
			resultFirmasBean.setError(true);
		}

		return consultaTarjeta;
	}

	public ConsultaTarjeta getObjetoRaizXml(String numColegiado, String nive, String bastidor) throws Throwable {

		ConsultaTarjeta consultaTarjetaEitv=null;
		ObjectFactory objFactory = new ObjectFactory();

		Colegiado colegiadoCompleto = utilesColegiado.getColegiado(numColegiado);
		//-----------------ELEMENTO RAIZ----------------------
		consultaTarjetaEitv= (ConsultaTarjeta) objFactory.createConsultaTarjeta();

		consultaTarjetaEitv.setDatosFirmados(objFactory.createDatosFirmados());
		consultaTarjetaEitv.setFirmaGestor(new byte[0]);

		if (colegiadoCompleto==null){
			log.error("No se ha podido recuperar el colegiado");
			consultaTarjetaEitv.getDatosFirmados().setAGENCYDOI("");
			consultaTarjetaEitv.getDatosFirmados().setAGENTDOI("");
		} else {
			consultaTarjetaEitv.getDatosFirmados().setAGENCYDOI(colegiadoCompleto.getUsuario().getNif());
			consultaTarjetaEitv.getDatosFirmados().setAGENTDOI(colegiadoCompleto.getUsuario().getNif());
		}

		consultaTarjetaEitv.getDatosFirmados().setNIVE(nive);
		consultaTarjetaEitv.getDatosFirmados().setVIN(bastidor);
		consultaTarjetaEitv.getDatosFirmados().setTEXTOLEGAL(TipoTextoLegal.TEXTO_LEGAL);
		return consultaTarjetaEitv;
	}

}