/**
 * Copyright (c) 2011-2014 blackshark production by Nico Smeenk
 */
package gwbot.track.extendet;

import gwbot.track.Piece;

/**
 * TODO Description
 * 
 * @author Nico Smeenk
 * 
 */
public class ExtendetPiece extends Piece {

	private ExtendetPiece _next;
	private ExtendetPiece _prev;

	/**
	 * 
	 * @param pLength
	 * @param pIsSwitch
	 * @param pRadius
	 * @param pAngle
	 * @param pNext
	 * @param pPrev
	 */
	public ExtendetPiece(Piece pOld) {
		super(pOld.getLength(), pOld.isSwitch(), pOld.getRadius(), pOld.getAngle());
	}

	/**
	 * @param pNext
	 *            the next to set
	 */
	public void setNext(ExtendetPiece pNext) {
		_next = pNext;
	}

	/**
	 * @param pPrev
	 *            the prev to set
	 */
	public void setPrev(ExtendetPiece pPrev) {
		_prev = pPrev;
	}

	public ExtendetPiece next() {
		return _next;
	}

	public ExtendetPiece prev() {
		return _prev;
	}

}
