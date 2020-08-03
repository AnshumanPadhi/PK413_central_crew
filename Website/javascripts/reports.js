function rice() {
  var dname = document.getElementById("dnamei").value;
  var yan = document.getElementById("yai").value;
  var lan = document.getElementById("lai").value;
  document.getElementById("reportWheat").style.display = "none";
  document.getElementById("reportMaize").style.display = "none";
  document.getElementById("reportSugarcane").style.display = "none";
  document.getElementById("reportRice").style.display = "block";

  document.getElementById("dn1").innerHTML = dname;
  document.getElementById("yv1").innerHTML = yan;
  document.getElementById("la1").innerHTML = lan;
  var la = lan * 2.471;
  var ya = yan * 411.1;

  document.getElementById("rseed").innerHTML = la * 8 + " kg";
  document.getElementById("rfer1").innerHTML = la * 110 + " kg";
  document.getElementById("rfer2").innerHTML = la * 27 + "/" + la * 75 + " kg";
  document.getElementById("rfer3").innerHTML = la * 20 + " kg";
  document.getElementById("rnutri1").innerHTML = la * 50 + " kg";
  document.getElementById("rnutri2").innerHTML = la * 12 + " kg";
  document.getElementById("rnutri3").innerHTML = la * 12 + " kg";
  document.getElementById("rweed").innerHTML = la * 30 + " gm";
  document.getElementById("rwater").innerHTML = la * ya * 4000 + " litres";
  document.getElementById("rbag").innerHTML = (la * ya) / 100 + " bags";
  document.getElementById("rlogi").innerHTML = (la * ya) / 20000 + " trucks";
}
function wheat() {
  var dname = document.getElementById("dnamei").value;
  var yan = document.getElementById("yai").value;
  var lan = document.getElementById("lai").value;
  document.getElementById("reportRice").style.display = "none";
  document.getElementById("reportMaize").style.display = "none";
  document.getElementById("reportSugarcane").style.display = "none";
  document.getElementById("reportWheat").style.display = "block";

  document.getElementById("dn2").innerHTML = dname;
  document.getElementById("yv2").innerHTML = yan;
  document.getElementById("la2").innerHTML = lan;
  var la = lan * 2.471;
  var ya = yan * 411.33;

  document.getElementById("wseed").innerHTML = la * 45 + " kg";
  document.getElementById("wfer1").innerHTML = la * 110 + " kg";
  document.getElementById("wfer2").innerHTML = la * 55 + " kg";
  document.getElementById("wfer3").innerHTML = la * 20 + " kg";
  document.getElementById("wnutri1").innerHTML = la * 50 + " kg";
  document.getElementById("wnutri2").innerHTML = la * 25 + " kg";
  document.getElementById("wnutri3").innerHTML = la * 12 + " kg";
  document.getElementById("wweed").innerHTML = la * 1 + " l";
  document.getElementById("wwater").innerHTML = la * ya * 2300 + " litres";
  document.getElementById("wbag").innerHTML = (la * ya) / 100 + " bags";
  document.getElementById("wlogi").innerHTML = (la * ya) / 20000 + " trucks";
}
function maize() {
  var dname = document.getElementById("dnamei").value;
  var yan = document.getElementById("yai").value;
  var lan = document.getElementById("lai").value;
  document.getElementById("reportWheat").style.display = "none";
  document.getElementById("reportRice").style.display = "none";
  document.getElementById("reportSugarcane").style.display = "none";
  document.getElementById("reportMaize").style.display = "block";

  document.getElementById("dn3").innerHTML = dname;
  document.getElementById("yv3").innerHTML = yan;
  document.getElementById("la3").innerHTML = lan;
  var la = lan * 2.471;
  var ya = yan * 411.33;
  document.getElementById("mseed").innerHTML = la * 10 + " kg";
  document.getElementById("mfer1").innerHTML = la * 100 + " kg";
  document.getElementById("mfer2").innerHTML = la * 45 + "/" + la * 130 + " kg";
  document.getElementById("mfer3").innerHTML = la * 18 + " kg";
  document.getElementById("mfer4").innerHTML = la * 8 + " kg";
  document.getElementById("mnutri1").innerHTML = la * 45 + " kg";
  document.getElementById("mnutri2").innerHTML = la * 20 + " kg";
  document.getElementById("mnutri3").innerHTML = la * 10 + " kg";
  document.getElementById("mweed").innerHTML = la * 50 + " gm";
  document.getElementById("mwater").innerHTML = la * ya * 2800 + " litres";
  document.getElementById("mbag").innerHTML = (la * ya) / 100 + " bags";
  document.getElementById("mlogi").innerHTML = (la * ya) / 20000 + " trucks";
}
function sugarcane() {
  var dname = document.getElementById("dnamei").value;
  var yan = document.getElementById("yai").value;
  var lan = document.getElementById("lai").value;
  document.getElementById("reportWheat").style.display = "none";
  document.getElementById("reportMaize").style.display = "none";
  document.getElementById("reportRice").style.display = "none";
  document.getElementById("reportSugarcane").style.display = "block";

  document.getElementById("dn4").innerHTML = dname;
  document.getElementById("yv4").innerHTML = yan;
  document.getElementById("la4").innerHTML = lan;
  var la = lan * 2.471;
  var ya = yan * 411.33;
  document.getElementById("sseed").innerHTML = la * 3500 + " kg";
  document.getElementById("sfer1").innerHTML = la * 180 + " kg";
  document.getElementById("sfer2").innerHTML = la * 125 + "/" + la * 75 + " kg";
  // document.getElementById("sfer3").innerHTML = la * 20 + " kg";
  document.getElementById("snutri1").innerHTML = la * 80 + " kg";
  document.getElementById("snutri2").innerHTML = la * 20 + " kg";
  //document.getElementById("snutri3").innerHTML = la * 12 + " kg";
  document.getElementById("sweed").innerHTML = la * 1.2 + " kg";
  document.getElementById("swater").innerHTML = la * ya * 2500 + " litres";
  document.getElementById("sbag").innerHTML = (la * ya) / 100 + " bags";
  document.getElementById("slogi").innerHTML = (la * ya) / 20000 + " trucks";
}
