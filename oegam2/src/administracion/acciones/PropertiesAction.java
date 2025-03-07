package administracion.acciones;

import java.io.IOException;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.properties.model.service.ServicioProperties;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import administracion.beans.paginacion.ConsultaPropertiesBean;
import administracion.utiles.PropertiesUtils;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PropertiesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 3795406610721667748L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PropertiesAction.class);

	@Resource
	private ModelPagination modeloConsultaPropertiesPaginated;

	private ConsultaPropertiesBean consultaPropertiesBean;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioProperties servicioProperties;

	private String idProperty;
	private String valorNuevo;
	private String nombreProperty;

	public String guardarBBDD() {
		try {
			ResultBean resultado = servicioProperties.actualizarPropertiePantalla(getIdProperty(), getValorNuevo());
			if (!resultado.getError()) {
				
				//Si se cambia el modo de acceso a la aplicación, es necesario volver a acceder.
				if (getNombreProperty().equalsIgnoreCase("nuevo.gestorAccesos")) {
					return "cerrarSesion";
				} else {
					addActionMessage(
							"Mapa de Properties Actualizado. Recuerde que los valores modificados son los de la BBDD ante cualquier reinicio del frontal los valores que predominaran son los configurados en el .properties");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la property, error: ", e);
		}
		return actualizarPaginatedList();
	}

	public String refrescar() {
		try {
			ResultBean resultado = servicioProperties.refrescarProperties();
			if (!resultado.getError()) {
				addActionMessage("Mapa de Properties Actualizado.");
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la property, error: ", e);
		}
		return actualizarPaginatedList();
	}

	@Override
	public String getColumnaPorDefecto() {
		return "nombre";
	}

	@Override
	public String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaPropertiesPaginated;
	}

	@Override
	protected void cargarFiltroInicial() {

		if (consultaPropertiesBean == null) {
			consultaPropertiesBean = new ConsultaPropertiesBean();
		}
		try {
			consultaPropertiesBean.setEntorno(new PropertiesUtils().getEntorno());
		} catch (IOException e) {
			log.error("Error leyendo las propiedades del entorno. No se podrán recuperar las properties");
		}

		setSort("nombre");
		setDir(GenericDaoImplHibernate.ordenAsc);

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaPropertiesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaPropertiesBean = (ConsultaPropertiesBean) object;

	}

	@Override
	protected void cargaRestricciones() {
		if (consultaPropertiesBean == null) {
			consultaPropertiesBean = new ConsultaPropertiesBean();
		}
		try {
			consultaPropertiesBean.setEntorno(new PropertiesUtils().getEntorno());
		} catch (IOException e) {
			log.error("Error leyendo las propiedades del entorno. No se podrán recuperar las properties");
		}

	}

	public ConsultaPropertiesBean getConsultaPropertiesBean() {
		return consultaPropertiesBean;
	}

	public void setConsultaPropertiesBean(ConsultaPropertiesBean consultaPropertiesBean) {
		this.consultaPropertiesBean = consultaPropertiesBean;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	public String getNombreProperty() {
		return nombreProperty;
	}

	public void setNombreProperty(String nombreProperty) {
		this.nombreProperty = nombreProperty;
	}
}
