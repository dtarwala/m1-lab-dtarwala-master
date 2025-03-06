package timing;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bridges.base.LineChart;
import bridges.validation.RateLimitException;
import coursesupport.Bridge;
import timing.output.Output;


import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 
 * @author roncytron
 * 
 * Allows runs of an algorithm for a specific input size.
 * This is used for generating timing profiles for an algorithm.
 *
 * @param <T> Input type for the algorithm
 * @param <U> Output type for the algorithm
 */
public class ExecuteAlgorithm<T,U> {

	private final static int NUMREPEATS = 3;
	private U              results;
	private T              input;
	private Algorithm<T,U> algorithm;
	private Long           ticks;
	private Duration       time;
	
	
	public static void openURL(String url) {
		if(credentials.Assignment.OPEN == false) {
			return; 
		}
		// Based on https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				System.err.println("Unable to open browser to " + url);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param inputProvider source for the input to the algorithm
	 * @param algorithm     the algorithm itself
	 * @param size          describes the size of the input to be supplied by the inputProvider
	 */
	public ExecuteAlgorithm(InputProvider<T> inputProvider, Algorithm<T,U> algorithm, InputSpec size) {
		this.input     = inputProvider.genInput(size);
		this.algorithm = algorithm;
	}

	/**
	 * Load the input, and then run the algorithm under the
	 * controlled timing setting.
	 */
	public void run() {
		algorithm.loadInput(input);
		GenResults gs = new GenResults(algorithm, NUMREPEATS);
		gs.run();
		this.results = algorithm.getResults();
		this.ticks   = gs.getTicks();
		this.time    = gs.getTime();
	}

	public U getResults() {
		return results;
	}

	public Duration getTime() {
		return time;
	}

	public Long getTicks() {
		return ticks;
	}
	
	/**
	 * 
	 * @param name
	 * @param className String name of the class to be instantiated
	 * @param ip can provide suitable input of specified size
	 * @param sizes values of n to try for the algorithm
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static<T,U> List<U> timeAlgorithm(
			String name,
			String className,
			InputProvider<T> ip,
			Iterable<Integer> sizes
			) {

		ArrayList<Double> n = new ArrayList<Double>();
		ArrayList<Double> timeValues = new ArrayList<Double>();
		ArrayList<Double> tickValues = new ArrayList<Double>();
		
		try {			
			List<U> results = new LinkedList<U>();
			Algorithm<T,U> alg = (Algorithm<T,U>) Class.forName(className).newInstance();
			Output ticks = new Output(name+".ticks", name+"-ticks");
			Output times = new Output(name+".time", name+"-time");
			for (int size : sizes) {
				ExecuteAlgorithm<T,U> ea = new ExecuteAlgorithm<T,U>(
						ip, alg, InputSpec.gen(size)
						);
				ea.run();
				ticks.writeSizeValue(size, ea.getTicks());
				times.writeSizeValue(size, ea.getTime().toMillis());

				n.add((double)size);
				timeValues.add((double)ea.getTime().toMillis());
				tickValues.add((double)ea.getTicks());
				
				System.out.println("size \tticks \ttime");
				System.out.println(size+" \t"+ea.getTicks()+" \t"+ea.getTime().toMillis());
				results.add(ea.getResults());
			}
			
			Bridge.setDisplayMode("stack");
			Bridge.setTitle(name);
			Bridge.setDescription("");
			try {
				Bridge.postVisualizationLink(false);
				LineChart timePlot = new LineChart();
				timePlot.setTitle(name + ": n vs. time");
				Bridge.setTitle(name + ": n vs. time");
				timePlot.setDataSeries("time", n, timeValues);
				Bridge.setDataStructure(timePlot);
				Bridge.visualize();

				LineChart tickPlot = new LineChart();
				tickPlot.setTitle(name + ": n vs. ticks");
				Bridge.setTitle(name + ": n vs. ticks");
				tickPlot.setDataSeries("ticks", n, tickValues);
				Bridge.setDataStructure(tickPlot);
				Bridge.visualize();

				LineChart ttPlot = new LineChart();
				ttPlot.setTitle(name + ": n vs. ticks and time");
				Bridge.setTitle(name + ": n vs. ticks and time");
				ttPlot.setDataSeries("ticks", n, tickValues);
				ttPlot.setDataSeries("time", n, timeValues);
				Bridge.setDataStructure(ttPlot);
				Bridge.visualize();

				Bridge.postVisualizationLink(true);
				
				String url = "http://bridges-cs.herokuapp.com/assignments/" + Bridge.getAssignmentID() + "/" + Bridge.getUserName(); 
				System.out.println(url);
				openURL(url);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RateLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			return results;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Error("Error " + t);
		}

	}

}
