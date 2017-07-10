package de.promotos.parser.blueprint.parser;

import org.parboiled.Context;
import org.parboiled.ContextAware;

public class Match<StackType> implements ContextAware<StackType> {
	
	/* ------------------------------------------------------------- *
	 * Members
	 * ------------------------------------------------------------- */
	private String innerMatch;
	private int matchStart, matchEnd;
	private Context<StackType> context;
	
	/* ------------------------------------------------------------- *
	 * Constructors
	 * ------------------------------------------------------------- */
	public Match() {}
	
	
	/* ------------------------------------------------------------- *
	 * Public methods
	 * ------------------------------------------------------------- */
	public boolean append() {
		if (innerMatch.equals("")) {
			return reset();
		} else {
			innerMatch += context.getMatch();
			matchEnd = context.getMatchEndIndex();
			return true;
		}
	}
	
	public boolean clear() {
		innerMatch = "";
		matchStart = -1;
		matchEnd = -1;
		return true;
	}
	
	public int getEndOffset() {
		return matchEnd;
	}
	
	public String getMatch() {
		return innerMatch;
	}
	
	public int getStartOffset() {
		return matchStart;
	}
	
	public boolean reset() {
		innerMatch = context.getMatch();
		matchStart = context.getMatchStartIndex();
		matchEnd = context.getMatchEndIndex();
		return true;
	}

	public void setContext(Context<StackType> context) {
		this.context = context;
	}

}
