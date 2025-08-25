package com.woori.codenova.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminCategoryDto {
	private Integer id;

	private String name;

	private Boolean isCheck;

	public AdminCategoryDto toEntity() {
		return new AdminCategoryDto(id, name, isCheck);
	}
}
