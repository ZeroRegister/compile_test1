package cn.edu.bit.newnewcc.backend.asm.util;

public class Pair<A, B> {
    public A a;
    public B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "<" + a.toString() + "," + b.toString() + ">";
    }
}

