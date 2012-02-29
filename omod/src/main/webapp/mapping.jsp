<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<b class="boxHeader"><spring:message code="@MODULE_ID@.mapping.itemMappings" /></b>
<form class="box" method="post">
<sform:form commandName="mapping" cssClass="box" action="mapping.form">
	<table>
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.mapping.tracnetIdentifierType" /></td>
	    	<td>
	    		<sform:select path="tracnetIDTypeId">
	    			<c:if test="${mapping.tracnetIDTypeId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<sform:option value="0">&lt;<spring:message code="@MODULE_ID@.mapping.createNew" />&gt;</sform:option>
					<c:forEach items="${idTypes}" var="idType">
						<sform:option value="${idType.patientIdentifierTypeId}" label="${idType.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	</table>
	<input type="submit" value="<spring:message code="general.save" />" />
</sform:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>