package com.yorozuyas.demo.controller.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseEntity<T> {
	
	private Integer code;
	
	private String errMsg;
	
	private T data;

}
