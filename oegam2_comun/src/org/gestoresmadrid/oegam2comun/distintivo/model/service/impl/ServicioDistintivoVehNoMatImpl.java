package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioEvolucionVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DuplicadoDstvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioDistintivoVehNoMatImpl implements ServicioDistintivoVehNoMat {

	private static final long serialVersionUID = 6638354209523357550L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioDistintivoVehNoMatImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	VehNoMatOegamDao vehNoMatOegamDao;

	@Autowired
	ServicioEvolucionVehNoMat servicioEvolucionVehNoMat;

	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean guardarImportacionDstv(DuplicadoDstvBean duplicadoDstvBean, ContratoDto contrato, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<VehNoMatOegamVO> listaDuplicadosBBDD = vehNoMatOegamDao.getListaVehNoMatOegamVOPorMatriculaContrato(duplicadoDstvBean.getMatricula(), contrato.getIdContrato().longValue());
			if(listaDuplicadosBBDD == null || listaDuplicadosBBDD.isEmpty()){
				List<TramiteTrafMatrVO> lista = servicioTramiteTraficoMatriculacion.getTramitePorMatriculaContrato(duplicadoDstvBean.getMatricula(), contrato.getIdContrato().longValue());
				
				if (lista != null && !lista.isEmpty()) {
					if (lista.size() > 1) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
					} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(lista.get(0).getEstadoImpDstv())
							&& !EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum().equals(lista.get(0).getEstadoImpDstv())){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La matricula: " + duplicadoDstvBean.getMatricula() + " debera de solicitar su distintivo desde el tramite.");
					} else {
						Date fecha = new Date();
						VehNoMatOegamVO vehNoMatOegamVO = convertirImportBeanToVO(duplicadoDstvBean,contrato, fecha);
						vehNoMatOegamDao.guardar(vehNoMatOegamVO);
						resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(),
								idUsuario, OperacionPrmDstvFicha.CREACION.getValorEnum(), null, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha, null);
					}
				} else {
					Date fecha = new Date();
					VehNoMatOegamVO vehNoMatOegamVO = convertirImportBeanToVO(duplicadoDstvBean,contrato, fecha);
					vehNoMatOegamDao.guardar(vehNoMatOegamVO);
					resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(),
							idUsuario, OperacionPrmDstvFicha.CREACION.getValorEnum(), null, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha, null);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La matricula: " + duplicadoDstvBean.getMatricula() + " no se puede importar porque ya existe y esta sin solicitar su impresion.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de guardar la importacion del duplicado con matricula: " + duplicadoDstvBean.getMatricula() + ", error:",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la importacion del duplicado con matricula: " + duplicadoDstvBean.getMatricula());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private VehNoMatOegamVO convertirImportBeanToVO(DuplicadoDstvBean duplicadoDstvBean, ContratoDto contrato, Date fecha) {
		VehNoMatOegamVO vehNoMatOegamVO = new VehNoMatOegamVO();
		vehNoMatOegamVO.setBastidor(duplicadoDstvBean.getBastidor());
		vehNoMatOegamVO.setMatricula(duplicadoDstvBean.getMatricula());
		if (duplicadoDstvBean.getNive() != null && !duplicadoDstvBean.getNive().isEmpty()) {
			vehNoMatOegamVO.setNive(duplicadoDstvBean.getNive());
		}
		vehNoMatOegamVO.setFechaAlta(fecha);
		vehNoMatOegamVO.setIdContrato(contrato.getIdContrato().longValue());
		vehNoMatOegamVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
		vehNoMatOegamVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
		vehNoMatOegamVO.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
		return vehNoMatOegamVO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsumoMaterialValue> getListaConsumoDstvDuplicadosJefaturaPorDia(String jefatura, Date fecha) {
		return vehNoMatOegamDao.getListaConsumoDstvDuplicadosJefaturaPorDia(jefatura, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Long> getListaIdsContratosConDistintivosDuplicados() {
		try {
			List<Long> listaIdsContratos = vehNoMatOegamDao.getListaIdsContratosConDistintivosDuplicados();
			if (listaIdsContratos != null && !listaIdsContratos.isEmpty()) {
				return listaIdsContratos;
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un erro a la hora de obtener la lista con los contratos que tienen duplicados de distintivos pendientes de imprimir, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos) {
		try {
			return vehNoMatOegamDao.getMaterialesImpresos(docDistintivos);
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de obtener la cantidad de duplicados de distintivos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public int comprobarMatriculaDoc(String matricula) {
		return vehNoMatOegamDao.comprobarExisteMatricula(matricula);
	}

	@Override
	@Transactional(readOnly = true)
	public String getIdDocPorMatricula(String matricula) {
		try {
			VehNoMatOegamVO vehNoMatOegamVO = vehNoMatOegamDao.getVehNoMatOegamVOPorMatricula(matricula);
			if (vehNoMatOegamVO != null && vehNoMatOegamVO.getDocDistintivoVO() != null && vehNoMatOegamVO.getDocDistintivoVO().getDocIdPerm() != null && !vehNoMatOegamVO.getDocDistintivoVO()
					.getDocIdPerm().isEmpty()) {
				return vehNoMatOegamVO.getDocDistintivoVO().getDocIdPerm();
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de obtener el doc_id, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoNoMatriculadoOegamDto> getListaVehiculoDistintivosDtoPorDocId(Long idDoc) {
		try {
			List<VehNoMatOegamVO> listaBBDD = getListaVehiculoDistintivosPorDocId(idDoc);
			if (listaBBDD != null && !listaBBDD.isEmpty()) {
				return conversor.transform(listaBBDD, VehiculoNoMatriculadoOegamDto.class);
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener el listado de duplicados por docId, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountNumVehNotMatOegamDstv(Long idDocPermDistItv) {
		return vehNoMatOegamDao.getCountNumVehNotMatOegamDstv(idDocPermDistItv);
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarEstadosImpresionDstv(Long idDocPermDistItv, EstadoPermisoDistintivoItv estado, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<VehNoMatOegamVO> listaBBDD = vehNoMatOegamDao.getListaVehiculoPorDocId(idDocPermDistItv);
			if (listaBBDD != null && !listaBBDD.isEmpty()) {
				Date fecha = new Date();
				for (VehNoMatOegamVO vehNoMatOegamVO : listaBBDD) {
					vehNoMatOegamVO.setEstadoImpresion(estado.getValorEnum());
					vehNoMatOegamVO.setFechaImpresion(fecha);
					vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
					ResultadoDistintivoDgtBean resultDstv = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario,
							OperacionPrmDstvFicha.IMPRESION.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), estado.getValorEnum(), fecha, null);
					if (resultDstv.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultDstv.getMensaje());
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de cambiar el estado de los duplicados de distinttivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de los duplicados de distinttivos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void actualizarEstadoImpresionDstv(Long idVehNotMat, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, Date fecha, String docId) {
		VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehNotMat);
		if (vehNoMatOegamVO != null) {
			vehNoMatOegamVO.setEstadoImpresion(estadoNuevo);
			vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
			servicioEvolucionVehNoMat.guardarEvolucionVehiculo(idVehNotMat, null, idUsuario, OperacionPrmDstvFicha.MODIFICACION.getValorEnum(), estadoAnt, estadoNuevo, fecha, docId);
		}
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean indicarDocIdVehiculos(List<Long> listaDuplicados, Long idDocPermDstvEitv, BigDecimal idUsuario, Date fecha, String docId) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			for (Long idVehiculoNotMatOegam : listaDuplicados) {
				VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehiculoNotMatOegam);
				vehNoMatOegamVO.setDocDistintivo(idDocPermDstvEitv);
				vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
				servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, 
						OperacionPrmDstvFicha.MODIFICACION.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), 
						EstadoPermisoDistintivoItv.ORDENADO_DOC.getValorEnum(),fecha, docId);
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de indicar el doId a los vehiculos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de indicar el doId a los vehiculos.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehNoMatOegamVO> getListaVehiculoDistintivosPorDocId(Long idDocPermDistItv) {
		try {
			return vehNoMatOegamDao.getListaVehiculoPorDocId(idDocPermDistItv);
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener la lista de vehiculos de los distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean guardarVehNoMatOegam(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			resultado = validarDatosVehiculoGuardados(vehiculoNoMatriculadoDto);
			if (!resultado.getError()) {
				convertirDatos(vehiculoNoMatriculadoDto);
				String estadoAnt = null;
				if (vehiculoNoMatriculadoDto.getId() == null) {
					if (vehiculoNoMatriculadoDto.getNumColegiado() == null || vehiculoNoMatriculadoDto.getNumColegiado().isEmpty()) {
						ContratoDto contrato = servicioContrato.getContratoDto(vehiculoNoMatriculadoDto.getContrato().getIdContrato());
						vehiculoNoMatriculadoDto.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
					}
				} else {
					estadoAnt = vehiculoNoMatriculadoDto.getEstadoSolicitud();
				}
				Date fecha = new Date();
				VehNoMatOegamVO vehNoMatOegamVO = conversor.transform(vehiculoNoMatriculadoDto, VehNoMatOegamVO.class);
				vehNoMatOegamVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				vehNoMatOegamVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				vehNoMatOegamVO.setPrimeraImpresion("N");
				vehNoMatOegamVO.setFechaAlta(fecha);
				String tipoOperacion = "";
				if (vehNoMatOegamVO.getId() == null) {
					tipoOperacion = OperacionPrmDstvFicha.CREACION.getValorEnum();
				} else {
					tipoOperacion = OperacionPrmDstvFicha.MODIFICACION.getValorEnum();
				}
				vehNoMatOegamVO.setMatricula(vehNoMatOegamVO.getMatricula().toUpperCase());
				vehNoMatOegamVO.setBastidor(vehNoMatOegamVO.getBastidor().toUpperCase());
				if (vehNoMatOegamVO.getNive() != null && !vehNoMatOegamVO.getNive().isEmpty()) {
					vehNoMatOegamVO.setNive(vehNoMatOegamVO.getNive().toUpperCase());
				}
				vehNoMatOegamDao.guardarOActualizar(vehNoMatOegamVO);
				resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, tipoOperacion, estadoAnt,
						EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha, null);
				if (!resultado.getError()) {
					resultado.setVehiculoNoMatrOegam(conversor.transform(vehNoMatOegamVO, VehiculoNoMatriculadoOegamDto.class));
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de guardar el vehiculo no matriculado en OEGAM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el vehiculo no matriculado en OEGAM.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void convertirDatos(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto) {
		vehiculoNoMatriculadoDto.setBastidor(vehiculoNoMatriculadoDto.getBastidor().trim().toUpperCase());
		vehiculoNoMatriculadoDto.setMatricula(vehiculoNoMatriculadoDto.getMatricula().trim().toUpperCase());
		if (vehiculoNoMatriculadoDto.getNive() != null && !vehiculoNoMatriculadoDto.getNive().isEmpty()) {
			vehiculoNoMatriculadoDto.setNive(vehiculoNoMatriculadoDto.getNive().trim().toUpperCase());
		}
	}

	private ResultadoDistintivoDgtBean validarDatosVehiculoGuardados(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (vehiculoNoMatriculadoDto == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar los datos para poder guardar el vehículo.");
		} else if (vehiculoNoMatriculadoDto.getMatricula() == null || vehiculoNoMatriculadoDto.getMatricula().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede guardar el vehículo. Es obligatorio rellenar la matrícula.");
		} else if (vehiculoNoMatriculadoDto.getBastidor() == null || vehiculoNoMatriculadoDto.getBastidor().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede guardar el vehículo. Es obligatorio rellenar el bastidor.");
		} else if (vehiculoNoMatriculadoDto.getContrato() == null || vehiculoNoMatriculadoDto.getContrato().getIdContrato() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede guardar el vehículo. Es obligatorio indicar el contrato.");
		} else if (!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vehiculoNoMatriculadoDto.getEstadoSolicitud()) && (vehiculoNoMatriculadoDto.getEstadoSolicitud() != null
				&& !vehiculoNoMatriculadoDto.getEstadoSolicitud().isEmpty())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El vehículo debe de estar en estado Iniciado para poder actualizarse.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDistintivoDgtBean getVehNoMatrPantalla(Long idVehNotMatOegam) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (idVehNotMatOegam != null) {
				VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehNotMatOegam);
				if (vehNoMatOegamVO != null) {
					resultado.setVehiculoNoMatrOegam(conversor.transform(vehNoMatOegamVO, VehiculoNoMatriculadoOegamDto.class));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para obtener el detalle del vehículo.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar el id de algún vehículo para obtener su detalle.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener un vehículo no matriculado en OEGAM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener un vehículo no matriculado en OEGAM.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public VehNoMatOegamVO getVehNoMatOegamVO(Long idVehNotMatOegam) {
		try {
			return vehNoMatOegamDao.getVehNoMatrOegamPorId(idVehNotMatOegam);
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener un vehículo no matriculado en OEGAM, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDistintivoDgtBean guardarPdfDstv(byte[] distintivoPdf, VehNoMatOegamVO vehNoMatOegamVO) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.DISTINTIVOS);
			fichero.setSubTipo(null);
			fichero.setFecha(utilesFecha.getFechaConDate(vehNoMatOegamVO.getFechaAlta()));
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			fichero.setFicheroByte(distintivoPdf);
			fichero.setNombreDocumento(TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + vehNoMatOegamVO.getMatricula().trim() + "_" + vehNoMatOegamVO.getId());
			gestorDocumentos.guardarByte(fichero);
		} catch (OegamExcepcion e) {
			LOG.error("Ha sucedido un error a la hora de guardar el pdf con el distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el pdf con el distintivo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean actualizarRecepcionDstv(VehNoMatOegamVO vehNoMatOegamVO, Boolean tieneDstv, String tipoDistintivo, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			String estadoNuevo = "";
			Date fecha = new Date();
			if (tieneDstv) {
				estadoNuevo = EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum();
				vehNoMatOegamVO.setTipoDistintivo(tipoDistintivo);
			} else {
				estadoNuevo = EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum();
				vehNoMatOegamVO.setRespuestaSol("No se ha encontrado distintinvo asociado a la matricula.");
			}
			vehNoMatOegamVO.setFechaSolicitud(fecha);
			vehNoMatOegamVO.setEstadoSolicitud(estadoNuevo);
			vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
			resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, OperacionPrmDstvFicha.DOC_RECIBIDA.getValorEnum(),
					EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), estadoNuevo, fecha, null);
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de actualizar el vehiculo con los datos del distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el vehiculo con los datos del distintivo.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public void actualizarEstadoProceso(Long idVehNotMatOegam, EstadoPermisoDistintivoItv estadoNuevo, String respuesta, BigDecimal idUsuario) {
		Boolean error = Boolean.FALSE;
		try {
			if (idVehNotMatOegam != null) {
				VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehNotMatOegam);
				if (vehNoMatOegamVO != null) {
					Date fecha = new Date();
					vehNoMatOegamVO.setEstadoSolicitud(estadoNuevo.getValorEnum());
					vehNoMatOegamVO.setRespuestaSol(respuesta);
					vehNoMatOegamVO.setFechaSolicitud(fecha);
					vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
					ResultadoDistintivoDgtBean resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(idVehNotMatOegam, vehNoMatOegamVO.getMatricula(), idUsuario,
							OperacionPrmDstvFicha.DOC_RECIBIDA.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), estadoNuevo.getValorEnum(), fecha, null);
					if (resultado.getError()) {
						error = Boolean.TRUE;
					}
				} else {
					LOG.error("No se ha encontrado ningún vehiculo con el id: " + idVehNotMatOegam);
				}
			} else {
				LOG.error("Debe de indicar un idVehNotMatOegam para poder actualizar el estado de la solicitud de distintivo");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedidio un error a la hora de actualizar el estado del vehiculo desde el proceso, error: ", e);
			error = Boolean.TRUE;
		}
		if (error) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(vehiculoNoMatriculadoDto.getId());
			if (vehNoMatOegamVO != null) {
				if (EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vehNoMatOegamVO.getEstadoSolicitud()) || EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(vehNoMatOegamVO
						.getEstadoSolicitud())) {
					Date fecha = new Date();
					String estadoAnt = vehNoMatOegamVO.getEstadoSolicitud();
					vehNoMatOegamVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
					vehNoMatOegamVO.setFechaSolicitud(fecha);
					vehNoMatOegamDao.guardarOActualizar(vehNoMatOegamVO);
					resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), null, idUsuario, OperacionPrmDstvFicha.SOLICITUD.getValorEnum(), estadoAnt,
							EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), fecha, null);
					if (!resultado.getError()) {
						ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.DSTV.getNombreEnum(), ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV + "_" + vehNoMatOegamVO.getId(),
								gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_2), TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum(), vehNoMatOegamVO.getId().toString(), idUsuario, null,
								new BigDecimal(vehNoMatOegamVO.getIdContrato()));
						if (resultBean.getError()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultBean.getMensaje());
						} else {
							resultado.setMensaje("La solicitud del distintivo para la matricula: " + vehNoMatOegamVO.getMatricula() + " se ha generado correctamente.");
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para poder solicitar el distintivo para la matricula: " + vehNoMatOegamVO.getMatricula() + " el estado de la solicitud debera de ser Iniciado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para poder solicitar el distintivo de la matricula: " + vehiculoNoMatriculadoDto.getMatricula());
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de solicitar el distintivo para la matricula: " + vehiculoNoMatriculadoDto.getMatricula() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar el distintivo para la matricula: " + vehiculoNoMatriculadoDto.getMatricula());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarImpresionDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatrOegamDto, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(vehiculoNoMatrOegamDto.getId());
			resultado = validarDatosSolicitudGenVehNotMatOegam(vehNoMatOegamVO, vehiculoNoMatrOegamDto.getId());
			if (!resultado.getError()) {
				resultado = comprobarPrimeraImpresion(vehNoMatOegamVO.getMatricula(), vehNoMatOegamVO.getIdContrato(), vehNoMatOegamVO.getBastidor());
				if (!resultado.getError()) {
					Date fecha = new Date();
					String estadoNuevo = null;
					String mensaje = null;
					if (resultado.getNoEsGenerable()) {
						estadoNuevo = EstadoPermisoDistintivoItv.NO_GENERABLE.getValorEnum();
						mensaje = "Es un distintivo no generable desde la consulta de duplicados. Para la impresión de la matrícula " + vehNoMatOegamVO.getMatricula()
								+ ", deberá realizarse desde la gestión de distintivos, consultando su propio trámite, realizado con anterioridad, en OEGAM.";
					} else if (resultado.getEsPrimeraImp()) {
						estadoNuevo = EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum();
						mensaje = "El distintivo con matrícula: " + vehNoMatOegamVO.getMatricula() + " se facturará a final de mes, como una impresión normal de distintivos y"
								+ " no como duplicado, ya que se trata de la primera impresión de dicha matrícula en la plataforma.";
						vehNoMatOegamVO.setPrimeraImpresion("S");
					} else {
						estadoNuevo = EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum();
						mensaje = "Solicitud de impresión para la matrícula: " + vehNoMatOegamVO.getMatricula() + ", generada correctamente.";
						vehNoMatOegamVO.setPrimeraImpresion("N");
					}
					vehNoMatOegamVO.setEstadoImpresion(estadoNuevo);
					vehNoMatOegamDao.guardarOActualizar(vehNoMatOegamVO);
					resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, OperacionPrmDstvFicha.SOL_IMPRESION
							.getValorEnum(), EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), estadoNuevo, fecha, null);
					if (!resultado.getError()) {
						resultado.setMensaje(mensaje);
					}
				}
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de solicitar la impresion del distintivo con matricula: " + vehiculoNoMatrOegamDto.getMatricula() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion del distintivo con matricula: " + vehiculoNoMatrOegamDto.getMatricula());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean comprobarPrimeraImpresion(String matricula, Long idContrato, String bastidor) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramites = servicioTramiteTraficoMatriculacion.getListaTramitesPorMatricula(matricula);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramite : listaTramites) {
					if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(tramite.getEstadoImpDstv())
							|| EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum().equals(tramite.getEstadoImpDstv())) {
						resultado.setEsPrimeraImp(Boolean.FALSE);
						resultado.setNoEsGenerable(Boolean.FALSE);
						break;
					} else {
						if (tramite.getContrato().getIdContrato() == idContrato) {
							resultado.setNoEsGenerable(Boolean.TRUE);
						} else {
							resultado.setEsPrimeraImp(Boolean.TRUE);
						}
					}
				}
				if (resultado.getEsPrimeraImp() || resultado.getNoEsGenerable()) {
					List<VehNoMatOegamVO> listaDuplicados = vehNoMatOegamDao.getListaDuplicadosPorMatricula(matricula);
					if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
						resultado.setEsPrimeraImp(Boolean.FALSE);
						resultado.setNoEsGenerable(Boolean.FALSE);
					}
				}
			} else {
				List<VehNoMatOegamVO> listaDuplicados = vehNoMatOegamDao.getListaDuplicadosPorMatricula(matricula);
				if (listaDuplicados == null || listaDuplicados.isEmpty()) {
					resultado.setEsPrimeraImp(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de comprobar las impresiones de la matricula:" + matricula + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar las impresiones de la matricula: ");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean autorizarImpresionDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(vehiculoNoMatriculadoDto.getId());
			resultado = validarDatosAutorizacionVehNotMatOegam(vehNoMatOegamVO, vehiculoNoMatriculadoDto.getId());
			if (!resultado.getError()) {
				Date fecha = new Date();
				vehNoMatOegamVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
				vehNoMatOegamVO.setPrimeraImpresion("S");
				vehNoMatOegamDao.guardarOActualizar(vehNoMatOegamVO);
				resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, OperacionPrmDstvFicha.SOL_IMPRESION.getValorEnum(),
						EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), fecha, null);
				if (!resultado.getError()) {
					resultado.setMensaje("Solicitud de impresion para la matricula: " + vehNoMatOegamVO.getMatricula() + ", generada correctamente");
				}
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de autorizar la impresion del distintivo con matricula: " + vehiculoNoMatriculadoDto.getMatricula() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de autorizar la impresion del distintivo con matricula: " + vehiculoNoMatriculadoDto.getMatricula());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarDatosAutorizacionVehNotMatOegam(VehNoMatOegamVO vehNoMatOegamVO, Long idVehNotMatOegam) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (vehNoMatOegamVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos para el id: " + idVehNotMatOegam);
		} else if (!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(vehNoMatOegamVO.getEstadoSolicitud())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para poder generar el distintivo de la matricula: " + vehNoMatOegamVO.getMatricula() + " debera de solicitarlo con anterioridad.");
		} else if (!EstadoPermisoDistintivoItv.PDTE_CONF_COLEGIADO.getValorEnum().equals(vehNoMatOegamVO.getEstadoImpresion()) && vehNoMatOegamVO.getEstadoImpresion() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para poder confirmar el distintivo de la matricula: " + vehNoMatOegamVO.getMatricula()
					+ " debera de estar el estado de la peticion de impresión en Pdte. Confirmación.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] idsVehsNoMatOegam, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<VehNoMatOegamVO> listaVehiculos = vehNoMatOegamDao.getListaVehiculosPorListaIds(utiles.convertirStringArrayToLongArray(idsVehsNoMatOegam));
			resultado = validarVehiculosNotMatOegamGeneracion(listaVehiculos);
			if (!resultado.getError()) {
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					resultado.getResumen().addListaErrores(resultado.getListaMensajes());
					resultado.getResumen().addListaNumErrores(resultado.getListaMensajes().size());
				}
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaCeroVNMO(), TipoDistintivo.CERO, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaEcoVNMO(), TipoDistintivo.ECO, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaCVNMO(), TipoDistintivo.C, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaBVNMO(), TipoDistintivo.B, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaCeroMotosVNMO(), TipoDistintivo.CEROMT, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaEcoMotosVNMO(), TipoDistintivo.ECOMT, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaCMotosVNMO(), TipoDistintivo.CMT, idUsuario);
				generarDocDistintivoVehNotMatOegam(resultado, resultado.getListaDemandaBMotosVNMO(), TipoDistintivo.BMT, idUsuario);
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de generar los distintivos de las matriculas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los distintivos de las matriculas seleccionadas.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public void generarDocDistintivoVehNotMatOegam(ResultadoDistintivoDgtBean resultado, List<VehNoMatOegamVO> listaVehNotMatOegam, TipoDistintivo tipoDistintivo, BigDecimal idUsuario) {
		try {
			if (listaVehNotMatOegam != null && !listaVehNotMatOegam.isEmpty()) {
				ResultadoPermisoDistintivoItvBean resultGenDoc = servicioDocPrmDstvFicha.generarDoc(idUsuario,
						new Date(), TipoDocumentoImprimirEnum.DISTINTIVO, tipoDistintivo,
						listaVehNotMatOegam.get(0).getContrato().getIdContrato().longValue(),
						listaVehNotMatOegam.get(0).getContrato().getJefaturaTrafico().getJefaturaProvincial(),
						Boolean.TRUE);
				if (!resultGenDoc.getError()) {
					Long idDoc = resultGenDoc.getIdDocPermDstvEitv();
					String docId = resultGenDoc.getDocId();
					for (VehNoMatOegamVO vehiculo : listaVehNotMatOegam) {
						Date fecha = new Date();
						vehiculo.setDocDistintivo(idDoc);
						vehiculo.setEstadoImpresion(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						vehNoMatOegamDao.guardarOActualizar(vehiculo);
						ResultadoDistintivoDgtBean resultEvolucion = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehiculo.getId(), null, idUsuario, OperacionPrmDstvFicha.SOL_IMPRESION_DMD
								.getValorEnum(), EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), fecha, docId);
						if (resultEvolucion.getError()) {
							resultado.getResumen().addListaMensajeError(resultEvolucion.getMensaje());
							resultado.getResumen().addListaNumErrores(resultEvolucion.getListaMensajes().size());
						}
					}
					ResultadoDistintivoDgtBean resultPeticion = generarPeticionImpresionDistintivo(idDoc, idUsuario, listaVehNotMatOegam.get(0).getContrato().getIdContrato(), tipoDistintivo
							.getNombreEnum());
					if (!resultPeticion.getError()) {
						resultado.getResumen().addListaMensajeOk("Peticion de Impresión generada para el tipo de distintivo " + tipoDistintivo.getNombreEnum() + " con docId: " + idDoc);
						resultado.getResumen().addNumOk();
					} else {
						resultado.getResumen().addListaMensajeError(resultPeticion.getMensaje());
						resultado.getResumen().addNumError();
					}
					/*
					 * if(!resultado.getError()){ resultado = servicioDocPrmDstvFicha.generarDocGestoriaDistintivo(listaTramites, tipoDistintivo,docId,new Date(),esMoto); if(!resultado.getError()){ resultado = servicioDocPrmDstvFicha.generarDocJefaturaDistintivo(listaTramites, tipoDistintivo,
					 * docId,new Date(), esMoto); } }
					 */
				} else {
					resultado.getResumen().addListaMensajeError(resultGenDoc.getMensaje());
					resultado.getResumen().addNumError();
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error al generar los distintivos del Tipo: " + tipoDistintivo.getNombreEnum() + ", error: ", e);
			resultado.getResumen().addNumError();
			resultado.getResumen().addListaMensajeError("Ha sucedido un error al generar los distintivos del Tipo: " + tipoDistintivo.getNombreEnum());
		}
	}

	private ResultadoDistintivoDgtBean generarPeticionImpresionDistintivo(Long idDoc, BigDecimal idUsuario, Long idContrato, String tipoDistintivo) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMP_DSTV.getNombreEnum(),
					ServicioDistintivoDgt.SOLICITUD_DUPLICADO_DSTV + "_" + idDoc.toString(),
					gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_2),
					TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum(), idDoc.toString(), idUsuario, null,
					new BigDecimal(idContrato));
			if (resultBean.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			LOG.error("Ha sucedido un error a la hora encolar la peticion de impresion de los distintivos del tipo: " + tipoDistintivo + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de los distintivos del tipo: " + tipoDistintivo);
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarVehiculosNotMatOegamGeneracion(List<VehNoMatOegamVO> listaVehiculos) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (listaVehiculos != null && !listaVehiculos.isEmpty()) {
			for (VehNoMatOegamVO vehiculo : listaVehiculos) {
				if (!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(vehiculo.getEstadoSolicitud())) {
					resultado.addListaMensaje("La matricula: " + vehiculo.getMatricula() + " no se puede generar porque el estado del distintivo no es Documentacion Recibida.");
				} else if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(vehiculo.getEstadoImpresion())) {
					resultado.addListaMensaje("La matricula: " + vehiculo.getMatricula() + " no se puede generar porque el distintivo ya se ha impreso.");
				} else if (vehiculo.getTipoDistintivo() == null || vehiculo.getTipoDistintivo().isEmpty()) {
					resultado.addListaMensaje("La matricula: " + vehiculo.getMatricula() + " no se puede generar porque no se reconoce el tipo de distintivo.");
				} else {
					if (TipoDistintivo.ECO.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaEcoVNMO(vehiculo);
					} else if (TipoDistintivo.B.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaBVNMO(vehiculo);
					} else if (TipoDistintivo.C.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaCVNMO(vehiculo);
					} else if (TipoDistintivo.CERO.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaCeroVNMO(vehiculo);
					} else if (TipoDistintivo.ECOMT.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaEcoMotosVNMO(vehiculo);
					} else if (TipoDistintivo.BMT.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaBMotosVNMO(vehiculo);
					} else if (TipoDistintivo.CMT.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaCMotosVNMO(vehiculo);
					} else if (TipoDistintivo.CEROMT.getValorEnum().equals(vehiculo.getTipoDistintivo())) {
						resultado.addListaDemandaCeroMotosVNMO(vehiculo);
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos en la BBDD para los ids seleccionados.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarDatosSolicitudGenVehNotMatOegam(VehNoMatOegamVO vehNoMatOegamVO, Long idVehNotMatOegam) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (vehNoMatOegamVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos para el id: " + idVehNotMatOegam);
		} else if (!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(vehNoMatOegamVO.getEstadoSolicitud())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para poder generar el distintivo de la matricula: " + vehNoMatOegamVO.getMatricula() + " debera de solicitarlo con anterioridad.");
		} else if (!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vehNoMatOegamVO.getEstadoImpresion()) && vehNoMatOegamVO.getEstadoImpresion() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para poder generar el distintivo de la matricula: " + vehNoMatOegamVO.getMatricula() + " debera de estar el estado de la peticion de impresión en Iniciado.");
		} else {
			VehNoMatOegamVO vehDup = vehNoMatOegamDao.getVehNoMatOegamVOPorMatriculaColegiadoPendiente(vehNoMatOegamVO.getMatricula(), vehNoMatOegamVO.getIdContrato());
			if (vehDup != null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ya tiene un distintivo pendiente de generar para la matricula: " + vehNoMatOegamVO.getMatricula());
			} else {
				List<TramiteTrafMatrVO> lista = servicioTramiteTraficoMatriculacion.getTramitePorMatriculaContrato(vehNoMatOegamVO.getMatricula(), vehNoMatOegamVO.getIdContrato());
				if (lista != null && !lista.isEmpty()) {
					if (lista.size() > 1) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
					} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(lista.get(0).getEstadoImpDstv())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Para poder solicitar la impresion de la matricula: " + vehNoMatOegamVO.getMatricula()
								+ " debera dirigirse a la gestion de distintivos y realizar la impresion desde el tramite.");
					}
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean revertirDistintivo(Long idVehNotMatOegam, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehNotMatOegam);
			resultado = validarVehiculoRevertirDoc(vehNoMatOegamVO, idVehNotMatOegam);
			if (!resultado.getError()) {
				Date fecha = new Date();
				String estadoAnt = vehNoMatOegamVO.getEstadoImpresion();
				vehNoMatOegamVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				vehNoMatOegamVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				vehNoMatOegamVO.setDocDistintivo(null);
				vehNoMatOegamVO.setDocDistintivoVO(null);
				vehNoMatOegamVO.setFechaImpresion(null);
				vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
				resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, OperacionPrmDstvFicha.REVERTIR.getValorEnum(),
						estadoAnt, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha, null);
				if (!resultado.getError()) {
					resultado.setMensaje("La matricula: " + vehNoMatOegamVO.getMatricula() + " se ha revertir correctamente.");
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de revertir el distintivo para el id: " + idVehNotMatOegam + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir el distintivo para el id: " + idVehNotMatOegam);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarVehiculoRevertirDoc(VehNoMatOegamVO vehNoMatOegamVO, Long idVehNotMatOegam) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (vehNoMatOegamVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el id:" + idVehNotMatOegam);
		} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(vehNoMatOegamVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el distintivo para la matricula" + vehNoMatOegamVO.getMatricula() + ", porque no se encuentra impreso todavia.");
		} else if (vehNoMatOegamVO.getDocDistintivo() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el distintivo para la matricula" + vehNoMatOegamVO.getMatricula() + ", porque no se encuentran datos de su documento.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean cambiarEstadoDstv(Long idVehNotMatOegam, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = getVehNoMatOegamVO(idVehNotMatOegam);
			if (vehNoMatOegamVO != null) {
				Date fecha = new Date();
				String estadoAnt = vehNoMatOegamVO.getEstadoSolicitud();
				vehNoMatOegamVO.setEstadoSolicitud(estadoNuevo);
				vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
				resultado = servicioEvolucionVehNoMat.guardarEvolucionVehiculo(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula(), idUsuario, OperacionPrmDstvFicha.CAMBIO_ESTADO.getValorEnum(),
						estadoAnt, estadoNuevo, fecha, null);
				if (!resultado.getError()) {
					resultado.setMensaje("La matricula: " + vehNoMatOegamVO.getMatricula() + " se ha cambiado el estado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos del vehiculo para cambiar el estado.");
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de cambiar el estado a la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado a la solicitud.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehNoMatOegamVO> getListaVehiculosPdteImpresionPorContrato(Long idContrato) {
		try {
			return vehNoMatOegamDao.getListaVehiculosPdteImpresionPorContrato(idContrato);
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener la lista de vehiculos con distintivos pendientes de generar para el idContrato: " + idContrato + ", error: ", e);
		}
		return Collections.emptyList();
	}

}