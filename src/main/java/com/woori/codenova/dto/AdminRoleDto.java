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
public class AdminRoleDto {

	private Integer id;

	private String name;

	private Boolean isCheck;

	public AdminRoleDto toEntity() {
		return new AdminRoleDto(id, name, isCheck);
	}
}
