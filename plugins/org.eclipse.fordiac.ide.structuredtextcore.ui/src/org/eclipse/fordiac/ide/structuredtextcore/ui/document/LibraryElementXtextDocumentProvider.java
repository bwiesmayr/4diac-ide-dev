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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;

public abstract class LibraryElementXtextDocumentProvider extends XtextDocumentProvider {
	@Override
	protected boolean setDocumentContent(final IDocument document, final IEditorInput editorInput,
			final String encoding) throws CoreException {
		var result = false;
		if (editorInput instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry != null) {
				setDocumentContent(document, typeEntry.getTypeEditable());
				result = true;
			} else {
				result = super.setDocumentContent(document, editorInput, encoding);
			}
		}
		if (result) {
			setDocumentResource((XtextDocument) document, editorInput, encoding);
		}
		return result;
	}

	@Override
	protected void doSaveDocument(final IProgressMonitor monitor, final Object element, final IDocument document,
			final boolean overwrite) throws CoreException {
		if (element instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry != null) {
				doSaveDocument(monitor, fileEditorInput, typeEntry.getTypeEditable(), (XtextDocument) document);
				final FileInfo info = (FileInfo) getElementInfo(element);
				if (info != null) {
					final var model = info.fModel;
					if (model instanceof final ResourceMarkerAnnotationModel resourceMarkerAnnotationModel) {
						resourceMarkerAnnotationModel.updateMarkers(info.fDocument);
					}
					info.fModificationStamp = computeModificationStamp(fileEditorInput.getFile());
				}
				return;
			}
		}
		super.doSaveDocument(monitor, element, document, overwrite);
	}

	@Override
	protected void handleElementContentChanged(final IFileEditorInput fileEditorInput) {
		final FileInfo info = (FileInfo) getElementInfo(fileEditorInput);
		if (info == null) {
			return;
		}
		final XtextDocument document = (XtextDocument) info.fDocument;
		super.handleElementContentChanged(fileEditorInput);
		if (document == info.fDocument) { // still unchanged? -> update FB reference and reparse
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry != null) {
				final LibraryElement libraryElement = typeEntry.getTypeEditable();
				removeUnchangedElementListeners(fileEditorInput, info);

				document.internalModify(resource -> {
					if (resource instanceof final LibraryElementXtextResource libResource) {
						libResource.setLibraryElement(libraryElement);
					}
					return null;
				});

				addUnchangedElementListeners(fileEditorInput, info);
			}
		}
	}

	public abstract void doSaveDocument(IProgressMonitor monitor, IFileEditorInput fileEditorInput,
			LibraryElement element, XtextDocument document) throws CoreException;

	public abstract void setDocumentContent(IDocument document, LibraryElement element) throws CoreException;
}