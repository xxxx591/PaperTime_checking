package com.tocheck.parent.common.dto;

/**
 *   微信接口返回对象base
 *	@author Andy
 *  2016年8月4日 上午9:08:23
 */
public class WechatBaseDto {
	private String errcode;
	private String errmsg;
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
