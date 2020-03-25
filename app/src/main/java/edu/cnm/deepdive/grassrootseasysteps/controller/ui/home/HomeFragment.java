package edu.cnm.deepdive.grassrootseasysteps.controller.ui.home;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
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
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResponse;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;
import edu.cnm.deepdive.grassrootseasysteps.BuildConfig;
import edu.cnm.deepdive.grassrootseasysteps.R;
import edu.cnm.deepdive.grassrootseasysteps.controller.MainActivity;
import edu.cnm.deepdive.grassrootseasysteps.viewmodel.MainViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
    TomtomMapCallback.OnMapLongClickListener {

  private TomtomMap tomtomMap;
  private SearchApi searchApi;
  private RoutingApi routingApi;
  private Route route;
  private LatLng departurePosition;
  private LatLng destinationPosition;
  private LatLng wayPointPosition;
  private Icon departureIcon;
  private Icon destinationIcon;
  private ImageButton searchButton;
  private EditText searchText;
  private MainViewModel viewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    initTomTomServices();
    initUIViews(root);
    setupUIViewListeners();
    return root;
  }

  @Override
  public void onMapReady(@NonNull final TomtomMap tomtomMap) {
    setupViewModel(tomtomMap);
    this.tomtomMap = tomtomMap;
    this.tomtomMap.centerOnMyLocationWithNorthUp();
    this.tomtomMap.setMyLocationEnabled(true);
    this.tomtomMap.addOnMapLongClickListener(this);
    this.tomtomMap.getMarkerSettings().setMarkersClustering(true);
  }

  private void setupViewModel(TomtomMap tomtomMap) {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getPermissions().observe(this, (permissions) -> {
      String[] perms = permissions.toArray(new String[0]);
      int[] grants = new int[perms.length];
      Arrays.fill(grants, PackageManager.PERMISSION_GRANTED);
      tomtomMap.onRequestPermissionsResult(MainActivity.PERMISSION_REQUEST_CODE, perms, grants);
    });

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
    searchApi.reverseGeocoding(
        new ReverseGeocoderSearchQueryBuilder(latLng.getLatitude(), latLng.getLongitude()).build())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            this::processResponse,
            this::handleApiError
        );
  }

  private void processResponse(ReverseGeocoderSearchResponse response) {
    if (response.hasResults()) {
      processFirstResult(response.getAddresses().get(0).getPosition());
    } else {
      Toast.makeText(getContext(), getString(R.string.geocode_no_results),
          Toast.LENGTH_SHORT).show();
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

  private boolean isDestinationPositionSet() {
    return destinationPosition != null;
  }

  private boolean isDeparturePositionSet() {
    return departurePosition != null;
  }

  private RouteQuery createRouteQuery(LatLng start, LatLng stop, LatLng[] wayPoints) {
    return (wayPoints != null) ?
        new RouteQueryBuilder(start, stop).withWayPoints(wayPoints).withRouteType(RouteType.FASTEST)
            .build() :
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
        .subscribe(
            (response) -> {
              displayRoutes(response.getRoutes());
              tomtomMap.displayRoutesOverview();
            },
            (throwable) -> {
              handleApiError(throwable);
              clearMap();

            }
        );
  }

  private void displayRoutes(List<FullRoute> routes) {
    for (FullRoute fullRoute : routes) {
      route = tomtomMap.addRoute(new RouteBuilder(
          fullRoute.getCoordinates()).startIcon(departureIcon).endIcon(destinationIcon));
    }
  }

  private void createMarkerIfNotPresent(LatLng position, Icon icon) {
    Optional optionalMarker = tomtomMap.findMarkerByPosition(position);
    if (!optionalMarker.isPresent()) {
      tomtomMap.addMarker(new MarkerBuilder(position)
          .icon(icon));
    }
  }

  private void handleApiError(Throwable e) {
    Toast.makeText(getContext(),
        getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
  }

  private void initTomTomServices() {
    MapFragment mapFragment = (MapFragment) getChildFragmentManager()
        .findFragmentById(R.id.mapFragment);
    mapFragment.getAsyncMap(this);
    searchApi = OnlineSearchApi.create(getContext(), BuildConfig.API_KEY);
    routingApi = OnlineRoutingApi.create(getContext(), BuildConfig.API_KEY);
  }

  private void initUIViews(View root) {
    searchButton = root.findViewById(R.id.btn_main_poisearch);
    searchText = root.findViewById(R.id.edittext_main_poisearch);
    departureIcon = Icon.Factory
        .fromResources(getContext(), R.drawable.ic_map_route_departure);
    destinationIcon = Icon.Factory
        .fromResources(getContext(), R.drawable.ic_map_route_destination);
  }

  private void clearMap() {
    tomtomMap.clear();
    departurePosition = null;
    destinationPosition = null;
    route = null;
  }

  private void setupUIViewListeners() {
    searchButton.setOnClickListener(this::handleSearchClick);
  }


  private void handleSearchClick(View v) {
    if (isRouteSet()) {
      Optional<CharSequence> description = Optional.fromNullable(v.getContentDescription());
      if (description.isPresent()) {
        searchText.setText(description.get());
        v.setSelected(true);
      }
      if (isWayPointPositionSet()) {
        tomtomMap.clear();
        drawRoute(departurePosition, destinationPosition);
      }
      String textToSearch = searchText.getText().toString();
      if (!textToSearch.isEmpty()) {
        tomtomMap.removeMarkers();
        searchAlongTheRoute(route, textToSearch);
      }
    }
  }


  private boolean isRouteSet() {
    return route != null;
  }

  private boolean isWayPointPositionSet() {
    return wayPointPosition != null;
  }

  private void searchAlongTheRoute(Route route, final String textToSearch) {
    final Integer MAX_DETOUR_TIME = 1000;
    final Integer QUERY_LIMIT = 10;
    searchApi.alongRouteSearch(
        new AlongRouteSearchQueryBuilder(textToSearch, route.getCoordinates(), MAX_DETOUR_TIME)
            .withLimit(QUERY_LIMIT).build())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (response) -> displaySearchResults(response.getResults(), textToSearch),
            this::handleApiError
        );
  }

  private void displaySearchResults(List<AlongRouteSearchResult> results, String textToSearch) {
    if (!results.isEmpty()) {
      for (AlongRouteSearchResult result : results) {
        createAndDisplayCustomMarker(result.getPosition(), result);
      }
      tomtomMap.zoomToAllMarkers();
    } else {
      Toast.makeText(getContext(),
          String.format(getString(R.string.no_search_results), textToSearch),
          Toast.LENGTH_LONG).show();
    }
  }

  private void createAndDisplayCustomMarker(LatLng position,
      AlongRouteSearchResult result) {
    String address = result.getAddress().getFreeformAddress();
    String poiName = result.getPoi().getName();
    BaseMarkerBalloon markerBalloonData = new BaseMarkerBalloon();
    markerBalloonData.addProperty(getString(R.string.poi_name_key), poiName);
    markerBalloonData.addProperty(getString(R.string.address_key), address);
    MarkerBuilder markerBuilder = new MarkerBuilder(position)
        .markerBalloon(markerBalloonData)
        .shouldCluster(true);
    tomtomMap.addMarker(markerBuilder);
  }


}