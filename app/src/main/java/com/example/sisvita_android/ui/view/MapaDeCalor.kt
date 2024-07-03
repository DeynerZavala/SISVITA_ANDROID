package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.data.model.MapaDeCalorData
import com.example.sisvita_android.ui.viewmodel.MapaDeCalorViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import kotlin.math.min

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaDeCalor(ids: List<Int>, navController: NavController, mapaDeCalorViewModel: MapaDeCalorViewModel = viewModel()) {
    mapaDeCalorViewModel.setSelectedID(ids)
    val mapaDeCalorResponse by mapaDeCalorViewModel.mapaDeCalorResponse.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mapa de Calor",
                        color = androidx.compose.ui.graphics.Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        content = {
            mapaDeCalorResponse?.data?.let { data ->
                MapViewContainer(data = data)
            }
        }
    )
}

@Composable
fun MapViewContainer(data: List<MapaDeCalorData>) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(factory = { mapView }) { mapView ->
        setupHeatMap(mapView, data)
    }
}

fun setupHeatMap(mapView: MapView, data: List<MapaDeCalorData>) {
    val context = mapView.context
    Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", 0))
    mapView.setTileSource(TileSourceFactory.MAPNIK)
    mapView.setMultiTouchControls(true)
    mapView.controller.setZoom(10.0)

    val geoPoints = data.map { GeoPoint(it.latitud, it.longitud) }
    val ubigeoScores = data.groupBy { it.ubigeo }.mapValues { entry ->
        entry.value.map { it.puntuacion.toFloat() / it.maximo }.average().toFloat()
    }

    val heatMapOverlay = object : Overlay() {
        override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
            val paint = Paint()
            paint.style = Paint.Style.FILL

            val projection = mapView.projection
            val screenPoint = Point()

            for (point in data) {
                val geoPoint = GeoPoint(point.latitud, point.longitud)
                projection.toPixels(geoPoint, screenPoint)

                val intensity = ubigeoScores[point.ubigeo] ?: 0f
                paint.color = getColorForIntensity(intensity)
                canvas.drawCircle(screenPoint.x.toFloat(), screenPoint.y.toFloat(), 50f, paint)
            }
        }
    }

    mapView.overlays.add(heatMapOverlay)
    if (geoPoints.isNotEmpty()) {
        mapView.controller.setCenter(geoPoints.first())
    }
}



fun getColorForIntensity(intensity: Float): Int {
    val normalizedIntensity = min(intensity, 1f)
    return Color.argb(
        (normalizedIntensity * 255).toInt(),
        (255 * normalizedIntensity).toInt(),
        (255 * (1 - normalizedIntensity)).toInt(),
        0
    )
}


@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    DisposableEffect(Unit) {
        mapView.onResume()
        onDispose { mapView.onPause() }
    }
    return mapView
}
