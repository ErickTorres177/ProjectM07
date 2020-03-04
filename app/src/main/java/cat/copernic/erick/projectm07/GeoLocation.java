package cat.copernic.erick.projectm07;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocation {
    public static void getAdress(final String locationAddress, final Context context, final Handler handler) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String lat = null;
                String longit = null;
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(address.getLatitude());
                        lat = stringBuilder.toString();

                        stringBuilder = new StringBuilder();
                        stringBuilder.append(address.getLongitude());
                        longit = stringBuilder.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (lat != null && longit != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("latitud", lat);
                        bundle.putString("longitud", longit);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
