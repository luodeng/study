package com.roden.study.java.util.function;

import java.util.function.Supplier;

public class InterfaceTest {
    public static void main( String[] args ) {
        Defaulable defaulable = DefaulableFactory.create(DefaultableImpl::new );
        System.out.println( defaulable.notRequired() );

        defaulable = DefaulableFactory.create( OverridableImpl::new );
        System.out.println( defaulable.notRequired() );
    }
}

interface Defaulable {
    // Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.

    default String notRequired() {
        return "Default implementation";
    }
}

class DefaultableImpl implements Defaulable { }

class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}

interface DefaulableFactory {
    // Interfaces now allow static methods
    static Defaulable create(Supplier<Defaulable> supplier) {
        return supplier.get();
    }
}


