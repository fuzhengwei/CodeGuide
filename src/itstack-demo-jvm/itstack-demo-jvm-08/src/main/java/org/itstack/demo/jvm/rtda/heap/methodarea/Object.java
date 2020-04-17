package org.itstack.demo.jvm.rtda.heap.methodarea;

public class Object {

    Class clazz;
    java.lang.Object data;

    public Object(Class clazz) {
        this.clazz = clazz;
        this.data = new Slots(clazz.instanceSlotCount);
    }

    public Object(Class clazz, java.lang.Object data) {
        this.clazz = clazz;
        this.data = data;
    }

    public Class clazz() {
        return this.clazz;
    }

    public Slots fields() {
        return (Slots) this.data;
    }

    public boolean isInstanceOf(Class clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    public Object getRefVar(String name, String descriptor) {
        Field field = this.clazz.getField(name, descriptor, false);
        Slots slots = (Slots) this.data;
        return slots.getRef(field.slotId);
    }

    public void setRefVal(String name, String descriptor, Object ref) {
        Field field = this.clazz.getField(name, descriptor, false);
        Slots slots = (Slots) this.data;
        slots.setRef(field.slotId, ref);
    }

    public byte[] bytes() {
        return (byte[]) this.data;
    }

    public short[] shorts() {
        return (short[]) this.data;
    }

    public int[] ints() {
        return (int[]) this.data;
    }

    public long[] longs() {
        return (long[]) this.data;
    }

    public char[] chars() {
        return (char[]) this.data;
    }

    public float[] floats() {
        return (float[]) this.data;
    }

    public double[] doubles() {
        return (double[]) this.data;
    }

    public Object[] refs() {
        return (Object[]) this.data;
    }

    public int arrayLength() {

        if (this.data instanceof byte[]) {
            return ((byte[]) this.data).length;
        }

        if (this.data instanceof short[]) {
            return ((short[]) this.data).length;
        }

        if (this.data instanceof int[]) {
            return ((int[]) this.data).length;
        }

        if (this.data instanceof long[]) {
            return ((long[]) this.data).length;
        }

        if (this.data instanceof char[]) {
            return ((char[]) this.data).length;
        }

        if (this.data instanceof float[]) {
            return ((float[]) this.data).length;
        }

        if (this.data instanceof double[]) {
            return ((double[]) this.data).length;
        }

        if (this.data instanceof Object[]) {
            return ((Object[]) this.data).length;
        }

        throw new RuntimeException("Not array");

    }

}
