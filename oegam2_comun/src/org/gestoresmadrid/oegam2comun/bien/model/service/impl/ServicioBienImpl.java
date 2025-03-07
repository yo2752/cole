package org.gestoresmadrid.oegam2comun.bien.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.bien.model.dao.BienDao;
import org.gestoresmadrid.core.bien.model.dao.BienRemesaDao;
import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.bien.model.vo.BienRemesasPK;
import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.modelo.bien.model.dao.ModeloBienDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienPK;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.registradores.model.dao.InmuebleDao;
import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienRemesaDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.ModeloBienDto;
import org.gestoresmadrid.oegam2comun.bienRustico.model.service.ServicioBienRustico;
import org.gestoresmadrid.oegam2comun.bienUrbano.model.service.ServicioBienUrbano;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccionCam;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioInmueble;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioBienImpl implements ServicioBien {

	private static final long serialVersionUID = -2856682422616839945L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioBienImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioBienRustico servicioBienRustico;

	@Autowired
	private ServicioBienUrbano servicioBienUrbano;

	@Autowired
	private ServicioConcepto servicioConcepto;

	@Autowired
	private BienDao bienDao;

	@Autowired
	private ServicioDireccionCam servicioDireccionCam;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private BienRemesaDao bienRemesaDao;

	@Autowired
	private ModeloBienDao modeloBienDao;

	@Autowired
	private InmuebleDao inmuebleDao;

	@Autowired
	private ServicioInmueble servicioInmueble;

	@Override
	@Transactional(readOnly = true)
	public ResultBean getBienPorId(Long idBien) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idBien != null) {
				BienVO bienVO = bienDao.getBienPorId(idBien);
				if (bienVO != null) {
					BienDto bienDto = conversor.transform(bienVO, BienDto.class);
					List<ModeloBienVO> listaModeloBien = modeloBienDao.getListaPorIdBien(idBien);
					if (listaModeloBien != null && !listaModeloBien.isEmpty()) {
						bienDto.setListaModelos(conversor.transform(listaModeloBien, ModeloBienDto.class));
					}
					List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getListaPorIdBien(idBien);
					if (listaBienesRemesa != null && !listaBienesRemesa.isEmpty()) {
						bienDto.setListaRemesas(conversor.transform(listaBienesRemesa, BienRemesaDto.class));
					}
					List<InmuebleDto> listaBienesInmueble = servicioInmueble.getInmuebles(idBien);
					if (listaBienesInmueble != null && !listaBienesInmueble.isEmpty()) {
						bienDto.setListaInmuebles(listaBienesInmueble);
					}
					resultado.addAttachment("bienDto", bienDto);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccionar algún bien para obtener sus datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar el bien por su id, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de recuperar el bien ");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getBienPorIdufir(Long idufir) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idufir != null) {
				BienVO bienVO = bienDao.getBienPorIdufir(idufir);
				if (bienVO != null) {
					BienDto bienDto = conversor.transform(bienVO, BienDto.class);
					resultado.addAttachment("bienDto", bienDto);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar el bien por su idufir, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de recuperar el bien ");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarBienRemesaPantalla(BienDto bienDto, Long idRemesa, BigDecimal numExpediente, UsuarioDto usuario, String tipoTramiteModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			Long idDireccion = null;

			// Se guarda la dirección si es un bien Rústico o Urbano o un bien de tipo otro y que tenga relleno la provincia o el municipio
			if (TipoBien.Rustico.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) || TipoBien.Urbano.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) || (TipoBien.Otro
					.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) && (!bienDto.getDireccion().getIdProvincia().equals("-1") || !bienDto.getDireccion().getIdMunicipio().equals(
							"-1")))) {
				ResultBean resultDireccion = servicioDireccion.guardarActualizarBien(conversor.transform(bienDto.getDireccion(), DireccionVO.class));
				if (resultDireccion.getError()) {
					resultado.addMensajeALista(resultDireccion.getMensaje());
				} else {
					DireccionVO direccionVO = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
					if (direccionVO != null) {
						idDireccion = direccionVO.getIdDireccion();
					}
				}
			}

			ResultBean resultBien = null;
			if (TipoBien.Urbano.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien())) {
				resultBien = servicioBienUrbano.guardarBienUrbanoPantalla(bienDto, idDireccion);
			} else if (TipoBien.Rustico.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien())) {
				resultBien = servicioBienRustico.guardarBienRusticoPantalla(bienDto, idDireccion);
			} else {
				boolean aplicaTasa = false;
				if (tipoTramiteModelo.equals("R600")) {
					aplicaTasa = true;
				}
				resultBien = guardarOtroBienPantalla(bienDto, aplicaTasa, idDireccion, true);
			}
			if (!resultBien.getError()) {
				guardarActualizarBienRemesaPantalla(idRemesa, (Long) resultBien.getAttachment("idBien"), bienDto.getValorDeclarado(), bienDto.getTransmision(), bienDto.getValorTasacion());
			} else {
				/*
				 * Hay error por lo tanto borramos los que hay en base de datos si el concepto si el tipo de bien es diferente al que hay no tiene bienes, es decir no se han seleccionado bienes de pantalla pero se ha guardado.
				 */

				BienRemesaVO bienRemesaBBDD = null;
				if (bienDto != null) {
					bienRemesaBBDD = bienRemesaDao.getBienRemesaPorId(bienDto.getIdBien(), idRemesa, false);

				}
				if (bienRemesaBBDD != null) {
					List<BienRemesaVO> bienesRemesa = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
					for (BienRemesaVO bienRemesaVO : bienesRemesa) {
						BienVO bien = bienDao.getBienPorId(bienRemesaVO.getId().getIdBien());
						BienVO bienBBDD = bienDao.getBienPorId(bienRemesaBBDD.getId().getIdBien());
						if (!bienBBDD.getTipoInmueble().getId().getIdTipoBien().equals(bien.getTipoInmueble().getId().getIdTipoBien())) {
							bienRemesaDao.borrar(bienRemesaVO);
							// Comprobamos que ese bien no tiene modelos asociados, si no los tiene lo borramos.
							List<BienRemesaVO> listaBienesBorrados = null;
							listaBienesBorrados = bienRemesaDao.getListaPorIdBien(bien.getIdBien());
							if (listaBienesBorrados != null && listaBienesBorrados.isEmpty())
								bienDao.borrar(bien);
						}
					}
				} else {
					List<BienRemesaVO> bienesModelo = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
					for (BienRemesaVO bienRemesaVO : bienesModelo) {
						BienVO bien = bienDao.getBienPorId(bienRemesaVO.getId().getIdBien());
						if (!bienDto.getTipoInmueble().getIdTipoBien().equals(bien.getTipoInmueble().getId().getIdTipoBien())) {
							bienRemesaDao.borrar(bienRemesaVO);
							List<BienRemesaVO> listaBienesBorrados = null;
							listaBienesBorrados = bienRemesaDao.getListaPorIdBien(bien.getIdBien());
							if (listaBienesBorrados != null && listaBienesBorrados.isEmpty())
								bienDao.borrar(bien);
						}
					}
				}

				resultado.setError(true);
				resultado.addMensajeALista(resultBien.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarModeloBienPantalla(BienDto bienDto, Long idModelo, BigDecimal numExpediente, UsuarioDto usuario, String tipoTramiteModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			Long idDireccion = null;
			// Se guarda la dirección si es un bien Rústico o Urbano o un bien de tipo otro y que tenga relleno la provincia o el municipio
			if (TipoBien.Rustico.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) || TipoBien.Urbano.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) || (TipoBien.Otro
					.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien()) && (!bienDto.getDireccion().getIdProvincia().equals("-1") || !bienDto.getDireccion().getIdMunicipio().equals(
							"-1")))) {
				ResultBean resultDireccion = servicioDireccion.guardarActualizarBien(conversor.transform(bienDto.getDireccion(), DireccionVO.class));
				if (resultDireccion.getError()) {
					resultado.addMensajeALista(resultDireccion.getMensaje());
				} else {
					DireccionVO direccionVO = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
					if (direccionVO != null) {
						idDireccion = direccionVO.getIdDireccion();
					}
				}
			}
			ResultBean resultBien = null;
			if (TipoBien.Otro.getValorEnum().equals(bienDto.getTipoBien())) {
				boolean aplicaTasa = false;
				if (tipoTramiteModelo.equals("M600")) {
					aplicaTasa = true;
				}
				resultBien = guardarOtroBienPantalla(bienDto, aplicaTasa, idDireccion, true);
			} else if (TipoBien.Urbano.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien())) {
				resultBien = servicioBienUrbano.guardarBienUrbanoPantalla(bienDto, idDireccion);
			} else if (TipoBien.Rustico.getValorEnum().equals(bienDto.getTipoInmueble().getIdTipoBien())) {
				resultBien = servicioBienRustico.guardarBienRusticoPantalla(bienDto, idDireccion);
			}
			if (!resultBien.getError()) {
				guardarActualizarModeloBienPantalla(idModelo, (Long) resultBien.getAttachment("idBien"), bienDto.getValorDeclarado(), bienDto.getTransmision(), bienDto.getValorTasacion());
			} else {
				/*
				 * Hay error por lo tanto borramos los que hay en base de datos si el concepto si el tipo de bien es diferente al que hay no tiene bienes, es decir no se han seleccionado bienes de pantalla pero se ha guardado.
				 */

				ModeloBienVO modeloBienBBDD = null;
				if (bienDto != null) {
					modeloBienBBDD = modeloBienDao.getModeloBienPorId(bienDto.getIdBien(), idModelo, false);
				}
				if (modeloBienBBDD != null) {
					List<ModeloBienVO> bienesModelo = modeloBienDao.getModeloBienPorIdModelo(idModelo);
					for (ModeloBienVO modeloBienVO : bienesModelo) {
						BienVO bien = bienDao.getBienPorId(modeloBienVO.getId().getIdBien());
						BienVO bienBBDD = bienDao.getBienPorId(modeloBienBBDD.getId().getIdBien());
						if (!bienBBDD.getTipoInmueble().getId().getIdTipoBien().equals(bien.getTipoInmueble().getId().getIdTipoBien())) {
							modeloBienDao.borrar(modeloBienVO);
							// Comprobamos que ese bien no tiene modelos asociados, si no los tiene lo borramos.
							List<ModeloBienVO> listaBienesBorrados = null;
							listaBienesBorrados = modeloBienDao.getListaPorIdBien(bien.getIdBien());
							if (listaBienesBorrados != null && listaBienesBorrados.isEmpty())
								bienDao.borrar(bien);
						}
					}
				} else {
					List<ModeloBienVO> bienesModelo = modeloBienDao.getModeloBienPorIdModelo(idModelo);
					for (ModeloBienVO modeloBienVO : bienesModelo) {
						BienVO bien = bienDao.getBienPorId(modeloBienVO.getId().getIdBien());
						if (!bienDto.getTipoInmueble().getIdTipoBien().equals(bien.getTipoInmueble().getId().getIdTipoBien())) {
							modeloBienDao.borrar(modeloBienVO);
							List<ModeloBienVO> listaBienesBorrados = null;
							listaBienesBorrados = modeloBienDao.getListaPorIdBien(bien.getIdBien());
							if (listaBienesBorrados != null && listaBienesBorrados.isEmpty())
								bienDao.borrar(bien);
						}
					}
				}

				resultado.setError(true);
				resultado.addMensajeALista(resultBien.getListaMensajes().get(0));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien");
		}
		return resultado;
	}

	private void guardarActualizarModeloBienPantalla(Long idModelo, Long idBien, BigDecimal valorDeclarado, BigDecimal transmision, BigDecimal valorTasacion) {

		// lista de todos los bienes del modelo ( los que tiene)

		ModeloBienVO modeloBienBBDD = modeloBienDao.getModeloBienPorId(idBien, idModelo, false);
		if (modeloBienBBDD != null) {
			modeloBienBBDD.setValorDeclarado(valorDeclarado);
			modeloBienBBDD.setTransmision(transmision);
			if (valorTasacion != null) {
				modeloBienBBDD.setValorTasacion(valorTasacion);
			}
			eliminarBienesNoCoherentes(idModelo, modeloBienBBDD);
		} else {
			modeloBienBBDD = new ModeloBienVO();
			ModeloBienPK id = new ModeloBienPK();
			id.setIdBien(idBien);
			id.setIdModelo(idModelo);
			modeloBienBBDD.setId(id);
			modeloBienBBDD.setValorDeclarado(valorDeclarado);
			if (transmision == null) {
				transmision = new BigDecimal("100");
			}
			modeloBienBBDD.setTransmision(transmision);
			if (valorTasacion != null) {
				modeloBienBBDD.setValorTasacion(valorTasacion);
			}
		}

		modeloBienDao.guardarOActualizar(modeloBienBBDD);
	}

	/*
	 * Mantis 30882 Verificamos todos los bienes del modelo, si el CONCEPTO de pantalla que vamos a guardar es diferente del CONCEPTO que tengo, los borro tanto de la tabla de relacion como de bienes.
	 */
	void eliminarBienesNoCoherentes(Long idModelo, ModeloBienVO modeloBienBBDD) {
		List<ModeloBienVO> bienesModelo = modeloBienDao.getModeloBienPorIdModelo(idModelo);
		for (ModeloBienVO modeloBienVO : bienesModelo) {
			if (modeloBienVO.getModelo600_601() != null && modeloBienBBDD.getModelo600_601() != null && !modeloBienVO.getModelo600_601().getConcepto().getId().getConcepto().equals(modeloBienBBDD
					.getModelo600_601().getConcepto().getId().getConcepto())) {
				modeloBienDao.borrar(modeloBienVO);
			}
		}
	}

	/**
	 * @param idRemesa
	 * @param bienRemesaBBDD
	 */
	void eliminarBienesRemNoCoherentes(Long idRemesa, BienRemesaVO bienRemesaBBDD) {
		List<BienRemesaVO> bienesModelo = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
		for (BienRemesaVO bienRemesaVO : bienesModelo) {
			if (bienRemesaVO.getRemesa() != null && !bienRemesaVO.getRemesa().getConcepto().getId().getConcepto().equals(bienRemesaBBDD.getRemesa().getConcepto().getId().getConcepto())) {
				bienRemesaDao.borrar(bienRemesaVO);
				// Comprobamos que ese bien no tiene modelos asociados, si no los tiene lo borramos.
			}
		}
	}

	private void guardarActualizarBienRemesaPantalla(Long idRemesa, Long idBien, BigDecimal valorDeclarado, BigDecimal transmision, BigDecimal valorTasacion) {

		// lista de todos los bienes de la remesa ( los que tiene)
		BienRemesaVO bienRemesaBBDD = bienRemesaDao.getBienRemesaPorId(idBien, idRemesa, false);
		if (bienRemesaBBDD != null) {
			bienRemesaBBDD.setValorDeclarado(valorDeclarado);
			bienRemesaBBDD.setTransmision(transmision);
			if (valorTasacion != null) {
				bienRemesaBBDD.setValorTasacion(valorTasacion);
			}
			eliminarBienesRemNoCoherentes(idRemesa, bienRemesaBBDD);
		} else {
			bienRemesaBBDD = new BienRemesaVO();
			BienRemesasPK id = new BienRemesasPK();
			id.setIdBien(idBien);
			id.setIdRemesa(idRemesa);
			bienRemesaBBDD.setId(id);
			bienRemesaBBDD.setValorDeclarado(valorDeclarado);
			bienRemesaBBDD.setTransmision(transmision);
			if (valorTasacion != null) {
				bienRemesaBBDD.setValorTasacion(valorTasacion);
			}
		}
		bienRemesaDao.guardarOActualizar(bienRemesaBBDD);
	}

	@Override
	@Transactional
	public ResultBean guardarBienRemesa(Long idBien, Long idRemesa) {
		ResultBean resultado = null;
		try {
			BienRemesaVO bienRemesaVO = bienRemesaDao.getBienRemesaPorId(idBien, idRemesa, false);
			if (bienRemesaVO == null) {
				bienRemesaVO = new BienRemesaVO();
				BienRemesasPK id = new BienRemesasPK();
				id.setIdBien(idBien);
				id.setIdRemesa(idRemesa);
				bienRemesaVO.setId(id);
				bienRemesaDao.guardar(bienRemesaVO);
			} else {
				resultado = new ResultBean(false);
				bienRemesaDao.evict(bienRemesaVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien");
		}
		return resultado;
	}

	@Override
	public ResultBean getListaBienesPorIdRemesa(Long idRemesa) {
		ResultBean resultado = null;
		try {
			if (idRemesa != null) {
				List<BienRemesaVO> lista = bienRemesaDao.getBienRemesaPorIdRemesa(idRemesa);
				if (lista != null && !lista.isEmpty()) {
					resultado = new ResultBean(false);
					resultado.addAttachment("listaBienesVO", lista);
				}
			} else {
				resultado = new ResultBean(true, "El id de la remesa no puede ser nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de bienes por remesa, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener la lista de bienes por remesa");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public BienRemesaDto getBienRemesaPorBienYRemesa(Long idBien, Long idRemesa) {
		if (idBien != null && idRemesa != null) {
			BienRemesaVO bienRemesaVO = bienRemesaDao.getBienRemesaPorId(idBien, idRemesa, false);
			if (bienRemesaVO != null) {
				BienRemesaDto bienRemesaDto = conversor.transform(bienRemesaVO, BienRemesaDto.class);
				if (bienRemesaVO.getBien() instanceof BienRusticoVO) {
					bienRemesaDto.getBien().setTipoBien(TipoBien.Rustico.getValorEnum());
				} else if (bienRemesaVO.getBien() instanceof BienUrbanoVO) {
					bienRemesaDto.getBien().setTipoBien(TipoBien.Urbano.getValorEnum());
				} else {
					bienRemesaDto.getBien().setTipoBien(TipoBien.Otro.getValorEnum());
					bienRemesaDto.getBien().setValorTasacion(bienRemesaDto.getValorTasacion());
				}
				bienRemesaDto.getBien().setValorDeclarado(bienRemesaDto.getValorDeclarado());
				bienRemesaDto.getBien().setTransmision(bienRemesaDto.getTransmision());
				return bienRemesaDto;
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ModeloBienDto getModeloBienPorModeloYBien(Long idBien, Long idModelo) {
		if (idBien != null && idModelo != null) {
			ModeloBienVO modeloBienVO = modeloBienDao.getModeloBienPorId(idBien, idModelo, false);
			if (modeloBienVO != null) {
				ModeloBienDto modeloBienDto = conversor.transform(modeloBienVO, ModeloBienDto.class);
				if (modeloBienVO.getBien() instanceof BienRusticoVO) {
					modeloBienDto.getBien().setTipoBien(TipoBien.Rustico.getValorEnum());
				} else if (modeloBienVO.getBien() instanceof BienUrbanoVO) {
					modeloBienDto.getBien().setTipoBien(TipoBien.Urbano.getValorEnum());
				} else {
					modeloBienDto.getBien().setTipoBien(TipoBien.Otro.getValorEnum());
					modeloBienDto.getBien().setValorTasacion(modeloBienDto.getValorTasacion());
				}
				modeloBienDto.getBien().setValorDeclarado(modeloBienDto.getValorDeclarado());
				modeloBienDto.getBien().setTransmision(modeloBienDto.getTransmision());

				return modeloBienDto;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean eliminarBienRemesa(Long idBien, Long idRemesa) {
		ResultBean resultado = null;
		try {
			BienRemesaVO bienRemesaVO = bienRemesaDao.getBienRemesaPorId(idBien, idRemesa, false);
			if (bienRemesaVO != null) {
				bienRemesaDao.borrar(bienRemesaVO);
				// comprobamos si existe alguna remesa asociada mas al bien para borrarlo
				List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getListaPorIdBien(idBien);
				List<ModeloBienVO> listaModeloBien = modeloBienDao.getListaPorIdBien(idBien);
				if ((listaBienesRemesa == null || listaBienesRemesa.isEmpty()) && (listaModeloBien == null || listaModeloBien.isEmpty())) {
					ResultBean resultBien = eliminarBien(bienRemesaVO.getBien());
					if (resultBien.getError()) {
						resultado = resultBien;
					}
				}
			} else {
				resultado = new ResultBean(true, "El bien que desea eliminar no pertenence a esta remesa");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien de la remesa, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar el bien de la remesa");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarListaModeloBien(Set<ModeloBienVO> listaBienes) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (listaBienes != null && !listaBienes.isEmpty()) {
				for (ModeloBienVO modeloBienVO : listaBienes) {
					resultado = eliminarModeloBien(modeloBienVO);
					if (resultado.getError()) {
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes del modelo, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes del modelo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarModeloBien(Long idBien, Long idModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			ModeloBienVO modeloBienVO = modeloBienDao.getModeloBienPorId(idBien, idModelo, false);
			if (modeloBienVO != null) {
				resultado = eliminarModeloBien(modeloBienVO);
				if (!resultado.getError()) {
					resultado.addAttachment("modeloVO", modeloBienVO.getModelo600_601());
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("El bien que desea eliminar no pertenence a esta remesa");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien de la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar el bien de la remesa");
		}
		return resultado;
	}

	private ResultBean eliminarModeloBien(ModeloBienVO modeloBienVO) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		modeloBienDao.borrar(modeloBienVO);
		// comprobamos si existe alguna remesa o modelo asociada mas al bien para borrarlo
		List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
		List<ModeloBienVO> listaModeloBien = modeloBienDao.getListaPorIdBien(modeloBienVO.getId().getIdBien());
		if ((listaBienesRemesa == null || listaBienesRemesa.isEmpty()) && (listaModeloBien == null || listaModeloBien.isEmpty())) {
			resultBean = eliminarBien(modeloBienVO.getBien());
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean eliminarBien(Long idBien) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idBien != null) {
				BienVO bienBBDD = bienDao.getBienPorId(idBien);
				if (bienBBDD != null) {
					ResultBean resultValEliminar = validarBienParaEliminar(bienBBDD);
					if (!resultValEliminar.getError()) {
						ResultBean resultEliminar = eliminarBien(bienBBDD);
						if (resultEliminar.getError()) {
							resultado.setError(true);
							resultado.addMensajeALista("El bien no se ha podido eliminar porque ha surgido un error.");
						}
					} else {
						resultado = resultValEliminar;
					}
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("No existe ningún bien con ese identificador para poder eliminarlo.");
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe indicar que bien desea eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar el bien.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarBienParaEliminar(BienVO bienBBDD) {
		ResultBean resultVal = new ResultBean(false);
		List<BienRemesaVO> listaBienesRemesa = bienRemesaDao.getListaPorIdBien(bienBBDD.getIdBien());
		if (listaBienesRemesa != null && !listaBienesRemesa.isEmpty()) {
			resultVal.setError(true);
			resultVal.addMensajeALista("El bien no se puede eliminar porque tiene remesas asociadas.");
			resultVal.addAttachment("existenRemesas", true);
		}
		List<ModeloBienVO> listaModeloBien = modeloBienDao.getListaPorIdBien(bienBBDD.getIdBien());
		if (listaModeloBien != null && !listaModeloBien.isEmpty()) {
			resultVal.setError(true);
			resultVal.addMensajeALista("El bien no se puede eliminar porque tiene modelos asociados.");
			resultVal.addAttachment("existenModelos", true);
		}
		List<InmuebleVO> listaInmuebleBien = inmuebleDao.getInmuebles(bienBBDD.getIdBien());
		if (listaInmuebleBien != null && !listaInmuebleBien.isEmpty()) {
			resultVal.setError(true);
			resultVal.addMensajeALista("El bien no se puede eliminar porque tiene inmuebles asociados.");
			resultVal.addAttachment("existenInmuebles", true);
		}
		return resultVal;
	}

	private ResultBean eliminarBien(BienVO bienVO) {
		ResultBean resultado = new ResultBean(false);
		if (bienVO instanceof BienRusticoVO) {
			resultado = servicioBienRustico.eliminarBienRustico((BienRusticoVO) bienVO);
		} else if (bienVO instanceof BienUrbanoVO) {
			resultado = servicioBienUrbano.eliminarBienUrbano((BienUrbanoVO) bienVO);
		} else {
			if (!bienDao.borrar(bienVO)) {
				resultado.setError(true);
				resultado.setMensaje("Error al borrar el Bien");
			}
		}
		if (bienVO.getIdDireccion() != null) {
			List<BienVO> listaBienDireccion = bienDao.getListaBienPorIdDireccion(bienVO.getIdDireccion());
			if (listaBienDireccion == null || listaBienDireccion.isEmpty()) {
				servicioDireccion.eliminar(bienVO.getIdDireccion());
			}
		}
		return resultado;
	}

	@Override
	public ResultBean convertirListaBienVoToDto(RemesaDto remesaDto, RemesaVO remesaVO) {
		ResultBean resultado = null;
		try {
			if (remesaVO != null && remesaVO.getListaBienes() != null && !remesaVO.getListaBienes().isEmpty()) {
				List<BienRemesaDto> listaAuxBienRustico = null;
				List<BienRemesaDto> listaAuxBienUrbano = null;
				List<BienRemesaDto> listaAuxOtroBien = null;
				for (BienRemesaVO bienRemesaVO : remesaVO.getListaBienes()) {
					boolean esUrbano = TipoBien.Urbano.getValorEnum().equals(bienRemesaVO.getBien().getTipoInmueble().getId().getIdTipoBien());
					boolean esRustico = TipoBien.Rustico.getValorEnum().equals(bienRemesaVO.getBien().getTipoInmueble().getId().getIdTipoBien());
					BienRemesaDto bienRemesaDto = conversor.transform(bienRemesaVO, BienRemesaDto.class);
					String provincia = null;
					if (esUrbano || esRustico) {
						if (bienRemesaDto.getBien() != null && bienRemesaDto.getBien().getDireccion() != null && bienRemesaDto.getBien().getDireccion().getIdProvincia() != null && !bienRemesaDto
								.getBien().getDireccion().getIdProvincia().isEmpty()) {
							provincia = servicioDireccionCam.obtenerNombreProvincia(bienRemesaDto.getBien().getDireccion().getIdProvincia());
							if (provincia != null && !provincia.isEmpty()) {
								bienRemesaDto.getBien().getDireccion().setNombreProvincia(provincia);
							}
							if (bienRemesaDto.getBien().getDireccion().getIdMunicipio() != null && !bienRemesaDto.getBien().getDireccion().getIdMunicipio().isEmpty()) {
								String municipio = servicioDireccionCam.obtenerNombreMunicipio(bienRemesaDto.getBien().getDireccion().getIdProvincia(), bienRemesaDto.getBien().getDireccion()
										.getIdMunicipio());
								if (municipio != null && !municipio.isEmpty()) {
									bienRemesaDto.getBien().getDireccion().setNombreMunicipio(municipio);
								}
							}
						}
					}
					if (esUrbano) {
						if (listaAuxBienUrbano == null || listaAuxBienUrbano.isEmpty()) {
							listaAuxBienUrbano = new ArrayList<BienRemesaDto>();
						}
						listaAuxBienUrbano.add(bienRemesaDto);
					} else if (esRustico) {
						if (listaAuxBienRustico == null || listaAuxBienRustico.isEmpty()) {
							listaAuxBienRustico = new ArrayList<BienRemesaDto>();
						}
						listaAuxBienRustico.add(bienRemesaDto);
					} else {
						if (listaAuxOtroBien == null || listaAuxOtroBien.isEmpty()) {
							listaAuxOtroBien = new ArrayList<BienRemesaDto>();
						}
						listaAuxOtroBien.add(bienRemesaDto);
					}
				}
				remesaDto.setListaBienesRusticos(listaAuxBienRustico);
				remesaDto.setListaBienesUrbanos(listaAuxBienUrbano);
				remesaDto.setListaOtrosBienes(listaAuxOtroBien);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir la lista de bienes, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de convertir la lista de bienes");
		}
		return resultado;
	}

	@Override
	public ResultBean convertirListaModeloBienVoToDto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto) {
		ResultBean resultado = null;
		try {
			boolean esConceptoBienes = servicioConcepto.esConceptoBienes(modeloDto.getConcepto());
			if (esConceptoBienes) {
				if (modeloVO != null && modeloVO.getListaBienes() != null && !modeloVO.getListaBienes().isEmpty()) {
					List<ModeloBienDto> listaAuxBienRustico = null;
					List<ModeloBienDto> listaAuxBienUrbano = null;
					List<ModeloBienDto> listaAuxOtroBien = null;
					BigDecimal valorDeclarado = BigDecimal.ZERO;
					for (ModeloBienVO modeloBienVO : modeloVO.getListaBienes()) {
						ModeloBienDto modeloBienDto = conversor.transform(modeloBienVO, ModeloBienDto.class);
						String provincia = null;
						if (null != modeloBienDto.getBien().getDireccion() && modeloBienDto.getBien().getDireccion().getIdProvincia() != null && !modeloBienDto.getBien().getDireccion()
								.getIdProvincia().isEmpty()) {
							provincia = servicioDireccionCam.obtenerNombreProvincia(modeloBienDto.getBien().getDireccion().getIdProvincia());
							if (provincia != null && !provincia.isEmpty()) {
								modeloBienDto.getBien().getDireccion().setNombreProvincia(provincia);
							}
							if (modeloBienDto.getBien().getDireccion().getIdMunicipio() != null && !modeloBienDto.getBien().getDireccion().getIdMunicipio().isEmpty()) {
								String municipio = servicioDireccionCam.obtenerNombreMunicipio(modeloBienDto.getBien().getDireccion().getIdProvincia(), modeloBienDto.getBien().getDireccion()
										.getIdMunicipio());
								if (municipio != null && !municipio.isEmpty()) {
									modeloBienDto.getBien().getDireccion().setNombreMunicipio(municipio);
								}
							}
						}
						if (TipoBien.Urbano.getValorEnum().equals(modeloBienVO.getBien().getTipoInmueble().getId().getIdTipoBien())) {
							if (listaAuxBienUrbano == null || listaAuxBienUrbano.isEmpty()) {
								listaAuxBienUrbano = new ArrayList<ModeloBienDto>();
							}
							listaAuxBienUrbano.add(modeloBienDto);
						} else if (TipoBien.Rustico.getValorEnum().equals(modeloBienVO.getBien().getTipoInmueble().getId().getIdTipoBien())) {
							if (listaAuxBienRustico == null || listaAuxBienRustico.isEmpty()) {
								listaAuxBienRustico = new ArrayList<ModeloBienDto>();
							}
							listaAuxBienRustico.add(modeloBienDto);
						} else {
							if (listaAuxOtroBien == null || listaAuxOtroBien.isEmpty()) {
								listaAuxOtroBien = new ArrayList<ModeloBienDto>();
							}
							listaAuxOtroBien.add(modeloBienDto);
						}
						if (modeloBienVO.getValorDeclarado() != null) {
							valorDeclarado = valorDeclarado.add(modeloBienVO.getValorDeclarado());
						}
					}
					modeloDto.setListaBienesRusticos(listaAuxBienRustico);
					modeloDto.setListaBienesUrbanos(listaAuxBienUrbano);
					modeloDto.setListaOtrosBienes(listaAuxOtroBien);
					// if(!modeloDto.isLiquidacionManual()){
					modeloDto.setValorDeclarado(valorDeclarado);
					// }
				} else {
					modeloDto.setValorDeclarado(BigDecimal.ZERO);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir la lista de bienes, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de convertir la lista de bienes");
		}
		return resultado;
	}

	@Override
	public ResultBean validarGenerarModelosBien(RemesaVO remesaVO) {
		ResultBean resultado = null;
		try {
			Boolean esConceptoConBienes = servicioConcepto.esConceptoBienes(conversor.transform(remesaVO.getConcepto(), ConceptoDto.class));
			if (esConceptoConBienes) {
				if (remesaVO.getListaBienes() == null || remesaVO.getListaBienes().isEmpty()) {
					resultado = new ResultBean(true, "Debe de seleccionar algún bien para poder generar la remesa.");
				} else {
					BigDecimal importeTotal = BigDecimal.ZERO;
					for (BienRemesaVO bienRemesaVO : remesaVO.getListaBienes()) {
						if (bienRemesaVO.getValorDeclarado() == null) {
							resultado = new ResultBean(true, "Debe de rellenar el valor declarado de los bienes para poder generar la remesa.");
							break;
						} else if (bienRemesaVO.getTransmision() == null) {
							resultado = new ResultBean(true, "Debe de rellenar el porcentaje de transmisión de los bienes para poder generar la remesa.");
							break;
						}
						importeTotal = importeTotal.add(bienRemesaVO.getValorDeclarado());
					}
					if (resultado == null || !resultado.getError()) {
						if (importeTotal.compareTo(remesaVO.getImporteTotal()) != 0) {
							resultado = new ResultBean(true, "El importe total de la remesa no coincide con la suma de los valores declarados de los bienes.");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los bienes de la remesa, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de validar los bienes de la remesa.");
		}
		return resultado;
	}

	@Override
	public ResultBean validarBienModelos(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			Boolean esConceptoBienes = servicioConcepto.esConceptoBienes(conversor.transform(modeloVO.getConcepto(), ConceptoDto.class));
			if (esConceptoBienes) {
				if (modeloVO.getListaBienes() == null || modeloVO.getListaBienes().isEmpty()) {
					resultado = new ResultBean(true, "Debe de seleccionar algún bien para poder autoliquidar el modelo.");
				} else {
					BigDecimal importeTotal = BigDecimal.ZERO;
					int contadorBonificado = 0;
					int contadorNoBonificado = 0;
					for (ModeloBienVO modeloBienVO : modeloVO.getListaBienes()) {
						if (modeloBienVO.getValorDeclarado() == null) {
							resultado = new ResultBean(true, "Debe de rellenar el valor declarado de todos los bienes.");
							break;
						} else if (modeloBienVO.getTransmision() == null) {
							resultado = new ResultBean(true, "Debe de rellenar el porcentaje de transmisión de todos los bienes.");
							break;
						}
						importeTotal = importeTotal.add(modeloBienVO.getValorDeclarado());
						if (modeloBienVO.getValorDeclarado().compareTo(new BigDecimal(250000)) == 1) {
							contadorNoBonificado++;
						} else {
							contadorBonificado++;
						}
						if (contadorBonificado != 0 && contadorNoBonificado != 0) {
							resultado = new ResultBean(true, "No se deben mezclar bienes bonificados y no bonificados.");
							break;
						}
					}
					if (importeTotal.compareTo(modeloVO.getValorDeclarado()) != 0) {
						resultado = new ResultBean(true, "El valor declarado del modelo no coincide con la suma de los valores declarados de los bienes.");
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los bienes, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar los bienes");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarModeloBien(Long idBien, Long idModelo) {
		ResultBean resultado = null;
		try {
			ModeloBienVO modeloBienVO = modeloBienDao.getModeloBienPorId(idBien, idModelo, false);
			if (modeloBienVO == null) {
				modeloBienVO = new ModeloBienVO();
				ModeloBienPK id = new ModeloBienPK();
				id.setIdBien(idBien);
				id.setIdModelo(idModelo);
				modeloBienVO.setId(id);
				modeloBienDao.guardar(modeloBienVO);
			} else {
				resultado = new ResultBean(false);
				modeloBienDao.evict(modeloBienVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien");
		}
		return resultado;
	}

	@Override
	public void guardarValorDeclaradoBien(Modelo600_601VO modeloVO) {
		BigDecimal valorDeclarado = BigDecimal.ZERO;
		if (modeloVO.getListaBienes() != null && !modeloVO.getListaBienes().isEmpty()) {
			for (ModeloBienVO modeloBienVo : modeloVO.getListaBienes()) {
				if (modeloBienVo.getValorDeclarado() != null) {
					valorDeclarado = valorDeclarado.add(modeloBienVo.getValorDeclarado());
				}
			}
			modeloVO.setValorDeclarado(valorDeclarado);
		}
	}

	@Override
	@Transactional
	/**
	 * metodo que guarda el bien dado de alta desde la pantalla de alta de bienes
	 */
	public ResultBean guardar(BienDto bien) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarBien(bien);
			if (!resultValidar.getError()) {
				Long idDireccion = null;
				ResultBean resultDireccion = null;
				resultDireccion = servicioDireccion.guardarActualizarBien(conversor.transform(bien.getDireccion(), DireccionVO.class));

				if (resultDireccion != null) {
					if (resultDireccion.getError()) {
						resultado.addMensajeALista(resultDireccion.getMensaje());
					} else {
						DireccionVO direccionVO = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
						if (direccionVO != null) {
							idDireccion = direccionVO.getIdDireccion();
						}
					}
				}

				if (TipoBien.Urbano.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
					resultado = servicioBienUrbano.guardarBienUrbanoPantalla(bien, idDireccion);
				} else if (TipoBien.Rustico.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
					resultado = servicioBienRustico.guardarBienRusticoPantalla(bien, idDireccion);
				} else if (TipoBien.Otro.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
					resultado = guardarOtroBienPantalla(bien, false, idDireccion, false);
				}
			} else {
				resultado = resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar el bien.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarBien(BienDto bien) {
		ResultBean resultado = new ResultBean(false);
		if (bien == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar los datos del bien para guardar.");
			return resultado;
		}

		if (bien.getTipoInmueble() == null || bien.getTipoInmueble().getIdTipoBien() == null || bien.getTipoInmueble().getIdTipoBien().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar el tipo de bien.");
			return resultado;
		}

		if (bien.getTipoInmueble() == null || bien.getTipoInmueble().getIdTipoInmueble() == null || bien.getTipoInmueble().getIdTipoInmueble().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar el tipo de inmueble del bien.");
			return resultado;
		}

		if (!bien.getTipoInmueble().getIdTipoBien().equals("OTRO") && bien.getDireccion() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar los datos mínimos de la dirección del bien.");
			return resultado;
		}
		if (!bien.getTipoInmueble().getIdTipoBien().equals("OTRO") && (bien.getDireccion().getIdMunicipio() == null || bien.getDireccion().getIdMunicipio().isEmpty())) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar el municipio de la dirección.");
			return resultado;
		}
		if (!bien.getTipoInmueble().getIdTipoBien().equals("OTRO") && (bien.getDireccion().getIdProvincia() == null || bien.getDireccion().getIdProvincia().isEmpty())) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar la provincia de la dirección.");
			return resultado;
		}
		ResultBean resultVal = null;
		if (TipoBien.Rustico.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
			resultVal = servicioBienRustico.validarBienRustico(bien);
			if (resultVal.getError()) {
				return resultVal;
			}
		} else if (TipoBien.Urbano.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
			resultVal = servicioBienUrbano.validarBienUrbano(bien);
			if (resultVal.getError()) {
				return resultVal;
			}
		}
		if (!bien.getTipoInmueble().getIdTipoBien().equals("OTRO"))
			convertirCombos(bien);
		return resultado;
	}

	private void convertirCombos(BienDto bien) {
		if (bien.getSistemaExplotacion() != null && StringUtils.isBlank(bien.getSistemaExplotacion().getIdSistemaExplotacion())) {
			bien.setSistemaExplotacion(null);
		}

		if (bien.getDupTri() != null && StringUtils.isBlank(bien.getDupTri())) {
			bien.setDupTri(null);
		}

		if (bien.getSituacion() != null && StringUtils.isBlank(bien.getSituacion().getIdSituacion())) {
			bien.setSituacion(null);
		}
	}

	@Override
	public List<BienBean> convertirListaEnBeanPantalla(List<BienVO> lista) {
		try {
			List<BienBean> listBienBean = new ArrayList<BienBean>();
			for (BienVO bienVO : lista) {
				BienBean bienBean = conversor.transform(bienVO, BienBean.class);
				if (bienVO.getDireccion() != null && bienVO.getDireccion().getIdProvincia() != null && !bienVO.getDireccion().getIdProvincia().isEmpty()) {
					bienBean.setProvincia(servicioDireccionCam.obtenerNombreProvincia(bienVO.getDireccion().getIdProvincia()));
					if (bienVO.getDireccion().getIdMunicipio() != null && !bienVO.getDireccion().getIdMunicipio().isEmpty()) {
						bienBean.setMunicipio(servicioDireccionCam.obtenerNombreMunicipio(bienVO.getDireccion().getIdProvincia(), bienVO.getDireccion().getIdMunicipio()));
					}
				}

				listBienBean.add(bienBean);
			}
			return listBienBean;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto BienVO a BienBean, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public List<BienDto> convertirListaEnDto(List<BienVO> lista) {
		try {
			List<BienDto> listBienDto = new ArrayList<BienDto>();
			for (BienVO bienVO : lista) {
				BienDto bienDto = conversor.transform(bienVO, BienDto.class);
				if (bienVO.getDireccion() != null && bienVO.getDireccion().getIdProvincia() != null && !bienVO.getDireccion().getIdProvincia().isEmpty()) {
					bienDto.getDireccion().setNombreProvincia(servicioDireccionCam.obtenerNombreProvincia(bienVO.getDireccion().getIdProvincia()));
					if (bienVO.getDireccion().getIdMunicipio() != null && !bienVO.getDireccion().getIdMunicipio().isEmpty()) {
						bienDto.getDireccion().setNombreMunicipio(servicioDireccionCam.obtenerNombreMunicipio(bienVO.getDireccion().getIdProvincia(), bienVO.getDireccion().getIdMunicipio()));
					}
				}

				listBienDto.add(bienDto);
			}
			return listBienDto;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto BienVO a BienBean, error: ", e);
		}
		return Collections.emptyList();
	}

	@Transactional
	public ResultBean guardarOtroBienPantalla(BienDto bienDto, boolean aplicaTasacion, Long idDireccion, boolean esModeloORemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarOtroBienPantalla(bienDto, aplicaTasacion, esModeloORemesa);
			if (resultValidar == null) {
				BienVO bienVO = conversor.transform(bienDto, BienVO.class);
				bienVO.setIdDireccion(idDireccion);
				if (bienVO.getFechaAlta() == null) {
					bienVO.setFechaAlta(new Date());
				}
				if (bienVO.getEstado() == null) {
					bienVO.setEstado(Long.valueOf(Estado.Habilitado.getValorEnum()));
				}
				bienDao.guardarOActualizar(bienVO);
				resultado.addAttachment("idBien", bienVO.getIdBien());
			} else {
				resultado = resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien de tipo Otros en pantalla, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien de tipo Otros.");
		}
		return resultado;
	}

	private ResultBean validarOtroBienPantalla(BienDto bienDto, boolean aplicaTasacion, boolean esModeloORemesa) {
		ResultBean resultado = null;

		if (StringUtils.isBlank(bienDto.getRefCatrastal())) {
			resultado = new ResultBean(true);
			resultado.setMensaje("Debe rellenar la referencia catastral.");
		}

		if (esModeloORemesa) {
			if (bienDto.getValorDeclarado() == null || bienDto.getValorDeclarado().equals(BigDecimal.ZERO)) {
				resultado = new ResultBean(true);
				resultado.setMensaje("Debe rellenar el valor declarado.");
			}
			if (bienDto.getTransmision() == null || bienDto.getTransmision().equals(BigDecimal.ZERO)) {
				resultado = new ResultBean(true);
				resultado.setMensaje("Debe rellenar el porcentaje de transmisión.");
			}
			if (!aplicaTasacion && (bienDto.getValorTasacion() == null || bienDto.getValorTasacion().equals(BigDecimal.ZERO))) {
				resultado = new ResultBean(true);
				resultado.setMensaje("Debe rellenar el valor de tasación.");
			}
		}
		return resultado;
	}

	@Override
	public Boolean esOtroBien(BienDto bienDto) {
		if (bienDto == null) {
			return false;
		}
		if (bienDto.getIdBien() != null) {
			return true;
		}
		if (bienDto.getTipoInmueble() != null && StringUtils.isNotBlank(bienDto.getTipoInmueble().getIdTipoInmueble())) {
			return true;
		}
		return false;
	}

}
