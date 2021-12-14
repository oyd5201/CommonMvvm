package com.yqkj.yqframedemo.data.bean;

import java.util.List;

public class RlBean {

    private List<RlBeans>ledgerCalendar;

    public List<RlBeans> getLedgerCalendar() {
        return ledgerCalendar;
    }

    public void setLedgerCalendar(List<RlBeans> ledgerCalendar) {
        this.ledgerCalendar = ledgerCalendar;
    }

   public class RlBeans {
        private String workStatus;
        private String ledgerDate;
        private int ledgerDay;
        private int holidayType;
        private boolean isChoose = false;

        public int getHolidayType() {
            return holidayType;
        }

        public void setHolidayType(int holidayType) {
            this.holidayType = holidayType;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public String getLedgerDate() {
            return ledgerDate;
        }

        public void setLedgerDate(String ledgerDate) {
            this.ledgerDate = ledgerDate;
        }

        public int getLedgerDay() {
            return ledgerDay;
        }

        public void setLedgerDay(int ledgerDay) {
            this.ledgerDay = ledgerDay;
        }
    }
}
