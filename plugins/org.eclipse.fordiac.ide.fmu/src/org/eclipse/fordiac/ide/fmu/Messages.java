/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2020 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *	   - Externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fmu.messages"; //$NON-NLS-1$

	public static String CreateFMUWizard_FMUCreationError;
	public static String CreateFMUWizardPage_IncludeTheFollowingLibrariesInExportedFMU;
	public static String CreateFMUWizardPage_NoLibrariesSelectedToInclude;
	public static String CreateFMUWizardPage_SaveSelectedLibrariesForFutureFMUExports;
	public static String FMUDeviceManagementCommunicationHandler_BinaryDirectoryDoesNotExist;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateTheComponentsInsideTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateFolderInTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_DoYouWantToRetry;
	public static String FMUDeviceManagementCommunicationHandler_GeneratingFMUsForDevice;
	public static String FMUDeviceManagementCommunicationHandler_InternalCopyingError;
	public static String FMUDeviceManagementCommunicationHandler_LibraryCouldNotBeFound;
	public static String FMUDeviceManagementCommunicationHandler_NoSelectedLibrariesWereFound;
	public static String FMUDeviceManagementCommunicationHandler_OutputFMUFileExistsOverwriteIt;
	public static String FMUDeviceManagementCommunicationHandler_OutputFolderDoesNotExistAndCouldNotBeCreated;
	public static String FMUDeviceManagementCommunicationHandler_TheDirectoryIsInvalid;
	public static String FMUDeviceManagementCommunicationHandler_UnableToCreateFolder;
	public static String FMUPreferencePage_BinariesLocation;
	public static String FMUPreferencePage_FMUPreferencesPage;
	public static String FMUPreferencePage_IncludeTheFollowingLibrariesInExportedFMU;
	public static String FMUPreferencePage_InsideTheSelectedPathTheFilesSearchedFor;
	public static String FordiacCreateFMUWizard_LABEL_Window_Title;
	public static String FordiacCreateFMUWizard_PageDESCRIPTION;
	public static String FordiacCreateFMUWizard_PageName;
	public static String FordiacCreateFMUWizard_PageTITLE;
	public static String LogListener_MalformedError;
	public static String LogListener_ReturnedError;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
