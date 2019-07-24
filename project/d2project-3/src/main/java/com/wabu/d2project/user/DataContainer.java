package com.wabu.d2project.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class DataContainer {
	String column1;
	String column2;
	String column3;
	String column4;
	String column5;
	String column6;
	
	@Override
	public String toString() {
		return String.format("column1:%s, column2:%s, column3:%s, column4:%s, column5:%s, column6:%s",
				column1, column2, column3, column4, column5, column6);
	}
}
