importPackage(Packages.bwapi);
importPackage(Packages.bpbwapi.events);

java.lang.System.out.println("Defining Global BroodWar Constants...");
/* when passing parameters to a variadic function in Java
 pass them inside an array - Rhino doesn't auto-cast them
 to a single object array like Java does! It instead
 inteprets them as a JS function call with a first this parameter!*/
unitCreateEvent = new EventsOfClass([UnitCreate]);
aFrameEvent = new FrameEvent();
java.lang.System.out.println("Finished defining constants.");
