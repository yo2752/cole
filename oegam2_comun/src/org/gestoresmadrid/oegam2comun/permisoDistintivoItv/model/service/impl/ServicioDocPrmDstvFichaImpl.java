package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.docPermDistItv.model.beans.FacturacionDocBean;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.values.CantidadDistintivoValue;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.GenKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.DocumentoPermDistItvBean;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.ServicioEvolucionDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.service.ServicioFacturacionDistintivo;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioStockMateriales;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpresionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DetalleDocPrmDstvFichaDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.NumeroSerieBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioJobsImpresion;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.FacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.ResultadoFacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioDocPrmDstvFichaImpl implements ServicioDocPrmDstvFicha {

	private static final long serialVersionUID = -1516868382216825501L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDocPrmDstvFichaImpl.class);

	@Autowired
	DocPermDistItvDao docPermDistItvDao;

	@Autowired
	ServicioJefaturaTrafico servicioJefatura;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioConsultaStock servicioConsultaStock;

	@Autowired
	ServicioEvolucionDocPrmDstvFicha servicioEvolucionDocPrmDstvFicha;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;

	@Autowired
	ServicioJobsImpresion servicioJobsImpresion;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	ServicioFacturacionDistintivo servicioFacturacionDistintivo;

	@Autowired
	ServicioStockMateriales servicioStockMateriales;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean cambiarEstado(String docId, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (docId != null && !docId.isEmpty()) {
				DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getDocPermPorDoc(docId, Boolean.TRUE);
				if (docPermDistItvVO != null) {
					if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
						docPermDistItvVO.setNumDescarga(new Long(0));
						if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
							resultado = servicioFacturacionDistintivo.borrarFacturacion(docPermDistItvVO.getIdDocPermDistItv());
						}
						if (!resultado.getError()) {
							Long cantidad = new Long(0);
							if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
								if (docPermDistItvVO.getListaDuplicadoDistintivos() != null && !docPermDistItvVO.getListaDuplicadoDistintivos().isEmpty()) {
									cantidad += docPermDistItvVO.getListaDuplicadoDistintivos().size();
								}
								if (docPermDistItvVO.getListaTramitesDistintivo() != null && !docPermDistItvVO.getListaTramitesDistintivo().isEmpty()) {
									cantidad += docPermDistItvVO.getListaTramitesDistintivo().size();
								}
								resultado = servicioConsultaStock.aniadirStock(docPermDistItvVO.getJefatura(), docPermDistItvVO.getTipoDistintivo(), cantidad);
							} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo()) || TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(
									docPermDistItvVO.getTipo())) {
								resultado = servicioConsultaStock.aniadirStock(docPermDistItvVO.getJefatura(), TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), new Long(docPermDistItvVO
										.getListaTramitesPermiso().size()));
							}
						}
					}
					if (!resultado.getError()) {
						String estadoAnt = docPermDistItvVO.getEstado().toString();
						docPermDistItvVO.setEstado(estadoNuevo);
						docPermDistItvDao.actualizar(docPermDistItvVO);
						resultado = servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), new Date(), estadoAnt,estadoNuevo.toString(), OperacionPrmDstvFicha.CAMBIO_ESTADO, docPermDistItvVO.getDocIdPerm(),
								idUsuario);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos en BBDD para el docId: " + docId);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un docId para poder cambiar el estado del documento.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado al documento: " + docId + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado al documento: " + docId);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getListaJefaturasImpresionDia(Date fecha) {
		try {
			return docPermDistItvDao.getListaJefaturasImpresionDia(fecha);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de jefaturas que han impreso distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetalleDocPrmDstvFichaDgtBean> getListaDetalleDocDstv(Long idDoc) {
		try {
			List<DetalleDocPrmDstvFichaDgtBean> listaDetalle = new ArrayList<>();
			int cont = 0;
			List<TramiteTrafMatrDto> listaTramitesBBDD = getListaTramitesDistintivos(idDoc);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				for (TramiteTrafMatrDto trafMatrDto : listaTramitesBBDD) {
					DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
					detalle.setMatricula(trafMatrDto.getVehiculoDto().getMatricula());
					detalle.setNumExpediente(trafMatrDto.getNumExpediente().toString());
					detalle.setTipo(TipoDistintivo.convertirValor(trafMatrDto.getTipoDistintivo()));
					detalle.setTipoDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum());
					detalle.setNumSerie("");
					listaDetalle.add(cont++, detalle);
				}
			}
			List<VehiculoNoMatriculadoOegamDto> listaDuplicados = servicioDistintivoVehNoMat.getListaVehiculoDistintivosDtoPorDocId(idDoc);
			if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
				for (VehiculoNoMatriculadoOegamDto veNoMatriculadoOegamDto : listaDuplicados) {
					DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
					detalle.setMatricula(veNoMatriculadoOegamDto.getMatricula());
					detalle.setNumExpediente("Duplicado");
					detalle.setTipo(TipoDistintivo.convertirValor(veNoMatriculadoOegamDto.getTipoDistintivo()));
					detalle.setTipoDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum());
					detalle.setNumSerie("");
					listaDetalle.add(cont++, detalle);
				}
			}
			return listaDetalle;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con los detalles de los distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetalleDocPrmDstvFichaDgtBean> getListaDetalleDocFichaPermiso(Long idDocPermDistItv) {
		try {
			List<DetalleDocPrmDstvFichaDgtBean> listaDetalleAux = new ArrayList<>();
			List<DetalleDocPrmDstvFichaDgtBean> listaDetalle = new ArrayList<>();
			int cont = 0;
			List<TramiteTrafDto> listaTramitesPC = getListaTramitesPermisos(idDocPermDistItv);
			if (listaTramitesPC != null && !listaTramitesPC.isEmpty()) {
				for (TramiteTrafDto tramiteTrafDto : listaTramitesPC) {
					DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
					detalle.setMatricula(tramiteTrafDto.getVehiculoDto().getMatricula());
					detalle.setNumExpediente(tramiteTrafDto.getNumExpediente().toString());
					detalle.setTipoDocumento(TipoDocumentoImprimirEnum.FICHA_PERMISO.getNombreEnum());
					detalle.setTipo(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum());
					listaDetalleAux.add(cont++, detalle);
				}
			}
			cont = 0;
			Boolean existenPC = listaDetalleAux != null && !listaDetalleAux.isEmpty();
			List<TramiteTrafDto> listaTramitesFCH = getListaTramitesFichasTecnicas(idDocPermDistItv);
			if (listaTramitesFCH != null && !listaTramitesFCH.isEmpty()) {
				for (TramiteTrafDto tramiteTrafFchDto : listaTramitesFCH) {
					DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
					if (existenPC) {
						for (DetalleDocPrmDstvFichaDgtBean detallePC : listaDetalleAux) {
							if (detallePC.getMatricula().equals(tramiteTrafFchDto.getVehiculoDto().getMatricula())) {
								detalle = detallePC;
								detalle.setTipo(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum());
								break;
							}
						}
					} else {
						detalle.setMatricula(tramiteTrafFchDto.getVehiculoDto().getMatricula());
						detalle.setNumExpediente(tramiteTrafFchDto.getNumExpediente().toString());
						detalle.setTipoDocumento(TipoDocumentoImprimirEnum.FICHA_PERMISO.getNombreEnum());
						detalle.setTipo(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum());
					}
					listaDetalle.add(cont++, detalle);
				}
			} else {
				return listaDetalleAux;
			}
			return listaDetalle;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con los detalles del tipo documento de ficha y permiso, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoFacturacionStockBean obtenerFacturacionDocumentos(FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		try {
			List<FacturacionDocBean> listaFacturacionDocBBDD = new ArrayList<>();

			if (facturacionStock.getIdContrato() != null) {
				listaFacturacionDocBBDD.addAll(rellenarListaFacturacion(facturacionStock, facturacionStock.getIdContrato()));
			} else {
				List<Long> listaContratos = docPermDistItvDao.getContratosConImpresionesPorDia(facturacionStock.getTipo(), facturacionStock.getTipoDistintivo(), facturacionStock.getFecha()
						.getFechaInicio(), facturacionStock.getFecha().getFechaFin());
				if (listaContratos != null && !listaContratos.isEmpty()) {
					for (Long idContrato : listaContratos) {
						listaFacturacionDocBBDD.addAll(rellenarListaFacturacion(facturacionStock, idContrato));
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen impresiones para el rango de fechas indicado.");
				}
			}
			if (listaFacturacionDocBBDD != null && !listaFacturacionDocBBDD.isEmpty()) {
				resultado.setListaFacturacion(listaFacturacionDocBBDD);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para generar el excel.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con la facturacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la lista con la facturación.");
		}
		return resultado;
	}

	private List<FacturacionDocBean> rellenarListaFacturacion(FacturacionStockBean facturacionStock, Long idContrato) {
		HashMap<String, FacturacionDocBean> mapaPorContrato = new HashMap<>();
		List<CantidadDistintivoValue> listaFactTraficoContrato = docPermDistItvDao.getListaFacturacionPorContratoDstvTrafico(idContrato, facturacionStock.getTipoDistintivo(), facturacionStock
				.getFecha().getFechaInicio(), facturacionStock.getFecha().getFechaFin());
		if (listaFactTraficoContrato != null && !listaFactTraficoContrato.isEmpty()) {
			for (CantidadDistintivoValue cantidadDstv : listaFactTraficoContrato) {
				FacturacionDocBean fact = null;
				if (mapaPorContrato.get(cantidadDstv.getFechaImpresion()) != null) {
					fact = mapaPorContrato.get(cantidadDstv.getFechaImpresion());
					aniadirCantidadDstv(fact, cantidadDstv);
				} else {
					fact = new FacturacionDocBean();
					fact.setFechaImpresion(cantidadDstv.getFechaImpresion());
					fact.setNombreColegiado(cantidadDstv.getNombreColegiado());
					fact.setNum_colegiado(cantidadDstv.getNumColegiado());
					fact.setProvincia(cantidadDstv.getProvincia());
					fact.setVia(cantidadDstv.getVia());
					aniadirCantidadDstv(fact, cantidadDstv);
				}
				mapaPorContrato.put(cantidadDstv.getFechaImpresion(), fact);
			}
		}
		List<CantidadDistintivoValue> listaFactDuplicadoContrato = docPermDistItvDao.getListaFacturacionPorContratoDstvDuplicado(idContrato, facturacionStock.getTipoDistintivo(), facturacionStock
				.getFecha().getFechaInicio(), facturacionStock.getFecha().getFechaFin());
		if (listaFactDuplicadoContrato != null && !listaFactDuplicadoContrato.isEmpty()) {
			for (CantidadDistintivoValue cantidadDstv : listaFactDuplicadoContrato) {
				FacturacionDocBean fact = null;
				if (mapaPorContrato.get(cantidadDstv.getFechaImpresion()) != null) {
					fact = mapaPorContrato.get(cantidadDstv.getFechaImpresion());
					aniadirCantidadDstv(fact, cantidadDstv);
				} else {
					fact = new FacturacionDocBean();
					fact.setFechaImpresion(cantidadDstv.getFechaImpresion());
					fact.setNombreColegiado(cantidadDstv.getNombreColegiado());
					fact.setNum_colegiado(cantidadDstv.getNumColegiado());
					fact.setProvincia(cantidadDstv.getProvincia());
					fact.setVia(cantidadDstv.getVia());
					aniadirCantidadDstv(fact, cantidadDstv);
				}
				mapaPorContrato.put(cantidadDstv.getFechaImpresion(), fact);
			}
		}
		List<FacturacionDocBean> listaFacturacionDocBBDD = new ArrayList<>();
		listaFacturacionDocBBDD.addAll(mapaPorContrato.values());
		return listaFacturacionDocBBDD;
	}

	private void aniadirCantidadDstv(FacturacionDocBean fact, CantidadDistintivoValue cantidadDstv) {
		if (TipoDistintivo.B.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalB() != null) {
				fact.setTotalB(cantidadDstv.getCantidad() + fact.getTotalB());
			} else {
				fact.setTotalB(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.C.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalC() != null) {
				fact.setTotalC(cantidadDstv.getCantidad() + fact.getTotalC());
			} else {
				fact.setTotalC(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.ECO.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalEco() != null) {
				fact.setTotalEco(cantidadDstv.getCantidad() + fact.getTotalEco());
			} else {
				fact.setTotalEco(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.CERO.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalCero() != null) {
				fact.setTotalCero(cantidadDstv.getCantidad() + fact.getTotalCero());
			} else {
				fact.setTotalCero(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.BMT.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalBMts() != null) {
				fact.setTotalBMts(cantidadDstv.getCantidad() + fact.getTotalBMts());
			} else {
				fact.setTotalBMts(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.CEROMT.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalCeroMts() != null) {
				fact.setTotalCeroMts(cantidadDstv.getCantidad() + fact.getTotalCeroMts());
			} else {
				fact.setTotalCeroMts(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.ECOMT.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalEcoMts() != null) {
				fact.setTotalEcoMts(cantidadDstv.getCantidad() + fact.getTotalEcoMts());
			} else {
				fact.setTotalEcoMts(cantidadDstv.getCantidad());
			}
		} else if (TipoDistintivo.CMT.getValorEnum().equals(cantidadDstv.getTipoDistintivo())) {
			if (fact.getTotalB() != null) {
				fact.setTotalB(cantidadDstv.getCantidad() + fact.getTotalB());
			} else {
				fact.setTotalB(cantidadDstv.getCantidad());
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDistintivoDgtBean generarDocGestoriaDstvProceso(List<TramiteTrafMatrVO> listaTramites, List<VehNoMatOegamVO> listaDuplicados, DocPermDistItvVO docPermDistItvVO, String tipoSolicitud) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			int total = 0;
			if (listaTramites != null && !listaTramites.isEmpty()) {
				total = total + listaTramites.size();
			}
			if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
				total = total + listaDuplicados.size();
			}
			params.put("numeroTramites", total);
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfDistintivoProcesoComun(listaTramites, listaDuplicados, docPermDistItvVO);
			ReportExporter re = new ReportExporter();
			resultado.setByteFichero(re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), "justificantePermEitvDstv",
					"pdf", xml, TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum(), params, null));
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.JUSTIFICANTE_DISTINTIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()),
					docPermDistItvVO.getDocIdPerm() + "_Gestoria", ConstantesGestorFicheros.EXTENSION_PDF, resultado.getByteFichero());
			String direccionCC = obtenerDireccion(docPermDistItvVO.getJefatura());

			String direccionCorreo = docPermDistItvVO.getContrato().getCorreoElectronico();
			String tipoTramite = TipoDocumentoImprimirEnum.convertirValorATipoTramite(docPermDistItvVO.getTipo());

			if (docPermDistItvVO.getContrato().getCorreosTramites() != null && !docPermDistItvVO.getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : docPermDistItvVO.getContrato().getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			resultado = enviarMailColegiadoDistintivo(direccionCorreo, docPermDistItvVO.getDocIdPerm(), direccionCC, resultado.getByteFichero(), TipoDistintivo
					.convertir(docPermDistItvVO.getTipoDistintivo()));
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso para los distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso para los distintivos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void borrarDoc(DocPermDistItvVO docPermDistItvNuevo) {
		try {
			docPermDistItvDao.borrar(docPermDistItvNuevo);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar el docPermDistvItv, error: ", e);
		}
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean generarDoc(BigDecimal idUsuario, Date fecha, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, Long idContrato,
			String jefaturaProvincial, Boolean esDemanda) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);

		try {
			DocPermDistItvVO docPermDistItvVO = new DocPermDistItvVO();
			docPermDistItvVO.setIdUsuario(idUsuario.longValue());
			String estadoAnt =null;
			if(docPermDistItvVO.getEstado() != null) {
				estadoAnt = docPermDistItvVO.getEstado().toString();
			} else {
				estadoAnt = EstadoPermisoDistintivoItv.Iniciado.getValorEnum();
			}
			docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Iniciado.getValorEnum()));
			docPermDistItvVO.setFechaAlta(fecha);
			docPermDistItvVO.setIdContrato(idContrato);
			docPermDistItvVO.setJefatura(jefaturaProvincial);
			docPermDistItvVO.setTipo(tipoDocumento.getValorEnum());
			docPermDistItvVO.setEsDemanda(esDemanda ? "S" : "N");
			if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				docPermDistItvVO.setTipoDistintivo(tipoDistintivo.getValorEnum());
			}
			resultado.setIdDocPermDstvEitv((Long) docPermDistItvDao.guardar(docPermDistItvVO));
			docPermDistItvVO.setDocIdPerm(generarIdPerm(fecha, resultado.getIdDocPermDstvEitv().toString()));
			docPermDistItvDao.actualizar(docPermDistItvVO);
			resultado.setDocId(docPermDistItvVO.getDocIdPerm());
			resultado.setIdDocPermDstvEitv(docPermDistItvVO.getIdDocPermDistItv());
			resultado.setDocPermDistItv(docPermDistItvVO);
			ResultadoPermisoDistintivoItvBean resultEvol = 
					servicioEvolucionDocPrmDstvFicha.guardarEvolucion(tipoDocumento.getValorEnum(), fecha,estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.CREACION, docPermDistItvVO
					.getDocIdPerm(), idUsuario);
			if (resultEvol.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultEvol.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear el docPermDstvEitv, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido generar el identificador del documento.");
		}
		return resultado;
	}

	private String generarIdPerm(Date fecha, String docId) {
		String nuevaGeneracion = gestorPropiedades.valorPropertie("nueva.generacion.doc.id.permisos");
		String docIdImpresion = new SimpleDateFormat("yyyyMMdd").format(fecha).substring(0, 4) + "-";
		if ("SI".equals(nuevaGeneracion)) {
			List<String> listaDocId = docPermDistItvDao.obtenerIdDocPermDistItvMax(fecha);
			String nextDocId = null;
			if (listaDocId != null && !listaDocId.isEmpty()) {
				nextDocId = String.valueOf(Integer.valueOf(listaDocId.get(0).substring(listaDocId.get(0).length() - longitudSecuencialDocPermDstvEitv, listaDocId.get(0).length())) + 1);
			} else {
				nextDocId = "1";
			}
			String secuencial = utiles.rellenarCeros(nextDocId, longitudSecuencialDocPermDstvEitv);
			docIdImpresion += secuencial;
		} else {
			String secuencial = utiles.rellenarCeros(docId, longitudSecuencialDocPermDstvEitv);
			docIdImpresion += secuencial;
		}
		return docIdImpresion;
	}

	@Override
	@Transactional
	public void actualizarEstado(DocPermDistItvVO docPermDistItvVO, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, OperacionPrmDstvFicha operacion) {
		try {
			String estadoAnt = docPermDistItvVO.getEstado().toString();
			docPermDistItvVO.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
			docPermDistItvDao.actualizar(docPermDistItvVO);
			servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), new Date(),estadoAnt,estadoNuevo.getValorEnum(), operacion, docPermDistItvVO.getDocIdPerm(), idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el documento: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
		}
	}

	@Override
	public ResultadoDistintivoDgtBean generarDocGestoriaDistintivo(List<TramiteTrafMatrVO> listaTramites, DocPermDistItvVO docPermDistItvVO) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", listaTramites.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfDstv(listaTramites, docPermDistItvVO);
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), "justificantePermEitvDstv", "pdf",
					xml, TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum(), params, null);
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.JUSTIFICANTE_DISTINTIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()),
					docPermDistItvVO.getDocIdPerm() + "_Gestoria", ConstantesGestorFicheros.EXTENSION_PDF, fichero);
			String direccionCC = obtenerDireccion(docPermDistItvVO.getJefatura());

			String direccionCorreo = docPermDistItvVO.getContrato().getCorreoElectronico();
			String tipoTramite = TipoDocumentoImprimirEnum.convertirValorATipoTramite(docPermDistItvVO.getTipo());

			if (docPermDistItvVO.getContrato().getCorreosTramites() != null && !docPermDistItvVO.getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : docPermDistItvVO.getContrato().getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			resultado = enviarMailColegiadoDistintivo(direccionCorreo, docPermDistItvVO.getDocIdPerm(), direccionCC, fichero, TipoDistintivo.convertir(
					docPermDistItvVO.getTipoDistintivo()));
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean generarDocGestoriaDistintivoDuplicado(List<VehNoMatOegamVO> listaVehiculos, DocPermDistItvVO docPermDistItvVO) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", listaVehiculos.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfDstvDuplicado(listaVehiculos, docPermDistItvVO);
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), "justificantePermEitvDstv", "pdf",
					xml, TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum(), params, null);
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.JUSTIFICANTE_DISTINTIVO, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()),
					docPermDistItvVO.getDocIdPerm() + "_Gestoria", ConstantesGestorFicheros.EXTENSION_PDF, fichero);

			String direccionCorreo = docPermDistItvVO.getContrato().getCorreoElectronico();
			String tipoTramite = TipoDocumentoImprimirEnum.convertirValorATipoTramite(docPermDistItvVO.getTipo());

			if (docPermDistItvVO.getContrato().getCorreosTramites() != null && !docPermDistItvVO.getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : docPermDistItvVO.getContrato().getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			resultado = enviarMailColegiadoDistintivo(direccionCorreo, docPermDistItvVO.getDocIdPerm(), "distintivos@gestoresmadrid.org", fichero, TipoDistintivo
					.convertir(docPermDistItvVO.getTipoDistintivo()));
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean enviarMailColegiadoPrmFicha(List<TramitesPermDistItvBean> listaTramites, ContratoVO contrato, byte[] fichero, String subject,
			DocPermDistItvVO docPermDistItv, Boolean esDesdeError, String esSinNive, String tipoTramite) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String direccion = null;
			String direccionCC = null;
			ContratoVO contratoVO = null;

			if (contrato != null && esDesdeError) {
				contratoVO = contrato;
			} else {
				contratoVO = docPermDistItv.getContrato();
			}

			if (contratoVO != null && StringUtils.isNotBlank(contratoVO.getCorreoElectronico())) {
				direccion = contratoVO.getCorreoElectronico();
			}

			if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
						direccion = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			if (contratoVO != null && contratoVO.getJefaturaTrafico() != null && StringUtils.isNotBlank(contratoVO.getJefaturaTrafico().getJefaturaProvincial())) {
				direccionCC = obtenerDireccion(contratoVO.getJefaturaTrafico().getJefaturaProvincial());
			}

			String dirOculta = gestorPropiedades.valorPropertie("direcciones.ocultas.impresiones.impr");
			StringBuffer sb = new StringBuffer();
			if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION_KO.getValorEnum().equals(docPermDistItv.getTipo())) {
				sb.append("Este es el justificante de los permisos de circulación que no se han podido descargar su documentación.");
			} else {
				sb.append("Este es el justificante de la solicitud de impresion de l@s " + TipoDocumentoImprimirEnum.convertirTexto(docPermDistItv.getTipo()));
				sb.append("<br>");
				sb.append("Recuerde que para poder retirar todos los documentos debera de presentar dicho justificante firmado.");
				sb.append("<br>");
				if ("SNV".equals(esSinNive)) {
					sb.append(
							"También recordarle que para los casos de matriculaciones sin nive que llevan ficha tecnica original, debera presentar las fichas a la vez que el justificante que se adjunta en el correo para poder retirar los permisos.");
					sb.append("<br>");
				}
			}
			FicheroBean ficheroBEan = new FicheroBean();
			ficheroBEan.setFicheroByte(fichero);
			ficheroBEan.setNombreDocumento(docPermDistItv.getDocIdPerm());
			ficheroBEan.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccion, direccionCC, dirOculta, null, ficheroBEan);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo con el justificante de la solicitud de la impresión de los documentos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean enviarMailColegiadoDistintivo(String direccion, String docId, String direccionCC, byte[] fichero, TipoDistintivo tipoDistintivo) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Este es el justificante de la solicitud de impresion de los distintivos del tipo: " + tipoDistintivo.getNombreEnum());
			sb.append("<br>");
			sb.append("Recuerde que para poder retirar todos los documentos debera de presentar dicho justificante firmado.");
			sb.append("<br>");
			String subject = "Solicitud para impresion de Distintivos";
			FicheroBean ficheroBEan = new FicheroBean();
			ficheroBEan.setFicheroByte(fichero);
			ficheroBEan.setNombreDocumento(docId);
			ficheroBEan.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccion, direccionCC, null, null, ficheroBEan);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo con el justificante de la solicitud de la impresión de los documentos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFicha(List<TramiteTraficoVO> listaTramites, TipoDocumentoImprimirEnum tipoDocumento, String docId, Date fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", listaTramites.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfPermFicha(listaTramites, tipoDocumento, docId);
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), "justificantePermEitv", "pdf", xml,
					tipoDocumento.getNombreEnum(), params, null);
			String subTipo = null;
			String subject = null;
			if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_EITV;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA);
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS);
			} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO_FICHA;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA_PERMISOS);
			}
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subTipo, utilesFecha.getFechaConDate(fecha), docId + "_Gestoria", ConstantesGestorFicheros.EXTENSION_PDF, fichero);
			resultado = enviarMailColegiadoPrmFicha(listaTramites, docId, fichero, tipoDocumento, subject);
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	private String obtenerDireccion(String jefatura) {
		String direcciones = null;
		if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.arturosoria.bcc");
		} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.alcorcon.bcc");
		} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.alcala.bcc");
		} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.segovia.bcc");
		} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.avila.bcc");
		} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.cuenca.bcc");
		} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.ciudadreal.bcc");
		} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("solicitud.generar.permiso.mail.guadalajara.bcc");
		}
		return direcciones;
	}

	private ResultadoPermisoDistintivoItvBean enviarMailColegiadoPrmFicha(List<TramiteTraficoVO> listaTramites, String nombreFichero, byte[] fichero, TipoDocumentoImprimirEnum tipoDocumento,
			String subject) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String direccion = listaTramites.get(0).getContrato().getCorreoElectronico();
			if (listaTramites.get(0).getContrato().getCorreosTramites() != null && !listaTramites.get(0).getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : listaTramites.get(0).getContrato().getCorreosTramites()) {
					if (listaTramites.get(0).getTipoTramite().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
						direccion = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			String direccionCC = obtenerDireccion(listaTramites.get(0).getContrato().getJefaturaTrafico().getJefaturaProvincial());
			String dirOculta = gestorPropiedades.valorPropertie("direcciones.ocultas.impresiones.impr");
			StringBuffer sb = new StringBuffer();
			sb.append("Este es el justificante de la solicitud de impresion de l@s " + tipoDocumento.getNombreEnum());
			sb.append("<br>");
			sb.append("Recuerde que para poder retirar todos los documentos debera de presentar dicho justificante firmado.");
			sb.append("<br>");
			FicheroBean ficheroBEan = new FicheroBean();
			ficheroBEan.setFicheroByte(fichero);
			ficheroBEan.setNombreDocumento(nombreFichero);
			ficheroBEan.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccion, direccionCC, dirOculta, null, ficheroBEan);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo con el justificante de la solicitud de la impresión de los documentos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud.");
		}
		return resultado;
	}

	private String generarXmlJustfDstv(List<TramiteTrafMatrVO> listaTramites, DocPermDistItvVO docPermDistItvVO) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		if (TipoDistintivo.esDistintivoMoto(docPermDistItvVO.getTipoDistintivo())) {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()) + "- Motocicletas");
		} else {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()));
		}
		documentoPermDistItvBean.setJefatura(docPermDistItvVO.getContrato().getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(docPermDistItvVO.getContrato().getColegiado().getNumColegiado());
		documentoPermDistItvBean.setGestoria(docPermDistItvVO.getContrato().getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(docPermDistItvVO.getContrato().getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<>();
		int cont = 1;
		int i = 0;
		for (TramiteTraficoVO tramiteBBDD : listaTramites) {
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
			tramitesPermDistItvBean.setPrimeraImpresion("S");
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));

			listaTramitesBean.add(i++, tramitesPermDistItvBean);
		}
		documentoPermDistItvBean.setTramites(listaTramitesBean);

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	private String generarXmlJustfDstvDuplicado(List<VehNoMatOegamVO> listaVehiculos, DocPermDistItvVO docPermDistItvVO) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		if (TipoDistintivo.esDistintivoMoto(docPermDistItvVO.getTipoDistintivo())) {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()) + "- Motocicletas");
		} else {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()));
		}
		documentoPermDistItvBean.setJefatura(docPermDistItvVO.getContrato().getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(docPermDistItvVO.getContrato().getColegiado().getNumColegiado());
		documentoPermDistItvBean.setGestoria(docPermDistItvVO.getContrato().getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(docPermDistItvVO.getContrato().getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<>();
		int cont = 1;
		int i = 0;
		for (VehNoMatOegamVO vehNoMatOegamVO : listaVehiculos) {
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setMatricula(vehNoMatOegamVO.getMatricula());
			tramitesPermDistItvBean.setNumExpediente("Duplicado");
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd/MM/YYYY").format(vehNoMatOegamVO.getFechaSolicitud()));
			tramitesPermDistItvBean.setPrimeraImpresion(vehNoMatOegamVO.getPrimeraImpresion());
			tramitesPermDistItvBean.setNumero(cont++);
			listaTramitesBean.add(i++, tramitesPermDistItvBean);
		}
		documentoPermDistItvBean.setTramites(listaTramitesBean);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	private String generarXmlJustfDistintivoProcesoComun(List<TramiteTrafMatrVO> listaTramites, List<VehNoMatOegamVO> listaDuplicados, DocPermDistItvVO docPermDistItvVO) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		if (TipoDistintivo.esDistintivoMoto(docPermDistItvVO.getTipoDistintivo())) {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()) + "- Motocicletas");
		} else {
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum() + " " + TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()));
		}
		documentoPermDistItvBean.setJefatura(docPermDistItvVO.getContrato().getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(docPermDistItvVO.getContrato().getColegiado().getNumColegiado());
		documentoPermDistItvBean.setGestoria(docPermDistItvVO.getContrato().getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(docPermDistItvVO.getContrato().getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<>();
		int cont = 1;
		int i = 0;
		if (listaTramites != null && !listaTramites.isEmpty()) {
			for (TramiteTrafMatrVO trafMatrVO : listaTramites) {
				TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
				tramitesPermDistItvBean.setMatricula(trafMatrVO.getVehiculo().getMatricula());
				tramitesPermDistItvBean.setNumExpediente(trafMatrVO.getNumExpediente().toString());
				tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd/MM/YYYY").format(trafMatrVO.getFechaPresentacion()));
				tramitesPermDistItvBean.setPrimeraImpresion("S");
				tramitesPermDistItvBean.setNumero(cont++);
				listaTramitesBean.add(i++, tramitesPermDistItvBean);
			}
		}
		if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
			for (VehNoMatOegamVO vehNoMatOegamVO : listaDuplicados) {
				TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
				tramitesPermDistItvBean.setMatricula(vehNoMatOegamVO.getMatricula());
				tramitesPermDistItvBean.setNumExpediente("Duplicado");
				tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd/MM/YYYY").format(vehNoMatOegamVO.getFechaSolicitud()));
				tramitesPermDistItvBean.setPrimeraImpresion(vehNoMatOegamVO.getPrimeraImpresion());
				tramitesPermDistItvBean.setNumero(cont++);
				listaTramitesBean.add(i++, tramitesPermDistItvBean);
			}
		}
		documentoPermDistItvBean.setTramites(listaTramitesBean);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	private String generarXmlJustfPermFicha(List<TramiteTraficoVO> listaTramites, TipoDocumentoImprimirEnum tipoDocumento, String docId) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		documentoPermDistItvBean.setDocumento(tipoDocumento.getNombreEnum());
		documentoPermDistItvBean.setJefatura(listaTramites.get(0).getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(listaTramites.get(0).getNumColegiado());
		documentoPermDistItvBean.setGestoria(listaTramites.get(0).getContrato().getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(listaTramites.get(0).getContrato().getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docId);
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(listaTramites.get(0).getTipoTramite())) {
			documentoPermDistItvBean.setTipoTramite(TipoTramiteTrafico.Matriculacion.getNombreEnum());
		} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(listaTramites.get(0).getTipoTramite())) {
			documentoPermDistItvBean.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());
		}
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<>();
		int cont = 1;
		int i = 0;
		for (TramiteTraficoVO tramiteBBDD : listaTramites) {
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
			if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				tramitesPermDistItvBean.setPc("OK");
				tramitesPermDistItvBean.setEitv("OK");
			} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				tramitesPermDistItvBean.setEitv("OK");
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				tramitesPermDistItvBean.setPc("OK");
			}
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));
			listaTramitesBean.add(i++, tramitesPermDistItvBean);
		}
		documentoPermDistItvBean.setTramites(listaTramitesBean);

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	@Override
	@Transactional(readOnly = true)
	public DocPermDistItvVO getDocPermDistFicha(String docId, Boolean completo) {
		try {
			return docPermDistItvDao.getDocPermPorDoc(docId, completo);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el docPrmDstvFicha: " + docId + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public DocPermDistItvVO getDocPorId(Long idDoc, Boolean completo) {
		try {
			if (idDoc != null) {
				return docPermDistItvDao.getPermDistItvPorId(idDoc, completo);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el documento con id: " + idDoc + " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocDistintivoJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Integer cantidadExpediente = null;
			Date fecha = new Date();
			Long stock = null;
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				stock = servicioConsultaStock.cantidadStockPorTipo(jefaturaImpr, docPermDistItvVO.getTipoDistintivo());
				cantidadExpediente = obtenerNumDistintivosImprimir(docPermDistItvVO.getIdDocPermDistItv());
				if (stock == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado stock suficiente para poder imprimir.");
				} else if (cantidadExpediente == null || cantidadExpediente == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningun expediente asociado al documento.");
				} else if (cantidadExpediente > stock) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir los documento porque no tiene stock suficiente.");
				}
				if (!resultado.getError()) {
					servicioConsultaStock.descontarStock(docPermDistItvVO.getJefatura(), docPermDistItvVO.getTipoDistintivo(), cantidadExpediente.longValue());
				}

				if (!resultado.getError()) {
					resultado = servicioFacturacionDistintivo.guardarFacturacionDocumento(docPermDistItvVO);
					if (!resultado.getError()) {
						resultado = servicioJobsImpresion.imprimirDistintivosJefatura(docPermDistItvVO, idUsuario, jefaturaImpr);
						if (!resultado.getError()) {
							String estadoAnt = docPermDistItvVO.getEstado().toString();
							docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum()));
							docPermDistItvDao.actualizar(docPermDistItvVO);
							servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha,estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRIMIENDO, docPermDistItvVO.getDocIdPerm(), idUsuario);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocDistintivo(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Integer cantidadExpediente = null;
			Date fecha = new Date();
			Long stock = null;
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				stock = servicioConsultaStock.cantidadStockPorTipo(jefaturaImpr, docPermDistItvVO.getTipoDistintivo());
				cantidadExpediente = obtenerNumDistintivosImprimir(docPermDistItvVO.getIdDocPermDistItv());
				if (stock == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado stock suficiente para poder imprimir.");
				} else if (cantidadExpediente == null || cantidadExpediente == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningun expediente asociado al documento.");
				} else if (cantidadExpediente > stock) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir los documento porque no tiene stock suficiente.");
				}
				if (!resultado.getError()) {
					servicioConsultaStock.descontarStock(jefaturaImpr, docPermDistItvVO.getTipoDistintivo(), cantidadExpediente.longValue());
				}

				if (!resultado.getError()) {
					resultado = servicioFacturacionDistintivo.guardarFacturacionDocumento(docPermDistItvVO);
					if (!resultado.getError()) {
						docPermDistItvVO.setFechaImpresion(fecha);
						String estadoAnt = docPermDistItvVO.getEstado().toString();
						docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum()));
						docPermDistItvDao.actualizar(docPermDistItvVO);
						servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha, estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRESION, docPermDistItvVO.getDocIdPerm(), idUsuario);
						resultado = actualizarEstadosImpresionDstv(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Impresion_OK, idUsuario,
								docPermDistItvVO.getDocIdPerm(), ipConexion);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private Integer obtenerNumDistintivosImprimir(Long idDocPermDistItv) {
		Integer totalDstv = new Integer(0);
		totalDstv += servicioTramiteTraficoMatriculacion.getCountNumTramitesDstv(idDocPermDistItv);
		totalDstv += servicioDistintivoVehNoMat.getCountNumVehNotMatOegamDstv(idDocPermDistItv);
		return totalDstv;
	}

	private ResultadoPermisoDistintivoItvBean actualizarEstadosImpresionDstv(Long idDocPermDistItv, EstadoPermisoDistintivoItv estado, BigDecimal idUsuario, 
			String docIdPerm, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			resultado = servicioTramiteTraficoMatriculacion.actualizarEstadoImpresionDocDistintivos(idDocPermDistItv, estado, idUsuario, docIdPerm, ipConexion);
			if (!resultado.getError()) {
				resultado = servicioDistintivoVehNoMat.actualizarEstadosImpresionDstv(idDocPermDistItv, estado, idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los distintivos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocPermisoJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		Date fecha = new Date();
		try {
			Long stock = null;
			Integer cantidadExpediente = null;
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				stock = servicioConsultaStock.cantidadStockPorTipo(jefaturaImpr, docPermDistItvVO.getTipo());
				cantidadExpediente = servicioTramiteTrafico.getCountNumTramitesPermisos(docPermDistItvVO.getIdDocPermDistItv());
				if (stock == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado stock suficiente para poder imprimir.");
				} else if (cantidadExpediente == null || cantidadExpediente == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningun expediente asociado al documento.");
				} else if (cantidadExpediente > stock) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir los documento porque no tiene stock suficiente.");
				}
				if (!resultado.getError()) {
					servicioConsultaStock.descontarStock(jefaturaImpr, docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
				}
					
				if (!resultado.getError()) {
					resultado = servicioJobsImpresion.imprimirPermisosJefatura(docPermDistItvVO, idUsuario, jefaturaImpr);
					if (!resultado.getError()) {
						String estadoAnt = docPermDistItvVO.getEstado().toString();
						docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum()));
						docPermDistItvDao.actualizar(docPermDistItvVO);
						servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha,estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRIMIENDO, docPermDistItvVO.getDocIdPerm(), idUsuario);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocPermiso(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		Date fecha = new Date();
		try {
			Long stock = null;
			Integer cantidadExpediente = null;
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				stock = servicioConsultaStock.cantidadStockPorTipo(jefaturaImpr, docPermDistItvVO.getTipo());
				cantidadExpediente = servicioTramiteTrafico.getCountNumTramitesPermisos(docPermDistItvVO.getIdDocPermDistItv());
				if (stock == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado stock suficiente para poder imprimir.");
				} else if (cantidadExpediente == null || cantidadExpediente == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningun expediente asociado al documento.");
				} else if (cantidadExpediente > stock) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir los documento porque no tiene stock suficiente.");
				}
				if (!resultado.getError()) {
					servicioConsultaStock.descontarStock(jefaturaImpr, docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
				}
				if (!resultado.getError()) {
					if ("SI".equals(gestorPropiedades.valorPropertie("impresion.distintivosPermisos.jefatura"))) {
						servicioConsultaStock.descontarStock(jefaturaImpr, docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
					} else {
						servicioConsultaStock.descontarStock(jefaturaImpr, docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
					}
				}
				if (!resultado.getError()) {
					docPermDistItvVO.setFechaImpresion(fecha);
					String estadoAnt = docPermDistItvVO.getEstado().toString();
					docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum()));
					docPermDistItvDao.actualizar(docPermDistItvVO);
					servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha,estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRESION, docPermDistItvVO.getDocIdPerm(), idUsuario);
					resultado = servicioTramiteTrafico.actualizarTramitesImpresionPermisos(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Impresion_OK, idUsuario,
							docPermDistItvVO.getDocIdPerm(), ipConexion);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocFichasJefatura(DocPermDistItvVO docPermDistFichaVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistFichaVO.getEstado().toString())) {
				resultado = servicioJobsImpresion.imprimirFichasJefatura(docPermDistFichaVO, idUsuario, jefaturaImpr);
				if (!resultado.getError()) {
					String estadoAnt = docPermDistFichaVO.getEstado().toString();
					docPermDistFichaVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum()));
					docPermDistItvDao.actualizar(docPermDistFichaVO);
					servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistFichaVO.getTipo(), fecha, estadoAnt,docPermDistFichaVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRIMIENDO, docPermDistFichaVO.getDocIdPerm(), idUsuario);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistFichaVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistFichaVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistFichaVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocFichas(DocPermDistItvVO docPermDistFichaVO, BigDecimal idUsuario, Boolean esAuto, 
			Boolean esColegio, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			if (EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docPermDistFichaVO.getEstado().toString())) {
				if (esAuto) {
					resultado = servicioJobsImpresion.imprimirFichas(docPermDistFichaVO, idUsuario, esColegio);
					if (!resultado.getError()) {
						String estadoAnt = docPermDistFichaVO.getEstado().toString();
						docPermDistFichaVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum()));
						docPermDistItvDao.actualizar(docPermDistFichaVO);
						servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistFichaVO.getTipo(), fecha, estadoAnt,docPermDistFichaVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRIMIENDO, docPermDistFichaVO.getDocIdPerm(), idUsuario);
					}
				} else {
					docPermDistFichaVO.setFechaImpresion(fecha);
					String estadoAnt = docPermDistFichaVO.getEstado().toString();
					docPermDistFichaVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum()));
					docPermDistItvDao.actualizar(docPermDistFichaVO);
					servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistFichaVO.getTipo(), fecha, estadoAnt, docPermDistFichaVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRESION, docPermDistFichaVO.getDocIdPerm(), idUsuario);
					resultado = servicioTramiteTrafico.actualizarTramitesImpresionFichas(docPermDistFichaVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Impresion_OK, idUsuario,
							docPermDistFichaVO.getDocIdPerm(), ipConexion);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistFichaVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado Jefatura.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistFichaVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistFichaVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean validarDocMismoTipo(String[] sDocId) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<DocPermDistItvVO> listaDoc = docPermDistItvDao.getListaDocs(sDocId, Boolean.FALSE);
			if (listaDoc != null && !listaDoc.isEmpty()) {
				String documento = listaDoc.get(0).getTipo();
				for (DocPermDistItvVO docPermDistItvVO : listaDoc) {
					if (!documento.equals(docPermDistItvVO.getTipo())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se pueden imprimir documentos en bloque de distintio tipo.");
						break;
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los documentos indicados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los tipos de documentos a imprimir, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar los tipos de documentos a imprimir");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean descargarPdfFichasPermisos(DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				String numExp = docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
				FileResultBean fichaTecnicaPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha
						.getFechaConDate(docPermDistItvVO.getFechaAlta()), NOMBRE_FICH_IMPRESION + "_" + numExp, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fichaTecnicaPdf != null && fichaTecnicaPdf.getFile() != null) {
					resultado.addListaFicheros("FICHAS_TECNICAS_" + docPermDistItvVO.getDocIdPerm(), fichaTecnicaPdf.getFile());
					FileResultBean permisosPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PERMISOS_DEFINITIVO, utilesFecha.getFechaConDate(
							docPermDistItvVO.getFechaAlta()), NOMBRE_FICH_IMPRESION + "_" + numExp, ConstantesGestorFicheros.EXTENSION_PDF);
					if (permisosPdf != null && permisosPdf.getFile() != null) {
						resultado.addListaFicheros("PERMISOS_" + docPermDistItvVO.getDocIdPerm(), permisosPdf.getFile());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningún PDF de fichas téccnicas para la consulta:" + docPermDistItvVO.getDocIdPerm());
				}
				if (!resultado.getError()) {
					actualizarNumDescargarFicha(docPermDistItvVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede descargar. Debe estar en estado Impresion OK.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean descargarPdf(DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (docPermDistItvVO != null) {
				if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
					String subTipo = null;
					if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						subTipo = ConstantesGestorFicheros.PERMISOS_DEFINITIVO;
					} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						subTipo = ConstantesGestorFicheros.DISTINTIVOS;
					} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						subTipo = ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA;
					}
					FileResultBean ficheroPdf = null;
					String numExp = docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();

					if ("2631".equals(docPermDistItvVO.getContrato().getColegiado().getNumColegiado())) {
						ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipoAM(ConstantesGestorFicheros.MATE, subTipo, 
								utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), NOMBRE_FICH_IMPRESION + "_" + numExp, ConstantesGestorFicheros.EXTENSION_PDF);
					} else {
						ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, subTipo, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), NOMBRE_FICH_IMPRESION
							+ "_" + numExp, ConstantesGestorFicheros.EXTENSION_PDF);
					}
					if (ficheroPdf != null && ficheroPdf.getFile() != null) {
						resultado.setFichero(ficheroPdf.getFile());
						resultado.setNombreFichero(numExp + ConstantesGestorFicheros.EXTENSION_PDF);
						actualizarNumDescargarFicha(docPermDistItvVO);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha encontrado ningún PDF para la consulta:" + docPermDistItvVO.getDocIdPerm());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede descargar. Debe estar en estado Impresión OK.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos validos del documento para poder descargar la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean reiniciarDocImpr(String docId, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getDocPermPorDoc(docId, Boolean.TRUE);
			resultado = validarReiniciarDocImpr(docPermDistItvVO, docId);
			if (!resultado.getError()) {
				Date fechaPresentacion = null;
				String tipoTramite = null;
				String tipoTramiteCola = null;
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
					fechaPresentacion = docPermDistItvVO.getListaTramitesPermisoAsList().get(0).getFechaPresentacion();
					tipoTramite = docPermDistItvVO.getListaTramitesPermisoAsList().get(0).getTipoTramite();
					tipoTramiteCola = TipoTramiteTrafico.Solicitud_Permiso.getValorEnum();
				} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())) {
					fechaPresentacion = docPermDistItvVO.getListaTramitesEitvAsList().get(0).getFechaPresentacion();
					tipoTramite = docPermDistItvVO.getListaTramitesEitvAsList().get(0).getTipoTramite();
					tipoTramiteCola = TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum();
				}
				resultado = servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), new Date(), EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), 
						EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), OperacionPrmDstvFicha.REINICIAR_IMPR, docId, idUsuario);
				if (!resultado.getError()) {
					String datos = new SimpleDateFormat("ddMMyyyy").format(fechaPresentacion) + "_" + tipoTramite +
					"_" + docPermDistItvVO.getIdContrato() + "_" + docPermDistItvVO.getTipo() + "_" + docPermDistItvVO.getIdDocPermDistItv();
					ResultadoBean resultBean = servicioCola.crearSolicitud(docPermDistItvVO.getIdDocPermDistItv(),
							ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), gestorPropiedades.valorPropertie(NOMBRE_HOST),tipoTramiteCola, 
							new BigDecimal(docPermDistItvVO.getContrato().getColegiado().getUsuario().getIdUsuario()),
							new BigDecimal(docPermDistItvVO.getIdContrato()), datos);
					if (resultBean.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultBean.getMensaje());
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarReiniciarDocImpr(DocPermDistItvVO docPermDistItvVO, String docId) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(docPermDistItvVO == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos del documento con docId: " + docId);
		} else if(!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(docPermDistItvVO.getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede reiniciar el documento con docId: " + docId + " porque no se encuentra en estado Iniciado.");
		} else if(!TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo()) 
			&& !TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede reiniciar el documento con docId: " + docId + " porque no es un tipo documento valido para ello.");
		}else if(servicioCola.existeColaTramiteProceso(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), new BigDecimal(docPermDistItvVO.getIdDocPermDistItv()), gestorPropiedades.valorPropertie(NOMBRE_HOST))){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede reiniciar el documento con docId: " + docId + " porque se encuentra encolado para pedir su documentación.");
		} else {
			if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo()) &&
				(docPermDistItvVO.getListaTramitesPermiso() == null || docPermDistItvVO.getListaTramitesPermiso().isEmpty())){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede reiniciar el documento con docId: " + docId + " porque no tiene expediente asignados al documento.");
			} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo()) &&
					(docPermDistItvVO.getListaTramitesEitv() == null || docPermDistItvVO.getListaTramitesEitv().isEmpty())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede reiniciar el documento con docId: " + docId + " porque no tiene expediente asignados al documento.");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean reactivarDocJefatura(String docId, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getDocPermPorDoc(docId, Boolean.FALSE);
			resultado = validarReactivarDocJefatura(docPermDistItvVO, jefaturaImpr, docId);
			if (!resultado.getError()) {
				Date fecha = new Date();
				String estadoAnt = docPermDistItvVO.getEstado().toString();
				docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Iniciado.getValorEnum()));
				docPermDistItvVO.setFechaModificacion(fecha);
				docPermDistItvDao.actualizar(docPermDistItvVO);
				servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha, estadoAnt,docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.CAMBIO_ESTADO, docId, idUsuario);
				String proceso = "";
				String datos = null;
				if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
					proceso = ProcesosEnum.IMP_DSTV.getNombreEnum();
					datos = ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV + "_" + docPermDistItvVO.getDocIdPerm();
				} else {
					if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(docPermDistItvVO.getIdDocPermDistItv());
						if (listaTramites != null && !listaTramites.isEmpty()) {
							if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(listaTramites.get(0).getTipoTramite())) {
								proceso = ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
							} else {
								proceso = ProcesosEnum.IMP_PRM_CTIT.getNombreEnum();
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No existen tramites relacionados con el documento");
						}
					} else {
						proceso = ProcesosEnum.IMP_FICHA.getNombreEnum();
					}
				}
				if (!resultado.getError()) {
					ResultadoBean resultBean = servicioCola.crearSolicitud(docPermDistItvVO.getIdDocPermDistItv(),proceso, 
							gestorPropiedades.valorPropertie(NOMBRE_HOST), docPermDistItvVO.getTipo(),idUsuario, new BigDecimal(docPermDistItvVO.getIdContrato()), datos);
					if (resultBean.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultBean.getMensaje());
					}
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarReactivarDocJefatura(DocPermDistItvVO docPermDistItvVO, String jefaturaImpr, String docId) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if (docPermDistItvVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos del documento con docId: " + docId);
		} else if (!EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el documento con docId: " + docId + " porque no se encuentra en estado Finalizado con Error.");
		} else if (!"CO".equals(jefaturaImpr) && !jefaturaImpr.equals(docPermDistItvVO.getJefatura())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el documento con docId: " + docId + " porque no es de la jefatura de la que tiene permisos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean reactivarDoc(String docId, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getDocPermPorDoc(docId, Boolean.FALSE);
			if (docPermDistItvVO != null) {
				if (EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
					Date fecha = new Date();
					String estadoAnt = docPermDistItvVO.getEstado().toString();
					docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Iniciado.getValorEnum()));
					docPermDistItvVO.setFechaModificacion(fecha);
					docPermDistItvDao.actualizar(docPermDistItvVO);
					servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha, estadoAnt, docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.CAMBIO_ESTADO, docId, idUsuario);
					String proceso = "";
					String datos = null;
					if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						proceso = ProcesosEnum.IMP_DSTV.getNombreEnum();
						datos = ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV + "_" + docPermDistItvVO.getDocIdPerm();
					} else {
						if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
							List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(docPermDistItvVO.getIdDocPermDistItv());
							if (listaTramites != null && !listaTramites.isEmpty()) {
								if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(listaTramites.get(0).getTipoTramite())) {
									if("2631".equals(docPermDistItvVO.getContrato().getColegiado().getNumColegiado())) {
										proceso = ProcesosAmEnum.IMP_PRM_MATW.getNombreEnum();
									} else {
										proceso = ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
									}
								} else {
									if("2631".equals(docPermDistItvVO.getContrato().getColegiado().getNumColegiado())) {
										proceso = ProcesosAmEnum.IMP_PRM_CTIT.getNombreEnum();
									} else {
										proceso = ProcesosEnum.IMP_PRM_CTIT.getNombreEnum();
									}
								}
							} else {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("No existen tramites relacionados con el documento");
							}
						} else {
							if ("2631".equals(docPermDistItvVO.getContrato().getColegiado().getNumColegiado())) {
								proceso = ProcesosAmEnum.IMP_FICHA.getNombreEnum();
							}else {
								proceso = ProcesosEnum.IMP_FICHA.getNombreEnum();
							}
						}
					}
					if (!resultado.getError()) {
						ResultadoBean resultBean = servicioCola.crearSolicitud(docPermDistItvVO.getIdDocPermDistItv(),proceso, gestorPropiedades.valorPropertie(NOMBRE_HOST)
								, docPermDistItvVO.getTipo(), idUsuario, new BigDecimal(docPermDistItvVO.getIdContrato()), datos);
						if (resultBean.getError()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultBean.getMensaje());
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede revertir el documento con docId: " + docId + " porque no se encuentra en estado Finalizado con Error.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con docId: " + docId);
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar el documento con docId: " + docId);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public void actualizarNumDescargarFicha(DocPermDistItvVO docPermDistItvVO) throws Exception {
		if (docPermDistItvVO != null) {
			docPermDistItvVO.setNumDescarga(1L);
			docPermDistItvDao.actualizar(docPermDistItvVO);
		} else {
			throw new Exception("No existen datos del documento para poder actualizarlo");
		}
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarNumSerie(DocPermDistItvVO docPermDistItvVO, NumeroSerieBean numeroSerieBean) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(docPermDistItvVO.getIdDocPermDistItv());
			if (listaTramites != null && !listaTramites.isEmpty()) {
				int numTotalSerie = (Integer.parseInt(numeroSerieBean.getSerieFinalF())) - (Integer.parseInt(numeroSerieBean.getSerieFinalI()) - 1);
				if (numTotalSerie == listaTramites.size()) {
					int numSerie = Integer.parseInt(numeroSerieBean.getSerieFinalI());
					for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
						String nSerie = utiles.rellenarCeros(String.valueOf(numSerie), 8);
						tramiteTraficoVO.setnSeriePermiso(numeroSerieBean.getSerieInicial() + "-" + nSerie);
						servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
						numSerie++;
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento tiene asociado mas permisos que numero de serie ha introducir, docuemnto:" + docPermDistItvVO.getDocIdPerm());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los expedientes para introducir el numero de serie" + docPermDistItvVO.getDocIdPerm());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de introducir los numeros de serie de los permisos del documento: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de introducir los numeros de serie de los permisos del documento: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.getError()) {
			docPermDistItvVO.setNumDescarga(0L);
			docPermDistItvDao.actualizar(docPermDistItvVO);
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean descargarPdfFicha(DocPermDistItvVO docPermDistItvVO, int numPdf) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (docPermDistItvVO != null) {
				if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
					FileResultBean ficheroPdf = null;
					String nombreFichero = ServicioImpresionPermisosFichasDgt.NOMBRE_PERM_DSTV_EITV_IMPRESION + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato()
							.getColegiado().getNumColegiado() + "_" + numPdf;
					if ("2631".equals(docPermDistItvVO.getContrato().getColegiado().getNumColegiado())) {
						ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipoAM(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(
								docPermDistItvVO.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
					} else {
						ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA, utilesFecha.getFechaConDate(
							docPermDistItvVO.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
					}

					if (ficheroPdf != null && ficheroPdf.getFile() != null) {
						resultado.setFichero(ficheroPdf.getFile());
						resultado.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha encontrado ningún PDF para la consulta:" + docPermDistItvVO.getDocIdPerm());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos validos del documento para poder descargar la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean enviarMailImpresionDocumento(DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String subject = null;
			StringBuffer sb = getResumenEnvio(docPermDistItvVO);

			if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISO_IMPRESO);
			} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
				subject = gestorPropiedades.valorPropertie(SUBJECT_DISTINTIVO_IMPRESO);
			} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())) {
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA_PERMISOS);
			}

			String direcciones = docPermDistItvVO.getContrato().getCorreoElectronico();

			String tipoTramite = TipoDocumentoImprimirEnum.convertirValorATipoTramite(docPermDistItvVO.getTipo());

			if (docPermDistItvVO.getContrato().getCorreosTramites() != null && !docPermDistItvVO.getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : docPermDistItvVO.getContrato().getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direcciones = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			ResultBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direcciones, null, null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo a la jefatura para la solicitud de impresion de los permisos de circulación, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo a la jefatura para la solicitud de impresion de los permisos de circulación.");
		}
		return resultado;
	}

	private StringBuffer getResumenEnvio(DocPermDistItvVO docPermDistItvVO) {
		StringBuffer resultadoHtml = new StringBuffer();
		resultadoHtml.append("<br>");
		resultadoHtml.append("Buenos días, ");
		resultadoHtml.append("<br>");
		resultadoHtml.append("Pueden pasar a retirar los documentos solicitados con número de justificante: ").append(docPermDistItvVO.getDocIdPerm());
		resultadoHtml.append("<br>");
		resultadoHtml.append("Un saludo. ");
		resultadoHtml.append("<br>");
		resultadoHtml.append("<br>");
		return resultadoHtml;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrDto> getListaTramitesDistintivos(Long idDocPermDistItv) {
		try {
			List<TramiteTrafMatrVO> listaTramitesBBDD = getListaTramitesDistintivosVO(idDocPermDistItv);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				return conversor.transform(listaTramitesBBDD, TramiteTrafMatrDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites de los distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehNoMatOegamVO> getListaTramitesDuplicadosDstv(Long idDocPermDistItv) {
		try {
			return servicioDistintivoVehNoMat.getListaVehiculoDistintivosPorDocId(idDocPermDistItv);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de vehiculos de los distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesDistintivosVO(Long idDocPermDistItv) {
		try {
			return servicioTramiteTraficoMatriculacion.getListaTramitesDistintivosPorDocId(idDocPermDistItv);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites de los distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafDto> getListaTramitesFichasTecnicas(Long idDocPermDistItv) {
		try {
			List<TramiteTraficoVO> listaTramitesBBDD = servicioTramiteTrafico.getListaTramitesFichasTecnicasPorDocId(idDocPermDistItv);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				return conversor.transform(listaTramitesBBDD, TramiteTrafDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites de las fichas tecnicas, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafDto> getListaTramitesPermisos(Long idDocPermDistItv) {
		try {
			List<TramiteTraficoVO> listaTramitesBBDD = servicioTramiteTrafico.getListaTramitesPermisosPorDocId(idDocPermDistItv);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				return conversor.transform(listaTramitesBBDD, TramiteTrafDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites de los permisos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDocFichaPermiso(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esAuto,
			NumeroSerieBean numSerie, Boolean esColegio, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Integer cantidadExpediente = null;
			Date fecha = new Date();
			if (EstadoPermisoDistintivoItv.Generado.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
				String jefatura = null;
				if ("SI".equals(gestorPropiedades.valorPropertie("impresion.distintivosPermisos.jefatura"))) {
					jefatura = docPermDistItvVO.getJefatura();
				} else {
					jefatura = "M";
				}
				Long stock = servicioConsultaStock.cantidadStockPorTipo(jefatura, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum());
				cantidadExpediente = servicioTramiteTrafico.getCountNumTramitesPermisos(docPermDistItvVO.getIdDocPermDistItv());
				if (stock == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado stock suficiente para poder imprimir.");
				} else if (cantidadExpediente == null || cantidadExpediente == 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningun expediente asociado al documento.");
				} else if (cantidadExpediente > stock) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede imprimir los documento porque no tiene stock suficiente.");
				}
				if (!resultado.getError()) {
					if (esColegio) {
						servicioConsultaStock.descontarStock("M", docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
					} else {
						if ("SI".equals(gestorPropiedades.valorPropertie("impresion.distintivosPermisos.jefatura"))) {
							servicioConsultaStock.descontarStock(docPermDistItvVO.getJefatura(), docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
						} else {
							servicioConsultaStock.descontarStock("M", docPermDistItvVO.getTipo(), cantidadExpediente.longValue());
						}
					}
				}
				if (!resultado.getError()) {
					if (esAuto) {
						resultado = actualizarNumSerie(docPermDistItvVO, numSerie);
						if (!resultado.getError()) {
							resultado = servicioJobsImpresion.imprimirPermisosFichas(docPermDistItvVO, idUsuario, esColegio);
							if (!resultado.getError()) {
								String estadoAnt = docPermDistItvVO.getEstado().toString();
								docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum()));
								docPermDistItvDao.actualizar(docPermDistItvVO);
								servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha, estadoAnt, docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRIMIENDO, docPermDistItvVO.getDocIdPerm(), idUsuario);
							}
						}
					} else {
						docPermDistItvVO.setFechaImpresion(fecha);
						String estadoAnt = docPermDistItvVO.getEstado().toString();
						docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum()));
						docPermDistItvDao.actualizar(docPermDistItvVO);
						servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), fecha, estadoAnt, docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRESION, docPermDistItvVO.getDocIdPerm(), idUsuario);
						resultado = servicioTramiteTrafico.actualizarTramitesImpresionPermisos(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Impresion_OK, idUsuario,
								docPermDistItvVO.getDocIdPerm(), ipConexion);
						if (!resultado.getError()) {
							resultado = servicioTramiteTrafico.actualizarTramitesImpresionFichas(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Impresion_OK, idUsuario,
									docPermDistItvVO.getDocIdPerm(), ipConexion);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento " + docPermDistItvVO.getDocIdPerm() + " no se puede imprimir. Debe estar en estado Generado.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la gestion de documentos: " + docPermDistItvVO.getDocIdPerm());
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFichaDemandaKO(List<TramitesPermDistItvBean> listaTramitesBean, DocPermDistItvVO docPermDstvFicha,
			TramiteTraficoVO tramiteTraficoVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", listaTramitesBean.size());
			params.put("numTramitesPagina", 30);
			params.put("numeroTramites", listaTramitesBean.size());
			DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
			documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDstvFicha.getTipo()) + " de " + TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite()));
			documentoPermDistItvBean.setJefatura(tramiteTraficoVO.getContrato().getJefaturaTrafico().getDescripcion());
			documentoPermDistItvBean.setJefatura(tramiteTraficoVO.getContrato().getJefaturaTrafico().getDescripcion());
			documentoPermDistItvBean.setNumColegiado(tramiteTraficoVO.getContrato().getColegiado().getNumColegiado());
			documentoPermDistItvBean.setGestoria(tramiteTraficoVO.getContrato().getColegiado().getUsuario().getApellidosNombre());
			documentoPermDistItvBean.setNifGestor(tramiteTraficoVO.getContrato().getColegiado().getUsuario().getNif());
			documentoPermDistItvBean.setDocId(docPermDstvFicha.getDocIdPerm());
			documentoPermDistItvBean.setTramites(listaTramitesBean);
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
			XStream xStream = new XStream();
			xStream.processAnnotations(DocumentoPermDistItvBean.class);
			xml += xStream.toXML(documentoPermDistItvBean);
			xml = xml.replaceAll("__", "_");
			xml = xml.replaceAll("\n *<", "<");
			String nombreDoc = "justificantePermEitv";
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), nombreDoc, "pdf", xml,
					TipoDocumentoImprimirEnum.convertirTexto(docPermDstvFicha.getTipo()), params, null);
			String subTipo = null;
			String subject = null;
			String nombreFichero = docPermDstvFicha.getDocIdPerm();
			if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_EITV;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO_FICHA;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA_PERMISOS);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION_KO.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS) + " que han sido Erroneos";
				nombreFichero += "_Gestoria_Erroneos";
			}
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subTipo, utilesFecha.getFechaConDate(docPermDstvFicha.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF,
					fichero);
			resultado = enviarMailColegiadoPrmFicha(listaTramitesBean, tramiteTraficoVO.getContrato(), fichero, subject, docPermDstvFicha, Boolean.FALSE, "", tramiteTraficoVO.getTipoTramite());
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFichaProceso(List<TramitesPermDistItvBean> listaTramites, DocPermDistItvVO docPermDstvFicha, ContratoVO contrato,
			Boolean esDesdeError, String esConNive, String tipoTramite) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", listaTramites.size());
			params.put("numTramitesPagina", 30);
			params.put("numeroTramites", listaTramites.size());
			String xml = generarXmlJustfPermFichaProceso(listaTramites, docPermDstvFicha.getTipo(), docPermDstvFicha, contrato, esDesdeError, tipoTramite);
			String nombreDoc = "";
//			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			nombreDoc = "justificantePermEitv";
//			} else {
//				nombreDoc = "justificantePermEitv";
//			}
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), nombreDoc, "pdf", xml,
					TipoDocumentoImprimirEnum.convertirTexto(docPermDstvFicha.getTipo()), params, null);
			String subTipo = null;
			String subject = null;
			String nombreFichero = docPermDstvFicha.getDocIdPerm();
			if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_EITV;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO_FICHA;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA_PERMISOS);
				nombreFichero += "_Gestoria";
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION_KO.getValorEnum().equals(docPermDstvFicha.getTipo())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS) + " que han sido Erroneos";
				nombreFichero += "_Gestoria_Erroneos";
			}
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subTipo, utilesFecha.getFechaConDate(docPermDstvFicha.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF,
					fichero);
			resultado = enviarMailColegiadoPrmFicha(listaTramites, contrato, fichero, subject, docPermDstvFicha, esDesdeError, esConNive, tipoTramite);
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocGestoriaErroresTramites(List<TramiteTraficoVO> listaTramites, DocPermDistItvVO docPermDistItvVO, TipoDocumentoImprimirEnum tipoDocumento,
			String tipoTramite, ContratoVO contrato, String esSinNive) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfPermisoFichaError(docPermDistItvVO, listaTramites, params, tipoTramite, tipoDocumento, contrato);
			ReportExporter re = new ReportExporter();
			String nombreDoc = "justificantePermEitv";
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), nombreDoc, "pdf", xml,
					TipoDocumentoImprimirEnum.convertirTexto(tipoDocumento.getValorEnum()) + " Tramites Erroneos", params, null);
			String subTipo = null;
			String subject = null;
			if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_EITV;
				subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA);
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				subTipo = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISOS);
			}
			subject += " que han sido Erroneos";
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, subTipo, utilesFecha.getFechaConDate(docPermDistItvVO.getFechaAlta()), docPermDistItvVO.getDocIdPerm()
					+ "_Gestoria_Erroneos", ConstantesGestorFicheros.EXTENSION_PDF, fichero);

			String direccionCorreo = contrato.getCorreoElectronico();

			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			enviarMailColegiadoPrmFichaError(docPermDistItvVO.getDocIdPerm(), fichero, subject, direccionCorreo);
		} catch (JRException | ParserConfigurationException | OegamExcepcion e) {
			log.error("Se ha producido un error a la hora de generar el documento impreso con los tramites erroneos, error: ", e);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso con los tramites erroneos");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private void enviarMailColegiadoPrmFichaError(String docId, byte[] fichero, String subject, String direccion) {
		try {
			StringBuffer resultadoHtml = new StringBuffer();
			resultadoHtml.append("Este es el justificante de la solicitud de impresion de l@s " + TipoDocumentoImprimirEnum.FICHA_PERMISO.getNombreEnum()).append(
					", que no se han obtenido su ficha ténica o permiso.");
			resultadoHtml.append("<br>");
			FicheroBean ficheroBEan = new FicheroBean();
			ficheroBEan.setFicheroByte(fichero);
			ficheroBEan.setNombreDocumento(docId);
			ficheroBEan.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(resultadoHtml.toString(), Boolean.FALSE, null, subject, direccion, "distintivos@gestoresmadrid.org,desarrollo1@gestoresmadrid.org",
					null, null, ficheroBEan);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
				log.error("Ha sucedido un error a la hora de enviar el correo con los tramites erroneos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con los tramites erroneos, error: ", e);
		}
	}

	private String generarXmlJustfPermisoFichaError(DocPermDistItvVO docPermDistItvVO, List<TramiteTraficoVO> listaTramites, Map<String, Object> params, String tipoTramite,
			TipoDocumentoImprimirEnum tipoDocumento, ContratoVO contrato) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		documentoPermDistItvBean.setDocumento(tipoDocumento.getNombreEnumError() + " Erroneos de: " + TipoTramiteTrafico.convertirTexto(tipoTramite));
		documentoPermDistItvBean.setJefatura(contrato.getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(contrato.getColegiado().getNumColegiado());
		documentoPermDistItvBean.setGestoria(contrato.getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(contrato.getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<>();
		int cont = 1;
		int i = 0;
		for (TramiteTraficoVO tramiteBBDD : listaTramites) {
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());
			if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				tramitesPermDistItvBean.setPc("KO");
			} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento.getValorEnum())) {
				tramitesPermDistItvBean.setEitv("KO");
			}
			listaTramitesBean.add(i++, tramitesPermDistItvBean);
		}
		params.put("numeroTramites", listaTramitesBean.size());
		documentoPermDistItvBean.setTramites(listaTramitesBean);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	public String generarXmlJustfPermFichaProceso(List<TramitesPermDistItvBean> listaTramitesBean, String tipoDocumento, DocPermDistItvVO docPermDstvFicha, ContratoVO contrato, Boolean esDesdeError,
			String tipoTramite) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		documentoPermDistItvBean.setDocumento(TipoDocumentoImprimirEnum.convertirTexto(tipoDocumento) + " de " + TipoTramiteTrafico.convertirTexto(tipoTramite));
		if (esDesdeError) {
			documentoPermDistItvBean.setJefatura(contrato.getJefaturaTrafico().getDescripcion());
			documentoPermDistItvBean.setNumColegiado(contrato.getColegiado().getNumColegiado());
			documentoPermDistItvBean.setGestoria(contrato.getColegiado().getUsuario().getApellidosNombre());
			documentoPermDistItvBean.setNifGestor(contrato.getColegiado().getUsuario().getNif());
		} else {
			if (docPermDstvFicha.getContrato() != null && docPermDstvFicha.getContrato().getJefaturaTrafico() != null
					&& docPermDstvFicha.getContrato().getJefaturaTrafico().getDescripcion() != null
					&& !docPermDstvFicha.getContrato().getJefaturaTrafico().getDescripcion().isEmpty()) {
				documentoPermDistItvBean.setJefatura(docPermDstvFicha.getContrato().getJefaturaTrafico().getDescripcion());
			} else {
				servicioJefatura.getJefatura(docPermDstvFicha.getJefatura());
			}
			documentoPermDistItvBean.setNumColegiado(docPermDstvFicha.getContrato().getColegiado().getNumColegiado());
			documentoPermDistItvBean.setGestoria(docPermDstvFicha.getContrato().getColegiado().getUsuario().getApellidosNombre());
			documentoPermDistItvBean.setNifGestor(docPermDstvFicha.getContrato().getColegiado().getUsuario().getNif());
		}
		documentoPermDistItvBean.setDocId(docPermDstvFicha.getDocIdPerm());
		documentoPermDistItvBean.setTramites(listaTramitesBean);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	@Override
	@Transactional
	public void finalizarImpresionGestor(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String ipConexion) {
		try {
			docPermDistItvVO.setFechaImpresion(new Date());
			String estadoAnt = docPermDistItvVO.getEstado().toString();
			docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum()));
			docPermDistItvDao.actualizar(docPermDistItvVO);
			servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), new Date(),estadoAnt, docPermDistItvVO.getEstado().toString(), OperacionPrmDstvFicha.IMPRESO_GESTORIA, docPermDistItvVO.getDocIdPerm(), idUsuario);
			servicioTramiteTraficoMatriculacion.actualizarEstadoImpresionDocDistintivos(docPermDistItvVO.getIdDocPermDistItv(),
					EstadoPermisoDistintivoItv.IMPRESO_GESTORIA, idUsuario, docPermDistItvVO.getDocIdPerm(), ipConexion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el documento: " + docPermDistItvVO.getDocIdPerm() + ", error: ", e);
		}
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean anular(String docId, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (docId != null && !docId.isEmpty()) {
				DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getDocPermPorDoc(docId, Boolean.FALSE);
				if (docPermDistItvVO != null) {
					resultado = validarDocumentos(docPermDistItvVO);
					if (!resultado.getError()) {
						String estadoAnt = docPermDistItvVO.getEstado().toString();
						String estadoAnulado = EstadoPermisoDistintivoItv.ANULADO.getValorEnum();
						docPermDistItvVO.setEstado(new BigDecimal(estadoAnulado));
						docPermDistItvDao.actualizar(docPermDistItvVO);
						resultado = servicioEvolucionDocPrmDstvFicha.guardarEvolucion(docPermDistItvVO.getTipo(), new Date(), estadoAnt, estadoAnulado, OperacionPrmDstvFicha.CAMBIO_ESTADO, docPermDistItvVO.getDocIdPerm(),
								idUsuario);
						if (!resultado.getError()) {
							if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())) {
								servicioTramiteTrafico.actualizarTramitesAnuladosPermisos(docPermDistItvVO.getIdDocPermDistItv(),
										EstadoPermisoDistintivoItv.Iniciado, idUsuario, docPermDistItvVO.getDocIdPerm(), ipConexion);
							} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())) {
								servicioTramiteTrafico.actualizarTramitesAnuladosFichas(docPermDistItvVO.getIdDocPermDistItv(),
										EstadoPermisoDistintivoItv.Iniciado, idUsuario, docPermDistItvVO.getDocIdPerm(), ipConexion);
							} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
								servicioTramiteTraficoMatriculacion.actualizarTramitesAnuladosDistintivos(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.Iniciado,
										idUsuario, docPermDistItvVO.getDocIdPerm(), ipConexion);
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.getMensaje();
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.getMensaje();
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos en BBDD para el docId: " + docId);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un docId para poder anular el documento.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de anular el documento: " + docId + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de anular el documento: " + docId);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarDocumentos(DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if (!EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(docPermDistItvVO.getEstado().toString())
				&& !EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede anular un trámite que no esté Finalizado con Error o IMPR no Encontrado");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaKoBean generarDocGestoriaKO(GenKoBean genKoBean, Long idUsuario, String tipo, String tipoTramite, String jefatura,Date fechaGenExcel, ContratoVO contrato) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", genKoBean.getListaConsultaKo().size());
			params.put("numTramitesPagina", 30);
			params.put("numeroTramites", genKoBean.getListaConsultaKo().size());
			DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
			documentoPermDistItvBean.setDocumento("Listado KO de " + TipoDocumentoImprimirEnum.convertirTexto(tipo) + " de " + TipoTramiteTrafico.convertirTexto(tipoTramite));
			documentoPermDistItvBean.setJefatura(JefaturasJPTEnum.convertirJefatura(jefatura));
			documentoPermDistItvBean.setNumColegiado(contrato.getColegiado().getNumColegiado());
			documentoPermDistItvBean.setGestoria(contrato.getColegiado().getUsuario().getApellidosNombre());
			documentoPermDistItvBean.setNifGestor(contrato.getColegiado().getUsuario().getNif());
			documentoPermDistItvBean.setDocId("");
			documentoPermDistItvBean.setTramites(generarListaTramitesBeanKO(genKoBean.getListaConsultaKo()));
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
			XStream xStream = new XStream();
			xStream.processAnnotations(DocumentoPermDistItvBean.class);
			xml += xStream.toXML(documentoPermDistItvBean);
			xml = xml.replaceAll("__", "_");
			xml = xml.replaceAll("\n *<", "<");
			String nombreDoc = "justificantePermEitv";
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"), nombreDoc, "pdf", xml,
					TipoDocumentoImprimirEnum.convertirTexto(tipo), params, null);
			String nombreFichero = "justificanteKO_"+ genKoBean.getIdContrato() + "_"+tipo+"_"+tipoTramite+"_"+jefatura;
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.JUSTIFICANTES_IMPR_KO, utilesFecha.getFechaConDate(fechaGenExcel)
					, nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, fichero);
			resultado = enviarMailColegiadoGenKo(contrato, fichero, jefatura, tipo, tipoTramite, nombreFichero);
		} catch (Throwable e) {
			log.error("Se ha producido un error a la hora de generar el listado con los KO del tipo: " + tipo + " para los tramites de " + TipoTramiteTrafico.convertirTexto(tipoTramite) +
					" de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefatura) + " del contrato: " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el listado con los KO del tipo: " + tipo + " para los tramites de " + TipoTramiteTrafico.convertirTexto(tipoTramite) +
					" de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefatura) + " del contrato: " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia());
		}
		return resultado;
	}

	private ResultadoConsultaKoBean enviarMailColegiadoGenKo(ContratoVO contrato, byte[] fichero, String jefatura, String tipo, String tipoTramite, String nombreFichero) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean();
		String direccionCC = obtenerDireccion(jefatura);
		String dirOculta = gestorPropiedades.valorPropertie("direcciones.ocultas.impresiones.impr");
		String subject = "Justificante de KO de " + TipoDocumentoImprimirEnum.convertirTexto(tipo) + " para los tramites de " + TipoTramiteTrafico.convertirTexto(tipoTramite);
		StringBuffer sb = new StringBuffer();
		sb.append("Este es el justificante de los permisos de circulación que no se han podido descargar su documentación y se ha generado una relacion de KO.");
		FicheroBean ficheroBEan = new FicheroBean();
		ficheroBEan.setFicheroByte(fichero);
		ficheroBEan.setNombreDocumento(nombreFichero);
		ficheroBEan.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

		String direccionCorreo = contrato.getCorreoElectronico();
		if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
			for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
				if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
					direccionCorreo = correoContratoTramite.getCorreoElectronico();
					break;
				}
			}
		}
		try {
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccionCorreo, direccionCC, dirOculta, null, ficheroBEan);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo con el justificante de la solicitud de la generacion de KO para el contrato : " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia());
			}
		} catch (Throwable e) {
			log.error("Se ha producido un error a la hora de enviar el justificante de los KO para el contrato : " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia() + " del tipo: " + tipo + " para los tramites de " + TipoTramiteTrafico.convertirTexto(tipoTramite) +
					" de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefatura) +" , error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de enviar el justificante de los KO para el contrato : " + contrato.getColegiado().getNumColegiado() + "-" + contrato.getVia() + " del tipo: " + tipo + " para los tramites de " + TipoTramiteTrafico.convertirTexto(tipoTramite) +
			" de la jefatura: " + JefaturasJPTEnum.convertirJefatura(jefatura));
		}

		return resultado;
	}

	private List<TramitesPermDistItvBean> generarListaTramitesBeanKO(List<ConsultaKoVO> listaConsultaKo) {
		List<TramitesPermDistItvBean> listaTram = new ArrayList<>();
		int cont = 1;
		int i = 0; 
		for (ConsultaKoVO consultaKoVO : listaConsultaKo) {
			TramitesPermDistItvBean tramite = new TramitesPermDistItvBean();
			tramite.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(consultaKoVO.getFecha()));
			tramite.setNumero(cont++);
			tramite.setNumExpediente(consultaKoVO.getNumExpediente().toString());
			tramite.setMatricula(consultaKoVO.getMatricula());
			listaTram.add(i++, tramite);
		}
		return listaTram;
	}

}