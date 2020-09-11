
/**  
 * All rights Reserved, Designed By www.sys-test.com.cn
 * @Title:  Project.java   
 * @Package com.xyzc.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: zhubin@sys-test.com.cn     
 * @date:   2018年3月15日 下午1:51:21   
 * @version V1.0 
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */ 
 
package com.android.uiautomator.model;


/**   
 * @ClassName:Project   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:zhubin@sys-test.com.cn 
 * @date:2018年3月15日 下午1:51:21   
 * @Copyright: 2018 www.sys-test.com.cn Inc. All rights reserved. 
 * 注意:本内容仅限于南京讯优智超软件科技有限公司内部传阅,禁止外泄以及用于其他的商业目的
 */

public class Project 
{
	//项目编号
	private Integer id;
	//项目名称
	private String projectName;
	//项目描述
	private String descstr;
	//区别手机接口字段
	private Integer systemno;
	 public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getDescstr() {
		return descstr;
	}


	public void setDescstr(String descstr) {
		this.descstr = descstr;
	}


	public Integer getSystemno() {
		return systemno;
	}


	public void setSystemno(Integer systemno) {
		this.systemno = systemno;
	}


	/**
	  * @param id
	  * @param name
	  * @param paramtype
	  * @param paramvalue
	  * @param descstr
	  * @param ordernum
	  * @param direction
	  */
	 
	public Project(Integer id, String projectName, String descstr) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.descstr = descstr;
	}

	 
	/**
	  */
	 
	public Project() {
		
		super();
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descstr == null) ? 0 : descstr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		return result;
	}
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (descstr == null) {
			if (other.descstr != null)
				return false;
		} else if (!descstr.equals(other.descstr))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	 
	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName+ ", descstr=" + descstr +  "]";
	}
	
	
    
}
