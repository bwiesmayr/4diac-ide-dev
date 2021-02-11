/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

/**
 * The Class MoveBendpointCommand.
 */
public class MoveBendpointCommand extends Command {

	/** The e c transition. */
	private final ECTransition eCTransition;

	private final Point point;
	private Point oldPoint;


	/**
	 * Instantiates a new move bendpoint command.
	 *
	 * @param transition the transition
	 * @param point      the point
	 */
	public MoveBendpointCommand(final ECTransition transition, final Point point) {
		super();
		this.eCTransition = transition;
		this.point = point;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldPoint = eCTransition.getPosition().asPoint();
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		eCTransition.updatePosition(oldPoint);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		eCTransition.updatePosition(point);
	}

}
