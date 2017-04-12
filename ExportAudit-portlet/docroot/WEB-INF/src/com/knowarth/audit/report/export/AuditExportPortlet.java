package com.knowarth.audit.report.export;

import com.knowarth.audit.report.util.ExcelUtil;
import com.liferay.portal.audit.model.AuditEvent;
import com.liferay.portal.audit.service.AuditEventLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class AuditExportPortlet extends MVCPortlet {
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		
		SimpleDateFormat dateFormats= new SimpleDateFormat("MM/dd/yyyy");		
		resourceResponse.setContentType("application/vnd.ms-excel");
		resourceResponse.setProperty("Content-Disposition",  "attachement;filename=\"AuditReport " + dateFormats.format(new Date())+ "_" +new Date().getTime() + ".xls");
	Date fromDates=null;
	Date toDate=null;
	
		if (Validator.isNotNull(ParamUtil
				.getString(resourceRequest, "fromDate"))) {
			fromDates = ParamUtil.getDate(resourceRequest, "fromDate",
					dateFormats);
			toDate = ParamUtil.getDate(resourceRequest, "toDate", dateFormats);
		}


	 List<AuditEvent> auditEvenst=null;
	
		if (Validator.isNotNull(fromDates)) {

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
					AuditEvent.class, PortletClassLoaderUtil.getClassLoader());
			dynamicQuery.add(PropertyFactoryUtil.forName("createDate").between(
					fromDates, toDate));

			try {
				auditEvenst = AuditEventLocalServiceUtil
						.dynamicQuery(dynamicQuery);
				ExcelUtil.generateReport(resourceResponse, auditEvenst);
				log.info("Audit report generated");
			} catch (SystemException e) {

				log.error("error occured while generating a auditReport"
						+ e.getMessage());
			}
		}
		
	else{
		try {
			auditEvenst=AuditEventLocalServiceUtil.getAuditEvents(-1, -1);
			ExcelUtil.generateReport(resourceResponse, auditEvenst);
			log.info("Audit report generated");
		} catch (SystemException e) {
			log.error("error occured while generating a auditReport"+e.getMessage());
		}
	
		
	}
}
	private static Log log = LogFactoryUtil
			.getLog(AuditExportPortlet.class);
	
}
