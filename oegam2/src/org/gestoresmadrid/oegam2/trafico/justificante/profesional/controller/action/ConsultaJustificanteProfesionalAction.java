package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.MotivoJustificante;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioConsultaJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaJustificanteProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResumenJustificanteProfBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaJustificanteProfesionalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -9220722202152034879L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaJustificanteProfesionalAction.class);

	private static final String MOSTRAR_DOCUMENTO = "mostrarDocumentoJustificanteProf";

	private static final String POP_UP_PRESENTAR_JUSTIF = "popPupPresentarJustif";

	List<MotivoJustificante> listaMotivos;

	@Resource
	private ModelPagination modeloJustificanteProfesionalPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioConsultaJustificanteProfesional servicioConsultaJustificanteProfesional;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ConsultaJustificanteProfBean consultaJustificanteProfBean;

	private Long[] idJustificanteInternos;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String nombreJustificantes;
	private ResumenJustificanteProfBean resumen;
	private static final String[] fetchList = { "tramiteTrafico", "tramiteTrafico.vehiculo" , "tramiteTrafico.contrato"};

	// Desde el trámite
	private TramiteTrafTranDto tramiteTrafTranDto;
	private String arrayIdsJustificantes;
	private String tipoTramiteGenerar;

	public String pendienteAutorizacionColegio() {
		if (utilesColegiado.tienePermisoAdmin()) {
			ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.pendienteAutorizacionColegioEnBloque(idJustificanteInternos, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumenConsulta(resultado);
				resumen.setEsPteAutoColegio(Boolean.TRUE);
			}
		} else {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre los justificantes seleccionados.");
		}
		return inicio();
	}

	private void rellenarResumenConsulta(ResultadoConsultaJustProfBean resultado) {
		resumen = new ResumenJustificanteProfBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setListaOK(resultado.getListaOK());
	}

	public String generarJustifPro() throws Throwable {
		ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.generarJPBloque(arrayIdsJustificantes,utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.tienePermisoAdmin());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			rellenarResumenConsulta(resultado);
			resumen.setEsGenerarJp(Boolean.TRUE);
			nombreJustificantes = resultado.getNombreFichero();
		}
		return inicio();
	}

	public String forzar() throws OegamExcepcion {
		if (utilesColegiado.tienePermisoAdmin()) {
			ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.forzarJPBloque(idJustificanteInternos,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumenConsulta(resultado);
				resumen.setEsForzarJp(Boolean.TRUE);
			}
		} else {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre los justificantes seleccionados.");
		}
		return inicio();
	}

	public String imprimir() {
		ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.imprimirJPBloque(idJustificanteInternos);
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			rellenarResumenConsulta(resultado);
			nombreJustificantes = resultado.getNombreFichero();
			if (nombreJustificantes != null && !nombreJustificantes.isEmpty()) {
				resumen.setEsImpresion(Boolean.TRUE);
			}
		}
		return inicio();
	}

	public String descargar() {
		ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.descargarJPBloque(nombreJustificantes,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			inputStream = new ByteArrayInputStream(resultado.getByteFichero());
			fileName = resultado.getNombreFichero();
			return MOSTRAR_DOCUMENTO;
		}
		return actualizarPaginatedList();
	}

	public String anular() throws OegamExcepcion {
		if (utilesColegiado.tienePermisoAdmin()) {
			ResultadoConsultaJustProfBean resultado = servicioConsultaJustificanteProfesional.anularJPBloque(idJustificanteInternos,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumenConsulta(resultado);
				resumen.setEsAnular(Boolean.TRUE);
			}
		} else {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre los justificantes seleccionados.");
		}
		return inicio();
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		consultaJustificanteProfBean = (ConsultaJustificanteProfBean) object;
		if (consultaJustificanteProfBean.getNumExpediente() == null
				&& StringUtils.isBlank(consultaJustificanteProfBean.getMatricula())
				&& consultaJustificanteProfBean.getIdJustificante() == null) {
			if (consultaJustificanteProfBean.getFechaIni() == null && consultaJustificanteProfBean.getFechaFin() == null) {
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " días para poder obtener los justificantes.");
			} else {
				if (consultaJustificanteProfBean.getFechaIni() != null
						&& !consultaJustificanteProfBean.getFechaIni().isfechaNula()
						&& consultaJustificanteProfBean.getFechaIni().getFechaInicio() != null
						&& consultaJustificanteProfBean.getFechaFin().getFechaInicio() != null) {
					if (StringUtils.isNotBlank(valorRangoFechas)) {
						esRangoValido = utilesFecha.comprobarRangoFechas(
								consultaJustificanteProfBean.getFechaIni().getFechaInicio(),
								consultaJustificanteProfBean.getFechaFin().getFechaInicio(),
								Integer.parseInt(valorRangoFechas));
					}
				}
				if (!esRangoValido) {
					addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas
							+ " días para poder obtener los justificantes.");
				}
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaJustificanteProfBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloJustificanteProfesionalPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaJustificanteProfBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaJustificanteProfBean = (ConsultaJustificanteProfBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaJustificanteProfBean == null) {
			consultaJustificanteProfBean = new ConsultaJustificanteProfBean();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaJustificanteProfBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaJustificanteProfBean.setFechaFin(utilesFecha.getFechaFracionadaActual());
		consultaJustificanteProfBean.setFechaIni(utilesFecha.getFechaFracionadaActual());
		consultaJustificanteProfBean.setCodigoVerificacion(true);
		consultaJustificanteProfBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		setSort("idJustificanteInterno");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public boolean isBuscarInicio() {
		return false;
	}

	public ConsultaJustificanteProfBean getConsultaJustificanteProfBean() {
		return consultaJustificanteProfBean;
	}

	public void setConsultaJustificanteProfBean(ConsultaJustificanteProfBean consultaJustificanteProfBean) {
		this.consultaJustificanteProfBean = consultaJustificanteProfBean;
	}

	public Long[] getIdJustificanteInternos() {
		return idJustificanteInternos;
	}

	public void setIdJustificanteInternos(Long[] idJustificanteInternos) {
		this.idJustificanteInternos = idJustificanteInternos;
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
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

	public ResumenJustificanteProfBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenJustificanteProfBean resumen) {
		this.resumen = resumen;
	}

	public String getNombreJustificantes() {
		return nombreJustificantes;
	}

	public void setNombreJustificantes(String nombreJustificantes) {
		this.nombreJustificantes = nombreJustificantes;
	}

	public TramiteTrafTranDto getTramiteTrafTranDto() {
		return tramiteTrafTranDto;
	}

	public void setTramiteTrafTranDto(TramiteTrafTranDto tramiteTrafTranDto) {
		this.tramiteTrafTranDto = tramiteTrafTranDto;
	}

	public String getTipoTramiteGenerar() {
		return tipoTramiteGenerar;
	}

	public void setTipoTramiteGenerar(String tipoTramiteGenerar) {
		this.tipoTramiteGenerar = tipoTramiteGenerar;
	}

	public String getArrayIdsJustificantes() {
		return arrayIdsJustificantes;
	}

	public void setArrayIdsJustificantes(String arrayIdsJustificantes) {
		this.arrayIdsJustificantes = arrayIdsJustificantes;
	}

	public static String getPopUpPresentarJustif() {
		return POP_UP_PRESENTAR_JUSTIF;
	}

}