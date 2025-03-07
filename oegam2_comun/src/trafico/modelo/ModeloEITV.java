package trafico.modelo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import oegam.constantes.ConstantesPQ;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.beans.schemas.generated.eitv.generated.ObjectFactory;
import trafico.beans.schemas.generated.eitv.generated.TipoTextoLegal;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import trafico.utiles.XmlAFirmar;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloEITV {

	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloEITV.class);
	private static final String UTF_8 = "UTF-8";

	private ModeloColegiado modeloColegiado;
	private ModeloMatriculacion modeloMatriculacion;

	private ModeloEITV modeloEITV;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloEITV() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private ConsultaTarjeta anhadirFirmasHsm(ConsultaTarjeta consultaTarjeta, String numColegiado) throws UnsupportedEncodingException {
		ResultBean resultFirmasBean = new ResultBean();

		/**
		 * Firma del XML por el Colegiado
		 */
		String xml = new XMLConsultaTarjetaEITVFactory().toXML(consultaTarjeta);
		ResultBean resultFirmaColegiadoHsm = XmlAFirmar.realizarFirmaDatosFirmadosHsm(xml, utilesColegiado.getAlias(numColegiado));
		String xmlFirmadoColegiado = resultFirmaColegiadoHsm.getMensaje();
		log.debug("XML firmado por el Colegiado:" + xmlFirmadoColegiado);

		if (!resultFirmaColegiadoHsm.getError()) {
			/**
			 * Se añade la firma del gestor al Registro de Entrada
			 */
			consultaTarjeta.setFirmaGestor(xmlFirmadoColegiado.getBytes(UTF_8));
			/**
			 * Firma del XML por el Colegio
			 */
		} else {
			resultFirmasBean.setError(true);
		}
		return consultaTarjeta;
	}

	/**
	 * @param traficoTramiteMatriculacionBean
	 * @throws Throwable
	 */
	public void consultarTarjetaEITV(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) throws Throwable {
		generarXmlSolicitudEitv(traficoTramiteMatriculacionBean);
		getModeloMatriculacion().crearSolicitudEnColaEItv(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
	}

	/**
	 * @param traficoTramiteMatriculacionBean
	 * @throws Throwable
	 */

	public void generarXmlSolicitudEitv(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) throws Throwable {
		ConsultaTarjeta consultaTarjetaEitv = crearConsultaTarjetaEitv(traficoTramiteMatriculacionBean);
		exportarFicheroEitv(consultaTarjetaEitv, traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
	}

	/**
	 * @param traficoTramiteMatriculacionBean
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private ConsultaTarjeta crearConsultaTarjetaEitv(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) throws UnsupportedEncodingException {
		ConsultaTarjeta consultaTarjetaEitv = getObjetoRaizXml(traficoTramiteMatriculacionBean);
		consultaTarjetaEitv = anhadirFirmasHsm(consultaTarjetaEitv, traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
		return consultaTarjetaEitv;
	}

	/**
	 * @param consultaTarjetaEitv
	 * @param numExpediente
	 * @throws Throwable
	 */
	private void exportarFicheroEitv(ConsultaTarjeta consultaTarjetaEitv, BigDecimal numExpediente) throws Throwable {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.EITV);
		ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_EITV + numExpediente);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);

		try {
			gestorDocumentos.crearFicheroXml(ficheroBean, XMLConsultaTarjetaEITVFactory.NAME_CONTEXT, consultaTarjetaEitv, null, null);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento XML");
		}
	}

//  Ya no se usa MATEEITV. Ha sido sustituido por CONSULTA_TARJETA_EITV
//	/**
//	 * @param numExpediente
//	 * @param nive
//	 * @throws CrearSolicitudExcepcion
//	 * @throws NiveNoValidoExcepcion
//	 * @throws BastidorNoValidoExcepcion
//	 */
//	public void consultarTarjetaEITVSinFichero(BigDecimal numExpediente, String nive, String bastidor, String proceso) throws CrearSolicitudExcepcion, NiveNoValidoExcepcion, BastidorNoValidoExcepcion {
//		getModeloMatriculacion().crearSolicitudEnColaWsInfoEitv(numExpediente, utilesColegiado.getIdUsuarioDeSesion(), proceso);
//	}

	/**
	 * @param traficoMatriculacionBean
	 * @return
	 */
	private ConsultaTarjeta getObjetoRaizXml(TramiteTraficoMatriculacionBean traficoMatriculacionBean) {
		ConsultaTarjeta consultaTarjetaEitv = null;
		ObjectFactory objFactory = new ObjectFactory();

		Persona colegiadoCompleto = getModeloColegiado().obtenerColegiadoCompleto(traficoMatriculacionBean.getTramiteTraficoBean().getIdContrato());
		// Obtenemos datos del colegio
		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(traficoMatriculacionBean.getTramiteTraficoBean().getIdContrato());

		// -----------------ELEMENTO RAIZ----------------------
		consultaTarjetaEitv = objFactory.createConsultaTarjeta();

		consultaTarjetaEitv.setDatosFirmados(objFactory.createDatosFirmados());
		consultaTarjetaEitv.setFirmaGestor(new byte[0]);

		consultaTarjetaEitv.getDatosFirmados().setAGENCYDOI(colegiadoCompleto.getNif());
		consultaTarjetaEitv.getDatosFirmados().setAGENTDOI(colegiadoCompleto.getNif());

		consultaTarjetaEitv.getDatosFirmados().setNIVE(traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive());
		consultaTarjetaEitv.getDatosFirmados().setVIN(traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor());
		consultaTarjetaEitv.getDatosFirmados().setTEXTOLEGAL(TipoTextoLegal.TEXTO_LEGAL);
		return consultaTarjetaEitv;
	}

	public void guardarDatosEitv(VehiculoBean vehiculo, ColaBean solicitud) throws OegamExcepcion {
		BeanPQGUARDAR beanPQGuardar = new BeanPQGUARDAR();

		beanPQGuardar = vehiculoBeanPQConversion.convertirBeanToPQNuevo(vehiculo);

		beanPQGuardar.setP_ID_USUARIO(solicitud.getIdUsuario());
		beanPQGuardar.setP_ID_CONTRATO_SESSION(solicitud.getIdUsuario());
		beanPQGuardar.setP_NUM_EXPEDIENTE(solicitud.getIdTramite());
		beanPQGuardar.setP_ID_CONTRATO(solicitud.getIdUsuario());
		beanPQGuardar.setP_NUM_COLEGIADO(solicitud.getIdTramite().toString().substring(0, 4));
		beanPQGuardar.setP_TIPO_TRAMITE(solicitud.getTipoTramite());

		beanPQGuardar.execute();

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQGuardar.getP_CODE();
		log.info(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.info(ConstantesPQ.LOG_P_SQLERRM + beanPQGuardar.getP_SQLERRM());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQGuardar.getP_INFORMACION());

		if (!pCodeTramite.equals(ConstantesPQ.pCodeOk)) {
			throw new OegamExcepcion(beanPQGuardar.getP_SQLERRM());
		}
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloColegiado getModeloColegiado() {
		if (modeloColegiado == null) {
			modeloColegiado = new ModeloColegiado();
		}
		return modeloColegiado;
	}

	public void setModeloColegiado(ModeloColegiado modeloColegiado) {
		this.modeloColegiado = modeloColegiado;
	}

	public ModeloEITV getModeloEITV() {
		if (modeloEITV == null) {
			modeloEITV = new ModeloEITV();
		}
		return modeloEITV;
	}

	public void setModeloEITV(ModeloEITV modeloEITV) {
		this.modeloEITV = modeloEITV;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}
}