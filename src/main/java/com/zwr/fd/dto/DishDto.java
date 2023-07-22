package com.zwr.fd.dto;

import java.util.ArrayList;
import java.util.List;

import com.zwr.fd.beans.Dish;
import com.zwr.fd.beans.DishFlavor;

import lombok.Data;


@Data
public class DishDto  extends Dish{
	
	private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

}
