/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class AdapterConnectionCreateCommand extends AbstractConnectionCreateCommand {

	public AdapterConnectionCreateCommand(FBNetwork parent) {
		super(parent);
	}

	@Override
	protected Connection createConnectionElement(){
		return LibraryElementFactory.eINSTANCE.createAdapterConnection();		
	}
	
	@Override
	public boolean canExecute() {
		if (getSource() == null || getDestination() == null) {
			return false;
		}
		if (!(getSource() instanceof AdapterDeclaration)) {
			return false;
		}
		if (!(getDestination() instanceof AdapterDeclaration)) {
			return false;
		}

		return LinkConstraints.canCreateAdapterConnection((AdapterDeclaration) getSource(), (AdapterDeclaration) getDestination());
	}
	
	@Override
	protected AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork) {
		return new AdapterConnectionCreateCommand(fbNetwork);
	}

}
