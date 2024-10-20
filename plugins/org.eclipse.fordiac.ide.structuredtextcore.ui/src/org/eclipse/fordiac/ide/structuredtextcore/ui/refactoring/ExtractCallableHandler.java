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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ExtractCallableHandler extends AbstractHandler {

	@Inject
	private Provider<ExtractCallableRefactoring> refactoringProvider;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			final XtextEditor editor = EditorUtils.getActiveXtextEditor(event);
			if (editor != null) {
				final ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
				final ExtractCallableRefactoring refactoring = refactoringProvider.get();
				refactoring.initialize(editor, selection);
				final ExtractCallableWizard wizard = new ExtractCallableWizard(refactoring);
				final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
				openOperation.run(editor.getShell(), refactoring.getName());
			}
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during refactoring", e); //$NON-NLS-1$
		}
		return null;
	}
}
