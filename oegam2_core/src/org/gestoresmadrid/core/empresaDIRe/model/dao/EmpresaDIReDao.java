package org.gestoresmadrid.core.empresaDIRe.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIReVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EmpresaDIReDao extends Serializable, GenericDao<EmpresaDIReVO>{

	EmpresaDIReVO getEmpresaDIRePorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);

	BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception;

	EmpresaDIReVO getEmpresaDIRePorExpediente(BigDecimal numExpediente);

	EmpresaDIReVO getEmpresaDIRePorId(long longValue, Boolean tramiteCompleto);

	public String Generar_Numero_DIRe(String  Codigo_DIRe);

}