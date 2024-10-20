/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public abstract class CreateInternalVariableCommandTestBase extends FBNetworkTestBase {

	protected static State initializeState() {
		final State state = new State();
		return FBCreateCommandTest.executeCommand(state);
	}

	private static void verifyInitialState(final State state, final State oldState, final TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, tester.get()); // skip further tests if FB
		// creation failed

	}

	protected static BaseFBType getBaseFBType(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType() instanceof BaseFBType);
		return (BaseFBType) state.getFbNetwork().getNetworkElements().get(0).getType();
	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", //$NON-NLS-1$
				CreateInternalVariableCommandTestBase::initializeState, //
				(StateVerifier<State>) CreateInternalVariableCommandTestBase::verifyInitialState, //
				executionDescriptions //
				));

		return commands;
	}

}
