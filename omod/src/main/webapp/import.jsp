<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localInclude.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">	
function updateTaskStatus() {
    $.ajax({
        url: 'status.form',
        dataType: 'json',
        success: function (data) {
        	var task = data.task;
			if (task == null) {
				$('#progressbar').hide();
				$('#importbutton').removeAttr('disabled');
				$('#statusmsg').show();
				$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.ready" />');
			}
			else {
				if (task.completed) {
					$('#progressbar').hide();
					$('#importbutton').removeAttr('disabled');
					$('#statusmsg').show();
					
					if (task.exception == null)				
						$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.finished" /> (' + task.importedPatients + ' patients imported)');
					else					
						$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.failed" /> (' + task.exception + ')');	
	        	}
				else {
					$('#progressbar').show();
					$('#progressbar').progressbar("value", task.progress);
					$('#statusmsg').hide();
					$('#importbutton').attr('disabled', 'disabled');
				}
			}

			setTimeout('updateTaskStatus()', 3000);
        }
    });
}

$(function() {
	$('#progressbar').progressbar();
	
	updateTaskStatus();
});
</script>

<b class="boxHeader"><spring:message code="@MODULE_ID@.import.importCurrentDatabase" /></b>
<form class="box" method="post">
	<table width="100%" border="0">
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.import.status" /></td>
	    	<td>
	    		<div id="progressbar"></div>
	    		<div id="statusmsg"></div>
	    	</td>
	    </tr>
	</table>
	<input type="submit" id="importbutton" value="<spring:message code="@MODULE_ID@.import.start" />" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>