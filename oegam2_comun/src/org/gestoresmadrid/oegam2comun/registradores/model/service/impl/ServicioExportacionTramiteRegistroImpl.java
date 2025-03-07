package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.BienEscrituraType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.BienesEscrituraType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.DomicilioINEType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CABECERA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CABECERA.DATOSGESTORIA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.RegistroType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.TipoProvincia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioExportacionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioInmueble;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import general.utiles.UtilesXML;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Service
public class ServicioExportacionTramiteRegistroImpl implements ServicioExportacionTramiteRegistro {

	private static final long serialVersionUID = 6169893039803618027L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioExportacionTramiteRegistroImpl.class);

	public static final String ID_TRAMITE_REGISTRO = "IDTRAMITEREGISTRO";

	public static final String RUTA_ESQUEMA_REGISTRO = "ruta.esquema.registro";

	@Autowired
	private Conversiones conversiones;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioInmueble servicioInmueble;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioRegistro servicioRegistro;

	@Autowired
	private TramiteRegistroDao tramiteRegistroDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Override
	@Transactional
	public ResultBean exportarEscritura(String[] codSeleccionados) throws Exception, OegamExcepcion {
		ResultBean resultadoExportacion = new ResultBean();
		List<TramiteRegistroVO> listaTramitesEscritura = new ArrayList<>();
		for (int i = 0; i < codSeleccionados.length; i++) {
			TramiteRegistroVO tramiteRegistroVO = tramiteRegistroDao.getTramite(new BigDecimal(codSeleccionados[i].trim()));

			if (tramiteRegistroVO != null) {
				listaTramitesEscritura.add(tramiteRegistroVO);
			}
		}

		// Inicialiazación
		FORMATOGA ga = new FORMATOGA();
		ga.setCABECERA(new CABECERA());
		ga.getCABECERA().setDATOSGESTORIA(new DATOSGESTORIA());

		ContratoDto contrato = servicioContrato.getContratoDto(listaTramitesEscritura.get(0).getIdContrato());
		// Se añade Cabecera
		ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(contrato.getColegiadoDto().getNumColegiado()));
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(conversiones.getSiglasFromIdProvincia(contrato.getIdProvincia())));

		// Se añade Fecha de creación
		ga.setFechaCreacion(utilesFecha.getTextoFechaFormato(new Date(), new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss")));
		// Se añade plataforma
		ga.setPlataforma("OEGAM");

		for (int i = 0; i < listaTramitesEscritura.size(); i++) {

			FORMATOGA.ESCRITURA escritura;

			escritura = conversor.transform(listaTramitesEscritura.get(i), FORMATOGA.ESCRITURA.class);

			// Se añade los bienes
			List<InmuebleDto> inmuebles = servicioInmueble.getInmuebles(listaTramitesEscritura.get(i).getIdTramiteRegistro());
			if (inmuebles != null && !inmuebles.isEmpty()) {
				BienesEscrituraType bienes = new BienesEscrituraType();
				for (int j = 0; j < inmuebles.size(); j++) {
					BienEscrituraType bien;
					bien = conversor.transform(inmuebles.get(j), BienEscrituraType.class);
					bienes.getBien().add(bien);
				}

				escritura.setBienes((bienes));
			}
			// ---------------------------------------------------------------------

			// Añadimos la direccion del destinatario
			DireccionVO direccionVO = null;
			if (listaTramitesEscritura.get(i).getSociedad() != null && listaTramitesEscritura.get(i).getIdDireccionDestinatario() != null) {
				direccionVO = servicioDireccion.getDireccionVO(listaTramitesEscritura.get(i).getIdDireccionDestinatario());
			}
			escritura.setDomicilio(conversor.transform(direccionVO, DomicilioINEType.class));
			// -------------------------------------------------------------------------------------

			// Se añade el registro
			if (StringUtils.isNotBlank(listaTramitesEscritura.get(i).getIdRegistro())) {
				RegistroDto registroDto = servicioRegistro.getRegistroPorId(Long.parseLong(listaTramitesEscritura.get(i).getIdRegistro()));
				RegistroType registro = new RegistroType();
				registro.setCodProvincia(registroDto.getIdProvincia());
				registro.setCodRegistro(registroDto.getIdRegistro());
				escritura.setRegistro(registro);
			}

			// Se añaden los trámites
			ga.getESCRITURA().add(escritura);
		}

		if (ga.getESCRITURA().isEmpty()) {
			log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_INICIO + " Al exportar, obteniendo FORMATOGA de Escrituras.");
			resultadoExportacion.setError(Boolean.TRUE);
			resultadoExportacion.setMensaje("Error al convertir a FORMATOGA de Escritura.");
		}

		String idSession = ServletActionContext.getRequest().getSession().getId();
		resultadoExportacion = formatogaToXML(ga, idSession);

		if (resultadoExportacion.getError()) {
			log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_INICIO + " Al exportar, Generando el XML de Escrituras.");
			resultadoExportacion.setMensaje("Error al generar XML: " + (null != resultadoExportacion ? resultadoExportacion.getMensaje() : "Devuelve Null"));
		}
		return resultadoExportacion;
	}

	/**
	 * Convierte el FORMATOGA a XML
	 * @param ga
	 */
	public ResultBean formatogaToXML(FORMATOGA ga, String idSession) {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "/temp/registradores" + idSession + ".xml");
		try {
			// Crear clasificador

			JAXBContext jc = JAXBContext.newInstance(FORMATOGA.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			marshaller.marshal(ga, salida);
			byte[] bytes = utiles.getBytesFromFile(salida);
			resultado.addAttachment(BYTESXML, bytes);
		} catch (Exception e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al generar el fichero XML");
			return resultado;
		} finally {
			try {
				// Validación interna contra el esquema antes de borrar el temporal:
				UtilesXML.validarXML(salida, gestorPropiedades.valorPropertie(RUTA_ESQUEMA_REGISTRO));
			} catch (XmlNoValidoExcepcion | Exception e) {
				log.error(e);
			}
			salida.delete();
		}

		return resultado;
	}

}
