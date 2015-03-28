package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.List;

import tictactoe.events.Click;
import bp.BThread;
import bp.search.EnvironmentSimBThread;

/**
 * A scenario that handles click events
 */
public class ClickSimulator extends EnvironmentSimBThread {
	private int row, col;

	public ClickSimulator(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void runBThread() {
		try {

			bsync(new Click(row, col), none, none);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "ClickSimulator(" + row + "," + col + ")";
	}

	public static List<BThread> constructInstances() {
		List<BThread> bts = new ArrayList<BThread>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// System.out.println("Invoking javaflow instrumentation "
				// + ClickSimulator.class.getName() + " " + "tictactoe");
				// ContinuationClassLoader cl1 = new ContinuationClassLoader(
				// new URL[] { new File("bin/").toURI().toURL() },
				// ClickSimulator.class.getClassLoader());
				//
				// cl1.addLoaderPackageRoot("tictactoe");
				// cl1.addLoaderPackageRoot(bpjPackage);
				//
				// Class<?> loadClass =
				// cl1.loadClass(ClickSimulator.class.getName());
				//
				// m = (BApplication) classToLoad.newInstance();
				BThread sc = new ClickSimulator(i, j);
				bts.add(sc);
			}
		}
		return bts;
	}
}