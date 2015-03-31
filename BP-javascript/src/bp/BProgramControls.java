package bp;

public class BProgramControls {

	public static boolean suppressDeadlock = false;

	/**
	 * If shallow=true, we will not go through deep serialization of thread
	 * states.
	 */
	public static String bpjPackage = "bp";
	public static boolean globalShallow = false;
	public static boolean globalDFS = true;
	public static boolean globalSafetyCheck = true;
	public static boolean globalRunSubtree = false;
	public static boolean globalDisableStateHashing = false;
	public static boolean globalLogBacktracking = false;
	public static int estimatedStates = new Integer(0);
	public static String temp;
	public static boolean iterativeMode = false;
	public static boolean javaflowInstrumentation = true;
	/**
	 * Indicates whether this program is executed in verification mode
	 */
	static public boolean continuationMode = false;
	/**
	 * Indicates whether this program is executed in DEBUG mode E.g. issue debug
	 * msgs.
	 */
	public static boolean debugMode = false;
	/**
	 * Indicates whether this program is executed in LOG mode E.g. issue EVENT
	 * occurrence messages .
	 */
	public static boolean logMode = true;

	// TODO Remove iterativeMode and use only RunMode

	static {

		// if (System.getProperty("suppressDeadlock", "false").equals("true"))
		// suppressDeadlock = true;
		// System.out.println("suppressDeadlock=" + suppressDeadlock);
		//
		// if (System.getProperty("shallow", "false").equals("true"))
		// globalShallow = true;
		// System.out.println("shallow=" + globalShallow);
		//
		// if (System.getProperty("search", "DFS").equals("BFS"))
		// globalDFS = false;
		// System.out.println("search=" + (globalDFS ? "DFS" : "BFS"));
		//
		// if (System.getProperty("safetyCheck", "true").equals("false"))
		// globalSafetyCheck = false;
		// System.out.println("safetyCheck=" + globalSafetyCheck);
		//
		// if (System.getProperty("recursive", "false").equals("true"))
		// globalRunSubtree = true;
		// System.out.println("recursive=" + globalRunSubtree);
		//
		// if (System.getProperty("disableStateHashing",
		// "false").equals("true"))
		// globalDisableStateHashing = true;
		// System.out.println("disableStateHashing=" +
		// globalDisableStateHashing);
		//
		// estimatedStates =
		// Integer.parseInt(System.getProperty("estimatedStates", "100000"));
		// System.out.println("estimatedStates=" + estimatedStates);
		//
		// if (System.getProperty("logBacktracking", "false").equals("true"))
		// globalLogBacktracking = true;
		// System.out.println("logBacktracking=" + globalLogBacktracking);

	}

}
