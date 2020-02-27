package edu.cnm.deepdive.grassrootseasysteps.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.ApiKeyType;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MapProperties;
import com.tomtom.online.sdk.map.MapProperties.Builder;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResponse;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;
import edu.cnm.deepdive.grassrootseasysteps.BuildConfig;
import edu.cnm.deepdive.grassrootseasysteps.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, TomtomMapCallback.OnMapLongClickListener {

  private MapProperties properties;
  private ApiKeyType keyType;
  private TomtomMap tomtomMap;
  private SearchApi searchApi;
  private RoutingApi routingApi;
  private Route route;
  private LatLng departurePosition;
  private LatLng destinationPosition;
  private LatLng wayPointPosition;
  private Icon departureIcon;
  private Icon destinationIcon;


    @Override
    public void onMapReady(@NonNull final TomtomMap tomtomMap) {
      this.tomtomMap = tomtomMap;
      this.tomtomMap.setMyLocationEnabled(true);
      this.tomtomMap.addOnMapLongClickListener(this);
      this.tomtomMap.getMarkerSettings().setMarkersClustering(true);
    }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    this.tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
    public void onMapLongClick(LatLng latLng) {
      if (isDeparturePositionSet() && isDestinationPositionSet()) {
        clearMap();
      } else {
        handleLongClick(latLng);
      }
  }

  private void handleLongClick(@NonNull LatLng latLng) {
      searchApi.reverseGeocoding(new ReverseGeocoderSearchQueryBuilder(latLng.getLatitude(), latLng.getLongitude()).build())
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new DisposableSingleObserver<ReverseGeocoderSearchResponse>() {
        @Override
        public void onSuccess(ReverseGeocoderSearchResponse response) {
          processResponse(response);
        }

        @Override
        public void onError(Throwable e) {
          handleApiError(e);
        }

        private void processResponse(ReverseGeocoderSearchResponse response) {
          if (response.hasResults()) {
            processFirstResult(response.getAddresses().get(0).getPosition());
          } else {
            Toast.makeText(MainActivity.this, getString(R.string.geocode_no_results), Toast.LENGTH_SHORT).show();
          }
        }

        private void processFirstResult(LatLng geocodedPosition) {
          if (!isDeparturePositionSet()) {
            setAndDisplayDeparturePosition(geocodedPosition);
          } else {
            destinationPosition = geocodedPosition;
            tomtomMap.removeMarkers();
            drawRoute(departurePosition, destinationPosition);
          }
        }
        private void setAndDisplayDeparturePosition(LatLng geocodedPosition) {
          departurePosition = geocodedPosition;
          createMarkerIfNotPresent(departurePosition, departureIcon);
        }
      });
  }

    private boolean isDestinationPositionSet() {
      return destinationPosition != null;
    }
    private boolean isDeparturePositionSet() {
      return departurePosition != null;
    }

  private RouteQuery createRouteQuery(LatLng start, LatLng stop, LatLng[] wayPoints) {
    return (wayPoints != null) ?
        new RouteQueryBuilder(start, stop).withWayPoints(wayPoints).withRouteType(RouteType.FASTEST).build() :
        new RouteQueryBuilder(start, stop).withRouteType(RouteType.FASTEST).build();
  }

  private void drawRoute(LatLng start, LatLng stop) {
    wayPointPosition = null;
    drawRouteWithWayPoints(start, stop, null);
  }

  private void drawRouteWithWayPoints(LatLng start, LatLng stop, LatLng[] wayPoints) {
    RouteQuery routeQuery = createRouteQuery(start, stop, wayPoints);
    routingApi.planRoute(routeQuery)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableSingleObserver<RouteResponse>() {

          @Override
          public void onSuccess(RouteResponse routeResponse) {
            displayRoutes(routeResponse.getRoutes());
            tomtomMap.displayRoutesOverview();
          }

          private void displayRoutes(List<FullRoute> routes) {
            for (FullRoute fullRoute : routes) {
              route = tomtomMap.addRoute(new RouteBuilder(
                  fullRoute.getCoordinates()).startIcon(departureIcon).endIcon(destinationIcon));
            }
          }

          @Override
          public void onError(Throwable e) {
            handleApiError(e);
            clearMap();
          }
        });
  }

  private void createMarkerIfNotPresent(LatLng position, Icon icon) {
    Optional optionalMarker = tomtomMap.findMarkerByPosition(position);
    if (!optionalMarker.isPresent()) {
      tomtomMap.addMarker(new MarkerBuilder(position)
          .icon(icon));
    }
  }

    private void handleApiError(Throwable e) {
      Toast.makeText(MainActivity.this, getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
    }

    public static  void initMap () {
      Map keys =  new Map<ApiKeyType, String>

    }
    private void initTomTomServices() {
      Map keys = new Map<ApiKeyType, String>
      keys.put(ApiKeyType.MAPS_API_KEY, BuildConfig.API_KEY);
      properties = new MapProperties.Builder().keys(keys).build();
      MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
          .findFragmentById(R.id.mapFragment);
      mapFragment.getAsyncMap(this);
      searchApi = OnlineSearchApi.create(this, BuildConfig.API_KEY);
      routingApi = OnlineRoutingApi.create(this, BuildConfig.API_KEY);
    }

    private void initUIViews() {
      departureIcon = Icon.Factory.fromResources(MainActivity.this, R.drawable.ic_map_route_departure);
      destinationIcon = Icon.Factory.fromResources(MainActivity.this, R.drawable.ic_map_route_destination);
    }

    private void clearMap() {
      tomtomMap.clear();
      departurePosition = null;
      destinationPosition = null;
      route = null;
    }

    private void setupUIViewListeners(){}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BottomNavigationView navView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);
    initTomTomServices();
    initUIViews();
    setupUIViewListeners();
  }

}
