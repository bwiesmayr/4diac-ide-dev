/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui;

import org.eclipse.fordiac.ide.globalconstantseditor.ui.resource.GlobalConstantsResourceSetInitializer;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;

import com.google.inject.Binder;
import com.google.inject.Module;

public class GlobalConstantsSharedModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(IResourceSetInitializer.class).to(GlobalConstantsResourceSetInitializer.class);
	}
}
