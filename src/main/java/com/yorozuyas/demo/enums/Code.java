package com.yorozuyas.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Code {

	OK(0), BAD(90400), NOT_ALLOWED(90405), UNSUPPORTED_MEDIA_TYPE(90415), INTERNAL_SERVER_ERROR(90500);

	@Getter
	private int code;

}
