package me.chriskyle.library.net.connection;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/12/8.
 */
public abstract class BaseApiSSLConnector extends BaseApiConnector {

    private static List<byte[]> CERTIFICATES_DATA = new ArrayList<>();

    private Context context;

    public BaseApiSSLConnector(Context context, Interceptor signInterceptor, long timeout) {
        super(signInterceptor, timeout);

        this.context = context;
        createHttpCerts();
    }

    @Override
    public OkHttpClient.Builder createHttpBuilder() {
        List<InputStream> certificates = new ArrayList<>();
        List<byte[]> certsData = getCertificatesData();

        if (certsData != null && !certsData.isEmpty()) {
            for (byte[] bytes : certsData) {
                certificates.add(new ByteArrayInputStream(bytes));
            }
        }

        HttpsManager.SSLParams sslParams = HttpsManager.getSslSocketFactory(certificates, null, null);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new HttpsManager.UnSafeHostnameVerifier());

        return builder;
    }

    private void createHttpCerts() {
        try {
            String[] certFiles = context.getAssets().list("certs");
            if (certFiles != null) {
                for (String cert : certFiles) {
                    InputStream is = context.getAssets().open("certs/" + cert);
                    addCertificate(is);
                }
            }
        } catch (IOException e) {
        }
    }

    private synchronized static void addCertificate(InputStream inputStream) {
        if (null != inputStream) {
            try {
                int ava;
                int len = 0;
                ArrayList<byte[]> data = new ArrayList<>();
                while ((ava = inputStream.available()) > 0) {
                    byte[] buffer = new byte[ava];
                    inputStream.read(buffer);
                    data.add(buffer);
                    len += ava;
                }

                byte[] buff = new byte[len];
                int dstPos = 0;
                for (byte[] bytes : data) {
                    int length = bytes.length;
                    System.arraycopy(bytes, 0, buff, dstPos, length);
                    dstPos += length;
                }
                CERTIFICATES_DATA.add(buff);
            } catch (IOException e) {
            }
        }
    }

    private static List<byte[]> getCertificatesData() {
        return CERTIFICATES_DATA;
    }
}
