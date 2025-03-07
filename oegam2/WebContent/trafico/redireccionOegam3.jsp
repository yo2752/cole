<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:set var="urlOegam3" value="%{@trafico.utiles.UtilesVistaTrafico@getInstance().getUrlOegam3()}"></s:set>
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular"><span class="titulo">
						Redirección OEGAM3
					</span></td>
				</tr>
			</table>
		</div> 
		<table cellSpacing=50 class=redirectOegam3 cellPadding=0>
			<tr>	
				<td style="font-size:13px;color: #A3232F;">
				<a><img src="img/warning-icon.png" class="parpadea text" alt="Oegam3" id="warningColegio"/></a>
					El acceso a este módulo debe realizarse a través de la nueva plataforma de OEGAM. <br><br>
					<a href="<s:property value='%{urlOegam3}'/>" title="Acceso a OEGAM3" style="width:3%;">	
					<img src="img/Logo-GA-BLANCO-fondo-transparente.png" alt="Oegam3" id="logoColegio"/></a>	
					Haga click en la imagen y se le redirigira a la url donde deberá identificarse con su carné profesional.
				</td>
			</tr>
		</table>