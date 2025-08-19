package com.woori.codenova.form;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
	@NotEmpty(message = "제목은 필수항목입니다.")
	@Size(max = 200) // 크기
	private String subject;
	@NotEmpty(message = "내용은 필수항목입니다.")
	private String content;

	private List<String> filenames = new ArrayList<>();
}
