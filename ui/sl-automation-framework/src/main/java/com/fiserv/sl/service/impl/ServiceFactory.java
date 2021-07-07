package com.fiserv.sl.service.impl;

import com.fiserv.sl.constant.ServiceType;
import com.fiserv.sl.service.SLAutoService;
import com.fiserv.sl.service.SLService;

/**
 * 
 * @author atul.singh
 *
 */
public class ServiceFactory {
	private ServiceFactory() {
	}
	private static ServiceFactory factory=null;
	
	@SuppressWarnings("unchecked")
	public <E extends SLService>  E getService(ServiceType type) {
		if (type.equals(ServiceType.AUTOMATION_SERVICE)) {
			SLAutoService service=SLAutoServiceImpl.getInstance();
			return (E) service;
		}
		return null;
	}

	public static ServiceFactory getFactory() {
		if(factory==null){
		return new ServiceFactory();
		}
		return factory;
	}
}
