package com.wzm.licai.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzm.licai.bean.Wallet;

public class WalletDao {
	private Context context;
    private UserOpenHelper helper;
    public WalletDao(Context context){
   	   this.context=context;
   	   helper=new UserOpenHelper(context);
    }
    //增加钱包
    public void insertWallet(Wallet wallet,String uid){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		String sql="insert into wallet(name,money,u_id) values(?,?,?)";
    		db.execSQL(sql, new String[]{wallet.getName(),wallet.getMoney()+"",uid});
    		db.close();
    	}
    }
    //查询钱包
    public List<Wallet> seletWallet(String uid){
    	List<Wallet> wa=new ArrayList<Wallet>();
    	SQLiteDatabase db=helper.getReadableDatabase();
    	if(db.isOpen()){
    		String sql="select * from wallet where u_id=?";
    		Cursor c=db.rawQuery(sql, new String[]{uid});
    		while(c.moveToNext()){
    			Wallet w=new Wallet();
    			w.setId(c.getInt(c.getColumnIndex("_id")));
    			w.setName(c.getString(c.getColumnIndex("name")));
    			w.setMoney(c.getDouble(c.getColumnIndex("money")));
    			wa.add(w);
    		}
    	}
    	return wa;
    }
    //修改
    public void updateWallet(String money,String wid){
    	SQLiteDatabase db=helper.getWritableDatabase();
    	if(db.isOpen()){
    		System.out.println("helperzhiixng");
    		String sql="update wallet set money=money+? where _id=?";
    		db.execSQL(sql, new String[]{money,wid});
    		db.close();
    	}
    }
}
