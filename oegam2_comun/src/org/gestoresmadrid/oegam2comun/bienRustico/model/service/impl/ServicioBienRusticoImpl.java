package org.gestoresmadrid.oegam2comun.bienRustico.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.bien.model.dao.BienRemesaDao;
import org.gestoresmadrid.core.bien.model.dao.BienRusticoDao;
import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.modelo.bien.model.dao.ModeloBienDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienRusticoBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bienRustico.model.service.ServicioBienRustico;
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
public class ServicioBienRusticoImpl implements ServicioBienRustico {

	private static final long serialVersionUID = 968344649708169681L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioBienRusticoImpl.class);

	@Autowired
	private BienRusticoDao bienRusticoDao;

	@Autowired
	private ServicioDireccionCam servicioDireccionCam;

	@Autowired
	private Conversor conversor;

	@Autowired
	private BienRemesaDao bienRemesaDao;

	@Autowired
	private ModeloBienDao modeloBienDao;

	@Autowired
	private ServicioParticipacion servicioParticipacion;

	@Override
	public List<BienRusticoBean> convertirListaEnBeanPantalla(List<BienRusticoVO> listaBienRusticoVO) {
		List<BienRusticoBean> lista = new ArrayList<BienRusticoBean>();
		for (BienRusticoVO bienRusticoVO : listaBienRusticoVO) {
			BienRusticoBean bienRusticoBean = conversor.transform(bienRusticoVO, BienRusticoBean.class);
			if (bienRusticoVO.getDireccion() != null && bienRusticoVO.getDireccion().getIdProvincia() != null) {
				bienRusticoBean.setProvincia(servicioDireccionCam.obtenerNombreProvincia(bienRusticoVO.getDireccion().getIdProvincia()));
			}
			if (bienRusticoVO.getDireccion() != null && bienRusticoVO.getDireccion().getIdMunicipio() != null) {
				bienRusticoBean.setMunicipio(servicioDireccionCam.obtenerNombreMunicipio(bienRusticoVO.getDireccion().getIdProvincia(), bienRusticoVO.getDireccion().getIdMunicipio()));
			}
			lista.add(bienRusticoBean);
		}
		return lista;
	}

	private ResultBean validarBienRusticoPantalla(BienDto bienDto) {
		ResultBean resultado = null;

		if (bienDto.getTipoInmueble() == null || StringUtils.isBlank(bienDto.getTipoInmueble().getIdTipoInmueble())) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe seleccionar algún tipo de Inmueble para poder guardar el bien.");
		}

		if (bienDto.getUsoRustico() == null || bienDto.getUsoRustico().getIdUsoRustico() == null) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe seleccionar algún tipo de uso para poder guardar el bien.");
		} else {
			String[] usoRustico = bienDto.getUsoRustico().getIdUsoRustico().split(",");
			for (String idUsoRustico : usoRustico) {
				if (StringUtils.isNotBlank(idUsoRustico)) {
					bienDto.getUsoRustico().setIdUsoRustico(idUsoRustico);
					break;
				}
			}
		}

		if (bienDto.getSuperficie() == null) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe rellenar la superficie del bien.");
		}

		if (bienDto.getUnidadMetrica() == null || StringUtils.isBlank(bienDto.getUnidadMetrica().getUnidadMetrica())) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe seleccionar alguna Unidad Métrica para poder guardar el bien.");
		}

		if (bienDto.getDireccion() == null) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe rellenar algún dato de la dirección del bien.");
		}

		if (bienDto.getDireccion().getIdProvincia() == null || bienDto.getDireccion().getIdProvincia().equals("-1")) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe seleccionar alguna provincia para la dirección del bien.");
		}

		if (bienDto.getDireccion().getIdMunicipio() == null || bienDto.getDireccion().getIdMunicipio().equals("")) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe seleccionar algún municipio para la dirección del bien.");
		}
		return resultado;
	}

	@Override
	public Boolean esBienRustico(BienDto bienDto) {
		if (bienDto == null) {
			return false;
		}
		if (bienDto.getIdBien() != null) {
			return true;
		}
		if (bienDto.getTipoInmueble() != null && StringUtils.isNotBlank(bienDto.getTipoInmueble().getIdTipoInmueble())) {
			return true;
		}
		if (bienDto.getUsoRustico() != null && bienDto.getUsoRustico().getIdUsoRustico() != null && bienDto.getUsoRustico().getTipoUso() != null) {
			return true;
		}
		if (bienDto.getSuperficie() != null) {
			return true;
		}
		if (bienDto.getUnidadMetrica() != null && StringUtils.isNotBlank(bienDto.getUnidadMetrica().getUnidadMetrica())) {
			return true;
		}
		if (bienDto.getDireccion().getIdProvincia() != null && !bienDto.getDireccion().getIdProvincia().equals("") && !bienDto.getDireccion().getIdProvincia().equals("-1")) {
			return true;
		}
		if (bienDto.getDireccion().getIdMunicipio() != null && !bienDto.getDireccion().getIdMunicipio().equals("") && !bienDto.getDireccion().getIdMunicipio().equals("-1")) {
			return true;
		}
		
		return false;
	}

	@Override
	@Transactional
	public ResultBean guardarBienRusticoPantalla(BienDto bienDto, Long idDireccion) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarBienRusticoPantalla(bienDto);
			if (resultValidar == null) {
				BienRusticoVO bienRusticoVO = conversor.transform(bienDto, BienRusticoVO.class);
				bienRusticoVO.setIdDireccion(idDireccion);
				if (bienRusticoVO.getFechaAlta() == null) {
					bienRusticoVO.setFechaAlta(new Date());
				}
				if (bienRusticoVO.getEstado() == null) {
					bienRusticoVO.setEstado(Long.valueOf(Estado.Habilitado.getValorEnum()));
				}
				bienRusticoDao.guardarOActualizar(bienRusticoVO);
				resultado.addAttachment("idBien", bienRusticoVO.getIdBien());
			} else {
				resultado = resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien rustico de pantalla, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien rústico.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarBienRustico(BienRusticoVO bienVO) {
		ResultBean resultBean = new ResultBean(false);
		try {
			if (bienVO != null) {
				bienRusticoDao.borrar(bienVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien rustico, error: ", e);
			resultBean = new ResultBean(true, "Ha sucedido un error a la hora de eliminar el bien rústico");
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean eliminarBienesRusticosRemesa(Long idRemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
			if (listaBienesRemesa != null && !listaBienesRemesa.isEmpty()) {
				for (BienRemesaVO bienRemesaVO : listaBienesRemesa) {
					if (bienRemesaVO.getBien() instanceof BienRusticoVO) {
						ResultBean resultPart = servicioParticipacion.eliminarCoefPartRemesaBien(idRemesa, bienRemesaVO.getId().getIdBien());
						if (resultPart == null || !resultPart.getError()) {
							bienRemesaDao.borrar(bienRemesaVO);
							List<BienRemesaVO> listaAuxBienRemesa = bienRemesaDao.getListaPorIdBien(bienRemesaVO.getId().getIdBien());
							if (listaAuxBienRemesa == null || listaAuxBienRemesa.isEmpty()) {
								List<ModeloBienVO> listaModeloBienes = modeloBienDao.getListaPorIdBien(bienRemesaVO.getId().getIdBien());
								if (listaModeloBienes == null || listaModeloBienes.isEmpty()) {
									bienRusticoDao.borrar((BienRusticoVO) bienRemesaVO.getBien());
								}
							}
						} else {
							return resultPart;
						}
					} else {
						bienRemesaDao.evict(bienRemesaVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes rusticos de la remesa,error: ");
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes rústicos de la remesa.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarBienesRusticosModelo(Long idModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			List<ModeloBienVO> listaBienes = modeloBienDao.getModeloBienPorIdModelo(idModelo);
			if (listaBienes != null && !listaBienes.isEmpty()) {
				for (ModeloBienVO modeloBienVO : listaBienes) {
					if (modeloBienVO.getBien() instanceof BienRusticoVO) {
						modeloBienDao.borrar(modeloBienVO);
						List<ModeloBienVO> listaModeloBienes = modeloBienDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
						if (listaModeloBienes == null || listaModeloBienes.isEmpty()) {
							List<BienRemesaVO> listaAuxBienRemesa = bienRemesaDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
							if (listaAuxBienRemesa == null || listaAuxBienRemesa.isEmpty()) {
								bienRusticoDao.borrar((BienRusticoVO) modeloBienVO.getBien());
							}
						}
					} else {
						modeloBienDao.evict(modeloBienVO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes rusticos de la remesa,error: ");
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes rústicos de la remesa.");
		}
		return resultado;
	}

	@Override
	public ResultBean validarBienRustico(BienDto bienRustico) {
		ResultBean resultado = new ResultBean(false);
		if (bienRustico.getUsoRustico() == null || bienRustico.getUsoRustico().getIdUsoRustico() == null || bienRustico.getUsoRustico().getIdUsoRustico().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar el tipo de uso rústico del bien.");
			return resultado;
		}
		if (bienRustico.getUnidadMetrica() == null || bienRustico.getUnidadMetrica().getUnidadMetrica() == null || bienRustico.getUnidadMetrica().getUnidadMetrica().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar la unidad métrica del bien.");
			return resultado;
		}
		if (bienRustico.getSuperficie() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar la superficie del bien rústico.");
			return resultado;
		}

		return resultado;
	}
}
