importPackage(Packages.bp);
importPackage(Packages.bp.eventSets);
importPackage(Packages.bp.exceptions);

bploop = function(start, end, step, func) {
  if (start <= end) {
    func(start);
    bploop(start + step, end, step, func);
  } else {
    return end;
  }
};

/*java.lang.System.out.println("evaluated globalScopeInit");*/
