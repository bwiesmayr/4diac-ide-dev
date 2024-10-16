/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.IContainerEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.gef.tools.ResizeTracker;

public class ContainerResizePolicy extends ModifiedResizeablePolicy {

	@Override
	protected ResizeTracker getResizeTracker(final int direction) {
		return new ContainerResizeTracker(getHost(), direction);
	}

	@Override
	public IContainerEditPart getHost() {
		return (IContainerEditPart) super.getHost();
	}

	@Override
	protected IFigure createSelectionFeedbackFigure() {
		final RoundedRectangle figure = (RoundedRectangle) super.createSelectionFeedbackFigure();
		figure.setFill(false);
		figure.setOutline(true);
		figure.setLineWidth(5 * ModifiedMoveHandle.SELECTION_BORDER_WIDTH);
		figure.setLayoutManager(new ToolbarLayout());
		figure.add(createCommentAreaFBFigure(figure));
		return figure;
	}

	private IFigure createCommentAreaFBFigure(final RoundedRectangle figure) {
		final RectangleFigure commentArea = new RectangleFigure() {
			@Override
			public void setBounds(final Rectangle rect) {
				// the layouter is putting us on the same position as the outer figure which is to high, left, and to
				// wide correct this here
				rect.translate(figure.getLineWidth(), figure.getLineWidth());
				rect.width -= 2 * figure.getLineWidth();
				super.setBounds(rect);
			}
		};
		commentArea.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		commentArea.setOutline(false);
		commentArea.setBounds(getCommentAreaBounds(figure));
		commentArea.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		commentArea.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
		return commentArea;
	}

	private Rectangle getCommentAreaBounds(final RoundedRectangle figure) {
		final Rectangle bounds = getHost().getFigure().getBounds().getCopy();
		final Rectangle contentBounds = getHost().getContentEP().getFigure().getBounds();
		bounds.height = contentBounds.y - bounds.y - figure.getLineWidth();
		return bounds;
	}

}