/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Case Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl#getSelector <em>Selector</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl#getCases <em>Cases</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl#getElse <em>Else</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STCaseStatementImpl extends STStatementImpl implements STCaseStatement {
	/**
	 * The cached value of the '{@link #getSelector() <em>Selector</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelector()
	 * @generated
	 * @ordered
	 */
	protected STExpression selector;

	/**
	 * The cached value of the '{@link #getCases() <em>Cases</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCases()
	 * @generated
	 * @ordered
	 */
	protected EList<STCaseCases> cases;

	/**
	 * The cached value of the '{@link #getElse() <em>Else</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElse()
	 * @generated
	 * @ordered
	 */
	protected STElsePart else_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STCaseStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_CASE_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExpression getSelector() {
		return selector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSelector(STExpression newSelector, NotificationChain msgs) {
		STExpression oldSelector = selector;
		selector = newSelector;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_CASE_STATEMENT__SELECTOR, oldSelector, newSelector);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSelector(STExpression newSelector) {
		if (newSelector != selector) {
			NotificationChain msgs = null;
			if (selector != null)
				msgs = ((InternalEObject)selector).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_CASE_STATEMENT__SELECTOR, null, msgs);
			if (newSelector != null)
				msgs = ((InternalEObject)newSelector).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_CASE_STATEMENT__SELECTOR, null, msgs);
			msgs = basicSetSelector(newSelector, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_CASE_STATEMENT__SELECTOR, newSelector, newSelector));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STCaseCases> getCases() {
		if (cases == null) {
			cases = new EObjectContainmentWithInverseEList<STCaseCases>(STCaseCases.class, this, STCorePackage.ST_CASE_STATEMENT__CASES, STCorePackage.ST_CASE_CASES__STATEMENT);
		}
		return cases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STElsePart getElse() {
		return else_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElse(STElsePart newElse, NotificationChain msgs) {
		STElsePart oldElse = else_;
		else_ = newElse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_CASE_STATEMENT__ELSE, oldElse, newElse);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElse(STElsePart newElse) {
		if (newElse != else_) {
			NotificationChain msgs = null;
			if (else_ != null)
				msgs = ((InternalEObject)else_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_CASE_STATEMENT__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_CASE_STATEMENT__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_CASE_STATEMENT__ELSE, newElse, newElse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCases()).basicAdd(otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__SELECTOR:
				return basicSetSelector(null, msgs);
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				return ((InternalEList<?>)getCases()).basicRemove(otherEnd, msgs);
			case STCorePackage.ST_CASE_STATEMENT__ELSE:
				return basicSetElse(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__SELECTOR:
				return getSelector();
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				return getCases();
			case STCorePackage.ST_CASE_STATEMENT__ELSE:
				return getElse();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__SELECTOR:
				setSelector((STExpression)newValue);
				return;
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				getCases().clear();
				getCases().addAll((Collection<? extends STCaseCases>)newValue);
				return;
			case STCorePackage.ST_CASE_STATEMENT__ELSE:
				setElse((STElsePart)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__SELECTOR:
				setSelector((STExpression)null);
				return;
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				getCases().clear();
				return;
			case STCorePackage.ST_CASE_STATEMENT__ELSE:
				setElse((STElsePart)null);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case STCorePackage.ST_CASE_STATEMENT__SELECTOR:
				return selector != null;
			case STCorePackage.ST_CASE_STATEMENT__CASES:
				return cases != null && !cases.isEmpty();
			case STCorePackage.ST_CASE_STATEMENT__ELSE:
				return else_ != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //STCaseStatementImpl
