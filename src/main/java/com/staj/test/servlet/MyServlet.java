package com.staj.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.staj.test.servlet.DatabaseOperations;

import net.java.ao.EntityManager;
import net.java.ao.Query;

@Scanned
public class MyServlet extends HttpServlet{

	private static final Logger log = LoggerFactory.getLogger(MyServlet.class);

    @ComponentImport
    private final TemplateRenderer templateRenderer;
    private DatabaseOperations dop=new DatabaseOperations();
    @ComponentImport
    private final ActiveObjects ao;
    
    public MyServlet(TemplateRenderer templateRenderer,ActiveObjects ao) {
		this.templateRenderer = templateRenderer;
		this.ao=ao;
		
	}
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	
    	Map<String, Object> context = new HashMap<>();
    	context.put("message", "MyPluginEntities Name");
    	
    	ArrayList<Object> lists = new ArrayList<>();
    	
    	lists=dop.RetrieveFromDatabase(ao);
        context.put("lists",lists);
        templateRenderer.render("/templates/test.vm", context,resp.getWriter());

    	
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	 
    	if(req.getParameter("add")!=null) {
    		 final String name= req.getParameter("name");
    		 dop.AddDatabase(ao, name);
             
    	}
    	else if(req.getParameter("delete")!=null) {
    		 final String name= req.getParameter("delete");
    		 dop.DeleteDatabase(ao, name);
    	         
    	}
    	else if(req.getParameter("save")!=null) {
    		
	   		 final String name=req.getParameter("save");
	   		 final String user1= req.getParameter("first");
	   		 final String user2=req.getParameter("second");
	   		 dop.UpdateDatabase(ao,name,user1,user2);
            
   	 
    	}
         resp.sendRedirect(req.getContextPath() + "/plugins/servlet/myservlet");
        
    }

}