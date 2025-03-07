package org.gestoresmadrid.oegam2comun.bienUrbano.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.bien.model.dao.BienRemesaDao;
import org.gestoresmadrid.core.bien.model.dao.BienUrbanoDao;
import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.modelo.bien.model.dao.ModeloBienDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienUrbanoBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bienUrbano.model.service.ServicioBienUrbano;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccionCam;
import org.gestoresmadrid.oegam2comun.participacion.model.service.ServicioParticipacion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioBienUrbanoImpl implements ServicioBienUrbano{

	private static final long serialVersionUID = 2765622518157174238L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioBienUrbanoImpl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private ServicioDireccionCam servicioDireccionCam;
	
	@Autowired
	private BienUrbanoDao bienUrbanoDao;
	
	@Autowired
	private BienRemesaDao bienRemesaDao;
	
	@Autowired
	private ModeloBienDao modeloBienDao;
	
	@Autowired
	private ServicioParticipacion servicioParticipacion;
	
	@Override
	public List<BienUrbanoBean> convertirListaEnBeanPantalla(List<BienUrbanoVO> lista) {
		List<BienUrbanoBean> listaBienes = new ArrayList<BienUrbanoBean>();
		BienUrbanoBean bienUrbanoBean = null;
		for(BienUrbanoVO bienUrbanoVO : lista){
			bienUrbanoBean = new BienUrbanoBean();
			conversor.transform(bienUrbanoVO, bienUrbanoBean,"bienUrbanoPantallaBean");
			if(bienUrbanoVO.getDireccion() != null && bienUrbanoVO.getDireccion().getIdProvincia() != null){
				bienUrbanoBean.setProvincia(servicioDireccionCam.obtenerNombreProvincia(bienUrbanoVO.getDireccion().getIdProvincia()));
			}
			if(bienUrbanoVO.getDireccion() != null && bienUrbanoVO.getDireccion().getIdMunicipio() != null){
				bienUrbanoBean.setMunicipio(servicioDireccionCam.obtenerNombreMunicipio(bienUrbanoVO.getDireccion().getIdProvincia(), bienUrbanoVO.getDireccion().getIdMunicipio()));
			}
			listaBienes.add(bienUrbanoBean);
		}
		return listaBienes;
	}
	

	@Override
	public Boolean esBienUrbano(BienDto bienDto) {
		if(bienDto==null){
			return false;
		}
		if(bienDto.getIdBien() != null){
			return true;
		}
		if(bienDto.getTipoInmueble() != null && StringUtils.isNotBlank(bienDto.getTipoInmueble().getIdTipoInmueble())){
			return true;
		}
		return false;
	}
	
	private ResultBean validarBienUrbanoPantalla(BienDto bienDto) {
		ResultBean resultBean = null;
		if(bienDto.getTipoInmueble() == null || bienDto.getTipoInmueble().getIdTipoInmueble() == null || bienDto.getTipoInmueble().getIdTipoInmueble().equals("")){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe seleccionar un tipo de inmueble");
		}
		if(bienDto.getDireccion() == null){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe rellenar algún dato de la dirección del bien.");
		}
		
		if(bienDto.getDireccion().getIdProvincia() == null || bienDto.getDireccion().getIdProvincia().equals("-1")){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		
		if(bienDto.getDireccion().getIdMunicipio() == null || bienDto.getDireccion().getIdMunicipio().equals("")){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe seleccionar algún municipio para la dirección del bien.");
		}
		
		if(bienDto.getDireccion().getIdTipoVia() == null || bienDto.getDireccion().getIdTipoVia().equals("") || bienDto.getDireccion().getIdTipoVia().equals("-1")){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe seleccionar el tipo de vía para la dirección del bien.");
		}
		
		if(bienDto.getDireccion().getNombreVia() == null || bienDto.getDireccion().getNombreVia().isEmpty()){
			resultBean = new ResultBean(true);
			resultBean.setMensaje("Debe rellenar el nombre de la vía de la dirección del bien.");
		}
		
		if(resultBean == null){
			convertirCombos(bienDto);
		}
		return resultBean;
	}
	private void convertirCombos(BienDto bienDto) {
		if(bienDto.getTipoInmueble() != null && StringUtils.isBlank(bienDto.getTipoInmueble().getIdTipoInmueble())){
			bienDto.setTipoInmueble(null);
		}
		if(bienDto.getSituacion() != null && StringUtils.isBlank(bienDto.getSituacion().getIdSituacion())){
			bienDto.setSituacion(null);
		}
	}


	@Override
	public ResultBean guardarBienUrbanoPantalla(BienDto bienDto,Long idDireccion) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarBienUrbanoPantalla(bienDto);
			if(resultValidar == null){
				BienUrbanoVO bienUrbanoVO = conversor.transform(bienDto, BienUrbanoVO.class);
				bienUrbanoVO.setIdDireccion(idDireccion);
				if(bienUrbanoVO.getFechaAlta() == null){
					bienUrbanoVO.setFechaAlta(new Date());
				}
				if(bienUrbanoVO.getEstado() == null){
					bienUrbanoVO.setEstado(Long.valueOf(Estado.Habilitado.getValorEnum()));
				}
				bienUrbanoDao.guardarOActualizar(bienUrbanoVO);
				resultado.addAttachment("idBien", bienUrbanoVO.getIdBien());
			}else{
				resultado = resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien urbano de pantalla, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien urbano.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean eliminarBienUrbano(BienUrbanoVO bienVO) {
		ResultBean resultBean = new ResultBean(false);
		try {
			if(bienVO != null){
				bienUrbanoDao.borrar(bienVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien urbano, error: ",e);
			resultBean = new ResultBean(true,"Ha sucedido un error a la hora de eliminar el bien urbano");
		}
		return resultBean;
	}
	
	@Override
	@Transactional
	public ResultBean eliminarBienesUrbanoRemesa(Long idRemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
			if(listaBienesRemesa != null && !listaBienesRemesa.isEmpty()){
				for(BienRemesaVO bienRemesaVO : listaBienesRemesa){
					if(bienRemesaVO.getBien() instanceof BienUrbanoVO){
						ResultBean resultPart = servicioParticipacion.eliminarCoefPartRemesaBien(idRemesa, bienRemesaVO.getId().getIdBien());
						if(resultPart == null || !resultPart.getError()){
							bienRemesaDao.borrar(bienRemesaVO);
							List<BienRemesaVO> listaAuxBienRemesa = bienRemesaDao.getListaPorIdBien(bienRemesaVO.getId().getIdBien());
							if(listaAuxBienRemesa == null || listaAuxBienRemesa.isEmpty()){
								List<ModeloBienVO> listaModeloBienes = modeloBienDao.getListaPorIdBien(bienRemesaVO.getId().getIdBien());
								if(listaModeloBienes == null || listaModeloBienes.isEmpty()){
									bienUrbanoDao.borrar((BienUrbanoVO) bienRemesaVO.getBien());
								}
							}
						}else{
							return resultPart;
						}
					}else{
						bienRemesaDao.evict(bienRemesaVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes urbanos de la remesa,error: ");
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes urbanos de la remesa.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean eliminarBienUrbanoModelo(Long idModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<ModeloBienVO> listaBienes = modeloBienDao.getModeloBienPorIdModelo(idModelo);
			if(listaBienes != null && !listaBienes.isEmpty()){
				for(ModeloBienVO modeloBienVO : listaBienes){
					if(modeloBienVO.getBien() instanceof BienUrbanoVO){
						modeloBienDao.borrar(modeloBienVO);
						List<ModeloBienVO> listaModeloBienes = modeloBienDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
						if(listaModeloBienes == null || listaModeloBienes.isEmpty()){
							List<BienRemesaVO> listaAuxBienRemesa = bienRemesaDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
							if(listaAuxBienRemesa == null || listaAuxBienRemesa.isEmpty()){
								bienUrbanoDao.borrar((BienUrbanoVO) modeloBienVO.getBien());
							}
						}
					}else{
						modeloBienDao.evict(modeloBienVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes urbanos de la remesa,error: ");
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes urbanos de la remesa.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean validarBienUrbano(BienDto bienUrbano) {
		ResultBean resultado = new ResultBean(false);
		if(bienUrbano.getArrendamiento() == null){
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar si es arrendamiento o no.");
			return resultado;
		}
		if(bienUrbano.getViviendaProtOficial() == null){
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar si es VPO o no.");
			return resultado;
		}
		return resultado;
	}
}
