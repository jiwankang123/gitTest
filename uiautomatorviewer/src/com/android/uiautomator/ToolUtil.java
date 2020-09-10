
/**  
 * All rights Reserved, Designed By www.sys-test.com.cn
 * @Title:  ToolUtil.java   
 * @Package com.android.uiautomator   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: zhubin@sys-test.com.cn     
 * @date:   2018年11月13日 下午1:44:51   
 * @version V1.0 
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */ 
 
package com.android.uiautomator;


/**   
 * @ClassName:ToolUtil   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:zhubin@sys-test.com.cn 
 * @date:2018年11月13日 下午1:44:51   
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */

public class ToolUtil 
{

	 public static void getAndroidImage(String udid,String imagePath) 
	 {
		 
		 CmdUtil.execCommand(DebugBridge.adbPath + " -s " + udid + " shell rm /data/local/screenshot.png");
			CmdUtil.execCommand(
					DebugBridge.adbPath + " -s " + udid + " shell screencap -p /data/local/tmp/screenshot.png");
			CmdUtil.execCommand(
					DebugBridge.adbPath + " -s " + udid + " pull /data/local/tmp/screenshot.png " + imagePath);
		 
	 }
}
