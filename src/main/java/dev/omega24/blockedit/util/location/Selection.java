package dev.omega24.blockedit.util.location;

public class Selection {
    private Position pos1;
    private Position pos2;

    public Position pos1() {
        return this.pos1;
    }

    public void pos1(Position pos1) {
        this.pos1 = pos1;
    }

    public Position pos2() {
        return this.pos2;
    }

    public void pos2(Position pos2) {
        this.pos2 = pos2;
    }
}
