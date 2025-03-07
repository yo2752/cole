package administracion.acciones;

import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaFabricante;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import hibernate.entities.trafico.MarcaDgt;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class MarcaDgtAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(MarcaDgtAction.class);

	private MarcaDgt marcaDgt;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	@Autowired
	private ServicioMarcaFabricante servicioMarcaFabricante;

	// Returns struts
	private static final String JSP = "jsp";

	public String inicio(){
		return JSP;
	}

	public String alta() throws Exception {
		String fabricante = null;

		try {
			if (!marcaDgt.getMarca().equals("") && !marcaDgt.isMate() && !marcaDgt.isMatw()) {
				addActionError("Indique los tipos de trámite desde los que debe ser accesible la nueva marca");
				return JSP;
			}

			if (marcaDgt.getFabricante() != null) {
				fabricante = marcaDgt.getFabricante();
			}

			Long version = null;

			if (!marcaDgt.isMate() && marcaDgt.isMatw()) {
				version = 1L;
			} else if (marcaDgt.isMate()) {
				version = marcaDgt.isMatw() ? 2L : 0L;
			}

			// sB[0]Mensajes de OK. sB[1]Mensajes de Error.
			ResultBean[] sB = servicioMarcaFabricante.addMarcaFabricante(marcaDgt.getMarca(), fabricante, version);

			if (!sB[0].getListaMensajes().isEmpty()) {
				for (int i = 0; i < sB[0].getListaMensajes().size(); i++) {
					addActionMessage(sB[0].getListaMensajes().get(i));
				}
				servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.COMBOS);
				addActionMessage("Creada petición para refresco de caché.");
			}

			if (!sB[1].getListaMensajes().isEmpty()) {
				for (int i = 0; i < sB[1].getListaMensajes().size(); i++) {
					addActionError(sB[1].getListaMensajes().get(i));
				}
			}
			return JSP;
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			throw ex;
		}
	}

	public MarcaDgt getMarcaDgt() {
		return marcaDgt;
	}

	public void setMarcaDgt(MarcaDgt marcaDgt) {
		this.marcaDgt = marcaDgt;
	}

}