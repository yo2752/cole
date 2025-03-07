package org.gestoresmadrid.oegam2.modelos.tags;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.BotonPaginaConsultaBean;
import utilidades.web.OegamExcepcion;

/**
 * Para añadir un nuevo botón a la página de la consulta de trámites de tráfico,
 * solo hay que instanciar un nuevo BotonPaginaConsultaBean en el método
 * getBotones()...
 */
public class BotonesPaginaConsultaModelosTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String TR_OPEN = "<tr>";
	private static final String TR_END = "</tr>";
	private static final String TD_OPEN = "<td>";
	private static final String TD_END = "</td>";
	private static final String TABLE_END = "</table>";

	private static final String CLASE_CSS_TABLE = "acciones";

	private static final String REF_PROP_METODOS_VALIDADORES_PERMISO = ".metodos.validadores.permiso";
	private static final String REF_PROP_USUARIOS_AUTORIZADOS = ".usuarios.autorizados";
	private static final String DELIMITADOR_ELEMENTOS_PROPIEDADES = ",";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	public BotonesPaginaConsultaModelosTag() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String aperturaTabla = "<table class=\"" + CLASE_CSS_TABLE + "\">";
			ArrayList<String> listaTds = new ArrayList<>();
			StringBuilder tablaCompleta = new StringBuilder();
			tablaCompleta.append(aperturaTabla);
			ArrayList<BotonPaginaConsultaBean> listaBotones = getBotones();

			for (BotonPaginaConsultaBean boton : listaBotones) {
				boolean autorizado = usuarioAutorizado(boton.getName());
				if (autorizado) {
					listaTds.add(TD_OPEN + boton.toString() + TD_END);
				}
			}

			int numBotones = listaTds.size();

			tablaCompleta.append(TR_OPEN);
			for (int i = 0; i < numBotones; i++) {
				if (i > 0 && i % 5 == 0) {
					tablaCompleta.append(TR_END + TR_OPEN);
				}
				tablaCompleta.append(listaTds.get(i));
			}
			tablaCompleta.append(TR_END);

			tablaCompleta.append(TABLE_END);
			super.pageContext.getOut().print(tablaCompleta.toString());

		} catch (Exception ex) {
			throw new JspException(ex);
		} catch (OegamExcepcion ex) {
			throw new JspException(ex);
		}
		return SKIP_BODY;
	}

	/**
	 * El constructor de BotonPaginaConsultaBean debe recibir : name, id, value y
	 * onclick. Los demas valores se establecen por defecto pero se pueden cambiar
	 * con el set correspondiente
	 * 
	 * Ids usuarios autorizados : en los properties : nombreBoton +
	 * '.usuarios.autorizados=' + lista ids usuarios autorizados separados por coma
	 * (',') Métodos validadores de permiso : en los properties : nombreBoton +
	 * '.metodos.validadores.permiso=' + lista métodos validadores de permiso
	 * separados por coma (',') Los métodos de validación de permisos deben devolver
	 * un boolean...
	 */
	private ArrayList<BotonPaginaConsultaBean> getBotones() {

		ArrayList<BotonPaginaConsultaBean> listaBotones = new ArrayList<>();

		// BOTÓN 'Validar'
		BotonPaginaConsultaBean botonValidar = new BotonPaginaConsultaBean("bValidarModelo", "bValidarModelo",
				"Validar", "return validarBloque();");
		listaBotones.add(botonValidar);

		// BOTÓN 'AutoLiquidar'
		BotonPaginaConsultaBean botonAutoLiqu = new BotonPaginaConsultaBean("bAutoLiquidar", "bAutoLiquidar",
				"AutoLiquidar", "return autoLiquidarBloque();");
		listaBotones.add(botonAutoLiqu);

		// BOTÓN 'Presentar'
		BotonPaginaConsultaBean botonPresentar = new BotonPaginaConsultaBean("bPresentar", "bPresentar", "Presentar",
				"return presentarBloque();");
		listaBotones.add(botonPresentar);

		// BOTÓN 'Imprimir Documentos'
		BotonPaginaConsultaBean botonImprimir = new BotonPaginaConsultaBean("bImprimir", "bImprimir",
				"Imprimir Documentos", "return imprimirBloque();");
		listaBotones.add(botonImprimir);

		// BOTÓN 'Cambiar Estado'
		BotonPaginaConsultaBean botonCambiarEstado = new BotonPaginaConsultaBean("bCambiarEstado", "bCambiarEstado",
				"Cambiar Estado", "return cargarVentanaCambioEstado();");
		listaBotones.add(botonCambiarEstado);

		// BOTÓN 'Eliminar'
		BotonPaginaConsultaBean botonEliminar = new BotonPaginaConsultaBean("bEliminar", "bEliminar", "Eliminar",
				"return eliminarBloque();");
		listaBotones.add(botonEliminar);

		return listaBotones;
	}

	private boolean usuarioAutorizado(String nombreBoton) throws OegamExcepcion {
		boolean necesitaAutorizacion = false;

		String refPropUsuariosAutorizados = nombreBoton + REF_PROP_USUARIOS_AUTORIZADOS;

		if (gestorPropiedades.valorPropertie(refPropUsuariosAutorizados) != null
				&& !gestorPropiedades.valorPropertie(refPropUsuariosAutorizados).equals("")) {
			String[] listaUsuariosAutorizados = gestorPropiedades.valorPropertie(refPropUsuariosAutorizados)
					.split(DELIMITADOR_ELEMENTOS_PROPIEDADES);
			for (String cadIdAutorizado : listaUsuariosAutorizados) {
				necesitaAutorizacion = true;
				try {
					if (utilesColegiado.getIdUsuarioSessionBigDecimal()
							.compareTo(new BigDecimal(cadIdAutorizado)) == 0) {
						return true;
					}
				} catch (NumberFormatException ex) {
					continue;
				}
			}
		}

		String refPropMetodosValidadoresPermiso = nombreBoton + REF_PROP_METODOS_VALIDADORES_PERMISO;

		if (gestorPropiedades.valorPropertie(refPropMetodosValidadoresPermiso) != null
				&& !gestorPropiedades.valorPropertie(refPropMetodosValidadoresPermiso).equals("")) {
			String[] listaMetodosValidadoresPermisos = gestorPropiedades
					.valorPropertie(refPropMetodosValidadoresPermiso).split(DELIMITADOR_ELEMENTOS_PROPIEDADES);
			for (String metodoValidadorPermiso : listaMetodosValidadoresPermisos) {
				necesitaAutorizacion = true;
				try {
					int situacionUltimoPunto = metodoValidadorPermiso.lastIndexOf(".");
					String nombreClase = metodoValidadorPermiso.substring(0, situacionUltimoPunto);
					String nombreMetodo = metodoValidadorPermiso.substring(situacionUltimoPunto,
							metodoValidadorPermiso.length());
					nombreMetodo = nombreMetodo.replace("(", "");
					nombreMetodo = nombreMetodo.replace(")", "");
					nombreMetodo = nombreMetodo.replace(".", "");
					Object tempClass = Class.forName(nombreClase).newInstance();
					Class claseCargada = tempClass.getClass();
					Method metodo = claseCargada.getDeclaredMethod(nombreMetodo);
					Object resultado = metodo.invoke(tempClass);
					if ((Boolean) resultado) {
						return true;
					}
				} catch (Exception ex) {
					continue;
				}
			}
		}

		return (!necesitaAutorizacion);
	}

}