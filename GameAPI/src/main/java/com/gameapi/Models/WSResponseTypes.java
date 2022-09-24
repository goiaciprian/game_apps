package com.gameapi.Models;

public enum WSResponseTypes {
    CODE("CODE"), EXCEPTION("EXCEPTION");

    private final String _name;
    WSResponseTypes(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

}
