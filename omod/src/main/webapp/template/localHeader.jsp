<%-- 
	Local header file
--%>
<h2>
	<spring:message code="@MODULE_ID@.title" />
</h2>

<ul id="menu">
	<li class="first<c:if test='<%= request.getRequestURI().contains("upload") %>'> active</c:if>">
		<a href="upload.form"><spring:message code="@MODULE_ID@.menu.upload"/></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("/mappings") %>'>class="active"</c:if>>
		<a href="mappings.form"><spring:message code="@MODULE_ID@.menu.mappings"/></a>
	</li>
	<c:if test="${database != null}">
		<li <c:if test='<%= request.getRequestURI().contains("/preview") || request.getRequestURI().contains("/patient") %>'>class="active"</c:if>>
			<a href="preview.form"><spring:message code="@MODULE_ID@.menu.preview"/></a>
		</li>
		<li <c:if test='<%= request.getRequestURI().contains("/import") %>'>class="active"</c:if>>
			<a href="import.form"><spring:message code="@MODULE_ID@.menu.import"/></a>
		</li>
	</c:if>
</ul>
