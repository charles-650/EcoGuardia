package com.devst.ecoguardia;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class SyncService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Uso de Thread para la tarea asíncrona requerida por el aprendizaje esperado
        new Thread(() -> {
            try {
                // Simulación de sincronización asíncrona de 3 segundos
                Thread.sleep(3000);

                // Retornar el resultado al hilo principal (UI Thread)
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(getApplicationContext(), "Sincronización en segundo plano completada 🌿", Toast.LENGTH_LONG).show()
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopSelf(); // Detener el servicio al finalizar
        }).start();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}