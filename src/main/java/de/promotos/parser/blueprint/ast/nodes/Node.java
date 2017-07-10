package de.promotos.parser.blueprint.ast.nodes;

import org.parboiled.common.StringUtils;
import org.parboiled.trees.MutableTreeNodeImpl;
import org.parboiled.trees.TreeUtils;

public class Node extends MutableTreeNodeImpl<Node> {
		
	/* ------------------------------------------------------------- *
	 * Members
	 * ------------------------------------------------------------- */
    protected String text;
    private int offsetEnd = -1;    
    private int offsetStart = -1;
        
    
    /* ------------------------------------------------------------- *
     * Constructors
     * ------------------------------------------------------------- */
    public Node() {}
    
    public Node(String text) {
    	setText(text);
    }

    
    /* ------------------------------------------------------------- *
     * Public methods
     * ------------------------------------------------------------- */
    public boolean addChild(Node child) {
        if (child != null) {
            TreeUtils.addChild(this, child);
        }
        return true;
    }
    
	public int getOffsetEnd() {
		return offsetEnd;
	}

	public int getOffsetLength() {
		return offsetEnd - offsetStart;
	}

	public int getOffsetStart() {
		return offsetStart;
	}

    public String getText() {
        return text;
    }
    
    public boolean hasText() {
    	return text != null && text.length() > 0;
    }
    
	public boolean hasOffset() {
		return offsetStart != -1 && offsetEnd != -1;
	}
    
    public Node lastChild() {
        return getChildren().get(getChildren().size() - 1);
    }

	public boolean setOffset(int start, int end) {
		offsetStart = start;
		offsetEnd = end;
		return true;
	}
	
    public void setText(String text) {
        this.text = text;
    }
	
    
	/* ------------------------------------------------------------- *
     * Overrides java.lang.Object
     * ------------------------------------------------------------- */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        final String text = getText();
        if (text != null) {
        	sb.append(" '").append(StringUtils.escape(text)).append('\'');
        }
        return sb.toString();
    }
	
}
