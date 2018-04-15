package com.zichen.world.base;

/**
 * Created by Administrator on 2018/1/1.
 */

public class BookkeepingBook {

    private int id;
    private String accountId;//记账人ID
    private String createDate;//记账日期
    private String modifyDate;//修账日期
    private String accountTitle;//记账标题
    private String accountNote;//记账笔记
    private String money;//金额
    private boolean isIncome;//是否是收入 0:支出;1:收入

    public BookkeepingBook() {
    }

    public BookkeepingBook(String accountId, String createDate, String modifyDate, String accountTitle, String accountNote, String money, boolean isIncome) {
        this.accountId = accountId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.accountTitle = accountTitle;
        this.accountNote = accountNote;
        this.money = money;
        this.isIncome = isIncome;
    }

    public BookkeepingBook(int id, String accountId, String createDate, String modifyDate, String accountTitle, String accountNote, String money, boolean isIncome) {
        this.id = id;
        this.accountId = accountId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.accountTitle = accountTitle;
        this.accountNote = accountNote;
        this.money = money;
        this.isIncome = isIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getAccountNote() {
        return accountNote;
    }

    public void setAccountNote(String accountNote) {
        this.accountNote = accountNote;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(boolean income) {
        isIncome = income;
    }

    @Override
    public String toString() {
        return "BookkeepingBook{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", accountTitle='" + accountTitle + '\'' +
                ", accountNote='" + accountNote + '\'' +
                ", money='" + money + '\'' +
                ", isIncome=" + isIncome +
                '}';
    }
}
