package com.BookSystem.ExcScript;

import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

 

public class ExecuteScript {
	private static final ExecuteScript EXECUTE_SCRIPT = new ExecuteScript();
	
	public static ExecuteScript getExecuteScript() {
		return EXECUTE_SCRIPT;
	}

	private GetTK executeMethod;
	
	public String getTK(String a) {
		return executeMethod.TL(a);
	}
	
	private ExecuteScript() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		
		try {
			String path = ExecuteScript.class.getResource("").getPath();
			System.out.println(path+"a.js");
			engine.eval(new FileReader(path+"a.js"));
			if(engine instanceof Invocable) {
				Invocable invocable = (Invocable)engine;
				// 从脚本引擎中返回一个给定接口的实现
				executeMethod = invocable.getInterface(GetTK.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String a = ExecuteScript.getExecuteScript().getTK("哼哼哈嘿快使用双截棍，哼哼哈嘿快使用双截棍,这首歌的原唱是谁");
		System.out.println(a);
	}
}
