package com.zwr.fd.dto;

import lombok.Data;
import java.util.List;

import com.zwr.fd.beans.Setmeal;
import com.zwr.fd.beans.SetmealDish;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
