<%@ include file="/html/init.jsp"%>
<h2>Audit Data Export</h2>

<portlet:resourceURL  var="auditReport"/>

<aui:form action="${auditReport}" method="post">
<aui:input name="fromDate" type="text" label="from-date" id="fromDate"></aui:input>
<aui:input name="toDate" type="text" label="to-date" id="toDate" helpMessage="end-date"></aui:input>
<aui:button cssClass="btn btn-large btn-primary" value="export" type="submit"></aui:button>
<p>&nbsp;
<div><h2><b>Note:</b></h2><ul>
<li><h3>
If you click Export it will generate full Audit Report.</h3></li>
<li><h3>
Otherwise you can choose Dates, It will generated report between Dates </h3></li>
</ul></div>
</aui:form>


<aui:script>
AUI().use('aui-datepicker', function(A) {
new A.DatePicker({
trigger : '#<portlet:namespace/>fromDate',
popover : {
zIndex : 1
}
});
});
</aui:script>


<aui:script>
AUI().use('aui-datepicker', function(A) {
new A.DatePicker({
trigger : '#<portlet:namespace/>toDate',
popover : {
zIndex : 1
}
});
});
</aui:script>

