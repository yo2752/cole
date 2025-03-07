package org.gestoresmadrid.oegam2comun.consultaKo.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.consultaKo.model.enumerados.EstadoKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.OperacionConsultaKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoDocumentoKoEnum;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoTramiteKo;
import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioConsultaKo;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioPersistenciaKo;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ConsultaKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.GenKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.JefaturaTipoGenKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
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
import jxl.write.WriteException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaKoImpl implements ServicioConsultaKo {

	private static final long serialVersionUID = 9051719627525650449L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaKoImpl.class);

	private static final String DESTINATARIOS_EXCEL_KO = "destinatarios.excel.ko";

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioPersistenciaKo servicioPersistenciaKo;

	@Autowired
	ServicioComunContrato servicioContrato;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrm;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public List<ConsultaKoBean> convertirListaEnBeanPantallaConsulta(List<ConsultaKoVO> listaBBDD) {
		List<ConsultaKoBean> listaKo = new ArrayList<>();
		for(ConsultaKoVO consultaKoVo : listaBBDD) {
			ConsultaKoBean consultaKoBean = new ConsultaKoBean();
			consultaKoBean.setDescContrato(consultaKoVo.getContrato().getColegiado().getNumColegiado() + " - " + consultaKoVo.getContrato().getVia());
			consultaKoBean.setEstado(EstadoKo.convertirEstadoBigDecimal(new BigDecimal(consultaKoVo.getEstado())));
			consultaKoBean.setFecha(utilesFecha.formatoFecha("dd/MM/yyyy", consultaKoVo.getFecha()));
			consultaKoBean.setId(consultaKoVo.getId());
			consultaKoBean.setJefatura(JefaturasJPTEnum.convertirJefatura(consultaKoVo.getJefatura()));
			consultaKoBean.setMatricula(consultaKoVo.getMatricula());
			consultaKoBean.setTipo(TipoDocumentoKoEnum.convertirValor(consultaKoVo.getTipo()));
			consultaKoBean.setTipoTramite(TipoTramiteKo.convertirValor(consultaKoVo.getTipoTramite()));

			listaKo.add(consultaKoBean);
		}
		return listaKo;
	}

	@Override
	public ResultadoConsultaKoBean cambiarEstado(String codSeleccionados, String estadoNuevo, Long idUsuario) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsConsultaKo = codSeleccionados.split(";");
				for (String idConsultaKo : idsConsultaKo) {
					try {
						ResultadoConsultaKoBean resultEstado = servicioPersistenciaKo.cambiarEstadoConsultaConEvolucion(new Long(idConsultaKo), estadoNuevo, idUsuario);
						if (resultEstado.getError()) {
							resultado.addResumenError(resultEstado.getMensaje());
						} else {
							resultado.addResumenOK(resultEstado.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambair el estado de la consulta KO con el id: " + idConsultaKo + ", error: ",e);
						resultado.addResumenError("Ha sucedido un error a la hora de cambair el estado de la consulta KO con el id: " + idConsultaKo);
					}
				}
			} else {
				resultado.setMensaje("Debe de seleccionar algún KO para generar.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar a demanda el excel con los KO, error: ", e);
			resultado.setMensaje("Ha sucedido un error a la hora de generar a demanda el excel con los KO");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean crearConsultaKoTramite(TramiteTraficoVO tramiteTraficoVO, String tipo, Date fecha){
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			ConsultaKoVO consultaKoVo = new ConsultaKoVO();
			consultaKoVo.setIdContrato(tramiteTraficoVO.getContrato().getIdContrato());
			consultaKoVo.setIdUsuario(tramiteTraficoVO.getUsuario().getIdUsuario());
			consultaKoVo.setFecha(fecha);
			consultaKoVo.setJefatura(tramiteTraficoVO.getJefaturaTrafico().getJefaturaProvincial());
			consultaKoVo.setTipo(tipo);
			consultaKoVo.setTipoTramite(tramiteTraficoVO.getTipoTramite());
			consultaKoVo.setEstado(EstadoKo.Iniciado.getValorEnum());
			consultaKoVo.setMatricula(tramiteTraficoVO.getVehiculo().getMatricula());
			consultaKoVo.setNumExpediente(tramiteTraficoVO.getNumExpediente().longValue());
			servicioPersistenciaKo.guardarConEvolucion(consultaKoVo, null, new Date(), OperacionConsultaKo.CREACION);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar en la consulta KO", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar en la consulta KO");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaKoBean generarExcelDemanda(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsConsultaKo = codSeleccionados.split(";");
				List<ConsultaKoVO> listaConsultaKO = servicioPersistenciaKo.getListaConsultaKO(utiles.convertirStringArrayToLongArray(idsConsultaKo));
				resultado = validarGenExcelKoDemanda(listaConsultaKO);
				if (!resultado.getError() && resultado.getListaIdsConsultaKO() != null && !resultado.getListaIdsConsultaKO().isEmpty()) {
					ResultadoConsultaKoBean resultSolGenKO = servicioPersistenciaKo.solicitarGenExcelDemanda(resultado.getListaIdsConsultaKO(), idUsuario.longValue(), new Date());
					if (resultSolGenKO.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultSolGenKO.getMensaje());
					}
				}
			} else {
				resultado.setMensaje("Debe de seleccionar algún KO para generar.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar a demanda el excel con los KO, error: ",e);
			resultado.setMensaje("Ha sucedido un error a la hora de generar a demanda el excel con los KO");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoConsultaKoBean validarGenExcelKoDemanda(List<ConsultaKoVO> listaConsultaKO) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		if (listaConsultaKO == null || listaConsultaKO.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos en las base de datos para las consultas de KO seleccionadas.");
		} else if (servicioCola.existeColaProceso(ProcesosEnum.GEN_EXCEL_KO_IMPR.getNombreEnum(),gestorPropiedades.valorPropertie("nombreHostProceso"))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se pueden generar el excel de KO porque ya hay actualmente una peticion encolada para ello.");
		} else {
			for (ConsultaKoVO consultaKoVO : listaConsultaKO) {
				if (StringUtils.isBlank(consultaKoVO.getMatricula())) {
					resultado.addResumenError("El KO con ID: " + consultaKoVO.getId() + " no se puede generar en el excel porque no tiene matricula asociada.");
				} else if(StringUtils.isBlank(consultaKoVO.getTipoTramite())){
					resultado.addResumenError("El KO de la matricual: " + consultaKoVO.getMatricula() + " no se puede generar en el excel porque no tiene tipo tramite asociado.");
				} else if(!EstadoKo.Iniciado.getValorEnum().equals(consultaKoVO.getEstado()) && !EstadoKo.Finalizado_Con_Error.getValorEnum().equals(consultaKoVO.getEstado())){
					resultado.addResumenError("El KO de la matricula: " + consultaKoVO.getMatricula() + " y del tipo tramite: " + TipoTramiteTrafico.convertirTexto(consultaKoVO.getTipoTramite())
						+ " no se puede generar en el excel porque no se encuentra en un estado valido para ello.");
				} else {
					resultado.addListaIdsConsultaKO(consultaKoVO.getId());
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaKoBean generarExcelImprNocturno() {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		List<String> listaKoContratoError = new ArrayList<>();
		List<String> listaKoContratoOK = new ArrayList<>();
		try {
			List<ConsultaKoVO> listaKoBBDD = servicioPersistenciaKo.getListaKoPorEstado(EstadoKo.Iniciado.getValorEnum());
			if (listaKoBBDD != null && !listaKoBBDD.isEmpty()) {
				dividirConsultaKoPorJefaturaYTipo(listaKoBBDD, resultado);
				Date fechaGenExcel = new Date();
				for (JefaturaTipoGenKoBean jefaturaTipoGenKoBean : resultado.getListaKoJefaturas()) {
					try {
						ResultadoConsultaKoBean resultGenKoJefatura = generarExcelKoDemanda(jefaturaTipoGenKoBean, fechaGenExcel);
						if (!resultGenKoJefatura.getError()) {
							String nombreFichero = resultGenKoJefatura.getNombreFichero();
							ResultadoConsultaKoBean resultEnvioExcel = enviarMailExcelKo(resultGenKoJefatura.getFichero(), JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()),
									jefaturaTipoGenKoBean.getTipo(), TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite()), resultGenKoJefatura.getNombreFichero());
							if (!resultEnvioExcel.getError()) {
								resultado.addResumenOK("Excel con los KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + ", del tipo tramite: "+ TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite()) +
										" y de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()) + " se ha generado correctamente.");
								for (GenKoBean genKoBean : jefaturaTipoGenKoBean.getListaGenKo()) {
									try {
										ContratoVO contrato = servicioContrato.getContrato(genKoBean.getIdContrato());
										ResultadoConsultaKoBean resulGenKoContrato = gestionarKoContrato(genKoBean, null,jefaturaTipoGenKoBean.getTipo(), jefaturaTipoGenKoBean.getTipoTramite(), jefaturaTipoGenKoBean.getJefaturaProvincial(), fechaGenExcel, contrato, nombreFichero);
										if (resulGenKoContrato.getError()) {
											listaKoContratoError.add(resulGenKoContrato.getMensaje());
										} else {
											listaKoContratoOK.add("Listado KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + " para los tramites del tipo: " + TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite())
											+ " de la jefatura: " +JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()) + " para el contrato: " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia() + " generado correctamente.");
										}
									} catch (Exception e) {
										log.error("Ha sucedido un error a la hora de gestionar los KO para el contrato: " + genKoBean.getIdContrato().toString() + ", error: ",e);
										listaKoContratoError.add("Ha sucedido un error a la hora de gestionar los KO para el contrato: " + genKoBean.getIdContrato().toString());
									}
								}
							} else {
								resultado.addResumenError(resultEnvioExcel.getMensaje());
							}
						} else {
							resultado.addResumenError(resultGenKoJefatura.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de generar el excel con los KO de la jefatura: " + jefaturaTipoGenKoBean.getJefaturaProvincial() +
							", del tipo: " + jefaturaTipoGenKoBean.getTipo() + " y del tipo tramite: " + jefaturaTipoGenKoBean.getTipoTramite() + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de generar el excel con los KO de la jefatura: " + jefaturaTipoGenKoBean.getJefaturaProvincial() +
							", del tipo: " + jefaturaTipoGenKoBean.getTipo() + " y del tipo tramite: " + jefaturaTipoGenKoBean.getTipoTramite());
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen KO en estado iniciado para generar ningún excel.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel de los KO, error: ",e);
			resultado.setExcepcion(e);
		}
		if(!resultado.getError() && resultado.getExcepcion() == null){
			enviarMailResultadoGenExcelKO(resultado, listaKoContratoOK, listaKoContratoError,"Resumen Excel KO Nocturno");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaKoBean procesarPeticionGenExcelKoDemanda(Long idUsuario) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		List<String> listaKoContratoError = new ArrayList<>();
		List<String> listaKoContratoOK = new ArrayList<>();
		try {
			List<ConsultaKoVO> listaKoPendientes = servicioPersistenciaKo.getListaKoPorEstado(EstadoKo.Pdte_Generar_KO.getValorEnum());
			if(listaKoPendientes != null && !listaKoPendientes.isEmpty()){
				dividirConsultaKoPorJefaturaYTipo(listaKoPendientes, resultado);
				Date fechaGenExcel = new Date();
				for (JefaturaTipoGenKoBean jefaturaTipoGenKoBean : resultado.getListaKoJefaturas()) {
					try {
						ResultadoConsultaKoBean resultGenKoJefatura = generarExcelKoDemanda(jefaturaTipoGenKoBean, fechaGenExcel);
						if (!resultGenKoJefatura.getError()) {
							String nombreFichero = resultGenKoJefatura.getNombreFichero();
							ResultadoConsultaKoBean resultEnvioExcel = enviarMailExcelKo(resultGenKoJefatura.getFichero(), JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()),
									jefaturaTipoGenKoBean.getTipo(), TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite()), resultGenKoJefatura.getNombreFichero());
							if (!resultEnvioExcel.getError()) {
								resultado.addResumenOK("Excel con los KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + ", del tipo tramite: "+ TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite()) +
										" y de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()) + " se ha generado correctamente.");
								for (GenKoBean genKoBean : jefaturaTipoGenKoBean.getListaGenKo()) {
									try {
										ContratoVO contrato = servicioContrato.getContrato(genKoBean.getIdContrato());
										ResultadoConsultaKoBean resulGenKoContrato = gestionarKoContrato(genKoBean, idUsuario,jefaturaTipoGenKoBean.getTipo(), jefaturaTipoGenKoBean.getTipoTramite(), jefaturaTipoGenKoBean.getJefaturaProvincial(), fechaGenExcel, contrato, nombreFichero);
										if (resulGenKoContrato.getError()) {
											listaKoContratoError.add(resulGenKoContrato.getMensaje());
											resultado.addListaIdsConsultaKOError(genKoBean.getListaConsultaKo());
										} else {
											listaKoContratoOK.add("Listado KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + " para los tramites del tipo: " + TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite())
											+ " de la jefatura: " +JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()) + " para el contrato: " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia() + " generado correctamente.");
										}
									} catch (Exception e) {
										log.error("Ha sucedido un error a la hora de gestionar los KO para el contrato: " + genKoBean.getIdContrato().toString() + ", error: ",e);
										listaKoContratoError.add("Ha sucedido un error a la hora de gestionar los KO para el contrato: " + genKoBean.getIdContrato().toString());
										resultado.addListaIdsConsultaKOError(genKoBean.getListaConsultaKo());
									}
								}
							} else {
								resultado.addResumenError(resultEnvioExcel.getMensaje());
							}
						} else {
							resultado.addResumenError(resultGenKoJefatura.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de generar el excel con los KO de la jefatura: " + jefaturaTipoGenKoBean.getJefaturaProvincial() +
							", del tipo: " + jefaturaTipoGenKoBean.getTipo() + " y del tipo tramite: " + jefaturaTipoGenKoBean.getTipoTramite() + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de generar el excel con los KO de la jefatura: " + jefaturaTipoGenKoBean.getJefaturaProvincial() +
							", del tipo: " + jefaturaTipoGenKoBean.getTipo() + " y del tipo tramite: " + jefaturaTipoGenKoBean.getTipoTramite());
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen KO pendientes para generar ningún excel.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel de los KO, error: ",e);
			resultado.setExcepcion(e);
		}
		if (!resultado.getError() && resultado.getExcepcion() == null) {
			enviarMailResultadoGenExcelKO(resultado, listaKoContratoOK, listaKoContratoError, "Resumen Excel KO Demanda");
		}
		return resultado;
	}

	@Override
	public void actualizarEstadoErrorConsultaKoProceso(List<Long> listaIdsConsultaKo, Long idUsuario) {
		try {
			for(Long id : listaIdsConsultaKo){
				try {
					servicioPersistenciaKo.actualizarEstadoGenExcel(id, EstadoKo.Finalizado_Con_Error.getValorEnum(),idUsuario, new Date(), OperacionConsultaKo.GENERADO, null, null);
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de actualizar el estado de la consulta KO con id: " + id + ", error: ",e);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de las consultas KO que han finalizado con error, error: ",e);
		}
	}

	@Override
	public void finalizarConErrorDemanda(Long idUsuario) {
		try {
			List<ConsultaKoVO> listaKoPendientes = servicioPersistenciaKo.getListaKoPorEstado(EstadoKo.Pdte_Generar_KO.getValorEnum());
			if (listaKoPendientes != null && !listaKoPendientes.isEmpty()) {
				for (ConsultaKoVO consultaKoVO : listaKoPendientes) {
					try {
						servicioPersistenciaKo.actualizarEstadoGenExcel(consultaKoVO.getId(), EstadoKo.Finalizado_Con_Error.getValorEnum(), idUsuario, new Date(), OperacionConsultaKo.GENERADO, null, null);
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de actualizar el estado de la consulta KO con id: " + consultaKoVO.getId() + ", error: ", e);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de las consultas KO que han finalizado con error, error: ", e);
		}
	}

	private void enviarMailResultadoGenExcelKO(ResultadoConsultaKoBean resultado, List<String> listaKoContratoOK, List<String> listaKoContratoError, String asunto) {
		try {
			String destinatarios = gestorPropiedades.valorPropertie(DESTINATARIOS_EXCEL_KO);
			StringBuffer sb = getResumenEnvio(resultado, listaKoContratoOK, listaKoContratoError);
			servicioCorreo.enviarCorreo(sb.toString(), null, null, asunto, destinatarios, null, null, null);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el resumen de la generación del excel de KO, error: ", e);
		}
	}

	private StringBuffer getResumenEnvio(ResultadoConsultaKoBean resultado, List<String> listaKoContratoOK,	List<String> listaKoContratoError) {
		StringBuffer resultadoHtml = new StringBuffer();
		resultadoHtml.append("<br>Resumen de la generación del excel de KO a demanda.")
		.append(".<br></br>");

		resultadoHtml.append(cadenaTextoPlanoResultadoExcel(resultado));

		resultadoHtml.append(cadenaTextoPlanoResultadoKO(listaKoContratoOK, listaKoContratoError));

		return resultadoHtml;
	}

	private Object cadenaTextoPlanoResultadoKO(List<String> listaKoContratoOK,	List<String> listaKoContratoError) {
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");
		StringBuffer listadoOk = new StringBuffer();
		StringBuffer listadoError = new StringBuffer();

		cadenaResultado.append("Resultado Excel KO");
		cadenaResultado.append("</u></b><br><br>");

		listadoOk.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (listaKoContratoOK != null && !listaKoContratoOK.isEmpty()) {
			for (String mensaje : listaKoContratoOK) {
				listadoOk.append("<tr><td>" + mensaje + "</td></tr>");
			}
			listadoOk.append("</span>");
		} else {
			listadoOk.append("<tr><td></td></tr></span>");
		}

		cadenaResultado.append("<table border='1'><span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Resultados OK Generacion Listado Por Contrato, Tipo, TipoTramite y Jefatura</th></tr></span> ");
		cadenaResultado.append(listadoOk).append("</table>");// fin tabla

		cadenaResultado.append("<br><u><b>");

		listadoError.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (listaKoContratoError != null && !listaKoContratoError.isEmpty()) {
			for (String mensaje : listaKoContratoError) {
				listadoError.append("<tr><td>" + mensaje + "</td></tr>");
			}
			listadoError.append("</span>");
		} else {
			listadoError.append("<tr><td></td></tr></span>");
		}

		cadenaResultado.append("<table border='1'><span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Resultados Error Generacion Listado Por Contrato, Tipo, TipoTramite y Jefatura</th></tr></span> ");
		cadenaResultado.append(listadoOk).append("</table>");// fin tabla

		return cadenaResultado.toString();
	}

	private String cadenaTextoPlanoResultadoExcel(ResultadoConsultaKoBean resultado) {
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");
		StringBuffer listadoOk = new StringBuffer();
		StringBuffer listadoError = new StringBuffer();

		cadenaResultado.append("Resultado Excel KO");
		cadenaResultado.append("</u></b><br><br>");

		listadoOk.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (resultado.getResumen().getNumOk() > 0) {
			for (String mensaje : resultado.getResumen().getListaOk()) {
				listadoOk.append("<tr><td>" + mensaje + "</td></tr>");
			}
			listadoOk.append("</span>");
		} else {
			listadoOk.append("<tr><td></td></tr></span>");
		}

		cadenaResultado.append("<table border='1'><span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Resultados OK</th></tr></span> ");
		cadenaResultado.append(listadoOk).append("</table>");// fin tabla

		cadenaResultado.append("<br><u><b>");

		listadoError.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (resultado.getResumen().getNumError() > 0) {
			for (String mensaje : resultado.getResumen().getListaErrores()) {
				listadoError.append("<tr><td>" + mensaje + "</td></tr>");
			}
			listadoError.append("</span>");
		} else {
			listadoError.append("<tr><td></td></tr></span>");
		}

		cadenaResultado.append("<table border='1'><span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Resultados Error</th></tr></span> ");
		cadenaResultado.append(listadoOk).append("</table>");// fin tabla

		return cadenaResultado.toString();
	}

	private ResultadoConsultaKoBean gestionarKoContrato(GenKoBean genKoBean, Long idUsuario, String tipo, String tipoTramite, String jefatura, Date fechaGenExcel, ContratoVO contrato, String nombreFichero) {
		ResultadoConsultaKoBean resultado = servicioDocPrmDstvFicha.generarDocGestoriaKO(genKoBean, idUsuario, tipo, tipoTramite, jefatura, fechaGenExcel, contrato);
		if (!resultado.getError()) {
			for (ConsultaKoVO consultaKoVO : genKoBean.getListaConsultaKo()) {
				servicioPersistenciaKo.actualizarEstadoGenExcel(consultaKoVO.getId(), EstadoKo.Pdte_DGT.getValorEnum(), idUsuario, new Date(), OperacionConsultaKo.GENERADO, nombreFichero, fechaGenExcel);
			}
		}
		return resultado;
	}

	private ResultadoConsultaKoBean enviarMailExcelKo(File fichero, String jefatura, String tipo, String tipoTramite, String nombreFichero) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean();
		try {
			FicheroBean adjunto = new FicheroBean();
			adjunto.setFichero(fichero);
			adjunto.setNombreYExtension(nombreFichero + ConstantesGestorFicheros.EXTENSION_XLS);
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Se adjunta fichero de KO generado del tipo:").append(tipo)
			.append(" para los tramites del tipo: " + tipoTramite + " y la jefatura: " + jefatura);
			String destinatarios = gestorPropiedades.valorPropertie(DESTINATARIOS_EXCEL_KO);
			String asunto = "Generación Excel " + tipo + " KO de " + tipoTramite + " para la jefatura: " + jefatura;
			ResultBean resultCorreo = servicioCorreo.enviarCorreo(texto.toString(), null, null, asunto, destinatarios, null, null, null, adjunto);
			if (resultCorreo.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCorreo.getMensaje());
			}
		} catch (Throwable e) {
			log.error("No se ha enviado el correo con el fichero adjunto de KO:, error: " ,e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha enviado el correo con el fichero adjunto de KO.");
		}
		return resultado;
	}

	private ResultadoConsultaKoBean generarExcelKoDemanda(JefaturaTipoGenKoBean jefaturaTipoGenKoBean, Date fecha) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		String tipoTramite = TipoTramiteTrafico.convertirTexto(jefaturaTipoGenKoBean.getTipoTramite()).trim();
		try {
			File archivo = null;
			resultado.setNombreFichero("Resumen_" + jefaturaTipoGenKoBean.getTipo() + "_KO_" + tipoTramite + "_" +
					new SimpleDateFormat("ddMMyyyy").format(new Date()) + "_" + JefaturasJPTEnum.convertirJefatura(jefaturaTipoGenKoBean.getJefaturaProvincial()));
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
			fichero.setNombreDocumento(resultado.getNombreFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
			fichero.setSubTipo(ConstantesGestorFicheros.ENVIOS_IMPR_KO);
			fichero.setFecha(utilesFecha.getFechaConDate(fecha));
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(resultado.getNombreFichero()));
			archivo = gestorDocumentos.guardarFichero(fichero);
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Resumen_KO", 0);
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			int numColumnas = 3;

			for (int i = 0; i <= numColumnas; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10,
					WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.WHITE);
			fuenteCabecera.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.DARK_RED);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10,
					WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			sheet.addCell(new Label(0, 0, "Colegiado", formatoCabecera));
			sheet.addCell(new Label(1, 0, "Matricula", formatoCabecera));
			sheet.addCell(new Label(2, 0, "Fecha Presentacion", formatoCabecera));

			int conFila = 1;
			int contColumn = 0;
			for (GenKoBean genKoBean : jefaturaTipoGenKoBean.getListaGenKo()) {
				for(ConsultaKoVO consultaKoVO : genKoBean.getListaConsultaKo()) {
					contColumn = 0;
					sheet.addCell(new Label(contColumn++, conFila, consultaKoVO.getContrato().getColegiado().getNumColegiado(), formatoDatos));
					sheet.addCell(new Label(contColumn++, conFila, consultaKoVO.getMatricula(), formatoDatos));
					sheet.addCell(new Label(contColumn++, conFila, utilesFecha.formatoFecha(consultaKoVO.getFecha()), formatoDatos));
					conFila++;
				}
			}
			copyWorkbook.write();
			resultado.setFichero(archivo);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el excel con los KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + " del tipo tramite: " + 
					TipoTramiteTrafico.convertir(jefaturaTipoGenKoBean.getTipoTramite()) + " para la jefatura: " +jefaturaTipoGenKoBean.getJefaturaProvincial() +" , error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con los KO del tipo: " + jefaturaTipoGenKoBean.getTipo() + " del tipo tramite: " + 
			TipoTramiteTrafico.convertir(jefaturaTipoGenKoBean.getTipoTramite()) + " para la jefatura: " +jefaturaTipoGenKoBean.getJefaturaProvincial() );
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException | IOException e) {
					log.error("Error ", e);
				}
			}
		}
		return resultado;
	}

	private void dividirConsultaKoPorJefaturaYTipo(List<ConsultaKoVO> listaKoPendientes, ResultadoConsultaKoBean resultado) {
		for(ConsultaKoVO consultaKoVO : listaKoPendientes){
			resultado.addListaJefaturaTipoGenKoBean(consultaKoVO);
		}
	}

	@Override
	public ResultadoConsultaKoBean validar(String codSeleccionados, BigDecimal idUsuario, String idJefaturaSesion) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] ids = codSeleccionados.split(";");
				List<ConsultaKoVO> listaConsultaKO = servicioPersistenciaKo.getListaConsultaKO(utiles.convertirStringArrayToLongArray(ids));
				if (listaConsultaKO != null && !listaConsultaKO.isEmpty()) {
					for (ConsultaKoVO consultaKoVO : listaConsultaKO) {
						ResultadoConsultaKoBean resultVal = validarJefaturaKO(consultaKoVO, idJefaturaSesion);
						if(!resultVal.getError()) {
							String estadoAnt = consultaKoVO.getEstado();
							consultaKoVO.setEstado(EstadoKo.Finalizado.getValorEnum());
							servicioPersistenciaKo.guardarConEvolucion(consultaKoVO, estadoAnt, new Date(), OperacionConsultaKo.RECIBIDO);
							resultado.addResumenOK("El trámite con matricula " + consultaKoVO.getMatricula() + " se ha validado correctamente.");
						} else {
							resultado.addResumenError(resultVal.getMensaje());
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para validar los datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los trámites Ko, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar los trámites Ko.");
		}
		return resultado;
	}

	private ResultadoConsultaKoBean validarJefaturaKO(ConsultaKoVO consultaKoVO, String jefaturaSession) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		if (!EstadoKo.Pdte_DGT.getValorEnum().equals(consultaKoVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se pueden validar los trámites en estado Pendiente DGT");
		} else if (!utilesColegiado.tienePermisoAdmin() && !jefaturaSession.equals(consultaKoVO.getJefatura())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No puede validar el trámite con matricula " + consultaKoVO.getMatricula() +
					" ,ya que no pertenece a la jefatura desde donde se realiza la validación");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaKoBean descargarExcelKO(String codSeleccionados) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] listaIds = codSeleccionados.split(";");
				List<ConsultaKoVO> listaConsultaKo = servicioPersistenciaKo.getListaConsultaKO(utiles.convertirStringArrayToLongArray(listaIds));
				validarConsultasDescargaExcel(listaConsultaKo, resultado);
				if (!resultado.getError()) {
					resultado = descargarExcel(resultado.getListaConsultaKo());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún Ko para descargar su excel.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el excel de los Ko, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el excel de los Ko.");
		}
		return resultado;
	}

	private void validarConsultasDescargaExcel(List<ConsultaKoVO> listaConsultaKo, ResultadoConsultaKoBean resultado) {
		if(listaConsultaKo == null || listaConsultaKo.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos de los KO seleccionados");
		} else {
			for(ConsultaKoVO consultaKoBBDD : listaConsultaKo){
				if ((EstadoKo.Pdte_DGT.getValorEnum().equals(consultaKoBBDD.getEstado())
						|| EstadoKo.Finalizado.getValorEnum().equals(consultaKoBBDD.getEstado()))
						&& StringUtils.isNotBlank(consultaKoBBDD.getNombreFichero())
						&& consultaKoBBDD.getFechaGenExcel() != null) {
					if (resultado.getListaConsultaKo() != null && !resultado.getListaConsultaKo().isEmpty()) {
						Boolean existeNombreFichero = Boolean.FALSE;
						for (ConsultaKoVO consultaKoVO : resultado.getListaConsultaKo()) {
							if (consultaKoBBDD.getNombreFichero().equals(consultaKoVO.getNombreFichero())) {
								existeNombreFichero = Boolean.TRUE;
								break;
							}
						}
						if (!existeNombreFichero) {
							resultado.addListaConsultaKO(consultaKoBBDD);
						}
					} else {
						resultado.addListaConsultaKO(consultaKoBBDD);
					}
				}
			}
			if (resultado.getListaConsultaKo() == null || resultado.getListaConsultaKo().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado ninguna consulta KO apta para descargar su excel.");
			}
		}
	}

	private ResultadoConsultaKoBean descargarExcel(List<ConsultaKoVO> listaConsultaKo) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		Boolean esZip = Boolean.FALSE;
		try {
			if (listaConsultaKo.size() > 1) {
				esZip = Boolean.TRUE;
				url = gestorPropiedades.valorPropertie(ServicioConsultaKo.RUTA_FICH_TEMP) + "zip" + System.currentTimeMillis();
				out = new FileOutputStream(url);
				zip = new ZipOutputStream(out);
				for (ConsultaKoVO consultaKoVO : listaConsultaKo) {
					ResultadoConsultaKoBean resultDescarga = buscarExcelKo(consultaKoVO);
					if (!resultDescarga.getError()) {
						ZipEntry zipEntry = new ZipEntry(resultDescarga.getNombreFichero());
						zip.putNextEntry(zipEntry);
						zip.write(gestorDocumentos.transformFiletoByte(resultDescarga.getFichero()));
						zip.closeEntry();
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.getMensaje();
						break;
					}
				}
				zip.close();
				if (!resultado.getError()) {
					File fichero = new File(url);
					resultado.setEsZip(Boolean.TRUE);
					resultado.setNombreFichero(ServicioConsultaKo.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
					resultado.setFichero(fichero);
				}
			} else {
				resultado = buscarExcelKo(listaConsultaKo.get(0));
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el excel de los Ko, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el excel de los Ko.");
		} finally {
			if (esZip) {
				try {
					zip.close();
				} catch (IOException e) {
					log.error("Ha sucedido un error a la hora de cerrar el zip, error: ",e);
				}
			}
		}
		return resultado;
	}

	@Override
	public void borrarZipKO(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	private ResultadoConsultaKoBean buscarExcelKo(ConsultaKoVO consultaKoVO) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			FileResultBean ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE,
					ConstantesGestorFicheros.ENVIOS_IMPR_KO, utilesFecha.getFechaConDate(consultaKoVO.getFechaGenExcel()),
					consultaKoVO.getNombreFichero(), ConstantesGestorFicheros.EXTENSION_XLS);
			if (ficheroPdf != null && ficheroPdf.getFile() != null) {
				resultado.setFichero(ficheroPdf.getFile());
				resultado.setNombreFichero(consultaKoVO.getNombreFichero() + ConstantesGestorFicheros.EXTENSION_XLS);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado ningún xls para la consulta: " + consultaKoVO.getNombreFichero());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el xls: " + consultaKoVO.getNombreFichero() +", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el xls: " + consultaKoVO.getNombreFichero());
		}
		return resultado;
	}

}