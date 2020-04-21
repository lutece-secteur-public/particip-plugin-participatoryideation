<section id="map-result">
	
	<#include "ideation_search_solr_search_results.ftl">
	
	<div class="container">
    	<div id="map" class="clearfix" ></div>
	</div>
</section>

<script type='text/javascript'>
          var loadresource = document.createElement('link');
          loadresource.setAttribute("rel", "stylesheet");
          loadresource.setAttribute("type", "text/css");
          loadresource.setAttribute("href", "js/plugins/leaflet/leaflet/leaflet.css");
          document.getElementsByTagName("head")[0].appendChild(loadresource);

          loadresource = document.createElement('link');
          loadresource.setAttribute("rel", "stylesheet");
          loadresource.setAttribute("type", "text/css");
          loadresource.setAttribute("href", "js/plugins/leaflet/leaflet/MarkerCluster.css");
          document.getElementsByTagName("head")[0].appendChild(loadresource);

           loadresource = document.createElement('link');
          loadresource.setAttribute("rel", "stylesheet");
          loadresource.setAttribute("type", "text/css");
          loadresource.setAttribute("href", "js/plugins/leaflet/leaflet/MarkerCluster.Default.css");
          document.getElementsByTagName("head")[0].appendChild(loadresource);
  </script>
  <script type="text/javascript" src="js/plugins/leaflet/leaflet/leaflet.js"></script>
  <script type="text/javascript" src="js/plugins/leaflet/esri/esri-leaflet.js"></script>
  <script type="text/javascript" src="js/plugins/leaflet/leaflet/leaflet.markercluster.js"></script>
  <script type="text/javascript" src="js/plugins/leaflet/esri/esri-leaflet-clustered-feature-layer.js"></script>
  <script src='https://api.mapbox.com/mapbox.js/plugins/leaflet-fullscreen/v1.0.1/Leaflet.fullscreen.min.js'></script>
  <link href='https://api.mapbox.com/mapbox.js/plugins/leaflet-fullscreen/v1.0.1/leaflet.fullscreen.css' rel='stylesheet' />

