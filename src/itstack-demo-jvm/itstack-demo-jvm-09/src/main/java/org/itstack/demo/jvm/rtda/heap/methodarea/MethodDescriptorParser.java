package org.itstack.demo.jvm.rtda.heap.methodarea;

import javax.management.ObjectName;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/28
 */
public class MethodDescriptorParser {

    private String raw;
    private int offset;
    private MethodDescriptor parsed;

    public static MethodDescriptor parseMethodDescriptorParser(String descriptor) {
        MethodDescriptorParser parser = new MethodDescriptorParser();
        return parser.parse(descriptor);
    }

    public MethodDescriptor parse(String descriptor) {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        this.startParams();
        this.parseParamTypes();
        this.endParams();
        this.parseReturnType();
        this.finish();
        return this.parsed;
    }

    private void startParams() {
        if (this.readUint8() != '(') {
            causePanic();
        }
    }

    private void endParams() {
        if (this.readUint8() != ')') {
            causePanic();
        }
    }

    public void finish(){
        if (this.offset != this.raw.length()){
            this.causePanic();
        }
    }

    public void causePanic() {
        throw new RuntimeException("BAD descriptor：" + this.raw);
    }

    public byte readUint8() {
        byte[] bytes = this.raw.getBytes();
        byte b = bytes[this.offset];
        this.offset++;
        return b;
    }

    public void unreadUint8() {
        this.offset--;
    }

    public void parseParamTypes() {
        while (true) {
            String type = this.parseFieldType();
            if ("".equals(type)) break;
            this.parsed.addParameterType(type);
        }
    }

    public void parseReturnType() {
        if (this.readUint8() == 'V'){
            this.parsed.returnType = "V";
            return;
        }

        this.unreadUint8();
        String type = this.parseFieldType();
        if (!"".equals(type)){
            this.parsed.returnType = type;
            return;
        }

        this.causePanic();
    }

    public String parseFieldType() {
        switch (this.readUint8()) {
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'S':
                return "S";
            case 'Z':
                return "Z";
            case 'L':
                return this.parseObjectType();
            case '[':
                return this.parseArrayType();
            default:
                this.unreadUint8();
                return "";
        }
    }

    private String parseObjectType() {
        String unread = this.raw.substring(this.offset);
        int semicolonIndx = unread.indexOf(";");
        if (semicolonIndx == -1) {
            this.causePanic();
            return "";
        }
        int objStart = this.offset - 1;
        int ojbEnd = this.offset + semicolonIndx + 1;
        this.offset = ojbEnd;
        //descriptor
        return this.raw.substring(objStart, ojbEnd);
    }

    private String parseArrayType() {
        int arrStart = this.offset - 1;
        this.parseFieldType();
        int arrEnd = this.offset;
        //descriptor
        return this.raw.substring(arrStart, arrEnd);
    }


}
