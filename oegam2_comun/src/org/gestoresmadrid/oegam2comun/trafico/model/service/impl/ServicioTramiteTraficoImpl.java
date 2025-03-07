package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.dao.UnidadPoblacionalDao;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.UnidadPoblacionalVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.enumerados.AccionesGestionCreditos;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTraficoJustificante;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.ResumenJPTBean;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteCertificacionDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoCambioServicioDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoFactDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoSolInfoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TipoIntervinienteVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteCertificacionVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafCambioServicioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafFacturacionVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoTramiteTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioLocalidadDgt;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoGeneralYPersonalizadoBean;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.estadisticas.ctit.diarias.bean.EstadisticasTransmisionBean;
import org.gestoresmadrid.oegam2comun.trafico.estadisticas.ctit.diarias.bean.RespuestaEstadisticasTransmisionBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioAccionUsuario;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.credito.service.ServicioGestionCredito;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.view.dto.FormularioAutorizarTramitesDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseEstadoTramite;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.viafirma.cliente.exception.InternalException;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultadoAccionUsuarioBean;
import estadisticas.utiles.enumerados.Agrupacion;
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
import jxl.write.biff.RowsExceededException;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.constantes.ConstantesEstadisticas;
import trafico.utiles.enumerados.CertificadoCsv;
import trafico.utiles.enumerados.TipoAutorizacion;
import trafico.utiles.enumerados.TipoDatoActualizar;
import trafico.utiles.enumerados.TipoDatoBorrar;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
@Transactional
public class ServicioTramiteTraficoImpl implements ServicioTramiteTrafico {

