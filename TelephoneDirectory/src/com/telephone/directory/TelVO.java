package com.telephone.directory;


public class TelVO {
	String store_name = null;
	String t_name = null;
	String address = null;
	String tel_num = null;
	String food_style = null;
	String main_dish = null;
	
	public TelVO() {}
	public TelVO(String store_name, String t_name, String address, String tel_num, String food_style, String main_dish) {
		this.store_name = store_name;
		this.t_name = t_name;
		this.address = address;
		this.tel_num = tel_num;
		this.food_style = food_style;
		this.main_dish = main_dish;
		
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	public String getFood_style() {
		return food_style;
	}
	public void setFood_style(String food_style) {
		this.food_style = food_style;
	}
	public String getMain_dish() {
		return main_dish;
	}
	public void setMain_dish(String main_dish) {
		this.main_dish = main_dish;
	}
	
	
	
	
	
}
