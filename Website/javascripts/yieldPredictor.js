var states = {};
states["Bihar"] = [
  "Araria",
  "Arwal",
  "Aurangabad",
  "Banka",
  "Begusarai",
  "Bhagalpur",
  "Bhojpur",
  "Buxar",
  "Darbhanga",
  "East Champaran",
  "Gaya",
  "Gopalganj",
  "Jamui",
  "Jehanabad",
  "Kaimur",
  "katihar",
  "Khagaria",
  "Kisanganj",
  "Lakhisarai",
  "Medhepura",
  "Madhubani",
  "Munger",
  "Muzzaffarpur",
  "Nalanda",
  "Nawada",
  "Patna",
  "Purnia",
  "Rohtas",
  "Saharsa",
  "Samastipur",
  "Saran",
  "Sheikhpura",
  "Sheohar",
  "Sitamarhi",
  "Siwan",
  "Supaul",
  "Vaishali",
  "West Champaran",
];
states["Haryana"] = [
  "Ambala",
  "Bhiwani",
  "Charkhi Dadri",
  "Faridabad",
  "Gurgaon",
  "Hisar",
  "Jhajjar",
  "Jind",
  "Katihal",
  "Karnal",
  "Kurukshetra",
  "Mahenderagarh",
  "Nuh",
  "Palwal",
  "Panchkula",
  "Panipat",
  "Rewari",
  "Rohtak",
  "Sirsa",
  "Sonipat",
  "Yamunanagar",
];
states["Maharashtra"] = [
  "Ahmednagar",
  "Akola",
  "Amravati",
  "Aurangabad",
  "Beed",
  "Bhandara",
  "Chandrapur",
  "Dhule",
  "Gadchiroli",
  "Gondia",
  "Hingoli",
  "Jalgaon",
  "Jalna",
  "Kolhapur",
  "Latur",
  "Mumbai City",
  "Mumbai Suburban",
  "Nagpur",
  "Nanded",
  "Nandurbar",
  "Nashik",
  "Osmanabad",
  "Parbhani",
  "Pune",
  "Raigad",
  "Ratnagiri",
  "Sangli",
  "Satara",
  "Sindhudurg",
  "Solapur",
  "Thane",
  "Wardha",
  "Washim",
  "Yavatmal",
  "Palghar",
];
states["Punjab"] = [
  "Amritsar",
  "Barnala",
  "Bathinda",
  "Fazilka",
  "Faridkot",
  "Fatehgarh Sahib",
  "Firozpur",
  "Gurdaspur",
  "Hoshiarpur",
  "Jalandhar",
  "Kapurthala",
  "Ludhiana",
  "Mansa",
  "Moga",
  "Mohali",
  "Muktsar",
  "Pathankot",
  "Patiala",
  "Rupnagar",
  "Sangrur",
  "Shahid Bhagat Singh Nagar ",
  "Tarn Taran",
];
states["UttarPradesh"] = [
  "Agra",
  "Aligarh",
  "Allahabad",
  "Ambedkar nagar",
  "Amethi",
  "Amroha",
  "Auraiya",
  "Azamgarh",
  "Baghpat",
  "Bahraich",
  "Ballia",
  "Balrampur",
  "Banda",
  "Barabanki",
  "Bareilly",
  "Basti",
  "Bhadohi",
  "Bijnor",
  "Budaun",
  "Bulandshahr",
  "Chandauli",
  "Chitrakoot",
  "Deoria",
  "Etah",
  "Etawah",
  "Faizabad",
  "Farrukhabad",
  "Fatehpur",
  "Firozabad",
  "Gautam Buddha Nagar",
  "Ghaziabad",
  "Gazipur",
  "Gonda",
  "Gorakhpur",
  "Hamirpur",
  "Hapur",
  "Hardor",
  "Hathras",
  "Jaunpur",
  "Jalaun",
  "Jhansi",
  "Kannauj",
  "Kanpur Dehat",
  "Kanpur Nagar",
  "Kanshiram Nagar",
  "Kaushambi",
  "Kushinagar",
  "Lakhimpur Kheri",
  "Khusinagar",
  "Lalitpur",
  "Lucknow",
  "Maharajaganj",
  "Mahoba",
  "Mainpiri",
  "Mathura",
  "Mau",
  "Meerut",
  "Mirzapur",
  "Moradabad",
  "Muzaffarnagr",
  "Hapur",
  "Pilibhit",
  "Pratapgarh",
  "Raebareli",
  "Rampur",
  "Saharanpur",
  "Sant Kabir Nagar",
  "Shahjahanpur",
  "Shamli",
  "Shravasti",
  "Siddharthnagar",
  "Sitapur",
  "Sonbhadra",
  "Sultanpur",
  "Unnao",
  "Varanasi",
];
var Cstates = {};
Cstates["Bihar"] = ["Rice", "Wheat", "Maize"];
Cstates["Haryana"] = ["Rice", "Wheat"];
Cstates["Maharashtra"] = ["Arhar", "Cotton", "Rice", "Soya"];
Cstates["Punjab"] = ["Rice", "Maize", "Wheat"];
Cstates["UttarPradesh"] = ["Rice", "Sugarcane", "Wheat"];

function changeDistList() {
  var state = document.getElementById("_state");
  var district = document.getElementById("_district");
  var crop = document.getElementById("_crop");
  var selected_option = state.options[state.selectedIndex].value;
  while (district.options.length) {
    district.remove(0);
  }
  var cats = states[selected_option];
  if (cats) {
    var i;
    for (i = 0; i < cats.length; i++) {
      var cat = new Option(cats[i], i);
      district.options.add(cat);
    }
  }
  while (crop.options.length) {
    crop.remove(0);
  }
  var cats = Cstates[selected_option];
  if (cats) {
    var i;
    for (i = 0; i < cats.length; i++) {
      var cat = new Option(cats[i], i);
      crop.options.add(cat);
    }
  }
}

async function getValues() {
   var state = document.getElementById("_state");
  var district = document.getElementById("_district");
  var crop = document.getElementById("_crop");
  var selectedState=state.value;      //This gives value of selected state.
  var selectedDistrict=district.options[district.selectedIndex].innerHTML;   // This gives value of selected district.
  var selectedCrop=crop.options[crop.selectedIndex].innerHTML;   // This gives value of selected crop.\

  var selectedYear=2021;
  console.log(selectedState);
  console.log(selectedDistrict);
  console.log(selectedCrop);
  console.log(JSON.stringify({
        year: parseInt(selectedYear),
        district_encode: parseInt(district.value)
      }));
  
    if (selectedState=="Bihar"){
      selectedState="br";
    }
     if (selectedState=="Haryana"){
      selectedState="hr";
    }
     if (selectedState=="Maharashtra"){
      selectedState="mh";
    }
     if (selectedState=="Punjab"){
      selectedState="pb";
    }
     if (selectedState=="UttarPradesh"){
      selectedState="up";
    }

      var lselectedCrop=selectedCrop.toLowerCase();

  await fetch(`https://projectcentralcrew.azurewebsites.net/${selectedState}/${lselectedCrop}`, {
    method: 'POST',
    mode: 'no-cors',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        year: parseInt(selectedYear),
        district_encode: parseInt(district.value)
      })
  }).then(r=>r.text()).then(console.log)
  
}

