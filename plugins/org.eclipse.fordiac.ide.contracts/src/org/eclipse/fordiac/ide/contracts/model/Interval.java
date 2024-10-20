/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

public class Interval extends AbstractTime {
	private final int minTime;
	private final int maxTime;

	public Interval(final int minTime, final int maxTime) {
		this.minTime = minTime;
		this.maxTime = maxTime;
	}

	public boolean isValid() {
		return minTime <= maxTime;
	}

	public Interval calculateOverlap(final Interval other) {
		if (other == null) {
			return null;
		}
		final int min = Math.max(other.getMin(), this.getMin());
		final int max = Math.min(other.getMax(), this.getMax());
		return new Interval(min, max);
	}

	public Interval merge(final Interval other) {
		if (other == null) {
			return null;
		}
		if (hasNoOverlap(other)) {
			return new Interval(0, -1);
		}
		final int min = Math.min(other.getMin(), this.getMin());
		final int max = Math.max(other.maxTime, this.getMax());
		return new Interval(min, max);

	}

	private boolean hasNoOverlap(final Interval other) {
		return (other.getMax() < this.getMin()) && (other.getMin() > this.getMax());
	}

	private Interval add(final Interval other) {
		return new Interval(this.getMin() + other.getMin(), this.getMax() + other.getMax());
	}

	private Interval add(final Instant other) {
		return new Interval(this.getMin() + other.getMin(), this.getMax() + other.getMin());
	}

	@Override
	public Interval add(final AbstractTime other) {
		if (other instanceof final Instant otherInstant) {
			return this.add(otherInstant);
		}
		if (other instanceof final Interval otherInterval) {
			return this.add(otherInterval);
		}
		return null;
	}

	@Override
	public int getMin() {
		return minTime;
	}

	@Override
	public int getMax() {
		return maxTime;
	}

	@Override
	public AbstractTime getCopy() {
		return new Interval(minTime, maxTime);
	}
}
