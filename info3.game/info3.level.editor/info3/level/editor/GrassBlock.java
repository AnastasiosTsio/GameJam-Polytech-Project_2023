package info3.level.editor;

import java.io.IOException;

public class GrassBlock extends Block {


    public GrassBlock() throws IOException {
        super("resources/blocks/1.png");
    }
    
    public Element copy() throws IOException {
        return new GrassBlock();
    }
    
    public String toString() {
        return "GrassBlock";
    }

}
