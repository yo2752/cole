<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<display:table name="representants" uid="representants" class="tablacoin" id="representants"
	summary="Listado de deudas" cellspacing="0" cellpadding="0">
	<display:column title="Tipo" property="corpmePersonType" />
	<display:column title="Nif/Cif" property="fiscalId" />
	<display:column title="Nombre" property="name" />
	<display:column title="Apellido" property="surname" />
	<display:column>
		 <a href="javascript:lightbox('init_FinancialBackerRepresentant.action?idSession=<s:property value='idSession'/>&identificador=<%=pageContext.getAttribute("representants_rowNum")%>')" >
			<img src="img/mostrarV2.gif" alt="Detalle" 	title="Detalle" />
		</a>
		 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este representante?', 'none', 'remove_FinancialBackerRepresentant.action?idSession=<s:property value="idSession"/>&identificador=<%=pageContext.getAttribute("representants_rowNum")%>', 'listRepresentantesList', 'parent.$.fn.colorbox.close(); $(document).trigger(%ready%); $(%a.iframe%, parent.document).colorbox()');">
			<img src="img/icon_eliminar.gif" alt="Eliminar" title="Eliminar" />
		</a>
	</display:column>
</display:table>
