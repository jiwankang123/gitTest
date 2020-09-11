package com.android.uiautomator.model;



public class ProjectVersion  {
	private Integer id;
	private Integer projectid;
	private String  versionname;
	private String  createtime;
	private String  versiondescstr;
	private Integer userid;
	private String userName;
	private String versionnumber; 
	
	



	public ProjectVersion(Integer id, Integer projectid, String versionname, String createtime, String versiondescstr,
			Integer userid, String userName, String versionnumber) {
		super();
		this.id = id;
		this.projectid = projectid;
		this.versionname = versionname;
		this.createtime = createtime;
		this.versiondescstr = versiondescstr;
		this.userid = userid;
		this.userName = userName;
		this.versionnumber = versionnumber;
	}

	public ProjectVersion() {
		// TODO Auto-generated constructor stub
	}

	public String getVersionnumber() {
		return versionnumber;
	}

	public void setVersionnumber(String versionnumber) {
		this.versionnumber = versionnumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getVersiondescstr() {
		return versiondescstr;
	}

	public void setVersiondescstr(String versiondescstr) {
		this.versiondescstr = versiondescstr;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	

	
}
