package tictactoe.bThreads;

import bp.BThread;

public class DetectDraw extends BThread {

    public DetectDraw() {
//        String source = "for (count = 0; count < 9; count++) {\n" +
//                jsIdentifier() + ".bsync(none, moves, none);\n" +
//                "java.lang.System.out.println(\"DetectDraw move \" + count)\n" +
//                "}\n" +
//                jsIdentifier() + ".bsync(draw, none, none);\n";
        String source = jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 1\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 2\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 3\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 4\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 5\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 6\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 7\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 8\");\n" +
                jsIdentifier() +
                ".bsync(none, moves, none);\n" +
                "java.lang.System.out.println(\"DetectDraw move 9\");\n" +
                jsIdentifier() + ".bsync(draw, none, none);\n";
        setScript(source);
    }


    public String toString() {
        return "DetectDraw";
    }

}
