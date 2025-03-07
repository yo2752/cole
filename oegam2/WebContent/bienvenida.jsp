<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form action="Registrar">
	<table width="90%" height="30px" align="left" border="0" cellspacing="0"
		cellpadding="0" summary="Acceso: Selección de certificado">
		<tr>
			<th class="cab">Datos de acceso</th>
		</tr>
		<tr>
			<th class="cab">
				<center>
					<s:actionmessage />
					<s:fielderror key="nif.error" /> 
					<s:textfield name="nif" size="9" maxlength="9" label="Your Nif" />
					<s:submit />
				</center>
			</th>
		</tr>
	</table>
</s:form>