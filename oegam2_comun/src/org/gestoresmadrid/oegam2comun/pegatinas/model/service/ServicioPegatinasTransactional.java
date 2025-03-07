package org.gestoresmadrid.oegam2comun.pegatinas.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;

public interface ServicioPegatinasTransactional {
	
	public List<PegatinasStockVO> getListaStock();

	public PegatinasStockVO getStockByTipoPegatina(String tipo);
	
	public PegatinasStockVO getStockByJefaturayTipoPegatina(String tipo, String jefatura);
	
	public PegatinasStockVO getStockByIdStock(int idStock);
	
	public int guardarPeticionStock(PegatinasStockPeticionesVO pegatinasStockPeticion);
	
	public PegatinasStockPeticionesVO getPeticionStockById(String id);

	public void cambiarEstadoPeticionById(String id, String nombreEnum, Integer valorEnum);
	
	public int restarStockByTipo(String tipo);
	
	public void insertarHistorico(int idStock, String accion, int stockRestante, String tipo, String matricula);

	public void actualizarPegatinaIdentificador(String string, String identificadorPeticion, String nombreEnum, Integer valorEnum);

	public void cambiarEstadoPeticionByIdentificadorPeticion(String string, String nombreEnum, Integer valorEnum);
	
	public PegatinasStockPeticionesVO getIdStockByIdentificador(String identificador);

	public int actualizarStockByIdStock(Integer idStock, Integer numPegatinas);

	public void guardarPeticionStockEvo(PegatinasPeticionEvolucionVO pegatinaPeticionEvo);

	public void guardarPegatinasPeticionEvolucion(String idStock, String estado, String observaciones);

	public void guardarPegatinasPeticionEvolucionByIdentificador(String identificador, String estado, String observaciones);

	public List<PegatinasPeticionEvolucionVO> getPegatinasPeticionEvolucionByIdPeticion(int idPeticion);

	public int restarStockByTipoyJefatura(String tipo, String jefatura);
	
	public PegatinasStockVO getStockByTipoPegatinaJefatura(String tipo, String jefatura);

	public int insertarPegatinasStock(PegatinasStockVO pegatinasStock);
	
	public List<PegatinasVO> getAllPegatinas();
	
	public List<PegatinasNotificaVO> getAllPegatinasHoyByJefatura(String jefatura);
	
	public int insertarPegatina(PegatinasVO pegatina);
	
	public void insertarPegatinasNotificacion(PegatinasNotificaVO pegatinasNotificacion);
	
	public void insertarPegatinasEvolucion(PegatinasEvolucionVO evolucion);
	
	public List<PegatinasEvolucionVO> getPegatinasEvolucionByIdPegatina(Integer idPegatina);
	
	public PegatinasVO getPegatinaByIdPegatina(Integer idPegatina);
	
	public void cambiarEstadoPegatina(String idPegatina, Integer estado, String descrEstado);

	public String getNumExpedienteByMatricula(String matricula);
	
	public PegatinasVO getPegatinaByExpediente(BigDecimal expediente);

}