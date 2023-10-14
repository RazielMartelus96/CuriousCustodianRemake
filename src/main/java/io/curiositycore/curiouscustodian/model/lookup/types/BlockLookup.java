package io.curiositycore.curiouscustodian.model.lookup.types;
import io.curiositycore.curiouscustodian.model.lookup.AbstractLookup;
import org.bukkit.Material;

public class BlockLookup extends AbstractLookup {
    protected Material blockType;

    public static class AbstractLookupBuilder extends AbstractLookup.AbstractLookupBuilder<BlockLookup> {
        private Material blockType;

        @Override
        public BlockLookup build() {
            BlockLookup blockLookup = super.build();
            blockLookup.blockType = this.blockType;
            return blockLookup;
        }

        @Override
        protected BlockLookup createLookup() {
            return new BlockLookup();
        }
        public AbstractLookupBuilder setBlockType(Material blockType){
            this.blockType = blockType;
            return this;
        }
    }

}
