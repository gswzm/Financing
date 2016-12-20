package com.wzm.licai.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.wzm.licai.bean.UserBean;

public class UserDao {
     private Context context;
     private UserOpenHelper helper;
     public UserDao(Context context){
    	 this.context=context;
    	 helper=new UserOpenHelper(context);
     }
     //增加用户
     public void insert(String name,String pwd){
    	
    	 SQLiteDatabase db=helper.getWritableDatabase();
    	 if(db.isOpen()){
    		 String sql="insert into "+UserBean.TABLE_USER+"("+
    	                UserBean.NAME+","+UserBean.PWD+")"+" values(?,?)";
    		 db.execSQL(sql, new String[]{name,pwd});
    		 db.close();
       }
     }
     //更新名字
     public void updateName(String newname,String oldname){
    	 SQLiteDatabase db=helper.getWritableDatabase();
    	 if(db.isOpen()){
    		 String sql="update "+UserBean.TABLE_USER+" set "+
    	             UserBean.NAME+"=? where "+UserBean.NAME+"=?";
    		 db.execSQL(sql, new String[]{newname,oldname});
    		 db.close();
    	 }
     }
     //更新密码
     public void updatePwd(String oldpwd,String name){
    	 SQLiteDatabase db=helper.getWritableDatabase();
    	 if(db.isOpen()){
    		 String sql="update "+UserBean.TABLE_USER+" set "+
    	             UserBean.PWD+"=? where "+UserBean.NAME+"=?";
    		 db.execSQL(sql, new String[]{oldpwd,name});
    		 db.close();
    	 }
     }
   //查密码
 	public String selectPwd(String name){
 		String pwd="";
 		SQLiteDatabase db=helper.getReadableDatabase();
 		if(db.isOpen()){
 			Cursor cursor=db.rawQuery("select password from user where name=?", new String[]{name});//查找
 			if(cursor.moveToNext()){
 				pwd=cursor.getString(cursor.getColumnIndex("password"));
 			}
 			cursor.close();
 			db.close();
 		}
 		return pwd;
 	}
 	//查id
 	 	public int selectId(String name){
 	 		int pwd=0;
 	 		SQLiteDatabase db=helper.getReadableDatabase();
 	 		if(db.isOpen()){
 	 			Cursor cursor=db.rawQuery("select _id from user where name=?", new String[]{name});//查找
 	 			if(cursor.moveToNext()){
 	 				pwd=cursor.getInt(cursor.getColumnIndex("_id"));
 	 			}
 	 			cursor.close();
 	 			db.close();
 	 		}
 	 		return pwd;
 	 	}
     //查询全部成员
     public List<UserBean> selectAll(){
    	 List<UserBean> users=new ArrayList<UserBean>();
    	 SQLiteDatabase db=helper.getReadableDatabase();
    	 if(db.isOpen()){
    		 String sql="select * from "+UserBean.TABLE_USER;
    		 Cursor cursor=db.rawQuery(sql, null);
    		 while(cursor.moveToNext()){
    			 UserBean user=new UserBean();
                 user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                 user.setName(cursor.getString(cursor.getColumnIndex("name")));
                 user.setPwd(cursor.getString(cursor.getColumnIndex("password")));
                 users.add(user);
    		 }
    		 cursor.close();
    		 db.close();
    	 }
    	 return users;
     }
}
