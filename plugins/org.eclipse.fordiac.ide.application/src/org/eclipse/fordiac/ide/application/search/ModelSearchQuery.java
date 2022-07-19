/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin, Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search2.internal.ui.SearchView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class ModelSearchQuery implements ISearchQuery {

	private final ModelQuerySpec modelQuerySpec;
	private ModelSearchResult searchResult;

	public ModelSearchQuery(final ModelQuerySpec modelQuerySpec) {
		this.modelQuerySpec = modelQuerySpec;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) throws OperationCanceledException {
		searchResult = getSearchResult();
		final List<AutomationSystem> searchRootSystems = new ArrayList<>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		if (modelQuerySpec.isCheckWorkspaceScope()) { // If it's workspace, search all
			for (final IProject proj : root.getProjects()) {
				if (proj.isOpen()) {
					searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(proj).values());
				}
			}
		} else {
			Display.getDefault().syncExec(() -> {
				final IEditorPart openEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				final IProject project = openEditor.getAdapter(FBNetwork.class).getAutomationSystem().getTypeLibrary()
						.getProject();
				searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(project).values());
			});
		}

		for (final AutomationSystem sys : searchRootSystems) {
			searchApplications(sys);
			searchResources(sys);
			searchTypeLibrary(sys);
		}

		Display.getDefault()
		.asyncExec(() -> ((SearchView) NewSearchUI.getSearchResultView()).showSearchResult(getSearchResult()));

		return Status.OK_STATUS;
	}

	public void searchApplications(final AutomationSystem sys) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app);
		}
	}

	public void searchApplication(final Application app) {
		if (matchEObject(app)) {
			searchResult.addResult(app);
		}
		searchFBNetwork(app.getFBNetwork());
	}

	private void searchFBNetwork(final FBNetwork network) {
		for (final FBNetworkElement fbnetworkElement : network.getNetworkElements()) {
			if (matchEObject(fbnetworkElement)) {
				searchResult.addResult(fbnetworkElement);
			}
			if (fbnetworkElement instanceof SubApp) {
				final SubApp subApp = (SubApp) fbnetworkElement;
				// for typed subapps and subapps without content (i.e., mapped subapp) don't search further
				if (!subApp.isTyped() && subApp.getSubAppNetwork() != null) {
					searchFBNetwork(subApp.getSubAppNetwork());
				}
			}
			if (modelQuerySpec.isCheckedPinName()) {
				final List<EObject> matchingPins = fbnetworkElement.getInterface().getAllInterfaceElements().stream()
						.filter(pin -> pin.getName() != null && compareStrings(pin.getName()))
						.collect(Collectors.toList());
				if (!matchingPins.isEmpty()) {
					searchResult.addResults(matchingPins);
				}
			}
		}
	}

	private void searchResources(final AutomationSystem sys) {
		for (final Device dev : sys.getSystemConfiguration().getDevices()) {
			if (matchEObject(dev)) {
				searchResult.addResult(dev);
			}
			for (final Resource res : dev.getResource()) {
				if (matchEObject(res)) {
					searchResult.addResult(res);
				}
			}
		}
	}

	private void searchTypeLibrary(final AutomationSystem sys) {
		final TypeLibrary lib = sys.getTypeLibrary();
		for (final TypeEntry entry : lib.getFbTypes().values()) {
			if (matchEObject(entry.getType())) {
				searchResult.addResult(entry.getType());
			}
		}
	}

	private boolean matchEObject(final EObject modelElement) {
		if (modelQuerySpec.isCheckedInstanceName() && modelElement instanceof INamedElement) {
			final String name = ((INamedElement) modelElement).getName();
			final boolean matchInstanceName = name != null && compareStrings(name);
			if (matchInstanceName) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedComment() && modelElement instanceof INamedElement) {
			final String comment = ((INamedElement) modelElement).getComment();
			final boolean matchComment = comment != null && compareStrings(comment);
			if (matchComment) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedType()) {
			if (modelElement instanceof INamedElement) {
				final INamedElement namElem = (INamedElement) modelElement;
				return compareStrings(namElem.getName());
			} else if (modelElement instanceof ConfigurableObject) {
				final ConfigurableObject config = (ConfigurableObject) modelElement;
				return compareStrings(config.getClass().getSimpleName());
			}
		}
		return false;
	}

	private boolean compareStrings(final String toTest) {
		final ModelSearchPattern pattern = new ModelSearchPattern(toTest, modelQuerySpec);
		if (pattern.matchSearchString()) {
			return true;
		}
		if (modelQuerySpec.isCheckExactMatching()) {
			return toTest.equals(modelQuerySpec.getSearchString());
		}
		if (modelQuerySpec.isCheckCaseSensitive()) {
			return toTest.contains(modelQuerySpec.getSearchString());
		}
		return toTest.toLowerCase().contains(modelQuerySpec.getSearchString().toLowerCase());
	}

	@Override
	public String getLabel() {
		return modelQuerySpec.getSearchString();
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	// Has to return true so that NewSearchUI.runQueryInBackground(searchJob); in MSP can actually run
	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ModelSearchResult getSearchResult() {
		if (searchResult == null) {
			searchResult = new ModelSearchResult(this);
		}
		return searchResult;
	}

}
