<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
		<s:hidden name="tramiteRegRbmDto.propiedadDto.buque.idBuque"/>
		<s:hidden name="tramiteRegRbmDto.propiedadDto.buque.idPropiedad"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Buque:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      		  <td align="left" nowrap="nowrap"><label for="buqueNombreBuque">Nombre<span class="naranja">*</span></label>
      		  	<img src="img/botonDameInfo.gif" alt="Info" title="Nombre del buque"/>
      		  </td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.nombreBuque" id="buqueNombreBuque" size="18" maxlength="255"></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="buqueNib" class="small" >NIB</label>
              	<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de identificaci&oacute;n del buque"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.nib" id="buqueNib" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
      		  <td align="left" nowrap="nowrap"><label for="buquePabellon" >Pabell&oacute;n</label>
      		  	<img src="img/botonDameInfo.gif" alt="Info" title="Pabell&oacute;n del pa&iacute;s bajo la bandera que navega"/>
      		  </td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.pabellon" id="buquePabellon" size="18" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="buqueOmi" class="small" >OMI</label>
              	<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero internacional del buque"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.omi" id="buqueOmi" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
      		  <td align="left" nowrap="nowrap"><label for="buqueEstado" >Estado</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.buque.estado" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListEstadoBuque()" 
                         listKey="key"
                		 headerKey="" 
                		 headerValue="Seleccionar estado"
                         id="buqueEstado"/>
               </td>
              <td align="left" nowrap="nowrap"><label for="buqueOtrosDatos" class="small" >Otros datos</label></td>
              <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.otrosDatos" id="buqueOtrosDatos" size="18" maxlength="255"></s:textfield></td>
            </tr>
  		</table>
  		
  		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Matr&iacute;cula buque:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              	<td align="left" nowrap="nowrap"><label for="buqueCapitaniaMaritima" class="small" >Capitan&iacute;a mar&iacute;tima</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo de capitan&iacute;a mar&iacute;tima"/>
              	</td>
                 <td width="30%">  <s:select name="tramiteRegRbmDto.propiedadDto.buque.capitaniaMaritima" 
                      list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCapitaniaMaritima()" 
                      listKey="key"
                      headerKey="" 
                      headerValue="Selecionar capitanía"
                      id="buqueCapitaniaMaritima"/>
				</td>
                
			    <td align="left" nowrap="nowrap"><label for="buqueProvinciaMaritima" class="small">Provincia mar&iacute;tima</label></td>
                <td width="30%">
                	<s:select name="tramiteRegRbmDto.propiedadDto.buque.provinciaMaritima" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="buqueProvinciaMaritima"/>
                </td>
			</tr>
			<tr>	
			   <td align="left" nowrap="nowrap"><label for="buqueDistritoMaritimo" class="small">Distrito mar&iacute;timo</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.distritoMaritimo" id="buqueDistritoMaritimo" size="18" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="buqueNumLista" class="small">N&uacute;m. lista</label></td>
               <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.buque.numLista" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListListaMaritima()" 
                         listKey="key"
                		 headerKey="" 
                		 headerValue="Seleccionar lista"
                         id="buqueNumLista" 
                         cssStyle="width:50%"/>
               </td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="buqueFolioInscripcion" class="small">Folio inscrip.</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.folioInscripcion" id="buqueFolioInscripcion" size="18" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="buqueAnioInscripcion" class="small">Año inscrip.</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.anioInscripcion" id="buqueAnioInscripcion" size="18" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"></s:textfield></td>
            </tr>
		</table>
		
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Matr&iacute;cula fluvial:</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
               <td align="left" nowrap="nowrap" width="20%"><label for="buqueMatriculaFluvial" class="small">Matr&iacute;cula fluvial</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.matriculaFluvial" id="buqueMatriculaFluvial" size="18" maxlength="255"></s:textfield></td>
               <td width="20%">&nbsp;</td>
               <td width="30%">&nbsp;</td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Clasificaci&oacute;n buque:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueTipoEmbarcacion" class="small">Tipo embarcaci&oacute;n</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.tipoEmbarcacion" id="buqueTipoEmbarcacion" size="18" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="buqueSubTipoEmbarcacion" class="small">Subtipo embarcaci&oacute;n</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.subTipoEmbarcacion" id="buqueSubTipoEmbarcacion" size="18" maxlength="255"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Dimensiones:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueEslora" class="small">Eslora</label>
 			   			<img src="img/botonDameInfo.gif" alt="Info" title="Longitud de la embarcaci&oacute;n. Es la distancia medida paralelamente a la l&iacute;nea de agua de diseño, entre dos planos perpendiculares a la l&iacute;nea de cruj&iacute;a, uno a proa y otro a popa, sin considerar elementos no estructurales del casco"/>
 			   		</td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.eslora" id="buqueEslora" size="18" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="buqueManga" class="small">Manga</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Anchura de la embarcaci&oacute;n. Es la m&aacute;xima anchura del casco con las estructuras fijas"/>
               	</td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.manga" id="buqueManga" size="18" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap"><label for="buquePuntal" class="small" >Puntual</label>
 			   		<img src="img/botonDameInfo.gif" alt="Info" title="M&aacute;xima dimensi&oacute;n vertical medida desde la parte superior de la quilla hasta cubierta estanca superior"/>
 			   	</td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.puntal" id="buquePuntal" size="18" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="buqueCaladoMaximo" class="small" >Calado m&aacute;ximo</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Profundidad de la embarcaci&oacute;n. Es la m&aacute;xima dimensi&oacute;n sumergida del casco medida verticalmente, sin contar el tim&oacute;n, la orza, las colas de los motores y otros ap&eacute;ndices similares"/>
               	</td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.caladoMaximo" id="buqueCaladoMaximo" size="18" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Tonelaje:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueBruto" class="small">Bruto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.bruto" id="buqueBruto" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
                <td align="left" nowrap="nowrap"><label for="buqueRegistradoBruto" class="small">Registrado bruto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.registradoBruto" id="buqueRegistradoBruto" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueNeto" class="small">Neto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.neto" id="buqueNeto" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
                <td align="left" nowrap="nowrap"><label for="buqueRegistradoNeto" class="small">Registrado neto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.registradoNeto" id="buqueRegistradoNeto" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueDesplazamiento" class="small" >Desplazamiento</label>
 			   	<img src="img/botonDameInfo.gif" alt="Info" title="Tonelaje de desplazamiento de la embarcaci&oacute;n"/>
 			   </td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.desplazamiento" id="buqueDesplazamiento" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
                <td align="left" nowrap="nowrap"><label for="buquePesoMuerto" class="small">Peso muerto</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.pesoMuerto" id="buquePesoMuerto" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueCargaMaxima" class="small">Carga m&aacute;xima</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.cargaMaxima" id="buqueCargaMaxima" size="18" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '10', '3')"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Casco:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueAstillero" class="small" >Astillero</label>
 				<img src="img/botonDameInfo.gif" alt="Info" title="Nombre del astillero donde se construy&oacute; la embarcaci&oacute;n"/>
 			   </td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.astillero" id="buqueAstillero" size="18" maxlength="255"></s:textfield></td>
                 <td align="left" nowrap="nowrap"><label for="buqueAnioConstruccion" class="small">Año construcci&oacute;n</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.anioConstruccion" id="buqueAnioConstruccion" size="18" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"></s:textfield></td>
            </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="buquePais" class="small">Pa&iacute;s</label></td>
               <td width="35%">
               <s:select name="tramiteRegRbmDto.propiedadDto.buque.pais" 
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
							listKey="codigo" listValue="nombre" headerKey=""
							headerValue="Selecionar Tipo" id="buquePais" />
               </td>
      			
               <td align="left" nowrap="nowrap"><label for="buqueMarca" class="small">Marca</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.marca" id="buqueMarca" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="buqueModelo" class="small">Modelo</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.modelo" id="buqueModelo" size="18" maxlength="255"></s:textfield></td>
            	<td align="left" nowrap="nowrap"><label for="buqueNumSerie" >Serie</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.numSerie" id="buqueNumSerie" size="18" maxlength="255"></s:textfield>	</td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap"><label for="buqueMaterial" class="small">Material</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.buque.material" id="buqueMaterial" size="18" maxlength="255"></s:textfield></td>
            </tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		   	<tr>
				<td align="center" nowrap="nowrap" colspan="6">
					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
						<input type="button" onclick="javascript:aniadirMotor('formData', '');" id="btnAniadirMotor" class="button corporeo"value="Añadir motor" />
					</s:if>
				</td>
			</tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Lista motores</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
	   			 	<jsp:include page="/registradores/rbm/contratosRecursos/motoresList.jsp" flush="true"/>
	   			</td>
		    </tr>
		</table> 
