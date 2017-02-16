package com.jspiders.logics.compiler;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.jspiders.logics.business.ClassFinder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CompileSourceInMemory {
	public static List<String> compile(String codes) throws IOException {

		ArrayList<String> output = new ArrayList<String>();

		/*int count = 0;
		String temp = "";
		loop1:while(true){
			temp =temp+codes.charAt(count);
			count++;
			if(temp.contains("{")){
				break;
			}
		}
		String splits[] = temp.split(" ");
		String className = splits[2].trim();
		System.out.println("Class Name: "+className);
		System.out.println("temp: "+temp);
		 */
		String className = ClassFinder.findClass(codes);
		System.out.println("Class Name: "+className);
		System.out.println(codes);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if(compiler == null){
			output.add("Empty");
		}

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.println(codes);
		out.close();
		JavaFileObject file = new JavaSourceFromString(className, writer.toString());

		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
		CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

		boolean success = task.call();
		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			output.add(diagnostic.getCode());
			output.add(diagnostic.getKind().toString());
			output.add(""+diagnostic.getPosition());
			output.add(""+diagnostic.getStartPosition());
			output.add(""+diagnostic.getEndPosition());
			output.add(""+diagnostic.getSource());
			output.add(diagnostic.getMessage(null));
		}
		output.add("Execution : " + success);

		if (success) {
			try {
				// Create a stream to hold the output
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				// IMPORTANT: Save the old System.out!
				PrintStream old = System.out;
				// Tell Java to use your special stream
				System.setOut(ps);
				// Print some output: goes to your special stream
				//System.out.println("Foofoofoo!");

				URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File("").toURI().toURL() });
				Class.forName(className, true, classLoader).getDeclaredMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { null });
				// Put things back
				System.out.flush();
				System.setOut(old);
				// Show what happened
				output.add(baos.toString());
			} catch (ClassNotFoundException e) {
				output.add(output+" Class not found: " + e.toString());
			} catch (NoSuchMethodException e) {
				output.add(output+" No such method: " + e.toString());
			} catch (IllegalAccessException e) {
				output.add(output+" Illegal access: " + e.toString());
			} catch (InvocationTargetException e) {
				output.add(output+" Invocation target: " + e.toString());
			}
		}
		return output;
	}
}

