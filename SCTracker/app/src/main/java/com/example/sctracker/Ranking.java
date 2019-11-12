package com.example.sctracker;

import javax.script.ScriptEngineManager;
public class Ranking {

    public void callback(String result) {

        if(result != null) {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval("4*5");
        }

    }

}
