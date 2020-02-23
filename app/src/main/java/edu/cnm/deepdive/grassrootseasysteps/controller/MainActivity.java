package edu.cnm.deepdive.grassrootseasysteps.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import edu.cnm.deepdive.grassrootseasysteps.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, TomtomMapCallback.OnMapLongClickListener {

  private TomtomMap tomtomMap;

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
    public void onMapLongClick(LatLng latLng) {}

    private void initTomTomServices() {
      MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
          .findFragmentById(R.id.mapFragment);
      mapFragment.getAsyncMap(this);
    }

    private void initUIViews() {}
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
