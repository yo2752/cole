package org.gestoresmadrid.oegam2comun.participacion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.participacion.model.dao.ParticipacionDao;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.ServicioIntervinienteModelos;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.participacion.model.service.ServicioParticipacion;
import org.gestoresmadrid.oegam2comun.participacion.view.dto.ParticipacionDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioParticipacionImpl implements ServicioParticipacion{

	private static final long serialVersionUID = -860527203390820788L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioParticipacionImpl.class);
	
	@Autowired
	private ServicioBien servicioBien;
	
	@Autowired
	private ServicioIntervinienteModelos servicioIntervinienteModelos;
	
	@Autowired
	private ServicioConcepto servicioConcepto;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private ParticipacionDao participacionDao;
	
	@Override
	public ResultBean convertirListaCoefParticipacionVoToDto(RemesaDto remesaDto, RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<ParticipacionDto> listaCoefPartSujPas = null;
			List<ParticipacionDto> listaCoefPartTrans = null;
			if(remesaVO.getListaCoefParticipacion() != null && !remesaVO.getListaCoefParticipacion().isEmpty()){
				for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
					if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
						if(listaCoefPartSujPas == null || listaCoefPartSujPas.isEmpty()){
							listaCoefPartSujPas = new ArrayList<ParticipacionDto>();
						}
						listaCoefPartSujPas.add(conversor.transform(participacionVO, ParticipacionDto.class));
					}else if(TipoInterviniente.Transmitente.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
						if(listaCoefPartTrans == null || listaCoefPartTrans.isEmpty()){
							listaCoefPartTrans = new ArrayList<ParticipacionDto>();
						}
						listaCoefPartTrans.add(conversor.transform(participacionVO, ParticipacionDto.class));
					}
				}
				if(listaCoefPartSujPas != null && !listaCoefPartSujPas.isEmpty()){
					remesaDto.setListaPartSujPasivo(listaCoefPartSujPas);
				}
				if(listaCoefPartTrans != null && !listaCoefPartTrans.isEmpty()){
					remesaDto.setListaPartTransmitente(listaCoefPartTrans);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la lista de coeficientes de participacion, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de gestionar la lista de coeficientes de participacion.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean convertirListaCoefPartIntervVOToDto(RemesaDto remesaDto, RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(remesaVO.getListaCoefParticipacion() != null && !remesaVO.getListaCoefParticipacion().isEmpty()){
				if(remesaDto.getListaSujetosPasivos() != null && !remesaDto.getListaSujetosPasivos().isEmpty()){
					List<IntervinienteModelosDto> listaAuxSjP = new ArrayList<IntervinienteModelosDto>();
					for(IntervinienteModelosDto sujPasivoDto : remesaDto.getListaSujetosPasivos()){
						for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
							if(participacionVO.getIdInterviniente().equals(sujPasivoDto.getIdInterviniente())){
								sujPasivoDto.setPorcentaje(participacionVO.getPorcentaje());
								listaAuxSjP.add(sujPasivoDto);
							}
						}
					}
					if(listaAuxSjP != null && !listaAuxSjP.isEmpty()){
						remesaDto.setListaSujetosPasivos(listaAuxSjP);
					}
				}
				if(remesaDto.getListaTransmitentes() != null && !remesaDto.getListaTransmitentes().isEmpty()){
					List<IntervinienteModelosDto> listaTrSjP = new ArrayList<IntervinienteModelosDto>();
					for(IntervinienteModelosDto transmitenteDto : remesaDto.getListaTransmitentes()){
						for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
							if(participacionVO.getIdInterviniente().equals(transmitenteDto.getIdInterviniente())){
								transmitenteDto.setPorcentaje(participacionVO.getPorcentaje());
								listaTrSjP.add(transmitenteDto);
							}
						}
					}
					if(listaTrSjP != null && !listaTrSjP.isEmpty()){
						remesaDto.setListaTransmitentes(listaTrSjP);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la lista de coeficientes de participacion, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de gestionar la lista de coeficientes de participacion.");
		}
		return resultado;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultBean guardarCoefParticipacion(String idParticipaciones, String porcentajes, Long idRemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<ParticipacionVO> listaCoefPartBBDD = participacionDao.getListaParticipacionPorIdRemesa(idRemesa,true);
			ResultBean resultBien = servicioBien.getListaBienesPorIdRemesa(idRemesa);
			if(resultBien != null && !resultBien.getError()){
				List<BienRemesaVO> listaBienes = (List<BienRemesaVO>) resultBien.getAttachment("listaBienesVO");
				ResultBean resultInterv = servicioIntervinienteModelos.getIntervinientesPorIdRemesa(idRemesa);
				if(resultInterv != null && !resultInterv.getError()){
					List<IntervinienteModelosVO> listaIntervinientes = (List<IntervinienteModelosVO>) resultInterv.getAttachment("listaIntervinientesVO");
					if(listaCoefPartBBDD != null && !listaCoefPartBBDD.isEmpty()){
						if(idParticipaciones != null && !idParticipaciones.isEmpty()){
							// se modifican los coeficientes de participacion 
							String idParticipacion[] = idParticipaciones.split("-");
							String porcentaje[] = porcentajes.split("-");
							int i = 0;
							for(String id : idParticipacion){
								for(ParticipacionVO participacionBBDD : listaCoefPartBBDD){
									if(participacionBBDD.getIdParticipacion().equals(Long.parseLong(id))){
										participacionBBDD.setPorcentaje(new BigDecimal(porcentaje[i++]));
										participacionDao.guardarOActualizar(participacionBBDD);
									}
								}
							}
						}else{
							//se añaden los coeficientes de participacion por intervinientes
							Boolean existe = false;
							for(BienRemesaVO bienRemesaVO : listaBienes){
								for(IntervinienteModelosVO intervinienteModelosVO : listaIntervinientes){
									existe = false;
									for(ParticipacionVO participacionVO : listaCoefPartBBDD){
										if(participacionVO.getIdBien().equals(bienRemesaVO.getId().getIdBien()) && 
												participacionVO.getIdInterviniente().equals(intervinienteModelosVO.getIdInterviniente())){
											existe = true;
											break;
										}
									}
									if(!existe){
										ParticipacionVO participacionVO = new ParticipacionVO();
										participacionVO.setIdBien(bienRemesaVO.getId().getIdBien());
										participacionVO.setIdInterviniente(intervinienteModelosVO.getIdInterviniente());
										participacionVO.setIdRemesa(idRemesa);
										participacionDao.guardar(participacionVO);
									}
								}
							}
						}
					}else{
						for(BienRemesaVO bienRemesaVO : listaBienes){
							for(IntervinienteModelosVO intervinienteModelosVO : listaIntervinientes){
								if(!TipoInterviniente.Presentador.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
									ParticipacionVO participacionVO = new ParticipacionVO();
									participacionVO.setIdBien(bienRemesaVO.getId().getIdBien());
									participacionVO.setIdInterviniente(intervinienteModelosVO.getIdInterviniente());
									participacionVO.setIdRemesa(idRemesa);
									participacionDao.guardar(participacionVO);
								}
							}
						}
					}
				
				}else{
					if(resultInterv != null && resultInterv.getError()){
						resultado = resultInterv;
					}
				}
			}else{
				if(resultBien != null && resultBien.getError()){
					resultado = resultBien;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los coeficientes de participación, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los coeficientes de participación");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarCoefPartRemesaBien(Long idRemesa, Long idBien) {
		ResultBean resultado = null;
		try {
			if(idBien != null && idRemesa != null){
				List<ParticipacionVO> listaPart = participacionDao.getListaParticipacionRemesaBien(idRemesa,idBien);
				if(listaPart != null && !listaPart.isEmpty()) {
					for(ParticipacionVO participacionVO : listaPart){
						participacionDao.borrar(participacionVO);
					}
				}
			}else{
				resultado = new ResultBean(true, "Faltan datos para poder borrar el coeficiente de participación del bien que desea eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el coeficiente de participación del bien, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar el coeficiente de participación del bien");
		}
		return resultado;
	}
	
	@Override
	public ResultBean eliminarCoefPartRemesaInterviniente(Long idRemesa, Long idInterviniente) {
		ResultBean resultado = null;
		try {
			if(idInterviniente != null && idRemesa != null){
				List<ParticipacionVO> listaPart = participacionDao.getListaParticipacionPorRemesaEInterviniente(idRemesa, idInterviniente);
				if(listaPart != null && !listaPart.isEmpty()) {
					for(ParticipacionVO participacionVO : listaPart){
						participacionDao.borrar(participacionVO);
					}
				}
			}else{
				resultado = new ResultBean(true, "Faltan datos para poder borrar el coeficiente de participación del interviniente que desea eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el coeficiente de participación del interviniente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar el coeficiente de participación del interviniente");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarCoefParticipacionInterviniente(Long idInterviniente, Long idRemesa, BigDecimal porcentaje) {
		ResultBean resultado = new ResultBean(false);
		try {
			ParticipacionVO participacionVO = participacionDao.getParticipacionPorRemesaEInterviniente(idRemesa, idInterviniente);
			if(participacionVO != null){
				participacionVO.setPorcentaje(porcentaje);
			}else{
				participacionVO = new ParticipacionVO();
				participacionVO.setIdInterviniente(idInterviniente);
				participacionVO.setIdRemesa(idRemesa);
				participacionVO.setPorcentaje(porcentaje);
			}
			participacionDao.guardarOActualizar(participacionVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el coeficiente de participación del interviniente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el coeficiente de participación del interviniente");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean comprobarParticipacionesExistentes(Long idRemesa, boolean esConceptoBienes) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<ParticipacionVO> listaParticipacion = participacionDao.getListaParticipacionPorIdRemesa(idRemesa,false);
			if(listaParticipacion != null && !listaParticipacion.isEmpty()){
				if(esConceptoBienes){
					for(ParticipacionVO participacionVO : listaParticipacion){
						if(participacionVO.getIdBien() == null){
							participacionDao.borrar(participacionVO);
						}else{
							participacionDao.evict(participacionVO);
						}
						
					}
				}else{
					for(ParticipacionVO participacionVO : listaParticipacion){
						if(participacionVO.getIdBien() != null){
							participacionDao.borrar(participacionVO);
						}else{
							participacionDao.evict(participacionVO);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los coeficiente de participación de la remesa, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de comprobar los coeficiente de participación de la remesa.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean validarGenerarModelosCoefParticipacion(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(remesaVO.getListaCoefParticipacion() != null && !remesaVO.getListaCoefParticipacion().isEmpty()){
				BigDecimal porcentajeTotalSjP = BigDecimal.ZERO;
				BigDecimal porcentajeTotalTr = BigDecimal.ZERO;
				Boolean existeTrans = false;
				boolean esConceptoBienes = servicioConcepto.esConceptoBienes(conversor.transform(remesaVO.getConcepto(), ConceptoDto.class));
				if(esConceptoBienes){
					if(remesaVO.getListaBienes() != null && !remesaVO.getListaBienes().isEmpty()){
						for(BienRemesaVO bienRemesaVO : remesaVO.getListaBienes()){
							porcentajeTotalSjP = BigDecimal.ZERO;
							porcentajeTotalTr = BigDecimal.ZERO;
							for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
								if(participacionVO.getIdBien().equals(bienRemesaVO.getId().getIdBien())){
									if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
										if(participacionVO.getPorcentaje() != null){
											porcentajeTotalSjP = porcentajeTotalSjP.add(participacionVO.getPorcentaje());
										}else{
											resultado.setError(true);
											resultado.addMensajeALista("Los porcentajes de participacion de los Sujetos Pasivos no suman el 100%.");
											return resultado;
										}
									}else if(TipoInterviniente.Transmitente.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
										existeTrans = true;
										if(participacionVO.getPorcentaje() != null){
											porcentajeTotalTr = porcentajeTotalTr.add(participacionVO.getPorcentaje());
										}else{
											resultado.setError(true);
											resultado.addMensajeALista("Los porcentajes de participacion de los Transmitentes no suman el 100%.");
											return resultado;
										}
									}
									
								}
							}
							if(porcentajeTotalSjP.compareTo(new BigDecimal(100)) != 0){
								resultado.setError(true);
								resultado.addMensajeALista("Los porcentajes de participacion de los Sujetos Pasivos no suman el 100%.");
							}else if(existeTrans && porcentajeTotalTr.compareTo(new BigDecimal(100)) != 0){
								resultado.setError(true);
								resultado.addMensajeALista("Los porcentajes de participacion de los Transmitentes no suman el 100%.");
							}
						}
					}
				}else{
					for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
						if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
							if(participacionVO.getPorcentaje() != null){
								porcentajeTotalSjP = porcentajeTotalSjP.add(participacionVO.getPorcentaje());
							}else{
								resultado.setError(true);
								resultado.addMensajeALista("Los porcentajes de participacion de los Sujetos Pasivos no suman el 100%.");
								return resultado;
							}
						}else if(TipoInterviniente.Transmitente.getValorEnum().equals(participacionVO.getIntervinienteModelos().getTipoInterviniente())){
							existeTrans = true;
							if(participacionVO.getPorcentaje() != null){
								porcentajeTotalTr = porcentajeTotalTr.add(participacionVO.getPorcentaje());
							}else{
								resultado.setError(true);
								resultado.addMensajeALista("Los porcentajes de participacion de los Transmitentes no suman el 100%.");
								return resultado;
							}
						}
					}
					if(porcentajeTotalSjP.compareTo(new BigDecimal(100)) != 0){
						resultado.setError(true);
						resultado.addMensajeALista("Los porcentajes de participacion de los Sujetos Pasivos no suman el 100%.");
					}else if(existeTrans && porcentajeTotalTr.compareTo(new BigDecimal(100)) != 0){
						resultado.setError(true);
						resultado.addMensajeALista("Los porcentajes de participacion de los Transmitentes no suman el 100%.");
					}
				}
			}else{
				resultado = new ResultBean(true, "Debe de rellenar los coeficientes de participacion de los intervinientes de la remesa.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los coeficientes de participacion, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de validar los coeficientes de participacion.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ParticipacionDto> getListaParticipacionIntervinientesRemesa(Long idInterviniente, Long idRemesa) {
		try {
			List<ParticipacionVO> listaParticipacionVO = participacionDao.getListaParticipacionPorRemesaEInterviniente(idRemesa, idInterviniente);
			if(listaParticipacionVO != null && !listaParticipacionVO.isEmpty()){
				return conversor.transform(listaParticipacionVO, ParticipacionDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de participacion del interviniente por la remesa, error: ",e);
		}
		return null;
	}
	
}
