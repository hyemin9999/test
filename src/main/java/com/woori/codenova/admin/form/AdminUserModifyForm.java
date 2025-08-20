package com.woori.codenova.admin.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserModifyForm {

	private Long id;
	private String username;

	@NotEmpty(message = "변경 비밀번호는 필수항목입니다.")
	private String password1;

	@Email
	private String email;

}
