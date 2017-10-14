/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Annotation;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>FB</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class FBImpl extends FBNetworkElementImpl implements FB {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected FBImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.FB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBType getFBType() {
		return org.eclipse.fordiac.ide.model.Annotations.GEN.getFBType(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResourceTypeFB() {
		return org.eclipse.fordiac.ide.model.Annotations.GEN.isResourceTypeFB(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResourceFB() {
		return org.eclipse.fordiac.ide.model.Annotations.GEN.isResourceFB(this);
	}

	@Override
	public void setName(final String newName) {
		String oldName = name;
		name = newName;

		getAnnotations().clear();
		
		NameRepository.checkNameIdentifier(this);
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONFIGURABLE_OBJECT__NAME, oldName, name));
	}

} // FBImpl
