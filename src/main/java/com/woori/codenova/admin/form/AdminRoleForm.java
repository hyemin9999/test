package com.woori.codenova.admin.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRoleForm {
	private Integer id;

	@NotEmpty(message = "역할 이름은 필수항목입니다.")
	private String name;

	private Boolean isChecked;

//	private List<AdminCategoryDto> clist;
}
