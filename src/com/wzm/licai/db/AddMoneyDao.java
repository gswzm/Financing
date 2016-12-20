package com.wzm.licai.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzm.licai.bean.AddMoney;

public class AddMoneyDao {
    private Context context;
    private UserOpenHelper helper;
    public AddMoneyDao(Context context) {
      this.context=context;
      helper=new UserOpenHelper(context);
    }
    //增加数据
    public void insertMoney(AddMoney money){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="insert into add_money(money,date,type,mark,w_id,u_id,z_id) "
    				+ "values(?,?,?,?,?,?,?)";
    		db.execSQL(sql, new String[]{money.getMoney()+"",money.getDate()+"",
    				money.getType(),money.getMark(),money.getWid(),money.getUid(),money.getZid()});
            db.close();
    	}
    }
  //查询全部钱通过账本
    public List<Double> selectMoneyByZhangBen(String zid,String date){
    	List<Double> list=new ArrayList<Double>();
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select money from add_money where z_id=? and strftime('%m',date)=?";
    		Cursor c=db.rawQuery(sql, new String[]{zid,date});
    		while(c.moveToNext()){
    			double mm=c.getDouble(c.getColumnIndex("money"));
    			list.add(mm);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
  //查询全部通过月日
    public List<Double> selectMoneyByDay(String date,String zid){
    	List<Double> list=new ArrayList<Double>();
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select money from add_money where strftime('%m%d',date)=? and z_id=? order by date ASC";
    		Cursor c=db.rawQuery(sql, new String[]{date,zid});
    		while(c.moveToNext()){
    			double mm=c.getDouble(c.getColumnIndex("money"));
    			list.add(mm);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
    //查询类型
    public List<Double> selectByType(String type){
    	List<Double> list=new ArrayList<Double>();
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select money from add_money where type=?";
    		Cursor c=db.rawQuery(sql, new String[]{type});
    		while(c.moveToNext()){
    			double mm=c.getDouble(c.getColumnIndex("money"));
    			list.add(mm);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
  //查询id
    public List<Integer> selectId(String date,String zid){
    	List<Integer> list=new ArrayList<Integer>();
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select _id from add_money where strftime('%m',date)=? and z_id=? order by date ASC";
    		Cursor c=db.rawQuery(sql, new String[]{date,zid});
    		while(c.moveToNext()){
    			int mm=c.getInt(c.getColumnIndex("_id"));
    			list.add(mm);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
  //查询钱包id
    public int selectWid(String aid){
    	List<Integer> list=new ArrayList<Integer>();
    	int mm=0;
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select w_id from add_money where _id=?";
    		Cursor c=db.rawQuery(sql, new String[]{aid});
    		while(c.moveToNext()){
    			 mm=c.getInt(c.getColumnIndex("w_id"));
    		}
    		c.close();
    		db.close();
    	}
    	return mm;
    }
  //查询全部
    public List<AddMoney> selectAll(String uid){
    	List<AddMoney> list=new ArrayList<AddMoney>();
    	AddMoney mm=null;
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select * from add_money where u_id=?";
    		Cursor c=db.rawQuery(sql, new String[]{uid});
    		while(c.moveToNext()){
    			mm=new AddMoney();
    		    mm.setId(c.getInt(c.getColumnIndex("_id")));
    		    mm.setType(c.getString(c.getColumnIndex("type")));
    		    mm.setMoney(c.getDouble(c.getColumnIndex("money")));
    		    mm.setDate(c.getString(c.getColumnIndex("date")));
    			list.add(mm);
    		}
    		c.close();
    		db.close();
    	}
    	return list;
    }
    //删除一条
    public void deleteItem(String id){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="delete from add_money where _id=?";
    		db.execSQL(sql, new String[]{id});
            db.close();
    	}
    }
    //删除zhangben
    public void deleteZZb(String zid){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="delete from add_money where z_id=?";
    		db.execSQL(sql, new String[]{zid});
            db.close();
    	}
    }
  //修改
    public void updateItem(String money,String id){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="update add_money set money=? where _id=?";
    		db.execSQL(sql, new String[]{money,id});
            db.close();
    	}
    }
}
