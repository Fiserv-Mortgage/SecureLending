package com.fiserv.sl.service;

import com.fiserv.sl.vo.SLAutoWrapper;

/**
 * 
 * @author atul.singh
 *
 */
public interface SLAutoService extends SLService {

	public void executeTestSuite(SLAutoWrapper slAutoWrapper);
}
