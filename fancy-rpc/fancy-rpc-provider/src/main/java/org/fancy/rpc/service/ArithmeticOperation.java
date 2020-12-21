package org.fancy.rpc.service;

import org.fancy.rpc.api.Operation;

public class ArithmeticOperation implements Operation {

    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public int sub(int x, int y) {
        return x - y;
    }

    @Override
    public int mult(int x, int y) {
        return x * y;
    }

    @Override
    public int div(int x, int y) {
        return x / y;
    }
}
