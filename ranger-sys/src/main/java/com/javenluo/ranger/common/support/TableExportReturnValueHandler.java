package com.javenluo.ranger.common.support;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.utils.excel.ExportExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.javenluo.ranger.common.config.Global;
import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.utils.IdGen;

/**
 * 
 * <pre>
 * 导出助手类
 * </pre>
 * 
 * @author gulong
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class TableExportReturnValueHandler implements HandlerMethodReturnValueHandler {

	private RequestResponseBodyMethodProcessor processor;

	public TableExportReturnValueHandler() {

	}

	public TableExportReturnValueHandler(RequestResponseBodyMethodProcessor processor) {
		this.processor = processor;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return this.processor.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
		ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
		HttpServletRequest request = inputMessage.getServletRequest();
		HttpServletResponse response = outputMessage.getServletResponse();

		String flag = request.getParameter("table_export_flag");
		if (StringUtils.isEmpty(flag)) {
			this.processor.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		} else {
			mavContainer.setRequestHandled(true);

			response.setContentType("text");
			String fileName = request.getParameter("table_exportFileName");
			if (returnValue instanceof Page<?>) {
				Page<?> page = (Page<?>) returnValue;
				String fileId = IdGen.uuid();

				ExportExcel ee = new ExportExcel(fileName, page.getEntityClass());
				ee.setDataList(page.getRows());

				ee.writeFile(Global.getConfig("ranger.tmp.dir") + File.separator + fileId).dispose();

				response.getWriter().write(fileId);
			}
		}

	}

	protected <T extends Object> void writeWithMessageConverters(T returnValue, MethodParameter returnType,
			org.springframework.http.server.ServletServerHttpRequest inputMessage,
			org.springframework.http.server.ServletServerHttpResponse outputMessage)
			throws IOException, HttpMediaTypeNotAcceptableException {
	};

	protected ServletServerHttpRequest createInputMessage(NativeWebRequest webRequest) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		return new ServletServerHttpRequest(servletRequest);
	}

	protected ServletServerHttpResponse createOutputMessage(NativeWebRequest webRequest) {
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		return new ServletServerHttpResponse(response);
	}

}