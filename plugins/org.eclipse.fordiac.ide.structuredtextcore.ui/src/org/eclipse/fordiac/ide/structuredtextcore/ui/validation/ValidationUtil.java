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
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

public final class ValidationUtil {

	public static boolean shouldProcess(final Issue issue, final boolean ignoreWarnings) {
		return issue.getSeverity() == Severity.ERROR || !ignoreWarnings;
	}

	public static boolean isIgnoreWarnings(final IFile file) {
		return Boolean.parseBoolean(findSourceFolder(file).map(sourceFolder -> BuildpathAttributes
				.getAttributeValue(sourceFolder.getAttributes(), BuildpathAttributes.IGNORE_WARNINGS)).orElse(null));
	}

	public static Optional<SourceFolder> findSourceFolder(final IFile file) {
		return getBuildpath(file).flatMap(buildpath -> BuildpathUtil.findSourceFolder(buildpath, file));
	}

	public static Optional<Buildpath> getBuildpath(final IFile file) {
		return Optional.ofNullable(file).map(IFile::getProject).map(TypeLibraryManager.INSTANCE::getTypeLibrary)
				.map(TypeLibrary::getBuildpath);
	}

	public static Issue createModelIssue(final Severity severity, final String message, final EObject target) {
		final IssueImpl issue = new IssueImpl();
		issue.setMessage(message);
		issue.setSeverity(severity);
		issue.setCode(LibraryElementValidator.DIAGNOSTIC_SOURCE);
		issue.setType(CheckType.FAST);
		if (target != null) {
			issue.setUriToProblem(EcoreUtil.getURI(target));
			issue.setData(new String[] { FordiacMarkerHelper.getLocation(target), // [0] LOCATION
					EcoreUtil.getURI(target).toString(), // [1] TARGET_URI
					EcoreUtil.getURI(target.eClass()).toString() // [2] TARGET_TYPE
			});
		}
		return issue;
	}

	public static List<Issue> convertToModelIssues(final List<Issue> issues, final EObject target) {
		return issues.stream().map(issue -> convertToModelIssue(issue, target)).toList();
	}

	public static Issue convertToModelIssue(final Issue issue, final EObject target) {
		return createModelIssue(issue.getSeverity(), issue.getMessage(), target);
	}

	protected static Map<String, Object> getModelMarkerAttributes(final Issue issue) {
		final URI canonicalURI = getCanonicalURI(issue.getUriToProblem());
		if (canonicalURI != null) {
			if (isModelValidationIssue(issue)) {
				final String[] data = issue.getData();
				if (data != null) {
					if (data.length >= 4 && data[3] != null) {
						return Map.of(IMarker.LOCATION, data[0], FordiacErrorMarker.TARGET_URI, canonicalURI.toString(),
								FordiacErrorMarker.TARGET_TYPE, data[2], FordiacErrorMarker.TARGET_FEATURE, data[3]);
					}
					if (data.length >= 3) {
						return Map.of(IMarker.LOCATION, data[0], FordiacErrorMarker.TARGET_URI, canonicalURI.toString(),
								FordiacErrorMarker.TARGET_TYPE, data[2]);
					}
				}
			}
			return Map.of(FordiacErrorMarker.TARGET_URI, canonicalURI.toString());
		}
		return Collections.emptyMap();
	}

	public static URI getCanonicalURI(final URI uri) {
		if (uri != null && uri.hasFragment() && uri.fragment().startsWith("/1")) { //$NON-NLS-1$
			return uri.trimFragment().appendFragment("/" + uri.fragment().substring(2)); //$NON-NLS-1$
		}
		return uri;
	}

	protected static int getMarkerSeverity(final Issue issue) {
		return switch (issue.getSeverity()) {
		case ERROR -> IMarker.SEVERITY_ERROR;
		case WARNING -> IMarker.SEVERITY_WARNING;
		case INFO -> IMarker.SEVERITY_INFO;
		default -> throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		};
	}

	public static boolean isModelValidationIssue(final Issue issue) {
		return issue.getCode() != null && issue.getCode().startsWith(LibraryElementValidator.DIAGNOSTIC_SOURCE);
	}

	private ValidationUtil() {
		throw new UnsupportedOperationException();
	}
}
