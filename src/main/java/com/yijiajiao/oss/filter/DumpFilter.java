package com.yijiajiao.oss.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@SuppressWarnings("ALL")
@Slf4j
@WebFilter(filterName="DumpFilter",urlPatterns="/*")
public class DumpFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(DumpFilter.class);

	private static class ByteArrayServletStream extends ServletOutputStream {
		ByteArrayOutputStream baos;

		ByteArrayServletStream(ByteArrayOutputStream baos) {
			this.baos = baos;
		}

		public void write(int param) throws IOException {
			baos.write(param);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {

		}
	}

	private static class ByteArrayPrintWriter {
		private ByteArrayOutputStream baos = new ByteArrayOutputStream();
		private PrintWriter pw = new PrintWriter(baos);
		private ServletOutputStream sos = new ByteArrayServletStream(baos);

		public PrintWriter getWriter() {
			return pw;
		}

		public ServletOutputStream getStream() {
			return sos;
		}

		byte[] toByteArray() {
			return baos.toByteArray();
		}
	}

	private class BufferedServletInputStream extends ServletInputStream {
		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {

		}

		ByteArrayInputStream bais;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.bais = bais;
		}

		public int available() {
			return bais.available();
		}

		public int read() {
			return bais.read();
		}

		public int read(byte[] buf, int off, int len) {
			return bais.read(buf, off, len);
		}

	}

	private class BufferedRequestWrapper extends HttpServletRequestWrapper {
		ByteArrayInputStream bais;
		ByteArrayOutputStream baos;
		BufferedServletInputStream bsis;
		byte[] buffer;

		public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
			super(req);
			InputStream is = req.getInputStream();
			baos = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int letti;
			while ((letti = is.read(buf)) > 0) {
				baos.write(buf, 0, letti);
			}
			buffer = baos.toByteArray();
		}

		public ServletInputStream getInputStream() {
			try {
				bais = new ByteArrayInputStream(buffer);
				bsis = new BufferedServletInputStream(bais);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return bsis;
		}

		public byte[] getBuffer() {
			return buffer;
		}

	}

	private boolean dumpRequest;
	private boolean dumpResponse;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// dumpRequest =
		// Boolean.valueOf(filterConfig.getInitParameter("dumpRequest"));
		// dumpResponse =
		// Boolean.valueOf(filterConfig.getInitParameter("dumpResponse"));
		dumpRequest = true;
		dumpResponse = true;
		log.info("dumpFitler init...");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		long _start = System.currentTimeMillis();
		// DateTime _dt_start = DateTime.now();
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpRequest);

		bufferedRequest.setCharacterEncoding("utf-8");
		log.info("请求地址: {}", bufferedRequest.getRequestURL());
		log.info("来源地址: {}",getIp(bufferedRequest));
		log.info("请求参数: {}",bufferedRequest.getQueryString());
		log.info("涉及到的方法为: {}",bufferedRequest.getMethod());
		log.info("token: {}",bufferedRequest.getHeader("token"));
		log.info("jsessionId: {}",bufferedRequest.getSession().getId());
		String _content_type = bufferedRequest.getContentType();
		log.info("Content-Type: {}",_content_type);
		log.info("CharacterEncoding: {}", bufferedRequest.getCharacterEncoding());

		// log.info("getCookies: {}", bufferedRequest.getCookies());
		// log.info("getDispatcherType: " + req.getDispatcherType());
		// log.info("getLocalAddr: {}", bufferedRequest.getLocalAddr());
		// log.info("getLocales: " + req.getLocales());
		// log.info("getLocalName: {}", bufferedRequest.getLocalName());
		// log.info("getLocalPort: {}", bufferedRequest.getLocalPort());

		if ("application/x-www-form-urlencoded".equals(_content_type)) {
			log.debug("[application/x-www-form-urlencoded]请求内容不是JSON，过滤器将不读取，直接向下传递");
			filterChain.doFilter(bufferedRequest, servletResponse);
			return;
		}
		if ("application/x-www-form-urlencoded; charset=UTF-8".equals(_content_type)) {
			log.debug("[application/x-www-form-urlencoded; charset=UTF-8]请求内容不是JSON，过滤器将不读取，直接向下传递");
			filterChain.doFilter(bufferedRequest, servletResponse);
			return;
		}

		if (dumpRequest) {
			log.info("Request body ->>>\n {} ",new String(bufferedRequest.getBuffer()));
		}
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		final ByteArrayPrintWriter pw = new ByteArrayPrintWriter();
		HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {
			public PrintWriter getWriter() {
				return pw.getWriter();
			}

			public ServletOutputStream getOutputStream() {
				return pw.getStream();
			}
		};
		filterChain.doFilter(bufferedRequest, wrappedResp);

		byte[] bytes = pw.toByteArray();
		response.getOutputStream().write(bytes);
		if (dumpResponse)
			log.info("Response content:  ->>>\n{}", new String(bytes));

		long _end = System.currentTimeMillis();
		// DateTime _end_dt = DateTime.now();
		log.info("本次请求耗时（毫秒）：{}",(_end - _start));
	}

	@Override
	public void destroy() {
	}

	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
