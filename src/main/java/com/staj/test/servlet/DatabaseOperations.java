package com.staj.test.servlet;

import java.util.ArrayList;
import java.util.Collection;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;

import net.java.ao.Query;

public class DatabaseOperations {
	
	public DatabaseOperations() {
		
	}
	
	public ArrayList<Object> RetrieveFromDatabase(ActiveObjects ao){
		ArrayList<Object> lists = new ArrayList<>();
        for (MyPluginEntities ent : ao.find(MyPluginEntities.class)) 
	    {
	       lists.add(ent);
	                  
	    }
		return lists;
 }
	public MyPluginEntities AddDatabase(ActiveObjects ao,String name){
		MyPluginEntities[] entities = ao.find(MyPluginEntities.class, Query.select().where("NAME = ?", name));
        if(entities.length>=0) {
        	final MyPluginEntities ent = ao.create(MyPluginEntities.class); 
        	ent.setName(name);
        	ent.save(); 
        	return ent;
        }
        return null;
        
	}
	public void DeleteDatabase(ActiveObjects ao,String name){

		MyPluginEntities[] lists = ao.find(MyPluginEntities.class, Query.select().where("NAME = ?", name));
  		 ao.delete(lists[0]);
	}
	public void UpdateDatabase(ActiveObjects ao,String name,String user1,String user2){

  		 MyPluginEntities[] lists = ao.find(MyPluginEntities.class, Query.select().where("NAME = ?", name));
  		 lists[0].setUser1(user1);
  		 lists[0].setUser2(user2);
  		 lists[0].save();
	}
	public MyPluginEntities FindDatabase(ActiveObjects ao,String val){

		MyPluginEntities[] ent= ao.find(MyPluginEntities.class,Query.select().where("NAME= ?",val));
		return ent[0];
	}
	public Collection<ApplicationUser> RetrieveUserFromDatabase(ActiveObjects ao){
		UserManager userMan = ComponentAccessor.getUserManager();
	    Collection<ApplicationUser> users;
		users =userMan.getAllUsers();
		return users;
 }
	

}