importPackage(Packages.bpbwapi.optimalMineralsExample);

function findAndHarvestMinerals(move) {
    while(true){
        var minerals = bsync(new FindMineral(this), none, none);
        var othersHarvesting = new OthersHarvesting(this,
            minerals.getLocation());
        while(notFullyLoaded){
            var harvest =
            bsync(new Harvest(minerals.getLocation()),
            none, othersHarvesting);
            updateLoaded(harvest.getAmount());
        }
        bsync(new ReturnToBase(this), none, none);
    }
}

var totalMinerals = 0;
function hMineralsFunction() {
    while(true){
        var collectedEvent = bsync(none, new EventsOfClass(MineralsCollectedEvent), none);
        totalMinerals = totalMinerals + collectedEvent.getValue();
        bsync(new ResourceUpdated(), none, none);
    }
}
bpjs.registerBThread("total-minerals-bt", hMineralsFunction);

var totalGas = 0;
function hGasFunction() {
    while(true){
        var collectedEvent = bsync(none, new EventsOfClass(GasCollectedEvent), none);
        totalMinerals = totalMinerals + collectedEvent.getValue();
        bsync(new ResourceUpdated(), none, none);
    }
}
bpjs.registerBThread("total-gas-bt", hGasFunction);

var totalResources;
bpjs.registerBThread("total-resources-bt", function() {
    bsync(none, new EventsOfClass(ResourceUpdated), none);
    totalResources = totalMinerals + totalGas;
});

function h() {
    return totalResources;
}

bpjs.registerHeuristicFunction(h);