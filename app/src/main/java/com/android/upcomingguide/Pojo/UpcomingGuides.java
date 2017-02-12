package com.android.upcomingguide.Pojo;

import java.util.ArrayList;

/**
 * Created by shweta on 2/10/2017.
 */

public class UpcomingGuides {
    String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Datas> getData() {
        return data;
    }

    public void setData(ArrayList<Datas> data) {
        this.data = data;
    }

    ArrayList<Datas> data;
    public static class Datas{
        String startDate,objType,endDate,name,loginRequired,url,icon;
        Venues venue;
        int quantity,id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getObjType() {
            return objType;
        }

        public void setObjType(String objType) {
            this.objType = objType;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLoginRequired() {
            return loginRequired;
        }

        public void setLoginRequired(String loginRequired) {
            this.loginRequired = loginRequired;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Venues getVenue() {
            return venue;
        }

        public void setVenue(Venues venue) {
            this.venue = venue;
        }
    }
    class Venues{

    }
}
