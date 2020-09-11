/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.uiautomator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.android.uiautomator.util.JdbcUtil;
import com.android.uiautomator.util.verifyJavaName;
/**
 * Implements a file selection dialog for both screen shot and xml dump file
 *
 * "OK" button won't be enabled unless both files are selected
 * It also has a convenience feature such that if one file has been picked, and the other
 * file path is empty, then selection for the other file will start from the same base folder
 *
 */
public class ControlDefineDialog extends Dialog {
    private static final int FIXED_TEXT_FIELD_WIDTH = 300;
    private static final int DEFAULT_LAYOUT_SPACING = 20;
    private Text mXpathText;
    private Text mResourceIdText;
    private Text mDescriptionText;
    private Text mControlNameText;
    private String selprojectname;
    private Integer selprojectid;
    private String selprojectver;
    private Integer selversionid;
    private boolean mFileChanged = false;
    private Button mOkButton;
    private String mControlClass;
    private static File sScreenshotFile;
    private static File sXmlDumpFile;

    private List list;

    private java.util.List<Map<String, String>> controlList;

    /**
     * Create the dialog.
     *
     * @param parentShell
     */
    public ControlDefineDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.MODELESS);
        controlList = new ArrayList<Map<String, String>>();
    }

    /**
     * Create contents of the dialog.
     *
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout gl_container = new GridLayout(2, false);
        gl_container.verticalSpacing = DEFAULT_LAYOUT_SPACING;
        gl_container.horizontalSpacing = DEFAULT_LAYOUT_SPACING;
        gl_container.marginWidth = DEFAULT_LAYOUT_SPACING;
        gl_container.marginHeight = DEFAULT_LAYOUT_SPACING;
        container.setLayout(gl_container);

        Group openScreenshotGroup = new Group(container, SWT.NONE);
        openScreenshotGroup.setLayout(new GridLayout(1, false));
        openScreenshotGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        openScreenshotGroup.setText("Xpath");

        mXpathText = new Text(openScreenshotGroup, SWT.BORDER | SWT.READ_ONLY);
        mXpathText.setEditable(true);

        GridData gd_screenShotText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_screenShotText.minimumWidth = FIXED_TEXT_FIELD_WIDTH;
        gd_screenShotText.widthHint = FIXED_TEXT_FIELD_WIDTH;
        mXpathText.setLayoutData(gd_screenShotText);

        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));
        GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false, 1,4);
        gd_composite.widthHint = 171;
        composite.setLayoutData(gd_composite);
        Group group = new Group(composite, SWT.NONE);
        group.setText("已选控件");
        group.setLayout(new FillLayout(SWT.HORIZONTAL));
        list = new List(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        list.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                updateSelControlInfo();
            }
        });

        Group openResourceGroup = new Group(container, SWT.NONE);
        openResourceGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        openResourceGroup.setText("resource-id");
        openResourceGroup.setLayout(new GridLayout(1, false));

        mResourceIdText = new Text(openResourceGroup, SWT.BORDER | SWT.READ_ONLY);
        mResourceIdText.setEditable(true);

        GridData rs_xmlText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        rs_xmlText.minimumWidth = FIXED_TEXT_FIELD_WIDTH;
        rs_xmlText.widthHint = FIXED_TEXT_FIELD_WIDTH;
        mResourceIdText.setLayoutData(rs_xmlText);
        mResourceIdText.setEditable(true);
        
        
        Group openXmlGroup = new Group(container, SWT.NONE);
        openXmlGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        openXmlGroup.setText("描述");
        openXmlGroup.setLayout(new GridLayout(1, false));

        mDescriptionText = new Text(openXmlGroup, SWT.BORDER | SWT.READ_ONLY);
        mDescriptionText.setEditable(true);

        GridData gd_xmlText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_xmlText.minimumWidth = FIXED_TEXT_FIELD_WIDTH;
        gd_xmlText.widthHint = FIXED_TEXT_FIELD_WIDTH;
        mDescriptionText.setLayoutData(gd_xmlText);
        mDescriptionText.setEditable(true);

        Group controlName = new Group(container, SWT.NONE);
        controlName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        controlName.setText("控件名称");
        controlName.setLayout(new GridLayout(1, false));

        mControlNameText = new Text(controlName, SWT.BORDER | SWT.READ_ONLY);
        mControlNameText.setEditable(true);

        GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_xmlText.minimumWidth = FIXED_TEXT_FIELD_WIDTH;
        gd_xmlText.widthHint = FIXED_TEXT_FIELD_WIDTH;
        mControlNameText.setLayoutData(gd_nameText);

		Combo combo = new Combo(container, SWT.DROP_DOWN);
		Combo combo1 = new Combo(container, SWT.DROP_DOWN);
		combo1.setSize(100, 50);
		
		
		String[] strs = new JdbcUtil().getprojectlist();
		combo.setItems(strs);
		combo.setData(strs);
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				String projectname = ((Combo)arg0.getSource()).getText();
				selprojectname = projectname;
				Integer projectid = new JdbcUtil().getProjectId(projectname);
				selprojectid = projectid;
				String[] pvversion = new JdbcUtil().getProjectVersion(String.valueOf(projectid));
				combo1.setSize(100, 50);
				
				combo1.setItems(pvversion);
				combo1.setData(pvversion);
				combo1.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent arg0) 
					{
						 selprojectver = ((Combo)arg0.getSource()).getText();
						 selversionid = new JdbcUtil().getProjecVersiontId(selprojectver);
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				System.out.println(((Combo)arg0.getSource()).getText());
			}
		});
		
		
        
        Button addButton = new Button(container, SWT.NONE);
        addButton.setText("添加控件");
        addButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                addControl();
            }
        });

        Button delButton = new Button(container, SWT.NONE);
        delButton.setText("删除选中");
        delButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                delControl();
            }
        });

        Button saveModifyButton = new Button(container, SWT.NONE);
        saveModifyButton.setText("保存修改");
        saveModifyButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                saveModify();
            }
        });

        mXpathText.setText("xpath");
        mResourceIdText.setText("resource-id");
        mDescriptionText.setText("描述");
        mControlNameText.setText("xyzccontro1");

        return container;
    }

    /**
     * Create contents of the button bar.
     *
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        mOkButton = createButton(parent, IDialogConstants.OK_ID, "导出到对象库", true);
        mOkButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
            	savetodatabase();
            	//saveFile();
            }
        });
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }
    
    private void savetodatabase() 
    {
    	if(selprojectname==null||"".equals(selprojectname)) 
    	{
    		  warning("请选择项目！");
    		  return;
    	}
    	if(selprojectver==null||"".equals(selprojectver)) 
    	{
    		  warning("请选择项目版本！");
    		  return;
    	}
    	if(controlList.size()<=0) {
    		  warning("请添加需要导入对象库的控件！");
    		  return;
    	}
    	String date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	JdbcUtil util = new JdbcUtil();
    	for(int i=0;i<controlList.size();i++) 
    	{
		    String resourceid = controlList.get(i).get("resourceid");
		    String description = controlList.get(i).get("desp");
		    String mxpath = controlList.get(i).get("xpath");
            if(null!=resourceid&&!"".equals(resourceid))
            {
            	String elementname = "resource-id="+resourceid;
            	if(util.getCount(selprojectid, selversionid, elementname)<=0)
            	util.insertObjectLibrary(selprojectid, selversionid, date, "元素工具导入", elementname, "resource-id", "Android", description);
            }else 
            {
            	String elementname = "xpath="+mxpath;
            	if(util.getCount(selprojectid, selversionid, elementname)<=0)
            	util.insertObjectLibrary(selprojectid, selversionid, date, "元素工具导入", elementname, "xpath", "Android", description);
            }
		    String mclass = controlList.get(i).get("class");
            String name = controlList.get(i).get("controlName");
            System.out.println(mclass+description+mxpath+resourceid+name);
                
    	}
    	
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
        return new Point(568, 480);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("控件—变量定义");
    }

    public void updateSelXpathInfo(String xpath,String resourceid,String description, String controlClass) {
       
    	mXpathText.setText(xpath);
    	mResourceIdText.setText(resourceid);
    	mDescriptionText.setText(description);
        mControlClass = controlClass;
    }

    public void addControl() {
        Map<String, String> controlInfoMap = new HashMap<String, String>();
        controlInfoMap.put("xpath", mXpathText.getText());
        controlInfoMap.put("desp", mDescriptionText.getText());
        String name = mControlNameText.getText();
        controlInfoMap.put("controlName", name);
        controlInfoMap.put("class", mControlClass);
        controlInfoMap.put("resourceid", mResourceIdText.getText());
        if (verifyControlName(name)) {
            controlList.add(controlInfoMap);
            updateListView();
        }
    }

    public void delControl() {
        if (0 == list.getSelectionCount()) {
            warning("先选择要删除的控件");
            return;
        }
        String selName = list.getSelection()[0];
        int index = -1;
        for (index = 0; index < controlList.size(); ++index) {
            if (controlList.get(index).get("controlName").equals(selName))
                break;
        }
        if (index == controlList.size()) {
            warning("控件删除失败");
            return;
        }
        controlList.remove(index);
        updateListView();
    }

    public void saveModify() {
        String selName = list.getSelection()[0];
        int index = -1;
        for (index = 0; index < controlList.size(); ++index) {
            if (controlList.get(index).get("controlName").equals(selName))
                break;
        }
        if (index == controlList.size()) {
            warning("出现未知错误");
            return;
        }
        String xpath = mXpathText.getText();
        String resourceid = mResourceIdText.getText();
        String description = mDescriptionText.getText();
        String name = mControlNameText.getText();
        //controlList.remove(index);
        controlList.get(index).put("xpath", xpath);
        controlList.get(index).put("resourceid", resourceid);
        controlList.get(index).put("desp", description);
        controlList.get(index).put("controlName", name);
        updateListView();
    }

    public void updateListView() {
        list.removeAll();
        for (int i = 0; i < controlList.size(); ++i) {
            String name = controlList.get(i).get("controlName");
            list.add(name);
        }
    }

    public void updateSelControlInfo() {
        String xpath = "";
        String resourceid="";
        String description = "";
        String name = "";
   
        String selName = list.getSelection()[0];
        int index = -1;
        for (index = 0; index < controlList.size(); ++index) {
            if (controlList.get(index).get("controlName").equals(selName))
                break;
        }
        if (index == controlList.size()) {
            warning("出现未知错误");
            return;
        }
        xpath = controlList.get(index).get("xpath");
        resourceid = controlList.get(index).get("resourceid");
        description = controlList.get(index).get("desp");
        name = controlList.get(index).get("controlName");
       
        mXpathText.setText(xpath);
        mResourceIdText.setText(resourceid);
        mDescriptionText.setText(description);
        mControlNameText.setText(name);
    }

    public boolean verifyControlName(String name) {
        boolean result = false;
        String res = verifyJavaName.checkJavaName(name);
        if (res.equals("SUCCESS")) {
            result = true;
        } else {
            warning("变量名不符合命名规范，请重新输入变量名");
            result = false;
        }
        for (int i = 0; i<controlList.size(); ++i) {
            if (controlList.get(i).get("controlName").equals(name)) {
                warning("已存在同名变量，请重新输入变量名");
                result = false;
            }
        }
        return result;
    }

    public void saveFile() {
        try
        {
           FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.SAVE);
           dialog.setText("保存java文件");
           dialog.setOverwrite(true);
           dialog.setFilterExtensions(new String[]{"*.java"});
           String file = dialog.open();
            if (file == null) {
                return;
            }
            String packageName = "test";
            String fileName = dialog.getFileName();
            String className = fileName.substring(0, fileName.indexOf("."));
            String path = "";
            if (file.contains("com")) {
                path = dialog.getFilterPath();
                int begin = path.indexOf("com");
                packageName = path.substring(begin, path.length()).replace("\\", ".");
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
            writer.write("package " + packageName + ";\r\n");
            writer.write("import com.android.annotations.*;\r\n" +
                    "import com.android.bean.BaseBean;\r\n" +
                    "import com.android.controls.*;\r\n" +
                    "import io.appium.java_client.AppiumDriver;\r\n\r\n");

            writer.write("public class " + className + " extends BaseBean{\r\n\r\n");

            for (int i = 0; i < controlList.size(); i++) {
                String mclass = controlList.get(i).get("class");
                String description = controlList.get(i).get("desp");
                String mxpath = controlList.get(i).get("xpath");
                String name = controlList.get(i).get("controlName");
                writer.write("    @Xpath(xpath={\"" + mxpath.replace("\\\"", "'") + "\"})\r\n");
                writer.write("    @Description(description=\"" + description + "\")\r\n");

                if (mclass.equals("android.widget.TextView")) {
                        writer.write("    public  PlainText  " + name + ";\r\n");
                } else if (mclass.equals("android.widget.EditText")) {
                        writer.write("    public  Text  " + name + ";\r\n");
                } else if (mclass.equals("android.widget.Button") || mclass.equals("android.widget.CheckBox") || mclass.equals("android.widget.RadioButton")) {
                        writer.write("    public  Click  " + name + ";\r\n");
                } else if (mclass.equals("android.widget.Spinner")) {
                        writer.write("    public  Select  " + name + ";\r\n");
                } else {
                        writer.write("    public  View  " + name + ";\r\n");
                    }
                writer.write("\r\n");
            }
            writer.write("\r\n");
            writer.write("    public " + className + "(AppiumDriver aDriver){super(aDriver);}\r\n\r\n");
            writer.write("}");
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
   }

    public void warning(String message) {
        MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.OK| SWT.ICON_WARNING);
        messageBox.setMessage(message);
        messageBox.open();
    }
}