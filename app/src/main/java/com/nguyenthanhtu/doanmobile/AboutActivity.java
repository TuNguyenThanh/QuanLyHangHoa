package com.nguyenthanhtu.doanmobile;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thanhtu on 9/30/16.
 */
public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mgoogleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServiceAvailable()){
            Toast.makeText(this,"good", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_about);
            //setTitle(this.getString(R.string.simple_about));

            //getActionBar().setHomeButtonEnabled(true);

            initMap();
        }else {
            //No google map layout
            Toast.makeText(this,"no layout", Toast.LENGTH_LONG).show();
        }
    }

    private void initMap() {
        MapFragment mapFragemt = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragemt.getMapAsync(AboutActivity.this);
    }


    public boolean googleServiceAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(AboutActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(AboutActivity.this, isAvailable, 0);
            dialog.show();
        }else{
            Toast.makeText(AboutActivity.this,"Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mgoogleMap = googleMap;
        goToLocaltionZoom(10.738178, 106.677940, 16);
    }

    private void goToLocaltionZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        this.mgoogleMap.moveCamera(update);

        MarkerOptions options = new MarkerOptions().title("Trường Đại học Công nghệ Sài Gòn").position(ll);
        this.mgoogleMap.addMarker(options);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
