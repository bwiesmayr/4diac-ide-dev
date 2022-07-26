/**
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
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.fbtypextext.FBTypeXtextResource
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider

class STAlgorithmImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {
	override internalGetImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
		val result = super.internalGetImportedNamespaceResolvers(context, ignoreCase)
		if (context.eContainer === null) {
			val resource = context.eResource
			if (resource instanceof FBTypeXtextResource) {
				val fbType = resource.fbType
				if (fbType !== null && fbType.name !== null) {
					val fbTypeName = qualifiedNameConverter.toQualifiedName(fbType.name)
					result.add(doCreateImportNormalizer(fbTypeName, true, ignoreCase))
				}
			}
		}
		return result
	}
	
	override isRelativeImport() { false }
	
}
