/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
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

import org.eclipse.gef.commands.UnexecutableCommand;
import org.junit.jupiter.params.provider.Arguments;

public class UnexecutableCommandTest extends CommandTestBase<CommandTestBase.StateBase> {

	public static class State extends StateBase {

		@Override
		public Object getClone() {
			return new State(); // No need to copy, this command will not succeed in execution
		}

		public State() {
			// currently nothing to be done here
		}

	}

	protected static Collection<Arguments> describeCommand(final String description, final StateInitializer<?> initializer,
			final StateVerifier<?> initialVerifier, final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				CommandTestBase::disabledUndoCommand, CommandTestBase::disabledRedoCommand);
	}

	private static State executeCommand(final State state) {
		state.setCommand(UnexecutableCommand.INSTANCE);
		return disabledCommandExecution(state);
	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) CommandTestBase::verifyNothing, //
				executionDescriptions //
				));

		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Check if unexecutable command is not executable", //$NON-NLS-1$
						UnexecutableCommandTest::executeCommand, //
						CommandTestBase::verifyNothing //
						) //
				);

		return createCommands(executionDescriptions);
	}

}
