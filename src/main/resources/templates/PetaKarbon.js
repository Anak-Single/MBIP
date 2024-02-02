function openPopup(popupId) {
    document.getElementById("overlay").style.display = "block";
    document.getElementById(popupId).style.display = "block";
}

function closePopup(popupId) {
    document.getElementById("overlay").style.display = "none";
    document.getElementById(popupId).style.display = "none";
}

function openPopup2() {
    document.getElementById("popup2").style.display = "block";
}

function closePopup2() {
    document.getElementById("popup2").style.display = "none";
}

function initMap() {
    const options = {
        zoom: 16,
        center: { lat: 53.46312701980667, lng: -2.2472026054971903 }
    }

    map = new google.maps.Map(
        document.getElementById('map'),
        options
    )
}

/*  let map;

 async function initMap() {
     const { Map } = await google.maps.importLibrary("maps");

     map = new Map(document.getElementById("map"), {
         center: { lat: -34.397, lng: 150.644 },
         zoom: 8,
     });
 }

 initMap(); */