
/**  
 * All rights Reserved, Designed By www.sys-test.com.cn
 * @Title:  HttpClientTest.java   
 * @Package com.android.uiautomator   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: zhubin@sys-test.com.cn     
 * @date:   2018年9月13日 下午2:49:03   
 * @version V1.0 
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */ 
 
package com.android.uiautomator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**   
 * @ClassName:HttpClientTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:zhubin@sys-test.com.cn 
 * @date:2018年9月13日 下午2:49:03   
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */

public class HttpClientTest 
{
	
	public static void main(String[] args) 
	{
		String str = "system_profiler SPUSBDataType | sed -n -e '/iPad/,/Extra/p' -e '/iPod/,/Extra/p' -e '/iPhone/,/Extra/p'";
		CloseableHttpResponse response2 = null;
		try 
		{
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://targethost/login");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", "vip"));
			nvps.add(new BasicNameValuePair("password", "secret"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			 response2 = httpclient.execute(httpPost);
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    EntityUtils.consume(entity2);
		}catch(Exception e) {
			e.printStackTrace();
		} finally 
		{
			 try 
			 {
				response2.close();
			 }catch (IOException e) 
			 {
				e.printStackTrace();
			 }
		}
	}

}
