package com.xhk.labmanage.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * create by xhk on 18/3/4
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static ThreadLocal<ServletResponse> resLocal = new ThreadLocal<>();
	private static ThreadLocal<String> LogIdLocal = new ThreadLocal<>();

	public static void setLogId(String res) {
		LogIdLocal.set(res);
	}

	public static String getLogId() {
		return LogIdLocal.get() == null ? "" : LogIdLocal.get();
	}

	public static HttpServletRequest getHttpRequest() {
		HttpServletRequest request = (HttpServletRequest) ThreadVariableUtil.getThreadVariable("httpRequest");
		if (request != null) {
			return request;
		}
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return requestAttributes.getRequest();
	}

	/**
	 * 返回head值
	 * 
	 * @param key
	 * @return value ""
	 */
	public static String getHeaderValue(String key) {
		HttpServletRequest request = getHttpRequest();
		if (request == null) {
			return null;
		}
		return request.getHeader(key);
	}

	/**
	 * 返回request值
	 * 
	 * @param key
	 * @return value ""
	 */
	public static String getRequestValue(String key) {
		HttpServletRequest request = getHttpRequest();
		if (request == null) {
			return null;
		}
		return request.getParameter(key);
	}

	public static void destroyLogId() {
		LogIdLocal.remove();
	}

	public static void setResponse(ServletResponse res) {
		resLocal.set(res);
	}

	public static ServletResponse getResponse() {
		return resLocal.get();
	}

	public static void destroyResponse() {
		resLocal.remove();
	}

	public static String post(String url, Map<String, String> params, Integer connTimeout, Integer soTimeout)
			throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Connection", "close");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
		client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		Iterator iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (key == null || key.toString().trim().length() == 0 || val == null
					|| val.toString().trim().length() == 0)
				continue;
			String paramName = key.toString();
			String paramValue = val.toString();
			post.addParameter(paramName, paramValue);
		}
		client.executeMethod(post);
		int statusCode = post.getStatusCode();
		String res = post.getResponseBodyAsString();
		post.releaseConnection();
		return res;
	}

	public static byte[] postTest(String url, Map<String, String> params, Integer connTimeout, Integer soTimeout)
			throws Exception {

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Connection", "close");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
		client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		Iterator iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (key == null || key.toString().trim().length() == 0 || val == null
					|| val.toString().trim().length() == 0)
				continue;
			String paramName = key.toString();
			String paramValue = val.toString();
			post.addParameter(paramName, paramValue);
		}
		client.executeMethod(post);
		int statusCode = post.getStatusCode();
		byte[] res = post.getResponseBody();
		post.releaseConnection();
		return res;
	}

	public static String get(String url, Integer connTimeout, Integer soTimeout) throws Exception {

		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
		client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Connection", "close");
		int statusCode = client.executeMethod(getMethod);
		if (statusCode == 200) {
			return getMethod.getResponseBodyAsString();
		}
		return "";
	}

	private static class DefaultTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public static String postSupportHttps(String uri, Map map, Integer connTimeout, Integer soTimeout)
			throws Exception {
		if (!uri.substring(0, 5).toUpperCase().equals("HTTPS")) {
			return post(uri, map, connTimeout, soTimeout);
		}
		logger.info("postSupportHttps to url="+uri);
		HttpsURLConnection httpsConn = null;
		try {
			SSLContext ctx = null;
			ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLSocketFactory ssf = ctx.getSocketFactory();

			URL url = new URL(uri);
			httpsConn = (HttpsURLConnection) url.openConnection();
			httpsConn.setSSLSocketFactory(ssf);
			httpsConn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			httpsConn.setConnectTimeout(connTimeout);
			httpsConn.setReadTimeout(soTimeout);
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setRequestMethod("POST");

			httpsConn.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			logger.info("postSupportHttps to pamas="+StringUtil.getUriFromMap(map));
			httpsConn.getOutputStream().write(StringUtil.getUriFromMap(map).getBytes("utf-8"));
			httpsConn.getOutputStream().flush();
			httpsConn.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
			String ret = "";
			String line = "";
			while ((line = in.readLine()) != null) {
				ret = ret + line;
			}
			logger.info("postSupportHttps to ret="+ret);
			return ret;
		} catch (Exception e){
			logger.error("postSupportHttps error",e);
			return "";
		}finally {
			if (httpsConn != null) {
				try {
					httpsConn.disconnect();
				} catch (Exception e) {
				}
			}
		}

	}

	public final static String getIpAddress(HttpServletRequest request)  {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isDebugEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isDebugEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isDebugEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isDebugEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isDebugEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isDebugEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		//logger.info("IP="+ip);
		return ip;
	}


	public static Map<String, String> toRequestMaps(HttpServletRequest request) {

		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}

		Set<Map.Entry<String, String>> set = map.entrySet();

		Map<String,String> paramMap  = new HashMap<>();
		for (Map.Entry entry : set) {
			paramMap.put(entry.getKey().toString() ,entry.getValue().toString());
		}


		return paramMap;

	}

	/**
	 * 得到本机ip
	 * @return
	 * @throws Exception
	 */
	public static String getLocalIp(){
		try{
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements())
			{
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements())
				{
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address)
					{
						return ip.getHostAddress();
					}
				}
			}
			return "127.0.0.1";
		}catch (Exception e){

		}
		return "127.0.0.1";
	}
}
