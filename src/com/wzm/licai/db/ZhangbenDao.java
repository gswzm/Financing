package com.wzm.licai.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzm.licai.bean.ZhangBen;

public class ZhangbenDao {
	private Context context;
    private UserOpenHelper helper;
    public ZhangbenDao(Context context){
   	   this.context=context;
   	   helper=new UserOpenHelper(context);
    }
    //增加账本
    public void insertZhangBen(String name,String uid){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="insert into zhangben(name,u_id) values(?,?)";
    		db.execSQL(sql, new String[]{name,uid});
    		db.close();
    	}
    }
    //查询账本
    public List<ZhangBen> selectZhangBen(String uid){
    	List<ZhangBen> list=new ArrayList<ZhangBen>();
    	ZhangBen zhangBen=null;
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select _id,name from zhangben where u_id=?";
    		Cursor c=db.rawQuery(sql, new String[]{uid});
    		while(c.moveToNext()){
    			zhangBen=new ZhangBen();
    			zhangBen.setId(c.getInt(c.getColumnIndex("_id")));
    			zhangBen.setName(c.getString(c.getColumnIndex("name")));
    		    list.add(zhangBen);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
    //删除账本
    public void deleteZhangBen(String zid){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="delete from zhangben where _id=?";
    		db.execSQL(sql, new String[]{zid});
    		db.close();
    	}
    }
}
