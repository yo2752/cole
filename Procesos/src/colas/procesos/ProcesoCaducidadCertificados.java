package colas.procesos;

import java.security.KeyStore;
import java.util.ArrayList;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.administracion.utiles.KeyStoreUtils;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import administracion.beans.CertificateBean;
import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import escrituras.beans.ResultBean;
import procesos.enumerados.RetornoProceso;
import trafico.utiles.enumerados.TipoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoCaducidadCertificados extends ProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoCaducidadCertificados.class);

	private static final String REF_PROP_LISTA_DESTINATARIOS = "certificados.validez.lista.destinatarios";
	private static final String REF_PROP_DIAS_RESTANTES = "certificados.validez.alerta.dias.restantes";
	private static final String REF_PROP_DESACTIVAR_ALERTA = "certificados.validez.alerta.desactivar";
	private static final String REF_PROP_CONSIDERAR_FESTIVOS = "certificados.validez.lanza.proceso.considerando.festivos.nacionales";

	private static final String REF_PROP_CLAVES_PUBLICAS_UBICACION = "keystore.claves.publicas.xDefecto.ubicacion";
	private static final String REF_PROP_CLAVES_PUBLICAS_PASSWORD = "keystore.claves.publicas.xDefecto.password";
	private static final String REF_PROP_CLAVES_PRIVADAS_UBICACION = "keystore.claves.privadas.xDefecto.ubicacion";
	private static final String REF_PROP_CLAVES_PRIVADAS_PASSWORD = "keystore.claves.privadas.xDefecto.password";
	// Mantis 16616. David Sierra: Se pasa por properties el asunto del correo adaptado a cada entorno
	private static final String SUBJECT_CADUCIDAD = "certificados.validez.subject";

	private String CLAVES_PUBLICAS_UBICACION;
	private String CLAVES_PUBLICAS_PASSWORD;
	private String CLAVES_PRIVADAS_UBICACION;
	private String CLAVES_PRIVADAS_PASSWORD;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	public ServicioCorreo getServicioCorreo() {
		if (servicioCorreo == null) {
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		String listaDirecciones = null;
		String desactivarAlerta = null;
		String asuntoCorreo = null;
		int limiteDiasValidez = 0;
		try {
			listaDirecciones = gestorPropiedades.valorPropertie(REF_PROP_LISTA_DESTINATARIOS);
			desactivarAlerta = gestorPropiedades.valorPropertie(REF_PROP_DESACTIVAR_ALERTA);
			asuntoCorreo     = gestorPropiedades.valorPropertie(SUBJECT_CADUCIDAD);
			limiteDiasValidez = Integer.valueOf(gestorPropiedades.valorPropertie(REF_PROP_DIAS_RESTANTES));
			boolean considerarFestivos = "si".equals(gestorPropiedades.valorPropertie(REF_PROP_CONSIDERAR_FESTIVOS));
			//
			if (!desactivarAlerta.equalsIgnoreCase("si")
					&& !getModeloTrafico().hayEnvio(TipoProceso.CADUCIDAD_CERTIFICADOS)
					&& utilesFecha.esFechaLaborable(considerarFestivos)) {
				ArrayList<CertificateBean> alertaCertificados = new ArrayList<>();
				KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
				CLAVES_PRIVADAS_UBICACION = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_UBICACION);
				CLAVES_PRIVADAS_PASSWORD = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_PASSWORD);
				CLAVES_PUBLICAS_UBICACION = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_UBICACION);
				CLAVES_PUBLICAS_PASSWORD = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_PASSWORD);
				KeyStore clavesPublicasJks = keyStoreUtils.load(CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
				KeyStore clavesPrivadasJks = keyStoreUtils.load(CLAVES_PRIVADAS_UBICACION, CLAVES_PRIVADAS_PASSWORD);
				CertificateBean[] certificatesClavesPublicas = keyStoreUtils.getCertificateBeanList(clavesPublicasJks);
				for (int i = 0; i < certificatesClavesPublicas.length; i ++) {
					if (certificatesClavesPublicas[i].getDiasValidezRestantes() < limiteDiasValidez) {
						alertaCertificados.add(certificatesClavesPublicas[i]);
					}
				}
				CertificateBean[] certificatesClavesPrivadas = keyStoreUtils.getCertificateBeanList(clavesPrivadasJks);
				for (int i = 0; i < certificatesClavesPrivadas.length; i ++) {
					if (certificatesClavesPrivadas[i].getDiasValidezRestantes() < limiteDiasValidez){
						alertaCertificados.add(certificatesClavesPrivadas[i]);
					}
				}
				if (!alertaCertificados.isEmpty()) {
					ResultBean resultEnvio;
					resultEnvio = getServicioCorreo().enviarCorreo(getTextoAlertaCaducidadCertificados(alertaCertificados, limiteDiasValidez), null, null, asuntoCorreo, listaDirecciones, null, null, null, null);
					if (resultEnvio == null || resultEnvio.getError())
							throw new OegamExcepcion("Error envio mail");
				}
			}
			//
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.CORRECTO);
			log.info("Proceso " + ConstantesProcesos.PROCESO_ENVIO_FIRST_MATE + " -- Proceso ejecutado correctamente");
			try {
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.CADUCIDAD_CERTIFICADOS.getValorEnum(),
						ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA);
			} catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CADUCIDAD_CERTIFICADOS.getNombreEnum());
			}
		} catch (OegamExcepcion ex) {
			String errorPropiedades = "No se han configurado correctamente las propiedades necesarias para la ejecución del proceso";
			try {
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.CADUCIDAD_CERTIFICADOS.getValorEnum(), errorPropiedades, ConstantesProcesos.EJECUCION_EXCEPCION);
			} catch(Exception e) { 
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CADUCIDAD_CERTIFICADOS.getNombreEnum());
			}
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.ERROR_RECUPERABLE);
		} catch(Exception e) {
			try {
				String error;
				if (e.getMessage() != null && !e.getMessage().equals("")) {
					error = e.getMessage();
				} else {
					error = e.toString();
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.CADUCIDAD_CERTIFICADOS.getValorEnum(), error, ConstantesProcesos.EJECUCION_EXCEPCION);
			} catch(Exception ex) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.CADUCIDAD_CERTIFICADOS.getNombreEnum());
			}
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.ERROR_RECUPERABLE);
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Proceso " + ProcesosEnum.CADUCIDAD_CERTIFICADOS.getNombreEnum() + " --  caducidad certificados.");
	}

	private String getTextoAlertaCaducidadCertificados(ArrayList<CertificateBean> array, int limiteDiasValidez) {
		StringBuilder stringBuilder = new StringBuilder();
		String encabezado = "La plataforma OEGAM, está configurada para envíar este mensaje cuando alguno de los<br>" +
				"certificados implicados en el establecimiento de un protocolo de comunicación<br>" + 
				"segura (https) tiene un número de dias de validez restantes por debajo de " + limiteDiasValidez + "<br><br>" +
				"Los certificados que han originado este aviso son los siguientes: <br><br>";
		stringBuilder.append(encabezado);
		String certificados = "";
		for (CertificateBean certificateBean : array) {
			certificados += "<p style=\"background-color:yellow\">* Alias del certificado : <b>\'" + certificateBean.getAlias()  + "\'</b></p>" +
					"<p style=\"background-color:yellow\">* Dias de validez restantes: <b>" + certificateBean.getDiasValidezRestantes() + "</b></p><br>";
		}
		stringBuilder.append(certificados);
		String pie = "<br>Este aviso se mandará diariamente hasta que se renueve el/los certificado/s.<br>" +
				"También puede desactivarse.<br>" + 
				"Comuníquelo al departamento de desarrollo.<br>";
		stringBuilder.append(pie);
		return stringBuilder.toString();
	}

}