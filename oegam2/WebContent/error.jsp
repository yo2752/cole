<%@ page isErrorPage="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="opcionMenu" value="%{null}"/>

<script type="text/javascript">
	function volverRaiz(){
		window.location="/oegam2";
	}
</script>

<br/><br/><br/><br/><br/>

<div class="rbroundbox">
	<div class="rbtop">
		<div></div>
	</div>
	<div class="rbcontent">
	<br><br>
	<center>
		<table>
			<s:if test="#attr.mensajeError != null">
				<tr><td><ul><li><s:property value="#attr.mensajeError" /></li></ul></td>
			</s:if>
			<tr>
				<td align="left"><s:actionerror/></td>
			</tr>
			<tr>
				<td align="left"><s:fielderror/></td>
			</tr>
		</table>

		<br><br>
		<input type="button" class="boton" name="bReiniciar" id="bReiniciar"
			value="Volver" onClick="volverRaiz();" onKeyPress="this.onClick" />
		<br><br>
	</center>
	</div><!-- /rbcontent -->
		<div class="rbbot">
			<div></div>
		</div>
</div><!-- /rbroundbox -->