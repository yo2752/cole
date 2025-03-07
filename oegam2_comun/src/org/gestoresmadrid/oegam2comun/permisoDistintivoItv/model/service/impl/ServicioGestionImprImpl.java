package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioNavegacionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoExcelKOBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioGestionImprImpl implements ServicioGestionImpr{

	private static final long serialVersionUID = -8523745162039626792L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionImprImpl.class);

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioPermisosDgt servicioPermisoDgt;

	@Autowired
	ServicioFichasTecnicasDgt servicioFichasTecnicasDgt;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioPermisos serviciosPermisos;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioNavegacionImpr servicioNavegacionImpr;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioEmpresaTelematica servicioEmpresaTelematica;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioImpresionPermisosFichasDgtImpl servicioImpresionPermisosFichasDgtImpl;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoPermisoDistintivoItvBean crearSolicitudImprFichasPantalla(Long idContrato, String tipoTramite, Date fechaPresentacion, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			resultado = validarDatosImprNocturnoPantalla(idContrato, tipoTramite, fechaPresentacion);
			if (!resultado.getError()) {
				ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(idContrato));
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					ResultadoPermisoDistintivoItvBean resultCoches = servicioFichasTecnicasDgt.generarDocFichasContratoCoches(contratoDto,fechaPresentacion, ipConexion);
					if(!resultCoches.getError()){
						resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente de coches para solicitar sus fichas técnicas.");
					} else {
						resultado.addListaError(resultCoches.getMensaje());
					}
					ResultadoPermisoDistintivoItvBean resultMotos = servicioFichasTecnicasDgt.generarDocFichasContratoMotos(contratoDto,fechaPresentacion, ipConexion);
					if(!resultMotos.getError()){
						resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente de motos para solicitar sus fichas técnicas.");
					} else {
						resultado.addListaError(resultMotos.getMensaje());
					}
				} else if(TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
					ResultadoPermisoDistintivoItvBean resultCoches = servicioFichasTecnicasDgt.generarDocFichasContratoCochesDupli(contratoDto,fechaPresentacion, ipConexion);
					if (!resultCoches.getError()) {
						resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente de coches para solicitar sus fichas técnicas.");
					} else {
						resultado.addListaError(resultCoches.getMensaje());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Solo se pueden solicitar fichas de tramites de MATW.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear la solicitud para el Impr_Nocturno desde pantalla, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para el Impr_Nocturno desde pantalla.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean crearSolicitudImprPantalla(Long idContrato, String tipoTramite, Date fechaPresentacion, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			resultado = validarDatosImprNocturnoPantalla(idContrato,tipoTramite,fechaPresentacion);
			if(!resultado.getError()){
				ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(idContrato));
				if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)){
					resultado = servicioPermisoDgt.generarDocPermisosContrato(contratoDto,fechaPresentacion, TipoTramiteTrafico.Matriculacion.getValorEnum(), ipConexion);
				} else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)){
					resultado = servicioPermisoDgt.generarDocPermisosContrato(contratoDto,fechaPresentacion, TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), ipConexion);
				}else if(TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)){
					resultado = servicioPermisoDgt.generarDocPermisosContrato(contratoDto,fechaPresentacion, TipoTramiteTrafico.Duplicado.getValorEnum(), ipConexion);
				}
				if (!resultado.getError()) {
					resultado.setMensaje("La " + TipoTramiteTrafico.Solicitud_Permiso.getNombreEnum() + " de impresion para el contrato " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " y la fecha de presentacion: " +
							new SimpleDateFormat("dd/MM/YYYY").format(fechaPresentacion) + " se ha generado correctamente.");
				}

			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear la solicitud para el Impr_Nocturno desde pantalla, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para el Impr_Nocturno desde pantalla.");
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarDatosImprNocturnoPantalla(Long idContrato, String tipoTramite, Date fechaPresentacion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if (idContrato == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un contrato para poder obtener su documentación.");
		} else if(tipoTramite == null || tipoTramite.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar el tipo de tramite para el que quiere obtener su documentación.");
		} else if(!TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite) 
			&& !TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)
			&& !TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se pueden obtener los documentos de los tramites de transmision electronica,duplicado o matriculacion.");
		} else if (fechaPresentacion == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar una fecha de presentación para poder obtener la documentación.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly= true)
	public Boolean esColegiadoSuperTelematico(BigDecimal idContrato) {
		String colegiadosImprTodos = gestorPropiedades.valorPropertie("colegiado.impresion.impr.matw");
		String sJefaturasAutoMatw = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matw");
		ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
		if (sJefaturasAutoMatw != null && !sJefaturasAutoMatw.isEmpty()) {
			String[] jefaturasAuto = sJefaturasAutoMatw.split(",");
			for (String jefaturoProv : jefaturasAuto) {
				if (jefaturoProv.equals(contratoDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
					return Boolean.TRUE;
				}
			}
		}
		if (colegiadosImprTodos.contains(contratoDto.getColegiadoDto().getNumColegiado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean gestionColasImpr() {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Date fecha = null;
			if ("SI".equals(gestorPropiedades.valorPropertie("forzar.ejecucion.gestionColasImpr"))) {
				String fechaForzada = gestorPropiedades.valorPropertie("fecha.forzar.ejecucion.gestionColasImpr");
				if (fechaForzada != null && !fechaForzada.isEmpty()) {
					fecha = utilesFecha.formatoFecha("dd/MM/yyyy", fechaForzada);
				} else {
					fecha = new Date();
				}
			} else {
				fecha = new Date();
			}
			List<Long> listaIdsContratos = servicioTramiteTrafico.getListaIdsContratosFinalizadosTelematicamente(fecha);
			if (listaIdsContratos != null && !listaIdsContratos.isEmpty()) {
				for (Long idContrato : listaIdsContratos) {
					ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(idContrato));
					if (!"2631".equals(contratoDto.getColegiadoDto().getNumColegiado())){
						String colegiadosImprTodos = gestorPropiedades.valorPropertie("colegiado.impresion.impr.matw");
						Boolean esJefaturaMatw = Boolean.FALSE;
						Boolean esJefaturaCtit = Boolean.FALSE;
						String sJefaturasAutoMatw = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matw");

						if (sJefaturasAutoMatw != null && !sJefaturasAutoMatw.isEmpty()) {
							String[] jefaturasAuto = sJefaturasAutoMatw.split(",");
							for (String jefaturoProv : jefaturasAuto) {
								if (jefaturoProv.equals(contratoDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
									esJefaturaMatw = Boolean.TRUE;
									impresionImprTelematicosMatw(resultado, contratoDto,fecha, "proceso");
									break;
								}
							}
						}
						String sJefaturasAutoCtit = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.ctit");
						if (sJefaturasAutoCtit != null && !sJefaturasAutoCtit.isEmpty()) {
							String[] jefaturasAuto = sJefaturasAutoCtit.split(",");
							for (String jefaturoProv : jefaturasAuto) {
								if (jefaturoProv.equals(contratoDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
									esJefaturaCtit = Boolean.TRUE;
									impresionCtitImpr(resultado, contratoDto, fecha, "proceso");
									break;
								}
							}
						}

						if (!esJefaturaMatw) {
							if (colegiadosImprTodos.contains(contratoDto.getColegiadoDto().getNumColegiado())){
								impresionImprTelematicosMatw(resultado, contratoDto,fecha, "proceso");
							} else {
								impresionIMPRPorEmpresaYSTMatw(resultado, contratoDto, idContrato, fecha);
							}
						}
						if (!esJefaturaCtit && serviciosPermisos.tienePermisoElContrato(idContrato, "OT19M", "OEGAM_TRAF")) {
							impresionCtitImpr(resultado, contratoDto, fecha, "proceso");
						}
					}
				}
				enviarMailResultadoGestionColasImpr(resultado, fecha);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen contratos con matriculaciones para la fecha: " + new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(fecha));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear las colas para las solicitudes de IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private void impresionCtitImpr(ResultadoPermisoDistintivoItvBean resultado, ContratoDto contratoDto, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultMatw = servicioPermisoDgt.generarDocPermisosContrato(contratoDto,fecha, TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), ipConexion);
		if (resultMatw.getError()) {
			resultado.addError();
			resultado.addListaMensaje(resultMatw.getMensaje());
		} else if(resultMatw.getNumError() != null && resultMatw.getNumError() > 0) {
			for (String mensaje : resultMatw.getListaError()) {
				resultado.addError();
				resultado.addListaError(mensaje);
			}
		} else {
			resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente por lo que se le piden sus permisos de Transmision.");
			resultado.addOk();
		}
	}

	private void impresionImprTelematicosMatw(ResultadoPermisoDistintivoItvBean resultado, ContratoDto contratoDto, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultMatw = servicioPermisoDgt.generarDocPermisosContrato(contratoDto, fecha, TipoTramiteTrafico.Matriculacion.getValorEnum(), ipConexion);
		if (resultMatw.getError()) {
			resultado.addError();
			resultado.addListaMensaje(resultMatw.getMensaje());
		} else if (resultMatw.getNumError() != null && resultMatw.getNumError() > 0) {
			for(String mensaje : resultMatw.getListaError()){
				resultado.addError();
				resultado.addListaError(mensaje);
			}
		} else {
			resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente por lo que se le piden sus permisos de Matriculacion.");
			resultado.addOk();
		}
		resultMatw = servicioFichasTecnicasDgt.generarDocFichasContratoCoches(contratoDto,fecha, ipConexion);
		if (resultMatw.getError()) {
			resultado.addError();
			resultado.addListaMensaje(resultMatw.getMensaje());
		} else if(resultMatw.getNumError() != null && resultMatw.getNumError() > 0) {
			for (String mensaje : resultMatw.getListaError()) {
				resultado.addError();
				resultado.addListaError(mensaje);
			}
		} else {
			resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente de coches para solicitar sus fichas técnicas.");
			resultado.addOk();
		}
		resultMatw = servicioFichasTecnicasDgt.generarDocFichasContratoMotos(contratoDto, fecha, ipConexion);
		if (resultMatw.getError()) {
			resultado.addError();
			resultado.addListaMensaje(resultMatw.getMensaje());
		} else if (resultMatw.getNumError() != null && resultMatw.getNumError() > 0) {
			for (String mensaje : resultMatw.getListaError()) {
				resultado.addError();
				resultado.addListaError(mensaje);
			}
		} else {
			resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites finalizados telemáticamente de motos para solicitar sus fichas técnicas.");
			resultado.addOk();
		}
	}

	private void impresionIMPRPorEmpresaYSTMatw(ResultadoPermisoDistintivoItvBean resultado, ContratoDto contratoDto, Long idContrato, Date fecha) {
		if (serviciosPermisos.tienePermisoElContrato(idContrato, "OT16M", "OEGAM_TRAF")
				|| serviciosPermisos.tienePermisoElContrato(idContrato, "OT17M", "OEGAM_TRAF")
				||  serviciosPermisos.tienePermisoElContrato(idContrato, "OT20M", "OEGAM_TRAF")){
			String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Matriculacion.getValorEnum();
			ResultadoPermisoDistintivoItvBean resultMatw = servicioTramiteTraficoMatriculacion.tieneTramitesValidosIMPRPorContrato(contratoDto, fecha, TipoTramiteTrafico.Solicitud_Permiso.getValorEnum());
			if (!resultMatw.getError()) {
				if (resultMatw.getTieneTramitesST()) {
					ResultadoPermisoDistintivoItvBean resultCola = crearPeticionImpr(contratoDto, ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, TipoTramiteTrafico.Solicitud_Permiso.getValorEnum());
					if (resultCola.getError()) {
						resultado.addListaError(resultCola.getMensaje());
						resultado.addError();
					} else {
						resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites supertelematicos por lo que se le piden sus permisos de Matriculacion.");
						resultado.addOk();
					}
				} else {
					resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " no tiene trámites supertelematicos de Matriculacion para solicitar sus permisos.");
					resultado.addOk();
				}
			} else {
				resultado.addListaError(resultMatw.getMensaje());
				resultado.addError();
			}
			resultMatw = servicioTramiteTraficoMatriculacion.tieneTramitesValidosIMPRPorContrato(contratoDto, fecha, TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum());
			if (!resultMatw.getError()) {
				if (resultMatw.getTieneTramitesST()) {
					ResultadoPermisoDistintivoItvBean resultCola = crearPeticionImpr(contratoDto, ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum());
					if (resultCola.getError()) {
						resultado.addListaError(resultCola.getMensaje());
						resultado.addError();
					} else {
						resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " tiene trámites supertelematicos por lo que se le piden sus fichas técnicas.");
						resultado.addOk();
					}
				} else {
					resultado.addListaOk("El contrato:" + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + " no tiene trámites supertelematicos de Matriculacion para solicitar sus fichas técnicas.");
					resultado.addOk();
				}
			} else {
				resultado.addListaError(resultMatw.getMensaje());
				resultado.addError();
			}
		}
	}

	private void enviarMailResultadoGestionColasImpr(ResultadoPermisoDistintivoItvBean resultado, Date fecha) {
		try {
			StringBuffer resultadoHtml = new StringBuffer();
			if (resultado.getListaError() != null && !resultado.getListaError().isEmpty()) {
				resultadoHtml.append("El proceso de gestion de colas de IMPR con fecha " + new SimpleDateFormat("dd/MM/yyyy").format(fecha) + "  ha tenido los siguientes errores: <br>");
				for (String mensaje : resultado.getListaError()) {
					resultadoHtml.append("- " + mensaje);
					resultadoHtml.append("<br>");
				}
			}
			if (resultado.getListaOk() != null && !resultado.getListaOk().isEmpty()) {
				resultadoHtml.append("El proceso de gestion de colas de IMPR con fecha " + new SimpleDateFormat("dd/MM/yyyy").format(fecha) + "  ha tenido los siguientes creaciones: <br>");
				for (String mensaje : resultado.getListaOk()) {
					resultadoHtml.append("- " + mensaje);
					resultadoHtml.append("<br>");
				}
			}
			String subject = gestorPropiedades.valorPropertie("subject.correo.colasAuto");
			String direcciones = gestorPropiedades.valorPropertie("direccion.correo.colasAuto");
			ResultBean resultEnvio =
					servicioCorreo.enviarCorreo(resultadoHtml.toString(),Boolean.FALSE, null, subject, direcciones , null, null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con el resultado de la creacion de colas, error: ",e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de enviar el correo con el resultado de la creacion de colas.");
			resultado.setError(Boolean.TRUE);
		}
	}

	private void enviarMailErrorCerrarSession(StringBuffer texto, String subject) {
		try {
			String direcciones = gestorPropiedades.valorPropertie(EITV_CORREO_RESULTADO_PARA);
			ResultBean resultEnvio =
					servicioCorreo.enviarCorreo(texto.toString(),Boolean.FALSE, null, subject, direcciones , null, null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error("Error al enviar el correo con el error a la hora de cerrar la session de IMPR, error: " + mensaje);
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con el resultado al cerrar la session de IMPR, error: ",e);
		}
	}

	@Override
	public ResultadoPermisoDistintivoItvBean crearPeticionImpr(ContratoDto contratoDto, String proceso, String datos, String tipoTramite) {
		ResultadoPermisoDistintivoItvBean resultadoCola = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			ResultBean resultCola = servicioCola.crearSolicitud(proceso,datos,
					gestorPropiedades.valorPropertie(NOMBRE_HOST), tipoTramite,
					null, contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),null,contratoDto.getIdContrato());
			if (resultCola.getError()) {
				resultadoCola.setError(Boolean.TRUE);
				resultadoCola.setMensaje(resultCola.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de crear la cola para las solicitudes de IMPR del contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia() + ", error: ",e);
			resultadoCola.setError(Boolean.TRUE);
			resultadoCola.setMensaje("Ha sucedido un error a la hora de crear la cola para las solicitudes de IMPR del contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia());
		}
		return resultadoCola;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean imprDemanda(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(solicitud.getIdTramite() != null){
				if(!comprobarColasActivaImprNocturno()){
					TramiteTraficoVO  tramiteTrafico = servicioTramiteTrafico.getTramite(solicitud.getIdTramite(), Boolean.TRUE);
					if (tramiteTrafico != null) {
						if (comprobarJefatura(tramiteTrafico.getJefaturaTrafico().getJefatura())){
							log.info(ProcesosEnum.IMPR_DEMANDA.getNombreEnum() + " Tramite obtenido" + tramiteTrafico.getNumExpediente());
							resultado = servicioNavegacionImpr.iniciarNavegador( resultado, DEMANDA);
							if (!resultado.getError()) {
								resultado = servicioNavegacionImpr.doLogin(resultado, tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial());
								if (!resultado.getError()) {
									resultado = servicioNavegacionImpr.comprobarSessionActivaYSegundoLogin(resultado, tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial());
									if (!resultado.getError()) {
										String matricula = tramiteTrafico.getVehiculo().getMatricula();
										String dniColegiado = tramiteTrafico.getContrato().getColegiado().getUsuario().getNif();
										resultado = servicioNavegacionImpr.buscarDocImpr(matricula,dniColegiado,tramiteTrafico.getNumExpediente(), resultado, tramiteTrafico.getTipoTramite(), tramiteTrafico.getFechaPresentacion());
										if (!resultado.getError()) {
											if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(solicitud.getTipoTramite())){
												resultado = servicioNavegacionImpr.obtenerFichaDemanda( resultado,DEMANDA);
												if (!resultado.getErrorFichas()) {
													byte[] fichero = resultado.getByteFichero();
													gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA,
															Utilidades.transformExpedienteFecha(tramiteTrafico.getNumExpediente()),tramiteTrafico.getNumExpediente() + "_"+TipoImpreso.SolicitudFichaTecnica.getNombreEnum() 
															, ConstantesGestorFicheros.EXTENSION_PDF, fichero);
												} else {
													resultado.setError(Boolean.TRUE);
													resultado.setMensaje(resultado.getMensajeErrorFichas());
												}
											} else if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(solicitud.getTipoTramite())){
												resultado = servicioNavegacionImpr.obtenerPermisoDemanda( resultado,DEMANDA);
												if (!resultado.getErrorPermisos()) {
													byte[] fichero = resultado.getByteFichero();
													gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO,
															Utilidades.transformExpedienteFecha(tramiteTrafico.getNumExpediente()),tramiteTrafico.getNumExpediente()+"_"+ TipoImpreso.SolicitudPermiso.getNombreEnum()
															, ConstantesGestorFicheros.EXTENSION_PDF, fichero);
												} else {
													resultado.setError(Boolean.TRUE);
													resultado.setMensaje(resultado.getMensajeErrorPermisos());
												}
											}
										}
									}
								}
							}
						} else {
							tramiteTrafico.setEstadoSolPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
							servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("La jefatura del contrato no permite solicitar impresión de Permisos y fichas técnicas");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se pueden obtener los documentos porque no existen datos en BBDD para el expediente: " + solicitud.getIdTramite());
					}
				} else {
					resultado.setExisteImprNocturno(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pueden obtener los documentos porque no se le ha indicado un numero de expediente");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar los documentos de IMPR a demanda, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e));
		}
		if (!resultado.getExisteImprNocturno()) {
			ResultadoPermisoDistintivoItvBean resultCerrarSession = servicioNavegacionImpr.cerrarSession();
			if (resultCerrarSession.getError()) {
				String subject = "Error Cerrar Session IMPR Demanda";
				StringBuffer resultadoHtml = new StringBuffer();
				resultadoHtml.append("El proceso de IMPR a demanda ha fallado a la hora de cerrar la session por el siguiente motivo.").append("<br>");
				resultadoHtml.append("- " + resultCerrarSession.getMensaje());
				resultadoHtml.append("<br>");
				resultadoHtml.append("- Expediente: " + solicitud.getIdTramite());
				resultadoHtml.append("<br>");
				resultadoHtml.append("- Tipo de IMPR: " + TipoTramiteTrafico.convertirTexto(solicitud.getTipoTramite()));
				enviarMailErrorCerrarSession(resultadoHtml, subject);
			}
		}
		return resultado;
	}

	private boolean comprobarColasActivaImprNocturno() {
		Boolean existe = servicioCola.comprobarColasProcesoActivas(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), null, null);
		if (!existe) {
			return servicioCola.comprobarColasProcesoActivas(ProcesosAmEnum.IMPR_NOCTURNO.getValorEnum(), null, null);
		}
		return existe;
	}

	private boolean comprobarJefatura(String jefaturaTramite) {
		String jefaturaMadrid = gestorPropiedades.valorPropertie("permiso.jefatura.impr.demanda");
		String[] jefaturas = jefaturaMadrid.split(",");
		for (String jpt : jefaturas) {
			if (jefaturaTramite.equals(jpt)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void actualizarEstadoPeticion(ColaDto solicitud, EstadoPermisoDistintivoItv estadoNuevo) {
		TramiteTraficoVO  tramiteTrafico = servicioTramiteTrafico.getTramite(solicitud.getIdTramite(), Boolean.TRUE);
		if (tramiteTrafico != null) {
			BigDecimal idUsuario = obtenerUsuarioImpr(tramiteTrafico);
			String estadoAnt = null;
			ResultadoPermisoDistintivoItvBean resultado = null;
			if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(solicitud.getTipoTramite())) {
				estadoAnt = tramiteTrafico.getEstadoSolFicha(); 
				if (EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(estadoNuevo.getValorEnum())) {
					Long numImp = tramiteTrafico.getNumImpresionesFicha();
					tramiteTrafico.setFichaTecnica("S");
					if (numImp != null) {
						tramiteTrafico.setNumImpresionesFicha(numImp + 1);
					} else {
						tramiteTrafico.setNumImpresionesFicha(1L);
					}
				}
				tramiteTrafico.setEstadoSolFicha(estadoNuevo.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafico.getNumExpediente(),idUsuario,
						TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.SOLICITUD, new Date(),estadoAnt, 
						estadoNuevo.getValorEnum(),null,"proceso");
			} else {
				estadoAnt = tramiteTrafico.getEstadoSolPerm();
				if (EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(estadoNuevo.getValorEnum())) {
					Long numImp = tramiteTrafico.getNumImpresionesPerm();
					tramiteTrafico.setPermiso("S");
					tramiteTrafico.setNumImpresionesPerm(numImp != null ? numImp + 1 : 1L);
				}
				tramiteTrafico.setEstadoSolPerm(estadoNuevo.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);

				resultado = servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafico.getNumExpediente(), idUsuario,
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOLICITUD, new Date(), estadoAnt,
						estadoNuevo.getValorEnum(), null,"proceso");
			}
			// Pintar logs
			if (resultado.getError()) {
				log.error(resultado.getMensaje());
			} else {
				log.info("El estado del tramite: " +solicitud.getIdTramite() + " se ha cambiado correctamente.");
			}
		} else {
			log.error("No se ha podido actualizar el estado del tramites de IMPR porque no se han encontrados las properties con los idUsuarios para el expediente, idTramite: " + solicitud.getIdTramite());
		}
	}

	private BigDecimal obtenerUsuarioImpr(TramiteTraficoVO tramiteTrafico) {
		BigDecimal usuario = null;
		List<UsuarioVO> listaDatosUsuario = null;
		if (JefaturasJPTEnum.MADRID.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.madrid"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()) {
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.alcorcon"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()) {
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.alcala"));
			if(listaDatosUsuario != null && !listaDatosUsuario.isEmpty()){
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.avila"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()) {
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.cdReal"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()){
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.cuenca"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()){
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.segovia"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()){
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(tramiteTrafico.getJefaturaTrafico().getJefaturaProvincial())) {
			listaDatosUsuario = servicioUsuario.getUsuarioPorNif(gestorPropiedades.valorPropertie("eitv.login.usuario.guadalajara"));
			if (listaDatosUsuario != null && !listaDatosUsuario.isEmpty()){
				usuario = new BigDecimal(listaDatosUsuario.get(0).getIdUsuario());
			}
		}
		return usuario;
	}

	@Override
	@Transactional
	public void finalizarErrorColaImprNocturnoNoExisteDocImpr(ColaDto solicitud, String mensaje) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites  = null;
			if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(solicitud.getTipoTramite())) {
				listaTramites = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(solicitud.getIdTramite().longValue());
			} else {
				listaTramites = servicioTramiteTrafico.getListaTramitesFichasTecnicasPorDocId(solicitud.getIdTramite().longValue());
			}
			if (listaTramites != null && !listaTramites.isEmpty()) {
				TipoDocumentoImprimirEnum tipoDocumento = null;
				servicioDocPrmDstvFicha.actualizarEstado(servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(), Boolean.FALSE), 
						EstadoPermisoDistintivoItv.IMPR_No_Encontrado, solicitud.getIdUsuario(), OperacionPrmDstvFicha.MODIFICACION);
				for (TramiteTraficoVO tramiteTrafMatrVO : listaTramites){
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(solicitud.getXmlEnviar().split("_")[1])) {
						if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(solicitud.getTipoTramite())) {
							tramiteTrafMatrVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum());
							tipoDocumento = TipoDocumentoImprimirEnum.PERMISO_CIRCULACION;
						} else {
							tramiteTrafMatrVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum());
							tipoDocumento = TipoDocumentoImprimirEnum.FICHA_TECNICA;
						}
					} else {
						tramiteTrafMatrVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum());
						tipoDocumento = TipoDocumentoImprimirEnum.PERMISO_CIRCULACION;
					}
					servicioTramiteTrafico.actualizarTramite(tramiteTrafMatrVO);
					resultado = servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(),solicitud.getIdUsuario(),
							tipoDocumento,OperacionPrmDstvFicha.SOLICITUD, new Date(),EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), 
							EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum(),null,"proceso");
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de finalizar con error los tramites del impr_nocturno, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e));
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprNocturno(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		WebDriver driver = null;
		try {
			if (solicitud.getIdContrato() != null) {
				ContratoDto contrato = servicioContrato.getContratoDto(solicitud.getIdContrato());
				String[] datosSolicitud = solicitud.getXmlEnviar().split("_");
				List<TramiteTraficoVO> listaTramites = null;
				if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(solicitud.getTipoTramite())) {
					listaTramites = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(solicitud.getIdTramite().longValue());
				} else {
					listaTramites = servicioTramiteTrafico.getListaTramitesFichasTecnicasPorDocId(solicitud.getIdTramite().longValue());
				}
				if(listaTramites != null && !listaTramites.isEmpty()){
					DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(), Boolean.FALSE);
					if(docPermDistItvVO != null){
						List<String> listaMatriculas = obtenerListaMatriculas(listaTramites);
						if (listaMatriculas != null){
							log.info(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum() + " Contrato: " + contrato.getColegiadoDto().getNumColegiado() + "_" + contrato.getIdContrato());
							resultado = servicioNavegacionImpr.iniciarNavegador(resultado, DEMANDA);
							if (!resultado.getError()){
								resultado = servicioNavegacionImpr.doLogin(resultado, contrato.getJefaturaTraficoDto().getJefaturaProvincial());
								if (!resultado.getError()){
									resultado = servicioNavegacionImpr.comprobarSessionActivaYSegundoLogin(resultado, contrato.getJefaturaTraficoDto().getJefaturaProvincial());
									if(!resultado.getError()){
										resultado = servicioNavegacionImpr.buscarDocImprNocturno(listaMatriculas, contrato.getIdContrato(), contrato.getColegiadoDto().getUsuario().getNif(), resultado, datosSolicitud[1],new SimpleDateFormat("ddMMyyyy").parse(solicitud.getXmlEnviar().split("_")[0]), solicitud.getTipoTramite());
										if(!resultado.getError()){
											TipoDocumentoImprimirEnum tipoDocumento = null;
											if(TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(solicitud.getTipoTramite())){
												guardarDocNocturnos(resultado.getListaPdfPermisos(), solicitud.getIdTramite() + "_" + TipoImpreso.SolicitudPermiso.getNombreEnum(), ConstantesGestorFicheros.PERMISOS_DEFINITIVO, docPermDistItvVO.getFechaAlta());
												tipoDocumento = TipoDocumentoImprimirEnum.PERMISO_CIRCULACION;
											} else if(TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(solicitud.getTipoTramite())){
												tipoDocumento = TipoDocumentoImprimirEnum.FICHA_TECNICA;
												guardarDocNocturnos(resultado.getListaPdfFichas(), solicitud.getIdTramite() + "_" + TipoImpreso.SolicitudFichaTecnica.getNombreEnum(), ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, docPermDistItvVO.getFechaAlta());
											}
											servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvVO, EstadoPermisoDistintivoItv.ORDENADO_DOC, 
											contrato.getColegiadoDto().getUsuario().getIdUsuario(), OperacionPrmDstvFicha.CAMBIO_ESTADO);
											generarPeticionImpresion(solicitud.getIdTramite().longValue(),contrato.getColegiadoDto().getUsuario().getIdUsuario(),
														contrato.getIdContrato(),tipoDocumento, resultado, datosSolicitud[1], datosSolicitud[0]);
										}
									}
								}
							}
						} else {
							resultado.setMensaje("No existen datos de los vehículos finalizados de " + TipoTramiteTrafico.convertirTexto(datosSolicitud[1]) + " aptos para obtener su documentación.");
							resultado.setError(Boolean.TRUE);
						}
					} else {
						resultado.setMensaje("No existen datos del Documento: " + solicitud.getIdTramite() + " para poder obtener sus datos.");
						resultado.setError(Boolean.TRUE);
					}
				} else {
					resultado.setMensaje("No existen tramites finalizados de " + TipoTramiteTrafico.convertirTexto(datosSolicitud[1]) + " aptos para obtener su documentación.");
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pueden obtener los documentos porque no se le ha indicado un numero de contrato");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solicitar los documentos de IMPR nocturnos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e));
		}
		ResultadoPermisoDistintivoItvBean resultCerrarSession = servicioNavegacionImpr.cerrarSession();
		if (resultCerrarSession.getError()) {
			String subject = "Error Cerrar Session IMPR Nocturno";
			StringBuffer resultadoHtml = new StringBuffer();
			resultadoHtml.append("El proceso de IMPR nocturno ha fallado a la hora de cerrar la session por el siguiente motivo.").append("<br>");
			resultadoHtml.append("- " + resultCerrarSession.getMensaje());
			resultadoHtml.append("<br>");
			resultadoHtml.append("- Contrato: " + solicitud.getIdContrato());
			resultadoHtml.append("<br>");
			resultadoHtml.append("- Tipo de IMPR: " + TipoTramiteTrafico.convertirTexto(solicitud.getTipoTramite()));
			enviarMailErrorCerrarSession(resultadoHtml, subject);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprErrores(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (solicitud.getIdContrato() != null) {
				ContratoVO contrato = servicioContrato.getContrato(solicitud.getIdContrato());
				String[] datosSolicitud = solicitud.getXmlEnviar().split("_");
				List<TramiteTraficoVO> listaTramites = obtenerListaTramitesErroneos(solicitud);
				if(listaTramites != null && !listaTramites.isEmpty()){
					List<String> listaMatriculas = obtenerListaMatriculas(listaTramites);
					if(listaMatriculas != null){
						log.info(ProcesosEnum.IMPR_ERRORES.getNombreEnum() + " Contrato: " + contrato.getColegiado().getNumColegiado() + "_" + contrato.getIdContrato());
						resultado = servicioNavegacionImpr.iniciarNavegador(resultado, DEMANDA);
						if(!resultado.getError()){
							resultado = servicioNavegacionImpr.doLogin(resultado,contrato.getJefaturaTrafico().getJefaturaProvincial());
							if(!resultado.getError()){
								resultado = servicioNavegacionImpr.comprobarSessionActivaYSegundoLogin(resultado,contrato.getJefaturaTrafico().getJefaturaProvincial());
								if(!resultado.getError()){
									resultado = servicioNavegacionImpr.buscarDocImprNocturnoError(listaMatriculas, solicitud.getIdContrato(), contrato.getColegiado().getUsuario().getNif(), resultado, datosSolicitud[2],datosSolicitud[0]);
									if(!resultado.getError()){
										Date fechaGen = new Date();
										TipoDocumentoImprimirEnum tipoDocumento = null;
										List<byte[]> listaPdf = null;
										if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(datosSolicitud[0])){
											tipoDocumento = TipoDocumentoImprimirEnum.FICHA_TECNICA;
											if(!resultado.getNoExistenDoc()){
												listaPdf = resultado.getListaPdfFichas();
											}
										} else {
											tipoDocumento = TipoDocumentoImprimirEnum.PERMISO_CIRCULACION;
											if(!resultado.getNoExistenDoc()){
												listaPdf = resultado.getListaPdfPermisos();
											}
										}
										resultado = generarDocImpresionErroneos(listaTramites, contrato,
												fechaGen, tipoDocumento, solicitud.getIdUsuario(),
												solicitud, resultado.getNoExistenDoc(), datosSolicitud[2], listaPdf);
									}
								}
							}
						}
					} else {
						resultado.setMensaje("No existen datos de los vehículos finalizados de " + TipoTramiteTrafico.convertirTexto(datosSolicitud[1]) + " aptos para obtener su documentación.");
						resultado.setError(Boolean.TRUE);
					}
				} else {
					resultado.setMensaje("No existen tramites finalizados de " + TipoTramiteTrafico.convertirTexto(datosSolicitud[1]) + " aptos para obtener su documentación.");
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pueden obtener los documentos porque no se le ha indicado un numero de contrato");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solicitar los documentos de IMPR nocturnos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e));
		}
		ResultadoPermisoDistintivoItvBean resultCerrarSession = servicioNavegacionImpr.cerrarSession();
		if (resultCerrarSession.getError()) {
			String subject = "Error Cerrar Session IMPR Errores";
			StringBuffer resultadoHtml = new StringBuffer();
			resultadoHtml.append("El proceso de IMPR nocturno ha fallado a la hora de cerrar la session por el siguiente motivo.").append("<br>");
			resultadoHtml.append("- " + resultCerrarSession.getMensaje());
			resultadoHtml.append("<br>");
			resultadoHtml.append("- Contrato: " + solicitud.getIdContrato());
			resultadoHtml.append("<br>");
			resultadoHtml.append("- Tipo de IMPR: " + TipoTramiteTrafico.convertirTexto(solicitud.getTipoTramite()));
			enviarMailErrorCerrarSession(resultadoHtml, subject);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ResultadoPermisoDistintivoItvBean generarDocImpresionErroneos(List<TramiteTraficoVO> listaTramites, ContratoVO contrato, 
			Date fechaGen, TipoDocumentoImprimirEnum tipoDocImprimir, BigDecimal idUsuarioImpr, 
			ColaDto solicitud, Boolean noExisteDoc, String tipoTramite, List<byte[]> listaPdf) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(new Long(solicitud.getXmlEnviar().split("_")[1]),
				Boolean.TRUE);
		if(!noExisteDoc){
			ResultadoPermisoDistintivoItvBean resultGenDoc = servicioDocPrmDstvFicha.generarDoc(new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()), fechaGen, 
					tipoDocImprimir, null, contrato.getIdContrato().longValue(), contrato.getJefaturaTrafico().getJefaturaProvincial(), Boolean.FALSE);
			if(!resultGenDoc.getError()){
				resultado = servicioImpresionPermisosFichasDgtImpl.impresionPermisoFichasErroneos(listaPdf,listaTramites, resultGenDoc.getDocPermDistItv(), tipoTramite, contrato, docPermDistItvVO, "NV");
			} else {
				resultado = resultGenDoc;
			}
		} else {
			for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
				tramiteTraficoVO.setDocPermiso(null);
				tramiteTraficoVO.setDocPermisoVO(null);
				tramiteTraficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTraficoVO.setExcelKoImpr("N");
				servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
				servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
						new BigDecimal(contrato.getColegiado().getUsuario().getIdUsuario()),
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.ERROR_GENERADO, new Date(),EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum(), 
						EstadoPermisoDistintivoItv.Iniciado.getValorEnum(),null,"proceso");
			}
			resultado = servicioDocPrmDstvFicha.generarDocGestoriaErroresTramites(listaTramites, docPermDistItvVO, tipoDocImprimir, tipoTramite, contrato,"NV");
		}
		return resultado;
	}

	public void actualizarCamposTramitePorTipo(TramiteTraficoVO tramiteTraficoVO, Boolean esError, Long idDocPermDstvEitv, TipoDocumentoImprimirEnum tipoDoc) {
		if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc.getValorEnum())){
			if (tramiteTraficoVO.getNumImpresionesFicha()!= null){
				tramiteTraficoVO.setNumImpresionesFicha(tramiteTraficoVO.getNumImpresionesFicha() + 1);
			} else {
				tramiteTraficoVO.setNumImpresionesFicha(1L);
			}
			if (!esError) {
				tramiteTraficoVO.setFichaTecnica("S");
				tramiteTraficoVO.setDocFichaTecnica(idDocPermDstvEitv);
				tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
				tramiteTraficoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
			} else {
				tramiteTraficoVO.setFichaTecnica("N");
				tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
			}
		} else {
			if (tramiteTraficoVO.getNumImpresionesPerm() != null) {
				tramiteTraficoVO.setNumImpresionesPerm(tramiteTraficoVO.getNumImpresionesPerm() + 1);
			} else {
				tramiteTraficoVO.setNumImpresionesPerm(1L);
			}
			if(!esError){
				tramiteTraficoVO.setPermiso("S");
				tramiteTraficoVO.setDocPermiso(idDocPermDstvEitv);
				tramiteTraficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
				tramiteTraficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
			} else {
				tramiteTraficoVO.setPermiso("N");
				tramiteTraficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
			}
		}
	}

	private void guardarDocNocturnos(List<byte[]> listaPdf, String nombreFichero, String subtipo, Date fechaGen) throws OegamExcepcion {
		if(listaPdf.size() > 1){
			int cont = 0;
			for(byte[] pdf : listaPdf){
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subtipo,
						utilesFecha.getFechaConDate(fechaGen),nombreFichero + "_" + cont++ , ConstantesGestorFicheros.EXTENSION_PDF, pdf);
			}
		} else {
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subtipo,
					utilesFecha.getFechaConDate(fechaGen),nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, listaPdf.get(0));
		}
	}

	private void generarPeticionImpresion(Long idDoc, BigDecimal idUsuario, BigDecimal idContrato, TipoDocumentoImprimirEnum tipoDoc, ResultadoPermisoDistintivoItvBean resultado, String tipoTramite, String fechaPresentacion) {
		try {
			String proceso = "";
			String xml_enviar = fechaPresentacion;
			if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDoc.getValorEnum())) {
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					proceso = ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
					xml_enviar += "_" + ProcesosEnum.IMP_PRM_MATW.getNombreEnum() + "_" + idDoc;
				} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
					proceso = ProcesosEnum.IMP_PRM_CTIT.getNombreEnum();
					xml_enviar += "_" + ProcesosEnum.IMP_PRM_CTIT.getNombreEnum() + "_" + idDoc;
				}
			} else {
				proceso = ProcesosEnum.IMP_FICHA.getNombreEnum();
				xml_enviar += "_" + ProcesosEnum.IMP_PRM_MATW.getNombreEnum() + "_" + idDoc;
			}
			ResultBean resultBean = servicioCola.crearSolicitud(proceso, xml_enviar, 
					gestorPropiedades.valorPropertie(NOMBRE_HOST), tipoDoc.getValorEnum(), 
					idDoc.toString(), idUsuario, null, idContrato);
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora encolar la peticion de impresion de los documentos de IMPR, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de los documentos de IMPR.");
		}
	}

	private List<String> obtenerListaMatriculas(List<TramiteTraficoVO> listaTramites) {
		List<String> listaMatriculas = new ArrayList<>();
		for(TramiteTraficoVO tramiteTraficoVO : listaTramites) {
			listaMatriculas.add(tramiteTraficoVO.getVehiculo().getMatricula());
		}
		return listaMatriculas;
	}

	private List<TramiteTraficoVO> obtenerListaTramitesErroneos(ColaDto solicitud) throws ParseException {
		Long docId = Long.parseLong(solicitud.getXmlEnviar().split("_")[1]);
		String tipoDocumento = solicitud.getXmlEnviar().split("_")[0];
		if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento)){
			return servicioTramiteTrafico.getListaTramitesPermisosPorDocIdErroneos(docId);
		} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento)) {
			return servicioTramiteTrafico.getListaTramitesFichasTecnicasPorDocIdErroneos(docId);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoExcelKOBean generarExcelKO(JefaturasJPTEnum jefatura) {
		ResultadoExcelKOBean resultado = new ResultadoExcelKOBean(Boolean.FALSE);
		String nombreFichero = null;
		try {
			List<TramiteTraficoVO> listaKO = servicioTramiteTrafico.getListaTramitesKOPendientesPorJefatura(jefatura.getJefatura());
			if(listaKO != null && !listaKO.isEmpty()){
				resultado = generarExcelJefaturaKO(listaKO, jefatura);
				if(!resultado.getError()){
					nombreFichero = resultado.getNombreFichero();
					resultado = actualizarExcelImprKO(listaKO);
					if(!resultado.getError()){
						resultado = enviarMailImprKO(nombreFichero, JefaturasJPTEnum.MADRID);
					}
				}
			} else {
				resultado.setMensaje("No existen tramites KO para la jefatura.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel con los KO, error: ",e);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con los KO.");
			resultado.setError(Boolean.TRUE);
		}
		if(resultado.getError()){
			if(nombreFichero != null && !nombreFichero.isEmpty()){
				try {
					gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.EXCELS, ConstantesGestorFicheros.ENVIOS_IMPR_KO, 
						utilesFecha.getFechaActual(), nombreFichero + ConstantesGestorFicheros.EXTENSION_XLS);
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de boorar el fichero excel con los IMPR KO para la jefatura: " + jefatura.getJefatura() + ", error: ",e);
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoExcelKOBean actualizarExcelImprKO(List<TramiteTraficoVO> listaKO) {
		ResultadoExcelKOBean resultado = new ResultadoExcelKOBean(Boolean.FALSE);
		try {
			for(TramiteTraficoVO tramite : listaKO){
				tramite.setExcelKoImpr("S");
				servicioTramiteTrafico.actualizarTramite(tramite);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el campo de IMPR_KO, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el campo de IMPR_KO.");
		}
		return resultado;
	}

	private ResultadoExcelKOBean enviarMailImprKO(String nombreFichero, JefaturasJPTEnum jefatura) {
		ResultadoExcelKOBean resultado = new ResultadoExcelKOBean(Boolean.FALSE);
		try {
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXCELS, ConstantesGestorFicheros.ENVIOS_IMPR_KO, 
					utilesFecha.getFechaActual(), nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS);
			if(fichero != null && fichero.getFile() != null){
				FicheroBean fileBean = new FicheroBean();
				fileBean.setFichero(fichero.getFile());
				fileBean.setNombreDocumento(nombreFichero + ConstantesGestorFicheros.EXTENSION_XLS);
				String to = null;
				if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())) {
					to = gestorPropiedades.valorPropertie("direccion.excel.imprKOAS.mail.destinatario");
				} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())) {
					to = gestorPropiedades.valorPropertie("direccion.excel.imprKOALC.mail.destinatario");
				}else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())) {
					to = gestorPropiedades.valorPropertie("direccion.excel.imprKOAH.mail.destinatario");
				}

				String direccionesOcultas = gestorPropiedades.valorPropertie("direccion.excel.imprKO.mail.destinatario.oculto");
				String subject = "Fichero Excel con KO IMPR";
				String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), el envío del siguiente fichero Excel con los KO de IMPR.<br><br></span>";
				ResultBean resultEnvio = servicioCorreo.enviarCorreo(texto, null, null, subject, to, null, direccionesOcultas, null, fileBean);
				if (resultEnvio.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultEnvio.getMensaje());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el mail con los KO de IMPR a la jefatura: " + jefatura.getDescripcion() + " ,error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el mail con los KO de IMPR");
		}
		return resultado;
	}

	private ResultadoExcelKOBean generarExcelJefaturaKO(List<TramiteTraficoVO> listaKo, JefaturasJPTEnum jefatura) {
		ResultadoExcelKOBean resultado = new ResultadoExcelKOBean(Boolean.FALSE);
		WritableWorkbook copyWorkbook = null;
		try {
			resultado.setNombreFichero("IMPR_KO_ICOGAM_" + jefatura.getDescripcion() + "_" + utilesFecha.getCadenaFecha('_'));
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(".xls");
			fichero.setNombreDocumento(resultado.getNombreFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.ENVIOS_IMPR_KO);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(resultado.getNombreFichero()));

			File archivo = gestorDocumentos.guardarFichero(fichero);
			copyWorkbook = Workbook.createWorkbook(archivo);
			WritableSheet sheet = copyWorkbook.createSheet(IMPRKOGESTORES, 0);
			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			for (int i = 0; i <= 3; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuente);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);
			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			Label label = new Label(0, 0, "NUM_COLEGIADO", formatoCabecera);
			sheet.addCell(label);
			label = new Label(1, 0, "MATRICULA", formatoCabecera);
			sheet.addCell(label);
			label = new Label(2, 0, "FECHA_PRESENTACION", formatoCabecera);
			sheet.addCell(label);

			int cont=1;
			for(TramiteTraficoVO tramiteTrafico : listaKo){

				label = new Label(0, cont, tramiteTrafico.getNumColegiado(), formatoDatos);
				sheet.addCell(label);

				label = new Label(1, cont, tramiteTrafico.getVehiculo().getMatricula(), formatoDatos);
				sheet.addCell(label);

				label = new Label(2, cont, new SimpleDateFormat("dd/MM/YYYY").format(tramiteTrafico.getFechaPresentacion()), formatoDatos);
				sheet.addCell(label);

				cont++;
			}
			copyWorkbook.write();
		} catch (Throwable e) {
			log.error("Ha sucedido un erro a la hora de generar el excel para la jefatura: " + jefatura.getJefatura() +", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un erro a la hora de generar el excel.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (Throwable e) {
					log.error("Error al generar el excel con los KO de IMPR, error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al generar el excel con los KO de IMPR.");
				}
			}
		}

		return resultado;
	}

}