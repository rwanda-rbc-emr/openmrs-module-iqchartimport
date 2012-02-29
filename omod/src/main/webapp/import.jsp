<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<b class="boxHeader"><spring:message code="@MODULE_ID@.import.importCurrentDatabase" /></b>
<form class="box" method="post">
	<table>
		<tr>
	    	<td style="font-weight: bold" width="300">TODO</td>
	    	<td></td>
	    </tr>
	</table>
	<input type="submit" value="<spring:message code="@MODULE_ID@.general.import" />" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>