	private static final long serialVersionUID = -2170165954444701322L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoImpl.class);

	@Autowired
	TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	TramiteTraficoFactDao tramitetrafFactDao;

	@Autowired
	TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Autowired
	TramiteTraficoTransDao tramiteTraficoTransDao;

	@Autowired
	TramiteTraficoSolInfoDao tramiteTraficoSolInfoDao;

	@Autowired
	TramiteTraficoDuplicadosDao tramiteTraficoDuplicadosDao;

	@Autowired
	TramiteTraficoCambioServicioDao tramiteTraficoCambioServicioDao;
	
	@Autowired
	ContratoDao contratoDao;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioFacturacion servicioFacturacion;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	EvolucionTramiteTraficoDao evolucionTramiteDao;

	@Autowired
	FabricanteDao fabricanteDao;

	@Autowired
	TramiteCertificacionDao tramiteCertificacionDao;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;

	@Autowired
	ServicioPermisosDgt servicioPermisosDgt;

	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	ServicioGestionCredito servicioGestionCredito;

	@Autowired
	UtilesColegiado utilesColegiado;
	
	@Autowired
	VehiculoTramiteTraficoDao vehiculoTramiteTraficoDao;
	
	@Autowired
	ServicioAccionUsuario servicioAccionUsuario;
	
	@Autowired
	ServicioLocalidadDgt servicioLocalidadDgt;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	UnidadPoblacionalDao unidadPoblacionalDao;
	
	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	

	private ArrayList<CampoPdfBean> camposFormateados;
	private Set<String> camposPlantilla;

	private String ruta;
	private PdfMaker pdf;
	private byte[] bytePdf;
	private String mensaje;

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesKOPendientesPorJefatura(String jefatura) {
		try {
			if (jefatura != null && !jefatura.isEmpty()) {
				return tramiteTraficoDao.getListaTramitesKOPendientesPorJefatura(jefatura);
			} else {
				log.error("Debe de indicar una jefatura para poder obtener el listado de sus KO.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de KO para la jefatura " + jefatura + ", error, e", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void guardarEvolucionTramiteClonado(TramiteTraficoVO tramiteTraficoVO) {
		EvolucionTramiteTraficoVO evTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
		id.setEstadoAnterior(BigDecimal.ZERO);
		id.setEstadoNuevo(BigDecimal.ONE);
		id.setFechaCambio(new Date());
		id.setNumExpediente(tramiteTraficoVO.getNumExpediente());
		evTramiteTraficoVO.setId(id);
		evTramiteTraficoVO.setUsuario(tramiteTraficoVO.getUsuario());

		evolucionTramiteDao.guardarOActualizar(evTramiteTraficoVO);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTraficoVO tramite = null;
		try {
			if (numExpediente != null) {
				tramite = tramiteTraficoDao.getTramite(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoSolInteveVO getTramiteInteve(BigDecimal numExpediente, String codigoTasa) {
		TramiteTraficoSolInteveVO tramite = null;
		try {
			if (numExpediente != null) {
				tramite = tramiteTraficoDao.getTramiteInteve(numExpediente, codigoTasa);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaExpedientesPorListNumExp(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		try {
			if (numExpedientes != null) {
				return tramiteTraficoDao.getListaExpedientesPorListNumExp(numExpedientes, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de los trámites, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public List<TramiteTraficoVO> getListaExpedientesGenDocBase(BigDecimal[] numExpedientes) {
		try {
			if (numExpedientes != null) {
				return tramiteTraficoDao.getListaExpedientesGenDocBase(numExpedientes);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de los trámites, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public TramiteTrafDto getTramiteDto(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafDto result = null;
		TramiteTraficoVO tramiteVO = getTramite(numExpediente, tramiteCompleto);
		if (tramiteVO != null) {
			result = conversor.transform(tramiteVO, TramiteTrafDto.class);
		}
		return result;
	}

	@Override
	public TramiteTraficoDto getTramiteTraficoDto(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTraficoDto result = null;
		TramiteTraficoVO tramiteVO = getTramite(numExpediente, tramiteCompleto);
		if (tramiteVO != null) {
			result = conversor.transform(tramiteVO, TramiteTraficoDto.class);
		}
		return result;
	}

	@Override
	public List<TramiteTrafDto> getListaTramiteTrafico(String[] numsExpedientes, boolean tramiteCompleto) {
		try {
			if (numsExpedientes != null) {
				List<TramiteTraficoVO> listaTramitesBBDD = tramiteTraficoDao.getTramiteClonar(utiles.convertirStringArrayToBigDecimal(numsExpedientes), tramiteCompleto);
				if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
					return conversor.transform(listaTramitesBBDD, TramiteTrafDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite: " + numsExpedientes, e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		try {
			if (numExpedientes != null) {
				List<TramiteTraficoVO> listaTramitesBBDD = tramiteTraficoDao.getTramiteClonar(numExpedientes, tramiteCompleto);
				if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
					return listaTramitesBBDD;
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de trámites, error:  ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public TramiteTrafDto getTramiteDtoImpresion05(BigDecimal numExpediente, Boolean tramiteCompleto) {
		TramiteTrafDto result = null;
		TramiteTraficoVO tramiteVO = getTramite(numExpediente, tramiteCompleto);
		if (tramiteVO != null) {
			result = conversor.transform(tramiteVO, TramiteTrafDto.class);

			if (tramiteVO.getIntervinienteTraficos() != null && !tramiteVO.getIntervinienteTraficos().isEmpty()) {
				for (IntervinienteTraficoVO interviniente : tramiteVO.getIntervinienteTraficosAsList()) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteVO.getTipoTramite())) {
						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
							result.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteVO.getTipoTramite()) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteVO
							.getTipoTramite())) {
						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.TransmitenteTrafico.getValorEnum())) {
							result.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafBajaVO tramiteBajaVO = null;
		try {
			if (numExpediente != null) {
				tramiteBajaVO = tramiteTraficoBajaDao.getTramiteBaja(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de baja: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteBajaVO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafBajaVO> bajasExcel(String jefatura) {
		List<TramiteTrafBajaVO> lista = null;
		try {
			lista = tramiteTraficoBajaDao.bajasExcel(jefatura);
		} catch (Exception e) {
			log.error("Error al recuperar las bajasExcel", e);
		}
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafDuplicadoVO> duplicadosExcel(String jefatura) {
		List<TramiteTrafDuplicadoVO> lista = null;
		try {
			lista = tramiteTraficoDuplicadosDao.duplicadosExcel(jefatura);
		} catch (Exception e) {
			log.error("Error al recuperar las bajasExcel", e);
		}
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafCambioServicioVO> cambioServicioExcel(String jefatura) {
		List<TramiteTrafCambioServicioVO> lista = null;
		try {
			lista = tramiteTraficoCambioServicioDao.cambioServicioExcel(jefatura);
		} catch (Exception e) {
			log.error("Error al recuperar las bajasExcel", e);
		}
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafTranVO tramiteTranVO = null;
		try {
			if (numExpediente != null) {
				tramiteTranVO = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de transmisión: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteTranVO;
	}

	@Override
	public void evictTramiteTransmision(TramiteTrafTranVO tramiteTrafTranVO) {
		if (tramiteTrafTranVO != null) {
			tramiteTraficoTransDao.evict(tramiteTrafTranVO);
		}
	}

	@Override
	public void evictTramiteBaja(TramiteTrafBajaVO tramiteTrafBajaVO) {
		if (tramiteTrafBajaVO != null) {
			tramiteTraficoBajaDao.evict(tramiteTrafBajaVO);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafDuplicadoVO tramiteDuplicadoVO = null;
		try {
			if (numExpediente != null) {
				tramiteDuplicadoVO = tramiteTraficoDuplicadosDao.getTramiteDuplicado(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de duplicado: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteDuplicadoVO;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafCambioServicioVO getTramiteCambioServicio(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafCambioServicioVO tramiteCambioServicioVO = null;
		try {
			if (numExpediente != null) {
				tramiteCambioServicioVO = tramiteTraficoCambioServicioDao.getTramiteCambServ(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de cambio de servicio: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteCambioServicioVO;
	}

	@Override
	public ResultBean custodiar(TramiteTrafDto tramiteDto, String aliasColegiado) throws UnsupportedEncodingException, InternalException {
		ResultBean result = new ResultBean();
		XStream xstream = new XStream();
		xstream.processAnnotations(TramiteTrafDto.class);
		String xml = xstream.toXML(tramiteDto);

		byte[] xmlByte = firmarXml(xml, aliasColegiado);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(xmlByte);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.NO_TELEMATICO);
		ficheroBean.setSubTipo(TipoTramiteTrafico.convertirTexto(tramiteDto.getTipoTramite()));
		ficheroBean.setNombreDocumento(tramiteDto.getNumExpediente().toString());
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteDto.getNumExpediente()));
		ficheroBean.setSobreescribir(true);

		try {
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero de custodia para una matriculación.", e, tramiteDto.getNumExpediente().toString());
			result.setMensaje("Error al guardar el fichero de custodia para una matriculación");
			result.setError(true);
		}

		return result;
	}

	private byte[] firmarXml(String xml, String aliasColegiado) throws InternalException, UnsupportedEncodingException {
		byte[] Afirmar = new String(xml.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		return utilesViafirma.firmarXMLTrafico(Afirmar, aliasColegiado);
	}

	@Transactional
	@Override
	public ResultBean guardarActualizarTramiteFacturacion(TramiteTrafFacturacionDto tramiteFacturacionDto) {
		ResultBean resultado = new ResultBean();
		if (tramiteFacturacionDto.getDireccion() != null && tramiteFacturacionDto.getPersona() != null && tramiteFacturacionDto.getPersona().getNumColegiado() != null && tramiteFacturacionDto
				.getPersona().getNif() != null) {

			TramiteTrafFacturacionVO tramiteFact = conversor.transform(tramiteFacturacionDto, TramiteTrafFacturacionVO.class);

			tramitetrafFactDao.guardarOActualizar(tramiteFact);

			ResultBean resultPer = servicioPersona.guardarActualizar(tramiteFacturacionDto.getPersona());
			if (!resultPer.getError()) {
				ResultBean resultDir = servicioDireccion.guardarActualizar(tramiteFacturacionDto.getDireccion());
				if (!resultDir.getError()) {
					PersonaDireccionDto perDir = new PersonaDireccionDto();
					perDir.setDireccion(tramiteFacturacionDto.getDireccion());
					perDir.setPersona(tramiteFacturacionDto.getPersona());
					perDir.setNif(tramiteFacturacionDto.getPersona().getNif());
					perDir.setNumColegiado(tramiteFacturacionDto.getPersona().getNumColegiado());
					perDir.setFechaInicio(utilesFecha.getFechaActual());
					servicioPersonaDireccion.guardarActualizar(perDir);
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultBean getTipoTramite(String[] arrayExpediente) {
		ResultBean resultado = new ResultBean();
		try {
			for (String numExp : arrayExpediente) {
				String tipoTramite = tramiteTraficoDao.getTipoTramite(new BigDecimal(numExp));
				resultado.addAttachment(numExp, tipoTramite);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de tramites de los expedientes, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los tipos de tramites de los expedientes.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public String getTipoTramite(BigDecimal numExpediente) {
		try {
			return tramiteTraficoDao.getTipoTramite(numExpediente);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de tramites de los expedientes, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoSinValidacion(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario, boolean notificar,
			String textoNotificacion) {
		ResultBean result = new ResultBean();
		result = cambiarEstadoConEvolucion(numExpediente, antiguoEstado, nuevoEstado, notificar, textoNotificacion, idUsuario);

		if (!result.getError()) {
			TramiteTraficoVO tramiteTrafico = (TramiteTraficoVO) result.getAttachment(TRAMITE_TRAFICO);
			accionesCambiarEstadoSinValidacion(tramiteTrafico, antiguoEstado, nuevoEstado, idUsuario);
		}

		return result;
	}

	private void accionesCambiarEstadoSinValidacion(TramiteTraficoVO tramiteTrafico, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario) {
		// Eliminar de la cola
		if (EstadoTramiteTrafico.Pendiente_Check_Ctit.equals(antiguoEstado) || EstadoTramiteTrafico.Pendiente_Envio.equals(antiguoEstado) || EstadoTramiteTrafico.Pendiente_Consulta_BTV.equals(
				antiguoEstado)) {
			servicioCola.eliminar(tramiteTrafico.getNumExpediente(), null);
		}

		// Devolver créditos
		if ((EstadoTramiteTrafico.Finalizado_Excel_Incidencia.equals(nuevoEstado) && EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura.equals(antiguoEstado)) || ((EstadoTramiteTrafico.Iniciado.equals(
				nuevoEstado) || EstadoTramiteTrafico.Validado_PDF.equals(nuevoEstado)) && EstadoTramiteTrafico.Pendiente_Envio_Excel.equals(antiguoEstado))) {
			if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
				servicioCredito.devolverCreditos(tramiteTrafico.getTipoTramite(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), 1, idUsuario, ConceptoCreditoFacturado.DEBB,
						tramiteTrafico.getNumExpediente().toString());
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
				servicioCredito.devolverCreditos(tramiteTrafico.getTipoTramite(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), 1, idUsuario, ConceptoCreditoFacturado.DEBD,
						tramiteTrafico.getNumExpediente().toString());
			}
		}

		// Borrar de la tabla de facturación si se ha cambiado el estado del trámite
		// de Finalizado pdf --> Modificado a petición del colegiado.
		if (tramiteTrafico.getNumExpediente() != null && tramiteTrafico.getTasa() != null && EstadoTramiteTrafico.Finalizado_PDF.equals(antiguoEstado) && EstadoTramiteTrafico.Modificado_Peticion
				.equals(nuevoEstado)) {
			servicioFacturacion.eliminarFacturacionTramite(tramiteTrafico.getNumExpediente(), tramiteTrafico.getTasa().getCodigoTasa());
		}

	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoConEvolucion(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, boolean notificar, String textoNotificacion,
			BigDecimal idUsuario) {
		return cambiarEstadoConPosibleEvolucion(true, numExpediente, antiguoEstado, nuevoEstado, notificar, textoNotificacion, idUsuario);
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoFinalizadoError(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraficoVO = tramiteTraficoDao.getTramite(numExpediente, Boolean.FALSE);
			if (tramiteTraficoVO != null) {
				if (EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
					tramiteTraficoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_Con_Error, EstadoTramiteTrafico.Iniciado, idUsuario);
				} else if (EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
					tramiteTraficoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio, EstadoTramiteTrafico.Iniciado, idUsuario);
				} else if (EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
					tramiteTraficoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_Excel_Incidencia, EstadoTramiteTrafico.Iniciado, idUsuario);
				} else {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje(" El expediente " + numExpediente + " no se encuentra 'Finalizado con error' ");
				}
			} else {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("El expediente no existe");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del trámite, error: ", e, numExpediente.toString());
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("El expediente" + numExpediente + "no se encuentra 'Finalizado con error'");

		}
		if (resultBean != null && resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoConEvolucionEstadosPermitidos(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado,
			EstadoTramiteTrafico[] estadosPermitidos, boolean notificar, String textoNotificacion, BigDecimal idUsuario) {
		ResultBean rs = comprobarEstadosPermitidos(numExpediente, estadosPermitidos);
		if (rs.getError()) {
			return rs;
		}
		return cambiarEstadoConEvolucion(numExpediente, antiguoEstado, nuevoEstado, notificar, textoNotificacion, idUsuario);
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoConEvolucionEstadosPermitidos(BigDecimal numExpediente, EstadoTramiteTrafico nuevoEstado, EstadoTramiteTrafico[] estadosPermitidos, boolean notificar,
			String textoNotificacion, BigDecimal idUsuario) {
		TramiteTraficoVO tramite = tramiteTraficoDao.buscarPorExpediente(numExpediente);
		if (tramite == null) {
			return new ResultBean(true, "No se ha encontrado el trámite");
		}
		tramiteTraficoDao.evict(tramite);
		if (tramite.getEstado() == null) {
			return cambiarEstadoConEvolucionEstadosPermitidos(numExpediente, null, nuevoEstado, estadosPermitidos, notificar, textoNotificacion, idUsuario);
		}
		return cambiarEstadoConEvolucionEstadosPermitidos(numExpediente, EstadoTramiteTrafico.convertir(tramite.getEstado().toString()), nuevoEstado, estadosPermitidos, notificar, textoNotificacion,
				idUsuario);
	}

	private ResultBean comprobarEstadosPermitidos(BigDecimal numExpediente, EstadoTramiteTrafico[] estadosPermitidos) {
		if (estadosPermitidos != null) {
			TramiteTraficoVO tramite = tramiteTraficoDao.buscarPorExpediente(numExpediente);
			if (tramite == null) {
				return new ResultBean(true, "No se ha encontrado el trámite");
			}
			if (!EstadoTramiteTrafico.in(tramite.getEstado(), estadosPermitidos)) {
				return new ResultBean(true, "El trámite está en un estado en el que no es posible realizar el cambio de estado");
			}
			tramiteTraficoDao.evict(tramite);
		}
		return new ResultBean(false);
	}

	private ResultBean cambiarEstadoConPosibleEvolucion(boolean evolucion, BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, boolean notificar,
			String textoNotificacion, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();

		if (nuevoEstado == null) {
			return new ResultBean(true, "No se ha indicado el estado para el cambio de estado");
		}

		TramiteTraficoVO tramiteTrafico = cambiarEstado(numExpediente, nuevoEstado);

		if (tramiteTrafico == null) {
			return new ResultBean(true, "Error durante el cambio de estado del trámite");
		}

		log.debug("Cambio de estado a " + nuevoEstado.getNombreEnum() + " del trámite: " + numExpediente);

		if (evolucion) {
			guardarEvolucionTramite(tramiteTrafico.getNumExpediente(), antiguoEstado, nuevoEstado, idUsuario);
		}

		if (notificar) {
			NotificacionDto dto = new NotificacionDto();
			dto.setDescripcion(textoNotificacion);
			if (antiguoEstado != null) {
				dto.setEstadoAnt(new BigDecimal(antiguoEstado.getValorEnum()));
			}
			if (nuevoEstado != null) {
				dto.setEstadoNue(new BigDecimal(nuevoEstado.getValorEnum()));
			}
			dto.setIdTramite(tramiteTrafico.getNumExpediente());
			if (tramiteTrafico.getUsuario() != null) {
				dto.setIdUsuario(tramiteTrafico.getUsuario().getIdUsuario());
			}
			dto.setTipoTramite(tramiteTrafico.getTipoTramite());
			servicioNotificacion.crearNotificacion(dto);
		}

		result.addAttachment(TRAMITE_TRAFICO, tramiteTrafico);
		result.setError(false);
		return result;
	}

	private TramiteTraficoVO cambiarEstado(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo) {
		TramiteTraficoVO tramite = tramiteTraficoDao.buscarPorExpediente(numExpediente);
		// Modificar fecha de presentación en los siguientes casos
		if ((EstadoTramiteTrafico.Finalizado_PDF.equals(estadoNuevo) && tramite.getFechaPresentacion() == null) || EstadoTramiteTrafico.Finalizado_Con_Error.equals(estadoNuevo)
				|| (EstadoTramiteTrafico.Finalizado_Telematicamente.equals(estadoNuevo) && (new BigDecimal(EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura.getValorEnum()).equals(tramite.getEstado())
						|| tramite.getFechaPresentacion() == null)) || EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura.equals(estadoNuevo)) {
			tramite.setFechaPresentacion(new Date());
		}
		tramite.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
		tramite.setFechaUltModif(new Date());
		tramite = (TramiteTraficoVO) tramiteTraficoDao.guardarOActualizar(tramite);

		// Para las nuevas solicitudes de informacion
		if (tramite != null && tramite.getTipoTramite().equals(TipoTramiteTrafico.Solicitud_Inteve.getValorEnum())) {
			actualizarSiesSolicitud(tramite.getNumExpediente());
		}

		if (tramite != null) {
			return tramite;
		}
		return null;
	}

	private void actualizarSiesSolicitud(BigDecimal numExpediente) {
		TramiteTrafSolInfoVO solinfo = tramiteTraficoSolInfoDao.getTramiteTrafSolInfoNuevo(numExpediente, Boolean.TRUE);
		if (solinfo != null) {
			for (TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : solinfo.getSolicitudes()) {
				tramiteTrafSolInfoVehiculoVO.setEstado(new BigDecimal(org.gestoresmadrid.core.model.enumerados.EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum()));
				tramiteTraficoSolInfoDao.guardarOActualizarTramSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
			}
		}
	}

	private EvolucionTramiteTraficoVO guardarEvolucionTramite(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario) {
		EvolucionTramiteTraficoVO evolucion = null;
		evolucion = new EvolucionTramiteTraficoVO();
		evolucion.setId(new EvolucionTramiteTraficoPK());

		if (antiguoEstado != null) {
			evolucion.getId().setEstadoAnterior(new BigDecimal(antiguoEstado.getValorEnum()));
		} else {
			evolucion.getId().setEstadoAnterior(BigDecimal.ZERO);
		}

		if (nuevoEstado != null) {
			evolucion.getId().setEstadoNuevo(new BigDecimal(nuevoEstado.getValorEnum()));
		} else {
			evolucion.getId().setEstadoNuevo(BigDecimal.ZERO);
		}

		if (numExpediente != null) {
			evolucion.getId().setNumExpediente(numExpediente);
		} else {
			evolucion.getId().setNumExpediente(BigDecimal.ZERO);
		}

		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		if (usuario != null && usuario.getIdUsuario() != null) {
			evolucion.setUsuario(usuario);
		} else {
			usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
		}

		servicioEvolucionTramite.guardar(evolucion);

		return evolucion;
	}

	@Override
	public int presentarJPT(String idDoc) {
		String query;
		if (idDoc.length() == 36) {
			// Documento base antiguo
			query = TramiteTraficoVO.PRESENTACION_JPT_YBPDF;
		} else {
			// Documento base nuevo
			query = TramiteTraficoVO.PRESENTACION_JPT;
		}
		String[] namedParemeters = { "idDocBase" };
		String[] namedValues = { idDoc };
		int result = tramiteTraficoDao.executeNamedQuery(query, namedParemeters, namedValues);

		return result;
	}

	public ResultBean modificarReferenciaPropia(BigDecimal numExpediente, BigDecimal idUsuario, String nuevaReferenciaPropia) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraficoVO = tramiteTraficoDao.getTramite(numExpediente, Boolean.FALSE);
			if (tramiteTraficoVO != null) {
					tramiteTraficoVO.setRefPropia(nuevaReferenciaPropia);
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_Con_Error, EstadoTramiteTrafico.Iniciado, idUsuario);
			} else {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("El expediente no existe");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar la referencia propia del tramite, error: ", e, numExpediente.toString());
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error a la hora de cambiar la referencia propia del tramite" + numExpediente );

		}
		if (resultBean != null && resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultBean;
	}

	@Override
	public List<TramiteTraficoVO> obtenerNoPresentados() {
		List<TramiteTraficoVO> lista = tramiteTraficoDao.obtenerNoPresentados();
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTraficoVO> recuperaTramitesBloqueantes() {
		List<TramiteTraficoVO> lista = tramiteTraficoDao.recuperaTramitesBloqueantes();
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTraficoVO> obtenerPresentados(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		List<TramiteTraficoVO> lista = tramiteTraficoDao.obtenerPresentados(fechaPresentado, carpeta, jefaturaJpt);
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public int getCantidadTipoEstadistica(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		return tramiteTraficoDao.getCantidadTipoEstadistica(fechaPresentado, carpeta, jefaturaJpt);
	}

	@Override
	public int getCantidadTipoEstadisticaNive(Date fechaPresentado, String jefaturaJpt) throws Exception {
		return tramiteTraficoDao.getCantidadTipoEstadisticaNive(fechaPresentado, jefaturaJpt);
	}

	@Override
	public int getNumColegiadosDistintos(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		return tramiteTraficoDao.getNumColegiadosDistintos(fechaPresentado, carpeta, jefaturaJpt);
	}

	@Override
	public int getNumColegiadosDistintosNive(Date fechaPresentado, String jefaturaJpt) throws Exception {
		return tramiteTraficoDao.getNumColegiadosDistintosNive(fechaPresentado, jefaturaJpt);
	}

	@Override
	public ResumenJPTBean getTramitesDocumentoBase(String idDoc) {
		ResumenJPTBean resumen = new ResumenJPTBean();
		if (idDoc != null) {
			resumen.setExpedientes(tramiteTraficoDao.getTramitesDocumentoBase(idDoc));
			resumen.setIdDocumento(idDoc);
		}
		return resumen;
	}

	@Override
	public String getMismoTipoCreditoTramiteTransmision(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramite) {
		String tipoTransferencia = tramiteTraficoTransDao.recuperarMismoTipoCreditoTramiteTransmision(numerosExpedientes, tipoTramite);
		if (tipoTransferencia == null) {
			return null;
		}
		return tipoTransferencia;
	}

	@Override
	public TipoTransferenciaActual getTipoTransferenciaActual(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramiteTrafico) {
		String tipoTransmision = tramiteTraficoTransDao.recuperarMismoTipoCreditoTramiteTransmision(numerosExpedientes, tipoTramiteTrafico);
		if (tipoTransmision == null) {
			return null;
		}
		return TipoTransferenciaActual.convertir(tipoTransmision);
	}

	@Override
	public int recuperarTipoTramiteMatriculacionSiEsElMismo(BigDecimal[] numExpedientes) {
		return tramiteTraficoMatrDao.recuperarTipoTramiteMatriculacionSiEsElMismo(numExpedientes);
	}

	@Override
	public TipoTramiteTrafico recuperarTipoTramiteSiEsElMismo(BigDecimal[] numExpedientes) {
		return TipoTramiteTrafico.convertir(tramiteTraficoDao.getTipoTramiteTraficoPorExpedientes(numExpedientes));
	}

	@Override
	public List<Long> getListaTramitesMismoContratoPorExpedientes(BigDecimal[] numExpedientes) {
		return tramiteTraficoDao.getListaTramitesMismoContratoPorExpediente(numExpedientes);
	}

	@Override
	public int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo) {
		return tramiteTraficoDao.getNumTramitePorVehiculo(numExpediente, idVehiculo);
	}

	@Override
	public TipoTransferencia getTipoTransferencia(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramiteTrafico) {
		String tipoTransmision = tramiteTraficoTransDao.recuperarMismoTipoCreditoTramiteTransmision(numerosExpedientes, tipoTramiteTrafico);
		if (tipoTransmision == null) {
			return null;
		}
		return TipoTransferencia.convertir(tipoTransmision);
	}

	@Override
	public void cambiarAPresentado(List<TramiteTraficoVO> listaTramitesTraficoVO) throws OegamExcepcion {
		try {
			if (listaTramitesTraficoVO != null && !listaTramitesTraficoVO.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramitesTraficoVO) {
					tramiteTraficoVO.setFechaPresentacionJpt(Calendar.getInstance().getTime());
					tramiteTraficoVO.setPresentadoJpt(EstadoPresentacionJPT.Presentado.getValorEnum());
					tramiteTraficoVO.setYbestado(new BigDecimal(DocumentoBaseEstadoTramite.RECIBIDO_PROCESADO.getValorEnum()));
					actualizarTramite(tramiteTraficoVO);
				}
			}
		} catch (Exception e) {
			log.error("Error al cambiar el trámite a presentado por tener Nive", e.getMessage());
		}
	}

	@Override
	public String validarRelacionMatriculas(String[] codSeleccionados) {
		BigDecimal[] estados = null;
		String mensajeError = null;
		BigDecimal[] numExpedientes = null;
		if (codSeleccionados != null) {
			numExpedientes = utiles.convertirStringArrayToBigDecimal(codSeleccionados);
		} else {
			return "Debe seleccionar algún expediente para poder realizar la impresión";
		}
		// Se validan número de trámites
		int maxNum = Integer.valueOf(gestorPropiedades.valorPropertie("matw.relacionMatriculas.maximo"));
		if (codSeleccionados.length > maxNum) {
			mensajeError = "El número de trámites seleccionados para la Relación de Matrículas no debe ser superior a " + maxNum;
		} else {
			estados = new BigDecimal[2];
			Long estado = Long.valueOf(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum());
			estados[0] = new BigDecimal(estado);
			estado = Long.valueOf(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum());
			estados[1] = new BigDecimal(estado);

			// Validamos estados
			List<TramiteTraficoVO> lista = tramiteTraficoDao.existeNumExpedientesEstados(numExpedientes, estados);

			if (lista == null) {
				mensajeError = "No existen tramites con esos estados.";
			} else if (lista.size() == codSeleccionados.length) {
				if (!comprobarFechaPresentacion(lista)) {
					mensajeError = "No todos los trámites tienen la misma fecha de presentación";
				}
			} else {
				mensajeError = "No todos los trámites tienen los estados correctos, deben estar en el estado Finalizado Telemáticamente o Finalizado Telemáticamente Impreso";
			}
		}
		return mensajeError;
	}

	private boolean comprobarFechaPresentacion(List<TramiteTraficoVO> lista) {
		String fechaPresentacion = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (lista != null) {
			for (TramiteTraficoVO tramiteTraficoVO : lista) {
				if (fechaPresentacion == null) {
					fechaPresentacion = sdf.format(tramiteTraficoVO.getFechaPresentacion());
				} else if (!fechaPresentacion.equals(sdf.format(tramiteTraficoVO.getFechaPresentacion()))) {
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public ResultBean validarPDF417(String[] numExpedientes) {
		BigDecimal[] estados = new BigDecimal[2];
		BigDecimal[] numExps = utiles.convertirStringArrayToBigDecimal(numExpedientes);
		ResultBean resultado = null;

		Long estado = Long.valueOf(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
		estados[0] = new BigDecimal(estado);
		estado = Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
		estados[1] = new BigDecimal(estado);

		List<TramiteTraficoVO> lista = tramiteTraficoDao.noExisteNumExpedientesEstados(numExps, estados);

		if (lista != null && lista.isEmpty()) {
			resultado = new ResultBean(false, "No existen expedientes en ese estado");
		} else if (lista != null && !lista.isEmpty()) {
			resultado = new ResultBean(true, "Existen expedientes en ese estado");
		}

		return resultado;
	}

	@Override
	public ResultBean validarPDFPresentacionTelematica(String[] numExpedientes) {
		BigDecimal[] estados = new BigDecimal[1];
		BigDecimal[] numExps = utiles.convertirStringArrayToBigDecimal(numExpedientes);
		ResultBean resultado = null;

		Long estado = Long.valueOf(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum());
		estados[0] = new BigDecimal(estado);

		List<TramiteTraficoVO> lista = tramiteTraficoDao.noExisteNumExpedientesEstados(numExps, estados);

		if (lista != null && lista.isEmpty()) {
			resultado = new ResultBean(false, "No existen expedientes en ese estado");
		} else if (lista != null && !lista.isEmpty()) {
			resultado = new ResultBean(true, "Existen expedientes en ese estado");
		}

		return resultado;
	}

	@Override
	public ResultBean comprobarEstadosTramites(BigDecimal[] numExp, EstadoTramiteTrafico[] estados) {
		BigDecimal[] estadoTramite = new BigDecimal[estados.length];

		for (int i = 0; i < estados.length; i++) {
			estadoTramite[i] = new BigDecimal(estados[i].getValorEnum());
		}

		List<TramiteTraficoVO> lista = tramiteTraficoDao.comprobarTramitesEnEstados(numExp, estadoTramite);
		if (lista != null && !lista.isEmpty()) {
			if (lista.size() == numExp.length) {
				return null;
			}
		}
		return new ResultBean(true, "no existes tramites con esos estados o el numero de tramites existentes no concuerda con el numero de expedientes");
	}

	@Override
	public Boolean comprobarEstadosTramitesAnulados(BigDecimal[] numerosExpedientes) {
		BigDecimal[] estadoTramite = new BigDecimal[1];
		estadoTramite[0] = new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum());

		List<TramiteTraficoVO> lista = tramiteTraficoDao.comprobarTramitesEnEstados(numerosExpedientes, estadoTramite);
		if (lista != null && !lista.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoSinEvolucion(BigDecimal numExpediente, String estado) {
		ResultBean result = null;
		try {
			TramiteTraficoVO tramiteTraficoVO = cambiarEstado(numExpediente, EstadoTramiteTrafico.convertir(estado));
			if (tramiteTraficoVO == null) {
				result = new ResultBean(true, "No existe tramite para cambiar el estado o ha sucedido un error a la hora de actualizar.");
			}
		} catch (Exception e) {
			result = new ResultBean(true, "Ha sucedido un error a la hora de cambiar el estado del tramite.");
			log.error("Ha sucedido un error a la hora de cambiar el estado del tramite, error: ", e, numExpediente.toString());
		}
		return result;
	}

	@Override
	@Transactional
	public TramiteTraficoVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente) {
		TramiteTraficoVO tramite = null;
		try {
			if (respuesta != null && numExpediente != null) {
				tramite = tramiteTraficoDao.actualizarRespuestaMateEitv(respuesta, numExpediente);
			}
		} catch (Exception e) {
			log.error("Error al actualizar la respuesta del tramite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	@Override
	@Transactional
	public RespuestaEstadisticasTransmisionBean getListadoTransmisionesDiarios() {
		RespuestaEstadisticasTransmisionBean resultado;
		Map<BigDecimal, EstadisticasTransmisionBean> mapaEstadisticas = null;
		try {
			Fecha fecha = utilesFecha.getPrimerLaborableAnterior();
			List<Object[]> listadoEstadisticasTransmision6Meses = getListadoEstadisticasPorMeses(fecha, 6);
			List<Object[]> listadoEstadisticasTransmisionParaMedia = getListadoEstadisticasPorDias(fecha, -7);
			List<Object[]> listadoEstadisticasTransmisionDiarias = getListadoEstadisticasDiaria(utilesFecha.convertirFechaEnDate(fecha));

			if ((listadoEstadisticasTransmision6Meses != null && !listadoEstadisticasTransmision6Meses.isEmpty()) && (listadoEstadisticasTransmisionParaMedia != null
					&& !listadoEstadisticasTransmisionParaMedia.isEmpty())) {
				mapaEstadisticas = rellenarMapaConEstadisticasDiarias(getMapaConEstadisticasMensuales(listadoEstadisticasTransmision6Meses, listadoEstadisticasTransmisionParaMedia, fecha),
						listadoEstadisticasTransmisionDiarias);
				resultado = mandarCorreoEstadisticas(mapaEstadisticas, utilesFecha.convertirFechaEnDate(fecha));
			} else {
				resultado = new RespuestaEstadisticasTransmisionBean(true, "No hay datos para poder mostrar las estadisticas");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las estadisticas de transmisiones diarias, error: ", e);
			resultado = new RespuestaEstadisticasTransmisionBean(true, e.getMessage());
			resultado.setException(e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar las estadisticas de transmisiones diarias, error: ", e);
			resultado = new RespuestaEstadisticasTransmisionBean(true, e.getMessage());
			resultado.setException(new Exception(e));
		}

		return resultado;
	}

	private Map<String, EstadisticasTransmisionBean> getMapaConEstadisticasMensuales(List<Object[]> listadoEstadisticasTransmision6Meses, List<Object[]> listadoEstadisticasTransmisionUltimaSemana,
			Fecha fecha) throws OegamExcepcion {
		Map<String, EstadisticasTransmisionBean> aux = new HashMap<String, EstadisticasTransmisionBean>();

		for (Object[] objects : listadoEstadisticasTransmisionUltimaSemana) {
			String numColegiado = (String) objects[0];
			BigDecimal numeroTramitesUltimaSemana = new BigDecimal((Integer) objects[1]);
			int numDias = utilesFecha.getNumDiasLaborableEntreFechas(utilesFecha.restarDias(utilesFecha.convertirFechaEnDate(fecha), 7, new Integer(0), new Integer(0), new Integer(0)), utilesFecha
					.convertirFechaEnDate(fecha));
			EstadisticasTransmisionBean estadisticas = new EstadisticasTransmisionBean();
			estadisticas.setNumColegiado(numColegiado);
			estadisticas.setMediaUltimaSemana(numeroTramitesUltimaSemana.divide(new BigDecimal(numDias), 2, BigDecimal.ROUND_HALF_UP));
			estadisticas.setMedia6Meses(BigDecimal.ZERO);
			estadisticas.setMediaDiaria(BigDecimal.ZERO);
			aux.put(numColegiado, estadisticas);
		}

		for (Object[] objects : listadoEstadisticasTransmision6Meses) {
			String numColegiado = (String) objects[0];
			BigDecimal numeroTramites6Meses = new BigDecimal((Integer) objects[1]);
			EstadisticasTransmisionBean estadisticas = aux.get(numColegiado);
			if (estadisticas == null) {
				estadisticas = new EstadisticasTransmisionBean();
				estadisticas.setNumColegiado(numColegiado);
				estadisticas.setMediaUltimaSemana(BigDecimal.ZERO);
				int numDias = utilesFecha.getNumDiasLaborableEntreFechas(utilesFecha.restarMeses(utilesFecha.convertirFechaEnDate(fecha), 6, new Integer(0), new Integer(0), new Integer(0)),
						utilesFecha.convertirFechaEnDate(fecha));
				estadisticas.setMedia6Meses(numeroTramites6Meses.divide(new BigDecimal(numDias), 2, BigDecimal.ROUND_HALF_UP));
				estadisticas.setMediaDiaria(BigDecimal.ZERO);
			} else {
				int numDias = utilesFecha.getNumDiasLaborableEntreFechas(utilesFecha.restarMeses(utilesFecha.convertirFechaEnDate(fecha), 6, new Integer(0), new Integer(0), new Integer(0)),
						utilesFecha.convertirFechaEnDate(fecha));
				estadisticas.setMedia6Meses(numeroTramites6Meses.divide(new BigDecimal(numDias), 2, BigDecimal.ROUND_HALF_UP));
				estadisticas.setNumColegiado(numColegiado);
			}
			aux.put(numColegiado, estadisticas);
		}

		return aux;
	}

	private Map<BigDecimal, EstadisticasTransmisionBean> rellenarMapaConEstadisticasDiarias(Map<String, EstadisticasTransmisionBean> mapaEstadisticasAux,
			List<Object[]> listadoEstadisticasTransmisionDiarias) {
		Map<BigDecimal, EstadisticasTransmisionBean> mapaEstadisticas = new TreeMap<BigDecimal, EstadisticasTransmisionBean>(Collections.reverseOrder());
		if (listadoEstadisticasTransmisionDiarias != null && !listadoEstadisticasTransmisionDiarias.isEmpty()) {
			for (Object[] objects : listadoEstadisticasTransmisionDiarias) {
				String numColegiado = (String) objects[0];
				BigDecimal numeroTramitesDiarios = new BigDecimal((Integer) objects[1]);
				EstadisticasTransmisionBean estadisticas = mapaEstadisticasAux.get(numColegiado);
				if (estadisticas == null) {
					estadisticas = new EstadisticasTransmisionBean();
					estadisticas.setNumColegiado(numColegiado);
					estadisticas.setMediaDiaria(numeroTramitesDiarios);
					estadisticas.setMedia6Meses(BigDecimal.ZERO);
				} else {
					estadisticas.setMediaDiaria(numeroTramitesDiarios);
				}
				if (estadisticas.getMedia6Meses() == null) {
					estadisticas.setMedia6Meses(BigDecimal.ZERO);
				}
				if (estadisticas.getMediaUltimaSemana() == null) {
					estadisticas.setMediaUltimaSemana(BigDecimal.ZERO);
				}
				if (estadisticas.getMediaDiaria() == null) {
					estadisticas.setMediaDiaria(BigDecimal.ZERO);
				}
				BigDecimal mediaPonderada = estadisticas.getMedia6Meses().add(estadisticas.getMediaUltimaSemana().add(estadisticas.getMediaDiaria()));
				mapaEstadisticas.put(mediaPonderada.divide(new BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP), estadisticas);
			}
		} else {
			for (EstadisticasTransmisionBean estadisticas : mapaEstadisticasAux.values()) {
				estadisticas.setMediaDiaria(BigDecimal.ZERO);
				BigDecimal mediaPonderada = estadisticas.getMedia6Meses().add(estadisticas.getMediaUltimaSemana().add(estadisticas.getMediaDiaria()));
				mapaEstadisticas.put(mediaPonderada.divide(new BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP), estadisticas);
			}
		}

		return mapaEstadisticas;
	}

	private RespuestaEstadisticasTransmisionBean mandarCorreoEstadisticas(Map<BigDecimal, EstadisticasTransmisionBean> mapaEstadisticas, Date fecha) throws OegamExcepcion {
		RespuestaEstadisticasTransmisionBean result;
		String cuerpo = generarEmailEstadisticasTransmision(mapaEstadisticas, fecha);
		String direccion = "";
		String subject = "";
		String dirOcultas = "";

		if (cuerpo != null) {
			String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.envio.estadisticas.transmisiones");
			if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
				direccion = gestorPropiedades.valorPropertie(ServicioTramiteTrafico.direccionEstadisticasTransmisiones);
				dirOcultas = gestorPropiedades.valorPropertie(ServicioTramiteTrafico.dirOcultaEstadisticasTransmisiones);
				subject = gestorPropiedades.valorPropertie(ServicioTramiteTrafico.subjectEstadisticasTranmisiones);
			} else {
				direccion = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
				subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - Transmision Diarios";
				dirOcultas = "";
			}

			try {
				ResultBean resultEnvio;
				StringBuffer sb2 = new StringBuffer(
						"<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM) <br>")
								.append(cuerpo).append("<br><br></span>");
				resultEnvio = servicioCorreo.enviarCorreo(sb2.toString(), null, null, subject, direccion, null, dirOcultas, null);
				if (resultEnvio == null || resultEnvio.getError()) {
					throw new OegamExcepcion("Error en la notificacion de error servicio");
				}
				result = new RespuestaEstadisticasTransmisionBean(false, null);
			} catch (OegamExcepcion e) {
				log.error("Ha sucedido un error a la hora de mandar el correo de estadisticas de transmisiones, error: ", e);
				result = new RespuestaEstadisticasTransmisionBean(true, e.getMensajeError1());
				result.setException(new Exception(e));
			} catch (IOException e) {
				log.error("Ha sucedido un error a la hora de mandar el correo de estadisticas de transmisiones, error: ", e);
				result = new RespuestaEstadisticasTransmisionBean(true, e.getMessage());
				result.setException(new Exception(e));
			}
		} else {
			result = new RespuestaEstadisticasTransmisionBean(true, "No se ha generado cuerpo del mensaje");
		}
		return result;
	}

	private List<Object[]> getListadoEstadisticasDiaria(Date fecha) throws OegamExcepcion {
		List<Object[]> lista = null;
		try {
			Fecha fecha2 = utilesFecha.getPrimerLaborableAnterior();
			fecha2.setHora("23");
			fecha2.setMinutos("59");
			fecha2.setSegundos("59");
			lista = tramiteTraficoDao.getListaTramitesPorFechas(fecha, utilesFecha.convertirFecha(fecha2));
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites diarios, error: ", e);
		}
		return lista;
	}

	private List<Object[]> getListadoEstadisticasPorMeses(Fecha fecha, int numMeses) {
		List<Object[]> lista = null;
		try {
			Date fechaAntesAyer = utilesFecha.convertirFechaEnDate(utilesFecha.getPrimerLaborableAnterior(fecha));
			utilesFecha.setHorasMinutosSegundosEnDate(fechaAntesAyer, "23:59:59");
			Date fechaSeisMeses = utilesFecha.restarMeses(utilesFecha.convertirFechaEnDate(utilesFecha.getPrimerLaborableAnterior(fecha)), numMeses, new Integer(0), new Integer(0), new Integer(0));
			lista = tramiteTraficoDao.getListaTramitesPorFechas(fechaSeisMeses, fechaAntesAyer);
		} catch (ParseException e) {
			log.error("Ha sucedido un error en la conversion de fechas ultimos " + numMeses + " meses, error: ", e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error en la conversion de fechas ultimos " + numMeses + " meses, error: ", e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return lista;
	}

	private List<Object[]> getListadoEstadisticasPorDias(Fecha fecha, int numDias) {
		List<Object[]> lista = null;
		try {
			Date fechaAntesAyer = utilesFecha.convertirFechaEnDate(utilesFecha.getPrimerLaborableAnterior(fecha));
			utilesFecha.setHorasMinutosSegundosEnDate(fechaAntesAyer, "23:59:59");
			Date fechaInicial = utilesFecha.restarDias(utilesFecha.convertirFechaEnDate(utilesFecha.getPrimerLaborableAnterior(fecha)), numDias, new Integer(0), new Integer(0), new Integer(0));
			lista = tramiteTraficoDao.getListaTramitesPorFechas(fechaInicial, fechaAntesAyer);
		} catch (ParseException e) {
			log.error("Ha sucedido un error en la conversion de fechas ultimos " + numDias + " meses, error: ", e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error en la conversion de fechas ultimos " + numDias + " meses, error: ", e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return lista;
	}

	private String generarEmailEstadisticasTransmision(Map<BigDecimal, EstadisticasTransmisionBean> mapaEstadisticas, Date fecha) throws OegamExcepcion {
		StringBuilder sb = new StringBuilder();
		BigDecimal mediaAnterior = null;
		BigDecimal mediaNueva = null;
		BigDecimal incremento = null;
		if (mapaEstadisticas != null && !mapaEstadisticas.isEmpty()) {
			sb.append("<br><span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
			sb.append(
					"<br>Se solicita desde la Oficina Electrónica de Gesti&oacute;n Administrativa (OEGAM), el env&iacute;o del siguiente correo con la media de transmisiones para cada gestor de los &uacute;ltimos seis meses, adem&aacute;s del total realizadas en el &uacute;ltimo d&iacute;a laborable:<br>");
			sb.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\">");
			sb.append("<tr> <th>N&uacute;m. Gestor</th> <th>Media diaria de los &uacute;ltimos seis meses</th> ");
			sb.append("<th>Media diaria de la &uacute;ltima semana</th> <th>");

			sb.append(utilesFecha.formatoFecha(fecha)).append(" </th>");
			sb.append("<th>Incremento</th></tr>");

			for (EstadisticasTransmisionBean colegiado : mapaEstadisticas.values()) {
				sb.append("<tr>");
				sb.append("<td>").append(colegiado.getNumColegiado()).append("</td>");
				sb.append("<td>").append(colegiado.getMedia6Meses()).append("</td>");
				mediaAnterior = colegiado.getMedia6Meses() == null ? BigDecimal.ZERO : colegiado.getMedia6Meses();

				if (colegiado.getMediaUltimaSemana() != null) {
					sb.append("<td>").append(colegiado.getMediaUltimaSemana()).append("</td>");
					mediaNueva = colegiado.getMediaUltimaSemana();
				} else {
					sb.append("<td>").append(0).append("</td>");
					mediaNueva = BigDecimal.ZERO;
				}
				if (colegiado.getMediaDiaria() != null) {
					sb.append("<td>").append(colegiado.getMediaDiaria()).append("</td>");
				} else {
					sb.append("<td>").append(0).append("</td>");
				}

				incremento = porcentajeIncremento(mediaNueva, mediaAnterior);

				sb.append("<td>").append(incremento).append("% </td>");

				sb.append("</tr>");
			}

			sb.append("</table>");
			sb.append("</span>");

			// Mantis 21977. Jorge Fernandez. Custodia de datos del Correo de Estadisticas con totales de CTIT
			Fecha fechaFin = new Fecha(fecha);
			String nombreFichero = ConstantesEstadisticas.FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();

			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.TRANS, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, sb.toString()
					.getBytes());
		} else {
			return null;
		}
		return sb.toString();
	}

	private BigDecimal porcentajeIncremento(BigDecimal numeroNuevo, BigDecimal numeroOriginal) {
		BigDecimal incremento = numeroNuevo.subtract(numeroOriginal);
		if (!numeroOriginal.equals(BigDecimal.ZERO)) {
			return incremento.divide(numeroOriginal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
		} else {
			return incremento.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
		}
	}

	@Override
	@Transactional
	public BigDecimal crearTramite(String numColegiado, String tipoTramite, BigDecimal idContrato, BigDecimal idUsuario, Date fechaAlta) throws Exception {
		TramiteTraficoVO tramiteTrafico = new TramiteTraficoVO();
		tramiteTrafico.setFechaUltModif(fechaAlta);
		tramiteTrafico.setFechaAlta(fechaAlta);
		tramiteTrafico.setTipoTramite(tipoTramite);
		ContratoVO contrato = new ContratoVO();
		contrato.setIdContrato(idContrato.longValue());
		tramiteTrafico.setContrato(contrato);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramiteTrafico.setUsuario(usuario);
		tramiteTrafico.setNumColegiado(numColegiado);
		if (tramiteTrafico.getEstado() == null || (tramiteTrafico.getEstado() != null && !EstadoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getEstado()))) {
			tramiteTrafico.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		}
		BigDecimal numExpediente = tramiteTraficoDao.generarNumExpediente(numColegiado);
		tramiteTrafico.setNumExpediente(numExpediente);
		tramiteTraficoDao.guardar(tramiteTrafico);
		return numExpediente;
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return tramiteTraficoDao.generarNumExpediente(numColegiado);
	}

	@Override
	public BigDecimal generarNumExpedienteFacturacionTasa(String numColegiado) throws Exception {
		return tramiteTraficoDao.generarNumExpedienteFacturacionTasa(numColegiado);
	}

	@Override
	@Transactional
	public void actualizarTramite(TramiteTraficoVO tramite) {
		tramiteTraficoDao.actualizar(tramite);
	}

	@Override
	@Transactional
	public void borrar(BigDecimal numExpediente) {
		TramiteTraficoVO tramiteTrafico = getTramite(numExpediente, false);
		tramiteTraficoDao.borrar(tramiteTrafico);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafFacturacionDto getTramiteFacturacion(BigDecimal numExpediente) {
		try {
			if (numExpediente != null) {
				TramiteTrafFacturacionVO tramiteTrafFacturacionVO = tramitetrafFactDao.getTramiteTraficoFactura(numExpediente, null);
				if (tramiteTrafFacturacionVO != null) {
					return conversor.transform(tramiteTrafFacturacionVO, TramiteTrafFacturacionDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de facturacion, error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getNifFacturacionPorNumExp(String numeroExpediente) {
		try {
			if (numeroExpediente != null) {
				TramiteTrafFacturacionVO trafFacturacionVO = tramitetrafFactDao.getNifFacturacionPorNumExp(numeroExpediente);
				if (trafFacturacionVO != null && trafFacturacionVO.getNif() != null) {
					return trafFacturacionVO.getNif();
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de facturacion, error: ", e, numeroExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean esImprimiblePdf(String numExpediente) {
		boolean imprimible = true;
		log.debug("Comprueba si una transmisión en estado validada telemáticamente ha pasado por los estados necesarios para sacar la mancha.");

		BigDecimal numExp = new BigDecimal(numExpediente);

		ResultBean result = comprobarEstadosTramites(new BigDecimal[] { numExp }, new EstadoTramiteTrafico[] { EstadoTramiteTrafico.Validado_Telematicamente });
		if (result == null) {
			// El tramite esta en estado validado telematicamente y debe comprobarse el numero de intentos y su respuesta
			int numeroFinalizacionConError = servicioEvolucionTramite.getNumeroFinalizacionesConError(numExp);
			if (numeroFinalizacionConError < 1) {
				// Aun no ha pasado por finalizado con Error. No se puede imprimir
				imprimible = false;
			} else {// Ya ha pasado por finalizado con Error

				String numVecesPermitidas = null;
				// Mantis 0025414
				String modoAdjudicacion = null;

				TramiteTrafTranVO tramite = tramiteTraficoTransDao.getTramiteTransmision(numExp, false);
				modoAdjudicacion = tramite.getModoAdjudicacion();

				if (modoAdjudicacion != null && !modoAdjudicacion.isEmpty()) {
					// Si modoAdjudicacion = Transmision (necesita + de 3 Finalizado con Error)
					if (ModoAdjudicacion.tipo1.toString().equals(modoAdjudicacion)) {
						numVecesPermitidas = gestorPropiedades.valorPropertie("numero.finalizaciones.error");
					} else { // ModoAdjudicacion distinto de Transmision ( necesita + 1 Finalizado con Error)
						numVecesPermitidas = gestorPropiedades.valorPropertie("numero.finalizaciones.no.transmision.error");
					}

					if (numVecesPermitidas != null && !numVecesPermitidas.isEmpty()) {

						if (Integer.valueOf(numVecesPermitidas) <= numeroFinalizacionConError) {
							// Numero de intentos menor del configurado por properties (5)
							// mantis 22517
							if ("SI".equals(gestorPropiedades.valorPropertie("comprobar.error.imprimir.telematicos"))) {
								String sErrores = gestorPropiedades.valorPropertie("lista.errores.telematicos.imprimibles");
								if (sErrores != null && !sErrores.isEmpty()) {
									String[] listaErrores = sErrores.split(";");
									int numExpErrores = tramiteTraficoDao.comprobarRespuestaTipoErrorValidoImprimir(numExp, listaErrores);
									if (numExpErrores == 0) {
										// Error tecnico y numero de intentos menor del configurado por properties (5)
										imprimible = false;
									}
								} else {
									log.error("No se comprueban los errores permitidos para imprimir porque la lista esta vacia.");
								}
							} else {// Solo se valora el numero de finalizados con error
								imprimible = true;
							}
						} else {
							imprimible = false;
						}
					}
				}

			}
		}

		return imprimible;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerNumColegiadoByNumExpediente(BigDecimal numExpediente) {
		return tramiteTraficoDao.obtenerNumColegiadoByNumExpediente(numExpediente);
	}

	@Override
	@Transactional
	public ResultBean liberarDocBaseNive(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramite = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.FALSE, Boolean.FALSE);
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite())) {
				if (tramite.getEstado() != null && EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramite.getEstado().toString())) {
					tramite.setPresentadoJpt(null);
					tramite.setFechaPresentacionJpt(null);
					tramite.setYbestado(new BigDecimal(DocumentoBaseEstadoTramite.NO_ENVIADO.getValorEnum()));
					tramite = (TramiteTraficoVO) tramiteTraficoDao.actualizar(tramite);
					if (tramite != null) {
						UsuarioDto usuarioDto = servicioUsuario.getUsuarioDto(idUsuario);
						result = servicioVehiculo.liberarNive(tramite.getVehiculo().getIdVehiculo(), tramite.getNumColegiado(), numExpediente, tramite.getTipoTramite(), usuarioDto);
					} else {
						result.setError(Boolean.TRUE);
						result.setMensaje("No se ha actualizado el trámite");
					}
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("El trámite debe ser esta en Finalizado Telemáticamente o Finalizado Telemáticamente Impreso");
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("El trámite debe ser de Matriculación");
			}
		} catch (Exception e) {
			log.error("Error al liberar el Nive", e, numExpediente.toString());
			result.setError(Boolean.TRUE);
			result.setMensaje(" El expediente " + numExpediente + " no tiene código nive ");
		}
		if (result != null && result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Object[] listarTramitesFinalizadosPorTiempo(String tipoTramite, List<BigDecimal> estadosOk, List<BigDecimal> estadosKo, List<String> tipoTransferencia, int segundos) {
		return tramiteTraficoDao.listarTramitesFinalizadosPorTiempo(tipoTramite, estadosOk, estadosKo, tipoTransferencia, segundos);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaExpedientesPorTasa(String codigoTasa) {
		List<TramiteTraficoVO> listaTramites = new ArrayList<TramiteTraficoVO>();
		List<TramiteTraficoVO> listaBBDD = null;
		try {
			if (codigoTasa != null && !codigoTasa.isEmpty()) {
				listaBBDD = tramiteTraficoDao.getListaExpedientesPorTasa(codigoTasa);
				if (listaBBDD != null && !listaBBDD.isEmpty()) {
					listaTramites.addAll(listaBBDD);
				}
				listaBBDD = tramiteTraficoTransDao.getListaExpedientesPorTasaCambServOInforme(codigoTasa);
				if (listaBBDD != null && !listaBBDD.isEmpty()) {
					listaTramites.addAll(listaBBDD);
				}
			} else {
				log.error("No se pueden obtener el listado de expedientes asociados a la tasa porque el código de tasa esta vacío.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los expedientes asociados a la tasa: " + codigoTasa + ", error: ", e);
		}
		return listaTramites;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoDto> getListaExpedientesPantallaPorTasa(String codigoTasa) {
		List<TramiteTraficoDto> listaTramiteTraficoBBDD = null;
		try {
			if (codigoTasa != null && !codigoTasa.isEmpty()) {
				List<TramiteTraficoVO> listaVOBBDD = getListaExpedientesPorTasa(codigoTasa);
				if (listaVOBBDD != null && !listaVOBBDD.isEmpty()) {
					return conversor.transform(listaVOBBDD, TramiteTraficoDto.class);
				}
			} else {
				log.error("No se pueden obtener el listado de expedientes asociados a la tasa porque el codigo de tasa esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los expedientes asociados a la tasa: " + codigoTasa + ", error: ", e);
		}
		return listaTramiteTraficoBBDD;
	}

	@Override
	public ResultBean desasignarTasaTramites(TramiteTraficoVO tramiteTraficoVO, String codigoTasa) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			// COMPROBACIONES DE NULOS
			tramiteTraficoDao.getTramite(tramiteTraficoVO.getNumExpediente(), Boolean.TRUE);
			if (tramiteTraficoVO.getTasa() != null && tramiteTraficoVO.getTasa().getCodigoTasa() != null && !tramiteTraficoVO.getTasa().getCodigoTasa().isEmpty() && tramiteTraficoVO.getTasa()
					.getCodigoTasa().equals(codigoTasa)) {
				tramiteTraficoVO.setTasa(null);
				tramiteTraficoDao.actualizar(tramiteTraficoVO);
			} else {
				if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoVO.getTipoTramite())) {
					TramiteTrafTranVO tramiteTrafTranVO = tramiteTraficoTransDao.getTramiteTransmision(tramiteTraficoVO.getNumExpediente(), Boolean.TRUE);
					if (tramiteTrafTranVO.getTasa1() != null && tramiteTrafTranVO.getTasa1().getCodigoTasa() != null && !tramiteTrafTranVO.getTasa1().getCodigoTasa().isEmpty() && tramiteTrafTranVO
							.getTasa1().getCodigoTasa().equals(codigoTasa)) {
						tramiteTrafTranVO.setTasa1(null);
						tramiteTraficoTransDao.actualizar(tramiteTrafTranVO);
					}
					if (tramiteTrafTranVO.getTasa2() != null && tramiteTrafTranVO.getTasa2().getCodigoTasa() != null && !tramiteTrafTranVO.getTasa2().getCodigoTasa().isEmpty() && tramiteTrafTranVO
							.getTasa2().getCodigoTasa().equals(codigoTasa)) {
						tramiteTrafTranVO.setTasa2(null);
						tramiteTraficoTransDao.actualizar(tramiteTrafTranVO);
					}
				} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTraficoVO.getTipoTramite())) {
					TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteTraficoVO.getNumExpediente(), Boolean.TRUE);
					if (tramiteTrafSolInfoVO.getTasa() != null && tramiteTrafSolInfoVO.getTasa().getCodigoTasa() != null && !tramiteTrafSolInfoVO.getTasa().getCodigoTasa().isEmpty()
							&& tramiteTrafSolInfoVO.getTasa().getCodigoTasa().equals(codigoTasa)) {
						tramiteTrafSolInfoVO.setTasa(null);
						tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la tasa: " + codigoTasa + ", error: ", e, tramiteTraficoVO.getNumExpediente().toString());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean desasignarDuplicarTramite(BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario, String desasignarTasaAlDuplicar) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
				TramiteTrafTranVO tramiteTrafTranVO = tramiteTraficoTransDao.getTramiteTransmision(numExpediente, Boolean.TRUE);
				if (tramiteTrafTranVO != null) {
					if ("NO".equalsIgnoreCase(desasignarTasaAlDuplicar)) {
						tramiteTrafTranVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoTransDao.actualizar(tramiteTrafTranVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
					} else {
						TasaVO tasaTramite = tramiteTrafTranVO.getTasa();
						tramiteTrafTranVO.setTasa(null);
						TasaVO tasaTramite1 = tramiteTrafTranVO.getTasa1();
						tramiteTrafTranVO.setTasa1(null);
						TasaVO tasaTramite2 = tramiteTrafTranVO.getTasa2();
						tramiteTrafTranVO.setTasa2(null);
						tramiteTrafTranVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoTransDao.actualizar(tramiteTrafTranVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
						if (tasaTramite != null && tasaTramite.getCodigoTasa() != null && !tasaTramite.getCodigoTasa().isEmpty()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaTramite.getCodigoTasa(), idUsuario);
						}
						if (tasaTramite1 != null && tasaTramite1.getCodigoTasa() != null && !tasaTramite1.getCodigoTasa().isEmpty() && !resultado.getError()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaTramite1.getCodigoTasa(), idUsuario);
						}
						if (tasaTramite2 != null && tasaTramite2.getCodigoTasa() != null && !tasaTramite2.getCodigoTasa().isEmpty() && !resultado.getError()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaTramite2.getCodigoTasa(), idUsuario);
						}
					}
				}
			} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				TramiteTrafMatrVO tramiteTrafMatrVO = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.TRUE, Boolean.TRUE);
				if (tramiteTrafMatrVO != null) {
					if ("NO".equalsIgnoreCase(desasignarTasaAlDuplicar)) {
						tramiteTrafMatrVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
					} else {
						TasaVO tasaTramite = tramiteTrafMatrVO.getTasa();
						tramiteTrafMatrVO.setTasa(null);
						tramiteTrafMatrVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
						if (tasaTramite != null && tasaTramite.getCodigoTasa() != null && !tasaTramite.getCodigoTasa().isEmpty()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaTramite.getCodigoTasa(), idUsuario);
						}
					}
				}
			} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
				TramiteTrafBajaVO tramiteTrafBajaVO = tramiteTraficoBajaDao.getTramiteBaja(numExpediente, Boolean.TRUE);
				if (tramiteTrafBajaVO != null) {
					if ("NO".equalsIgnoreCase(desasignarTasaAlDuplicar)) {
						tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
					} else {
						TasaVO tasaTramite = tramiteTrafBajaVO.getTasa();
						tramiteTrafBajaVO.setTasa(null);
						String tasaDuplicado = tramiteTrafBajaVO.getTasaDuplicado();
						tramiteTrafBajaVO.setTasaDuplicado(null);
						tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
						guardarEvolucionTramite(numExpediente, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Iniciado, idUsuario);
						if (tasaTramite != null && tasaTramite.getCodigoTasa() != null && !tasaTramite.getCodigoTasa().isEmpty()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaTramite.getCodigoTasa(), idUsuario);
						}
						if (tasaDuplicado != null && !tasaDuplicado.isEmpty()) {
							resultado = servicioTasa.desasignarTasaDuplicar(tasaDuplicado, idUsuario);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar el duplicado con número de expediente " + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el duplicado con número de expediente");
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoConGestionCreditos(String numExpediente, String nuevoEstado, BigDecimal idUsuario) {
		ResultBean resultEstado = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafDto tramiteDto = getTramiteDto(new BigDecimal(numExpediente), false);
			EstadoTramiteTrafico antiguoEstado = org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(tramiteDto.getEstado());
			EstadoTramiteTrafico estadoNuevo = org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(nuevoEstado);
			// resultEstado = cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), antiguoEstado, estadoNuevo, false, null, idUsuario);
			resultEstado = cambiarEstadoConPosibleEvolucion(true, tramiteDto.getNumExpediente(), antiguoEstado, estadoNuevo, false, null, idUsuario);
			// ¿notificar?

			if (!resultEstado.getError()) {
				TramiteTraficoVO tramiteTrafico = (TramiteTraficoVO) resultEstado.getAttachment(TRAMITE_TRAFICO);
				accionesCambiarEstadoSinValidacionNuevo(tramiteTrafico, antiguoEstado, estadoNuevo, idUsuario);
				resultEstado = gestionarCreditosCambioEstado(tramiteTrafico, antiguoEstado, estadoNuevo, idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar el cambiarEstadoConGestionCreditos " + numExpediente + ", error: ", e, numExpediente);
			resultEstado.setError(true);
			resultEstado.setMensaje("Ha sucedido un error a la hora de realizar el cambio de estado para: " + numExpediente);
		}

		if (resultEstado != null && resultEstado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultEstado;
	}

	private void accionesCambiarEstadoSinValidacionNuevo(TramiteTraficoVO tramiteTrafico, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario) {
		// Eliminar de la cola
		if (EstadoTramiteTrafico.Pendiente_Check_Ctit.equals(antiguoEstado) || EstadoTramiteTrafico.Pendiente_Envio.equals(antiguoEstado) || EstadoTramiteTrafico.Pendiente_Consulta_BTV.equals(
				antiguoEstado)) {
			servicioCola.eliminar(tramiteTrafico.getNumExpediente(), null);
		}

		// Borrar de la tabla de facturacion si se ha cambiado el estado del trámite
		// de Finalizado pdf --> Modificado a petición del colegiado.
		if (tramiteTrafico.getNumExpediente() != null && tramiteTrafico.getTasa() != null && EstadoTramiteTrafico.Finalizado_PDF.equals(antiguoEstado) && EstadoTramiteTrafico.Modificado_Peticion
				.equals(nuevoEstado)) {
			servicioFacturacion.eliminarFacturacionTramite(tramiteTrafico.getNumExpediente(), tramiteTrafico.getTasa().getCodigoTasa());
		}

		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(nuevoEstado
				.toString()) && (EstadoTramiteTrafico.No_Tramitable.getValorEnum().equals(antiguoEstado.toString()) || EstadoTramiteTrafico.Iniciado.getValorEnum().equals(antiguoEstado.toString())
						|| EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(antiguoEstado.toString()))) {
			if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
				servicioPermisosDgt.solicitarPermisoFinalizadoError(tramiteTrafico, idUsuario, null);
			}
		}
		if(EstadoTramiteTrafico.Autorizado.getValorEnum().equalsIgnoreCase(antiguoEstado.toString()) && (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(nuevoEstado
				.toString()) || !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(nuevoEstado
						.toString()))) {
			servicioTramiteTraficoMatriculacion.actualizarCampoAutorizacion(tramiteTrafico);
		}
	}

	private ResultBean gestionarCreditosCambioEstado(TramiteTraficoVO tramiteTrafico, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario) {
		final String FINALIZADO = "Finalizado";
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		String nuevaGestionCreditos = gestorPropiedades.valorPropertie("nueva.gestion.creditos");
		if (StringUtils.isNotBlank(nuevaGestionCreditos) && "SI".equals(nuevaGestionCreditos) && "2631".equals(tramiteTrafico.getNumColegiado())) {
			if ((antiguoEstado.getNombreEnum().contains(FINALIZADO) && !EstadoTramiteTrafico.Finalizado_Con_Error.equals(antiguoEstado)) && (!nuevoEstado.getNombreEnum().contains(FINALIZADO)
					|| EstadoTramiteTrafico.Finalizado_Con_Error.equals(nuevoEstado))) {
				String tipoTramiteAm = obtenerTipoTramiteAm(tramiteTrafico.getTipoTramite());
				String concepto = obtenerConceptoDevolver(tipoTramiteAm);
				ResultadoBean resultadoCreditos = servicioGestionCredito.crearSolicitudCreditos(tramiteTrafico.getNumExpediente(), tramiteTrafico.getContrato().getIdContrato(), tramiteTrafico
						.getTipoTramite(), tipoTramiteAm, concepto, AccionesGestionCreditos.DEVOLVER_MANUAL.getValorEnum(), idUsuario);
				if (resultadoCreditos != null && resultadoCreditos.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultadoCreditos.getMensaje());
				}
			} else if ((!antiguoEstado.getNombreEnum().contains(FINALIZADO) || EstadoTramiteTrafico.Finalizado_Con_Error.equals(antiguoEstado)) && (nuevoEstado.getNombreEnum().contains(FINALIZADO)
					&& !EstadoTramiteTrafico.Finalizado_Con_Error.equals(nuevoEstado))) {
				String tipoTramiteAm = obtenerTipoTramiteAm(tramiteTrafico.getTipoTramite());
				String concepto = obtenerConceptoDescontar(tipoTramiteAm);
				ResultadoBean resultadoCreditos = servicioGestionCredito.crearSolicitudCreditos(tramiteTrafico.getNumExpediente(), tramiteTrafico.getContrato().getIdContrato(), tramiteTrafico
						.getTipoTramite(), tipoTramiteAm, concepto, AccionesGestionCreditos.CARGA_MANUAL.getValorEnum(), idUsuario);
				if (resultadoCreditos != null && resultadoCreditos.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultadoCreditos.getMensaje());
				}
			}
		} else {
			// Devolver creditos : De finalizado --> Cualquier otro estado no finalizado o finalizado error
			if ((antiguoEstado.getNombreEnum().contains(FINALIZADO) && !EstadoTramiteTrafico.Finalizado_Con_Error.equals(antiguoEstado)) && (!nuevoEstado.getNombreEnum().contains(FINALIZADO)
					|| EstadoTramiteTrafico.Finalizado_Con_Error.equals(nuevoEstado))) {
				// Distinguimos entre tipo de tramite para incrementales o decrementales.
				/* DECREMENTALES */
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
						resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Matriculacion.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
								.intValue(), idUsuario, ConceptoCreditoFacturado.DTMT, tramiteTrafico.getNumExpediente().toString());
					} else {
						resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Matriculacion_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
								.intValue(), idUsuario, ConceptoCreditoFacturado.DTMA, tramiteTrafico.getNumExpediente().toString());
					}
				} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Transmision.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE.intValue(),
							idUsuario, ConceptoCreditoFacturado.DITT, tramiteTrafico.getNumExpediente().toString());
				} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					if (!EstadoTramiteTrafico.Finalizado_Incidencia.getValorEnum().equals(antiguoEstado.getValorEnum())) {
						if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
							resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Baja.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
									.intValue(), idUsuario, ConceptoCreditoFacturado.DTBT, tramiteTrafico.getNumExpediente().toString());
						} else {
							resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.BAJA_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
									.intValue(), idUsuario, ConceptoCreditoFacturado.DTBA, tramiteTrafico.getNumExpediente().toString());
						}
					} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(nuevoEstado.getValorEnum()) && !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
							.getValorEnum().equals(nuevoEstado.getValorEnum()) && !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(nuevoEstado.getValorEnum())) {
						if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
							resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Baja.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
									.intValue(), idUsuario, ConceptoCreditoFacturado.DTBT, tramiteTrafico.getNumExpediente().toString());
						} else {
							resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.BAJA_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE
									.intValue(), idUsuario, ConceptoCreditoFacturado.DTBA, tramiteTrafico.getNumExpediente().toString());
						}
					}

				} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
						resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()),
								BigDecimal.ONE.intValue(), idUsuario, ConceptoCreditoFacturado.DITT, tramiteTrafico.getNumExpediente().toString());
					} else {
						resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.CTIT_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE.intValue(),
								idUsuario, ConceptoCreditoFacturado.DTTA, tramiteTrafico.getNumExpediente().toString());
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE.intValue(),
							idUsuario, ConceptoCreditoFacturado.DEBD, tramiteTrafico.getNumExpediente().toString());
				} /* INCREMENTALES */
				else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Solicitud.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE.intValue(),
							idUsuario, ConceptoCreditoFacturado.DAVP, tramiteTrafico.getNumExpediente().toString());
				}
			}
			// DESCONTAR creditos (cobramos): De Finalizado con Error o otro no finalizado --> Finalizado{
			else if ((!antiguoEstado.getNombreEnum().contains(FINALIZADO) || EstadoTramiteTrafico.Finalizado_Con_Error.equals(antiguoEstado)) && (!EstadoTramiteTrafico.Finalizado_Con_Error.equals(
					nuevoEstado) && nuevoEstado.getNombreEnum().contains(FINALIZADO))) {
				resultado = servicioCredito.validarCreditos(tramiteTrafico.getTipoTramite(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE);
				if (!resultado.getError()) {
					/* DECREMENTALES */
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Matriculacion.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
									idUsuario, ConceptoCreditoFacturado.TMT, tramiteTrafico.getNumExpediente().toString());
						} else {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Matriculacion_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()),
									BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.TMAM, tramiteTrafico.getNumExpediente().toString());
						}
					} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Transmision.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
								idUsuario, ConceptoCreditoFacturado.TTT, tramiteTrafico.getNumExpediente().toString());
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
									idUsuario, ConceptoCreditoFacturado.TBT, tramiteTrafico.getNumExpediente().toString());
						} else {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.BAJA_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
									idUsuario, ConceptoCreditoFacturado.TBAM, tramiteTrafico.getNumExpediente().toString());
						}
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()),
									BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.ITT, tramiteTrafico.getNumExpediente().toString());
						} else {
							resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.CTIT_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
									idUsuario, ConceptoCreditoFacturado.TTAM, tramiteTrafico.getNumExpediente().toString());
						}
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
								idUsuario, ConceptoCreditoFacturado.EBD, tramiteTrafico.getNumExpediente().toString());
					} /* INCREMENTALES */
					else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Solicitud.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
								idUsuario, ConceptoCreditoFacturado.AVPO, tramiteTrafico.getNumExpediente().toString());
					}
				}
			} else if (EstadoTramiteTrafico.Finalizado_Incidencia.getValorEnum().equals(nuevoEstado.getValorEnum())) {
				if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
					if (!"2631".equals(tramiteTrafico.getNumColegiado())) {
						resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE, idUsuario,
								ConceptoCreditoFacturado.TBT, tramiteTrafico.getNumExpediente().toString());
					} else {
						resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.BAJA_AM.getValorEnum(), new BigDecimal(tramiteTrafico.getContrato().getIdContrato()), BigDecimal.ONE,
								idUsuario, ConceptoCreditoFacturado.TBAM, tramiteTrafico.getNumExpediente().toString());
					}
				}
			}
		}
		return resultado;
	}

	private String obtenerTipoTramiteAm(String tipoTramite) {
		String concepto = null;
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			concepto = TipoTramiteTrafico.Matriculacion_AM.getValorEnum();
		} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			concepto = TipoTramiteTrafico.CTIT_AM.getValorEnum();
		} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			concepto = TipoTramiteTrafico.BAJA_AM.getValorEnum();
		} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
			concepto = TipoTramiteTrafico.DUPLICADO_AM.getValorEnum();
		}
		return concepto;
	}

	private String obtenerConceptoDevolver(String tipoTramiteAm) {
		String concepto = null;
		if (TipoTramiteTrafico.Matriculacion_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.DTMA.getValorEnum();
		} else if (TipoTramiteTrafico.CTIT_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.DTTA.getValorEnum();
		} else if (TipoTramiteTrafico.BAJA_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.DTBA.getValorEnum();
		} else if (TipoTramiteTrafico.DUPLICADO_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.DTDA.getValorEnum();
		}
		return concepto;
	}

	private String obtenerConceptoDescontar(String tipoTramiteAm) {
		String concepto = null;
		if (TipoTramiteTrafico.Matriculacion_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.TMAM.getValorEnum();
		} else if (TipoTramiteTrafico.CTIT_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.TTAM.getValorEnum();
		} else if (TipoTramiteTrafico.BAJA_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.TBAM.getValorEnum();
		} else if (TipoTramiteTrafico.DUPLICADO_AM.getValorEnum().equals(tipoTramiteAm)) {
			concepto = ConceptoCreditoFacturado.TBAM.getValorEnum();
		}
		return concepto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesPermisosPorDocId(Long idDocPermisos) {
		try {
			return tramiteTraficoDao.getListaTramitesPermisosPorDocId(idDocPermisos);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de los permisos del docId: " + idDocPermisos + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesPermisosPorDocIdErroneos(Long docId) {
		try {
			return tramiteTraficoDao.getListaTramitesPermisosPorDocIdErroneos(docId);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de los permisos erroneos del docId: " + docId + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocId(Long idDocFichasTecnicas) {
		try {
			return tramiteTraficoDao.getListaTramitesFichasTecnicasPorDocId(idDocFichasTecnicas);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de las fichas técnicas del docId: " + idDocFichasTecnicas + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocIdErroneos(Long docId) {
		try {
			return tramiteTraficoDao.getListaTramitesFichasTecnicasPorDocIdErroneas(docId);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de las fichas técnicas erroneas del docId: " + docId + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesPermisosPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					tramiteTraficoVO.setEstadoImpPerm(estadoNuevo.getValorEnum());
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los trámites para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesPermisosPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					String estadoAnt = tramiteTraficoVO.getEstadoImpPerm();
					tramiteTraficoVO.setEstadoImpPerm(estadoNuevo.getValorEnum());
					tramiteTraficoVO.setDocPermiso(null);
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.ANULAR,
							new Date(), estadoAnt, estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesFichasTecnicasPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					String estadoAnt = tramiteTraficoVO.getEstadoImpFicha();
					tramiteTraficoVO.setEstadoImpFicha(estadoNuevo.getValorEnum());
					tramiteTraficoVO.setDocFichaTecnica(null);
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.ANULAR, new Date(),
							estadoAnt, estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesFichasTecnicasPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					tramiteTraficoVO.setEstadoImpFicha(estadoNuevo.getValorEnum());
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los trámites para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountNumTramitesPermisos(Long idDocPerm) {
		try {
			return tramiteTraficoDao.getCountNumTramitesPermisos(idDocPerm);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el numero de tramites de permisos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountNumTramitesFichas(Long idDocFichas) {
		try {
			return tramiteTraficoDao.getCountNumTramitesFichas(idDocFichas);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el numero de tramites de Fichas, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesPermisosPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					String estadoAnt = tramiteTraficoVO.getEstadoImpPerm();
					tramiteTraficoVO.setEstadoImpPerm(estadoNuevo.getValorEnum());
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.IMPRESION,
							new Date(), estadoAnt, estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los trámites para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoDao.getListaTramitesFichasTecnicasPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					String estadoAnt = tramiteTraficoVO.getEstadoImpFicha();
					tramiteTraficoVO.setEstadoImpFicha(estadoNuevo.getValorEnum());
					tramiteTraficoDao.actualizar(tramiteTraficoVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.IMPRESION, new Date(),
							estadoAnt, estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los trámites para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los estados de los trámites.");
		}
		return resultado;
	}

	/*
	 * @Override
	 * @Transactional public void actualizarTramiteTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) { try { if(tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() != null){ TramiteTraficoVO tramiteTrafVO =
	 * tramiteTraficoDao.getTramite(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), Boolean.TRUE); if(tramiteTrafVO != null){ tramiteTrafVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum())); tramiteTraficoDao.guardarOActualizar(tramiteTrafVO); } }
	 * else { log.error("No se puede actualizar el tramite porque es nulo o no tiene numero de expediente."); } } catch (Exception e) { log.error("Ha sucedido un error a la hora de actualizar el tramite de trasnmision, error: ",e); } }
	 */

	@Override
	@Transactional(readOnly = true)
	public List<Long> getListaIdsContratosFinalizadosTelematicamente(Date fechaPresentacion) {
		try {
			String[] tiposTramites = new String[] { TipoTramiteTrafico.Matriculacion.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum() };
			List<Long> listaIdContratos = tramiteTraficoDao.getListaIdsContratosFinalizadosTelematicamentePorFecha(fechaPresentacion, tiposTramites, Boolean.FALSE);
			if (listaIdContratos != null && !listaIdContratos.isEmpty()) {
				return listaIdContratos;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de contratos con matriculaciones, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoPermisoDistintivoItvBean tieneTramitesFinalizadosCtit(ContratoDto contratoDto, Date fechaPresentacion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Integer numTramites = tramiteTraficoTransDao.getTramitesFinalizadosTelematicamentePorContrato(contratoDto.getIdContrato().longValue(), fechaPresentacion);
			if (numTramites > 0) {
				resultado.setTieneTramitesST(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si el contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia()
					+ " tiene tramites de CTIT para IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si el contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia()
					+ " tiene tramites de CTIT supertelematicos para IMPR.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafTranVO> listaTramitesBBDD = tramiteTraficoTransDao.getListaTramitesFinalizadosTelematicamentePorContrato(idContrato, fecha);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				resultado.setListaTramitesCtit(listaTramitesBBDD);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El contrato: " + idContrato + " no tiene trámites telemáticos de transmisión para imprimir sus permisos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites para imprimir sus permisos para el contrato: " + idContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el listado de trámites para imprimir sus permisos para el contrato: " + idContrato);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String cifEmpresa, BigDecimal idContrato, String tipoInterviniente, Date fechaPresentacion, String tipoTramite) {
		try {
			return tramiteTraficoDao.getListaTramitesPorNifTipoIntervienteYFecha(cifEmpresa, idContrato.longValue(), tipoInterviniente, tipoTramite, fechaPresentacion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de trámites, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getlistaTramitesCtitFinalizadosPorContratoYFecha(Long idContrato, Date fechaPresentacion) {
		try {
			List<TramiteTraficoVO> listaTramites = tramiteTraficoTransDao.getListaTramitesImprimirImprPorContratoYFecha(idContrato, fechaPresentacion);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				return listaTramites;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de trámites, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultBean guardarOActualizarCS(TramiteTrafDto tramiteTraficoDto) {
		ResultBean resultado = new ResultBean(false);
		TramiteTraficoVO traficoVO = conversor.transform(tramiteTraficoDto, TramiteTraficoVO.class);
		if (null == tramiteTraficoDao.actualizar(traficoVO)) {
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al guardar el estado.");
		}
		return resultado;
	}

	@Transactional
	@Override
	public TramiteTraficoVO primeraMatricula(Date fechaActual) throws Exception {
		return tramiteTraficoDao.primeraMatricula(new Date());
	}

	@Transactional
	@Override
	public TramiteTraficoVO ultimaMatricula(Date fechaActual) throws Exception {
		return tramiteTraficoDao.ultimaMatricula(fechaActual);
	}

	@Override
	public ResultBean cambiarEstadoRevisado(BigDecimal numExpediente, Long idUsuario) {
		ResultBean result = new ResultBean(false);
		ResultadoBean resultado = servicioComunTramiteTrafico.cambiarEstadoRevisado(numExpediente, idUsuario);
		if (resultado != null && resultado.getError()) {
			result.setError(Boolean.TRUE);
			result.setMensaje(resultado.getMensaje());
		}
		return result;
	}

	@Override
	public ResultBean asignarTasaLibre(BigDecimal numExpediente, Long idUsuario) {
		ResultBean result = new ResultBean(false);
		ResultadoBean resultado = servicioComunTramiteTrafico.asignarTasaLibre(numExpediente, idUsuario);
		if (resultado != null && resultado.getError()) {
			result.setError(Boolean.TRUE);
			result.setMensaje(resultado.getMensaje());
		}
		return result;
	}

	@Override
	@Transactional
	public MarcaDgtVO recuperarMarcaConFabricantes(String codigoMarca) {
		return fabricanteDao.recuperarMarcaConFabricantes(codigoMarca);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getExcelResumenNRE06PorFecha(Date fechaInicio, Date fechaFin) throws Exception {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		List<Object[]> listado = tramiteTraficoDao.getListaResumenEstadisticaNRE06(fechaInicio, fechaFin);
		if (listado != null && listado.size() > 0) {
			salida.addAttachment("excel", crearExcel(listado));
		} else {
			salida.setError(Boolean.TRUE);
			salida.setMensaje("No existe listado para el rango de fecha marcado para el resumen NRE06");
		}
		return salida;
	}

	private XSSFWorkbook crearExcel(List<Object[]> listado) {
		XSSFWorkbook myWorkBook = null;
		if (listado != null && listado.size() > 0) {
			try {
				boolean cabecera = true;
				int fila = 0;
				int col = 0;
				myWorkBook = new XSSFWorkbook();
				XSSFSheet sheet = myWorkBook.createSheet("Resumen NRE06");
				XSSFCellStyle estiloNegrita = myWorkBook.createCellStyle();
				XSSFFont negrita = myWorkBook.createFont();
				negrita.setBold(true);
				negrita.setFontHeight(9);
				estiloNegrita.setFont(negrita);
				sheet.autoSizeColumn(col);
				XSSFRow row = sheet.createRow(fila++);
				for (Object[] element : listado) {
					if (cabecera) {
						cabecera = false;
						row.createCell(col).setCellValue("NÚMERO COLEGIADO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("TOTAL");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("FECHA REGISTRO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
					}
					col = 0;
					row = sheet.createRow(fila++);
					row.createCell(col++).setCellValue((String) element[1]);
					row.createCell(col++).setCellValue((int) element[0]);
					row.createCell(col++).setCellValue(utilesFecha.formatoFecha((Timestamp) element[2]));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return myWorkBook;
	}

	public ResultBean actualizarFechaPresentacion(BigDecimal numExpediente, Date fechaPresentacion) {
		ResultBean resultado = new ResultBean(false);

		TramiteTraficoVO tramite = tramiteTraficoDao.buscarPorExpediente(numExpediente);
		// Modificar fecha de presentación en los siguientes casos
		tramite.setFechaPresentacion(fechaPresentacion);
		tramite = (TramiteTraficoVO) tramiteTraficoDao.guardarOActualizar(tramite);

		if (tramite == null) {
			resultado.setError(true);
			resultado.setMensaje("Error al actualizar la fecha de presentación");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public int numeroElementosListadosEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, String agrupacion) {
		if (agrupacion.equals(Agrupacion.Tipo_Transferencia.getValorEnum())) {
			return tramiteTraficoTransDao.numeroElementos(filter, fetchModeJoinList, entitiesJoin);
		} else {
			return tramiteTraficoDao.numeroElementos(filter, fetchModeJoinList, entitiesJoin);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int numeroElementosListadosEstadisticasConListaProyeccion(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			String agrupacion) {

		if (agrupacion.equals(Agrupacion.Tipo_Transferencia.getValorEnum())) {
			List<TramiteTrafTranVO> lista = tramiteTraficoTransDao.listadoPantallasEstadisticas(filter, fetchModeJoinList, entitiesJoin, listaProyecciones);
			if (lista != null && !lista.isEmpty()) {
				return lista.size();
			}
		} else {
			List<TramiteTraficoVO> lista = tramiteTraficoDao.listadoPantallasEstadisticas(filter, fetchModeJoinList, entitiesJoin, listaProyecciones);
			if (lista != null && !lista.isEmpty()) {
				return lista.size();
			}
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean generarExcelListadoVehiculosEstadisticas(Object filter) {
		File archivo = null;
		ResultBean resultado = new ResultBean();

		String nombreFichero = "ESTADISTICAS__ICOGAM__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.ESTADISTICAS);
		fichero.setSubTipo(ConstantesGestorFicheros.EXCELS);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
		fichero.setNombreDocumento(nombreFichero);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		try {
			archivo = gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero excel con las estadísticas de vehículos: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al guardar el fichero excel con las estadísticas de vehículos.");
		}

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;

		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("EstadisticasTramites", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 8; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 8; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;

			try {
				label = new Label(0, 0, "Número de Colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Tipo Trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Estado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Matrícula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Bastidor", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Fecha Primera Matrícula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Fecha Presentación", formatoCabecera);
				sheet.addCell(label);

				Integer contador = 1;

				List<TramiteTraficoVO> lista = tramiteTraficoDao.listadoPantallasEstadisticas(filter, null, null, null);

				for (TramiteTraficoVO element : lista) {

					// Columna Núm. Colegiado
					if (StringUtils.isNotBlank(element.getNumColegiado())) {
						label = new Label(0, contador, utiles.rellenarCeros(element.getNumColegiado(), 4), formatoDatos);
					} else {
						label = new Label(0, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo de Trámite
					if (StringUtils.isNotBlank(element.getTipoTramite())) {
						label = new Label(1, contador, TipoTramiteTraficoJustificante.convertirTexto(element.getTipoTramite()), formatoDatos);
					} else {
						label = new Label(1, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Estado
					if (element.getEstado() != null) {
						label = new Label(2, contador, EstadoTramiteTrafico.convertirTexto(element.getEstado().toString()), formatoDatos);
					} else {
						label = new Label(2, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Matrícula
					if (element.getVehiculo() != null && StringUtils.isNotBlank(element.getVehiculo().getMatricula())) {
						label = new Label(3, contador, element.getVehiculo().getMatricula(), formatoDatos);
					} else {
						label = new Label(3, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Bastidor
					if (element.getVehiculo() != null && StringUtils.isNotBlank(element.getVehiculo().getBastidor())) {
						label = new Label(4, contador, element.getVehiculo().getBastidor(), formatoDatos);
					} else {
						label = new Label(4, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Fecha Primera Matrícula
					if (element.getVehiculo() != null && element.getVehiculo().getFechaPrimMatri() != null) {
						label = new Label(5, contador, utilesFecha.formatoFecha(element.getVehiculo().getFechaPrimMatri()), formatoDatos);
					} else {
						label = new Label(5, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Fecha Presentación
					if (element.getFechaPresentacion() != null) {
						label = new Label(6, contador, utilesFecha.formatoFecha(element.getFechaPresentacion()), formatoDatos);
					} else {
						label = new Label(6, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					contador++;
				}
			} catch (RowsExceededException e) {
				log.error("Error al generar el fichero excel con las estadísticas de vehículos: ,error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de vehículos.");
			}
			copyWorkbook.write();
		} catch (IOException | WriteException e) {
			log.error("Error al generar el fichero excel con las estadísticas de vehículos: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de vehículos.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (IOException | WriteException e) {
					log.error("Error al generar el fichero excel con las estadísticas de vehículos: ,error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas de vehículos.");
				}
			}
		}

		resultado.addAttachment(ResultBean.TIPO_FILE, archivo);
		resultado.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero);

		return resultado;
	}

	@Override
	public ResultBean validarAgrupacionListadoGeneralYPersonalizado(ListadoGeneralYPersonalizadoBean bean) {
		ResultBean resultado = new ResultBean();

		if (StringUtils.isNotBlank(bean.getNumColegiado()) && Agrupacion.Colegiado.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Colegiado.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (StringUtils.isNotBlank(bean.getIdProvincia()) && Agrupacion.Provincia.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Provincia.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (StringUtils.isNotBlank(bean.getJefaturaProvincial()) && Agrupacion.Jefatura.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Jefatura.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (StringUtils.isNotBlank(bean.getTipoTramite()) && Agrupacion.Tipo_tramite.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Tipo_tramite.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (StringUtils.isNotBlank(bean.getTipoTramite()) && !bean.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.getValorEnum()) && Agrupacion.Municipio_Titular.getValorEnum()
				.equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Si agrupa por " + Agrupacion.Municipio_Titular.getNombreEnum() + " no puede filtrar por Tipo trámite.");
		} else if (StringUtils.isNotBlank(bean.getTipoVehiculo()) && Agrupacion.Tipo_vehiculo.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Tipo_vehiculo.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (StringUtils.isNotBlank(bean.getCodigoMarca()) && Agrupacion.Marca.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Marca.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		} else if (bean.getIdTipoCreacion() != null && Agrupacion.Tipo_Creacion.getValorEnum().equals(bean.getAgrupacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(Agrupacion.Tipo_Creacion.getNombreEnum() + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean generarExcelListadoGeneralYPersonalizadoEstadisticas(Object filter) {
		File archivo = null;
		ResultBean resultado = new ResultBean();

		String nombreFichero = "ESTADISTICAS__ICOGAM__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.ESTADISTICAS);
		fichero.setSubTipo(ConstantesGestorFicheros.EXCELS);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
		fichero.setNombreDocumento(nombreFichero);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		try {
			archivo = gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero excel con las estadísticas de vehículos: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al guardar el fichero excel con las estadísticas de vehículos.");
		}

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;

		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("EstadisticasTramites", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 8; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 8; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;

			try {
				label = new Label(0, 0, "Número de expediente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Número de colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Provincia", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Jefatura de tráfico", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Tipo de carburante", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Exento", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Servicio", formatoCabecera);
				sheet.addCell(label);
				label = new Label(7, 0, "Tipo de trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(8, 0, "Estado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(9, 0, "Marca del vehículo", formatoCabecera);
				sheet.addCell(label);
				label = new Label(10, 0, "Tipo de vehículo", formatoCabecera);
				sheet.addCell(label);
				label = new Label(11, 0, "Tipo de Creación", formatoCabecera);
				sheet.addCell(label);

				Integer contador = 1;

				String agrupacion = ((ListadoGeneralYPersonalizadoBean) filter).getAgrupacion();

				List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
				listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
				listaAlias.add(new AliasQueryBean(ProvinciaVO.class, "contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN));
				listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
				listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
				listaAlias.add(new AliasQueryBean(TipoVehiculoVO.class, "vehiculo.tipoVehiculoVO", "vehiculoTipoVehiculoVO", CriteriaSpecification.LEFT_JOIN));
				if (Agrupacion.Municipio_Titular.getValorEnum().equals(agrupacion)) {
					listaAlias.add(new AliasQueryBean(IntervinienteTraficoVO.class, "intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN));
					listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN));
					listaAlias.add(new AliasQueryBean(TipoIntervinienteVO.class, "intervinienteTraficos.tipoIntervinienteVO", "intervinienteTraficosTipoIntervinienteVO",
							CriteriaSpecification.LEFT_JOIN));
				}

				List<TramiteTraficoVO> lista = tramiteTraficoDao.listadoPantallasEstadisticas(filter, null, listaAlias, null);

				for (TramiteTraficoVO element : lista) {

					// Columna Núm Expediente
					if (element.getNumExpediente() != null) {
						label = new Label(0, contador, element.getNumExpediente().toString(), formatoDatos);
					} else {
						label = new Label(0, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Núm. Colegiado
					if (StringUtils.isNotBlank(element.getNumColegiado())) {
						label = new Label(1, contador, utiles.rellenarCeros(element.getNumColegiado(), 4), formatoDatos);
					} else {
						label = new Label(1, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Provincia del contrato
					if (element.getContrato() != null && element.getContrato().getProvincia() != null && StringUtils.isNotBlank(element.getContrato().getProvincia().getNombre())) {
						label = new Label(2, contador, element.getContrato().getProvincia().getNombre(), formatoDatos);
					} else {
						label = new Label(2, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Jefatura de tráfico
					if (element.getJefaturaTrafico() != null && StringUtils.isNotBlank(element.getJefaturaTrafico().getDescripcion())) {
						label = new Label(3, contador, element.getJefaturaTrafico().getDescripcion(), formatoDatos);
					} else {
						label = new Label(3, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo de carburante
					if (element.getVehiculo() != null && StringUtils.isNotBlank(element.getVehiculo().getIdCarburante())) {
						label = new Label(4, contador, Combustible.convertirAString(element.getVehiculo().getIdCarburante()), formatoDatos);
					} else {
						label = new Label(4, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Exento
					if (StringUtils.isNotBlank(element.getExentoCem())) {
						label = new Label(5, contador, element.getExentoCem(), formatoDatos);
					} else {
						label = new Label(5, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Servicio
					if (element.getVehiculo() != null && StringUtils.isNotBlank(element.getVehiculo().getIdServicio())) {
						label = new Label(6, contador, element.getVehiculo().getIdServicio(), formatoDatos);
					} else {
						label = new Label(6, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo de Trámite
					if (StringUtils.isNotBlank(element.getTipoTramite())) {
						label = new Label(7, contador, TipoTramiteTrafico.convertirTexto(element.getTipoTramite()), formatoDatos);
					} else {
						label = new Label(7, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Estado
					if (element.getEstado() != null) {
						label = new Label(8, contador, EstadoTramiteTrafico.convertirTexto(element.getEstado().toString()), formatoDatos);
					} else {
						label = new Label(8, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Marca del vehículo
					if (element.getVehiculo() != null && StringUtils.isNotBlank(element.getVehiculo().getCodigoMarca())) {
						MarcaDgtVO marca = servicioMarcaDgt.getMarcaDgtCodigo(Long.parseLong(element.getVehiculo().getCodigoMarca()));
						if (marca != null) {
							label = new Label(9, contador, marca.getMarca(), formatoDatos);
						} else {
							label = new Label(9, contador, "", formatoDatos);
						}
					} else {
						label = new Label(9, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo Vehículo
					if (element.getVehiculo() != null && element.getVehiculo().getTipoVehiculoVO() != null && StringUtils.isNotBlank(element.getVehiculo().getTipoVehiculo()) && StringUtils.isNotBlank(
							element.getVehiculo().getTipoVehiculoVO().getDescripcion())) {
						label = new Label(10, contador, element.getVehiculo().getTipoVehiculoVO().getDescripcion(), formatoDatos);
					} else {
						label = new Label(10, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Tipo creación
					if (element.getIdTipoCreacion() != null) {
						label = new Label(11, contador, TipoCreacion.convertirTexto(element.getIdTipoCreacion().toString()), formatoDatos);
					} else {
						label = new Label(11, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					contador++;
				}

			} catch (RowsExceededException e) {
				log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
			}
			copyWorkbook.write();
		} catch (IOException | WriteException e) {
			log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (IOException | WriteException e) {
					log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
				}
			}
		}

		resultado.addAttachment(ResultBean.TIPO_FILE, archivo);
		resultado.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero);

		return resultado;
	}

	@Override
	public ResultBean autorizacionInicialCertificacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, String opcionSeleccionada) throws NoSuchAlgorithmException, IOException {
		ResultBean result = new ResultBean();
		String opcion = null;
		if("opcion1".equals(opcionSeleccionada)) {
			opcion = "VEHÍCULOS PROCEDENTES DE LA UE E IRLANDA DEL NORTE (NUEVOS).";
		}else if("opcion2".equals(opcionSeleccionada)) {
			opcion = "VEHÍCULOS PROCEDENTES DE UN TERCER PAÍS, INCLUYENDO EEE NORUEGA LIECHTENSTEIN ISLANDIA (NUEVOS).";
		}else if("opcion3".equals(opcionSeleccionada)) {
			opcion = "VEHÍCULOS PROCEDENTES DE LA UE E IRLANDA DEL NORTE (USADOS).";
		}else if("opcion4".equals(opcionSeleccionada)) {
			opcion = "VEHÍCULOS PROCEDENTES DE UN TERCER PAÍS, INCLUYENDO EEE NORUEGA LIECHTENSTEIN ISLANDIA (USADOS).";
		}
		UtilResources util = new UtilResources();
		camposFormateados = new ArrayList<CampoPdfBean>();
		pdf = new PdfMaker();
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		ruta = util.getFilePath(ruta + TipoImpreso.CertificadoRevisionColegialInicial.getNombreEnum());
		bytePdf = pdf.abrirPdf(ruta);
		camposPlantilla = pdf.getAllFields(bytePdf);
		camposFormateados.addAll(obtenerValoresCertificadoRevisionInicial(ConstantesPDF._11,formularioAutorizarTramitesDto,camposPlantilla,opcion));
		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		result.addAttachment(ResultBean.TIPO_PDF,bytePdf);
		result.addAttachment(ResultBean.NOMBRE_FICHERO, "CertificadoRevisionColegialInicial.pdf");
		if(!result.getError()) {
			guardardocumento(bytePdf,formularioAutorizarTramitesDto.getNumExpediente(),TipoImpreso.CertificadoRevisionColegialInicial.toString(),result);
			if(!result.getError()) {
				String versionMax = tramiteCertificacionDao.getCertificacion(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()),null,formularioAutorizarTramitesDto.getVersion());
				if(versionMax == null){
					formularioAutorizarTramitesDto.setVersion("001");
				}else {
					Integer valor = Integer.parseInt(versionMax) + 1;
					String cadena = String.format("%0" + versionMax.length() + "d", valor);
					formularioAutorizarTramitesDto.setVersion(cadena);
				}
				generarCsv(formularioAutorizarTramitesDto,result);
			}else {
				result.setError(Boolean.TRUE);
				result.getMensaje();
			}
		}else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha ocurrido un problema a la hora de guardar os valores en los campos del pdf");
		}

		return result;
	}

	private Collection<? extends CampoPdfBean> obtenerValoresCertificadoRevisionInicial(Integer tamCampos, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, Set<String> camposPlantilla, String opcion) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		if (camposPlantilla.contains(ConstantesPDF.TITULO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.TITULO, opcion, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.EXP_BASTIDOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.EXP_BASTIDOR, formularioAutorizarTramitesDto.getNumExpediente() +"-"+ 
					formularioAutorizarTramitesDto.getBastiMatri(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.NUM_EXPEDIENTE)) {
			campoAux = new CampoPdfBean(ConstantesPDF.NUM_EXPEDIENTE, formularioAutorizarTramitesDto.getNumExpediente(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.BASTIDOR_MATRICULA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.BASTIDOR_MATRICULA, formularioAutorizarTramitesDto.getBastiMatri(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ESTA_ITV)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ESTA_ITV, formularioAutorizarTramitesDto.getEstacionItv(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.KILOMETROS)) {
			campoAux = new CampoPdfBean(ConstantesPDF.KILOMETROS, formularioAutorizarTramitesDto.getKilometraje(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.KILOMETR)) {
			String valor = formularioAutorizarTramitesDto.isValidacionKm() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.KILOMETR, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.EST_ITV)) {
			String valor = formularioAutorizarTramitesDto.isValidacionEstacion() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.EST_ITV, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PAIS_PREVIA)) {
			String valor = formularioAutorizarTramitesDto.isValidacionPaisPrevMatr() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.PAIS_PREVIA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PAIS)) {
			campoAux = new CampoPdfBean(ConstantesPDF.PAIS, formularioAutorizarTramitesDto.getPaisPreviaMatri(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TARJETA_A)) {
			String valor = formularioAutorizarTramitesDto.isTarjetaTipoA1() || formularioAutorizarTramitesDto.isTarjetaTipoA2()
					 || formularioAutorizarTramitesDto.isTarjetaTipoA3() || formularioAutorizarTramitesDto.isTarjetaTipoA4()? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TARJETA_A, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IEDMT)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIedmt1() || formularioAutorizarTramitesDto.isImpuestoIedmt2()
					 || formularioAutorizarTramitesDto.isImpuestoIedmt3()  || formularioAutorizarTramitesDto.isImpuestoIedmt4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IEDMT, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVTM)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIvtm1() || formularioAutorizarTramitesDto.isImpuestoIvtm2()
					 || formularioAutorizarTramitesDto.isImpuestoIvtm3() || formularioAutorizarTramitesDto.isImpuestoIvtm4()? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVTM, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ACREDITACION_PROPIEDAD)) {
			String valor = formularioAutorizarTramitesDto.isAcreditacionPropiedad1() || formularioAutorizarTramitesDto.isAcreditacionPropiedad2() 
					|| formularioAutorizarTramitesDto.isAcreditacionPropiedad3() || formularioAutorizarTramitesDto.isAcreditacionPropiedad4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ACREDITACION_PROPIEDAD, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.JUSTIFICANTE_IVA)) {
			String valor = formularioAutorizarTramitesDto.isJustificanteIva1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.JUSTIFICANTE_IVA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CENSO)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarCenso1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CENSO, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.SERVICIO_VEHICULO)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarServicioVeh1() || formularioAutorizarTramitesDto.isAcreditarServicioVeh2()
					|| formularioAutorizarTramitesDto.isAcreditarServicioVeh3() || formularioAutorizarTramitesDto.isAcreditarServicioVeh4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.SERVICIO_VEHICULO, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CEMA)) {
			String valor = formularioAutorizarTramitesDto.isCema1() || formularioAutorizarTramitesDto.isCema2()
					 || formularioAutorizarTramitesDto.isCema3() || formularioAutorizarTramitesDto.isCema4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CEMA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NO_CERTIF_TRANSPORTE)) {
			String valor = formularioAutorizarTramitesDto.isNoJustifTransp1() || formularioAutorizarTramitesDto.isNoJustifTransp2()
					|| formularioAutorizarTramitesDto.isNoJustifTransp3() || formularioAutorizarTramitesDto.isNoJustifTransp4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.NO_CERTIF_TRANSPORTE, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.DUA)) {
			String valor = formularioAutorizarTramitesDto.isDua2() || formularioAutorizarTramitesDto.isDua4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DUA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.DOCUMENTO_ORIGINAL_1)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal13() || formularioAutorizarTramitesDto.isDocOriginal14() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOCUMENTO_ORIGINAL_1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOCUMENTO_ORIGINAL_2)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal23() || formularioAutorizarTramitesDto.isDocOriginal24() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOCUMENTO_ORIGINAL_2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PLACA_VERDE)) {
			String valor = formularioAutorizarTramitesDto.isPlacaVerde3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.PLACA_VERDE, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TRADUCCION_CONTRATO)) {
			String valor = formularioAutorizarTramitesDto.isTraduccionContrato3() || formularioAutorizarTramitesDto.isTraduccionContrato4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TRADUCCION_CONTRATO, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ITP)) {
			String valor = formularioAutorizarTramitesDto.isItp3() || formularioAutorizarTramitesDto.isItp4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ITP, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.FACTURA)) {
			String valor = formularioAutorizarTramitesDto.isIae3() || formularioAutorizarTramitesDto.isIae4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.FACTURA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		return camposFormateados;
	}

	@Override
	public ResultBean autorizarCertificacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) throws NoSuchAlgorithmException, IOException {
		ResultBean result = new ResultBean();
		UtilResources util = new UtilResources();
		camposFormateados = new ArrayList<CampoPdfBean>();
		pdf = new PdfMaker();
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		ruta = util.getFilePath(ruta + TipoImpreso.CertificadoRevisionColegial.getNombreEnum());
		bytePdf = pdf.abrirPdf(ruta);
		camposPlantilla = pdf.getAllFields(bytePdf);
		camposFormateados.addAll(obtenerValoresCertificadoRevision(ConstantesPDF._11,formularioAutorizarTramitesDto,camposPlantilla));
		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		result.addAttachment(ResultBean.TIPO_PDF,bytePdf);
		result.addAttachment(ResultBean.NOMBRE_FICHERO, "CertificadoRevisionColegial.pdf");
		if(!result.getError()) {
			guardardocumento(bytePdf,formularioAutorizarTramitesDto.getNumExpediente(),TipoImpreso.CertificadoRevisionColegial.toString(),result);
			if(!result.getError()) {
				String versionMax = tramiteCertificacionDao.getCertificacion(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()),null,formularioAutorizarTramitesDto.getVersion());
				if(versionMax == null){
					formularioAutorizarTramitesDto.setVersion("001");
				}else {
					Integer valor = Integer.parseInt(versionMax) + 1;
					String cadena = String.format("%0" + versionMax.length() + "d", valor);
					formularioAutorizarTramitesDto.setVersion(cadena);
				}
				generarCsv(formularioAutorizarTramitesDto,result);
			}else {
				result.setError(Boolean.TRUE);
				result.getMensaje();
			}
		}else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha ocurrido un problema a la hora de guardar os valores en los campos del pdf");
		}

		return result;
	}

	private void generarCsv(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, ResultBean result) throws NoSuchAlgorithmException, IOException {
		File archivoCSV = new File(gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP")+"certificado.csv");
		CertificadoCsv row = null;
		try {
			FileWriter writer = new FileWriter(archivoCSV);
			row = new CertificadoCsv("Q2861007I", "Q2861007I", formularioAutorizarTramitesDto.getNumExpediente(),formularioAutorizarTramitesDto.getVersion());
			writer.write(row.getDoiColegio() + "," + row.getDoiPlataforma() + "," + row.getIdExpediente() + "," + row.getIdVersionExp() + "," + row.getHash() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FicheroBean fichero = new FicheroBean();
		fichero.setFichero(archivoCSV);
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_CSV);
		fichero.setNombreDocumento(formularioAutorizarTramitesDto.getNumExpediente());
		fichero.setFecha(Utilidades.transformExpedienteFecha(formularioAutorizarTramitesDto.getNumExpediente()));
		try {
			gestorDocumentos.guardarFichero(fichero);
			archivoCSV.delete();
			log.info("Fichero guardado en TDOC");
			result = guardarTramiteCertificacion(formularioAutorizarTramitesDto.getNumExpediente(),row.getHash(),row.getIdVersionExp());
			if(result.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Ha ocurrido un error al guardar el registro en BBDD para el expediente" + formularioAutorizarTramitesDto.getNumExpediente());
			}
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el ficher en tdoc para el numero de expediente:" + formularioAutorizarTramitesDto.getNumExpediente());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean firmarAutorizacion(String numExpediente,BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FileResultBean ficheroColegiado=null;
			ContratoVO contrato = contratoDao.getContrato(idContrato.longValue(), Boolean.FALSE);
			if (contrato != null) {
				String autorizacionCertificacion = gestorPropiedades.valorPropertie("autorizacion.consejo");
				if("SI".equals(autorizacionCertificacion)) {
					ficheroColegiado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION, 
						Utilidades.transformExpedienteFecha(numExpediente), numExpediente+"_CertificadoRevisionColegial",
						ConstantesGestorFicheros.EXTENSION_PDF);
				}else {
					ficheroColegiado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION, 
							Utilidades.transformExpedienteFecha(numExpediente), numExpediente+"_CertificadoRevisionColegialInicial",
							ConstantesGestorFicheros.EXTENSION_PDF);
				}
				File pdf = null;
				pdf = ficheroColegiado.getFile();
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				byte[] docFirmado = utilesViafirma.firmarAutorizacionColegial(gestorDocumentos.transformFiletoByte(pdf), contrato.getColegiado().getAlias());
				if (docFirmado != null) {
					if("SI".equals(autorizacionCertificacion)) {
						pdf = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION, Utilidades.transformExpedienteFecha(numExpediente), numExpediente+"_CertificadoRevisionColegial" + "_Firma_OK",
								ConstantesGestorFicheros.EXTENSION_PDF, docFirmado);
					}else {
						pdf = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION, Utilidades.transformExpedienteFecha(numExpediente), numExpediente+"_CertificadoRevisionColegialInicial" + "_Firma_OK",
								ConstantesGestorFicheros.EXTENSION_PDF, docFirmado);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del colegiado para poder realizar la firma.");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de firmar la autorización colegial, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar la autorización colegial.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarTramiteCertificacion(String numExpediente, String hash, String version) {
		ResultBean result = new ResultBean();
		try {
			TramiteCertificacionVO tramiteCertificacionVON = new TramiteCertificacionVO();
			tramiteCertificacionVON.setHash(hash);
			tramiteCertificacionVON.setVersion(version);
			tramiteCertificacionVON.setNumExpediente(new BigDecimal(numExpediente));
			tramiteCertificacionDao.guardarOActualizar(tramiteCertificacionVON);
		} catch (Exception e) {
			log.error("Error al guardar el registro en BBDD:" + numExpediente);
		}

		return result;
	}
	
	@Override
	public ResultBean aceptarAutorizacionTransmision(TramiteTrafTranDto tramiteTrafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		ResultBean resultValidacion = validacionesPreviasAutorizar(tramiteTrafTranDto,formularioAutorizarTramitesDto);
		if(!resultValidacion.getError()) {
			result = guardardadoValorAdicional(tramiteTrafTranDto,formularioAutorizarTramitesDto,idUsuario);
		}else {
			result.setError(Boolean.TRUE);
			result.setListaMensajes(resultValidacion.getListaMensajes());
		}
		return result;
	}
	@Override
	public ResultBean cambiarEstadoAutorizacion(TramiteTrafTranDto tramiteTrafTranDto,TramiteTrafMatrDto tramiteTrafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		if(comprobarTodosAutorizados(tramiteTrafMatrDto,tramiteTrafTranDto,formularioAutorizarTramitesDto)) {
			result = cambiarEstado(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()),new BigDecimal(EstadoTramiteTrafico.Autorizado.getValorEnum()),idUsuario.longValue());
			if(result.getError()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Ha ocurrido un error a la hora de cambiar el estado del trámite.");
			}else {
				result.addMensajeALista("El expediente: " + formularioAutorizarTramitesDto.getNumExpediente() + " se ha autorizado para tramitar telemáticamente.");
			}
		}else {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("El estado del trámite no cambiará hasta que se autorice cada uno de los distintos tipos.Vuelva a Autorizar Trámites ICOGAM para comprobar que tipo falta.");
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public ResultBean guardardadoValorAdicional(TramiteTrafTranDto tramiteTrafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		try {
			if("IVTM".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
				tramiteTrafTranDto.setAutorizadoIvtm("SI");
			}
			if("HERENCIA".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado()) || "DONACION".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
				tramiteTrafTranDto.setAutorizadoHerenciaDonacion("SI");
			}
			TramiteTrafTranVO tramiteTrafTranVO = conversor.transform(tramiteTrafTranDto, TramiteTrafTranVO.class);
			tramiteTraficoTransDao.guardarOActualizar(tramiteTrafTranVO);
		} catch (Exception e) {
			log.error("Error al guardar el registro en BBDD:" + tramiteTrafTranDto.getNumExpediente());
		}
		return result;
		
	}

	private boolean comprobarTodosAutorizados(TramiteTrafMatrDto tramiteTrafMatrDto, TramiteTrafTranDto tramiteTrafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) {
		if(tramiteTrafTranDto != null) {
			if(("SI".equalsIgnoreCase(tramiteTrafTranDto.getAcreditacionPago()) && "SI".equalsIgnoreCase(tramiteTrafTranDto.getAutorizadoIvtm())) &&
			(("HERENCIA".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()) || "DONACION".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion())) && "SI".equalsIgnoreCase(tramiteTrafTranDto.getAutorizadoHerenciaDonacion()))){
				return true;
			}else if(("SI".equalsIgnoreCase(tramiteTrafTranDto.getAcreditacionPago()) && "SI".equalsIgnoreCase(tramiteTrafTranDto.getAutorizadoIvtm())) &&
					(!"HERENCIA".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()) || !"DONACION".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()))){
				return true;
			}else if(("NO".equalsIgnoreCase(tramiteTrafTranDto.getAcreditacionPago())) &&
					(("HERENCIA".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()) || "DONACION".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion())) && "SI".equalsIgnoreCase(tramiteTrafTranDto.getAutorizadoHerenciaDonacion()))){
				return true;
			}
		}
		if(tramiteTrafMatrDto != null) {
			if((servicioTramiteTraficoMatriculacion.esUnaFichaA(tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV()) && "SI".equalsIgnoreCase(tramiteTrafMatrDto.getAutorizadoFichaA())) &&
				(StringUtils.isBlank(tramiteTrafMatrDto.getExentoCtr()) || "NO".equalsIgnoreCase(tramiteTrafMatrDto.getExentoCtr()))) {
	    	return true;
		    }else if(tramiteTrafMatrDto != null && !servicioTramiteTraficoMatriculacion.esUnaFichaA(tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV()) &&
					("SI".equalsIgnoreCase(tramiteTrafMatrDto.getExentoCtr()) && "SI".equalsIgnoreCase(tramiteTrafMatrDto.getAutorizadoExentoCtr()))) {
				return true;
			}else if(tramiteTrafMatrDto != null && (servicioTramiteTraficoMatriculacion.esUnaFichaA(tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV()) && "SI".equalsIgnoreCase(tramiteTrafMatrDto.getAutorizadoFichaA())) &&
					("SI".equalsIgnoreCase(tramiteTrafMatrDto.getExentoCtr()) && "SI".equalsIgnoreCase(tramiteTrafMatrDto.getAutorizadoExentoCtr()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResultBean aceptarAutorizacionMatw(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto,TramiteTrafMatrDto trafMatrDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		ResultBean resultValidacion = validacionesPreviasAutorizarMatw(formularioAutorizarTramitesDto,trafMatrDto);
		if(!resultValidacion.getError()) {
			result = guardardadoValorAdicionalMatw(trafMatrDto,formularioAutorizarTramitesDto,idUsuario);
		}else {
			result.setError(Boolean.TRUE);
			result.setListaMensajes(resultValidacion.getListaMensajes());
		}
	
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardardadoValorAdicionalMatw(TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if("FICHA_A".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
				trafMatrDto.setAutorizadoFichaA("SI");
			}
			if("EXENTO_CTR".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
				trafMatrDto.setAutorizadoExentoCtr("SI");
			}
			TramiteTrafMatrVO tramiteTrafMatrVO = conversor.transform(trafMatrDto, TramiteTrafMatrVO.class);
			tramiteTraficoMatrDao.guardarOActualizar(tramiteTrafMatrVO);
		} catch (Exception e) {
			log.error("Error al guardar el registro en BBDD:" + trafMatrDto.getNumExpediente());
		}
		return result;
	}

	private ResultBean validacionesPreviasAutorizarMatw(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, TramiteTrafMatrDto trafMatrDto) {
		ResultBean result = new ResultBean();
		if("SI".equalsIgnoreCase(trafMatrDto.getExentoCtr()) && "EXENTO_CTR".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
			if(!formularioAutorizarTramitesDto.isCheckAcreditacion()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite con exención CTR hay que comprobar la documentación y marcar el check.");
			}
		}
		return result;
	}

	@Override
	public ResultBean denegarAutorizacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		ResultBean resultValidacion = validacionesPrevias(formularioAutorizarTramitesDto);
		if(!resultValidacion.getError()) {
			result = cambiarEstado(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()),
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()),idUsuario.longValue());
			if(result.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Ha ocurrido un error al actualizar el estado del trámite.");
			}else {
				result.setMensaje("Se ha denegado la autorización y el trámite " + formularioAutorizarTramitesDto.getNumExpediente() + " finaliza con error");
			}
		}else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Hubo un problema a la hora de denegar una autorizacion para el expediente " +formularioAutorizarTramitesDto.getNumExpediente() +"."+ resultValidacion.getMensaje());
		}

		return result;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario) {
		ResultBean result = new ResultBean();
		TramiteTraficoVO tramite = servicioComunTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
		EstadoTramiteTrafico estadoAntiguo = EstadoTramiteTrafico.convertir(tramite.getEstado());
		tramite.setEstado(estadoNuevo);
		tramiteTraficoDao.guardarOActualizar(tramite);
		try {
			guardarEvolucionTramite(numExpediente, estadoAntiguo, EstadoTramiteTrafico.convertir(estadoNuevo), BigDecimal.valueOf(idUsuario));
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolución del trámite: " + numExpediente + ".", e);
		}
		return result;
	}

	private ResultBean validacionesPrevias(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) {
		ResultBean result = new ResultBean();
		if(StringUtils.isBlank(formularioAutorizarTramitesDto.getObservaciones())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Para denegar una autorización debe indicar el motivo en el campo observaciones.");
		}
		
		return result;
	}
	
	private ResultBean validacionesPreviasAutorizar(TramiteTrafTranDto tramiteTrafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) {
		ResultBean result = new ResultBean();
		if("SI".equalsIgnoreCase(tramiteTrafTranDto.getAcreditacionPago()) && "IVTM".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
			if(!formularioAutorizarTramitesDto.isAcreditacionAbono()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite con IVTM impagado hay que comprobar y chequear Acreditación Abono.");
			}
			if(!formularioAutorizarTramitesDto.isInformeVehiculo()){
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite con IVTM impagado hay que comprobar y chequear Informe Vehículo.");
			}
		}
		if("HERENCIA".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()) && "HERENCIA".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
			if(!formularioAutorizarTramitesDto.isAnexoI()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite de herencia hay que comprobar y chequear documento anexoI.");
			}
			if(!formularioAutorizarTramitesDto.isJustificanteIsd()){
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite de herencia hay que comprobar y chequear el justificante de impuesto de sucesiones.");
			}
		}else if("DONACION".equalsIgnoreCase(tramiteTrafTranDto.getAcreditaHerenciaDonacion()) && "DONACION".equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
			if(!formularioAutorizarTramitesDto.isDocumentoAcreditativoDonacion()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite de donación hay que comprobar y chequear documento acreditativo.");
			}
			if(!formularioAutorizarTramitesDto.isJustificanteIsd()){
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Para autorizar un trámite de donación hay que comprobar y chequear el justificante de impuesto de sucesiones.");
			}
		}
		return result;
	}
	
	private ResultBean guardardocumento(byte[] bytePdf, String numExpediente, String nombreDocumento, ResultBean result) {
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ficheroBean.setNombreDocumento(numExpediente +"_"+nombreDocumento);
			ficheroBean.setFicheroByte(bytePdf);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el pdf del certificado de autorización, error: ", e, numExpediente.toString());
			result.setError(Boolean.TRUE);
			result.setMensaje("Error guardar el pdf del certificado de autorización");
		}
		return result;
	}

	private Collection<? extends CampoPdfBean> obtenerValoresCertificadoRevision(Integer tamCampos, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesPDF.NUM_COLEGIADO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.NUM_COLEGIADO, formularioAutorizarTramitesDto.getNumColegiado(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NUM_EXPEDIENTE)) {
			campoAux = new CampoPdfBean(ConstantesPDF.NUM_EXPEDIENTE, formularioAutorizarTramitesDto.getNumExpediente(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOI_TITULAR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.DOI_TITULAR, formularioAutorizarTramitesDto.getDoiTitular(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.BASTIDOR_MATRICULA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.BASTIDOR_MATRICULA, formularioAutorizarTramitesDto.getBastiMatri(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.JEFATURA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.JEFATURA, formularioAutorizarTramitesDto.getJefatura(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ESTACION_ITV)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ESTACION_ITV, formularioAutorizarTramitesDto.getEstacionItv(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.KILOMETRAJE)) {
			campoAux = new CampoPdfBean(ConstantesPDF.KILOMETRAJE, formularioAutorizarTramitesDto.getKilometraje(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PAIS_MATRICULA_PREVIA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.PAIS_MATRICULA_PREVIA, formularioAutorizarTramitesDto.getPaisPreviaMatri(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TARJETA_TIPO_A1)) {
			String valor = formularioAutorizarTramitesDto.isTarjetaTipoA1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TARJETA_TIPO_A1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IEDMT1)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIedmt1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IEDMT1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVTM1)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIvtm1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVTM1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PROPIEDAD_FACTURA1)) {
			String valor = formularioAutorizarTramitesDto.isAcreditacionPropiedad1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.PROPIEDAD_FACTURA1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVA1)) {
			String valor = formularioAutorizarTramitesDto.isJustificanteIva1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVA1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ACREDITAR_CENSO1)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarCenso1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ACREDITAR_CENSO1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.SERVICIO_VEHICULO1)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarServicioVeh1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.SERVICIO_VEHICULO1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CEMA1)) {
			String valor = formularioAutorizarTramitesDto.isCema1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CEMA1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NO_CERTIF_TRANSPORTE1)) {
			String valor = formularioAutorizarTramitesDto.isNoJustifTransp1() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.NO_CERTIF_TRANSPORTE1, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TARJETA_TIPO_A2)) {
			String valor = formularioAutorizarTramitesDto.isTarjetaTipoA2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TARJETA_TIPO_A2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IEDMT2)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIedmt2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IEDMT2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVTM2)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIvtm2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVTM2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PROPIEDAD_FACTURA2)) {
			String valor = formularioAutorizarTramitesDto.isAcreditacionPropiedad2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.PROPIEDAD_FACTURA2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.SERVICIO_VEHICULO2)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarServicioVeh2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.SERVICIO_VEHICULO2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CEMA2)) {
			String valor = formularioAutorizarTramitesDto.isCema2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CEMA2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DUA2)) {
			String valor = formularioAutorizarTramitesDto.isDua2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DUA2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NO_CERTIF_TRANSPORTE2)) {
			String valor = formularioAutorizarTramitesDto.isNoJustifTransp2() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.NO_CERTIF_TRANSPORTE2, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOC_ORIGINAL3)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal13() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOC_ORIGINAL3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOC_ORIGINALII3)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal23() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOC_ORIGINALII3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.PLACA_VERDE3)) {
			String valor = formularioAutorizarTramitesDto.isPlacaVerde3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.PLACA_VERDE3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TARJETA_TIPO_A3)) {
			String valor = formularioAutorizarTramitesDto.isTarjetaTipoA3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TARJETA_TIPO_A3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.EIDTM3)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIedmt3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.EIDTM3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVTM3)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIvtm3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVTM3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ACREDITAR_PROPIEDAD3)) {
			String valor = formularioAutorizarTramitesDto.isAcreditacionPropiedad3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ACREDITAR_PROPIEDAD3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TRADUCCION3)) {
			String valor = formularioAutorizarTramitesDto.isTraduccionContrato3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TRADUCCION3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ITP3)) {
			String valor = formularioAutorizarTramitesDto.isItp3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ITP3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IAE3)) {
			String valor = formularioAutorizarTramitesDto.isIae3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IAE3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.SERVICIO_VEHICULO3)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarServicioVeh3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.SERVICIO_VEHICULO3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CEMA3)) {
			String valor = formularioAutorizarTramitesDto.isCema3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CEMA3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NO_CERTIF_TRANSPORTE3)) {
			String valor = formularioAutorizarTramitesDto.isNoJustifTransp3() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.NO_CERTIF_TRANSPORTE3, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOC_ORIGINAL4)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal14() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOC_ORIGINAL4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DOC_ORIGINALII4)) {
			String valor = formularioAutorizarTramitesDto.isDocOriginal24() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DOC_ORIGINALII4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TARJETA_TIPO_A4)) {
			String valor = formularioAutorizarTramitesDto.isTarjetaTipoA4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TARJETA_TIPO_A4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.EIDTM4)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIedmt4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.EIDTM4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IVTM4)) {
			String valor = formularioAutorizarTramitesDto.isImpuestoIvtm4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IVTM4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ACREDITAR_PROPIEDAD4)) {
			String valor = formularioAutorizarTramitesDto.isAcreditacionPropiedad4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ACREDITAR_PROPIEDAD4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.TRADUCCION4)) {
			String valor = formularioAutorizarTramitesDto.isTraduccionContrato4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.TRADUCCION4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ITP4)) {
			String valor = formularioAutorizarTramitesDto.isItp4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.ITP4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.IAE4)) {
			String valor = formularioAutorizarTramitesDto.isIae4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.IAE4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.SERVICIO_VEHICULO4)) {
			String valor = formularioAutorizarTramitesDto.isAcreditarServicioVeh4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.SERVICIO_VEHICULO4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.CEMA4)) {
			String valor = formularioAutorizarTramitesDto.isCema4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.CEMA4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.DUA4)) {
			String valor = formularioAutorizarTramitesDto.isDua4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.DUA4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.NO_CERTIF_TRANSPORTE4)) {
			String valor = formularioAutorizarTramitesDto.isNoJustifTransp4() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.NO_CERTIF_TRANSPORTE4, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.VALIDACION_ESTACION_ITV)) {
			String valor = formularioAutorizarTramitesDto.isValidacionEstacion() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.VALIDACION_ESTACION_ITV, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.VALIDACION_KILOMETRAJE)) {
			String valor = formularioAutorizarTramitesDto.isValidacionKm() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.VALIDACION_KILOMETRAJE, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.VALIDACION_PAIS_MATRICULA_PREVIA)) {
			String valor = formularioAutorizarTramitesDto.isValidacionPaisPrevMatr() ? "Sí" : "Off";
			campoAux = new CampoPdfBean(ConstantesPDF.VALIDACION_PAIS_MATRICULA_PREVIA, valor, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		return camposFormateados;
	}

	@Override
	public ResultBean enviarMailDenegarColegiado(TramiteTrafTranDto trafTranDto,TramiteTrafMatrDto trafMatrDto,FormularioAutorizarTramitesDto formularioAutorizarTramitesDto,String tipoTramite) {
		ResultBean result = new ResultBean();
		try {
			ContratoVO contratoBBDD = utilesColegiado.getDetalleColegiado(formularioAutorizarTramitesDto.getNumColegiado());
			String direccion = contratoBBDD.getCorreoElectronico();
			String subject = "";
			String dirOcultas= "";
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tipoTramite)) { 
				if(trafMatrDto != null) {
					if(TipoAutorizacion.EXENTO_CTR.getNombreEnum().equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
						 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA EXENTOS CTR";
						 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.matw");
					}else if(TipoAutorizacion.FICHA_A.getNombreEnum().equalsIgnoreCase(formularioAutorizarTramitesDto.getValorSeleccionado())) {
						 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA FICHAS TIPO A";
						 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.matw");
					} 
				}
			}else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tipoTramite)) {
				if(trafTranDto != null && trafTranDto.getAcreditacionPago() != null && "SI".equalsIgnoreCase(trafTranDto.getAcreditacionPago())) {
					 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA IVTM IMPAGADOS";
					 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.ivtm.ctit");	
				}else if(trafTranDto != null && trafTranDto.getAcreditaHerenciaDonacion() != null && ("HERENCIA".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion())
						|| "DONACION".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion()))) {
					 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA HERENCIAS";
					 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.herencia.ctit");	
				}
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("Le informamos que el expediente " + formularioAutorizarTramitesDto.getNumExpediente() + " no se ha podido autorizar porque el tipo de autorización " + formularioAutorizarTramitesDto.getValorSeleccionado() + " ha tenido los problemas siguientes: ");
			sb.append("<br>");
			sb.append(formularioAutorizarTramitesDto.getObservaciones());
			sb.append("<br>");
			ResultBean resultCorreo = servicioCorreo.enviarCorreoInciJptm(sb.toString(), null, null, subject, direccion, null, dirOcultas, null);
			if (resultCorreo.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultCorreo.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Error al enviar el correo electronico al colegiado, error: ", e, formularioAutorizarTramitesDto.getNumExpediente());
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al enviar el correo electronico al colegiado");
		}
		return result;
	}

	@Override
	public ResultBean enviarMailAutorizarColegiado(TramiteTrafTranDto trafTranDto,TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, String tipoTramite) {
		ResultBean result = new ResultBean();
		try {
			ContratoVO contratoBBDD = utilesColegiado.getDetalleColegiado(formularioAutorizarTramitesDto.getNumColegiado());
			String direccion = contratoBBDD.getCorreoElectronico();
			String subject = "";
			String dirOcultas= "";
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tipoTramite)) { 
					if(trafMatrDto != null) {
						if((trafMatrDto.getExentoCtr() != null && "SI".equalsIgnoreCase(trafMatrDto.getExentoCtr()) && "SI".equalsIgnoreCase(trafMatrDto.getAutorizadoExentoCtr())) && !esUnaFichaA(trafMatrDto.getVehiculoDto().getTipoTarjetaITV())) {
							 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA EXENTOS CTR";
							 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.matw");
						}else if((trafMatrDto.getExentoCtr() != null && "NO".equalsIgnoreCase(trafMatrDto.getExentoCtr())) && (esUnaFichaA(trafMatrDto.getVehiculoDto().getTipoTarjetaITV()) 
								&& "SI".equalsIgnoreCase(trafMatrDto.getAutorizadoFichaA()))) {
							 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA FICHAS TIPO A";
							 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.matw");
						}else if((trafMatrDto.getExentoCtr() != null && "SI".equalsIgnoreCase(trafMatrDto.getExentoCtr()) && "SI".equalsIgnoreCase(trafMatrDto.getAutorizadoExentoCtr())) &&
								(esUnaFichaA(trafMatrDto.getVehiculoDto().getTipoTarjetaITV()) && "SI".equalsIgnoreCase(trafMatrDto.getAutorizadoFichaA()))) {
							subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA FICHAS TIPO A Y EXENCIÓN CTR";
							 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.matw");
						}
							
					}
			}else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tipoTramite)) {
				if(trafTranDto != null && trafTranDto.getAcreditacionPago() != null && "SI".equalsIgnoreCase(trafTranDto.getAcreditacionPago())) {
					 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA IVTM IMPAGADOS";
					 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.ivtm.ctit");	
				}else if(trafTranDto != null && trafTranDto.getAcreditaHerenciaDonacion() != null && ("HERENCIA".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion())
						|| "DONACION".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion()))) {
					 subject = "AUTORIZACIÓN TRAMITACIÓN TELEMÁTICA HERENCIAS O DONACIONES";
					 dirOcultas = gestorPropiedades.valorPropertie("direccion.correo.autorizacion.colegio.herencia.ctit");	
				}
				
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Le informamos que el expediente " + formularioAutorizarTramitesDto.getNumExpediente() + " se ha autorizado correctamente. ");
			sb.append("<br>");
			sb.append("Deberá ir a la consulta de trámites y tramitar Telemáticamente.");
			sb.append("<br>");
			ResultBean resultCorreo = servicioCorreo.enviarCorreoInciJptm(sb.toString(), null, null, subject, direccion, null, dirOcultas, null);
			if (resultCorreo.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultCorreo.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Error al enviar el correo electronico al colegiado, error: ", e, formularioAutorizarTramitesDto.getNumExpediente());
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al enviar el correo electronico al colegiado");
		} 
		return result;
	}


	private boolean esUnaFichaA(String tipoTarjetaITV) {
		if (tipoTarjetaITV == null) {
			return false;
		}
 
		List<String> fichasA = new ArrayList<>();
		fichasA.add(TipoTarjetaITV.A.getValorEnum());
		fichasA.add(TipoTarjetaITV.AT.getValorEnum());
		fichasA.add(TipoTarjetaITV.AR.getValorEnum());
		fichasA.add(TipoTarjetaITV.AL.getValorEnum());
 
		return fichasA.contains(tipoTarjetaITV);
	}

	@Override
	public String getRespuestaTramiteTrafico(BigDecimal numExpediente) {
		return tramiteTraficoDao.obtenerRespuestaTramiteTrafico(numExpediente);
	}

	@Override
	@Transactional
	public ResultadoAccionUsuarioBean borrarDatos(String codSeleccionados, String datoBorrar, BigDecimal idUsuario) {
		ResultadoAccionUsuarioBean result = new ResultadoAccionUsuarioBean(Boolean.FALSE);
		try {
			TramiteTrafTranVO trafTranVO = null;
			VehiculoTramiteTraficoVO vehiculoTramiteTraficoVO = null;
			TramiteTrafMatrVO trafMatrVO = null;
			TramiteTraficoVO tramiteTraficoVO = tramiteTraficoDao.getTramite(new BigDecimal(codSeleccionados), Boolean.FALSE);
			if(!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getEstado().toString())
					&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getEstado().toString())
					&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getEstado().toString())) {
				if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite())) {
					if(TipoDatoBorrar.Respuesta_Check.getValorEnum().equalsIgnoreCase(datoBorrar)) {
						trafTranVO = tramiteTraficoTransDao.getTramiteTransmision(new BigDecimal(codSeleccionados), Boolean.FALSE);
						if(trafTranVO.getResCheckCtit() != null) {
							result.setRespuesta(trafTranVO.getResCheckCtit());
							trafTranVO.setResCheckCtit(null);
							tramiteTraficoTransDao.guardarOActualizar(trafTranVO);
						}else {
							result.setError(Boolean.TRUE);
							result.setMensaje("No hay respuesta checkCtit para borrar para el numero de expediente " +codSeleccionados);
						}
					}else if(TipoDatoBorrar.Respuesta_Tramitación.getValorEnum().equalsIgnoreCase(datoBorrar)) {
						trafTranVO = tramiteTraficoTransDao.getTramiteTransmision(new BigDecimal(codSeleccionados), Boolean.FALSE);
						if(trafTranVO.getRespuesta() != null) {
							result.setRespuesta(trafTranVO.getRespuesta());
							trafTranVO.setRespuesta(null);
							tramiteTraficoDao.guardarOActualizar(trafTranVO);
						}else {
							result.setError(Boolean.TRUE);
							result.setMensaje("No hay respuesta de tramitación para borrar para el numero de expediente " +codSeleccionados);
						}
					}else if(TipoDatoBorrar.Kilometraje.getValorEnum().equalsIgnoreCase(datoBorrar)) {
						vehiculoTramiteTraficoVO = servicioVehiculo.getVehiculoTramite(new BigDecimal(codSeleccionados), tramiteTraficoVO.getVehiculo().getIdVehiculo());
						if(vehiculoTramiteTraficoVO != null && vehiculoTramiteTraficoVO.getKilometros() != null) {
							result.setKilometros(vehiculoTramiteTraficoVO.getKilometros().toString());
							vehiculoTramiteTraficoVO.setKilometros(null);
							if (vehiculoTramiteTraficoVO.getVehiculo() != null) {
								vehiculoTramiteTraficoVO.getVehiculo().setFechaLecturaKm(null);
								vehiculoTramiteTraficoVO.getVehiculo().setDoiResponsableKm(null);
							}
							vehiculoTramiteTraficoDao.guardarOActualizar(vehiculoTramiteTraficoVO);
						}else {
							result.setError(Boolean.TRUE);
							result.setMensaje("No hay kilómetros para borrar para el número de expediente " +codSeleccionados);
						}
					}else {
						result.setError(Boolean.TRUE);
						result.setMensaje("El tipo de dato no está relacionado con el tipo de trámite del expediente.");
					}
				}else if(TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite())) {
					if(TipoDatoBorrar.Respuesta_Matriculacion.getValorEnum().equalsIgnoreCase(datoBorrar)) {
						trafMatrVO = tramiteTraficoMatrDao.getTramiteTrafMatr(new BigDecimal(codSeleccionados), Boolean.FALSE,Boolean.FALSE);
						if(trafMatrVO.getRespuesta() != null) {
							result.setRespuesta(trafMatrVO.getRespuesta());
							trafMatrVO.setRespuesta(null);
							tramiteTraficoMatrDao.guardarOActualizar(trafMatrVO);
						}else {
							result.setError(Boolean.TRUE);
							result.setMensaje("No hay respuesta de matriculación para borrar para el numero de expediente " +codSeleccionados);
						}
					}else {
						result.setError(Boolean.TRUE);
						result.setMensaje("El tipo de dato no está relacionado con el tipo de trámite del expediente.");
					}
				}
			}else {
				result.setError(Boolean.TRUE);
				result.setMensaje("En este estado no se puede borrar datos del expediente " + codSeleccionados);
			}
		} catch (Exception e) {
			log.error("Hubo un problema al borrar datos solicitados del expediente, error: ", e, codSeleccionados);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al borrar datos solicitados del expediente " + codSeleccionados);
		}
		return result;
	}
	
	@Override
	@Transactional
	public ResultadoAccionUsuarioBean actualizarDatos(String codSeleccionados,String datoActualizar, String datoNuevo, BigDecimal idUsuario) {
		ResultadoAccionUsuarioBean resultado = new ResultadoAccionUsuarioBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraficoVO = tramiteTraficoDao.getTramite(new BigDecimal(codSeleccionados), Boolean.TRUE);
			if(tramiteTraficoVO!= null) {
				VehiculoVO VehiculoNuevo = servicioVehiculo.getVehiculoVO(new Long(datoNuevo), tramiteTraficoVO.getNumColegiado(), null, null, null, EstadoVehiculo.Activo);
				if(VehiculoNuevo != null) {
					if(TipoDatoActualizar.Id_Vehiculo.getValorEnum().equalsIgnoreCase(datoActualizar)) {
						tramiteTraficoVO.setVehiculo(VehiculoNuevo);
						tramiteTraficoDao.actualizar(tramiteTraficoVO);
					}
				}else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El idVehiculo " + datoNuevo + " no pertenece al colegiado por lo que no se va a actualizar el trámite");
				}
			}
			
		} catch (Exception e) {
			log.error("Hubo un problema al actualizar datos solicitados del expediente, error: ", e, codSeleccionados);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al actualizar datos solicitados del expediente " + codSeleccionados);
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoAccionUsuarioBean actualizarPueblo(String codSeleccionados, String interviniente,String puebloNuevo, BigDecimal idUsuario) {
		ResultadoAccionUsuarioBean resultado = new ResultadoAccionUsuarioBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraficoVO = tramiteTraficoDao.getTramite(new BigDecimal(codSeleccionados), Boolean.TRUE);
			for (IntervinienteTraficoVO intervinienteVO : tramiteTraficoVO.getIntervinienteTraficos()) {
				if(TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite())) {
						if(interviniente.equalsIgnoreCase(intervinienteVO.getTipoIntervinienteVO().getTipoInterviniente())) {
							DireccionVO direccionVO = servicioDireccion.getDireccionVO(intervinienteVO.getIdDireccion());
							String puebloAntiguo = direccionVO.getPueblo();
							direccionVO.setPueblo(puebloNuevo.toUpperCase());
							ResultBean result = servicioDireccion.actualizar(direccionVO);
							if(!result.getError()) {
								MunicipioVO municipioVO = servicioMunicipio.getMunicipio(direccionVO.getIdMunicipio(), direccionVO.getIdProvincia());
								List<LocalidadDgtVO> localidadDgt = servicioLocalidadDgt.getLocalidades(puebloAntiguo, municipioVO.getNombre());
								for (LocalidadDgtVO localidadDgtVO : localidadDgt) {
									localidadDgtVO.setLocalidad(puebloNuevo.toUpperCase());
									ResultBean resultLocalidad = servicioLocalidadDgt.actualizar(localidadDgtVO);
									if(!resultLocalidad.getError()) {
										resultado.setMensaje(puebloAntiguo);
									}else {
										resultado.setError(Boolean.TRUE);
										resultado.setMensaje("Ha ocurrido un error al actualizar en la tabla LocalidadDgt para el expediente: " +codSeleccionados);
									}
								}
							}else {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Ha ocurrido un error al actualizar en la tabla Dirección para el expediente: " +codSeleccionados);
							}
						}
						
				}else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite())) {
						if(interviniente.equalsIgnoreCase(intervinienteVO.getTipoIntervinienteVO().getTipoInterviniente())) {
							DireccionVO direccionVO = servicioDireccion.getDireccionVO(intervinienteVO.getIdDireccion());
							String puebloAntiguo = direccionVO.getPueblo();
							direccionVO.setPueblo(puebloNuevo.toUpperCase());
							ResultBean result = servicioDireccion.actualizar(direccionVO);
							if(!result.getError()) {
								List<UnidadPoblacionalVO> unidadPoblacionalVO = servicioLocalidadDgt.getUnidadesPoblacionales(direccionVO.getIdMunicipio(), direccionVO.getIdProvincia());
								for (UnidadPoblacionalVO unidadPoblacional : unidadPoblacionalVO) {
									if(!"*DISEMINADO*".equalsIgnoreCase(unidadPoblacional.getNucleo()) && unidadPoblacional.getNucleo() != null){
										unidadPoblacional.setNucleo(puebloNuevo.toUpperCase());
										unidadPoblacional.setEntidadSingular(puebloNuevo.toUpperCase());
										unidadPoblacionalDao.actualizar(unidadPoblacional);
									}
								}
								resultado.setMensaje(puebloAntiguo);
							}else {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Ha ocurrido un error al actualizar en la tabla Dirección para el expediente: " +codSeleccionados);
							}
						} 
				}else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Sólo puede modificar el pueblo para Transmisión o Matriculación ");
				}
			}
		} catch (Exception e) {
			log.error("Hubo un problema al actualizar el pueblo del expediente, error: ", e, codSeleccionados);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al actualizar el pueblo del expediente " + codSeleccionados);
		}
		return resultado;
	}
	
	@Override
	public void guardarActionBorrarDatos(String numExpediente, String accion, ResultadoAccionUsuarioBean resultado, String ipAccesso) {
		servicioAccionUsuario.guardarActionBorrarDatos(numExpediente,accion,resultado,ipAccesso);
		
	}

	@Override
	public ResultadoBean gestionDesasignarTasaXMLExpediente(TasaVO tasaEnviadaEnXML, Long idContrato, String tipoTasa, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraficoVO = servicioComunTramiteTrafico.getTramite(tasaEnviadaEnXML.getNumExpediente(), Boolean.FALSE);
				ResultBean resultadoDesasig =servicioTasa.desasignarTasaXml(tasaEnviadaEnXML.getCodigoTasa(),tramiteTraficoVO.getNumExpediente(),new BigDecimal(idUsuario));
				if(!resultadoDesasig.getError()) {
					tramiteTraficoVO.setTasa(null);
					actualizarTramite(tramiteTraficoVO);
					resultado.addListaMensajes(resultadoDesasig.getListaMensajes());
				}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar la tasa: " + tasaEnviadaEnXML.getCodigoTasa() +  ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar la tasa: " + tasaEnviadaEnXML.getCodigoTasa());
		}
		return resultado;
	}
}
	
