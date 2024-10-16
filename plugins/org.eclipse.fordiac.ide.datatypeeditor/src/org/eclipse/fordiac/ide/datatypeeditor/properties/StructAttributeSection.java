/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.properties.AttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StructAttributeSection extends AttributeSection {

	private Label label;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		label.setBackground(GUIHelper.COLOR_WHITE);
		super.createControls(parent, tabbedPropertySheetPage);
	}

	@Override
	protected void setType(final Object input) {
		super.setType(input);
		refresh(); // refresh to update input of viewer
		if (getType() instanceof final INamedElement namedElement) {
			label.setText(namedElement.getName() + ":"); //$NON-NLS-1$
		}
	}

	@Override
	protected ConfigurableObject getInputType(final Object input) {
		if (input instanceof final VarDeclaration varDeclaration) {
			return varDeclaration;
		}
		if (input instanceof final EObject eObject
				&& EcoreUtil.getRootContainer(eObject) instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		return null;
	}
}
