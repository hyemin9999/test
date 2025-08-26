package com.woori.codenova;

import lombok.Getter;

@Getter
public enum SearchType {

	제목("ROLE_ADMIN"), USER("ROLE_USER");

	SearchType(String value) {
		this.value = value;
	}

	private String value;
}