<script type="text/javascript">
      var arrondissements = {
          "0": {"pos": [48.859085, 2.347403], "cnt": 0, "geoloc_cnt": 0},
          "1": {"pos": [48.862422, 2.337595], "cnt": 0, "geoloc_cnt": 0},
          "2": {"pos": [48.868222, 2.343667], "cnt": 0, "geoloc_cnt": 0},
          "3": {"pos": [48.863219, 2.360162], "cnt": 0, "geoloc_cnt": 0},
          "4": {"pos": [48.854448, 2.357675], "cnt": 0, "geoloc_cnt": 0},
          "5": {"pos": [48.844653, 2.351109], "cnt": 0, "geoloc_cnt": 0},
          "6": {"pos": [48.849865, 2.334951], "cnt": 0, "geoloc_cnt": 0},
          "7": {"pos": [48.856129, 2.312587], "cnt": 0, "geoloc_cnt": 0},
          "8": {"pos": [48.873376, 2.312882], "cnt": 0, "geoloc_cnt": 0},
          "9": {"pos": [48.877544, 2.338310], "cnt": 0, "geoloc_cnt": 0},
          "10": {"pos": [48.876526, 2.361216], "cnt": 0, "geoloc_cnt": 0},
          "11": {"pos": [48.859391, 2.380633], "cnt": 0, "geoloc_cnt": 0},
          "12": {"pos": [48.841399, 2.390914], "cnt": 0, "geoloc_cnt": 0},
          "13": {"pos": [48.829421, 2.366010], "cnt": 0, "geoloc_cnt": 0},
          "14": {"pos": [48.831096, 2.329405], "cnt": 0, "geoloc_cnt": 0},
          "15": {"pos": [48.843012, 2.294905], "cnt": 0, "geoloc_cnt": 0},
          "16": {"pos": [48.860419, 2.279201], "cnt": 0, "geoloc_cnt": 0},
          "17": {"pos": [48.887349, 2.308634], "cnt": 0, "geoloc_cnt": 0},
          "18": {"pos": [48.891820, 2.348945], "cnt": 0, "geoloc_cnt": 0},
          "19": {"pos": [48.886983, 2.386271], "cnt": 0, "geoloc_cnt": 0},
          "20": {"pos": [48.863604, 2.401843], "cnt": 0, "geoloc_cnt": 0},
      };


    var map = L.map('map', {fullscreenControl:true} );

    var defaultIcon = L.Icon.extend({
        options: {
            iconSize: [25, 41],
            iconAnchor: [12, 41],
            popupAnchor: [1, -34],
            shadowSize: [41, 41],
            shadowUrl: L.Icon.Default.imagePath + '/marker-shadow.png'
        }
    });
    var yellowIcon = new defaultIcon({
         iconUrl: L.Icon.Default.imagePath + '/marker-icon-yellow.png',
         iconRetinaUrl: L.Icon.Default.imagePath + '/marker-icon-yellow-2x.png'
    });
    var createOldProjetCluster = function (cluster) {
        var childCount = cluster.getChildCount();
        return new L.DivIcon({ html: '<div><span>' + childCount + '</span></div>', className: 'marker-cluster marker-cluster-oldproject', iconSize: new L.Point(40, 40) });
    };
    var createProjetCluster = function (cluster) {
        var childCount = cluster.getChildCount();
        return new L.DivIcon({ html: '<div><span>' + childCount + '</span></div>', className: 'marker-cluster marker-cluster-project', iconSize: new L.Point(40, 40) });
    };

    var arr = "${arr!""}";
    if ((arr == "") || (arr == "whole_city") || (arr == "all_arr")) {
        map.setView([48.85632, 2.33272], 12);
    } else {
        map.setView(arrondissements[parseInt(arr) - 75000]["pos"], 14);
    }

    // pour la première couche de fond on l'ajoute à la carte :
    var esri_streets = L.esri.basemapLayer('Streets').addTo(map);

    var markers = new L.MarkerClusterGroup({
        iconCreateFunction: createProjetCluster
    });
    var layergroup_arrondissements = L.layerGroup();
    var layergroup_currentcampaign = L.layerGroup();
    var layergroup_qpvqva = L.layerGroup();
    layergroup_currentcampaign.addLayer(markers);
    layergroup_currentcampaign.addLayer(layergroup_arrondissements);
    map.addLayer(layergroup_currentcampaign)
    var markers_oldprojects = new L.MarkerClusterGroup({
        iconCreateFunction: createOldProjetCluster
    });
    map.addLayer(layergroup_qpvqva);
    map.removeLayer(layergroup_qpvqva);

    var arrondissements_layer_qpv = L.esri.featureLayer({
//        "url": 'http://capgeo.sig.paris.fr/arcgis/rest/services/capgeo_dsti/DSTI_BP2016_QPV_QVA_WGS84/MapServer/0',
//        "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/SECTEUR_NPQV_WGS84/FeatureServer/0',
          "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/QPV_QVA_GPRU/FeatureServer/0',
        "style": {
            "color": "#ffffff",
            "weight": 0,
            "opacity": 1,
            "fillOpacity": 0.3,
            "fillColor": "#33ff33",
        },
        "where": "C_DEP='75' AND C_NAT_QPV='NQPV'"
    });
    //To have a popup giving the name (removed but might be added again)
    //arrondissements_layer_qpv.bindPopup(function (feature) {
    //        feature.properties["CodePostal"] = feature.properties["C_CAINSEE"] - 100;
    //        return L.Util.template('<p style="padding-top:10px"><strong>{L_NQPV}</strong><br/>{CodePostal}</p>', feature.properties);
    //});
    var arrondissements_layer_qva = L.esri.featureLayer( {
//        "url": 'http://capgeo.sig.paris.fr/arcgis/rest/services/capgeo_dsti/DSTI_BP2016_QPV_QVA_WGS84/MapServer/0',
//        "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/SECTEUR_NPQV_WGS84/FeatureServer/0',
          "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/QPV_QVA_GPRU/FeatureServer/0',
        "style": {
            "color": "#ffffff",
            "weight": 0,
            "opacity": 1,
            "fillOpacity": 0.3,
            "fillColor": "#33ff33",
        },
        "where": "C_DEP='75' AND C_NAT_QPV='QVA'"
    });
    //To have a popup giving the name (removed but might be added again)
    //arrondissements_layer_qva.bindPopup(function (feature) {
    //    feature.properties["CodePostal"] = feature.properties["C_CAINSEE"] - 100;
    //    return L.Util.template('<p style="padding-top:10px"><strong>{L_NQPV}</strong><br/>{CodePostal}</p>', feature.properties);
    //});
    var arrondissements_layer_gpru = L.esri.featureLayer( {
        "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/QPV_QVA_GPRU/FeatureServer/0',
        "style": {
            "color": "#ffffff",
            "weight": 0,
            "opacity": 1,
            "fillOpacity": 0.3,
            "fillColor": "#33ff33",
        },
        "where": "GPRU_NOM!=''"
    });
    var arrondissements_layer_qbp = L.esri.featureLayer( {
        "url": 'https://services1.arcgis.com/yFAX7hJID4ONeUHP/arcgis/rest/services/QPV_QVA_GPRU/FeatureServer/0',
        "style": {
            "color": "#ffffff",
            "weight": 0,
            "opacity": 1,
            "fillOpacity": 0.3,
            "fillColor": "#33ff33",
        },
        "where": "EXT_BP!=''"
    });
    layergroup_qpvqva.addLayer(arrondissements_layer_qpv);
    layergroup_qpvqva.addLayer(arrondissements_layer_qva);
    layergroup_qpvqva.addLayer(arrondissements_layer_gpru);
    layergroup_qpvqva.addLayer(arrondissements_layer_qbp);

    var points_tmp = [
        <#list points as point>
        {
                "type": "${point.type}",
                "code": "${point.code}",
                "id": "${point.id}",
                "geojson": ${point.geojson}
        }<#if point_has_next>,</#if>
        </#list>
    ];
    var points_old_projects_tmp = [
        <#list oldprojects_points as point>
        {
                "type": "${point.type}",
                "code": "${point.code}",
                "id": "${point.id}",
                "geojson": ${point.geojson}
        }<#if point_has_next>,</#if>
        </#list>
    ];
    var points = [];
    var match_geoloc = "proposal_geoloc-";
    var match_geoloc_length = match_geoloc.length;
    var match_ardt = "proposal_ardt-";
    var match_ardt_length = match_ardt.length;
    var match_paris = "proposal_paris-";
    var match_paris_length = match_paris.length;
    var match_geoloc_ardt = "ardt-";
    var match_geoloc_ardt_length = match_geoloc_ardt.length;
    var match_geoloc_paris = "paris-";
    var match_geoloc_paris_length = match_geoloc_paris.length;
    for (var i =0; i<points_tmp.length; i++) {
        var code_icon = points_tmp[i]["geojson"]["properties"]["icon"];
        if (code_icon.slice(0,match_geoloc_length) == match_geoloc) {
            if (code_icon.slice(match_geoloc_length, match_geoloc_length + match_geoloc_ardt_length) == match_geoloc_ardt) {
                var ardt_code = code_icon.slice(match_geoloc_length+match_geoloc_ardt_length,match_geoloc_length+match_geoloc_ardt_length+5); // "750XX"
                var ardt_n = parseInt(ardt_code) - 75000;
                if (1 <= ardt_n && ardt_n <= 20) {
                    arrondissements[ardt_n]["geoloc_cnt"] = arrondissements[ardt_n]["geoloc_cnt"] + 1;
                }
                points.push(points_tmp[i]);
            } else if (code_icon.slice(match_geoloc_length, match_geoloc_length + match_geoloc_paris_length) == match_geoloc_paris) {
                arrondissements["0"]["geoloc_cnt"] = arrondissements["0"]["geoloc_cnt"] + 1;
                points.push(points_tmp[i]);
            }
        } else if (code_icon.slice(0,match_ardt_length) == match_ardt) {
            var ardt_code = code_icon.slice(match_ardt_length,match_ardt_length+5); // "750XX"
            var ardt_n = parseInt(ardt_code) - 75000;
            if (1 <= ardt_n && ardt_n <= 20) {
                arrondissements[ardt_n]["cnt"] = arrondissements[ardt_n]["cnt"] + 1;
            }
        } else if (code_icon.slice(0,match_paris_length) == match_paris) {
            arrondissements["0"]["cnt"] = arrondissements["0"]["cnt"] + 1;
        } else if (points_tmp[i]["type"] == "doc") {
            points.push(points_tmp[i]);
        }
    }
    for (var i =0; i<points_old_projects_tmp.length; i++) {
            points.push(points_old_projects_tmp[i]);
    }

    for (var i in arrondissements) {
        if (arrondissements[i]["cnt"] != 0) {
        var radius = i == 0 ? 40 : 15+Math.floor(5*Math.log(Math.max(1, arrondissements[i]["cnt"])) / Math.LN10);
        var clazz = i == 0 ? "paris-cluster paris-cluster-small" : "ardt-cluster ardt-cluster-small";
        var icon = new L.DivIcon({
            className: 'label',
            html: "<div class=\"leaflet-marker-icon " + clazz + "\"><div style=\"width: " + radius + "px; height:" + radius + "px; margin-top:-" + (radius/2) + "px; margin-left:-" + (radius/2) + "px;\"><span style=\"line-height:" + radius + "px\">"+arrondissements[i]["cnt"]+"</span></div></div>"
        });
        var circleLabel = L.marker(arrondissements[i]["pos"], {icon:icon});
        layergroup_arrondissements.addLayer(circleLabel);

        var strProjet = "projet" + ((arrondissements[i]["geoloc_cnt"] + arrondissements[i]["cnt"]) != 1 ? "s" : "");
        var strArdtDescription = (i==0) ? "pour Paris" : "dans cet arrondissement";
        var strPopup = "<p>Il y a " +(arrondissements[i]["cnt"] + arrondissements[i]["geoloc_cnt"]) + " " + strProjet + " " + strArdtDescription + ", dont " + arrondissements[i]["cnt"] + " sans adresse précise.</p><p><button type=\"button\" class=\"btn btn-xs btn-lt-bg\" onclick=\"onClickArdtPopup("+i+");\">Voir ces projets</button></p>";
        circleLabel.bindPopup(strPopup);
        }
    }
    for (var i =0; i<points.length; i++) {
        points[i].loc = [points[i].geojson.geometry.coordinates[1], points[i].geojson.geometry.coordinates[0]];
        points[i].portlet_id= "foo";
    }

    for (var i in points) {
        var marker;
        if (points[i]["type"] == "doc") {
            marker = L.marker(points[i]["loc"], {icon: yellowIcon});
        } else {
            marker = L.marker(points[i]["loc"]);
        }
        popupContent = "<p>Chargement de " + points[i]["type"] + " " + points[i]["id"] + " " + points[i]["code"] + "...</p>";
        marker.bindPopup(popupContent)
        marker.on('click', (function(point) {
            return function(e) {
                var popup = e.target.getPopup();
                if (!popup._LuteceUpdated) {
                    var properties = point["properties"];
                    var url;
                    if ( (typeof(properties) != 'undefined') && (typeof(properties["popupAjax"]) != 'undefined') ) {
                        url = properties["popupAjax"];
                    } else {
                        url = "rest/leaflet/popup/" + point["type"] + "/" + point["id"] + "/" + point["code"];
                    }
                    $.get(url).done(function(data) {
                        popup.setContent(data);
                        popup.update();
                        popup._LuteceUpdated = true;
                    }).fail(function() {
                        map.closePopup();
                    });
                }
            };
        })(points[i]));
        if (points[i]["type"] == "doc") {
            markers_oldprojects.addLayer(marker);
        } else {
            markers.addLayer(marker);
        }
    }

    var positron = L.tileLayer('http://s.basemaps.cartocdn.com/light_all/{z}/{x}/{y}.png', {
        attribution: 'CartoDB base map, data from <a href="http://openstreetmap.org">OpenStreetMap</a>'
    });
    var esri_imagery = L.esri.basemapLayer('Imagery', {
        maxNativeZoom: 18,
    });

  var baseMaps = {
      "#i18n{participatoryideation.view.map.legend.map_type.standard}": esri_streets,
      "#i18n{participatoryideation.view.map.legend.map_type.monochromatic}": positron,
      "#i18n{participatoryideation.view.map.legend.map_type.satellite}" : esri_imagery
  };

  var overlayMaps = {
      "#i18n{participatoryideation.view.map.legend.map_layer.currentSearch}" : layergroup_currentcampaign,
      "#i18n{participatoryideation.view.map.legend.map_layer.previousCampaigns}" : layergroup_qpvqva,
      "#i18n{participatoryideation.view.map.legend.map_layer.popularLocations}" : markers_oldprojects
  };
  // paramétrage et ajout du L.control.layers à la carte
  var control =  L.control.layers(baseMaps, overlayMaps, {collapsed:false}).addTo(map);

