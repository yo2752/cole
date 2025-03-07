package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.model.security.utils.Cryptographer;
import org.gestoresmadrid.core.tasas.model.dao.CompraTasasDao;
import org.gestoresmadrid.core.tasas.model.dao.TasasDgtDao;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.core.tasas.model.vo.CompraTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.DesgloseCompraTasa;
import org.gestoresmadrid.core.tasas.model.vo.TasaDgtVO;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.TasasSolicitadasBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.HibernateException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaCompraTasasImpl implements ServicioPersistenciaCompraTasas, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4644974967329029200L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioPersistenciaCompraTasasImpl.class);

	@Autowired
	private CompraTasasDao compraTasasDao;

	@Autowired
	private TasasDgtDao tasasDtgDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private Cryptographer cryptographer;

	@Override
	@Transactional
	public Long guardaCompra(ResumenCompraBean resumenCompraBean) {
		CompraTasaVO compraTasaVO = null;
		if (resumenCompraBean.getIdCompra() != null) {
			compraTasaVO = compraTasasDao.get(resumenCompraBean.getIdCompra(), resumenCompraBean.getIdContrato(), true);
			conversor.transform(resumenCompraBean, compraTasaVO, "actualizacionCompraTasas");
			if (compraTasaVO.getDesgloseCompraTasas() == null) {
				compraTasaVO.setDesgloseCompraTasas(new ArrayList<DesgloseCompraTasa>());
			}
			List<DesgloseCompraTasa> desglosesBorrar = new ArrayList<DesgloseCompraTasa>();
			for (DesgloseCompraTasa desglose : compraTasaVO.getDesgloseCompraTasas()) {
				boolean mantener = mantenerDesgloseActualizado(desglose, resumenCompraBean.getListaResumenCompraBean());
				if (!mantener) {
					desglosesBorrar.add(desglose);
				}
			}
			compraTasaVO.getDesgloseCompraTasas().removeAll(desglosesBorrar);
			if (resumenCompraBean.getListaResumenCompraBean() != null && !resumenCompraBean.getListaResumenCompraBean().isEmpty()) {
				List<DesgloseCompraTasa> desgloseCompraNuevo = conversor.transform(resumenCompraBean.getListaResumenCompraBean(), DesgloseCompraTasa.class);
				compraTasaVO.getDesgloseCompraTasas().addAll(desgloseCompraNuevo);
			}
		} else {
			compraTasaVO = conversor.transform(resumenCompraBean, CompraTasaVO.class);
		}
		if (compraTasaVO != null) {
			if (compraTasaVO.getDesgloseCompraTasas() != null) {
				for (DesgloseCompraTasa d : compraTasaVO.getDesgloseCompraTasas()) {
					d.setCompraTasa(compraTasaVO);
				}
			}
			encriptarDatosBancarios(compraTasaVO);
			try {
				if (compraTasaVO.getIdCompra() != null) {
					compraTasaVO = compraTasasDao.actualizar(compraTasaVO);
				} else {
					compraTasaVO.setFechaAlta(Calendar.getInstance().getTime());
					compraTasaVO = compraTasasDao.guardarCompraTasa(compraTasaVO);
				}
				if (resumenCompraBean.getListaResumenCompraBean() != null) {
					resumenCompraBean.getListaResumenCompraBean().clear();
				}
				conversor.transform(compraTasaVO, resumenCompraBean);
				desencriptarDatosBancarios(resumenCompraBean);
			} catch (HibernateException e) {
				throw new TransactionalException(e);
			}
		}
		return compraTasaVO != null ? compraTasaVO.getIdCompra() : null;
	}

	private boolean mantenerDesgloseActualizado(DesgloseCompraTasa desglose, List<TasasSolicitadasBean> listaResumenCompraBean) {
		if (listaResumenCompraBean != null) {
			TasasSolicitadasBean tasaEncontrada = null;
			for (TasasSolicitadasBean t : listaResumenCompraBean) {
				if (desglose.getTasaDgt().getCodigoTasa().equals(t.getCodigoTasa())) {
					tasaEncontrada = t;
					break;
				}
			}
			if (tasaEncontrada != null) {
				desglose.setCantidad(tasaEncontrada.getCantidad());
				listaResumenCompraBean.remove(tasaEncontrada);
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean actualizarEstado(EstadoCompra estado, Long idCompra, String respuesta) {
		boolean result = false;
		if (estado != null && idCompra != null) {
			String[] namedParemeters = { "estado", "respuesta", "idCompra" };
			Object[] namedValues = { new BigDecimal(estado.getCodigo()), respuesta, idCompra };
			try {
				result = BigDecimal.ONE.intValue() == compraTasasDao.executeNamedQuery(CompraTasaVO.CAMBIO_ESTADO, namedParemeters, namedValues);
			} catch (HibernateException e) {
				throw new TransactionalException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public boolean actualizarReferenciaPropia(String refPropia, Long idCompra) {
		boolean result = false;
		if (idCompra != null) {
			String[] namedParemeters = { "refPropia", "idCompra" };
			Object[] namedValues = { refPropia, idCompra };
			try {
				result = BigDecimal.ONE.intValue() == compraTasasDao.executeNamedQuery(CompraTasaVO.CAMBIO_REFERENCIA_PROPIA, namedParemeters, namedValues);
			} catch (HibernateException e) {
				throw new TransactionalException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ResumenCompraBean getCompra(Long idCompra, Long idContrato, boolean desglose) {
		ResumenCompraBean resumenCompraBean = null;
		try {
			CompraTasaVO compraVO = compraTasasDao.get(idCompra, idContrato, desglose);
			if (compraVO != null) {
				resumenCompraBean = conversor.transform(compraVO, ResumenCompraBean.class);
				desencriptarDatosBancarios(resumenCompraBean);
			}
		} catch (HibernateException e) {
			LOG.error("Error recuperando compra con identificador: " + idCompra, e);
		}
		return resumenCompraBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResumenCompraBean iniciarNuevaCompra() {
		ResumenCompraBean resumenCompraBean = new ResumenCompraBean();

		resumenCompraBean.setListaCompraTasasCirculacion(new ArrayList<TasasSolicitadasBean>());
		resumenCompraBean.setListaCompraTasasConduccion(new ArrayList<TasasSolicitadasBean>());
		resumenCompraBean.setListaCompraTasasFormacionReconocimiento(new ArrayList<TasasSolicitadasBean>());
		resumenCompraBean.setListaCompraTasasOtrasTarifas(new ArrayList<TasasSolicitadasBean>());

		List<TasaDgtVO> listaTasasDGT = null;
		try {
			listaTasasDGT = tasasDtgDao.buscar(null);
		} catch (HibernateException e) {
			Log.error("Error recuperando los datos maestros de tasas dgt", e);
		}
		if (listaTasasDGT != null) {
			for (TasaDgtVO tasaDgtVO : listaTasasDGT) {
				TasasSolicitadasBean tasasSolicitadasBean = new TasasSolicitadasBean();
				int grupo = tasaDgtVO.getGrupo();
				tasasSolicitadasBean.setCodigoTasa(tasaDgtVO.getCodigoTasa());
				tasasSolicitadasBean.setGrupo(tasaDgtVO.getGrupo());
				tasasSolicitadasBean.setDescripcion(tasaDgtVO.getDescripcion());
				tasasSolicitadasBean.setImporte(tasaDgtVO.getImporte());
				tasasSolicitadasBean.setCantidad(BigDecimal.ZERO);

				switch (grupo) {
					case 1:
						resumenCompraBean.getListaCompraTasasCirculacion().add(tasasSolicitadasBean);
						break;
					case 2:
						resumenCompraBean.getListaCompraTasasConduccion().add(tasasSolicitadasBean);
						break;
					case 3:
						resumenCompraBean.getListaCompraTasasFormacionReconocimiento().add(tasasSolicitadasBean);
						break;
					case 4:
						resumenCompraBean.getListaCompraTasasOtrasTarifas().add(tasasSolicitadasBean);
						break;
				}
			}
		}

		return resumenCompraBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResumenCompraBean resumenCompra(ResumenCompraBean resumenCompraBean) {
		FormaPago formaPago = FormaPago.convertir(resumenCompraBean.getFormaPago());
		if (FormaPago.CCC.equals(formaPago)) {
			resumenCompraBean.setDatosBancarios(resumenCompraBean.getEntidad() != null ? resumenCompraBean.getEntidad() : "" + resumenCompraBean.getOficina() != null ? resumenCompraBean.getOficina()
					: "" + resumenCompraBean.getDc() != null ? resumenCompraBean.getDc() : "" + resumenCompraBean.getNumeroCuentaPago() != null ? resumenCompraBean.getNumeroCuentaPago() : "");
		} else if (FormaPago.TARJETA.equals(formaPago)) {
			resumenCompraBean.setDatosBancarios(resumenCompraBean.getTarjetaNum1() + resumenCompraBean.getTarjetaNum2() + resumenCompraBean.getTarjetaNum3() + resumenCompraBean.getTarjetaNum4()
					+ resumenCompraBean.getTarjetaCcv() + resumenCompraBean.getTarjetaMes() + resumenCompraBean.getTarjetaAnio() + resumenCompraBean.getTarjetaEntidad());
		} else if (FormaPago.IBAN.equals(formaPago)) {
			resumenCompraBean.setDatosBancarios(resumenCompraBean.getIban());
		}

		resumenCompraBean.setListaResumenCompraBean(new ArrayList<TasasSolicitadasBean>());
		if (resumenCompraBean.getListaCompraTasasCirculacion() != null) {
			for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasCirculacion()) {
				if (t.getCantidad() != null && t.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
					resumenCompraBean.getListaResumenCompraBean().add(t);
				}
			}
		}
		if ( resumenCompraBean.getListaCompraTasasConduccion() != null) {
			for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasConduccion()) {
				if (t.getCantidad() != null && t.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
					resumenCompraBean.getListaResumenCompraBean().add(t);
				}
			}
		}
		if (resumenCompraBean.getListaCompraTasasFormacionReconocimiento() != null) {
			for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasFormacionReconocimiento()) {
				if (t.getCantidad() != null && t.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
					resumenCompraBean.getListaResumenCompraBean().add(t);
				}
			}
		}
		if (resumenCompraBean.getListaCompraTasasOtrasTarifas() != null) {
			for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasOtrasTarifas()) {
				if (t.getCantidad() != null && t.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
					resumenCompraBean.getListaResumenCompraBean().add(t);
				}
			}
		}

		actualizarTasasSolicitadas(resumenCompraBean.getListaResumenCompraBean());
		BigDecimal importe = calcularImporteInterno(resumenCompraBean.getListaResumenCompraBean());

		if (LOG.isDebugEnabled()) {
			LOG.debug("Se ha calculado el siguiente importe para compra de tasas: " + importe.toString());
		}
		resumenCompraBean.setImporteTotalTasas(importe);

		return resumenCompraBean;
	}

	@Override
	@Transactional
	public boolean anular(Long idCompra, Long idContrato) {
		boolean result = false;
		CompraTasaVO compra = compraTasasDao.get(idCompra, idContrato, false);
		if (estadoAnulable(compra) && pagoNoRealizado(compra)) {
			compra.setEstado(new BigDecimal(EstadoCompra.ANULADO.getCodigo()));
			//17012017_0025117_Borrar importe compra tasa al eliminar compra 
			compra.setImporteTotalTasas(BigDecimal.ZERO);
			try {
				compraTasasDao.actualizar(compra);
				result = true;
			} catch(HibernateException e) {
				LOG.error("No se ha podido actualizar", e);
			}
		}
		return result;
	}

	private boolean estadoAnulable(CompraTasaVO compra) {
		return compra != null && !new BigDecimal(EstadoCompra.FINALIZADO_OK.getCodigo()).equals(compra.getEstado());
	}

	private boolean pagoNoRealizado(CompraTasaVO compra) {
		return noCsv(compra) && noAutoliquidacion(compra) && noNrc(compra);
	}

	private boolean noNrc(CompraTasaVO compra) {
		return compra.getNrc() == null || compra.getNrc().isEmpty();
	}

	private boolean noAutoliquidacion(CompraTasaVO compra) {
		return compra.getNAutoliquidacion() == null || compra.getNAutoliquidacion().isEmpty();
	}

	private boolean noCsv(CompraTasaVO compra) {
		return compra.getCsv() == null || compra.getCsv().isEmpty();
	}

	/**
	 * Encripta los datos bancarios
	 * @param compraTasasBean
	 */
	private void encriptarDatosBancarios(CompraTasaVO compraTasa) {
		if (compraTasa != null && compraTasa.getDatosBancarios() != null && !compraTasa.getDatosBancarios().isEmpty()) {
			compraTasa.setDatosBancarios(cryptographer.encrypt(compraTasa.getDatosBancarios()));
		}
	}

	/**
	 * Desencripta los datos bancarios
	 * @param compraTasaVO
	 * @return
	 */
	private void desencriptarDatosBancarios(ResumenCompraBean compraBean) {

		if (compraBean != null) {
			// Datos bancarios
			if (compraBean.getDatosBancarios() != null && !compraBean.getDatosBancarios().isEmpty()) {
				compraBean.setDatosBancarios(cryptographer.decrypt(compraBean.getDatosBancarios()));
				FormaPago formaPago = FormaPago.convertir(compraBean.getFormaPago());
				if (FormaPago.CCC.equals(formaPago) && compraBean.getDatosBancarios().length() == 20) {
					compraBean.setEntidad(compraBean.getDatosBancarios().substring(0, 4));
					compraBean.setOficina(compraBean.getDatosBancarios().substring(4, 8));
					compraBean.setDc(compraBean.getDatosBancarios().substring(8, 10));
					compraBean.setNumeroCuentaPago(compraBean.getDatosBancarios().substring(10, 20));
				} else if (FormaPago.TARJETA.equals(formaPago) && compraBean.getDatosBancarios().length() == 29) {
					compraBean.setTarjetaNum1(compraBean.getDatosBancarios().substring(0, 4));
					compraBean.setTarjetaNum2(compraBean.getDatosBancarios().substring(4, 8));
					compraBean.setTarjetaNum3(compraBean.getDatosBancarios().substring(8, 12));
					compraBean.setTarjetaNum4(compraBean.getDatosBancarios().substring(12, 16));
					compraBean.setTarjetaCcv(compraBean.getDatosBancarios().substring(16, 19));
					compraBean.setTarjetaMes(compraBean.getDatosBancarios().substring(19, 21));
					compraBean.setTarjetaAnio(compraBean.getDatosBancarios().substring(21, 25));
					compraBean.setTarjetaEntidad(compraBean.getDatosBancarios().substring(25));
				} else if (FormaPago.IBAN.equals(formaPago)) {
					compraBean.setIban(compraBean.getDatosBancarios());
				}
			}
		}
	}

	/**
	 * @param listaTasas
	 * @return
	 */
	private BigDecimal calcularImporteInterno(List<TasasSolicitadasBean> listaTasas) {
		BigDecimal importe = new BigDecimal(0);
		for (TasasSolicitadasBean tasasSolicitadas : listaTasas) {
			importe = importe.add(tasasSolicitadas.getSubTotal());
		}
		return importe;
	}

	/**
	 * Actualiza el valor de las tasas solicitadas con el valor almacenado en BBDD, el metodo publico que acabe llamando a este metodo, debe estar anotado como transactional
	 * @param listaTasas
	 * @return
	 */
	private List<TasasSolicitadasBean> actualizarTasasSolicitadas(List<TasasSolicitadasBean> listaTasas) {
		// Obtener importe desde el DAO
		try {
			for (TasasSolicitadasBean tasasSolicitadas : listaTasas) {
				TasaDgtVO tasa = tasasDtgDao.get(tasasSolicitadas.getCodigoTasa());
				tasasSolicitadas.setGrupo(tasa.getGrupo());
				tasasSolicitadas.setTipo(tasa.getTipo());
				tasasSolicitadas.setImporte(tasa.getImporte());
			}
		} catch (HibernateException e) {

		}
		return listaTasas;
	}

	public CompraTasasDao getCompraTasasDao() {
		return compraTasasDao;
	}

	public void setCompraTasasDao(CompraTasasDao compraTasasDao) {
		this.compraTasasDao = compraTasasDao;
	}

	public Cryptographer getCryptographer() {
		return cryptographer;
	}

	public void setCryptographer(Cryptographer cryptographer) {
		this.cryptographer = cryptographer;
	}

	public TasasDgtDao getTasasDtgDao() {
		return tasasDtgDao;
	}

	public void setTasasDtgDao(TasasDgtDao tasasDtgDao) {
		this.tasasDtgDao = tasasDtgDao;
	}

}
