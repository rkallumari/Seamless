const BeaconScanner = require('node-beacon-scanner');
const noble = require('noble');
const scanner = new BeaconScanner({'noble': noble});
 
// Set an Event handler for the Bluetooth service
noble.on('stateChange', (state) => {
  if (state === "poweredOff") {
    scanner.stopScan()
  } else if (state === "poweredOn") {
    scanner.startScan()
  }
});


 
// Set an Event handler for beacons
scanner.onadvertisement = (ad) => {
  if(ad != null) {
     if(ad.beaconType === 'iBeacon') {
        var data = '\nUUID: ' + ad.iBeacon.uuid + ' Distance: '+ calculateDistance(ad.rssi, ad.iBeacon.txPower) + ' Timestamp: ' + new Date();
        console.log(ad);
        console.log(data);
        var fs = require("fs");
        fs.appendFile("temp.txt", data, (err) => {
          if (err) console.log(err);
            console.log("Successfully Written to File.");
          });
        } else if(ad.beaconType === 'eddystoneUid'){
	   var data = '\nUUID: ' + ad.eddystoneUid.namespece + ad.eddystoneUid.instance + ' Distance: '+ calculateDistance(ad.rssi, ad.eddystoneUid.txPower) + ' Timestamp: ' + new Date();
	   console.log(ad);
	   console.log(data);
           var fs = require("fs");
           fs.appendFile("temp.txt", data, (err) => {
           if (err) console.log(err);
             console.log("Successfully Written to File.");
           });
	}
     }
     scanner.stopScan();	
};

function calculateDistance(rssi, txPower) {
  
  if (rssi == 0) {
    return -1.0; 
  }

  var ratio = rssi*1.0/txPower;
  if (ratio < 1.0) {
    return Math.pow(ratio,10);
  }
  else {
    var distance =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;    
    return distance;
  }
} 

function scan() {
    scanner.startScan().then(() => {
     console.log('Started to scan.')  ;
    }).catch((error) => {
     console.error(error);
   });
}

setInterval(scan,10000);
