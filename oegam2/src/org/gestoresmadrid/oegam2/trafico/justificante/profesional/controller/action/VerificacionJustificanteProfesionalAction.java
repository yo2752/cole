package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import java.io.InputStream;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class VerificacionJustificanteProfesionalAction extends ActionBase {

	private static final long serialVersionUID = -5042888304559012384L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(VerificacionJustificanteProfesionalAction.class);

	private static final String MOSTRAR_DOCUMENTO = "mostrarDocumentoJustificante";

	private String codigoSeguroVerif;

	private Long idJustificanteInterno = null;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String inicio() throws Throwable {
		return SUCCESS;
	}

	public String buscarVerificar() throws Throwable {
		if (codigoSeguroVerif == null || codigoSeguroVerif.isEmpty()) {
			addActionError("Tiene que indicar un Código Seguro de Verificación de un justificante para buscar.");
			return SUCCESS;
		}
		try {
			JustificanteProfVO justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorCodigoVerificacion(codigoSeguroVerif);
			if (justificanteProfVO != null) {
				if (ConstantesProcesos.SI.equals(justificanteProfVO.getVerificado())) {
					addActionMessage("El justificante es válido.");
					idJustificanteInterno = justificanteProfVO.getIdJustificanteInterno();
				} else {
					addActionMessage("El justificante no es válido.");
				}
			} else {
				addActionError("No se ha encontrado un justificante asociado al código de seguridad introducido, si no ha solicitado su verificación pinche en el botón verificar para validarlo en SEA, de lo contrario espere a que se actualice en sistema.");
			}
		} catch (Exception e) {
			log.error("Error al intentar buscar un justificante profesional por código de verificacfión. Detalle: " + e.getMessage());
			addActionError("Ocurrió un error al intentar buscar el justificante profesional asociado al código de verificación.");
		}
		return SUCCESS;
	}

	public String verificar() throws Throwable {
		if (codigoSeguroVerif == null || codigoSeguroVerif.isEmpty()) {
			addActionError("Tiene que indicar un Código Seguro de Verificación de un justificante para validar.");
			return SUCCESS;
		}
		try {
			JustificanteProfVO justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorCodigoVerificacion(codigoSeguroVerif);
			if (justificanteProfVO != null) {
				if (ConstantesProcesos.SI.equals(justificanteProfVO.getVerificado())) {
					addActionMessage("El justificante ya ha sido verificado a través de Oegam y es válido.");
					idJustificanteInterno = justificanteProfVO.getIdJustificanteInterno();
				} else if (ConstantesProcesos.NO.equals(justificanteProfVO.getVerificado())) {
					addActionMessage("El justificante ya ha sido verificado a través de Oegam y no es válido.");
				} else {
					ResultBean result = null;
					String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
					if ("SI".equals(generarJustifSega)) {
						result = servicioJustificanteProfesional.crearSolicitud(
								ProcesosEnum.VERIFICACIONJPROFSEGA.toString(),
								justificanteProfVO.getIdJustificanteInterno(),
								utilesColegiado.getIdUsuarioSessionBigDecimal(),
								utilesColegiado.getIdContratoSessionBigDecimal(), null);
					} else {
						result = servicioJustificanteProfesional.crearSolicitud(
								ProcesosEnum.VERIFICACIONJPROF.toString(),
								justificanteProfVO.getIdJustificanteInterno(),
								utilesColegiado.getIdUsuarioSessionBigDecimal(),
								utilesColegiado.getIdContratoSessionBigDecimal(), null);
					}
					if (!result.getError()) {
						addActionMessage("Solicitud de verificación de justificante firmada y enviada, espere a recibir la notificación para buscarlo en el sistema");
					} else {
						addActionError(result.getMensaje() + ": " + justificanteProfVO.getNumExpediente());
					}
				}
			} else {
				JustificanteProfDto justificanteProfDto = new JustificanteProfDto();
				justificanteProfDto.setCodigoVerificacion(codigoSeguroVerif);
				ResultBean result = servicioJustificanteProfesional.verificar(justificanteProfDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
				if (!result.getError()) {
					addActionMessage("Solicitud de verificación de justificante firmada y enviada, espere a recibir la notificación para buscarlo en el sistema");
				} else {
					addActionError(result.getMensaje() + ": " + justificanteProfDto.getNumExpediente());
				}
			}
		} catch (Exception e) {
			log.error("Error al intentar verificar un justificante profesional. Detalle: " + e.getMessage());
			addActionError("Ocurrió un error al intentar verificar el justificante profesional.");
		}
		return SUCCESS;
	}

	public String imprimir() {
		Long[] listaIdJustificantesInterno = new Long[]{idJustificanteInterno};
		ByteArrayInputStreamBean byteArrayInputStreamBean = servicioJustificanteProfesional.imprimirJustificantes(listaIdJustificantesInterno);

		if (byteArrayInputStreamBean != null) {
			if (FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus())) {
				addActionError("No se han encontrado los ficheros para ninguno de los justificantes seleccionados.");
				return SUCCESS;
			}
			inputStream = byteArrayInputStreamBean.getByteArrayInputStream();
			fileName = byteArrayInputStreamBean.getFileName();
		}
		if (inputStream == null) {
			return SUCCESS;
		}
		return MOSTRAR_DOCUMENTO;
	}

	public String getCodigoSeguroVerif() {
		return codigoSeguroVerif;
	}

	public void setCodigoSeguroVerif(String codigoSeguroVerif) {
		this.codigoSeguroVerif = codigoSeguroVerif;
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}