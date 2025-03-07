package org.gestoresmadrid.procesos.model.jobs;

import java.security.KeyStore;
import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.administracion.utiles.KeyStoreUtils;
import org.gestoresmadrid.oegam2comun.administracion.view.dto.CertificateDto;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoCaducidadCertificados extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoCaducidadCertificados.class);

	private final String REF_PROP_LISTA_DESTINATARIOS = "certificados.validez.lista.destinatarios";
	private final String REF_PROP_DIAS_RESTANTES = "certificados.validez.alerta.dias.restantes";
	private final String REF_PROP_DESACTIVAR_ALERTA = "certificados.validez.alerta.desactivar";
	private final String REF_PROP_CONSIDERAR_FESTIVOS = "certificados.validez.lanza.proceso.considerando.festivos.nacionales";

	private final String REF_PROP_CLAVES_PUBLICAS_UBICACION = "keystore.claves.publicas.xDefecto.ubicacion";
	private final String REF_PROP_CLAVES_PUBLICAS_PASSWORD = "keystore.claves.publicas.xDefecto.password";
	private final String REF_PROP_CLAVES_PRIVADAS_UBICACION = "keystore.claves.privadas.xDefecto.ubicacion";
	private final String REF_PROP_CLAVES_PRIVADAS_PASSWORD = "keystore.claves.privadas.xDefecto.password";

	private static final String SUBJECT_CADUCIDAD = "certificados.validez.subject";

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		try {
			boolean considerarFestivos = "si".equals(gestorPropiedades.valorPropertie(REF_PROP_CONSIDERAR_FESTIVOS));
			boolean desactivarAlerta = "si".equals(gestorPropiedades.valorPropertie(REF_PROP_DESACTIVAR_ALERTA));

			if (!hayEnvioData(getProceso()) && !desactivarAlerta && utilesFecha.esFechaLaborable(considerarFestivos)) {
				ArrayList<CertificateDto> alertaCertificados = new ArrayList<>();
				KeyStoreUtils keyStoreUtils = new KeyStoreUtils();

				int limiteDiasValidez = Integer.valueOf(gestorPropiedades.valorPropertie(REF_PROP_DIAS_RESTANTES));

				String clavesPrivadasUbicacion = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_UBICACION);
				String clavesPrivadasPassword = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_PASSWORD);
				String clavesPublicasUbicacion = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_UBICACION);
				String clavesPublicasPassword = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_PASSWORD);

				KeyStore clavesPublicasJks = keyStoreUtils.load(clavesPublicasUbicacion, clavesPublicasPassword);
				KeyStore clavesPrivadasJks = keyStoreUtils.load(clavesPrivadasUbicacion, clavesPrivadasPassword);

				CertificateDto[] certificatesClavesPublicas = keyStoreUtils.getCertificateDtoList(clavesPublicasJks);
				for (int i = 0; i < certificatesClavesPublicas.length; i++) {
					if (certificatesClavesPublicas[i].getDiasValidezRestantes() < limiteDiasValidez) {
						alertaCertificados.add(certificatesClavesPublicas[i]);
					}
				}

				CertificateDto[] certificatesClavesPrivadas = keyStoreUtils.getCertificateDtoList(clavesPrivadasJks);
				for (int i = 0; i < certificatesClavesPrivadas.length; i++) {
					if (certificatesClavesPrivadas[i].getDiasValidezRestantes() < limiteDiasValidez) {
						alertaCertificados.add(certificatesClavesPrivadas[i]);
					}
				}

				if (!alertaCertificados.isEmpty()) {
					enviarCorreo(alertaCertificados, limiteDiasValidez);
				}
			}
			log.info("Proceso " + getProceso() + " -- Proceso ejecutado correctamente");
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, ConstantesProcesos.EJECUCION_CORRECTA, "0");
			peticionCorrecta();
		} catch (Throwable e) {
			log.error("Excepcion Caducidad Certificados General", e);
			String messageException = getMessageException(e);
			actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
			peticionRecuperable();
		}
	}

	private void enviarCorreo(ArrayList<CertificateDto> alertaCertificados, int limiteDiasValidez) throws Throwable {
		String listaDirecciones = gestorPropiedades.valorPropertie(REF_PROP_LISTA_DESTINATARIOS);
		String asuntoCorreo = gestorPropiedades.valorPropertie(SUBJECT_CADUCIDAD);

		ResultBean resultEnvio = servicioCorreo.enviarCorreo(
				getTextoAlertaCaducidadCertificados(alertaCertificados, limiteDiasValidez), null, null, asuntoCorreo,
				listaDirecciones, null, null, null, null);
		if (resultEnvio == null || resultEnvio.getError())
			throw new OegamExcepcion("Error envio mail");
	}

	private String getTextoAlertaCaducidadCertificados(ArrayList<CertificateDto> array, int limiteDiasValidez) {
		StringBuilder stringBuilder = new StringBuilder();
		String encabezado = "La plataforma OEGAM, está configurada para envíar este mensaje cuando alguno de los<br>"
				+ "certificados implicados en el establecimiento de un protocolo de comunicación<br>" + "segura (https) tiene un número de dias de validez restantes por debajo de " + limiteDiasValidez
				+ "<br><br>" + "Los certificados que han originado este aviso son los siguientes: <br><br>";
		stringBuilder.append(encabezado);
		String certificados = "";
		for (CertificateDto certificateDto : array) {
			certificados += "<p style=\"background-color:yellow\">* Alias del certificado : <b>\'" + certificateDto.getAlias() + "\'</b></p>"
					+ "<p style=\"background-color:yellow\">* Dias de validez restantes: <b>" + certificateDto.getDiasValidezRestantes() + "</b></p><br>";
		}
		stringBuilder.append(certificados);
		String pie = "<br>Este aviso se mandará diariamente hasta que se renueve el/los certificado/s.<br>" + "También puede desactivarse.<br>" + "Comuníquelo al departamento de desarrollo.<br>";
		stringBuilder.append(pie);
		return stringBuilder.toString();
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.CADUCIDAD_CERTIFICADOS.getNombreEnum();
	}
}