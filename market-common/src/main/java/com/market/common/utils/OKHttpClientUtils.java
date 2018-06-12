package com.market.common.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.market.common.constants.HttpConstant;
import com.market.common.response.HttpResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 发送htpp请求的工具类
 * @author alex
 */
public class OKHttpClientUtils {
	
	private static final Logger logger = Logger.getLogger(OKHttpClientUtils.class);
	
	private static OkHttpClient httpClient;
	
	static {
		// 初始化 并设置超时时间（）
		httpClient = new OkHttpClient.Builder()
				.connectTimeout(20, TimeUnit.SECONDS) // 连接超时时间
				.readTimeout(20, TimeUnit.SECONDS)	// 读取超时时间
				.writeTimeout(10,TimeUnit.SECONDS)	// 写超时时间
				.build();
	}
	
	private static final String URL_LINK_PARAM = "?";
	private static final String PARAM_KEY_VALUE = "&";
	
	private static final String CONTENT_TYPE = "Content-Type";
	
	/**
	 * OKHttpClien 发送get请求
	 * @param url
	 * @param contentType
	 * @param params
	 * @return
	 */
	public static String get(String url,Map<String,String> headers,Map<String, Object> params){
		if(StringUtils.isBlank(url)){
			return HttpResponse.RESULT.BLANK_URL.getMsg();
		}
		try {
			loadGetParams(url, params);
			Builder builder = new Request.Builder();
			loadHeaders(builder, headers);
			Request request = builder.build();
			Response response = httpClient.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			logger.error("--"+url+":请求异常--",e);
			return e.getMessage();
		}
	}
	
	/**
	 * 发送post请求
	 * @param url
	 * @param headers
	 * @param json
	 * @return
	 */
	public static String postString(String url,Map<String,String> headers,String json){
		if(StringUtils.isBlank(url)){
			return HttpResponse.RESULT.BLANK_URL.getMsg();
		}
		String contentType = HttpConstant.ContentType.JSON.get();
		if(headers.containsKey(CONTENT_TYPE)){
			contentType = headers.get(CONTENT_TYPE);
		}
		try {
			Builder builder = new Request.Builder();
			loadHeaders(builder, headers);
			RequestBody body = RequestBody.create(MediaType.parse(json),contentType);
			Request request = builder.url(url).post(body).build();
			Response res = httpClient.newCall(request).execute();
			return res.body().string();
		} catch (Exception e) {
			logger.error("--"+url+"请求异常--",e);
			return e.getMessage();
		}
	}
	
	
	private static void loadHeaders(Builder builder,Map<String,String> headers){
		if(!headers.isEmpty()){
			Set<Entry<String, String>> headerEntry = headers.entrySet();
			for (Entry<String, String> header : headerEntry) {
				builder.addHeader(header.getKey(), header.getValue());
			}
		}
	}
	
	private static void loadGetParams(String url,Map<String, Object> params){
		if(params.size() > 0){
			if(!url.contains(URL_LINK_PARAM)){
				url += URL_LINK_PARAM;
			}
			Set<String> keys = params.keySet();
			for (String key : keys) {
				if(url.endsWith(PARAM_KEY_VALUE)){
					url += (key+"="+params.get(key));
				}else{
					url += (PARAM_KEY_VALUE+key+"="+params.get(key));
				}
			}
		}
	}
	
}
