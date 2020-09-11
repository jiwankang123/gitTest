
/**  
 * All rights Reserved, Designed By www.sys-test.com.cn
 * @Title:  JdbcUtil.java   
 * @Package com.android.uiautomator.util   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: zhubin@sys-test.com.cn     
 * @date:   2018年9月21日 下午2:23:45   
 * @version V1.0 
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */ 
 
package com.android.uiautomator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.android.uiautomator.model.Project;
import com.android.uiautomator.model.ProjectVersion;

/**   
 * @ClassName:JdbcUtil   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:zhubin@sys-test.com.cn 
 * @date:2018年9月21日 下午2:23:45   
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */

public class JdbcUtil 
{
	public   Connection conn = null;
	
	private static ResourceBundle rb = ResourceBundle.getBundle("com.android.uiautomator.config");
	
	/**
	 * 
	  * @Title: getConnection 
	  * @Description: TODO(这里用一句话描述这个方法的作用) 
	  * @param @return    设定文件 
	  * @return Connection    返回类型 
	  * @throws 
	  * @author: zhubin@sys-test.com.cn     
	  * @date:   2018年9月26日 上午11:51:24   
	  * @version V1.0
	 */
	public  Connection getConnection() 
	{
		try 
		{
			Class.forName(rb.getString("driver"));
			conn = DriverManager.getConnection(rb.getString("url"),rb.getString("username"),rb.getString("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 
	  * @Title: getProjectId 
	  * @Description: TODO(这里用一句话描述这个方法的作用) 
	  * @param @param projectname
	  * @param @return    设定文件 
	  * @return Integer    返回类型 
	  * @throws 
	  * @author: zhubin@sys-test.com.cn     
	  * @date:   2018年9月26日 上午11:51:29   
	  * @version V1.0
	 */
	public Integer getProjectId(String projectname) 
	{
		QueryRunner run = new  QueryRunner();
		BeanHandler<Project> h = new BeanHandler<Project>(Project.class);
		try 
		{
			Project project = run.query(getConnection(),"select * from xyzc_project where projectname=?",h,projectname);
			return  project.getId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * 
      * @Title: getprojectlist 
      * @Description: TODO(这里用一句话描述这个方法的作用) 
      * @param @return    设定文件 
      * @return String[]    返回类型 
      * @throws 
      * @author: zhubin@sys-test.com.cn     
      * @date:   2018年9月26日 上午11:51:36   
      * @version V1.0
     */
	public String[] getprojectlist()
	{
		QueryRunner run = new  QueryRunner();
		List<String> strlist = new ArrayList<String>();
		ResultSetHandler<List<Project>> h = new BeanListHandler<Project>(Project.class);
		try 
		{
			List<Project> list = run.query(getConnection(),"select * from xyzc_project where systemno=?",h,2);
			for(Project p : list) 
			{
				strlist.add(p.getProjectName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] temp = new String[1];
		return strlist.toArray(temp);
	}
	
	
	public Integer getProjecVersiontId(String versioname) 
	{
		QueryRunner run = new  QueryRunner();
		BeanHandler<ProjectVersion> h = new BeanHandler<ProjectVersion>(ProjectVersion.class);
		try 
		{
			ProjectVersion pversion = run.query(getConnection(),"select * from xyzc_project_version where  versionname=?",h,versioname);
			return  pversion.getId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	  * @Title: getProjectVersion 
	  * @Description: TODO(这里用一句话描述这个方法的作用) 
	  * @param @param projectid
	  * @param @return    设定文件 
	  * @return String[]    返回类型 
	  * @throws 
	  * @author: zhubin@sys-test.com.cn     
	  * @date:   2018年9月26日 上午11:51:40   
	  * @version V1.0
	 */
	public String[] getProjectVersion(String projectid) 
	{
		QueryRunner run = new  QueryRunner();
		ResultSetHandler<List<ProjectVersion>> h = new BeanListHandler<ProjectVersion>(ProjectVersion.class);
		List<String> strlist = new ArrayList<String>();
		String[] temp = new String[1];
		try 
		{
			List<ProjectVersion> pvlsit = run.query(getConnection(),"select * from xyzc_project_version where projectid=?",h,projectid);
			for(ProjectVersion p : pvlsit) 
			{
				strlist.add(p.getVersionname());
			}
			return  strlist.toArray(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Integer getCount(Integer projectid,Integer projectversionid,String elementname) 
	{
        QueryRunner qr = new QueryRunner();
        try {
            Object obj = (Object) qr.query(getConnection(),"select count(1) from xyzc_mobileobjectlibrary where projectid=? and projectversionid=? and elementname=?",new ScalarHandler<Object>(),projectid,projectversionid,elementname);
            return Integer.valueOf(obj.toString());
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }    
        return null;
	}
	
	public void insertObjectLibrary(Integer projectid,Integer projectversionid,String createtime,String username,String elementname,String elementtype,String mobiletype,String descstr) 
	{
		String sql = "insert into xyzc_mobileobjectlibrary (projectid,projectversionid,createtime,username,elementname,elementtype,mobiletype,descstr,type,elementvaluetype) values(?,?,?,?,?,?,?,?,'NATIVE_APP','text')";
		QueryRunner run = new  QueryRunner();
		try 
		{
			run.insert(getConnection(), sql, new ResultSetHandler<Object>() 
			{
				@Override
				public Object handle(ResultSet rs) throws SQLException 
				{
					return null;
				}
			}, projectid,projectversionid,createtime,username,elementname,elementtype,mobiletype,descstr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
