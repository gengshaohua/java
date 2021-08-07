package ioc.service.impl;


import ioc.annotation.GPService;
import ioc.service.IDemoService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	@Override
	public String get(String name) {
		return "My name is " + name;
	}

}
