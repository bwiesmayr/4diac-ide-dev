/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ExportPlugin extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.export"; //$NON-NLS-1$

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		new LanguageSupportFactoryRegistryReader().readRegistry();
	}
}
