<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<display:table name="creditoFacturado.creditoFacturadoTramites"
		excludedParams="*" class="tablacoin"
		uid="tablaCreditosFacturadosTramites"
		summary="Trámites involucrados en el gasto de créditos"
		cellspacing="0" cellpadding="0">
		<display:column property="idTramite" title="Núm. trámite" style="width:8%;" />
	</display:table>