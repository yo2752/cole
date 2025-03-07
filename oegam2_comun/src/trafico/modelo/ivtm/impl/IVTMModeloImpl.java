package trafico.modelo.ivtm.impl;
//TODO MPC. Cambio IVTM. Clase nueva.
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import trafico.dao.implHibernate.ivtm.IvtmDaoImplHibernate;
import trafico.dao.ivtm.IvtmDaoInterface;
import trafico.modelo.ivtm.IVTMModeloInterface;
import trafico.utiles.constantes.ConstantesIVTM;
import utilidades.estructuras.Fecha;

/**
 * 
 * @author Globaltms
 *
 */
public class IVTMModeloImpl implements IVTMModeloInterface {

	private IvtmDaoInterface ivtmDao;

	public IVTMModeloImpl() {
		ivtmDao = new IvtmDaoImplHibernate();
	}

	/**
	 * @see IVTMModeloInterface
	 */
	public BigDecimal generarIdPeticion(String numColegiado) {
		BigDecimal idPeticion = ivtmDao.generarIdPeticion(numColegiado);
		Formatter fmt = new Formatter();
		// Forma el número 00001
		String resultSumarPet = fmt.format("%05d", (new BigDecimal(1)).intValue()).toString();

		if (idPeticion == null) {
			String peticion = numColegiado;
			Fecha fecha = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			peticion += fecha.getDia()+fecha.getMes()+fecha.getAnio().substring(2);
			peticion += ConstantesIVTM.TIPO_ID_PETICION_IVTM;
			peticion += resultSumarPet;
			idPeticion = new BigDecimal(peticion);
		} else {
			idPeticion = idPeticion.add(new BigDecimal(resultSumarPet));
		}
		return idPeticion;
	}

}