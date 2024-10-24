/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.util.OperationalSemanticsAdapterFactory;

/** This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated */
public class OperationalSemanticsItemProviderAdapterFactory extends OperationalSemanticsAdapterFactory
		implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/** This keeps track of the root adapter factory that delegates to this adapter factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated */
	protected ComposedAdapterFactory parentAdapterFactory;

	/** This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/** This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected Collection<Object> supportedTypes = new ArrayList<>();

	/** This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public OperationalSemanticsItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected EventOccurrenceItemProvider eventOccurrenceItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createEventOccurrenceAdapter() {
		if (eventOccurrenceItemProvider == null) {
			eventOccurrenceItemProvider = new EventOccurrenceItemProvider(this);
		}

		return eventOccurrenceItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected EventManagerItemProvider eventManagerItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createEventManagerAdapter() {
		if (eventManagerItemProvider == null) {
			eventManagerItemProvider = new EventManagerItemProvider(this);
		}

		return eventManagerItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected BasicFBTypeRuntimeItemProvider basicFBTypeRuntimeItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createBasicFBTypeRuntimeAdapter() {
		if (basicFBTypeRuntimeItemProvider == null) {
			basicFBTypeRuntimeItemProvider = new BasicFBTypeRuntimeItemProvider(this);
		}

		return basicFBTypeRuntimeItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected SimpleFBTypeRuntimeItemProvider simpleFBTypeRuntimeItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createSimpleFBTypeRuntimeAdapter() {
		if (simpleFBTypeRuntimeItemProvider == null) {
			simpleFBTypeRuntimeItemProvider = new SimpleFBTypeRuntimeItemProvider(this);
		}

		return simpleFBTypeRuntimeItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected FBNetworkRuntimeItemProvider fbNetworkRuntimeItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createFBNetworkRuntimeAdapter() {
		if (fbNetworkRuntimeItemProvider == null) {
			fbNetworkRuntimeItemProvider = new FBNetworkRuntimeItemProvider(this);
		}

		return fbNetworkRuntimeItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected FBTransactionItemProvider fbTransactionItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createFBTransactionAdapter() {
		if (fbTransactionItemProvider == null) {
			fbTransactionItemProvider = new FBTransactionItemProvider(this);
		}

		return fbTransactionItemProvider;
	}

	/** This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated */
	protected ConnectionToValueMapItemProvider connectionToValueMapItemProvider;

	/** This creates an adapter for a {@link java.util.Map.Entry}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createConnectionToValueMapAdapter() {
		if (connectionToValueMapItemProvider == null) {
			connectionToValueMapItemProvider = new ConnectionToValueMapItemProvider(this);
		}

		return connectionToValueMapItemProvider;
	}

	/** This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated */
	protected RuntimeMapItemProvider runtimeMapItemProvider;

	/** This creates an adapter for a {@link java.util.Map.Entry}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createRuntimeMapAdapter() {
		if (runtimeMapItemProvider == null) {
			runtimeMapItemProvider = new RuntimeMapItemProvider(this);
		}

		return runtimeMapItemProvider;
	}

	/** This keeps track of the one adapter used for all {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Trace}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected TraceItemProvider traceItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Trace}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createTraceAdapter() {
		if (traceItemProvider == null) {
			traceItemProvider = new TraceItemProvider(this);
		}

		return traceItemProvider;
	}

	/** This keeps track of the one adapter used for all {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected EccTraceItemProvider eccTraceItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createEccTraceAdapter() {
		if (eccTraceItemProvider == null) {
			eccTraceItemProvider = new EccTraceItemProvider(this);
		}

		return eccTraceItemProvider;
	}

	/** This keeps track of the one adapter used for all
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	protected TransitionTraceItemProvider transitionTraceItemProvider;

	/** This creates an adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter createTransitionTraceAdapter() {
		if (transitionTraceItemProvider == null) {
			transitionTraceItemProvider = new TransitionTraceItemProvider(this);
		}

		return transitionTraceItemProvider;
	}

	/** This returns the root adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/** This sets the composed adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/** This implementation substitutes the factory itself as the key for the adapter. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/** This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/** This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/** This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/** This disposes all of the item providers created by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void dispose() {
		if (eventOccurrenceItemProvider != null)
			eventOccurrenceItemProvider.dispose();
		if (eventManagerItemProvider != null)
			eventManagerItemProvider.dispose();
		if (basicFBTypeRuntimeItemProvider != null)
			basicFBTypeRuntimeItemProvider.dispose();
		if (simpleFBTypeRuntimeItemProvider != null)
			simpleFBTypeRuntimeItemProvider.dispose();
		if (fbNetworkRuntimeItemProvider != null)
			fbNetworkRuntimeItemProvider.dispose();
		if (fbTransactionItemProvider != null)
			fbTransactionItemProvider.dispose();
		if (connectionToValueMapItemProvider != null)
			connectionToValueMapItemProvider.dispose();
		if (runtimeMapItemProvider != null)
			runtimeMapItemProvider.dispose();
		if (traceItemProvider != null)
			traceItemProvider.dispose();
		if (eccTraceItemProvider != null)
			eccTraceItemProvider.dispose();
		if (transitionTraceItemProvider != null)
			transitionTraceItemProvider.dispose();
	}

}
