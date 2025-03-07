package arbol.acciones;

import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioGestionAcceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import general.acciones.ActionBase;
import net.sf.navigator.menu.MenuRepository;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloAccesos;
import utilidades.mensajes.Claves;
import utilidades.web.GestorArbol;

public class ArbolAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private BigDecimal contratoSeleccionado;
	private ModeloAccesos modeloAccesos;
	private static final String SUCCESS = "SUCCESS";

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioGestionAcceso servicioGestionAcceso;

	@Override
	public String execute() throws Exception {
		if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
			UsuarioAccesoBean usuarioAccesoBeana = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			ResultadoAccesoBean resultadoAcceso = servicioGestionAcceso.gestionAcceso(usuarioAccesoBeana.getNif(), contratoSeleccionado != null ? contratoSeleccionado.longValue() : null);
			if (!resultadoAcceso.getError()) {
				UsuarioAccesoBean usuarioAcceso = resultadoAcceso.getUsuarioAcceso();
				resultadoAcceso = servicioGestionAcceso.gestionFavoritos(resultadoAcceso.getUsuarioAcceso());
				if (!resultadoAcceso.getError()) {
					usuarioAcceso.setListaFavoritos(resultadoAcceso.getListaFavoritos());
				}
				resultadoAcceso = servicioGestionAcceso.montarMenuRepository(usuarioAcceso);
				if (!resultadoAcceso.getError()) {
					MenuRepository menuArbol = resultadoAcceso.getMenuArbol();
					resultadoAcceso = servicioGestionAcceso.guardarDatosAccesoSession(usuarioAcceso);
					if (resultadoAcceso.getError()) {
						addActionError(resultadoAcceso.getMensaje());
						return Action.ERROR;
					}
					Claves.setObjetoDeContextoSesion(Claves.CLAVE_ARBOL, menuArbol);
					Claves.setObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO, usuarioAcceso);
					Claves.setObjetoDeContextoSesion(ConstantesSession.RAZON_SOCIAL_CONTRATOP, usuarioAcceso.getRazonSocialContrato());
					Claves.setObjetoDeContextoSesion(ConstantesSession.CIFP,usuarioAcceso.getCifContrato());
					setContratoSeleccionado(new BigDecimal(usuarioAcceso.getIdContrato()));
					if (usuarioAcceso.getListaFavoritos() != null && !usuarioAcceso.getListaFavoritos().isEmpty()) {
						Claves.setObjetoDeContextoSesion("favoritos", usuarioAcceso.getListaFavoritos());
					}
					return SUCCESS;
				} else {
					addActionError(resultadoAcceso.getMensaje());
					return Action.ERROR;
				}
			} else {
				addActionError(resultadoAcceso.getMensaje());
				return Action.ERROR;
			}
		} else {
			GestorArbol ga = (GestorArbol)Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			try {
				getModeloAccesos().crearArbol(ga.getUsuario().getNif(),getContratoSeleccionado());
				setContratoSeleccionado(ga.getIdContrato());
				return SUCCESS;
			} catch (Throwable e) {
				setMensajeError("No existe menú para ese contrato.");
				return "error";
			}
		}
	}

	public BigDecimal getContratoSeleccionado() {
		return contratoSeleccionado;
	}

	public void setContratoSeleccionado(BigDecimal contratoSeleccionado) {
		this.contratoSeleccionado = contratoSeleccionado;
	}

	/* ************************************************* */
	/* MODELOS ***************************************** */
	/* ************************************************* */

	public ModeloAccesos getModeloAccesos() {
		if (modeloAccesos == null) {
			modeloAccesos = new ModeloAccesos();
		}
		return modeloAccesos;
	}

	public void setModeloAccesos(ModeloAccesos modeloAccesos) {
		this.modeloAccesos = modeloAccesos;
	}

}