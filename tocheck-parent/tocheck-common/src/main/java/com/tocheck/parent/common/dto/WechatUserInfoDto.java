package com.tocheck.parent.common.dto;

/**
 *  请求userinfo接口返回
 *	@author Andy
 *  2016年8月4日 上午10:14:50
 */
public class WechatUserInfoDto extends WechatBaseDto{
	private String openid;
	private String nickname;
	private int sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String privilege[];
	private String unionid;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String[] getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
}
