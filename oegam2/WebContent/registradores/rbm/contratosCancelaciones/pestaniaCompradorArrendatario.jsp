<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.compradorArrendatario.idInterviniente"/>

<div class="contenido" id="compradorArrendatario">
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
					<span class="titulo">Datos comprador/arrendatario</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>
             <td align="left" nowrap="nowrap"><label for="compradorArrendatarioTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
             <td width="30%">  <s:select name="tramiteRegRbmDto.compradorArrendatario.tipoPersona" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
                        headerKey="" 
                        headerValue="Selecionar Tipo"
                        listValue="nombreEnum" listKey="valorXML"
                        id="compradorArrendatarioTipoPersona"/>
			</td>
             <td align="left" nowrap="nowrap"><label for="compradorArrendatarioNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
             
             <td width="35%">
             	 	<table  cellSpacing="0">
             	 		<tr>
             	 			<td align="left" nowrap="nowrap">
	               			<s:textfield name="tramiteRegRbmDto.compradorArrendatario.nif" id="compradorArrendatarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	               		</td>
	               		
	               		<td align="left" nowrap="nowrap">
							<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
		               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
									onclick="javascript:buscarIntervinienteAjax('compradorArrendatarioNif', 'compradorArrendatario')" />
							</s:if>
						</td>
					</tr>
              		</table>
              </td>
              
           </tr>
           <tr>
             <td align="left" nowrap="nowrap"><label for="compradorArrendatarioNombre" >Nombre</label>
             		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
             </td>
             <td>
             <s:textfield name="tramiteRegRbmDto.compradorArrendatario.persona.nombre" id="compradorArrendatarioNombre" size="18" maxlength="100"></s:textfield>
             </td>
             <td align="left" nowrap="nowrap"><label for="compradorArrendatarioApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
       			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
             </td>
             <td><s:textfield name="tramiteRegRbmDto.compradorArrendatario.persona.apellido1RazonSocial" id="compradorArrendatarioApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
           </tr>
           <tr>
             <td align="left" nowrap="nowrap"><label for="compradorArrendatarioApellido2" >2do apellido</label>
             		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
             </td>
             <td><s:textfield name="tramiteRegRbmDto.compradorArrendatario.persona.apellido2" id="compradorArrendatarioApellido2" size="18" maxlength="100"></s:textfield></td>
           </tr>
         
      </table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista compradores/arrendatarios</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="compradoresArrendatariosList.jsp" />
        	</td>
        </tr>
	</table>

</div>

  