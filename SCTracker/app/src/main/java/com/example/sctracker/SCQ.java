package com.example.sctracker;

// _scq 객체화, 싱글톤 패턴 사용
public class SCQ {
    private static final SCQ _scq = new SCQ();

    public static SCQ getInstance() {
        return _scq;
    }

    private SCQ() {
    }
}
