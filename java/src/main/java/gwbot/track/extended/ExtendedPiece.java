/**
 * Copyright (c) 2011-2014 blackshark production by Nico Smeenk
 */
package gwbot.track.extended;

import gwbot.track.Piece;

/**
 * TODO Description
 *
 * @author Nico Smeenk
 *
 */
public class ExtendedPiece extends Piece {

	private ExtendedPiece _next;
	private ExtendedPiece _prev;

	public ExtendedPiece(Piece pOld) {
		super(pOld.getLength(), pOld.isSwitch(), pOld.getRadius(), pOld.getAngle());
	}

	/**
	 * @param pNext the next to set
	 */
	public void setNext(ExtendedPiece pNext) {
		_next = pNext;
	}

	/**
	 * @param pPrev the prev to set
	 */
	public void setPrev(ExtendedPiece pPrev) {
		_prev = pPrev;
	}

	public ExtendedPiece next() {
		return _next;
	}

	public ExtendedPiece prev() {
		return _prev;
	}

}
