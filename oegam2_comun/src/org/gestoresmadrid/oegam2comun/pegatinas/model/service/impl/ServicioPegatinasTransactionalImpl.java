package org.gestoresmadrid.oegam2comun.pegatinas.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasEvolucionDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasNotificaDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasPeticionEvolucionDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockHistoricoDao;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasStockPeticionesDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPegatinasTransactionalImpl implements ServicioPegatinasTransactional {
	
	@Autowired
	private PegatinasStockDao pegatinasStockDao;
	
	@Autowired
	private PegatinasStockHistoricoDao pegatinasStockHistoricoDao;
	
	@Autowired
	private PegatinasStockPeticionesDao  pegatinasStockPeticionesDao;
	
	@Autowired
	private PegatinasPeticionEvolucionDao pegatinasPeticionEvolucionDao;
	
	@Autowired
	private PegatinasDao pegatinasDao;
	
	@Autowired
	private PegatinasEvolucionDao pegatinasEvolucionDao;
	
	@Autowired
	private PegatinasNotificaDao pegatinasNotificaDao;
	
	@Override
	@Transactional
	public List<PegatinasStockVO> getListaStock(){
		return pegatinasStockDao.getListaStock();
	}

	@Override
	@Transactional
	public PegatinasStockVO getStockByTipoPegatina(String tipo) {
		return pegatinasStockDao.getStockByTipoPegatina(tipo);
	}
	
	@Override
	@Transactional
	public PegatinasStockVO getStockByIdStock(int idStock) {
		return pegatinasStockDao.getStockByIdStock(idStock);
	}

	@Override
	@Transactional
	public int guardarPeticionStock(PegatinasStockPeticionesVO pegatinasStockPeticion) {
		return (Integer) pegatinasStockPeticionesDao.guardar(pegatinasStockPeticion);
	}

	@Override
	@Transactional
	public PegatinasStockPeticionesVO getPeticionStockById(String id){
		return pegatinasStockPeticionesDao.getPeticionStockById(id);
	}
	
	@Override
	@Transactional
	public void cambiarEstadoPeticionById(String id, String nombreEnum, Integer valorEnum) {
		PegatinasStockPeticionesVO peticionStock = pegatinasStockPeticionesDao.getPeticionStockById(id);
		peticionStock.setDescrEstado(nombreEnum);
		peticionStock.setEstado(valorEnum);
		pegatinasStockPeticionesDao.actualizar(peticionStock);
	}
	
	@Override
	@Transactional
	public int restarStockByTipo(String tipo){
		return pegatinasStockDao.restarStockByTipo(tipo);
	}

	@Override
	@Transactional
	public void insertarHistorico(int idStock, String accion, int stockRestante, String tipo, String matricula) {
		pegatinasStockHistoricoDao.insertarHistorico(idStock, accion, stockRestante, tipo, matricula);
		
	}

	@Override
	@Transactional
	public void actualizarPegatinaIdentificador(String idPeticion, String identificadorPeticion, String nombreEnum, Integer valorEnum) {
		PegatinasStockPeticionesVO peticionStock = pegatinasStockPeticionesDao.getPeticionStockById(idPeticion);
		peticionStock.setIdentificador(identificadorPeticion);
		peticionStock.setDescrEstado(nombreEnum);
		peticionStock.setEstado(valorEnum);
		pegatinasStockPeticionesDao.actualizar(peticionStock);
		
	}

	@Override
	@Transactional
	public void cambiarEstadoPeticionByIdentificadorPeticion(String identificador, String nombreEnum, Integer valorEnum) {
		PegatinasStockPeticionesVO peticionStock = pegatinasStockPeticionesDao.getPeticionStockByIdentificador(identificador);
		peticionStock.setEstado(valorEnum);
		peticionStock.setDescrEstado(nombreEnum);
		pegatinasStockPeticionesDao.actualizar(peticionStock);
		
	}

	@Override
	@Transactional
	public PegatinasStockPeticionesVO getIdStockByIdentificador(String identificador) {
		return pegatinasStockPeticionesDao.getPeticionStockByIdentificador(identificador);
		
	}

	@Override
	@Transactional
	public int actualizarStockByIdStock(Integer idStock, Integer numPegatinas) {
		PegatinasStockVO pegatinasStock = pegatinasStockDao.getStockByIdStock(idStock);
		int actual = pegatinasStock.getStock() + numPegatinas;
		pegatinasStock.setStock(actual);
		pegatinasStockDao.actualizar(pegatinasStock);
		return actual;
	}

	@Override
	@Transactional
	public void guardarPeticionStockEvo(PegatinasPeticionEvolucionVO pegatinaPeticionEvo) {
		pegatinasPeticionEvolucionDao.guardar(pegatinaPeticionEvo);
	}

	@Override
	@Transactional
	public void guardarPegatinasPeticionEvolucion(String idStock, String estado, String observaciones) {
		PegatinasPeticionEvolucionVO pegatinasPeticionEvolucionVO = new PegatinasPeticionEvolucionVO();
		pegatinasPeticionEvolucionVO.setEstado(estado);
		pegatinasPeticionEvolucionVO.setIdStockPeti(Integer.valueOf(idStock));
		pegatinasPeticionEvolucionVO.setFecha(new Date());
		pegatinasPeticionEvolucionVO.setObservaciones(observaciones);
		pegatinasPeticionEvolucionDao.guardar(pegatinasPeticionEvolucionVO);
		
	}

	@Override
	@Transactional
	public void guardarPegatinasPeticionEvolucionByIdentificador(String identificador, String estado, String observaciones) {
		PegatinasStockPeticionesVO pegatinasStock = getIdStockByIdentificador(identificador);
		PegatinasPeticionEvolucionVO pegatinasPeticionEvolucionVO = new PegatinasPeticionEvolucionVO();
		pegatinasPeticionEvolucionVO.setEstado(estado);
		pegatinasPeticionEvolucionVO.setIdStockPeti(pegatinasStock.getIdPeticion());
		pegatinasPeticionEvolucionVO.setFecha(new Date());
		pegatinasPeticionEvolucionVO.setObservaciones(observaciones);
		pegatinasPeticionEvolucionDao.guardar(pegatinasPeticionEvolucionVO);
		
	}

	@Override
	@Transactional
	public List<PegatinasPeticionEvolucionVO> getPegatinasPeticionEvolucionByIdPeticion(int idPeticion) {
		return pegatinasPeticionEvolucionDao.getPegatinasPeticionEvolucionByIdPeticion(idPeticion);
	}
	
	@Override
	@Transactional
	public int restarStockByTipoyJefatura(String tipo, String jefatura){
		return pegatinasStockDao.restarStockByTipoyJefatura(tipo, jefatura);
	}

	@Override
	@Transactional
	public PegatinasStockVO getStockByJefaturayTipoPegatina(String tipo, String jefatura) {
		return pegatinasStockDao.getStockByJefaturayTipoPegatina(tipo, jefatura);
		
		
	}

	@Override
	@Transactional
	public PegatinasStockVO getStockByTipoPegatinaJefatura(String tipo, String jefatura) {
		return pegatinasStockDao.getStockByTipoPegatinaJefatura(tipo, jefatura);
	}

	@Override
	@Transactional
	public int insertarPegatinasStock(PegatinasStockVO pegatinasStock) {
		return (Integer) pegatinasStockDao.guardar(pegatinasStock);
	}
	
	@Override
	@Transactional
	public List<PegatinasVO> getAllPegatinas(){
		return pegatinasDao.getAllPegatinas();
	}
	
	@Override
	@Transactional
	public int insertarPegatina(PegatinasVO pegatina){
		return (Integer) pegatinasDao.guardar(pegatina);
	}

	@Override
	@Transactional
	public void insertarPegatinasNotificacion(PegatinasNotificaVO pegatinasNotificacion){
		pegatinasNotificaDao.guardar(pegatinasNotificacion);
	}
	
	@Override
	@Transactional
	public List<PegatinasEvolucionVO> getPegatinasEvolucionByIdPegatina(Integer idPegatina){
		return pegatinasEvolucionDao.getPegatinasEvolucionByIdPegatina(idPegatina);
	}
	
	@Override
	@Transactional
	public void insertarPegatinasEvolucion(PegatinasEvolucionVO evolucion){
		pegatinasEvolucionDao.guardar(evolucion);
	}
	
	@Override
	@Transactional
	public PegatinasVO getPegatinaByIdPegatina(Integer idPegatina){
		return pegatinasDao.getPegatinaByIdPegatina(idPegatina);
	}
	
	@Override
	@Transactional
	public void cambiarEstadoPegatina(String idPegatina, Integer estado, String descrEstado){
		pegatinasDao.cambiarEstadoPegatina(idPegatina, estado, descrEstado);
	}

	@Override
	@Transactional
	public List<PegatinasNotificaVO> getAllPegatinasHoyByJefatura(String jefatura) {
		return pegatinasNotificaDao.getAllPegatinasHoyByJefatura(jefatura);
	}

	@Override
	@Transactional
	public String getNumExpedienteByMatricula(String matricula) {
		return pegatinasDao.getNumExpedienteByMatricula(matricula);
	}
	
	@Override
	@Transactional
	public PegatinasVO getPegatinaByExpediente(BigDecimal expediente) {
		return pegatinasDao.getPegatinaByExpediente(expediente);
	}

}
