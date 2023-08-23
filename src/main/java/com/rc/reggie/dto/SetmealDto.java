package com.rc.reggie.dto;


import com.rc.reggie.entity.Setmeal;
import com.rc.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
