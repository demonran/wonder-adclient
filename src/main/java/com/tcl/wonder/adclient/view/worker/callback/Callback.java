package com.tcl.wonder.adclient.view.worker.callback;

import java.util.Map;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;

/**
 * �ص��ӿ�
 * @author liuran
 * 2015��1��23��
 *
 */
public interface Callback
{
	public void call(Map<String,Ad> map);
	
	public void call(Ad ad);

	void call(Video video);
	
}
