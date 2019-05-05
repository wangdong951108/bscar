package com.wangdbysj.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class IdcardAudit {

	public JSONObject dcardAudit(String email ,String realName) {
		String host = "http://idcard3.market.alicloudapi.com";
		String path = "/idcardAudit";
		String method = "GET";
		String appcode = "2445e3bce4744dd083d7c782f4c389a3";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("idcard", email);
		querys.put("name", realName);
		try {
			HttpResponse response = new HttpUtil().doGet(host, path, method, headers, querys);
			// 将流形式转为string类型 再转为jsonodject
			JSONObject responseBody = new JSONObject(EntityUtils.toString(response.getEntity()));
			// 获取其中的showapi_res_body 内容并转为string类型
			String aString = responseBody.get("showapi_res_body").toString();
			// 将string类型转为jsonobject类型
			JSONObject JSONObject = new JSONObject(aString);
			return JSONObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}

}
