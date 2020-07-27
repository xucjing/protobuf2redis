package com.yorozuyas.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliverAddress {
	
	private Long id;
	
	private String uid;

	private String name;

	private String address;

	private Long mobile;
}
