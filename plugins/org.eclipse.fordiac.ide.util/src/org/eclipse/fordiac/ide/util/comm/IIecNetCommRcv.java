/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.util.comm.channels.ChannelManager;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;

public abstract class IIecNetCommRcv implements IIecReceivable {

	private ChannelManager m_oManager;
	private List<IEC_ANY> m_loReceiveData;

	private String id;
	
	public IIecNetCommRcv() {
		m_oManager = ChannelManager.getInstance();
		m_loReceiveData=new ArrayList<>();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	protected boolean initialize(String sID, int nChannelType) {
		try {
			m_oManager.register(sID, nChannelType, this);
		} catch (CommException e) {
			//registration failed
			return false;
		}
		return true;
	}

	@Override
	public boolean deInitialize(String id) {
		try {
			m_oManager.deregister(id);
		} catch (CommException e) {
			//deregistration failed
			return false;
		}
		return true;
		
	}
	
	protected boolean receivedDataTypeMatch(List<IEC_ANY> receivedList) {
		if (receivedList.size()!= m_loReceiveData.size())
			return false;
		boolean equal=true;
		for (int i=0;i<receivedList.size();i++)
			if (receivedList.get(i).getClass()!=m_loReceiveData.get(i).getClass())
				equal=false;
		
		return equal;
	}

	@Override
	public void receiveIECData(List<IEC_ANY> inList) {
			if (!receivedDataTypeMatch(inList))
				{
				System.out.println("did not receive expected data");
				return;
				}
			for (int i=0;i<inList.size();i++) {
				m_loReceiveData.get(i).setValue(inList.get(i));
			}
			for (IEC_ANY elem: inList)
					System.out.println("Value: " + elem.toString());
	
		}

	@Override
	public void setMyReceiveData(List<IEC_ANY> receiveData) {
		this.m_loReceiveData = receiveData;
		System.out.println(receiveData.toString());
	}

}