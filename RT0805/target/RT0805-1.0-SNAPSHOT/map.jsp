<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Bienvenue sur la map" />
</jsp:include>

<c:if test="${empty auth}">
    <c:redirect url="/index"/>
</c:if>

    <body>
        <div id="infos">
            Date de début : ${act.date_debut}
            <br>
            Date de fin : ${act.date_fin}
            <br>
            <p id="km"></p>
        </div>
        <div id="mapid"></div>
        <script>

           
            function distanceInKm(lat1, lon1, lat2, lon2) 
            {
                var earthRadiusKm = 6371;
                
                var dLat = (lat2-lat1) * Math.PI / 180;
                var dLon = (lon2-lon1) * Math.PI / 180;
                
                lat1 = lat1 * Math.PI / 180;
                lat2 = lat2 * Math.PI / 180;
                
                var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
                var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                return earthRadiusKm * c;
            }

            function CreateMarker(Coord)
            {
                var marker1 = L.marker([Coord["coord_x"], Coord["coord_y"]]).addTo(maCarte);
                marker1.bindPopup("<b>".concat(Coord["heure"], "</b>"));
            }

            function connectTheDots(data)
            {
                size = data.length;
                for (i = 0; i < size-1; i++)
                {
                    var polygon = L.polygon([[data[i]["coord_x"], data[i]["coord_y"]], [data[i+1]["coord_x"], data[i+1]["coord_y"]]]).addTo(maCarte);
                }
            }
            arrayPts = ${act.pts};
            var maCarte = L.map('mapid').setView([arrayPts[0]["coord_x"], arrayPts[0]["coord_y"]], 15);

            L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
                maxZoom: 18,
                attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
                    '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
                    'Imagery © <a href="http://mapbox.com">Mapbox</a>',
                    id: 'mapbox.streets'
                }).addTo(maCarte);
            
            arrayPts.forEach(CreateMarker);

            pathCoords = connectTheDots(arrayPts);

            var distance = 0;
            for (i = 0; i < arrayPts.length-1; i++)
            {
               distance += distanceInKm(arrayPts[i]["coord_x"], arrayPts[i]["coord_y"], arrayPts[i+1]["coord_x"], arrayPts[i+1]["coord_y"]);
            }

            document.getElementById("km").innerHTML = "Distance parcouru : " + distance.toPrecision(3) + " Km";
           
        </script>

    </body>
<%@ include file="footer.jsp"%> 
