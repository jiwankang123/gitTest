
/**  
 * All rights Reserved, Designed By www.sys-test.com.cn
 * @Title:  CmdUtil.java   
 * @Package com.android.uiautomator   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: zhubin@sys-test.com.cn     
 * @date:   2018年11月13日 下午1:43:56   
 * @version V1.0 
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */ 
 
package com.android.uiautomator;


/**   
 * @ClassName:CmdUtil   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:zhubin@sys-test.com.cn 
 * @date:2018年11月13日 下午1:43:56   
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */

public class CmdUtil 
{
	public static void execCommand(String cmd) {
		Process pr = null;
		try {
			Runtime rt = Runtime.getRuntime();
			pr = rt.exec(cmd);
			System.out.println("执行命令:" + cmd + " 状态:" + pr.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
