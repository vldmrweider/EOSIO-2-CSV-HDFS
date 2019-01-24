import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Block {

    @BsonId
    private ObjectId id;

    private int block_num;
    private BlockDetail block;


    public Block() {

    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setBlock_num(int block_num) {
        this.block_num = block_num;
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