// !This depends on the text inside the select!
function onClickArdtPopup(i) {
    if (i==0) {
        $("#arrondissement option:contains('whole_city')").prop({selected: true});
    } else {
        $("#arrondissement option:contains(' " + i + "e')").prop({selected: true});
    }
    displayInMap();
    searchTri();
    return false;
}

	// Leaflet Legend
	var legend = L.control({position: 'bottomleft'});
	legend.onAdd = function (map) {
		var div = L.DomUtil.create('div', 'map-legend leaflet-control-layers leaflet-control-layers-expanded');
		div.innerHTML += '<div class="map-legend-line"><div class="marker"><img src="' +  (L.Icon.Default.imagePath + '/marker-icon.png') + '"></div>#i18n{participatoryideation.view.map.legend.withLocation}</div>';
		div.innerHTML += '<div class="map-legend-line"><div class="paris-cluster-small paris-cluster"><div><span style="line-height:21px">&nbsp;</span></div></div>#i18n{participatoryideation.view.map.legend.wholeWithoutLocation}</div>';
		div.innerHTML += '<div class="map-legend-line"><div class=" ardt-cluster-small  ardt-cluster"><div><span style="line-height:21px">&nbsp;</span></div></div>#i18n{participatoryideation.view.map.legend.areaWithoutLocation}</div>';
		return div;
	};
	legend.addTo(map);

  </script>