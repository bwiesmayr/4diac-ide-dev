/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceEventSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class EditInterfaceEventSection extends AbstractEditInterfaceEventSection {
	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final DataType last = getLastUsedEventType(getType().getInterface(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(last,
				getCreationName(interfaceElement), getType().getInterface(), isInput, pos);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(interfaceElement,
				isInput, getType().getInterface(), index);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return EditInterfaceAdapterSection.getSubAppFromInput(input);
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

}
