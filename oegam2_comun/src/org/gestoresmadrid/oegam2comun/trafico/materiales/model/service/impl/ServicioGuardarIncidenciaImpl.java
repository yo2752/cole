package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.AutorDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaIntervaloDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaSerialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaIntervaloVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaIntervaloVOId;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVOId;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaAutor;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidencias;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidenciasSerial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaUsuarioConsejo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCrearSolicitudesPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarIncidencia;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaIntervalosDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaSerialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarIncidenciaImpl implements ServicioGuardarIncidencia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1926153807281718204L;

	private static final String INCIDENCIA_OK = "Incidencia guardada correctamente";
	private static final String INCIDENCIA_KO = "Incidencia no guardada";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarPedidoImpl.class);

	@Resource AutorDao               autorDao;
	@Resource IncidenciaDao          incidenciaDao;
	@Resource JefaturaTraficoDao     jefaturaTraficoDao;
	@Resource IncidenciaSerialDao    incidenciaSerialDao;
	@Resource IncidenciaIntervaloDao incidenciaIntervaloDao;
	
	@Autowired ServicioConsultaIncidencias       servicioConsultaIncidencias;
	@Autowired ServicioCrearSolicitudesPedido    servicioCrearSolicitudesPedido;
	@Autowired ServicioConsultaAutor             servicioConsultaAutor;
	@Autowired ServicioConsultaUsuarioConsejo    servicioConsultaUsuarioConsejo;
	@Autowired ServicioConsultaMateriales	     servicioConsultaMateriales;	
	@Autowired ServicioConsultaPedidos		     servicioConsutlaPedidos;
	@Autowired ServicioConsultaIncidenciasSerial servicioConsultaIncidenciasSerial;
	
	@Override
	@Transactional
	public ResultBean salvarIncidencia(IncidenciaDto incidenciaDto, String accion) {
		ResultBean result = new ResultBean();
		
		try {
			IncidenciaVO incidenciaVO = servicioConsultaIncidencias.getVOFromDto(incidenciaDto);
			
			AutorVO autorVO = servicioConsultaUsuarioConsejo.obtenerAutorFromUsuarioColegio();
			incidenciaVO.setAutorVO(autorVO);
			
			JefaturaTraficoVO jefaturaTraficoVO = jefaturaTraficoDao.getJefatura(incidenciaVO.getJefaturaProvincial().getJefaturaProvincial());
			incidenciaVO.setJefaturaProvincial(jefaturaTraficoVO);
			
			MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(incidenciaVO.getMaterialVO().getMaterialId());
			incidenciaVO.setMaterialVO(materialVO);
			
			incidenciaVO = guardarIncidencia(incidenciaVO);
			
			incidenciaDto.setIncidenciaId(incidenciaVO.getIncidenciaId());
			if (incidenciaDto.getJefaturaProvincial() == null) {
				incidenciaDto.setJefaturaProvincial(jefaturaTraficoVO.getJefaturaProvincial());
			} else {
				incidenciaDto.setJefaturaDescripcion(jefaturaTraficoVO.getDescripcion());
			}
			
			if (incidenciaDto.getMateriaId() == null) {
				incidenciaDto.setMateriaId(materialVO.getMaterialId());
			} else {
				incidenciaDto.setMateriaNombre(materialVO.getName());
			}

			result.setMensaje(INCIDENCIA_OK);
			result.addAttachment("incidenciaDto", incidenciaDto);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(INCIDENCIA_KO);
		}
		
		return result;

	}
	
	@Override
	@Transactional
	public ResultBean modificarIncidencia(IncidenciaDto incidenciaDto, String accion) {
		ResultBean result = new ResultBean();
		
		try {
			IncidenciaVO incidenciaVO = servicioConsultaIncidencias.obtenerIncidenciaVO(incidenciaDto.getIncidenciaId());
			
			if (!incidenciaDto.getJefaturaProvincial().equals(incidenciaVO.getJefaturaProvincial().getJefaturaProvincial())) {
				JefaturaTraficoVO jefaturaTraficoVO = jefaturaTraficoDao.getJefatura(incidenciaDto.getJefaturaProvincial());
				incidenciaVO.setJefaturaProvincial(jefaturaTraficoVO);
			}
			
			if (!incidenciaDto.getMateriaId().equals(incidenciaVO.getMaterialVO().getMaterialId())) {
				MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(incidenciaDto.getMateriaId());
				incidenciaVO.setMaterialVO(materialVO);
			}
			
			if (!incidenciaDto.getObservaciones().equals(incidenciaVO.getObservaciones())) {
				incidenciaVO.setObservaciones(incidenciaDto.getObservaciones());
			}
			
			if (!incidenciaDto.getTipo().equals(incidenciaVO.getTipo())) {
				incidenciaVO.setTipo( incidenciaDto.getTipo());
			}

			List<IncidenciaSerialVO> listaSerialVO = modificarListaSeriales(incidenciaDto.getListaSerial(), incidenciaVO.getListaSeriales());
			incidenciaVO.getListaSeriales().clear();
			incidenciaVO.getListaSeriales().addAll(listaSerialVO);
			
			List<IncidenciaIntervaloVO> listaIntervalosVO = modificarListaIntervalos(incidenciaDto.getListaIntervalos(), incidenciaVO.getListaIntervalos());
			incidenciaVO.getListaIntervalos().clear();
			incidenciaVO.getListaIntervalos().addAll(listaIntervalosVO);

			if (ServicioGuardarIncidencia.ACCION_CONFIRMAR.equals(accion)) {
				result = servicioCrearSolicitudesPedido.crearSolicitudIncidencia(incidenciaVO.getIncidenciaId());
				
				EstadoIncidencia estado = EstadoIncidencia.Activo;
				incidenciaVO.setEstado(Long.valueOf(estado.getValorEnum()));
				incidenciaVO = guardarIncidencia(incidenciaVO);
			} else {
				incidenciaVO = guardarIncidencia(incidenciaVO);
			}

			result.setMensaje(INCIDENCIA_OK);
			result.addAttachment("incidenciaDto", incidenciaDto);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(INCIDENCIA_KO);
		}
		
		return result;

	}
	
	private List<IncidenciaSerialVO> modificarListaSeriales(List<IncidenciaSerialDto> listaSerialDto, List<IncidenciaSerialVO> listaSerialesVO) {
		
		List<IncidenciaSerialVO> guardarSeriales = new ArrayList<IncidenciaSerialVO>();
		
		//Seriales a eliminar del VO
		for(IncidenciaSerialVO itemVO: listaSerialesVO) {
			boolean existe = false;
			for(IncidenciaSerialDto itemDto: listaSerialDto) {
				if (itemVO.getPk().getNumSerie().equals(itemDto.getNumSerie())) {
					existe = true;
					guardarSeriales.add(itemVO);
					break;
				}
			}
			if (!existe) {
				incidenciaSerialDao.borrar(itemVO);
			}
		}
		
		//Nuevas altas
		for(IncidenciaSerialDto itemDto: listaSerialDto) {
			boolean existe = false;
			for(IncidenciaSerialVO itemVO: listaSerialesVO) {
				if (itemDto.getNumSerie().equals(itemVO.getPk().getNumSerie())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				IncidenciaSerialVO serialVO = new IncidenciaSerialVO();
				IncidenciaSerialVOId pk = new IncidenciaSerialVOId();
				pk.setNumSerie(itemDto.getNumSerie());
				serialVO.setPk(pk);
				serialVO = incidenciaSerialDao.guardarOActualizar(serialVO);
				guardarSeriales.add(serialVO);
			}
		}
		
		return guardarSeriales;
	}

	private List<IncidenciaIntervaloVO> modificarListaIntervalos(List<IncidenciaIntervalosDto> listaIntervalosDto,	List<IncidenciaIntervaloVO> listaIntervalosVO) {
		// TODO Auto-generated method stub
		List<IncidenciaIntervaloVO> guardarIntervalos = new ArrayList<IncidenciaIntervaloVO>();
		
		//Intervalos a eliminar del VO
		for(IncidenciaIntervaloVO itemVO: listaIntervalosVO){
			boolean existe = false;
			for (IncidenciaIntervalosDto itemDto: listaIntervalosDto) {
				boolean iguales = itemVO.getPk().getNumSerieIni().equals(itemDto.getNumSerieIni()) && 
						          itemVO.getNumSerieFin().equals(itemDto.getNumSerieFin());
				if (iguales) {
					existe = true;
					guardarIntervalos.add(itemVO);
					break;
				}
				
				if (!existe) {
					incidenciaIntervaloDao.borrar(itemVO);
				}
			}
		}
		
		// Nuevas Altas
		for(IncidenciaIntervalosDto itemDto: listaIntervalosDto) {
			boolean existe = false;
			for(IncidenciaIntervaloVO itemVO: listaIntervalosVO) {
				boolean iguales = itemDto.getNumSerieIni().equals(itemVO.getPk().getNumSerieIni()) &&
						          itemDto.getNumSerieFin().equals(itemVO.getNumSerieFin());
				if (iguales) {
					existe = true;
					break;
				}
			}
			
			if (!existe) {
				IncidenciaIntervaloVO interVO = new IncidenciaIntervaloVO();
				IncidenciaIntervaloVOId pk = new IncidenciaIntervaloVOId();
				pk.setNumSerieIni(itemDto.getNumSerieIni());
				interVO.setPk(pk);
				interVO.setNumSerieFin(itemDto.getNumSerieFin());
				interVO = incidenciaIntervaloDao.guardarOActualizar(interVO);
				guardarIntervalos.add(interVO);
			}
		}
		
		return guardarIntervalos;
	}

	private IncidenciaVO guardarIncidencia(IncidenciaVO incidenciaVO) {
		incidenciaVO =  incidenciaDao.guardarOActualizar(incidenciaVO);
		
		// Lista de seriales
		List<IncidenciaSerialVO> seriales = new ArrayList<IncidenciaSerialVO>();
		for(IncidenciaSerialVO item: incidenciaVO.getListaSeriales()) {
			item.getPk().setIncidenciaId(incidenciaVO.getIncidenciaId());
			
			IncidenciaSerialVO incidenciaSerialVO = incidenciaSerialDao.guardarOActualizar(item);
			seriales.add(incidenciaSerialVO);
		}
		incidenciaVO.setListaSeriales(new ArrayList<IncidenciaSerialVO>());
		incidenciaVO.getListaSeriales().addAll(seriales);

		// Lista de intervalos
		List<IncidenciaIntervaloVO> intervalos = new ArrayList<IncidenciaIntervaloVO>();
		for(IncidenciaIntervaloVO item: incidenciaVO.getListaIntervalos()) {
			item.getPk().setIncidenciaId(incidenciaVO.getIncidenciaId());
			
			IncidenciaIntervaloVO incidenciaIntervaloVO = incidenciaIntervaloDao.guardarOActualizar(item);
			intervalos.add(incidenciaIntervaloVO);
		}
		incidenciaVO.setListaIntervalos(new ArrayList<IncidenciaIntervaloVO>());
		incidenciaVO.getListaIntervalos().addAll(intervalos);
		
		incidenciaVO =  incidenciaDao.guardarOActualizar(incidenciaVO);
		
		return incidenciaVO;
	}


	// Creo que no se usa
	@SuppressWarnings("unused")
	@Transactional
	public ResultBean salvarIncidencia(IncidenciaDto incidenciaDto) {
		ResultBean result = new ResultBean();
		
		try {
			String jefatura = incidenciaDto.getJefaturaProvincial();
			Long   materia  = incidenciaDto.getMateriaId();
			//String numSerie = incidenciaDto.getNumSerie();
			//IncidenciaVO incidenciaVO = servicioConsultaIncidencias.obtenerIncidenciaForJefaturaMaterialNumSerie(jefatura, materia, numSerie);
			IncidenciaVO incidenciaVO = new IncidenciaVO();
			if (incidenciaVO == null) {
				incidenciaVO = servicioConsultaIncidencias.getVOFromDto(incidenciaDto);
				AutorVO autorVO = servicioConsultaUsuarioConsejo.obtenerAutorFromUsuarioColegio();
				incidenciaVO.setAutorVO(autorVO);
				incidenciaVO =  incidenciaDao.guardarOActualizar(incidenciaVO);
			} else {
				boolean noCambiar = incidenciaVO.getObservaciones().equals(incidenciaDto.getObservaciones()) &&
						            incidenciaVO.getTipo().equals(incidenciaDto.getTipo());
				
				if (!noCambiar) {
					incidenciaVO.setObservaciones(incidenciaDto.getObservaciones());
					incidenciaVO.setFecha(incidenciaDto.getFecha());
					incidenciaVO.setTipo(incidenciaDto.getTipo());
					incidenciaVO =  incidenciaDao.guardarOActualizar(incidenciaVO);
				}
			}
			
			incidenciaDto.setIncidenciaId(incidenciaVO.getIncidenciaId());

			result.setMensaje(INCIDENCIA_OK);
			result.addAttachment("incidenciaDto", incidenciaDto);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(INCIDENCIA_KO);
		}
		
		return result;

	}

	@Override
	@Transactional
	public ResultBean salvarIncidencia(IncidenciaVO incidenciaVO) {
		ResultBean result = new ResultBean();
		
		AutorVO autorOld = servicioConsultaAutor.getAutorByPrimaryKey(incidenciaVO.getAutorVO().getAutorId());
		if (autorOld != null) {
			incidenciaVO.setAutorVO(autorOld);
		} else {
			AutorVO autor = new AutorVO();
			autor.setAutorId(incidenciaVO.getAutorVO().getAutorId());
			autor.setEmail(incidenciaVO.getAutorVO().getEmail());
			autor.setNombre(incidenciaVO.getAutorVO().getNombre());
			autor.setRol(incidenciaVO.getAutorVO().getRol());
			autor = autorDao.guardarOActualizar(autor);
			incidenciaVO.setAutorVO(autor);
		}

		try {
			
			for(IncidenciaSerialVO item: incidenciaVO.getListaSeriales()) {
				IncidenciaVO incidenciaOld = incidenciaDao.findIncidenciaBySerial(item.getPk().getNumSerie());
				
				if (incidenciaOld != null) {
					IncidenciaSerialVO incidenciaSerial = servicioConsultaIncidenciasSerial.obtenerIncidenciaSerial(incidenciaOld, item.getPk().getNumSerie());
					if (incidenciaSerial.getIncidenciaInvId() != null && item.getIncidenciaInvId().equals(incidenciaSerial.getIncidenciaInvId())) {
						log.info("Incidencia " + "ya registrada en el sistema");
					} else {
						incidenciaSerial.setIncidenciaInvId(item.getIncidenciaInvId());
						incidenciaSerialDao.guardarOActualizar(incidenciaSerial);
						
						incidenciaDao.guardarOActualizar(incidenciaOld);
					}
				} else {
					IncidenciaSerialVO incidenciaConsejo = servicioConsultaIncidenciasSerial.obtenerIncidenciaByIncidenciaConsejo(item.getIncidenciaInvId());
					if (incidenciaConsejo != null) {
						log.info("La incidencia " + item.getIncidenciaInvId() + " con distinto serial en consejo y sistema.");
					} else {
						log.info("La incidencia " + item.getIncidenciaInvId() + " es nueva para el sistema");
						IncidenciaVO incidenciaVOnew = incidenciaDao.guardarOActualizar(incidenciaVO);
						
						List<IncidenciaSerialVO> listaSeriales = new ArrayList<IncidenciaSerialVO>();
						
						IncidenciaSerialVOId pkSerial = new IncidenciaSerialVOId(incidenciaVOnew.getIncidenciaId(), item.getPk().getNumSerie());
						IncidenciaSerialVO incidenciaSerialVO = new IncidenciaSerialVO();
						incidenciaSerialVO.setPk(pkSerial);
						incidenciaSerialVO.setIncidenciaInvId(item.getIncidenciaInvId());
						IncidenciaSerialVO inciSerial = incidenciaSerialDao.guardarOActualizar(incidenciaSerialVO);
						
						listaSeriales.add(inciSerial);
						
						incidenciaVOnew.setListaSeriales(listaSeriales);
						incidenciaDao.guardarOActualizar(incidenciaVOnew);

					}
					
				}
				
				//IncidenciaVO incidenciaBefore = incidenciaDao.findIncidenciaByPk(incidenciaVO.getIncidenciaId());
				boolean salvar = false;
				if (!incidenciaOld.getMaterialVO().getMaterialId().equals(incidenciaVO.getMaterialVO().getMaterialId())) {
					MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(incidenciaVO.getMaterialVO().getMaterialId());
					incidenciaOld.setMaterialVO(materialVO);
					salvar = true;
				}
				
				if (!incidenciaOld.getJefaturaProvincial().getJefaturaProvincial().equals(incidenciaVO.getJefaturaProvincial().getJefaturaProvincial())) {
					JefaturaTraficoVO jprov = jefaturaTraficoDao.getJefatura(incidenciaVO.getJefaturaProvincial().getJefaturaProvincial());
					incidenciaOld.setJefaturaProvincial(jprov);
					salvar = true;
				}
				
				if (!incidenciaOld.getTipo().equals(incidenciaVO.getTipo())) {
					incidenciaOld.setTipo(incidenciaVO.getTipo());
					salvar = true;
				}
				
				if (!incidenciaOld.getObservaciones().equals(incidenciaVO.getObservaciones())) {
					incidenciaOld.setObservaciones(incidenciaVO.getObservaciones());
					salvar = true;
				}

				if (salvar) {
					log.info("La incidencia " + incidenciaVO.getIncidenciaId() + " actualizados datos generales.");
					incidenciaDao.guardarOActualizar(incidenciaOld);
				}
			}
			
			result.setMensaje(INCIDENCIA_OK);
			result.addAttachment("incidenciaVO", incidenciaVO);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(INCIDENCIA_KO);
		}
		
		return result;
	}


	@Override
	@Transactional
	public ResultBean actualizarIncidenciaWithIdInventario(Long incidenciaId, Long inventarioId) {
		// TODO Auto-generated method stub		
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByPk(incidenciaId);
			
			if (incidenciaVO == null) {
				result.setError(Boolean.TRUE);
				log.error("No se ha encontrado la incidencia con id ==> " + incidenciaId);
				return result;
			} else {
				
				log.info("Incidencia =================================> " + incidenciaVO.getIncidenciaId());
				
				
				//incidenciaVO.setIncidenciaInvId(inventarioId);
				IncidenciaVO incidenciaNew = incidenciaDao.guardarOActualizar(incidenciaVO);
				
				result.setMensaje(INCIDENCIA_OK);
				result.addAttachment("incidenciaVO", incidenciaNew);
			}
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(INCIDENCIA_KO);
		}
		
		return result;
	}

}
