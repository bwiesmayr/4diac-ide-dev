/*******************************************************************************
 * Copyright (c) 2014, 2018 fortiss GmbH, Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.providers;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.iec61499.executors.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class FDBK2DevMgmInteractorProvider extends DefaultDevMgmInteractorProvider {
	private static final String PROFILE_NAME = "FBDK2"; //$NON-NLS-1$

	private static final String WRITE_PARAMETER_FBDK2 = "<Request ID=\"{0}\" Action=\"WRITE\"><Parameter Value=\"{1}\" Reference=\"{2}\" /></Request>"; //$NON-NLS-1$

	@Override
	public String getProfileName() {
		return PROFILE_NAME;
	}

	@Override
	public IDeviceManagementInteractor createInteractor(Device dev,
			IDeviceManagementCommunicationHandler overrideHandler) {
		return new DeploymentExecutor(dev, overrideHandler) {

			@Override
			protected String getWriteParameterMessage() {
				return WRITE_PARAMETER_FBDK2;
			}
		};
	}
}
