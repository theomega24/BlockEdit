package dev.omega24.blockedit.api.operation;

public class OperationStats {
    private long startTime;
    private long endTime;
    private int blocksChanged;

    public long getTimeInMillis() {
        return endTime - startTime;
    }

    public int getBlocksChanged() {
        return blocksChanged;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void end() {
        this.endTime = System.currentTimeMillis();
    }

    public void setBlocksChanged(int blocksChanged) {
        this.blocksChanged = blocksChanged;
    }
}
