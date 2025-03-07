package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.bean.PairBean;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class TramiteRegistroDaoImpl extends GenericDaoImplHibernate<TramiteRegistroVO> implements TramiteRegistroDao {

	private static final long serialVersionUID = 1657209090287334872L;

	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	private List<String> letras = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "Y", "Z");

	private List<String> numeros = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public TramiteRegistroVO getTramite(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		criteria.createAlias("sociedad", "sociedad", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("librosRegistro", "librosRegistro", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("facturasRegistro", "facturasRegistro", CriteriaSpecification.LEFT_JOIN);

		return (TramiteRegistroVO) criteria.uniqueResult();

	}

	@Override
	public TramiteRegistroVO getTramiteIdCorpme(String idTramiteCorpme) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		if (idTramiteCorpme != null && !idTramiteCorpme.isEmpty()) {
			criteria.add(Restrictions.eq("idTramiteCorpme", idTramiteCorpme));
		}
		criteria.createAlias("sociedad", "sociedad", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("librosRegistro", "librosRegistro", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("facturasRegistro", "facturasRegistro", CriteriaSpecification.LEFT_JOIN);

		return (TramiteRegistroVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteRegistroVO> getTramiteIdCorpmeConCodigoRegistro(String idTramiteCorpme, String codigoRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		if (idTramiteCorpme != null && !idTramiteCorpme.isEmpty()) {
			criteria.add(Restrictions.eq("idTramiteCorpme", idTramiteCorpme));
		}
		if (codigoRegistro != null && !codigoRegistro.isEmpty()) {
			criteria.add(Restrictions.eq("registro.idRegistro", codigoRegistro));
		}
		criteria.createAlias("sociedad", "sociedad", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("librosRegistro", "librosRegistro", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("facturasRegistro", "facturasRegistro", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("registro", "registro", CriteriaSpecification.LEFT_JOIN);

		return (List<TramiteRegistroVO>) criteria.list();
	}

	@Override
	public BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception {
		String numExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		numExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "10";
		} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "20";
		} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "30";
		} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "40";
		} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "50";
		} else if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "60";
		} else if (TipoTramiteRegistro.MODELO_7.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "70";
		} else if (TipoTramiteRegistro.MODELO_8.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "80";
		} else if (TipoTramiteRegistro.MODELO_9.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "90";
		} else if (TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "00";
		} else if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "11";
		} else if (TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "12";
		} else if (TipoTramiteRegistro.MODELO_13.getValorEnum().equals(tipoTramiteRegistro)) {
			numExpediente += "13";
		}

		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		criteria.add(Restrictions.eq("tipoTramite", tipoTramiteRegistro));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaCreacion", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("idTramiteRegistro"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(numExpediente + "000");
		}
		maximoExistente = new BigDecimal(maximoExistente.longValue() + 1);
		return maximoExistente;
	}

	@Override
	public String generarIdTramiteCorpme(String numColegiado, String cif) throws Exception {
		String idColegio = gestorPropiedades.valorPropertie("corpmeEdoc.id.colegio");
		String numExpediente = cif + idColegio + numColegiado;

		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.addOrder(Order.desc("fechaCreacion"));

		@SuppressWarnings("unchecked")
		List<TramiteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			TramiteRegistroVO tramite = lista.get(0);
			String numExpedienteUltimo = tramite.getIdTramiteCorpme();

			if (numExpedienteUltimo != null && !numExpedienteUltimo.isEmpty()) {
				String secuencia = numExpedienteUltimo.substring(15);
				// Si no es 5 es porque es un tramite antiguo
				if (secuencia != null && secuencia.length() == 5) {
					String caracter1 = String.valueOf(secuencia.charAt(secuencia.length() - 1));
					String caracter2 = String.valueOf(secuencia.charAt(secuencia.length() - 2));
					String caracter3 = String.valueOf(secuencia.charAt(secuencia.length() - 3));
					String caracter4 = String.valueOf(secuencia.charAt(secuencia.length() - 4));
					String caracter5 = String.valueOf(secuencia.charAt(secuencia.length() - 5));

					PairBean pair = obtenerCaracter(secuencia.charAt(secuencia.length() - 1), secuencia.charAt(secuencia.length() - 2), caracter1);
					caracter1 = pair.getCaracter();
					if (pair.isCambio()) {
						pair = obtenerCaracter(secuencia.charAt(secuencia.length() - 2), secuencia.charAt(secuencia.length() - 3), caracter2);
						caracter2 = pair.getCaracter();
						if (pair.isCambio()) {
							pair = obtenerCaracter(secuencia.charAt(secuencia.length() - 3), secuencia.charAt(secuencia.length() - 4), caracter3);
							caracter3 = pair.getCaracter();
							if (pair.isCambio()) {
								pair = obtenerCaracter(secuencia.charAt(secuencia.length() - 4), secuencia.charAt(secuencia.length() - 5), caracter4);
								caracter4 = pair.getCaracter();
								if (pair.isCambio()) {
									pair = obtenerCaracter(secuencia.charAt(secuencia.length() - 5), ' ', caracter5);
									caracter5 = pair.getCaracter();
								}
							}
						}
					}
					numExpediente += caracter5 + caracter4 + caracter3 + caracter2 + caracter1;
				} else {
					numExpediente = null;
				}
			} else {
				numExpediente += "A0000";
			}
		} else {
			numExpediente += "A0000";
		}
		return numExpediente;
	}

	private PairBean obtenerCaracter(char caracter, char caracterAnterior, String caracterActual) {
		PairBean pairBean = new PairBean();
		if (Character.isDigit(caracter)) {
			if (caracter == '9') {
				if (caracterAnterior != ' ' && Character.isLetter(caracterAnterior)) {
					pairBean.setCaracter("A");
					pairBean.setCambio(false);
				} else {
					pairBean.setCaracter("0");
					pairBean.setCambio(true);
				}
			} else {
				int pos = numeros.indexOf(caracterActual);
				pairBean.setCaracter(numeros.get(pos + 1));
				pairBean.setCambio(false);
			}
		} else if (Character.isLetter(caracter)) {
			if (caracter == 'Z') {
				pairBean.setCaracter("0");
				pairBean.setCambio(true);
			} else {
				int pos = letras.indexOf(caracterActual);
				pairBean.setCaracter(letras.get(pos + 1));
				pairBean.setCambio(false);
			}
		}
		return pairBean;
	}

	@Override
	public TramiteRegistroVO cambiarEstado(BigDecimal idTramiteRegistro, BigDecimal estadoNuevo) {
		TramiteRegistroVO tramite = getTramite(idTramiteRegistro);
		tramite.setEstado(estadoNuevo);
		tramite.setFechaUltEstado(new Date());
		tramite = guardarOActualizar(tramite);
		if (tramite != null) {
			return tramite;
		}
		return null;
	}

	@Override
	public TramiteRegistroVO getTramiteJustificante(String id) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegistroVO.class);
		criteria.add(Restrictions.eq("idTramiteOrigen", new BigDecimal(id)));
		return (TramiteRegistroVO) criteria.uniqueResult();
	}
}
