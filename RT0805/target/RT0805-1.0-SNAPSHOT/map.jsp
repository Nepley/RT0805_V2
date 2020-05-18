<jsp:include page="header.jsp">
    <jsp:param name="title" value="Bienvenue sur la map" />
</jsp:include>
    <body>
        <div id="mapid" style="width: 100%; height: 600px;"></div>
        <script>

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
            //var marker1 = L.marker([49.26224, 4.052293]).addTo(maCarte);
            //var marker2 = L.marker([69.26223999999999, -25.947707]).addTo(maCarte);

            
            arrayPts.forEach(CreateMarker);

            pathCoords = connectTheDots(arrayPts);
            //var polygon = L.polygon([[49.26224, 4.052293], [69.26223999999999, -25.947707]]).addTo(maCarte);
            
           
        </script>

    </body>
<%@ include file="footer.jsp"%> 
