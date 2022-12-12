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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

public class EventListProvider extends ListDataProvider<Event> implements FordiacInterfaceListProvider {


	public EventListProvider(final AbstractSection section, final List<Event> list) {
		super(list, new EventColumnAcesssor(section));
	}

	public EventListProvider(final List<Event> list, final IColumnAccessor<Event> columnAccessor) {
		super(list, columnAccessor);
	}


	@Override
	public <T extends List<? extends IInterfaceElement>> void setInput(final T varDecl) {
		this.list = (List<Event>) varDecl;

	}

}