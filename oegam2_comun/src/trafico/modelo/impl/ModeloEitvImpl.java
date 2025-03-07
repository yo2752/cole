package trafico.modelo.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl.ComunesServicioVehiculoPrematriculados;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.modelo.interfaz.ModeloEitvInt;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloEitvImpl implements ModeloEitvInt {

	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloEitvImpl.class);

	private ComunesServicioVehiculoPrematriculados comunesServicioVehiculoPrematriculados;

	private GestorDocumentos gestorDocumentos;

	public GestorDocumentos getGuardarDocumentos() {
		if (gestorDocumentos == null) {
			gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
		}
		return gestorDocumentos;
	}

	@Override
	public void generarXmlSolicitudEitv(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato, String nive, String bastidor) throws Throwable {
		ConsultaTarjeta consultaTarjetaEitv = crearConsultaTarjetaEitv(numColegiado, nive, bastidor);
		exportarFicheroEitv(consultaTarjetaEitv, numExpediente);
	}

	/**
	 * 
	 * @param traficoTramiteMatriculacionBean
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private ConsultaTarjeta crearConsultaTarjetaEitv(String numColegiado, String nive, String bastidor) throws Throwable {
		ConsultaTarjeta consultaTarjetaEitv = getComunesServicioVehiculoPrematriculados().getObjetoRaizXml(numColegiado, nive, bastidor);
		consultaTarjetaEitv = getComunesServicioVehiculoPrematriculados().anhadirFirmasHsm(consultaTarjetaEitv, numColegiado);
		return consultaTarjetaEitv;
	}

	/**
	 * 
	 * @param consultaTarjetaEitv
	 * @param numExpediente
	 * @throws Throwable 
	 */
	@Override
	public void exportarFicheroEitv(ConsultaTarjeta consultaTarjetaEitv, BigDecimal numExpediente) throws Throwable {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.EITV);
		ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_EITV+numExpediente);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);

		try {
			getGuardarDocumentos().crearFicheroXml(ficheroBean, XMLConsultaTarjetaEITVFactory.NAME_CONTEXT, consultaTarjetaEitv, null, null);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento XML");
		}
	}

	public ComunesServicioVehiculoPrematriculados getComunesServicioVehiculoPrematriculados() {
		if (comunesServicioVehiculoPrematriculados==null){
			comunesServicioVehiculoPrematriculados = new ComunesServicioVehiculoPrematriculados();
		}
		return comunesServicioVehiculoPrematriculados;
	}

	public void setComunesServicioVehiculoPrematriculados(ComunesServicioVehiculoPrematriculados comunesServicioVehiculoPrematriculados) {
		this.comunesServicioVehiculoPrematriculados = comunesServicioVehiculoPrematriculados;
	}

}