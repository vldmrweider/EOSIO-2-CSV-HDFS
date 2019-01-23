import org.bson.types.ObjectId;

public class Block {

    private ObjectId id;
    private int block_num;
    private BlockDetail block;


    public Block() {

    }

    public ObjectId getId() {
        return id;
    }

    public void setBlock_num(int block_num) {
        this.block_num = block_num;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getBlock_num() {
        return block_num;
    }

    public BlockDetail getBlock() {
        return block;
    }

    public void setBlock(BlockDetail block) {
        this.block = block;
    }
}
