package com.xhk.labmanage.common;


import com.xhk.labmanage.common.constant.ErrorCodeMap;

/**
 * create by xhk on 18/3/4
 */
public class ProjectException extends RuntimeException{

    private int errorNo;
    private String errorMsg;

    public ProjectException(int errorNo, String errorMsg){
        this.setErrorMsg(errorMsg);
        this.setErrorNo(errorNo);
    }
    public ProjectException(int errorNo){
        this.setErrorMsg(ErrorCodeMap.getErrorMsg(errorNo));
        this.setErrorNo(errorNo);
    }
    public ProjectException() {
    }
    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
