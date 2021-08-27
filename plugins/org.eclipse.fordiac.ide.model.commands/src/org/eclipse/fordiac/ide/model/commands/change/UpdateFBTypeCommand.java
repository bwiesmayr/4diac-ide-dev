/*******************************************************************************
 * Copyright (c) 2012 - 2017 AIT, fortiss GmbH, Profactor GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked update fb type to accept also supapps
 *   Alois Zoitl - fixed issues in maintaining FB parameters
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdateFBTypeCommand extends AbstractUpdateFBNElementCommand {

	/** If not null this entry should be used for the type of the updated FB */
	private PaletteEntry entry;
	private ErrorMarkerBuilder errorMarkerFB;
	private final List<ErrorMarkerBuilder> errorPins;

	public UpdateFBTypeCommand(final FBNetworkElement fbnElement, final PaletteEntry entry) {
		super(fbnElement);
		if ((entry instanceof FBTypePaletteEntry) || (entry instanceof SubApplicationTypePaletteEntry)) {
			this.entry = entry;
		} else {
			this.entry = fbnElement.getPaletteEntry();
		}
		errorPins = new ArrayList<>();
	}

	@Override
	public boolean canExecute() {
		if ((null == entry) || (null == oldElement) || (null == network)) {
			return false;
		}
		return FBNetworkHelper.isTypeInsertionSave((FBType) entry.getType(), network);
	}

	protected void setEntry(final PaletteEntry entry) {
		this.entry = entry;
	}

	protected PaletteEntry getEntry() {
		return entry;
	}

	private void handleErrorMarker() {
		if ((!(oldElement instanceof ErrorMarkerFBNElement)) && newElement instanceof ErrorMarkerFBNElement) {
			final String errorMessage = MessageFormat.format("Type File: {0} could not be loaded for FB", //$NON-NLS-1$
					entry.getFile() != null ? entry.getFile().getFullPath() : "null type"); //$NON-NLS-1$
			errorMarkerFB = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement, 0);
			errorMarkerFB.setErrorMarkerRef((ErrorMarkerRef) newElement);
			((ErrorMarkerRef) newElement).setErrorMessage(errorMessage);
			FordiacMarkerHelper.createMarkerInFile(errorMarkerFB);
		}
	}

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement, final IInterfaceElement oldInterface,
			final String errorMessage) {
		return createErrorMarker(newElement, oldInterface, oldInterface.getName(), errorMessage);
	}

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement, final IInterfaceElement oldInterface,
			final String name, final String errorMessage) {
		final boolean markerExists = newElement.getInterface().getErrorMarker().stream()
				.anyMatch(e -> e.getName().equals(name));

		IInterfaceElement interfaceElement;
		interfaceElement = ConnectionHelper.createErrorMarkerInterface(oldInterface.getType(), name,
				oldInterface.isIsInput(), newElement.getInterface());

		if (!markerExists) {
			final ErrorMarkerBuilder createErrorMarker = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement,
					0);
			createErrorMarker.setErrorMarkerRef((ErrorMarkerRef) interfaceElement);
			FordiacMarkerHelper.createMarkerInFile(createErrorMarker);
			errorPins.add(createErrorMarker);
		}
		return interfaceElement;
	}

	private void copyFB() {
		newElement = createCopiedFBEntry(oldElement);

		newElement.setInterface(newElement.getType().getInterfaceList().copy());

		newElement.setName(oldElement.getName());
		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));

		createValues();

	}

	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		FBNetworkElement copy;
		if (entry instanceof SubApplicationTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createSubApp();
		} else if (entry instanceof AdapterTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createAdapterFB();
			((AdapterFB) copy).setAdapterDecl(((AdapterFB) srcElement).getAdapterDecl());
		} else if (entry.getType() instanceof CompositeFBType) {
			copy = LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else if (oldElement instanceof ErrorMarkerFBNElement && entry instanceof FBTypePaletteEntry) {
			copy = createErrorTypeFb();
		} else if (entry.getFile() == null || !entry.getFile().exists()) {
			copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		} else if (isMultiplexer()) {	// $NON-NLS-1$
			copy = createMultiplexer();
		} else {
			copy = LibraryElementFactory.eINSTANCE.createFB();
		}

		copy.setPaletteEntry(entry);
		return copy;
	}

	private FBNetworkElement createMultiplexer() {
		FBNetworkElement copy;
		StructManipulator sManipulator;
		if ("STRUCT_MUX".equals(entry.getType().getName())) { //$NON-NLS-1$
			sManipulator = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else {
			sManipulator = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		sManipulator.setStructType(new DataTypeLibrary().getStructuredTypes().get(0));
		copy = sManipulator;
		return copy;
	}

	private boolean isMultiplexer() {
		return entry.getType() instanceof ServiceInterfaceFBType && entry.getType().getName().startsWith("STRUCT");
	}

	public FBNetworkElement createErrorTypeFb() {
		FBNetworkElement copy;
		final TypeLibrary typeLibrary = oldElement.getPaletteEntry().getTypeLibrary();
		FBTypePaletteEntry fbTypeEntry = typeLibrary.getErrorTypeLib().getFBTypeEntry(oldElement.getType().getName());
		if (fbTypeEntry == null) {
			fbTypeEntry = typeLibrary.getBlockTypeLib().getFBTypeEntry(oldElement.getType().getName());
		}

		if (fbTypeEntry != null && fbTypeEntry.getFile() != null) {
			copy = LibraryElementFactory.eINSTANCE.createFB();
			copy.setPaletteEntry(fbTypeEntry);
			return copy;
		}
		copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		return copy;
	}

	@Override
	protected void handleApplicationConnections() {
		for (final Connection connection : getAllConnections(oldElement)) {
			IInterfaceElement onSelectedFB;
			IInterfaceElement onOtherFB;
			IInterfaceElement updatedSelected;
			IInterfaceElement updatedOther;

			if (connection.getSourceElement() == oldElement) {
				// OUPUT
				onSelectedFB = connection.getSource();
				onOtherFB = connection.getDestination();
			} else {
				// INPUT
				onSelectedFB = connection.getDestination();
				onOtherFB = connection.getSource();
			}

			updatedSelected = updateSelectedInterface(onSelectedFB, newElement);
			updatedOther = onOtherFB.getFBNetworkElement().getInterfaceElement(onOtherFB.getName());

			handleReconnect(connection, onSelectedFB, onOtherFB, updatedSelected, updatedOther);
		}
	}

	private void handleReconnect(final Connection connection, final IInterfaceElement selected, final IInterfaceElement other,
			IInterfaceElement updatedSelected, IInterfaceElement updatedOther) {
		if (!updatedSelected.getType().isCompatibleWith(updatedOther.getType())) {
			// connection not compatible
			if (other instanceof ErrorMarkerInterface && other.getType().isCompatibleWith(updatedSelected.getType())) {
				updatedOther = other;
			} else {
				updatedSelected = createWrongTypeMarker(selected, updatedSelected, newElement);
			}
		}

		if (updatedOther instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedOther).setRepairedEndpoint(updatedSelected);
		}
		if (updatedSelected instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedSelected).setRepairedEndpoint(updatedOther);
		}

		if (updatedOther instanceof ErrorMarkerInterface) {
			reconnectConnections(connection, updatedSelected, updatedOther, connection.getFBNetwork());
			updatedOther = null;
		}

		reconnect(connection, updatedSelected, updatedOther);
	}

	private void reconnect(final Connection connection, final IInterfaceElement updatedSelected,
			final IInterfaceElement updatedOther) {
		if (connection.getSourceElement() == oldElement) {
			doReconnect(connection, updatedSelected, updatedOther);
		} else {
			doReconnect(connection, updatedOther, updatedSelected);
		}
	}

	private IInterfaceElement updateSelectedInterface(final IInterfaceElement oldInterface, final FBNetworkElement newElement) {
		IInterfaceElement updatedSelected = newElement.getInterfaceElement(oldInterface.getName());
		if (updatedSelected == null) {
			updatedSelected = createErrorMarker(newElement, oldInterface,
					MessageFormat.format(Messages.UpdateFBTypeCommand_Pin_not_found, oldInterface.getName()));
		}
		return updatedSelected;
	}

	protected ErrorMarkerInterface createWrongTypeMarker(final IInterfaceElement oldInterface, final IInterfaceElement newInterface,
			final FBNetworkElement element) {
		final String errorMessage = MessageFormat.format(Messages.UpdateFBTypeCommand_type_mismatch,
				oldInterface.getTypeName(), newInterface.getTypeName());
		final ErrorMarkerInterface createErrorMarker = (ErrorMarkerInterface) createErrorMarker(element, oldInterface,
				oldInterface.getName(), errorMessage);
		createErrorMarker.setErrorMessage(Messages.UpdateFBTypeCommand_wrong_type);
		return createErrorMarker;
	}

	@Override
	protected IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement newElement,
			final FBNetworkElement oldElement, final IInterfaceElement oldInterface) {
		if (oldInterface != null && oldInterface.getFBNetworkElement() == oldElement) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			final IInterfaceElement interfaceElement = updateSelectedInterface(oldInterface, newElement);

			if (!oldInterface.getType().isCompatibleWith(interfaceElement.getType())) {
				return createWrongTypeMarker(oldInterface, interfaceElement, newElement);
			}

			return interfaceElement;
		}
		return oldInterface;
	}

	@Override
	protected AbstractConnectionCreateCommand createConnCreateCMD(IInterfaceElement interfaceElement,
			final FBNetwork fbn) {
		if (interfaceElement instanceof ErrorMarkerInterface) {
			interfaceElement = ((ErrorMarkerInterface) interfaceElement).getRepairedEndpoint();
		}
		if (interfaceElement instanceof Event) {
			return new EventConnectionCreateCommand(fbn);
		}
		if (interfaceElement instanceof AdapterDeclaration) {
			return new AdapterConnectionCreateCommand(fbn);
		}
		if (interfaceElement instanceof VarDeclaration) {
			return new DataConnectionCreateCommand(fbn);
		}
		if (interfaceElement instanceof ErrorMarkerInterface) {
			return new DataConnectionCreateCommand(fbn);
		}
		return null;

	}

	@Override
	protected void reconnectConnections(final Connection oldConn, final IInterfaceElement source, final IInterfaceElement dest,
			final FBNetwork fbn) {
		// if source or dest is null it means that an interface element is not available
		// any more
		final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, fbn);
		if (null != dccc) {
			dccc.setSource(source);
			dccc.setDestination(dest);
			dccc.setArrangementConstraints(oldConn.getRoutingData());
			dccc.execute();
			connCreateCmds.add(dccc);
		}
	}

	@Override
	protected void handleExecute() {
		// Create new FB
		copyFB();

		network.getNetworkElements().add(newElement);
		// Find connections which should be reconnected

		handleErrorMarker();

		handleApplicationConnections();
		network.getNetworkElements().remove(oldElement);

		// Change name
		newElement.setName(oldElement.getName());
	}

	@Override
	protected void handleRedo() {
		network.getNetworkElements().remove(oldElement);
		handleErrorMarker();
		network.getNetworkElements().add(newElement);
		errorPins.forEach(FordiacMarkerHelper::createMarkerInFile);
	}

	@Override
	protected void handleUndo() {
		errorPins.stream().map(ErrorMarkerBuilder::getErrorMarkerRef).forEach(FordiacMarkerHelper::deleteErrorMarker);
		connCreateCmds.undo();
		if (errorMarkerFB != null && newElement instanceof ErrorMarkerRef) {
			FordiacMarkerHelper.deleteErrorMarker((ErrorMarkerRef) newElement);
		}
	}
}
