package org.itstack.demo.jvm.rtda.heap.methodarea;

import org.itstack.demo.jvm.rtda.Slot;

public class Slots {

    private Slot[] slots;

    public Slots(int slotCount) {
        if (slotCount > 0) {
            slots = new Slot[slotCount];
            for (int i = 0; i < slotCount; i++) {
                slots[i] = new Slot();
            }
        }
    }

    public void setInt(int idx, int val) {
        this.slots[idx].num = val;
    }

    public int getInt(int idx) {
        return this.slots[idx].num;
    }

    public void setFloat(int idx, float val) {
        this.slots[idx].num = (int) val;
    }

    public float getFloat(int idx) {
        return this.slots[idx].num;
    }

    public void setLong(int idx, long val) {
        this.slots[idx].num = (int) val;
        this.slots[idx + 1].num = (int) (val >> 32);
    }

    public long getLong(int idx) {
        int low = this.slots[idx].num;
        int high = this.slots[idx + 1].num;
        return (long) high << 32 | (long) low;
    }

    public void setDouble(int idx, double val) {
        this.setLong(idx, (long) val);
    }

    public Double getDouble(int idx) {
        return (double) this.getLong(idx);
    }

    public void setRef(int idx, Object ref) {
        this.slots[idx].ref = ref;
    }

    public Object getRef(int idx){
        return this.slots[idx].ref;
    }

}
