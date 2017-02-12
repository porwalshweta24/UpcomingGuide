package com.android.upcomingguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.android.upcomingguide.Pojo.UpcomingGuides;

import java.util.ArrayList;

/**
 * Created by shweta on 2/11/2017.
 */

public class DBCartAdapter extends DatabaseConnectivity  {
    public static final String TABLE_NAME_MENU_ITEM = "MenuItem";
    public static final String CART_ID = "cartId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String OBJ_TYPE = "objType";
    public static final String NAME = "name";
    public static final String LOGIN_REQUIRED = "loginRequired";
    public static final String URL = "url";
    public static final String ICON = "icon";
    public static final String QUANTITY = "qty";


    public DBCartAdapter(Context context) {
        super(context);
    }
    // ---opens the database---
    public DBCartAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }

    public long createNewItem(ArrayList<UpcomingGuides.Datas> datasArrayList) {
        long ret = 0l;
        try {
            open();
            //saving menu items
            for (UpcomingGuides.Datas datas : datasArrayList) {
                ContentValues initialValues = new ContentValues();
                initialValues.put(START_DATE, datas.getStartDate());
                initialValues.put(END_DATE, datas.getEndDate());
                initialValues.put(NAME, datas.getName());
                initialValues.put(OBJ_TYPE, datas.getObjType());
                initialValues.put(LOGIN_REQUIRED, datas.getLoginRequired());
                initialValues.put(URL, datas.getUrl());
                initialValues.put(ICON, datas.getIcon());
                initialValues.put(QUANTITY, 0);
                db.insert(TABLE_NAME_MENU_ITEM, null, initialValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return ret;
    }

   public void updateMenuItemDetailInCart(UpcomingGuides.Datas datas) {
        try {
            open();
            ContentValues data = new ContentValues();
            data.put(QUANTITY, datas.getQuantity() + 1);
            db.update(TABLE_NAME_MENU_ITEM, data, CART_ID + "=" + datas.getId(), null);
        } catch (Exception e) {
            close();
        } finally {
            close();
        }
    }


    public void deleteMenuItemDetailInCart(UpcomingGuides.Datas datas) {
        try {
            open();
            ContentValues data = new ContentValues();
            data.put(QUANTITY, datas.getQuantity() - 1);
            db.update(TABLE_NAME_MENU_ITEM, data, CART_ID + "=" + datas.getId(), null);
        } catch (Exception e) {
            close();
        } finally {
            close();
        }
    }

    public int getQuantityValue(UpcomingGuides.Datas datas) {
        Cursor cursor1;
        open();
        int quantity=0;
        String selectQuery = "SELECT * from " + TABLE_NAME_MENU_ITEM + " WHERE " + CART_ID + " = " + datas.getId() + "";
        cursor1 = db.rawQuery(selectQuery, null);
        try {

            if (cursor1 != null) {
                if (cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                    do {
                        quantity=(cursor1.getInt(cursor1.getColumnIndex(QUANTITY)));
                    } while (cursor1.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor1.close();
            close();
        }
        return quantity;
    }
    public UpcomingGuides.Datas getSingleData(UpcomingGuides.Datas datas)
    {
        Cursor cursor1;
        open();
        UpcomingGuides.Datas itemsVO = new UpcomingGuides.Datas();

        String selectQuery = "SELECT * from " + TABLE_NAME_MENU_ITEM + " WHERE " + CART_ID + " = " + datas.getId() + "";
        cursor1 = db.rawQuery(selectQuery, null);
        try {

            if (cursor1 != null) {
                if (cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                        itemsVO.setId((cursor1.getInt(cursor1.getColumnIndex(CART_ID))));
                        itemsVO.setQuantity((cursor1.getInt(cursor1.getColumnIndex(QUANTITY))));
                        itemsVO.setEndDate((cursor1.getString(cursor1.getColumnIndex(END_DATE))));
                        itemsVO.setName((cursor1.getString(cursor1.getColumnIndex(NAME))));
                        itemsVO.setStartDate((cursor1.getString(cursor1.getColumnIndex(START_DATE))));
                        itemsVO.setIcon((cursor1.getString(cursor1.getColumnIndex(ICON))));
                        itemsVO.setLoginRequired((cursor1.getString(cursor1.getColumnIndex(LOGIN_REQUIRED))));
                        itemsVO.setUrl((cursor1.getString(cursor1.getColumnIndex(URL))));
                        itemsVO.setObjType((cursor1.getString(cursor1.getColumnIndex(OBJ_TYPE))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor1.close();
            close();
        }
        return  itemsVO;
    }
    public ArrayList<UpcomingGuides.Datas> getMenuListForAll() {
        ArrayList<UpcomingGuides.Datas> upcomingDataList = new ArrayList<UpcomingGuides.Datas>();
        Cursor cursor1;
        open();

        String selectQuery = "SELECT * from " + TABLE_NAME_MENU_ITEM ;
        cursor1 = db.rawQuery(selectQuery, null);
        try {

            if (cursor1 != null) {
                if (cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                    do {
                        UpcomingGuides.Datas itemsVO = new UpcomingGuides.Datas();
                        itemsVO.setId((cursor1.getInt(cursor1.getColumnIndex(CART_ID))));
                        itemsVO.setQuantity((cursor1.getInt(cursor1.getColumnIndex(QUANTITY))));
                        itemsVO.setEndDate((cursor1.getString(cursor1.getColumnIndex(END_DATE))));
                        itemsVO.setName((cursor1.getString(cursor1.getColumnIndex(NAME))));
                        itemsVO.setStartDate((cursor1.getString(cursor1.getColumnIndex(START_DATE))));
                        itemsVO.setIcon((cursor1.getString(cursor1.getColumnIndex(ICON))));
                        itemsVO.setLoginRequired((cursor1.getString(cursor1.getColumnIndex(LOGIN_REQUIRED))));
                        itemsVO.setUrl((cursor1.getString(cursor1.getColumnIndex(URL))));
                        itemsVO.setObjType((cursor1.getString(cursor1.getColumnIndex(OBJ_TYPE))));
                        upcomingDataList.add(itemsVO);
                    } while (cursor1.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor1.close();
            close();
        }
        return upcomingDataList;
    }

    public int getCartValueCount() {
        Cursor cursor1;
        open();
        int count=0;
        String selectQuery = "SELECT * from " + TABLE_NAME_MENU_ITEM ;
        cursor1 = db.rawQuery(selectQuery, null);
        try {

            if (cursor1 != null) {
                if (cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                    do {
                        int quantity=(cursor1.getInt(cursor1.getColumnIndex(QUANTITY)));
                        if(quantity>0)
                            count++;
                    } while (cursor1.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor1.close();
            close();
        }
        return count;
    }
    public void deleteFromCart() {
        try {
            open();
            db.delete(TABLE_NAME_MENU_ITEM, "", null);

        } catch (Exception e) {
            close();
        } finally {
            close();
        }
    }
}
