package org.itstack.demo.jvm.instructions.base;

public class BytecodeReader {

    private byte[] codes;
    private int pc;

    public void reset(byte[] codes, int pc) {
        this.codes = codes;
        this.pc = pc;
    }

    public int pc() {
        return this.pc;
    }

    //[go]int8 = [java]byte
    public byte readByte() {
        byte code = this.codes[this.pc];
        this.pc++;
        return code;
    }

    //[go]int16 = [java]short
    public short readShort() {
        byte byte1 = readByte();
        byte byte2 = readByte();
        int ub1 = byte1 & 0xff;
        int ub2 = byte2 & 0xff;
        return (short) ((ub1 << 8) | ub2);
    }

    public int readInt() {
        int byte1 = this.readByte();
        int byte2 = this.readByte();
        int byte3 = this.readByte();
        int byte4 = this.readByte();
        return (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    }

    //used by lookupswitch and tableswitch
    public int[] readInts(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = this.readInt();
        }
        return ints;
    }

    //used by lookupswitch and tableswitcch
    public void skipPadding() {
        while (this.pc % 4 != 0) {
            this.readByte();
        }
    }

}
