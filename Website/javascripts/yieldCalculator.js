function smartYield() {
  var crop1name = document.getElementById("_crop1name").value;
  var crop1msp = document.getElementById("_crop1msp").value;
  var crop1inv = document.getElementById("_crop1inv").value;
  var crop1yield = document.getElementById("_crop1yield").value;
  var crop2name = document.getElementById("_crop2name").value;
  var crop2msp = document.getElementById("_crop2msp").value;
  var crop2inv = document.getElementById("_crop2inv").value;
  var crop2yield = document.getElementById("_crop2yield").value;
  var landarea = document.getElementById("_landarea").value;

  if (
    crop1name &&
    crop1msp &&
    crop1inv &&
    crop2name &&
    crop2msp &&
    crop2inv &&
    landarea &&
    crop1yield &&
    crop2yield
  ) {
    if (
      crop1msp > 0 &&
      crop1inv > 0 &&
      crop2msp > 0 &&
      crop2inv > 0 &&
      landarea > 0 &&
      crop1yield > 0 &&
      crop2yield > 0
    ) {
      var prof1 = crop1msp - crop1inv; //profit per quintal for crop 1 in Rupees
      var prof2 = crop2msp - crop2inv; //profit per quintal for crop 2 in Rupees
      var land1 = 1 / crop1yield; //Land require to grow 1 quintal of crop 1 in acre
      var land2 = 1 / crop2yield; //Land require to grow 1 quintal of crop 2 in acre
      var yield1 = landarea / land1; //yield of crop 1 in quintal in given land area
      var yield2 = landarea / land2; //yield of crop 2 in quintal in given land area
      var fpro1 = yield1 * prof1; //Profit when we grow only crop 1 in Rupees
      var fpro2 = yield2 * prof2; //Profit when we grow only crop 2 in Rupees
      var x = (prof2 * land1) / (prof1 * land2);
      var y = x + 1;
      var landarea1 = (x / y) * 100; // Smart yield land percentage for crop 1
      var landarea2 = (1 / y) * 100; // Smart yield land percentage for crop 2
      var fland1 = (landarea1 / 100) * landarea; // final land for crop 1 in smart yield
      var fland2 = (landarea2 / 100) * landarea; // final land for crop 2 in smart yield
      var sminv1 = crop1inv * (fland1 / land1); // smart investment for crop 1
      var sminv2 = crop2inv * (fland2 / land2); // smart investment for crop 1
      var smmsp1 = crop1msp * (fland1 / land1); // smart msp for crop 1
      var smmsp2 = crop2msp * (fland2 / land2); // smart msp for crop 1
      var smtprof = smmsp1 + smmsp2 - (sminv1 + sminv2);

      var NCF1 = crop1inv * yield1;
      var NCF2 = crop2inv * yield2;
      var NCF3 = Math.abs(
        fpro2 / 2 -
          crop1inv * (yield1 / 2) -
          (fpro1 / 2 - crop2inv * (yield2 / 2))
      );
      var NCF4 = Math.abs(
        smmsp2 - (sminv1 + sminv2) - (smmsp1 - (sminv1 + sminv2))
      );

      document.getElementById("c1t1").innerHTML = crop1inv * yield1;
      document.getElementById("c1t9").innerHTML = crop1msp * yield1;
      document.getElementById("c1t2").innerHTML = fpro1;
      document.getElementById("c1t3").innerHTML = crop1inv * yield1;
      document.getElementById("c1t7").innerHTML = "-" + crop1inv * yield1;
      document.getElementById("c1t4").innerHTML = crop2inv * yield2;
      document.getElementById("c1t10").innerHTML = crop2msp * yield2;
      document.getElementById("c1t5").innerHTML = fpro2;
      document.getElementById("c1t6").innerHTML = crop2inv * yield2;
      document.getElementById("c1t8").innerHTML = "-" + crop2inv * yield2;

      document.getElementById("c2t11").innerHTML = crop1inv * (yield1 / 2);
      document.getElementById("c2t1").innerHTML = crop1msp * (yield1 / 2);
      document.getElementById("c2t2").innerHTML = fpro1 / 2;
      document.getElementById("c2t3").innerHTML = crop1inv * (yield1 / 2);
      document.getElementById("c2t9").innerHTML =
        fpro2 / 2 - crop1inv * (yield1 / 2);
      document.getElementById("c2t5").innerHTML = crop2inv * (yield2 / 2);
      document.getElementById("c2t12").innerHTML = crop2msp * (yield2 / 2);
      document.getElementById("c2t6").innerHTML = fpro2 / 2;
      document.getElementById("c2t7").innerHTML = crop2inv * (yield2 / 2);
      document.getElementById("c2t10").innerHTML =
        fpro1 / 2 - crop2inv * (yield2 / 2);
      document.getElementById("c2t13").innerHTML = fpro1 / 2 + fpro2 / 2;

      document.getElementById("c3t1").innerHTML = landarea1;
      document.getElementById("c3t2").innerHTML = fland1;
      document.getElementById("c3t3").innerHTML = sminv1;
      document.getElementById("c3t4").innerHTML = smmsp1;
      document.getElementById("c3t5").innerHTML = smmsp1 - sminv1;
      document.getElementById("c3t6").innerHTML = sminv1;
      document.getElementById("c3t7").innerHTML = smmsp2 - (sminv1 + sminv2);
      document.getElementById("c3t8").innerHTML = landarea2;
      document.getElementById("c3t9").innerHTML = fland2;
      document.getElementById("c3t10").innerHTML = sminv2;
      document.getElementById("c3t11").innerHTML = smmsp2;
      document.getElementById("c3t12").innerHTML = smmsp2 - sminv2;
      document.getElementById("c3t13").innerHTML = sminv2;
      document.getElementById("c3t14").innerHTML = smmsp1 - (sminv1 + sminv2);
      document.getElementById("c3t15").innerHTML = smtprof;

      document.getElementById("c4t1").innerHTML = NCF1;
      document.getElementById("c4t2").innerHTML = NCF2;

      document.getElementById("c4t3").innerHTML = NCF3;
      document.getElementById("c4t4").innerHTML = NCF4;

      if (NCF3 < NCF4) {
        document.getElementById("c4t5").innerHTML =
          " use 50% of given land for each crop";
      } else {
        document.getElementById("c4t5").innerHTML =
          " use Smart Yield land % for each crop";
      }

      var data = {
        datasets: [
          {
            data: [NCF1, NCF2, NCF3, NCF4],
            backgroundColor: ["#FF6384", "#4BC0C0", "#FFCE56", "#E7E9ED"],
            label: "My dataset", // for legend
          },
        ],
        labels: ["DNCF1", "DNCF2", "DNCF3", "DNCF4"],
      };
      var ctx = $("#myChart");
      new Chart(ctx, {
        data: data,
        type: "pie",
      });
    } else {
      alert("Error in values");
    }
  } else {
    alert("Field cannot be left empty");
  }
}
