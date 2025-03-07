package org.gestoresmadrid.core.trafico.justificante.profesional.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.hibernate.criterion.ProjectionList;

public interface JustificanteProfDao extends GenericDao<JustificanteProfVO>, Serializable {

	List<JustificanteProfVO> getJustificante(Long idJustificanteInterno, BigDecimal numExpediente, BigDecimal estado);

	JustificanteProfVO getIdJustificanteInternoPorIdJustificante(String idJustificante);

	JustificanteProfVO getJustificanteCodigoVerificacion(String codigoVerficiacion);

	List<JustificanteProfVO> getJustificantes(String numColegiado, String matricula, String nif);

	JustificanteProfVO getJustificanteProfPorIdInterno(Long idJustificanteInterno);

	/**
	 * metodo que comprueba si existe algún justificante con los datos indicados
	 * @param idJustificanteInterno id del justificante, que si se indica servira para excluir ese justificante de la query
	 * @param matricula matricula del vehiculo
	 * @param nif nif del titular
	 * @param fecha para comprobar si existe un justificante en estado valido
	 * @return
	 */
	Integer comprobarJustificantesPorMatriculaNifYFecha(Long idJustificanteInterno, String matricula, String nif, Date fecha);

	Integer existenJustificantesPorMatriculaEnEstadoPendiente(Long idJustificanteInterno, String matricula);

	JustificanteProfVO getJustificanteProfPorNumExpediente(BigDecimal numExpediente, Boolean justificanteCompleto, BigDecimal estadoJustificante);

	JustificanteProfVO getJustificanteProfPorIdJustificante(BigDecimal idJustificante);

	List<JustificanteProfVO> obtenerJustificantesNoFinalizados(Date fechaHoy, Date fechaMenosDiezDias);

	Integer numeroTramitesJustificantes(Date fechaHoy, Date fechaMenosDiezDias, String matricula, String numColegiado, String dniAdquiriente);

	List<JustificanteProfVO> listadoJustificantesNoUltimadosEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

}
