function openPopup(popupId) {
    document.getElementById("overlay").style.display = "block";
    document.getElementById(popupId).style.display = "block";
}

function closePopup(popupId) {
    document.getElementById("overlay").style.display = "none";
    document.getElementById(popupId).style.display = "none";
}

/*   function openPopup2() {
      document.getElementById("popup2").style.display = "block";
  }

  function closePopup2() {
      document.getElementById("popup2").style.display = "none";
  } */

function initMap() {
    const options = {
        zoom: 12,
        center: { lat: 1.460726835138045, lng: 103.66776819560515 }


    }

    let map = new google.maps.Map(
        document.getElementById('map'),
        options
    )

    const markerPositions = [
        { lat: 1.5353739594086127, lng: 103.65781183205502, popupId: 'Skudai', info: 'Skudai' },
        { lat: 1.5032336868203957, lng: 103.617632065453, popupId: 'LimaKedai', info: 'Lima Kedai' },
        { lat: 1.452169947220544, lng: 103.5993072625927, popupId: 'GelangPatah', info: 'Gelang Patah' },
        { lat: 1.5089474366426703, lng: 103.51327296294426, popupId: 'KangkarPulai', info: 'Kangkar Pulai' },
        { lat: 1.42042257129841, lng: 103.6257431127626, popupId: 'IskandarPuteri', info: 'Iskandar Puteri' },
        { lat: 1.527928213821243, lng: 103.54105325305007, popupId: 'UluChoh', info: 'Ulu Choh' }
    ];

    // Create a single infowindow instance
    const infowindow = new google.maps.InfoWindow();

    // Create markers and add click and hover event listeners
    markerPositions.forEach(position => {
        const marker = new google.maps.Marker({
            position: position,
            map: map,
            title: position.info
        });

        marker.addListener('click', function () {
            openPopup(position.popupId);
        });

        marker.addListener('mouseover', function () {
            // Show tooltip or infowindow with the name of the location
            infowindow.setContent(position.info);
            infowindow.open(map, marker);
        });

        marker.addListener('mouseout', function () {
            // Close the tooltip or infowindow when mouseout
            infowindow.close();
        });
    });

}